package com.itmayiedu.app;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	private static Logger logger = Logger.getLogger(ScheduledTasks.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	/**
	 * add:(使用@Scheduled进行调度). <br/> 
	 * TODO(缺点：1.不够安全，部能集群化处理；2.若出错了一直报错；).<br/> 
	 * 
	 * @author xn042142 付品欣 
	 * @since JDK 1.8
	 * 2017年12月8日 下午12:33:28
	 */
	@Scheduled(fixedRate = 6000000)//1分钟执行一次
	public void add(){
		logger.info("使用@Scheduled调度正在执行，已过去 1 分钟时间！" );//+ System.currentTimeMillis()/1000
	}
	
	/**
	 * reportCurrentTime:(调度：频率：5秒). <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * 
	 * @author fpq 付品欣 
	 * @since JDK 1.8
	 * 2017年12月8日 下午11:54:45
	 */
    @Scheduled(fixedRate = 500000)
    public void reportCurrentTime() {
        System.out.println("现在时间：" + dateFormat.format(new Date()));
    }

}
