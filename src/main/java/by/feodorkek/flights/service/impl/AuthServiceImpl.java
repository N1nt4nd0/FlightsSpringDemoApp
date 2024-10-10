package by.feodorkek.flights.service.impl;

import by.feodorkek.flights.aspect.logging.ExcludeFromAspectLog;
import by.feodorkek.flights.aspect.logging.loggers.ServicesLogAspect;
import by.feodorkek.flights.config.JwtTokenProvider;
import by.feodorkek.flights.config.SpringSecurityConfig;
import by.feodorkek.flights.dto.LoginDto;
import by.feodorkek.flights.dto.TokenDto;
import by.feodorkek.flights.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * {@link AuthService} implementation for authentication and token processing
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    /**
     * {@link AuthenticationManager} instance from Spring context which created in
     * {@link Bean @Bean} {@code authenticationManager} declared in {@link SpringSecurityConfig}
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     */
    private final AuthenticationManager authenticationManager;

    /**
     * {@link JwtTokenProvider} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     */
    private final JwtTokenProvider tokenProvider;

    /**
     * Main login method. Creates {@link Authentication} and generates new JWT token
     * If authentication is success, returns new generated JWT token,
     * otherwise throw AuthenticationException, InvalidKeyException
     *
     * @param loginDto Dto with username and password
     * @return generated token
     */
    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }

    /**
     * Validating existing input token and return refreshed JWT token string with new expire date
     *
     * @param authDto Dto with existing token value
     * @return new JWT token string
     */
    @Override
    public String refreshToken(TokenDto authDto) {
        // Validating exist token or throw inner exceptions. See in validateToken method realize
        tokenProvider.validateToken(authDto.getAccessToken());
        // Getting authentication of current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Return generated token from authentication or throw exception
        return tokenProvider.generateToken(authentication);
    }

    /**
     * JWT token expiration time which accepted to all new tokens.
     * Method marked by {@link ExcludeFromAspectLog @ExcludeFromAspectLog} annotation
     * that excludes it from {@link ServicesLogAspect} logic
     *
     * @return time in milliseconds after which the new token becomes invalid.
     * Configured in application configuration
     */
    @ExcludeFromAspectLog
    public long getTokenExpirationTime() {
        return tokenProvider.getExpirationTime();
    }

}