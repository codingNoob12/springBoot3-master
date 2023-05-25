package com.in28minutes.learnspringframework.test;

import java.io.Serializable;

class Pojo { // Plain Old Java Object : 그냥 자바 객체
	private String text;
	private int number;
	
	public String toString() {
		return text + ":" + number;
	}
}

//3: Serializable을 구현
class JavaBean implements Serializable { //EJB
	private String text;
	private int number;
	
	// 기본 생성자
	//1: public no-arg constructor
	public JavaBean() {
		
	}

	//2: getters and setters
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}

//Spring Bean : 스프링이 관리하는 객체
public class SpringBeanVsJavaBean {

	public static void main(String[] args) {
		Pojo pojo = new Pojo();
		
		System.out.println(pojo);
	}

}
