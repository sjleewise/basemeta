/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : WamMtaCol.java
 * 2. Package : kr.wise.meta.mta.service
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.09.12.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.09.12. :            : 신규 개발.
 */
package kr.wise.meta.mta.service;

import kr.wise.commons.cmm.CommonVo;

public class WamMtaCol extends CommonVo {
	
	private String mtaColId;
	private String mtaColPnm;
	private String mtaColLnm;
	private String mtaTblId;
	private String colRelEntyNm;
	private String colRelAttrNm;
	private Integer colOrd;
	private String pkYn;
	private Integer pkOrd;
	private String fkYn;
	private String dataType;
	private String dataLen;
	private Integer dataScal;
	private String dataFmt;
	private String nonulYn;
	private String defltVal;
	private String constCnd;
	private String openYn;
	private String prsnInfoYn;
	private String encTrgYn;
	private String priRsn;
	private String rqstNo;
	private Integer rqstSno;
	private Integer rqstDtlSno;
	//private String objDescn;
	//private Integer objVers;
	//private String regTypCd;
	//private String frsRqstDtm;
	//private String frsRqstUserId;
	//private String rqstDtm;
	//private String rqstUserId;
	//private String aprvDtm;
	//private String aprvUserId;

	private String errDataScaleYn ;
	private String errDataLengthYn;
	private String errDataTypeYn   ;
	private String errColPnmYn  ;
	private String errItemIdYn   ;  
	private String itemStatus     ;   
	
	private String mtaTblPnm;
	
	public String getMtaTblPnm() {
		return mtaTblPnm;
	}
	public void setMtaTblPnm(String mtaTblPnm) {
		this.mtaTblPnm = mtaTblPnm;
	}
	public String getErrDataScaleYn() {
		return errDataScaleYn;
	}
	public void setErrDataScaleYn(String errDataScaleYn) {
		this.errDataScaleYn = errDataScaleYn;
	}
	public String getErrDataLengthYn() {
		return errDataLengthYn;
	}
	public void setErrDataLengthYn(String errDataLengthYn) {
		this.errDataLengthYn = errDataLengthYn;
	}
	public String getErrDataTypeYn() {
		return errDataTypeYn;
	}
	public void setErrDataTypeYn(String errDataTypeYn) {
		this.errDataTypeYn = errDataTypeYn;
	}
	public String getErrColPnmYn() {
		return errColPnmYn;
	}
	public void setErrColPnmYn(String errColPnmYn) {
		this.errColPnmYn = errColPnmYn;
	}
	public String getErrItemIdYn() {
		return errItemIdYn;
	}
	public void setErrItemIdYn(String errItemIdYn) {
		this.errItemIdYn = errItemIdYn;
	}
	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	public String getMtaColId() {
		return mtaColId;
	}
	public void setMtaColId(String mtaColId) {
		this.mtaColId = mtaColId;
	}
	public String getMtaColPnm() {
		return mtaColPnm;
	}
	public void setMtaColPnm(String mtaColPnm) {
		this.mtaColPnm = mtaColPnm;
	}
	public String getMtaColLnm() {
		return mtaColLnm;
	}
	public void setMtaColLnm(String mtaColLnm) {
		this.mtaColLnm = mtaColLnm;
	}
	public String getMtaTblId() {
		return mtaTblId;
	}
	public void setMtaTblId(String mtaTblId) {
		this.mtaTblId = mtaTblId;
	}
	public String getColRelEntyNm() {
		return colRelEntyNm;
	}
	public void setColRelEntyNm(String colRelEntyNm) {
		this.colRelEntyNm = colRelEntyNm;
	}
	public String getColRelAttrNm() {
		return colRelAttrNm;
	}
	public void setColRelAttrNm(String colRelAttrNm) {
		this.colRelAttrNm = colRelAttrNm;
	}
	public Integer getColOrd() {
		return colOrd;
	}
	public void setColOrd(Integer colOrd) {
		this.colOrd = colOrd;
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
	public String getFkYn() {
		return fkYn;
	}
	public void setFkYn(String fkYn) {
		this.fkYn = fkYn;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataLen() {
		return dataLen;
	}
	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}
	public Integer getDataScal() {
		return dataScal;
	}
	public void setDataScal(Integer dataScal) {
		this.dataScal = dataScal;
	}
	public String getDataFmt() {
		return dataFmt;
	}
	public void setDataFmt(String dataFmt) {
		this.dataFmt = dataFmt;
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
	public String getConstCnd() {
		return constCnd;
	}
	public void setConstCnd(String constCnd) {
		this.constCnd = constCnd;
	}
	public String getOpenYn() {
		return openYn;
	}
	public void setOpenYn(String openYn) {
		this.openYn = openYn;
	}
	public String getPrsnInfoYn() {
		return prsnInfoYn;
	}
	public void setPrsnInfoYn(String prsnInfoYn) {
		this.prsnInfoYn = prsnInfoYn;
	}
	public String getEncTrgYn() {
		return encTrgYn;
	}
	public void setEncTrgYn(String encTrgYn) {
		this.encTrgYn = encTrgYn;
	}
	public String getPriRsn() {
		return priRsn;
	}
	public void setPriRsn(String priRsn) {
		this.priRsn = priRsn;
	}
	public String getRqstNo() {
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
	}
	public Integer getRqstDtlSno() {
		return rqstDtlSno;
	}
	public void setRqstDtlSno(Integer rqstDtlSno) {
		this.rqstDtlSno = rqstDtlSno;
	}
}