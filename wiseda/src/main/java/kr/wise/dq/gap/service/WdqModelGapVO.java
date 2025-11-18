package kr.wise.dq.gap.service;

import kr.wise.commons.cmm.CommonVo;

public class WdqModelGapVO extends CommonVo {

	private String gapStatus;
	private String subjLnm;
	private String pdmTblPnm;
	private String pdmTblLnm;
	private String pdmColCnt;
	private String dbConnTrgLnm;
	private String dbConnTrgPnm;
	private String dbSchLnm;
	private String dbSchPnm;
	private String ddlTblPnm;
	private String ddlColCnt;
	private String pdmGapColCntDdl;
	private String dbcTblNm;
	private String dbcColCnt;
	private String pdmGapColCntDbc;
	private String ddlGapColCntDbc;
	private String ddlTblErrDescn;
	private String ddlColErrDescn;
	private String pdmTblErrDescn;
	private String pdmColErrDescn;
	
	//검색조건들
	private String subjId;
	private String dbSchId;
	private String dbConnTrgId;
	private String searchBgnDe;
	private String searchEndDe;
	private String searchTyp;
	private String objNm;
	private String tblNm;
	private String colNm;
	private String pdmTblId;
	private String ddlTblId;
	private String ddlTrgDcd;
	
	//Main GAP 차트용
	private String pdmTblCnt;
	private String gapCnt;
	private String nmlCnt;
	
	
	
	private String ddlColOrd;
    private String ddlDataType; 
    private String ddlDataLen; 
    private String ddlDataScal; 
    private String ddlPkYn; 
    private String ddlPkOrd; 
    private String ddlNonulYn; 
    private String ddlDefltVal;
    
    private String pdmColOrd;
    private String pdmDataType; 
    private String pdmDataLen; 
    private String pdmDataScal; 
    private String pdmPkYn; 
    private String pdmPkOrd; 
    private String pdmNonulYn; 
    private String pdmDefltVal;
    
    private String ddlColId;
    private String ddlColPnm;
    private String ddlColLnm;
    
    private String pdmColId;
    private String pdmColPnm;
    private String pdmColLnm;
	
    
    private String dbcColOrd;
    private String dbcDataType; 
    private String dbcDataLen; 
    private String dbcDataScal; 
    private String dbcPkYn; 
    private String dbcPkOrd; 
    private String dbcNonulYn; 
    private String dbcDefltVal;
    
    private String dbcColPnm;
    private String dbcColLnm;
    
    private String ddlTblLnm;
    
    //마트 관련 컬럼
    private String martTblPnm;
    private String martTblLnm;
    private String martColCnt;
    
    private String martColPnm;
    private String martColLnm;
    private String martColOrd;
    private String martDataType; 
    private String martDataLen; 
    private String martDataScal; 
    private String martPkYn; 
    private String martPkOrd; 
    private String martNonulYn; 
    private String martDefltVal;
    
    private String gapConts;
    
    private String dbTsfSchLnm;
	private String dbTsfConnTrgLnm;
    private String dbTsfSchId;
	private String dbTsfConnTrgId;
    private String ddlTsfTblId;
    private String ddlTsfTblLnm;
    private String ddlTsfTblPnm;
    private String ddlTsfColId;
    private String ddlTsfColPnm;
    private String ddlTsfColLnm;
    private String ddlTsfColOrd;
    private String ddlTsfDataType; 
    private String ddlTsfDataLen; 
    private String ddlTsfDataScal; 
    private String ddlTsfPkYn; 
    private String ddlTsfPkOrd; 
    private String ddlTsfNonulYn; 
    private String ddlTsfDefltVal;
    private String ddlTsfColCnt;
    
    private String dbRealSchId;
    private String dbcRealTblNm;
	private String dbcRealColCnt;
	
	private String dbcRealColOrd;
    private String dbcRealDataType; 
    private String dbcRealDataLen; 
    private String dbcRealDataScal; 
    private String dbcRealPkYn; 
    private String dbcRealPkOrd; 
    private String dbcRealNonulYn; 
    private String dbcRealDefltVal;
    private String dbcRealColPnm;
    private String dbcRealColLnm;
    
    private String dbcDbSchLnm;
	private String dbcDbConnTrgLnm;
	
	private String dbcRealDbSchLnm;
	private String dbcRealDbConnTrgLnm;
	
	private String dbcTblLnm;
	private String dbcRealTblLnm;
	
	private String fullPath;
	private String dbColCnt;
	private String colGapCnt;
	
	private String sysAreaLnm;
	
	private String sditmPnm;
    private String sditmLnm;    
    private String sditmDataType;
    private String sditmDataLen;
    private String sditmDataScal;
    private String dbmsTypCd;
	
    private String uppSubjLnm;
    
    private String erwinColPnm;     
    private String erwinColLnm;     
    private String erwinColOrd;     
    private String erwinPkYn;     
    private String erwinDataType;   
    private String erwinDataLen;   
    private String erwinDataScal;   
    private String erwinNonulYn;   
    private String erwinDefltVal;   
    
    
      
    private String srcDbcColCnt  ;
    private String srcDbcTblPnm  ;
    private String srcDbcTblLnm  ;  
    private String srcDbcColPnm  ;
    private String srcDbcColLnm  ;
    private String srcDbcColSpnm ;
    private String srcDbcColOrd  ; 
    private String srcDbcPkYn    ;
    private String srcDbcDataType;
    private String srcDbcDataLen ;
    private String srcDbcDataScal;
    private String srcDbcNonulYn ;
    private String srcDbcDefltVal;
    
    private String tgtDbcColCnt  ;
    private String tgtDbcColPnm  ;
    private String tgtDbcColLnm  ;
    private String tgtDbcColOrd  ;
    private String tgtDbcPkYn    ;
    private String tgtDbcDataType;
    private String tgtDbcDataLen ;
    private String tgtDbcDataScal;
    private String tgtDbcNonulYn ;
    private String tgtDbcDefltVal;
    
   
    private String srcDdlColCnt     ;
    private String tgtDdlColCnt     ;
    private String srcDdlTblId      ;
    private String srcDdlTblPnm     ;
    private String srcDdlTblLnm     ;  
    private String srcDdlColPnm     ;
    private String srcDdlColLnm     ;
    private String srcDdlColSpnm    ;
    private String srcDdlColOrd     ;
    private String srcDdlPkYn       ;
    private String srcDdlDataType   ;
    private String srcDdlDataLen    ;
    private String srcDdlDataScal   ;
    private String srcDdlNonulYn    ;
    private String srcDdlDefltVal   ;
    private String tgtDdlColPnm     ;
    private String tgtDdlColLnm     ;
    private String tgtDdlColOrd     ;
    private String tgtDdlPkYn       ;
    private String tgtDdlDataType   ;
    private String tgtDdlDataLen    ;
    private String tgtDdlDataScal   ;
    private String tgtDdlNonulYn    ;
    private String tgtDdlDefltVal   ;
    
        
    private String ddlIdxPnm;
    private String srcDdlIdxColCnt;
    private String tgtDdlIdxColCnt;
    
    
    private String srcDdlIdxPnm;
    private String srcUkIdxYn;
    private String srcDdlIdxColPnm;  
    private String srcDdlIdxColLnm;        
    private String srcDdlIdxColOrd;  
    private String srcSortTyp ; 
    
    private String tgtDdlTblPnm;
    private String tgtDdlTblLnm;
    private String tgtDdlIdxPnm;
    private String tgtUkIdxYn;
    private String tgtDdlIdxColPnm;  
    private String tgtDdlIdxColLnm;      
    private String tgtDdlIdxColOrd; 
    private String tgtSortTyp     ;  
    
    private String regDtm     ;  
    
    private String gapDcd;
   
    
    public String getGapDcd() {
		return gapDcd;
	}



	public void setGapDcd(String gapDcd) {
		this.gapDcd = gapDcd;
	}



	public String getGapStatus() {
        return gapStatus;
    }



    public void setGapStatus(String gapStatus) {
        this.gapStatus = gapStatus;
    }



    public String getSubjLnm() {
        return subjLnm;
    }



    public void setSubjLnm(String subjLnm) {
        this.subjLnm = subjLnm;
    }



    public String getPdmTblPnm() {
        return pdmTblPnm;
    }



    public void setPdmTblPnm(String pdmTblPnm) {
        this.pdmTblPnm = pdmTblPnm;
    }



    public String getPdmTblLnm() {
        return pdmTblLnm;
    }



    public void setPdmTblLnm(String pdmTblLnm) {
        this.pdmTblLnm = pdmTblLnm;
    }



    public String getPdmColCnt() {
        return pdmColCnt;
    }



    public void setPdmColCnt(String pdmColCnt) {
        this.pdmColCnt = pdmColCnt;
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



    public String getDbSchLnm() {
        return dbSchLnm;
    }



    public void setDbSchLnm(String dbSchLnm) {
        this.dbSchLnm = dbSchLnm;
    }



    public String getDbSchPnm() {
        return dbSchPnm;
    }



    public void setDbSchPnm(String dbSchPnm) {
        this.dbSchPnm = dbSchPnm;
    }



    public String getDdlTblPnm() {
        return ddlTblPnm;
    }



    public void setDdlTblPnm(String ddlTblPnm) {
        this.ddlTblPnm = ddlTblPnm;
    }



    public String getDdlColCnt() {
        return ddlColCnt;
    }



    public void setDdlColCnt(String ddlColCnt) {
        this.ddlColCnt = ddlColCnt;
    }



    public String getPdmGapColCntDdl() {
        return pdmGapColCntDdl;
    }



    public void setPdmGapColCntDdl(String pdmGapColCntDdl) {
        this.pdmGapColCntDdl = pdmGapColCntDdl;
    }



    public String getDbcTblNm() {
        return dbcTblNm;
    }



    public void setDbcTblNm(String dbcTblNm) {
        this.dbcTblNm = dbcTblNm;
    }



    public String getDbcColCnt() {
        return dbcColCnt;
    }



    public void setDbcColCnt(String dbcColCnt) {
        this.dbcColCnt = dbcColCnt;
    }



    public String getPdmGapColCntDbc() {
        return pdmGapColCntDbc;
    }



    public void setPdmGapColCntDbc(String pdmGapColCntDbc) {
        this.pdmGapColCntDbc = pdmGapColCntDbc;
    }



    public String getDdlGapColCntDbc() {
        return ddlGapColCntDbc;
    }



    public void setDdlGapColCntDbc(String ddlGapColCntDbc) {
        this.ddlGapColCntDbc = ddlGapColCntDbc;
    }



    public String getDdlTblErrDescn() {
        return ddlTblErrDescn;
    }



    public void setDdlTblErrDescn(String ddlTblErrDescn) {
        this.ddlTblErrDescn = ddlTblErrDescn;
    }



    public String getDdlColErrDescn() {
        return ddlColErrDescn;
    }



    public void setDdlColErrDescn(String ddlColErrDescn) {
        this.ddlColErrDescn = ddlColErrDescn;
    }



    public String getPdmTblErrDescn() {
        return pdmTblErrDescn;
    }



    public void setPdmTblErrDescn(String pdmTblErrDescn) {
        this.pdmTblErrDescn = pdmTblErrDescn;
    }



    public String getPdmColErrDescn() {
        return pdmColErrDescn;
    }



    public void setPdmColErrDescn(String pdmColErrDescn) {
        this.pdmColErrDescn = pdmColErrDescn;
    }



    public String getSubjId() {
        return subjId;
    }



    public void setSubjId(String subjId) {
        this.subjId = subjId;
    }



    public String getDbSchId() {
        return dbSchId;
    }



    public void setDbSchId(String dbSchId) {
        this.dbSchId = dbSchId;
    }



    public String getDbConnTrgId() {
        return dbConnTrgId;
    }



    public void setDbConnTrgId(String dbConnTrgId) {
        this.dbConnTrgId = dbConnTrgId;
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



    public String getSearchTyp() {
        return searchTyp;
    }



    public void setSearchTyp(String searchTyp) {
        this.searchTyp = searchTyp;
    }



    public String getObjNm() {
        return objNm;
    }



    public void setObjNm(String objNm) {
        this.objNm = objNm;
    }



    public String getTblNm() {
        return tblNm;
    }



    public void setTblNm(String tblNm) {
        this.tblNm = tblNm;
    }



    public String getColNm() {
        return colNm;
    }



    public void setColNm(String colNm) {
        this.colNm = colNm;
    }



    public String getPdmTblId() {
        return pdmTblId;
    }



    public void setPdmTblId(String pdmTblId) {
        this.pdmTblId = pdmTblId;
    }



    public String getDdlTblId() {
        return ddlTblId;
    }



    public void setDdlTblId(String ddlTblId) {
        this.ddlTblId = ddlTblId;
    }



    public String getDdlTrgDcd() {
        return ddlTrgDcd;
    }



    public void setDdlTrgDcd(String ddlTrgDcd) {
        this.ddlTrgDcd = ddlTrgDcd;
    }



    public String getPdmTblCnt() {
        return pdmTblCnt;
    }



    public void setPdmTblCnt(String pdmTblCnt) {
        this.pdmTblCnt = pdmTblCnt;
    }



    public String getGapCnt() {
        return gapCnt;
    }



    public void setGapCnt(String gapCnt) {
        this.gapCnt = gapCnt;
    }



    public String getNmlCnt() {
        return nmlCnt;
    }



    public void setNmlCnt(String nmlCnt) {
        this.nmlCnt = nmlCnt;
    }



    public String getDdlColOrd() {
        return ddlColOrd;
    }



    public void setDdlColOrd(String ddlColOrd) {
        this.ddlColOrd = ddlColOrd;
    }



    public String getDdlDataType() {
        return ddlDataType;
    }



    public void setDdlDataType(String ddlDataType) {
        this.ddlDataType = ddlDataType;
    }



    public String getDdlDataLen() {
        return ddlDataLen;
    }



    public void setDdlDataLen(String ddlDataLen) {
        this.ddlDataLen = ddlDataLen;
    }



    public String getDdlDataScal() {
        return ddlDataScal;
    }



    public void setDdlDataScal(String ddlDataScal) {
        this.ddlDataScal = ddlDataScal;
    }



    public String getDdlPkYn() {
        return ddlPkYn;
    }



    public void setDdlPkYn(String ddlPkYn) {
        this.ddlPkYn = ddlPkYn;
    }



    public String getDdlPkOrd() {
        return ddlPkOrd;
    }



    public void setDdlPkOrd(String ddlPkOrd) {
        this.ddlPkOrd = ddlPkOrd;
    }



    public String getDdlNonulYn() {
        return ddlNonulYn;
    }



    public void setDdlNonulYn(String ddlNonulYn) {
        this.ddlNonulYn = ddlNonulYn;
    }



    public String getDdlDefltVal() {
        return ddlDefltVal;
    }



    public void setDdlDefltVal(String ddlDefltVal) {
        this.ddlDefltVal = ddlDefltVal;
    }



    public String getPdmColOrd() {
        return pdmColOrd;
    }



    public void setPdmColOrd(String pdmColOrd) {
        this.pdmColOrd = pdmColOrd;
    }



    public String getPdmDataType() {
        return pdmDataType;
    }



    public void setPdmDataType(String pdmDataType) {
        this.pdmDataType = pdmDataType;
    }



    public String getPdmDataLen() {
        return pdmDataLen;
    }



    public void setPdmDataLen(String pdmDataLen) {
        this.pdmDataLen = pdmDataLen;
    }



    public String getPdmDataScal() {
        return pdmDataScal;
    }



    public void setPdmDataScal(String pdmDataScal) {
        this.pdmDataScal = pdmDataScal;
    }



    public String getPdmPkYn() {
        return pdmPkYn;
    }



    public void setPdmPkYn(String pdmPkYn) {
        this.pdmPkYn = pdmPkYn;
    }



    public String getPdmPkOrd() {
        return pdmPkOrd;
    }



    public void setPdmPkOrd(String pdmPkOrd) {
        this.pdmPkOrd = pdmPkOrd;
    }



    public String getPdmNonulYn() {
        return pdmNonulYn;
    }



    public void setPdmNonulYn(String pdmNonulYn) {
        this.pdmNonulYn = pdmNonulYn;
    }



    public String getPdmDefltVal() {
        return pdmDefltVal;
    }



    public void setPdmDefltVal(String pdmDefltVal) {
        this.pdmDefltVal = pdmDefltVal;
    }



    public String getDdlColId() {
        return ddlColId;
    }



    public void setDdlColId(String ddlColId) {
        this.ddlColId = ddlColId;
    }



    public String getDdlColPnm() {
        return ddlColPnm;
    }



    public void setDdlColPnm(String ddlColPnm) {
        this.ddlColPnm = ddlColPnm;
    }



    public String getDdlColLnm() {
        return ddlColLnm;
    }



    public void setDdlColLnm(String ddlColLnm) {
        this.ddlColLnm = ddlColLnm;
    }



    public String getPdmColId() {
        return pdmColId;
    }



    public void setPdmColId(String pdmColId) {
        this.pdmColId = pdmColId;
    }



    public String getPdmColPnm() {
        return pdmColPnm;
    }



    public void setPdmColPnm(String pdmColPnm) {
        this.pdmColPnm = pdmColPnm;
    }



    public String getPdmColLnm() {
        return pdmColLnm;
    }



    public void setPdmColLnm(String pdmColLnm) {
        this.pdmColLnm = pdmColLnm;
    }



    public String getDbcColOrd() {
        return dbcColOrd;
    }



    public void setDbcColOrd(String dbcColOrd) {
        this.dbcColOrd = dbcColOrd;
    }



    public String getDbcDataType() {
        return dbcDataType;
    }



    public void setDbcDataType(String dbcDataType) {
        this.dbcDataType = dbcDataType;
    }



    public String getDbcDataLen() {
        return dbcDataLen;
    }



    public void setDbcDataLen(String dbcDataLen) {
        this.dbcDataLen = dbcDataLen;
    }



    public String getDbcDataScal() {
        return dbcDataScal;
    }



    public void setDbcDataScal(String dbcDataScal) {
        this.dbcDataScal = dbcDataScal;
    }



    public String getDbcPkYn() {
        return dbcPkYn;
    }



    public void setDbcPkYn(String dbcPkYn) {
        this.dbcPkYn = dbcPkYn;
    }



    public String getDbcPkOrd() {
        return dbcPkOrd;
    }



    public void setDbcPkOrd(String dbcPkOrd) {
        this.dbcPkOrd = dbcPkOrd;
    }



    public String getDbcNonulYn() {
        return dbcNonulYn;
    }



    public void setDbcNonulYn(String dbcNonulYn) {
        this.dbcNonulYn = dbcNonulYn;
    }



    public String getDbcDefltVal() {
        return dbcDefltVal;
    }



    public void setDbcDefltVal(String dbcDefltVal) {
        this.dbcDefltVal = dbcDefltVal;
    }



    public String getDbcColPnm() {
        return dbcColPnm;
    }



    public void setDbcColPnm(String dbcColPnm) {
        this.dbcColPnm = dbcColPnm;
    }



    public String getDbcColLnm() {
        return dbcColLnm;
    }



    public void setDbcColLnm(String dbcColLnm) {
        this.dbcColLnm = dbcColLnm;
    }



    public String getDdlTblLnm() {
        return ddlTblLnm;
    }



    public void setDdlTblLnm(String ddlTblLnm) {
        this.ddlTblLnm = ddlTblLnm;
    }



    public String getMartTblPnm() {
        return martTblPnm;
    }



    public void setMartTblPnm(String martTblPnm) {
        this.martTblPnm = martTblPnm;
    }



    public String getMartTblLnm() {
        return martTblLnm;
    }



    public void setMartTblLnm(String martTblLnm) {
        this.martTblLnm = martTblLnm;
    }



    public String getMartColCnt() {
        return martColCnt;
    }



    public void setMartColCnt(String martColCnt) {
        this.martColCnt = martColCnt;
    }



    public String getMartColPnm() {
        return martColPnm;
    }



    public void setMartColPnm(String martColPnm) {
        this.martColPnm = martColPnm;
    }



    public String getMartColLnm() {
        return martColLnm;
    }



    public void setMartColLnm(String martColLnm) {
        this.martColLnm = martColLnm;
    }



    public String getMartColOrd() {
        return martColOrd;
    }



    public void setMartColOrd(String martColOrd) {
        this.martColOrd = martColOrd;
    }



    public String getMartDataType() {
        return martDataType;
    }



    public void setMartDataType(String martDataType) {
        this.martDataType = martDataType;
    }



    public String getMartDataLen() {
        return martDataLen;
    }



    public void setMartDataLen(String martDataLen) {
        this.martDataLen = martDataLen;
    }



    public String getMartDataScal() {
        return martDataScal;
    }



    public void setMartDataScal(String martDataScal) {
        this.martDataScal = martDataScal;
    }



    public String getMartPkYn() {
        return martPkYn;
    }



    public void setMartPkYn(String martPkYn) {
        this.martPkYn = martPkYn;
    }



    public String getMartPkOrd() {
        return martPkOrd;
    }



    public void setMartPkOrd(String martPkOrd) {
        this.martPkOrd = martPkOrd;
    }



    public String getMartNonulYn() {
        return martNonulYn;
    }



    public void setMartNonulYn(String martNonulYn) {
        this.martNonulYn = martNonulYn;
    }



    public String getMartDefltVal() {
        return martDefltVal;
    }



    public void setMartDefltVal(String martDefltVal) {
        this.martDefltVal = martDefltVal;
    }



    public String getGapConts() {
        return gapConts;
    }



    public void setGapConts(String gapConts) {
        this.gapConts = gapConts;
    }



    public String getDbTsfSchLnm() {
        return dbTsfSchLnm;
    }



    public void setDbTsfSchLnm(String dbTsfSchLnm) {
        this.dbTsfSchLnm = dbTsfSchLnm;
    }



    public String getDbTsfConnTrgLnm() {
        return dbTsfConnTrgLnm;
    }



    public void setDbTsfConnTrgLnm(String dbTsfConnTrgLnm) {
        this.dbTsfConnTrgLnm = dbTsfConnTrgLnm;
    }



    public String getDbTsfSchId() {
        return dbTsfSchId;
    }



    public void setDbTsfSchId(String dbTsfSchId) {
        this.dbTsfSchId = dbTsfSchId;
    }



    public String getDbTsfConnTrgId() {
        return dbTsfConnTrgId;
    }



    public void setDbTsfConnTrgId(String dbTsfConnTrgId) {
        this.dbTsfConnTrgId = dbTsfConnTrgId;
    }



    public String getDdlTsfTblId() {
        return ddlTsfTblId;
    }



    public void setDdlTsfTblId(String ddlTsfTblId) {
        this.ddlTsfTblId = ddlTsfTblId;
    }



    public String getDdlTsfTblLnm() {
        return ddlTsfTblLnm;
    }



    public void setDdlTsfTblLnm(String ddlTsfTblLnm) {
        this.ddlTsfTblLnm = ddlTsfTblLnm;
    }



    public String getDdlTsfTblPnm() {
        return ddlTsfTblPnm;
    }



    public void setDdlTsfTblPnm(String ddlTsfTblPnm) {
        this.ddlTsfTblPnm = ddlTsfTblPnm;
    }



    public String getDdlTsfColId() {
        return ddlTsfColId;
    }



    public void setDdlTsfColId(String ddlTsfColId) {
        this.ddlTsfColId = ddlTsfColId;
    }



    public String getDdlTsfColPnm() {
        return ddlTsfColPnm;
    }



    public void setDdlTsfColPnm(String ddlTsfColPnm) {
        this.ddlTsfColPnm = ddlTsfColPnm;
    }



    public String getDdlTsfColLnm() {
        return ddlTsfColLnm;
    }



    public void setDdlTsfColLnm(String ddlTsfColLnm) {
        this.ddlTsfColLnm = ddlTsfColLnm;
    }



    public String getDdlTsfColOrd() {
        return ddlTsfColOrd;
    }



    public void setDdlTsfColOrd(String ddlTsfColOrd) {
        this.ddlTsfColOrd = ddlTsfColOrd;
    }



    public String getDdlTsfDataType() {
        return ddlTsfDataType;
    }



    public void setDdlTsfDataType(String ddlTsfDataType) {
        this.ddlTsfDataType = ddlTsfDataType;
    }



    public String getDdlTsfDataLen() {
        return ddlTsfDataLen;
    }



    public void setDdlTsfDataLen(String ddlTsfDataLen) {
        this.ddlTsfDataLen = ddlTsfDataLen;
    }



    public String getDdlTsfDataScal() {
        return ddlTsfDataScal;
    }



    public void setDdlTsfDataScal(String ddlTsfDataScal) {
        this.ddlTsfDataScal = ddlTsfDataScal;
    }



    public String getDdlTsfPkYn() {
        return ddlTsfPkYn;
    }



    public void setDdlTsfPkYn(String ddlTsfPkYn) {
        this.ddlTsfPkYn = ddlTsfPkYn;
    }



    public String getDdlTsfPkOrd() {
        return ddlTsfPkOrd;
    }



    public void setDdlTsfPkOrd(String ddlTsfPkOrd) {
        this.ddlTsfPkOrd = ddlTsfPkOrd;
    }



    public String getDdlTsfNonulYn() {
        return ddlTsfNonulYn;
    }



    public void setDdlTsfNonulYn(String ddlTsfNonulYn) {
        this.ddlTsfNonulYn = ddlTsfNonulYn;
    }



    public String getDdlTsfDefltVal() {
        return ddlTsfDefltVal;
    }



    public void setDdlTsfDefltVal(String ddlTsfDefltVal) {
        this.ddlTsfDefltVal = ddlTsfDefltVal;
    }



    public String getDdlTsfColCnt() {
        return ddlTsfColCnt;
    }



    public void setDdlTsfColCnt(String ddlTsfColCnt) {
        this.ddlTsfColCnt = ddlTsfColCnt;
    }



    public String getDbRealSchId() {
        return dbRealSchId;
    }



    public void setDbRealSchId(String dbRealSchId) {
        this.dbRealSchId = dbRealSchId;
    }



    public String getDbcRealTblNm() {
        return dbcRealTblNm;
    }



    public void setDbcRealTblNm(String dbcRealTblNm) {
        this.dbcRealTblNm = dbcRealTblNm;
    }



    public String getDbcRealColCnt() {
        return dbcRealColCnt;
    }



    public void setDbcRealColCnt(String dbcRealColCnt) {
        this.dbcRealColCnt = dbcRealColCnt;
    }



    public String getDbcRealColOrd() {
        return dbcRealColOrd;
    }



    public void setDbcRealColOrd(String dbcRealColOrd) {
        this.dbcRealColOrd = dbcRealColOrd;
    }



    public String getDbcRealDataType() {
        return dbcRealDataType;
    }



    public void setDbcRealDataType(String dbcRealDataType) {
        this.dbcRealDataType = dbcRealDataType;
    }



    public String getDbcRealDataLen() {
        return dbcRealDataLen;
    }



    public void setDbcRealDataLen(String dbcRealDataLen) {
        this.dbcRealDataLen = dbcRealDataLen;
    }



    public String getDbcRealDataScal() {
        return dbcRealDataScal;
    }



    public void setDbcRealDataScal(String dbcRealDataScal) {
        this.dbcRealDataScal = dbcRealDataScal;
    }



    public String getDbcRealPkYn() {
        return dbcRealPkYn;
    }



    public void setDbcRealPkYn(String dbcRealPkYn) {
        this.dbcRealPkYn = dbcRealPkYn;
    }



    public String getDbcRealPkOrd() {
        return dbcRealPkOrd;
    }



    public void setDbcRealPkOrd(String dbcRealPkOrd) {
        this.dbcRealPkOrd = dbcRealPkOrd;
    }



    public String getDbcRealNonulYn() {
        return dbcRealNonulYn;
    }



    public void setDbcRealNonulYn(String dbcRealNonulYn) {
        this.dbcRealNonulYn = dbcRealNonulYn;
    }



    public String getDbcRealDefltVal() {
        return dbcRealDefltVal;
    }



    public void setDbcRealDefltVal(String dbcRealDefltVal) {
        this.dbcRealDefltVal = dbcRealDefltVal;
    }



    public String getDbcRealColPnm() {
        return dbcRealColPnm;
    }



    public void setDbcRealColPnm(String dbcRealColPnm) {
        this.dbcRealColPnm = dbcRealColPnm;
    }



    public String getDbcRealColLnm() {
        return dbcRealColLnm;
    }



    public void setDbcRealColLnm(String dbcRealColLnm) {
        this.dbcRealColLnm = dbcRealColLnm;
    }



    public String getDbcDbSchLnm() {
        return dbcDbSchLnm;
    }



    public void setDbcDbSchLnm(String dbcDbSchLnm) {
        this.dbcDbSchLnm = dbcDbSchLnm;
    }



    public String getDbcDbConnTrgLnm() {
        return dbcDbConnTrgLnm;
    }



    public void setDbcDbConnTrgLnm(String dbcDbConnTrgLnm) {
        this.dbcDbConnTrgLnm = dbcDbConnTrgLnm;
    }



    public String getDbcRealDbSchLnm() {
        return dbcRealDbSchLnm;
    }



    public void setDbcRealDbSchLnm(String dbcRealDbSchLnm) {
        this.dbcRealDbSchLnm = dbcRealDbSchLnm;
    }



    public String getDbcRealDbConnTrgLnm() {
        return dbcRealDbConnTrgLnm;
    }



    public void setDbcRealDbConnTrgLnm(String dbcRealDbConnTrgLnm) {
        this.dbcRealDbConnTrgLnm = dbcRealDbConnTrgLnm;
    }



    public String getDbcTblLnm() {
        return dbcTblLnm;
    }



    public void setDbcTblLnm(String dbcTblLnm) {
        this.dbcTblLnm = dbcTblLnm;
    }



    public String getDbcRealTblLnm() {
        return dbcRealTblLnm;
    }



    public void setDbcRealTblLnm(String dbcRealTblLnm) {
        this.dbcRealTblLnm = dbcRealTblLnm;
    }



    public String getFullPath() {
        return fullPath;
    }



    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }



    public String getDbColCnt() {
        return dbColCnt;
    }



    public void setDbColCnt(String dbColCnt) {
        this.dbColCnt = dbColCnt;
    }



    public String getColGapCnt() {
        return colGapCnt;
    }



    public void setColGapCnt(String colGapCnt) {
        this.colGapCnt = colGapCnt;
    }



    public String getSysAreaLnm() {
        return sysAreaLnm;
    }



    public void setSysAreaLnm(String sysAreaLnm) {
        this.sysAreaLnm = sysAreaLnm;
    }



    public String getSditmPnm() {
        return sditmPnm;
    }



    public void setSditmPnm(String sditmPnm) {
        this.sditmPnm = sditmPnm;
    }



    public String getSditmLnm() {
        return sditmLnm;
    }



    public void setSditmLnm(String sditmLnm) {
        this.sditmLnm = sditmLnm;
    }



    public String getSditmDataType() {
        return sditmDataType;
    }



    public void setSditmDataType(String sditmDataType) {
        this.sditmDataType = sditmDataType;
    }



    public String getSditmDataLen() {
        return sditmDataLen;
    }



    public void setSditmDataLen(String sditmDataLen) {
        this.sditmDataLen = sditmDataLen;
    }



    public String getSditmDataScal() {
        return sditmDataScal;
    }



    public void setSditmDataScal(String sditmDataScal) {
        this.sditmDataScal = sditmDataScal;
    }



    public String getDbmsTypCd() {
        return dbmsTypCd;
    }



    public void setDbmsTypCd(String dbmsTypCd) {
        this.dbmsTypCd = dbmsTypCd;
    }



    public String getUppSubjLnm() {
        return uppSubjLnm;
    }



    public void setUppSubjLnm(String uppSubjLnm) {
        this.uppSubjLnm = uppSubjLnm;
    }



    public String getErwinColPnm() {
        return erwinColPnm;
    }



    public void setErwinColPnm(String erwinColPnm) {
        this.erwinColPnm = erwinColPnm;
    }



    public String getErwinColLnm() {
        return erwinColLnm;
    }



    public void setErwinColLnm(String erwinColLnm) {
        this.erwinColLnm = erwinColLnm;
    }



    public String getErwinColOrd() {
        return erwinColOrd;
    }



    public void setErwinColOrd(String erwinColOrd) {
        this.erwinColOrd = erwinColOrd;
    }



    public String getErwinPkYn() {
        return erwinPkYn;
    }



    public void setErwinPkYn(String erwinPkYn) {
        this.erwinPkYn = erwinPkYn;
    }



    public String getErwinDataType() {
        return erwinDataType;
    }



    public void setErwinDataType(String erwinDataType) {
        this.erwinDataType = erwinDataType;
    }



    public String getErwinDataLen() {
        return erwinDataLen;
    }



    public void setErwinDataLen(String erwinDataLen) {
        this.erwinDataLen = erwinDataLen;
    }



    public String getErwinDataScal() {
        return erwinDataScal;
    }



    public void setErwinDataScal(String erwinDataScal) {
        this.erwinDataScal = erwinDataScal;
    }



    public String getErwinNonulYn() {
        return erwinNonulYn;
    }



    public void setErwinNonulYn(String erwinNonulYn) {
        this.erwinNonulYn = erwinNonulYn;
    }



    public String getErwinDefltVal() {
        return erwinDefltVal;
    }



    public void setErwinDefltVal(String erwinDefltVal) {
        this.erwinDefltVal = erwinDefltVal;
    }



    public String getSrcDbcColCnt() {
        return srcDbcColCnt;
    }



    public void setSrcDbcColCnt(String srcDbcColCnt) {
        this.srcDbcColCnt = srcDbcColCnt;
    }



    public String getSrcDbcTblPnm() {
        return srcDbcTblPnm;
    }



    public void setSrcDbcTblPnm(String srcDbcTblPnm) {
        this.srcDbcTblPnm = srcDbcTblPnm;
    }



    public String getSrcDbcTblLnm() {
        return srcDbcTblLnm;
    }



    public void setSrcDbcTblLnm(String srcDbcTblLnm) {
        this.srcDbcTblLnm = srcDbcTblLnm;
    }



    public String getSrcDbcColPnm() {
        return srcDbcColPnm;
    }



    public void setSrcDbcColPnm(String srcDbcColPnm) {
        this.srcDbcColPnm = srcDbcColPnm;
    }



    public String getSrcDbcColLnm() {
        return srcDbcColLnm;
    }



    public void setSrcDbcColLnm(String srcDbcColLnm) {
        this.srcDbcColLnm = srcDbcColLnm;
    }



    public String getSrcDbcColSpnm() {
        return srcDbcColSpnm;
    }



    public void setSrcDbcColSpnm(String srcDbcColSpnm) {
        this.srcDbcColSpnm = srcDbcColSpnm;
    }



    public String getSrcDbcColOrd() {
        return srcDbcColOrd;
    }



    public void setSrcDbcColOrd(String srcDbcColOrd) {
        this.srcDbcColOrd = srcDbcColOrd;
    }



    public String getSrcDbcPkYn() {
        return srcDbcPkYn;
    }



    public void setSrcDbcPkYn(String srcDbcPkYn) {
        this.srcDbcPkYn = srcDbcPkYn;
    }



    public String getSrcDbcDataType() {
        return srcDbcDataType;
    }



    public void setSrcDbcDataType(String srcDbcDataType) {
        this.srcDbcDataType = srcDbcDataType;
    }



    public String getSrcDbcDataLen() {
        return srcDbcDataLen;
    }



    public void setSrcDbcDataLen(String srcDbcDataLen) {
        this.srcDbcDataLen = srcDbcDataLen;
    }



    public String getSrcDbcDataScal() {
        return srcDbcDataScal;
    }



    public void setSrcDbcDataScal(String srcDbcDataScal) {
        this.srcDbcDataScal = srcDbcDataScal;
    }



    public String getSrcDbcNonulYn() {
        return srcDbcNonulYn;
    }



    public void setSrcDbcNonulYn(String srcDbcNonulYn) {
        this.srcDbcNonulYn = srcDbcNonulYn;
    }



    public String getSrcDbcDefltVal() {
        return srcDbcDefltVal;
    }



    public void setSrcDbcDefltVal(String srcDbcDefltVal) {
        this.srcDbcDefltVal = srcDbcDefltVal;
    }



    public String getTgtDbcColCnt() {
        return tgtDbcColCnt;
    }



    public void setTgtDbcColCnt(String tgtDbcColCnt) {
        this.tgtDbcColCnt = tgtDbcColCnt;
    }



    public String getTgtDbcColPnm() {
        return tgtDbcColPnm;
    }



    public void setTgtDbcColPnm(String tgtDbcColPnm) {
        this.tgtDbcColPnm = tgtDbcColPnm;
    }



    public String getTgtDbcColLnm() {
        return tgtDbcColLnm;
    }



    public void setTgtDbcColLnm(String tgtDbcColLnm) {
        this.tgtDbcColLnm = tgtDbcColLnm;
    }



    public String getTgtDbcColOrd() {
        return tgtDbcColOrd;
    }



    public void setTgtDbcColOrd(String tgtDbcColOrd) {
        this.tgtDbcColOrd = tgtDbcColOrd;
    }



    public String getTgtDbcPkYn() {
        return tgtDbcPkYn;
    }



    public void setTgtDbcPkYn(String tgtDbcPkYn) {
        this.tgtDbcPkYn = tgtDbcPkYn;
    }



    public String getTgtDbcDataType() {
        return tgtDbcDataType;
    }



    public void setTgtDbcDataType(String tgtDbcDataType) {
        this.tgtDbcDataType = tgtDbcDataType;
    }



    public String getTgtDbcDataLen() {
        return tgtDbcDataLen;
    }



    public void setTgtDbcDataLen(String tgtDbcDataLen) {
        this.tgtDbcDataLen = tgtDbcDataLen;
    }



    public String getTgtDbcDataScal() {
        return tgtDbcDataScal;
    }



    public void setTgtDbcDataScal(String tgtDbcDataScal) {
        this.tgtDbcDataScal = tgtDbcDataScal;
    }



    public String getTgtDbcNonulYn() {
        return tgtDbcNonulYn;
    }



    public void setTgtDbcNonulYn(String tgtDbcNonulYn) {
        this.tgtDbcNonulYn = tgtDbcNonulYn;
    }



    public String getTgtDbcDefltVal() {
        return tgtDbcDefltVal;
    }



    public void setTgtDbcDefltVal(String tgtDbcDefltVal) {
        this.tgtDbcDefltVal = tgtDbcDefltVal;
    }



    public String getSrcDdlColCnt() {
        return srcDdlColCnt;
    }



    public void setSrcDdlColCnt(String srcDdlColCnt) {
        this.srcDdlColCnt = srcDdlColCnt;
    }



    public String getTgtDdlColCnt() {
        return tgtDdlColCnt;
    }



    public void setTgtDdlColCnt(String tgtDdlColCnt) {
        this.tgtDdlColCnt = tgtDdlColCnt;
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



    public String getSrcDdlTblLnm() {
        return srcDdlTblLnm;
    }



    public void setSrcDdlTblLnm(String srcDdlTblLnm) {
        this.srcDdlTblLnm = srcDdlTblLnm;
    }



    public String getSrcDdlColPnm() {
        return srcDdlColPnm;
    }



    public void setSrcDdlColPnm(String srcDdlColPnm) {
        this.srcDdlColPnm = srcDdlColPnm;
    }



    public String getSrcDdlColLnm() {
        return srcDdlColLnm;
    }



    public void setSrcDdlColLnm(String srcDdlColLnm) {
        this.srcDdlColLnm = srcDdlColLnm;
    }



    public String getSrcDdlColSpnm() {
        return srcDdlColSpnm;
    }



    public void setSrcDdlColSpnm(String srcDdlColSpnm) {
        this.srcDdlColSpnm = srcDdlColSpnm;
    }



    public String getSrcDdlColOrd() {
        return srcDdlColOrd;
    }



    public void setSrcDdlColOrd(String srcDdlColOrd) {
        this.srcDdlColOrd = srcDdlColOrd;
    }



    public String getSrcDdlPkYn() {
        return srcDdlPkYn;
    }



    public void setSrcDdlPkYn(String srcDdlPkYn) {
        this.srcDdlPkYn = srcDdlPkYn;
    }



    public String getSrcDdlDataType() {
        return srcDdlDataType;
    }



    public void setSrcDdlDataType(String srcDdlDataType) {
        this.srcDdlDataType = srcDdlDataType;
    }



    public String getSrcDdlDataLen() {
        return srcDdlDataLen;
    }



    public void setSrcDdlDataLen(String srcDdlDataLen) {
        this.srcDdlDataLen = srcDdlDataLen;
    }



    public String getSrcDdlDataScal() {
        return srcDdlDataScal;
    }



    public void setSrcDdlDataScal(String srcDdlDataScal) {
        this.srcDdlDataScal = srcDdlDataScal;
    }



    public String getSrcDdlNonulYn() {
        return srcDdlNonulYn;
    }



    public void setSrcDdlNonulYn(String srcDdlNonulYn) {
        this.srcDdlNonulYn = srcDdlNonulYn;
    }



    public String getSrcDdlDefltVal() {
        return srcDdlDefltVal;
    }



    public void setSrcDdlDefltVal(String srcDdlDefltVal) {
        this.srcDdlDefltVal = srcDdlDefltVal;
    }



    public String getTgtDdlColPnm() {
        return tgtDdlColPnm;
    }



    public void setTgtDdlColPnm(String tgtDdlColPnm) {
        this.tgtDdlColPnm = tgtDdlColPnm;
    }



    public String getTgtDdlColLnm() {
        return tgtDdlColLnm;
    }



    public void setTgtDdlColLnm(String tgtDdlColLnm) {
        this.tgtDdlColLnm = tgtDdlColLnm;
    }



    public String getTgtDdlColOrd() {
        return tgtDdlColOrd;
    }



    public void setTgtDdlColOrd(String tgtDdlColOrd) {
        this.tgtDdlColOrd = tgtDdlColOrd;
    }



    public String getTgtDdlPkYn() {
        return tgtDdlPkYn;
    }



    public void setTgtDdlPkYn(String tgtDdlPkYn) {
        this.tgtDdlPkYn = tgtDdlPkYn;
    }



    public String getTgtDdlDataType() {
        return tgtDdlDataType;
    }



    public void setTgtDdlDataType(String tgtDdlDataType) {
        this.tgtDdlDataType = tgtDdlDataType;
    }



    public String getTgtDdlDataLen() {
        return tgtDdlDataLen;
    }



    public void setTgtDdlDataLen(String tgtDdlDataLen) {
        this.tgtDdlDataLen = tgtDdlDataLen;
    }



    public String getTgtDdlDataScal() {
        return tgtDdlDataScal;
    }



    public void setTgtDdlDataScal(String tgtDdlDataScal) {
        this.tgtDdlDataScal = tgtDdlDataScal;
    }



    public String getTgtDdlNonulYn() {
        return tgtDdlNonulYn;
    }



    public void setTgtDdlNonulYn(String tgtDdlNonulYn) {
        this.tgtDdlNonulYn = tgtDdlNonulYn;
    }



    public String getTgtDdlDefltVal() {
        return tgtDdlDefltVal;
    }



    public void setTgtDdlDefltVal(String tgtDdlDefltVal) {
        this.tgtDdlDefltVal = tgtDdlDefltVal;
    }

    
    public String getDdlIdxPnm() {
        return ddlIdxPnm;
    }



    public void setDdlIdxPnm(String ddlIdxPnm) {
        this.ddlIdxPnm = ddlIdxPnm;
    }



    public String getSrcDdlIdxColCnt() {
        return srcDdlIdxColCnt;
    }



    public void setSrcDdlIdxColCnt(String srcDdlIdxColCnt) {
        this.srcDdlIdxColCnt = srcDdlIdxColCnt;
    }



    public String getTgtDdlIdxColCnt() {
        return tgtDdlIdxColCnt;
    }



    public void setTgtDdlIdxColCnt(String tgtDdlIdxColCnt) {
        this.tgtDdlIdxColCnt = tgtDdlIdxColCnt;
    }
    
    
    public String getSrcDdlIdxColPnm() {
        return srcDdlIdxColPnm;
    }



    public void setSrcDdlIdxColPnm(String srcDdlIdxColPnm) {
        this.srcDdlIdxColPnm = srcDdlIdxColPnm;
    }



    public String getSrcDdlIdxColLnm() {
        return srcDdlIdxColLnm;
    }



    public void setSrcDdlIdxColLnm(String srcDdlIdxColLnm) {
        this.srcDdlIdxColLnm = srcDdlIdxColLnm;
    }



    public String getSrcDdlIdxColOrd() {
        return srcDdlIdxColOrd;
    }



    public void setSrcDdlIdxColOrd(String srcDdlIdxColOrd) {
        this.srcDdlIdxColOrd = srcDdlIdxColOrd;
    }

    public String getSrcSortTyp() {
        return srcSortTyp;
    }



    public void setSrcSortTyp(String srcSortTyp) {
        this.srcSortTyp = srcSortTyp;
    }



    public String getTgtDdlIdxColPnm() {
        return tgtDdlIdxColPnm;
    }



    public void setTgtDdlIdxColPnm(String tgtDdlIdxColPnm) {
        this.tgtDdlIdxColPnm = tgtDdlIdxColPnm;
    }



    public String getTgtDdlIdxColLnm() {
        return tgtDdlIdxColLnm;
    }



    public void setTgtDdlIdxColLnm(String tgtDdlIdxColLnm) {
        this.tgtDdlIdxColLnm = tgtDdlIdxColLnm;
    }


    public String getTgtDdlIdxColOrd() {
        return tgtDdlIdxColOrd;
    }



    public void setTgtDdlIdxColOrd(String tgtDdlIdxColOrd) {
        this.tgtDdlIdxColOrd = tgtDdlIdxColOrd;
    }



    public String getTgtSortTyp() {
        return tgtSortTyp;
    }



    public void setTgtSortTyp(String tgtSortTyp) {
        this.tgtSortTyp = tgtSortTyp;
    }

    
    


    public String getSrcDdlIdxPnm() {
        return srcDdlIdxPnm;
    }



    public void setSrcDdlIdxPnm(String srcDdlIdxPnm) {
        this.srcDdlIdxPnm = srcDdlIdxPnm;
    }



    public String getTgtDdlIdxPnm() {
        return tgtDdlIdxPnm;
    }



    public void setTgtDdlIdxPnm(String tgtDdlIdxPnm) {
        this.tgtDdlIdxPnm = tgtDdlIdxPnm;
    }


    public String getTgtDdlTblPnm() {
        return tgtDdlTblPnm;
    }



    public void setTgtDdlTblPnm(String tgtDdlTblPnm) {
        this.tgtDdlTblPnm = tgtDdlTblPnm;
    }



    public String getTgtDdlTblLnm() {
        return tgtDdlTblLnm;
    }

    public void setTgtDdlTblLnm(String tgtDdlTblLnm) {
        this.tgtDdlTblLnm = tgtDdlTblLnm;
    }



    public String getSrcUkIdxYn() {
        return srcUkIdxYn;
    }



    public void setSrcUkIdxYn(String srcUkIdxYn) {
        this.srcUkIdxYn = srcUkIdxYn;
    }



    public String getTgtUkIdxYn() {
        return tgtUkIdxYn;
    }



    public void setTgtUkIdxYn(String tgtUkIdxYn) {
        this.tgtUkIdxYn = tgtUkIdxYn;
    }



    public String getRegDtm() {
		return regDtm;
	}

	public void setRegDtm(String regDtm) {
		this.regDtm = regDtm;
	}

	@Override
	public String toString() {
		return "ModelGapVO [gapStatus=" + gapStatus + ", subjLnm=" + subjLnm
				+ ", pdmTblPnm=" + pdmTblPnm + ", pdmTblLnm=" + pdmTblLnm
				+ ", pdmColCnt=" + pdmColCnt + ", dbConnTrgLnm=" + dbConnTrgLnm
				+ ", dbConnTrgPnm=" + dbConnTrgPnm + ", dbSchLnm=" + dbSchLnm
				+ ", dbSchPnm=" + dbSchPnm + ", ddlTblPnm=" + ddlTblPnm
				+ ", ddlColCnt=" + ddlColCnt + ", pdmGapColCntDdl="
				+ pdmGapColCntDdl + ", dbcTblNm=" + dbcTblNm + ", dbcColCnt="
				+ dbcColCnt + ", pdmGapColCntDbc=" + pdmGapColCntDbc
				+ ", ddlGapColCntDbc=" + ddlGapColCntDbc + ", ddlTblErrDescn="
				+ ddlTblErrDescn + ", ddlColErrDescn=" + ddlColErrDescn
				+ ", pdmTblErrDescn=" + pdmTblErrDescn + ", pdmColErrDescn="
				+ pdmColErrDescn + ", subjId=" + subjId + ", dbSchId="
				+ dbSchId + ", dbConnTrgId=" + dbConnTrgId + ", searchBgnDe="
				+ searchBgnDe + ", searchEndDe=" + searchEndDe + ", searchTyp="
				+ searchTyp + ", objNm=" + objNm + ", tblNm=" + tblNm
				+ ", colNm=" + colNm + ", pdmTblId=" + pdmTblId + ", ddlTblId="
				+ ddlTblId + ", ddlTrgDcd=" + ddlTrgDcd + ", pdmTblCnt="
				+ pdmTblCnt + ", gapCnt=" + gapCnt + ", nmlCnt=" + nmlCnt
				+ ", ddlColOrd=" + ddlColOrd + ", ddlDataType=" + ddlDataType
				+ ", ddlDataLen=" + ddlDataLen + ", ddlDataScal=" + ddlDataScal
				+ ", ddlPkYn=" + ddlPkYn + ", ddlPkOrd=" + ddlPkOrd
				+ ", ddlNonulYn=" + ddlNonulYn + ", ddlDefltVal=" + ddlDefltVal
				+ ", pdmColOrd=" + pdmColOrd + ", pdmDataType=" + pdmDataType
				+ ", pdmDataLen=" + pdmDataLen + ", pdmDataScal=" + pdmDataScal
				+ ", pdmPkYn=" + pdmPkYn + ", pdmPkOrd=" + pdmPkOrd
				+ ", pdmNonulYn=" + pdmNonulYn + ", pdmDefltVal=" + pdmDefltVal
				+ ", ddlColId=" + ddlColId + ", ddlColPnm=" + ddlColPnm
				+ ", ddlColLnm=" + ddlColLnm + ", pdmColId=" + pdmColId
				+ ", pdmColPnm=" + pdmColPnm + ", pdmColLnm=" + pdmColLnm
				+ ", dbcColOrd=" + dbcColOrd + ", dbcDataType=" + dbcDataType
				+ ", dbcDataLen=" + dbcDataLen + ", dbcDataScal=" + dbcDataScal
				+ ", dbcPkYn=" + dbcPkYn + ", dbcPkOrd=" + dbcPkOrd
				+ ", dbcNonulYn=" + dbcNonulYn + ", dbcDefltVal=" + dbcDefltVal
				+ ", dbcColPnm=" + dbcColPnm + ", dbcColLnm=" + dbcColLnm
				+ ", ddlTblLnm=" + ddlTblLnm + ", martTblPnm=" + martTblPnm
				+ ", martTblLnm=" + martTblLnm + ", martColCnt=" + martColCnt
				+ ", martColPnm=" + martColPnm + ", martColLnm=" + martColLnm
				+ ", martColOrd=" + martColOrd + ", martDataType="
				+ martDataType + ", martDataLen=" + martDataLen
				+ ", martDataScal=" + martDataScal + ", martPkYn=" + martPkYn
				+ ", martPkOrd=" + martPkOrd + ", martNonulYn=" + martNonulYn
				+ ", martDefltVal=" + martDefltVal + ", gapConts=" + gapConts
				+ ", dbTsfSchLnm=" + dbTsfSchLnm + ", dbTsfConnTrgLnm="
				+ dbTsfConnTrgLnm + ", dbTsfSchId=" + dbTsfSchId
				+ ", dbTsfConnTrgId=" + dbTsfConnTrgId + ", ddlTsfTblId="
				+ ddlTsfTblId + ", ddlTsfTblLnm=" + ddlTsfTblLnm
				+ ", ddlTsfTblPnm=" + ddlTsfTblPnm + ", ddlTsfColId="
				+ ddlTsfColId + ", ddlTsfColPnm=" + ddlTsfColPnm
				+ ", ddlTsfColLnm=" + ddlTsfColLnm + ", ddlTsfColOrd="
				+ ddlTsfColOrd + ", ddlTsfDataType=" + ddlTsfDataType
				+ ", ddlTsfDataLen=" + ddlTsfDataLen + ", ddlTsfDataScal="
				+ ddlTsfDataScal + ", ddlTsfPkYn=" + ddlTsfPkYn
				+ ", ddlTsfPkOrd=" + ddlTsfPkOrd + ", ddlTsfNonulYn="
				+ ddlTsfNonulYn + ", ddlTsfDefltVal=" + ddlTsfDefltVal
				+ ", ddlTsfColCnt=" + ddlTsfColCnt + ", dbRealSchId="
				+ dbRealSchId + ", dbcRealTblNm=" + dbcRealTblNm
				+ ", dbcRealColCnt=" + dbcRealColCnt + ", dbcRealColOrd="
				+ dbcRealColOrd + ", dbcRealDataType=" + dbcRealDataType
				+ ", dbcRealDataLen=" + dbcRealDataLen + ", dbcRealDataScal="
				+ dbcRealDataScal + ", dbcRealPkYn=" + dbcRealPkYn
				+ ", dbcRealPkOrd=" + dbcRealPkOrd + ", dbcRealNonulYn="
				+ dbcRealNonulYn + ", dbcRealDefltVal=" + dbcRealDefltVal
				+ ", dbcRealColPnm=" + dbcRealColPnm + ", dbcRealColLnm="
				+ dbcRealColLnm + ", dbcDbSchLnm=" + dbcDbSchLnm
				+ ", dbcDbConnTrgLnm=" + dbcDbConnTrgLnm + ", dbcRealDbSchLnm="
				+ dbcRealDbSchLnm + ", dbcRealDbConnTrgLnm="
				+ dbcRealDbConnTrgLnm + ", dbcTblLnm=" + dbcTblLnm
				+ ", dbcRealTblLnm=" + dbcRealTblLnm + ", fullPath=" + fullPath
				+ ", dbColCnt=" + dbColCnt + ", colGapCnt=" + colGapCnt
				+ ", sysAreaLnm=" + sysAreaLnm + ", sditmPnm=" + sditmPnm
				+ ", sditmLnm=" + sditmLnm + ", sditmDataType=" + sditmDataType
				+ ", sditmDataLen=" + sditmDataLen + ", sditmDataScal="
				+ sditmDataScal + ", dbmsTypCd=" + dbmsTypCd + ", uppSubjLnm="
				+ uppSubjLnm + ", erwinColPnm=" + erwinColPnm
				+ ", erwinColLnm=" + erwinColLnm + ", erwinColOrd="
				+ erwinColOrd + ", erwinPkYn=" + erwinPkYn + ", erwinDataType="
				+ erwinDataType + ", erwinDataLen=" + erwinDataLen
				+ ", erwinDataScal=" + erwinDataScal + ", erwinNonulYn="
				+ erwinNonulYn + ", erwinDefltVal=" + erwinDefltVal
				+ ", srcDbcColCnt=" + srcDbcColCnt + ", srcDbcTblPnm="
				+ srcDbcTblPnm + ", srcDbcTblLnm=" + srcDbcTblLnm
				+ ", srcDbcColPnm=" + srcDbcColPnm + ", srcDbcColLnm="
				+ srcDbcColLnm + ", srcDbcColSpnm=" + srcDbcColSpnm
				+ ", srcDbcColOrd=" + srcDbcColOrd + ", srcDbcPkYn="
				+ srcDbcPkYn + ", srcDbcDataType=" + srcDbcDataType
				+ ", srcDbcDataLen=" + srcDbcDataLen + ", srcDbcDataScal="
				+ srcDbcDataScal + ", srcDbcNonulYn=" + srcDbcNonulYn
				+ ", srcDbcDefltVal=" + srcDbcDefltVal + ", tgtDbcColCnt="
				+ tgtDbcColCnt + ", tgtDbcColPnm=" + tgtDbcColPnm
				+ ", tgtDbcColLnm=" + tgtDbcColLnm + ", tgtDbcColOrd="
				+ tgtDbcColOrd + ", tgtDbcPkYn=" + tgtDbcPkYn
				+ ", tgtDbcDataType=" + tgtDbcDataType + ", tgtDbcDataLen="
				+ tgtDbcDataLen + ", tgtDbcDataScal=" + tgtDbcDataScal
				+ ", tgtDbcNonulYn=" + tgtDbcNonulYn + ", tgtDbcDefltVal="
				+ tgtDbcDefltVal + ", srcDdlColCnt=" + srcDdlColCnt
				+ ", tgtDdlColCnt=" + tgtDdlColCnt + ", srcDdlTblId="
				+ srcDdlTblId + ", srcDdlTblPnm=" + srcDdlTblPnm
				+ ", srcDdlTblLnm=" + srcDdlTblLnm + ", srcDdlColPnm="
				+ srcDdlColPnm + ", srcDdlColLnm=" + srcDdlColLnm
				+ ", srcDdlColSpnm=" + srcDdlColSpnm + ", srcDdlColOrd="
				+ srcDdlColOrd + ", srcDdlPkYn=" + srcDdlPkYn
				+ ", srcDdlDataType=" + srcDdlDataType + ", srcDdlDataLen="
				+ srcDdlDataLen + ", srcDdlDataScal=" + srcDdlDataScal
				+ ", srcDdlNonulYn=" + srcDdlNonulYn + ", srcDdlDefltVal="
				+ srcDdlDefltVal + ", tgtDdlColPnm=" + tgtDdlColPnm
				+ ", tgtDdlColLnm=" + tgtDdlColLnm + ", tgtDdlColOrd="
				+ tgtDdlColOrd + ", tgtDdlPkYn=" + tgtDdlPkYn
				+ ", tgtDdlDataType=" + tgtDdlDataType + ", tgtDdlDataLen="
				+ tgtDdlDataLen + ", tgtDdlDataScal=" + tgtDdlDataScal
				+ ", tgtDdlNonulYn=" + tgtDdlNonulYn + ", tgtDdlDefltVal="
				+ tgtDdlDefltVal + ", ddlIdxPnm=" + ddlIdxPnm
				+ ", srcDdlIdxColCnt=" + srcDdlIdxColCnt + ", tgtDdlIdxColCnt="
				+ tgtDdlIdxColCnt + ", srcDdlIdxPnm=" + srcDdlIdxPnm
				+ ", srcUkIdxYn=" + srcUkIdxYn + ", srcDdlIdxColPnm="
				+ srcDdlIdxColPnm + ", srcDdlIdxColLnm=" + srcDdlIdxColLnm
				+ ", srcDdlIdxColOrd=" + srcDdlIdxColOrd + ", srcSortTyp="
				+ srcSortTyp + ", tgtDdlTblPnm=" + tgtDdlTblPnm
				+ ", tgtDdlTblLnm=" + tgtDdlTblLnm + ", tgtDdlIdxPnm="
				+ tgtDdlIdxPnm + ", tgtUkIdxYn=" + tgtUkIdxYn
				+ ", tgtDdlIdxColPnm=" + tgtDdlIdxColPnm + ", tgtDdlIdxColLnm="
				+ tgtDdlIdxColLnm + ", tgtDdlIdxColOrd=" + tgtDdlIdxColOrd
				+ ", tgtSortTyp=" + tgtSortTyp + ", regDtm=" + regDtm + "]";
	}
}
