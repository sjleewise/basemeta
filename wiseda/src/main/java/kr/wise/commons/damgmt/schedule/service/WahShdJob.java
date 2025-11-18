package kr.wise.commons.damgmt.schedule.service;

import java.util.Date;

public class WahShdJob {
    private String shdId;

    private String shdJobId;

    private Integer shdJobSno;

    private Date expDtm;

    private Date strDtm;

    private String rqstNo;

    private Integer rqstSno;

    private String objDescn;

    private Integer objVers;

    private String regTypCd;

    private Date frsRqstDtm;

    private String frsRqstUserId;

    private Date rqstDtm;

    private String rqstUserId;

    private Date aprvDtm;

    private String aprvUserId;
    
    private String etcJobNm;

    private String etcJobDtls;

    private String etcJobKndCd;

    public String getShdId() {
        return shdId;
    }

    public void setShdId(String shdId) {
        this.shdId = shdId;
    }

    public String getShdJobId() {
        return shdJobId;
    }

    public void setShdJobId(String shdJobId) {
        this.shdJobId = shdJobId;
    }

    public Integer getShdJobSno() {
        return shdJobSno;
    }

    public void setShdJobSno(Integer shdJobSno) {
        this.shdJobSno = shdJobSno;
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

    public String getRqstNo() {
        return rqstNo;
    }

    public void setRqstNo(String rqstNo) {
        this.rqstNo = rqstNo;
    }

    public Integer getRqstSno() {
        return rqstSno;
    }

    public void setRqstSno(Integer rqstSno) {
        this.rqstSno = rqstSno;
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

    public Date getFrsRqstDtm() {
        return frsRqstDtm;
    }

    public void setFrsRqstDtm(Date frsRqstDtm) {
        this.frsRqstDtm = frsRqstDtm;
    }

    public String getFrsRqstUserId() {
        return frsRqstUserId;
    }

    public void setFrsRqstUserId(String frsRqstUserId) {
        this.frsRqstUserId = frsRqstUserId;
    }

    public Date getRqstDtm() {
        return rqstDtm;
    }

    public void setRqstDtm(Date rqstDtm) {
        this.rqstDtm = rqstDtm;
    }

    public String getRqstUserId() {
        return rqstUserId;
    }

    public void setRqstUserId(String rqstUserId) {
        this.rqstUserId = rqstUserId;
    }

    public Date getAprvDtm() {
        return aprvDtm;
    }

    public void setAprvDtm(Date aprvDtm) {
        this.aprvDtm = aprvDtm;
    }

    public String getAprvUserId() {
        return aprvUserId;
    }

    public void setAprvUserId(String aprvUserId) {
        this.aprvUserId = aprvUserId;
    }
    
    public String getEtcJobNm() {
		return etcJobNm;
	}

	public void setEtcJobNm(String etcJobNm) {
		this.etcJobNm = etcJobNm;
	}

	public String getEtcJobDtls() {
		return etcJobDtls;
	}

	public void setEtcJobDtls(String etcJobDtls) {
		this.etcJobDtls = etcJobDtls;
	}

	public String getEtcJobKndCd() {
		return etcJobKndCd;
	}

	public void setEtcJobKndCd(String etcJobKndCd) {
		this.etcJobKndCd = etcJobKndCd;
	}
}