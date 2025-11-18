package kr.wise.advisor.prepare.outlier.service;

import kr.wise.commons.cmm.CommonVo;

public class WadOtlVal extends CommonVo {
    private String otlDtcId; //이상값 탐지ID

    private Integer varSno;	 //변수 순번

    private String anlVarId; //변수 ID
    
    private String otlAddCnd; //추가 조건
    
    private String anaVarChk; //체크여부 
    
    private String condCd; //조건(AND, OR)
    private String oprCd; //연산자(>, <, = ...)
    private String oprVal; //값
    
    private String dqiId; //품질지표ID
    
    private String weight;

    public String getOtlDtcId() {
        return otlDtcId;
    }

    public void setOtlDtcId(String otlDtcId) {
        this.otlDtcId = otlDtcId;
    }

    public Integer getVarSno() {
        return varSno;
    }

    public void setVarSno(Integer varSno) {
        this.varSno = varSno;
    }

    public String getAnlVarId() {
        return anlVarId;
    }

    public void setAnlVarId(String anlVarId) {
        this.anlVarId = anlVarId;
    }

    
	public String getOtlAddCnd() {
		return otlAddCnd;
	}

	public void setOtlAddCnd(String otlAddCnd) {
		this.otlAddCnd = otlAddCnd;
	}

	public String getAnaVarChk() {
		return anaVarChk;
	}

	public void setAnaVarChk(String anaVarChk) {
		this.anaVarChk = anaVarChk; 
	}

	public String getCondCd() {
		return condCd;
	}

	public void setCondCd(String condCd) {
		this.condCd = condCd;
	}

	public String getOprCd() {
		return oprCd;
	}

	public void setOprCd(String oprCd) {
		this.oprCd = oprCd;
	}

	public String getOprVal() {
		return oprVal;
	}

	public void setOprVal(String oprVal) {
		this.oprVal = oprVal;
	}

	public String getDqiId() {
		return dqiId;
	}

	public void setDqiId(String dqiId) {
		this.dqiId = dqiId;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

    
    
    
}