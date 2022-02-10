package com.kkubit.eng.scrum.manager.authentication.security.jwt;

import com.kkubit.eng.scrum.manager.authentication.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwtSecret}")
    private String jwtSecret;
    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.out.println("bad signature");
            e.printStackTrace();

        } catch (MalformedJwtException e) {
            System.out.println("bad token");
            e.printStackTrace();

        } catch (ExpiredJwtException e) {
            System.out.println("expired token");
            e.printStackTrace();

        } catch (UnsupportedJwtException e) {
            System.out.println("unsupported token");
            e.printStackTrace();

        } catch (IllegalArgumentException e) {
            System.out.println("invalid token argument");
            e.printStackTrace();

        }

        return false;
    }
}
