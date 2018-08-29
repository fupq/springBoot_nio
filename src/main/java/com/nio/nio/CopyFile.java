/**@Description:Buffer&Chanel
 * @Title:  CopyFile.java
 * @Package com.nio.nio
 * @author: 付品欣
 * @date:   2018年3月18日 下午7:59:13
 * @Copyright: 2018 com.fpq
*/ 
package com.nio.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

/**@Description:TODO(使用nio中的FileChannel和ByteBuffer拷贝文件)   
 * @ClassName:  CopyFile   
 * @author: 付品欣
 * @date:   2018年3月18日 下午7:59:13   
 *     
 * @Copyright: 2018 com.fpq
 * channel用于向 buffer 提供数据或者读取 buffer 数据 ,buffer 对象的唯一接口
 */
public class CopyFile {

	public static void main(String[] args) throws Exception {  
        String infile = "E:\\oracle11g\\oradata\\oracle\\SYSAUX01.DBF";  
        String outfile = "D:\\nio\\file\\nio\\SYSAUX01.DBF";  
        // 获取源文件和目标文件的输入输出流  
        FileInputStream fin = new FileInputStream(infile);  
        FileOutputStream fout = new FileOutputStream(outfile);  
        // 获取输入输出通道  
        FileChannel fcin = fin.getChannel();  
        FileChannel fcout = fout.getChannel();  
        // 创建缓冲区  
        ByteBuffer buffer = ByteBuffer.allocate(1024);  
        Date startDate = new Date();
        while (true) {  
            // clear方法重设缓冲区，使它可以接受读入的数据  
            buffer.clear();  
            // 从输入通道中将数据读到缓冲区  
            int r = fcin.read(buffer);  
            // read方法返回读取的字节数，可能为零，如果该通道已到达流的末尾，则返回-1  
            if (r == -1) {  
                break;  
            }  
            // flip方法让缓冲区可以将新读入的数据写入另一个通道  
            //翻转这个缓冲区。限制设置为当前位置，然后位置设置为0。如果标记被定义，那么它将被丢弃。在一系列的通道读取或操作之后，调用此方法来准备一个通道写入或相对get操作序列
            buffer.flip();  
            // 从输出通道中将数据写入缓冲区  
            fcout.write(buffer);  
        }  
        Date endDate = new Date();
        System.out.println("ByteBuffer+FileChannel拷贝799M的文件所需要的时间"+((endDate.getTime()-startDate.getTime())/1000)+"秒。");
    }  
}
