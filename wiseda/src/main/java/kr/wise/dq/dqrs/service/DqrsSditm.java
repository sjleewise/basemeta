package kr.wise.dq.dqrs.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class DqrsSditm extends CommonVo{
	
	private String sditmId;
	private String sysAreaId;
	
	private String govYn;
	
//	private String dbConnTrgPnm;
//	private String dbSchPnm;
	private String sditmLnm;
	private String sditmPnm;
	private String dmnId;
	private String dmnNm;
	private String objDescn;
	private String rqstDcd;
	private String vrfRmk;
	private String dataType;
	private String dataLen;
	private String dataScal;
	private String dicOrgn;
	private Date regDtm;
	private String regUserId;
	private String excYn;
	
	//
	private String infoSysCd;
//	private String dbConnTrgId;
//	private String dbSchId;
	
	
	public String getSysAreaId() {
		return sysAreaId;
	}
	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
	}
	public String getInfoSysCd() {
		return infoSysCd;
	}
	public void setInfoSysCd(String infoSysCd) {
		this.infoSysCd = infoSysCd;
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
	public String getDmnId() {
		return dmnId;
	}
	public void setDmnId(String dmnId) {
		this.dmnId = dmnId;
	}
	public String getDmnNm() {
		return dmnNm;
	}
	public void setDmnNm(String dmnNm) {
		this.dmnNm = dmnNm;
	}
	public String getObjDescn() {
		return objDescn;
	}
	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}
	public String getRqstDcd() {
		return rqstDcd;
	}
	public void setRqstDcd(String rqstDcd) {
		this.rqstDcd = rqstDcd;
	}
	public String getVrfRmk() {
		return vrfRmk;
	}
	public void setVrfRmk(String vrfRmk) {
		this.vrfRmk = vrfRmk;
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
	public String getDicOrgn() {
		return dicOrgn;
	}
	public void setDicOrgn(String dicOrgn) {
		this.dicOrgn = dicOrgn;
	}
	public Date getRegDtm() {
		return regDtm;
	}
	public void setRegDtm(Date regDtm) {
		this.regDtm = regDtm;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	public String getExcYn() {
		return excYn;
	}
	public void setExcYn(String excYn) {
		this.excYn = excYn;
	}
	public String getGovYn() {
		return govYn;
	}
	public void setGovYn(String govYn) {
		this.govYn = govYn;
	}
	
	@Override
	public String toString() {
		return "DqrsSditm [sditmId=" + sditmId + ", sysAreaId=" + sysAreaId
				+ ", govYn=" + govYn + ", sditmLnm=" + sditmLnm + ", sditmPnm="
				+ sditmPnm + ", dmnId=" + dmnId + ", dmnNm=" + dmnNm
				+ ", objDescn=" + objDescn + ", rqstDcd=" + rqstDcd
				+ ", vrfRmk=" + vrfRmk + ", dataType=" + dataType
				+ ", dataLen=" + dataLen + ", dataScal=" + dataScal
				+ ", dicOrgn=" + dicOrgn + ", regDtm=" + regDtm
				+ ", regUserId=" + regUserId + ", excYn=" + excYn
				+ ", infoSysCd=" + infoSysCd + "]";
	}
}
