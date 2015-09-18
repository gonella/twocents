package com.twocents.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCase {

	@Before
	public void before(){
		System.out.println("Before");
	}
	@After
	public void after(){
		System.out.println("After");
	}
	
	@Test
	public void test01(){
		
		System.out.println("INSIDE");
	}
	
	@Test
	public void test02(){
		
		System.out.println("INSIDE 2");
	}
}
