package com.gzepro.wx96333.service;

import javax.servlet.http.HttpServletRequest;

public interface IndexService {

	/**
	 * 提交报警信息
	 * @param request
	 * @param mobile
	 * @param zcode
	 * @param ztype
	 * @return
	 * @throws Exception
	 */
	public String postBJ(HttpServletRequest request,String openid,String mobile,String zcode,String ztype) throws Exception;
	
	
	/**救人进度
	 * @param request
	 * @param mobile
	 * @param zcode
	 * @param ztype
	 * @return
	 * @throws Exception
	 */
	public String postBJResult(HttpServletRequest request,String openid,String mobile,String zcode,String ztype) throws Exception;
	
	
}
