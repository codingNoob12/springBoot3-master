package com.in28minutes.learnspringframework.game;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GameRunner {
	
	private GamingConsole game;

	// spring에서 @Qualifier는 어노테이션으로 따로 설정한 이름이나 
	//스프링 빈의 이름을 모두 사용할 수 있다.
//	@Autowired
	public GameRunner(@Qualifier("SuperContraGameQualifier") 
		GamingConsole game) {
		 this.game = game;
	}

	public void run() {
		System.out.println("Running game: " + game);
		game.up();
		game.down();
		game.left();
		game.right();
	}

}
