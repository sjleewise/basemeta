package kr.wise.dq.dqrs.service;

import kr.wise.commons.cmm.CommonVo;
import java.util.Date;



public class DqrsDmn extends CommonVo {
	
	private String dmnId; //도메인 아이디
		
	private String govYn;

	private String sysAreaId; // 시스템물리명
//	private String dbConnTrgPnm; // 디비논리명
//	private String dbSchPnm;//스키마 물리명
	
	private String uppDmngNm; //도메인 그룹명
	private String dmngNm; // 도메인 분류명
	private String dmnNm; // 도메인명 
	
	private String objDescn; //공통표준도메인설명
	
	private String rqstDcn; //요청구분코드
	private String vrfRmk;//검증결과
	
	private String dataType;
	private String dataLen;
	private String dataScal;
	
	private String dicOrgn; // 출처
	
	private String regDtm; //등록일시
	private String regUserId; //등록아이디
	
	private String excYn; //엑셀여부
	
	private String infoSysCd;
	
	private String allDbms;
	private String allDbmsSchId;
//	private String dbSchId;
//	private String dbConnTrgId;
	


	public String getDmnId() {
		return dmnId;
	}

	public String getAllDbms() {
		return allDbms;
	}

	public void setAllDbms(String allDbms) {
		this.allDbms = allDbms;
	}

	public String getAllDbmsSchId() {
		return allDbmsSchId;
	}

	public void setAllDbmsSchId(String allDbmsSchId) {
		this.allDbmsSchId = allDbmsSchId;
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

	
	public String getInfoSysCd() {
		return infoSysCd;
	}

	public void setInfoSysCd(String infoSysCd) {
		this.infoSysCd = infoSysCd;
	}

	public String getGovYn() {
		return govYn;
	}

	public void setGovYn(String govYn) {
		this.govYn = govYn;
	}

	@Override
	public String toString() {
		return "DqrsDmn [dmnId=" + dmnId + ", govYn=" + govYn + ", sysAreaId="
				+ sysAreaId + ", uppDmngNm=" + uppDmngNm + ", dmngNm=" + dmngNm
				+ ", dmnNm=" + dmnNm + ", objDescn=" + objDescn + ", rqstDcn="
				+ rqstDcn + ", vrfRmk=" + vrfRmk + ", dataType=" + dataType
				+ ", dataLen=" + dataLen + ", dataScal=" + dataScal
				+ ", dicOrgn=" + dicOrgn + ", regDtm=" + regDtm
				+ ", regUserId=" + regUserId + ", excYn=" + excYn
				+ ", infoSysCd=" + infoSysCd + ", allDbms=" + allDbms
				+ ", allDbmsSchId=" + allDbmsSchId + "]";
	}
}