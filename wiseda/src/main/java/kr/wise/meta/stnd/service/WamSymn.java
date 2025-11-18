package kr.wise.meta.stnd.service;

import kr.wise.commons.cmm.CommonVo;

public class WamSymn extends CommonVo{
    private String symnId;

    private String symnLnm;

    private String symnPnm;

    private String symnDcd;

    private String stwdId;

    private String sbswdLnm;

    private String sbswdPnm;

    private String stndAsrt;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date frsRqstDtm;
//
//    private String frsRqstUserId;
//
//    private Date rqstDtm;
//
//    private String rqstUserId;
//
//    private Date aprvDtm;
//
//    private String aprvUserId;

    public String getSymnId() {
        return symnId;
    }

    public void setSymnId(String symnId) {
        this.symnId = symnId;
    }

    public String getSymnLnm() {
        return symnLnm;
    }

    public void setSymnLnm(String symnLnm) {
        this.symnLnm = symnLnm;
    }

    public String getSymnPnm() {
        return symnPnm;
    }

    public void setSymnPnm(String symnPnm) {
        this.symnPnm = symnPnm;
    }

    public String getSymnDcd() {
        return symnDcd;
    }

    public void setSymnDcd(String symnDcd) {
        this.symnDcd = symnDcd;
    }

    public String getStwdId() {
        return stwdId;
    }

    public void setStwdId(String stwdId) {
        this.stwdId = stwdId;
    }

    public String getSbswdLnm() {
        return sbswdLnm;
    }

    public void setSbswdLnm(String sbswdLnm) {
        this.sbswdLnm = sbswdLnm;
    }

    public String getSbswdPnm() {
        return sbswdPnm;
    }

    public void setSbswdPnm(String sbswdPnm) {
        this.sbswdPnm = sbswdPnm;
    }

    
    
	public String getStndAsrt() {
		return stndAsrt;
	}

	public void setStndAsrt(String stndAsrt) {
		this.stndAsrt = stndAsrt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamSymn [symnId=").append(symnId).append(", symnLnm=")
				.append(symnLnm).append(", symnPnm=").append(symnPnm).append(", stndAsrt=").append(stndAsrt)
				.append(", symnDcd=").append(symnDcd).append(", stwdId=")
				.append(stwdId).append(", sbswdLnm=").append(sbswdLnm)
				.append(", sbswdPnm=").append(sbswdPnm).append("]");
		return super.toString() + builder.toString();
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
//    public Date getFrsRqstDtm() {
//        return frsRqstDtm;
//    }
//
//    public void setFrsRqstDtm(Date frsRqstDtm) {
//        this.frsRqstDtm = frsRqstDtm;
//    }
//
//    public String getFrsRqstUserId() {
//        return frsRqstUserId;
//    }
//
//    public void setFrsRqstUserId(String frsRqstUserId) {
//        this.frsRqstUserId = frsRqstUserId;
//    }
//
//    public Date getRqstDtm() {
//        return rqstDtm;
//    }
//
//    public void setRqstDtm(Date rqstDtm) {
//        this.rqstDtm = rqstDtm;
//    }
//
//    public String getRqstUserId() {
//        return rqstUserId;
//    }
//
//    public void setRqstUserId(String rqstUserId) {
//        this.rqstUserId = rqstUserId;
//    }
//
//    public Date getAprvDtm() {
//        return aprvDtm;
//    }
//
//    public void setAprvDtm(Date aprvDtm) {
//        this.aprvDtm = aprvDtm;
//    }
//
//    public String getAprvUserId() {
//        return aprvUserId;
//    }
//
//    public void setAprvUserId(String aprvUserId) {
//        this.aprvUserId = aprvUserId;
//    }
    
    
}