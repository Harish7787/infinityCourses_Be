package com.payment_gateway.Auth.Config.JWT;

import com.payment_gateway.Auth.Entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkey123456";

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Generate Token
    public String generateToken(String email, String role , int id) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("userId", id)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)
                )
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();


    }

    //  Extract Username
    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }

    // Validate Token
    public boolean isTokenValid(String token, String email) {

        final String username = extractUsername(token);

        return username.equals(email)
                && !isTokenExpired(token);
    }

    // Check Expiration
    private boolean isTokenExpired(String token) {

        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // Extract Claims
    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractRole(String token) {

        return extractAllClaims(token)
                .get("role", String.class);
    }
    public Integer extractUserId(String token) {

        return extractAllClaims(token)
                .get("userId", Integer.class);
    }


}