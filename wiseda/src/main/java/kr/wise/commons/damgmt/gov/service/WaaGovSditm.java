package kr.wise.commons.damgmt.gov.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaGovSditm extends CommonVo {
    public String getSditmId() {
		return sditmId;
	}


	public void setSditmId(String sditmId) {
		this.sditmId = sditmId;
	}


	public String getEnactDgr() {
		return enactDgr;
	}


	public void setEnactDgr(String enactDgr) {
		this.enactDgr = enactDgr;
	}


	public String getSditmLnm() {
		return sditmLnm;
	}


	public void setSditmLnm(String sditmLnm) {
		this.sditmLnm = sditmLnm;
	}




	public String getObjDescn() {
		return objDescn;
	}


	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
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


	public String getAplDtm() {
		return aplDtm;
	}


	public void setAplDtm(String aplDtm) {
		this.aplDtm = aplDtm;
	}


	private String sditmId;
    private String enactDgr;
    private String sditmLnm;
    private String sditmPnm;
    public String getSditmPnm() {
		return sditmPnm;
	}


	public void setSditmPnm(String sditmPnm) {
		this.sditmPnm = sditmPnm;
	}


	private String objDescn;
    private String dmnId;
    private String dmnLnm;
    private String saveType;
    private String examData;
    private String cdValRule;
    private String govStndCd;

    private String govOrgNm;
    private String regTypCd;
    private String regDtm;
    private String regUserId;
    private String aplDtm;
    
    
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaGovSditm [sditmId=").append(sditmId)
				.append(", enactDgr=").append(enactDgr)
				.append(", sditmLnm=").append(sditmLnm)
				.append(", sditmPnm=").append(sditmPnm).append(", objDescn=")
				.append(objDescn).append(", dmnId=")
				.append(dmnId).append(", dmnLnm=").append(dmnLnm)
				.append(", saveType=").append(saveType).append(", examData=")
				.append(examData).append(", cdValRule=").append(cdValRule)
				.append(examData).append(", govStndCd=").append(govStndCd)
				.append(examData).append(", govOrgNm=").append(govOrgNm)
				.append(examData).append(", regTypCd=").append(regTypCd)
				.append(examData).append(", regDtm=").append(regDtm)
				.append(examData).append(", regUserId=").append(regUserId)
				.append(examData).append(", aplDtm=").append(aplDtm)
				.append("]");
		return builder.toString() + super.toString();
	}


    
}