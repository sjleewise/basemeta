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

public class WamMtaTbl extends CommonVo {

	private String mtaTblId;
	private String mtaTblPnm;
	private String mtaTblLnm;
	//private String orgCd;
	private String rqstNo;
	private Integer rqstSno;
	private String infoSysCd;
	private String dbConnTrgId;
	private String tblTypNm;
	private String relEntyNm;
	private String prsvTerm;
	private String occrCyl;
	private String tblVol;
	private String exptOccrCnt;
	private String tagInfNm;
	
	private String infoSysNm;
	private String dbConnTrgPnm;
	private String dbSchId;
	private String dbSchPnm;
	//private String objDescn;
	//private Integer objVers;
	//private String regTypCd;
	//private String frsRqstDtm;
	//private String frsRqstUserId;
	//private String rqstDtm;
	//private String rqstUserId;
	//private String aprvDtm;
	//private String aprvUserId;
	private String subjNm;
	private String tblCreDt;
	private String nopenRsn;
	private String openDataLst;
	
	private String crgUserNm; //담당자
    private String encYn; //암호화 컬럼 포함여부....
    private String colCnt;
    private String dbmsTypCd;
    
    private String gapStsCd;
    private String dqDgnsYn;
    private String openRsnCd;
    private String nopenDtlRelBss;
    // 2019.5.21
    private String orgNm;
    private String orgCd;
    
    private String brmNm;
    private String brmId;
    
	public String getGapStsCd() {
		return gapStsCd;
	}
	public void setGapStsCd(String gapStsCd) {
		this.gapStsCd = gapStsCd;
	}
	public String getInfoSysNm() {
		return infoSysNm;
	}
	public void setInfoSysNm(String infoSysNm) {
		this.infoSysNm = infoSysNm;
	}
	public String getMtaTblId() {
		return mtaTblId;
	}
	public void setMtaTblId(String mtaTblId) {
		this.mtaTblId = mtaTblId;
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
	public String getRqstNo() {
		return rqstNo;
	}
	public void setRqstNo(String rqstNo) {
		this.rqstNo = rqstNo;
	}
	public Integer getRqstSno() {
		return rqstSno;
	}
	public void setRqstSno(Integer rqstSno) {
		this.rqstSno = rqstSno;
	}
	public String getInfoSysCd() {
		return infoSysCd;
	}
	public void setInfoSysCd(String infoSysCd) {
		this.infoSysCd = infoSysCd;
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
	public String getTblTypNm() {
		return tblTypNm;
	}
	public void setTblTypNm(String tblTypNm) {
		this.tblTypNm = tblTypNm;
	}
	public String getRelEntyNm() {
		return relEntyNm;
	}
	public void setRelEntyNm(String relEntyNm) {
		this.relEntyNm = relEntyNm;
	}
	public String getSubjNm() {
		return subjNm;
	}
	public void setSubjNm(String subjNm) {
		this.subjNm = subjNm;
	}
	public String getTagInfNm() {
		return tagInfNm;
	}
	public void setTagInfNm(String tagInfNm) {
		this.tagInfNm = tagInfNm;
	}
	public String getPrsvTerm() {
		return prsvTerm;
	}
	public void setPrsvTerm(String prsvTerm) {
		this.prsvTerm = prsvTerm;
	}
	public String getTblVol() {
		return tblVol;
	}
	public void setTblVol(String tblVol) {
		this.tblVol = tblVol;
	}
	public String getExptOccrCnt() {
		return exptOccrCnt;
	}
	public void setExptOccrCnt(String exptOccrCnt) {
		this.exptOccrCnt = exptOccrCnt;
	}
	public String getTblCreDt() {
		return tblCreDt;
	}
	public void setTblCreDt(String tblCreDt) {
		this.tblCreDt = tblCreDt;
	}
	public String getNopenRsn() {
		return nopenRsn;
	}
	public void setNopenRsn(String nopenRsn) {
		this.nopenRsn = nopenRsn;
	}
	public String getOpenDataLst() {
		return openDataLst;
	}
	public void setOpenDataLst(String openDataLst) {
		this.openDataLst = openDataLst;
	}
	public String getOccrCyl() {
		return occrCyl;
	}
	public void setOccrCyl(String occrCyl) {
		this.occrCyl = occrCyl;
	}
	public String getCrgUserNm() {
		return crgUserNm;
	}
	public void setCrgUserNm(String crgUserNm) {
		this.crgUserNm = crgUserNm;
	}
	public String getEncYn() {
		return encYn;
	}
	public void setEncYn(String encYn) {
		this.encYn = encYn;
	}
	public String getColCnt() {
		return colCnt;
	}
	public void setColCnt(String colCnt) {
		this.colCnt = colCnt;
	}
	public String getDbmsTypCd() {
		return dbmsTypCd;
	}
	public void setDbmsTypCd(String dbmsTypCd) {
		this.dbmsTypCd = dbmsTypCd;
	}
	public String getDqDgnsYn() {
		return dqDgnsYn;
	}
	public void setDqDgnsYn(String dqDgnsYn) {
		this.dqDgnsYn = dqDgnsYn;
	}
	public String getOpenRsnCd() {
		return openRsnCd;
	}
	public void setOpenRsnCd(String openRsnCd) {
		this.openRsnCd = openRsnCd;
	}
	public String getNopenDtlRelBss() {
		return nopenDtlRelBss;
	}
	public void setNopenDtlRelBss(String nopenDtlRelBss) {
		this.nopenDtlRelBss = nopenDtlRelBss;
	}
	public String getOrgNm() {
		return orgNm;
	}
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
	public String getOrgCd() {
		return orgCd;
	}
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}
	public String getBrmNm() {
		return brmNm;
	}
	public void setBrmNm(String brmNm) {
		this.brmNm = brmNm;
	}
	public String getBrmId() {
		return brmId;
	}
	public void setBrmId(String brmId) {
		this.brmId = brmId;
	}
	
}