package kr.wise.advisor.prepare.dqiquality.service;

import kr.wise.commons.cmm.CommonVo;

public class DqiQltyVo extends CommonVo {
	private String dbConnTrgId ;
	private String dbConnTgrPnm;
	private String dbConnTgrLnm;
	private String dbSchId     ;
	private String dbSchPnm    ;

	private String dqiId       ;
 	private String dqiLnm	   ;
 	private String uppDqiLnm   ;
 	
 	private String bizDcd      ;
 	private String fullPath    ;
 	
 	private String brAnaDgr    ;
 	private String brCnt       ;
 	private String sigma       ;
 	private String brAnaCnt    ;
 	private String brErCnt     ;
 	private String brNonRate   ;
 	private String brErrRate   ;
 	
 	private String prfAnaDgr   ;
 	private String prfCnt      ;
 	private String prfAnaCnt   ;
 	private String prfErCnt    ;
 	private String prfNonRate  ;
 	private String prfErrRate  ;
 	
 	private String mdAnaDgr    ;
 	private String mdCnt       ;
 	private String mdAnaCnt    ;
 	private String mdErCnt     ;
 	private String mdNonRate   ;
 	private String mdErrRate   ;
 	
 	private String anaDgr      ;
 	
 	private String totAnaCnt   ;
 	private String totNonRate  ;
 	private String totErrRate  ;
 	
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
	public String getDqiLnm() {
		return dqiLnm;
	}
	public void setDqiLnm(String dqiLnm) {
		this.dqiLnm = dqiLnm;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public String getBrAnaDgr() {
		return brAnaDgr;
	}
	public void setBrAnaDgr(String brAnaDgr) {
		this.brAnaDgr = brAnaDgr;
	}
	public String getBrCnt() {
		return brCnt;
	}
	public void setBrCnt(String brCnt) {
		this.brCnt = brCnt;
	}
	public String getSigma() {
		return sigma;
	}
	public void setSigma(String sigma) {
		this.sigma = sigma;
	}
	public String getBrAnaCnt() {
		return brAnaCnt;
	}
	public void setBrAnaCnt(String brAnaCnt) {
		this.brAnaCnt = brAnaCnt;
	}
	public String getBrErCnt() {
		return brErCnt;
	}
	public void setBrErCnt(String brErCnt) {
		this.brErCnt = brErCnt;
	}
	public String getBrNonRate() {
		return brNonRate;
	}
	public void setBrNonRate(String brNonRate) {
		this.brNonRate = brNonRate;
	}
	public String getBrErrRate() {
		return brErrRate;
	}
	public void setBrErrRate(String brErrRate) {
		this.brErrRate = brErrRate;
	}
	public String getPrfAnaDgr() {
		return prfAnaDgr;
	}
	public void setPrfAnaDgr(String prfAnaDgr) {
		this.prfAnaDgr = prfAnaDgr;
	}
	public String getPrfCnt() {
		return prfCnt;
	}
	public void setPrfCnt(String prfCnt) {
		this.prfCnt = prfCnt;
	}
	public String getPrfAnaCnt() {
		return prfAnaCnt;
	}
	public void setPrfAnaCnt(String prfAnaCnt) {
		this.prfAnaCnt = prfAnaCnt;
	}
	public String getPrfErCnt() {
		return prfErCnt;
	}
	public void setPrfErCnt(String prfErCnt) {
		this.prfErCnt = prfErCnt;
	}
	public String getPrfNonRate() {
		return prfNonRate;
	}
	public void setPrfNonRate(String prfNonRate) {
		this.prfNonRate = prfNonRate;
	}
	public String getPrfErrRate() {
		return prfErrRate;
	}
	public void setPrfErrRate(String prfErrRate) {
		this.prfErrRate = prfErrRate;
	}
	public String getMdAnaDgr() {
		return mdAnaDgr;
	}
	public void setMdAnaDgr(String mdAnaDgr) {
		this.mdAnaDgr = mdAnaDgr;
	}
	public String getMdCnt() {
		return mdCnt;
	}
	public void setMdCnt(String mdCnt) {
		this.mdCnt = mdCnt;
	}
	public String getMdAnaCnt() {
		return mdAnaCnt;
	}
	public void setMdAnaCnt(String mdAnaCnt) {
		this.mdAnaCnt = mdAnaCnt;
	}
	public String getMdErCnt() {
		return mdErCnt;
	}
	public void setMdErCnt(String mdErCnt) {
		this.mdErCnt = mdErCnt;
	}
	public String getMdNonRate() {
		return mdNonRate;
	}
	public void setMdNonRate(String mdNonRate) {
		this.mdNonRate = mdNonRate;
	}
	public String getMdErrRate() {
		return mdErrRate;
	}
	public void setMdErrRate(String mdErrRate) {
		this.mdErrRate = mdErrRate;
	}
	public String getDbConnTgrLnm() {
		return dbConnTgrLnm;
	}
	public void setDbConnTgrLnm(String dbConnTgrLnm) {
		this.dbConnTgrLnm = dbConnTgrLnm;
	}
	public String getDqiId() {
		return dqiId;
	}
	public void setDqiId(String dqiId) {
		this.dqiId = dqiId;
	}
	public String getAnaDgr() {
		return anaDgr;
	}
	public void setAnaDgr(String anaDgr) {
		this.anaDgr = anaDgr;
	}
	public String getBizDcd() {
		return bizDcd;
	}
	public void setBizDcd(String bizDcd) {
		this.bizDcd = bizDcd;
	}
	public String getUppDqiLnm() {
		return uppDqiLnm;
	}
	public void setUppDqiLnm(String uppDqiLnm) {
		this.uppDqiLnm = uppDqiLnm;
	}
	public String getTotAnaCnt() {
		return totAnaCnt;
	}
	public void setTotAnaCnt(String totAnaCnt) {
		this.totAnaCnt = totAnaCnt;
	}
	public String getTotNonRate() {
		return totNonRate;
	}
	public void setTotNonRate(String totNonRate) {
		this.totNonRate = totNonRate;
	}
	public String getTotErrRate() {
		return totErrRate;
	}
	public void setTotErrRate(String totErrRate) {
		this.totErrRate = totErrRate;
	}
}
