package com.yaacreations.swagger.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yaacreations.swagger.dto.DeveloperDto;
import com.yaacreations.swagger.entity.Developer;
import com.yaacreations.swagger.repo.DeveloperRepo;

@Service
public class SwaggerService {
	
	@Autowired
	DeveloperRepo developerRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	SwaggerUtil swaggerUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	
	 public String refreshToken;
	 public String accessToken;

		   public DeveloperDto login(DeveloperDto developer){

		    	 DeveloperDto response = new DeveloperDto();
		    	
		        String email = developer.getEmail();
		        String password = developer.getPassword();
		        
		        Developer userEmail =  developerRepo.findByEmail(email);
		        String getPassword = userEmail.getPassword();
		        
		        if(!passwordEncoder.matches(password, getPassword))
				{
					response.setMessage("Password is Wrong");
					return response;
				}
		       
		        try {
		        	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
		        	
		        	var user = developerRepo.findByEmail(email);
		            if(user != null)
		            {
		            accessToken = swaggerUtil.generateAccessToken(user);
		            refreshToken = swaggerUtil.generateRefreshToken(new HashMap<>(), user);
		            
		            user.setDId(user.getDId());
		            
		           	response.setAccessToken(accessToken);
		            response.setRefreshToken(refreshToken);   
		           	
		            }
		           
		            }catch (Exception e){
		        	response.setStatusCode("-1");
	           		response.setAlertMessage("Failure");
		        	response.setError("Invalid Credentials");
		            }
		        return response;
		    }

}
