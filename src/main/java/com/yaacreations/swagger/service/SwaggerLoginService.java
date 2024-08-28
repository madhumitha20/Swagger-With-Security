package com.yaacreations.swagger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yaacreations.swagger.repo.DeveloperRepo;

@Service
public class SwaggerLoginService implements UserDetailsService{
	
	@Autowired
	DeveloperRepo developerRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		return developerRepo.findByEmail(email);
	
	}
	
}
