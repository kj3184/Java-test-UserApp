package com.kunal.digib.app.api.gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;




@Component
public class CustomAuthorizationGatewayFilter extends AbstractGatewayFilterFactory<CustomAuthorizationGatewayFilter.Config> {

	Environment environment;
	
	public CustomAuthorizationGatewayFilter(Environment environment) {
        this.environment = environment;
    }
	
	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
				ServerHttpRequest req = exchange.getRequest();
				String authorizationHeader=null;
				
				
				
				if (!req.getHeaders().containsKey(environment.getProperty("authorization.token.header.name"))) {
	                  return this.onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
	              }
				else {
					authorizationHeader = req.getHeaders().get(environment.getProperty("authorization.token.header.name")).get(0);
				}


		        if (authorizationHeader == null || !authorizationHeader.startsWith(environment.getProperty("authorization.token.header.prefix"))) {
//		            chain.doFilter(req, res);
		            return this.onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
		        }

		        //UsernamePasswordAuthenticationToken 
		        String authentication = getAuthentication(req);
		       
		       // SecurityContextHolder.getContext().setAuthentication(authentication);
	             ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().header("Authorisation", authentication).build();
	            		 
	              return chain.filter(exchange.mutate().request(modifiedRequest).build());
	          
	      };
	}
	
	 private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus)  {
         ServerHttpResponse response = exchange.getResponse();
         response.setStatusCode(httpStatus);
 
         return response.setComplete();
     }

	 private String getAuthentication(ServerHttpRequest req) {
	        String authorizationHeader = req.getHeaders().get(environment.getProperty("authorization.token.header.name")).get(0);
	   
	         if (authorizationHeader == null) {
	             return null;
	         }

	         String token = authorizationHeader.replace(environment.getProperty("authorization.token.header.prefix"), "");

	         String userId = Jwts.parser()
	                 .setSigningKey(environment.getProperty("token.secret"))
	                 .parseClaimsJws(token)
	                 .getBody()
	                 .getSubject();

	         if (userId == null) {
	             return null;
	         }
	   
	         return userId;//new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());

	     }
	
	
	public static class Config {
		// Put the configuration properties
	}


}
