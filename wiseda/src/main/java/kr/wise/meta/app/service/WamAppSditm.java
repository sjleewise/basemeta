package kr.wise.meta.app.service;

import kr.wise.commons.cmm.CommonVo;

public class WamAppSditm extends CommonVo{
    private String appSditmId;

    private String appSditmLnm;

    private String appSditmPnm;

    private String lnmCriDs;

    private String pnmCriDs;
    
    private String dataType;

    private String dataLen;
    
    private String dataScal;

    public String getAppSditmId() {
        return appSditmId;
    }

    public void setAppSditmId(String appSditmId) {
        this.appSditmId = appSditmId;
    }

    public String getAppSditmLnm() {
        return appSditmLnm;
    }

    public void setAppSditmLnm(String appSditmLnm) {
        this.appSditmLnm = appSditmLnm;
    }

    public String getAppSditmPnm() {
    	return appSditmPnm;
    }

    public void setAppSditmPnm(String appSditmPnm) {
        this.appSditmPnm = appSditmPnm;
    }

    public String getLnmCriDs() {
        return lnmCriDs;
    }

    public void setLnmCriDs(String lnmCriDs) {
        this.lnmCriDs = lnmCriDs;
    }

    public String getPnmCriDs() {
        return pnmCriDs;
    }

    public void setPnmCriDs(String pnmCriDs) {
        this.pnmCriDs = pnmCriDs;
    }

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataLen() {
		return dataLen;
	}

	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}

	public String getDataScal() {
		return dataScal;
	}

	public void setDataScal(String dataScal) {
		this.dataScal = dataScal;
	}

	@Override
	public String toString() {
		return "WamAppSditm [appSditmId=" + appSditmId + ", appSditmLnm="
				+ appSditmLnm + ", appSditmPnm=" + appSditmPnm + ", lnmCriDs="
				+ lnmCriDs + ", pnmCriDs=" + pnmCriDs + ", dataType="
				+ dataType + ", dataLen=" + dataLen + ", dataScal=" + dataScal
				+ "]";
	}
}