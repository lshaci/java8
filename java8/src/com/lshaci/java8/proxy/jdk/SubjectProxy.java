package com.lshaci.java8.proxy.jdk;

import java.lang.reflect.Method;

public class SubjectProxy extends InvocationHandlerImpl<Subject> {

	public SubjectProxy(Object subject) {
		super(subject);
	}

	@Override
	protected Object before(Object proxy, Method method, Object[] args) {
		System.out.println("在调用之前，我要干点啥呢？");
		return false;
	}

	@Override
	protected void after(Object proxy, Method method, Object[] args) {
		System.out.println("在调用之后，我要干点啥呢？");
	}

}
