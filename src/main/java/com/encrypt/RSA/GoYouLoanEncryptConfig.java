package com.encrypt.RSA;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class GoYouLoanEncryptConfig {
	
	private static Logger logger = Logger.getLogger(GoYouLoanEncryptConfig.class);

    public static String getValue(String key) {
    	Properties prop = new Properties();
    	String dirPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    	File file = new File("D:\\jvm\\jvmWorkSpase\\springboot-jvm\\src\\main\\resources\\","goYouLoanEncryptConfig.properties");  
    	InputStream in  = null;
    	try {
    		in = new FileInputStream(file);
			prop.load(in);
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			try {
				if(in!=null){
					in.close();
				}
			} catch (IOException e) {
				logger.info(e.getMessage());
			}
		}
        return prop.getProperty(key);
    }

 
}

