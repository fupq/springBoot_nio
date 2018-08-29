/**@Description:
 * @Title:  BufferTest.java
 * @Package com.nio.nio
 * @author: 付品欣
 * @date:   2018年8月30日 上午12:13:09
 * @Copyright: 2018 com.fpq
 * Buffer：缓冲区 负责数据的存储（读写）
 * channel :通道  代表了数据源与IO节点 (文件，网络socket) 之间的链接，负责传输Buffer
*/ 
package com.nio.nio;

import java.nio.ByteBuffer;

/**@Description:TODO(这里用一句话描述这个类的作用)   
 * @ClassName:  BufferTest   
 * @author: 付品欣
 * @date:   2018年8月30日 上午12:13:09   
 *     
 * @Copyright: 2018 com.fpq
 *  * 一.Nio中传输中的两个重要的概念：
 *                          Buffer：缓冲区 负责数据的存储（读写）
 *                          channel :通道  代表了数据源与IO节点 (文件，网络socket) 之间的链接，负责传输Buffer
 *
 *                  二。 java.nio.Buffer
 *                          |--------ByteBuffer
 *                          |--------CharBuffer
 *
 *                          |--------IntBuffer
 *                          |--------DoubleBuffer
 *                          |--------ShortBuffer
 *                          |--------LongBuffer
 *                          |--------DoubleBuffer
            底层对应类型的数组
    xxxBuffer的常用属性
    capacity  ：容量
 limit   ： 限制  默认的时候与capation 的值一样
 position  ： 位置
 mark   ： 标记
 */
public class BufferTest {

	/**@Description:
	 * @Title: main 
	 * @param args  
	 * @throws   
	 */
	public static void main(String[] args) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(10);//底层为10 的byte[]数组
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.position());
//        System.out.println(byteBuffer.mark());
        byteBuffer.put("ddd".getBytes());
        System.out.println("***************");
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.position());//每一次put都会将值移动一次

        System.out.println("**********flip（）********");

        byteBuffer.flip();//将存入数据模式变成取出数据模式 已经存入的数据posstion又变成0，从头继续读

        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.limit());
        System.out.println("----"+byteBuffer.position());

        System.out.println("**********get（）********");

        System.out.println((char)byteBuffer.get());   //每get一次posstion+1
        System.out.println((char)byteBuffer.get());
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.position());

        byteBuffer.rewind();//重置position
        byteBuffer.clear();//清空  回到用户最初的状态  10，10，0

        System.out.println((char)byteBuffer.get());

	}

}
