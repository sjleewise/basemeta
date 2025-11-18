package kr.wise.dq.profile.colrngana.service;

import java.util.Date;

import kr.wise.dq.profile.coldtfrmana.service.WamPrfDtfrmAnaVO;

public class WamPrfRngAnaVO extends WamPrfDtfrmAnaVO {
    private String prfId;

    private String rngOpr1;

    private String rngEfva1;

    private String rngCnc;

    private String rngOpr2;

    private String rngEfva2;

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

    public String getPrfId() {
        return prfId;
    }

    public void setPrfId(String prfId) {
        this.prfId = prfId;
    }

    public String getRngOpr1() {
        return rngOpr1;
    }

    public void setRngOpr1(String rngOpr1) {
        this.rngOpr1 = rngOpr1;
    }

    public String getRngEfva1() {
        return rngEfva1;
    }

    public void setRngEfva1(String rngEfva1) {
        this.rngEfva1 = rngEfva1;
    }

    public String getRngCnc() {
        return rngCnc;
    }

    public void setRngCnc(String rngCnc) {
        this.rngCnc = rngCnc;
    }

    public String getRngOpr2() {
        return rngOpr2;
    }

    public void setRngOpr2(String rngOpr2) {
        this.rngOpr2 = rngOpr2;
    }

    public String getRngEfva2() {
        return rngEfva2;
    }

    public void setRngEfva2(String rngEfva2) {
        this.rngEfva2 = rngEfva2;
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