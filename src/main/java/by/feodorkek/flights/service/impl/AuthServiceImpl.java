package by.feodorkek.flights.service.impl;

import by.feodorkek.flights.aspect.logging.ExcludeFromAspectLog;
import by.feodorkek.flights.config.JwtTokenProvider;
import by.feodorkek.flights.dto.LoginDto;
import by.feodorkek.flights.dto.TokenDto;
import by.feodorkek.flights.service.AuthService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }

    public String refreshToken(TokenDto authDto) {
        if (tokenProvider.validateToken(authDto.getAccessToken())) {
            return tokenProvider.generateToken(SecurityContextHolder.getContext().getAuthentication());
        } else {
            throw new JwtException("Invalid input token");
        }
    }

    @ExcludeFromAspectLog
    public long getTokenExpirationTime() {
        return tokenProvider.getExpirationTime();
    }

}