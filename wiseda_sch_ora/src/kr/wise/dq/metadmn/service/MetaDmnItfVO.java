package kr.wise.dq.metadmn.service;

import kr.wise.commons.cmm.CommonVo;

public class MetaDmnItfVO extends CommonVo {
    //항목
    private String sditmId;

    private String sditmLnm;

    private String sditmPnm;
    
	//도메인
	private String dmnId;

    private String dmnLnm;

    private String dmnPnm;
    //코드도메인유형코드
    private String cdValTypCd;
    private String cdValTypNm;
    
    private String lstEntyId;

    private String lstEntyPnm;

    private String lstEntyLnm;
    
    private String pdmColLnm;
    
    private String pdmColPnm;
    
    //도메인그룹
    private String dmngId;
    
    private String dmngLnm;

    private String dmngPnm;
    
    //info type
    private String infotpId;

	private String infotpLnm;
	
	private String dataType;
	
	private Integer dataLen;
	
	private Integer dataScal;
	
	//조회조건
	//DBC 컬럼명
	private String dbcColNm;
	
	private String dbcColKorNm;

	public String getSditmId() {
		return sditmId;
	}

	public void setSditmId(String sditmId) {
		this.sditmId = sditmId;
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

	public String getCdValTypCd() {
		return cdValTypCd;
	}

	public void setCdValTypCd(String cdValTypCd) {
		this.cdValTypCd = cdValTypCd;
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

	public String getDmngPnm() {
		return dmngPnm;
	}

	public void setDmngPnm(String dmngPnm) {
		this.dmngPnm = dmngPnm;
	}

	public String getInfotpId() {
		return infotpId;
	}

	public void setInfotpId(String infotpId) {
		this.infotpId = infotpId;
	}

	public String getInfotpLnm() {
		return infotpLnm;
	}

	public void setInfotpLnm(String infotpLnm) {
		this.infotpLnm = infotpLnm;
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

	public String getDbcColNm() {
		return dbcColNm;
	}

	public void setDbcColNm(String dbcColNm) {
		this.dbcColNm = dbcColNm;
	}

	public String getDbcColKorNm() {
		return dbcColKorNm;
	}

	public void setDbcColKorNm(String dbcColKorNm) {
		this.dbcColKorNm = dbcColKorNm;
	}

	public String getCdValTypNm() {
		return cdValTypNm;
	}

	public void setCdValTypNm(String cdValTypNm) {
		this.cdValTypNm = cdValTypNm;
	}

	public String getPdmColLnm() {
		return pdmColLnm;
	}

	public void setPdmColLnm(String pdmColLnm) {
		this.pdmColLnm = pdmColLnm;
	}

	public String getPdmColPnm() {
		return pdmColPnm;
	}

	public void setPdmColPnm(String pdmColPnm) {
		this.pdmColPnm = pdmColPnm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MetaDmnItfVO [sditmId=").append(sditmId)
				.append(", sditmLnm=").append(sditmLnm).append(", sditmPnm=")
				.append(sditmPnm).append(", dmnId=").append(dmnId)
				.append(", dmnLnm=").append(dmnLnm).append(", dmnPnm=")
				.append(dmnPnm).append(", cdValTypCd=").append(cdValTypCd)
				.append(", lstEntyId=").append(lstEntyId)
				.append(", lstEntyPnm=").append(lstEntyPnm)
				.append(", lstEntyLnm=").append(lstEntyLnm).append(", dmngId=")
				.append(dmngId).append(", dmngLnm=").append(dmngLnm)
				.append(", dmngPnm=").append(dmngPnm).append(", infotpId=")
				.append(infotpId).append(", infotpLnm=").append(infotpLnm)
				.append(", dataType=").append(dataType).append(", dataLen=")
				.append(dataLen).append(", dataScal=").append(dataScal)
				.append(", dbcColNm=").append(dbcColNm)
				.append(", dbcColKorNm=").append(dbcColKorNm).append("]");
		return builder.toString();
	}

	
    
}