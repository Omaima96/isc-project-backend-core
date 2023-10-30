package com.isc.core.security;



import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;


@Component
public class JwtTokenUtil implements Serializable {

    @Autowired
    private KeyUtils keyUtils;

    public Boolean isTokenValid(String token) {
        try {
            final Date expiration = Jwts.parser()
                    .setSigningKey(keyUtils.getPublicKey())
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();

            return expiration.after(new Date());
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

}