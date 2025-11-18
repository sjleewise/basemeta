package kr.wise.meta.ddl.service;

import kr.wise.commons.cmm.CommonVo;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WamDdlTbl.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 4. 24. 오후 6:50:58
 * </PRE>
 */ 
public class WamDdlTbl extends CommonVo{
    private String ddlTblId;

    private String ddlTblPnm;

    private String ddlTblLnm;

    private String dbSchId;
    
    private String dbSchPnm;

    private String tblSpacId;
    
    private String tblSpacPnm;

    private String pdmTblId;

    private String scrtInfo;
    
    private String prcTypCd;
    
    private String prcDt;
    
    private String prcDbaId;
    
    private String prcDbaNm;
    
    private String dbSchLnm;
    
    private String dbConnTrgId;
    
    private String dbConnTrgLnm;
    
    private String dbConnTrgPnm;
    
    private String pdmTblLnm;
    
    private String pdmTblPnm;
    
    private String objId;
    
    private String objLnm;
    
    private String objPnm;
    
    private String objDcd;
    
    private String tblTypCd;
    
    private String searchBgnDe;
    
    private String searchEndDe;
    
    private String ddlColLnm;
    
    private String subjId;
    
    private String scrnDcd;
    
    private String ddlIdxPnm;
    private String ddlIdxLnm;
    
    private String partTblYn;
    
    private String ddlTrgDcd;
    
    private String srcDdlTrgDcd;
    private String srcDbSchId;
    private String srcDbSchPnm;
    private String srcDdlTblId;
    private String srcDdlTblPnm;
    
    private String tgtDdlTrgDcd;
    private String tgtDbSchId;
    private String tgtDbSchPnm;
    private String tgtDdlTblId;
    private String tgtDdlTblPnm;
    
    private String encYn;
    
    private String sysAreaId;
    
    private String idxSpacId;
    private String idxSpacPnm;
    
    private String expDtmStr;
    

    public String getSubjId() {
		return subjId;
	}

	public void setSubjId(String subjId) {
		this.subjId = subjId;
	}

	public String getDdlColLnm() {
		return ddlColLnm;
	}

	public void setDdlColLnm(String ddlColLnm) {
		this.ddlColLnm = ddlColLnm;
	}

	public String getPdmTblLnm() {
		return pdmTblLnm;
	}

	public void setPdmTblLnm(String pdmTblLnm) {
		this.pdmTblLnm = pdmTblLnm;
	}

	public String getDbSchLnm() {
		return dbSchLnm;
	}

	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}

	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}

	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
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

    public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}

	public String getDbSchId() {
        return dbSchId;
    }

    public void setDbSchId(String dbSchId) {
        this.dbSchId = dbSchId;
    }

    public String getPrcTypCd() {
		return prcTypCd;
	}

	public void setPrcTypCd(String prcTypCd) {
		this.prcTypCd = prcTypCd;
	}

	public String getPrcDt() {
		return prcDt;
	}

	public void setPrcDt(String prcDt) {
		this.prcDt = prcDt;
	}

	public String getPrcDbaId() {
		return prcDbaId;
	}

	public void setPrcDbaId(String prcDbaId) {
		this.prcDbaId = prcDbaId;
	}

	public String getPrcDbaNm() {
		return prcDbaNm;
	}

	public void setPrcDbaNm(String prcDbaNm) {
		this.prcDbaNm = prcDbaNm;
	}

	public String getTblSpacId() {
        return tblSpacId;
    }

    public void setTblSpacId(String tblSpacId) {
        this.tblSpacId = tblSpacId;
    }

    public String getPdmTblId() {
        return pdmTblId;
    }

    public void setPdmTblId(String pdmTblId) {
        this.pdmTblId = pdmTblId;
    }


    public String getScrtInfo() {
        return scrtInfo;
    }

    public void setScrtInfo(String scrtInfo) {
        this.scrtInfo = scrtInfo;
    }

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getObjLnm() {
		return objLnm;
	}

	public void setObjLnm(String objLnm) {
		this.objLnm = objLnm;
	}

	public String getObjPnm() {
		return objPnm;
	}

	public void setObjPnm(String objPnm) {
		this.objPnm = objPnm;
	}

	public String getObjDcd() {
		return objDcd;
	}

	public void setObjDcd(String objDcd) {
		this.objDcd = objDcd;
	}

	public String getTblTypCd() {
		return tblTypCd;
	}

	public void setTblTypCd(String tblTypCd) {
		this.tblTypCd = tblTypCd;
	}

	public String getSearchBgnDe() {
		return searchBgnDe;
	}

	public void setSearchBgnDe(String searchBgnDe) {
		this.searchBgnDe = searchBgnDe;
	}

	public String getSearchEndDe() {
		return searchEndDe;
	}

	public void setSearchEndDe(String searchEndDe) {
		this.searchEndDe = searchEndDe;
	}
	
	
	public String getScrnDcd() {
		return scrnDcd;
	}

	public void setScrnDcd(String scrnDcd) {
		this.scrnDcd = scrnDcd;
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

	public String getSrcDdlTblId() {
		return srcDdlTblId;
	}

	public void setSrcDdlTblId(String srcDdlTblId) {
		this.srcDdlTblId = srcDdlTblId;
	}

	public String getSrcDdlTblPnm() {
		return srcDdlTblPnm;
	}

	public void setSrcDdlTblPnm(String srcDdlTblPnm) {
		this.srcDdlTblPnm = srcDdlTblPnm;
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

	public String getTgtDdlTblId() {
		return tgtDdlTblId;
	}

	public void setTgtDdlTblId(String tgtDdlTblId) {
		this.tgtDdlTblId = tgtDdlTblId;
	}

	public String getTgtDdlTblPnm() {
		return tgtDdlTblPnm;
	}

	public void setTgtDdlTblPnm(String tgtDdlTblPnm) {
		this.tgtDdlTblPnm = tgtDdlTblPnm;
	}
	
	
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
	
	public String getDdlTrgDcd() {
		return ddlTrgDcd;
	}

	public void setDdlTrgDcd(String ddlTrgDcd) {
		this.ddlTrgDcd = ddlTrgDcd;
	}
	
	
	public String getPartTblYn() {
		return partTblYn;
	}

	public void setPartTblYn(String partTblYn) {
		this.partTblYn = partTblYn;
	}
	
	public String getPdmTblPnm() {
		return pdmTblPnm;
	}

	public void setPdmTblPnm(String pdmTblPnm) {
		this.pdmTblPnm = pdmTblPnm;
	}

	public String getEncYn() {
		return encYn;
	}

	public void setEncYn(String encYn) {
		this.encYn = encYn;
	}
	
	public String getSysAreaId() {
		return sysAreaId;
	}

	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
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

	public String getExpDtmStr() {
		return expDtmStr;
	}

	public void setExpDtmStr(String expDtmStr) {
		this.expDtmStr = expDtmStr;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamDdlTbl [ddlTblId=").append(ddlTblId)
				.append(", ddlTblPnm=").append(ddlTblPnm)
				.append(", ddlTblLnm=").append(ddlTblLnm).append(", dbSchId=")
				.append(dbSchId).append(", tblSpacId=").append(tblSpacId)
				.append(", pdmTblId=").append(pdmTblId).append(", scrtInfo=")
				.append(scrtInfo).append(", prcTypCd=").append(prcTypCd)
				.append(", prcDt=").append(prcDt).append(", prcDbaId=")
				.append(prcDbaId).append(", prcDbaNm=").append(prcDbaNm)
				.append(", dbSchLnm=").append(dbSchLnm)
				.append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", dbConnTrgLnm=").append(dbConnTrgLnm)
				.append(", pdmTblLnm=").append(pdmTblLnm).append(", objId=")
				.append(objId).append(", objLnm=").append(objLnm)
				.append(", objPnm=").append(objPnm).append(", objDcd=")
				.append(objDcd).append(", tblTypCd=").append(tblTypCd)
				.append(", searchBgnDe=").append(searchBgnDe)
				.append(", searchEndDe=").append(searchEndDe)
				.append(", ddlColLnm=").append(ddlColLnm).append(", subjId=")
				.append(subjId).append("]");
		return builder.toString()+super.toString();
	}

	public String getTblSpacPnm() {
		return tblSpacPnm;
	}

	public void setTblSpacPnm(String tblSpacPnm) {
		this.tblSpacPnm = tblSpacPnm;
	}
    
}