package com.lshaci.java8.proxy.jdk;

/**
 * 需要动态代理的接口
 */
public interface Subject {
	
	/**
	 * 你好
	 * 
	 * @param name
	 * @return
	 */
	String SayHello(String name);

	/**
	 * 再见
	 * 
	 * @return
	 */
	String SayGoodBye();
}
