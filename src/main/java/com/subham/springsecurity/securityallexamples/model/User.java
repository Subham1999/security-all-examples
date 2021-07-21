package com.subham.springsecurity.securityallexamples.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@Column(name = "user_name", unique = true, updatable = false)
	private String userName;
	@Column(name = "password")
	private String password;
	@Column(name = "role")
	private String role;
}
