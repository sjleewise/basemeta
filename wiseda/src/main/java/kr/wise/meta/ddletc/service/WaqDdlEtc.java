package kr.wise.meta.ddletc.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqDdlEtc extends CommonVo {
    
	private String ddlEtcId;
	
	private String dbConnTrgId;
	
	private String etcObjDcd;
	
	private String dbConnTrgPnm;
	
	private String dbSchId;
	
	private String dbSchPnm;

	private String ddlEtcPnm;
	
	private String ddlEtcLnm;
	
	private String scrtInfo;
	
	private String etcObjDcdNm;

	public String getDdlEtcId() {
		return ddlEtcId;
	}

	public void setDdlEtcId(String ddlEtcId) {
		this.ddlEtcId = ddlEtcId;
	}

	public String getDdlEtcPnm() {
		return ddlEtcPnm;
	}

	public void setDdlEtcPnm(String ddlEtcPnm) {
		this.ddlEtcPnm = ddlEtcPnm;
	}

	public String getDdlEtcLnm() {
		return ddlEtcLnm;
	}

	public void setDdlEtcLnm(String ddlEtcLnm) {
		this.ddlEtcLnm = ddlEtcLnm;
	}

	public String getScrtInfo() {
		return scrtInfo;
	}

	public void setScrtInfo(String scrtInfo) {
		this.scrtInfo = scrtInfo;
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

	public String getEtcObjDcd() {
		return etcObjDcd;
	}

	public void setEtcObjDcd(String etcObjDcd) {
		this.etcObjDcd = etcObjDcd;
	}
	
	public String getEtcObjDcdNm() {
		return etcObjDcdNm;
	}
	
	public void setEtcObjDcdNm(String etcObjDcdNm) {
		this.etcObjDcdNm = etcObjDcdNm;
	}
		
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("WaqDdlEtc [")
		.append("ddlEtcId=").append(ddlEtcId)
		.append("dbConnTrgId=").append(dbConnTrgId)
		.append("etcObjDcd=").append(etcObjDcd)
		.append("dbConnTrgPnm=").append(dbConnTrgPnm)
		.append("dbSchId=").append(dbSchId)
		.append("dbSchPnm=").append(dbSchPnm)
		.append("ddlEtcPnm=").append(ddlEtcPnm)
		.append("ddlEtcLnm=").append(ddlEtcLnm).append("]");
		return builder.toString() + super.toString();
	}
    
}