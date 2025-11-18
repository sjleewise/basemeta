package kr.wise.advisor.prepare.udefoutlier.daserowdv.service;

import java.util.Date;

import kr.wise.advisor.prepare.udefoutlier.service.WadUod;
import kr.wise.commons.cmm.CommonVo;

public class WadUodcDaseRowDv extends WadUod { 
    private String udfOtlDtcId; 

    private String creCompId;

    private String anaDaseId;

    private String sctStrVal;

    private String sctEndVal;

    private String rowSltTypCd;

    private String drtDsgVal;

    private String srcAnaDaseId;
    private String srcAnaDaseNm;
    
    private String rdmSmplTypCd;
    private String rdmSmplCnt;
    private String rdmSmplRate;
    private String rdmValFix;

   
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

    public String getAnaDaseId() {
        return anaDaseId;
    }

    public void setAnaDaseId(String anaDaseId) {
        this.anaDaseId = anaDaseId;
    }

    public String getSctStrVal() {
        return sctStrVal;
    }

    public void setSctStrVal(String sctStrVal) {
        this.sctStrVal = sctStrVal;
    }

    public String getSctEndVal() {
        return sctEndVal;
    }

    public void setSctEndVal(String sctEndVal) {
        this.sctEndVal = sctEndVal;
    }

    public String getRowSltTypCd() {
        return rowSltTypCd;
    }

    public void setRowSltTypCd(String rowSltTypCd) {
        this.rowSltTypCd = rowSltTypCd;
    }

    public String getDrtDsgVal() {
        return drtDsgVal;
    }

    public void setDrtDsgVal(String drtDsgVal) {
        this.drtDsgVal = drtDsgVal;
    }

    public String getSrcAnaDaseId() {
        return srcAnaDaseId;
    }

    public void setSrcAnaDaseId(String srcAnaDaseId) {
        this.srcAnaDaseId = srcAnaDaseId;
    }

	public String getRdmSmplCnt() {
		return rdmSmplCnt;
	}

	public void setRdmSmplCnt(String rdmSmplCnt) {
		this.rdmSmplCnt = rdmSmplCnt;
	}

	public String getRdmSmplRate() {
		return rdmSmplRate;
	}

	public void setRdmSmplRate(String rdmSmplRate) {
		this.rdmSmplRate = rdmSmplRate;
	}

	public String getRdmValFix() {
		return rdmValFix;
	}

	public void setRdmValFix(String rdmValFix) {
		this.rdmValFix = rdmValFix;
	}
 
	public String getRdmSmplTypCd() {
		return rdmSmplTypCd;
	}

	public void setRdmSmplTypCd(String rdmSmplTypCd) {
		this.rdmSmplTypCd = rdmSmplTypCd;
	}

	public String getSrcAnaDaseNm() {
		return srcAnaDaseNm;
	}

	public void setSrcAnaDaseNm(String srcAnaDaseNm) { 
		this.srcAnaDaseNm = srcAnaDaseNm;
	}
        
	 
    
}