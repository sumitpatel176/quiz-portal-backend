package com.quiz.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.quiz.config.UserDetailServiceImpl;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailServiceImpl userDetailService;
    
    @Autowired
    private TokenBlacklistService tokenBlacklistService;
    
    
   // Auth endpoints (/login, /register, /refresh, /logout) par ye Filter bilkul NAHI chalega
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/api/v1/auth/login") || path.equals("/api/v1/auth/register") || path.equals("/api/v1/auth/refresh");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        
        String token = getJwtFromRequest(request);

        
        if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {
        	
        	if (tokenBlacklistService.isBlacklisted(token)) {
                     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                     response.getWriter().write("Token has been revoked/logged out!");
                     return;
                     
        	}

           
            String email = jwtProvider.getEmailFromJwt(token);

            
            UserDetails userDetails = userDetailService.loadUserByUsername(email);

            
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

           
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        
        filterChain.doFilter(request, response);
    }

    
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " (7 characters) skip kar rahe hain
        }
        return null;
    }
}