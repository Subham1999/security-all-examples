package com.subham.springsecurity.securityallexamples.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenUtilImpl implements JwtTokenUtil {

    @Value("${secretkey}")
    private String SECRET_KEY;
    private static final long LIFE_TIME = 2 * (60 * 1000l); 
    
    public JwtTokenUtilImpl() {
	System.err.println("JwtTokenUtilImpl instantiated with secret = " + SECRET_KEY);
    }
    @Override
    public String extractUserName(String jwtToken) {
	return extractClaim(jwtToken, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String jwtToken) {
	return extractClaim(jwtToken, Claims::getExpiration);
    }

    @Override
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
	final Claims claims = extractAllClaims(jwtToken);
	return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
	Map<String, Object> claims = new HashMap<String, Object>();
	return createToken(claims, userDetails);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
	final Date issueDate = new Date();
	final Date expirationDate = new Date(issueDate.getTime() + LIFE_TIME);
	
	System.err.println("signing with key : " + SECRET_KEY);
	return Jwts.builder()
		.setClaims(claims)
		.setSubject(userDetails.getUsername())
		.setIssuedAt(issueDate)
		.setExpiration(expirationDate)
		.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    @Override
    public Boolean validateToken(String jwtToken, UserDetails userDetails) {
	String userName = extractUserName(jwtToken);
	return (userName.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken) {
	Date expirationDateAndTime = extractExpiration(jwtToken);
	Date currentDateAndTime = new Date();
	
	// return TRUE if expiration < currentDate
	return expirationDateAndTime.before(currentDateAndTime);
    }

    private Claims extractAllClaims(String jwtToken) {
	return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken).getBody();
    }
}
