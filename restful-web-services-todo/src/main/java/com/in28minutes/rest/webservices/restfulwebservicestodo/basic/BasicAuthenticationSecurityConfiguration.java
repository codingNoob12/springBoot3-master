package com.in28minutes.rest.webservices.restfulwebservicestodo.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class BasicAuthenticationSecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return 
			http
				.authorizeHttpRequests(
					auth -> auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() //사전 요청 허용
					.anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				// 무상태 세션
				.sessionManagement(
					session -> session.sessionCreationPolicy
					(SessionCreationPolicy.STATELESS))
				.csrf().disable() // 403에러 방지
				.build();
	}
}
