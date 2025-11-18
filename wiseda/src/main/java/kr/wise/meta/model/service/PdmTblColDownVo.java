package kr.wise.meta.model.service;

import kr.wise.commons.cmm.CommonVo;

public class PdmTblColDownVo extends CommonVo{
    //검색조건
	private String subjId;	//주제영역ID
	
	private String subjLnm; //주제영역물리명
    
    private String pdmTblLnm;	//테이블논리명
	
    //테이블정의서
	private String dbConnTrgPnm;	//DB접속대상물리명
    
	private String dbConnAcId;	//DB접속계정ID
	
	private String pdmTblPnm;	//테이블물리명
	
	private String ObjDescn;	//설명	
	
	private String fullPath;	//주제영역 FULLPATH
	
	//컬럼정의서(추가)
	private String pdmColLnm;	//컬럼논리명
	
	private String pdmColPnm;	//컬럼물리명
	
	private String dataType;	//컬럼 데이터타입
	
	private String dataLen;	//컬럼 데이터길이
	
	private String nonulYn;	//컬럼 notnull 여부
	
	private String pkYn;	//컬럼 PK여부
	
	private String fkYn;	//컬럼 FK여부
	
	private String akYn;	//컬럼 AK여부
	
	private String encYn;	//컬럼 암호화여부

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

	public String getPdmTblLnm() {
		return pdmTblLnm;
	}

	public void setPdmTblLnm(String pdmTblLnm) {
		this.pdmTblLnm = pdmTblLnm;
	}

	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}

	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}

	public String getDbConnAcId() {
		return dbConnAcId;
	}

	public void setDbConnAcId(String dbConnAcId) {
		this.dbConnAcId = dbConnAcId;
	}

	public String getPdmTblPnm() {
		return pdmTblPnm;
	}

	public void setPdmTblPnm(String pdmTblPnm) {
		this.pdmTblPnm = pdmTblPnm;
	}

	public String getObjDescn() {
		return ObjDescn;
	}

	public void setObjDescn(String objDescn) {
		ObjDescn = objDescn;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getPdmColLnm() {
		return pdmColLnm;
	}

	public void setPdmColLnm(String pdmColLnm) {
		this.pdmColLnm = pdmColLnm;
	}

	public String getPdmColPnm() {
		return pdmColPnm;
	}

	public void setPdmColPnm(String pdmColPnm) {
		this.pdmColPnm = pdmColPnm;
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

	public String getNonulYn() {
		return nonulYn;
	}

	public void setNonulYn(String nonulYn) {
		this.nonulYn = nonulYn;
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

	public String getAkYn() {
		return akYn;
	}

	public void setAkYn(String akYn) {
		this.akYn = akYn;
	}

	public String getEncYn() {
		return encYn;
	}

	public void setEncYn(String encYn) {
		this.encYn = encYn;
	}
}