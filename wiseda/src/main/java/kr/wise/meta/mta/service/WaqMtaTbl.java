/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : WaqMtaTbl.java
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

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.cmm.CommonVo;

public class WaqMtaTbl extends CommonVo {

	private String rqstNo;
	private Integer rqstSno;
	private String mtaTblId;
	private String mtaTblPnm;
	private String mtaTblLnm;
	//private String rqstDcd;
	//private String rvwStsCd;
	//private String rvwConts;
	//private String vrfCd;
	//private String vrfRmk;
	private String orgCd;
	private String orgNm;
	private String infoSysCd;
	private String infoSysNm;
	private String dbConnTrgId;
	private String dbConnTrgPnm;
	private String tblTypNm;
	private String relEntyNm;
	private String prsvTerm;
	private String occrCyl;
	private String tblVol;
	private String exptOccrCnt;
	private String tagInfNm;
	//private String objDescn;
	//private Integer objVers;
	//private String regTypCd;
	//private String frsRqstUserId;
	//private String frsRqstDtm;
	//private String rqstUserId;
	//private String rqstDtm;
	//private String aprvUserId;
	//private String aprvDtm;
	private String subjClDcd;
	private String subjNm;
	private String subjId;
	private String uppSubjId;
	private String tblCreDt;
	private String openRsnCd;  //공개사유코드  
	private String nopenRsn; 	  
	private String openDataLst;
	
	//
	private String dbConnTrgLnm;
	private String dbSchId;
	private String dbSchPnm;
	private String dbSchLnm;
	private String dbcTblPnm;
	private String dbcTblLnm;
	
	private String tblNm;
	private String gapStatus;	
	
	private String gapStsCd; //GAP상태코드
	
	private String regTblStatus;
	private String regColStatus;
	
	private String nopenRsnCdNm; //비공개사유코드명
	private String dqDgnsYn;
	
	private String tblClltDcd; //테이블수집구분코드 
	
	private Object chkNopenRsn;
	
	private String  nopenDtlRelBss;
	
	private String rowEacnt;	

	public String getOrgNm() {
		return orgNm;
	}



	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}



	public String getRegTblStatus() {
		return regTblStatus;
	}



	public void setRegTblStatus(String regTblStatus) {
		this.regTblStatus = regTblStatus;
	}



	public String getRegColStatus() {
		return regColStatus;
	}



	public void setRegColStatus(String regColStatus) {
		this.regColStatus = regColStatus;
	}

	public String getGapStsCd() {
		return gapStsCd;
	}

	public void setGapStsCd(String gapStsCd) {
		this.gapStsCd = gapStsCd;
	}



	public String getNopenRsnCdNm() {
		return nopenRsnCdNm;
	}



	public void setNopenRsnCdNm(String nopenRsnCdNm) {
		this.nopenRsnCdNm = nopenRsnCdNm;
	}
	

	public String getGapStatus() {
		return gapStatus;
	}



	public void setGapStatus(String gapStatus) {
		this.gapStatus = gapStatus;
	}



	public String getRqstNo() {
		return rqstNo;
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



	public void setRqstNo(String rqstNo) {
		this.rqstNo = rqstNo;
	}



	public Integer getRqstSno() {
		return rqstSno;
	}



	public void setRqstSno(Integer rqstSno) {
		this.rqstSno = rqstSno;
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

	public String getTblNm() {
		return tblNm;
	}

	public void setTblNm(String tblNm) {
		this.tblNm = tblNm;
	}
		
	public String getDqDgnsYn() {
		return dqDgnsYn;
	}

	public void setDqDgnsYn(String dqDgnsYn) {
		this.dqDgnsYn = dqDgnsYn;
	}
	
	public String getSubjId() {
		return subjId;
	}

	public void setSubjId(String subjId) {
		this.subjId = subjId;
	}

	public String getUppSubjId() {
		return uppSubjId;
	}

	public void setUppSubjId(String uppSubjId) {
		this.uppSubjId = uppSubjId;
	}

	public String getTblClltDcd() {
		return tblClltDcd;
	}

	public void setTblClltDcd(String tblClltDcd) {
		this.tblClltDcd = tblClltDcd;
	}
	
	public String getSubjClDcd() {
		return subjClDcd;
	}

	public void setSubjClDcd(String subjClDcd) {
		this.subjClDcd = subjClDcd;
	}
	
	
	public String getOpenRsnCd() {
		return openRsnCd;
	}
	
	public void setOpenRsnCd(String openRsnCd) {
		this.openRsnCd = openRsnCd;
	}

	public String getNopenRsn() {
		return nopenRsn;
	}

	public void setNopenRsn(String nopenRsn) {
		this.nopenRsn = nopenRsn;
	}
	
	public Object getChkNopenRsn() {
		return chkNopenRsn;
	}

	public void setChkNopenRsn(Object chkNopenRsn) {
		this.chkNopenRsn = chkNopenRsn;
	}

	public String getNopenDtlRelBss() {
		return nopenDtlRelBss;
	}

	public void setNopenDtlRelBss(String nopenDtlRelBss) {
		this.nopenDtlRelBss = nopenDtlRelBss;
	}


	public String getRowEacnt() {
		return rowEacnt;
	}


	public void setRowEacnt(String rowEacnt) {
		this.rowEacnt = rowEacnt;
	}



	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("WaqMtaTbl [rqstNo=").append(rqstNo)
		.append(", rqstSno=").append(rqstSno)
		.append(", mtaTblId=").append(mtaTblId)
		.append(", mtaTblPnm=").append(mtaTblPnm)
		.append(", mtaTblLnm=").append(mtaTblLnm)
		.append(", orgNm=").append(getOrgNm())
		.append(", infoSysCd=").append(infoSysCd)
		.append(", infoSysNm=").append(infoSysNm)
		.append(", dbConnTrgId=").append(dbConnTrgId)
		.append(", dbConnTrgPnm=").append(dbConnTrgPnm).append("]");
		
		return builder.toString();
	}
	public String getOrgCd() {
		return orgCd;
	}
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}


	
}
