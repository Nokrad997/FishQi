package com.ztpai.fishqi.config;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.ztpai.fishqi.exceptions.ExpiredRefreshTokenException;

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

    public String generateRefreshToken(String email, boolean isAdmin) {
        long expirationTime = 1000 * 60 * 60;
        String claims = isAdmin ? "admin" : "user";

        return Jwts.builder().subject(email).claim("role", claims).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime)).signWith(secretKey).compact();
    }

    public String refreshAccessToken(String refreshToken) throws ExpiredRefreshTokenException {
        String subject = this.getSubjectFromToken(refreshToken);
        Claims claim = this.getClaimsFromToken(refreshToken);

        if (this.validateToken(refreshToken, subject)) {
            Object roleClaim = claim.get("role");
            boolean isAdmin = "admin".equals(roleClaim);

            return this.generateToken(subject, isAdmin);
        } else {
            throw new ExpiredRefreshTokenException("Token is not valid");
        }
    }

    public Boolean validateToken(String token, String username) {
        final String subject = this.getSubjectFromToken(token);

        return (username.equals(subject) && !this.isTokenExpired(token));
    }

    public String getSubjectFromToken(String token) {
        return this.getClaimsFromToken(token).getSubject();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(this.secretKey).build().parseSignedClaims(token).getPayload();
    }

    public boolean isTokenExpired(String token) {
        return this.getClaimsFromToken(token).getExpiration().before(new Date());
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = this.getClaimsFromToken(token);

        String role = claims.get("role", String.class);
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());
        return Collections.singletonList(authority);
    }
}
