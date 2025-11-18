package kr.wise.dq.model.service;

import kr.wise.commons.cmm.CommonVo;

public class WdqmPdmTbl extends CommonVo {
    private String pdmTblId;

    private String dbConnTrgId;

//    private String dbSchId;

    private String pdmTblPnm;

    private String pdmTblLnm;

    private String crgUserId;

//    private String objDescn;

//    private int objVers;

    private String regTypCd;

//    private String rqstDtm;

//    private String rqstUserId;
    
//    private String dbConnTrgPnm;
    private String dbConnTrgLnm;
    
//    private String dbSchPnm;
    private String dbSchLnm;
    
    private String pdmColLnm;
    private String pdmColPnm;

    public String getPdmColLnm() {
		return pdmColLnm;
	}

	public void setPdmColLnm(String pdmColLnm) {
		this.pdmColLnm = pdmColLnm;
	}

	public String getPdmColPnm() {
		return pdmColPnm;
	}

	public void setPdmColPnm(String pdmColPnm) {
		this.pdmColPnm = pdmColPnm;
	}
    

    public String getPdmTblId() {
        return pdmTblId;
    }

    public void setPdmTblId(String pdmTblId) {
        this.pdmTblId = pdmTblId;
    }

    public String getPdmTblPnm() {
        return pdmTblPnm;
    }

    public void setPdmTblPnm(String pdmTblPnm) {
        this.pdmTblPnm = pdmTblPnm;
    }

    public String getPdmTblLnm() {
        return pdmTblLnm;
    }

    public void setPdmTblLnm(String pdmTblLnm) {
        this.pdmTblLnm = pdmTblLnm;
    }

    public String getCrgUserId() {
        return crgUserId;
    }

    public void setCrgUserId(String crgUserId) {
        this.crgUserId = crgUserId;
    }


    public String getRegTypCd() {
        return regTypCd;
    }

    public void setRegTypCd(String regTypCd) {
        this.regTypCd = regTypCd;
    }

	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}

	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
	}

	public String getDbSchLnm() {
		return dbSchLnm;
	}

	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}

	public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}
}