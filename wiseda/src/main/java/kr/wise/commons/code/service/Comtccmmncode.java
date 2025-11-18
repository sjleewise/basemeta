package kr.wise.commons.code.service;

import java.util.Date;

public class Comtccmmncode {
    private String codeId;

    private String codeIdNm;

    private String codeIdDc;

    private String useAt;

    private String clCode;

    private String clCodeNm;
    
    private Date frstRegistPnttm;

    private String frstRegisterId;

    private Date lastUpdtPnttm;

    private String lastUpdusrId;

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getCodeIdNm() {
        return codeIdNm;
    }

    public void setCodeIdNm(String codeIdNm) {
        this.codeIdNm = codeIdNm;
    }

    public String getCodeIdDc() {
        return codeIdDc;
    }

    public void setCodeIdDc(String codeIdDc) {
        this.codeIdDc = codeIdDc;
    }

    public String getUseAt() {
        return useAt;
    }

    public void setUseAt(String useAt) {
        this.useAt = useAt;
    }

    public String getClCode() {
        return clCode;
    }

    public void setClCode(String clCode) {
        this.clCode = clCode;
    }

    public Date getFrstRegistPnttm() {
        return frstRegistPnttm;
    }

    public void setFrstRegistPnttm(Date frstRegistPnttm) {
        this.frstRegistPnttm = frstRegistPnttm;
    }

    public String getFrstRegisterId() {
        return frstRegisterId;
    }

    public void setFrstRegisterId(String frstRegisterId) {
        this.frstRegisterId = frstRegisterId;
    }

    public Date getLastUpdtPnttm() {
        return lastUpdtPnttm;
    }

    public void setLastUpdtPnttm(Date lastUpdtPnttm) {
        this.lastUpdtPnttm = lastUpdtPnttm;
    }

    public String getLastUpdusrId() {
        return lastUpdusrId;
    }

    public void setLastUpdusrId(String lastUpdusrId) {
        this.lastUpdusrId = lastUpdusrId;
    }

	public String getClCodeNm() {
		return clCodeNm;
	}

	public void setClCodeNm(String clCodeNm) {
		this.clCodeNm = clCodeNm;
	}
}