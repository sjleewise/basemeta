package kr.wise.meta.stnd.service;

import kr.wise.commons.cmm.CommonVo;

public class WamSditm extends CommonVo {
    private String sditmId;

    private String sditmLnm;

    private String sditmPnm;
    
    private String notsditmLnm;
    
    private String notsditmPnm;

    private String lnmCriDs;

    private String pnmCriDs;

	private String dmnId;

    private String dmngId;
    
    private String dmngLnm;

    private String infotpId;
    
    private String infotpLnm;

    private String infotpChgYn;

    private String encYn;

    private String stndInfoYn;
    
    
//    private String rqstNo;
//
//    private Integer rqstSno;
//
//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date frsRqstDtm;
//
//    private String frsRqstUserId;
//
//    private Date rqstDtm;
//
//    private String rqstUserId;
//
//    private Date aprvDtm;
//
//    private String aprvUserId;
    
    private String dataLen;
    
    private String dataScal;
    
    private String dataType;
    
    private String dmnPnm;
    
    private String dmnLnm;
    
//    private String frsRqstUserNm;
//    
//    private String rqstUserNm;
//    
//    private String aprvUserNm;
    
    private String uppDmngId;
    
    private String uppDmngLnm;
    
    
    private String fullEngMean;
    
    private String transYn;
    
    private String reqStr;
    
    private String testDataCnvYn;

    private String persInfoCnvYn;
    
    private String persInfoGrd;
    
    private String oraDataType;

    private String msDataType;
    
    private String myDataType;
    
    private String dupYn;
    
    private String persInfoYn;
    
    private String sditmPnmCamel;
    //=========== 범정부 연계 항목 ==============
    private String openYn; // 공개/비공개 여부
    
    private String priRsn; // 비공개 사유
    
    private String prsnInfoYn; // 개인정보여부
    
    private String constCnd; // 제약조건

	public String getPersInfoYn() {
		return persInfoYn;
	}

	public void setPersInfoYn(String persInfoYn) {
		this.persInfoYn = persInfoYn;
	}

	public String getDupYn() {
		return dupYn;
	}

	public void setDupYn(String dupYn) {
		this.dupYn = dupYn;
	}

	public String getOraDataType() {
		return oraDataType;
	}

	public void setOraDataType(String oraDataType) {
		this.oraDataType = oraDataType;
	}

	public String getMsDataType() {
		return msDataType;
	}

	public void setMsDataType(String msDataType) {
		this.msDataType = msDataType;
	}

	public String getMyDataType() {
		return myDataType;
	}

	public void setMyDataType(String myDataType) {
		this.myDataType = myDataType;
	}

	public String getPersInfoCnvYn() {
		return persInfoCnvYn;
	}

	public void setPersInfoCnvYn(String persInfoCnvYn) {
		this.persInfoCnvYn = persInfoCnvYn;
	}

	public String getPersInfoGrd() {
		return persInfoGrd;
	}

	public void setPersInfoGrd(String persInfoGrd) {
		this.persInfoGrd = persInfoGrd;
	}

	public String getTransYn() {
		return transYn;
	}

	public void setTransYn(String transYn) {
		this.transYn = transYn;
	}

	public String getPnmCriDs() {
		return pnmCriDs;
	}

	public void setPnmCriDs(String pnmCriDs) {
		this.pnmCriDs = pnmCriDs;
	}
  
    
//    public String getFrsRqstUserNm() {
//		return frsRqstUserNm;
//	}
//
//	public void setFrsRqstUserNm(String frsRqstUserNm) {
//		this.frsRqstUserNm = frsRqstUserNm;
//	}
//
//	public String getRqstUserNm() {
//		return rqstUserNm;
//	}
//
//	public void setRqstUserNm(String rqstUserNm) {
//		this.rqstUserNm = rqstUserNm;
//	}
//
//	public String getAprvUserNm() {
//		return aprvUserNm;
//	}

//	public void setAprvUserNm(String aprvUserNm) {
//		this.aprvUserNm = aprvUserNm;
//	}

	public String getDataLen() {
		return dataLen;
	}

	public String getUppDmngId() {
		return uppDmngId;
	}

	public void setUppDmngId(String uppDmngId) {
		this.uppDmngId = uppDmngId;
	}

	public String getUppDmngLnm() {
		return uppDmngLnm;
	}

	public void setUppDmngLnm(String uppDmngLnm) {
		this.uppDmngLnm = uppDmngLnm;
	}

	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDmnPnm() {
		return dmnPnm;
	}

	public void setDmnPnm(String dmnPnm) {
		this.dmnPnm = dmnPnm;
	}

	public String getDmnLnm() {
		return dmnLnm;
	}

	public void setDmnLnm(String dmnLnm) {
		this.dmnLnm = dmnLnm;
	}

	public String getSditmId() {
        return sditmId;
    }

    public void setSditmId(String sditmId) {
        this.sditmId = sditmId;
    }

    public String getSditmLnm() {
        return sditmLnm;
    }

    public void setSditmLnm(String sditmLnm) {
        this.sditmLnm = sditmLnm;
    }

    public String getSditmPnm() {
        return sditmPnm;
    }

    public void setSditmPnm(String sditmPnm) {
        this.sditmPnm = sditmPnm;
    }

    public String getLnmCriDs() {
        return lnmCriDs;
    }

    public void setLnmCriDs(String lnmCriDs) {
        this.lnmCriDs = lnmCriDs;
    }

    public String getDmnId() {
        return dmnId;
    }

    public void setDmnId(String dmnId) {
        this.dmnId = dmnId;
    }

    public String getDmngId() {
        return dmngId;
    }

    public void setDmngId(String dmngId) {
        this.dmngId = dmngId;
    }

    public String getInfotpId() {
        return infotpId;
    }

    public void setInfotpId(String infotpId) {
        this.infotpId = infotpId;
    }

    public String getInfotpChgYn() {
        return infotpChgYn;
    }

    public void setInfotpChgYn(String infotpChgYn) {
        this.infotpChgYn = infotpChgYn;
    }

    public String getEncYn() {
        return encYn;
    }

    public void setEncYn(String encYn) {
        this.encYn = encYn;
    }

	public String getDataScal() {
		return dataScal;
	}

	public void setDataScal(String dataScal) {
		this.dataScal = dataScal;
	}

	public String getFullEngMean() {
		return fullEngMean;
	}

	public void setFullEngMean(String fullEngMean) {
		this.fullEngMean = fullEngMean;
	}

	public String getDmngLnm() {
		return dmngLnm;
	}

	public void setDmngLnm(String dmngLnm) {
		this.dmngLnm = dmngLnm;
	}

	public String getInfotpLnm() {
		return infotpLnm;
	}

	public void setInfotpLnm(String infotpLnm) {
		this.infotpLnm = infotpLnm;
	}
	
	
	public String getReqStr() {
		return reqStr;
	}

	public void setReqStr(String reqStr) {
		this.reqStr = reqStr;
	}

	public String getNotsditmLnm() {
		return notsditmLnm;
	}

	public void setNotsditmLnm(String notsditmLnm) {
		this.notsditmLnm = notsditmLnm;
	}

	public String getNotsditmPnm() {
		return notsditmPnm;
	}

	public void setNotsditmPnm(String notsditmPnm) {
		this.notsditmPnm = notsditmPnm;
	}

	public String getStndInfoYn() {
		return stndInfoYn;
	}

	public void setStndInfoYn(String stndInfoYn) {
		this.stndInfoYn = stndInfoYn;
	}

	public String getTestDataCnvYn() {
		return testDataCnvYn;
	}

	public void setTestDataCnvYn(String testDataCnvYn) {
		this.testDataCnvYn = testDataCnvYn;
	}
	
    public String getOpenYn() {
		return openYn;
	}

	public void setOpenYn(String openYn) {
		this.openYn = openYn;
	}

	public String getPriRsn() {
		return priRsn;
	}
	
	public void setPriRsn(String priRsn) {
		this.priRsn = priRsn;
	}

	public String getConstCnd() {
		return constCnd;
	}

	public void setConstCnd(String constCnd) {
		this.constCnd = constCnd;
	}
	
	public String getPrsnInfoYn() {
		return prsnInfoYn;
	}

	public void setPrsnInfoYn(String prsnInfoYn) {
		this.prsnInfoYn = prsnInfoYn;
	}

	public String getSditmPnmCamel() {
		return sditmPnmCamel;
	}

	public void setSditmPnmCamel(String sditmPnmCamel) {
		this.sditmPnmCamel = sditmPnmCamel;
	}

	@Override
	public String toString() {
		return "WamSditm [sditmId=" + sditmId + ", sditmLnm=" + sditmLnm
				+ ", sditmPnm=" + sditmPnm + ", notsditmLnm=" + notsditmLnm
				+ ", notsditmPnm=" + notsditmPnm + ", lnmCriDs=" + lnmCriDs
				+ ", pnmCriDs=" + pnmCriDs + ", dmnId=" + dmnId + ", dmngId="
				+ dmngId + ", dmngLnm=" + dmngLnm + ", infotpId=" + infotpId
				+ ", infotpLnm=" + infotpLnm + ", infotpChgYn=" + infotpChgYn
				+ ", encYn=" + encYn + ", stndInfoYn=" + stndInfoYn
				+ ", dataLen=" + dataLen + ", dataScal=" + dataScal
				+ ", dataType=" + dataType + ", dmnPnm=" + dmnPnm + ", dmnLnm="
				+ dmnLnm + ", uppDmngId=" + uppDmngId + ", uppDmngLnm="
				+ uppDmngLnm + ", fullEngMean=" + fullEngMean + ", transYn="
				+ transYn + ", reqStr=" + reqStr + ", testDataCnvYn="
				+ testDataCnvYn + ", persInfoCnvYn=" + persInfoCnvYn + "]";
	}

    
}