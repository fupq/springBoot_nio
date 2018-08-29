package com.nio.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.Date;

public class MyIOTest {
	private static String inFileName = "E:\\oracle11g\\oradata\\oracle\\SYSAUX01.DBF";
	private static String outFileName = "D:\\nio\\file\\io\\ioWrite.txt";
	private static String mp3InFileName = "D:/nio/file/io/carson-in.mp4";
	private static String mp3OutFileName = "D:/nio/file/io/carson-out.mp4";
	
	public static void main(String[] args){
		System.out.println("使用io的字节流来实现文件的copy功能，将文件内容从ioRead.txt拷贝到ioWrite.txt中");
		copyReadFileByBytes(inFileName,outFileName);
		
//		System.out.println("DataOutputStream的API测试函数 :应用程序以与机器无关方式从底层输入流中写基本 Java 数据类型");
//		testDataOutputStream();
//		
//		testDataInputStream();
//		
//		readAndWrite(mp3InFileName,mp3OutFileName);
//		
//		//ByteArrayInputStream 和 ByteArrayOutputStream该类从内存中的字节数组中读取数据，它的数据源是一个字节数组，它们分别继承自InputStream 和 OutputStream。
//		byte[] dataSource = {'f','u',' ','p','i','n',' ','x','i','n','.',};
//		byte[] copybytes = readWithByteArray(dataSource);
//		System.out.println(dataSource.toString()+"使用ByteArrayInputStream 和 ByteArrayOutputStream拷贝后的byte[]是："+copybytes.toString());
//		
//		//InputStreamReader 和 OutputStreamWriter为各种输入输出字符流的基类，所有字符流都继承这两个基类
//		readFileByChars(inFileName);
//		
//		//使用FileReader - 和 FileWriter拷贝文件，每次拷贝一个字符
//		outFileName = "D:\\nio\\file\\io\\ioFileWritechar.txt";
//		myReadAndWriteChar(inFileName,outFileName);
//		
//		//使用FileReader - 和 FileWriter拷贝文件，每次拷贝一个指定长度的字符数组
//		myReadAndWriteChars(inFileName,"D:\\nio\\file\\io\\ioFileWritechars.txt");
//		
//		//使用 FileWriter将String字符串写到文件中
//		myWriteString("D:\\nio\\file\\io\\ioFileWriteString.txt","hi how are you! \n使用 FileWriter将string字符串写到文件中");
//		
//		//以行为单位读取文件,直到读入null为文件结束 
//		readWithBufferedReader(inFileName);
//		
//		//System.in是一个位流，为了转换为字符流，可使用InputStreamReader为其进行字符转换，然后再使用BufferedReader为其增加缓冲功能
//		//readAndWrite("D:\\nio\\file\\io\\BufferedWritFile.txt");
//		
//		//RandomAccessFile能在文件里面前后移动,随机读取文件内容 
//		readFileByRandomAccess("D:\\nio\\file\\io\\RandomAccessFile.txt");
	}


	/**
	 * @Description:使用jdk的io中的字节流（ FileInputStream 和 FileOutputStream）实现文件的拷贝功能
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。 
	 * @Title: readFileByBytes 
	 * @param inFile 原文件
	 * @param outFile  目标文件
	 * @throws
	 */
	public static void copyReadFileByBytes(String inFile, String outFile) {  
	    InputStream in = null;  
	    OutputStream out = null;  
	    Date startDate = new Date();
	    try {  
	        byte[] tempbytes = new byte[1024];  
	        int byteread = 0;  
	        in = new FileInputStream(inFile);  
	        out = new FileOutputStream(outFile);  
	        while ((byteread = in.read(tempbytes)) != -1) {  
	        	System.out.println("拷贝的内容："+tempbytes.toString());
	            out.write(tempbytes, 0, byteread);  
	        }  
	        Date endDate = new Date();
	        System.out.println("FileInputStream+FileOutputStream拷贝文件所需要的时间"+((endDate.getTime()-startDate.getTime())/1000)+"秒。");
	    } catch (Exception e1) {  
	        e1.printStackTrace();  
	    } finally {  
	        if (in != null) {  
	            try {  
	                in.close();  
	            } catch (IOException e1) {  
	            }  
	            try {  
	                out.close();  
	            } catch (IOException e1) {  
	            }  
	        }  
	    }  
	} 
	
	
 
	/**
	 * @Description:DataOutputStream的API测试函数 
	 * @Title: testDataOutputStream   
	 * @throws
	 	DataInputStream 是数据输入流，它继承于FilterInputStream。
		DataOutputStream 是数据输出流，它继承于FilterOutputStream。
		二者配合使用，“允许应用程序以与机器无关方式从底层输入流中读写基本 Java 数据类型”。
	 */
	private static void testDataOutputStream() {  
	    DataOutputStream out = null;  
	    try {  
	        File file = new File("D:\\nio\\file\\io\\DataOutputStream.txt");  
	        out = new DataOutputStream(new FileOutputStream(file));  
	          
	        out.writeBoolean(true);  
	        out.writeByte((byte)0x41);  
	        out.writeChar((char)0x4243);  
	        out.writeShort((short)0x4445);  
	        out.writeInt(0x12345678);  
	        out.writeLong(0x0FEDCBA987654321L);  
	        out.writeUTF("abcdefg");  
	    } catch (FileNotFoundException e) {  
	        e.printStackTrace();  
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        try {  
	            out.close();  
	        } catch(IOException e) {  
	        }  
	    }  
	}  
	  
	/** 
	 * 
	 */  
	/**
	 * @Description:DataInputStream的API测试函数 
	 * @Title: testDataInputStream   
	 * @throws
	 */
	private static void testDataInputStream() {
	    DataInputStream in = null;  
	    try {  
	        File file = new File("D:\\nio\\file\\io\\DataOutputStream.txt");  
	        in = new DataInputStream(new FileInputStream(file));  
	  
	        System.out.printf("byteToHexString(0x8F):0x%s\n", byteToHexString((byte)0x8F));  
//	        System.out.printf("charToHexString(0x8FCF):0x%s\n", charToHexString((char)0x8FCF));  
	        System.out.printf("readBoolean():%s\n", in.readBoolean());  
	        System.out.printf("readByte():0x%s\n", byteToHexString(in.readByte()));  
//	        System.out.printf("readChar():0x%s\n", charToHexString(in.readChar()));  
	        System.out.println("in.readChar():"+in.readChar());
//	        System.out.printf("readShort():0x%s\n", shortToHexString(in.readShort())); 
	        System.out.println("in.readShort():"+in.readShort());
	        System.out.printf("readInt():0x%s\n", Integer.toHexString(in.readInt()));  
	        System.out.printf("readLong():0x%s\n", Long.toHexString(in.readLong()));  
//	        System.out.printf("readUTF():%s\n", in.readUTF());  
	        System.out.println("in.readUTF():"+in.readUTF());
	    } catch (FileNotFoundException e) {  
	        e.printStackTrace();  
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        try {  
	            in.close();  
	        } catch(IOException e) {  
	        }  
	    }  
	} 
	
	/**
     * Convert byte[] to hex string
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
	
    public static String byteToHexString(byte src){
        StringBuilder stringBuilder = new StringBuilder("");
            int v = src & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        return stringBuilder.toString();
    }
    
    /**
     * @Description:使用BufferedInputStream和BufferedOutputStream拷贝文件
     * @Title: readAndWrite 
     * @param args  
     * @throws
	BufferedInputStream是带缓冲区的输入流，它继承于FilterInputStream。默认缓冲区大小是8M，能够减少访问磁盘的次数，提高文件读取性能。
	BufferedOutputStream是带缓冲区的输出流，它继承于FilterOutputStream，能够提高文件的写入效率。
	它们提供的“缓冲功能”本质上是通过一个内部缓冲区数组实现的。例如，在新建某输入流对应的BufferedInputStream后，当我们通过read()读取
	输入流的数据时，BufferedInputStream会将该输入流的数据分批的填入到缓冲区中。每当缓冲区中的数据被读完之后，输入流会再次填充数据缓冲区；
	如此反复，直到我们读完输入流数据。     
     */
    public static void readAndWrite(String fileInName,String fileOutName) {   
    	System.out.println("使用BufferedInputStream和BufferedOutputStream拷贝文件");
    	BufferedInputStream bis=null;
    	BufferedOutputStream bos = null;
        try {  
            bis = new BufferedInputStream(new FileInputStream(fileInName));  
            bos = new BufferedOutputStream(new FileOutputStream(fileOutName));  
            byte[] b=new byte[1024];
            int len=0;
            while(-1!= (len = bis.read(b, 0, b.length))) {  
                bos.write(b, 0, len);
            }   
      
        } catch (FileNotFoundException e) {  
            System.out.println("文件找不到");  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            if (null!= bos){  
                try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
            }  
            if (null!= bis){  
                try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
            }  
        }  
    }  
    
    /**
     * @Description:
     * @Title: readWithByteArray 
     * @param dataSource 内存中的字节数组
     * @return  
     * @throws
		ByteArrayInputStream 和 ByteArrayOutputStream该类从内存中的字节数组中读取数据，它的数据源是一个字节数组，它们分别继承自InputStream 和 OutputStream。
		ByteArrayInputStream类的构造方法包括： 
		ByteArrayInputStream(byte[] buf)--------参数buf指定字节数组类型的数据源。 
		ByteArrayInputStream(byte[] buf, int offset, int length)-----参数buf指定字节数组类型数据源，
			参数offset指定从数组中开始读取数据的起始下标位置，length指定从数组中读取的字节数。     
     */
    private static byte[] readWithByteArray(byte[] dataSource) {
    	System.out.println("从内存中的字节数组中读取数据，它的数据源是一个字节数组，它们分别继承自InputStream 和 OutputStream。");
        ByteArrayInputStream in = null;  
        ByteArrayOutputStream out = null;  
        try {  
            in = new ByteArrayInputStream(dataSource);  
            out = new ByteArrayOutputStream();  
            int len = 0;  
            byte[] buffer = new byte[1024];  
            while ((len = in.read(buffer, 0, buffer.length)) != -1){  
                out.write(buffer, 0, len);  
            }  
            return out.toByteArray();  
        } finally {  
            try {  
                in.close();  
            } catch (IOException e1) {  
            }  
            try {  
                out.close();  
            } catch (IOException e1) {  
            }  
        }  
    }  
    
    
    /**
     * @Description:以字符为单位读取文件，常用于读文本，数字等类型的文件 
     * @Title: readFileByChars 
     * @param fileName  待读取的文件路径
     * @throws
	InputStreamReader 和 OutputStreamWriter为各种输入输出字符流的基类，所有字符流都继承这两个基类。实际上，这两个类内部各自持有一个
	inputStream 和 outputStream对象，相当于是对inputStream 和 outputStream进行了包装，将输入字节流转换成字符流，便于读写操作。     
     */
    public static void readFileByChars(String fileName) {  
    	System.out.println("InputStreamReader 和 OutputStreamWriter为各种输入输出字符流的基类，所有字符流都继承这两个基类");
        File file = new File(fileName);  
        Reader reader = null;  
        try {  
            System.out.println("以字符为单位读取文件内容，一次读一个字节：");  
            //1. 一次读一个字符  
            reader = new InputStreamReader(new FileInputStream(file));//可以是任意的InputStream类，不一定必须是FileInputStream  
            int tempchar;  
            while ((tempchar = reader.read()) != -1) {  
                if (((char) tempchar) != '\r') {  
                    System.out.print((char) tempchar);  
                }  
            }  
            reader.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        try {  
            System.out.println("以字符为单位读取文件内容，一次读多个字节：");  
            //2. 一次读多个字符
            char[] tempchars = new char[30];  
            int charread = 0;  
            reader = new InputStreamReader(new FileInputStream(fileName));  
            while ((charread = reader.read(tempchars)) != -1) {  
                for (int i = 0; i < charread; i++) {  
                    if (tempchars[i] != '\r') {  
                        System.out.print(tempchars[i]);  
                    }  
                }  
            }  
      
        } catch (Exception e1) {  
            e1.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
    }
    
   /**
    * @Description:使用FileReader - 和 FileWriter拷贝文件，每次拷贝一个字符
    * @Title: myReadAndWrite 
    * @param readFileName
    * @param writeFileName  
    * @throws
    * FileReader 和 FileWriter分别继承自 inputStreamReader 和 outputStreamWriter。它是对读取文件操作系统的封装，所有的读写都是直接操作文件系统。
    * 因此如果是频繁读写操作，不建议使用FileReader 和 FileWriter，性能将会非常低，这时你需要使用BufferedReader。
    */
    public static void myReadAndWriteChar(String readFileName,String writeFileName) {
    	System.out.println("使用FileReader - 和 FileWriter拷贝文件，每次拷贝一个字符");
        FileReader fr = null;  
        FileWriter fw = null;  
        try {  
            fr = new FileReader(readFileName);  
            fw = new FileWriter(writeFileName);  
            //1.读一个字符，写一个字符方法  
            int ch = 0;    
            while ((ch = fr.read()) != -1) {    
                fw.write(ch);    
            }   
        } catch (Exception e) {  
            System.out.println(e.toString());  
        } finally {  
            if (fr != null)  
                try {  
                    fr.close();  
                } catch (Exception e2) {  
                    throw new RuntimeException("关闭失败！");  
                }  
            if (fw != null){  
                try {  
                    fw.close();  
                } catch (IOException e) {  
                    throw new RuntimeException("关闭失败！");  
                }  
            }  
        }  
    }  
    
    
    /**
     * @Description:使用FileReader - 和 FileWriter拷贝文件，每次拷贝一个指定长度的字符数组
     * @Title: myReadAndWrite 
     * @param readFileName
     * @param writeFileName  
     * @throws
     * FileReader 和 FileWriter分别继承自 inputStreamReader 和 outputStreamWriter。它是对读取文件操作系统的封装，所有的读写都是直接操作文件系统。
     * 因此如果是频繁读写操作，不建议使用FileReader 和 FileWriter，性能将会非常低，这时你需要使用BufferedReader。
     */
     public static void myReadAndWriteChars(String readFileName,String writeFileName) {  
    	 System.out.println("使用FileReader - 和 FileWriter拷贝文件，每次拷贝一个指定长度的字符数组");
         FileReader fr = null;  
         FileWriter fw = null;  
         try {  
             fr = new FileReader(readFileName);  
             fw = new FileWriter(writeFileName);  
             //2.读一个数组大小，写一个数组大小方法。  
             char []buf = new char[1024];  
             int len = 0;  
             while((len = fr.read(buf)) != -1){  
                 fw.write(buf, 0, len);                
             }  
         } catch (Exception e) {  
             System.out.println(e.toString());  
         } finally {  
             if (fr != null)  
                 try {  
                     fr.close();  
                 } catch (Exception e2) {  
                     throw new RuntimeException("关闭失败！");  
                 }  
             if (fw != null){  
                 try {  
                     fw.close();  
                 } catch (IOException e) {  
                     throw new RuntimeException("关闭失败！");  
                 }  
             }  
         }  
     } 
     

     /**
      * @Description:使用 FileWriter将string字符串写到文件中
      * @Title: myWriteString 
      * @param writeFileName 写入的文件的名称
      * @param context 写的String类型的内容
      * @throws
      * FileReader 和 FileWriter分别继承自 inputStreamReader 和 outputStreamWriter。它是对读取文件操作系统的封装，所有的读写都是直接操作文件系统。
      * 因此如果是频繁读写操作，不建议使用FileReader 和 FileWriter，性能将会非常低，这时你需要使用BufferedReader。
      */
      public static void myWriteString(String writeFileName,String context) {
    	  System.out.println("使用 FileWriter将string字符串写到文件中");
          FileWriter fw = null;  
          try {  
        	  fw = new FileWriter(writeFileName);  
              //3.直接写一个字符串  
              fw.write(context);
          } catch (Exception e) {  
              System.out.println(e.toString());  
          } finally {   
              if (fw != null){  
                  try {  
                      fw.close();  
                  } catch (IOException e) {  
                      throw new RuntimeException("关闭失败！");  
                  }  
              }  
          }  
      } 
      
      
      /**
       * @Description:以行为单位读取文件，常用于读面向行的格式化文件 
       * 为了能一次读取一行使用者的输入，使用了BufferedReader来对使用者输入的字符进行缓冲。
       * readLine()方法会在读取到使用者的换行字符时，再一次将整行字符串传入
       * @Title: readWithBufferedReader 
       * @param fileName 读取的文件名称
       * @throws
	   *BufferedReader和BufferedWriter类各拥有8192字符的缓冲区。
	   *当BufferedReader在读取文本文件时，会先尽量从文件中读入字符数据并置入缓冲区，而之后若使用read()方法，会先从缓冲区中进行读取。
	   *	如果缓冲区数据不足，才会再从文件中读取;
	   *使用BufferedWriter时，写入的数据并不会先输出到目的地，而是先存储至缓冲区中。如果缓冲区中的数据满了，才会一次对目的地进行写出。       
       */
      public static void readWithBufferedReader(String fileName) {
    	  System.out.println("以行为单位读取文件,直到读入null为文件结束 ");
          File file = new File(fileName);  
          BufferedReader reader = null;  
          try {  
              reader = new BufferedReader(new FileReader(file));  
              String tempString = null;  
              int line = 1;  
              // 一次读入一行，直到读入null为文件结束  
              while ((tempString = reader.readLine()) != null) {  
                  System.out.println("line " + line + ": " + tempString);  
                  line++;  
              }  
              reader.close();  
          } catch (IOException e) {  
              e.printStackTrace();  
          } finally {  
              if (reader != null) {  
                  try {  
                      reader.close();  
                  } catch (IOException e1) {  
                  }  
              }  
          }  
      }  
      
      /**?????????????????
       * @Description:System.in是一个位流，为了转换为字符流，可使用InputStreamReader为其进行字符转换，然后再使用BufferedReader为其增加缓冲功能
       * @Title: readAndWrite 
       * @param writeFileName 写入的文件名称
       * @throws
       */
      public static void readAndWrite(String writeFileName) {
    	  System.out.println("System.in是一个位流，为了转换为字符流，可使用InputStreamReader为其进行字符转换，然后再使用BufferedReader为其增加缓冲功能");
    	    try {  
    	        //缓冲System.in输入流  
    	        //System.in是位流，可以通过InputStreamReader将其转换为字符流  
    	        BufferedReader bufReader = new BufferedReader(new InputStreamReader(System.in));  
    	        //缓冲FileWriter  
    	        BufferedWriter bufWriter = new BufferedWriter(new FileWriter(writeFileName));  
    	  
    	        String input = null;  
    	        //每读一行进行一次写入动作  
    	        while((input = bufReader.readLine())!="close") { 
    	            bufWriter.write(input);  
    	            //newLine()方法写入与操作系统相依的换行字符，依执行环境当时的OS来决定该输出那种换行字符  
    	            bufWriter.newLine();  
    	        }  
    	        bufReader.close();  
    	        bufWriter.close();  
    	    } catch(ArrayIndexOutOfBoundsException e) {  
    	        System.out.println("没有指定文件");  
    	    } catch(IOException e) {  
    	        e.printStackTrace();  
    	    }  
    	} 
      

      /**
       * @Description:RandomAccessFile能在文件里面前后移动,随机读取文件内容 
       * @Title: readFileByRandomAccess 
       * @param fileName 操作的文件名称
       * @throws
       * RandomAccessFile的基本功能有：定位用的getFilePointer( )，在文件里移动用的seek( )，以及判断文件大小的length( )、
       * skipBytes()跳过多少字节数。此外，它的构造函数还要一个表示以只读方式("r")，还是以读写方式("rw")打开文件的参数。
       * 实际它和C的fopen()一模一样，都是直接对文件句柄操作。
       */
      public static void readFileByRandomAccess(String fileName) {
    	  System.out.println("RandomAccessFile能在文件里面前后移动,随机读取文件内容 ");
          RandomAccessFile randomFile = null;  
          try {  
              // 打开一个随机访问文件流，按只读方式  
              randomFile = new RandomAccessFile(fileName, "rw");  
              //判断文件大小
              long fileLength = randomFile.length();  
        
              // 设置读写文件的起始位置  ,在文件里移动
              randomFile.seek(0);  
        
              // 一次读取多个数据  
              byte[] bytes = new byte[10];  
              int byteread = 0;  
              while ((byteread = randomFile.read(bytes)) != -1) {  
                  System.out.write(bytes, 0, byteread);  
              }  
              //一次写入多个数据  
              randomFile.write(bytes);  
                
              // 一次读取单个数据  
              randomFile.read();  
              // 一次写入单个数据  
              randomFile.writeDouble(47.0001);    
        
          } catch (IOException e) {  
              e.printStackTrace();  
          } finally {  
              if (randomFile != null) {  
                  try {  
                      randomFile.close();  
                  } catch (IOException e1) {  
                  }  
              }  
          }  
      }  
      
      
      
      
      
      
      
      
	/*
	 int java.io.InputStream.read(byte[] b) throws IOException
	 从输入流中读取一些字节，并将它们存储到缓冲区数组b中。实际上，读取的字节数作为一个整数返回。这个方法会阻塞直到输入数据可用，检测到文件的结束，或者抛出异常。
	如果b的长度为0，则不读取字节，返回0;否则，就会尝试读取至少一个字节。如果没有字节可用，因为流位于文件的末尾，则返回值-1;否则，将至少读取一个字节并将其存储到b中。
	第一个字节读入元素b[0]，下一个字节为b[1]，以此类推。读取的字节数最多等于b的长度，让k为实际读取的字节数;这些字节将存储在b[0]到b[k-1]元素中，将元素b[k]通过b[b]。长度是1)不受影响。
	类InputStream的read(b)方法的效果与:read(b,0,b.length)
	参数:b -读取数据的缓冲区。返回:在缓冲区中读取的字节总数，或者是-1，如果没有更多的数据，因为已经到达了流的末尾。
	抛出:IOException——如果第一个字节不能以任何原因读取，如果输入流已经关闭，或者其他一些I/O错误发生，则不能读取文件的末尾。NullPointerException——如果b为空。
		
	void java.io.OutputStream.write(byte[] b, int off, int len) throws IOException
	从指定的字节数组中写入len字节，从偏移到这个输出流开始。写入(b, off, len)的一般契约是，数组b中的某些字节被写入到输出流中;元素b[off]是第一个字节，而b[off+ len1]是这个操作所写的最后一个字节。
	OutputStream的写方法调用在每个字节上写一个参数的写方法。鼓励子类重写此方法并提供更有效的实现。
	如果b为空，则抛出NullPointerException。
	如果是负数,或者len是负的,或关闭+ len大于数组的长度b,然后抛出IndexOutOfBoundsException。
	参数:b -数据。off——数据中的开始偏移量。len——写入的字节数。	
	*/
}
