package kr.wise.meta.govstnd.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class GovSditm extends CommonVo{
	
	private String sditmId;
	private String sysAreaId;
	private String dbConnTrgPnm;
	private String dbSchPnm;
	private String sditmLnm;
	private String sditmPnm;
	private String dmnId;
	private String dmnNm;
	private String objDescn;
	private String rqstDcd;
	private String vrfRmk;
	private String dataType;
	private int dataLen;
	private int dataScal;
	private String dicOrgn;
	private Date regDtm;
	private String regUserId;
	private String excYn;
	
	//
	private String infoSysCd;
	private String dbConnTrgId;
	private String dbSchId;
	
	
	public String getSysAreaId() {
		return sysAreaId;
	}
	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
	}
	public String getDbSchId() {
		return dbSchId;
	}
	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}
	public String getInfoSysCd() {
		return infoSysCd;
	}
	public void setInfoSysCd(String infoSysCd) {
		this.infoSysCd = infoSysCd;
	}
	public String getDbConnTrgId() {
		return dbConnTrgId;
	}
	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}
	public String getSditmId() {
		return sditmId;
	}
	public void setSditmId(String sditmId) {
		this.sditmId = sditmId;
	}
	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}
	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}
	public String getDbSchPnm() {
		return dbSchPnm;
	}
	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
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
	public int getDataLen() {
		return dataLen;
	}
	public void setDataLen(int dataLen) {
		this.dataLen = dataLen;
	}
	public int getDataScal() {
		return dataScal;
	}
	public void setDataScal(int dataScal) {
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
	@Override
	public String toString() {
		return "GovSditm [sditmId=" + sditmId + ", sysAreaPnm="  + ", sysAreaLnm=" 
				+ ", dbConnTrgPnm=" + dbConnTrgPnm + ", dbSchPnm=" + dbSchPnm + ", sditmLnm=" + sditmLnm + ", sditmPnm="
				+ sditmPnm + ", dmnId=" + dmnId + ", dmnNm=" + dmnNm + ", objDescn=" + objDescn + ", rqstDcd=" + rqstDcd
				+ ", vrfRmk=" + vrfRmk + ", dataType=" + dataType + ", dataLen=" + dataLen + ", dataScal=" + dataScal
				+ ", dicOrgn=" + dicOrgn + ", regDtm=" + regDtm + ", regUserId=" + regUserId + ", excYn=" + excYn
				+ ", infoSysCd=" + infoSysCd + ", dbConnTrgId=" + dbConnTrgId + "]";
	}
}
