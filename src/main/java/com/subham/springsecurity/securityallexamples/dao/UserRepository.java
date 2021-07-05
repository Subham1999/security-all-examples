package com.subham.springsecurity.securityallexamples.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subham.springsecurity.securityallexamples.model.User;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUserName(String userName);

}
