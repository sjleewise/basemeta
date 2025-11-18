package kr.wise.meta.admin.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaOrg extends CommonVo {
	private String orgCd;
	private String orgNm;
	private String uppOrgCd; 
	private String objDescn;  
	private String regTypCd;
	
	private String uppOrgNm;
	
	private String fullCd;
	
	private String fullPath;
	
	private Short orgLvl;
	
	
	
	
	
	public Short getOrgLvl() {
		return orgLvl;
	}
	public void setOrgLvl(Short orgLvl) {
		this.orgLvl = orgLvl;
	}
	public String getUppOrgNm() {
		return uppOrgNm;
	}
	public void setUppOrgNm(String uppOrgNm) {
		this.uppOrgNm = uppOrgNm;
	}
	public String getFullCd() {
		return fullCd;
	}
	public void setFullCd(String fullCd) {
		this.fullCd = fullCd;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public String getOrgCd() {
		return orgCd;
	}
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}
	public String getOrgNm() {
		return orgNm;
	}
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
	public String getUppOrgCd() {
		return uppOrgCd;
	}
	public void setUppOrgCd(String uppOrgCd) {
		this.uppOrgCd = uppOrgCd;
	}
	public String getObjDescn() {
		return objDescn;
	}
	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}
	public String getRegTypCd() {
		return regTypCd;
	}
	public void setRegTypCd(String regTypCd) {
		this.regTypCd = regTypCd;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaOrg [orgCd=").append(orgCd)
				.append(", orgNm=").append(orgNm)
				.append(", uppOrgCd=").append(uppOrgCd)
				.append(", orgLvl=").append(orgLvl)
				.append(", objDescn=").append(objDescn)
				.append(", regTypCd=").append(regTypCd)
				.append("]");
		return builder.toString()+super.toString();
	}

}