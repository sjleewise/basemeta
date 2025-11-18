package kr.wise.advisor.prepare.stat.service;

import kr.wise.commons.cmm.CommonVo;

public class StatVo extends CommonVo {
	private String prfId;
	private String prfNm;
	private String dbcTblNm;
	private int anaDgr;
	private String dbcColNm;
	private String prfKndNm;
	private long anaCnt;
	private long esnErCnt;
	private String brId;
	private String brNm;
	private long erCnt;
	private String otlDtcId;
	private String dbColNm;
	private String otlNm;
	private int erRate;
	
	private String schDbConnTrgId;
	private String schDbSchId;
	private String schDbSchNm;
	private String schDbcTblNm;
	private String schDbcColNm;
	private String dbcTblKorNm;
	private String dbcColKorNm;
	
	private long clstCnt;
	private String clstId;
	private String mtcId;
	
	private String srcDbcTblNm;
	private String tgtDbcTblNm;
	private String srcDbcColNm;
	private String tgtDbcColNm;
	private String srcDbcTblKorNm;
	private String tgtDbcTblKorNm;
	private String srcDbcColKorNm;
	private String tgtDbcColKorNm;
	
	private String clstNm;
	private String clstSno;
	private long mtcSno;
	private long varCnt;
	
	private String mtchNm;
	
	public String getPrfId() {
		return prfId;
	}
	public void setPrfId(String prfId) {
		this.prfId = prfId;
	}
	public String getDbcTblNm() {
		return dbcTblNm;
	}
	public void setDbcTblNm(String dbcTblNm) {
		this.dbcTblNm = dbcTblNm;
	}
	public int getAnaDgr() {
		return anaDgr;
	}
	public void setAnaDgr(int anaDgr) {
		this.anaDgr = anaDgr;
	}
	public String getDbcColNm() {
		return dbcColNm;
	}
	public void setDbcColNm(String dbcColNm) {
		this.dbcColNm = dbcColNm;
	}
	public String getPrfKndNm() {
		return prfKndNm;
	}
	public void setPrfKndNm(String prfKndNm) {
		this.prfKndNm = prfKndNm;
	}
	public long getAnaCnt() {
		return anaCnt;
	}
	public void setAnaCnt(long anaCnt) {
		this.anaCnt = anaCnt;
	}
	public long getEsnErCnt() {
		return esnErCnt;
	}
	public void setEsnErCnt(long esnErCnt) {
		this.esnErCnt = esnErCnt;
	}
	public String getBrId() {
		return brId;
	}
	public void setBrId(String brId) {
		this.brId = brId;
	}
	public long getErCnt() {
		return erCnt;
	}
	public void setErCnt(long erCnt) {
		this.erCnt = erCnt;
	}
	public String getOtlDtcId() {
		return otlDtcId;
	}
	public void setOtlDtcId(String otlDtcId) {
		this.otlDtcId = otlDtcId;
	}
	public String getDbColNm() {
		return dbColNm;
	}
	public void setDbColNm(String dbColNm) {
		this.dbColNm = dbColNm;
	}
	public String getOtlNm() {
		return otlNm;
	}
	public void setOtlNm(String otlNm) {
		this.otlNm = otlNm;
	}
	public int getErRate() {
		return erRate;
	}
	public void setErRate(int erRate) {
		this.erRate = erRate;
	}
	public String getPrfNm() {
		return prfNm;
	}
	public void setPrfNm(String prfNm) {
		this.prfNm = prfNm;
	}
	public String getSchDbConnTrgId() {
		return schDbConnTrgId;
	}
	public void setSchDbConnTrgId(String schDbConnTrgId) {
		this.schDbConnTrgId = schDbConnTrgId;
	}
	public String getSchDbSchId() {
		return schDbSchId;
	}
	public void setSchDbSchId(String schDbSchId) {
		this.schDbSchId = schDbSchId;
	}
	public String getSchDbSchNm() {
		return schDbSchNm;
	}
	public void setSchDbSchNm(String schDbSchNm) {
		this.schDbSchNm = schDbSchNm;
	}
	public String getSchDbcTblNm() {
		return schDbcTblNm;
	}
	public void setSchDbcTblNm(String schDbcTblNm) {
		this.schDbcTblNm = schDbcTblNm;
	}
	public String getSchDbcColNm() {
		return schDbcColNm;
	}
	public void setSchDbcColNm(String schDbcColNm) {
		this.schDbcColNm = schDbcColNm;
	}
	public String getBrNm() {
		return brNm;
	}
	public void setBrNm(String brNm) {
		this.brNm = brNm;
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
	public long getClstCnt() {
		return clstCnt;
	}
	public void setClstCnt(long clstCnt) {
		this.clstCnt = clstCnt;
	}
	public String getMtcId() {
		return mtcId;
	}
	public void setMtcId(String mtcId) {
		this.mtcId = mtcId;
	}
	public String getSrcDbcTblNm() {
		return srcDbcTblNm;
	}
	public void setSrcDbcTblNm(String srcDbcTblNm) {
		this.srcDbcTblNm = srcDbcTblNm;
	}
	public String getTgtDbcTblNm() {
		return tgtDbcTblNm;
	}
	public void setTgtDbcTblNm(String tgtDbcTblNm) {
		this.tgtDbcTblNm = tgtDbcTblNm;
	}
	public String getSrcDbcColNm() {
		return srcDbcColNm;
	}
	public void setSrcDbcColNm(String srcDbcColNm) {
		this.srcDbcColNm = srcDbcColNm;
	}
	public String getTgtDbcColNm() {
		return tgtDbcColNm;
	}
	public void setTgtDbcColNm(String tgtDbcColNm) {
		this.tgtDbcColNm = tgtDbcColNm;
	}
	public String getSrcDbcTblKorNm() {
		return srcDbcTblKorNm;
	}
	public void setSrcDbcTblKorNm(String srcDbcTblKorNm) {
		this.srcDbcTblKorNm = srcDbcTblKorNm;
	}
	public String getTgtDbcTblKorNm() {
		return tgtDbcTblKorNm;
	}
	public void setTgtDbcTblKorNm(String tgtDbcTblKorNm) {
		this.tgtDbcTblKorNm = tgtDbcTblKorNm;
	}
	public String getSrcDbcColKorNm() {
		return srcDbcColKorNm;
	}
	public void setSrcDbcColKorNm(String srcDbcColKorNm) {
		this.srcDbcColKorNm = srcDbcColKorNm;
	}
	public String getTgtDbcColKorNm() {
		return tgtDbcColKorNm;
	}
	public void setTgtDbcColKorNm(String tgtDbcColKorNm) {
		this.tgtDbcColKorNm = tgtDbcColKorNm;
	}
	public String getClstNm() {
		return clstNm;
	}
	public void setClstNm(String clstNm) {
		this.clstNm = clstNm;
	}
	public long getMtcSno() {
		return mtcSno;
	}
	public void setMtcSno(long mtcSno) {
		this.mtcSno = mtcSno;
	}
	public long getVarCnt() {
		return varCnt;
	}
	public void setVarCnt(long varCnt) {
		this.varCnt = varCnt;
	}
	public String getMtchNm() {
		return mtchNm;
	}
	public void setMtchNm(String mtchNm) {
		this.mtchNm = mtchNm;
	}
	public String getClstId() {
		return clstId;
	}
	public void setClstId(String clstId) {
		this.clstId = clstId;
	}
	public String getClstSno() {
		return clstSno;
	}
	public void setClstSno(String clstSno) {
		this.clstSno = clstSno;
	}
}
