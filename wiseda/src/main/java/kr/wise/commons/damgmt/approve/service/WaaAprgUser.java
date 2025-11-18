package kr.wise.commons.damgmt.approve.service;

import kr.wise.commons.user.service.WaaUser;

public class WaaAprgUser extends WaaUser{
    private String aprgId;

    private String userId;

//    private Date expDtm;
//
//    private Date strDtm;
//
//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date writDtm;
//
//    private String writUserId;

    public String getAprgId() {
        return aprgId;
    }

    public void setAprgId(String aprgId) {
        this.aprgId = aprgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

	/** insomnia */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaAprgUser [aprgId=").append(aprgId)
				.append(", userId=").append(userId).append("]");
		return super.toString() + builder.toString();
	}

/*    public Date getExpDtm() {
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

    public String getObjDescn() {
        return objDescn;
    }

    public void setObjDescn(String objDescn) {
        this.objDescn = objDescn;
    }

    public Integer getObjVers() {
        return objVers;
    }

    public void setObjVers(Integer objVers) {
        this.objVers = objVers;
    }

    public String getRegTypCd() {
        return regTypCd;
    }

    public void setRegTypCd(String regTypCd) {
        this.regTypCd = regTypCd;
    }

    public Date getWritDtm() {
        return writDtm;
    }

    public void setWritDtm(Date writDtm) {
        this.writDtm = writDtm;
    }

    public String getWritUserId() {
        return writUserId;
    }

    public void setWritUserId(String writUserId) {
        this.writUserId = writUserId;
    }*/
}