/**@Description:设置新生代GC次数比老年代GC次数多，可设置新生代空间与老年代空间比例是1:3或1:4；（老年代/新生代）-XX:NewRatio=4
 * @Title:  C_JVMNewTenured.java
 * @Package com.jvm.javaVirtualMachineBase
 * @author: 付品欣
 * @date:   2018年3月12日 下午10:42:23
 * @Copyright: 2018 com.fpq
*/ 
package com.jvm.javaVirtualMachineBase;

/**@Description:TODO(设置新生代GC次数比老年代GC次数多，可设置新生代空间与老年代空间比例是1:3或1:4；)   
 * @ClassName:  C_JVMNewTenured   
 * @author: 付品欣
 * @date:   2018年3月12日 下午10:42:23   
 *     
 * @Copyright: 2018 com.fpq
 */
public class C_JVMNewTenured {

	/**@Description:
	 * @Title: main 
	 * @param args  
	 * @throws   
	 * -Xms20m -Xmx20m -Xmn7m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC -XX:NewRatio=2 -XX:NewRatio=4

	 */
	public static void main(String[] args) {
		System.out.println("设置新生代GC次数比老年代GC次数多，可设置新生代空间与老年代空间比例是1:3或1:4；");
		byte[] bytes = null;
		for(int i=0;i<10;i++){
			bytes = new byte[1*1024*1024];
		}

	}

}
