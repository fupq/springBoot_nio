package com.itmayiedu.app;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**建议日志保存到mongodb数据库中
 * AOP为Aspect Oriented Programming的缩写，意为：面向切面编程，通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术。AOP是OOP的延续，
 * 是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间
 * 的耦合度降低，提高程序的可重用性，同时提高了开发的效率。
* <p>Title:WebLogAspect </p>
* <p>Description: </p>
* @author xn042142 付品欣
* @date 2017年12月7日 下午12:24:45
 */
@Aspect
@Component
public class WebLogAspect {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * webLog:(定义切入点的表达式). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣 
	 * @since JDK 1.8
	 * 2017年12月7日 下午12:32:31
	 */
	@Pointcut("execution(public * com.itmayiedu.controller..*.*(..))")
	public void webLog() {
	}
	
	/**
	 * doBefore:(定义打印日志的执行时间：请求之前执行). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param joinPoint
	 * @throws Throwable 
	 * @since JDK 1.8
	 * 2017年12月7日 下午12:36:12
	 */
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 记录下请求内容
		logger.info("URL : " + request.getRequestURL().toString());
		logger.info("HTTP_METHOD : " + request.getMethod());
		logger.info("IP : " + request.getRemoteAddr());
		Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			logger.info("name:{},value:{}", name, request.getParameter(name));
		}
	}
	
	/**
	 * doAfterReturning:(请求之后执行：打印返回结果). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author xn042142 付品欣
	 * @param ret
	 * @throws Throwable 
	 * @since JDK 1.8
	 * 2017年12月7日 下午12:38:08
	 */
	@AfterReturning(returning = "ret", pointcut = "webLog()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		logger.info("RESPONSE : " + ret);
	}

}
