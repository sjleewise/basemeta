package kr.wise.commons.api.service;

import java.util.Date;

public class WamDdlColApi {

    private String ddlColPnm;

    private String ddlColLnm;

    private Integer colOrd;

    private String dataType;

    private Integer dataLen;

    private Integer dataScal;

    private String pkYn;

    private Integer pkOrd;

    private String nonulYn;

    private String defltVal;
    
    private String ddlTblPnm;
    
    private String encYn;
    
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

	public String getDdlColPnm() {
		return ddlColPnm;
	}

	public void setDdlColPnm(String ddlColPnm) {
		this.ddlColPnm = ddlColPnm;
	}

	public String getDdlColLnm() {
		return ddlColLnm;
	}

	public void setDdlColLnm(String ddlColLnm) {
		this.ddlColLnm = ddlColLnm;
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

	public String getDdlTblPnm() {
		return ddlTblPnm;
	}

	public void setDdlTblPnm(String ddlTblPnm) {
		this.ddlTblPnm = ddlTblPnm;
	}

	public String getEncYn() {
		return encYn;
	}

	public void setEncYn(String encYn) {
		this.encYn = encYn;
	}
    
    
}
