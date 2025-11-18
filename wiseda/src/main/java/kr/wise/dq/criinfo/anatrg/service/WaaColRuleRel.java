package kr.wise.dq.criinfo.anatrg.service;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.helper.IBSDateJsonDeserializer;

public class WaaColRuleRel extends WaaExpTbl{
	
	private String dbConnTrgId;
	
	private String dbConnTrgPnm;
	
	private String dbConnTrgLnm;
	
    private String dbSchId;    
    
    private String dbSchPnm;

    private String dbSchLnm;
    
    private String dbcTblNm;
    
    private String dbcTblKorNm;
   
    private String vrfcId;
   
    private String shdKndCd;
    
    private String shdJobId;
    
    private String etcJobKndCd;
    
    private String shdJobKndCd;
    
    private String etcJobNm;
    
    private String shdJobNm;  
    
    private String vrfcNm;
    
    private String vrfcTyp;
    
    private String ruleRelId;

    private String cdClsId;
    
    private String prfId;
    
    private Date anaStrDtm;
    
    private String dataType;
    
    private String anaStsCd;
    
    private String anaDgr;
    
    private String anaDgrNm;
    
	public String getAnaDgr() {
		return anaDgr;
	}

	public void setAnaDgr(String anaDgr) {
		this.anaDgr = anaDgr;
	}

	public String getAnaDgrNm() {
		return anaDgrNm;
	}

	public void setAnaDgrNm(String anaDgrNm) {
		this.anaDgrNm = anaDgrNm;
	}

	public String getAnaStsCd() {
		return anaStsCd;
	}

	public void setAnaStsCd(String anaStsCd) {
		this.anaStsCd = anaStsCd;
	}

	public String getCdClsId() {
		return cdClsId;
	}

	public void setCdClsId(String cdClsId) {
		this.cdClsId = cdClsId;
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

	public String getVrfcId() {
		return vrfcId;
	}

	public void setVrfcId(String vrfcId) {
		this.vrfcId = vrfcId;
	}

	public String getShdKndCd() {
		return shdKndCd;
	}

	public void setShdKndCd(String shdKndCd) {
		this.shdKndCd = shdKndCd;
	}

	public String getShdJobId() {
		return shdJobId;
	}

	public void setShdJobId(String shdJobId) {
		this.shdJobId = shdJobId;
	}
    
	public String getEtcJobKndCd() {
		return etcJobKndCd;
	}

	public void setEtcJobKndCd(String etcJobKndCd) {
		this.etcJobKndCd = etcJobKndCd;
	}

	public String getVrfcNm() {
		return vrfcNm;
	}

	public void setVrfcNm(String vrfcNm) {
		this.vrfcNm = vrfcNm;
	}

	public String getVrfcTyp() {
		return vrfcTyp;
	}

	public void setVrfcTyp(String vrfcTyp) {
		this.vrfcTyp = vrfcTyp;
	}

	public String getRuleRelId() {
		return ruleRelId;
	}

	public void setRuleRelId(String ruleRelId) {
		this.ruleRelId = ruleRelId;
	}

	public String getShdJobNm() {
		return shdJobNm;
	}

	public void setShdJobNm(String shdJobNm) {
		this.shdJobNm = shdJobNm;
	}

	public String getEtcJobNm() {
		return etcJobNm;
	}

	public void setEtcJobNm(String etcJobNm) { 
		this.etcJobNm = etcJobNm;
	}
	
	public String getShdJobKndCd() {
		return shdJobKndCd;
	}

	public void setShdJobKndCd(String shdJobKndCd) {
		this.shdJobKndCd = shdJobKndCd;
	}

	public String getPrfId() {
		return prfId;
	}

	public void setPrfId(String prfId) {
		this.prfId = prfId;
	}

	public Date getAnaStrDtm() {
		return anaStrDtm;
	}

	@JsonDeserialize(using = IBSDateJsonDeserializer.class)
	public void setAnaStrDtm(Date anaStrDtm) {
		this.anaStrDtm = anaStrDtm;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	
	

}