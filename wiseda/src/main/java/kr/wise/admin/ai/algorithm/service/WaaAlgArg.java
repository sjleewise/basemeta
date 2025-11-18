package kr.wise.admin.ai.algorithm.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WaaAlgArg extends CommonVo {
    private String algId;

    private String algArgId;

    private String argLnm;

    private String argPnm;

    private String argDataType;

    private String argDefltVal;

    private String argVal;

    private String minVal;

    private String maxVal;

    private Integer dispOrd;

    private String useYn;


    public String getAlgId() {
        return algId;
    }

    public void setAlgId(String algId) {
        this.algId = algId;
    }

    public String getAlgArgId() {
        return algArgId;
    }

    public void setAlgArgId(String algArgId) {
        this.algArgId = algArgId;
    }

    public String getArgLnm() {
        return argLnm;
    }

    public void setArgLnm(String argLnm) {
        this.argLnm = argLnm;
    }

    public String getArgPnm() {
        return argPnm;
    }

    public void setArgPnm(String argPnm) {
        this.argPnm = argPnm;
    }

    public String getArgDataType() {
        return argDataType;
    }

    public void setArgDataType(String argDataType) {
        this.argDataType = argDataType;
    }

    public String getArgDefltVal() {
        return argDefltVal;
    }

    public void setArgDefltVal(String argDefltVal) {
        this.argDefltVal = argDefltVal;
    }

    public String getMinVal() {
        return minVal;
    }

    public void setMinVal(String minVal) {
        this.minVal = minVal;
    }

    public String getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(String maxVal) {
        this.maxVal = maxVal;
    }

    public Integer getDispOrd() {
        return dispOrd;
    }

    public void setDispOrd(Integer dispOrd) {
        this.dispOrd = dispOrd;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

	/**
	 * @return the argVal
	 */
	public String getArgVal() {
		return argVal;
	}

	/**
	 * @param argVal the argVal to set
	 */
	public void setArgVal(String argVal) {
		this.argVal = argVal;
	}

}