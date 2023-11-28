package edu.amaro.encuestabackend.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends BasicAuthenticationFilter{

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
        String header = request.getHeader(SecuritConstants.HEADER_STRING);

        if(header == null || !header.startsWith(SecuritConstants.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader(SecuritConstants.HEADER_STRING);

        if(token != null){
            token = token.replace(SecuritConstants.TOKEN_PREFIX, "");
            String subject = Jwts.parser().verifyWith(SecuritConstants.getKey()).build().parseSignedClaims(token).getPayload().getSubject();
            if (subject != null){
                return new UsernamePasswordAuthenticationToken(subject, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
    
}
