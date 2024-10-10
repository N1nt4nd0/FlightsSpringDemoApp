package by.feodorkek.flights.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * A {@link Component @Component} that is designed to set the credentials
 * of the root user specified in root-user application configuration
 */
@Component
@Getter
@RequiredArgsConstructor
public class RootUserProvider {

    /**
     * Username of init user (the first user will be created if users database is empty)
     */
    @Value("${custom-config.root-user.username}")
    private String rootUsername;

    /**
     * Password of init user (the first user will be created if users database is empty)
     */
    @Value("${custom-config.root-user.password}")
    private String rootPassword;

    /**
     * Setts the root account activity
     */
    @Value("${custom-config.root-user.allow-root}")
    private boolean allowRoot;

    /**
     * Check if specified username is equals root username
     *
     * @param username input username
     * @return boolean value - username is not null and equals root username
     */
    public boolean checkRootUsername(String username) {
        return username != null && username.equalsIgnoreCase(rootUsername);
    }

    /**
     * Getting encoded password with {@link PasswordEncoder} specified in Spring context
     *
     * @return encoded password string
     */
    public String getEncodedPassword() {
        PasswordEncoder passwordEncoder = ApplicationContextProvider.getBean(PasswordEncoder.class);
        return passwordEncoder.encode(rootPassword);
    }

}