/**
 * 
 */
package com.jvm.javaVirtualMachineBase;

import org.apache.log4j.Logger;

/**
 * @Description:TODO(测试static修饰的类中属性是否放置在jvm内存的方法区（静态区）)   
 * @ClassName:  A_MethodArea   
 * @author: 付品欣
 * @date:   2018年3月9日 下午5:38:26   
 *     
 * @Copyright: 2018 com.fpq
 */
public class A_MethodArea {

	private static Logger logger = Logger.getLogger(A_MethodArea.class);
	/**
	 * 未被static修饰的属性，存储在jvm内存中的堆区，类类A_MethodArea的每个对象独自创建自己的该变量，对象间不共享
	 */
	private int noStaticCount = 0;
	
	/**
	 * 被static修饰的属性，存储在jvm内存中的方法区（静态区），类A_MethodArea的所有对象共享该变量
	 */
	private static int staticCount = 0;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 
		A_MethodArea methodArea1 = new A_MethodArea();
		A_MethodArea methodArea2 = new A_MethodArea();
		methodArea1.noStaticCount++;
		methodArea2.noStaticCount++;
		
		methodArea1.staticCount++;
		methodArea2.staticCount++;
		
		//未被static修饰的属性，存储在jvm内存中的？区，类类A_MethodArea的每个对象独自创建自己的该变量，对象间不共享
		logger.info("结论：未被static修饰的属性，对象间各自创建并存储自己的noStaticCount属性的值，methodArea1.noStaticCount="+methodArea1.noStaticCount);
		
		//被static修饰的属性，存储在jvm内存中的方法区（静态区），类A_MethodArea的所有对象共享该变量
		logger.info("结论：被static修饰的属性，类的所有对象共享在方法区（静态区）创建并存储的staticCount属性的值，methodArea1.staticCount="+methodArea1.staticCount);
		
		
	}

}
