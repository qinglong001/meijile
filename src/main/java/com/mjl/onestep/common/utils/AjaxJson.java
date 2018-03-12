package com.mjl.onestep.common.utils;

import java.util.LinkedHashMap;



/**
 * $.ajax后需要接受的JSON
 * 
 * @author fcj
 * 
 */
public class AjaxJson {
	/**
	 * 是否成功
	 */
	private boolean success = true;
	/**
	 * 错误代码
	 */
	private String code = "200";
	/**
	 * 提示信息
	 */
	private String msg = "操作成功";
	/**
	 * 封装json的map
	 */
	private LinkedHashMap<String, Object> body = new LinkedHashMap<>();
	
	public LinkedHashMap<String, Object> getBody() {
		return body;
	}

	public void setBody(LinkedHashMap<String, Object> body) {
		this.body = body;
	}

	public void put(String key, Object value){//向json中添加属性，在js中访问，请调用data.map.key
		body.put(key, value);
	}
	
	public void remove(String key){
		body.remove(key);
	}
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {//向json中添加属性，在js中访问，请调用data.msg
		this.msg = msg;
	}


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 返回错误信息
	 * @param msg
	 * @param code
	 */
	public void errorMsg(String msg,String code){
		this.success=false;
		this.code=code;
		this.msg=msg;
	}
	/**
	 * 返回正确信息
	 * @param msg
	 */
	public void successMsg(String msg){
		this.success=true;
		this.code="200";
		this.msg=msg;
	}
}
