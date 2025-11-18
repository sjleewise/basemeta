package kr.wise.meta.app.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WamAppPrgm extends CommonVo{
    private String appPrgmId;

	private String appPrgmLnm;

	private String appPrgmPnm;

	private String appPrgmDcd;

	public String getAppPrgmId() {
		return appPrgmId;
	}

	public void setAppPrgmId(String appPrgmId) {
		this.appPrgmId = appPrgmId;
	}

	public String getAppPrgmLnm() {
		return appPrgmLnm;
	}

	public void setAppPrgmLnm(String appPrgmLnm) {
		this.appPrgmLnm = appPrgmLnm;
	}

	public String getAppPrgmPnm() {
		return appPrgmPnm;
	}

	public void setAppPrgmPnm(String appPrgmPnm) {
		this.appPrgmPnm = appPrgmPnm;
	}

	public String getAppPrgmDcd() {
		return appPrgmDcd;
	}

	public void setAppPrgmDcd(String appPrgmDcd) {
		this.appPrgmDcd = appPrgmDcd;
	}

	@Override
	public String toString() {
		return "WamAppPrgm [appPrgmId=" + appPrgmId + ", appPrgmLnm=" + appPrgmLnm + ", appPrgmPnm=" + appPrgmPnm
				+ ", appPrgmDcd=" + appPrgmDcd + "]";
	}
	
	
}