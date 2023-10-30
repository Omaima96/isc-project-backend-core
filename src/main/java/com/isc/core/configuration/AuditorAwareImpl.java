package com.isc.core.configuration;


import com.isc.core.security.SecurityPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String username;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            username = "ANONYMOUS";
        } else if (authentication.getPrincipal() instanceof SecurityPrincipal) {
            username = ((SecurityPrincipal) authentication.getPrincipal()).getUsername();
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        } else {
            username = "ANONYMOUS";
        }
        return Optional.of(username);
    }

}

