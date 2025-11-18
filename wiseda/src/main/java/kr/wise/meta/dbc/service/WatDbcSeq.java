package kr.wise.meta.dbc.service;

import java.math.BigDecimal;
import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WatDbcSeq extends CommonVo{
    private String dbSchId;

	private String dbcSeqNm;

	private Integer vers;

	private String regTyp;

	private Date regDtm;

	private Date updDtm;

	private BigDecimal minval;

	private BigDecimal maxval;

	private BigDecimal incby;

	private String cycYn;

	private String ordYn;

	private BigDecimal cacheSz;

	private BigDecimal lstNum;

	private String descn;

	private String dbConnTrgId;

	private String ddlSeqId;

	private String dbmsType;

	private String subjId;

	private Date anaDtm;

	private Date crtDtm;

	private Date chgDtm;

	private String ddlSeqDescn;

	private String ddlSeqErrExs;

	private String ddlSeqErrCd;

	private String ddlSeqErrDescn;

	private String ddlSeqExtncExs;


	public String getDbSchId() {
		return dbSchId;
	}

	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}

	public String getDbcSeqNm() {
		return dbcSeqNm;
	}

	public void setDbcSeqNm(String dbcSeqNm) {
		this.dbcSeqNm = dbcSeqNm;
	}

	public Integer getVers() {
		return vers;
	}

	public void setVers(Integer vers) {
		this.vers = vers;
	}

	public String getRegTyp() {
		return regTyp;
	}

	public void setRegTyp(String regTyp) {
		this.regTyp = regTyp;
	}

	public Date getRegDtm() {
		return regDtm;
	}

	public void setRegDtm(Date regDtm) {
		this.regDtm = regDtm;
	}

	public Date getUpdDtm() {
		return updDtm;
	}

	public void setUpdDtm(Date updDtm) {
		this.updDtm = updDtm;
	}

	public BigDecimal getMinval() {
		return minval;
	}

	public void setMinval(BigDecimal minval) {
		this.minval = minval;
	}

	public BigDecimal getMaxval() {
		return maxval;
	}

	public void setMaxval(BigDecimal maxval) {
		this.maxval = maxval;
	}

	public BigDecimal getIncby() {
		return incby;
	}

	public void setIncby(BigDecimal incby) {
		this.incby = incby;
	}

	public String getCycYn() {
		return cycYn;
	}

	public void setCycYn(String cycYn) {
		this.cycYn = cycYn;
	}

	public String getOrdYn() {
		return ordYn;
	}

	public void setOrdYn(String ordYn) {
		this.ordYn = ordYn;
	}

	public BigDecimal getCacheSz() {
		return cacheSz;
	}

	public void setCacheSz(BigDecimal cacheSz) {
		this.cacheSz = cacheSz;
	}

	public BigDecimal getLstNum() {
		return lstNum;
	}

	public void setLstNum(BigDecimal lstNum) {
		this.lstNum = lstNum;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}

	public String getDdlSeqId() {
		return ddlSeqId;
	}

	public void setDdlSeqId(String ddlSeqId) {
		this.ddlSeqId = ddlSeqId;
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

	public String getDdlSeqDescn() {
		return ddlSeqDescn;
	}

	public void setDdlSeqDescn(String ddlSeqDescn) {
		this.ddlSeqDescn = ddlSeqDescn;
	}

	public String getDdlSeqErrExs() {
		return ddlSeqErrExs;
	}

	public void setDdlSeqErrExs(String ddlSeqErrExs) {
		this.ddlSeqErrExs = ddlSeqErrExs;
	}

	public String getDdlSeqErrCd() {
		return ddlSeqErrCd;
	}

	public void setDdlSeqErrCd(String ddlSeqErrCd) {
		this.ddlSeqErrCd = ddlSeqErrCd;
	}

	public String getDdlSeqErrDescn() {
		return ddlSeqErrDescn;
	}

	public void setDdlSeqErrDescn(String ddlSeqErrDescn) {
		this.ddlSeqErrDescn = ddlSeqErrDescn;
	}

	public String getDdlSeqExtncExs() {
		return ddlSeqExtncExs;
	}

	public void setDdlSeqExtncExs(String ddlSeqExtncExs) {
		this.ddlSeqExtncExs = ddlSeqExtncExs;
	}
}