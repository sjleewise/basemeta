package kr.wise.commons.damgmt.gov.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaGovStwd extends CommonVo {
	


	private String stwdId;

    private String enactDgr;
    
    private String stwdLnm;
    
    private String stwdPnm;
    
    public String getEngNm() {
		return engNm;
	}


	public void setEngNm(String engNm) {
		this.engNm = engNm;
	}


	private String engNm;
    
    private String objDescn;
    private String dmnYn;

    public String getDmnLnm() {
		return dmnLnm;
	}


	public void setDmnLnm(String dmnLnm) {
		this.dmnLnm = dmnLnm;
	}


	private String dmnLnm;
    
    private String synonyms;
     
    private String prohibits;
    
	private String regTypCd;
    
    private String regDtm;
    
    private String regUserId;
    
    private String aplDtm;
    
    public String getStwdId() {
		return stwdId;
	}


	public void setStwdId(String stwdId) {
		this.stwdId = stwdId;
	}


	public String getEnactDgr() {
		return enactDgr;
	}


	public void setEnactDgr(String enactDgr) {
		this.enactDgr = enactDgr;
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


	public String getRegTypCd() {
		return regTypCd;
	}


	public void setRegTypCd(String regTypCd) {
		this.regTypCd = regTypCd;
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


	public String getAplDtm() {
		return aplDtm;
	}


	public void setAplDtm(String aplDtm) {
		this.aplDtm = aplDtm;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaGovStwd [stwdId=").append(stwdId)
				.append(", enactDgr=").append(enactDgr)
				.append(", stwdLnm=").append(stwdLnm)
				.append(", stwdPnm=").append(stwdPnm).append(", objDescn=")
				.append(objDescn).append(", dmnYn=").append(dmnYn)
				.append(", dmnLnm=").append("dmnLnm").append(", synonyms=")
				.append(synonyms).append(", prohibits=").append(prohibits)
				.append(", regTypCd=").append(regTypCd).append(", regDtm=")
				.append(regDtm).append(", regUserId=").append(regUserId)
				.append("]");
		return builder.toString() + super.toString();
	}


    
}