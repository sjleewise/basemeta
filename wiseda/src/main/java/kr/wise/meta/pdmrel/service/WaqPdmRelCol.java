package kr.wise.meta.pdmrel.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqPdmRelCol extends CommonVo {

	private String pdmRelColId;
	
	private String pdmRelId;

	private String pdmRelPnm; 

	private String pdmRelLnm;
    
	private String paSubjId;
	
	private String paFullPath;

	private String paTblId;

	private String paTblPnm;
	
	private String paColId;

    private String paColPnm;

    private String chSubjId;
    
    private String chFullPath;
    
    private String chTblId;

    private String chTblPnm;
        
    private String chColId;
    
    private String chColPnm;
    
	private String relTypCd;

    private String crdTypCd;

    private String paOptTypCd;

    
    
	public String getPdmRelColId() {
		return pdmRelColId;
	}



	public void setPdmRelColId(String pdmRelColId) {
		this.pdmRelColId = pdmRelColId;
	}



	public String getPdmRelId() {
		return pdmRelId;
	}



	public void setPdmRelId(String pdmRelId) {
		this.pdmRelId = pdmRelId;
	}



	public String getPdmRelPnm() {
		return pdmRelPnm;
	}



	public void setPdmRelPnm(String pdmRelPnm) {
		this.pdmRelPnm = pdmRelPnm;
	}



	public String getPdmRelLnm() {
		return pdmRelLnm;
	}



	public void setPdmRelLnm(String pdmRelLnm) {
		this.pdmRelLnm = pdmRelLnm;
	}



	public String getPaSubjId() {
		return paSubjId;
	}



	public void setPaSubjId(String paSubjId) {
		this.paSubjId = paSubjId;
	}



	public String getPaFullPath() {
		return paFullPath;
	}



	public void setPaFullPath(String paFullPath) {
		this.paFullPath = paFullPath;
	}



	public String getPaTblId() {
		return paTblId;
	}



	public void setPaTblId(String paTblId) {
		this.paTblId = paTblId;
	}



	public String getPaTblPnm() {
		return paTblPnm;
	}



	public void setPaTblPnm(String paTblPnm) {
		this.paTblPnm = paTblPnm;
	}



	public String getPaColId() {
		return paColId;
	}



	public void setPaColId(String paColId) {
		this.paColId = paColId;
	}



	public String getPaColPnm() {
		return paColPnm;
	}



	public void setPaColPnm(String paColPnm) {
		this.paColPnm = paColPnm;
	}



	public String getChSubjId() {
		return chSubjId;
	}



	public void setChSubjId(String chSubjId) {
		this.chSubjId = chSubjId;
	}



	public String getChFullPath() {
		return chFullPath;
	}



	public void setChFullPath(String chFullPath) {
		this.chFullPath = chFullPath;
	}



	public String getChTblId() {
		return chTblId;
	}



	public void setChTblId(String chTblId) {
		this.chTblId = chTblId;
	}



	public String getChTblPnm() {
		return chTblPnm;
	}



	public void setChTblPnm(String chTblPnm) {
		this.chTblPnm = chTblPnm;
	}



	public String getChColId() {
		return chColId;
	}



	public void setChColId(String chColId) {
		this.chColId = chColId;
	}



	public String getChColPnm() {
		return chColPnm;
	}



	public void setChColPnm(String chColPnm) {
		this.chColPnm = chColPnm;
	}



	public String getRelTypCd() {
		return relTypCd;
	}



	public void setRelTypCd(String relTypCd) {
		this.relTypCd = relTypCd;
	}



	public String getCrdTypCd() {
		return crdTypCd;
	}



	public void setCrdTypCd(String crdTypCd) {
		this.crdTypCd = crdTypCd;
	}



	public String getPaOptTypCd() {
		return paOptTypCd;
	}



	public void setPaOptTypCd(String paOptTypCd) {
		this.paOptTypCd = paOptTypCd;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqPdmRel [pdmRelId=").append(pdmRelId)
				.append(", pdmRelPnm=").append(pdmRelPnm)
				.append(", pdmRelLnm=").append(pdmRelLnm).append(", paSubjId=")
				.append(paSubjId).append(", paFullPath=").append(paFullPath)
				.append(", paTblId=").append(paTblId).append(", paTblPnm=")
				.append(paTblPnm).append(", paColId=").append(paColId)
				.append(", paColPnm=").append(paColPnm).append(", chSubjId=")
				.append(chSubjId).append(", chFullPath=").append(chFullPath)
				.append(", chTblId=").append(chTblId).append(", chTblPnm=")
				.append(chTblPnm).append(", chColId=").append(chColId)
				.append(", chColPnm=").append(chColPnm) 				
				.append(", relTypCd=").append(relTypCd).append(", crdTypCd=")
				.append(crdTypCd).append(", paOptTypCd=").append(paOptTypCd)
				.append("]");
		return builder.toString()+super.toString();
	}


}