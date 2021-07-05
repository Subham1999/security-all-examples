package com.subham.springsecurity.securityallexamples.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.subham.springsecurity.securityallexamples.security.JwtTokenUtilImpl;
import com.subham.springsecurity.securityallexamples.security.MyUserDetailsService;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtilImpl jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {

	final String authHeader = request.getHeader("Authorization");
	String userName = null;
	String jwtToken = null;

	if (authHeader != null && authHeader.startsWith("Bearer ")) {
	    jwtToken = authHeader.substring(7);
	    userName = jwtTokenUtil.extractUserName(jwtToken);
	}

	if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	    UserDetails loadUserByUsername = userDetailsService.loadUserByUsername(userName);

	    if (jwtTokenUtil.validateToken(jwtToken, loadUserByUsername)) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
			jwtToken, null, loadUserByUsername.getAuthorities());

		usernamePasswordAuthenticationToken
			.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	    }
	}

	filterChain.doFilter(request, response);
    }

}
