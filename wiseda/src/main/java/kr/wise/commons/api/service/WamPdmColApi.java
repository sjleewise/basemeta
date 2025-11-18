package kr.wise.commons.api.service;

import java.util.Date;

public class WamPdmColApi {
	
	private String pdmColPnm;
	
	private String pdmColLnm;
	
	private Integer colOrd;
	
	private String dataType;
	
	private String pkYn;
	
	private Integer pkOrd;
	
	private String nonulYn;
	
	private String encYn; //암호화 여부
	
	private String pdmTblLnm;
	
	private String pdmTblPnm;
	
	private String sditmLnm;
	
	private String sditmPnm;
	
	private String subjLnm;
	
	private String dmnLnm;
	
	private String dmnPnm;
	
	//주제영역 풀패스
	private String fullPath;
	
	//표준적용여부
	private String stdAplYn;
	
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
	
	private String persInfoGrd; 		//개인정보등급
    
    private String persInfoCnvYn; 		//개인정보여부
    
    

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

	public String getEncYn() {
		return encYn;
	}

	public void setEncYn(String encYn) {
		this.encYn = encYn;
	}

	public String getPdmTblLnm() {
		return pdmTblLnm;
	}

	public void setPdmTblLnm(String pdmTblLnm) {
		this.pdmTblLnm = pdmTblLnm;
	}

	public String getPdmTblPnm() {
		return pdmTblPnm;
	}

	public void setPdmTblPnm(String pdmTblPnm) {
		this.pdmTblPnm = pdmTblPnm;
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

	public String getSubjLnm() {
		return subjLnm;
	}

	public void setSubjLnm(String subjLnm) {
		this.subjLnm = subjLnm;
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

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getStdAplYn() {
		return stdAplYn;
	}

	public void setStdAplYn(String stdAplYn) {
		this.stdAplYn = stdAplYn;
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

}
