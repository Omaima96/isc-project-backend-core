package com.isc.core.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@PropertySource("classpath:/application.yml")
public class KeyUtils {

    @Value("${jwt.key.public}")
    private String publicKeyString;

    public PublicKey getPublicKey() {
        try {
            byte[] publicKeyByteServer = Base64.getDecoder().decode(publicKeyString);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyByteServer));
            return publicKey;
        } catch (Exception ex) {
            return null;
        }
    }

}
