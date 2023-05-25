package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

//	@GetMapping("/filtering")
//	public SomeBean filtering() {
//		// 동적 필터링
//		//MappingJacksonValue : 변환 방시
//		return new SomeBean("value1", "value2", "value3");
//	}

	// 동적 필터링
	// MappingJacksonValue : Json 메시지 컨버터를 이용할 때 사용 가능
	// setFilters로 filter를 설정해준 뒤, SomeBean에서도 @JsonFilter로 필터를 등록해야 동작함
	@GetMapping("/filtering") // field2
	public MappingJacksonValue filtering() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3");

		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);

		FilterProvider filters = createFilterProvider("SomeBeanFilter", "field1", "field3");
		// setFilters => 직렬화 로직을 추가
		mappingJacksonValue.setFilters(filters);

		return mappingJacksonValue;
	}

//	@GetMapping("/filtering-list")
//	public List<SomeBean> filteringList() {
//		return Arrays.asList(new SomeBean("value1", "value2", "value3"),
//				new SomeBean("value4", "value5", "value6"));
//	}

	@GetMapping("/filtering-list") // field2, field3
	public MappingJacksonValue filteringList() {
		List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"),
				new SomeBean("value4", "value5", "value6"));

		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
		FilterProvider filters = createFilterProvider("SomeBeanFilter", "field2", "field3");
		mappingJacksonValue.setFilters(filters);

		return mappingJacksonValue;
	}

	private FilterProvider createFilterProvider(String filterName, String... fields) {
		// filterOutAllExcept에서 명시한 필드만 포함
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
		FilterProvider filters = new SimpleFilterProvider().addFilter(filterName, filter);
		return filters;
	}
}
