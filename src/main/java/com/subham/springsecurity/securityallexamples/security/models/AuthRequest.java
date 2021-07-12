package com.subham.springsecurity.securityallexamples.security.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private String role;
}
