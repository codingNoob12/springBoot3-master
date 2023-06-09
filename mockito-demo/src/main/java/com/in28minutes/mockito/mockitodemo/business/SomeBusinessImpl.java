package com.in28minutes.mockito.mockitodemo.business;

// serv 역할
public class SomeBusinessImpl {

	private DataService dataService;
	
	public SomeBusinessImpl(DataService dataService) {
		super();
		this.dataService = dataService;
	}

	public int findTheGreatestFromAllData() {
		int[] data = dataService.retrieveAllData();
		int greatesValue = Integer.MIN_VALUE;
		for (int value : data) {
			if (greatesValue < value) {
				greatesValue = value;
			}
		}
		return greatesValue;
	}
	
}

// repo역할
interface DataService {
	int[] retrieveAllData();
}

// DB없이 서비스 계층 테스트 => Stub