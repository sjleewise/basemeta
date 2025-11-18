package kr.wise.meta.app.service;

import kr.wise.commons.cmm.CommonVo;

public class WamAppStwd extends CommonVo{
    private String appStwdId;

    private String appStwdLnm;

    private String appStwdPnm;

    private String engMean;

    private String bizDtlCd;

    /** 검색시작일 */
    private String searchBgnDe = "";

    /** 검색종료일 */
    private String searchEndDe = "";

    private String orgDs;
    
    private String wdDcd;

    public String getAppStwdId() {
        return appStwdId;
    }

    public void setAppStwdId(String appStwdId) {
        this.appStwdId = appStwdId;
    }

    public String getAppStwdLnm() {
        return appStwdLnm;
    }

    public void setAppStwdLnm(String appStwdLnm) {
        this.appStwdLnm = appStwdLnm;
    }

    public String getAppStwdPnm() {
        return appStwdPnm;
    }

    public void setAppStwdPnm(String appStwdPnm) {
        this.appStwdPnm = appStwdPnm;
    }

    public String getEngMean() {
        return engMean;
    }

    public void setEngMean(String engMean) {
        this.engMean = engMean;
    }

	public String getBizDtlCd() {
		return bizDtlCd;
	}

	public void setBizDtlCd(String bizDtlCd) {
		this.bizDtlCd = bizDtlCd;
	}

	public String getSearchBgnDe() {
		return searchBgnDe;
	}

	public void setSearchBgnDe(String searchBgnDe) {
		this.searchBgnDe = searchBgnDe;
	}

	public String getSearchEndDe() {
		return searchEndDe;
	}

	public void setSearchEndDe(String searchEndDe) {
		this.searchEndDe = searchEndDe;
	}

	public String getOrgDs() {
		return orgDs;
	}

	public void setOrgDs(String orgDs) {
		this.orgDs = orgDs;
	}

	public String getWdDcd() {
		return wdDcd;
	}

	public void setWdDcd(String wdDcd) {
		this.wdDcd = wdDcd;
	}

@Override
	public String toString() {
		return "WamAppStwd [appStwdId=" + appStwdId + ", appStwdLnm="
				+ appStwdLnm + ", appStwdPnm=" + appStwdPnm + ", engMean="
				+ engMean + ", bizDtlCd=" + bizDtlCd + ", searchBgnDe="
				+ searchBgnDe + ", searchEndDe=" + searchEndDe + "]";
	}	
	
}