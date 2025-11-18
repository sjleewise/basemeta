package kr.wise.dq.profile.mstr.service;

import kr.wise.commons.cmm.CommonVo;

public class WamPrfMstrCommonVO extends CommonVo{

	private Integer anaDgr;
	private String anaStrDtm;
    private String dbSchId;
    private String dbSchLnm;
    private String dbSchPnm;
    private String dbcTblNm;
    private String dbcTblKorNm;
    private Integer ord;
    private String dbcColNm;
    private String dbcColKorNm;
    private String colErrRate;
    private String dpmo;
    private String sigma;
    private String anaCnt;
    private String esnErCnt;
    private String prfKndCd;
    private String prfKndCdNm;
    private String dbConnTrgLnm;
    private String dbConnTrgPnm;
    //조회조건
    private String dbConnTrgId;
    private String schRegYn;
    
    private String prfId;
    
	private String shdJobId;
	
	private String etcJobNm;

    //관계분석
    private String objNm;
    private String chTblDbcColNm;
    private String paTblDbcTblNm;
    private String paTblDbcTblKorNm;
    private String paTblDbcColNm;
    private String relColNm;

    //컬럼분석
    private String crdAnaYn;
    private String minMaxValAnaYn;
    private String aonlYn;
    private String lenAnaYn;
    private Integer nullCnt;
    private Integer spaceCnt;
    private String minVal1;
    private String minVal2;
    private String minVal3;
    private String maxVal1;
    private String maxVal2;
    private String maxVal3;
    private Integer minLen;
    private Integer maxLen;

    //유효값분석
    private String efvaAnaKndCd;
    private String userDfEfva;
    private String cdTblDbcTblNm;
    private String cdTblDbcColNm;
    private String cdTblCdIdColNm;
    private String cdTblCdId;
    private String cdTblAdtCndSql;

    //날짜분석
    private String dateFrmCd;
    private String userDateFrm;

    //범위분석
    private String prfRng;

    //패턴분석
    private String userDfPtr;
    
    private String dqiLnm;
    private String dqiId;
    
    
    private String stndRate;
    
    private String errCntSql;
    
    private String stddevVal;
    private String varianceVal;
    private String avgVal;
    private String unqCnt;
    private String minCntVal;
    private String maxCntVal;
    
    //검증룰 춤질추이를 위해 추가
    private String vrfcTyp;

    
	public String getVrfcTyp() {
		return vrfcTyp;
	}
	public void setVrfcTyp(String vrfcTyp) {
		this.vrfcTyp = vrfcTyp;
	}
	public String getStddevVal() {
		return stddevVal;
	}
	public void setStddevVal(String stddevVal) {
		this.stddevVal = stddevVal;
	}
	public String getVarianceVal() {
		return varianceVal;
	}
	public void setVarianceVal(String varianceVal) {
		this.varianceVal = varianceVal;
	}
	public String getAvgVal() {
		return avgVal;
	}
	public void setAvgVal(String avgVal) {
		this.avgVal = avgVal;
	}
	public String getUnqCnt() {
		return unqCnt;
	}
	public void setUnqCnt(String unqCnt) {
		this.unqCnt = unqCnt;
	}
	public String getMinCntVal() {
		return minCntVal;
	}
	public void setMinCntVal(String minCntVal) {
		this.minCntVal = minCntVal;
	}
	public String getMaxCntVal() {
		return maxCntVal;
	}
	public void setMaxCntVal(String maxCntVal) {
		this.maxCntVal = maxCntVal;
	}
	public String getDbSchPnm() {
		return dbSchPnm;
	}
	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
	}
	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}
	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}
	public Integer getAnaDgr() {
		return anaDgr;
	}
	public void setAnaDgr(Integer anaDgr) {
		this.anaDgr = anaDgr;
	}
	public String getAnaStrDtm() {
		return anaStrDtm;
	}
	public void setAnaStrDtm(String anaStrDtm) {
		this.anaStrDtm = anaStrDtm;
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
	public Integer getOrd() {
		return ord;
	}
	public void setOrd(Integer ord) {
		this.ord = ord;
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
	public String getColErrRate() {
		return colErrRate;
	}
	public void setColErrRate(String colErrRate) {
		this.colErrRate = colErrRate;
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
	public String getPrfKndCd() {
		return prfKndCd;
	}
	public void setPrfKndCd(String prfKndCd) {
		this.prfKndCd = prfKndCd;
	}
	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}
	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
	}
	public String getPrfId() {
		return prfId;
	}
	public void setPrfId(String prfId) {
		this.prfId = prfId;
	}
	public String getObjNm() {
		return objNm;
	}
	public void setObjNm(String objNm) {
		this.objNm = objNm;
	}
	public String getChTblDbcColNm() {
		return chTblDbcColNm;
	}
	public void setChTblDbcColNm(String chTblDbcColNm) {
		this.chTblDbcColNm = chTblDbcColNm;
	}
	public String getPaTblDbcTblNm() {
		return paTblDbcTblNm;
	}
	public void setPaTblDbcTblNm(String paTblDbcTblNm) {
		this.paTblDbcTblNm = paTblDbcTblNm;
	}
	public String getPaTblDbcColNm() {
		return paTblDbcColNm;
	}
	public void setPaTblDbcColNm(String paTblDbcColNm) {
		this.paTblDbcColNm = paTblDbcColNm;
	}
	public String getRelColNm() {
		return relColNm;
	}
	public void setRelColNm(String relColNm) {
		this.relColNm = relColNm;
	}
	public String getCrdAnaYn() {
		return crdAnaYn;
	}
	public void setCrdAnaYn(String crdAnaYn) {
		this.crdAnaYn = crdAnaYn;
	}
	public String getMinMaxValAnaYn() {
		return minMaxValAnaYn;
	}
	public void setMinMaxValAnaYn(String minMaxValAnaYn) {
		this.minMaxValAnaYn = minMaxValAnaYn;
	}
	public String getAonlYn() {
		return aonlYn;
	}
	public void setAonlYn(String aonlYn) {
		this.aonlYn = aonlYn;
	}
	public String getLenAnaYn() {
		return lenAnaYn;
	}
	public void setLenAnaYn(String lenAnaYn) {
		this.lenAnaYn = lenAnaYn;
	}
	public String getEfvaAnaKndCd() {
		return efvaAnaKndCd;
	}
	public void setEfvaAnaKndCd(String efvaAnaKndCd) {
		this.efvaAnaKndCd = efvaAnaKndCd;
	}
	public String getUserDfEfva() {
		return userDfEfva;
	}
	public void setUserDfEfva(String userDfEfva) {
		this.userDfEfva = userDfEfva;
	}
	public String getCdTblDbcTblNm() {
		return cdTblDbcTblNm;
	}
	public void setCdTblDbcTblNm(String cdTblDbcTblNm) {
		this.cdTblDbcTblNm = cdTblDbcTblNm;
	}
	public String getCdTblDbcColNm() {
		return cdTblDbcColNm;
	}
	public void setCdTblDbcColNm(String cdTblDbcColNm) {
		this.cdTblDbcColNm = cdTblDbcColNm;
	}
	public String getCdTblCdIdColNm() {
		return cdTblCdIdColNm;
	}
	public void setCdTblCdIdColNm(String cdTblCdIdColNm) {
		this.cdTblCdIdColNm = cdTblCdIdColNm;
	}
	public String getCdTblCdId() {
		return cdTblCdId;
	}
	public void setCdTblCdId(String cdTblCdId) {
		this.cdTblCdId = cdTblCdId;
	}
	public String getCdTblAdtCndSql() {
		return cdTblAdtCndSql;
	}
	public void setCdTblAdtCndSql(String cdTblAdtCndSql) {
		this.cdTblAdtCndSql = cdTblAdtCndSql;
	}
	public String getDateFrmCd() {
		return dateFrmCd;
	}
	public void setDateFrmCd(String dateFrmCd) {
		this.dateFrmCd = dateFrmCd;
	}
	public String getUserDateFrm() {
		return userDateFrm;
	}
	public void setUserDateFrm(String userDateFrm) {
		this.userDateFrm = userDateFrm;
	}
	public String getPrfRng() {
		return prfRng;
	}
	public void setPrfRng(String prfRng) {
		this.prfRng = prfRng;
	}
	public String getUserDfPtr() {
		return userDfPtr;
	}
	public void setUserDfPtr(String userDfPtr) {
		this.userDfPtr = userDfPtr;
	}
	public String getDbConnTrgId() {
		return dbConnTrgId;
	}
	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}
	public String getSchRegYn() {
		return schRegYn;
	}
	public void setSchRegYn(String schRegYn) {
		this.schRegYn = schRegYn;
	}
	public String getPaTblDbcTblKorNm() {
		return paTblDbcTblKorNm;
	}
	public void setPaTblDbcTblKorNm(String paTblDbcTblKorNm) {
		this.paTblDbcTblKorNm = paTblDbcTblKorNm;
	}

	public String getShdJobId() {
		return shdJobId;
	}
	public void setShdJobId(String shdJobId) {
		this.shdJobId = shdJobId;
	}

	public Integer getNullCnt() {
		return nullCnt;
	}
	public void setNullCnt(Integer nullCnt) {
		this.nullCnt = nullCnt;
	}
	public Integer getSpaceCnt() {
		return spaceCnt;
	}
	public void setSpaceCnt(Integer spaceCnt) {
		this.spaceCnt = spaceCnt;
	}
	public String getMinVal1() {
		return minVal1;
	}
	public void setMinVal1(String minVal1) {
		this.minVal1 = minVal1;
	}
	public String getMinVal2() {
		return minVal2;
	}
	public void setMinVal2(String minVal2) {
		this.minVal2 = minVal2;
	}
	public String getMinVal3() {
		return minVal3;
	}
	public void setMinVal3(String minVal3) {
		this.minVal3 = minVal3;
	}
	public String getMaxVal1() {
		return maxVal1;
	}
	public void setMaxVal1(String maxVal1) {
		this.maxVal1 = maxVal1;
	}
	public String getMaxVal2() {
		return maxVal2;
	}
	public void setMaxVal2(String maxVal2) {
		this.maxVal2 = maxVal2;
	}
	public String getMaxVal3() {
		return maxVal3;
	}
	public void setMaxVal3(String maxVal3) {
		this.maxVal3 = maxVal3;
	}
	public Integer getMinLen() {
		return minLen;
	}
	public void setMinLen(Integer minLen) {
		this.minLen = minLen;
	}
	public Integer getMaxLen() {
		return maxLen;
	}
	public void setMaxLen(Integer maxLen) {
		this.maxLen = maxLen;
	}
	public String getPrfKndCdNm() {
		return prfKndCdNm;
	}
	public void setPrfKndCdNm(String prfKndCdNm) {
		this.prfKndCdNm = prfKndCdNm;
	}
	
	public String getEtcJobNm() {
		return etcJobNm;
	}
	public void setEtcJobNm(String etcJobNm) {
		this.etcJobNm = etcJobNm;
	}
	public String getDqiLnm() {
		return dqiLnm;
	}
	public void setDqiLnm(String dqiLnm) {
		this.dqiLnm = dqiLnm;
	}
	public String getDqiId() {
		return dqiId;
	}
	public void setDqiId(String dqiId) {
		this.dqiId = dqiId;
	}
	public String getStndRate() {
		return stndRate;
	}
	public void setStndRate(String stndRate) {
		this.stndRate = stndRate;
	}
	public String getErrCntSql() {
		return errCntSql;
	}
	public void setErrCntSql(String errCntSql) {
		this.errCntSql = errCntSql;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamPrfMstrCommonVO [shdJobId=").append(shdJobId)
				.append(", anaDgr=").append(anaDgr).append(", anaStrDtm=")
				.append(anaStrDtm).append(", dbSchId=").append(dbSchId)
				.append(", dbSchLnm=").append(dbSchLnm).append(", dbcTblNm=")
				.append(dbcTblNm).append(", dbcTblKorNm=").append(dbcTblKorNm)
				.append(", ord=").append(ord).append(", dbcColNm=")
				.append(dbcColNm).append(", dbcColKorNm=").append(dbcColKorNm)
				.append(", colErrRate=").append(colErrRate).append(", dpmo=")
				.append(dpmo).append(", sigma=").append(sigma)
				.append(", anaCnt=").append(anaCnt).append(", esnErCnt=")
				.append(esnErCnt).append(", prfKndCd=").append(prfKndCd)
				.append(", dbConnTrgLnm=").append(dbConnTrgLnm)
				.append(", prfId=").append(prfId).append(", objNm=")
				.append(objNm).append(", chTblDbcColNm=").append(chTblDbcColNm)
				.append(", paTblDbcTblNm=").append(paTblDbcTblNm)
				.append(", paTblDbcTblKorNm=").append(paTblDbcTblKorNm)
				.append(", paTblDbcColNm=").append(paTblDbcColNm)
				.append(", relColNm=").append(relColNm).append(", crdAnaYn=")
				.append(crdAnaYn).append(", minMaxValAnaYn=")
				.append(minMaxValAnaYn).append(", aonlYn=").append(aonlYn)
				.append(", lenAnaYn=").append(lenAnaYn).append(", nullCnt=")
				.append(nullCnt).append(", minVal1=").append(minVal1)
				.append(", minVal2=").append(minVal2).append(", minVal3=")
				.append(minVal3).append(", maxVal1=").append(maxVal1)
				.append(", maxVal2=").append(maxVal2).append(", maxVal3=")
				.append(maxVal3).append(", minLen=").append(minLen)
				.append(", maxLen=").append(maxLen).append(", efvaAnaKndCd=")
				.append(efvaAnaKndCd).append(", userDfEfva=")
				.append(userDfEfva).append(", cdTblDbcTblNm=")
				.append(cdTblDbcTblNm).append(", cdTblDbcColNm=")
				.append(cdTblDbcColNm).append(", cdTblCdIdColNm=")
				.append(cdTblCdIdColNm).append(", cdTblCdId=")
				.append(cdTblCdId).append(", cdTblAdtCndSql=")
				.append(cdTblAdtCndSql).append(", dateFrmCd=")
				.append(dateFrmCd).append(", userDateFrm=").append(userDateFrm)
				.append(", prfRng=").append(prfRng).append(", userDfPtr=")
				.append(userDfPtr).append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", schRegYn=").append(schRegYn).append("]");
		return builder.toString();
	}




}