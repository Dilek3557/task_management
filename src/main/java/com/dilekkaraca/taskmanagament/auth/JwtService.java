package com.dilekkaraca.taskmanagament.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Key key;
    private final long expirationMs;

    // 1) Secret ve expiration değerlerini application.properties'ten okuyoruz
    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expirationMs
    ) {
        // 2) Secret string’ini imzalama anahtarına çeviriyoruz (HMAC key)
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    // 3) Token üretme: subject olarak email koyuyoruz (bizim kullanıcı kimliğimiz email)
    public String generateToken(String email) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(email)          // token’ın içine “bu kim?” bilgisini koyuyoruz
                .setIssuedAt(now)           // ne zaman üretildi
                .setExpiration(exp)         // ne zaman bitecek
                .signWith(key, SignatureAlgorithm.HS256) // imza: token değiştirilemesin
                .compact();
    }

    public String extractEMails(String token) {
        return extractAllClaims(token).getSubject();
    }
    public boolean isTokenValid(String token) {
        try{
            extractAllClaims(token);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody();


    }
}
