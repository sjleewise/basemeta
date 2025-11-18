package kr.wise.dq.result.vrfcrule.service;

import java.util.Date;
import java.util.List;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.report.service.DataPatternVO;
import kr.wise.dq.vrfcrule.sqlgenerator.VrfcSqlGeneratorVO;

public class VrfcruleResultVO extends CommonVo{
	
	private String subjId;
	
	private String prfId;
	
	private String subjLnm;
	
	private String stndAsrt;
	
	private String dbSchId;

    private String dbcTblNm;

    private String dbcTblKorNm;
    
    private String anaTrgYn;

    private Integer vers;

    private String regTyp;

    private String regDtm;

    private String updDtm;
    
    private String descn;
    
    private String dbConnTrgId;
    //기관명, 시스템영역
    private String sysAreaId;
    
    private String orgCd;
    
    private String orgNm;
    
    /**
     * 진단대상 테이블 현황
     */
    private String trgTblCnt;
    private String trgExcCnt;
    private String ntCllcCnt;
    private String trgTblRt;
    private String trgExcRt;
    private String ntCllcRt;
    
    /**
     * 진단실행상태
     */
    private String anaCmlCnt;
    private String anaRnnCnt;
    private String anaFalCnt;
    private String anaCmlRt;
    private String anaRnnRt;
    private String anaFalRt;
    
    /**
     * 값진단결과
     */
    private String anaTrgCol;
    private String anaErrCol;
    private String erCnt;
    private String errRt;
    
    /**
	 * 진단대상테이블
	 */
    private Date anaDtm;
    private String strAnaDtm;
	//진단 대상 테이블 수집 상태
    private String anaTrgStatus;
    //범위조건
    private String anaTrgRangeCon;
    
    /**
     * 도메인
     */
    private String vrfFrm;
    private String errExcData;
    private Date aprvDtm;
    private String strAprvDtm;
    private String dataPtr;
    private String dbcColOpn;
    
    
    /**
     * 참조무결성
     */
  	private String chTblDbcTblNm;
  	private String chTblDbcColNm;
  	private String relColSno;
  	private String paTblDbcColNm;
  	private String paTblDbcTblNm;
  	private String paTblAdtCndSql;
  	
  	
  	/**
  	 * 필수값완전성
  	 */
  	private String aonlYn;
  	private String dbcColNm;
  	private String dbcColKorNm;
  	private String dataType;
  	private String dataLen;
  	
  	/**
  	 * 데이터중복
  	 */
  	private String colSeqPnm;
  	private String colSeqLnm;
  	
  	/**
  	 * 업무규칙
  	 */
  	private String bizAreaLnm;
  	private String brId;
  	private String brNm;
  	private String objDescn;
  	private String cntSql;
  	private String anaSql;
  	private String erCntSql;
  	
  	/**
  	 * 진단항목대상정보
  	 */
  	private String kndCd;
  	private String kndNm;
  	private String anaStsTxt;
  	private Date anaStrDtm;
  	private String strAnaStrDtm;
  	
  	/**
  	 * 진당항목오류정보
  	 */
  	private String dbcPaTblNm;
  	private String dbcPaColNm;
  	private String objId;
  	private String objDate;
  	private String prfKndCd;
  	// sql정보
  	private SqlGeneratorVO sqlGeneratorVO;
  	// 오류데이터정보
  	private DataPatternVO dataPatternVO;
  	private List<DataPatternVO> dataPatternVoList;
  	
  	
  	private String errSql;
  	private String errData;
  	private String colErrRate;
  	private String esnErCnt;
  	private String anaCnt;

	private VrfcSqlGeneratorVO vrfcSqlGeneratorVO;
	
	
  	
  	
	public String getSysAreaId() {
		return sysAreaId;
	}
	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
	}
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
	public VrfcSqlGeneratorVO getVrfcSqlGeneratorVO() {
		return vrfcSqlGeneratorVO;
	}
	public void setVrfcSqlGeneratorVO(VrfcSqlGeneratorVO vrfcSqlGeneratorVO) {
		this.vrfcSqlGeneratorVO = vrfcSqlGeneratorVO;
	}
	public String getPrfId() {
		return prfId;
	}
	public void setPrfId(String prfId) {
		this.prfId = prfId;
	}
	public String getSubjId() {
		return subjId;
	}
	public void setSubjId(String subjId) {
		this.subjId = subjId;
	}
	public String getSubjLnm() {
		return subjLnm;
	}
	public void setSubjLnm(String subjLnm) {
		this.subjLnm = subjLnm;
	}
	public String getStndAsrt() {
		return stndAsrt;
	}
	public void setStndAsrt(String stndAsrt) {
		this.stndAsrt = stndAsrt;
	}
	public String getDbSchId() {
		return dbSchId;
	}
	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
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
	public String getAnaTrgYn() {
		return anaTrgYn;
	}
	public void setAnaTrgYn(String anaTrgYn) {
		this.anaTrgYn = anaTrgYn;
	}
	public Integer getVers() {
		return vers;
	}
	public void setVers(Integer vers) {
		this.vers = vers;
	}
	public String getRegTyp() {
		return regTyp;
	}
	public void setRegTyp(String regTyp) {
		this.regTyp = regTyp;
	}
	public String getRegDtm() {
		return regDtm;
	}
	public void setRegDtm(String regDtm) {
		this.regDtm = regDtm;
	}
	public String getUpdDtm() {
		return updDtm;
	}
	public void setUpdDtm(String updDtm) {
		this.updDtm = updDtm;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public String getDbConnTrgId() {
		return dbConnTrgId;
	}
	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}
	public String getTrgTblCnt() {
		return trgTblCnt;
	}
	public void setTrgTblCnt(String trgTblCnt) {
		this.trgTblCnt = trgTblCnt;
	}
	public String getTrgExcCnt() {
		return trgExcCnt;
	}
	public void setTrgExcCnt(String trgExcCnt) {
		this.trgExcCnt = trgExcCnt;
	}
	public String getNtCllcCnt() {
		return ntCllcCnt;
	}
	public void setNtCllcCnt(String ntCllcCnt) {
		this.ntCllcCnt = ntCllcCnt;
	}
	public String getTrgTblRt() {
		return trgTblRt;
	}
	public void setTrgTblRt(String trgTblRt) {
		this.trgTblRt = trgTblRt;
	}
	public String getTrgExcRt() {
		return trgExcRt;
	}
	public void setTrgExcRt(String trgExcRt) {
		this.trgExcRt = trgExcRt;
	}
	public String getNtCllcRt() {
		return ntCllcRt;
	}
	public void setNtCllcRt(String ntCllcRt) {
		this.ntCllcRt = ntCllcRt;
	}
	public String getAnaCmlCnt() {
		return anaCmlCnt;
	}
	public void setAnaCmlCnt(String anaCmlCnt) {
		this.anaCmlCnt = anaCmlCnt;
	}
	public String getAnaRnnCnt() {
		return anaRnnCnt;
	}
	public void setAnaRnnCnt(String anaRnnCnt) {
		this.anaRnnCnt = anaRnnCnt;
	}
	public String getAnaFalCnt() {
		return anaFalCnt;
	}
	public void setAnaFalCnt(String anaFalCnt) {
		this.anaFalCnt = anaFalCnt;
	}
	public String getAnaCmlRt() {
		return anaCmlRt;
	}
	public void setAnaCmlRt(String anaCmlRt) {
		this.anaCmlRt = anaCmlRt;
	}
	public String getAnaRnnRt() {
		return anaRnnRt;
	}
	public void setAnaRnnRt(String anaRnnRt) {
		this.anaRnnRt = anaRnnRt;
	}
	public String getAnaFalRt() {
		return anaFalRt;
	}
	public void setAnaFalRt(String anaFalRt) {
		this.anaFalRt = anaFalRt;
	}
	public String getAnaTrgCol() {
		return anaTrgCol;
	}
	public void setAnaTrgCol(String anaTrgCol) {
		this.anaTrgCol = anaTrgCol;
	}
	public String getAnaErrCol() {
		return anaErrCol;
	}
	public void setAnaErrCol(String anaErrCol) {
		this.anaErrCol = anaErrCol;
	}
	public String getErCnt() {
		return erCnt;
	}
	public void setErCnt(String erCnt) {
		this.erCnt = erCnt;
	}
	public String getErrRt() {
		return errRt;
	}
	public void setErrRt(String errRt) {
		this.errRt = errRt;
	}
	public Date getAnaDtm() {
		return anaDtm;
	}
	public void setAnaDtm(Date anaDtm) {
		this.anaDtm = anaDtm;
	}
	public String getStrAnaDtm() {
		return strAnaDtm;
	}
	public void setStrAnaDtm(String strAnaDtm) {
		this.strAnaDtm = strAnaDtm;
	}
	public String getAnaTrgStatus() {
		return anaTrgStatus;
	}
	public void setAnaTrgStatus(String anaTrgStatus) {
		this.anaTrgStatus = anaTrgStatus;
	}
	public String getAnaTrgRangeCon() {
		return anaTrgRangeCon;
	}
	public void setAnaTrgRangeCon(String anaTrgRangeCon) {
		this.anaTrgRangeCon = anaTrgRangeCon;
	}
	public String getVrfFrm() {
		return vrfFrm;
	}
	public void setVrfFrm(String vrfFrm) {
		this.vrfFrm = vrfFrm;
	}
	public String getErrExcData() {
		return errExcData;
	}
	public void setErrExcData(String errExcData) {
		this.errExcData = errExcData;
	}
	public Date getAprvDtm() {
		return aprvDtm;
	}
	public void setAprvDtm(Date aprvDtm) {
		this.aprvDtm = aprvDtm;
	}
	public String getStrAprvDtm() {
		return strAprvDtm;
	}
	public void setStrAprvDtm(String strAprvDtm) {
		this.strAprvDtm = strAprvDtm;
	}
	public String getDataPtr() {
		return dataPtr;
	}
	public void setDataPtr(String dataPtr) {
		this.dataPtr = dataPtr;
	}
	public String getDbcColOpn() {
		return dbcColOpn;
	}
	public void setDbcColOpn(String dbcColOpn) {
		this.dbcColOpn = dbcColOpn;
	}
	public String getChTblDbcTblNm() {
		return chTblDbcTblNm;
	}
	public void setChTblDbcTblNm(String chTblDbcTblNm) {
		this.chTblDbcTblNm = chTblDbcTblNm;
	}
	public String getChTblDbcColNm() {
		return chTblDbcColNm;
	}
	public void setChTblDbcColNm(String chTblDbcColNm) {
		this.chTblDbcColNm = chTblDbcColNm;
	}
	public String getRelColSno() {
		return relColSno;
	}
	public void setRelColSno(String relColSno) {
		this.relColSno = relColSno;
	}
	public String getPaTblDbcColNm() {
		return paTblDbcColNm;
	}
	public void setPaTblDbcColNm(String paTblDbcColNm) {
		this.paTblDbcColNm = paTblDbcColNm;
	}
	public String getPaTblDbcTblNm() {
		return paTblDbcTblNm;
	}
	public void setPaTblDbcTblNm(String paTblDbcTblNm) {
		this.paTblDbcTblNm = paTblDbcTblNm;
	}
	public String getPaTblAdtCndSql() {
		return paTblAdtCndSql;
	}
	public void setPaTblAdtCndSql(String paTblAdtCndSql) {
		this.paTblAdtCndSql = paTblAdtCndSql;
	}
	public String getAonlYn() {
		return aonlYn;
	}
	public void setAonlYn(String aonlYn) {
		this.aonlYn = aonlYn;
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
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataLen() {
		return dataLen;
	}
	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}
	public String getColSeqPnm() {
		return colSeqPnm;
	}
	public void setColSeqPnm(String colSeqPnm) {
		this.colSeqPnm = colSeqPnm;
	}
	public String getColSeqLnm() {
		return colSeqLnm;
	}
	public void setColSeqLnm(String colSeqLnm) {
		this.colSeqLnm = colSeqLnm;
	}
	public String getBizAreaLnm() {
		return bizAreaLnm;
	}
	public void setBizAreaLnm(String bizAreaLnm) {
		this.bizAreaLnm = bizAreaLnm;
	}
	public String getBrId() {
		return brId;
	}
	public void setBrId(String brId) {
		this.brId = brId;
	}
	public String getBrNm() {
		return brNm;
	}
	public void setBrNm(String brNm) {
		this.brNm = brNm;
	}
	public String getObjDescn() {
		return objDescn;
	}
	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}
	public String getCntSql() {
		return cntSql;
	}
	public void setCntSql(String cntSql) {
		this.cntSql = cntSql;
	}
	public String getAnaSql() {
		return anaSql;
	}
	public void setAnaSql(String anaSql) {
		this.anaSql = anaSql;
	}
	public String getErCntSql() {
		return erCntSql;
	}
	public void setErCntSql(String erCntSql) {
		this.erCntSql = erCntSql;
	}
	public String getKndCd() {
		return kndCd;
	}
	public void setKndCd(String kndCd) {
		this.kndCd = kndCd;
	}
	public String getKndNm() {
		return kndNm;
	}
	public void setKndNm(String kndNm) {
		this.kndNm = kndNm;
	}
	public String getAnaStsTxt() {
		return anaStsTxt;
	}
	public void setAnaStsTxt(String anaStsTxt) {
		this.anaStsTxt = anaStsTxt;
	}
	public Date getAnaStrDtm() {
		return anaStrDtm;
	}
	public void setAnaStrDtm(Date anaStrDtm) {
		this.anaStrDtm = anaStrDtm;
	}
	public String getStrAnaStrDtm() {
		return strAnaStrDtm;
	}
	public void setStrAnaStrDtm(String strAnaStrDtm) {
		this.strAnaStrDtm = strAnaStrDtm;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getObjDate() {
		return objDate;
	}
	public void setObjDate(String objDate) {
		this.objDate = objDate;
	}
	public String getPrfKndCd() {
		return prfKndCd;
	}
	public void setPrfKndCd(String prfKndCd) {
		this.prfKndCd = prfKndCd;
	}
	public SqlGeneratorVO getSqlGeneratorVO() {
		return sqlGeneratorVO;
	}
	public void setSqlGeneratorVO(SqlGeneratorVO sqlGeneratorVO) {
		this.sqlGeneratorVO = sqlGeneratorVO;
	}
	public DataPatternVO getDataPatternVO() {
		return dataPatternVO;
	}
	public void setDataPatternVO(DataPatternVO dataPatternVO) {
		this.dataPatternVO = dataPatternVO;
	}
	public String getErrSql() {
		return errSql;
	}
	public void setErrSql(String errSql) {
		this.errSql = errSql;
	}
	public String getErrData() {
		return errData;
	}
	public void setErrData(String errData) {
		this.errData = errData;
	}
	public String getColErrRate() {
		return colErrRate;
	}
	public void setColErrRate(String colErrRate) {
		this.colErrRate = colErrRate;
	}
	public String getEsnErCnt() {
		return esnErCnt;
	}
	public void setEsnErCnt(String esnErCnt) {
		this.esnErCnt = esnErCnt;
	}
	public String getAnaCnt() {
		return anaCnt;
	}
	public void setAnaCnt(String anaCnt) {
		this.anaCnt = anaCnt;
	}
	public List<DataPatternVO> getDataPatternVoList() {
		return dataPatternVoList;
	}
	public void setDataPatternVoList(List<DataPatternVO> dataPatternVoList) {
		this.dataPatternVoList = dataPatternVoList;
	}
	public String getDbcPaTblNm() {
		return dbcPaTblNm;
	}
	public void setDbcPaTblNm(String dbcPaTblNm) {
		this.dbcPaTblNm = dbcPaTblNm;
	}
	public String getDbcPaColNm() {
		return dbcPaColNm;
	}
	public void setDbcPaColNm(String dbcPaColNm) {
		this.dbcPaColNm = dbcPaColNm;
	}
}
