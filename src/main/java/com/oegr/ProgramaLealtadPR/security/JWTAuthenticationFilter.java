package com.oegr.ProgramaLealtadPR.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        AuthCredentials authCredentials = new AuthCredentials();

        try{
            authCredentials = new ObjectMapper().readValue(request.getReader(),AuthCredentials.class);
        } catch (IOException e) {
        }

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getEmail(),
                authCredentials.getPassword(),
                Collections.emptyList()
        );

        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String token;
        if (Objects.equals(userDetails.getRol(), "BUSINESS")){
            token = TokenUtils.createToken(
                    userDetails.getName(),
                    userDetails.getUsername(),
                    userDetails.getRol(),
                    userDetails.getId(),
                    userDetails.getIdBusiness(),
                    userDetails.getNameBusiness());
        } else {
            token = TokenUtils.createToken(
                    userDetails.getName(),
                    userDetails.getUsername(),
                    userDetails.getRol(),
                    userDetails.getId(),
                    null,
                    null);
        }

        response.addHeader("Authorization", "Bearer " + token);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"Token\": \"%s\" }",token));
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}

