package com.subham.springsecurity.securityallexamples.security.models;

import java.io.Serializable;

public class AuthResponse implements Serializable {
	private static final long serialVersionUID = -3262370992842849937L;
	private String token;

	public AuthResponse(String token) {
		this.token = token;
	}

	public AuthResponse() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "AuthResponse [token=" + token + "]";
	}

}
