package com.subham.springsecurity.securityallexamples.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
	private static final long serialVersionUID = 2516068140438078368L;
	private String userName;
	private String roles;
	private String password;

	public MyUserDetails(String userName, String password, String roles) {
		super();
		this.userName = userName;
		this.password = password;
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : roles.split(",")) {
			System.err.println(role);
			SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
			authorities.add(grantedAuthority);
			System.err.println(grantedAuthority);
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		System.out.println("password : " + password);
		return password;
	}

	@Override
	public String getUsername() {
		System.out.println("username : " + userName);
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
