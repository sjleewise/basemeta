package kr.wise.executor.dm;

import java.math.BigDecimal;


public class ProfileResultDM {

	private Long totCnt;
	private BigDecimal errCnt;
	
	private BigDecimal nullCnt;
	private BigDecimal spaceCnt;
	private String minVal1;
	private String minVal2;
	private String minVal3;
	private String maxVal1;
	private String maxVal2;
	private String maxVal3;
	private BigDecimal minLen;
	private BigDecimal maxLen;
	
	//��ǥ�ص����� �Ǻ� 
	private String dateYn;
	private String telYn;
	private String spaceRt;
	private String crlfYn;
	private String alphaYn;
	private String dataFmt;
	private String numYn;
	private String hundRt;
	private String cntRt;
	
	//표준편차
	private String stddevVal;
	//분산
	private String varianceVal;
	//평균
	private String avgVal;
	//유일값수
	private String unqCnt;
	//최소빈도값
	private String minCntVal;
	//최대빈도값
	private String maxCntVal;
	
	public Long getTotCnt() {
		return totCnt;
	}
	public void setTotCnt(Long totCnt) {
		this.totCnt = totCnt;
	}
	public BigDecimal getErrCnt() {
		return errCnt;
	}
	public void setErrCnt(BigDecimal errCnt) {
		this.errCnt = errCnt;
	}
	public BigDecimal getNullCnt() {
		return nullCnt;
	}
	public void setNullCnt(BigDecimal nullCnt) {
		this.nullCnt = nullCnt;
	}
	public BigDecimal getSpaceCnt() {
		return spaceCnt;
	}
	public void setSpaceCnt(BigDecimal spaceCnt) {
		this.spaceCnt = spaceCnt;
	}
	public String getMinVal1() {
		return minVal1;
	}
	public void setMinVal1(String minVal1) {
		this.minVal1 = minVal1;
	}
	public String getMinVal2() {
		return minVal2;
	}
	public void setMinVal2(String minVal2) {
		this.minVal2 = minVal2;
	}
	public String getMinVal3() {
		return minVal3;
	}
	public void setMinVal3(String minVal3) {
		this.minVal3 = minVal3;
	}
	public String getMaxVal1() {
		return maxVal1;
	}
	public void setMaxVal1(String maxVal1) {
		this.maxVal1 = maxVal1;
	}
	public String getMaxVal2() {
		return maxVal2;
	}
	public void setMaxVal2(String maxVal2) {
		this.maxVal2 = maxVal2;
	}
	public String getMaxVal3() {
		return maxVal3;
	}
	public void setMaxVal3(String maxVal3) {
		this.maxVal3 = maxVal3;
	}
	public BigDecimal getMinLen() {
		return minLen;
	}
	public void setMinLen(BigDecimal minLen) {
		this.minLen = minLen;
	}
	public BigDecimal getMaxLen() {
		return maxLen;
	}
	public void setMaxLen(BigDecimal maxLen) {
		this.maxLen = maxLen;
	}
	
	public String getDateYn() {
		return dateYn;
	}
	public void setDateYn(String dateYn) {
		this.dateYn = dateYn;
	}
	
	public String getTelYn() {
		return telYn;
	}
	public void setTelYn(String telYn) {
		this.telYn = telYn;
	}
		
	public String getSpaceRt() {
		return spaceRt;
	}
	public void setSpaceRt(String spaceRt) {
		this.spaceRt = spaceRt;
	}	
	public String getCrlfYn() {
		return crlfYn;
	}
	public void setCrlfYn(String crlfYn) {
		this.crlfYn = crlfYn;
	}	
	public String getAlphaYn() {
		return alphaYn;
	}
	public void setAlphaYn(String alphaYn) {
		this.alphaYn = alphaYn;
	}	
	public String getDataFmt() {
		return dataFmt;
	}
	public void setDataFmt(String dataFmt) {
		this.dataFmt = dataFmt;
	}	
	public String getNumYn() {
		return numYn;
	}
	public void setNumYn(String numYn) {
		this.numYn = numYn;
	}
	
	public String getHundRt() {
		return hundRt;
	}
	public void setHundRt(String hundRt) {
		this.hundRt = hundRt;
	}	
	public String getCntRt() {
		return cntRt;
	}
	public void setCntRt(String cntRt) {
		this.cntRt = cntRt;
	}
	
	public String getStddevVal() {
		return stddevVal;
	}
	public void setStddevVal(String stddevVal) {
		this.stddevVal = stddevVal;
	}
	public String getVarianceVal() {
		return varianceVal;
	}
	public void setVarianceVal(String varianceVal) {
		this.varianceVal = varianceVal;
	}
	public String getAvgVal() {
		return avgVal;
	}
	public void setAvgVal(String avgVal) {
		this.avgVal = avgVal;
	}
	public String getUnqCnt() {
		return unqCnt;
	}
	public void setUnqCnt(String unqCnt) {
		this.unqCnt = unqCnt;
	}
	public String getMinCntVal() {
		return minCntVal;
	}
	public void setMinCntVal(String minCntVal) {
		this.minCntVal = minCntVal;
	}
	public String getMaxCntVal() {
		return maxCntVal;
	}
	public void setMaxCntVal(String maxCntVal) {
		this.maxCntVal = maxCntVal;
	}
	/** insomnia */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProfileResultDM [totCnt=").append(totCnt).append(", errCnt=").append(errCnt)
				.append(", nullCnt=").append(nullCnt).append(", spaceCnt=").append(spaceCnt).append(", minVal1=")
				.append(minVal1).append(", minVal2=").append(minVal2).append(", minVal3=").append(minVal3)
				.append(", maxVal1=").append(maxVal1).append(", maxVal2=").append(maxVal2).append(", maxVal3=")
				.append(maxVal3).append(", minLen=").append(minLen).append(", maxLen=").append(maxLen).append("]");
		return builder.toString();
	}
}
