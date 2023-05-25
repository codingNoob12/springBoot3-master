package com.in28minutes.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Rest API 버전 관리시 고려사항
//1. URI Pollution
	// URI, Request Parameter Versioning을 하면 너무 많은 URL을 사용함
	//=> URI Pollution이 많이 발생함
//2. Misuse of HTTP Headers : HTTP헤더 오용
	// HTTP헤더는 버전관리용으로 사용하면 안된다.
	//=> Header, Media type Versioning은 HTTP 헤더를 오용
//3. Caching
	//일반적으로 캐싱은 URL을 기반으로 수행됨
	//-> Header, Media type Versioning은 같은 URL을 사용 => URL기반 캐싱 불가
	//=> 헤더를 살펴보고 캐싱
//4. 브라우저에서 요청 실행 여부
	// URI, Request Parameter Versioning은 브라우저에서 쉽게 실행 가능
	// Header, Media type Versioning은 브라우저에서 실행 불가능
//5. API Documentation
	// URI, Request Parameter Versioning에선 API문서 생성이 편리 <= URL이 다름
	// API 문서 생성 툴은 헤더 기준으로 구분하는 API 문서 생성은 지원하지 않을 수 있다.

@RestController
public class VersioningPersonController {

	// URI Versioning
	@GetMapping("/v1/person")
	public PersonV1 getFirstVersionOfPerson() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping("/v2/person")
	public PersonV2 getSecondVersionOfPerson() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	// Parameter Versioning
	//파라미터에서 값을 가져오려면 params 속성을 써야함
	@GetMapping(path = "/person", params = "version=1")
	public PersonV1 getFirstVersionOfPersonRequestParameter() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping(path = "/person", params = "version=2")
	public PersonV2 getSecondVersionOfPersonRequestParameter() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	// (Custom) Header Versioning
	//요청 헤더에서 값을 가져오려면 headers 속성을 써야 함
	@GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
	public PersonV1 getFirstVersionOfPersonRequestHeader() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
	public PersonV2 getSecondVersionOfPersonRequestHeader() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
	
	// Media Type Versioning || Content Negotiation || Accept Header
	//컨텐츠 협상시 사용되는 Request Header의 Accept 헤더를 이용하는 방법
	// Accept헤더에서 값을 얻어오려면 produces 속성을 이용해야 함
	@GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v1+json")
	public PersonV1 getFirstVersionOfPersonAcceptHeader() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v2+json")
	public PersonV2 getSecondVersionOfPersonAcceptHeader() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
}
