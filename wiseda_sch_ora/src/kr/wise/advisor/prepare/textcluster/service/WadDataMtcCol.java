package kr.wise.advisor.prepare.textcluster.service;

import kr.wise.commons.cmm.CommonVo;

public class WadDataMtcCol extends CommonVo {
    
	private String mtcId       ;
	private String mtcColSno   ;
	private String srcDbcColNm ;
	private String tgtDbcColNm ;
	
	
	public String getMtcId() {
		return mtcId;
	}
	public void setMtcId(String mtcId) {
		this.mtcId = mtcId;
	}
	public String getMtcColSno() {
		return mtcColSno;
	}
	public void setMtcColSno(String mtcColSno) {
		this.mtcColSno = mtcColSno;
	}
	public String getSrcDbcColNm() {
		return srcDbcColNm;
	}
	public void setSrcDbcColNm(String srcDbcColNm) {
		this.srcDbcColNm = srcDbcColNm;
	}
	public String getTgtDbcColNm() {
		return tgtDbcColNm;
	}
	public void setTgtDbcColNm(String tgtDbcColNm) {
		this.tgtDbcColNm = tgtDbcColNm;
	}
	
	

}