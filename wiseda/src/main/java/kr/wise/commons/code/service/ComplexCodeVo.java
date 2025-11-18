/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : ComplexCodeVo.java
 * 2. Package : 
 * 3. Comment :
 * 4. 작성자  : 이상익 
 * 5. 작성일  : 2015-10-30
 * 6. 변경이력 :
 *    
 */
package kr.wise.commons.code.service;


public class ComplexCodeVo {


     private String coexDtlCd     ;
     private String coexUprnCd       ;
     private String coexLvlNo        ;
     private String coexCdNm         ;
     private String coexCdStsYn      ;
     private String etcCd1           ;
     private String etcCd2           ;
     private String etcCd3           ;
     private String etcCd4           ;
     private String etcCd5           ;
     private String etcCdNm1         ;
     private String etcCdNm2         ;
     private String etcCdNm3         ;
     private String etcCdNm4         ;
     private String etcCdNm5         ;
     private String cdOutlCntn1      ;
     private String cdOutlCntn2      ;
     private String arySqncNo        ;
     private String rmrkCntn         ;

     private String gapStatus;
     
	public String getGapStatus() {
		return gapStatus;
	}

	public void setGapStatus(String gapStatus) {
		this.gapStatus = gapStatus;
	}

	public String getCoexDtlCd() {
		return coexDtlCd;
	}

	public void setCoexDtlCd(String coexDtlCd) {
		this.coexDtlCd = coexDtlCd;
	}

	public String getCoexUprnCd() {
		return coexUprnCd;
	}

	public void setCoexUprnCd(String coexUprnCd) {
		this.coexUprnCd = coexUprnCd;
	}

	public String getCoexLvlNo() {
		return coexLvlNo;
	}

	public void setCoexLvlNo(String coexLvlNo) {
		this.coexLvlNo = coexLvlNo;
	}

	public String getCoexCdNm() {
		return coexCdNm;
	}

	public void setCoexCdNm(String coexCdNm) {
		this.coexCdNm = coexCdNm;
	}

	public String getCoexCdStsYn() {
		return coexCdStsYn;
	}

	public void setCoexCdStsYn(String coexCdStsYn) {
		this.coexCdStsYn = coexCdStsYn;
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
		builder.append("SimpleCodeVo [coexDtlCd=");
		builder.append(coexDtlCd);
		builder.append(", coexUprnCd=");
		builder.append(coexUprnCd);
		builder.append(", coexCdNm=");
		builder.append(coexCdNm);
		builder.append("]");
		return builder.toString();  
	}
}
