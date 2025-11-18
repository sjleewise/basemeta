package kr.wise.commons.damgmt.db.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaDbSynonym extends CommonVo {

	private String synonymId;

	private String srcDbConnTrgId;

	private String srcDbSchId;

	private String srcDbSchPnm;

	private String srcDbSchLnm;

	private String srcObjPnm;

	private String srcObjLnm;

	private String trgDbConnTrgId;

	private String trgDbSchId;

	private String trgDbSchPnm;

	private String trgDbSchLnm;

	private String synonymPnm;

	private String synonymLnm;
	
	private String synonymPrefix;

	private String synonymSuffix;

	private String publicYn;

	public String getSynonymId() {
		return synonymId;
	}

	public void setSynonymId(String synonymId) {
		this.synonymId = synonymId;
	}

	public String getSrcDbConnTrgId() {
		return srcDbConnTrgId;
	}

	public void setSrcDbConnTrgId(String srcDbConnTrgId) {
		this.srcDbConnTrgId = srcDbConnTrgId;
	}

	public String getSrcDbSchId() {
		return srcDbSchId;
	}

	public void setSrcDbSchId(String srcDbSchId) {
		this.srcDbSchId = srcDbSchId;
	}

	public String getSrcDbSchPnm() {
		return srcDbSchPnm;
	}

	public void setSrcDbSchPnm(String srcDbSchPnm) {
		this.srcDbSchPnm = srcDbSchPnm;
	}

	public String getSrcDbSchLnm() {
		return srcDbSchLnm;
	}

	public void setSrcDbSchLnm(String srcDbSchLnm) {
		this.srcDbSchLnm = srcDbSchLnm;
	}

	public String getSrcObjPnm() {
		return srcObjPnm;
	}

	public void setSrcObjPnm(String srcObjPnm) {
		this.srcObjPnm = srcObjPnm;
	}

	public String getSrcObjLnm() {
		return srcObjLnm;
	}

	public void setSrcObjLnm(String srcObjLnm) {
		this.srcObjLnm = srcObjLnm;
	}
	

	public String getTrgDbConnTrgId() {
		return trgDbConnTrgId;
	}

	public void setTrgDbConnTrgId(String trgDbConnTrgId) {
		this.trgDbConnTrgId = trgDbConnTrgId;
	}

	public String getTrgDbSchId() {
		return trgDbSchId;
	}

	public void setTrgDbSchId(String trgDbSchId) {
		this.trgDbSchId = trgDbSchId;
	}

	public String getTrgDbSchPnm() {
		return trgDbSchPnm;
	}

	public void setTrgDbSchPnm(String trgDbSchPnm) {
		this.trgDbSchPnm = trgDbSchPnm;
	}

	public String getTrgDbSchLnm() {
		return trgDbSchLnm;
	}

	public void setTrgDbSchLnm(String trgDbSchLnm) {
		this.trgDbSchLnm = trgDbSchLnm;
	}

	public String getSynonymPnm() {
		return synonymPnm;
	}

	public void setSynonymPnm(String synonymPnm) {
		this.synonymPnm = synonymPnm;
	}

	public String getSynonymLnm() {
		return synonymLnm;
	}

	public void setSynonymLnm(String synonymLnm) {
		this.synonymLnm = synonymLnm;
	}

	public String getPublicYn() {
		return publicYn;
	}

	public void setPublicYn(String publicYn) {
		this.publicYn = publicYn;
	}

    
	
    public String getSynonymPrefix() {
		return synonymPrefix;
	}

	public void setSynonymPrefix(String synonymPrefix) {
		this.synonymPrefix = synonymPrefix;
	}

	public String getSynonymSuffix() {
		return synonymSuffix;
	}

	public void setSynonymSuffix(String synonymSuffix) {
		this.synonymSuffix = synonymSuffix;
	}



	@Override
	public String toString() {
		return "WaaDbSynonym [synonymId=" + synonymId + ", srcDbConnTrgId=" + srcDbConnTrgId + ", srcDbSchId="
				+ srcDbSchId + ", srcDbSchPnm=" + srcDbSchPnm + ", srcDbSchLnm=" + srcDbSchLnm + ", srcObjPnm="
				+ srcObjPnm + ", srcObjLnm=" + srcObjLnm + ", trgDbConnTrgId=" + trgDbConnTrgId + ", trgDbSchId="
				+ trgDbSchId + ", trgDbSchPnm=" + trgDbSchPnm + ", trgDbSchLnm=" + trgDbSchLnm + ", synonymPnm="
				+ synonymPnm + ", synonymLnm=" + synonymLnm + ", synonymPrefix=" + synonymPrefix + ", synonymSuffix="
				+ synonymSuffix + ", publicYn=" + publicYn + "]";
	} 
	  
	 
}