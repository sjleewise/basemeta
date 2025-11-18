package kr.wise.advisor.prepare.dataset.service;

import kr.wise.commons.cmm.CommonVo;

public class WadAnaVar extends WadDataSet {
    private String anlVarId;

    private String daseId;

    private String dbSchId;

    private String dbTblNm;

    private String dbColNm;

    private String colMrpLnm;

    private String colMrpPnm;

    private String dbmsTypCd;
    private String dataType;
    private Integer dataLen;
    private Integer dataScal;

    private String varType;

    private String useYn;

    private String fpoYn;

    private String nmbYn;

    private String unqYn;

    private String dtYn;

    private String tgtVarYn;

    private String drvVarYn;

    private String lenChgYn;

    private String dataLenExcYn;

    private String dmngNm;
    private String dmnPdt;
    private String dmnPrb;
  
    private String histoYn;

    private String otlAddCnd;
    private String anaVarChk;
    private Integer varSno;
    
    private String condCd; //조건(AND, OR)
    private String oprCd; //연산자(>, <, = ...)
    private String oprVal; //값
    
    private String dqiId; //품질지표ID
    private String dqiLnm; //품질지표
    private String otlDtcId; //이상값ID
    
    private String weight;

    public String getAnlVarId() {
        return anlVarId;
    }

    public void setAnlVarId(String anlVarId) {
        this.anlVarId = anlVarId;
    }

    public String getDaseId() {
        return daseId;
    }

    public void setDaseId(String daseId) {
        this.daseId = daseId;
    }

    public String getDbSchId() {
        return dbSchId;
    }

    public void setDbSchId(String dbSchId) {
        this.dbSchId = dbSchId;
    }

    public String getDbTblNm() {
        return dbTblNm;
    }

    public void setDbTblNm(String dbTblNm) {
        this.dbTblNm = dbTblNm;
    }

    public String getDbColNm() {
        return dbColNm;
    }

    public void setDbColNm(String dbColNm) {
        this.dbColNm = dbColNm;
    }

    public String getColMrpLnm() {
        return colMrpLnm;
    }

    public void setColMrpLnm(String colMrpLnm) {
        this.colMrpLnm = colMrpLnm;
    }

    public String getColMrpPnm() {
        return colMrpPnm;
    }

    public void setColMrpPnm(String colMrpPnm) {
        this.colMrpPnm = colMrpPnm;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getVarType() {
        return varType;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getFpoYn() {
        return fpoYn;
    }

    public void setFpoYn(String fpoYn) {
        this.fpoYn = fpoYn;
    }

    public String getNmbYn() {
        return nmbYn;
    }

    public void setNmbYn(String nmbYn) {
        this.nmbYn = nmbYn;
    }

    public String getUnqYn() {
        return unqYn;
    }

    public void setUnqYn(String unqYn) {
        this.unqYn = unqYn;
    }

    public String getDtYn() {
        return dtYn;
    }

    public void setDtYn(String dtYn) {
        this.dtYn = dtYn;
    }

    public String getTgtVarYn() {
        return tgtVarYn;
    }

    public void setTgtVarYn(String tgtVarYn) {
        this.tgtVarYn = tgtVarYn;
    }

    public String getDrvVarYn() {
        return drvVarYn;
    }

    public void setDrvVarYn(String drvVarYn) {
        this.drvVarYn = drvVarYn;
    }

    public String getLenChgYn() {
        return lenChgYn;
    }

    public void setLenChgYn(String lenChgYn) {
        this.lenChgYn = lenChgYn;
    }

    public String getDataLenExcYn() {
        return dataLenExcYn;
    }

    public void setDataLenExcYn(String dataLenExcYn) {
        this.dataLenExcYn = dataLenExcYn;
    }

    public String getDmngNm() {
        return dmngNm;
    }

    public void setDmngNm(String dmngNm) {
        this.dmngNm = dmngNm;
    }

	/**
	 * @return the dbmsTypCd
	 */
	public String getDbmsTypCd() {
		return dbmsTypCd;
	}

	/**
	 * @param dbmsTypCd the dbmsTypCd to set
	 */
	public void setDbmsTypCd(String dbmsTypCd) {
		this.dbmsTypCd = dbmsTypCd;
	}

	/**
	 * @return the dataLen
	 */
	public Integer getDataLen() {
		return dataLen;
	}

	/**
	 * @param dataLen the dataLen to set
	 */
	public void setDataLen(Integer dataLen) {
		this.dataLen = dataLen;
	}

	/**
	 * @return the dataScal
	 */
	public Integer getDataScal() {
		return dataScal;
	}

	/**
	 * @param dataScal the dataScal to set
	 */
	public void setDataScal(Integer dataScal) {
		this.dataScal = dataScal;
	}


	/**
	 * @return the dmnPdt
	 */
	public String getDmnPdt() {
		return dmnPdt;
	}

	/**
	 * @param dmnPdt the dmnPdt to set
	 */
	public void setDmnPdt(String dmnPdt) {
		this.dmnPdt = dmnPdt;
	}

	/**
	 * @return the dmnPrb
	 */
	public String getDmnPrb() {
		return dmnPrb;
	}

	/**
	 * @param dmnPrb the dmnPrb to set
	 */
	public void setDmnPrb(String dmnPrb) {
		this.dmnPrb = dmnPrb;
	}

	/**
	 * @return the histoYn
	 */
	public String getHistoYn() {
		return histoYn;
	}

	/**
	 * @param histoYn the histoYn to set
	 */
	public void setHistoYn(String histoYn) {
		this.histoYn = histoYn;
	}

	public String getOtlAddCnd() {
		return otlAddCnd;
	}

	public void setOtlAddCnd(String otlAddCnd) {
		this.otlAddCnd = otlAddCnd;
	}

	public String getAnaVarChk() {
		return anaVarChk;
	}

	public void setAnaVarChk(String anaVarChk) {
		this.anaVarChk = anaVarChk;
	}

	public Integer getVarSno() {
		return varSno;
	}

	public void setVarSno(Integer varSno) {
		this.varSno = varSno;
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

	public String getOtlDtcId() {
		return otlDtcId;
	}

	public void setOtlDtcId(String otlDtcId) {
		this.otlDtcId = otlDtcId;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	 	

}