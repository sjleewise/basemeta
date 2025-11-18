package kr.wise.advisor.prepare.textcluster.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.cmm.CommonVo;

public class WadDataMtcTbl extends CommonVo {
    
	private String mtcId            ;
	private String srcDbConnTrgId   ;
	private String srcDbcSchId      ;
	private String srcDbcTblNm      ;
	private String srcAdtCndSql     ;
	private String tgtDbConnTrgId   ;
	private String tgtDbcSchId      ;
	private String tgtDbcTblNm      ;
	private String tgtAdtCndSql     ;
	
	private List<WadDataMtcCol> colList;  
	
	
	public String getMtcId() {
		return mtcId;
	}
	public void setMtcId(String mtcId) {
		this.mtcId = mtcId;
	}
	public String getSrcDbConnTrgId() {
		return srcDbConnTrgId;
	}
	public void setSrcDbConnTrgId(String srcDbConnTrgId) {
		this.srcDbConnTrgId = srcDbConnTrgId;
	}
	public String getSrcDbcSchId() {
		return srcDbcSchId;
	}
	public void setSrcDbcSchId(String srcDbcSchId) {
		this.srcDbcSchId = srcDbcSchId;
	}
	public String getSrcDbcTblNm() {
		return srcDbcTblNm;
	}
	public void setSrcDbcTblNm(String srcDbcTblNm) {
		this.srcDbcTblNm = srcDbcTblNm;
	}
	public String getSrcAdtCndSql() {
		return srcAdtCndSql;
	}
	public void setSrcAdtCndSql(String srcAdtCndSql) {
		this.srcAdtCndSql = srcAdtCndSql;
	}
	public String getTgtDbConnTrgId() {
		return tgtDbConnTrgId;
	}
	public void setTgtDbConnTrgId(String tgtDbConnTrgId) {
		this.tgtDbConnTrgId = tgtDbConnTrgId;
	}
	public String getTgtDbcSchId() {
		return tgtDbcSchId;
	}
	public void setTgtDbcSchId(String tgtDbcSchId) {
		this.tgtDbcSchId = tgtDbcSchId;
	}
	public String getTgtDbcTblNm() {
		return tgtDbcTblNm;
	}
	public void setTgtDbcTblNm(String tgtDbcTblNm) {
		this.tgtDbcTblNm = tgtDbcTblNm;
	}
	public String getTgtAdtCndSql() {
		return tgtAdtCndSql;
	}
	public void setTgtAdtCndSql(String tgtAdtCndSql) {
		this.tgtAdtCndSql = tgtAdtCndSql;
	}
	
	public List<WadDataMtcCol> getColList() {
		return colList;
	}
	public void setColList(List<WadDataMtcCol> colList) {
		this.colList = colList;
	}
	
	
	
	
	

}