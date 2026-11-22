package com.example.ChatApplicationPowderedByAI.Filter;

import com.example.ChatApplicationPowderedByAI.Repository.BlockedTokenRepository;
import com.example.ChatApplicationPowderedByAI.Service.JWTService;
import com.example.ChatApplicationPowderedByAI.Service.MyUserDetailsService;
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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private BlockedTokenRepository blockedTokenRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=null;
        String username=null;
        String authHeader=request.getHeader("Authorization");
        if (authHeader!=null && authHeader.startsWith("Bearer "))
        {
             token=authHeader.substring(7);

             if (blockedTokenRepository.existsByToken(token)){
                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                 response.getWriter().write("Token has been invalidated. Please sign in again.");
                 return;
             }

             username=jwtService.extractUsername(token);
        }

        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=myUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request,response);
    }
}
