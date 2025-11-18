/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : SimpleCodeVo.java
 * 2. Package : 
 * 3. Comment :
 * 4. 작성자  : 이상익 
 * 5. 작성일  : 2015-10-30
 * 6. 변경이력 :
 *    
 */
package kr.wise.commons.code.service;


public class SimpleCodeVo {


         private String smplLccd     ; 
     private String smplMccd     ; 
     private String smplSccd     ; 
     private String smplLccdNm   ; 
     private String smplMccdNm   ; 
     private String smplSccdNm   ; 
     private String smplCdStsYn  ; 
     private String etcCd1       ; 
     private String etcCd2       ; 
     private String etcCd3       ; 
     private String etcCd4       ; 
     private String etcCd5       ; 
     private String etcCdNm1     ; 
     private String etcCdNm2     ; 
     private String etcCdNm3     ; 
     private String etcCdNm4     ; 
     private String etcCdNm5     ; 
     private String cdOutlCntn1  ; 
     private String cdOutlCntn2  ; 
     private String arySqncNo    ; 
     private String rmrkCntn     ; 
     private String gapStatus ;
	
	
	public String getGapStatus() {
		return gapStatus;
	}



	public void setGapStatus(String gapStatus) {
		this.gapStatus = gapStatus;
	}



	public String getSmplLccd() {
		return smplLccd;
	}



	public void setSmplLccd(String smplLccd) {
		this.smplLccd = smplLccd;
	}



	public String getSmplMccd() {
		return smplMccd;
	}



	public void setSmplMccd(String smplMccd) {
		this.smplMccd = smplMccd;
	}



	public String getSmplSccd() {
		return smplSccd;
	}



	public void setSmplSccd(String smplSccd) {
		this.smplSccd = smplSccd;
	}



	public String getSmplLccdNm() {
		return smplLccdNm;
	}



	public void setSmplLccdNm(String smplLccdNm) {
		this.smplLccdNm = smplLccdNm;
	}



	public String getSmplMccdNm() {
		return smplMccdNm;
	}



	public void setSmplMccdNm(String smplMccdNm) {
		this.smplMccdNm = smplMccdNm;
	}



	public String getSmplSccdNm() {
		return smplSccdNm;
	}



	public void setSmplSccdNm(String smplSccdNm) {
		this.smplSccdNm = smplSccdNm;
	}



	public String getSmplCdStsYn() {
		return smplCdStsYn;
	}



	public void setSmplCdStsYn(String smplCdStsYn) {
		this.smplCdStsYn = smplCdStsYn;
	}



	public String getEtcCd1() {
		return etcCd1;
	}



	public void setEtcCd1(String etcCd1) {
		this.etcCd1 = etcCd1;
	}



	public String getEtcCd2() {
		return etcCd2;
	}



	public void setEtcCd2(String etcCd2) {
		this.etcCd2 = etcCd2;
	}



	public String getEtcCd3() {
		return etcCd3;
	}



	public void setEtcCd3(String etcCd3) {
		this.etcCd3 = etcCd3;
	}



	public String getEtcCd4() {
		return etcCd4;
	}



	public void setEtcCd4(String etcCd4) {
		this.etcCd4 = etcCd4;
	}



	public String getEtcCd5() {
		return etcCd5;
	}



	public void setEtcCd5(String etcCd5) {
		this.etcCd5 = etcCd5;
	}



	public String getEtcCdNm1() {
		return etcCdNm1;
	}



	public void setEtcCdNm1(String etcCdNm1) {
		this.etcCdNm1 = etcCdNm1;
	}



	public String getEtcCdNm2() {
		return etcCdNm2;
	}



	public void setEtcCdNm2(String etcCdNm2) {
		this.etcCdNm2 = etcCdNm2;
	}



	public String getEtcCdNm3() {
		return etcCdNm3;
	}



	public void setEtcCdNm3(String etcCdNm3) {
		this.etcCdNm3 = etcCdNm3;
	}



	public String getEtcCdNm4() {
		return etcCdNm4;
	}



	public void setEtcCdNm4(String etcCdNm4) {
		this.etcCdNm4 = etcCdNm4;
	}



	public String getEtcCdNm5() {
		return etcCdNm5;
	}



	public void setEtcCdNm5(String etcCdNm5) {
		this.etcCdNm5 = etcCdNm5;
	}



	public String getCdOutlCntn1() {
		return cdOutlCntn1;
	}



	public void setCdOutlCntn1(String cdOutlCntn1) {
		this.cdOutlCntn1 = cdOutlCntn1;
	}



	public String getCdOutlCntn2() {
		return cdOutlCntn2;
	}



	public void setCdOutlCntn2(String cdOutlCntn2) {
		this.cdOutlCntn2 = cdOutlCntn2;
	}



	public String getArySqncNo() {
		return arySqncNo;
	}



	public void setArySqncNo(String arySqncNo) {
		this.arySqncNo = arySqncNo;
	}



	public String getRmrkCntn() {
		return rmrkCntn;
	}



	public void setRmrkCntn(String rmrkCntn) {
		this.rmrkCntn = rmrkCntn;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SimpleCodeVo [smplLccd=");
		builder.append(smplLccd);
		builder.append(", smplMccd=");
		builder.append(smplMccd);
		builder.append(", smplSccd=");
		builder.append(smplSccd);
		builder.append("]");
		return builder.toString();  
	}
}
