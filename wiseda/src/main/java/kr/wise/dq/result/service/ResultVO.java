package kr.wise.dq.result.service;

import java.math.BigDecimal;
import java.util.List;

import kr.wise.commons.cmm.CommonVo;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ProgChartVO.java
 * 3. Package  : kr.wise.dq.report.profile.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 18. 오후 3:27:30
 * </PRE>
 */ 
public class ResultVO extends CommonVo{
	 
	private String uppDqiLnm; 
	
	private String dqiLnm;	
	
	private String cntName;
	
	private BigDecimal totCnt;
	
	private BigDecimal errCnt;
	
	private Float errRate;
	
	private String errSql;
	
	private BigDecimal noExe;
	
	private BigDecimal colCnt;
	
	private BigDecimal tblCnt;
	
	private Float sumRate;
	
	private String dbConnTrgId;
	
	private String dbConnTrgPnm;
	
	private String dbConnTrgLnm;
	
	private String dbSchPnm;
	
	private String dbSchLnm;
	
	private String dbcTblNm; 
	
	private String dbcTblKorNm;
	
	private String dbcColNm;
	
	private String dbcColKorNm;
	
	private String dbSchId;
	
	private String anaStrDtm;
	
	private String anaEndDtm;
	
	private String prfKndCd;
	
	private String prfTyp;
	
	private String prfNm;
	
	private String prfId;
	
	private String prfYn;
	
	private String dataType;
	
	private String expYn;
	
	private String expRsnCntn;
	
	private String stndRate;
	
	private String goal;
	
	private List<ResultDataVO> errList;
	
	private List<ResultDataVO> errData;
	
	private String pcolnm;
	private String bcolnm;
	private String notIn;
	
	private int anaDgr;
	
	private String pdmTblPnm;
	private String pdmColPnm;
	private String gapStatus;
	
	private String sditmLnm;
	private String sditmPnm;
	private String objDescn;
	private String infotpLnm;
	
	private String dmnLnm;
	private String dmnPnm;
	private String dataLen;
	private String dataScal;

	
	private String sigma;
	
	private String searchBgnDe;
	private String searchEndDe;
	

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getStndRate() {
		return stndRate;
	}

	public void setStndRate(String stndRate) {
		this.stndRate = stndRate;
	}

	public String getCntName() {
		return cntName;
	}

	public void setCntName(String cntName) {
		this.cntName = cntName;
	}

	public BigDecimal getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(BigDecimal totCnt) {
		this.totCnt = totCnt;
	}

	public BigDecimal getErrCnt() {
		return errCnt;
	}

	public void setErrCnt(BigDecimal errCnt) {
		this.errCnt = errCnt;
	}

	public Float getErrRate() {
		return errRate;
	}

	public void setErrRate(Float errRate) {
		this.errRate = errRate;
	}

	public BigDecimal getNoExe() {
		return noExe;
	}

	public void setNoExe(BigDecimal noExe) {
		this.noExe = noExe;
	}

	public BigDecimal getColCnt() {
		return colCnt;
	}

	public void setColCnt(BigDecimal colCnt) {
		this.colCnt = colCnt;
	}

	public BigDecimal getTblCnt() {
		return tblCnt;
	}

	public void setTblCnt(BigDecimal tblCnt) {
		this.tblCnt = tblCnt;
	}

	public Float getSumRate() {
		return sumRate;
	}

	public void setSumRate(Float sumRate) {
		this.sumRate = sumRate;
	}

	public String getDqiLnm() {
		return dqiLnm;
	}

	public void setDqiLnm(String dqiLnm) {
		this.dqiLnm = dqiLnm;
	}

	public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}

	public String getDbSchPnm() {
		return dbSchPnm;
	}

	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
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

	public String getUppDqiLnm() {
		return uppDqiLnm;
	}

	public void setUppDqiLnm(String uppDqiLnm) {
		this.uppDqiLnm = uppDqiLnm;
	}

	public String getDbSchId() {
		return dbSchId;
	}

	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
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

	public String getDbSchLnm() {
		return dbSchLnm;
	}

	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}

	public String getAnaStrDtm() {
		return anaStrDtm;
	}

	public void setAnaStrDtm(String anaStrDtm) {
		this.anaStrDtm = anaStrDtm;
	}

	public String getAnaEndDtm() {
		return anaEndDtm;
	}

	public void setAnaEndDtm(String anaEndDtm) {
		this.anaEndDtm = anaEndDtm;
	}

	public String getErrSql() {
		return errSql;
	}

	public void setErrSql(String errSql) {
		this.errSql = errSql;
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

	public String getPrfKndCd() {
		return prfKndCd;
	}

	public void setPrfKndCd(String prfKndCd) {
		this.prfKndCd = prfKndCd;
	}

	public String getPrfTyp() {
		return prfTyp;
	}

	public void setPrfTyp(String prfTyp) {
		this.prfTyp = prfTyp;
	}

	public String getPrfNm() {
		return prfNm;
	}

	public void setPrfNm(String prfNm) {
		this.prfNm = prfNm;
	}

	public String getPrfId() {
		return prfId;
	}

	public void setPrfId(String prfId) {
		this.prfId = prfId;
	}

	public String getPrfYn() {
		return prfYn;
	}

	public void setPrfYn(String prfYn) {
		this.prfYn = prfYn;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
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

	public List<ResultDataVO> getErrData() {
		return errData;
	}

	public void setErrData(List<ResultDataVO> errData) {
		this.errData = errData;
	}

	public List<ResultDataVO> getErrList() {
		return errList;
	}

	public void setErrList(List<ResultDataVO> errList) {
		this.errList = errList;
	}

	public String getPcolnm() {
		return pcolnm;
	}

	public void setPcolnm(String pcolnm) {
		this.pcolnm = pcolnm;
	}
	
	public String getBcolnm() {
		return bcolnm;
	}

	public void setBcolnm(String bcolnm) {
		this.bcolnm = bcolnm;
	}

	public int getAnaDgr() {
		return anaDgr;
	}

	public void setAnaDgr(int anaDgr) {
		this.anaDgr = anaDgr;
	}

	public String getNotIn() {
		return notIn;
	}

	public void setNotIn(String notIn) {
		this.notIn = notIn;
	}

	public String getPdmTblPnm() {
		return pdmTblPnm;
	}

	public void setPdmTblPnm(String pdmTblPnm) {
		this.pdmTblPnm = pdmTblPnm;
	}

	public String getPdmColPnm() {
		return pdmColPnm;
	}

	public void setPdmColPnm(String pdmColPnm) {
		this.pdmColPnm = pdmColPnm;
	}

	public String getGapStatus() {
		return gapStatus;
	}

	public void setGapStatus(String gapStatus) {
		this.gapStatus = gapStatus;
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

	public String getObjDescn() {
		return objDescn;
	}

	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}

	public String getInfotpLnm() {
		return infotpLnm;
	}

	public void setInfotpLnm(String infotpLnm) {
		this.infotpLnm = infotpLnm;
	}

	public String getDmnLnm() {
		return dmnLnm;
	}

	public void setDmnLnm(String dmnLnm) {
		this.dmnLnm = dmnLnm;
	}

	public String getDmnPnm() {
		return dmnPnm;
	}

	public void setDmnPnm(String dmnPnm) {
		this.dmnPnm = dmnPnm;
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

	public String getSigma() {
		return sigma;
	}

	public void setSigma(String sigma) {
		this.sigma = sigma;
	}

	public String getSearchBgnDe() {
		return searchBgnDe;
	}

	public void setSearchBgnDe(String searchBgnDe) {
		this.searchBgnDe = searchBgnDe;
	}

	public String getSearchEndDe() {
		return searchEndDe;
	}

	public void setSearchEndDe(String searchEndDe) {
		this.searchEndDe = searchEndDe;
	}

	@Override
	public String toString() {
		return "ResultVO [uppDqiLnm=" + uppDqiLnm + ", dqiLnm=" + dqiLnm
				+ ", cntName=" + cntName + ", totCnt=" + totCnt + ", errCnt="
				+ errCnt + ", errRate=" + errRate + ", errSql=" + errSql
				+ ", noExe=" + noExe + ", colCnt=" + colCnt + ", tblCnt="
				+ tblCnt + ", sumRate=" + sumRate + ", dbConnTrgId="
				+ dbConnTrgId + ", dbConnTrgPnm=" + dbConnTrgPnm
				+ ", dbConnTrgLnm=" + dbConnTrgLnm + ", dbSchPnm=" + dbSchPnm
				+ ", dbSchLnm=" + dbSchLnm + ", dbcTblNm=" + dbcTblNm
				+ ", dbcTblKorNm=" + dbcTblKorNm + ", dbcColNm=" + dbcColNm
				+ ", dbcColKorNm=" + dbcColKorNm + ", dbSchId=" + dbSchId
				+ ", anaStrDtm=" + anaStrDtm + ", anaEndDtm=" + anaEndDtm
				+ ", prfKndCd=" + prfKndCd + ", prfTyp=" + prfTyp + ", prfNm="
				+ prfNm + ", prfId=" + prfId + ", prfYn=" + prfYn
				+ ", dataType=" + dataType + ", expYn=" + expYn
				+ ", expRsnCntn=" + expRsnCntn + ", stndRate=" + stndRate
				+ ", goal=" + goal + ", errList=" + errList + ", errData="
				+ errData + ", pcolnm=" + pcolnm + ", bcolnm=" + bcolnm
				+ ", notIn=" + notIn + ", anaDgr=" + anaDgr + ", pdmTblPnm="
				+ pdmTblPnm + ", pdmColPnm=" + pdmColPnm + ", gapStatus="
				+ gapStatus + ", sditmLnm=" + sditmLnm + ", sditmPnm="
				+ sditmPnm + ", objDescn=" + objDescn + ", infotpLnm="
				+ infotpLnm + ", dmnLnm=" + dmnLnm + ", dmnPnm=" + dmnPnm
				+ ", dataLen=" + dataLen + ", dataScal=" + dataScal
				+ ", sigma=" + sigma + ", searchBgnDe=" + searchBgnDe
				+ ", searchEndDe=" + searchEndDe + "]";
	}
	

}