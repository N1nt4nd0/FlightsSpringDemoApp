package by.feodorkek.flights.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * A {@link Component @Component} which that intercepts exceptions that occur during
 * user authentication in {@link SpringSecurityConfig}
 */
@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

    /**
     * Called when an authentication exception occurs,
     * sets the response status to 401 (UNAUTHORIZED)
     * and print the exception message in the response body
     *
     * @param request   that resulted in an <code>AuthenticationException</code>
     * @param response  user agent can begin authentication
     * @param exception that caused the invocation
     * @throws IOException throwing of {@code response.getWriter().print} invocation
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(exception.getMessage());
    }

}