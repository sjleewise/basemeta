package kr.wise.meta.ddl.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqDdlPartMain extends CommonVo {

    private String ddlMainPartId;

    private String ddlPartPnm;


    private String ddlPartId;

    private String ddlTblId;

    private String ddlTblPnm;

    private String ddlPartVal;

    private String tblSpacId;

    private String tblSpacPnm;
    
    private String ddlTrgDcd;
    
    private String mainRegTypCd;



    public String getMainRegTypCd() {
		return mainRegTypCd;
	}

	public void setMainRegTypCd(String mainRegTypCd) {
		this.mainRegTypCd = mainRegTypCd;
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

    public String getDdlPartVal() {
        return ddlPartVal;
    }

    public void setDdlPartVal(String ddlPartVal) {
        this.ddlPartVal = ddlPartVal;
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

	public String getDdlTrgDcd() {
		return ddlTrgDcd;
	}

	public void setDdlTrgDcd(String ddlTrgDcd) {
		this.ddlTrgDcd = ddlTrgDcd;
	}

}