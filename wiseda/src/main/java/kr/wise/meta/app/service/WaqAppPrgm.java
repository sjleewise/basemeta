package kr.wise.meta.app.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqAppPrgm extends CommonVo{
    private String rqstNo;

    private Integer rqstSno;

    private String appPrgmId;

    private String appPrgmLnm;

    private String appPrgmPnm;

    private String appPrgmDcd;

    public String getRqstNo() {
        return rqstNo;
    }

    public void setRqstNo(String rqstNo) {
        this.rqstNo = rqstNo;
    }

    public Integer getRqstSno() {
        return rqstSno;
    }

    public void setRqstSno(Integer rqstSno) {
        this.rqstSno = rqstSno;
    }

    public String getAppPrgmId() {
        return appPrgmId;
    }

    public void setAppPrgmId(String appPrgmId) {
        this.appPrgmId = appPrgmId;
    }

    public String getAppPrgmLnm() {
        return appPrgmLnm;
    }

    public void setAppPrgmLnm(String appPrgmLnm) {
        this.appPrgmLnm = appPrgmLnm;
    }

    public String getAppPrgmPnm() {
        return appPrgmPnm;
    }

    public void setAppPrgmPnm(String appPrgmPnm) {
        this.appPrgmPnm = appPrgmPnm;
    }

    public String getAppPrgmDcd() {
        return appPrgmDcd;
    }

    public void setAppPrgmDcd(String appPrgmDcd) {
        this.appPrgmDcd = appPrgmDcd;
    }

	@Override
	public String toString() {
		return "WaqAppPrgm [rqstNo=" + rqstNo + ", rqstSno=" + rqstSno + ", appPrgmId=" + appPrgmId + ", appPrgmLnm="
				+ appPrgmLnm + ", appPrgmPnm=" + appPrgmPnm + ", appPrgmDcd=" + appPrgmDcd + "]";
	}
    
}