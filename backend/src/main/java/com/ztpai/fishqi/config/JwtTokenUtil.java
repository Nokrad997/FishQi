package com.ztpai.fishqi.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenUtil {
    private SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public String generateToken(String email, boolean isAdmin) {
        String claims = isAdmin ? "admin" : "user";
        long expirationTime = 1000 * 60 * 5;

        return Jwts.builder().subject(email).claim("role", claims).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime)).signWith(secretKey).compact();
    }

    public String generateRefreshToken(String email) {
        long expirationTime = 1000 * 60 * 60;

        return Jwts.builder().subject(email).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime)).signWith(secretKey).compact();
    }

    public Boolean validateToken(String token, String username) {
       final String subject = this.getSubjectFromToken(token);

       return (username.equals(subject) && this.isTokenExpired(token));
    }

    public String getSubjectFromToken(String token) {
        return this.getClaimsFromToken(token).getSubject();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().decryptWith(this.secretKey).build().parseSignedClaims(token).getPayload();
    }

    public boolean isTokenExpired(String token) {
        return !this.getClaimsFromToken(token).getExpiration().before(new Date());
    }
}
