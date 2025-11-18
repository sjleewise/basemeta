package kr.wise.commons.damgmt.gov.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaGovDmn extends CommonVo {
    
	public String getDmnId() {
		return dmnId;
	}



	public void setDmnId(String dmnId) {
		this.dmnId = dmnId;
	}



	public String getEnactDgr() {
		return enactDgr;
	}



	public void setEnactDgr(String enactDgr) {
		this.enactDgr = enactDgr;
	}



	public String getUppDmngLnm() {
		return uppDmngLnm;
	}



	public void setUppDmngLnm(String uppDmngLnm) {
		this.uppDmngLnm = uppDmngLnm;
	}



	public String getDmngLnm() {
		return dmngLnm;
	}



	public void setDmngLnm(String dmngLnm) {
		this.dmngLnm = dmngLnm;
	}



	public String getDmnLnm() {
		return dmnLnm;
	}



	public void setDmnLnm(String dmnLnm) {
		this.dmnLnm = dmnLnm;
	}



	public String getDmnPnm() {
		return dmnPnm;
	}



	public void setDmnPnm(String dmnPnm) {
		this.dmnPnm = dmnPnm;
	}



	public String getObjDescn() {
		return objDescn;
	}



	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}



	public String getDataType() {
		return dataType;
	}



	public void setDataType(String dataType) {
		this.dataType = dataType;
	}



	public String getDataLen() {
		return dataLen;
	}



	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}



	public String getdataScal() {
		return dataScal;
	}



	public void setdataScal(String dataScal) {
		this.dataScal = dataScal;
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



	public String getUnitType() {
		return unitType;
	}



	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}



	public String getCdValRule() {
		return cdValRule;
	}



	public void setCdValRule(String cdValRule) {
		this.cdValRule = cdValRule;
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



	private String dmnId;
	private String enactDgr;
	private String uppDmngLnm;
	private String dmngLnm;
	private String dmnLnm;
	private String dmnPnm;
	private String objDescn;
	private String dataType;
	private String dataLen;
	private String dataScal;
	private String saveType;
	private String examData;
	private String unitType;
	private String cdValRule;
	private String regTypCd;
	private String regDtm;
	private String regUserId;
	private String aplDtm;

    

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaGovDmn [dmnId=").append(dmnId)
				.append(", enactDgr=").append(enactDgr)
				.append(", uppDmngLnm=").append(uppDmngLnm)
				.append(", dmngLnm=").append(dmngLnm).append(", dmnLnm=")
				.append(dmnLnm).append(", dmnPnm=").append(dmnPnm)
				.append(", objDescn=").append(objDescn).append(", dataType=")
				.append(dataType).append(", dataLen=").append(dataLen)
				.append(", dataScal=").append(dataScal).append(", saveType=")
				.append(saveType).append(", examData=").append(examData)
				.append(saveType).append(", unitType=").append(unitType)
				.append(saveType).append(", cdValRule=").append(cdValRule)
				.append(saveType).append(", regTypCd=").append(regTypCd)
				.append(saveType).append(", regDtm=").append(examData)
				.append(saveType).append(", regUserId=").append(regUserId)
				.append("]");
		return builder.toString() + super.toString();
	}


    
}