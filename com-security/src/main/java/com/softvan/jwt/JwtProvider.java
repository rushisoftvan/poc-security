//package com.softvan.jwt;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.softvan.dto.response.TokenResponse;
//import io.jsonwebtoken.*;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class JwtProvider {
//
//
//    private static final String USER_ID = "userId";
//    private static final String ROLE = "ROLE";
//
//    private static final String jwtSecretKey = "e3LHqZMEtP7pc8HTJeU7cnslny6OQhNgaHa3Jdbjd0jK4AJUBz3xoRLS6AtuWgv4g0pu2L7OIpZvMOtGhGIZLSQ0P0oiQcY1OrvM";
//
//    public TokenResponse creatToken(String username, Integer userId, String role) {
//
//        Claims claims = Jwts.claims().setSubject(username);
//        claims.put(USER_ID, userId);
//        claims.put(ROLE, role);
//
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + 900000);
//
//        String token = Jwts.builder()
//                .setIssuedAt(now)
//                .setClaims(claims)
//                .setExpiration(validity)
//                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
//                .compact();
//        return new TokenResponse(token);
//
//    }
//
//    public boolean validateToken(String token) {
//        Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token);
//        System.out.println(">>>>>>>>>>>>>>" + Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token));
//        return true;
//    }
//
//    public String getUsername(String token) {
//        System.out.println("token = " + token);
//        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody().getSubject();
//    }
//
//    public Integer getUserIdToken(String token) {
//        return (Integer) Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody().get(USER_ID);
//    }
//
//    public String getUserRole(String token) {
//        try {
//            System.out.println("Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody() = " + Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody());
//            return (String) Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody().get(ROLE);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return e.getMessage();
//        }
//    }
//
//
//    public Authentication getAuthentication(String token) throws JsonProcessingException {
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority(getUserRole(token)));
//        UserDetails userDetails = new User(getUsername(token), getUsername(token), true, false, false, false, grantedAuthorities);
//        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//    }
//
//
//}
