package com.isc.core.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityPrincipal implements Serializable {

    private KeyUtils keyUtils;

    public static final String AUTHORITIES_CLAIM = "authorities";

    public static final String REMEMBER_ME_CLAIM = "remember-me";

    public static final String USER_TYPE_CLAIM = "user-type";

    public static final String PERMISSIONS_MAP_CLAIM = "permissions-map";

    private String token;

    public String getUsername() {
        return parseJwt()
                .getBody()
                .getSubject();
    }

    public String getUserType() {
        return getJwtClaim(USER_TYPE_CLAIM, String.class);
    }

    public Boolean getRememberMe() {
        return getJwtClaim(REMEMBER_ME_CLAIM, Boolean.class);
    }

    public List<GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(
                StringUtils.arrayToCommaDelimitedString(
                        getJwtClaim(AUTHORITIES_CLAIM, List.class).toArray()
                )
        );
    }

    private <T> T getJwtClaim(String claim, Class<T> claimType) {
        return parseJwt().getBody().get(claim, claimType);
    }

    private Jws<Claims> parseJwt() {
        return Jwts.parser().setSigningKey(keyUtils.getPublicKey()).parseClaimsJws(token);
    }

}
