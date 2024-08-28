package com.yaacreations.swagger.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeveloperDto {
	
	@JsonInclude(value = Include.NON_DEFAULT)
	private String statusCode;
	@JsonInclude(value = Include.NON_DEFAULT)
	private String alertMessage;
	@JsonInclude(value = Include.NON_DEFAULT)
	private String message;
	@JsonInclude(value = Include.NON_DEFAULT)
	private String expirationTime;
	@JsonInclude(value = Include.NON_DEFAULT)
	private String error;
	
	@JsonInclude(value = Include.NON_DEFAULT)
	private int dId;
	@JsonInclude(value = Include.NON_DEFAULT)
	private String name;
	@JsonInclude(value = Include.NON_DEFAULT)
	private String email;
	@JsonInclude(value = Include.NON_DEFAULT)
	private String password;
	@JsonInclude(value = Include.NON_DEFAULT)
	private String userType;
	
	@JsonInclude(value = Include.NON_DEFAULT)
	private String accessToken;
	@JsonInclude(value = Include.NON_DEFAULT)
	private String refreshToken;


}
