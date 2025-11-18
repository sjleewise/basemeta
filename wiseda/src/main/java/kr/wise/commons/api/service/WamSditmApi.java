package kr.wise.commons.api.service;

import java.util.Date;

public class WamSditmApi {

    private String sditmLnm;

    private String sditmPnm;
    
    private String lnmCriDs;

    private String pnmCriDs;
    
    private String dmngLnm;

    private String infotpLnm;

    private String infotpChgYn;

    private String encYn;

    private String dataLen;
    
    private String dataScal;
    
    private String dataType;
    
    private String dmnPnm;
    
    private String dmnLnm;
    
    private String uppDmngId;
    
    private String uppDmngLnm;
    
    private String fullEngMean;
    
    private String transYn;
    
    private String reqStr;
    
    private String testDataCnvYn;
    
  //commonVo 필요한것만 추림
	private String objDescn;
	
	private Integer objVers;
	
	private String regTypCd;			//등록유형코드 ('C', 'U', 'D)
	
	private Date frsRqstDtm;			//최초요청일시
	
	private String frsRqstUserId;		//최초요청자 ID
	
	private String frsRqstUserNm;		//최초요청자
	
	private Date rqstDtm;				//요청일시
	
	private String rqstUserId;			//요청자 ID
	
	private String rqstUserNm;			//요청자
	
	private Date aprvDtm;				//승인일시
	
	private String aprvUserId;			//승인자 ID
	
	private String aprvUserNm;			//승인자
    
    private String fullPath;	//계층형일 경우 전체 경로 (예: 모델>상위주제영역>주제영역)
    
    private String persInfoGrd; 		//개인정보등급
    
    private String persInfoCnvYn; 		//개인정보여부
    
  //20190111 이베이코리아
    private String stndAsrt; //표준구문 (옥션,지마켓, 공통)

    
	public String getPersInfoGrd() {
		return persInfoGrd;
	}

	public void setPersInfoGrd(String persInfoGrd) {
		this.persInfoGrd = persInfoGrd;
	}

	public String getPersInfoCnvYn() {
		return persInfoCnvYn;
	}

	public void setPersInfoCnvYn(String persInfoCnvYn) {
		this.persInfoCnvYn = persInfoCnvYn;
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

	public String getFullEngMean() {
		return fullEngMean;
	}

	public void setFullEngMean(String fullEngMean) {
		this.fullEngMean = fullEngMean;
	}

	public String getTransYn() {
		return transYn;
	}

	public void setTransYn(String transYn) {
		this.transYn = transYn;
	}

	public String getReqStr() {
		return reqStr;
	}

	public void setReqStr(String reqStr) {
		this.reqStr = reqStr;
	}

	public String getTestDataCnvYn() {
		return testDataCnvYn;
	}

	public void setTestDataCnvYn(String testDataCnvYn) {
		this.testDataCnvYn = testDataCnvYn;
	}

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

	public String getFrsRqstUserNm() {
		return frsRqstUserNm;
	}

	public void setFrsRqstUserNm(String frsRqstUserNm) {
		this.frsRqstUserNm = frsRqstUserNm;
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

	public String getRqstUserNm() {
		return rqstUserNm;
	}

	public void setRqstUserNm(String rqstUserNm) {
		this.rqstUserNm = rqstUserNm;
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

	public String getAprvUserNm() {
		return aprvUserNm;
	}

	public void setAprvUserNm(String aprvUserNm) {
		this.aprvUserNm = aprvUserNm;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getStndAsrt() {
		return stndAsrt;
	}

	public void setStndAsrt(String stndAsrt) {
		this.stndAsrt = stndAsrt;
	}

}
