package com.company.librarymanagement.auth.security.jwt;

import com.company.librarymanagement.auth.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("jwt.config.secret")
    private String jwtSecret;

    @Value("${jwt.config.expiration}")
    private long jwtExpirationMs;

    public String generateToken(String username, UserRole role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean isValidToken(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject() != null &&
                    claims.get("role") != null &&
                    claims.getExpiration().after(new Date(System.currentTimeMillis()));
        }
        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        }
        catch (Exception e) {
            return null;
        }
    }

    public String getLoginFromToken(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return Strings.EMPTY;
    }
}
