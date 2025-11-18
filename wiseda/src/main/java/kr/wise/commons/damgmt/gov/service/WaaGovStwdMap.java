package kr.wise.commons.damgmt.gov.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaGovStwdMap extends CommonVo {
	private String govStwdId;
    private String enactDgr;
    private String govStwdLnm;
    private String govStwdPnm;
	private String engMean;
    private String objDescn;
    private String dmnYn;
	private String govDmngLnm;
    private String synonyms;
    private String prohibits;
    
    private String stwdId;
    private String stwdLnm;
    private String stwdPnm;
    
    private String regDtm;
    private String regUserId;
    private String regUserNm;
    
    
	public String getGovStwdId() {
		return govStwdId;
	}
	public void setGovStwdId(String govStwdId) {
		this.govStwdId = govStwdId;
	}
	public String getEnactDgr() {
		return enactDgr;
	}
	public void setEnactDgr(String enactDgr) {
		this.enactDgr = enactDgr;
	}
	public String getGovStwdLnm() {
		return govStwdLnm;
	}
	public void setGovStwdLnm(String govStwdLnm) {
		this.govStwdLnm = govStwdLnm;
	}
	public String getGovStwdPnm() {
		return govStwdPnm;
	}
	public void setGovStwdPnm(String govStwdPnm) {
		this.govStwdPnm = govStwdPnm;
	}
	public String getEngMean() {
		return engMean;
	}
	public void setEngMean(String engMean) {
		this.engMean = engMean;
	}
	public String getObjDescn() {
		return objDescn;
	}
	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}
	public String getDmnYn() {
		return dmnYn;
	}
	public void setDmnYn(String dmnYn) {
		this.dmnYn = dmnYn;
	}
	public String getGovDmngLnm() {
		return govDmngLnm;
	}
	public void setGovDmngLnm(String govDmngLnm) {
		this.govDmngLnm = govDmngLnm;
	}
	public String getSynonyms() {
		return synonyms;
	}
	public void setSynonyms(String synonyms) {
		this.synonyms = synonyms;
	}
	public String getProhibits() {
		return prohibits;
	}
	public void setProhibits(String prohibits) {
		this.prohibits = prohibits;
	}
	public String getStwdId() {
		return stwdId;
	}
	public void setStwdId(String stwdId) {
		this.stwdId = stwdId;
	}
	public String getStwdLnm() {
		return stwdLnm;
	}
	public void setStwdLnm(String stwdLnm) {
		this.stwdLnm = stwdLnm;
	}
	public String getStwdPnm() {
		return stwdPnm;
	}
	public void setStwdPnm(String stwdPnm) {
		this.stwdPnm = stwdPnm;
	}
	public String getRegDtm() {
		return regDtm;
	}
	public void setRegDtm(String regDtm) {
		this.regDtm = regDtm;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	public String getRegUserNm() {
		return regUserNm;
	}
	public void setRegUserNm(String regUserNm) {
		this.regUserNm = regUserNm;
	}
	
	@Override
	public String toString() {
		return "WaaGovStwdMap [govStwdId=" + govStwdId + ", enactDgr="
				+ enactDgr + ", govStwdLnm=" + govStwdLnm + ", govStwdPnm="
				+ govStwdPnm + ", engMean=" + engMean + ", objDescn="
				+ objDescn + ", dmnYn=" + dmnYn + ", govDmngLnm=" + govDmngLnm
				+ ", synonyms=" + synonyms + ", prohibits=" + prohibits
				+ ", stwdId=" + stwdId + ", stwdLnm=" + stwdLnm + ", stwdPnm="
				+ stwdPnm + ", regDtm=" + regDtm + ", regUserId=" + regUserId
				+ "]";
	}
	
    
    
}