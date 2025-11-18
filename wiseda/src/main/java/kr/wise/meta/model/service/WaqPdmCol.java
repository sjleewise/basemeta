package kr.wise.meta.model.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqPdmCol extends CommonVo {
    private String rqstNo;

    private Integer rqstSno;

    private Integer rqstDtlSno;

    private String pdmColId;

    private String pdmColPnm;

    private String pdmColLnm;

    private String bfPdmColPnm;

//    private String rqstDcd;
//
//    private String vrfCd;
//
//    private String vrfRmk;

    private String pdmTblId;

    private String pdmTblPnm;

    private String mdlLnm;

    private String uppSubjLnm;

    private String subjId;

    private String subjLnm;

    private String sditmId;

    private Integer colOrd;

    private String dataType;

    private Integer dataLen;

    private Integer dataScal;

    private String pkYn;

    private Integer pkOrd;

    private String nonulYn;

    private String defltVal;

    private String encYn;

    private String fkYn;
    
    private String akYn;
    
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
    
    //범정부연계항목추가
    private String constCnd;
    private String openYn;
    private String prsnInfoYn;
    private String priRsn;

    private String exerdPdmTblId; 
    
    public String getExerdPdmTblId() {
		return exerdPdmTblId;
	}

	public void setExerdPdmTblId(String exerdPdmTblId) {
		this.exerdPdmTblId = exerdPdmTblId;
	}

	public String getConstCnd() {
		return constCnd;
	}

	public void setConstCnd(String constCnd) {
		this.constCnd = constCnd;
	}

	public String getOpenYn() {
		return openYn;
	}

	public void setOpenYn(String openYn) {
		this.openYn = openYn;
	}

	public String getPrsnInfoYn() {
		return prsnInfoYn;
	}

	public void setPrsnInfoYn(String prsnInfoYn) {
		this.prsnInfoYn = prsnInfoYn;
	}
	
	public String getPriRsn() {
		return priRsn;
	}

	public void setPriRsn(String priRsn) {
		this.priRsn = priRsn;
	}

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

    public Integer getRqstDtlSno() {
        return rqstDtlSno;
    }

    public void setRqstDtlSno(Integer rqstDtlSno) {
        this.rqstDtlSno = rqstDtlSno;
    }

    public String getPdmColId() {
        return pdmColId;
    }

    public void setPdmColId(String pdmColId) {
        this.pdmColId = pdmColId;
    }

    public String getPdmColPnm() {
        return pdmColPnm;
    }

    public void setPdmColPnm(String pdmColPnm) {
        this.pdmColPnm = pdmColPnm;
    }

    public String getPdmColLnm() {
        return pdmColLnm;
    }

    public void setPdmColLnm(String pdmColLnm) {
        this.pdmColLnm = pdmColLnm;
    }

    public String getBfPdmColPnm() {
        return bfPdmColPnm;
    }

    public void setBfPdmColPnm(String bfPdmColPnm) {
        this.bfPdmColPnm = bfPdmColPnm;
    }

//    public String getRqstDcd() {
//        return rqstDcd;
//    }
//
//    public void setRqstDcd(String rqstDcd) {
//        this.rqstDcd = rqstDcd;
//    }
//
//    public String getVrfCd() {
//        return vrfCd;
//    }
//
//    public void setVrfCd(String vrfCd) {
//        this.vrfCd = vrfCd;
//    }
//
//    public String getVrfRmk() {
//        return vrfRmk;
//    }
//
//    public void setVrfRmk(String vrfRmk) {
//        this.vrfRmk = vrfRmk;
//    }

    public String getPdmTblId() {
        return pdmTblId;
    }

    public void setPdmTblId(String pdmTblId) {
        this.pdmTblId = pdmTblId;
    }

    public String getPdmTblPnm() {
        return pdmTblPnm;
    }

    public void setPdmTblPnm(String pdmTblPnm) {
        this.pdmTblPnm = pdmTblPnm;
    }

    public String getMdlLnm() {
        return mdlLnm;
    }

    public void setMdlLnm(String mdlLnm) {
        this.mdlLnm = mdlLnm;
    }

    public String getUppSubjLnm() {
        return uppSubjLnm;
    }

    public void setUppSubjLnm(String uppSubjLnm) {
        this.uppSubjLnm = uppSubjLnm;
    }

    public String getSubjId() {
        return subjId;
    }

    public void setSubjId(String subjId) {
        this.subjId = subjId;
    }

    public String getSubjLnm() {
        return subjLnm;
    }

    public void setSubjLnm(String subjLnm) {
        this.subjLnm = subjLnm;
    }

    public String getSditmId() {
        return sditmId;
    }

    public void setSditmId(String sditmId) {
        this.sditmId = sditmId;
    }

    public Integer getColOrd() {
        return colOrd;
    }

    public void setColOrd(Integer colOrd) {
        this.colOrd = colOrd;
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

    public String getPkYn() {
        return pkYn;
    }

    public void setPkYn(String pkYn) {
        this.pkYn = pkYn;
    }

    public Integer getPkOrd() {
        return pkOrd;
    }

    public void setPkOrd(Integer pkOrd) {
        this.pkOrd = pkOrd;
    }

    public String getNonulYn() {
        return nonulYn;
    }

    public void setNonulYn(String nonulYn) {
        this.nonulYn = nonulYn;
    }

    public String getDefltVal() {
        return defltVal;
    }

    public void setDefltVal(String defltVal) {
        this.defltVal = defltVal;
    }

	/** insomnia */
	

	public String getFkYn() {
		return fkYn;
	}

	public void setFkYn(String fkYn) {
		this.fkYn = fkYn;
	}

	public String getAkYn() {
		return akYn;
	}

	public void setAkYn(String akYn) {
		this.akYn = akYn;
	}

	/**
	 * @return the encYn
	 */
	public String getEncYn() {
		return encYn;
	}

	@Override
	public String toString() {
		return "WaqPdmCol [rqstNo=" + rqstNo + ", rqstSno=" + rqstSno
				+ ", rqstDtlSno=" + rqstDtlSno + ", pdmColId=" + pdmColId
				+ ", pdmColPnm=" + pdmColPnm + ", pdmColLnm=" + pdmColLnm
				+ ", bfPdmColPnm=" + bfPdmColPnm + ", pdmTblId=" + pdmTblId
				+ ", pdmTblPnm=" + pdmTblPnm + ", mdlLnm=" + mdlLnm
				+ ", uppSubjLnm=" + uppSubjLnm + ", subjId=" + subjId
				+ ", subjLnm=" + subjLnm + ", sditmId=" + sditmId + ", colOrd="
				+ colOrd + ", dataType=" + dataType + ", dataLen=" + dataLen
				+ ", dataScal=" + dataScal + ", pkYn=" + pkYn + ", pkOrd="
				+ pkOrd + ", nonulYn=" + nonulYn + ", defltVal=" + defltVal
				+ ", encYn=" + encYn + ", constCnd=" + constCnd + ", openYn="
				+ openYn + ", prsnInfoYn=" + prsnInfoYn + ", exerdPdmTblId="
				+ exerdPdmTblId + "]";
	}

	/**
	 * @param encYn the encYn to set
	 */
	public void setEncYn(String encYn) {
		this.encYn = encYn;
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
//
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