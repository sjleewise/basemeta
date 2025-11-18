package kr.wise.advisor.prepare.dataset.service;

import kr.wise.commons.cmm.CommonVo;

public class WadAnaVarNstnd extends WadDataSet {
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
    
    //20180607 추가///////////
    private String pkYn;
    private String maxLen;
    private String minLen;
    private String dtYn;
    private String telYn;
    private String spaceRt;
    private String crlfYn;
    private String alphaYn;
    private String dataFmt;
    private String numYn;
    private String hundRt;
    private String cntRt;
    //////////////////////////
    
    private String varType;

    private String useYn;

    private String fpoYn;

    private String nmbYn;

    private String unqYn;

    //private String dtYn;

    private String tgtVarYn;

    private String drvVarYn;

    private String lenChgYn;

    private String dataLenExcYn;

    private String dmngNm;	//도메인 그룹명
    private String dmnPdt;
    private String dmnPrb;
  
    private String histoYn;

    private String otlAddCnd;
    private String anaVarChk;
    private Integer varSno;
    

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

	public String getPkYn() {
		return pkYn;
	}

	public void setPkYn(String pkYn) {
		this.pkYn = pkYn;
	}

	public String getMaxLen() {
		return maxLen;
	}

	public void setMaxLen(String maxLen) {
		this.maxLen = maxLen;
	}

	public String getMinLen() {
		return minLen;
	}

	public void setMinLen(String minLen) {
		this.minLen = minLen;
	}

	public String getTelYn() {
		return telYn;
	}

	public void setTelYn(String telYn) {
		this.telYn = telYn;
	}

	public String getSpaceRt() {
		return spaceRt;
	}

	public void setSpaceRt(String spaceRt) {
		this.spaceRt = spaceRt;
	}

	public String getCrlfYn() {
		return crlfYn;
	}

	public void setCrlfYn(String crlfYn) {
		this.crlfYn = crlfYn;
	}

	public String getAlphaYn() {
		return alphaYn;
	}

	public void setAlphaYn(String alphaYn) {
		this.alphaYn = alphaYn;
	}

	public String getDataFmt() {
		return dataFmt;
	}

	public void setDataFmt(String dataFmt) {
		this.dataFmt = dataFmt;
	}

	public String getNumYn() {
		return numYn;
	}

	public void setNumYn(String numYn) {
		this.numYn = numYn;
	}

	public String getHundRt() {
		return hundRt;
	}

	public void setHundRt(String hundRt) {
		this.hundRt = hundRt;
	}

	public String getCntRt() {
		return cntRt;
	}

	public void setCntRt(String cntRt) {
		this.cntRt = cntRt;
	}
	
	 	

}