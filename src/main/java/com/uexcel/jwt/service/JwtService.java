package com.uexcel.jwt.service;

import com.uexcel.jwt.constant.AppConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;


public class JwtService {

   public static String generateToken (Authentication auth, Environment env) {

       if (env != null) {
           String secret = env.getProperty(AppConstants.JWT_SECRET_KEY, AppConstants.JWT_DEFAULT_KEY);
           SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
           String JWT = Jwts.builder().issuer(AppConstants.JWT_ISSUER).subject(AppConstants.SUBJECT)
                   .claim("username", auth.getName())
                   .claim("authorities", auth.getAuthorities().stream()
                           .map(GrantedAuthority::getAuthority)
                           .collect(Collectors.joining(","))
                   )
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + 30000000))
                   .signWith(secretKey).compact();
           return JWT;
       }
       return null;
   }

}
