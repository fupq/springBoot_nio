/**@Description:内存溢出
 * @Title:  B_MemoryLeak.java
 * @Package com.jvm.gc
 * @author: 付品欣
 * @date:   2018年3月14日 下午6:18:45
 * @Copyright: 2018 com.fpq
*/
package com.jvm.gc;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:TODO(堆heap内存溢出)
 * @ClassName: B_MemoryLeak
 * @author: 付品欣
 * @date: 2018年3月14日 下午6:18:45
 * 
 * @Copyright: 2018 com.fpq
 */
public class B_MemoryLeak {

	private String[] s = new String[1000];

	/**
	 * 运行时设置jvm的参数：-Xms10m -Xmx10m -Xmn2m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC -XX:NewRatio=4
	 */
	public static void main(String[] args) {
		try {
			Map<String, Object> m = new HashMap<String, Object>();
			int i = 0;
			int j = 10000;
			while (true) {
				for (; i < j; i++) {
					B_MemoryLeak memoryLeak = new B_MemoryLeak();
					m.put(String.valueOf(i), memoryLeak);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

/*jvm参数：-XX:PermSize=2m -XX:MaxPermSize=2m  
将方法区的大小设置很低即可，在启动加载类库时就会出现内存不足的情况  */