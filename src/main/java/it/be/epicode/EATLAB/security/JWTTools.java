package it.be.epicode.EATLAB.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.be.epicode.EATLAB.entities.Owner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import it.be.epicode.EATLAB.entities.User;
import it.be.epicode.EATLAB.exceptions.UnauthorizedException;

import java.util.Date;

@Service
public class JWTTools {

    @Value("${jwt.secret}")
    private String secret;

    public String createToken(User user) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .subject(String.valueOf(user.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Problemi col token! Effettua di nuovo il login!");
        }
    }

    public String extractIdFromToken(String token) {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseSignedClaims(token).getPayload()
                .getSubject();
    }


}