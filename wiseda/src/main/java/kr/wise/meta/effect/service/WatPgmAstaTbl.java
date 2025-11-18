package kr.wise.meta.effect.service;

import java.util.Date;

public class WatPgmAstaTbl {
    private String requestId;

    private String objNm;

    private Date staDttm;

    private Integer subsystemId;

    private Integer pgmId;

    private String tblEnm;

    private String createYn;

    private String readYn;

    private String updateYn;

    private String deleteYn;

    private Integer ofFucId;

    /** 물리모델 테이블 ID insomnia */
    private String pdmTblId;

    /** 물리모델 테이블(물리명) insomnia */
    private String pdmTblPnm;

    /** 물리모델 테이블(논리명) insomnia */
    private String pdmTblLnm;

    private String fullPath; // 주제영역경로

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

    public String getTblEnm() {
        return tblEnm;
    }

    public void setTblEnm(String tblEnm) {
        this.tblEnm = tblEnm;
    }

    public String getCreateYn() {
        return createYn;
    }

    public void setCreateYn(String createYn) {
        this.createYn = createYn;
    }

    public String getReadYn() {
        return readYn;
    }

    public void setReadYn(String readYn) {
        this.readYn = readYn;
    }

    public String getUpdateYn() {
        return updateYn;
    }

    public void setUpdateYn(String updateYn) {
        this.updateYn = updateYn;
    }

    public String getDeleteYn() {
        return deleteYn;
    }

    public void setDeleteYn(String deleteYn) {
        this.deleteYn = deleteYn;
    }

    public Integer getOfFucId() {
        return ofFucId;
    }

    public void setOfFucId(Integer ofFucId) {
        this.ofFucId = ofFucId;
    }

	/**
	 * @return the pdmTblId
	 */
	public String getPdmTblId() {
		return pdmTblId;
	}

	/**
	 * @param pdmTblId the pdmTblId to set
	 */
	public void setPdmTblId(String pdmTblId) {
		this.pdmTblId = pdmTblId;
	}

	/**
	 * @return the pdmTblPnm
	 */
	public String getPdmTblPnm() {
		return pdmTblPnm;
	}

	/**
	 * @param pdmTblPnm the pdmTblPnm to set
	 */
	public void setPdmTblPnm(String pdmTblPnm) {
		this.pdmTblPnm = pdmTblPnm;
	}

	/**
	 * @return the pdmTblLnm
	 */
	public String getPdmTblLnm() {
		return pdmTblLnm;
	}

	/**
	 * @param pdmTblLnm the pdmTblLnm to set
	 */
	public void setPdmTblLnm(String pdmTblLnm) {
		this.pdmTblLnm = pdmTblLnm;
	}

	/**
	 * @return the fullPath
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * @param fullPath the fullPath to set
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
}