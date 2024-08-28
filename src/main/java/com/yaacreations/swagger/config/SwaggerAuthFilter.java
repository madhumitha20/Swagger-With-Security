package com.yaacreations.swagger.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.yaacreations.swagger.service.SwaggerLoginService;
import com.yaacreations.swagger.service.SwaggerUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SwaggerAuthFilter extends OncePerRequestFilter{
	
	private HandlerExceptionResolver handlerExceptionResolver;

	public SwaggerAuthFilter(HandlerExceptionResolver handlerExceptionResolver) {
		super();
		this.handlerExceptionResolver = handlerExceptionResolver;
	}

	@Autowired
	private SwaggerUtil swaggerUtil;

	@Autowired
	private SwaggerLoginService swaggerLoginService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwtToken;
		final String userEmail;
	try {
			if (authHeader == null || authHeader.isBlank()) {
				filterChain.doFilter(request, response);
				return;
			}
			jwtToken = authHeader.substring(7);
			userEmail = swaggerUtil.extractUsername(jwtToken);
			if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = swaggerLoginService.loadUserByUsername(userEmail);
				if (swaggerUtil.isTokenValid(jwtToken, userDetails)) {
					SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					token.setDetails(token);
					securityContext.setAuthentication(token);
					SecurityContextHolder.setContext(securityContext);
				}
			}
			filterChain.doFilter(request, response);
		} catch (SignatureException ex) {
			// TODO: handle exception
			handlerExceptionResolver.resolveException(request, response, null, ex);
		
		} catch (ExpiredJwtException ex) {
			handlerExceptionResolver.resolveException(request, response, null, ex);
			
		} catch (Exception  ex) {
			System.out.println(ex);
			// TODO: handle exception
			handlerExceptionResolver.resolveException(request, response, null, ex);
		}
	}

}
