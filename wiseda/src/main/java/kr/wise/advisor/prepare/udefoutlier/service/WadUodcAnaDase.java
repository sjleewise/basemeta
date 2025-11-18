package kr.wise.advisor.prepare.udefoutlier.service;

import java.util.Date;

import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImp;
import kr.wise.commons.cmm.CommonVo;

public class WadUodcAnaDase extends CommonVo {
    private String anaDaseId;

    private String udfOtlDtcId;

    private String creCompId;

    private String anaDaseNm;

    private String srcAnaDaseId;

    public String getAnaDaseId() {
        return anaDaseId;
    }

    public void setAnaDaseId(String anaDaseId) {
        this.anaDaseId = anaDaseId;
    }

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

    public String getAnaDaseNm() {
        return anaDaseNm;
    }

    public void setAnaDaseNm(String anaDaseNm) {
        this.anaDaseNm = anaDaseNm;
    }
    
	public String getSrcAnaDaseId() {
		return srcAnaDaseId;
	}

	public void setSrcAnaDaseId(String srcAnaDaseId) {
		this.srcAnaDaseId = srcAnaDaseId;
	}


    
   
}