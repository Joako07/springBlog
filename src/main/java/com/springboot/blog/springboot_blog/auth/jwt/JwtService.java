package com.springboot.blog.springboot_blog.auth.jwt;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    //Creo la clave secreta
    private static final String SECRET_KEY = "893G4568E6213K26C231M156J8472B4B625065535625J5632F654332A564658";

    //Genero el token
    public String getToken(UserDetails user){
        return Jwts.builder()
                 .claims(Map.of("role", user.getAuthorities().stream() //Obtengo la lista de roles del usuario
                 .findFirst() //Toma el primer role
                 .map(grantedAuthority -> "ROLE_" + grantedAuthority.getAuthority()) //le agrega el prefijo "ROLE_"
                 .orElseThrow(() -> new IllegalStateException("User does not have any roles assigned"))))
                 .subject(user.getUsername())
                 .issuedAt(new Date(System.currentTimeMillis())) //Fecha y hora en que se creo
                 .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 2)) //Cuando expira (es en 2 días)
                 .signWith(getKey())//firmo el token
                 .compact();

    }

    //decodifico la SECRET_KEY
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

   
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

}
