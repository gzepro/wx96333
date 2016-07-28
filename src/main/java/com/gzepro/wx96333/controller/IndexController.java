package com.gzepro.wx96333.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.weixin.model.OpenidInfo;
import org.ehais.weixin.utils.WeiXinUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.qos.logback.core.subst.Token.Type;

import com.google.gson.Gson;
import com.gzepro.wx96333.common.ResourceUtil;
import com.gzepro.wx96333.model.WeixinAccident;
import com.gzepro.wx96333.service.IndexService;


@Controller
@RequestMapping("/")
public class IndexController {

	String redircet = "redirect:/welcome";//进入长按关注界面
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	public static String weixin_appid = ResourceUtil.getProValue("weixin_appid");
	public static String weixin_appsecret = ResourceUtil.getProValue("weixin_appsecret");
	
	@Autowired
	private IndexService indexService;
	
	@RequestMapping("/index")
	public String go_index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		String REDIRECT_URI = request.getRequestURL().toString().replace("/index", "/wx-index");
		String url = WeiXinUtil.authorize_snsapi(weixin_appid, "snsapi_base", REDIRECT_URI);
		return "redirect:"+url;
	}
	
	
	@RequestMapping("/wx-index")
	public String wx_index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = true) String code) {
		try {
			//OpenidInfo openidInfo = WeiXinUtil.getOpenid(code, weixin_appid, weixin_appsecret);
			//if(openidInfo.getOpenid() == null || openidInfo.getOpenid().equals("")){
			//	return redircet;
			//}
			
			//以session保存openid
			//request.getSession().setAttribute("openid", openidInfo.getOpenid());
			request.getSession().setAttribute("openid",code);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/web/index";
	}
	
	@RequestMapping("/welcome")
	public String welcome(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		return "/web/welcome";
	}
	
	/**
	 * 提交报警事件
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param mobile 手机号码
	 * @param zcode 电梯码
	 * @param ztype 报警类类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/wx-postBJ")
	public String postBJ(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "zcode", required = true) String zcode,
			@RequestParam(value = "ztype", required = true) String ztype) {
		try {
			String openid = (String) request.getSession().getAttribute("openid");
			
			return indexService.postBJ(request,openid, mobile, zcode, ztype);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{'code':0,'msg':'error'}";
	}
	
	
	
	/**
	 * 96333 post 过来的救人进度信息
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param openid 微信唯一号
	 * @param mobile 手机号
	 * @param zcode 电梯码
	 * @param ztype 类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/postBJResult")
	public String postBJResult(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "param", required = true) String param) {
		try {
			//System.out.println("param"+param);
			if (param != null&&!"".equals(param)) {
				/*Map<String, String> params_map = new HashMap<String, String>();
				 java.lang.reflect.Type type = new TypeToken<Map<String, String>>(){}.getType();
				 Gson gson = new Gson();
				 String json = gson.toJson(params_map);
				 Map<String, String> map = gson.fromJson(json, type);*/
				JSONObject json = new JSONObject(param);
				JSONObject params = (JSONObject) json.get("params");
				 if(params!=null){
					 //System.out.println(params);
					 WeixinAccident wa = new WeixinAccident();
					 wa = wa.getUploadInfo(params);
					 //System.out.println(wa.getPhone());
					 String recode = indexService.postBJResult(request,wa.getOperaid(), wa.getPhone(), wa.getRegcode(), wa.getAccitype().toString());
					 if(recode.equals("ok")){
						 return "{'code':'1','msg':'true'}";
					 }else{
						 return "{'code':'0','msg':'error'}";
					 }
				 }
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{'code':0,'msg':'error'}";
	}
	
	
	@RequestMapping("/fuckyou_test")
	public String fuckyou_test(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		request.getSession().setAttribute("openid", "oT2uMs0xoSx5IFV75_vLEbSQfEEQ");
		
		return "/web/welcome";
	}
	
}
