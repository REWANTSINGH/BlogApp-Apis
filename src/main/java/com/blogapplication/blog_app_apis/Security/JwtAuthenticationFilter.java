package com.blogapplication.blog_app_apis.Security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

     @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Get token from header
        String requestToken = request.getHeader("Authorization");
        logger.info(" Header : {} ",requestToken);

        String username = null;
        String token = null;

        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            token = requestToken.substring(7);
            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.info("Unable to get JWT token");
                e.printStackTrace();

            } catch (ExpiredJwtException e) {
                logger.info("JWT token has expired");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("JWT token is malformed or invalid");
                e.printStackTrace();
            }
        } else {
            logger.info("JWT token does not begin with Bearer");
        }

        // 2. Validate token and set authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } 
            else {
                logger.info("Unable to validate token or invalid JWT token");
            } 
        } else {
            logger.info("Username is null or security context already has authentication");
        }

        // ✅ Important to allow the request to proceed
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean skip = path.startsWith("/api/v1/auth/")
            || path.equals("/v3/api-docs")
            || path.startsWith("/v3/api-docs/")
            || path.startsWith("/v2/api-docs")
            || path.startsWith("/swagger-ui")
            || path.startsWith("/swagger-resources")
            || path.startsWith("/webjars")
            || path.equals("/swagger-ui.html");
        logger.info("shouldNotFilter for path {}: {}", path, skip);
        return skip;
    }


}
