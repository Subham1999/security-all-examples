package com.subham.springsecurity.securityallexamples.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.subham.springsecurity.securityallexamples.dao.UserRepository;
import com.subham.springsecurity.securityallexamples.security.filters.JwtTokenFilter;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @SuppressWarnings("unused")
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	/*
	 * Multiple Configuration of authentication-manager-builder can be done case 1:
	 * In memory case 2: Jdbc
	 * 
	 */

	auth.inMemoryAuthentication().withUser("user").password("123").roles("USER").and().withUser("admin")
		.password("123").roles("ADMIN");

	auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http
	.cors().disable()
	.csrf().disable()
	.authorizeRequests()
		.antMatchers("/authenticate").permitAll()
		.antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/pub/**").permitAll()
		.anyRequest().authenticated()
	.and()
	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	.and()
	.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
	.exceptionHandling();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//	return new BCryptPasswordEncoder();
	return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
	return super.authenticationManagerBean();
    }

}
