package kr.wise.advisor.prepare.udefoutlier.daseimp.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WadUodcDaseImpCol extends WadUodcDaseImp { 
    private String udfOtlDtcId;

    private String creCompId;

    private Integer colSno;

    private String colPnm;

    private String anaVarId;
    
    private String dataType;

    private String colLnm;
    
    
    
    private String colNm;
    
    private Integer colCnt;
    
    private String headerText;


    public Integer getColCnt() {
		return colCnt;
	}

	public void setColCnt(Integer colCnt) {
		this.colCnt = colCnt;
	}

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {
		this.headerText = headerText;
	}

	public String getColNm() {
		return colNm;
	}

	public void setColNm(String colNm) {
		this.colNm = colNm;
	}

	public String getUdfOtlDtcId() {
        return udfOtlDtcId;
    }

    public void setUdfOtlDtcId(String udfOtlDtcId) {
        this.udfOtlDtcId = udfOtlDtcId;
    }

    public String getCreCompId() {
        return creCompId;
    }

    public void setCreCompId(String creCompId) {
        this.creCompId = creCompId;
    }

    public Integer getColSno() {
        return colSno;
    }

    public void setColSno(Integer colSno) {
        this.colSno = colSno;
    }

    public String getColPnm() {
        return colPnm;
    }

    public void setColPnm(String colPnm) {
        this.colPnm = colPnm;
    }

    public String getAnaVarId() {
        return anaVarId;
    }

    public void setAnaVarId(String anaVarId) {
        this.anaVarId = anaVarId;
    }

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColLnm() {
		return colLnm;
	}

	public void setColLnm(String colLnm) {
		this.colLnm = colLnm;
	}
    
	
	public String toString() {
		String str = "udfOtlDtcId >>> " + this.udfOtlDtcId + " creCompId >>> " + this.creCompId + " colSno >>> " + this.colSno;
		str += " colPnm >>> " + this.colPnm + " anaVarId >>> " + this.anaVarId + " dataType >>> " + this.dataType;
		str += " colLnm >>> " + this.colLnm;
		
		return str;
	}
   
}