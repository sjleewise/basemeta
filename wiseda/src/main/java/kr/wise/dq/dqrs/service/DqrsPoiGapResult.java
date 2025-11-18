package kr.wise.dq.dqrs.service;

import java.math.BigDecimal;
import java.util.List;

import kr.wise.commons.cmm.CommonVo;


public class DqrsPoiGapResult extends CommonVo{
	 
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
	
	private List<DqrsResultData> errList;
	
	private List<DqrsResultData> errData;
	
	private String pcolnm;
	private String bcolnm;
	private String notIn;
	
	private int anaDgr;
	
	private String pdmTblPnm;
	private String pdmTblLnm;
	private String pdmColPnm;
	private String pdmColLnm;
	private String gapStatus;
	
	private String sditmLnm;
	private String sditmPnm;
	private String objDescn;
	private String infotpLnm;
	
	//공통에서 사용하는 표준인 경우
	private String sditmDbConnTrgId;
	
	private String dmnLnm;
	private String dmnPnm;
	private String dataLen;
	private String dataScal;
	private String noNullYn;
	private String pkOrd;
	private String pkYn;
	
	private String itemNm;
	private String gapRate;
	private String resultDate;
	private String objCnt;
	private String errorCnt;
	
	private String govYn;
	private String orgYn;
	
	/////
	private String tblNm;
	private String state;
	private String detailStatus;
	private String colNm;
	
	private String stndType;
	
	private String maskingYn; 
	
	
	public String getSditmDbConnTrgId() {
		return sditmDbConnTrgId;
	}

	public void setSditmDbConnTrgId(String sditmDbConnTrgId) {
		this.sditmDbConnTrgId = sditmDbConnTrgId;
	}

	public String getMaskingYn() {
		return maskingYn;
	}

	public void setMaskingYn(String maskingYn) {
		this.maskingYn = maskingYn;
	}

	public String getStndType() {
		return stndType;
	}

	public void setStndType(String stndType) {
		this.stndType = stndType;
	}

	public String getPkOrd() {
		return pkOrd;
	}

	public void setPkOrd(String pkOrd) {
		this.pkOrd = pkOrd;
	}

	public String getPkYn() {
		return pkYn;
	}

	public void setPkYn(String pkYn) {
		this.pkYn = pkYn;
	}

	public String getNoNullYn() {
		return noNullYn;
	}

	public void setNoNullYn(String noNullYn) {
		this.noNullYn = noNullYn;
	}

	public String getPdmTblLnm() {
		return pdmTblLnm;
	}

	public void setPdmTblLnm(String pdmTblLnm) {
		this.pdmTblLnm = pdmTblLnm;
	}

	public String getPdmColLnm() {
		return pdmColLnm;
	}

	public void setPdmColLnm(String pdmColLnm) {
		this.pdmColLnm = pdmColLnm;
	}

	public String getTblNm() {
		return tblNm;
	}

	public void setTblNm(String tblNm) {
		this.tblNm = tblNm;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDetailStatus() {
		return detailStatus;
	}

	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}

	public String getColNm() {
		return colNm;
	}

	public void setColNm(String colNm) {
		this.colNm = colNm;
	}

	public String getGovYn() {
		return govYn;
	}

	public void setGovYn(String govYn) {
		this.govYn = govYn;
	}

	public String getOrgYn() {
		return orgYn;
	}

	public void setOrgYn(String orgYn) {
		this.orgYn = orgYn;
	}

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

	public List<DqrsResultData> getErrData() {
		return errData;
	}

	public void setErrData(List<DqrsResultData> errData) {
		this.errData = errData;
	}

	public List<DqrsResultData> getErrList() {
		return errList;
	}

	public void setErrList(List<DqrsResultData> errList) {
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

	public String getItemNm() {
		return itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	public String getGapRate() {
		return gapRate;
	}

	public void setGapRate(String gapRate) {
		this.gapRate = gapRate;
	}

	public String getResultDate() {
		return resultDate;
	}

	public void setResultDate(String resultDate) {
		this.resultDate = resultDate;
	}

	public String getObjCnt() {
		return objCnt;
	}

	public void setObjCnt(String objCnt) {
		this.objCnt = objCnt;
	}

	public String getErrorCnt() {
		return errorCnt;
	}

	public void setErrorCnt(String errorCnt) {
		this.errorCnt = errorCnt;
	}
	

	
	
	
	

}