package kr.wise.advisor.prepare.udefoutlier.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WadUodCreComp extends CommonVo {
    private String udfOtlDtcId;

    private String creCompId;

    private Integer compOrd;

    private String creCompNm;

    private String compId;
    
    private String compDcd;

   
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

    public Integer getCompOrd() {
        return compOrd;
    }

    public void setCompOrd(Integer compOrd) {
        this.compOrd = compOrd;
    }

    public String getCreCompNm() {
        return creCompNm;
    }

    public void setCreCompNm(String creCompNm) {
        this.creCompNm = creCompNm;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

	public String getCompDcd() {
		return compDcd;
	}

	public void setCompDcd(String compDcd) {
		this.compDcd = compDcd;
	}

    
   
}