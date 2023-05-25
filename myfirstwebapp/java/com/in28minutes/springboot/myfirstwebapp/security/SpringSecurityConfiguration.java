package com.in28minutes.springboot.myfirstwebapp.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
	//LDAP or DataBase
	//In Memory
	
	//InMemoryUserDetailsManager
	//InMemoryUserDetailsManager(UserDetails... users)
	
	@Bean
	public InMemoryUserDetailsManager createUserDetailsManager() {
		Function<String, String> passwordEncorder
		= input -> passwordEncorder().encode(input);
		
		UserDetails userDetails1 = createNewUser("in28minutes", "dummy", passwordEncorder);
		UserDetails userDetails2 = createNewUser("ranga", "dummydummy", passwordEncorder);
		
		return new InMemoryUserDetailsManager(userDetails1, userDetails2);
	}

	private UserDetails createNewUser(String username, String password, Function<String, String> passwordEncorder) {
		UserDetails userDetails = User.builder()
				.passwordEncoder(passwordEncorder)
				.username(username)
				.password(password)
				.roles("USER", "ADMIN")
				.build();
		return userDetails;
	}
	
	@Bean
	public PasswordEncoder passwordEncorder() {
		return new BCryptPasswordEncoder();
	}
	
	//All URLs are protected
	//A login form is shown for unauthorized request
	//CSRF disable : 사이트간 요청 위조 방지 기능을 비활성화
	//Frames
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 인증된 사용자의 요청 -> 모두 허용
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated());
		// 인증 안되어 있으면 로그인 폼으로 이동
		http.formLogin(withDefaults());
		
		//CSRF 비활성화
		http.csrf().disable();
		//iframe 사용가능하게 변경
		http.headers().frameOptions().disable();
		
		return http.build();
	}
}
