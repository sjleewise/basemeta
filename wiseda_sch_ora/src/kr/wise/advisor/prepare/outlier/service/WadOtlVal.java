package kr.wise.advisor.prepare.outlier.service;

import kr.wise.commons.cmm.CommonVo;

public class WadOtlVal extends CommonVo {
    private String otlDtcId; //이상값 탐지ID

    private Integer varSno;	 //변수 순번

    private String anlVarId; //변수 ID
    
    private String dbTblNm;

    private String dbColNm;
    
    private String otlAddCnd;
    
    private String condCd;
    private String oprCd;
    private String oprVal;


    public String getOtlDtcId() {
        return otlDtcId;
    }

    public void setOtlDtcId(String otlDtcId) {
        this.otlDtcId = otlDtcId;
    }

    public Integer getVarSno() {
        return varSno;
    }

    public void setVarSno(Integer varSno) {
        this.varSno = varSno;
    }

    public String getAnlVarId() {
        return anlVarId;
    }

    public void setAnlVarId(String anlVarId) {
        this.anlVarId = anlVarId;
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

	/**
	 * @return the dbColNm
	 */
	public String getDbColNm() {
		return dbColNm;
	}

	/**
	 * @param dbColNm the dbColNm to set
	 */
	public void setDbColNm(String dbColNm) {
		this.dbColNm = dbColNm;
	}

	public String getOtlAddCnd() {
		return otlAddCnd;
	}

	public void setOtlAddCnd(String otlAddCnd) {
		this.otlAddCnd = otlAddCnd;
	}

	public String getCondCd() {
		return condCd;
	}

	public void setCondCd(String condCd) {
		this.condCd = condCd;
	}

	public String getOprCd() {
		return oprCd;
	}

	public void setOprCd(String oprCd) {
		this.oprCd = oprCd;
	}

	public String getOprVal() {
		return oprVal;
	}

	public void setOprVal(String oprVal) {
		this.oprVal = oprVal;
	}
	
	
	

}