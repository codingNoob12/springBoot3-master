package com.in28minutes.learnspringsecurity.basic;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableMethodSecurity // 메서드 보안 (Method Security) => 메서드에 Pre, Post 어노테이션
//JSR-250, Secured 어노테이션 사용하려면 추가
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class BasicAuthSecurityConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// 전역 보안 (Global Security)
		http.authorizeHttpRequests(auth -> {
			auth
			.requestMatchers("/users").hasRole("USER")
			.requestMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated();
			// requestMatchers를 이용해서 인증 규칙 설정
			// hasRole, HasAuhority, hasAnyAuthority, isAuthenticated 등
		});
		http.sessionManagement(session -> 
			session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		http.csrf().disable();
		http.headers().frameOptions().sameOrigin();
		return http.build();
	}
	
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//					.allowedMethods("*")
//					.allowedOrigins("http://localhost:3000");
//			}
//		};
//	}
	
	// InMemoryUserCredential
	// 테스트하기는 좋은데, 프로덕션 레벨에선 쓰기 힘듬... => DB에 등록하는 법
//	@Bean
//	public UserDetailsService userDetailsService() {
//		//var : UserDetails
//		var user = User.withUsername("in28minutes")
//			.password("{noop}dummy") // No Encoding
//			.roles("USER")
//			.build();
//		
//		var admin = User.withUsername("admin")
//				.password("{noop}dummy") // No Encoding
//				.roles("ADMIN")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user, admin);
//	}
	
	// DB에 사용자 정보 등록하는 방법
	// spring-security-core에 JdbcDaoImpl이 있는데
	// 거기에 DEFAULT_USER_SCHEMA_DDL_LOCATION로 ddl의 위치를 지정할 수 있음.
	// datasource이용하면 스키마 이용해서 테이블 생성 가능
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
				.build();
	}
	
	// DB 테이블에 사용자 정보 등록하는 방법
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		//var : UserDetails
		var user = User.withUsername("in28minutes")
//			.password("{noop}dummy") // No Encoding
			.password("dummy")
			.passwordEncoder(str -> passwordEncorder().encode(str))
			.roles("USER") // Authority테이블에 "ROLE_역할" 형태로 저장 ROLE_USER
			.build();
		
		var admin = User.withUsername("admin")
//				.password("{noop}dummy") // No Encoding
				.password("dummy")
				.passwordEncoder(str -> passwordEncorder().encode(str))
				.roles("ADMIN", "USER")
				.build();
		
		//var : JdbcUserDetailsManager
		var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.createUser(user);
		jdbcUserDetailsManager.createUser(admin);
		
		return jdbcUserDetailsManager;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncorder() {
		return new BCryptPasswordEncoder();
	}
}
