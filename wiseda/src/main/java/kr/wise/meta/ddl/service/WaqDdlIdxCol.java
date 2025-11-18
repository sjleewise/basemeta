package kr.wise.meta.ddl.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqDdlIdxCol extends CommonVo {
	
//    private String rqstNo;
//
//    private Integer rqstSno;
//
//    private Integer rqstDtlSno;

    private String ddlIdxColId;

    private String ddlIdxColPnm;

    private String ddlIdxColLnm;
    
    private String ddlIdxColAsm;

//    private String rqstDcd;
//
//    private String vrfCd;
//
//    private String vrfRmk;

    private String ddlIdxId;

    private String ddlIdxPnm;

    private String ddlColId;

    private Integer ddlIdxColOrd;

    private String sortTyp;
    
//  private String objDescn;
//
//  private Integer objVers;
//
//  private Date frsRqstDtm;
//
//  private String frsRqstUserId;
//
//  private Date rqstDtm;
//
//  private String rqstUserId;
//
//  private Date aprvDtm;
//
//  private String aprvUserId;
    
    private String dbConnTrgPnm;
    
    private String dbSchPnm;
    
    private String ddlTblPnm;
    
    private String idxSpacPnm;
    
    


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

	public String getDdlTblPnm() {
		return ddlTblPnm;
	}

	public void setDdlTblPnm(String ddlTblPnm) {
		this.ddlTblPnm = ddlTblPnm;
	}

	public String getIdxSpacPnm() {
		return idxSpacPnm;
	}

	public void setIdxSpacPnm(String idxSpacPnm) {
		this.idxSpacPnm = idxSpacPnm;
	}

	public String getDdlIdxColId() {
		return ddlIdxColId;
	}

	public void setDdlIdxColId(String ddlIdxColId) {
		this.ddlIdxColId = ddlIdxColId;
	}

	public String getDdlIdxColPnm() {
		return ddlIdxColPnm;
	}

	public void setDdlIdxColPnm(String ddlIdxColPnm) {
		this.ddlIdxColPnm = ddlIdxColPnm;
	}

	public String getDdlIdxColLnm() {
		return ddlIdxColLnm;
	}

	public void setDdlIdxColLnm(String ddlIdxColLnm) {
		this.ddlIdxColLnm = ddlIdxColLnm;
	}

	public String getDdlIdxId() {
		return ddlIdxId;
	}

	public void setDdlIdxId(String ddlIdxId) {
		this.ddlIdxId = ddlIdxId;
	}

	public String getDdlIdxPnm() {
		return ddlIdxPnm;
	}

	public void setDdlIdxPnm(String ddlIdxPnm) {
		this.ddlIdxPnm = ddlIdxPnm;
	}

	public String getDdlColId() {
		return ddlColId;
	}

	public void setDdlColId(String ddlColId) {
		this.ddlColId = ddlColId;
	}

	public Integer getDdlIdxColOrd() {
		return ddlIdxColOrd;
	}

	public void setDdlIdxColOrd(Integer ddlIdxColOrd) {
		this.ddlIdxColOrd = ddlIdxColOrd;
	}

	public String getSortTyp() {
		return sortTyp;
	}

	public void setSortTyp(String sortTyp) {
		this.sortTyp = sortTyp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqDdlIdxCol [ddlIdxColId=").append(ddlIdxColId)
				.append(", ddlIdxColPnm=").append(ddlIdxColPnm)
				.append(", ddlIdxColLnm=").append(ddlIdxColLnm)
				.append(", ddlIdxId=").append(ddlIdxId).append(", ddlIdxPnm=")
				.append(ddlIdxPnm).append(", ddlColId=").append(ddlColId)
				.append(", ddlIdxColOrd=").append(ddlIdxColOrd)
				.append(", sortTyp=").append(sortTyp).append(", dbConnTrgPnm=")
				.append(dbConnTrgPnm).append(", dbSchPnm=").append(dbSchPnm)
				.append(", ddlTblPnm=").append(ddlTblPnm)
				.append(", idxSpacPnm=").append(idxSpacPnm).append("]");
		return builder.toString()+super.toString();
	}   
    
 
}