package kr.wise.commons.damgmt.db.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaTblSpac extends CommonVo{
    private String dbTblSpacId;

//    private Date expDtm;
//
//    private Date strDtm;

    private String dbTblSpacPnm;

    private String dbTblSpacLnm;

    private String dbConnTrgId;

    private String tblSpacTypCd;

    private String ddlDisplay;

    private String defaultUsage;

    private String dbTblSpacDatafile;

    private String dbTblSpacGroup;

    private String dbTblSpacInitSize;

    private String dbTblSpacNextSize;

    private String dbTblSpacMaxSize;

    private String dbTblSpacWrn;

    private String dbTblSpacMgn;

    private String dbTblSpacScript;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date writDtm;
//
//    private String writUserId;
    
    private String dbSchId;
    

    public String getDbSchId() {
		return dbSchId;
	}

	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}

	public String getDbTblSpacId() {
        return dbTblSpacId;
    }

    public void setDbTblSpacId(String dbTblSpacId) {
        this.dbTblSpacId = dbTblSpacId;
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

    public String getDbTblSpacPnm() {
        return dbTblSpacPnm;
    }

    public void setDbTblSpacPnm(String dbTblSpacPnm) {
        this.dbTblSpacPnm = dbTblSpacPnm;
    }

    public String getDbTblSpacLnm() {
        return dbTblSpacLnm;
    }

    public void setDbTblSpacLnm(String dbTblSpacLnm) {
        this.dbTblSpacLnm = dbTblSpacLnm;
    }

    public String getDbConnTrgId() {
        return dbConnTrgId;
    }

    public void setDbConnTrgId(String dbConnTrgId) {
        this.dbConnTrgId = dbConnTrgId;
    }

    public String getTblSpacTypCd() {
        return tblSpacTypCd;
    }

    public void setTblSpacTypCd(String tblSpacTypCd) {
        this.tblSpacTypCd = tblSpacTypCd;
    }

    public String getDdlDisplay() {
        return ddlDisplay;
    }

    public void setDdlDisplay(String ddlDisplay) {
        this.ddlDisplay = ddlDisplay;
    }

    public String getDefaultUsage() {
        return defaultUsage;
    }

    public void setDefaultUsage(String defaultUsage) {
        this.defaultUsage = defaultUsage;
    }

    public String getDbTblSpacDatafile() {
        return dbTblSpacDatafile;
    }

    public void setDbTblSpacDatafile(String dbTblSpacDatafile) {
        this.dbTblSpacDatafile = dbTblSpacDatafile;
    }

    public String getDbTblSpacGroup() {
        return dbTblSpacGroup;
    }

    public void setDbTblSpacGroup(String dbTblSpacGroup) {
        this.dbTblSpacGroup = dbTblSpacGroup;
    }

    public String getDbTblSpacInitSize() {
        return dbTblSpacInitSize;
    }

    public void setDbTblSpacInitSize(String dbTblSpacInitSize) {
        this.dbTblSpacInitSize = dbTblSpacInitSize;
    }

    public String getDbTblSpacNextSize() {
        return dbTblSpacNextSize;
    }

    public void setDbTblSpacNextSize(String dbTblSpacNextSize) {
        this.dbTblSpacNextSize = dbTblSpacNextSize;
    }

    public String getDbTblSpacMaxSize() {
        return dbTblSpacMaxSize;
    }

    public void setDbTblSpacMaxSize(String dbTblSpacMaxSize) {
        this.dbTblSpacMaxSize = dbTblSpacMaxSize;
    }

    public String getDbTblSpacWrn() {
        return dbTblSpacWrn;
    }

    public void setDbTblSpacWrn(String dbTblSpacWrn) {
        this.dbTblSpacWrn = dbTblSpacWrn;
    }

    public String getDbTblSpacMgn() {
        return dbTblSpacMgn;
    }

    public void setDbTblSpacMgn(String dbTblSpacMgn) {
        this.dbTblSpacMgn = dbTblSpacMgn;
    }

    public String getDbTblSpacScript() {
        return dbTblSpacScript;
    }

    public void setDbTblSpacScript(String dbTblSpacScript) {
        this.dbTblSpacScript = dbTblSpacScript;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaTblSpac [dbTblSpacId=").append(dbTblSpacId)
				.append(", dbTblSpacPnm=").append(dbTblSpacPnm)
				.append(", dbTblSpacLnm=").append(dbTblSpacLnm)
				.append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", tblSpacTypCd=").append(tblSpacTypCd)
				.append(", ddlDisplay=").append(ddlDisplay)
				.append(", defaultUsage=").append(defaultUsage)
				.append(", dbTblSpacDatafile=").append(dbTblSpacDatafile)
				.append(", dbTblSpacGroup=").append(dbTblSpacGroup)
				.append(", dbTblSpacInitSize=").append(dbTblSpacInitSize)
				.append(", dbTblSpacNextSize=").append(dbTblSpacNextSize)
				.append(", dbTblSpacMaxSize=").append(dbTblSpacMaxSize)
				.append(", dbTblSpacWrn=").append(dbTblSpacWrn)
				.append(", dbTblSpacMgn=").append(dbTblSpacMgn)
				.append(", dbTblSpacScript=").append(dbTblSpacScript)
				.append(", Level=").append(Level).append(", FontColor=")
				.append(FontColor).append(", getDbTblSpacId()=")
				.append(getDbTblSpacId()).append(", getDbTblSpacPnm()=")
				.append(getDbTblSpacPnm()).append(", getDbTblSpacLnm()=")
				.append(getDbTblSpacLnm()).append(", getDbConnTrgId()=")
				.append(getDbConnTrgId()).append(", getTblSpacTypCd()=")
				.append(getTblSpacTypCd()).append(", getDdlDisplay()=")
				.append(getDdlDisplay()).append(", getDefaultUsage()=")
				.append(getDefaultUsage()).append(", getDbTblSpacDatafile()=")
				.append(getDbTblSpacDatafile())
				.append(", getDbTblSpacGroup()=").append(getDbTblSpacGroup())
				.append(", getDbTblSpacInitSize()=")
				.append(getDbTblSpacInitSize())
				.append(", getDbTblSpacNextSize()=")
				.append(getDbTblSpacNextSize())
				.append(", getDbTblSpacMaxSize()=")
				.append(getDbTblSpacMaxSize()).append(", getDbTblSpacWrn()=")
				.append(getDbTblSpacWrn()).append(", getDbTblSpacMgn()=")
				.append(getDbTblSpacMgn()).append(", getDbTblSpacScript()=")
				.append(getDbTblSpacScript()).append(", getExpDtm()=")
				.append(getExpDtm()).append(", getStrDtm()=")
				.append(getStrDtm()).append(", getObjDescn()=")
				.append(getObjDescn()).append(", getObjVers()=")
				.append(getObjVers()).append(", getRegTypCd()=")
				.append(getRegTypCd()).append(", getFrsRqstDtm()=")
				.append(getFrsRqstDtm()).append(", getFrsRqstUserId()=")
				.append(getFrsRqstUserId()).append(", getRqstDtm()=")
				.append(getRqstDtm()).append(", getRqstUserId()=")
				.append(getRqstUserId()).append(", getAprvDtm()=")
				.append(getAprvDtm()).append(", getAprvUserId()=")
				.append(getAprvUserId()).append(", getWritDtm()=")
				.append(getWritDtm()).append(", getWritUserId()=")
				.append(getWritUserId()).append(", getIbsSeq()=")
				.append(getIbsSeq()).append(", getWritUserNm()=")
				.append(getWritUserNm()).append(", getIbsStatus()=")
				.append(getIbsStatus()).append(", getIbsCheck()=")
				.append(getIbsCheck()).append(", getRqstNo()=")
				.append(getRqstNo()).append(", getRqstSno()=")
				.append(getRqstSno()).append(", getRqstDcd()=")
				.append(getRqstDcd()).append(", getRvwStsCd()=")
				.append(getRvwStsCd()).append(", getRvwConts()=")
				.append(getRvwConts()).append(", getVrfCd()=")
				.append(getVrfCd()).append(", getVrfRmk()=")
				.append(getVrfRmk()).append(", getLevel()=").append(getLevel())
				.append(", getFrsRqstUserNm()=").append(getFrsRqstUserNm())
				.append(", getRqstUserNm()=").append(getRqstUserNm())
				.append(", getAprvUserNm()=").append(getAprvUserNm())
				.append(", getFontColor()=").append(getFontColor())
				.append(", toString()=").append(super.toString())
				.append(", getFullPath()=").append(getFullPath())
				.append(", getPopType()=").append(getPopType())
				.append(", getPopRqst()=").append(getPopRqst())
				.append(", getLvlList()=").append(getLvlList())
				.append(", getRqstDtlSno()=").append(getRqstDtlSno())
				.append(", getClass()=").append(getClass())
				.append(", hashCode()=").append(hashCode()).append("]");
		return builder.toString() + super.toString();
	}

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