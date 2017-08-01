package com.lshaci.java8.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public abstract class InvocationHandlerImpl<R> implements InvocationHandler {
	
    /** 
     * 这个就是我们要代理的真实对象 
     */  
    private Object subject;  
  
    /** 
     * 构造方法，给我们要代理的真实对象赋初值 
     * 
     * @param subject 
     */  
    public InvocationHandlerImpl(Object subject) {  
        this.subject = subject;  
    } 

    /** 
     * 该方法负责集中处理动态代理类上的所有方法调用。 
     * 调用处理器根据这三个参数进行预处理或分派到委托类实例上反射执行 
     * 
     * @param proxy  代理类实例 
     * @param method 被调用的方法对象 
     * @param args   调用参数 
     * @return 
     * @throws Throwable 
     */ 
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//在代理真实对象前我们可以添加一些自己的操作  
        before(proxy, method, args);
        //当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用  
        Object returnValue = method.invoke(subject, args);  
        after(proxy, method, args);
        //在代理真实对象后我们也可以添加一些自己的操作  
  
        return returnValue;  
	}
	
	@SuppressWarnings("unchecked")
	public R getObject() {
		ClassLoader loader = subject.getClass().getClassLoader();
		Class<?>[] interfaces = subject.getClass().getInterfaces();
		return (R) Proxy.newProxyInstance(loader, interfaces, this);
	}
	
	protected Object before(Object proxy, Method method, Object[] args) {
		return null;
	}
	
	protected void after(Object proxy, Method method, Object[] args) {
	}

}
