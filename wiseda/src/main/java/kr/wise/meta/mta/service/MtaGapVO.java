/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : MtaGapVO.java
 * 2. Package : kr.wise.meta.mta.service
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.08.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.08. :            : 신규 개발.
 */
package kr.wise.meta.mta.service;

import kr.wise.commons.cmm.CommonVo;

public class MtaGapVO extends CommonVo {
	
	private String gapStatus;
	private String gapConts;	

	private String mtaOrgCd;
	private String mtaOrgNm;
	private String mtaInfoSysCd;
	private String mtaInfoSysNm;
	private String mtaDbConnTrgId;
	private String mtaDbConnTrgNm;
	private String mtaTblId;
	private String mtaTblPnm;
	private String mtaTblLnm;
	private String mtaColCnt;

	private String dbcOrgCd;
	private String dbcOrgNm;
	private String dbcInfoSysCd;
	private String dbcInfoSysNm;
	private String dbConnTrgId;
	private String dbConnTrgLnm;
	private String dbSchId;
	private String dbSchLnm;
	private String dbcTblNm;
	private String dbcTblKorNm;
	private String dbcColCnt;
	
	
	private String infoSysNm;
	private String tblNm;
	private String infoSysCd;
	private String dbConnTrgPnm;
	private String dbSchPnm;
	
	//GAP 컬럼 정보
	private String dbcDbConnTrgId;
	private String dbcDbConnTrgPnm;
	private String dbcDbConnTrgLnm;
	private String dbcDbSchId;
	private String dbcTblPnm;
	private String dbcTblLnm;
	private String dbcColPnm;
	private String dbcColLnm;
	private String dbcColOrd;
	private String dbcPkYn;
	private String dbcDataType;
	private String dbcDataLen;
	private String dbcDataScal;
	private String dbcNonulYn;
	private String dbcDefltVal;
	private String mtaDbConnTrgPnm;
	private String mtaDbConnTrgLnm;
	private String mtaColId;
	private String mtaColPnm;
	private String mtaColLnm;
	private String mtaColOrd;
	private String mtaPkYn;
	private String mtaDataType;
	private String mtaDataLen;
	private String mtaDataScal;
	private String mtaNonulYn;
	private String mtaDefltVal;

	
	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}
	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}
	public String getDbSchPnm() {
		return dbSchPnm;
	}
	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
	}
	public String getInfoSysCd() {
		return infoSysCd;
	}
	public void setInfoSysCd(String infoSysCd) {
		this.infoSysCd = infoSysCd;
	}
	public String getDbcDbConnTrgId() {
		return dbcDbConnTrgId;
	}
	public void setDbcDbConnTrgId(String dbcDbConnTrgId) {
		this.dbcDbConnTrgId = dbcDbConnTrgId;
	}
	public String getDbcDbConnTrgPnm() {
		return dbcDbConnTrgPnm;
	}
	public void setDbcDbConnTrgPnm(String dbcDbConnTrgPnm) {
		this.dbcDbConnTrgPnm = dbcDbConnTrgPnm;
	}
	public String getDbcDbConnTrgLnm() {
		return dbcDbConnTrgLnm;
	}
	public void setDbcDbConnTrgLnm(String dbcDbConnTrgLnm) {
		this.dbcDbConnTrgLnm = dbcDbConnTrgLnm;
	}
	public String getDbcDbSchId() {
		return dbcDbSchId;
	}
	public void setDbcDbSchId(String dbcDbSchId) {
		this.dbcDbSchId = dbcDbSchId;
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
	public String getDbcColOrd() {
		return dbcColOrd;
	}
	public void setDbcColOrd(String dbcColOrd) {
		this.dbcColOrd = dbcColOrd;
	}
	public String getDbcPkYn() {
		return dbcPkYn;
	}
	public void setDbcPkYn(String dbcPkYn) {
		this.dbcPkYn = dbcPkYn;
	}
	public String getDbcDataType() {
		return dbcDataType;
	}
	public void setDbcDataType(String dbcDataType) {
		this.dbcDataType = dbcDataType;
	}
	public String getDbcDataLen() {
		return dbcDataLen;
	}
	public void setDbcDataLen(String dbcDataLen) {
		this.dbcDataLen = dbcDataLen;
	}
	public String getDbcDataScal() {
		return dbcDataScal;
	}
	public void setDbcDataScal(String dbcDataScal) {
		this.dbcDataScal = dbcDataScal;
	}
	public String getDbcNonulYn() {
		return dbcNonulYn;
	}
	public void setDbcNonulYn(String dbcNonulYn) {
		this.dbcNonulYn = dbcNonulYn;
	}
	public String getDbcDefltVal() {
		return dbcDefltVal;
	}
	public void setDbcDefltVal(String dbcDefltVal) {
		this.dbcDefltVal = dbcDefltVal;
	}
	public String getMtaDbConnTrgPnm() {
		return mtaDbConnTrgPnm;
	}
	public void setMtaDbConnTrgPnm(String mtaDbConnTrgPnm) {
		this.mtaDbConnTrgPnm = mtaDbConnTrgPnm;
	}
	public String getMtaDbConnTrgLnm() {
		return mtaDbConnTrgLnm;
	}
	public void setMtaDbConnTrgLnm(String mtaDbConnTrgLnm) {
		this.mtaDbConnTrgLnm = mtaDbConnTrgLnm;
	}
	public String getMtaColId() {
		return mtaColId;
	}
	public void setMtaColId(String mtaColId) {
		this.mtaColId = mtaColId;
	}
	public String getMtaColPnm() {
		return mtaColPnm;
	}
	public void setMtaColPnm(String mtaColPnm) {
		this.mtaColPnm = mtaColPnm;
	}
	public String getMtaColLnm() {
		return mtaColLnm;
	}
	public void setMtaColLnm(String mtaColLnm) {
		this.mtaColLnm = mtaColLnm;
	}
	public String getMtaColOrd() {
		return mtaColOrd;
	}
	public void setMtaColOrd(String mtaColOrd) {
		this.mtaColOrd = mtaColOrd;
	}
	public String getMtaPkYn() {
		return mtaPkYn;
	}
	public void setMtaPkYn(String mtaPkYn) {
		this.mtaPkYn = mtaPkYn;
	}
	public String getMtaDataType() {
		return mtaDataType;
	}
	public void setMtaDataType(String mtaDataType) {
		this.mtaDataType = mtaDataType;
	}
	public String getMtaDataLen() {
		return mtaDataLen;
	}
	public void setMtaDataLen(String mtaDataLen) {
		this.mtaDataLen = mtaDataLen;
	}
	public String getMtaDataScal() {
		return mtaDataScal;
	}
	public void setMtaDataScal(String mtaDataScal) {
		this.mtaDataScal = mtaDataScal;
	}
	public String getMtaNonulYn() {
		return mtaNonulYn;
	}
	public void setMtaNonulYn(String mtaNonulYn) {
		this.mtaNonulYn = mtaNonulYn;
	}
	public String getMtaDefltVal() {
		return mtaDefltVal;
	}
	public void setMtaDefltVal(String mtaDefltVal) {
		this.mtaDefltVal = mtaDefltVal;
	}
	public String getMtaOrgNm() {
		return mtaOrgNm;
	}
	public void setMtaOrgNm(String mtaOrgNm) {
		this.mtaOrgNm = mtaOrgNm;
	}
	public String getMtaInfoSysNm() {
		return mtaInfoSysNm;
	}
	public void setMtaInfoSysNm(String mtaInfoSysNm) {
		this.mtaInfoSysNm = mtaInfoSysNm;
	}
	public String getMtaDbConnTrgNm() {
		return mtaDbConnTrgNm;
	}
	public void setMtaDbConnTrgNm(String mtaDbConnTrgNm) {
		this.mtaDbConnTrgNm = mtaDbConnTrgNm;
	}
	public String getDbcOrgNm() {
		return dbcOrgNm;
	}
	public void setDbcOrgNm(String dbcOrgNm) {
		this.dbcOrgNm = dbcOrgNm;
	}
	public String getDbcInfoSysNm() {
		return dbcInfoSysNm;
	}
	public void setDbcInfoSysNm(String dbcInfoSysNm) {
		this.dbcInfoSysNm = dbcInfoSysNm;
	}
	public String getInfoSysNm() {
		return infoSysNm;
	}
	public void setInfoSysNm(String infoSysNm) {
		this.infoSysNm = infoSysNm;
	}
	public String getTblNm() {
		return tblNm;
	}
	public void setTblNm(String tblNm) {
		this.tblNm = tblNm;
	}
	public String getDbcTblKorNm() {
		return dbcTblKorNm;
	}
	public void setDbcTblKorNm(String dbcTblKorNm) {
		this.dbcTblKorNm = dbcTblKorNm;
	}
	public String getMtaOrgCd() {
		return mtaOrgCd;
	}
	public void setMtaOrgCd(String mtaOrgCd) {
		this.mtaOrgCd = mtaOrgCd;
	}
	public String getDbcOrgCd() {
		return dbcOrgCd;
	}
	public void setDbcOrgCd(String dbcOrgCd) {
		this.dbcOrgCd = dbcOrgCd;
	}
	public String getGapStatus() {
		return gapStatus;
	}
	public void setGapStatus(String gapStatus) {
		this.gapStatus = gapStatus;
	}
	public String getGapConts() {
		return gapConts;
	}
	public void setGapConts(String gapConts) {
		this.gapConts = gapConts;
	}
	public String getMtaDbConnTrgId() {
		return mtaDbConnTrgId;
	}
	public void setMtaDbConnTrgId(String mtaDbConnTrgId) {
		this.mtaDbConnTrgId = mtaDbConnTrgId;
	}
	public String getMtaInfoSysCd() {
		return mtaInfoSysCd;
	}
	public void setMtaInfoSysCd(String mtaInfoSysCd) {
		this.mtaInfoSysCd = mtaInfoSysCd;
	}
	public String getMtaTblPnm() {
		return mtaTblPnm;
	}
	public void setMtaTblPnm(String mtaTblPnm) {
		this.mtaTblPnm = mtaTblPnm;
	}
	public String getMtaTblLnm() {
		return mtaTblLnm;
	}
	public void setMtaTblLnm(String mtaTblLnm) {
		this.mtaTblLnm = mtaTblLnm;
	}
	public String getMtaColCnt() {
		return mtaColCnt;
	}
	public void setMtaColCnt(String mtaColCnt) {
		this.mtaColCnt = mtaColCnt;
	}
	public String getDbcColCnt() {
		return dbcColCnt;
	}
	public void setDbcColCnt(String dbcColCnt) {
		this.dbcColCnt = dbcColCnt;
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
	public String getDbcTblNm() {
		return dbcTblNm;
	}
	public void setDbcTblNm(String dbcTblNm) {
		this.dbcTblNm = dbcTblNm;
	}
	public String getDbcInfoSysCd() {
		return dbcInfoSysCd;
	}
	public void setDbcInfoSysCd(String dbcInfoSysCd) {
		this.dbcInfoSysCd = dbcInfoSysCd;
	}
	public String getDbConnTrgId() {
		return dbConnTrgId;
	}
	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}
	public String getDbSchId() {
		return dbSchId;
	}
	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}
	public String getMtaTblId() {
		return mtaTblId;
	}
	public void setMtaTblId(String mtaTblId) {
		this.mtaTblId = mtaTblId;
	}
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("MtaGapVO = [infoSysNm="+infoSysNm)
			.append(", infoSysCd="+infoSysCd)
			.append(", dbConnTrgId="+tblNm)
			.append(", dbConnTrgPnm="+dbConnTrgPnm)
			.append(", dbSchId="+dbSchId)
			.append(", dbSchPnm="+dbSchPnm)
			.append(", dbcTblPnm="+dbcTblPnm)
			.append(", dbcTblLnm="+dbcTblLnm)
			.append("]");
		return builder.toString();
	}
	
}
