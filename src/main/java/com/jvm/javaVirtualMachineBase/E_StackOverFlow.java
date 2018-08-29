/**@Description:栈溢出的解决方法
 * -Xss5m 设置最大调用深度为5M
 * @Title:  E_StackOverFlow.java
 * @Package com.jvm.javaVirtualMachineBase
 * @author: 付品欣
 * @date:   2018年3月13日 上午12:10:27
 * @Copyright: 2018 com.fpq
*/ 
package com.jvm.javaVirtualMachineBase;

/**@Description:TODO(这里用一句话描述这个类的作用)   
 * @ClassName:  E_StackOverFlow   
 * @author: 付品欣
 * @date:   2018年3月13日 上午12:10:27   
 *     
 * @Copyright: 2018 com.fpq
 */
public class E_StackOverFlow {

	private static long count = 0L;
	
	public static long getCount(){
		try{
			count++;
			getCount();
		}catch(Throwable e){
			System.out.println("栈的最大深度是："+count);
			e.printStackTrace();
		}
		return count;
	}
	/**@Description:
	 * @Title: main 
	 * @param args  
	 * @throws   
	 */
	public static void main(String[] args) {
		System.out.println("栈溢出的原因：进行无限的递归调用将栈空间占用完了");
		System.out.println("cont的值是："+getCount());
		
		

	}

}
