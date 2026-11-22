package com.example.ChatApplicationPowderedByAI.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

        @Value("${jwt.secret}")
        private String secretKey;
        @Value("${jwt.expiration}")
        private long expiration;

        private SecretKey key;

        @PostConstruct
        public void init(){
            this.key= Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        }

        public String generateToken(String username){
            Map<String,Object> claims=new HashMap<>();
            Date now=new Date();
            Date expiry=new Date(now.getTime()+expiration);

            return Jwts.builder().claims(claims).signWith(key, SignatureAlgorithm.HS256)
                    .subject(username).issuedAt(now).expiration(expiry).compact();
        }

        public Claims extractAllDetails(String token){
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        }

    public String extractUsername(String token){
        return extractAllDetails(token).getSubject();
    }


    public Date getExpiration(String token){
        return extractAllDetails(token).getExpiration();
    }

    public boolean isExpired(String token){
        return getExpiration(token).before(new Date());
    }

    public boolean isValid(String token, UserDetails userDetails){
        final String username=extractUsername(token);
        if (username.equals(userDetails.getUsername())){
            return true;
        }
        else
            return false;
    }

}
