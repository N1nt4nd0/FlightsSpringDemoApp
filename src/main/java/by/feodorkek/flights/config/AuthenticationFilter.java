package by.feodorkek.flights.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;

/**
 * This {@link Component @Component} is used for user token authentication
 * <p>
 * Extends {@link OncePerRequestFilter} which allows the request to be executed only once
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    /**
     * {@link UserDetailsService} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     * <p>
     * Required for loading user from users database
     */
    private final UserDetailsService userDetailsService;

    /**
     * {@link JwtTokenProvider} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     * <p>
     * Required for generating, validating token and getting username from token
     */
    private final JwtTokenProvider tokenProvider;

    /**
     * The main filter method in which the token is received from headers, the token is validated,
     * {@link UserDetails} is obtained and the user is authenticated.
     * <p>
     * If any exception occurs in method, response status will set to 401 (UNAUTHORIZED)
     * and send exception message to client in response body as a plain/text
     *
     * @param request     request from {@link DispatcherServlet}
     * @param response    response to {@link DispatcherServlet}
     * @param filterChain a chain that must be continued upon successful completion of the method
     */
    @Override
    public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                 @NonNull FilterChain filterChain) {
        try {
            // Extracting token from request headers
            String token = getTokenFromRequest(request);
            // Check if have token and token validating
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                // Getting username from token
                String username = tokenProvider.getUsername(token);
                // Load user from database
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // If everything went well, the user is authenticated
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            // Continuation of the filter chain
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // Set status UNAUTHORIZED to response if something went wrong during authentication
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // Sending Exception message to client
            try {
                response.setContentType("text/plain");
                response.getWriter().print(e.getMessage());
            } catch (IOException exception) {
                logger.error(exception);
            }
        }
    }

    /**
     * Get String token from request "Authorization" header
     *
     * @param request Request from {@link DispatcherServlet}
     * @return Token string or null if headers not contains token information
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            // substring(7) because token string in headers contains "Bearer " prefix
            return bearerToken.substring(7);
        }
        return null;
    }

}