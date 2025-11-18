package kr.wise.advisor.prepare.outlier.service;

import kr.wise.commons.cmm.CommonVo;

public class WadOtlArg extends CommonVo {
    private String otlDtcId;

    private String algArgId;

    private String argVal;

    

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

}