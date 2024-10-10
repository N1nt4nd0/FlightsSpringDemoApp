package by.feodorkek.flights.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * A {@link Component @Component} that extends verification of user access to service resources.
 * For example, you can block access for technical work and then
 * access will only be for login requests and for users with the 'ADMIN' role
 */
@Component
public class AccessProvider {

    /**
     * Variable that sets access permission. By default, access is open (true)
     */
    private boolean allowAccess = true;

    /**
     * Makes access opened
     */
    public void openAccess() {
        allowAccess = true;
    }

    /**
     * Makes access closed
     */
    public void closeAccess() {
        allowAccess = false;
    }

    /**
     * Returns current access state.
     * Also checks if the user has the 'ADMIN' role and if so returns true
     *
     * @return Access state boolean value
     */
    public boolean checkAccess() {
        if (!allowAccess) {
            return isAdmin();
        }
        return true;
    }

    /**
     * Checks if the user has the 'ADMIN' role.
     * Uses {@link SecurityContextHolder} context for this
     *
     * @return Boolean value of user 'ADMIN' role state
     */
    public boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

}