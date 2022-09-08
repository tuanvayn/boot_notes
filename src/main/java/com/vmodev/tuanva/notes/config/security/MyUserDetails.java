package com.vmodev.tuanva.notes.config.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vmodev.tuanva.notes.model.UserInfo;
import com.vmodev.tuanva.notes.repo.UserRepo;

@Service
public class MyUserDetails implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final UserInfo userInfo = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
		final Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("USER"));
		return org.springframework.security.core.userdetails.User.withUsername(username)
				.password(userInfo.getPassword()).accountExpired(false).accountLocked(false).credentialsExpired(false)
				.disabled(false).authorities(authorities).build();
	}

}