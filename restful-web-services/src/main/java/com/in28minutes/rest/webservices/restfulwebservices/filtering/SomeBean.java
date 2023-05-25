package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;

//정적 필터링 : 정적으로 응답에서 특정 필드를 제외

// class 수준에서 json 변환시 무시할 속성값을 지정할 수 있다.
//@JsonIgnoreProperties({"field1", "field2"})
@JsonFilter("SomeBeanFilter")
public class SomeBean {

	private String field1;

//	@JsonIgnore // 이 필드는 Json 변환시 무시됨
	private String field2;
	
	// JsonIgnore를 사용하는게 유지보수가 편함
//	@JsonIgnore
	private String field3;

	public SomeBean(String field1, String field2, String field3) {
		super();
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}

	public String getField1() {
		return field1;
	}

	public String getField2() {
		return field2;
	}

	public String getField3() {
		return field3;
	}

	@Override
	public String toString() {
		return "SomeBean [field1=" + field1 + ", field2=" + field2 + ", field3=" + field3 + "]";
	}
}
