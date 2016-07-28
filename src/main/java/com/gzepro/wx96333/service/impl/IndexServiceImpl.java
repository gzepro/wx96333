package com.gzepro.wx96333.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.gzepro.wx96333.common.EHttpClientUtil;
import com.gzepro.wx96333.common.ResourceUtil;
import com.gzepro.wx96333.common.TemplateMessage;
import com.gzepro.wx96333.common.WeixinUtils;
import com.gzepro.wx96333.service.IndexService;

@Service("indexService")
public class IndexServiceImpl implements IndexService{
	
	public static String weixin_appid = ResourceUtil.getProValue("weixin_appid");
	public static String weixin_appsecret = ResourceUtil.getProValue("weixin_appsecret");
	public static String weixin_template_id = ResourceUtil.getProValue("weixin_template_id");
	public static String wbupload_url = ResourceUtil.getProValue("wbupload_url");

	@Override
	public String postBJ(HttpServletRequest request,String openid, String mobile, String zcode, String ztype) throws Exception {
		// TODO Auto-generated method stub
		
		
		//在这里把这些数据传给96333平台接口
		String web_url = wbupload_url;
        JSONObject json = new JSONObject();
        JSONObject json2 = new JSONObject();
        Map<String, String> map3 = new HashMap<String, String>();
        
        json.put("operaid", openid);
        String accitype = "";
        if("电梯故障".equals(ztype)){
        	accitype = "0";
        }else if("困人".equals(ztype)){
        	accitype = "1";
        }else if("伤亡".equals(ztype)){
        	accitype = "2";
        }else{
        	accitype = "3";
        }
        json.put("accitype", accitype);
        if(zcode.length() <= 6){
        	json.put("ident", zcode);
        }else{
        	json.put("ident", "");
        }
        
        json.put("phone", mobile);
        json.put("regcode", zcode);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
        String createtime = dateFormat.format(new java.util.Date());
        json.put("createtime", createtime);
        json.put("longitude", "");
        json.put("latitude", "");
        
        json2.put("params", json);
        json2.put("type", "2");
        map3.put("param", json2.toString());
        
       
        String result = "";
        System.out.println(map3.toString());
        result = EHttpClientUtil.httpPost(web_url , map3);
        System.out.println(result);

		return result;
	}
	

	@Override
	public String postBJResult(HttpServletRequest request,String openid, String mobile, String zcode, String ztype) throws Exception {
		// TODO Auto-generated method stub
		
		//把数据调用黄品的jar的函数
		TemplateMessage message = new TemplateMessage();
		message.setTouser(openid);
		//oT2uMs0xoSx5IFV75_vLEbSQfEEQ    wo
		//oT2uMs6Hd6VM70zo0CTEXk3HbheI
		message.setTemplate_id(weixin_template_id);
		message.push("first", mobile);
		message.push("keyword1", zcode);
		message.push("keyword2", ztype);
		message.push("remark", "谢谢");
		
		Gson son = new Gson();
		
		String code = WeixinUtils.sendMuBanMsg(son.toJson(message));
		//System.out.println("code:::"+code);
		return code;
	}
	
	/*public static void main(String[] args) throws Exception {
		TemplateMessage message = new TemplateMessage();
		message.setTouser("oT2uMs6Hd6VM70zo0CTEXk3HbheI");
		//oT2uMs0xoSx5IFV75_vLEbSQfEEQ    wo
		//oT2uMs6Hd6VM70zo0CTEXk3HbheI
		message.setTemplate_id("aKI-EZtEKv-m5byFe5rcDeY7D6kW1xjhpKI5vtz4lTg");
		message.push("first", "欢迎");
		message.push("keyword1", "哦哦好好好");
		message.push("keyword2", "结婚后");
		message.push("remark", "谢谢");
		
		Gson son = new Gson();
		
		
		String code = WeixinUtils.sendMuBanMsg(son.toJson(message));

		System.out.println(code);

	}
*/
}
