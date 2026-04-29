package ra.edu.config.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;


@Component
@Slf4j
public class JwtService {
    @Value("${jwt.secret-key}")
    private String jwtSecret;

    @Value("${jwt.expired}")
    private long expiredTime;

    public String generateAccessToken(String username, String role){
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiredTime))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(){
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        System.out.println("key : "+ Arrays.toString(key.getEncoded()));
        return key;
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e){
            log.error("Invalid token ", e.getMessage());
        } catch (UnsupportedJwtException e){
            log.error("Unsupported token ", e.getMessage());
        } catch (ExpiredJwtException e){
            log.error("Expired token ", e.getMessage());
        } catch (IllegalArgumentException e){
            log.error("Jwt key string invalid ", e.getMessage());
        }
        return false;
    }

    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
