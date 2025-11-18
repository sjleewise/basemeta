package kr.wise.commons.damgmt.gov.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaGovCdVal extends CommonVo {
	private String cdValId;
	public String getCdValId() {
		return cdValId;
	}




	public void setCdValId(String cdValId) {
		this.cdValId = cdValId;
	}




	public String getCdVal() {
		return cdVal;
	}




	public void setCdVal(String cdVal) {
		this.cdVal = cdVal;
	}




	public String getCdValNm() {
		return cdValNm;
	}




	public void setCdValNm(String cdValNm) {
		this.cdValNm = cdValNm;
	}




	public String getDmnId() {
		return dmnId;
	}




	public void setDmnId(String dmnId) {
		this.dmnId = dmnId;
	}




	public String getDmnLnm() {
		return dmnLnm;
	}




	public void setDmnLnm(String dmnLnm) {
		this.dmnLnm = dmnLnm;
	}




	public String getUppCdValId() {
		return uppCdValId;
	}




	public void setUppCdValId(String uppCdValId) {
		this.uppCdValId = uppCdValId;
	}




	public String getObjDescn() {
		return objDescn;
	}




	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}




	public String getRegTypCd() {
		return regTypCd;
	}




	public void setRegTypCd(String regTypCd) {
		this.regTypCd = regTypCd;
	}




	public String getRegDtm() {
		return regDtm;
	}




	public void setRegDtm(String regDtm) {
		this.regDtm = regDtm;
	}




	public String getRegUserId() {
		return regUserId;
	}




	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}




	public String getDispOrd() {
		return dispOrd;
	}




	public void setDispOrd(String dispOrd) {
		this.dispOrd = dispOrd;
	}




	public String getAprStrDt() {
		return aprStrDt;
	}




	public void setAprStrDt(String aprStrDt) {
		this.aprStrDt = aprStrDt;
	}




	public String getAprEndDt() {
		return aprEndDt;
	}




	public void setAprEndDt(String aprEndDt) {
		this.aprEndDt = aprEndDt;
	}




	public String getUseYn() {
		return useYn;
	}




	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}




	private String cdVal;
	private String cdValNm;
	private String dmnId;
	private String dmnLnm;
	private String uppCdValId;
	private String objDescn;
	private String regTypCd;
	private String regDtm;
	private String regUserId;
	private String dispOrd;
	private String aprStrDt;
	private String aprEndDt;
	private String useYn;




	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaGovCdVal [cdValId=").append(cdValId)
				.append(", cdVal=").append(cdVal)
				.append(", cdValNm=").append(cdValNm)
				.append(", dmnId=").append(dmnId).append(", dmnLnm=")
				.append(dmnLnm).append(", uppCdValId=").append(uppCdValId)
				.append(", objDescn=").append(objDescn).append(", regTypCd=")
				.append(regTypCd).append(", regDtm=").append(regDtm)
				.append(", regUserId=").append(regUserId).append(", dispOrd=")
				.append(dispOrd).append("]");
		return builder.toString() + super.toString();
	}


    
}