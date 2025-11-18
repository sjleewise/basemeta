package kr.wise.advisor.prepare.outlier.service;

import kr.wise.commons.cmm.CommonVo;

public class WadOtlDtc extends CommonVo {
    private String otlDtcId;	//이상값탐지ID

    private String daseId;		//데이터셋ID
    private String dbSchId;		//스키마ID

    private String otlAlgId;	//이상값탐지 알고리즘ID
    
    private String dbTblNm;
    

    public String getOtlDtcId() {
        return otlDtcId;
    }

    public void setOtlDtcId(String otlDtcId) {
        this.otlDtcId = otlDtcId;
    }

    public String getDaseId() {
        return daseId;
    }

    public void setDaseId(String daseId) {
        this.daseId = daseId;
    }

    public String getOtlAlgId() {
        return otlAlgId;
    }

    public void setOtlAlgId(String otlAlgId) {
        this.otlAlgId = otlAlgId;
    }

	/**
	 * @return the dbSchId
	 */
	public String getDbSchId() {
		return dbSchId;
	}

	/**
	 * @param dbSchId the dbSchId to set
	 */
	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}

	/**
	 * @return the dbTblNm
	 */
	public String getDbTblNm() {
		return dbTblNm;
	}

	/**
	 * @param dbTblNm the dbTblNm to set
	 */
	public void setDbTblNm(String dbTblNm) {
		this.dbTblNm = dbTblNm;
	}




}