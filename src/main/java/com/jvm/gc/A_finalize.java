/**@Description:gc线程在回收堆堆内存中不可达对象之前可能回调用finalize（）方法；
 * @Title:  A_finalize.java
 * @Package com.jvm.gc
 * @author: 付品欣
 * @date:   2018年3月13日 下午11:18:48
 * @Copyright: 2018 com.fpq
*/ 
package com.jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**@Description:TODO(测试)   
 * @ClassName:  A_finalize   
 * @author: 付品欣
 * @date:   2018年3月13日 下午11:18:48   
 *     
 * @Copyright: 2018 com.fpq
 */
public class A_finalize {

	
	
	/**@Description:
	 * @Title: main 
	 * @param args  
	 * @throws   
	 */
	public static void main(String[] args) {
		System.out.println("测试gc线程在回收堆内存不可达对象前可能会调用finalize（）方法。");
		A_finalize myFinalize = new A_finalize();
		myFinalize = null;//告诉jvm虚拟机myFinalize对象不可用了，jvm的gc线程高概率回收该对象；
		List<String> myList = new ArrayList<String>();
		myList = null;
		System.gc();//手动回收堆内存不可达对象
		
		
	}

	/**
	 * <p>Title: finalize</p>
	 * <p>Description:gc垃圾回收线程在回堆内存的不可达对象之前有可能会调用该方法 </p>
	 * @throws Throwable
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable{
		System.out.println("jvm的gc垃圾回收线程开始回收A_finalize类的不可达对象了！");
	}
	
}
