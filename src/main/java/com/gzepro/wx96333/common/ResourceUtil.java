package com.gzepro.wx96333.common;

import java.util.ResourceBundle;

public class ResourceUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config/config");

	private void ResourceUtil() {
	}


	/**
	 * 获得某个属性值
	 * 
	 * @return
	 */
	public static final String getProValue(String name) {
		return bundle.getString(name);
	}
	
}
