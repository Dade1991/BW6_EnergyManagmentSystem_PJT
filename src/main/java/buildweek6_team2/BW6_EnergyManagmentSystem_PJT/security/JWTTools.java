package buildweek6_team2.BW6_EnergyManagmentSystem_PJT.security;

import buildweek6_team2.BW6_EnergyManagmentSystem_PJT.entities.Utente;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {

    @Value("${jwt.secret}")
    private String keySecret;

    // 1. Creazione del token
    // Oggetto utente che prenderò una volta fatta l'entità
    public String createToken(Utente utente) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .subject(String.valueOf(utente.getUtenteId()))
                .signWith(Keys.hmacShaKeyFor(keySecret.getBytes()))
                .compact();
    }

    // 2. Validità del token
    public void verifyToken(String accessToken) {
        // verrà usato UnauthorizedException
        Jwts.parser().verifyWith(Keys.hmacShaKeyFor(keySecret.getBytes())).build().parse(accessToken);
    }

    // 3. Estrazione dell'id dal token
    public Long exctractIdFromToken(String accessToken) {
        return Long.parseLong(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(keySecret.getBytes())).build().parseSignedClaims(accessToken).getPayload().getSubject());
    }
}