package kr.wise.advisor.prepare.udefoutlier.datafilter.service;

import java.util.Date;

import kr.wise.advisor.prepare.udefoutlier.service.WadUod;
import kr.wise.commons.cmm.CommonVo;

public class WadUodcDaseCndDv extends WadUod {  
    private String udfOtlDtcId;

    private String creCompId;

    private String cndConts;

    private String anaDaseId;

    private String srcAnaDaseId;

    private String srcAnaDaseNm;
   

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

    public String getCndConts() {
        return cndConts;
    }

    public void setCndConts(String cndConts) {
        this.cndConts = cndConts;
    }

    public String getAnaDaseId() {
        return anaDaseId;
    }

    public void setAnaDaseId(String anaDaseId) {
        this.anaDaseId = anaDaseId;
    }

    public String getSrcAnaDaseId() {
        return srcAnaDaseId;
    }

    public void setSrcAnaDaseId(String srcAnaDaseId) {
        this.srcAnaDaseId = srcAnaDaseId;
    }
    
	public String getSrcAnaDaseNm() {
		return srcAnaDaseNm;
	}

	public void setSrcAnaDaseNm(String srcAnaDaseNm) {
		this.srcAnaDaseNm = srcAnaDaseNm;
	}

    
    
   
}