package com.in28minutes.learnspringframework.example.e1;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
class NormalClass {
	
}

// 프록시를 등록해 놓고, 해당 빈을 요청하면 객체 인스턴스를 만들어서 준다.
@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class PrototypeClass {
	
}

// Scope 종류
// **1. Singleton : 스프링 컨테이너당 객체 인스턴스 1개**
	//스프링 컨테이너가 닫힐 때까지 유지
		//stateless하게 설계
// **2. Prototype : 스프링 컨테이너당 여러 개의 객체 인스턴스들이 존재할 수 있다.**
	//프록시 등록 => 빈 참조 요청 => 생성후 반환
		//stateful하게 설계
// 3. Request : HTTP 요청당 객체 인스턴스 1개
	//요청 처리 될 때까지 유지
// 4. Session : HTTP 세션당 객체 인스턴스 1개
	//세션 끝날때 까지 유지
// 5. Application : 웹 애플리케이션 런타임당 객체 인스턴스 한 개
	//하나의 웹 애플리케이션안에서 스프링 컨테이너를 여러 개 띄우는 것 가능...
	//웹 애플리케이션이 종료될 때 까지 유지
// 6. Websocket : 웹 소켓 인스턴스당 객체 인스턴스 1개
	//웹 소켓이 닫힐 때까지 유지
		//HTTP기반으로 통신 -> 웹소켓을 오랫동안 유지할 리가 없는데... => 사용할 이유가 있나..?

// Java Singleton(GOF) vs Spring Singleton
//- Spring Singleton : 스프링 컨테이너당 1개의 객체 인스턴스를 유지
//- Java Singleton : JVM당 1개의 객체 인스턴스를 유지

@Configuration
@ComponentScan
public class BeanScopesLauncherApplication {
	
	public static void main(String[] args) {
		try (var context = 
				new AnnotationConfigApplicationContext
					(BeanScopesLauncherApplication.class)) {
			
			System.out.println(context.getBean(NormalClass.class));
			System.out.println(context.getBean(NormalClass.class));
			System.out.println(context.getBean(NormalClass.class));
			System.out.println(context.getBean(NormalClass.class));
			System.out.println(context.getBean(NormalClass.class));
			System.out.println(context.getBean(NormalClass.class));
			
			System.out.println(context.getBean(PrototypeClass.class));
			System.out.println(context.getBean(PrototypeClass.class));
			System.out.println(context.getBean(PrototypeClass.class));
			System.out.println(context.getBean(PrototypeClass.class));
			
		}
	}

}
