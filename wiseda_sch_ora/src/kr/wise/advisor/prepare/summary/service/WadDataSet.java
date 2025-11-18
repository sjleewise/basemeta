package kr.wise.advisor.prepare.summary.service;

import kr.wise.commons.cmm.CommonVo;

public class WadDataSet extends CommonVo {
    
	private String daseId ;
	private String rqstNo ;
	private String daseLnm;
	private String dasePnm;
	private String dbSchId; 
	private String dbTblNm;
	
	
	public String getDaseId() {
		return daseId;
	}
	public void setDaseId(String daseId) {
		this.daseId = daseId;
	}
	public String getRqstNo() {
		return rqstNo;
	}
	public void setRqstNo(String rqstNo) {
		this.rqstNo = rqstNo;
	}
	public String getDaseLnm() {
		return daseLnm;
	}
	public void setDaseLnm(String daseLnm) {
		this.daseLnm = daseLnm;
	}
	public String getDasePnm() {
		return dasePnm;
	}
	public void setDasePnm(String dasePnm) {
		this.dasePnm = dasePnm;
	}
	public String getDbSchId() {
		return dbSchId;
	}
	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}
	public String getDbTblNm() {
		return dbTblNm;
	}
	public void setDbTblNm(String dbTblNm) {
		this.dbTblNm = dbTblNm;
	}
	
	
	

}