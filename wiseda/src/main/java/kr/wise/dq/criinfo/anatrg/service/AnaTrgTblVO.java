package kr.wise.dq.criinfo.anatrg.service;

import java.math.BigDecimal;
import java.util.Date;

public class AnaTrgTblVO  extends  AnaTrgColVO {
	private String sflag;

    private String dbSchId;

    private String dbcTblNm;

    private String dbcColNm;

    private String dbcTblKorNm;

    private Integer vers;

    private String regTyp;

    private Date regDtm;

    private Date updDtm;

    private String descn;

    private String dbConnTrgId;

    private String dbConnTrg;

    private String dbcTblSpacNm;

    private String ddlTblId;

    private String pdmTblId;

    private String dbmsType;

    private String subjId;

    private BigDecimal colEacnt;

    private BigDecimal rowEacnt;

    private BigDecimal tblSize;

    private BigDecimal dataSize;

    private BigDecimal idxSize;

    private BigDecimal nuseSize;

    private BigDecimal bfColEacnt;

    private BigDecimal bfRowEacnt;

    private BigDecimal bfTblSize;

    private BigDecimal bfDataSize;

    private BigDecimal bfIdxSize;

    private BigDecimal bfNuseSize;

    private Date anaDtm;

    private Date crtDtm;

    private Date chgDtm;

    private String pdmDescn;

    private String tblDqExpYn;

    private String ddlTblErrExs;

    private String ddlTblErrCd;

    private String ddlTblErrDescn;

    private String ddlColErrExs;

    private String ddlColErrCd;

    private String ddlColErrDescn;

    private String pdmTblErrExs;

    private String pdmTblErrCd;

    private String pdmTblErrDescn;

    private String pdmColErrExs;

    private String pdmColErrCd;

    private String pdmColErrDescn;

    private String ddlTblExtncExs;

    private String pdmTblExtncExs;

    private String dbConnTrgLnm;

    private String dbConnTrgPnm;

    private String dbSchPnm;

    private String dbSchLnm;

    private String subjLnm;

    private String subjPnm;

    private String uppSubjLnm;

    private String sysAreaLnm;

    private String sysAreaPnm;

    //조회
    private String schDbConnTrgId;

    private String schDbConnTrgLdm;

    private String schDbSchId;

    private String schDbSchNm;

    private String schDbcTblNm;

    private String schDbcTblKorNm;

    private String schDbcColNm;

    private String schRegYn;

    private String tblColGb;

    //파라미터
    private String prfId;
    private String anaDgr;

    private String metaAsscAnly;
    
    private String dmnMinVal;
    private String dmnMaxVal;
    
    private String dmnLnm;
    private String infotpLnm;

    public String getSflag() {
		return sflag;
	}

	public void setSflag(String sflag) {
		this.sflag = sflag;
	}

	@Override
	public String getDbSchId() {
        return dbSchId;
    }

    @Override
	public void setDbSchId(String dbSchId) {
        this.dbSchId = dbSchId;
    }

    @Override
	public String getDbcTblNm() {
        return dbcTblNm;
    }

    @Override
	public void setDbcTblNm(String dbcTblNm) {
        this.dbcTblNm = dbcTblNm;
    }

    public String getDbcTblKorNm() {
        return dbcTblKorNm;
    }

    public void setDbcTblKorNm(String dbcTblKorNm) {
        this.dbcTblKorNm = dbcTblKorNm;
    }

    @Override
	public Integer getVers() {
        return vers;
    }

    @Override
	public void setVers(Integer vers) {
        this.vers = vers;
    }

    @Override
	public String getRegTyp() {
        return regTyp;
    }

    @Override
	public void setRegTyp(String regTyp) {
        this.regTyp = regTyp;
    }

    @Override
	public Date getRegDtm() {
        return regDtm;
    }

    @Override
	public void setRegDtm(Date regDtm) {
        this.regDtm = regDtm;
    }

    @Override
	public Date getUpdDtm() {
        return updDtm;
    }

    @Override
	public void setUpdDtm(Date updDtm) {
        this.updDtm = updDtm;
    }

    @Override
	public String getDescn() {
        return descn;
    }

    @Override
	public void setDescn(String descn) {
        this.descn = descn;
    }

    public String getDbConnTrgId() {
        return dbConnTrgId;
    }

    public void setDbConnTrgId(String dbConnTrgId) {
        this.dbConnTrgId = dbConnTrgId;
    }

    public String getDbcTblSpacNm() {
        return dbcTblSpacNm;
    }

    public void setDbcTblSpacNm(String dbcTblSpacNm) {
        this.dbcTblSpacNm = dbcTblSpacNm;
    }

    public String getDdlTblId() {
        return ddlTblId;
    }

    public void setDdlTblId(String ddlTblId) {
        this.ddlTblId = ddlTblId;
    }

    public String getPdmTblId() {
        return pdmTblId;
    }

    public void setPdmTblId(String pdmTblId) {
        this.pdmTblId = pdmTblId;
    }

    public String getDbmsType() {
        return dbmsType;
    }

    public void setDbmsType(String dbmsType) {
        this.dbmsType = dbmsType;
    }

    public String getSubjId() {
        return subjId;
    }

    public void setSubjId(String subjId) {
        this.subjId = subjId;
    }

    public BigDecimal getColEacnt() {
        return colEacnt;
    }

    public void setColEacnt(BigDecimal colEacnt) {
        this.colEacnt = colEacnt;
    }

    public BigDecimal getRowEacnt() {
        return rowEacnt;
    }

    public void setRowEacnt(BigDecimal rowEacnt) {
        this.rowEacnt = rowEacnt;
    }

    public BigDecimal getTblSize() {
        return tblSize;
    }

    public void setTblSize(BigDecimal tblSize) {
        this.tblSize = tblSize;
    }

    public BigDecimal getDataSize() {
        return dataSize;
    }

    public void setDataSize(BigDecimal dataSize) {
        this.dataSize = dataSize;
    }

    public BigDecimal getIdxSize() {
        return idxSize;
    }

    public void setIdxSize(BigDecimal idxSize) {
        this.idxSize = idxSize;
    }

    public BigDecimal getNuseSize() {
        return nuseSize;
    }

    public void setNuseSize(BigDecimal nuseSize) {
        this.nuseSize = nuseSize;
    }

    public BigDecimal getBfColEacnt() {
        return bfColEacnt;
    }

    public void setBfColEacnt(BigDecimal bfColEacnt) {
        this.bfColEacnt = bfColEacnt;
    }

    public BigDecimal getBfRowEacnt() {
        return bfRowEacnt;
    }

    public void setBfRowEacnt(BigDecimal bfRowEacnt) {
        this.bfRowEacnt = bfRowEacnt;
    }

    public BigDecimal getBfTblSize() {
        return bfTblSize;
    }

    public void setBfTblSize(BigDecimal bfTblSize) {
        this.bfTblSize = bfTblSize;
    }

    public BigDecimal getBfDataSize() {
        return bfDataSize;
    }

    public void setBfDataSize(BigDecimal bfDataSize) {
        this.bfDataSize = bfDataSize;
    }

    public BigDecimal getBfIdxSize() {
        return bfIdxSize;
    }

    public void setBfIdxSize(BigDecimal bfIdxSize) {
        this.bfIdxSize = bfIdxSize;
    }

    public BigDecimal getBfNuseSize() {
        return bfNuseSize;
    }

    public void setBfNuseSize(BigDecimal bfNuseSize) {
        this.bfNuseSize = bfNuseSize;
    }

    public Date getAnaDtm() {
        return anaDtm;
    }

    public void setAnaDtm(Date anaDtm) {
        this.anaDtm = anaDtm;
    }

    public Date getCrtDtm() {
        return crtDtm;
    }

    public void setCrtDtm(Date crtDtm) {
        this.crtDtm = crtDtm;
    }

    public Date getChgDtm() {
        return chgDtm;
    }

    public void setChgDtm(Date chgDtm) {
        this.chgDtm = chgDtm;
    }

    public String getPdmDescn() {
        return pdmDescn;
    }

    public void setPdmDescn(String pdmDescn) {
        this.pdmDescn = pdmDescn;
    }

    public String getTblDqExpYn() {
        return tblDqExpYn;
    }

    public void setTblDqExpYn(String tblDqExpYn) {
        this.tblDqExpYn = tblDqExpYn;
    }

    public String getDdlTblErrExs() {
        return ddlTblErrExs;
    }

    public void setDdlTblErrExs(String ddlTblErrExs) {
        this.ddlTblErrExs = ddlTblErrExs;
    }

    public String getDdlTblErrCd() {
        return ddlTblErrCd;
    }

    public void setDdlTblErrCd(String ddlTblErrCd) {
        this.ddlTblErrCd = ddlTblErrCd;
    }

    public String getDdlTblErrDescn() {
        return ddlTblErrDescn;
    }

    public void setDdlTblErrDescn(String ddlTblErrDescn) {
        this.ddlTblErrDescn = ddlTblErrDescn;
    }

    @Override
	public String getDdlColErrExs() {
        return ddlColErrExs;
    }

    @Override
	public void setDdlColErrExs(String ddlColErrExs) {
        this.ddlColErrExs = ddlColErrExs;
    }

    public String getDdlColErrCd() {
        return ddlColErrCd;
    }

    public void setDdlColErrCd(String ddlColErrCd) {
        this.ddlColErrCd = ddlColErrCd;
    }

    public String getDdlColErrDescn() {
        return ddlColErrDescn;
    }

    public void setDdlColErrDescn(String ddlColErrDescn) {
        this.ddlColErrDescn = ddlColErrDescn;
    }

    public String getPdmTblErrExs() {
        return pdmTblErrExs;
    }

    public void setPdmTblErrExs(String pdmTblErrExs) {
        this.pdmTblErrExs = pdmTblErrExs;
    }

    public String getPdmTblErrCd() {
        return pdmTblErrCd;
    }

    public void setPdmTblErrCd(String pdmTblErrCd) {
        this.pdmTblErrCd = pdmTblErrCd;
    }

    public String getPdmTblErrDescn() {
        return pdmTblErrDescn;
    }

    public void setPdmTblErrDescn(String pdmTblErrDescn) {
        this.pdmTblErrDescn = pdmTblErrDescn;
    }

    @Override
	public String getPdmColErrExs() {
        return pdmColErrExs;
    }

    @Override
	public void setPdmColErrExs(String pdmColErrExs) {
        this.pdmColErrExs = pdmColErrExs;
    }

    public String getPdmColErrCd() {
        return pdmColErrCd;
    }

    public void setPdmColErrCd(String pdmColErrCd) {
        this.pdmColErrCd = pdmColErrCd;
    }

    public String getPdmColErrDescn() {
        return pdmColErrDescn;
    }

    public void setPdmColErrDescn(String pdmColErrDescn) {
        this.pdmColErrDescn = pdmColErrDescn;
    }

    public String getDdlTblExtncExs() {
        return ddlTblExtncExs;
    }

    public void setDdlTblExtncExs(String ddlTblExtncExs) {
        this.ddlTblExtncExs = ddlTblExtncExs;
    }

    public String getPdmTblExtncExs() {
        return pdmTblExtncExs;
    }

    public void setPdmTblExtncExs(String pdmTblExtncExs) {
        this.pdmTblExtncExs = pdmTblExtncExs;
    }

	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}

	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
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

	public String getSubjLnm() {
		return subjLnm;
	}

	public void setSubjLnm(String subjLnm) {
		this.subjLnm = subjLnm;
	}

	public String getSubjPnm() {
		return subjPnm;
	}

	public void setSubjPnm(String subjPnm) {
		this.subjPnm = subjPnm;
	}

	public String getUppSubjLnm() {
		return uppSubjLnm;
	}

	public void setUppSubjLnm(String uppSubjLnm) {
		this.uppSubjLnm = uppSubjLnm;
	}

	public String getSysAreaLnm() {
		return sysAreaLnm;
	}

	public void setSysAreaLnm(String sysAreaLnm) {
		this.sysAreaLnm = sysAreaLnm;
	}

	public String getSysAreaPnm() {
		return sysAreaPnm;
	}

	public void setSysAreaPnm(String sysAreaPnm) {
		this.sysAreaPnm = sysAreaPnm;
	}

	public String getSchDbConnTrgId() {
		return schDbConnTrgId;
	}

	public void setSchDbConnTrgId(String schDbConnTrgId) {
		this.schDbConnTrgId = schDbConnTrgId;
	}

	public String getSchDbSchId() {
		return schDbSchId;
	}

	public void setSchDbSchId(String schDbSchId) {
		this.schDbSchId = schDbSchId;
	}

	public String getSchDbcTblNm() {
		return schDbcTblNm;
	}

	public void setSchDbcTblNm(String schDbcTblNm) {
		this.schDbcTblNm = schDbcTblNm;
	}

	public String getSchDbcColNm() {
		return schDbcColNm;
	}

	public void setSchDbcColNm(String schDbcColNm) {
		this.schDbcColNm = schDbcColNm;
	}

	public String getSchRegYn() {
		return schRegYn;
	}

	public void setSchRegYn(String schRegYn) {
		this.schRegYn = schRegYn;
	}

	public String getSchDbSchNm() {
		return schDbSchNm;
	}

	public void setSchDbSchNm(String schDbSchNm) {
		this.schDbSchNm = schDbSchNm;
	}

	public String getDbConnTrg() {
		return dbConnTrg;
	}

	public void setDbConnTrg(String dbConnTrg) {
		this.dbConnTrg = dbConnTrg;
	}

	public String getSchDbConnTrgLdm() {
		return schDbConnTrgLdm;
	}

	public void setSchDbConnTrgLdm(String schDbConnTrgLdm) {
		this.schDbConnTrgLdm = schDbConnTrgLdm;
	}

	public String getSchDbcTblKorNm() {
		return schDbcTblKorNm;
	}

	public void setSchDbcTblKorNm(String schDbcTblKorNm) {
		this.schDbcTblKorNm = schDbcTblKorNm;
	}

	public String getTblColGb() {
		return tblColGb;
	}

	public void setTblColGb(String tblColGb) {
		this.tblColGb = tblColGb;
	}

	public String getPrfId() {
		return prfId;
	}

	public void setPrfId(String prfId) {
		this.prfId = prfId;
	}

	public String getAnaDgr() {
		return anaDgr;
	}

	public void setAnaDgr(String anaDgr) {
		this.anaDgr = anaDgr;
	}


	@Override
	public String getDbcColNm() {
		return dbcColNm;
	}

	@Override
	public void setDbcColNm(String dbcColNm) {
		this.dbcColNm = dbcColNm;
	}


	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}

	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}
		
	public String getMetaAsscAnly() {
		return metaAsscAnly;
	}

	public void setMetaAsscAnly(String metaAsscAnly) {
		this.metaAsscAnly = metaAsscAnly;
	}

	

	public String getDmnMinVal() {
		return dmnMinVal;
	}

	public void setDmnMinVal(String dmnMinVal) {
		this.dmnMinVal = dmnMinVal;
	}

	public String getDmnMaxVal() {
		return dmnMaxVal;
	}

	public void setDmnMaxVal(String dmnMaxVal) {
		this.dmnMaxVal = dmnMaxVal;
	}
	
	public String getDmnLnm() {
		return dmnLnm;
	}

	public void setDmnLnm(String dmnLnm) {
		this.dmnLnm = dmnLnm;
	}
	
	public String getInfotpLnm() {
		return infotpLnm;
	}

	public void setInfotpLnm(String infotpLnm) {
		this.infotpLnm = infotpLnm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AnaTrgTblVO [sflag=").append(sflag)
				.append(", dbSchId=").append(dbSchId).append(", dbcTblNm=")
				.append(dbcTblNm).append(", dbcColNm=").append(dbcColNm)
				.append(", dbcTblKorNm=").append(dbcTblKorNm).append(", vers=")
				.append(vers).append(", regTyp=").append(regTyp)
				.append(", regDtm=").append(regDtm).append(", updDtm=")
				.append(updDtm).append(", descn=").append(descn)
				.append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", dbConnTrg=").append(dbConnTrg)
				.append(", dbcTblSpacNm=").append(dbcTblSpacNm)
				.append(", ddlTblId=").append(ddlTblId).append(", pdmTblId=")
				.append(pdmTblId).append(", dbmsType=").append(dbmsType)
				.append(", subjId=").append(subjId).append(", colEacnt=")
				.append(colEacnt).append(", rowEacnt=").append(rowEacnt)
				.append(", tblSize=").append(tblSize).append(", dataSize=")
				.append(dataSize).append(", idxSize=").append(idxSize)
				.append(", nuseSize=").append(nuseSize).append(", bfColEacnt=")
				.append(bfColEacnt).append(", bfRowEacnt=").append(bfRowEacnt)
				.append(", bfTblSize=").append(bfTblSize)
				.append(", bfDataSize=").append(bfDataSize)
				.append(", bfIdxSize=").append(bfIdxSize)
				.append(", bfNuseSize=").append(bfNuseSize).append(", anaDtm=")
				.append(anaDtm).append(", crtDtm=").append(crtDtm)
				.append(", chgDtm=").append(chgDtm).append(", pdmDescn=")
				.append(pdmDescn).append(", tblDqExpYn=").append(tblDqExpYn)
				.append(", ddlTblErrExs=").append(ddlTblErrExs)
				.append(", ddlTblErrCd=").append(ddlTblErrCd)
				.append(", ddlTblErrDescn=").append(ddlTblErrDescn)
				.append(", ddlColErrExs=").append(ddlColErrExs)
				.append(", ddlColErrCd=").append(ddlColErrCd)
				.append(", ddlColErrDescn=").append(ddlColErrDescn)
				.append(", pdmTblErrExs=").append(pdmTblErrExs)
				.append(", pdmTblErrCd=").append(pdmTblErrCd)
				.append(", pdmTblErrDescn=").append(pdmTblErrDescn)
				.append(", pdmColErrExs=").append(pdmColErrExs)
				.append(", pdmColErrCd=").append(pdmColErrCd)
				.append(", pdmColErrDescn=").append(pdmColErrDescn)
				.append(", ddlTblExtncExs=").append(ddlTblExtncExs)
				.append(", pdmTblExtncExs=").append(pdmTblExtncExs)
				.append(", dbConnTrgLnm=").append(dbConnTrgLnm)
				.append(", dbConnTrgPnm=").append(dbConnTrgPnm)
				.append(", dbSchPnm=").append(dbSchPnm).append(", dbSchLnm=")
				.append(dbSchLnm).append(", subjLnm=").append(subjLnm)
				.append(", subjPnm=").append(subjPnm).append(", uppSubjLnm=")
				.append(uppSubjLnm).append(", sysAreaLnm=").append(sysAreaLnm)
				.append(", sysAreaPnm=").append(sysAreaPnm)
				.append(", schDbConnTrgId=").append(schDbConnTrgId)
				.append(", schDbConnTrgLdm=").append(schDbConnTrgLdm)
				.append(", schDbSchId=").append(schDbSchId)
				.append(", schDbSchNm=").append(schDbSchNm)
				.append(", schDbcTblNm=").append(schDbcTblNm)
				.append(", schDbcTblKorNm=").append(schDbcTblKorNm)
				.append(", schDbcColNm=").append(schDbcColNm)
				.append(", schRegYn=").append(schRegYn).append(", tblColGb=")
				.append(tblColGb).append(", prfId=").append(prfId)
				.append(", anaDgr=").append(anaDgr).append("]");
		return builder.toString();
	}



}