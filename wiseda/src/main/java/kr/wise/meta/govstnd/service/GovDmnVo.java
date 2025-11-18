package kr.wise.meta.govstnd.service;

import kr.wise.commons.cmm.CommonVo;
import java.util.Date;



public class GovDmnVo extends CommonVo {
	
private String dmnId; //도메인 아이디
	
	private String sysAreaId; // 시스템물리명
	private String dbConnTrgPnm; // 디비논리명
	private String dbSchPnm;//스키마 물리명
	
	private String uppDmngNm; //도메인 그룹명
	private String dmngNm; // 도메인 분류명
	private String dmnNm; // 도메인명 
	
	private String objDescn; //공통표준도메인설명
	
	private String rqstDcn; //요청구분코드
	private String vrfRmk;//검증결과
	
	private String dataType;
	private int dataLen;
	private int dataScal;
	
	private String dicOrgn; // 출처
	
	private String regDtm; //등록일시
	private String regUserId; //등록아이디
	
	private String excYn; //엑셀여부
	
	private String infoSysCd;
	
	public String getDmnId() {
		return dmnId;
	}

	public void setDmnId(String dmnId) {
		this.dmnId = dmnId;
	}

	public String getSysAreaId() {
		return sysAreaId;
	}

	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
	}

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

	public String getUppDmngNm() {
		return uppDmngNm;
	}

	public void setUppDmngNm(String uppDmngNm) {
		this.uppDmngNm = uppDmngNm;
	}

	public String getDmngNm() {
		return dmngNm;
	}

	public void setDmngNm(String dmngNm) {
		this.dmngNm = dmngNm;
	}

	public String getDmnNm() {
		return dmnNm;
	}

	public void setDmnNm(String dmnNm) {
		this.dmnNm = dmnNm;
	}

	public String getObjDescn() {
		return objDescn;
	}

	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}

	public String getRqstDcn() {
		return rqstDcn;
	}

	public void setRqstDcn(String rqstDcn) {
		this.rqstDcn = rqstDcn;
	}

	public String getVrfRmk() {
		return vrfRmk;
	}

	public void setVrfRmk(String vrfRmk) {
		this.vrfRmk = vrfRmk;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getDataLen() {
		return dataLen;
	}

	public void setDataLen(int dataLen) {
		this.dataLen = dataLen;
	}

	public int getDataScal() {
		return dataScal;
	}

	public void setDataScal(int dataScal) {
		this.dataScal = dataScal;
	}

	public String getDicOrgn() {
		return dicOrgn;
	}

	public void setDicOrgn(String dicOrgn) {
		this.dicOrgn = dicOrgn;
	}

	public String getRegDtm() {
		return regDtm;
	}

	public void setRegDtm(String regDtm) {
		this.regDtm = regDtm;
	}

	public String getRegUserId() {
		return regUserId;
	}

	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}

	public String getExcYn() {
		return excYn;
	}

	public void setExcYn(String excYn) {
		this.excYn = excYn;
	}

	
	
	
/*
	@Override
	public String toString() {
		return "WaqDmn [dmnId=" + dmnId + ", dmnLnm=" + dmnLnm + ", dmnPnm="
				+ dmnPnm + ", lnmCriDs=" + lnmCriDs + ", pnmCriDs=" + pnmCriDs
				+ ", dmngId=" + dmngId + ", dmngLnm=" + dmngLnm + ", infotpId="
				+ infotpId + ", dataType=" + dataType + ", dataLen=" + dataLen
				+ ", dataScal=" + dataScal + ", infotpLnm=" + infotpLnm
				+ ", uppDmnId=" + uppDmnId + ", uppDmnLnm=" + uppDmnLnm
				+ ", mdlLnm=" + mdlLnm + ", uppSubjLnm=" + uppSubjLnm
				+ ", subjId=" + subjId + ", subjLnm=" + subjLnm
				+ ", lstEntyId=" + lstEntyId + ", lstEntyPnm=" + lstEntyPnm
				+ ", lstEntyLnm=" + lstEntyLnm + ", lstAttrId=" + lstAttrId
				+ ", lstAttrPnm=" + lstAttrPnm + ", lstAttrLnm=" + lstAttrLnm
				+ ", cdValTypCd=" + cdValTypCd + ", cdValIvwCd=" + cdValIvwCd
				+ ", dataFrm=" + dataFrm + ", sditmAutoCrtYn=" + sditmAutoCrtYn
				+ ", crgUserId=" + crgUserId + ", crgUserNm=" + crgUserNm
				+ ", dmnOrgDs=" + dmnOrgDs + ", dmnOrgTxt=" + dmnOrgTxt
				+ ", dmngId1=" + dmngId1 + ", dmngId2=" + dmngId2
				+ ", uppDmngId=" + uppDmngId + ", uppDmngLnm=" + uppDmngLnm
				+ ", encYn=" + encYn + ", dmnDscd=" + dmnDscd + ", cdValId="
				+ cdValId + ", cdVal=" + cdVal + ", cdValNm=" + cdValNm
				+ ", subCdYn=" + subCdYn + "]";
	}*/




	public String getInfoSysCd() {
		return infoSysCd;
	}

	public void setInfoSysCd(String infoSysCd) {
		this.infoSysCd = infoSysCd;
	}
}