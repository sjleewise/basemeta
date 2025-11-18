package kr.wise.advisor.prepare.udefoutlier.coldasedv.service;

public class WadUodcColDaseDvCol extends WadUodcColDaseDv{ 
    private String udfOtlDtcId;

	private String creCompId;

	private Integer colSno;

	private String colPnm;
	
	private String colLnm;
	
	private String dataType;

	private String anaVarId;

   
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

	public String getColLnm() {
		return colLnm;
	} 

	public void setColLnm(String colLnm) {
		this.colLnm = colLnm;
	}

	public String getDataType() { 
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	
	
}