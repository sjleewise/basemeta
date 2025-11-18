package kr.wise.commons.user.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WaaUserSys extends CommonVo{
	private Integer userSysNo;
	
    private String userId;
    
    private String userNm;

    private String infoSysCd;
    
    private String infoSysNm;
    
    private String emailAddr;
    
    private String userHtelno;
    
    private String usergLnm;
    //2019.5.22
    private String orgCd;
    private String topOrgCd;
    
    
	public Integer getUserSysNo() {
		return userSysNo;
	}

	public void setUserSysNo(Integer userSysNo) {
		this.userSysNo = userSysNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getInfoSysCd() {
		return infoSysCd;
	}

	public void setInfoSysCd(String infoSysCd) {
		this.infoSysCd = infoSysCd;
	}

	public String getInfoSysNm() {
		return infoSysNm;
	}

	public void setInfoSysNm(String infoSysNm) {
		this.infoSysNm = infoSysNm;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getUserHtelno() {
		return userHtelno;
	}

	public void setUserHtelno(String userHtelno) {
		this.userHtelno = userHtelno;
	}

	public String getUsergLnm() {
		return usergLnm;
	}

	public void setUsergLnm(String usergLnm) {
		this.usergLnm = usergLnm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaUser [")
				.append("  userId=").append(userId)
				.append(", userNm=").append(userNm)
				.append(", infoSysCd=").append(infoSysCd)
				.append(", infoSysNm=").append(infoSysNm)
				.append("  ]");
		return builder.toString()+super.toString();
	}

	public String getOrgCd() {
		return orgCd;
	}

	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}

	public String getTopOrgCd() {
		return topOrgCd;
	}

	public void setTopOrgCd(String topOrgCd) {
		this.topOrgCd = topOrgCd;
	}
	

}