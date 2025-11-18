package kr.wise.sysinf.eai.service;

import kr.wise.commons.cmm.CommonVo;

public class Eai extends CommonVo {
	private Integer logSeq; 
	private String orglType;  
	private String dtlInfo; 
	private String dmnId; 
	private String endType;
	private String pgmType;
	
	public String getPgmType() {
		return pgmType;
	}
	public void setPgmType(String pgmType) {
		this.pgmType = pgmType;
	}
	
	public Integer getLogSeq() {
		return logSeq;
	}
	public void setLogSeq(Integer logSeq) {
		this.logSeq = logSeq;
	}
	public String getOrglType() {
		return orglType;
	}
	public void setOrglType(String orglType) {
		this.orglType = orglType;
	}
	public String getDtlInfo() {
		return dtlInfo;
	}
	public void setDtlInfo(String dtlInfo) {
		this.dtlInfo = dtlInfo;
	}
	public String getDmnId() {
		return dmnId;
	}
	public void setDmnId(String dmnId) {
		this.dmnId = dmnId;
	}
	public String getEndType() {
		return endType;
	}
	public void setEndType(String endType) {
		this.endType = endType;
	}
	
	
	
	
}