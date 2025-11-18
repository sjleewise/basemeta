package kr.wise.dq.dqrs.service;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.helper.IBSDateJsonDeserializer;

public class DqrsExpCol extends CommonVo{
	
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
    
    private String subjId;
    private String objNm;
    private String dataType;
    private String dataLen;
    private String dataPnt;
    private String nullYn;
    private String defltVal;
    private String pkYn;
    private String ord;
    private String pkOrd;
    
    
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

	public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
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

	public String getSubjId() {
		return subjId;
	}

	public String getObjNm() {
		return objNm;
	}

	public String getDataType() {
		return dataType;
	}

	public String getDataLen() {
		return dataLen;
	}

	public String getDataPnt() {
		return dataPnt;
	}

	public String getNullYn() {
		return nullYn;
	}

	public String getDefltVal() {
		return defltVal;
	}

	public String getPkYn() {
		return pkYn;
	}

	public String getOrd() {
		return ord;
	}

	public String getPkOrd() {
		return pkOrd;
	}

	public void setSubjId(String subjId) {
		this.subjId = subjId;
	}

	public void setObjNm(String objNm) {
		this.objNm = objNm;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}

	public void setDataPnt(String dataPnt) {
		this.dataPnt = dataPnt;
	}

	public void setNullYn(String nullYn) {
		this.nullYn = nullYn;
	}

	public void setDefltVal(String defltVal) {
		this.defltVal = defltVal;
	}

	public void setPkYn(String pkYn) {
		this.pkYn = pkYn;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}

	public void setPkOrd(String pkOrd) {
		this.pkOrd = pkOrd;
	}

	 

}