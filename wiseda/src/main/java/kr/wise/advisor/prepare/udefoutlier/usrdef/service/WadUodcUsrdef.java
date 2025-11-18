package kr.wise.advisor.prepare.udefoutlier.usrdef.service;

import kr.wise.advisor.prepare.udefoutlier.service.WadUod;

public class WadUodcUsrdef extends WadUod {
	private String udfOtlDtcId;
	private String creCompId;
	private String userDefScrtConts;
	
	public String getUdfOtlDtcId() {
		return udfOtlDtcId;
	}
	public void setUdfOtlDtcId(String udfOtlDtcId) {
		this.udfOtlDtcId = udfOtlDtcId;
	}
	public String getCreCompId() {
		return creCompId;
	}
	public void setCreCompId(String creCompId) {
		this.creCompId = creCompId;
	}
	public String getUserDefScrtConts() {
		return userDefScrtConts;
	}
	public void setUserDefScrtConts(String userDefScrtConts) {
		this.userDefScrtConts = userDefScrtConts;
	}
}
