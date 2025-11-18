package kr.wise.commons.api.service;

import java.util.Date;

public class WamDdlTblApi {

    private String ddlTblPnm;

    private String ddlTblLnm;

    private String dbSchPnm;
    
    private String prcTypCd;
    
    private String prcDt;
    
    private String prcDbaId;
    
    private String prcDbaNm;
    
    private String dbSchLnm;
    
    private String dbConnTrgLnm;
    
    private String dbConnTrgPnm;
    
    private String pdmTblLnm;
    
    private String pdmTblPnm;
    
    private String objId;
    
    private String objLnm;
    
    private String objPnm;
    
    private String objDcd;
    
    private String tblTypCd;
    
    private String searchBgnDe;
    
    private String searchEndDe;
    
    private String ddlColLnm;
    
    private String scrnDcd;
    
    private String ddlIdxPnm;
    
    private String ddlIdxLnm;
    
    private String partTblYn;
    
    private String ddlTrgDcd;
    
    private String srcDdlTrgDcd;
    
    private String srcDbSchId;
    
    private String srcDbSchPnm;
    
    private String srcDdlTblId;
    
    private String srcDdlTblPnm;
        
    private String tgtDdlTrgDcd;
    
    private String tgtDbSchId;
    
    private String tgtDbSchPnm;
    
    private String tgtDdlTblId;
        
    private String tgtDdlTblPnm;
    
  //commonVo 필요한것만 추림
  	private String objDescn;
  	
  	private Integer objVers;
  	
  	private String regTypCd;			//등록유형코드 ('C', 'U', 'D)
  	
  	private Date frsRqstDtm;			//최초요청일시
  	
  	private String frsRqstUserId;		//최초요청자 ID
  	
  	private String frsRqstUserNm;		//최초요청자
  	
  	private Date rqstDtm;				//요청일시
  	
  	private String rqstUserId;			//요청자 ID
  	
  	private String rqstUserNm;			//요청자
  	
  	private Date aprvDtm;				//승인일시
  	
  	private String aprvUserId;			//승인자 ID
  	
  	private String aprvUserNm;			//승인자

    private String fullPath;	//계층형일 경우 전체 경로 (예: 모델>상위주제영역>주제영역)
    
    private String persInfoCnvYn; 		//개인정보여부
    
	public String getPersInfoCnvYn() {
		return persInfoCnvYn;
	}

	public void setPersInfoCnvYn(String persInfoCnvYn) {
		this.persInfoCnvYn = persInfoCnvYn;
	}

	public String getDdlTblPnm() {
		return ddlTblPnm;
	}

	public void setDdlTblPnm(String ddlTblPnm) {
		this.ddlTblPnm = ddlTblPnm;
	}

	public String getDdlTblLnm() {
		return ddlTblLnm;
	}

	public void setDdlTblLnm(String ddlTblLnm) {
		this.ddlTblLnm = ddlTblLnm;
	}

	public String getDbSchPnm() {
		return dbSchPnm;
	}

	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
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

	public String getDbSchLnm() {
		return dbSchLnm;
	}

	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}

	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}

	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
	}

	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}

	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}

	public String getPdmTblLnm() {
		return pdmTblLnm;
	}

	public void setPdmTblLnm(String pdmTblLnm) {
		this.pdmTblLnm = pdmTblLnm;
	}

	public String getPdmTblPnm() {
		return pdmTblPnm;
	}

	public void setPdmTblPnm(String pdmTblPnm) {
		this.pdmTblPnm = pdmTblPnm;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getObjLnm() {
		return objLnm;
	}

	public void setObjLnm(String objLnm) {
		this.objLnm = objLnm;
	}

	public String getObjPnm() {
		return objPnm;
	}

	public void setObjPnm(String objPnm) {
		this.objPnm = objPnm;
	}

	public String getObjDcd() {
		return objDcd;
	}

	public void setObjDcd(String objDcd) {
		this.objDcd = objDcd;
	}

	public String getTblTypCd() {
		return tblTypCd;
	}

	public void setTblTypCd(String tblTypCd) {
		this.tblTypCd = tblTypCd;
	}

	public String getSearchBgnDe() {
		return searchBgnDe;
	}

	public void setSearchBgnDe(String searchBgnDe) {
		this.searchBgnDe = searchBgnDe;
	}

	public String getSearchEndDe() {
		return searchEndDe;
	}

	public void setSearchEndDe(String searchEndDe) {
		this.searchEndDe = searchEndDe;
	}

	public String getDdlColLnm() {
		return ddlColLnm;
	}

	public void setDdlColLnm(String ddlColLnm) {
		this.ddlColLnm = ddlColLnm;
	}

	public String getScrnDcd() {
		return scrnDcd;
	}

	public void setScrnDcd(String scrnDcd) {
		this.scrnDcd = scrnDcd;
	}

	public String getDdlIdxPnm() {
		return ddlIdxPnm;
	}

	public void setDdlIdxPnm(String ddlIdxPnm) {
		this.ddlIdxPnm = ddlIdxPnm;
	}

	public String getDdlIdxLnm() {
		return ddlIdxLnm;
	}

	public void setDdlIdxLnm(String ddlIdxLnm) {
		this.ddlIdxLnm = ddlIdxLnm;
	}

	public String getPartTblYn() {
		return partTblYn;
	}

	public void setPartTblYn(String partTblYn) {
		this.partTblYn = partTblYn;
	}

	public String getDdlTrgDcd() {
		return ddlTrgDcd;
	}

	public void setDdlTrgDcd(String ddlTrgDcd) {
		this.ddlTrgDcd = ddlTrgDcd;
	}

	public String getSrcDdlTrgDcd() {
		return srcDdlTrgDcd;
	}

	public void setSrcDdlTrgDcd(String srcDdlTrgDcd) {
		this.srcDdlTrgDcd = srcDdlTrgDcd;
	}

	public String getSrcDbSchId() {
		return srcDbSchId;
	}

	public void setSrcDbSchId(String srcDbSchId) {
		this.srcDbSchId = srcDbSchId;
	}

	public String getSrcDbSchPnm() {
		return srcDbSchPnm;
	}

	public void setSrcDbSchPnm(String srcDbSchPnm) {
		this.srcDbSchPnm = srcDbSchPnm;
	}

	public String getSrcDdlTblId() {
		return srcDdlTblId;
	}

	public void setSrcDdlTblId(String srcDdlTblId) {
		this.srcDdlTblId = srcDdlTblId;
	}

	public String getSrcDdlTblPnm() {
		return srcDdlTblPnm;
	}

	public void setSrcDdlTblPnm(String srcDdlTblPnm) {
		this.srcDdlTblPnm = srcDdlTblPnm;
	}

	public String getTgtDdlTrgDcd() {
		return tgtDdlTrgDcd;
	}

	public void setTgtDdlTrgDcd(String tgtDdlTrgDcd) {
		this.tgtDdlTrgDcd = tgtDdlTrgDcd;
	}

	public String getTgtDbSchId() {
		return tgtDbSchId;
	}

	public void setTgtDbSchId(String tgtDbSchId) {
		this.tgtDbSchId = tgtDbSchId;
	}

	public String getTgtDbSchPnm() {
		return tgtDbSchPnm;
	}

	public void setTgtDbSchPnm(String tgtDbSchPnm) {
		this.tgtDbSchPnm = tgtDbSchPnm;
	}

	public String getTgtDdlTblId() {
		return tgtDdlTblId;
	}

	public void setTgtDdlTblId(String tgtDdlTblId) {
		this.tgtDdlTblId = tgtDdlTblId;
	}

	public String getTgtDdlTblPnm() {
		return tgtDdlTblPnm;
	}

	public void setTgtDdlTblPnm(String tgtDdlTblPnm) {
		this.tgtDdlTblPnm = tgtDdlTblPnm;
	}

	public String getObjDescn() {
		return objDescn;
	}

	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}

	public Integer getObjVers() {
		return objVers;
	}

	public void setObjVers(Integer objVers) {
		this.objVers = objVers;
	}

	public String getRegTypCd() {
		return regTypCd;
	}

	public void setRegTypCd(String regTypCd) {
		this.regTypCd = regTypCd;
	}

	public Date getFrsRqstDtm() {
		return frsRqstDtm;
	}

	public void setFrsRqstDtm(Date frsRqstDtm) {
		this.frsRqstDtm = frsRqstDtm;
	}

	public String getFrsRqstUserId() {
		return frsRqstUserId;
	}

	public void setFrsRqstUserId(String frsRqstUserId) {
		this.frsRqstUserId = frsRqstUserId;
	}

	public String getFrsRqstUserNm() {
		return frsRqstUserNm;
	}

	public void setFrsRqstUserNm(String frsRqstUserNm) {
		this.frsRqstUserNm = frsRqstUserNm;
	}

	public Date getRqstDtm() {
		return rqstDtm;
	}

	public void setRqstDtm(Date rqstDtm) {
		this.rqstDtm = rqstDtm;
	}

	public String getRqstUserId() {
		return rqstUserId;
	}

	public void setRqstUserId(String rqstUserId) {
		this.rqstUserId = rqstUserId;
	}

	public String getRqstUserNm() {
		return rqstUserNm;
	}

	public void setRqstUserNm(String rqstUserNm) {
		this.rqstUserNm = rqstUserNm;
	}

	public Date getAprvDtm() {
		return aprvDtm;
	}

	public void setAprvDtm(Date aprvDtm) {
		this.aprvDtm = aprvDtm;
	}

	public String getAprvUserId() {
		return aprvUserId;
	}

	public void setAprvUserId(String aprvUserId) {
		this.aprvUserId = aprvUserId;
	}

	public String getAprvUserNm() {
		return aprvUserNm;
	}

	public void setAprvUserNm(String aprvUserNm) {
		this.aprvUserNm = aprvUserNm;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

}
