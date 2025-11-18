package kr.wise.advisor.prepare.udefoutlier.datacleansing.service;

import kr.wise.advisor.prepare.udefoutlier.service.WadUod;

public class WadUodcDataCln extends WadUod {
	private String udfOtlDtcId;
	private String creCompId;
	private String resPrcDcd;
	private String anaDaseId;
	private String srcAnaDaseId;
	private String srcAnaDaseNm;
	private String anaDaseNm;
	
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
	public String getResPrcDcd() {
		return resPrcDcd;
	}
	public void setResPrcDcd(String resPrcDcd) {
		this.resPrcDcd = resPrcDcd;
	}
	public String getAnaDaseId() {
		return anaDaseId;
	}
	public void setAnaDaseId(String anaDaseId) {
		this.anaDaseId = anaDaseId;
	}
	public String getSrcAnaDaseId() {
		return srcAnaDaseId;
	}
	public void setSrcAnaDaseId(String srcAnaDaseId) {
		this.srcAnaDaseId = srcAnaDaseId;
	}
	public String getAnaDaseNm() {
		return anaDaseNm;
	}
	public void setAnaDaseNm(String anaDaseNm) {
		this.anaDaseNm = anaDaseNm;
	}
	
	public String getSrcAnaDaseNm() {
		return srcAnaDaseNm;
	}
	public void setSrcAnaDaseNm(String srcAnaDaseNm) {
		this.srcAnaDaseNm = srcAnaDaseNm;
	}
	
	
	
}
