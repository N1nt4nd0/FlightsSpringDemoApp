package by.feodorkek.flights.config;

import by.feodorkek.flights.model.FlightsUser;
import by.feodorkek.flights.service.impl.FlightsUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final FlightsUserServiceImpl flightsUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FlightsUser flightsUser = flightsUserService.getByUsername(username);
        return User.builder()
                .username(flightsUser.getUsername())
                .password(flightsUser.getPassword())
                .roles(flightsUser.getRoles())
                .accountLocked(!flightsUser.isActive())
                .build();
    }

}