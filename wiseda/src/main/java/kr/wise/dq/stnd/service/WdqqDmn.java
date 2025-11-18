package kr.wise.dq.stnd.service;

import kr.wise.commons.cmm.CommonVo;

public class WdqqDmn extends CommonVo {
//    private String rqstNo;

//    private Integer rqstSno;

	/** 도메인ID */
    private String dmnId;

    /** 도메인논리명 */
    private String dmnLnm;

    /** 도메인물리명 */
    private String dmnPnm;

    /** 논리명기준구분 */
    private String lnmCriDs;
    
    /** 물리명기준구분 */
    private String pnmCriDs;

//    private String rqstDcd;

//    private String rvwStsCd;

//    private String rvwConts;

//    private String vrfCd;

//    private String vrfRmk;

    /** 도메인그룹ID */
    private String dmngId;

    /** 도메인그룹논리명 */
    private String dmngLnm;

    /** 인포타입ID */
    private String infotpId;

    /** 데이터타입 */
    private String dataType;

    /** 데이터길이 */
    private Integer dataLen;

    /** 데이터소수점길이 */
    private Integer dataScal;

    /** 인포타입논리명 */
    private String infotpLnm;

    /** 상위도메인ID */
    private String uppDmnId;

    /** 상위도메인논리명 */
    private String uppDmnLnm;

    /** 모델논리명 */
    private String mdlLnm;

    /** 상위주제영역논리명 */
    private String uppSubjLnm;

    /** 주제영역ID */
    private String subjId;

    /** 주제영역논리명 */
    private String subjLnm;

    /** 목록엔티티ID */
    private String lstEntyId;

    /** 목록엔티티물리명 */
    private String lstEntyPnm;

    /** 목록엔티티논리명 */
    private String lstEntyLnm;
    
    /** 목록어트리뷰트ID */
    private String lstAttrId;

    /** 목록어트리뷰트물리명 */
    private String lstAttrPnm;

    /** 목록어트리뷰트논리명 */
    private String lstAttrLnm;
        

    /** 코드값유형코드 */
    private String cdValTypCd;

    /** 코드값부여방식코드 */
    private String cdValIvwCd;

    /** 데이터형식 */
    private String dataFrm;

    /** 표준항목자동생성여부 */
    private String sditmAutoCrtYn;

    /** 담당사용자ID */
    private String crgUserId;

    /** 담당사용자명 */
    private String crgUserNm;

    /** 도메인출처구분 */
    private String dmnOrgDs;

    /** 도메인출처내용 */
    private String dmnOrgTxt;
    
    /** 도메인그룹 1레벨ID */
    private String dmngId1;
    
    /** 도메인그룹 2레벨ID */
    private String dmngId2;
    
    /** 상위 도메인그룹ID */
    private String uppDmngId;
    
    /** 상위 도메인그룹명 */
    private String uppDmngLnm;
    
    /** 암호화여부 */
    
    private String encYn;
    
    /** 대분류코드 */
    private String dmnDscd;
    
    private String dmnMaxVal;
    private String dmnMinVal;
    
    private String cdValId;
    private String cdVal;
    private String cdValNm;
    
    private String subCdYn;
    
    private String dupYn;
    
//    private String objDescn;

//    private Integer objVers;

//    private String regTypCd;

//    private Date frsRqstDtm;

//    private String frsRqstUserId;

//    private Date rqstDtm;

//    private String rqstUserId;

//    private Date aprvDtm;

//    private String aprvUserId;

/*    public String getRqstNo() {
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
    }*/

    public String getDupYn() {
		return dupYn;
	}

	public void setDupYn(String dupYn) {
		this.dupYn = dupYn;
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

    public String getLnmCriDs() {
        return lnmCriDs;
    }

    public void setLnmCriDs(String lnmCriDs) {
        this.lnmCriDs = lnmCriDs;
    }

/*    public String getRqstDcd() {
        return rqstDcd;
    }

    public void setRqstDcd(String rqstDcd) {
        this.rqstDcd = rqstDcd;
    }

    public String getRvwStsCd() {
        return rvwStsCd;
    }

    public void setRvwStsCd(String rvwStsCd) {
        this.rvwStsCd = rvwStsCd;
    }

    public String getRvwConts() {
        return rvwConts;
    }

    public void setRvwConts(String rvwConts) {
        this.rvwConts = rvwConts;
    }

    public String getVrfCd() {
        return vrfCd;
    }

    public void setVrfCd(String vrfCd) {
        this.vrfCd = vrfCd;
    }

    public String getVrfRmk() {
        return vrfRmk;
    }

    public void setVrfRmk(String vrfRmk) {
        this.vrfRmk = vrfRmk;
    }*/

    public String getDmngId() {
        return dmngId;
    }

    public void setDmngId(String dmngId) {
        this.dmngId = dmngId;
    }

    public String getDmngLnm() {
        return dmngLnm;
    }

    public void setDmngLnm(String dmngLnm) {
        this.dmngLnm = dmngLnm;
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

    public String getUppDmnId() {
        return uppDmnId;
    }

    public void setUppDmnId(String uppDmnId) {
        this.uppDmnId = uppDmnId;
    }

    public String getUppDmnLnm() {
        return uppDmnLnm;
    }

    public void setUppDmnLnm(String uppDmnLnm) {
        this.uppDmnLnm = uppDmnLnm;
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

    public String getDataFrm() {
        return dataFrm;
    }

    public void setDataFrm(String dataFrm) {
        this.dataFrm = dataFrm;
    }

    public String getSditmAutoCrtYn() {
        return sditmAutoCrtYn;
    }

    public void setSditmAutoCrtYn(String sditmAutoCrtYn) {
        this.sditmAutoCrtYn = sditmAutoCrtYn;
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

    public String getDmnOrgDs() {
        return dmnOrgDs;
    }

    public void setDmnOrgDs(String dmnOrgDs) {
        this.dmnOrgDs = dmnOrgDs;
    }

	public String getDmngId1() {
		return dmngId1;
	}

	public void setDmngId1(String dmngId1) {
		this.dmngId1 = dmngId1;
	}

	public String getDmngId2() {
		return dmngId2;
	}

	public void setDmngId2(String dmngId2) {
		this.dmngId2 = dmngId2;
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

	public String getPnmCriDs() {
		return pnmCriDs;
	}

	public void setPnmCriDs(String pnmCriDs) {
		this.pnmCriDs = pnmCriDs;
	}
	
	public String getDmnMaxVal() {
		return dmnMaxVal;
	}
	
	public void setDmnMaxVal(String dmnMaxVal) {
		this.dmnMaxVal = dmnMaxVal;
	}
	
	public String getDmnMinVal() {
		return dmnMinVal;
	}
	
	public void setDmnMinVal(String dmnMinVal) {
		this.dmnMinVal = dmnMinVal;
	}

	public String getCdValId() {
		return cdValId;
	}

	public void setCdValId(String cdValId) {
		this.cdValId = cdValId;
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
		return "WaqDmn [dmnId=" + dmnId + ", dmnLnm=" + dmnLnm + ", dmnPnm="
				+ dmnPnm + ", lnmCriDs=" + lnmCriDs + ", pnmCriDs=" + pnmCriDs
				+ ", dmngId=" + dmngId + ", dmngLnm=" + dmngLnm + ", infotpId="
				+ infotpId + ", dataType=" + dataType + ", dataLen=" + dataLen
				+ ", dataScal=" + dataScal + ", infotpLnm=" + infotpLnm
				+ ", uppDmnId=" + uppDmnId + ", uppDmnLnm=" + uppDmnLnm
				+ ", mdlLnm=" + mdlLnm + ", uppSubjLnm=" + uppSubjLnm
				+ ", subjId=" + subjId + ", subjLnm=" + subjLnm
				+ ", lstEntyId=" + lstEntyId + ", lstEntyPnm=" + lstEntyPnm
				+ ", lstEntyLnm=" + lstEntyLnm + ", lstAttrId=" + lstAttrId
				+ ", lstAttrPnm=" + lstAttrPnm + ", lstAttrLnm=" + lstAttrLnm
				+ ", cdValTypCd=" + cdValTypCd + ", cdValIvwCd=" + cdValIvwCd
				+ ", dataFrm=" + dataFrm + ", sditmAutoCrtYn=" + sditmAutoCrtYn
				+ ", crgUserId=" + crgUserId + ", crgUserNm=" + crgUserNm
				+ ", dmnOrgDs=" + dmnOrgDs + ", dmnOrgTxt=" + dmnOrgTxt
				+ ", dmngId1=" + dmngId1 + ", dmngId2=" + dmngId2
				+ ", uppDmngId=" + uppDmngId + ", uppDmngLnm=" + uppDmngLnm
				+ ", encYn=" + encYn + ", dmnDscd=" + dmnDscd + ", cdValId="
				+ cdValId + ", cdVal=" + cdVal + ", cdValNm=" + cdValNm
				+ ", subCdYn=" + subCdYn + "]";
	}

/*    public String getObjDescn() {
        return objDescn;
    }

    public void setObjDescn(String objDescn) {
        this.objDescn = objDescn;
    }

    public Integer getObjVers() {
        return objVers;
    }

    public void setObjVers(Integer objVers) {
        this.objVers = objVers;
    }

    public String getRegTypCd() {
        return regTypCd;
    }

    public void setRegTypCd(String regTypCd) {
        this.regTypCd = regTypCd;
    }

    public Date getFrsRqstDtm() {
        return frsRqstDtm;
    }

    public void setFrsRqstDtm(Date frsRqstDtm) {
        this.frsRqstDtm = frsRqstDtm;
    }

    public String getFrsRqstUserId() {
        return frsRqstUserId;
    }

    public void setFrsRqstUserId(String frsRqstUserId) {
        this.frsRqstUserId = frsRqstUserId;
    }

    public Date getRqstDtm() {
        return rqstDtm;
    }

    public void setRqstDtm(Date rqstDtm) {
        this.rqstDtm = rqstDtm;
    }

    public String getRqstUserId() {
        return rqstUserId;
    }

    public void setRqstUserId(String rqstUserId) {
        this.rqstUserId = rqstUserId;
    }

    public Date getAprvDtm() {
        return aprvDtm;
    }

    public void setAprvDtm(Date aprvDtm) {
        this.aprvDtm = aprvDtm;
    }

    public String getAprvUserId() {
        return aprvUserId;
    }

    public void setAprvUserId(String aprvUserId) {
        this.aprvUserId = aprvUserId;
    }*/
}