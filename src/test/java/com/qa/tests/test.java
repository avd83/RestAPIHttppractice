package com.qa.tests;

public class test {

	public static void main(String[] args) {
		
		String test = "[{\"book_name\":\"LearnSQL\",\"isbn\":\"SQL\",\"aisle\":\"112\",\"author\":\"AS Sharma\"}]";
		String test1 = test.substring(1);
		
		String test2 = test1.substring(0,72);
		System.out.println(test1.length());
		System.out.println(test2);
	}

}
