package com.itmayiedu.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itmayiedu.ds_fpq.service.token.MyTokenService;

@ResponseBody
public class TockenController {

	private static Logger logger = Logger.getLogger(TockenController.class);
	
	@Autowired
	private MyTokenService myToken;
	
	/**
	 * createToken:生产token. <br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * http://127.0.0.1:8888/createToken?account=fupinxin
	 * @author xn042142 付品欣
	 * @param account
	 * @return 
	 * @since JDK 1.8
	 * 2017年12月22日 下午2:09:40
	 */
//	@RequestBody
	@RequestMapping("/createToken")
	public String createToken(String account){
		logger.info("开始创建token......");
		String token = myToken.createTKN(account);
		logger.info("token创建成果，token=" + token);
		return token;
	}
	
	/**
	 * checkToken:校验token判断用户是否登入<br/> 
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	 * http://127.0.0.1:8888/checkToken?account=fupinxin
	 * @author xn042142 付品欣
	 * @param account
	 * @return 
	 * @since JDK 1.8
	 * 2017年12月22日 下午2:10:45
	 */
	@RequestMapping("/checkToken")
	public String checkToken(String account){
		logger.info("开始校验token，account = " + account + ",......");
		String tokenValue = "根据渠道编号生产tokenid";
		String result = myToken.checkTKN(account, tokenValue);
		logger.info("校验结果，account = " + account + "," + result);
		return result;
	}
	
}
