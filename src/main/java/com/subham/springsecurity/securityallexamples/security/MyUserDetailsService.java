package com.subham.springsecurity.securityallexamples.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.subham.springsecurity.securityallexamples.dao.UserRepository;
import com.subham.springsecurity.securityallexamples.model.MyUserDetails;
import com.subham.springsecurity.securityallexamples.model.User;

@Service
public class MyUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User user = repository.findByUserName(username);
	System.out.println(user);
	if (user == null) {
	    throw new UsernameNotFoundException(username);
	}
	return new MyUserDetails(user.getUserName(), user.getPassword(), user.getRole());
    }

}
