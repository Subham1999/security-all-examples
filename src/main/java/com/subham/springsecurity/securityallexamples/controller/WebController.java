package com.subham.springsecurity.securityallexamples.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.subham.springsecurity.securityallexamples.dao.UserRepository;
import com.subham.springsecurity.securityallexamples.model.User;
import com.subham.springsecurity.securityallexamples.security.JwtTokenUtilImpl;
import com.subham.springsecurity.securityallexamples.security.MyUserDetailsService;
import com.subham.springsecurity.securityallexamples.security.models.AuthRequest;
import com.subham.springsecurity.securityallexamples.security.models.AuthResponse;

@RestController
public class WebController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository repository;

    @Autowired
    JwtTokenUtilImpl jwtTokenUtil;
    @Autowired
    MyUserDetailsService userDetailsService;

    @GetMapping("/pub/test")
    public String pubTest() {
	return "public api";
    }

    @PostMapping("/pub/storeUser")
    public User pubTest(@RequestBody User user) {
	return repository.save(user);
    }

    @GetMapping("/pub/storeUser")
    public String pubTest_2() {
	return "<h3>WHAT A LUCK!</h3>";
    }

//    @PostMapping("/pub/storeUser")
//    public String pubTest_3() {
//	return "<h3>WHAT THE *UCK!</h3>";
//    }

    @GetMapping("/user/test")
    public String userTest() {
	return "user api";
    }

    @GetMapping("/admin/test")
    public String adminTest() {
	return "admin api";
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest)
	    throws Exception {

	try {
	    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
		    authenticationRequest.getUserName(), authenticationRequest.getPassword()));
	} catch (BadCredentialsException e) {
	    throw new Exception("Incorrect username or password", e);
	}

	final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());

	final String jwt = jwtTokenUtil.generateToken(userDetails);

	return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
