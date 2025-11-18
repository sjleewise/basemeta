package kr.wise.dq.report.profile.service;

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
public class ProgChartVO {

	
	private String maxAnaDgr;
	private String anaDgr;
	private String anaDgrDisp;
	private String prfKndCd;
	private String anaCnt;
	private String esnErCnt;
	private String erCnt;
	private String erRate;
	private String dpmo;
	private String sigma;
	private String totBrCnt;
	private String tgtBrCnt;
	private String csAnaCnt;
	private String imActCnt;
	private String anaStrDtm;
	private String anaTblCnt;
	private String anaColCnt;
	
	
	private String dbConnTrgId;
	private String dbConnTrgLnm;
	private String dbSchId;
	private String dbSchLnm;
	private String deptId;
	private String deptNm;
	private String deptFullPath;
	
	private String bizAreaId;
	private String bizAreaLnm;
	private String bizAreaFullPath;
	private String dqiId;
	private String dqiLnm;
	private String dqiFullPath;
	private String ctqId;
	private String ctqLnm;
	private String ctqFullPath;
	private String brNm;
	private String tgtVrfJoinCd;
	
	private String sortTyp;
	private String anaJobNm;
	private String dbcTblNm;
	private String dbcTblKorNm;
	private String dbcColNm;
	private String dbcColKorNm;
	
	private String prfTotCntDb;
	private String anaTblTotCntDb;
	private String anaColTotCntDb;
	private String anaCntDb;
	private String esnErCntDb;
	private String esnErRateDb;
	private String prfTotCntSch;
	private String anaTblTotCntSch;
	private String anaColTotCntSch;
	private String anaCntSch;
	private String esnErCntSch;
	private String esnErRateSch;
	private String prfTotCntTbl;
	private String anaTblTotCntTbl;
	private String anaColTotCntTbl;
	private String anaCntTbl;
	private String esnErCntTbl;
	private String esnErRateTbl;
	private String prfTotCntCol;
	private String anaTblTotCntCol;
	private String anaColTotCntCol;
	private String anaCntCol;
	private String esnErCntCol;
	private String esnErRateCol;
	
	private String prfCntPT01;
	private String prfAnaTgtCntPT01;
	private String prfAnaCntPT01;
	private String esnErCntPT01;
	private String esnErRatePT01;
	private String prfCntPT02;
	private String prfAnaTgtCntPT02;
	private String prfAnaCntPT02;
	private String esnErCntPT02;
	private String esnErRatePT02;
	private String prfCntPC01;
	private String prfAnaTgtCntPC01;
	private String prfAnaCntPC01;
	private String esnErCntPC01;
	private String esnErRatePC01;
	private String prfCntPC02;
	private String prfAnaTgtCntPC02;
	private String prfAnaCntPC02;
	private String esnErCntPC02;
	private String esnErRatePC02;
	private String prfCntPC03;
	private String prfAnaTgtCntPC03;
	private String prfAnaCntPC03;
	private String esnErCntPC03;
	private String esnErRatePC03;
	private String prfCntPC04;
	private String prfAnaTgtCntPC04;
	private String prfAnaCntPC04;
	private String esnErCntPC04;
	private String esnErRatePC04;
	private String prfCntPC05;
	private String prfAnaTgtCntPC05;
	private String prfAnaCntPC05;
	private String esnErCntPC05;
	private String esnErRatePC05;
	
	private String impRate;
	

    private String searchBgnDe;  //검색 시작일자
    
    private String searchEndDe;  //검색 종료일자
    
    
    
	
	
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
	public String getImpRate() {
		return impRate;
	}
	public void setImpRate(String impRate) {
		this.impRate = impRate;
	}
	public String getMaxAnaDgr() {
		return maxAnaDgr;
	}
	public void setMaxAnaDgr(String maxAnaDgr) {
		this.maxAnaDgr = maxAnaDgr;
	}
	public String getAnaDgr() {
		return anaDgr;
	}
	public void setAnaDgr(String anaDgr) {
		this.anaDgr = anaDgr;
	}
	public String getAnaDgrDisp() {
		return anaDgrDisp;
	}
	public void setAnaDgrDisp(String anaDgrDisp) {
		this.anaDgrDisp = anaDgrDisp;
	}
	public String getPrfKndCd() {
		return prfKndCd;
	}
	public void setPrfKndCd(String prfKndCd) {
		this.prfKndCd = prfKndCd;
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
	public String getErCnt() {
		return erCnt;
	}
	public void setErCnt(String erCnt) {
		this.erCnt = erCnt;
	}
	public String getErRate() {
		return erRate;
	}
	public void setErRate(String erRate) {
		this.erRate = erRate;
	}
	public String getDpmo() {
		return dpmo;
	}
	public void setDpmo(String dpmo) {
		this.dpmo = dpmo;
	}
	public String getSigma() {
		return sigma;
	}
	public void setSigma(String sigma) {
		this.sigma = sigma;
	}
	public String getTotBrCnt() {
		return totBrCnt;
	}
	public void setTotBrCnt(String totBrCnt) {
		this.totBrCnt = totBrCnt;
	}
	public String getTgtBrCnt() {
		return tgtBrCnt;
	}
	public void setTgtBrCnt(String tgtBrCnt) {
		this.tgtBrCnt = tgtBrCnt;
	}
	public String getCsAnaCnt() {
		return csAnaCnt;
	}
	public void setCsAnaCnt(String csAnaCnt) {
		this.csAnaCnt = csAnaCnt;
	}
	public String getImActCnt() {
		return imActCnt;
	}
	public void setImActCnt(String imActCnt) {
		this.imActCnt = imActCnt;
	}
	public String getAnaStrDtm() {
		return anaStrDtm;
	}
	public void setAnaStrDtm(String anaStrDtm) {
		this.anaStrDtm = anaStrDtm;
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
	public String getDbConnTrgId() {
		return dbConnTrgId;
	}
	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
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
	public String getDbSchLnm() {
		return dbSchLnm;
	}
	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getDeptFullPath() {
		return deptFullPath;
	}
	public void setDeptFullPath(String deptFullPath) {
		this.deptFullPath = deptFullPath;
	}
	public String getBizAreaId() {
		return bizAreaId;
	}
	public void setBizAreaId(String bizAreaId) {
		this.bizAreaId = bizAreaId;
	}
	public String getBizAreaLnm() {
		return bizAreaLnm;
	}
	public void setBizAreaLnm(String bizAreaLnm) {
		this.bizAreaLnm = bizAreaLnm;
	}
	public String getBizAreaFullPath() {
		return bizAreaFullPath;
	}
	public void setBizAreaFullPath(String bizAreaFullPath) {
		this.bizAreaFullPath = bizAreaFullPath;
	}
	public String getDqiId() {
		return dqiId;
	}
	public void setDqiId(String dqiId) {
		this.dqiId = dqiId;
	}
	public String getDqiLnm() {
		return dqiLnm;
	}
	public void setDqiLnm(String dqiLnm) {
		this.dqiLnm = dqiLnm;
	}
	public String getDqiFullPath() {
		return dqiFullPath;
	}
	public void setDqiFullPath(String dqiFullPath) {
		this.dqiFullPath = dqiFullPath;
	}
	public String getCtqId() {
		return ctqId;
	}
	public void setCtqId(String ctqId) {
		this.ctqId = ctqId;
	}
	public String getCtqLnm() {
		return ctqLnm;
	}
	public void setCtqLnm(String ctqLnm) {
		this.ctqLnm = ctqLnm;
	}
	public String getCtqFullPath() {
		return ctqFullPath;
	}
	public void setCtqFullPath(String ctqFullPath) {
		this.ctqFullPath = ctqFullPath;
	}
	public String getBrNm() {
		return brNm;
	}
	public void setBrNm(String brNm) {
		this.brNm = brNm;
	}
	public String getSortTyp() {
		return sortTyp;
	}
	public void setSortTyp(String sortTyp) {
		this.sortTyp = sortTyp;
	}
	public String getAnaJobNm() {
		return anaJobNm;
	}
	public void setAnaJobNm(String anaJobNm) {
		this.anaJobNm = anaJobNm;
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
	public String getPrfTotCntDb() {
		return prfTotCntDb;
	}
	public void setPrfTotCntDb(String prfTotCntDb) {
		this.prfTotCntDb = prfTotCntDb;
	}
	public String getAnaTblTotCntDb() {
		return anaTblTotCntDb;
	}
	public void setAnaTblTotCntDb(String anaTblTotCntDb) {
		this.anaTblTotCntDb = anaTblTotCntDb;
	}
	public String getAnaColTotCntDb() {
		return anaColTotCntDb;
	}
	public void setAnaColTotCntDb(String anaColTotCntDb) {
		this.anaColTotCntDb = anaColTotCntDb;
	}
	public String getAnaCntDb() {
		return anaCntDb;
	}
	public void setAnaCntDb(String anaCntDb) {
		this.anaCntDb = anaCntDb;
	}
	public String getEsnErCntDb() {
		return esnErCntDb;
	}
	public void setEsnErCntDb(String esnErCntDb) {
		this.esnErCntDb = esnErCntDb;
	}
	public String getEsnErRateDb() {
		return esnErRateDb;
	}
	public void setEsnErRateDb(String esnErRateDb) {
		this.esnErRateDb = esnErRateDb;
	}
	public String getPrfTotCntSch() {
		return prfTotCntSch;
	}
	public void setPrfTotCntSch(String prfTotCntSch) {
		this.prfTotCntSch = prfTotCntSch;
	}
	public String getAnaTblTotCntSch() {
		return anaTblTotCntSch;
	}
	public void setAnaTblTotCntSch(String anaTblTotCntSch) {
		this.anaTblTotCntSch = anaTblTotCntSch;
	}
	public String getAnaColTotCntSch() {
		return anaColTotCntSch;
	}
	public void setAnaColTotCntSch(String anaColTotCntSch) {
		this.anaColTotCntSch = anaColTotCntSch;
	}
	public String getAnaCntSch() {
		return anaCntSch;
	}
	public void setAnaCntSch(String anaCntSch) {
		this.anaCntSch = anaCntSch;
	}
	public String getEsnErCntSch() {
		return esnErCntSch;
	}
	public void setEsnErCntSch(String esnErCntSch) {
		this.esnErCntSch = esnErCntSch;
	}
	public String getEsnErRateSch() {
		return esnErRateSch;
	}
	public void setEsnErRateSch(String esnErRateSch) {
		this.esnErRateSch = esnErRateSch;
	}
	public String getPrfTotCntTbl() {
		return prfTotCntTbl;
	}
	public void setPrfTotCntTbl(String prfTotCntTbl) {
		this.prfTotCntTbl = prfTotCntTbl;
	}
	public String getAnaTblTotCntTbl() {
		return anaTblTotCntTbl;
	}
	public void setAnaTblTotCntTbl(String anaTblTotCntTbl) {
		this.anaTblTotCntTbl = anaTblTotCntTbl;
	}
	public String getAnaColTotCntTbl() {
		return anaColTotCntTbl;
	}
	public void setAnaColTotCntTbl(String anaColTotCntTbl) {
		this.anaColTotCntTbl = anaColTotCntTbl;
	}
	public String getAnaCntTbl() {
		return anaCntTbl;
	}
	public void setAnaCntTbl(String anaCntTbl) {
		this.anaCntTbl = anaCntTbl;
	}
	public String getEsnErCntTbl() {
		return esnErCntTbl;
	}
	public void setEsnErCntTbl(String esnErCntTbl) {
		this.esnErCntTbl = esnErCntTbl;
	}
	public String getEsnErRateTbl() {
		return esnErRateTbl;
	}
	public void setEsnErRateTbl(String esnErRateTbl) {
		this.esnErRateTbl = esnErRateTbl;
	}
	public String getPrfTotCntCol() {
		return prfTotCntCol;
	}
	public void setPrfTotCntCol(String prfTotCntCol) {
		this.prfTotCntCol = prfTotCntCol;
	}
	public String getAnaTblTotCntCol() {
		return anaTblTotCntCol;
	}
	public void setAnaTblTotCntCol(String anaTblTotCntCol) {
		this.anaTblTotCntCol = anaTblTotCntCol;
	}
	public String getAnaColTotCntCol() {
		return anaColTotCntCol;
	}
	public void setAnaColTotCntCol(String anaColTotCntCol) {
		this.anaColTotCntCol = anaColTotCntCol;
	}
	public String getAnaCntCol() {
		return anaCntCol;
	}
	public void setAnaCntCol(String anaCntCol) {
		this.anaCntCol = anaCntCol;
	}
	public String getEsnErCntCol() {
		return esnErCntCol;
	}
	public void setEsnErCntCol(String esnErCntCol) {
		this.esnErCntCol = esnErCntCol;
	}
	public String getEsnErRateCol() {
		return esnErRateCol;
	}
	public void setEsnErRateCol(String esnErRateCol) {
		this.esnErRateCol = esnErRateCol;
	}
	public String getPrfCntPT01() {
		return prfCntPT01;
	}
	public void setPrfCntPT01(String prfCntPT01) {
		this.prfCntPT01 = prfCntPT01;
	}
	public String getPrfAnaTgtCntPT01() {
		return prfAnaTgtCntPT01;
	}
	public void setPrfAnaTgtCntPT01(String prfAnaTgtCntPT01) {
		this.prfAnaTgtCntPT01 = prfAnaTgtCntPT01;
	}
	public String getPrfAnaCntPT01() {
		return prfAnaCntPT01;
	}
	public void setPrfAnaCntPT01(String prfAnaCntPT01) {
		this.prfAnaCntPT01 = prfAnaCntPT01;
	}
	public String getEsnErCntPT01() {
		return esnErCntPT01;
	}
	public void setEsnErCntPT01(String esnErCntPT01) {
		this.esnErCntPT01 = esnErCntPT01;
	}
	public String getEsnErRatePT01() {
		return esnErRatePT01;
	}
	public void setEsnErRatePT01(String esnErRatePT01) {
		this.esnErRatePT01 = esnErRatePT01;
	}
	public String getPrfCntPT02() {
		return prfCntPT02;
	}
	public void setPrfCntPT02(String prfCntPT02) {
		this.prfCntPT02 = prfCntPT02;
	}
	public String getPrfAnaTgtCntPT02() {
		return prfAnaTgtCntPT02;
	}
	public void setPrfAnaTgtCntPT02(String prfAnaTgtCntPT02) {
		this.prfAnaTgtCntPT02 = prfAnaTgtCntPT02;
	}
	public String getPrfAnaCntPT02() {
		return prfAnaCntPT02;
	}
	public void setPrfAnaCntPT02(String prfAnaCntPT02) {
		this.prfAnaCntPT02 = prfAnaCntPT02;
	}
	public String getEsnErCntPT02() {
		return esnErCntPT02;
	}
	public void setEsnErCntPT02(String esnErCntPT02) {
		this.esnErCntPT02 = esnErCntPT02;
	}
	public String getEsnErRatePT02() {
		return esnErRatePT02;
	}
	public void setEsnErRatePT02(String esnErRatePT02) {
		this.esnErRatePT02 = esnErRatePT02;
	}
	public String getPrfCntPC01() {
		return prfCntPC01;
	}
	public void setPrfCntPC01(String prfCntPC01) {
		this.prfCntPC01 = prfCntPC01;
	}
	public String getPrfAnaTgtCntPC01() {
		return prfAnaTgtCntPC01;
	}
	public void setPrfAnaTgtCntPC01(String prfAnaTgtCntPC01) {
		this.prfAnaTgtCntPC01 = prfAnaTgtCntPC01;
	}
	public String getPrfAnaCntPC01() {
		return prfAnaCntPC01;
	}
	public void setPrfAnaCntPC01(String prfAnaCntPC01) {
		this.prfAnaCntPC01 = prfAnaCntPC01;
	}
	public String getEsnErCntPC01() {
		return esnErCntPC01;
	}
	public void setEsnErCntPC01(String esnErCntPC01) {
		this.esnErCntPC01 = esnErCntPC01;
	}
	public String getEsnErRatePC01() {
		return esnErRatePC01;
	}
	public void setEsnErRatePC01(String esnErRatePC01) {
		this.esnErRatePC01 = esnErRatePC01;
	}
	public String getPrfCntPC02() {
		return prfCntPC02;
	}
	public void setPrfCntPC02(String prfCntPC02) {
		this.prfCntPC02 = prfCntPC02;
	}
	public String getPrfAnaTgtCntPC02() {
		return prfAnaTgtCntPC02;
	}
	public void setPrfAnaTgtCntPC02(String prfAnaTgtCntPC02) {
		this.prfAnaTgtCntPC02 = prfAnaTgtCntPC02;
	}
	public String getPrfAnaCntPC02() {
		return prfAnaCntPC02;
	}
	public void setPrfAnaCntPC02(String prfAnaCntPC02) {
		this.prfAnaCntPC02 = prfAnaCntPC02;
	}
	public String getEsnErCntPC02() {
		return esnErCntPC02;
	}
	public void setEsnErCntPC02(String esnErCntPC02) {
		this.esnErCntPC02 = esnErCntPC02;
	}
	public String getEsnErRatePC02() {
		return esnErRatePC02;
	}
	public void setEsnErRatePC02(String esnErRatePC02) {
		this.esnErRatePC02 = esnErRatePC02;
	}
	public String getPrfCntPC03() {
		return prfCntPC03;
	}
	public void setPrfCntPC03(String prfCntPC03) {
		this.prfCntPC03 = prfCntPC03;
	}
	public String getPrfAnaTgtCntPC03() {
		return prfAnaTgtCntPC03;
	}
	public void setPrfAnaTgtCntPC03(String prfAnaTgtCntPC03) {
		this.prfAnaTgtCntPC03 = prfAnaTgtCntPC03;
	}
	public String getPrfAnaCntPC03() {
		return prfAnaCntPC03;
	}
	public void setPrfAnaCntPC03(String prfAnaCntPC03) {
		this.prfAnaCntPC03 = prfAnaCntPC03;
	}
	public String getEsnErCntPC03() {
		return esnErCntPC03;
	}
	public void setEsnErCntPC03(String esnErCntPC03) {
		this.esnErCntPC03 = esnErCntPC03;
	}
	public String getEsnErRatePC03() {
		return esnErRatePC03;
	}
	public void setEsnErRatePC03(String esnErRatePC03) {
		this.esnErRatePC03 = esnErRatePC03;
	}
	public String getPrfCntPC04() {
		return prfCntPC04;
	}
	public void setPrfCntPC04(String prfCntPC04) {
		this.prfCntPC04 = prfCntPC04;
	}
	public String getPrfAnaTgtCntPC04() {
		return prfAnaTgtCntPC04;
	}
	public void setPrfAnaTgtCntPC04(String prfAnaTgtCntPC04) {
		this.prfAnaTgtCntPC04 = prfAnaTgtCntPC04;
	}
	public String getPrfAnaCntPC04() {
		return prfAnaCntPC04;
	}
	public void setPrfAnaCntPC04(String prfAnaCntPC04) {
		this.prfAnaCntPC04 = prfAnaCntPC04;
	}
	public String getEsnErCntPC04() {
		return esnErCntPC04;
	}
	public void setEsnErCntPC04(String esnErCntPC04) {
		this.esnErCntPC04 = esnErCntPC04;
	}
	public String getEsnErRatePC04() {
		return esnErRatePC04;
	}
	public void setEsnErRatePC04(String esnErRatePC04) {
		this.esnErRatePC04 = esnErRatePC04;
	}
	public String getPrfCntPC05() {
		return prfCntPC05;
	}
	public void setPrfCntPC05(String prfCntPC05) {
		this.prfCntPC05 = prfCntPC05;
	}
	public String getPrfAnaTgtCntPC05() {
		return prfAnaTgtCntPC05;
	}
	public void setPrfAnaTgtCntPC05(String prfAnaTgtCntPC05) {
		this.prfAnaTgtCntPC05 = prfAnaTgtCntPC05;
	}
	public String getPrfAnaCntPC05() {
		return prfAnaCntPC05;
	}
	public void setPrfAnaCntPC05(String prfAnaCntPC05) {
		this.prfAnaCntPC05 = prfAnaCntPC05;
	}
	public String getEsnErCntPC05() {
		return esnErCntPC05;
	}
	public void setEsnErCntPC05(String esnErCntPC05) {
		this.esnErCntPC05 = esnErCntPC05;
	}
	public String getEsnErRatePC05() {
		return esnErRatePC05;
	}
	public void setEsnErRatePC05(String esnErRatePC05) {
		this.esnErRatePC05 = esnErRatePC05;
	}
	public String getTgtVrfJoinCd() {
		return tgtVrfJoinCd;
	}
	public void setTgtVrfJoinCd(String tgtVrfJoinCd) {
		this.tgtVrfJoinCd = tgtVrfJoinCd;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProgChartVO [maxAnaDgr=").append(maxAnaDgr)
				.append(", anaDgr=").append(anaDgr).append(", anaDgrDisp=")
				.append(anaDgrDisp).append(", prfKndCd=").append(prfKndCd)
				.append(", anaCnt=").append(anaCnt).append(", esnErCnt=")
				.append(esnErCnt).append(", erCnt=").append(erCnt)
				.append(", erRate=").append(erRate).append(", dpmo=")
				.append(dpmo).append(", sigma=").append(sigma)
				.append(", totBrCnt=").append(totBrCnt).append(", tgtBrCnt=")
				.append(tgtBrCnt).append(", csAnaCnt=").append(csAnaCnt)
				.append(", imActCnt=").append(imActCnt).append(", anaStrDtm=")
				.append(anaStrDtm).append(", anaTblCnt=").append(anaTblCnt)
				.append(", anaColCnt=").append(anaColCnt)
				.append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", dbConnTrgLnm=").append(dbConnTrgLnm)
				.append(", dbSchId=").append(dbSchId).append(", dbSchLnm=")
				.append(dbSchLnm).append(", deptId=").append(deptId)
				.append(", deptNm=").append(deptNm).append(", deptFullPath=")
				.append(deptFullPath).append(", bizAreaId=").append(bizAreaId)
				.append(", bizAreaLnm=").append(bizAreaLnm)
				.append(", bizAreaFullPath=").append(bizAreaFullPath)
				.append(", dqiId=").append(dqiId).append(", dqiLnm=")
				.append(dqiLnm).append(", dqiFullPath=").append(dqiFullPath)
				.append(", ctqId=").append(ctqId).append(", ctqLnm=")
				.append(ctqLnm).append(", ctqFullPath=").append(ctqFullPath)
				.append(", brNm=").append(brNm).append(", sortTyp=")
				.append(sortTyp).append(", anaJobNm=").append(anaJobNm)
				.append(", dbcTblNm=").append(dbcTblNm)
				.append(", dbcTblKorNm=").append(dbcTblKorNm)
				.append(", dbcColNm=").append(dbcColNm)
				.append(", dbcColKorNm=").append(dbcColKorNm)
				.append(", prfTotCntDb=").append(prfTotCntDb)
				.append(", anaTblTotCntDb=").append(anaTblTotCntDb)
				.append(", anaColTotCntDb=").append(anaColTotCntDb)
				.append(", anaCntDb=").append(anaCntDb).append(", esnErCntDb=")
				.append(esnErCntDb).append(", esnErRateDb=")
				.append(esnErRateDb).append(", prfTotCntSch=")
				.append(prfTotCntSch).append(", anaTblTotCntSch=")
				.append(anaTblTotCntSch).append(", anaColTotCntSch=")
				.append(anaColTotCntSch).append(", anaCntSch=")
				.append(anaCntSch).append(", esnErCntSch=").append(esnErCntSch)
				.append(", esnErRateSch=").append(esnErRateSch)
				.append(", prfTotCntTbl=").append(prfTotCntTbl)
				.append(", anaTblTotCntTbl=").append(anaTblTotCntTbl)
				.append(", anaColTotCntTbl=").append(anaColTotCntTbl)
				.append(", anaCntTbl=").append(anaCntTbl)
				.append(", esnErCntTbl=").append(esnErCntTbl)
				.append(", esnErRateTbl=").append(esnErRateTbl)
				.append(", prfTotCntCol=").append(prfTotCntCol)
				.append(", anaTblTotCntCol=").append(anaTblTotCntCol)
				.append(", anaColTotCntCol=").append(anaColTotCntCol)
				.append(", anaCntCol=").append(anaCntCol)
				.append(", esnErCntCol=").append(esnErCntCol)
				.append(", esnErRateCol=").append(esnErRateCol)
				.append(", prfCntPT01=").append(prfCntPT01)
				.append(", prfAnaTgtCntPT01=").append(prfAnaTgtCntPT01)
				.append(", prfAnaCntPT01=").append(prfAnaCntPT01)
				.append(", esnErCntPT01=").append(esnErCntPT01)
				.append(", esnErRatePT01=").append(esnErRatePT01)
				.append(", prfCntPT02=").append(prfCntPT02)
				.append(", prfAnaTgtCntPT02=").append(prfAnaTgtCntPT02)
				.append(", prfAnaCntPT02=").append(prfAnaCntPT02)
				.append(", esnErCntPT02=").append(esnErCntPT02)
				.append(", esnErRatePT02=").append(esnErRatePT02)
				.append(", prfCntPC01=").append(prfCntPC01)
				.append(", prfAnaTgtCntPC01=").append(prfAnaTgtCntPC01)
				.append(", prfAnaCntPC01=").append(prfAnaCntPC01)
				.append(", esnErCntPC01=").append(esnErCntPC01)
				.append(", esnErRatePC01=").append(esnErRatePC01)
				.append(", prfCntPC02=").append(prfCntPC02)
				.append(", prfAnaTgtCntPC02=").append(prfAnaTgtCntPC02)
				.append(", prfAnaCntPC02=").append(prfAnaCntPC02)
				.append(", esnErCntPC02=").append(esnErCntPC02)
				.append(", esnErRatePC02=").append(esnErRatePC02)
				.append(", prfCntPC03=").append(prfCntPC03)
				.append(", prfAnaTgtCntPC03=").append(prfAnaTgtCntPC03)
				.append(", prfAnaCntPC03=").append(prfAnaCntPC03)
				.append(", esnErCntPC03=").append(esnErCntPC03)
				.append(", esnErRatePC03=").append(esnErRatePC03)
				.append(", prfCntPC04=").append(prfCntPC04)
				.append(", prfAnaTgtCntPC04=").append(prfAnaTgtCntPC04)
				.append(", prfAnaCntPC04=").append(prfAnaCntPC04)
				.append(", esnErCntPC04=").append(esnErCntPC04)
				.append(", esnErRatePC04=").append(esnErRatePC04)
				.append(", prfCntPC05=").append(prfCntPC05)
				.append(", prfAnaTgtCntPC05=").append(prfAnaTgtCntPC05)
				.append(", prfAnaCntPC05=").append(prfAnaCntPC05)
				.append(", esnErCntPC05=").append(esnErCntPC05)
				.append(", esnErRatePC05=").append(esnErRatePC05).append("]");
		return builder.toString();
	}
	
	
		
	
	
	
	
}