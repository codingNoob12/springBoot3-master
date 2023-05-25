package com.in28minutes.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

//HTTP 상태코드
// 200: Success 성공
// 201: Created 생성됨
// 204: No Content 콘텐츠 없음 (PUT -> 해당 콘텐츠가 없을 때)
// 401: Unauthorized 권한 없음
// 400: Bad Request 잘못된 요청 (ex. Validation error)
// 404: Not Found 리소스를 찾을 수 없음
// 500: Server Error 서버 오류

@RestController
public class UserResource {

	private UserDaoService service;

	public UserResource(UserDaoService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/users")
	public List<User> retriveAllUsers() {
		return service.findAll();
	}
	
	//EntityModel
	//WebMvcLinkBuilder
	@GetMapping("/users/{id}")
	// EntityModel로 Wrapping하면 id값이 사라짐... 왜지?
	public EntityModel<User> retriveUser(@PathVariable int id) {
		User user = service.findOne(id);
		
		// service에서 findOne을 실행하다 에러 => 500번 상태코드
		// 에러 발생안하게 코드 변경 => 200번 상태코드
		// 예외를 직접 만들었음 Exception은 컴파일러에서 체크하기 때문에 예외처리를 따로 해주라고 한다.
		// 그래서 RuntimeError로 만들었음
		if (user == null) {
			throw new UserNotFoundException("id:" + id);
		}
		
		// 사용자에게 Data와 Action을 모두 알려주기 위해 EntityModel로 User를 래핑
		//=> WebMvcLinkBuilder로 link 추가
		EntityModel<User> entityModel = EntityModel.of(user);
		
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retriveAllUsers());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		// /users/{id}
		// location 헤더 => /users/4 : 어떤 user가 생성되었는지 return
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		
		// 사용자 친화적
		// ResponseEntity의 헤더에 HTTP 상태코드, URI를 담아서 보내주기
		return ResponseEntity.created(location).build();
		// body에 객체 담아서 보내기
//		return ResponseEntity.created(location).body(savedUser);
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		service.deleteById(id);
	}
}
