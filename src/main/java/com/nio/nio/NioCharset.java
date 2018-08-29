/**@Description:
	字符编码解码 : 字节码本身只是一些数字，放到正确的上下文中被正确被解析。向 ByteBuffer 中存放数据时需要考虑字符集的编码方式，
	读取展示 ByteBuffer 数据时涉及对字符集解码。
	Java.nio.charset 提供了编码解码一套解决方案。
	以我们最常见的 http 请求为例，在请求的时候必须对请求进行正确的编码。在得到响应时必须对响应进行正确的解码。
	以下代码向 baidu 发一次请求，并获取结果进行显示。例子演示到了 charset 的使用。
 * @Title:  NioCharset.java
 * @Package nio
 * @author: 付品欣
 * @date:   2018年3月18日 下午9:02:33
 * @Copyright: 2018 com.fpq
*/ 
package com.nio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**@Description:TODO(演示charset的使用)   
 * @ClassName:  NioCharset   
 * @author: 付品欣
 * @date:   2018年3月18日 下午9:02:33   
 *     
 * @Copyright: 2018 com.fpq
 */
public class NioCharset {

	// 创建UTF-8字符集  
    private Charset charset = Charset.forName("UTF-8");
    
    private SocketChannel channel;  //TCP
    
    /**
     * @Description:向 baidu 发一次请求，并获取结果进行显示
     * @Title: readHTMLContent   
     * @throws
     */
    public void readHTMLContent() {  
        try {  
            InetSocketAddress socketAddress = new InetSocketAddress("www.baidu.com", 80);  
            //step1:打开连接  
			channel = SocketChannel.open(socketAddress);  
           //step2:发送请求，使用GBK编码  
            channel.write(charset.encode("GET " + "/ HTTP/1.1" + "\r\n\r\n"));  
            //step3:读取数据  
            /*
             *分配一个新的字节缓冲区。
				新缓冲区的位置将为零，其极限将为其容量，其标记将为未定义，其每个元素将被初始化为零。它将有一个支持数组，
				它的数组偏移量将为零。参数:容量——新缓冲区的容量，在bytesreturn:新的字节缓冲区。
             */
            ByteBuffer buffer = ByteBuffer.allocate(1024);// 创建1024字节的缓冲  
            while (channel.read(buffer) != -1) {  
                buffer.flip();// flip方法在读缓冲区字节操作之前调用。 
             // 使用Charset.decode方法将字节转换为字符串  
                System.out.println(charset.decode(buffer));  
                buffer.clear();// 清空缓冲  
            }  
        } catch (IOException e) {  
            System.err.println(e.toString());  
        } finally {  
            if (channel != null) {  
                try {  
                    channel.close();  
                } catch (IOException e) {  
                }  
            }  
        }  
    }  
    
    
    public static void main(String[] args) {  
        new NioCharset().readHTMLContent();  
    } 
}
