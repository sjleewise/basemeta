/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : WamMtaTbl.java
 * 2. Package : kr.wise.meta.mta.service
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.09.12.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.09.12. :            : 신규 개발.
 */
package kr.wise.meta.mta.service;

import kr.wise.commons.cmm.CommonVo;

public class WamMtaExl extends CommonVo {
	private String mtaExlId       ;
	private String mtaDgr         ;
	private String orgCd          ;
	private String orgNm          ;
	private String infoSysCd      ;
	private String infoSysNm      ;
	private String relLaw         ;
	private String constYy        ;
	private String deptNm         ;
	private String crgUserNm      ;
	private String crgTelNo       ;
	private String crgEmailAddr   ;
	private String dbConnTrgId    ;
	private String dbConnTrgLnm   ;
	private String dbConnTrgPnm   ;
	private String dbDescn        ;
	private String applctnDuty    ;
	private String dbmsTypCd      ;
	private String dbmsVersCd     ;
	private String osTypCd        ;
	private String osVersCd       ;
	private String constDt        ;
	private String tblCnt         ;
	private String dataCpt        ;
	private String colctExRescn   ;
	private String dbSchId        ;
	private String dbSchPnm       ;
	private String dbcTblPnm      ;
	private String dbcTblLnm      ;
	private String tblDescn       ;
	private String tblRowCnt      ;
	private String tblTypCd       ;
	private String qltyDgnsYn     ;
	private String psvPerd        ;
	private String ocrnCycl       ;
	private String tblOpnYn       ;
	private String tblNopnRescn   ;
	private String dtlRelBasis    ;
	private String dbcColPnm      ;
	private String dbcColLnm      ;
	private String colDescn       ;
	private String dataTyp        ;
	private String dataLen        ;
	private String dataFrm        ;
	private String nullYn         ;
	private String pkYn           ;
	private String fkYn           ;
	private String rstcCond       ;
	private String colOpnYn       ;
	private String colNopnRescn   ;
	private String persInfoYn     ;
	private String encYn          ;
	
	
	public String getOrgCd() {
		return orgCd;
	}
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}
	public String getOrgNm() {
		return orgNm;
	}
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
	public String getInfoSysCd() {
		return infoSysCd;
	}
	public void setInfoSysCd(String infoSysCd) {
		this.infoSysCd = infoSysCd;
	}
	public String getInfoSysNm() {
		return infoSysNm;
	}
	public void setInfoSysNm(String infoSysNm) {
		this.infoSysNm = infoSysNm;
	}
	public String getRelLaw() {
		return relLaw;
	}
	public void setRelLaw(String relLaw) {
		this.relLaw = relLaw;
	}
	public String getConstYy() {
		return constYy;
	}
	public void setConstYy(String constYy) {
		this.constYy = constYy;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getCrgUserNm() {
		return crgUserNm;
	}
	public void setCrgUserNm(String crgUserNm) {
		this.crgUserNm = crgUserNm;
	}
	public String getCrgTelNo() {
		return crgTelNo;
	}
	public void setCrgTelNo(String crgTelNo) {
		this.crgTelNo = crgTelNo;
	}
	public String getCrgEmailAddr() {
		return crgEmailAddr;
	}
	public void setCrgEmailAddr(String crgEmailAddr) {
		this.crgEmailAddr = crgEmailAddr;
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
	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}
	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}
	public String getDbDescn() {
		return dbDescn;
	}
	public void setDbDescn(String dbDescn) {
		this.dbDescn = dbDescn;
	}
	public String getApplctnDuty() {
		return applctnDuty;
	}
	public void setApplctnDuty(String applctnDuty) {
		this.applctnDuty = applctnDuty;
	}
	public String getDbmsTypCd() {
		return dbmsTypCd;
	}
	public void setDbmsTypCd(String dbmsTypCd) {
		this.dbmsTypCd = dbmsTypCd;
	}
	public String getDbmsVersCd() {
		return dbmsVersCd;
	}
	public void setDbmsVersCd(String dbmsVersCd) {
		this.dbmsVersCd = dbmsVersCd;
	}
	public String getOsTypCd() {
		return osTypCd;
	}
	public void setOsTypCd(String osTypCd) {
		this.osTypCd = osTypCd;
	}
	public String getOsVersCd() {
		return osVersCd;
	}
	public void setOsVersCd(String osVersCd) {
		this.osVersCd = osVersCd;
	}
	public String getConstDt() {
		return constDt;
	}
	public void setConstDt(String constDt) {
		this.constDt = constDt;
	}
	public String getTblCnt() {
		return tblCnt;
	}
	public void setTblCnt(String tblCnt) {
		this.tblCnt = tblCnt;
	}
	public String getDataCpt() {
		return dataCpt;
	}
	public void setDataCpt(String dataCpt) {
		this.dataCpt = dataCpt;
	}
	public String getColctExRescn() {
		return colctExRescn;
	}
	public void setColctExRescn(String colctExRescn) {
		this.colctExRescn = colctExRescn;
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
	public String getDbcTblPnm() {
		return dbcTblPnm;
	}
	public void setDbcTblPnm(String dbcTblPnm) {
		this.dbcTblPnm = dbcTblPnm;
	}
	public String getDbcTblLnm() {
		return dbcTblLnm;
	}
	public void setDbcTblLnm(String dbcTblLnm) {
		this.dbcTblLnm = dbcTblLnm;
	}
	public String getTblDescn() {
		return tblDescn;
	}
	public void setTblDescn(String tblDescn) {
		this.tblDescn = tblDescn;
	}
	public String getTblRowCnt() {
		return tblRowCnt;
	}
	public void setTblRowCnt(String tblRowCnt) {
		this.tblRowCnt = tblRowCnt;
	}
	public String getTblTypCd() {
		return tblTypCd;
	}
	public void setTblTypCd(String tblTypCd) {
		this.tblTypCd = tblTypCd;
	}
	public String getQltyDgnsYn() {
		return qltyDgnsYn;
	}
	public void setQltyDgnsYn(String qltyDgnsYn) {
		this.qltyDgnsYn = qltyDgnsYn;
	}
	public String getPsvPerd() {
		return psvPerd;
	}
	public void setPsvPerd(String psvPerd) {
		this.psvPerd = psvPerd;
	}
	public String getOcrnCycl() {
		return ocrnCycl;
	}
	public void setOcrnCycl(String ocrnCycl) {
		this.ocrnCycl = ocrnCycl;
	}
	public String getTblOpnYn() {
		return tblOpnYn;
	}
	public void setTblOpnYn(String tblOpnYn) {
		this.tblOpnYn = tblOpnYn;
	}
	public String getTblNopnRescn() {
		return tblNopnRescn;
	}
	public void setTblNopnRescn(String tblNopnRescn) {
		this.tblNopnRescn = tblNopnRescn;
	}
	public String getDtlRelBasis() {
		return dtlRelBasis;
	}
	public void setDtlRelBasis(String dtlRelBasis) {
		this.dtlRelBasis = dtlRelBasis;
	}
	public String getDbcColPnm() {
		return dbcColPnm;
	}
	public void setDbcColPnm(String dbcColPnm) {
		this.dbcColPnm = dbcColPnm;
	}
	public String getDbcColLnm() {
		return dbcColLnm;
	}
	public void setDbcColLnm(String dbcColLnm) {
		this.dbcColLnm = dbcColLnm;
	}
	public String getColDescn() {
		return colDescn;
	}
	public void setColDescn(String colDescn) {
		this.colDescn = colDescn;
	}
	public String getDataTyp() {
		return dataTyp;
	}
	public void setDataTyp(String dataTyp) {
		this.dataTyp = dataTyp;
	}
	public String getDataLen() {
		return dataLen;
	}
	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}
	public String getDataFrm() {
		return dataFrm;
	}
	public void setDataFrm(String dataFrm) {
		this.dataFrm = dataFrm;
	}
	public String getNullYn() {
		return nullYn;
	}
	public void setNullYn(String nullYn) {
		this.nullYn = nullYn;
	}
	public String getPkYn() {
		return pkYn;
	}
	public void setPkYn(String pkYn) {
		this.pkYn = pkYn;
	}
	public String getFkYn() {
		return fkYn;
	}
	public void setFkYn(String fkYn) {
		this.fkYn = fkYn;
	}
	public String getRstcCond() {
		return rstcCond;
	}
	public void setRstcCond(String rstcCond) {
		this.rstcCond = rstcCond;
	}
	public String getColOpnYn() {
		return colOpnYn;
	}
	public void setColOpnYn(String colOpnYn) {
		this.colOpnYn = colOpnYn;
	}
	public String getColNopnRescn() {
		return colNopnRescn;
	}
	public void setColNopnRescn(String colNopnRescn) {
		this.colNopnRescn = colNopnRescn;
	}
	public String getPersInfoYn() {
		return persInfoYn;
	}
	public void setPersInfoYn(String persInfoYn) {
		this.persInfoYn = persInfoYn;
	}
	public String getEncYn() {
		return encYn;
	}
	public void setEncYn(String encYn) {
		this.encYn = encYn;
	}
	public String getMtaExlId() {
		return mtaExlId;
	}
	public void setMtaExlId(String mtaExlId) {
		this.mtaExlId = mtaExlId;
	}
	public String getMtaDgr() {
		return mtaDgr;
	}
	public void setMtaDgr(String mtaDgr) {
		this.mtaDgr = mtaDgr;
	}
	
}