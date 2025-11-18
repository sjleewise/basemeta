package kr.wise.commons.damgmt.db.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaDbConnTrgVO extends CommonVo {

    private String dbConnTrgId;

//    private Date expDtm;
//
//    private Date strDtm;

    private String dbConnTrgPnm;

    private String dbConnTrgLnm;

    private String dbmsTypCd;

    private String dbmsVersCd;

    private String connTrgDbLnkChrw;

    private String connTrgLnkUrl;

    private String connTrgDrvrNm;

    private String crgpNm;

    private String crgpCntel;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date writDtm;
//
//    private String writUserId;
//    private String writUserNm;

    private String dbConnAcId;

    private String dbConnAcPwd;

    private String dbLnkSts;


    private String dbSchId;
    private String dbSchPnm;
    private String dbSchLnm;
    private String ddlTrgYn;
    private String cltXclYn;
    private String ddlTrgDcd;
    private String cdSndTrgYn;
    private String cdAutoSndYn;

    private String metaMngYn;

	private String comCd;
    
    private String dbLnkStsCtns; //접속테스트결과
    
    private String orgCd;
    
    private String infoSysCd;
    
    private String mappingUserId;
    
    //범정부용 변수 추가
    private String applBz;
    private String osKind;
    private String osVers;
    private String esblhDt;
    private String tblCnt;
    private String dataCapa;
    private String gthExcptRsn;

    //값진단종합결과 시스템영역 가져오기
    private String sysAreaId;
    private String orgNm;
    private String infoSysNm;
    
    private String userId;
	private String userNm;
	private String deptNm;
	private String deptPath;
	private String usergId;
	
	

	public String getOrgNm() {
		return orgNm;
	}

	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}

	public String getInfoSysNm() {
		return infoSysNm;
	}

	public void setInfoSysNm(String infoSysNm) {
		this.infoSysNm = infoSysNm;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public String getDeptPath() {
		return deptPath;
	}

	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}

	public String getUsergId() {
		return usergId;
	}

	public void setUsergId(String usergId) {
		this.usergId = usergId;
	}

	public String getSysAreaId() {
		return sysAreaId;
	}

	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
	}

	public String getDbLnkStsCtns() {
		return dbLnkStsCtns;
	}

	public void setDbLnkStsCtns(String dbLnkStsCtns) {
		this.dbLnkStsCtns = dbLnkStsCtns;
	}

	public String getMetaMngYn() {
		return metaMngYn;
	}

	public void setMetaMngYn(String metaMngYn) {
		this.metaMngYn = metaMngYn;
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

	public String getCdSndTrgYn() {
		return cdSndTrgYn;
	}

	public void setCdSndTrgYn(String cdSndTrgYn) {
		this.cdSndTrgYn = cdSndTrgYn;
	}

	public String getCdAutoSndYn() {
		return cdAutoSndYn;
	}

	public void setCdAutoSndYn(String cdAutoSndYn) {
		this.cdAutoSndYn = cdAutoSndYn;
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

    public String getDbConnTrgLnm() {
        return dbConnTrgLnm;
    }

    public void setDbConnTrgLnm(String dbConnTrgLnm) {
        this.dbConnTrgLnm = dbConnTrgLnm;
    }

    public String getDbmsTypCd() {
        return dbmsTypCd;
    }

    public void setDbmsTypCd(String dbmsTypCd) {
        this.dbmsTypCd = dbmsTypCd;
    }

    public String getDbmsVersCd() {
        return dbmsVersCd;
    }

    public void setDbmsVersCd(String dbmsVersCd) {
        this.dbmsVersCd = dbmsVersCd;
    }

    public String getConnTrgDbLnkChrw() {
        return connTrgDbLnkChrw;
    }

    public void setConnTrgDbLnkChrw(String connTrgDbLnkChrw) {
        this.connTrgDbLnkChrw = connTrgDbLnkChrw;
    }

    public String getConnTrgLnkUrl() {
        return connTrgLnkUrl;
    }

    public void setConnTrgLnkUrl(String connTrgLnkUrl) {
        this.connTrgLnkUrl = connTrgLnkUrl;
    }

    public String getConnTrgDrvrNm() {
        return connTrgDrvrNm;
    }

    public void setConnTrgDrvrNm(String connTrgDrvrNm) {
        this.connTrgDrvrNm = connTrgDrvrNm;
    }

    public String getCrgpNm() {
        return crgpNm;
    }

    public void setCrgpNm(String crgpNm) {
        this.crgpNm = crgpNm;
    }

    public String getCrgpCntel() {
        return crgpCntel;
    }

    public void setCrgpCntel(String crgpCntel) {
        this.crgpCntel = crgpCntel;
    }


	/**
	 * @return the dbConnAcId
	 */
	public String getDbConnAcId() {
		return dbConnAcId;
	}

	/**
	 * @param dbConnAcId the dbConnAcId to set
	 */
	public void setDbConnAcId(String dbConnAcId) {
		this.dbConnAcId = dbConnAcId;
	}

	/**
	 * @return the dbConnAcPwd
	 */
	public String getDbConnAcPwd() {
		return dbConnAcPwd;
	}

	/**
	 * @param dbConnAcPwd the dbConnAcPwd to set
	 */
	public void setDbConnAcPwd(String dbConnAcPwd) {
		this.dbConnAcPwd = dbConnAcPwd;
	}

	/**
	 * @return the dbLnkSts
	 */
	public String getDbLnkSts() {
		return dbLnkSts;
	}

	/**
	 * @param dbLnkSts the dbLnkSts to set
	 */
	public void setDbLnkSts(String dbLnkSts) {
		this.dbLnkSts = dbLnkSts;
	}
	
	public String getOrgCd() {
		return orgCd;
	}

	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}
		
	public String getInfoSysCd() {
		return infoSysCd;
	}

	public void setInfoSysCd(String infoSysCd) {
		this.infoSysCd = infoSysCd;
	}

	public String getMappingUserId() {
		return mappingUserId;
	}

	public void setMappingUserId(String mappingUserId) {
		this.mappingUserId = mappingUserId;
	}

	public String getComCd() {
		return comCd;
	}

	public void setComCd(String comCd) {
		this.comCd = comCd;
	}

	public String getApplBz() {
		return applBz;
	}

	public void setApplBz(String applBz) {
		this.applBz = applBz;
	}

	public String getOsKind() {
		return osKind;
	}

	public void setOsKind(String osKind) {
		this.osKind = osKind;
	}

	public String getOsVers() {
		return osVers;
	}

	public void setOsVers(String osVers) {
		this.osVers = osVers;
	}

	public String getEsblhDt() {
		return esblhDt;
	}

	public void setEsblhDt(String esblhDt) {
		this.esblhDt = esblhDt;
	}

	public String getTblCnt() {
		return tblCnt;
	}

	public void setTblCnt(String tblCnt) {
		this.tblCnt = tblCnt;
	}

	public String getDataCapa() {
		return dataCapa;
	}

	public void setDataCapa(String dataCapa) {
		this.dataCapa = dataCapa;
	}

	public String getGthExcptRsn() {
		return gthExcptRsn;
	}

	public void setGthExcptRsn(String gthExcptRsn) {
		this.gthExcptRsn = gthExcptRsn;
	}

	/** insomnia */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaDbConnTrgVO [dbConnTrgId=").append(dbConnTrgId)
				.append(", dbConnTrgPnm=").append(dbConnTrgPnm)
				.append(", dbConnTrgLnm=").append(dbConnTrgLnm)
				.append(", dbmsTypCd=").append(dbmsTypCd)
				.append(", dbmsVersCd=").append(dbmsVersCd)
				.append(", connTrgDbLnkChrw=").append(connTrgDbLnkChrw)
				.append(", connTrgLnkUrl=").append(connTrgLnkUrl)
				.append(", connTrgDrvrNm=").append(connTrgDrvrNm)
				.append(", crgpNm=").append(crgpNm).append(", crgpCntel=")
				.append(crgpCntel).append(", dbConnAcId=").append(dbConnAcId)
				.append(", dbConnAcPwd=").append(dbConnAcPwd)
				.append(", dbLnkSts=").append(dbLnkSts).append(", dbSchId=")
				.append(dbSchId).append(", dbSchPnm=").append(dbSchPnm)
				.append(", dbSchLnm=").append(dbSchLnm).append(", ddlTrgYn=")
				.append(ddlTrgYn).append(", cltXclYn=").append(cltXclYn)
				.append(", ddlTrgDcd=").append(ddlTrgDcd)
				.append(", cdSndTrgYn=").append(cdSndTrgYn)
				.append(", cdAutoSndYn=").append(cdAutoSndYn).append("]");
		return builder.toString();
	}




}