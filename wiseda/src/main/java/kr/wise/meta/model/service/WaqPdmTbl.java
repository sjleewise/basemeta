package kr.wise.meta.model.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqPdmTbl extends CommonVo {
//    private String rqstNo;
//
//    private Integer rqstSno;

    private String pdmTblId;

    private String pdmTblPnm;

    private String pdmTblLnm;

    private String bfPdmTblPnm;

//    private String rqstDcd;
//
//    private String rvwStsCd;
//
//    private String rvwConts;
//
//    private String vrfCd;
//
//    private String vrfRmk;

    private String mdlLnm;

    private String uppSubjLnm;

    private String subjId;

    private String subjLnm;

    private String stdAplYn;

    private String partTblYn;

    private String dwTrgTblYn;

    private String crgUserId;

    private String crgUserNm;

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

    private String ctdFcy;

    private String delCri;

    private String delMtd;

    private String bckFcy;

    private String pdmRegYn;
    
    private String userRqstCntn;
    
    //ERWIN VO
    private String category_id;
    private String model_id;
    private String subjectarea_id;
    private String entity_id;

    private String category_name;
    private String model_name;
    private String subjectarea_name;
	
    private String entity_name;
    private String entity_name_physical;
    private String entity_name_logical;         
    
    //asis용
    private String systemNm;         
    private String dbmsTypCd;         
    private String dbmsNm;         
    private String convTrgtYn;         
    private String nswtTrgtRsn;         

    private String nxtUserId;
    private String nxtUserNm;
    
    //범정부연계항목
    private String subjNm;
    private String prsvTerm;
    private String tblTypNm;
    private String tblVol;
    private String openRsnCd;
    private String nopenRsn;
    private String nopenDtlRelBss;
    private String openDataLst;
    private String occrCyl;
    private String dqDgnsYn;

    private String exerdPdmTblId;  
    
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
	
	public String getTblTypNm() {
		return tblTypNm;
	}

	public void setTblTypNm(String tblTypNm) {
		this.tblTypNm = tblTypNm;
	}

	public String getTblVol() {
		return tblVol;
	}

	public void setTblVol(String tblVol) {
		this.tblVol = tblVol;
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

	public String getNxtUserId() {
		return nxtUserId;
	}

	public void setNxtUserId(String nxtUserId) {
		this.nxtUserId = nxtUserId;
	}

	public String getNxtUserNm() {
		return nxtUserNm;
	}

	public void setNxtUserNm(String nxtUserNm) {
		this.nxtUserNm = nxtUserNm;
	}

	public String getUserRqstCntn() {
		return userRqstCntn;
	}

	public void setUserRqstCntn(String userRqstCntn) {
		this.userRqstCntn = userRqstCntn;
	}

	public String getPdmRegYn() {
		return pdmRegYn;
	}

	public void setPdmRegYn(String pdmRegYn) {
		this.pdmRegYn = pdmRegYn;
	}

//	public String getRqstNo() {
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

    public String getBfPdmTblPnm() {
        return bfPdmTblPnm;
    }

    public void setBfPdmTblPnm(String bfPdmTblPnm) {
        this.bfPdmTblPnm = bfPdmTblPnm;
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

    public String getCrgUserNm() {
        return crgUserNm;
    }

    public void setCrgUserNm(String crgUserNm) {
        this.crgUserNm = crgUserNm;
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
    
    
    
    
       
	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	

	public String getEntity_id() {
		return entity_id;
	}

	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getModel_name() {
		return model_name;
	}

	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}

	

	public String getEntity_name() {
		return entity_name;
	}

	public void setEntity_name(String entity_name) {
		this.entity_name = entity_name;
	}

	public String getEntity_name_physical() {
		return entity_name_physical;
	}

	public void setEntity_name_physical(String entity_name_physical) {
		this.entity_name_physical = entity_name_physical;
	}

	public String getEntity_name_logical() {
		return entity_name_logical;
	}

	public void setEntity_name_logical(String entity_name_logical) {
		this.entity_name_logical = entity_name_logical;
	}
	
	
	public String getSubjectarea_id() {
		return subjectarea_id;
	}

	public void setSubjectarea_id(String subjectarea_id) {
		this.subjectarea_id = subjectarea_id;
	}

	public String getSubjectarea_name() {
		return subjectarea_name;
	}

	public void setSubjectarea_name(String subjectarea_name) {
		this.subjectarea_name = subjectarea_name;
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
	
	public String getExerdPdmTblId() {
		return exerdPdmTblId;
	}

	public void setExerdPdmTblId(String exerdPdmTblId) {
		this.exerdPdmTblId = exerdPdmTblId;
	}

	@Override
	public String toString() {
		return "WaqPdmTbl [pdmTblId=" + pdmTblId + ", pdmTblPnm=" + pdmTblPnm
				+ ", pdmTblLnm=" + pdmTblLnm + ", bfPdmTblPnm=" + bfPdmTblPnm
				+ ", mdlLnm=" + mdlLnm + ", uppSubjLnm=" + uppSubjLnm
				+ ", subjId=" + subjId + ", subjLnm=" + subjLnm + ", stdAplYn="
				+ stdAplYn + ", partTblYn=" + partTblYn + ", dwTrgTblYn="
				+ dwTrgTblYn + ", crgUserId=" + crgUserId + ", crgUserNm="
				+ crgUserNm + ", ctdFcy=" + ctdFcy + ", delCri=" + delCri
				+ ", delMtd=" + delMtd + ", bckFcy=" + bckFcy + ", pdmRegYn="
				+ pdmRegYn + ", userRqstCntn=" + userRqstCntn
				+ ", category_id=" + category_id + ", model_id=" + model_id
				+ ", subjectarea_id=" + subjectarea_id + ", entity_id="
				+ entity_id + ", category_name=" + category_name
				+ ", model_name=" + model_name + ", subjectarea_name="
				+ subjectarea_name + ", entity_name=" + entity_name
				+ ", entity_name_physical=" + entity_name_physical
				+ ", entity_name_logical=" + entity_name_logical
				+ ", systemNm=" + systemNm + ", dbmsTypCd=" + dbmsTypCd
				+ ", dbmsNm=" + dbmsNm + ", convTrgtYn=" + convTrgtYn
				+ ", nswtTrgtRsn=" + nswtTrgtRsn + ", nxtUserId=" + nxtUserId
				+ ", nxtUserNm=" + nxtUserNm + ", subjNm=" + subjNm
				+ ", prsvTerm=" + prsvTerm + ", tblTypNm=" + tblTypNm
				+ ", tblVol=" + tblVol + ", openRsnCd=" + openRsnCd
				+ ", nopenRsn=" + nopenRsn + ", nopenDtlRelBss="
				+ nopenDtlRelBss + ", openDataLst=" + openDataLst
				+ ", occrCyl=" + occrCyl + ", dqDgnsYn=" + dqDgnsYn
				+ ", exerdPdmTblId=" + exerdPdmTblId + "]";
	}

}