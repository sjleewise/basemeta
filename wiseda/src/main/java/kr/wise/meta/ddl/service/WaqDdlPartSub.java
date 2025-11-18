package kr.wise.meta.ddl.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqDdlPartSub extends CommonVo {

    private String ddlSubPartId;

    private String ddlPartSubPnm;

    private String ddlMainPartId;

    private String ddlPartPnm;


    private String ddlPartId;

    private String ddlTblId;

    private String ddlTblPnm;

    private String ddlPartSubVal;

    private String tblSpacId;

    private String tblSpacPnm;
    
    private String subRegTypCd;



    public String getSubRegTypCd() {
		return subRegTypCd;
	}

	public void setSubRegTypCd(String subRegTypCd) {
		this.subRegTypCd = subRegTypCd;
	}

	public String getDdlSubPartId() {
        return ddlSubPartId;
    }

    public void setDdlSubPartId(String ddlSubPartId) {
        this.ddlSubPartId = ddlSubPartId;
    }

    public String getDdlPartSubPnm() {
        return ddlPartSubPnm;
    }

    public void setDdlPartSubPnm(String ddlPartSubPnm) {
        this.ddlPartSubPnm = ddlPartSubPnm;
    }

    public String getDdlMainPartId() {
        return ddlMainPartId;
    }

    public void setDdlMainPartId(String ddlMainPartId) {
        this.ddlMainPartId = ddlMainPartId;
    }

    public String getDdlPartPnm() {
        return ddlPartPnm;
    }

    public void setDdlPartPnm(String ddlPartPnm) {
        this.ddlPartPnm = ddlPartPnm;
    }


    public String getDdlPartId() {
        return ddlPartId;
    }

    public void setDdlPartId(String ddlPartId) {
        this.ddlPartId = ddlPartId;
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

    public String getDdlPartSubVal() {
        return ddlPartSubVal;
    }

    public void setDdlPartSubVal(String ddlPartSubVal) {
        this.ddlPartSubVal = ddlPartSubVal;
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

}