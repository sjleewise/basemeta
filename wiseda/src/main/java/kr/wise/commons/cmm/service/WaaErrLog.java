package kr.wise.commons.cmm.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WaaErrLog extends CommonVo{

	private int seq   ;
	private String pgmNm ;
	private String errLog;
	private Date   errDtm;
	private String userId;
	private String userNm;
	
	
	
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public String getErrLog() {
		return errLog;
	}
	public void setErrLog(String errLog) {
		this.errLog = errLog;
	}
	public Date getErrDtm() {
		return errDtm;
	}
	public void setErrDtm(Date errDtm) {
		this.errDtm = errDtm;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}