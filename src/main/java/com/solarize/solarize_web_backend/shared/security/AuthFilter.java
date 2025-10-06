package com.solarize.solarize_web_backend.shared.security;

import com.solarize.solarize_web_backend.modules.auth.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;


public class AuthFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

    private final AuthService authService;

    private final JwtTokenManager jwtTokenManager;

    public AuthFilter(AuthService authService, JwtTokenManager jwtTokenManager) {
        this.authService = authService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException{

        String username = null;
        String jwtToken = null;
        List<SimpleGrantedAuthority> authorities = null;

        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("Authorization")){
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }


        if (Objects.nonNull(jwtToken)) {
            try{
                username = jwtTokenManager.getUserIdFromToken(jwtToken);
                authorities = jwtTokenManager.getAuthorities(jwtToken);

            } catch (ExpiredJwtException exception){
                LOGGER.info("[FALHA AUTENTICAÇÃO] - Expired token: {} - {}", exception.getClaims().getSubject(), exception.getMessage());

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (MalformedJwtException exception) {
                LOGGER.info("[FALHA AUTENTICAÇÃO] - Malformed JWT JSON: {}", exception.getMessage());

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            addUsernameInContext(request, username, jwtToken, authorities);
        }

        filterChain.doFilter(request, response);

    }

    private void  addUsernameInContext(HttpServletRequest request, String username, String jwtToken, List<SimpleGrantedAuthority> authorities){

        UserDetails userDetails = authService.loadUserByUsername(username);

        if (jwtTokenManager.validateToken(jwtToken, userDetails)){

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

    }

}
