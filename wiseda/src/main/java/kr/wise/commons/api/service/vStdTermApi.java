package kr.wise.commons.api.service;

import kr.wise.commons.util.UtilString;

public class vStdTermApi {
	
	private String termname;
	
	private String termengname;
	
	private String domainname;
	
	private String ldatatype;
	
	private String pdatatype;
	
	private String comments;
	
	private String sysname;
	
	private String pers_info_cnv_yn;
	
	private String pers_info_grd;
	
	private String oraDataType;
	
	private String msDataType;
	
	private String myDataType;

	public String getOraDataType() {
		return UtilString.null2Blank(oraDataType);
	}

	public void setOraDataType(String oraDataType) {
		this.oraDataType = oraDataType;
	}

	public String getMsDataType() {
		return UtilString.null2Blank(msDataType);
	}

	public void setMsDataType(String msDataType) {
		this.msDataType = msDataType;
	}

	public String getMyDataType() {
		return UtilString.null2Blank(myDataType);
	}

	public void setMyDataType(String myDataType) {
		this.myDataType = myDataType;
	}

	public String getPers_info_cnv_yn() {
		return UtilString.null2Blank(pers_info_cnv_yn);
	}

	public void setPers_info_cnv_yn(String pers_info_cnv_yn) {
		this.pers_info_cnv_yn = pers_info_cnv_yn;
	}

	public String getPers_info_grd() {
		return UtilString.null2Blank(pers_info_grd);
	}

	public void setPers_info_grd(String pers_info_grd) {
		this.pers_info_grd = pers_info_grd;
	}

	public String getTermname() {
		return UtilString.null2Blank(termname);
	}

	public void setTermname(String termname) {
		this.termname = termname;
	}

	public String getTermengname() {
		return UtilString.null2Blank(termengname);
	}

	public void setTermengname(String termengname) {
		this.termengname = termengname;
	}

	public String getDomainname() {
		return UtilString.null2Blank(domainname);
	}

	public void setDomainname(String domainname) {
		this.domainname = domainname;
	}

	public String getLdatatype() {
		return UtilString.null2Blank(ldatatype);
	}

	public void setLdatatype(String ldatatype) {
		this.ldatatype = ldatatype;
	}

	public String getPdatatype() {
		return UtilString.null2Blank(pdatatype);
	}

	public void setPdatatype(String pdatatype) {
		this.pdatatype = pdatatype;
	}

	public String getComments() {
		return UtilString.null2Blank(comments);
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSysname() {
		return UtilString.null2Blank(sysname);
	}

	public void setSysname(String sysname) {
		this.sysname = sysname;
	}
	
}
