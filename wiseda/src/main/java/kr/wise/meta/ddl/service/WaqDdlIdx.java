package kr.wise.meta.ddl.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqDdlIdx extends CommonVo {
//    private String rqstNo;
//
//    private Integer rqstSno;

    private String ddlIdxId;

    private String ddlIdxPnm;

    private String ddlIdxLnm;

//    private String rqstDcd;
//
//    private String rvwStsCd;
//
//    private String rvwConts;
//
//    private String vrfCd;
//
//    private String vrfRmk;

    private String dbConnTrgId;
    
    private String dbConnTrgPnm;

    private String dbSchId;

    private String dbSchPnm;

    private String ddlTblId;

    private String ddlTblPnm;
    private String ddlTblLnm;

    private String idxSpacId;

    private String idxSpacPnm;

    private String pkIdxYn;

    private String ukIdxYn;

    private String idxTypCd;
    
    private String ddlIdxColAsm;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date frsRqstDtm;
//
//    private String frsRqstUserId;
//
//    private Date rqstDtm;
//
//    private String rqstUserId;
//
//    private Date aprvDtm;
//
//    private String aprvUserId;

    private String scrtInfo;
    
    private String ddlTrgDcd;
    
    
    private String srcDdlTrgDcd;
    private String srcDbSchId;
    private String srcDbSchPnm;
    private String srcDbConnTrgPnm;
   
    private String tgtDdlTrgDcd;
    private String tgtDbSchId;
    private String tgtDbSchPnm;
    private String tgtDbConnTrgPnm;
    
    private String objDcd;
    
    

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

	public String getDdlIdxLnm() {
		return ddlIdxLnm;
	}

	public void setDdlIdxLnm(String ddlIdxLnm) {
		this.ddlIdxLnm = ddlIdxLnm;
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

	public String getDdlTblId() {
		return ddlTblId;
	}

	public void setDdlTblId(String ddlTblId) {
		this.ddlTblId = ddlTblId;
	}

	public String getDdlTblPnm() {
		return ddlTblPnm;
	}

	public void setDdlTblPnm(String ddlTblPnm) {
		this.ddlTblPnm = ddlTblPnm;
	}

	public String getIdxSpacId() {
		return idxSpacId;
	}

	public void setIdxSpacId(String idxSpacId) {
		this.idxSpacId = idxSpacId;
	}

	public String getIdxSpacPnm() {
		return idxSpacPnm;
	}

	public void setIdxSpacPnm(String idxSpacPnm) {
		this.idxSpacPnm = idxSpacPnm;
	}

	public String getPkIdxYn() {
		return pkIdxYn;
	}

	public void setPkIdxYn(String pkIdxYn) {
		this.pkIdxYn = pkIdxYn;
	}

	public String getUkIdxYn() {
		return ukIdxYn;
	}

	public void setUkIdxYn(String ukIdxYn) {
		this.ukIdxYn = ukIdxYn;
	}

	public String getIdxTypCd() {
		return idxTypCd;
	}

	public void setIdxTypCd(String idxTypCd) {
		this.idxTypCd = idxTypCd;
	}

	public String getScrtInfo() {
		return scrtInfo;
	}

	public void setScrtInfo(String scrtInfo) {
		this.scrtInfo = scrtInfo;
	}

	 
	
	public String getDdlTrgDcd() {
		return ddlTrgDcd;
	}

	public void setDdlTrgDcd(String ddlTrgDcd) {
		this.ddlTrgDcd = ddlTrgDcd;
	}

	public String getSrcDdlTrgDcd() {
		return srcDdlTrgDcd;
	}

	public void setSrcDdlTrgDcd(String srcDdlTrgDcd) {
		this.srcDdlTrgDcd = srcDdlTrgDcd;
	}

	public String getSrcDbSchId() {
		return srcDbSchId;
	}

	public void setSrcDbSchId(String srcDbSchId) {
		this.srcDbSchId = srcDbSchId;
	}

	public String getSrcDbSchPnm() {
		return srcDbSchPnm;
	}

	public void setSrcDbSchPnm(String srcDbSchPnm) {
		this.srcDbSchPnm = srcDbSchPnm;
	}

	public String getSrcDbConnTrgPnm() {
		return srcDbConnTrgPnm;
	}

	public void setSrcDbConnTrgPnm(String srcDbConnTrgPnm) {
		this.srcDbConnTrgPnm = srcDbConnTrgPnm;
	}

	public String getTgtDdlTrgDcd() {
		return tgtDdlTrgDcd;
	}

	public void setTgtDdlTrgDcd(String tgtDdlTrgDcd) {
		this.tgtDdlTrgDcd = tgtDdlTrgDcd;
	}

	public String getTgtDbSchId() {
		return tgtDbSchId;
	}

	public void setTgtDbSchId(String tgtDbSchId) {
		this.tgtDbSchId = tgtDbSchId;
	}

	public String getTgtDbSchPnm() {
		return tgtDbSchPnm;
	}

	public void setTgtDbSchPnm(String tgtDbSchPnm) {
		this.tgtDbSchPnm = tgtDbSchPnm;
	}

	public String getTgtDbConnTrgPnm() {
		return tgtDbConnTrgPnm;
	}

	public void setTgtDbConnTrgPnm(String tgtDbConnTrgPnm) {
		this.tgtDbConnTrgPnm = tgtDbConnTrgPnm;
	}

	public String getObjDcd() {
		return objDcd;
	}

	public void setObjDcd(String objDcd) {
		this.objDcd = objDcd;
	}
	
	
	public String getDdlTblLnm() {
		return ddlTblLnm;
	}

	public void setDdlTblLnm(String ddlTblLnm) {
		this.ddlTblLnm = ddlTblLnm;
	}
	
	
	public String getDdlIdxColAsm() {
		return ddlIdxColAsm;
	}

	public void setDdlIdxColAsm(String ddlIdxColAsm) {
		this.ddlIdxColAsm = ddlIdxColAsm;
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqDdlIdx [ddlIdxId=").append(ddlIdxId)
				.append(", ddlIdxPnm=").append(ddlIdxPnm)
				.append(", ddlIdxLnm=").append(ddlIdxLnm)
				.append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", dbConnTrgPnm=").append(dbConnTrgPnm)
				.append(", dbSchId=").append(dbSchId).append(", dbSchPnm=")
				.append(dbSchPnm).append(", ddlTblId=").append(ddlTblId)
				.append(", ddlTblPnm=").append(ddlTblPnm)
				.append(", idxSpacId=").append(idxSpacId)
				.append(", idxSpacPnm=").append(idxSpacPnm)
				.append(", pkIdxYn=").append(pkIdxYn).append(", ukIdxYn=")
				.append(ukIdxYn).append(", idxTypCd=").append(idxTypCd)
				.append(", scrtInfo=").append(scrtInfo).append("]");
		return builder.toString()+super.toString();
	}

}