package kr.wise.meta.symn.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WamSymnItem  extends CommonVo {
    private String symnItemId;

    private String symnItemLnm;

    private String symnItemPnm;

    private String symnItemType;

    private String sditmId;

    private String sditmLnm;

    private String sditmPnm;

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

    private String pnmCriDs;

    public String getSymnItemId() {
        return symnItemId;
    }

    public void setSymnItemId(String symnItemId) {
        this.symnItemId = symnItemId;
    }

    public String getSymnItemLnm() {
        return symnItemLnm;
    }

    public void setSymnItemLnm(String symnItemLnm) {
        this.symnItemLnm = symnItemLnm;
    }

    public String getSymnItemPnm() {
        return symnItemPnm;
    }

    public void setSymnItemPnm(String symnItemPnm) {
        this.symnItemPnm = symnItemPnm;
    }

    public String getSymnItemType() {
        return symnItemType;
    }

    public void setSymnItemType(String symnItemType) {
        this.symnItemType = symnItemType;
    }

    public String getSditmId() {
        return sditmId;
    }

    public void setSditmId(String sditmId) {
        this.sditmId = sditmId;
    }

    public String getSditmLnm() {
        return sditmLnm;
    }

    public void setSditmLnm(String sditmLnm) {
        this.sditmLnm = sditmLnm;
    }

    public String getSditmPnm() {
        return sditmPnm;
    }

    public void setSditmPnm(String sditmPnm) {
        this.sditmPnm = sditmPnm;
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

    public String getPnmCriDs() {
        return pnmCriDs;
    }

    public void setPnmCriDs(String pnmCriDs) {
        this.pnmCriDs = pnmCriDs;
    }
}