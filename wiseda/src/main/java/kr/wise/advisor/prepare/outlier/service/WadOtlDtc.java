package kr.wise.advisor.prepare.outlier.service;

import java.util.List;

import kr.wise.commons.cmm.CommonVo;

public class WadOtlDtc extends CommonVo {
    private String otlDtcId;	//이상값탐지ID

    private String daseId;		//데이터셋ID
    private String dbSchId;		//스키마ID

    private String otlAlgId;	//이상값탐지 알고리즘ID
    
    
    private String[] algArgIds;	//알고리즘 파라미터  ID 목록

    private String[] argVals;	//알고리즘 파라미터 값 목록
    
    private String anlVarId;
    
    private String otlNm;

    private String dqiId; //품질지표ID
    private String dqiLnm; //품질지표ID

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
	 * @return the algArgIds
	 */
	public String[] getAlgArgIds() {
		return algArgIds;
	}

	/**
	 * @param algArgIds the algArgIds to set
	 */
	public void setAlgArgIds(String[] algArgIds) {
		this.algArgIds = algArgIds;
	}

	/**
	 * @return the argVals
	 */
	public String[] getArgVals() {
		return argVals;
	}

	/**
	 * @param argVals the argVals to set
	 */
	public void setArgVals(String[] argVals) {
		this.argVals = argVals;
	}

	/**
	 * @return the anlVarId
	 */
	public String getAnlVarId() {
		return anlVarId;
	}

	/**
	 * @param anlVarId the anlVarId to set
	 */
	public void setAnlVarId(String anlVarId) {
		this.anlVarId = anlVarId;
	}

	
	public String getOtlNm() {
		return otlNm;
	}

	public void setOtlNm(String otlNm) {
		this.otlNm = otlNm;
	}

	public String getDqiId() {
		return dqiId;
	}

	public void setDqiId(String dqiId) {
		this.dqiId = dqiId;
	}

	public String getDqiLnm() {
		return dqiLnm;
	}

	public void setDqiLnm(String dqiLnm) {
		this.dqiLnm = dqiLnm;
	}

	
	



}