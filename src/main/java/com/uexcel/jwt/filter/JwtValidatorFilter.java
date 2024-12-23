package com.uexcel.jwt.filter;

import com.uexcel.jwt.constant.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JwtValidatorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt;
        String jtToken = request.getHeader(AppConstants.HEADER);

        if(jtToken != null && jtToken.startsWith("Bearer ")) {
            jwt = jtToken.trim().substring(7);
        }else {
            jwt = jtToken;
        }

        Environment env = getEnvironment();

        if(jwt != null && env != null){
            try {

                String secret = env.getProperty(AppConstants.JWT_SECRET_KEY, AppConstants.JWT_DEFAULT_KEY);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                if (secretKey != null) {
                    Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();
                    String username = String.valueOf(claims.get("username"));
                    String authorities = String.valueOf(claims.get("authorities"));

                    Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,
                            AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }catch (Exception e){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\": \"Bad jwt token\"}");
                return;
            }

        }

        doFilter(request, response, filterChain);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/api/create") && request.getServletPath().equals("/login");
    }
}
