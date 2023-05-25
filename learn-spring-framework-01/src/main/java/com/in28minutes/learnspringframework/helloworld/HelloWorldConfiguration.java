package com.in28minutes.learnspringframework.helloworld;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

//jdk 16버전에서 추가된 기능 (레코드)
record Person (String name, int age, Address address) { };
record Address (String firstLine, String city) { };

@Configuration
public class HelloWorldConfiguration {
	
	@Bean
	public String name() {
		return "Ranga";
	}
	
	@Bean
	public int age() {
		return 15;
	}
	
	@Bean
	@Primary
	public Person person() {
		return new Person("Ravi", 20, new Address("Main Street", "Utrecht"));
	}
	
	@Bean
	public Person person2MethodCall() {
		return new Person(name(), age(), address());
	}
	
	// spring bean에서는 auto wired안해줘도 알아서 의존관계 자동 주입일어남
	@Bean
	public Person person3Parameters(String name, int age, Address address3) {
		return new Person(name, age, address3);
	}
	
	//address라는 이름을 가진 spring bean은 없음. => 타입으로 조회
	//=> 스프링 빈이 2개 조회되서 에러
	@Bean
	public Person person4Parameters(String name, int age, Address address3) {
		return new Person(name, age, address3);
	}
	
	//qualifier는 스프링 빈의 이름을 변경하는 것은 아님
	@Bean
	public Person person5Qualifier(String name, int age, 
			@Qualifier("address3qualifier") Address address) {
		return new Person(name, age, address);
	}
	
	@Bean(name = "address2")
	@Primary
	public Address address() {
		return new Address("Baker Street", "London");
	}
	
	@Bean(name = "address3")
	@Qualifier("address3qualifier")
	public Address address3() {
		return new Address("Motinagar", "Hyderabad");
	}
}
