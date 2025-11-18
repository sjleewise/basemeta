/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : EsbFilesendVO.java
 * 2. Package : kr.wise.esb.send.service
 * 3. Comment : ESB 파일전송 VO
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.22.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.22. :            : 신규 개발.
 */
package kr.wise.esb.send.service;

import kr.wise.commons.cmm.CommonVo;

public class EsbFilesendVO extends CommonVo{

	/*발생 일련번호 년(4)+월(4)+일(2)+시(2)+분(2)+초(2)+밀리초(3)+seq(6)*/
	private String occrrSn;
	
	/*발생일시 (최초등록시간)*/
	private String occrrDt;
	
	/*파일구분 M:메타데이터, R:원격파일배포*/
	private String fileGb;
	
	/*송신 기관코드*/
	private String srcOrgCd;
	
	/*송신 시스템코드*/
	private String srcSysCd;
	
	/*수신 상위 기관코드*/
	private String upperTgtOrgCd;
	
	/*수신 기관코드*/
	private String tgtOrgCd;
	
	/*수신 시스템 코드*/
	private String tgtSysCd;
	
	/*ESB 연계 ID*/
	private String esbCntcId;
	
	/*ESB 트랜잭션 ID*/
	private String esbTrnscId;
	
	/*ESB 처리 상태*/
	private String esbPrcsSttus;
	
	/*ESB 처리 횟수*/
	private int esbPrcsCo;
	
	/*송신 파일경로*/
	private String fileSendLoc;
	
	/*수신 파일경로*/
	private String fileRecvLoc;
	
	/*파일명*/
	private String fileName;
	
	/*ESB 에러 메시지*/
	private String esbErrMsg;
	
	/*처리일시*/
	private String esbCntcDt;
	
	
	private String infoSysCd;
	private String mtaRqstNo;
	
	private String mtaTblId;
	
	private String fileDtlCd; //파일 상세 구분 (테이블:T 컬럼:C)
	
	/** 메타데이터전송현황 */
	private Integer totalCnt; //총건수
	private Integer esbPrcsCnt; //처리건수
	private String esbPrcsStsCd;
	private String esbPrcsStsNm;
	private Integer successCnt;
	private Integer failCnt;
	private Integer sendingCnt;
	
	//확장자
	private String fileExt;
	private String[] fileHeader;
	private String[] csvFileHeader;
	
	private String orgNm;
	
	private String sendYn;

	private String searchOrgCd;
	
	private String searchInfoSysCd;

	private String sendStsCd;
	
	
	public String getSendStsCd() {
		return sendStsCd;
	}

	public void setSendStsCd(String sendStsCd) {
		this.sendStsCd = sendStsCd;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String[] getFileHeader() {
		return fileHeader;
	}

	public void setFileHeader(String[] fileHeader) {
		this.fileHeader = fileHeader;
	}

	public String[] getCsvFileHeader() {
		return csvFileHeader;
	}

	public void setCsvFileHeader(String[] csvFileHeader) {
		this.csvFileHeader = csvFileHeader;
	}

	public String getFileDtlCd() {
		return fileDtlCd;
	}

	public void setFileDtlCd(String fileDtlCd) {
		this.fileDtlCd = fileDtlCd;
	}

	public String getMtaRqstNo() {
		return mtaRqstNo;
	}

	public void setMtaRqstNo(String mtaRqstNo) {
		this.mtaRqstNo = mtaRqstNo;
	}

	public String getEsbPrcsStsCd() {
		return esbPrcsStsCd;
	}

	public void setEsbPrcsStsCd(String esbPrcsStsCd) {
		this.esbPrcsStsCd = esbPrcsStsCd;
	}

	public Integer getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(Integer totalCnt) {
		this.totalCnt = totalCnt;
	}

	public Integer getEsbPrcsCnt() {
		return esbPrcsCnt;
	}

	public void setEsbPrcsCnt(Integer esbPrcsCnt) {
		this.esbPrcsCnt = esbPrcsCnt;
	}

	public String getEsbPrcsStsNm() {
		return esbPrcsStsNm;
	}

	public void setEsbPrcsStsNm(String esbPrcsStsNm) {
		this.esbPrcsStsNm = esbPrcsStsNm;
	}

	public String getInfoSysCd() {
		return infoSysCd;
	}

	public void setInfoSysCd(String infoSysCd) {
		this.infoSysCd = infoSysCd;
	}

	public String getOccrrSn() {
		return occrrSn;
	}

	public void setOccrrSn(String occrrSn) {
		this.occrrSn = occrrSn;
	}

	public String getOccrrDt() {
		return occrrDt;
	}

	public void setOccrrDt(String occrrDt) {
		this.occrrDt = occrrDt;
	}

	public String getFileGb() {
		return fileGb;
	}

	public void setFileGb(String fileGb) {
		this.fileGb = fileGb;
	}

	public String getSrcOrgCd() {
		return srcOrgCd;
	}

	public void setSrcOrgCd(String srcOrgCd) {
		this.srcOrgCd = srcOrgCd;
	}

	public String getSrcSysCd() {
		return srcSysCd;
	}

	public void setSrcSysCd(String srcSysCd) {
		this.srcSysCd = srcSysCd;
	}

	public String getUpperTgtOrgCd() {
		return upperTgtOrgCd;
	}

	public void setUpperTgtOrgCd(String upperTgtOrgCd) {
		this.upperTgtOrgCd = upperTgtOrgCd;
	}

	public String getTgtOrgCd() {
		return tgtOrgCd;
	}

	public void setTgtOrgCd(String tgtOrgCd) {
		this.tgtOrgCd = tgtOrgCd;
	}

	public String getTgtSysCd() {
		return tgtSysCd;
	}

	public void setTgtSysCd(String tgtSysCd) {
		this.tgtSysCd = tgtSysCd;
	}

	public String getEsbCntcId() {
		return esbCntcId;
	}

	public void setEsbCntcId(String esbCntcId) {
		this.esbCntcId = esbCntcId;
	}

	public String getEsbTrnscId() {
		return esbTrnscId;
	}

	public void setEsbTrnscId(String esbTrnscId) {
		this.esbTrnscId = esbTrnscId;
	}

	public String getEsbPrcsSttus() {
		return esbPrcsSttus;
	}

	public void setEsbPrcsSttus(String esbPrcsSttus) {
		this.esbPrcsSttus = esbPrcsSttus;
	}

	public int getEsbPrcsCo() {
		return esbPrcsCo;
	}

	public void setEsbPrcsCo(int esbPrcsCo) {
		this.esbPrcsCo = esbPrcsCo;
	}

	public String getFileSendLoc() {
		return fileSendLoc;
	}

	public void setFileSendLoc(String fileSendLoc) {
		this.fileSendLoc = fileSendLoc;
	}

	public String getFileRecvLoc() {
		return fileRecvLoc;
	}

	public void setFileRecvLoc(String fileRecvLoc) {
		this.fileRecvLoc = fileRecvLoc;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getEsbErrMsg() {
		return esbErrMsg;
	}

	public void setEsbErrMsg(String esbErrMsg) {
		this.esbErrMsg = esbErrMsg;
	}

	public String getEsbCntcDt() {
		return esbCntcDt;
	}

	public void setEsbCntcDt(String esbCntcDt) {
		this.esbCntcDt = esbCntcDt;
	}
	
	public String getOrgNm() {
		return orgNm;
	}

	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}

	public String getSendYn() {
		return sendYn;
	}

	public void setSendYn(String sendYn) {
		this.sendYn = sendYn;
	}

	public String getSearchOrgCd() {
		return searchOrgCd;
	}

	public void setSearchOrgCd(String searchOrgCd) {
		this.searchOrgCd = searchOrgCd;
	}

	public String getSearchInfoSysCd() {
		return searchInfoSysCd;
	}

	public void setSearchInfoSysCd(String searchInfoSysCd) {
		this.searchInfoSysCd = searchInfoSysCd;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EsbFilesend [occrrSn="+occrrSn+", ")
		.append("occrrDt="+occrrDt+", ")
		.append("fileGb="+fileGb+", ")
		.append("srcOrgCd="+srcOrgCd+", ")
		.append("srcSysCd="+srcSysCd+", ")
		.append("tgtOrgCd="+tgtOrgCd+", ")
		.append("tgtSysCd="+tgtSysCd+", ")
		.append("esbCntcId="+esbCntcId+", ")
		.append("esbTrnscId="+esbTrnscId+", ")
		.append("esbPrcsSttus="+esbPrcsSttus+", ")
		.append("esbPrcsCo="+esbPrcsCo+", ")
		.append("fileSendLoc="+fileSendLoc+", ")
		.append("fileRecvLoc="+fileRecvLoc+", ")
		.append("fileName="+fileName+", ")
		.append("esbErrMsg="+esbErrMsg+", ")
		.append("esbCntcDt="+esbCntcDt+", ")
		.append("infoSysCd="+infoSysCd+", ")
		.append("]");
		
		return builder.toString()+super.toString();
	}

	public Integer getSuccessCnt() {
		return successCnt;
	}

	public void setSuccessCnt(Integer successCnt) {
		this.successCnt = successCnt;
	}

	public Integer getSendingCnt() {
		return sendingCnt;
	}

	public void setSendingCnt(Integer sendingCnt) {
		this.sendingCnt = sendingCnt;
	}

	public Integer getFailCnt() {
		return failCnt;
	}

	public void setFailCnt(Integer failCnt) {
		this.failCnt = failCnt;
	}

	public String getMtaTblId() {
		return mtaTblId;
	}

	public void setMtaTblId(String mtaTblId) {
		this.mtaTblId = mtaTblId;
	}
	
	
}
