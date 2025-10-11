package com.solarize.solarizeWebBackend.shared.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenManager {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.validity}")
    private Long jwtTokenValidity;

    public String getUserIdFromToken(String token){
        return getClaimForToken(token, Claims::getSubject);
    }

    public List<SimpleGrantedAuthority> getAuthorities(String token){
        List<?> authoritiesList = getClaimForToken(token, claims -> claims.get("authorities", List.class));
        List<String> secureAuthoritiesString = new ArrayList<>();

        for(Object authority : authoritiesList){
            secureAuthoritiesString.add((String) authority);
        }

        return secureAuthoritiesString.stream().map(SimpleGrantedAuthority::new).toList();
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimForToken(token, Claims::getExpiration);
    }

    public String generateToken(final Authentication authentication, Long userId){

        final List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authorities)
                .signWith(parseSecret())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1_000))
                .compact();

    }

    public <T> T getClaimForToken(String token, Function<Claims, T> claimsResolver){

        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String token, UserDetails userDetails){
        String username = getUserIdFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){

        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date(System.currentTimeMillis()));

    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(parseSecret())
                .build()
                .parseClaimsJws(token).getBody();

    }

    private SecretKey parseSecret(){
        return Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
    }

}
