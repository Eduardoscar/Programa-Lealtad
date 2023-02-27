package com.oegr.ProgramaLealtadPR.security;

import com.oegr.ProgramaLealtadPR.entity.Business;
import com.oegr.ProgramaLealtadPR.service.BusinessService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public class TokenUtils {

    private final static String ACCESS_TOKEN_SECRETS = "supersecrethsbgobgw0ogbweñgobiweogbpiwñgowegbweogbwe";
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2592000L;

    public static String createToken(String name, String email, String rol, Long id,
                                     Long id_business, String name_business){
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
        Date expirationDate = new Date(System.currentTimeMillis()+expirationTime);
        Map<String,Object> extra = new HashMap<>();
        extra.put("name", name);
        extra.put("rol", "ROLE_" + rol);
        if (id_business != null){
            extra.put("business_id",id_business);
            extra.put("business_name",name_business);
        }
        extra.put("id",id);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRETS.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication (String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRETS.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            String rol = (String) claims.get("rol");
            final Collection<SimpleGrantedAuthority> authorities =
                    Arrays.stream(claims.get("rol").toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(email, null, authorities);
        } catch (JwtException e){
            return null;
        }
    }
}
