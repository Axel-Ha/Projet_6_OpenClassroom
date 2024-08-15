package com.openclassrooms.mddapi.services;

import java.time.Instant;
import java.util.Date;

import com.openclassrooms.mddapi.exceptions.JWTErrorException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // Generate JWT token for a user
    public String generateToken(UserDetails userDetails) {
        try {
            String username = userDetails.getUsername();
            Date currentDate = new Date(System.currentTimeMillis());
            Date expireDate = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000); // 1 day in milliseconds

            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(currentDate)
                    .setExpiration(expireDate)
                    .signWith(getSignInKey())
                    .compact();
        } catch (Exception e) {
            throw new JWTErrorException("Error generating token", e);
        }
    }

    // Validate JWT token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        } catch (Exception e) {
            throw new JWTErrorException("Error validating token", e);
        }
    }
    // Check if the token is expired
    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            throw new JWTErrorException("Error checking if token is expired", e);
        }
    }

    // Extract username from JWT token
    public String extractUsername(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getSubject();
        } catch (Exception e) {
            throw new JWTErrorException("Error extracting username from token", e);
        }
    }

    // Extract expiration date from the token
    private Date extractExpiration(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration();
        } catch (Exception e) {
            throw new JWTErrorException("Error extracting expiration from token", e);
        }
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new JWTErrorException("Error extracting claims from token", e);
        }
    }

    // Decode the secret key
    private SecretKey getSignInKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            throw new JWTErrorException("Error decoding the secret key", e);
        }
    }
}