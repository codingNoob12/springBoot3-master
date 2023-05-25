package com.in28minutes.springboot.learnspringboot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// Application의 설정을 관리할 때, ConfigurationProperties애노테이션을 이용함.
// 아래와 같이 코드를 작성하고 컴포넌트로 스프링이 관리하게 하면 된다.
// 이럴 경우, application.properties에서 정의한 값을 가져와 인스턴스를 생성한다.
// 이렇게 객체화 해놓으면, 파싱하는 시간이 줄어듦!!
@ConfigurationProperties(prefix = "currency-service")
@Component
public class CurrencyServiceConfiguration {

	private String url;
	private String username;
	private String key;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
