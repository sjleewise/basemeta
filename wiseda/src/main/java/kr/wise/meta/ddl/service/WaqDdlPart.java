package kr.wise.meta.ddl.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqDdlPart extends CommonVo {

    private String ddlPartId;


    private String dbConnTrgId;

    private String dbConnTrgPnm;

    private String dbSchId;

    private String dbSchPnm;

    private String ddlTblId;

    private String ddlTblPnm;
    
    private String ddlTblLnm;

    private String tblSpacId;

    private String tblSpacPnm;

    private String partTypCd;

    private String partKey;

    private String subPartTypCd;

    private String subPartKey;


    private String scrtInfo;

    private String ddlTrgDcd;
    private String srcDbSchId;
    private String srcDdlPartId;
    //DDL 적용요청코드, 일자
    private String aplReqTypCd;
    private String aplReqdt;
    
    private String fullPath;
    private String sysAreaId;
    private String sysAreaLnm;
    
    private String prjMngNo;
    private String srMngNo;
    private String cudYn;
    private String objDcd;
    private String objId;
    
    
    public String getObjDcd() {
		return objDcd;
	}

	public void setObjDcd(String objDcd) {
		this.objDcd = objDcd;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getDdlPartId() {
        return ddlPartId;
    }

    public void setDdlPartId(String ddlPartId) {
        this.ddlPartId = ddlPartId;
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

    public String getPartTypCd() {
        return partTypCd;
    }

    public void setPartTypCd(String partTypCd) {
        this.partTypCd = partTypCd;
    }

    public String getPartKey() {
        return partKey;
    }

    public void setPartKey(String partKey) {
        this.partKey = partKey;
    }

    public String getSubPartTypCd() {
        return subPartTypCd;
    }

    public void setSubPartTypCd(String subPartTypCd) {
        this.subPartTypCd = subPartTypCd;
    }

    public String getSubPartKey() {
        return subPartKey;
    }

    public void setSubPartKey(String subPartKey) {
        this.subPartKey = subPartKey;
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

	public String getSrcDbSchId() {
		return srcDbSchId;
	}

	public void setSrcDbSchId(String srcDbSchId) {
		this.srcDbSchId = srcDbSchId;
	}

	public String getSrcDdlPartId() {
		return srcDdlPartId;
	}

	public void setSrcDdlPartId(String srcDdlPartId) {
		this.srcDdlPartId = srcDdlPartId;
	}

	public String getDdlTblLnm() {
		return ddlTblLnm;
	}

	public void setDdlTblLnm(String ddlTblLnm) {
		this.ddlTblLnm = ddlTblLnm;
	}

	public String getAplReqTypCd() {
		return aplReqTypCd;
	}

	public void setAplReqTypCd(String aplReqTypCd) {
		this.aplReqTypCd = aplReqTypCd;
	}

	public String getAplReqdt() {
		return aplReqdt;
	}

	public void setAplReqdt(String aplReqdt) {
		this.aplReqdt = aplReqdt;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getSysAreaId() {
		return sysAreaId;
	}

	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
	}

	public String getSysAreaLnm() {
		return sysAreaLnm;
	}

	public void setSysAreaLnm(String sysAreaLnm) {
		this.sysAreaLnm = sysAreaLnm;
	}

	public String getPrjMngNo() {
		return prjMngNo;
	}

	public void setPrjMngNo(String prjMngNo) {
		this.prjMngNo = prjMngNo;
	}

	public String getSrMngNo() {
		return srMngNo;
	}

	public void setSrMngNo(String srMngNo) {
		this.srMngNo = srMngNo;
	}

	public String getCudYn() {
		return cudYn;
	}

	public void setCudYn(String cudYn) {
		this.cudYn = cudYn;
	}

	
    
}