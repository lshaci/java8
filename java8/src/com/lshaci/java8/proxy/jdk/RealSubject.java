package com.lshaci.java8.proxy.jdk;

/**
 * 实际对象
 */
public class RealSubject implements Subject {

	@Override
	public String SayHello(String name) {
		return "hello " + name;
	}

	@Override
	public String SayGoodBye() {
		return " good bye ";
	}

}
