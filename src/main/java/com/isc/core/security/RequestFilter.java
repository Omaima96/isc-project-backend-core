package com.isc.core.security;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;



@Component
public class RequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtUtils;

    @Autowired
    private KeyUtils keyUtils;

    public static final String BEARER_TOKEN_PREFIX = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String headerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (Objects.nonNull(headerToken)) {
                String token = headerToken.replace(BEARER_TOKEN_PREFIX, StringUtils.EMPTY).trim();
                if (jwtUtils.isTokenValid(token)) {
                    AbstractAuthenticationToken authentication;
                    SecurityPrincipal principal = new SecurityPrincipal(keyUtils, token);
                    authentication = new UsernamePasswordAuthenticationToken(
                            principal,
                            null,
                            principal.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }

}
