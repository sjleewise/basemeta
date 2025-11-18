package kr.wise.meta.ddl.service;

import kr.wise.commons.cmm.CommonVo;

public class WamDdlGrt extends CommonVo{
	
	private String ddlGrtId;
	
	private String grtorDbId;
	
	private String grtorDbPnm;
	
	private String grtorSchId;
	
	private String grtorSchPnm;
	
	private String ddlObjId;
	
	private String ddlObjPnm;
	
	private String ddlObjTypCd;
	
	private String grtedDbId;
	
	private String grtedDbPnm;
	
	private String grtedSchId;
	
	private String grtedSchPnm;
	
	private String selectYn;
	
	private String insertYn;
	
	private String updateYn;
	
	private String deleteYn;
	
	private String executeYn;
	
	private String synonymYn;
	
	private String scrtInfo;
	
	private String rqstResn;
	
	private String ddlObjLnm;
	
	private String prcTypCd;
    
	private String prcDt;

	private String prcDbaId;

	private String prcDbaNm;

	public String getDdlGrtId() {
		return ddlGrtId;
	}

	public void setDdlGrtId(String ddlGrtId) {
		this.ddlGrtId = ddlGrtId;
	}

	public String getGrtorDbId() {
		return grtorDbId;
	}

	public void setGrtorDbId(String grtorDbId) {
		this.grtorDbId = grtorDbId;
	}

	public String getGrtorDbPnm() {
		return grtorDbPnm;
	}

	public void setGrtorDbPnm(String grtorDbPnm) {
		this.grtorDbPnm = grtorDbPnm;
	}

	public String getGrtorSchId() {
		return grtorSchId;
	}

	public void setGrtorSchId(String grtorSchId) {
		this.grtorSchId = grtorSchId;
	}

	public String getGrtorSchPnm() {
		return grtorSchPnm;
	}

	public void setGrtorSchPnm(String grtorSchPnm) {
		this.grtorSchPnm = grtorSchPnm;
	}

	public String getDdlObjId() {
		return ddlObjId;
	}

	public void setDdlObjId(String ddlObjId) {
		this.ddlObjId = ddlObjId;
	}

	public String getDdlObjPnm() {
		return ddlObjPnm;
	}

	public void setDdlObjPnm(String ddlObjPnm) {
		this.ddlObjPnm = ddlObjPnm;
	}

	public String getDdlObjTypCd() {
		return ddlObjTypCd;
	}

	public void setDdlObjTypCd(String ddlObjTypCd) {
		this.ddlObjTypCd = ddlObjTypCd;
	}

	public String getGrtedDbId() {
		return grtedDbId;
	}

	public void setGrtedDbId(String grtedDbId) {
		this.grtedDbId = grtedDbId;
	}

	public String getGrtedDbPnm() {
		return grtedDbPnm;
	}

	public void setGrtedDbPnm(String grtedDbPnm) {
		this.grtedDbPnm = grtedDbPnm;
	}

	public String getGrtedSchId() {
		return grtedSchId;
	}

	public void setGrtedSchId(String grtedSchId) {
		this.grtedSchId = grtedSchId;
	}

	public String getGrtedSchPnm() {
		return grtedSchPnm;
	}

	public void setGrtedSchPnm(String grtedSchPnm) {
		this.grtedSchPnm = grtedSchPnm;
	}

	public String getSelectYn() {
		return selectYn;
	}

	public void setSelectYn(String selectYn) {
		this.selectYn = selectYn;
	}

	public String getInsertYn() {
		return insertYn;
	}

	public void setInsertYn(String insertYn) {
		this.insertYn = insertYn;
	}

	public String getUpdateYn() {
		return updateYn;
	}

	public void setUpdateYn(String updateYn) {
		this.updateYn = updateYn;
	}

	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}

	public String getExecuteYn() {
		return executeYn;
	}

	public void setExecuteYn(String executeYn) {
		this.executeYn = executeYn;
	}

	public String getSynonymYn() {
		return synonymYn;
	}

	public void setSynonymYn(String synonymYn) {
		this.synonymYn = synonymYn;
	}

	public String getScrtInfo() {
		return scrtInfo;
	}

	public void setScrtInfo(String scrtInfo) {
		this.scrtInfo = scrtInfo;
	}

	public String getRqstResn() {
		return rqstResn;
	}

	public void setRqstResn(String rqstResn) {
		this.rqstResn = rqstResn;
	}

	public String getDdlObjLnm() {
		return ddlObjLnm;
	}

	public void setDdlObjLnm(String ddlObjLnm) {
		this.ddlObjLnm = ddlObjLnm;
	}

	public String getPrcTypCd() {
		return prcTypCd;
	}

	public void setPrcTypCd(String prcTypCd) {
		this.prcTypCd = prcTypCd;
	}

	public String getPrcDt() {
		return prcDt;
	}

	public void setPrcDt(String prcDt) {
		this.prcDt = prcDt;
	}

	public String getPrcDbaId() {
		return prcDbaId;
	}

	public void setPrcDbaId(String prcDbaId) {
		this.prcDbaId = prcDbaId;
	}

	public String getPrcDbaNm() {
		return prcDbaNm;
	}

	public void setPrcDbaNm(String prcDbaNm) {
		this.prcDbaNm = prcDbaNm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamDdlGrt [ddlGrtId=").append(ddlGrtId)
				.append(", grtorDbId=").append(grtorDbId)
				.append(", grtorDbPnm=").append(grtorDbPnm)
				.append(", grtorSchId=").append(grtorSchId)
				.append(", grtorSchPnm=").append(grtorSchPnm)
				.append(", ddlObjId=").append(ddlObjId).append(", ddlObjPnm=")
				.append(ddlObjPnm).append(", ddlObjTypCd=").append(ddlObjTypCd)
				.append(", grtedDbId=").append(grtedDbId)
				.append(", grtedDbPnm=").append(grtedDbPnm)
				.append(", grtedSchId=").append(grtedSchId)
				.append(", grtedSchPnm=").append(grtedSchPnm)
				.append(", selectYn=").append(selectYn).append(", insertYn=")
				.append(insertYn).append(", updateYn=").append(updateYn)
				.append(", deleteYn=").append(deleteYn).append(", executeYn=")
				.append(executeYn).append(", synonymYn=").append(synonymYn)
				.append("]");
		return builder.toString();
	}
	
	
}