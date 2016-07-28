package com.gzepro.wx96333.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TemplateMessage {


	private Date createtime;
	
	private String touser;
	
	private String template_id;
	
	private String url;


	private String topcolor = "#FF0000";

	private String postdata;


	private Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
	

	private String defaultColor = "#173177";

	public String getPostdata() {
		return postdata;
	}

	public void setPostdata(String postdata) {
		this.postdata = postdata;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public Map<String, Map<String, String>> getData() {
		return data;
	}

	public void setData(Map<String, Map<String, String>> data) {
		this.data = data;
	}

	public String getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(String defaultColor) {
		this.defaultColor = defaultColor;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}
	
	public void push(String name, String value) {
		push(name, value, defaultColor);
	}
	
	public void push(String name, String value, String color) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("value", value);
		map.put("color", color);
		this.data.put(name, map);
	}
}
