package kr.wise.commons.api.service;

import java.util.Date;

public class WamDmnApi {
	
    private String dmnLnm;

    private String dmnPnm;

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
    
  //commonVo 필요한것만 추림
  	private String objDescn;
  	
  	private Integer objVers;
  	
  	private String regTypCd;			//등록유형코드 ('C', 'U', 'D)
  	
  	private Date frsRqstDtm;			//최초요청일시
  	
  	private String frsRqstUserId;		//최초요청자 ID
  	
  	private Date rqstDtm;				//요청일시
  	
  	private String rqstUserId;			//요청자 ID
  	
  	private Date aprvDtm;				//승인일시
  	
  	private String aprvUserId;			//승인자 ID
      
    private String fullPath;	//계층형일 경우 전체 경로 (예: 모델>상위주제영역>주제영역)

	public String getObjDescn() {
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
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
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

	public String getDmnOrgTxt() {
		return dmnOrgTxt;
	}

	public void setDmnOrgTxt(String dmnOrgTxt) {
		this.dmnOrgTxt = dmnOrgTxt;
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

	public String getDmnEngMean() {
		return dmnEngMean;
	}

	public void setDmnEngMean(String dmnEngMean) {
		this.dmnEngMean = dmnEngMean;
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

	public String getUppDmngId() {
		return uppDmngId;
	}

	public void setUppDmngId(String uppDmngId) {
		this.uppDmngId = uppDmngId;
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

	public String getEncYn() {
		return encYn;
	}

	public void setEncYn(String encYn) {
		this.encYn = encYn;
	}

	public String getDmnDscd() {
		return dmnDscd;
	}

	public void setDmnDscd(String dmnDscd) {
		this.dmnDscd = dmnDscd;
	}

	public String getTransYn() {
		return transYn;
	}

	public void setTransYn(String transYn) {
		this.transYn = transYn;
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

	public String getSubCdYn() {
		return subCdYn;
	}

	public void setSubCdYn(String subCdYn) {
		this.subCdYn = subCdYn;
	}
	  
    //20190111 이베이코리아
    private String stndAsrt; //표준구문 (옥션,지마켓, 공통)

	public String getStndAsrt() {
		return stndAsrt;
	}

	public void setStndAsrt(String stndAsrt) {
		this.stndAsrt = stndAsrt;
	}


}
