package kr.wise.dq.dqrs.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class DqrsTbl extends CommonVo {

	private String tblId;
	private String tblLnm;
	private String tblPnm;
	private String colId;
	private String colLnm;		
	private String colPnm;		
	private String sysAreaId;	
	private String dbConnTrgId;
	private String dbSchId;
	private String subjAllPath;
	private String objDescn;	
	private String dataType;	
	private String precision;
	private String dataLen;		
	private String scal;		
	private String nullYn; 		
	private String pkYn; 		
	private String pkOrd; 		
	private String fkYn; 		
	private String vrfRmk; 		
	private String dicOrgn; 		
	private String dbConnTrgPnm; 		
	private String sysAreaPnm; 		
	private String dbSchPnm; 		
	private String dbConnTrgLnm; 		
	private String sysAreaLnm; 		
	private String dbSchLnm; 		
	private int colOrd; 		
	
	
	public String getPkOrd() {
		return pkOrd;
	}
	public void setPkOrd(String pkOrd) {
		this.pkOrd = pkOrd;
	}
	public int getColOrd() {
		return colOrd;
	}
	public void setColOrd(int colOrd) {
		this.colOrd = colOrd;
	}
	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}
	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
	}
	public String getSysAreaLnm() {
		return sysAreaLnm;
	}
	public void setSysAreaLnm(String sysAreaLnm) {
		this.sysAreaLnm = sysAreaLnm;
	}
	public String getDbSchLnm() {
		return dbSchLnm;
	}
	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}
	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}
	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}
	public String getSysAreaPnm() {
		return sysAreaPnm;
	}
	public void setSysAreaPnm(String sysAreaPnm) {
		this.sysAreaPnm = sysAreaPnm;
	}
	public String getDbSchPnm() {
		return dbSchPnm;
	}
	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
	}
	public String getTblId() {
		return tblId;
	}
	public void setTblId(String tblId) {
		this.tblId = tblId;
	}
	public String getTblLnm() {
		return tblLnm;
	}
	public void setTblLnm(String tblLnm) {
		this.tblLnm = tblLnm;
	}
	public String getTblPnm() {
		return tblPnm;
	}
	public void setTblPnm(String tblPnm) {
		this.tblPnm = tblPnm;
	}
	public String getColId() {
		return colId;
	}
	public void setColId(String colId) {
		this.colId = colId;
	}
	public String getColLnm() {
		return colLnm;
	}
	public void setColLnm(String colLnm) {
		this.colLnm = colLnm;
	}
	public String getColPnm() {
		return colPnm;
	}
	public void setColPnm(String colPnm) {
		this.colPnm = colPnm;
	}
	public String getSysAreaId() {
		return sysAreaId;
	}
	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
	}
	public String getDbConnTrgId() {
		return dbConnTrgId;
	}
	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}
	public String getDbSchId() {
		return dbSchId;
	}
	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}
	public String getSubjAllPath() {
		return subjAllPath;
	}
	public void setSubjAllPath(String subjAllPath) {
		this.subjAllPath = subjAllPath;
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
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public String getDataLen() {
		return dataLen;
	}
	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}
	public String getScal() {
		return scal;
	}
	public void setScal(String scal) {
		this.scal = scal;
	}
	public String getNullYn() {
		return nullYn;
	}
	public void setNullYn(String nullYn) {
		this.nullYn = nullYn;
	}
	public String getPkYn() {
		return pkYn;
	}
	@Override
	public String toString() {
		return "GovTblVO [tblId=" + tblId + ", tblLnm=" + tblLnm + ", tblPnm="
				+ tblPnm + ", colId=" + colId + ", colLnm=" + colLnm
				+ ", colPnm=" + colPnm + ", sysAreaId=" + sysAreaId
				+ ", dbConnTrgId=" + dbConnTrgId + ", dbSchId=" + dbSchId
				+ ", subjAllPath=" + subjAllPath + ", objDescn=" + objDescn
				+ ", dataType=" + dataType + ", precision=" + precision
				+ ", dataLen=" + dataLen + ", scal=" + scal + ", nullYn="
				+ nullYn + ", pkYn=" + pkYn + ", pkOrd=" + pkOrd + ", fkYn="
				+ fkYn + ", vrfRmk=" + vrfRmk + ", dicOrgn=" + dicOrgn
				+ ", dbConnTrgPnm=" + dbConnTrgPnm + ", sysAreaPnm="
				+ sysAreaPnm + ", dbSchPnm=" + dbSchPnm + ", dbConnTrgLnm="
				+ dbConnTrgLnm + ", sysAreaLnm=" + sysAreaLnm + ", dbSchLnm="
				+ dbSchLnm + ", colOrd=" + colOrd + "]";
	}
	public void setPkYn(String pkYn) {
		this.pkYn = pkYn;
	}
	public String getFkYn() {
		return fkYn;
	}
	public void setFkYn(String fkYn) {
		this.fkYn = fkYn;
	}
	public String getVrfRmk() {
		return vrfRmk;
	}
	public void setVrfRmk(String vrfRmk) {
		this.vrfRmk = vrfRmk;
	}
	public String getDicOrgn() {
		return dicOrgn;
	}
	public void setDicOrgn(String dicOrgn) {
		this.dicOrgn = dicOrgn;
	}
	
	
    
}





