package com.vmodev.tuanva.notes.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vmodev.tuanva.notes.config.security.JwtTokenProvider;
import com.vmodev.tuanva.notes.exception.CustomException;

@Service
public class UserService {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String signin(String username, String password) {
		try {
			AbstractAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);
			authenticationManager.authenticate(auth);
			return jwtTokenProvider.createToken(username);
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied");
		}
	}
}
