package kr.wise.dq.codemng.service;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.helper.IBSDateJsonDeserializer;

public class WaaCdRule extends CommonVo{  
	
	private String cdRuleId;
	
	private String dbConnTrgId;
	
	private String dbConnTrgPnm;
	
	private String dbConnTrgLnm;
	
    private String dbSchId;    
       
    private String cdRuleNm;
    
    private String cdSql;
    
    private String cdClsColNm;   
    
    private String cdClsNmColNm;
    
    private String cdIdColNm;   
    
    private String cdNmColNm;
    
    private String objDescn;

    private String codeClsVal;
    
    private String codeClsId;
    
    private String cdTypCd;
    
    
	public String getCodeClsVal() {
		return codeClsVal;
	}
	public void setCodeClsVal(String codeClsVal) {
		this.codeClsVal = codeClsVal;
	}
	public String getDbConnTrgId() {
		return dbConnTrgId;
	}
	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}
	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}
	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}
	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}
	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
	}
	public String getDbSchId() {
		return dbSchId;
	}
	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}
	public String getCdRuleNm() {
		return cdRuleNm;
	}
	public void setCdRuleNm(String cdRuleNm) {
		this.cdRuleNm = cdRuleNm;
	}
	public String getCdSql() {
		return cdSql;
	}
	public void setCdSql(String cdSql) {
		this.cdSql = cdSql;
	}
	public String getCdClsColNm() {
		return cdClsColNm;
	}
	public void setCdClsColNm(String cdClsColNm) {
		this.cdClsColNm = cdClsColNm;
	}
	public String getCdClsNmColNm() {
		return cdClsNmColNm;
	}
	public void setCdClsNmColNm(String cdClsNmColNm) {
		this.cdClsNmColNm = cdClsNmColNm;
	}

	public String getCdNmColNm() {
		return cdNmColNm;
	}
	public void setCdNmColNm(String cdNmColNm) {
		this.cdNmColNm = cdNmColNm;
	}
	public String getCdIdColNm() {
		return cdIdColNm;
	}
	public void setCdIdColNm(String cdIdColNm) {
		this.cdIdColNm = cdIdColNm;
	}
	public String getObjDescn() {
		return objDescn;
	}
	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}
	public String getCdRuleId() {
		return cdRuleId;
	}
	public void setCdRuleId(String cdRuleId) {
		this.cdRuleId = cdRuleId;
	}
		
	public String getCdTypCd() {
		return cdTypCd;
	}
	public void setCdTypCd(String cdTypCd) {
		this.cdTypCd = cdTypCd;
	}
	
	
	@Override
	public String toString() {
		return "WaaCdRule [cdRuleId=" + cdRuleId + ", dbConnTrgId="
				+ dbConnTrgId + ", dbConnTrgPnm=" + dbConnTrgPnm
				+ ", dbConnTrgLnm=" + dbConnTrgLnm + ", dbSchId=" + dbSchId
				+ ", cdRuleNm=" + cdRuleNm + ", cdSql=" + cdSql
				+ ", cdClsColNm=" + cdClsColNm + ", cdClsNmColNm="
				+ cdClsNmColNm + ", cdIdColNm=" + cdIdColNm + ", cdNmColNm="
				+ cdNmColNm + ", objDescn=" + objDescn + "]";
	}
	public String getCodeClsId() {
		return codeClsId;
	}
	public void setCodeClsId(String codeClsId) {
		this.codeClsId = codeClsId;
	}
	
         
	
	

}