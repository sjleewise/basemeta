package kr.wise.advisor.prepare.outlier.service;

import kr.wise.commons.cmm.CommonVo;

public class WadOtlArg extends CommonVo {
    private String otlDtcId;

    private String algArgId;

    private String argVal;
    
    private String argLnm;

    private String argPnm;

    private String argDataType;

    private String argDefltVal;
    

    public String getOtlDtcId() {
        return otlDtcId;
    }

    public void setOtlDtcId(String otlDtcId) {
        this.otlDtcId = otlDtcId;
    }

    public String getAlgArgId() {
        return algArgId;
    }

    public void setAlgArgId(String algArgId) {
        this.algArgId = algArgId;
    }

    public String getArgVal() {
        return argVal;
    }

    public void setArgVal(String argVal) {
        this.argVal = argVal;
    }

	/**
	 * @return the argLnm
	 */
	public String getArgLnm() {
		return argLnm;
	}

	/**
	 * @param argLnm the argLnm to set
	 */
	public void setArgLnm(String argLnm) {
		this.argLnm = argLnm;
	}

	/**
	 * @return the argPnm
	 */
	public String getArgPnm() {
		return argPnm;
	}

	/**
	 * @param argPnm the argPnm to set
	 */
	public void setArgPnm(String argPnm) {
		this.argPnm = argPnm;
	}

	/**
	 * @return the argDataType
	 */
	public String getArgDataType() {
		return argDataType;
	}

	/**
	 * @param argDataType the argDataType to set
	 */
	public void setArgDataType(String argDataType) {
		this.argDataType = argDataType;
	}

	/**
	 * @return the argDefltVal
	 */
	public String getArgDefltVal() {
		return argDefltVal;
	}

	/**
	 * @param argDefltVal the argDefltVal to set
	 */
	public void setArgDefltVal(String argDefltVal) {
		this.argDefltVal = argDefltVal;
	}

}