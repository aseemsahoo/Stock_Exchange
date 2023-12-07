package com.project.stock_exchange.util.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.stock_exchange.config.service.MyUserDetailsService;
//import com.project.stock_exchange.entity.dto.UserSignupDTO;
import com.project.stock_exchange.service.interfaces.UserSignupService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private UserSignupService userSignupService;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JWTService jwtUtil;
    public JWTFilter() {};

    public JWTFilter(UserSignupService userSignupService, MyUserDetailsService myUserDetailsService, JWTService jwtService) {
        this.userSignupService = userSignupService;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtUtil = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")){
            String jwt = authHeader.substring(7);
            if(jwt == null || jwt.isBlank()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
            }
            else {
                try {
                    String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    UserDetails user = myUserDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
//                catch(TokenExpiredException exc){
//                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The token has expired");
//                    throw exc;
//                }
                catch(JWTVerificationException exc){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
