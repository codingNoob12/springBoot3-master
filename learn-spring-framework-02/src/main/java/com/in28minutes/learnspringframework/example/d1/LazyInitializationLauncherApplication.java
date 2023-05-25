package com.in28minutes.learnspringframework.example.d1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
class ClassA {
	
}

// Lazy 어노테이션 => 지연 로딩
// 스프링 시작 시점이 아니라 사용될 때까지 스프링 빈을 등록하지 않음
//기본적으론 즉시로딩, @Lazy로 지연로딩
// 즉시 로딩을 추천함. Spring Configuration에 문제를 애플리케이션 실행시점에 바로 알 수 있음.
// 사용률이 적은 스프링 빈에 지연 초기화를 적용하는 게 좋다.
@Component
@Lazy
class ClassB {
	
	private ClassA classA;

	public ClassB(ClassA classA) {
		//Logic
		System.out.println("Some Initialization logic");
		this.classA = classA;
	}
	
	public void doSomething() {
		System.out.println("Do Something");
	}
}

@Configuration
@ComponentScan
public class LazyInitializationLauncherApplication {
	
	public static void main(String[] args) {
		try (var context = 
				new AnnotationConfigApplicationContext
					(LazyInitializationLauncherApplication.class)) {
			
			System.out.println("Initialization of context is completed");
			
			context.getBean(ClassB.class).doSomething();
			
		}
	}

}
