
package kr.wise.meta.ddl.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqDdlTbl extends CommonVo {
    
	private String rqstNo;

    private Integer rqstSno;

    private String ddlTblId; 

    private String ddlTblPnm;

    private String ddlTblLnm;

    private String bfDdlTblPnm;

//    private String rqstDcd;
//
//    private String rvwStsCd;
//
//    private String rvwConts;
//
//    private String vrfCd;
//
//    private String vrfRmk;

    private String dbConnTrgPnm;

    private String dbSchId;

    private String dbSchPnm;

    private String tblSpacId;

    private String tblSpacPnm;

    private String tblChgTypCd;

    private String pdmTblId;
    
    private String fullPath;
    
    private String scrtInfo;
   
    private String nonulUpdYn;
    private String dataTypeUpdYn;
    private String defltValUpdYn;  
    private String colUpdYn;

    
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
    
    private String ddlIdxId;
    private String ddlIdxPnm;
    private String idxSpacId;
    
    private String dbConnTrgId;
    
    private String idxSpacPnm;
    private String ukIdxYn;
    
    private String pdmRqstNo;
    
    private String tblCmmtUpdYn;
    
    private String partTblYn;
    
    private String encYn;

	public String getRqstNo() {
		return rqstNo;
	}

	public void setRqstNo(String rqstNo) {
		this.rqstNo = rqstNo;
	}

	public Integer getRqstSno() {
		return rqstSno;
	}

	public void setRqstSno(Integer rqstSno) {
		this.rqstSno = rqstSno;
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

	public String getDdlTblLnm() {
		return ddlTblLnm;
	}

	public void setDdlTblLnm(String ddlTblLnm) {
		this.ddlTblLnm = ddlTblLnm;
	}

	public String getBfDdlTblPnm() {
		return bfDdlTblPnm;
	}

	public void setBfDdlTblPnm(String bfDdlTblPnm) {
		this.bfDdlTblPnm = bfDdlTblPnm;
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

	public String getTblSpacId() {
		return tblSpacId;
	}

	public void setTblSpacId(String tblSpacId) {
		this.tblSpacId = tblSpacId;
	}

	public String getTblSpacPnm() {
		return tblSpacPnm;
	}

	public void setTblSpacPnm(String tblSpacPnm) {
		this.tblSpacPnm = tblSpacPnm;
	}

	public String getTblChgTypCd() {
		return tblChgTypCd;
	}

	public void setTblChgTypCd(String tblChgTypCd) {
		this.tblChgTypCd = tblChgTypCd;
	}

	public String getPdmTblId() {
		return pdmTblId;
	}

	public void setPdmTblId(String pdmTblId) {
		this.pdmTblId = pdmTblId;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getScrtInfo() {
		return scrtInfo;
	}

	public void setScrtInfo(String scrtInfo) {
		this.scrtInfo = scrtInfo;
	}

	public String getNonulUpdYn() {
		return nonulUpdYn;
	}

	public void setNonulUpdYn(String nonulUpdYn) {
		this.nonulUpdYn = nonulUpdYn;
	}

	public String getDataTypeUpdYn() {
		return dataTypeUpdYn;
	}

	public void setDataTypeUpdYn(String dataTypeUpdYn) {
		this.dataTypeUpdYn = dataTypeUpdYn;
	}

	public String getDefltValUpdYn() {
		return defltValUpdYn;
	}

	public void setDefltValUpdYn(String defltValUpdYn) {
		this.defltValUpdYn = defltValUpdYn;
	}

	public String getColUpdYn() {
		return colUpdYn;
	}

	public void setColUpdYn(String colUpdYn) {
		this.colUpdYn = colUpdYn;
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

	public String getIdxSpacId() {
		return idxSpacId;
	}

	public void setIdxSpacId(String idxSpacId) {
		this.idxSpacId = idxSpacId;
	}

	public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}

	public String getIdxSpacPnm() {
		return idxSpacPnm;
	}

	public void setIdxSpacPnm(String idxSpacPnm) {
		this.idxSpacPnm = idxSpacPnm;
	}

	public String getUkIdxYn() {
		return ukIdxYn;
	}

	public void setUkIdxYn(String ukIdxYn) {
		this.ukIdxYn = ukIdxYn;
	}

	public String getPdmRqstNo() {
		return pdmRqstNo;
	}

	public void setPdmRqstNo(String pdmRqstNo) {
		this.pdmRqstNo = pdmRqstNo;
	}

	public String getTblCmmtUpdYn() {
		return tblCmmtUpdYn;
	}

	public void setTblCmmtUpdYn(String tblCmmtUpdYn) {
		this.tblCmmtUpdYn = tblCmmtUpdYn;
	}

	public String getPartTblYn() {
		return partTblYn;
	}

	public void setPartTblYn(String partTblYn) {
		this.partTblYn = partTblYn;
	}


	public String getEncYn() {
		return encYn;
	}

	public void setEncYn(String encYn) {
		this.encYn = encYn;
	} 

	@Override
	public String toString() {
		return "WaqDdlTbl [rqstNo=" + rqstNo + ", rqstSno=" + rqstSno
				+ ", ddlTblId=" + ddlTblId + ", ddlTblPnm=" + ddlTblPnm
				+ ", ddlTblLnm=" + ddlTblLnm + ", bfDdlTblPnm=" + bfDdlTblPnm
				+ ", dbConnTrgPnm=" + dbConnTrgPnm + ", dbSchId=" + dbSchId
				+ ", dbSchPnm=" + dbSchPnm + ", tblSpacId=" + tblSpacId
				+ ", tblSpacPnm=" + tblSpacPnm + ", tblChgTypCd=" + tblChgTypCd
				+ ", pdmTblId=" + pdmTblId + ", fullPath=" + fullPath
				+ ", scrtInfo=" + scrtInfo + ", nonulUpdYn=" + nonulUpdYn
				+ ", dataTypeUpdYn=" + dataTypeUpdYn + ", defltValUpdYn="
				+ defltValUpdYn + ", colUpdYn=" + colUpdYn + ", ddlTrgDcd="
				+ ddlTrgDcd + ", srcDdlTrgDcd=" + srcDdlTrgDcd
				+ ", srcDbSchId=" + srcDbSchId + ", srcDbSchPnm=" + srcDbSchPnm
				+ ", srcDbConnTrgPnm=" + srcDbConnTrgPnm + ", tgtDdlTrgDcd="
				+ tgtDdlTrgDcd + ", tgtDbSchId=" + tgtDbSchId
				+ ", tgtDbSchPnm=" + tgtDbSchPnm + ", tgtDbConnTrgPnm="
				+ tgtDbConnTrgPnm + ", objDcd=" + objDcd + ", ddlIdxId="
				+ ddlIdxId + ", ddlIdxPnm=" + ddlIdxPnm + ", idxSpacId="
				+ idxSpacId + ", dbConnTrgId=" + dbConnTrgId + ", idxSpacPnm="
				+ idxSpacPnm + ", ukIdxYn=" + ukIdxYn + ", pdmRqstNo="
				+ pdmRqstNo + ", tblCmmtUpdYn=" + tblCmmtUpdYn + ", partTblYn="
				+ partTblYn + ", encYn=" + encYn + "]";
	}        
}