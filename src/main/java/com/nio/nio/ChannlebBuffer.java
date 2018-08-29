/**@Description:
 * @Title:  ChannlebBuffer.java
 * @Package com.nio.nio
 * @author: 付品欣
 * @date:   2018年8月30日 上午12:00:49
 * @Copyright: 2018 com.fpq
*/ 
package com.nio.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**@Description:TODO(实现文件的复制    FileChannel +ByteBuffer（非直接缓冲区）)   
 * @ClassName:  ChannlebBuffer   
 * @author: 付品欣
 * @date:   2018年8月30日 上午12:00:49   
 * channleb只能与buffer交互
     * <p>
     * //
 * @Copyright: 2018 com.fpq
 */
public class ChannlebBuffer {

	/**
	 * @throws IOException 
	 * @throws FileNotFoundException @Description:
	 * @Title: main 
	 * @param args  
	 * @throws   
	 */
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		
		//1.提供相应的输入输出流
        FileInputStream fis = new FileInputStream("E:\\oracle11g\\oradata\\oracle\\SYSAUX01.DBF");
        FileOutputStream fos = new FileOutputStream("D:\\nio\\file\\nio\\SYSAUX01.DBF");

        //创建相应的Channel  通过我们的流去创建对应的通道，然后通过通道继续读写数据
        FileChannel inchannel = fis.getChannel();
        FileChannel outchannel = fos.getChannel();

        //3.提供缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while ((inchannel.read(byteBuffer)) != -1) {

            byteBuffer.flip();//切换为读数据的模式
            //如果这里不切换的话，缓存区会满

            outchannel.write(byteBuffer);

            byteBuffer.clear();//清空，读完当前的缓存区然后再继续
        }
        fis.close();
        fos.close();
        inchannel.close();
        outchannel.close();
        long end = System.currentTimeMillis();
        System.out.println("直接缓冲区：" + ((end - start)/1000)+"秒");//1573-1575
	}

}
