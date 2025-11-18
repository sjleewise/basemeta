package kr.wise.advisor.prepare.otlquality.service;

import kr.wise.commons.cmm.CommonVo;

public class OtlQltyVo extends CommonVo {
	//진단대상별 현황 vo
	private String dbConnTrgId ;
	private String dbConnTgrPnm;
	private String dbSchId     ;
	private String dbSchPnm    ;
	private String dbSchLnm    ;
	private String tblCnt      ;
	private String colCnt      ;
	private String anaTblCnt   ;
	private String anaColCnt   ;
	private String anaDgr      ;
	private String anaCnt      ;
	private String esnErCnt    ;
	private String errRate     ;
	private String nonRate     ;

 	private String dqiLnm	   ;
 	
 	private String dbcTblNm    ;
 	private String dbcTblKorNm ;
 	private String dbcColNm    ;
 	private String dbcColKorNm ;
	               
	
	public String getDbConnTrgId() {
		return dbConnTrgId;
	}
	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}
	public String getDbConnTgrPnm() {
		return dbConnTgrPnm;
	}
	public void setDbConnTgrPnm(String dbConnTgrPnm) {
		this.dbConnTgrPnm = dbConnTgrPnm;
	}
	public String getDbSchId() {
		return dbSchId;
	}
	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}
	public String getDbSchPnm() {
		return dbSchPnm;
	}
	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
	}
	public String getDbSchLnm() {
		return dbSchLnm;
	}
	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}
	public String getTblCnt() {
		return tblCnt;
	}
	public void setTblCnt(String tblCnt) {
		this.tblCnt = tblCnt;
	}
	public String getColCnt() {
		return colCnt;
	}
	public void setColCnt(String colCnt) {
		this.colCnt = colCnt;
	}
	public String getAnaTblCnt() {
		return anaTblCnt;
	}
	public void setAnaTblCnt(String anaTblCnt) {
		this.anaTblCnt = anaTblCnt;
	}
	public String getAnaColCnt() {
		return anaColCnt;
	}
	public void setAnaColCnt(String anaColCnt) {
		this.anaColCnt = anaColCnt;
	}
	public String getAnaDgr() {
		return anaDgr;
	}
	public void setAnaDgr(String anaDgr) {
		this.anaDgr = anaDgr;
	}
	public String getAnaCnt() {
		return anaCnt;
	}
	public void setAnaCnt(String anaCnt) {
		this.anaCnt = anaCnt;
	}
	public String getEsnErCnt() {
		return esnErCnt;
	}
	public void setEsnErCnt(String esnErCnt) {
		this.esnErCnt = esnErCnt;
	}
	public String getErrRate() {
		return errRate;
	}
	public void setErrRate(String errRate) {
		this.errRate = errRate;
	}
	public String getNonRate() {
		return nonRate;
	}
	public void setNonRate(String nonRate) {
		this.nonRate = nonRate;
	}

	public String toString() {
		String textVal="";
		
		textVal = "OtlQltyVo toString() >>> ";
		textVal += "\n   dbConnTrgId  >>> " + dbConnTrgId ;
		textVal += "\n   dbConnTgrPnm >>> " + dbConnTgrPnm;
		textVal += "\n   dbSchId      >>> " + dbSchId     ;
		textVal += "\n   dbSchPnm     >>> " + dbSchPnm    ;
		textVal += "\n   dbSchLnm     >>> " + dbSchLnm    ;
		textVal += "\n   tblCnt       >>> " + tblCnt      ;
		textVal += "\n   colCnt       >>> " + colCnt      ;
		textVal += "\n   anaTblCnt    >>> " + anaTblCnt   ;
		textVal += "\n   anaColCnt    >>> " + anaColCnt   ;
		textVal += "\n   anaDgr       >>> " + anaDgr      ;
		textVal += "\n   anaCnt       >>> " + anaCnt      ;
		textVal += "\n   esnErCnt     >>> " + esnErCnt    ;
		textVal += "\n   errRate      >>> " + errRate     ;
		textVal += "\n   nonRate      >>> " + nonRate     ;
		
		return textVal;
	}
	public String getDbcTblNm() {
		return dbcTblNm;
	}
	public void setDbcTblNm(String dbcTblNm) {
		this.dbcTblNm = dbcTblNm;
	}
	public String getDbcColNm() {
		return dbcColNm;
	}
	public void setDbcColNm(String dbcColNm) {
		this.dbcColNm = dbcColNm;
	}
	public String getDqiLnm() {
		return dqiLnm;
	}
	public void setDqiLnm(String dqiLnm) {
		this.dqiLnm = dqiLnm;
	}
	public String getDbcTblKorNm() {
		return dbcTblKorNm;
	}
	public void setDbcTblKorNm(String dbcTblKorNm) {
		this.dbcTblKorNm = dbcTblKorNm;
	}
	public String getDbcColKorNm() {
		return dbcColKorNm;
	}
	public void setDbcColKorNm(String dbcColKorNm) {
		this.dbcColKorNm = dbcColKorNm;
	}
}
