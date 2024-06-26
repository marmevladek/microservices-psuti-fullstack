package ru.psuti.userservice.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);



    @Value("${security.jwt-secret}")
    private String jwtSecret;

    @Value("${security.jwt-expire-ms}")
    private int jwtExpirationInMs;



    public String generateToken(Authentication authentication) {
        LdapUserDetailsImpl userDetails = (LdapUserDetailsImpl) authentication.getPrincipal();

        StringBuilder roles = new StringBuilder();
        userDetails.getAuthorities().forEach(role -> roles.append(role.getAuthority()).append(" "));
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuer(roles.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationInMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromJwtToken(String token) {
        if (token.startsWith("Bearer")) token = token.substring(7);

        return Jwts
                .parserBuilder()
                .setSigningKey(key()).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getUidFromToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key()).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String[] getRolesFromJwtToken(String token) {
        if (token.startsWith("Bearer")) token = token.substring(7);

        String username = Jwts
                .parserBuilder()
                .setSigningKey(key()).build()
                .parseClaimsJws(token)
                .getBody()
                .getIssuer();

        return username.split(" ");
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
