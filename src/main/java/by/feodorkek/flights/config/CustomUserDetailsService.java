package by.feodorkek.flights.config;

import by.feodorkek.flights.model.FlightsUser;
import by.feodorkek.flights.service.impl.FlightsUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * This {@link Component @Component} is used to load a user from the user database
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * {@link FlightsUserServiceImpl} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     */
    private final FlightsUserServiceImpl flightsUserService;

    /**
     * {@link RootUserProvider} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     * <p>
     * Contains credentials of the root user specified in root-user application configuration
     */
    private final RootUserProvider rootUserProvider;

    /**
     * Loading {@link UserDetails} by username from users database through
     * implemented {@link FlightsUserServiceImpl} instance. Or loading root
     * user from root-user application configuration
     *
     * @param username the username identifying the user whose data is required.
     * @return {@link UserDetails} object of user from users database
     * @throws UsernameNotFoundException if a user with the given username is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FlightsUser flightsUser = rootUserProvider.checkRootUsername(username) ?
                // Loading root user credentials from root-user application configuration
                // if specified username is equals root username
                FlightsUser.builder()
                        .username(rootUserProvider.getRootUsername())
                        .password(rootUserProvider.getEncodedPassword())
                        .roles(FlightsUser.Roles.allRoles())
                        // Setts root user activity from root-user application configuration
                        .active(rootUserProvider.isAllowRoot())
                        .build()
                : flightsUserService.getByUsername(username);
        return User.builder()
                .username(flightsUser.getUsername())
                .password(flightsUser.getPassword())
                // Roles of user (e.g. USER, ADMIN, EDITOR)
                .roles(flightsUser.getRoles())
                // Setts boolean value if account is active or blocked
                .accountLocked(!flightsUser.isActive())
                .build();
    }

}