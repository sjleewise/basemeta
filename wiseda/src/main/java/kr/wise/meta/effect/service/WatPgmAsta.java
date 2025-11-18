package kr.wise.meta.effect.service;

import java.util.Date;

public class WatPgmAsta {
    private String requestId;

    private String objNm;

    private Date staDttm;

    private Integer subsystemId;

    private Integer pgmId;

    private String pgmFilePath;

    private String pgmFileNm;

    private String hostIp;

    private String pgmNm;

    private String pgmType;

    private String categoryType;

    private String pgmUrl;

    private String orglUser;

    private String updtUser;

    private Date orglDttm;

    private Date updtDttm;

    /** 시스템 */
    private String systemNm;
    /** 업무시스템 */
    private String bizsystemNm;

    /** 상세업무시스템 insomnia */
    private String subsystemNm;

    /** 테이블 물리명 insomnia */
    private String tblEnm;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getObjNm() {
        return objNm;
    }

    public void setObjNm(String objNm) {
        this.objNm = objNm;
    }

    public Date getStaDttm() {
        return staDttm;
    }

    public void setStaDttm(Date staDttm) {
        this.staDttm = staDttm;
    }

    public Integer getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(Integer subsystemId) {
        this.subsystemId = subsystemId;
    }

    public Integer getPgmId() {
        return pgmId;
    }

    public void setPgmId(Integer pgmId) {
        this.pgmId = pgmId;
    }

    public String getPgmFilePath() {
        return pgmFilePath;
    }

    public void setPgmFilePath(String pgmFilePath) {
        this.pgmFilePath = pgmFilePath;
    }

    public String getPgmFileNm() {
        return pgmFileNm;
    }

    public void setPgmFileNm(String pgmFileNm) {
        this.pgmFileNm = pgmFileNm;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getPgmNm() {
        return pgmNm;
    }

    public void setPgmNm(String pgmNm) {
        this.pgmNm = pgmNm;
    }

    public String getPgmType() {
        return pgmType;
    }

    public void setPgmType(String pgmType) {
        this.pgmType = pgmType;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getPgmUrl() {
        return pgmUrl;
    }

    public void setPgmUrl(String pgmUrl) {
        this.pgmUrl = pgmUrl;
    }

    public String getOrglUser() {
        return orglUser;
    }

    public void setOrglUser(String orglUser) {
        this.orglUser = orglUser;
    }

    public String getUpdtUser() {
        return updtUser;
    }

    public void setUpdtUser(String updtUser) {
        this.updtUser = updtUser;
    }

    public Date getOrglDttm() {
        return orglDttm;
    }

    public void setOrglDttm(Date orglDttm) {
        this.orglDttm = orglDttm;
    }

    public Date getUpdtDttm() {
        return updtDttm;
    }

    public void setUpdtDttm(Date updtDttm) {
        this.updtDttm = updtDttm;
    }

	/**
	 * @return the systemNm
	 */
	public String getSystemNm() {
		return systemNm;
	}

	/**
	 * @param systemNm the systemNm to set
	 */
	public void setSystemNm(String systemNm) {
		this.systemNm = systemNm;
	}

	/**
	 * @return the bizsystemNm
	 */
	public String getBizsystemNm() {
		return bizsystemNm;
	}

	/**
	 * @param bizsystemNm the bizsystemNm to set
	 */
	public void setBizsystemNm(String bizsystemNm) {
		this.bizsystemNm = bizsystemNm;
	}

	/**
	 * @return the subsystemNm
	 */
	public String getSubsystemNm() {
		return subsystemNm;
	}

	/**
	 * @param subsystemNm the subsystemNm to set
	 */
	public void setSubsystemNm(String subsystemNm) {
		this.subsystemNm = subsystemNm;
	}

	/**
	 * @return the tblEnm
	 */
	public String getTblEnm() {
		return tblEnm;
	}

	/**
	 * @param tblEnm the tblEnm to set
	 */
	public void setTblEnm(String tblEnm) {
		this.tblEnm = tblEnm;
	}
}