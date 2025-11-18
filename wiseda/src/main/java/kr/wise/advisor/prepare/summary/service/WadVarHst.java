package kr.wise.advisor.prepare.summary.service;

import java.math.BigDecimal;

import kr.wise.advisor.prepare.dataset.service.WadAnaVar;

public class WadVarHst extends WadAnaVar {
    private String anlVarId;

    private Integer hstSno;

    private Float sctVal;

    private String sctNm;

    private Float sctStrVal;

    private Float sctEndVal;


    public String getAnlVarId() {
        return anlVarId;
    }

    public void setAnlVarId(String anlVarId) {
        this.anlVarId = anlVarId;
    }

    public Integer getHstSno() {
        return hstSno;
    }

    public void setHstSno(Integer hstSno) {
        this.hstSno = hstSno;
    }

    public Float getSctVal() {
        return sctVal;
    }

    public void setSctVal(Float	 sctVal) {
        this.sctVal = sctVal;
    }

    public String getSctNm() {
        return sctNm;
    }

    public void setSctNm(String sctNm) {
        this.sctNm = sctNm;
    }

	/**
	 * @return the sctStrVal
	 */
	public Float getSctStrVal() {
		return sctStrVal;
	}

	/**
	 * @param sctStrVal the sctStrVal to set
	 */
	public void setSctStrVal(Float sctStrVal) {
		this.sctStrVal = sctStrVal;
	}

	/**
	 * @return the sctEndVal
	 */
	public Float getSctEndVal() {
		return sctEndVal;
	}

	/**
	 * @param sctEndVal the sctEndVal to set
	 */
	public void setSctEndVal(Float sctEndVal) {
		this.sctEndVal = sctEndVal;
	}


}