package kr.wise.dq.stnd.service;

import kr.wise.commons.cmm.CommonVo;

public class WdqmDmn extends CommonVo{
    private String dmnId;

    private String dmnLnm;

    private String dmnPnm;

    private String dmngId;

    private String infotpId;

    private String uppDmnId;

    private String subjId;

    private String lstEntyId;

    private String lstEntyPnm;

    private String lstEntyLnm;
    
    private String lstAttrId;
    
    private String lstAttrPnm;
    
    private String lstAttrLnm;

    private String cdValTypCd;

    private String cdValIvwCd;

    private String sditmAutoCrtYn;

    private String dataFrm;

    private String crgUserId;

    private String dmnOrgDs;
    
    private String dmnOrgTxt;
    
    private String cdVal;
    
    private String cdValNm;
    
    private String dmnEngMean;
    
    private String oraDataType;

    private String msDataType;
    
    private String myDataType;

    private String dupYn;
    
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

	private String dataType;

    private Integer dataLen;

    private Integer dataScal;
    
    private String lnmCriDs;
    
    private String pnmCriDs;
    
    private String frsRqstUserNm;
    
    private String rqstUserNm;
    
    private String aprvUserNm;
    
    private String uppDmngId;
    
    private String dmngLnm;
    private String uppDmngLnm;
    private String infotpLnm;
    
    private String encYn;
    
    private String dmnDscd;
    
    private String transYn;
    
    private String dmnMinVal;
    private String dmnMaxVal;
    
    private String subCdYn;
    
    public String getTransYn() {
		return transYn;
	}

	public void setTransYn(String transYn) {
		this.transYn = transYn;
	}

	public String getDmnDscd() {
		return dmnDscd;
	}

	public void setDmnDscd(String dmnDscd) {
		this.dmnDscd = dmnDscd;
	}

	public String getEncYn() {
		return encYn;
	}

	public void setEncYn(String encYn) {
		this.encYn = encYn;
	}

	public String getDmnEngMean() {
    	return dmnEngMean;
    }
    
    public void setDmnEngMean(String dmnEngMean) {
    	this.dmnEngMean = dmnEngMean;
    }

    public String getFrsRqstUserNm() {
		return frsRqstUserNm;
	}

	public void setFrsRqstUserNm(String frsRqstUserNm) {
		this.frsRqstUserNm = frsRqstUserNm;
	}

	public String getRqstUserNm() {
		return rqstUserNm;
	}

	public void setRqstUserNm(String rqstUserNm) {
		this.rqstUserNm = rqstUserNm;
	}

	public String getAprvUserNm() {
		return aprvUserNm;
	}

	public void setAprvUserNm(String aprvUserNm) {
		this.aprvUserNm = aprvUserNm;
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

    public String getUppDmnId() {
        return uppDmnId;
    }

    public void setUppDmnId(String uppDmnId) {
        this.uppDmnId = uppDmnId;
    }

    public String getSubjId() {
        return subjId;
    }

    public void setSubjId(String subjId) {
        this.subjId = subjId;
    }

    public String getLstEntyId() {
        return lstEntyId;
    }

    public void setLstEntyId(String lstEntyId) {
        this.lstEntyId = lstEntyId;
    }

    public String getLstEntyPnm() {
        return lstEntyPnm;
    }

    public void setLstEntyPnm(String lstEntyPnm) {
        this.lstEntyPnm = lstEntyPnm;
    }

    public String getLstEntyLnm() {
        return lstEntyLnm;
    }

    public void setLstEntyLnm(String lstEntyLnm) {
        this.lstEntyLnm = lstEntyLnm;
    }

    public String getLstAttrId() {
		return lstAttrId;
	}

	public void setLstAttrId(String lstAttrId) {
		this.lstAttrId = lstAttrId;
	}

	public String getLstAttrPnm() {
		return lstAttrPnm;
	}

	public void setLstAttrPnm(String lstAttrPnm) {
		this.lstAttrPnm = lstAttrPnm;
	}

	public String getLstAttrLnm() {
		return lstAttrLnm;
	}

	public void setLstAttrLnm(String lstAttrLnm) {
		this.lstAttrLnm = lstAttrLnm;
	}

	public String getCdValTypCd() {
        return cdValTypCd;
    }

    public void setCdValTypCd(String cdValTypCd) {
        this.cdValTypCd = cdValTypCd;
    }

    public String getCdValIvwCd() {
        return cdValIvwCd;
    }

    public void setCdValIvwCd(String cdValIvwCd) {
        this.cdValIvwCd = cdValIvwCd;
    }

    public String getSditmAutoCrtYn() {
        return sditmAutoCrtYn;
    }

    public void setSditmAutoCrtYn(String sditmAutoCrtYn) {
        this.sditmAutoCrtYn = sditmAutoCrtYn;
    }

    public String getDataFrm() {
        return dataFrm;
    }

    public void setDataFrm(String dataFrm) {
        this.dataFrm = dataFrm;
    }

    public String getCrgUserId() {
        return crgUserId;
    }

    public void setCrgUserId(String crgUserId) {
        this.crgUserId = crgUserId;
    }

    public String getDmnOrgDs() {
        return dmnOrgDs;
    }

    public void setDmnOrgDs(String dmnOrgDs) {
        this.dmnOrgDs = dmnOrgDs;
    }

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the dataLen
	 */
	public Integer getDataLen() {
		return dataLen;
	}

	/**
	 * @param dataLen the dataLen to set
	 */
	public void setDataLen(Integer dataLen) {
		this.dataLen = dataLen;
	}

	/**
	 * @return the dataScal
	 */
	public Integer getDataScal() {
		return dataScal;
	}

	/**
	 * @param dataScal the dataScal to set
	 */
	public void setDataScal(Integer dataScal) {
		this.dataScal = dataScal;
	}

	public String getUppDmngId() {
		return uppDmngId;
	}

	public void setUppDmngId(String uppDmngId) {
		this.uppDmngId = uppDmngId;
	}

	public String getCdVal() {
		return cdVal;
	}

	public void setCdVal(String cdVal) {
		this.cdVal = cdVal;
	}

	public String getCdValNm() {
		return cdValNm;
	}

	public void setCdValNm(String cdValNm) {
		this.cdValNm = cdValNm;
	}

	public String getPnmCriDs() {
		return pnmCriDs;
	}

	public void setPnmCriDs(String pnmCriDs) {
		this.pnmCriDs = pnmCriDs;
	}

	public String getDmngLnm() {
		return dmngLnm;
	}

	public void setDmngLnm(String dmngLnm) {
		this.dmngLnm = dmngLnm;
	}

	public String getUppDmngLnm() {
		return uppDmngLnm;
	}

	public void setUppDmngLnm(String uppDmngLnm) {
		this.uppDmngLnm = uppDmngLnm;
	}

	public String getInfotpLnm() {
		return infotpLnm;
	}

	public void setInfotpLnm(String infotpLnm) {
		this.infotpLnm = infotpLnm;
	}

	public String getDmnMinVal() {
		return dmnMinVal;
	}

	public void setDmnMinVal(String dmnMinVal) {
		this.dmnMinVal = dmnMinVal;
	}

	public String getDmnMaxVal() {
		return dmnMaxVal;
	}

	public void setDmnMaxVal(String dmnMaxVal) {
		this.dmnMaxVal = dmnMaxVal;
	}
	
	public String getDmnOrgTxt() {
		return dmnOrgTxt;
	}

	public void setDmnOrgTxt(String dmnOrgTxt) {
		this.dmnOrgTxt = dmnOrgTxt;
	}

	public String getSubCdYn() {
		return subCdYn;
	}

	public void setSubCdYn(String subCdYn) {
		this.subCdYn = subCdYn;
	}

	@Override
	public String toString() {
		return "WamDmn [dmnId=" + dmnId + ", dmnLnm=" + dmnLnm + ", dmnPnm="
				+ dmnPnm + ", dmngId=" + dmngId + ", infotpId=" + infotpId
				+ ", uppDmnId=" + uppDmnId + ", subjId=" + subjId
				+ ", lstEntyId=" + lstEntyId + ", lstEntyPnm=" + lstEntyPnm
				+ ", lstEntyLnm=" + lstEntyLnm + ", lstAttrId=" + lstAttrId
				+ ", lstAttrPnm=" + lstAttrPnm + ", lstAttrLnm=" + lstAttrLnm
				+ ", cdValTypCd=" + cdValTypCd + ", cdValIvwCd=" + cdValIvwCd
				+ ", sditmAutoCrtYn=" + sditmAutoCrtYn + ", dataFrm=" + dataFrm
				+ ", crgUserId=" + crgUserId + ", dmnOrgDs=" + dmnOrgDs
				+ ", dmnOrgTxt=" + dmnOrgTxt + ", cdVal=" + cdVal
				+ ", cdValNm=" + cdValNm + ", dmnEngMean=" + dmnEngMean
				+ ", dataType=" + dataType + ", dataLen=" + dataLen
				+ ", dataScal=" + dataScal + ", lnmCriDs=" + lnmCriDs
				+ ", pnmCriDs=" + pnmCriDs + ", frsRqstUserNm=" + frsRqstUserNm
				+ ", rqstUserNm=" + rqstUserNm + ", aprvUserNm=" + aprvUserNm
				+ ", uppDmngId=" + uppDmngId + ", dmngLnm=" + dmngLnm
				+ ", uppDmngLnm=" + uppDmngLnm + ", infotpLnm=" + infotpLnm
				+ ", encYn=" + encYn + ", dmnDscd=" + dmnDscd + ", transYn="
				+ transYn + ", subCdYn=" + subCdYn + "]";
	}
}