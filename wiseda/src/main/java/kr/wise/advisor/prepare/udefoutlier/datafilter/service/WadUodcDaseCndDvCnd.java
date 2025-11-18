package kr.wise.advisor.prepare.udefoutlier.datafilter.service;

import java.util.Date;

import kr.wise.advisor.prepare.udefoutlier.service.WadUod;

public class WadUodcDaseCndDvCnd extends WadUodcDaseCndDv{ 
    private String udfOtlDtcId;

    private String creCompId;

    private Integer cndSno;

    private String anaVarId;

    private String colPnm;

    private String cndCd;

    private String oprCd;

    private String cndVal;

   
    public String getUdfOtlDtcId() {
        return udfOtlDtcId;
    }

    public void setUdfOtlDtcId(String udfOtlDtcId) {
        this.udfOtlDtcId = udfOtlDtcId;
    }

    public String getCreCompId() {
        return creCompId;
    }

    public void setCreCompId(String creCompId) {
        this.creCompId = creCompId;
    }

    public Integer getCndSno() {
		return cndSno;
	}

	public void setCndSno(Integer cndSno) {
		this.cndSno = cndSno;
	}

	public String getAnaVarId() {
        return anaVarId;
    }

    public void setAnaVarId(String anaVarId) {
        this.anaVarId = anaVarId;
    }

    public String getColPnm() {
        return colPnm;
    }

    public void setColPnm(String colPnm) {
        this.colPnm = colPnm;
    }

    public String getCndCd() {
        return cndCd;
    }

    public void setCndCd(String cndCd) {
        this.cndCd = cndCd;
    }

    public String getOprCd() {
        return oprCd;
    }

    public void setOprCd(String oprCd) {
        this.oprCd = oprCd;
    }

    public String getCndVal() {
        return cndVal;
    }

    public void setCndVal(String cndVal) {
        this.cndVal = cndVal;
    }

   
}