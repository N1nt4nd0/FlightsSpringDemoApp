package by.feodorkek.flights.service.impl;

import by.feodorkek.flights.dto.ActiveFlightsUserDto;
import by.feodorkek.flights.dto.FlightsUserDto;
import by.feodorkek.flights.model.FlightsUser;
import by.feodorkek.flights.repository.FlightsUserRepository;
import by.feodorkek.flights.service.FlightsUserService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class FlightsUserServiceImpl implements FlightsUserService {

    private final FlightsUserRepository flightsUserRepository;
    private final ApplicationContext applicationContext;

    @Override
    @Transactional
    public Page<FlightsUser> getAllUsers(Pageable pageable) {
        return flightsUserRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public FlightsUser getByUsername(String username) {
        return flightsUserRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Username '%s' not found", username)));
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        FlightsUser flightsUser = getByUsername(username);
        flightsUserRepository.delete(flightsUser);
    }

    @Override
    @Transactional
    public void createUser(FlightsUserDto flightsUserDto) {
        String username = flightsUserDto.getUsername(), password = flightsUserDto.getPassword();
        String[] roles = flightsUserDto.getRoles();
        if ((username == null || username.isEmpty()) || (password == null || password.isEmpty()) || (roles == null || roles.length == 0 ||
                Arrays.stream(roles).anyMatch(s -> s == null || s.isEmpty()))) {
            throw new IllegalArgumentException("Bad arguments");
        }
        if (flightsUserRepository.findById(username).isPresent()) {
            throw new EntityExistsException("User already exist");
        }
        PasswordEncoder passwordEncoder = applicationContext.getBean(PasswordEncoder.class);
        FlightsUser flightsUser = FlightsUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .active(true)
                .roles(roles)
                .build();
        flightsUserRepository.save(flightsUser);
    }

    @Override
    @Transactional
    public void setActive(ActiveFlightsUserDto activeFlightsUserDto) {
        FlightsUser flightsUser = getByUsername(activeFlightsUserDto.getUsername());
        flightsUser.setActive(activeFlightsUserDto.isActive());
        flightsUserRepository.save(flightsUser);
    }

}