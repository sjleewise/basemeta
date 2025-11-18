package kr.wise.commons.damgmt.gov.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaGovDmnMap extends CommonVo {
	
	private String govDmnId;
	private String enactDgr;
	private String govUppDmngLnm;	//공통표준도메인그룹명
	private String govDmngLnm;		//공통표준도메인분류명
	private String govDmnLnm;		//공통표준도메인논리명
	private String objDescn;
	private String dataType;
	private String dataLen;
	private String dataScal;
	private String saveType;		//저장형식
	private String examData;		//표현형식
	private String unitType;		//단위
	private String cdValRule;		//허용값(유효값 예시)
	private String regTypCd;
	private String regDtm;
	private String regUserId;
	
	private String dmnId;
	private String dmnLnm;
	private String dmnPnm;
	
	
	public String getGovDmnId() {
		return govDmnId;
	}
	public void setGovDmnId(String govDmnId) {
		this.govDmnId = govDmnId;
	}
	public String getEnactDgr() {
		return enactDgr;
	}
	public void setEnactDgr(String enactDgr) {
		this.enactDgr = enactDgr;
	}
	public String getGovUppDmngLnm() {
		return govUppDmngLnm;
	}
	public void setGovUppDmngLnm(String govUppDmngLnm) {
		this.govUppDmngLnm = govUppDmngLnm;
	}
	public String getGovDmngLnm() {
		return govDmngLnm;
	}
	public void setGovDmngLnm(String govDmngLnm) {
		this.govDmngLnm = govDmngLnm;
	}
	public String getGovDmnLnm() {
		return govDmnLnm;
	}
	public void setGovDmnLnm(String govDmnLnm) {
		this.govDmnLnm = govDmnLnm;
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
	public String getDataScal() {
		return dataScal;
	}
	public void setDataScal(String dataScal) {
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
	
	@Override
	public String toString() {
		return "WaaGovDmnMap [govDmnId=" + govDmnId + ", enactDgr=" + enactDgr
				+ ", govUppDmngLnm=" + govUppDmngLnm + ", govDmngLnm="
				+ govDmngLnm + ", govDmnLnm=" + govDmnLnm
				+ ", objDescn=" + objDescn + ", dataType="
				+ dataType + ", dataLen=" + dataLen + ", dataScal=" + dataScal
				+ ", saveType=" + saveType + ", examData=" + examData
				+ ", unitType=" + unitType + ", cdValRule=" + cdValRule
				+ ", regTypCd=" + regTypCd + ", regDtm=" + regDtm
				+ ", regUserId=" + regUserId 
				+ ", dmnId=" + dmnId + ", dmnLnm=" + dmnLnm + "]";
	}
	public String getDmnPnm() {
		return dmnPnm;
	}
	public void setDmnPnm(String dmnPnm) {
		this.dmnPnm = dmnPnm;
	}
    
}