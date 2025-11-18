package kr.wise.commons.damgmt.gov.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaGovSditmMap extends CommonVo {
	
	private String govSditmId;
	private String enactDgr;
    private String govSditmLnm;
    private String govSditmPnm;
    
	private String objDescn;
    private String govDmnId;
    private String govDmnLnm;
    private String saveType;
    private String examData;
    private String cdValRule;
    private String govStndCd;
    private String govOrgNm;
    
    private String sditmId;
    private String sditmLnm;
    private String sditmPnm;
    
    private String regDtm;
    private String regUserId;
    private String regUserNm;
    
	

    public String getGovSditmId() {
		return govSditmId;
	}

	public void setGovSditmId(String govSditmId) {
		this.govSditmId = govSditmId;
	}

	public String getEnactDgr() {
		return enactDgr;
	}

	public void setEnactDgr(String enactDgr) {
		this.enactDgr = enactDgr;
	}

	public String getGovSditmLnm() {
		return govSditmLnm;
	}

	public void setGovSditmLnm(String govSditmLnm) {
		this.govSditmLnm = govSditmLnm;
	}

	public String getGovSditmPnm() {
		return govSditmPnm;
	}

	public void setGovSditmPnm(String govSditmPnm) {
		this.govSditmPnm = govSditmPnm;
	}

	public String getObjDescn() {
		return objDescn;
	}

	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}

	public String getGovDmnId() {
		return govDmnId;
	}

	public void setGovDmnId(String govDmnId) {
		this.govDmnId = govDmnId;
	}

	public String getGovDmnLnm() {
		return govDmnLnm;
	}

	public void setGovDmnLnm(String govDmnLnm) {
		this.govDmnLnm = govDmnLnm;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public String getExamData() {
		return examData;
	}

	public void setExamData(String examData) {
		this.examData = examData;
	}

	public String getCdValRule() {
		return cdValRule;
	}

	public void setCdValRule(String cdValRule) {
		this.cdValRule = cdValRule;
	}

	public String getGovStndCd() {
		return govStndCd;
	}

	public void setGovStndCd(String govStndCd) {
		this.govStndCd = govStndCd;
	}

	public String getGovOrgNm() {
		return govOrgNm;
	}

	public void setGovOrgNm(String govOrgNm) {
		this.govOrgNm = govOrgNm;
	}

	public String getSditmId() {
		return sditmId;
	}

	public void setSditmId(String sditmId) {
		this.sditmId = sditmId;
	}

	public String getSditmLnm() {
		return sditmLnm;
	}

	public void setSditmLnm(String sditmLnm) {
		this.sditmLnm = sditmLnm;
	}

	public String getSditmPnm() {
		return sditmPnm;
	}

	public void setSditmPnm(String sditmPnm) {
		this.sditmPnm = sditmPnm;
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

	public String getRegUserNm() {
		return regUserNm;
	}

	public void setRegUserNm(String regUserNm) {
		this.regUserNm = regUserNm;
	}


    
    
	@Override
	public String toString() {
		return "WaaGovSditmMap [govSditmId=" + govSditmId + ", enactDgr="
				+ enactDgr + ", govSditmLnm=" + govSditmLnm + ", govSditmPnm="
				+ govSditmPnm + ", objDescn=" + objDescn + ", govDmnId="
				+ govDmnId + ", govDmnLnm=" + govDmnLnm + ", saveType=" + saveType
				+ ", examData=" + examData + ", cdValRule=" + cdValRule
				+ ", govStndCd=" + govStndCd + ", govOrgNm=" + govOrgNm + ", sditmId="
				+ sditmId + ", sditmLnm=" + sditmLnm + ", sditmPnm=" + sditmPnm
				+ ", regDtm=" + regDtm + ", regUserId=" + regUserId + ", regUserNm=" + regUserNm
				+ "]";
	}

    
}