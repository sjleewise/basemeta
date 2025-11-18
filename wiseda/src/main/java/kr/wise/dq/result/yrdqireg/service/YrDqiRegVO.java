package kr.wise.dq.result.yrdqireg.service;

import java.util.Date;
import java.util.List;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.report.service.DataPatternVO;
import kr.wise.dq.result.service.ResultVO;
import kr.wise.dq.vrfcrule.sqlgenerator.VrfcSqlGeneratorVO;

public class YrDqiRegVO extends ResultVO{
	
	private String yrDqiId;
	private String evalYr;
	private String infoSysCd;
	private String dataStndDfs;
	private String dataStrucStab;
	private String dataLinkMng;
	private String dqi;
	private String dataResAct;
	private String dataErrRate;
	private String regTyp;
	private Date strDtm;
	private Date expDtm;

	private String ip;

	public String getYrDqiId() {
		return yrDqiId;
	}

	public void setYrDqiId(String yrDqiId) {
		this.yrDqiId = yrDqiId;
	}

	public String getEvalYr() {
		return evalYr;
	}

	public void setEvalYr(String evalYr) {
		this.evalYr = evalYr;
	}

	public String getInfoSysCd() {
		return infoSysCd;
	}

	public void setInfoSysCd(String infoSysCd) {
		this.infoSysCd = infoSysCd;
	}

	public String getDataStndDfs() {
		return dataStndDfs;
	}

	public void setDataStndDfs(String dataStndDfs) {
		this.dataStndDfs = dataStndDfs;
	}

	public String getDataStrucStab() {
		return dataStrucStab;
	}

	public void setDataStrucStab(String dataStrucStab) {
		this.dataStrucStab = dataStrucStab;
	}

	public String getDataLinkMng() {
		return dataLinkMng;
	}

	public void setDataLinkMng(String dataLinkMng) {
		this.dataLinkMng = dataLinkMng;
	}

	public String getDqi() {
		return dqi;
	}

	public void setDqi(String dqi) {
		this.dqi = dqi;
	}

	public String getDataResAct() {
		return dataResAct;
	}

	public void setDataResAct(String dataResAct) {
		this.dataResAct = dataResAct;
	}

	public String getDataErrRate() {
		return dataErrRate;
	}

	public void setDataErrRate(String dataErrRate) {
		this.dataErrRate = dataErrRate;
	}

	public String getRegTyp() {
		return regTyp;
	}

	public void setRegTyp(String regTyp) {
		this.regTyp = regTyp;
	}

	public Date getStrDtm() {
		return strDtm;
	}

	public void setStrDtm(Date strDtm) {
		this.strDtm = strDtm;
	}

	public Date getExpDtm() {
		return expDtm;
	}

	public void setExpDtm(Date expDtm) {
		this.expDtm = expDtm;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
