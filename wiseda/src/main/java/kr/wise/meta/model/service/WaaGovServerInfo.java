package kr.wise.meta.model.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WaaGovServerInfo extends CommonVo{
	
	private String orgNm;
	
	private String svrNm;
	
	private String lnkdMthd;
	
	private String svrIp;
	
	private String port;
	
	private String connId;
	
	private String connPwd;
	
	private String ftpPath;
	
	private Date expDtm;
	
	private Date strDtm;
	
	private String lnkSts;
	
	private String lnkStsCnts;
	
	private String writUserId;


	public String getWritUserId() {
		return writUserId;
	}

	public void setWritUserId(String writUserId) {
		this.writUserId = writUserId;
	}

	public String getLnkSts() {
		return lnkSts;
	}

	public void setLnkSts(String lnkSts) {
		this.lnkSts = lnkSts;
	}

	public String getLnkStsCnts() {
		return lnkStsCnts;
	}

	public void setLnkStsCnts(String lnkStsCnts) {
		this.lnkStsCnts = lnkStsCnts;
	}

	public String getOrgNm() {
		return orgNm;
	}

	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}

	public String getSvrNm() {
		return svrNm;
	}

	public void setSvrNm(String svrNm) {
		this.svrNm = svrNm;
	}

	public String getLnkdMthd() {
		return lnkdMthd;
	}

	public void setLnkdMthd(String lnkdMthd) {
		this.lnkdMthd = lnkdMthd;
	}

	public String getSvrIp() {
		return svrIp;
	}

	public void setSvrIp(String svrIp) {
		this.svrIp = svrIp;
	}

	public String getPort() {
		return port;
	}

	public void setPortPort(String port) {
		this.port = port;
	}

	public String getConnId() {
		return connId;
	}

	public void setConnId(String connId) {
		this.connId = connId;
	}

	public String getConnPwd() {
		return connPwd;
	}

	public void setConnPwd(String connPwd) {
		this.connPwd = connPwd;
	}

	public String getFtpPath() {
		return ftpPath;
	}

	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}

	public Date getExpDtm() {
		return expDtm;
	}

	public void setExpDtm(Date expDtm) {
		this.expDtm = expDtm;
	}

	public Date getStrDtm() {
		return strDtm;
	}

	public void setStrDtm(Date strDtm) {
		this.strDtm = strDtm;
	}

	@Override
	public String toString() {
		return "WaaGovServerInfo [orgNm=" + orgNm + ", svrNm=" + svrNm
				+ ", lnkdMthd=" + lnkdMthd + ", svrIp=" + svrIp + ", port="
				+ port + ", connId=" + connId + ", connPwd=" + connPwd
				+ ", ftpPath=" + ftpPath + ", expDtm=" + expDtm + ", strDtm="
				+ strDtm + ", lnkSts=" + lnkSts + ", lnkStsCnts=" + lnkStsCnts
				+ ", writUserId=" + writUserId + "]";
	}
	
}
