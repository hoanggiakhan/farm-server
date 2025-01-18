package com.farm.farm_manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.signerKey}")
    private String signerKey;
    @Value("${jwt.expiration}")
    private long expirationTime;

    public String getSecretKey(){
        return signerKey;
    }
    public long getExpirationTime(){
        return expirationTime;
    }
}
