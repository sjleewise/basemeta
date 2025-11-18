package kr.wise.commons.bbs.service;

import java.util.Date;

public class Comtnbbsmaster {
    private String bbsId;

    private String bbsNm;

    private String bbsIntrcn;

    private String bbsTyCode;

    private String bbsAttrbCode;

    private String replyPosblAt;

    private String fileAtchPosblAt;

    private Short atchPosblFileNumber;

    private Integer atchPosblFileSize;

    private String useAt;

    private String tmplatId;

    private String frstRegisterId;

    private Date frstRegistPnttm;

    private String lastUpdusrId;

    private Date lastUpdtPnttm;

    public String getBbsId() {
        return bbsId;
    }

    public void setBbsId(String bbsId) {
        this.bbsId = bbsId;
    }

    public String getBbsNm() {
        return bbsNm;
    }

    public void setBbsNm(String bbsNm) {
        this.bbsNm = bbsNm;
    }

    public String getBbsIntrcn() {
        return bbsIntrcn;
    }

    public void setBbsIntrcn(String bbsIntrcn) {
        this.bbsIntrcn = bbsIntrcn;
    }

    public String getBbsTyCode() {
        return bbsTyCode;
    }

    public void setBbsTyCode(String bbsTyCode) {
        this.bbsTyCode = bbsTyCode;
    }

    public String getBbsAttrbCode() {
        return bbsAttrbCode;
    }

    public void setBbsAttrbCode(String bbsAttrbCode) {
        this.bbsAttrbCode = bbsAttrbCode;
    }

    public String getReplyPosblAt() {
        return replyPosblAt;
    }

    public void setReplyPosblAt(String replyPosblAt) {
        this.replyPosblAt = replyPosblAt;
    }

    public String getFileAtchPosblAt() {
        return fileAtchPosblAt;
    }

    public void setFileAtchPosblAt(String fileAtchPosblAt) {
        this.fileAtchPosblAt = fileAtchPosblAt;
    }

    public Short getAtchPosblFileNumber() {
        return atchPosblFileNumber;
    }

    public void setAtchPosblFileNumber(Short atchPosblFileNumber) {
        this.atchPosblFileNumber = atchPosblFileNumber;
    }

    public Integer getAtchPosblFileSize() {
        return atchPosblFileSize;
    }

    public void setAtchPosblFileSize(Integer atchPosblFileSize) {
        this.atchPosblFileSize = atchPosblFileSize;
    }

    public String getUseAt() {
        return useAt;
    }

    public void setUseAt(String useAt) {
        this.useAt = useAt;
    }

    public String getTmplatId() {
        return tmplatId;
    }

    public void setTmplatId(String tmplatId) {
        this.tmplatId = tmplatId;
    }

    public String getFrstRegisterId() {
        return frstRegisterId;
    }

    public void setFrstRegisterId(String frstRegisterId) {
        this.frstRegisterId = frstRegisterId;
    }

    public Date getFrstRegistPnttm() {
        return frstRegistPnttm;
    }

    public void setFrstRegistPnttm(Date frstRegistPnttm) {
        this.frstRegistPnttm = frstRegistPnttm;
    }

    public String getLastUpdusrId() {
        return lastUpdusrId;
    }

    public void setLastUpdusrId(String lastUpdusrId) {
        this.lastUpdusrId = lastUpdusrId;
    }

    public Date getLastUpdtPnttm() {
        return lastUpdtPnttm;
    }

    public void setLastUpdtPnttm(Date lastUpdtPnttm) {
        this.lastUpdtPnttm = lastUpdtPnttm;
    }
}