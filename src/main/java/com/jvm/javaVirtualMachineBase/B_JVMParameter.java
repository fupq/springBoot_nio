/**@Description:将初始的堆大小与最大堆大小相等，来减少GC垃圾回收次数；
 * @Title:  B_JVMParameter.java
 * @Package com.jvm.javaVirtualMachineBase
 * @author: 付品欣
 * @date:   2018年3月11日 上午1:03:10
 * @Copyright: 2018 com.fpq
*/ 
package com.jvm.javaVirtualMachineBase;

import java.text.DecimalFormat;

/**@Description:TODO(这里用一句话描述这个类的作用)   
 * @ClassName:  B_JVMParameter   
 * @author: 付品欣
 * @date:   2018年3月11日 上午1:03:10   
 *     
 * @Copyright: 2018 com.fpq
 */
public class B_JVMParameter {

	public static void main(String[] args) throws InterruptedException{
		byte[] myByte1 = new byte[1*1024*1024];
		System.out.println("JVM在堆heap中分配了1M内存");
		printJVMParameter();
		Thread.sleep(3000L);
		byte[] myByte2 = new byte[4*1024*1024];
		System.out.println("JVM在堆heap中分配了4M内存");
		printJVMParameter();
		Thread.sleep(3000L);
//		byte[] myByte3 = new byte[100*1024*1024];
//		System.out.println("JVM在堆heap中分配了100M内存");
//		printJVMParameter();
	}
	
	public static void printJVMParameter(){
		//获取最大内存
		long maxMemory = Runtime.getRuntime().maxMemory();
		System.out.println("\nJVM虚拟机最大内存:"+ toM(maxMemory)+"M");
		//获取当前空闲的内存
		long freeMemory = Runtime.getRuntime().freeMemory();
		System.out.println("JVM虚拟机当前空闲内存:"+ toM(freeMemory)+"M");
		//获取JVM虚拟机已经使用的内存
		long totalMemory = Runtime.getRuntime().totalMemory();
		System.out.println("JVM虚拟机已经使用的内存:"+ toM(totalMemory)+"M");
	}
	
	/**
	 * @Description:将kb转换成兆M
	 * @Title: toM 
	 * @param maxMemory kb
	 * @return  maxMemory转换为M的值
	 * @throws
	 */
	private static String toM(final long maxMemory) {
		float num = (float) maxMemory / (1024 * 1024);
		DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
		String s = df.format(num);// 返回的是String类型
		return s;
	}

}
