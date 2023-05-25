package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Runtime에러는 서버에러 => HTTP 상태코드 : 500
// UserNotFoundException은 User를 못찾아서 404를 보내야함
// 이를 위해서 ResponseStatus를 이용
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String message) {
		super(message);
	}
}
