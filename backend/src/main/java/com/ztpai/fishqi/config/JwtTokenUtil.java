package com.ztpai.fishqi.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;

@Component
public class JwtTokenUtil {
    private SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public String generateToken(String username) {
        long expirationTime = 1000 * 60 * 60;
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(this.secretKey, SIG.HS256)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
       Jws<Claims> claims;
       try{
        claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        String subject = claims.getsub
        return username.equals(claims.getPayload().getExpiration().before(new Date()));
       }
    }
}
