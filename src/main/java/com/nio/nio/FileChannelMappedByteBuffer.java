/**@Description:
 * @Title:  FileChannelMappedByteBuffer.java
 * @Package com.nio.nio
 * @author: 付品欣
 * @date:   2018年8月30日 上午12:05:32
 * @Copyright: 2018 com.fpq
*/ 
package com.nio.nio;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**@Description:TODO(使用FileChannel + MappedByteBuffer（直接缓冲区）-->物理内存映射文件)   
 * @ClassName:  FileChannelMappedByteBuffer   
 * @author: 付品欣
 * @date:   2018年8月30日 上午12:05:32   
 *     
 * @Copyright: 2018 com.fpq
 */
public class FileChannelMappedByteBuffer {

	/**
	 * @throws IOException @Description:
	 * @Title: main 
	 * @param args  
	 * @throws   
	 */
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();


        //1.创建Channel
//      FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
//      FileChannel outChannel = FileChannel.open(Paths.get("3.jpg"), StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        FileChannel inChannel = FileChannel.open(Paths.get("E:\\oracle11g\\oradata\\oracle\\SYSAUX01.DBF"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("D:\\nio\\file\\nio\\SYSAUX01.DBF"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        //2.创建得到直接缓冲区
        MappedByteBuffer inMappedBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        //3.数据的读写
        byte[] dst = new byte[inMappedBuffer.limit()];
        inMappedBuffer.get(dst);//将数据写入到dst中
        outMappedBuffer.put(dst);//从dst中将数据取出

        //4.关闭资源
        inChannel.close();
        outChannel.close();


        long end = System.currentTimeMillis();
        System.out.println("直接缓冲区：" + ((end - start)/1000)+"秒");

	}

}
