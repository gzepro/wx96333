package com.gzepro.wx96333.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import com.google.gson.Gson;

public class WeixinAccident {

	private Long accidentid;
	private Integer state;
	private Integer accitype;
	private Date alarmtime;
	private String accidentcode;
	private String phone;
	private String alarmname;
	private String remark;
	private Date createtime;
	private Date updatetime;
	private Integer isdel;
	private String operaid;
	private String regcode;
	private String ident;
	private String longitude;
	private String latitude;
	private String location;
	private String detaillocation;
	private String operaname;
	public Long getAccidentid() {
		return accidentid;
	}
	public void setAccidentid(Long accidentid) {
		this.accidentid = accidentid;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getAccitype() {
		return accitype;
	}
	public void setAccitype(Integer accitype) {
		this.accitype = accitype;
	}
	public Date getAlarmtime() {
		return alarmtime;
	}
	public void setAlarmtime(Date alarmtime) {
		this.alarmtime = alarmtime;
	}
	public String getAccidentcode() {
		return accidentcode;
	}
	public void setAccidentcode(String accidentcode) {
		this.accidentcode = accidentcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAlarmname() {
		return alarmname;
	}
	public void setAlarmname(String alarmname) {
		this.alarmname = alarmname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	public String getOperaid() {
		return operaid;
	}
	public void setOperaid(String operaid) {
		this.operaid = operaid;
	}
	public String getRegcode() {
		return regcode;
	}
	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}
	public String getIdent() {
		return ident;
	}
	public void setIdent(String ident) {
		this.ident = ident;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDetaillocation() {
		return detaillocation;
	}
	public void setDetaillocation(String detaillocation) {
		this.detaillocation = detaillocation;
	}
	public String getOperaname() {
		return operaname;
	}
	public void setOperaname(String operaname) {
		this.operaname = operaname;
	}

	public WeixinAccident getUploadInfo(JSONObject params) {
		WeixinAccident wxInfo = new WeixinAccident();
		if (!params.isNull("operaid")) {
			wxInfo.setOperaid(params.getString("operaid"));
		}
		
		if (!params.isNull("phone")) {
			wxInfo.setPhone(params.getString("phone"));
		}
		if (!params.isNull("regcode")) {
			wxInfo.setRegcode(params.getString("regcode"));
		}
		if (!params.isNull("ident")) {
			wxInfo.setIdent(params.getString("ident"));
		}
		if (!params.isNull("accitype")) {
			wxInfo.setAccitype(Integer.parseInt(params.getString("accitype")));
		}
		if (!params.isNull("longitude")) {
			wxInfo.setLongitude(params.getString("longitude"));
		}
		if (!params.isNull("latitude")) {
			wxInfo.setLatitude(params.getString("latitude"));
		}
		if (!params.isNull("accidentcode")) {
			wxInfo.setAccidentcode(params.getString("accidentcode"));
		}
		if (!params.isNull("alarmtime")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
					 java.util.Date nd = null;;
					try {
						nd = dateFormat.parse(params.getString("alarmtime"));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			wxInfo.setAlarmtime(nd);
		}
		if (!params.isNull("state")) {
			wxInfo.setState(Integer.parseInt(params.getString("state")));
		}
		if (!params.isNull("location")) {
			wxInfo.setLocation(params.getString("location"));
		}
		if (!params.isNull("detaillocation")) {
			wxInfo.setDetaillocation(params.getString("detaillocation"));
		}
		if (!params.isNull("operaname")) {
			wxInfo.setOperaname(params.getString("operaname"));
		}

		return wxInfo;
	}
}
