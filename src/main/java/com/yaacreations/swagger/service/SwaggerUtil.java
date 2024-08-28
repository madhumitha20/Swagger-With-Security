package com.yaacreations.swagger.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.yaacreations.swagger.entity.Developer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class SwaggerUtil {

private SecretKey Key;
	
    private  static  final long EXPIRATION_TIME = 8640000;//50000;//1000 seconds or 16 minutes
    private static final long REFRESH_EXPIRATION_TIME = 8640000;//24hrs or 86400000 seconds
    
    String secret_String = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";

    public SwaggerUtil(){
        byte[] keyBytes = Base64.getDecoder().decode(secret_String.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateAccessToken(Developer developer){
    	HashMap<String,Object> claim=new HashMap<>();
    	claim.put("designation", developer.getDId());
    	claim.put("type", "AccessToken");
    	claim.put("type", developer.getUserType());
        return Jwts.builder()
        		.claims(claim)
                .subject(developer.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }
    
    public String generateRefreshToken(HashMap<String, Object> claims,Developer developer){
    	HashMap<String,Object> claim=new HashMap<>();
    	claims.put("designation", developer.getDId());
    	claims.put("type", "RefreshToken");
        return Jwts.builder()
                .claims(claims)
                //.claim("RefreshToken", claim)
                .subject(developer.getName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    private SecretKey getKey() {
		byte[] keybytes = Decoders.BASE64.decode(secret_String);
		return Keys.hmacShaKeyFor(keybytes);
	}
	public Map<String, Object> extractallclaims(String token) {
		JwtParser parser = Jwts.parser().verifyWith(getKey()).build();
		Claims claims = parser.parseSignedClaims(token).getPayload();
		return claims;
	}
	
	public String getEmail(String token) {	
		return (String) extractallclaims(token).get("sub");
	}
	
	
	public int getDesignationId(String token) {
		return (int) extractallclaims(token).get("designation");
	}
	
	
	public String getUserType(String token) {
		return (String) extractallclaims(token).get("type");
	}
	
}
