package kr.wise.commons.damgmt.subjsch.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaSubjDbSchMap extends CommonVo{
    private String dbSchId;

    private String subjId;

//    private Date expDtm;
//
//    private Date strDtm;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date writDtm;
//
//    private String writUserId;
    
    private String subjLnm;
    
    private String stdAplYn;
    
    private String dbSchLnm;
    
    private String ddlTrgYn;
    
    private String cltXclYn;
    
    private String ddlTrgDcd;
    
    
    
    

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaSubjDbSchMap [dbSchId=").append(dbSchId)
				.append(", subjId=").append(subjId).append(", subjLnm=")
				.append(subjLnm).append(", stdAplYn=").append(stdAplYn)
				.append(", dbSchLnm=").append(dbSchLnm).append(", ddlTrgYn=")
				.append(ddlTrgYn).append(", cltXclYn=").append(cltXclYn)
				.append(", ddlTrgDcd=").append(ddlTrgDcd).append("]");
		return builder.toString() + super.toString();
	}

	public String getSubjLnm() {
		return subjLnm;
	}

	public void setSubjLnm(String subjLnm) {
		this.subjLnm = subjLnm;
	}

	public String getStdAplYn() {
		return stdAplYn;
	}

	public void setStdAplYn(String stdAplYn) {
		this.stdAplYn = stdAplYn;
	}

	public String getDbSchLnm() {
		return dbSchLnm;
	}

	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}

	public String getDdlTrgYn() {
		return ddlTrgYn;
	}

	public void setDdlTrgYn(String ddlTrgYn) {
		this.ddlTrgYn = ddlTrgYn;
	}

	public String getCltXclYn() {
		return cltXclYn;
	}

	public void setCltXclYn(String cltXclYn) {
		this.cltXclYn = cltXclYn;
	}

	public String getDdlTrgDcd() {
		return ddlTrgDcd;
	}

	public void setDdlTrgDcd(String ddlTrgDcd) {
		this.ddlTrgDcd = ddlTrgDcd;
	}

	public String getDbSchId() {
        return dbSchId;
    }

    public void setDbSchId(String dbSchId) {
        this.dbSchId = dbSchId;
    }

    public String getSubjId() {
        return subjId;
    }

    public void setSubjId(String subjId) {
        this.subjId = subjId;
    }

//    public Date getExpDtm() {
//        return expDtm;
//    }
//
//    public void setExpDtm(Date expDtm) {
//        this.expDtm = expDtm;
//    }
//
//    public Date getStrDtm() {
//        return strDtm;
//    }
//
//    public void setStrDtm(Date strDtm) {
//        this.strDtm = strDtm;
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
//    public Date getWritDtm() {
//        return writDtm;
//    }
//
//    public void setWritDtm(Date writDtm) {
//        this.writDtm = writDtm;
//    }
//
//    public String getWritUserId() {
//        return writUserId;
//    }
//
//    public void setWritUserId(String writUserId) {
//        this.writUserId = writUserId;
//    }
}