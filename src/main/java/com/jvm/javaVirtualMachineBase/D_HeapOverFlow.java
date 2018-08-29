/**@Description:堆溢出的解决方法：扩大对的空间
 * @Title:  D_HeapOverFlow.java
 * @Package com.jvm.javaVirtualMachineBase
 * @author: 付品欣
 * @date:   2018年3月12日 下午11:45:01
 * @Copyright: 2018 com.fpq
*/ 
package com.jvm.javaVirtualMachineBase;

import java.util.ArrayList;
import java.util.List;

/**@Description:TODO(这里用一句话描述这个类的作用)   
 * @ClassName:  D_HeapOverFlow   
 * @author: 付品欣
 * @date:   2018年3月12日 下午11:45:01   
 *     
 * @Copyright: 2018 com.fpq
 */
public class D_HeapOverFlow {

	/**@Description:
	 * @Title: main 
	 * @param args  
	 * @throws   
	 */
	public static void main(String[] args) {
		List<Object> listObject = new ArrayList();
		for(int i=0;i<10;i++){
			System.out.println("i:"+i);
			listObject.add(new byte[1*1024*1024]);
		}
		System.out.println("赋值完毕，添加了10M的空间");
	}

}
