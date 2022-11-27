package com.ecom.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtUtil {
    private String secret;
    private final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;

    public JwtUtil(String key) {
        secret = key;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities()
                .stream().parallel()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());
        claims.put("roles", roles);
        return createToken(userDetails, claims);
    }

    private String createToken(UserDetails userDetails, Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(ALGORITHM, secret)
                .setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return userDetails.getUsername().equals(username) &&
                new Date().before(extractExpiration(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> function) {
        Claims claims = extractAllClaim(token);
        return function.apply(claims);
    }

    private Claims extractAllClaim(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody();
    }
}
