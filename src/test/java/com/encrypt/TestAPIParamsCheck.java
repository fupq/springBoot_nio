package com.encrypt;

import static org.junit.Assert.*;

import org.junit.Test;

import com.encrypt.APIParamsSign.APIServer;
import com.encrypt.APIParamsSign.TreeMapOrder;

import net.sf.json.JSONObject;

public class TestAPIParamsCheck {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void testJson(){
		JSONObject json = new JSONObject();
		json.put("id","1");
		json.put("name","张三");
		json.put("pwd","123456");
		System.out.println(json);
	}
	
	/*@Test
	public void testTreeMap(){
		TreeMapOrder treeMapOrder = new TreeMapOrder();
		int result = treeMapOrder.compare((Object)"name", (Object)"age");
		System.out.println(result);
	}*/
	
	@Test
	public void testcheckParams(){
		APIServer apiServer = new APIServer();
//		apiServer.checkParams("test");
	}
}
