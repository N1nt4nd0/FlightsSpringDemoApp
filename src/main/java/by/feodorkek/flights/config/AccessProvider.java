package by.feodorkek.flights.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AccessProvider {

    private boolean allowAccess = true;

    public void openAccess() {
        allowAccess = true;
    }

    public void closeAccess() {
        allowAccess = false;
    }

    public boolean checkAccess() {
        if (!allowAccess) {
            return isAdmin();
        }
        return true;
    }

    public boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(authority -> {
            return authority.getAuthority().equals("ROLE_ADMIN");
        });
    }

}