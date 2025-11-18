package kr.wise.meta.ddl.service;

import kr.wise.commons.cmm.CommonVo;

public class WamDdlIdx extends CommonVo{
    private String ddlIdxId;

    private String ddlIdxPnm;

    private String ddlIdxLnm;

    private String dbConnTrgId;

    private String dbSchId;

    private String ddlTblId;

    private String idxSpacId;

    private String pkIdxYn;

    private String ukIdxYn;

    private String idxTypCd;
    
    private String dbConnTrgPnm;
    
    private String dbSchPnm;

    private String ddlTblPnm;
    
    private String ddlTblLnm;
    
    private String ddlColPnm;
    
    private String idxSpacPnm;
    
    private String ddlIdxColAsm;
    
//    private String rqstNo;
//
//    private Integer rqstSno;
//
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

	public String getIdxSpacPnm() {
		return idxSpacPnm;
	}

	public void setIdxSpacPnm(String idxSpacPnm) {
		this.idxSpacPnm = idxSpacPnm;
	}

	private String scrtInfo;

    public String getDdlTblPnm() {
		return ddlTblPnm;
	}

	public void setDdlTblPnm(String ddlTblPnm) {
		this.ddlTblPnm = ddlTblPnm;
	}

	public String getDdlColPnm() {
		return ddlColPnm;
	}

	public void setDdlColPnm(String ddlColPnm) {
		this.ddlColPnm = ddlColPnm;
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

    public String getDbSchId() {
        return dbSchId;
    }

    public void setDbSchId(String dbSchId) {
        this.dbSchId = dbSchId;
    }

    public String getDdlTblId() {
        return ddlTblId;
    }

    public void setDdlTblId(String ddlTblId) {
        this.ddlTblId = ddlTblId;
    }

    public String getIdxSpacId() {
        return idxSpacId;
    }

    public void setIdxSpacId(String idxSpacId) {
        this.idxSpacId = idxSpacId;
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

//    public String getRqstNo() {
//        return rqstNo;
//    }
//
//    public void setRqstNo(String rqstNo) {
//        this.rqstNo = rqstNo;
//    }
//
//    public Integer getRqstSno() {
//        return rqstSno;
//    }
//
//    public void setRqstSno(Integer rqstSno) {
//        this.rqstSno = rqstSno;
//    }
//
//    public String getObjDescn() {
//        return objDescn;
//    }
//
//    public void setObjDescn(String objDescn) {
//        this.objDescn = objDescn;
//    }
//
//    public Integer getObjVers() {
//        return objVers;
//    }
//
//    public void setObjVers(Integer objVers) {
//        this.objVers = objVers;
//    }
//
//    public String getRegTypCd() {
//        return regTypCd;
//    }
//
//    public void setRegTypCd(String regTypCd) {
//        this.regTypCd = regTypCd;
//    }
//
//    public Date getFrsRqstDtm() {
//        return frsRqstDtm;
//    }
//
//    public void setFrsRqstDtm(Date frsRqstDtm) {
//        this.frsRqstDtm = frsRqstDtm;
//    }
//
//    public String getFrsRqstUserId() {
//        return frsRqstUserId;
//    }
//
//    public void setFrsRqstUserId(String frsRqstUserId) {
//        this.frsRqstUserId = frsRqstUserId;
//    }
//
//    public Date getRqstDtm() {
//        return rqstDtm;
//    }
//
//    public void setRqstDtm(Date rqstDtm) {
//        this.rqstDtm = rqstDtm;
//    }
//
//    public String getRqstUserId() {
//        return rqstUserId;
//    }
//
//    public void setRqstUserId(String rqstUserId) {
//        this.rqstUserId = rqstUserId;
//    }
//
//    public Date getAprvDtm() {
//        return aprvDtm;
//    }
//
//    public void setAprvDtm(Date aprvDtm) {
//        this.aprvDtm = aprvDtm;
//    }
//
//    public String getAprvUserId() {
//        return aprvUserId;
//    }
//
//    public void setAprvUserId(String aprvUserId) {
//        this.aprvUserId = aprvUserId;
//    }

    public String getScrtInfo() {
        return scrtInfo;
    }

    public void setScrtInfo(String scrtInfo) {
        this.scrtInfo = scrtInfo;
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
		builder.append("WamDdlIdx [ddlIdxId=").append(ddlIdxId)
				.append(", ddlIdxPnm=").append(ddlIdxPnm)
				.append(", ddlIdxLnm=").append(ddlIdxLnm)
				.append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", dbSchId=").append(dbSchId).append(", ddlTblId=")
				.append(ddlTblId).append(", idxSpacId=").append(idxSpacId)
				.append(", pkIdxYn=").append(pkIdxYn).append(", ukIdxYn=")
				.append(ukIdxYn).append(", idxTypCd=").append(idxTypCd)
				.append(", dbConnTrgPnm=").append(dbConnTrgPnm)
				.append(", dbSchPnm=").append(dbSchPnm).append(", ddlTblPnm=")
				.append(ddlTblPnm).append(", ddlTblLnm=").append(ddlTblLnm)
				.append(", ddlColPnm=").append(ddlColPnm)
				.append(", idxSpacPnm=").append(idxSpacPnm)
				.append(", scrtInfo=").append(scrtInfo).append("]");
		return builder.toString()+super.toString();
	}
}