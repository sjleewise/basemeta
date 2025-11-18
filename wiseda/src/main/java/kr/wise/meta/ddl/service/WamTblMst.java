package kr.wise.meta.ddl.service;

import kr.wise.commons.cmm.CommonVo;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WamTblMst.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  : 
 * 5. 작성자   : 이상익
 * 6. 작성일   : 2015. 11. 17. 
 * </PRE>
 */ 
public class WamTblMst extends CommonVo{
	
	private String objId;
	
    private String mstDdlTblId;

    private String mstDdlTblPnm;
    
    private String mstDdlTblLnm;

    private String histDdlTblId;

    private String histDdlTblPnm;

    private String histDdlTblLnm;

    private String dbSchId;
    
    private String subjLnm;
    
    private String histSubjLnm;

    public String getSubjLnm() {
		return subjLnm;
	}


	public void setSubjLnm(String subjLnm) {
		this.subjLnm = subjLnm;
	}


	public String getHistSubjLnm() {
		return histSubjLnm;
	}


	public void setHistSubjLnm(String histSubjLnm) {
		this.histSubjLnm = histSubjLnm;
	}


	public String getObjId() {
		return objId;
	}


	public void setObjId(String objId) {
		this.objId = objId;
	}


	public String getMstDdlTblId() {
		return mstDdlTblId;
	}


	public void setMstDdlTblId(String mstDdlTblId) {
		this.mstDdlTblId = mstDdlTblId;
	}


	public String getMstDdlTblPnm() {
		return mstDdlTblPnm;
	}


	public void setMstDdlTblPnm(String mstDdlTblPnm) {
		this.mstDdlTblPnm = mstDdlTblPnm;
	}


	public String getMstDdlTblLnm() {
		return mstDdlTblLnm;
	}


	public void setMstDdlTblLnm(String mstDdlTblLnm) {
		this.mstDdlTblLnm = mstDdlTblLnm;
	}


	public String getHistDdlTblId() {
		return histDdlTblId;
	}


	public void setHistDdlTblId(String histDdlTblId) {
		this.histDdlTblId = histDdlTblId;
	}


	public String getHistDdlTblPnm() {
		return histDdlTblPnm;
	}


	public void setHistDdlTblPnm(String histDdlTblPnm) {
		this.histDdlTblPnm = histDdlTblPnm;
	}


	public String getHistDdlTblLnm() {
		return histDdlTblLnm;
	}


	public void setHistDdlTblLnm(String histDdlTblLnm) {
		this.histDdlTblLnm = histDdlTblLnm;
	}


	public String getDbSchId() {
		return dbSchId;
	}


	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}


	

 
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("");
		return builder.toString()+super.toString();
	}
    
}