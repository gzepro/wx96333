package com.gzepro.wx96333.common;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.JsApiTicket;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 公众平台通用接口工具类
 * 
 * @author liuyq
 * @date 2013-08-09
 */
public class WeixinUtils {
	
	public static String weixin_appid = ResourceUtil.getProValue("weixin_appid");
	public static String weixin_appsecret = ResourceUtil.getProValue("weixin_appsecret");

	
	private static Map<String, Object> mapWeiXin = new HashMap<String, Object>();
			
	
	private static Logger log = LoggerFactory.getLogger(WeixinUtils.class);
	
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	//组成可以获取授权的链接
	public static String authorize = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	//获取opendid的接口地址
	public static String get_opendid_url = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";
	//获取用户信息，并判断是否关注此微信号
	public static String get_user_info = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	//获取ip
	public static String get_call_back_ip = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
	//获取getJsApiTicket
	public static String get_jsapi_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=ACCESS_TOKEN";
	//统一下单
	public static String unifiedorder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	//退款申请
	public static String refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	//退款查询
	public static String refund_query_url = "https://api.mch.weixin.qq.com/pay/refundquery";
	
	//发送红包
	private static String send_red_pack = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	//上传媒体
	private static String upload_mediaId = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	//上传图文消息内的图片获取URL
	private static String upload_media = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
	//上传图文消息素材
	private static String upload_fodder_url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	//下载媒体
	private static String download_media = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	// 获取组
	private static String getGroups = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	//分组群发媒体信息
	private static String sendMsg = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	//根据OpenID列表群发
	private static String sendAll = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
	//模板信息
	public static String send_muban_msg_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	//获取获取永久素材
	public static String get_material_list ="https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	// 删除永久素材
	public static String remove_material_media_id="https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";

	
	/**
	 * 获取access_token
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static AccessToken getAccessToken() throws Exception {
		AccessToken accessToken = (AccessToken)mapWeiXin.get("accessToken");
		if(accessToken!=null){
			if(System.currentTimeMillis() < accessToken.getExpire_time()){
				System.out.println(accessToken.getToken());
				return accessToken;
			}
				
		}

		String requestUrl = access_token_url.replace("APPID", weixin_appid).replace("APPSECRET", weixin_appsecret);
		System.out.println("AccessToken:"+requestUrl);
		
		String request = EHttpClientUtil.methodGet(requestUrl);
		JSONObject jsonObject = new JSONObject(request);
		System.out.println("AccessToken:"+jsonObject.toString());
		if (null != jsonObject) {
			accessToken = new AccessToken();
			accessToken.setToken(jsonObject.getString("access_token"));
			accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			accessToken.setExpire_time(System.currentTimeMillis() + jsonObject.getInt("expires_in") * 1000);//毫秒数
			mapWeiXin.put("accessToken", accessToken);//保存内存中，不需要经常读接口
		}
		
		return accessToken;
	}
	
	/**
	 * 获取access_token
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static JsApiTicket getJsApiTicket(String access_token) throws Exception {
		JsApiTicket jsApiTicket = (JsApiTicket)mapWeiXin.get("jsApiTicket");
		if(jsApiTicket!=null){
			if(System.currentTimeMillis() < jsApiTicket.getExpire_time()){
				System.out.println(jsApiTicket.getTicket());
				return jsApiTicket;
			}
				
		}
		String requestUrl = get_jsapi_url.replace("ACCESS_TOKEN", access_token);
		System.out.println("JsApiTicket:"+requestUrl);
//		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		String request = EHttpClientUtil.methodGet(requestUrl);
		JSONObject jsonObject = new JSONObject(request);
		
		System.out.println("JsApiTicket:"+request);
		// 如果请求成功
		if (null != jsonObject) {
			jsApiTicket = new JsApiTicket();
			jsApiTicket.setTicket(jsonObject.getString("ticket"));
			jsApiTicket.setErrcode(jsonObject.getInt("errcode"));
			jsApiTicket.setErrmsg(jsonObject.getString("errmsg"));
			jsApiTicket.setExpires_in(jsonObject.getInt("expires_in"));
			jsApiTicket.setExpire_time(System.currentTimeMillis() + jsonObject.getInt("expires_in") * 1000);
			mapWeiXin.put("jsApiTicket", jsApiTicket);//保存内存中，不需要经常读接口
			
		}
		
		
		return jsApiTicket;
	}
	
	/**
	 * 生成获取用户openid的地址
	 * @param REDIRECT_URI
	 * @return
	 */
	public static String authorize_snsapi(String SCOPE,String REDIRECT_URI){
		String url = authorize.replace("APPID", weixin_appid).replace("SCOPE", SCOPE).replace("REDIRECT_URI", REDIRECT_URI);
		
		
		return url ;
	}
	
/*	public static OpenidInfo getOpenid(String code) throws Exception {
		//code从这里来https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9439cbf94f9235f0&redirect_uri=http://www.gz96833.com/test.jsp&response_type=code&scope=snsapi_base&state=123#wechat_redirect
		OpenidInfo info = new OpenidInfo();
		if(StringUtil.NullOrEqual(weixin_appsecret) || StringUtil.NullOrEqual(weixin_appid) || 
				StringUtil.NullOrEqual(code) ){
			return null;
		}
		String requestUrl = get_opendid_url + "&appid=" + weixin_appid + "&secret=" + weixin_appsecret + "&code=" +code;
		log.info("请求openid:"+requestUrl);
		String request = EHttpClientUtil.methodGet(requestUrl);
		log.info("获取openid"+request);
		JSONObject jsonObject = new JSONObject(request);
		if(null != jsonObject){
			try {
				
				info.setOpenid(jsonObject.getString("openid"));
				info.setExpires_in(jsonObject.getInt("expires_in"));
			} catch (Exception e) {
				// TODO: handle exception
				// 获取token失败
				log.error("获取openid失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		
		return info;
	}
	*/
/*	public static UserInfo getUserInfo(String access_token,String openid){
		UserInfo userinfo = new UserInfo();
		String url = get_user_info.replace("ACCESS_TOKEN", access_token)
				.replace("OPENID", openid);
		try {
			String request = HttpClientUtil.methodGet(url);
			log.info("get user by openid"+request);
			JSONObject jsonObject = new JSONObject(request);
			if(null != jsonObject){
				
				if(jsonObject.has("subscribe"))userinfo.setSubscribe(jsonObject.getInt("subscribe"));
				if(jsonObject.has("openid"))userinfo.setOpenid(jsonObject.getString("openid"));
				
				if(jsonObject.has("sex"))userinfo.setSex(jsonObject.getInt("sex"));
				if(jsonObject.has("city"))userinfo.setCity(jsonObject.getString("city"));
				if(jsonObject.has("country"))userinfo.setCountry(jsonObject.getString("country"));
				if(jsonObject.has("province"))userinfo.setProvince(jsonObject.getString("province"));
				if(jsonObject.has("language"))userinfo.setLanguage(jsonObject.getString("language"));
				if(jsonObject.has("headimgurl"))userinfo.setHeadimgurl(jsonObject.getString("headimgurl"));
				if(jsonObject.has("subscribe_time"))userinfo.setSubscribe_time(jsonObject.getLong("subscribe_time"));
				if(jsonObject.has("unionid"))userinfo.setUnionid(jsonObject.getString("unionid"));
				if(jsonObject.has("remark"))userinfo.setRemark(jsonObject.getString("remark"));
				if(jsonObject.has("groupid"))userinfo.setGroupid(jsonObject.getInt("groupid"));
				
				//再单独处理
				try{
					if(jsonObject.has("nickname"))userinfo.setNickname(jsonObject.getString("nickname"));
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error("解析昵称出错", e);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("解析用户信息出错", e);
		}
		
		return userinfo;
	}*/

	public static List<String> getCallBackIP(String access_token){
		List<String> list = new ArrayList<String>();

		String url = get_call_back_ip.replace("ACCESS_TOKEN", access_token);
		try {
			String request = EHttpClientUtil.methodGet(url);
			log.info("get call back ip"+request);
			JSONObject jsonObject = new JSONObject(request);
			if(null != jsonObject){
				JSONArray arr = jsonObject.getJSONArray("ip_list");
				for (int i = 0 ; i < arr.length() ; i++) {
					list.add(arr.getString(i));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	



	
	public static void downloadMedia(String access_token,String media_id,String filename) throws Exception{
		String url = download_media.replace("ACCESS_TOKEN", access_token)
				.replace("MEDIA_ID", media_id);
		System.out.println("weixin download media:"+url);
		
		String nu = EHttpClientUtil.downloadFile(url, filename);
		System.out.println("下载文件返回null:"+nu);
	}
	
	public static void downloadMediaTest(String filename) throws Exception{
		
		String nu = EHttpClientUtil.downloadFile(
				"http://c.csdnimg.cn/comm_ask/css/../images/common_float_block.png",
				filename);
		System.out.println("下载文件返回null:"+nu);
	}
	
/*	*//**
	 * 微信上传素材
	 * @param access_token
	 * @param filename 素材路径
	 * @return
	 * @throws Exception
	 *//*
	public static String getUpload(String access_token, String filename, String type) throws Exception {
		String url = upload_mediaId.replace("ACCESS_TOKEN", access_token).replace("TYPE", type);;
		String str = EHttpClientUtil.methodPostFile(url, filename);
		return str;
	}*/


	/**
	 * 获取上传图文消息素材
	 * @param token 
	 * @param result 参数
	 * @return
	 * @throws Exception 
	 */
	public static String getUploadFodder(String token, String result) throws Exception {
		String url = upload_fodder_url.replace("ACCESS_TOKEN", token);
		String str = EHttpClientUtil.httpPostEntity(url,result);
		String media_id = null;
		if (str != null) {
			JSONObject jsonObject = new JSONObject(str);
			media_id = jsonObject.getString("media_id");
		}

		return media_id;
	}


	/**
	 * 群发
	 * @param token
	 * @param string
	 * @return
	 * @throws Exception 
	 */
	public static String sendMsg(String token, String string) throws Exception {
		String url = sendMsg.replace("ACCESS_TOKEN", token);
		String str = EHttpClientUtil.httpPostEntity(url,string);
		String errcode = null;
		if (str != null) {
			JSONObject jsonObject = new JSONObject(str);
			errcode = jsonObject.getString("errcode");
		}

		return errcode;
	}
	
	/**
	 * 根据openID 列表来发送
	 * @param token
	 * @param string
	 * @return
	 * @throws Exception 
	 */
	public static String sendAll(String token, String string) throws Exception {
		String url = sendAll.replace("ACCESS_TOKEN", token);
		String str = EHttpClientUtil.httpPostEntity(url,string);
		String errcode = null;
		if (str != null) {
			JSONObject jsonObject = new JSONObject(str);
			errcode = jsonObject.getString("errcode");
		}

		return errcode;
	}





	/**
	 * 模板发送信息
	 * @param token
	 * @param resultData
	 * @return
	 * @throws Exception 
	 */
	public static String sendMuBanMsg(String resultData) throws Exception {
		AccessToken accessToken = getAccessToken();
		String url = send_muban_msg_url.replace("ACCESS_TOKEN", accessToken.getToken());
		String str = EHttpClientUtil.httpPostEntity(url, resultData);
		String errcode = null;
		if (str != null) {
			JSONObject jsonObject = new JSONObject(str);
			errcode = jsonObject.getString("errmsg");
		}

		return errcode;
	}
	
	/**
	 * 
	 * @描述：获取永久素材列表
	 * @作者：HuangPin
	 * @日期：2016年7月14日上午11:31:31
	 * @param resultData
	 * @return
	 * @throws Exception
	 */
	public static String get_material_list(String resultData) throws Exception{
		AccessToken accessToken = getAccessToken();
		String url = get_material_list.replace("ACCESS_TOKEN", accessToken.getToken());
		String result = EHttpClientUtil.httpPostEntity(url, resultData);
		return result;
	}
	/**
	 * 
	 * @描述：删除永久素材
	 * @作者：HuangPin
	 * @日期：2016年7月14日上午11:32:07
	 * @param resultData
	 * @return
	 * @throws Exception
	 */
	public static String remove_material(String resultData) throws Exception{
		AccessToken accessToken = getAccessToken();
		String url = remove_material_media_id.replace("ACCESS_TOKEN", accessToken.getToken());
		String result = EHttpClientUtil.httpPostEntity(url, resultData);
		return result;
	}
	
}