package com.in28minutes.learnspringframework.example.a1;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
class YourBusinessClass {
	
//	@Autowired
	Dependency1 dependency1;
	
//	@Autowired
	Dependency2 dependency2;

//	@Autowired
	public YourBusinessClass(Dependency1 dependency1, Dependency2 dependency2) {
		super();
		System.out.println("Constructor Injection - YourBusinessClass");
		this.dependency1 = dependency1;
		this.dependency2 = dependency2;
	}
	
//	@Autowired
//	public void setDependency1(Dependency1 dependency1) {
//		System.out.println("Setter Injection - setDependency1");
//		this.dependency1 = dependency1;
//	}
//
//	@Autowired
//	public void setDependency2(Dependency2 dependency2) {
//		System.out.println("Setter Injection - setDependency2");
//		this.dependency2 = dependency2;
//	}

	@Override
	public String toString() {
		return "Using " + dependency1 + " and " + dependency2;
	}
}

@Component
class Dependency1 {
	
}

@Component
class Dependency2 {
	
}


//@ComponentScan에서 패키지 지정안하면 현재 패키지
	//@Configuration도 @Component다. -> 컴포넌트 스캔 대상이다.
	//그래서 @SpringBootApplication이 있는 패키지 내부에 아래와 같은 파일이 
	//있으면 다른 패키지의 컴포넌트까지도 스캔해올 수 있다.
		//Spring MVC 프로젝트에서는 @SpringBootApplication이 없다.
		//이 경우, 설정파일에 설정된 context:componet-scan의 base-package에
		//@ComponetScan을 하는 @Configuration파일을 넣으면 된다.
@Configuration
@ComponentScan
public class DepInjectionLauncherApplication {
	
	public static void main(String[] args) {
		try (var context = 
				new AnnotationConfigApplicationContext
					(DepInjectionLauncherApplication.class)) {
			
			Arrays.stream(context.getBeanDefinitionNames())
				.forEach(System.out::println);
			
			System.out.println(context.getBean(YourBusinessClass.class));
			
		}
	}

}
