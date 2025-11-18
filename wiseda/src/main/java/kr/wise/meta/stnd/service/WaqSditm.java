package kr.wise.meta.stnd.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqSditm extends CommonVo{
//    private String rqstNo;
//
//    private Integer rqstSno;

    private String sditmId;

    private String sditmLnm;

    private String sditmPnm;

    private String lnmCriDs;
    
    private String pnmCriDs;

//    private String rqstDcd;
//
//    private String rvwStsCd;
//
//    private String rvwConts;
//
//    private String vrfRmk;
//
//    private String vrfCd;

    private String dmnId;

    private String dmnLnm;
    
    private String dmnPnm;

    private String dmngId;
    private String dmngLnm;

    private String infotpId;

    private String dataType;

    private Integer dataLen;

    private Integer dataScal;

    private String infotpLnm;

    private String infotpChgYn;

    private String encYn;
    
    private String uppDmngId;
    
    private String uppDmngLnm;

    private String testDataCnvYn;
    
    private String persInfoCnvYn;
    
    private String persInfoGrd;
    
    private String stndAsrt; //표준분류
    
    private String dupYn;
    

    private String persInfoYn;
    
  //=========== 범정부 연계 항목 ==============
    private String openYn; // 공개/비공개 여부
    
    private String priRsn; // 비공개 사유
    
    private String prsnInfoYn; // 개인정보여부
    
    private String constCnd; // 제약조건
    
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

//    public String getRqstNo() {
//        return rqstNo;
//    }
//
//    public void setRqstNo(String rqstNo) {
//        this.rqstNo = rqstNo;
//    }
//
//    public Integer getRqstSno() {
//        return rqstSno;
//    }
//
//    public void setRqstSno(Integer rqstSno) {
//        this.rqstSno = rqstSno;
//    }

    
    public String getDupYn() {
		return dupYn;
	}

	public String getPersInfoYn() {
		return persInfoYn;
	}

	public void setPersInfoYn(String persInfoYn) {
		this.persInfoYn = persInfoYn;
	}

	public void setDupYn(String dupYn) {
		this.dupYn = dupYn;
	}

	public String getPersInfoCnvYn() {
		return persInfoCnvYn;
	}

	public String getStndAsrt() {
		return stndAsrt;
	}

	public void setStndAsrt(String stndAsrt) {
		this.stndAsrt = stndAsrt;
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

//    public String getRqstDcd() {
//        return rqstDcd;
//    }
//
//    public void setRqstDcd(String rqstDcd) {
//        this.rqstDcd = rqstDcd;
//    }
//
//    public String getRvwStsCd() {
//        return rvwStsCd;
//    }
//
//    public void setRvwStsCd(String rvwStsCd) {
//        this.rvwStsCd = rvwStsCd;
//    }
//
//    public String getRvwConts() {
//        return rvwConts;
//    }
//
//    public void setRvwConts(String rvwConts) {
//        this.rvwConts = rvwConts;
//    }
//
//    public String getVrfRmk() {
//        return vrfRmk;
//    }
//
//    public void setVrfRmk(String vrfRmk) {
//        this.vrfRmk = vrfRmk;
//    }
//
//    public String getVrfCd() {
//        return vrfCd;
//    }
//
//    public void setVrfCd(String vrfCd) {
//        this.vrfCd = vrfCd;
//    }

    public String getPnmCriDs() {
		return pnmCriDs;
	}

	public void setPnmCriDs(String pnmCriDs) {
		this.pnmCriDs = pnmCriDs;
	}

	public String getDmnId() {
        return dmnId;
    }

    public void setDmnId(String dmnId) {
        this.dmnId = dmnId;
    }

    public String getDmnLnm() {
        return dmnLnm;
    }

    public void setDmnLnm(String dmnLnm) {
        this.dmnLnm = dmnLnm;
    }

    public String getDmnPnm() {
		return dmnPnm;
	}

	public void setDmnPnm(String dmnPnm) {
		this.dmnPnm = dmnPnm;
	}

	public String getInfotpId() {
        return infotpId;
    }

    public void setInfotpId(String infotpId) {
        this.infotpId = infotpId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getDataLen() {
        return dataLen;
    }

    public void setDataLen(Integer dataLen) {
        this.dataLen = dataLen;
    }

    public Integer getDataScal() {
        return dataScal;
    }

    public void setDataScal(Integer dataScal) {
        this.dataScal = dataScal;
    }

    public String getInfotpLnm() {
        return infotpLnm;
    }

    public void setInfotpLnm(String infotpLnm) {
        this.infotpLnm = infotpLnm;
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

	@Override
	public String toString() {
		return "WaqSditm [sditmId=" + sditmId + ", sditmLnm=" + sditmLnm
				+ ", sditmPnm=" + sditmPnm + ", lnmCriDs=" + lnmCriDs
				+ ", pnmCriDs=" + pnmCriDs + ", dmnId=" + dmnId + ", dmnLnm="
				+ dmnLnm + ", dmnPnm=" + dmnPnm + ", dmngId=" + dmngId
				+ ", dmngLnm=" + dmngLnm + ", infotpId=" + infotpId
				+ ", dataType=" + dataType + ", dataLen=" + dataLen
				+ ", dataScal=" + dataScal + ", infotpLnm=" + infotpLnm
				+ ", infotpChgYn=" + infotpChgYn + ", encYn=" + encYn
				+ ", uppDmngId=" + uppDmngId + ", uppDmngLnm=" + uppDmngLnm
				+ ", testDataCnvYn=" + testDataCnvYn + ", persInfoCnvYn=" + persInfoCnvYn + "]";
	}

	/**
	 * @return the dmngId
	 */
	public String getDmngId() {
		return dmngId;
	}

	/**
	 * @param dmngId the dmngId to set
	 */
	public void setDmngId(String dmngId) {
		this.dmngId = dmngId;
	}

	/**
	 * @return the dmngLnm
	 */
	public String getDmngLnm() {
		return dmngLnm;
	}

	/**
	 * @param dmngLnm the dmngLnm to set
	 */
	public void setDmngLnm(String dmngLnm) {
		this.dmngLnm = dmngLnm;
	}

//    public String getObjDescn() {
//        return objDescn;
//    }
//
//    public void setObjDescn(String objDescn) {
//        this.objDescn = objDescn;
//    }
//
//    public Integer getObjVers() {
//        return objVers;
//    }
//
//    public void setObjVers(Integer objVers) {
//        this.objVers = objVers;
//    }
//
//    public String getRegTypCd() {
//        return regTypCd;
//    }
//
//    public void setRegTypCd(String regTypCd) {
//        this.regTypCd = regTypCd;
//    }
//
//    public Date getFrsRqstDtm() {
//        return frsRqstDtm;
//    }
//
//    public void setFrsRqstDtm(Date frsRqstDtm) {
//        this.frsRqstDtm = frsRqstDtm;
//    }
//
//    public String getFrsRqstUserId() {
//        return frsRqstUserId;
//    }
//
//    public void setFrsRqstUserId(String frsRqstUserId) {
//        this.frsRqstUserId = frsRqstUserId;
//    }
//
//    public Date getRqstDtm() {
//        return rqstDtm;
//    }
//
//    public void setRqstDtm(Date rqstDtm) {
//        this.rqstDtm = rqstDtm;
//    }
//
//    public String getRqstUserId() {
//        return rqstUserId;
//    }

//    public void setRqstUserId(String rqstUserId) {
//        this.rqstUserId = rqstUserId;
//    }
//
//    public Date getAprvDtm() {
//        return aprvDtm;
//    }
//
//    public void setAprvDtm(Date aprvDtm) {
//        this.aprvDtm = aprvDtm;
//    }
//
//    public String getAprvUserId() {
//        return aprvUserId;
//    }
//
//    public void setAprvUserId(String aprvUserId) {
//        this.aprvUserId = aprvUserId;
//    }
}