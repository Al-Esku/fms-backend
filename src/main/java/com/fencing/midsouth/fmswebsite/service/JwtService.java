package com.fencing.midsouth.fmswebsite.service;

import com.fencing.midsouth.fmswebsite.model.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class JwtService {

    private final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build(); //TODO: Look into securing this

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private BlacklistService blacklistService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        //1 day
        long EXPIRATION_TIME = 86_400_000;
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        String username = extractUsername(token);
        Optional<User> user = userService.getUserByUsername(username);
        Date date = extractIssuedAt(token);
        logger.info("Validating token for {}", username);

        return (userDetailsService.userExistsByUsername(username) && !isTokenExpired(token) && !blacklistService.isBlacklisted(user.orElseThrow(), token, date));
    }

    public Authentication getAuthentication(String token) {
        String username = extractUsername(token);

        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = extractExpiration(token);
        return expirationDate.before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    private Date extractIssuedAt(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getIssuedAt();
    }

    public String extractUsername(String token) {
        String username;
        try {
            username = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (SignatureException | ExpiredJwtException e) {
            username = null;
        }
        return username;
    }
}
