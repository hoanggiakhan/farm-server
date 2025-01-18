package com.farm.farm_manager.service.jwt;

import com.farm.farm_manager.config.JwtConfig;
import com.farm.farm_manager.dao.EmployeeRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.*;
@Component
public class JwtService {
    private final JwtConfig config;
    private final Key key;
    EmployeeRepository userRepository;
    public JwtService(JwtConfig config , EmployeeRepository userRepository) {
        this.userRepository=userRepository;
        this.config = config;
        this.key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(config.getSecretKey().getBytes()));
    }

    public String generateToken( String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + config.getExpirationTime());
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
