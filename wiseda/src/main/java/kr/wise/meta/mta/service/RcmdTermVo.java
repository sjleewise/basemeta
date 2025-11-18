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

import kr.wise.commons.cmm.CommonVo;

public class RcmdTermVo extends CommonVo {

	private String DOCID       ;
	private String TERM_NM     ;
	private String TERM_REP    ;
	private String ENG_NM      ;
	private String ENG_ABB_NM  ;
	private String TERM_DEF    ;
	private String TERM_COR    ;
	private String SUB_AREA    ;
	private String CLA_NM      ;
	private String ORG_NM      ;
	private String ORG_NO      ;
	private String FST_REG_DATE;
	private String STATE       ;
	private String REG_DATE    ;
	private String REG_USER    ;
	private String MOD_DATE    ;
	private String MOD_USER    ;
	private String CLA_YN      ;
	private String BRM_NM	   ;
	private String BRM_CD	   ;
	public String getDOCID() {
		return DOCID;
	}
	public void setDOCID(String dOCID) {
		DOCID = dOCID;
	}
	public String getTERM_NM() {
		return TERM_NM;
	}
	public void setTERM_NM(String tERM_NM) {
		TERM_NM = tERM_NM;
	}
	public String getTERM_REP() {
		return TERM_REP;
	}
	public void setTERM_REP(String tERM_REP) {
		TERM_REP = tERM_REP;
	}
	public String getENG_NM() {
		return ENG_NM;
	}
	public void setENG_NM(String eNG_NM) {
		ENG_NM = eNG_NM;
	}
	public String getENG_ABB_NM() {
		return ENG_ABB_NM;
	}
	public void setENG_ABB_NM(String eNG_ABB_NM) {
		ENG_ABB_NM = eNG_ABB_NM;
	}
	public String getTERM_DEF() {
		return TERM_DEF;
	}
	public void setTERM_DEF(String tERM_DEF) {
		TERM_DEF = tERM_DEF;
	}
	public String getTERM_COR() {
		return TERM_COR;
	}
	public void setTERM_COR(String tERM_COR) {
		TERM_COR = tERM_COR;
	}
	public String getSUB_AREA() {
		return SUB_AREA;
	}
	public void setSUB_AREA(String sUB_AREA) {
		SUB_AREA = sUB_AREA;
	}
	public String getCLA_NM() {
		return CLA_NM;
	}
	public void setCLA_NM(String cLA_NM) {
		CLA_NM = cLA_NM;
	}
	public String getORG_NM() {
		return ORG_NM;
	}
	public void setORG_NM(String oRG_NM) {
		ORG_NM = oRG_NM;
	}
	public String getORG_NO() {
		return ORG_NO;
	}
	public void setORG_NO(String oRG_NO) {
		ORG_NO = oRG_NO;
	}
	public String getFST_REG_DATE() {
		return FST_REG_DATE;
	}
	public void setFST_REG_DATE(String fST_REG_DATE) {
		FST_REG_DATE = fST_REG_DATE;
	}
	public String getSTATE() {
		return STATE;
	}
	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}
	public String getREG_DATE() {
		return REG_DATE;
	}
	public void setREG_DATE(String rEG_DATE) {
		REG_DATE = rEG_DATE;
	}
	public String getREG_USER() {
		return REG_USER;
	}
	public void setREG_USER(String rEG_USER) {
		REG_USER = rEG_USER;
	}
	public String getMOD_DATE() {
		return MOD_DATE;
	}
	public void setMOD_DATE(String mOD_DATE) {
		MOD_DATE = mOD_DATE;
	}
	public String getMOD_USER() {
		return MOD_USER;
	}
	public void setMOD_USER(String mOD_USER) {
		MOD_USER = mOD_USER;
	}
	public String getCLA_YN() {
		return CLA_YN;
	}
	public void setCLA_YN(String cLA_YN) {
		CLA_YN = cLA_YN;
	}
	public String getBRM_NM() {
		return BRM_NM;
	}
	public void setBRM_NM(String bRM_NM) {
		BRM_NM = bRM_NM;
	}
	public String getBRM_CD() {
		return BRM_CD;
	}
	public void setBRM_CD(String bRM_CD) {
		BRM_CD = bRM_CD;
	}
	
	

}

