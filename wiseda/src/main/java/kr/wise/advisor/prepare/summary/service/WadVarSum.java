package kr.wise.advisor.prepare.summary.service;

import kr.wise.advisor.prepare.dataset.service.WadAnaVar;

public class WadVarSum extends WadAnaVar{
    private String anlVarId;

    private String varType; //변수타입

    private String totCnt; //전체수

    private String mdnVal; //중앙값

    private String maxVal; //최대값

    private String topVal; //변수타입
    private String frqVal; //최빈값수

    private String minVal; //최소값

    private String unqVal;  //유니크수
 
    private String mnVal;  //평균

    private String stdDvt; //표준편차

    private String qrtCnt1; //1분위수
 
    private String qrtCnt3; //3분위수  

	public String getAnlVarId() {
		return anlVarId;
	}

	public void setAnlVarId(String anlVarId) {
		this.anlVarId = anlVarId;
	}

	public String getVarType() {
		return varType;
	}

	public void setVarType(String varType) {
		this.varType = varType;
	}

	public String getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(String totCnt) {
		this.totCnt = totCnt;
	}

	public String getMdnVal() {
		return mdnVal;
	}

	public void setMdnVal(String mdnVal) {
		this.mdnVal = mdnVal;
	}

	public String getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}

	public String getTopVal() {
		return topVal;
	}

	public void setTopVal(String topVal) {
		this.topVal = topVal;
	}

	public String getFrqVal() {
		return frqVal;
	}

	public void setFrqVal(String frqVal) {
		this.frqVal = frqVal;
	}

	public String getMinVal() {
		return minVal;
	}

	public void setMinVal(String minVal) {
		this.minVal = minVal;
	}

	public String getUnqVal() {
		return unqVal;
	}

	public void setUnqVal(String unqVal) {
		this.unqVal = unqVal;
	}

	public String getMnVal() {
		return mnVal;
	}

	public void setMnVal(String mnVal) {
		this.mnVal = mnVal;
	}

	public String getStdDvt() {
		return stdDvt;
	}

	public void setStdDvt(String stdDvt) {
		this.stdDvt = stdDvt;
	}

	public String getQrtCnt1() {
		return qrtCnt1;
	}

	public void setQrtCnt1(String qrtCnt1) {
		this.qrtCnt1 = qrtCnt1;
	}

	public String getQrtCnt3() {
		return qrtCnt3;
	}

	public void setQrtCnt3(String qrtCnt3) {
		this.qrtCnt3 = qrtCnt3;
	}


    

}