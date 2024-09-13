package by.feodorkek.flights.service;

import by.feodorkek.flights.dto.LoginDto;
import by.feodorkek.flights.dto.TokenDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String refreshToken(TokenDto authDto);

    long getTokenExpirationTime();

}