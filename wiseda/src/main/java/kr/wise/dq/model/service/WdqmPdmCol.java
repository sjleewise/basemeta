package kr.wise.dq.model.service;

import kr.wise.commons.cmm.CommonVo;

public class WdqmPdmCol extends CommonVo{
    private String pdmColId;

    private int pdmColSno;

    private String pdmColPnm;

    private String pdmColLnm;

    private String pdmTblId;

    private String dbConnTrgId;

    private String dbSchId;

    private int colOrd;

    private String dataType;

    private int dataLen;

    private int dataScal;

    private String pkYn;

    private int pkOrd;

    private String nonulYn;

    private String defltVal;

    private String objDescn;

    private int objVers;

    private String regTypCd;

    private String rqstDtm;

    private String rqstUserId;

    private String encYn;

    private String pdmTblLnm;
    
    private String pdmTblPnm;
    
    private String dbConnTrgLnm ;
    private String dbConnTrgPnm ;

    private String dbSchLnm  ;
    private String dbSchPnm  ;
    
    
    
    public String getDbSchLnm() {
		return dbSchLnm;
	}

	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}

	public String getDbSchPnm() {
		return dbSchPnm;
	}

	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
	}

	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}

	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
	}

	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}

	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}

	public String getPdmTblLnm() {
		return pdmTblLnm;
	}

	public void setPdmTblLnm(String pdmTblLnm) {
		this.pdmTblLnm = pdmTblLnm;
	}

	public String getPdmTblPnm() {
		return pdmTblPnm;
	}

	public void setPdmTblPnm(String pdmTblPnm) {
		this.pdmTblPnm = pdmTblPnm;
	}

	public String getPdmColId() {
        return pdmColId;
    }

    public void setPdmColId(String pdmColId) {
        this.pdmColId = pdmColId;
    }

    public int getPdmColSno() {
        return pdmColSno;
    }

    public void setPdmColSno(int pdmColSno) {
        this.pdmColSno = pdmColSno;
    }

    public String getPdmColPnm() {
        return pdmColPnm;
    }

    public void setPdmColPnm(String pdmColPnm) {
        this.pdmColPnm = pdmColPnm;
    }

    public String getPdmColLnm() {
        return pdmColLnm;
    }

    public void setPdmColLnm(String pdmColLnm) {
        this.pdmColLnm = pdmColLnm;
    }

    public String getPdmTblId() {
        return pdmTblId;
    }

    public void setPdmTblId(String pdmTblId) {
        this.pdmTblId = pdmTblId;
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

    public int getColOrd() {
        return colOrd;
    }

    public void setColOrd(int colOrd) {
        this.colOrd = colOrd;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getDataLen() {
        return dataLen;
    }

    public void setDataLen(int dataLen) {
        this.dataLen = dataLen;
    }

    public int getDataScal() {
        return dataScal;
    }

    public void setDataScal(int dataScal) {
        this.dataScal = dataScal;
    }

    public String getPkYn() {
        return pkYn;
    }

    public void setPkYn(String pkYn) {
        this.pkYn = pkYn;
    }

    public int getPkOrd() {
        return pkOrd;
    }

    public void setPkOrd(int pkOrd) {
        this.pkOrd = pkOrd;
    }

    public String getNonulYn() {
        return nonulYn;
    }

    public void setNonulYn(String nonulYn) {
        this.nonulYn = nonulYn;
    }

    public String getDefltVal() {
        return defltVal;
    }

    public void setDefltVal(String defltVal) {
        this.defltVal = defltVal;
    }

    public String getObjDescn() {
        return objDescn;
    }

    public void setObjDescn(String objDescn) {
        this.objDescn = objDescn;
    }

    public void setObjVers(int objVers) {
        this.objVers = objVers;
    }

    public String getRegTypCd() {
        return regTypCd;
    }

    public void setRegTypCd(String regTypCd) {
        this.regTypCd = regTypCd;
    }

    public void setRqstDtm(String rqstDtm) {
        this.rqstDtm = rqstDtm;
    }

    public String getRqstUserId() {
        return rqstUserId;
    }

    public void setRqstUserId(String rqstUserId) {
        this.rqstUserId = rqstUserId;
    }

    public String getEncYn() {
        return encYn;
    }

    public void setEncYn(String encYn) {
        this.encYn = encYn;
    }

	@Override
	public String toString() {
		return "WamPdmCol [" + (pdmColId != null ? "pdmColId=" + pdmColId + ", " : "") + "pdmColSno=" + pdmColSno + ", "
				+ (pdmColPnm != null ? "pdmColPnm=" + pdmColPnm + ", " : "")
				+ (pdmColLnm != null ? "pdmColLnm=" + pdmColLnm + ", " : "")
				+ (pdmTblId != null ? "pdmTblId=" + pdmTblId + ", " : "")
				+ (dbConnTrgId != null ? "dbConnTrgId=" + dbConnTrgId + ", " : "")
				+ (dbSchId != null ? "dbSchId=" + dbSchId + ", " : "") + "colOrd=" + colOrd + ", "
				+ (dataType != null ? "dataType=" + dataType + ", " : "") + "dataLen=" + dataLen + ", dataScal="
				+ dataScal + ", " + (pkYn != null ? "pkYn=" + pkYn + ", " : "") + "pkOrd=" + pkOrd + ", "
				+ (nonulYn != null ? "nonulYn=" + nonulYn + ", " : "")
				+ (defltVal != null ? "defltVal=" + defltVal + ", " : "")
				+ (objDescn != null ? "objDescn=" + objDescn + ", " : "") + "objVers=" + objVers + ", "
				+ (regTypCd != null ? "regTypCd=" + regTypCd + ", " : "")
				+ (rqstDtm != null ? "rqstDtm=" + rqstDtm + ", " : "")
				+ (rqstUserId != null ? "rqstUserId=" + rqstUserId + ", " : "")
				+ (encYn != null ? "encYn=" + encYn + ", " : "")
				+ (pdmTblLnm != null ? "pdmTblLnm=" + pdmTblLnm + ", " : "")
				+ (pdmTblPnm != null ? "pdmTblPnm=" + pdmTblPnm : "") + "]";
	}
    
    
}