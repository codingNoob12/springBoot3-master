package com.in28minutes.learnspringsecurity.jwt;

import static org.springframework.security.config.Customizer.withDefaults;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

//@Configuration
public class JwtSecurityConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/authenticate").permitAll()
				.anyRequest().authenticated();
		});
		http.sessionManagement(session -> 
			session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.httpBasic(withDefaults()); // Basic 인증방식을 지원함. => 최초 로그인시는 JWT를 발급받아야 되서 Basic으로 인증
		http.csrf().disable();
		http.headers().frameOptions().sameOrigin();
		// oauth 2.0 리소스 서버 구성 코드
		// 인증 방식은 jwt토큰 방식 사용 => JwtDecoder가 필요하다
		http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
		return http.build();
	}
	
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
				.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
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
		
		var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.createUser(user);
		jdbcUserDetailsManager.createUser(admin);
		
		return jdbcUserDetailsManager;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncorder() {
		return new BCryptPasswordEncoder();
	}
	
	// Jwt를 사용하려면 다음 단계를 거쳐야함
	// 1. RSA KeyPair만들기
	// 2. 만들어진 KeyPair로 RSA Key 객체 만들기
	// 3. JWKSource (JSon Web Key Source) 만들기
		// JWKSet => JWKSource
	// 4. RSA 공개키 만들기
	// 5. Use JWKSource for Encoding
	@Bean
	public KeyPair keyPair() {
		// singleton이라 getInstance
		//var: KeyPairGenerator
		try {			
			var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048); // 2048bit로 키를 만듬
			return keyPairGenerator.generateKeyPair();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Bean
	public RSAKey rsaKey(KeyPair keyPair) {
		return new RSAKey
				.Builder((RSAPublicKey) keyPair.getPublic())
				.privateKey(keyPair.getPrivate())
				.keyID(UUID.randomUUID().toString())
				.build();
	}
	
	@Bean
	public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
		//var: JWKSet
		var jwkSet = new JWKSet(rsaKey);
		
//		var jwkSource = new JWKSource() {
//			public List<JWK> get(JWKSelector jwkSelector, SecurityContext context)
//					throws KeySourceException {
//				return jwkSelector.select(jwkSet);
//			}
//		};
		
		return (jwkSelector, context) -> jwkSelector.select(jwkSet);
	}
	
	// JwtDecoder 만들기!! (공개키 이용해서 디코딩)
	@Bean
	public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
		return NimbusJwtDecoder
				.withPublicKey(rsaKey.toRSAPublicKey()) // 공개키 디코더 빌더
				.build();
	}
	
	// JwtEncoder 만들기!! (토큰 발행)
	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource);
	}
}
