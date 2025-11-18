package kr.wise.commons.damgmt.schedule.service;

//import java.math.BigDecimal;
import java.util.Date;

import kr.wise.commons.helper.IBSDateJsonDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


public class WamShdLogVO extends WamShdJob{
	private String objResTbl;

	private Integer anaSec;//진행시간

	private String objId;

	private String anaLogId; //분석로그ID

	private Date anaStrDtm; //분석시작일시

	private Date anaEndDtm; //분석종료일시

	private String anaStsCd; //분석상태코드

	private String anaErMsg; //분석오류메시지

	private String anaUserId; //분석사용자ID

	private String anaUserNm; //분석사용자이름

	private String jobCnt; //총건수

	private String searchBgnDe; //조회기간 시작일

	private String searchEndDe; //조회기간 종료일

	private String objNm; //작업명

	private Integer sucCnt; //성공건수

	private Integer erCnt; // 실패건수

	private String erRate; //오류율

	private Integer anaCnt; // 측정건수

	private String userNm;


	public String getObjResTbl() {
		return objResTbl;
	}

	public void setObjResTbl(String objResTbl) {
		this.objResTbl = objResTbl;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public Integer getErCnt() {
		return erCnt;
	}

	public void setErCnt(Integer erCnt) {
		this.erCnt = erCnt;
	}

	public String getErRate() {
		return erRate;
	}

	public void setErRate(String erRate) {
		this.erRate = erRate;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public Integer getAnaCnt() {
		return anaCnt;
	}

	public void setAnaCnt(Integer anaCnt) {
		this.anaCnt = anaCnt;
	}

	public Integer getSucCnt() {
		return sucCnt;
	}

	public void setSucCnt(Integer sucCnt) {
		this.sucCnt = sucCnt;
	}

	public String getJobCnt() {
		return jobCnt;
	}

	public void setJobCnt(String jobCnt) {
		this.jobCnt = jobCnt;
	}

	public String getObjNm() {
		return objNm;
	}

	public void setObjNm(String objNm) {
		this.objNm = objNm;
	}

	public String getAnaLogId() {
		return anaLogId;
	}

	public void setAnaLogId(String anaLogId) {
		this.anaLogId = anaLogId;
	}

	public Date getAnaStrDtm() {
		return anaStrDtm;
	}

	@JsonDeserialize(using = IBSDateJsonDeserializer.class)
	public void setAnaStrDtm(Date anaStrDtm) {
		this.anaStrDtm = anaStrDtm;
	}

	public Date getAnaEndDtm() {
		return anaEndDtm;
	}

	@JsonDeserialize(using = IBSDateJsonDeserializer.class)
	public void setAnaEndDtm(Date anaEndDtm) {
		this.anaEndDtm = anaEndDtm;
	}

	public String getAnaStsCd() {
		return anaStsCd;
	}

	public void setAnaStsCd(String anaStsCd) {
		this.anaStsCd = anaStsCd;
	}

	public String getAnaErMsg() {
		return anaErMsg;
	}

	public void setAnaErMsg(String anaErMsg) {
		this.anaErMsg = anaErMsg;
	}

	public String getAnaUserId() {
		return anaUserId;
	}

	public void setAnaUserId(String anaUserId) {
		this.anaUserId = anaUserId;
	}

	public Integer getAnaSec() {
		return anaSec;
	}

	public void setAnaSec(Integer anaSec) {
		this.anaSec = anaSec;
	}

	public String getAnaUserNm() {
		return anaUserNm;
	}

	public void setAnaUserNm(String anaUserNm) {
		this.anaUserNm = anaUserNm;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamShdLogVO [objResTbl=").append(objResTbl)
				.append(", anaSec=").append(anaSec).append(", objId=")
				.append(objId).append(", anaLogId=").append(anaLogId)
				.append(", anaStrDtm=").append(anaStrDtm)
				.append(", anaEndDtm=").append(anaEndDtm).append(", anaStsCd=")
				.append(anaStsCd).append(", anaErMsg=").append(anaErMsg)
				.append(", anaUserId=").append(anaUserId)
				.append(", anaUserNm=").append(anaUserNm).append(", jobCnt=")
				.append(jobCnt).append(", searchBgnDe=").append(searchBgnDe)
				.append(", searchEndDe=").append(searchEndDe)
				.append(", objNm=").append(objNm).append(", sucCnt=")
				.append(sucCnt).append(", erCnt=").append(erCnt)
				.append(", erRate=").append(erRate).append(", anaCnt=")
				.append(anaCnt).append(", userNm=").append(userNm).append("]");
		return builder.toString() + super.toString();
	}

}