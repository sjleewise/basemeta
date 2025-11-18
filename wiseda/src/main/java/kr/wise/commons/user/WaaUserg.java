package kr.wise.commons.user;

import kr.wise.commons.cmm.CommonVo;


public class WaaUserg extends CommonVo {
    private String usergId;

//    private Date expDtm;
//
//    private Date strDtm;

    private String usergLnm;

    private String usergPnm;

    private String usergTypCd;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date writDtm;
//
//    private String writUserId;

    public String getUsergId() {
        return usergId;
    }

    public void setUsergId(String usergId) {
        this.usergId = usergId;
    }

//    public Date getExpDtm() {
//        return expDtm;
//    }
//
//    public void setExpDtm(Date expDtm) {
//        this.expDtm = expDtm;
//    }
//
//    public Date getStrDtm() {
//        return strDtm;
//    }
//
//    public void setStrDtm(Date strDtm) {
//        this.strDtm = strDtm;
//    }

    public String getUsergLnm() {
        return usergLnm;
    }

    public void setUsergLnm(String usergLnm) {
        this.usergLnm = usergLnm;
    }

    public String getUsergPnm() {
        return usergPnm;
    }

    public void setUsergPnm(String usergPnm) {
        this.usergPnm = usergPnm;
    }

    public String getUsergTypCd() {
        return usergTypCd;
    }

    public void setUsergTypCd(String usergTypCd) {
        this.usergTypCd = usergTypCd;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaUserg [usergId=").append(usergId)
				.append(", usergLnm=").append(usergLnm).append(", usergPnm=")
				.append(usergPnm).append(", usergTypCd=").append(usergTypCd)
				.append("]");
		return builder.toString() + super.toString();
	}

//    public String getObjDescn() {
//        return objDescn;
//    }
//
//    public void setObjDescn(String objDescn) {
//        this.objDescn = objDescn;
//    }
//
//    public Integer getObjVers() {
//        return objVers;
//    }
//
//    public void setObjVers(Integer objVers) {
//        this.objVers = objVers;
//    }
//
//    public String getRegTypCd() {
//        return regTypCd;
//    }
//
//    public void setRegTypCd(String regTypCd) {
//        this.regTypCd = regTypCd;
//    }
//
//    public Date getWritDtm() {
//        return writDtm;
//    }
//
//    public void setWritDtm(Date writDtm) {
//        this.writDtm = writDtm;
//    }
//
//    public String getWritUserId() {
//        return writUserId;
//    }
//
//    public void setWritUserId(String writUserId) {
//        this.writUserId = writUserId;
//    }
    
    
}