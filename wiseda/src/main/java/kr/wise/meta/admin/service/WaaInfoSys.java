/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : WaaInfoSys.java
 * 2. Package : kr.wise.meta.admin.service
 * 3. Comment : 기관메타 > 관리자 - 정보시스템관리
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.12.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.12. :            : 신규 개발.
 */
package kr.wise.meta.admin.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaInfoSys extends CommonVo {
	
	
	//private String orgCd;
	private String orgNm;
	private String ibsStatus;
	private String infoSysCd;
	//private String expDtm;
	//private String strDtm;
	private String infoSysNm;
	private String objDescn;
	//private Integer objVers;
	//private String writUserId;
	//private String writDtm;
	private String relLaw;
	private String constPurp;
	private String constYy;
	private Integer totInvBdgt;
	private Integer operBdgt;
	private String operDeptNm;
	private String crgUserNm;
	private String crgTelNo;
	private String crgEmailAddr;
	
	//private String expYn; //만료여부 ibsStatus
	
	//정보시스템명 중복 체크를 위한 건수 변수
	private Integer userInfoSysCnt;
	private Integer infoSysCnt;
	//2019.5.22
	private String orgCd;
	private String mappingUserId;
	
	
	public Integer getUserInfoSysCnt() {
		return userInfoSysCnt;
	}
	public void setUserInfoSysCnt(Integer userInfoSysCnt) {
		this.userInfoSysCnt = userInfoSysCnt;
	}
	public Integer getInfoSysCnt() {
		return infoSysCnt;
	}
	public void setInfoSysCnt(Integer infoSysCnt) {
		this.infoSysCnt = infoSysCnt;
	}
	public String getIbsStatus() {
		return ibsStatus;
	}
	public void setIbsStatus(String ibsStatus) {
		this.ibsStatus = ibsStatus;
	}
	public String getObjDescn() {
		return objDescn;
	}
	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
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
	public String getConstPurp() {
		return constPurp;
	}
	public void setConstPurp(String constPurp) {
		this.constPurp = constPurp;
	}
	public String getConstYy() {
		return constYy;
	}
	public void setConstYy(String constYy) {
		this.constYy = constYy;
	}
	public Integer getTotInvBdgt() {
		return totInvBdgt;
	}
	public void setTotInvBdgt(Integer totInvBdgt) {
		this.totInvBdgt = totInvBdgt;
	}
	public Integer getOperBdgt() {
		return operBdgt;
	}
	public void setOperBdgt(Integer operBdgt) {
		this.operBdgt = operBdgt;
	}
	public String getOperDeptNm() {
		return operDeptNm;
	}
	public void setOperDeptNm(String operDeptNm) {
		this.operDeptNm = operDeptNm;
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
	@Override
	public String toString() {
		return "WaaInfoSys [orgNm=" + orgNm + ", infoSysCd=" + infoSysCd + ", infoSysNm=" + infoSysNm + ", relLaw="
				+ relLaw + ", constPurp=" + constPurp + ", constYy=" + constYy + ", totInvBdgt=" + totInvBdgt
				+ ", operBdgt=" + operBdgt + ", operDeptNm=" + operDeptNm + ", crgUserNm=" + crgUserNm + ", crgTelNo="
				+ crgTelNo + ", crgEmailAddr=" + crgEmailAddr + ", ibsStatus=" + ibsStatus + "]";
	}
	public String getOrgCd() {
		return orgCd;
	}
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}
	public String getMappingUserId() {
		return mappingUserId;
	}
	public void setMappingUserId(String mappingUserId) {
		this.mappingUserId = mappingUserId;
	}
	
	
	
}
