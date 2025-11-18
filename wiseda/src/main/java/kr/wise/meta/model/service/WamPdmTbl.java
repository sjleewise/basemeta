package kr.wise.meta.model.service;

import kr.wise.commons.cmm.CommonVo;

public class WamPdmTbl extends CommonVo {
    private String pdmTblId;

    private String pdmTblPnm;

    private String pdmTblLnm;

    private String subjId;
    private String subjLnm;		//주제영역논리명

    private String stdAplYn;

    private String partTblYn;

    private String dwTrgTblYn;

    private String crgUserId;

    
    private String ctdFcy;

    private String delCri;

    private String delMtd;

    private String bckFcy;

    private String sysAreaId;

    private String mdlLnm; //모델명

    private String uppSubjLnm; //상위주제영역명

    private String searchBgnDe; //조회기간 시작일

    private String searchEndDe; //조회기간 종료일

    private String subjLvl; //주제영역 레벨

    private String bfPdmTblPnm; //이전 물리모델 테이블 물리명

    private String pdmColPnm; //컬럼명(물리명)

    private String pdmColLnm; //컬럼명(논리명)

    private String dataType;//데이터타입

    private String crgUserNm; //담당자

    private String encYn; //암호화 컬럼 포함여부....
    
    private String colCnt;
    
    private String userRqstCntn;
    
    //asis용
    private String systemNm;         
    private String dbmsTypCd;         
    private String dbmsNm; 
    private String convTrgtYn; 
    private String nswtTrgtRsn; 
    private String tblMapRgstYn; 
    private String colMapRgstYn; 
    
    //범정부연계항목
    private String subjNm;
    private String prsvTerm;
    private String tblVol;
    private String tblTypNm;
    private String openRsnCd;
    private String nopenRsn;
    private String nopenDtlRelBss;
    private String openDataLst;
    private String occrCyl;
    private String dqDgnsYn;

    public String getSubjNm() {
		return subjNm;
	}

	public void setSubjNm(String subjNm) {
		this.subjNm = subjNm;
	}

	public String getPrsvTerm() {
		return prsvTerm;
	}

	public void setPrsvTerm(String prsvTerm) {
		this.prsvTerm = prsvTerm;
	}

	public String getTblVol() {
		return tblVol;
	}

	public void setTblVol(String tblVol) {
		this.tblVol = tblVol;
	}
	
	public String getTblTypNm() {
		return tblTypNm;
	}

	public void setTblTypNm(String tblTypNm) {
		this.tblTypNm = tblTypNm;
	}

	public String getOpenRsnCd() {
		return openRsnCd;
	}

	public void setOpenRsnCd(String openRsnCd) {
		this.openRsnCd = openRsnCd;
	}

	public String getNopenRsn() {
		return nopenRsn;
	}

	public void setNopenRsn(String nopenRsn) {
		this.nopenRsn = nopenRsn;
	}

	public String getNopenDtlRelBss() {
		return nopenDtlRelBss;
	}

	public void setNopenDtlRelBss(String nopenDtlRelBss) {
		this.nopenDtlRelBss = nopenDtlRelBss;
	}

	public String getOpenDataLst() {
		return openDataLst;
	}

	public void setOpenDataLst(String openDataLst) {
		this.openDataLst = openDataLst;
	}

	public String getOccrCyl() {
		return occrCyl;
	}

	public void setOccrCyl(String occrCyl) {
		this.occrCyl = occrCyl;
	}

	public String getDqDgnsYn() {
		return dqDgnsYn;
	}

	public void setDqDgnsYn(String dqDgnsYn) {
		this.dqDgnsYn = dqDgnsYn;
	}

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

    public String getPdmTblLnm() {
        return pdmTblLnm;
    }

    public void setPdmTblLnm(String pdmTblLnm) {
        this.pdmTblLnm = pdmTblLnm;
    }

    public String getSubjId() {
        return subjId;
    }

    public void setSubjId(String subjId) {
        this.subjId = subjId;
    }

    public String getStdAplYn() {
        return stdAplYn;
    }

    public void setStdAplYn(String stdAplYn) {
        this.stdAplYn = stdAplYn;
    }

    public String getPartTblYn() {
        return partTblYn;
    }

    public void setPartTblYn(String partTblYn) {
        this.partTblYn = partTblYn;
    }

    public String getDwTrgTblYn() {
        return dwTrgTblYn;
    }

    public void setDwTrgTblYn(String dwTrgTblYn) {
        this.dwTrgTblYn = dwTrgTblYn;
    }

    public String getCrgUserId() {
        return crgUserId;
    }

    public void setCrgUserId(String crgUserId) {
        this.crgUserId = crgUserId;
    }

    public String getCtdFcy() {
        return ctdFcy;
    }

    public void setCtdFcy(String ctdFcy) {
        this.ctdFcy = ctdFcy;
    }

    public String getDelCri() {
        return delCri;
    }

    public void setDelCri(String delCri) {
        this.delCri = delCri;
    }

    public String getDelMtd() {
        return delMtd;
    }

    public void setDelMtd(String delMtd) {
        this.delMtd = delMtd;
    }

    public String getBckFcy() {
        return bckFcy;
    }

    public void setBckFcy(String bckFcy) {
        this.bckFcy = bckFcy;
    }

	public String getSubjLnm() {
		return subjLnm;
	}

	public void setSubjLnm(String subjLnm) {
		this.subjLnm = subjLnm;
	}

	public String getSysAreaId() {
		return sysAreaId;
	}

	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
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

	public String getSubjLvl() {
		return subjLvl;
	}

	public void setSubjLvl(String subjLvl) {
		this.subjLvl = subjLvl;
	}

	public String getBfPdmTblPnm() {
		return bfPdmTblPnm;
	}

	public void setBfPdmTblPnm(String bfPdmTblPnm) {
		this.bfPdmTblPnm = bfPdmTblPnm;
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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getCrgUserNm() {
		return crgUserNm;
	}

	public void setCrgUserNm(String crgUserNm) {
		this.crgUserNm = crgUserNm;
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

	public String getColCnt() {
		return colCnt;
	}

	public void setColCnt(String colCnt) {
		this.colCnt = colCnt;
	}

	/**
	 * @param encYn the encYn to set
	 */
	public void setEncYn(String encYn) {
		this.encYn = encYn;
	}

	public String getConvTrgtYn() {
		return convTrgtYn;
	}

	public void setConvTrgtYn(String convTrgtYn) {
		this.convTrgtYn = convTrgtYn;
	}

	public String getNswtTrgtRsn() {
		return nswtTrgtRsn;
	}

	public void setNswtTrgtRsn(String nswtTrgtRsn) {
		this.nswtTrgtRsn = nswtTrgtRsn;
	}

	public String getTblMapRgstYn() {
		return tblMapRgstYn;
	}

	public void setTblMapRgstYn(String tblMapRgstYn) {
		this.tblMapRgstYn = tblMapRgstYn;
	}

	public String getColMapRgstYn() {
		return colMapRgstYn;
	}

	public void setColMapRgstYn(String colMapRgstYn) {
		this.colMapRgstYn = colMapRgstYn;
	}
	
	public String getUserRqstCntn() {
		return userRqstCntn;
	}

	public void setUserRqstCntn(String userRqstCntn) {
		this.userRqstCntn = userRqstCntn;
	}

	@Override
	public String toString() {
		return "WamPdmTbl [pdmTblId=" + pdmTblId + ", pdmTblPnm=" + pdmTblPnm
				+ ", pdmTblLnm=" + pdmTblLnm + ", subjId=" + subjId
				+ ", subjLnm=" + subjLnm + ", stdAplYn=" + stdAplYn
				+ ", partTblYn=" + partTblYn + ", dwTrgTblYn=" + dwTrgTblYn
				+ ", crgUserId=" + crgUserId + ", ctdFcy=" + ctdFcy
				+ ", delCri=" + delCri + ", delMtd=" + delMtd + ", bckFcy="
				+ bckFcy + ", sysAreaId=" + sysAreaId + ", mdlLnm=" + mdlLnm
				+ ", uppSubjLnm=" + uppSubjLnm + ", searchBgnDe=" + searchBgnDe
				+ ", searchEndDe=" + searchEndDe + ", subjLvl=" + subjLvl
				+ ", bfPdmTblPnm=" + bfPdmTblPnm + ", pdmColPnm=" + pdmColPnm
				+ ", pdmColLnm=" + pdmColLnm + ", dataType=" + dataType
				+ ", crgUserNm=" + crgUserNm + ", encYn=" + encYn + ", colCnt="
				+ colCnt + ", systemNm=" + systemNm + ", dbmsTypCd="
				+ dbmsTypCd + ", dbmsNm=" + dbmsNm + ", convTrgtYn="
				+ convTrgtYn + ", nswtTrgtRsn=" + nswtTrgtRsn
				+ ", tblMapRgstYn=" + tblMapRgstYn + ", colMapRgstYn="
				+ colMapRgstYn + ", subjNm=" + subjNm + ", prsvTerm="
				+ prsvTerm + ", tblVol=" + tblVol + ", openRsnCd=" + openRsnCd
				+ ", nopenRsn=" + nopenRsn + ", nopenDtlRelBss="
				+ nopenDtlRelBss + ", openDataLst=" + openDataLst
				+ ", occrCyl=" + occrCyl + ", dqDgnsYn=" + dqDgnsYn + "]";
	}
}