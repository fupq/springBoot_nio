package com.itmayiedu.ds_fpq.service.token;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class MyTokenService {
	private ArrayList myList = new ArrayList();

	public String createTKN(String account){//String pin,String imei
//        SaveToDb st = new SaveToDb();
        // 创建 GUID 对象
//         UUID uuid = UUID.randomUUID();
         String uuid = "根据渠道编号生产tokenid";
         // 得到对象产生的ID
         String token = uuid.toString();
         // 转换为大写
         token = token.toUpperCase();
         // 替换 “-”变成空格
         token = token.replaceAll("-", "");
         System.out.println(token);
         //向数据库中保存token及用户信息
         Map<String , Object> params = new HashMap<>();
         params.put("st_sid", token);
         params.put("st_account", account);
//         String tableName = "表名";
         //此处用的是mybaits的方法来保存数据到数据库，具体可看http://blog.csdn.net/suresand/article/details/52540684
//         st.save(tableName , params);
         myList.add(params);
         //保存后下次就可以通过用户的token在数据库来做个验证
         return "token";
   }
	
	/**
     * 验证用户的token值是否正确
     * @param account
     * @param tokenValue
     * @return 正确--1，错误--0
     */
    public  String checkTKN(String account, String tokenValue){
//          SaveToDb st = new SaveToDb();
          Map<String, Object> params=new HashMap<>();
          //在数据库中查询该用户的token是否存在
          params.put("st_sid", tokenValue);
          String tableName = "T_HY_LOGINTKN_I";
          //用的是一个list集合来接受map集合，所以后面需要转换出来
          List rs = new ArrayList<>();
          //同样用的mybaits的查询方法
//          rs = st.select(tableName , params);
          Map<String , Object> myparams = new HashMap<>();
          for(int i=0;i<myList.size();i++){
        	  myparams = (Map<String, Object>) myList.get(i);
        	  if(account.equals(myparams.get("st_account").toString())){
        		  return "1：该用户已经登入了";
        	  }
          }
          return "0:该用户未登入";
//          Map<String, Object> map=new HashMap<>();
//          map = (Map<String, Object>) rs.get(0);
          //检查该token是否属于该用户
//          if(account.equals( map.get("ST_ACCOUNT").toString())){
//              return "1";
//          }else{
//              return "0";
//          }
    }
}
