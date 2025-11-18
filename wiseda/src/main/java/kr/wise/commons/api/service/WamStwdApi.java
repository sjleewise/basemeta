package kr.wise.commons.api.service;

import java.util.Date;

public class WamStwdApi {

	/** 표준단어논리명 */
    private String stwdLnm;

    /** 표준단어물리명 */
    private String stwdPnm;

    /** 영문의미 */
    private String engMean;

    private String wdDcd;
    
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
    
  //20190111 이베이코리아
    private String stndAsrt; //표준구문 (옥션,지마켓, 공통)

	public String getStwdLnm() {
		return stwdLnm;
	}

	public void setStwdLnm(String stwdLnm) {
		this.stwdLnm = stwdLnm;
	}

	public String getStwdPnm() {
		return stwdPnm;
	}

	public void setStwdPnm(String stwdPnm) {
		this.stwdPnm = stwdPnm;
	}

	public String getEngMean() {
		return engMean;
	}

	public void setEngMean(String engMean) {
		this.engMean = engMean;
	}

	public String getWdDcd() {
		return wdDcd;
	}

	public void setWdDcd(String wdDcd) {
		this.wdDcd = wdDcd;
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
