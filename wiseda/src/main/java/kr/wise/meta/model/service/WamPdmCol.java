package kr.wise.meta.model.service;

import kr.wise.commons.cmm.CommonVo;

public class WamPdmCol extends CommonVo{
    private String pdmColId;

    private String pdmColPnm;

    private String pdmColLnm;

    private String pdmTblId;

    private String sditmId;

    private Integer colOrd;

    private String dataType;

    private Integer dataLen;

    private Integer dataScal;

    private String pkYn;

    private Integer pkOrd;
    
    private String fkYn;

    private String akYn;

    private String nonulYn;

    private String defltVal;

    private String encYn; //암호화 여부
    
//    private String rqstNo;
////
//    private Integer rqstSno;
//
//    private Integer rqstDtlSno;
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
//
//    private Date strDtm;
//
//    private Date expDtm;

    private String pdmTblLnm;

    private String pdmTblPnm;

    private String sditmLnm;

    private String sditmPnm;

//    private String frsRqstUserNm;
//
//    private String rqstUserNm;
//
//    private String aprvUserNm;

    private String subjId;

    private String subjLnm;

    private String subjPnm;

    private String dmnId;

    private String dmnLnm;

    private String dmnPnm;

    private String infotpId;

    private String infotpLnm;

    //특정 컬럼명에 컬럼정보만 찾기 위해.
    private String tgtColId;

    //주제영역 풀패스
    private String fullPath;
    
    //표준적용여부
    private String stdAplYn;
    
    private String sditmDataType;

    private Integer sditmDataLen;

    private Integer sditmDataScal;
    
   private String errDataScaleYn ;
   private String errDataLengthYn;
   private String errDataTypeYn   ;
   private String errColPnmYn  ;
   private String errItemIdYn   ;  
   private String itemStatus     ;      
   
   //asis용
   private String systemNm;         
   private String dbmsTypCd;         
   private String dbmsNm;   
   
   //범정부연계항목추가
   private String constCnd;
   private String openYn;
   private String prsnInfoYn;
   private String priRsn;
   
   //as-is컬럼 to-be 비교 메뉴에서 유형 검색조건(매핑/비매핑)
   private String selectType;
   
   
   
   
public String getSelectType() {
	return selectType;
}

public void setSelectType(String selectType) {
	this.selectType = selectType;
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

public String getErrDataScaleYn() {
	return errDataScaleYn;
}

public void setErrDataScaleYn(String errDataScaleYn) {
	this.errDataScaleYn = errDataScaleYn;
}

public String getErrDataLengthYn() {
	return errDataLengthYn;
}

public void setErrDataLengthYn(String errDataLengthYn) {
	this.errDataLengthYn = errDataLengthYn;
}

public String getErrDataTypeYn() {
	return errDataTypeYn;
}

public void setErrDataTypeYn(String errDataTypeYn) {
	this.errDataTypeYn = errDataTypeYn;
}

public String getErrColPnmYn() {
	return errColPnmYn;
}

public void setErrColPnmYn(String errColPnmYn) {
	this.errColPnmYn = errColPnmYn;
}

public String getErrItemIdYn() {
	return errItemIdYn;
}

public void setErrItemIdYn(String errItemIdYn) {
	this.errItemIdYn = errItemIdYn;
}

public String getItemStatus() {
	return itemStatus;
}

public void setItemStatus(String itemStatus) {
	this.itemStatus = itemStatus;
}

	public String getSditmDataType() {
		return sditmDataType;
	}

	public void setSditmDataType(String sditmDataType) {
		this.sditmDataType = sditmDataType;
	}

	public Integer getSditmDataLen() {
		return sditmDataLen;
	}

	public void setSditmDataLen(Integer sditmDataLen) {
		this.sditmDataLen = sditmDataLen;
	}

	public Integer getSditmDataScal() {
		return sditmDataScal;
	}

	public void setSditmDataScal(Integer sditmDataScal) {
		this.sditmDataScal = sditmDataScal;
	}

	public String getStdAplYn() {
		return stdAplYn;
	}

	public void setStdAplYn(String stdAplYn) {
		this.stdAplYn = stdAplYn;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getTgtColId() {
		return tgtColId;
	}

	public void setTgtColId(String tgtColId) {
		this.tgtColId = tgtColId;
	}

	public String getSditmPnm() {
		return sditmPnm;
	}

	public void setSditmPnm(String sditmPnm) {
		this.sditmPnm = sditmPnm;
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

	public String getInfotpLnm() {
		return infotpLnm;
	}

	public void setInfotpLnm(String infotpLnm) {
		this.infotpLnm = infotpLnm;
	}

	public String getSubjLnm() {
		return subjLnm;
	}

	public void setSubjLnm(String subjLnm) {
		this.subjLnm = subjLnm;
	}

	public String getSubjPnm() {
		return subjPnm;
	}

	public void setSubjPnm(String subjPnm) {
		this.subjPnm = subjPnm;
	}

	public String getSubjId() {
		return subjId;
	}

	public void setSubjId(String subjId) {
		this.subjId = subjId;
	}

	public String getPdmTblPnm() {
		return pdmTblPnm;
	}

	public void setPdmTblPnm(String pdmTblPnm) {
		this.pdmTblPnm = pdmTblPnm;
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

    public String getPdmTblId() {
        return pdmTblId;
    }

    public void setPdmTblId(String pdmTblId) {
        this.pdmTblId = pdmTblId;
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


//    public Integer getRqstDtlSno() {
//        return rqstDtlSno;
//    }
//
//    public void setRqstDtlSno(Integer rqstDtlSno) {
//        this.rqstDtlSno = rqstDtlSno;
//    }
//
//    @Override
//	public String getObjDescn() {
//        return objDescn;
//    }
//
//    @Override
//	public void setObjDescn(String objDescn) {
//        this.objDescn = objDescn;
//    }
//
//    @Override
//	public Integer getObjVers() {
//        return objVers;
//    }
//
//    @Override
//	public void setObjVers(Integer objVers) {
//        this.objVers = objVers;
//    }
//
//    @Override
//	public String getRegTypCd() {
//        return regTypCd;
//    }
//
//    @Override
//	public void setRegTypCd(String regTypCd) {
//        this.regTypCd = regTypCd;
//    }
//
//    @Override
//	public Date getFrsRqstDtm() {
//        return frsRqstDtm;
//    }
//
//    @Override
//	public void setFrsRqstDtm(Date frsRqstDtm) {
//        this.frsRqstDtm = frsRqstDtm;
//    }
//
//    @Override
//	public String getFrsRqstUserId() {
//        return frsRqstUserId;
//    }
//
//    @Override
//	public void setFrsRqstUserId(String frsRqstUserId) {
//        this.frsRqstUserId = frsRqstUserId;
//    }
//
//    @Override
//	public Date getRqstDtm() {
//        return rqstDtm;
//    }
//
//    @Override
//	public void setRqstDtm(Date rqstDtm) {
//        this.rqstDtm = rqstDtm;
//    }
//
//    @Override
//	public String getRqstUserId() {
//        return rqstUserId;
//    }
//
//    @Override
//	public void setRqstUserId(String rqstUserId) {
//        this.rqstUserId = rqstUserId;
//    }
//
//    @Override
//	public Date getAprvDtm() {
//        return aprvDtm;
//    }
//
//    @Override
//	public void setAprvDtm(Date aprvDtm) {
//        this.aprvDtm = aprvDtm;
//    }
//
//    @Override
//	public String getAprvUserId() {
//        return aprvUserId;
//    }
//
//    @Override
//	public void setAprvUserId(String aprvUserId) {
//        this.aprvUserId = aprvUserId;
//    }
//
//	@Override
//	public String getRqstNo() {
//		return rqstNo;
//	}
//
//	@Override
//	public void setRqstNo(String rqstNo) {
//		this.rqstNo = rqstNo;
//	}
//
//	@Override
//	public Integer getRqstSno() {
//		return rqstSno;
//	}
//
//	@Override
//	public void setRqstSno(Integer rqstSno) {
//		this.rqstSno = rqstSno;
//	}
//
//	@Override
//	public Date getStrDtm() {
//		return strDtm;
//	}
//
//	@Override
//	public void setStrDtm(Date strDtm) {
//		this.strDtm = strDtm;
//	}
//
//	@Override
//	public Date getExpDtm() {
//		return expDtm;
//	}
//
//	@Override
//	public void setExpDtm(Date expDtm) {
//		this.expDtm = expDtm;
//	}

	public String getPdmTblLnm() {
		return pdmTblLnm;
	}

	public void setPdmTblLnm(String pdmTblLnm) {
		this.pdmTblLnm = pdmTblLnm;
	}

	public String getSditmLnm() {
		return sditmLnm;
	}

	public void setSditmLnm(String sditmLnm) {
		this.sditmLnm = sditmLnm;
	}

//	@Override
//	public String getFrsRqstUserNm() {
//		return frsRqstUserNm;
//	}
//
//	@Override
//	public void setFrsRqstUserNm(String frsRqstUserNm) {
//		this.frsRqstUserNm = frsRqstUserNm;
//	}
//
//	@Override
//	public String getRqstUserNm() {
//		return rqstUserNm;
//	}
//
//	@Override
//	public void setRqstUserNm(String rqstUserNm) {
//		this.rqstUserNm = rqstUserNm;
//	}
//
//	@Override
//	public String getAprvUserNm() {
//		return aprvUserNm;
//	}
//
//	@Override
//	public void setAprvUserNm(String aprvUserNm) {
//		this.aprvUserNm = aprvUserNm;
//	}

	@Override
	public String toString() {
		return "WamPdmCol [pdmColId=" + pdmColId + ", pdmColPnm=" + pdmColPnm
				+ ", pdmColLnm=" + pdmColLnm + ", pdmTblId=" + pdmTblId
				+ ", sditmId=" + sditmId + ", colOrd=" + colOrd + ", dataType="
				+ dataType + ", dataLen=" + dataLen + ", dataScal=" + dataScal
				+ ", pkYn=" + pkYn + ", pkOrd=" + pkOrd + ", nonulYn="
				+ nonulYn + ", defltVal=" + defltVal + ", encYn=" + encYn
				+ ", pdmTblLnm=" + pdmTblLnm + ", pdmTblPnm=" + pdmTblPnm
				+ ", sditmLnm=" + sditmLnm + ", sditmPnm=" + sditmPnm
				+ ", subjId=" + subjId + ", subjLnm=" + subjLnm + ", subjPnm="
				+ subjPnm + ", dmnId=" + dmnId + ", dmnLnm=" + dmnLnm
				+ ", dmnPnm=" + dmnPnm + ", infotpId=" + infotpId
				+ ", infotpLnm=" + infotpLnm + ", tgtColId=" + tgtColId
				+ ", fullPath=" + fullPath + ", stdAplYn=" + stdAplYn
				+ ", sditmDataType=" + sditmDataType + ", sditmDataLen="
				+ sditmDataLen + ", sditmDataScal=" + sditmDataScal
				+ ", errDataScaleYn=" + errDataScaleYn + ", errDataLengthYn="
				+ errDataLengthYn + ", errDataTypeYn=" + errDataTypeYn
				+ ", errColPnmYn=" + errColPnmYn + ", errItemIdYn="
				+ errItemIdYn + ", itemStatus=" + itemStatus + ", systemNm="
				+ systemNm + ", dbmsTypCd=" + dbmsTypCd + ", dbmsNm=" + dbmsNm
				+ ", constCnd=" + constCnd + ", openYn=" + openYn
				+ ", prsnInfoYn=" + prsnInfoYn + "]";
	}

	public String getSystemNm() {
		return systemNm;
	}

	public void setSystemNm(String systemNm) {
		this.systemNm = systemNm;
	}

	public String getDbmsTypCd() {
		return dbmsTypCd;
	}

	public void setDbmsTypCd(String dbmsTypCd) {
		this.dbmsTypCd = dbmsTypCd;
	}

	public String getDbmsNm() {
		return dbmsNm;
	}

	public void setDbmsNm(String dbmsNm) {
		this.dbmsNm = dbmsNm;
	}

	/**
	 * @return the encYn
	 */
	public String getEncYn() {
		return encYn;
	}

	/**
	 * @param encYn the encYn to set
	 */
	public void setEncYn(String encYn) {
		this.encYn = encYn;
	}
}