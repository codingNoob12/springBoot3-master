package com.in28minutes.learnspringframework.helloworld;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App02HelloWorldSpring {

	public static void main(String[] args) {
		//1: Launch a Spring Context - 
		try (var context = 
				new AnnotationConfigApplicationContext
				(HelloWorldConfiguration.class)) {

			
			//2: Configure the things we want Spring to manage - @Configuration
			//HelloWorldConfiguration - @Configuration
			//name - @Bean
			
			//3: Retrieving Beans managed by Spring
			System.out.println(context.getBean("name"));
			
			System.out.println(context.getBean("age"));
			
			System.out.println(context.getBean("person"));
			
			System.out.println(context.getBean("person2MethodCall"));
			
			System.out.println(context.getBean("person3Parameters"));
			
			System.out.println(context.getBean("address2"));
			
			//NoUniqueBeanDefinitionException
			// 1. primary
			// 2. qualifier
			// 3. getbean시 이름과 타입 모두 제공
			System.out.println(context.getBean(Person.class));
			System.out.println(context.getBean(Address.class));
//			System.out.println(context.getBean("address3", Address.class));
			
			//UnsatisfiedDependencyException
			// 이것 역시도 의존관계 주입시, 스프링 빈이 여러개라 생기는 문제
			System.out.println(context.getBean("person4Parameters"));
			
			System.out.println(context.getBean("person5Qualifier"));
			
			//getBeanDefinitionNames : DI 컨테이너에 등록된 모든 빈의 이름을 조회
			//getBeanDefinitionCount : DI 컨테이너에 등록된 모든 빈의 갯수를 조회
			//getBeanDefinition : 해당 이름을 가진 BeanDefinition을 가져옴
				//참고: AnnotationConfigApplicationContext를 이용해서
				//application context를 만들면, @Configuration이 달린 설정파일도
				//spring bean으로 등록된다.
//			Arrays.stream(context.getBeanDefinitionNames())
//				.forEach(System.out::println);
		}
	}

}
