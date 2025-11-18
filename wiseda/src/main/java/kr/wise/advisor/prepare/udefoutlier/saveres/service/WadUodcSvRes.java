package kr.wise.advisor.prepare.udefoutlier.saveres.service;

import java.util.Date;

import kr.wise.advisor.prepare.udefoutlier.service.WadUod;

public class WadUodcSvRes extends WadUod {
    private String udfOtlDtcId;

    private String creCompId;

    private String srcAnaDaseId;
    
    private String anaDaseId;

    private String lrnMdlId;

    private String svResTypCd;
    
    private String anaDaseNm;

  
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

    public String getLrnMdlId() {
        return lrnMdlId;
    }

    public void setLrnMdlId(String lrnMdlId) {
        this.lrnMdlId = lrnMdlId;
    }

    public String getSvResTypCd() {
        return svResTypCd;
    }

    public void setSvResTypCd(String svResTypCd) {
        this.svResTypCd = svResTypCd;
    }

	public String getSrcAnaDaseId() {
		return srcAnaDaseId;
	}

	public void setSrcAnaDaseId(String srcAnaDaseId) {
		this.srcAnaDaseId = srcAnaDaseId;
	}

	public String getAnaDaseNm() {
		return anaDaseNm;
	}

	public void setAnaDaseNm(String anaDaseNm) {
		this.anaDaseNm = anaDaseNm;
	}
	
	

    
}