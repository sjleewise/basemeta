package kr.wise.commons.code.service;

import java.util.Date;

public class ComCodeDetailVO {
	
	private Integer keyId;
	
    private String codeId;

    private String codeIdNm;

    private String code;

    private String codeNm;

    private String codeDc;

    private String useAt;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeNm() {
        return codeNm;
    }

    public void setCodeNm(String codeNm) {
        this.codeNm = codeNm;
    }

    public String getCodeDc() {
        return codeDc;
    }

    public void setCodeDc(String codeDc) {
        this.codeDc = codeDc;
    }

    public String getUseAt() {
        return useAt;
    }

    public void setUseAt(String useAt) {
        this.useAt = useAt;
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

	public String getCodeIdNm() {
		return codeIdNm;
	}

	public void setCodeIdNm(String codeIdNm) {
		this.codeIdNm = codeIdNm;
	}

	public Integer getKeyId() {
		return keyId;
	}

	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}
}