package kr.wise.dq.criinfo.anatrg.service;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.helper.IBSDateJsonDeserializer;

public class WaaExpTbl extends CommonVo{
	
	private String dbConnTrgId;

	private String dbConnTrgPnm;
	
	private String dbConnTrgLnm;
	
    private String dbSchId;    
    
    private String dbSchPnm;

    private String dbSchLnm;
    
    private String dbcTblNm;
    
    private String dbcTblKorNm;
   
    private String expYn;
    
    private String expRsnCntn;
       
    private Date updtDtm;
    
    private String dbcColNm;
    
    private String dbcColKorNm;
    
    private String ruleRelId;
    
    private String regYn;
    
    private String vrfcTyp;
   
    private String addCnd;
    
    private String vrfcId;
    
	private String selectNm;
    
    private String selectCol;

	private String dbConnTrgNm;

	private String excStndRule;
	
	private String expRuleId;
	
	private String relation;
	
	private String dataType;
	
	
	
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getVrfcId() {
		return vrfcId;
	}

	public void setVrfcId(String vrfcId) {
		this.vrfcId = vrfcId;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getRegTyp() {
		return regTyp;
	}

	public void setRegTyp(String regTyp) {
		this.regTyp = regTyp;
	}

	private String regTyp;
	
	public String getExpRuleId() {
		return expRuleId;
	}

	public void setExpRuleId(String expRuleId) {
		this.expRuleId = expRuleId;
	}

	public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}
    
    public String getSelectNm() {
		return selectNm;
	}

	public void setSelectNm(String selectNm) {
		this.selectNm = selectNm;
	}

	public String getSelectCol() {
		return selectCol;
	}

	public void setSelectCol(String selectCol) {
		this.selectCol = selectCol;
	}
    
    public String getAddCnd() {
		return addCnd;
	}

	public void setAddCnd(String addCnd) {
		this.addCnd = addCnd;
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

	public String getDbcTblNm() {
		return dbcTblNm;
	}

	public void setDbcTblNm(String dbcTblNm) {
		this.dbcTblNm = dbcTblNm;
	}

	public String getDbcTblKorNm() {
		return dbcTblKorNm;
	}

	public void setDbcTblKorNm(String dbcTblKorNm) {
		this.dbcTblKorNm = dbcTblKorNm;
	}



	public String getExpYn() {
		return expYn;
	}

	public void setExpYn(String expYn) {
		this.expYn = expYn;
	}

	public String getExpRsnCntn() {
		return expRsnCntn;
	}

	public void setExpRsnCntn(String expRsnCntn) {
		this.expRsnCntn = expRsnCntn;
	}

	public Date getUpdtDtm() {
		return updtDtm;
	}

	@JsonDeserialize(using = IBSDateJsonDeserializer.class)
	public void setUpdtDtm(Date updtDtm) { 
		this.updtDtm = updtDtm;
	}

	public String getDbcColNm() {
		return dbcColNm;
	}

	public void setDbcColNm(String dbcColNm) {
		this.dbcColNm = dbcColNm;
	}

	public String getDbcColKorNm() { 
		return dbcColKorNm;
	}

	public void setDbcColKorNm(String dbcColKorNm) {
		this.dbcColKorNm = dbcColKorNm;
	}
    
	public String getRuleRelId() {
		return ruleRelId;
	}

	public void setRuleRelId(String ruleRelId) {
		this.ruleRelId = ruleRelId;
	}

	public String getRegYn() {
		return regYn;
	}

	public void setRegYn(String regYn) {
		this.regYn = regYn;
	}

	public String getVrfcTyp() {
		return vrfcTyp;
	} 

	public void setVrfcTyp(String vrfcTyp) {
		this.vrfcTyp = vrfcTyp;
	}

	public String getDbConnTrgNm() {
		return dbConnTrgNm;
	}

	public void setDbConnTrgNm(String dbConnTrgNm) {
		this.dbConnTrgNm = dbConnTrgNm;
	}

	public String getExcStndRule() {
		return excStndRule;
	}

	public void setExcStndRule(String excStndRule) {
		this.excStndRule = excStndRule;
	}

}