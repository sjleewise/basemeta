package kr.wise.commons.sysmgmt.basicinfo.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaBscLvl extends CommonVo {
    private String bscId;

//    private Date expDtm;

    private Short bscLvl;

//    private Date strDtm;

    private String selectBoxId;
    
    private String selectBoxNm;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date writDtm;
//
//    private String writUserId;

    public String getSelectBoxNm() {
		return selectBoxNm;
	}

	public void setSelectBoxNm(String selectBoxNm) {
		this.selectBoxNm = selectBoxNm;
	}

	public String getBscId() {
        return bscId;
    }

    public void setBscId(String bscId) {
        this.bscId = bscId;
    }

//    public Date getExpDtm() {
//        return expDtm;
//    }
//
//    public void setExpDtm(Date expDtm) {
//        this.expDtm = expDtm;
//    }

    public Short getBscLvl() {
        return bscLvl;
    }

    public void setBscLvl(Short bscLvl) {
        this.bscLvl = bscLvl;
    }

//    public Date getStrDtm() {
//        return strDtm;
//    }
//
//    public void setStrDtm(Date strDtm) {
//        this.strDtm = strDtm;
//    }

    public String getSelectBoxId() {
        return selectBoxId;
    }

    public void setSelectBoxId(String selectBoxId) {
        this.selectBoxId = selectBoxId;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaBscLvl [bscId=").append(bscId).append(", bscLvl=")
				.append(bscLvl).append(", selectBoxId=").append(selectBoxId)
				.append(", selectBoxNm=").append(selectBoxNm).append("]");
		return builder.toString()+super.toString();
	}
}