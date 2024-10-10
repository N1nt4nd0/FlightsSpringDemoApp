package by.feodorkek.flights.service.impl;

import by.feodorkek.flights.config.ApplicationContextProvider;
import by.feodorkek.flights.config.RootUserProvider;
import by.feodorkek.flights.dto.ActiveFlightsUserDto;
import by.feodorkek.flights.dto.FlightsUserDto;
import by.feodorkek.flights.model.FlightsUser;
import by.feodorkek.flights.repository.FlightsUserRepository;
import by.feodorkek.flights.service.FlightsUserService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

/**
 * {@link FlightsUserService} implementation for work with application users
 */
@Service
@RequiredArgsConstructor
public class FlightsUserServiceImpl implements FlightsUserService {

    /**
     * {@link FlightsUserRepository} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     */
    private final FlightsUserRepository flightsUserRepository;

    /**
     * {@link RootUserProvider} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     * <p>
     * Contains credentials of the root user specified in root-user application configuration
     */
    private final RootUserProvider rootUserProvider;

    /**
     * Getting {@link Page} of users in service using pagination request in input parameter
     * <p>
     * Example of code usage for getting all users in database:
     * <blockquote><pre>
     * PageRequest pageable = PageRequest.ofSize(Integer.MAX_VALUE);
     * Page<FlightUser> flightUsers = getAllUsers(pageable);
     * </pre></blockquote>
     *
     * @param pageable pageable object for using pagination
     * @return {@link Page} object which contains data based by pageable input object
     */
    @Override
    @Transactional
    public Page<FlightsUser> getAllUsers(Pageable pageable) {
        return flightsUserRepository.findAll(pageable);
    }

    /**
     * Getting {@link FlightsUser} by username
     *
     * @param username name of user
     * @return {@link FlightsUser} object
     * @throws UsernameNotFoundException if user by specified username not found
     */
    @Override
    @Transactional
    public FlightsUser getByUsername(String username) {
        return flightsUserRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username '%s' not found", username))
                );
    }

    /**
     * Deleting {@link FlightsUser} by username
     *
     * @param username name of user
     * @throws UsernameNotFoundException if user by specified username not found
     */
    @Override
    @Transactional
    public void deleteUser(String username) {
        FlightsUser flightsUser = getByUsername(username);
        flightsUserRepository.delete(flightsUser);
    }

    /**
     * Creating new user by username, password, which roles contains in {@link FlightsUserDto}
     *
     * @param flightsUserDto username, password, roles Dto parameter
     * @throws IllegalArgumentException bad DTO arguments
     * @throws IllegalArgumentException during creating user with root username specified in application config
     * @throws EntityExistsException    user already exists
     */
    @Override
    @Transactional
    public void createUser(FlightsUserDto flightsUserDto) {
        String username = flightsUserDto.getUsername(), password = flightsUserDto.getPassword();
        String[] roles = flightsUserDto.getRoles();
        // Check objects in flightsUserDto. Null or empty
        if ((username == null || username.isEmpty()) ||
                (password == null || password.isEmpty()) ||
                (roles == null || roles.length == 0 ||
                        // Check roles array for null or empty objects
                        Arrays.stream(roles).anyMatch(s -> s == null || s.isEmpty())
                )
        ) {
            // If arguments in flightsUserDto are incorrect, throw IllegalArgumentException
            throw new IllegalArgumentException("Bad arguments");
        }
        // Check if specified username equals root username
        if (rootUserProvider.checkRootUsername(username)) {
            throw new IllegalArgumentException("Can't create root user specified in application configuration");
        }
        // Check if user already exists and throw EntityExistsException
        Optional<FlightsUser> existingUser = flightsUserRepository.findByUsernameIgnoreCase(username);
        if (existingUser.isPresent()) {
            throw new EntityExistsException("User already exist");
        }
        // Getting PasswordEncoder from Spring context for encode password
        PasswordEncoder passwordEncoder = ApplicationContextProvider.getBean(PasswordEncoder.class);
        FlightsUser flightsUser = FlightsUser.builder()
                .username(username)
                // Encoding password by PasswordEncoder configured in SpringSecurityConfig
                .password(passwordEncoder.encode(password))
                // Set default activity to true
                .active(true)
                .roles(roles)
                .build();
        // If all checks passed, saving new user in database
        flightsUserRepository.save(flightsUser);
    }

    /**
     * Set {@link FlightsUser} active or inactive
     *
     * @param activeFlightsUserDto contains username and user activity boolean value
     * @throws UsernameNotFoundException if user not found
     */
    @Override
    @Transactional
    public void setActive(ActiveFlightsUserDto activeFlightsUserDto) {
        FlightsUser flightsUser = getByUsername(activeFlightsUserDto.getUsername());
        flightsUser.setActive(activeFlightsUserDto.isActive());
        flightsUserRepository.save(flightsUser);
    }

}