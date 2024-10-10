package by.feodorkek.flights.service;

import by.feodorkek.flights.dto.LoginDto;
import by.feodorkek.flights.dto.TokenDto;
import org.springframework.security.core.Authentication;

public interface AuthService {

    /**
     * Main login method. Creates {@link Authentication} and generates new JWT token
     * If authentication is success, returns new generated JWT token,
     * otherwise throw AuthenticationException, InvalidKeyException
     *
     * @param loginDto Dto with username and password
     * @return Generated token
     */
    String login(LoginDto loginDto);

    /**
     * Validating existing input token and return refreshed JWT token string with new expire date
     *
     * @param authDto Dto with existing token value
     * @return new JWT token string
     */
    String refreshToken(TokenDto authDto);

    /**
     * JWT token expiration time which accepted to all new tokens
     *
     * @return time in milliseconds after which the new token becomes invalid
     * Configured in application configuration
     */
    long getTokenExpirationTime();

}