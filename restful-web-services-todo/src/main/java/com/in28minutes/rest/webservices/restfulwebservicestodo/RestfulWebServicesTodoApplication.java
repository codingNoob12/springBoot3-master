package com.in28minutes.rest.webservices.restfulwebservicestodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RestfulWebServicesTodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServicesTodoApplication.class, args);
	}

	//http://localhost:3000/ to 8080
	// CORS는 SOP를 위반한 것 (Same Origin Policy)
	// 즉, 다른 Origin(출처)으로 요청을 보내는 것
	//Cross Orgin Resource Sharing : 아래 세 가지중 하나라도 다른 경우
		// 1. 프로토콜
		// 2. 도메인
		// 3. **포트 번호**
	//Allow all requests only from http://localhost:3000/
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		// 익명 내부 클래스로 특정 함수만 구현 (jdk 1.8이상)
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")	// 모든 URL에 대해 허용
					.allowedMethods("*")	// 모든 HTTP 메서드에 대해 허용
					.allowedOrigins("http://localhost:3000"); // 원격지 주소에 대해 허용
			}
		};
	}
}
