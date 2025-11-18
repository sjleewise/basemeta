package kr.wise.advisor.prepare.udefoutlier.datajoin.service;

import kr.wise.advisor.prepare.udefoutlier.service.WadUod;

public class WadUodcDaseJn extends WadUod {
	private String udfOtlDtcId;
	private String creCompId;
	private String jnTypCd;
	private String jnTypNm;
	private String leftAnaDaseId;
	private String rightAnaDaseId;
	private String anaDaseId;
	private String anaDaseNm;
	
	public String getAnaDaseNm() {
		return anaDaseNm;
	}
	public void setAnaDaseNm(String anaDaseNm) {
		this.anaDaseNm = anaDaseNm;
	}
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
	public String getJnTypCd() {
		return jnTypCd;
	}
	public void setJnTypCd(String jnTypCd) {
		this.jnTypCd = jnTypCd;
	}
	public String getLeftAnaDaseId() {
		return leftAnaDaseId;
	}
	public void setLeftAnaDaseId(String leftAnaDaseId) {
		this.leftAnaDaseId = leftAnaDaseId;
	}
	public String getRightAnaDaseId() {
		return rightAnaDaseId;
	}
	public void setRightAnaDaseId(String rightAnaDaseId) {
		this.rightAnaDaseId = rightAnaDaseId;
	}
	public String getAnaDaseId() {
		return anaDaseId;
	}
	public void setAnaDaseId(String anaDaseId) {
		this.anaDaseId = anaDaseId;
	}
	
	public String getJnTypNm() {
		return jnTypNm;
	}
	public void setJnTypNm(String jnTypNm) {
		this.jnTypNm = jnTypNm;
	}
	
	
	
}
