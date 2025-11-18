package kr.wise.dq.impv.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WamCsAnaMstr extends CommonVo{
    private String anaJobId;

    private Date anaStrDtm;
    
    private String anaStrDtmLink;

    private String anaKndCd;

    private String csAnaCd;

    private String csAnaDtls;

    private Date csAnaStrDtm;

    private Date csAnaEndDtm;

    private String imPlCd;

    private String imPlDtls;

    private Date imPlStrDtm;

    private Date imPlEndDtm;

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
    

    public String getAnaStrDtmLink() {
		return anaStrDtmLink;
	}

	public void setAnaStrDtmLink(String anaStrDtmLink) {
		this.anaStrDtmLink = anaStrDtmLink;
	}

	public String getAnaJobId() {
        return anaJobId;
    }

    public void setAnaJobId(String anaJobId) {
        this.anaJobId = anaJobId;
    }

    public Date getAnaStrDtm() {
        return anaStrDtm;
    }

    public void setAnaStrDtm(Date anaStrDtm) {
        this.anaStrDtm = anaStrDtm;
    }

    public String getAnaKndCd() {
        return anaKndCd;
    }

    public void setAnaKndCd(String anaKndCd) {
        this.anaKndCd = anaKndCd;
    }

    public String getCsAnaCd() {
        return csAnaCd;
    }

    public void setCsAnaCd(String csAnaCd) {
        this.csAnaCd = csAnaCd;
    }

    public String getCsAnaDtls() {
        return csAnaDtls;
    }

    public void setCsAnaDtls(String csAnaDtls) {
        this.csAnaDtls = csAnaDtls;
    }

    public Date getCsAnaStrDtm() {
        return csAnaStrDtm;
    }

    public void setCsAnaStrDtm(Date csAnaStrDtm) {
        this.csAnaStrDtm = csAnaStrDtm;
    }

    public Date getCsAnaEndDtm() {
        return csAnaEndDtm;
    }

    public void setCsAnaEndDtm(Date csAnaEndDtm) {
        this.csAnaEndDtm = csAnaEndDtm;
    }

    public String getImPlCd() {
        return imPlCd;
    }

    public void setImPlCd(String imPlCd) {
        this.imPlCd = imPlCd;
    }

    public String getImPlDtls() {
        return imPlDtls;
    }

    public void setImPlDtls(String imPlDtls) {
        this.imPlDtls = imPlDtls;
    }

    public Date getImPlStrDtm() {
        return imPlStrDtm;
    }

    public void setImPlStrDtm(Date imPlStrDtm) {
        this.imPlStrDtm = imPlStrDtm;
    }

    public Date getImPlEndDtm() {
        return imPlEndDtm;
    }

    public void setImPlEndDtm(Date imPlEndDtm) {
        this.imPlEndDtm = imPlEndDtm;
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
}