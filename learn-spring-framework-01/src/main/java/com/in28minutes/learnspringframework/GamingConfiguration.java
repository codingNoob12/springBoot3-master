package com.in28minutes.learnspringframework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.in28minutes.learnspringframework.game.GameRunner;
import com.in28minutes.learnspringframework.game.GamingConsole;
import com.in28minutes.learnspringframework.game.PacmanGame;

@Configuration
public class GamingConfiguration {

	@Bean
	public GamingConsole game() {
		return new PacmanGame();
	}
	
	// 스프링 빈 생성과 동시에 의존관계 주입
//	@Bean
//	public GameRunner gameRunner() {
//		return new GameRunner(game());
//	}
	
	// 스프링 빈 생성 이후, 의존관계 주입
	@Bean
	public GameRunner gameRunner(GamingConsole game) {
		return new GameRunner(game);
	}
}
