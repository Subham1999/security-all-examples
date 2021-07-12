package com.subham.springsecurity.securityallexamples.security;

import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface JwtTokenUtil {

    public String extractUserName(String jwtToken);

    public Date extractExpiration(String jwtToken);

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver);

    public String generateToken(UserDetails userDetails);

    public Boolean validateToken(String jwtToken, UserDetails userDetails);
    
    public Boolean isValid(String jwtToken);

    public String role(String jwtToken);
}
