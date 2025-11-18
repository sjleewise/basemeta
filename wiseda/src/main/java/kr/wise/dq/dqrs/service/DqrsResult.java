package kr.wise.dq.dqrs.service;

import java.math.BigDecimal;
import java.util.List;

import kr.wise.commons.cmm.CommonVo;

public class DqrsResult extends CommonVo {
	
	private String uppDqiLnm; 
	
	private String dqiLnm;	
	
	private String cntName;
	
	private BigDecimal totCnt;
	
	private BigDecimal errCnt;
	
	private Float errRate;
	
	private String errSql;
	
	private BigDecimal noExe;
	
	private BigDecimal colCnt;
	
	private BigDecimal tblCnt;
	
	private Float sumRate;
	
	private String dbConnTrgId;
	
	private String dbConnTrgPnm;
	
	private String dbConnTrgLnm;
	
	private String dbSchPnm;
	
	private String dbSchLnm;
	
	private String dbcTblNm; 
	
	private String dbcTblKorNm;
	
	private String dbcColNm;
	
	private String dbcColKorNm;
	
	private String dbSchId;
	
	private String anaStrDtm;
	
	private String anaEndDtm;
	
	private String prfKndCd;
	
	private String prfTyp;
	
	private String prfNm;
	
	private String prfId;
	
	private String prfYn;
	
	private String dataType;
	
	private String expYn;
	
	private String expRsnCntn;
	
	private String stndRate;
	
	private String goal;
	
	private String errLst;
	
	private BigDecimal errColCnt;
	
	private List<DqrsResultData> errList;
	
	private List<DqrsResultData> errData;
	
	private String pcolnm;
	private String bcolnm;
	private String bcolnm2;
	
	private String pdmTblPnm;
	private String pdmTblLnm;
	private String pdmColPnm;
	private String pdmColLnm;
	private String gapStatus;
	
	private String sditmLnm;
	private String sditmPnm;
	private String objDescn;
	private String infotpLnm;
	
	private String dmnLnm;
	private String dmnPnm;
	private String dataLen;
	private String dataScal;
	private String noNullYn;
	private String pkOrd;
	private String pkYn;
	
	private String itemNm;
	private String gapRate;
	private String resultDate;
	private String objCnt;
	private String errorCnt;
	
	private String govYn;
	private String orgYn;
	
	/////
	private String tblNm;
	private String state;
	private String detailStatus;
	private String colNm;
	
	private String stndType;
	
	
	//////////////////////////////////////////////////////
	

	private String subjLnm;
	private String pdmColCnt;
	private String ddlTblPnm;
	private String ddlColCnt;
	private String pdmGapColCntDdl;
	private String dbcColCnt;
	private String pdmGapColCntDbc;
	private String ddlGapColCntDbc;
	private String ddlTblErrDescn;
	private String ddlColErrDescn;
	private String pdmTblErrDescn;
	private String pdmColErrDescn;
	
	//검색조건들
	private String subjId;
	private String searchBgnDe;
	private String searchEndDe;
	private String searchTyp;
	private String objNm;
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
	
    private String sditmDataType;
    private String sditmDataLen;
    private String sditmDataScal;
    private String sditmDbConnTrgId;
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
    
    private String mngUserId;

	public String getUppDqiLnm() {
		return uppDqiLnm;
	}

	public String getDqiLnm() {
		return dqiLnm;
	}

	public String getCntName() {
		return cntName;
	}

	public BigDecimal getTotCnt() {
		return totCnt;
	}

	public BigDecimal getErrCnt() {
		return errCnt;
	}

	public Float getErrRate() {
		return errRate;
	}

	public String getErrSql() {
		return errSql;
	}

	public BigDecimal getNoExe() {
		return noExe;
	}

	public BigDecimal getColCnt() {
		return colCnt;
	}

	public BigDecimal getTblCnt() {
		return tblCnt;
	}

	public Float getSumRate() {
		return sumRate;
	}

	public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}

	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}

	public String getDbSchPnm() {
		return dbSchPnm;
	}

	public String getDbSchLnm() {
		return dbSchLnm;
	}

	public String getDbcTblNm() {
		return dbcTblNm;
	}

	public String getDbcTblKorNm() {
		return dbcTblKorNm;
	}

	public String getDbcColNm() {
		return dbcColNm;
	}

	public String getDbcColKorNm() {
		return dbcColKorNm;
	}

	public String getDbSchId() {
		return dbSchId;
	}

	public String getAnaStrDtm() {
		return anaStrDtm;
	}

	public String getAnaEndDtm() {
		return anaEndDtm;
	}

	public String getPrfKndCd() {
		return prfKndCd;
	}

	public String getPrfTyp() {
		return prfTyp;
	}

	public String getPrfNm() {
		return prfNm;
	}

	public String getPrfId() {
		return prfId;
	}

	public String getPrfYn() {
		return prfYn;
	}

	public String getDataType() {
		return dataType;
	}

	public String getExpYn() {
		return expYn;
	}

	public String getExpRsnCntn() {
		return expRsnCntn;
	}

	public String getStndRate() {
		return stndRate;
	}

	public String getGoal() {
		return goal;
	}

	public String getErrLst() {
		return errLst;
	}

	public BigDecimal getErrColCnt() {
		return errColCnt;
	}

	public List<DqrsResultData> getErrList() {
		return errList;
	}

	public List<DqrsResultData> getErrData() {
		return errData;
	}

	public String getPcolnm() {
		return pcolnm;
	}

	public String getBcolnm() {
		return bcolnm;
	}

	public String getBcolnm2() {
		return bcolnm2;
	}

	public String getPdmTblPnm() {
		return pdmTblPnm;
	}

	public String getPdmTblLnm() {
		return pdmTblLnm;
	}

	public String getPdmColPnm() {
		return pdmColPnm;
	}

	public String getPdmColLnm() {
		return pdmColLnm;
	}

	public String getGapStatus() {
		return gapStatus;
	}

	public String getSditmLnm() {
		return sditmLnm;
	}

	public String getSditmPnm() {
		return sditmPnm;
	}

	public String getObjDescn() {
		return objDescn;
	}

	public String getInfotpLnm() {
		return infotpLnm;
	}

	public String getDmnLnm() {
		return dmnLnm;
	}

	public String getDmnPnm() {
		return dmnPnm;
	}

	public String getDataLen() {
		return dataLen;
	}

	public String getDataScal() {
		return dataScal;
	}

	public String getNoNullYn() {
		return noNullYn;
	}

	public String getPkOrd() {
		return pkOrd;
	}

	public String getPkYn() {
		return pkYn;
	}

	public String getItemNm() {
		return itemNm;
	}

	public String getGapRate() {
		return gapRate;
	}

	public String getResultDate() {
		return resultDate;
	}

	public String getObjCnt() {
		return objCnt;
	}

	public String getErrorCnt() {
		return errorCnt;
	}

	public String getGovYn() {
		return govYn;
	}

	public String getOrgYn() {
		return orgYn;
	}

	public String getTblNm() {
		return tblNm;
	}

	public String getState() {
		return state;
	}

	public String getDetailStatus() {
		return detailStatus;
	}

	public String getColNm() {
		return colNm;
	}

	public String getStndType() {
		return stndType;
	}

	public String getSubjLnm() {
		return subjLnm;
	}

	public String getPdmColCnt() {
		return pdmColCnt;
	}

	public String getDdlTblPnm() {
		return ddlTblPnm;
	}

	public String getDdlColCnt() {
		return ddlColCnt;
	}

	public String getPdmGapColCntDdl() {
		return pdmGapColCntDdl;
	}

	public String getDbcColCnt() {
		return dbcColCnt;
	}

	public String getPdmGapColCntDbc() {
		return pdmGapColCntDbc;
	}

	public String getDdlGapColCntDbc() {
		return ddlGapColCntDbc;
	}

	public String getDdlTblErrDescn() {
		return ddlTblErrDescn;
	}

	public String getDdlColErrDescn() {
		return ddlColErrDescn;
	}

	public String getPdmTblErrDescn() {
		return pdmTblErrDescn;
	}

	public String getPdmColErrDescn() {
		return pdmColErrDescn;
	}

	public String getSubjId() {
		return subjId;
	}

	public String getSearchBgnDe() {
		return searchBgnDe;
	}

	public String getSearchEndDe() {
		return searchEndDe;
	}

	public String getSearchTyp() {
		return searchTyp;
	}

	public String getObjNm() {
		return objNm;
	}

	public String getPdmTblId() {
		return pdmTblId;
	}

	public String getDdlTblId() {
		return ddlTblId;
	}

	public String getDdlTrgDcd() {
		return ddlTrgDcd;
	}

	public String getPdmTblCnt() {
		return pdmTblCnt;
	}

	public String getGapCnt() {
		return gapCnt;
	}

	public String getNmlCnt() {
		return nmlCnt;
	}

	public String getDdlColOrd() {
		return ddlColOrd;
	}

	public String getDdlDataType() {
		return ddlDataType;
	}

	public String getDdlDataLen() {
		return ddlDataLen;
	}

	public String getDdlDataScal() {
		return ddlDataScal;
	}

	public String getDdlPkYn() {
		return ddlPkYn;
	}

	public String getDdlPkOrd() {
		return ddlPkOrd;
	}

	public String getDdlNonulYn() {
		return ddlNonulYn;
	}

	public String getDdlDefltVal() {
		return ddlDefltVal;
	}

	public String getPdmColOrd() {
		return pdmColOrd;
	}

	public String getPdmDataType() {
		return pdmDataType;
	}

	public String getPdmDataLen() {
		return pdmDataLen;
	}

	public String getPdmDataScal() {
		return pdmDataScal;
	}

	public String getPdmPkYn() {
		return pdmPkYn;
	}

	public String getPdmPkOrd() {
		return pdmPkOrd;
	}

	public String getPdmNonulYn() {
		return pdmNonulYn;
	}

	public String getPdmDefltVal() {
		return pdmDefltVal;
	}

	public String getDdlColId() {
		return ddlColId;
	}

	public String getDdlColPnm() {
		return ddlColPnm;
	}

	public String getDdlColLnm() {
		return ddlColLnm;
	}

	public String getPdmColId() {
		return pdmColId;
	}

	public String getDbcColOrd() {
		return dbcColOrd;
	}

	public String getDbcDataType() {
		return dbcDataType;
	}

	public String getDbcDataLen() {
		return dbcDataLen;
	}

	public String getDbcDataScal() {
		return dbcDataScal;
	}

	public String getDbcPkYn() {
		return dbcPkYn;
	}

	public String getDbcPkOrd() {
		return dbcPkOrd;
	}

	public String getDbcNonulYn() {
		return dbcNonulYn;
	}

	public String getDbcDefltVal() {
		return dbcDefltVal;
	}

	public String getDbcColPnm() {
		return dbcColPnm;
	}

	public String getDbcColLnm() {
		return dbcColLnm;
	}

	public String getDdlTblLnm() {
		return ddlTblLnm;
	}

	public String getMartTblPnm() {
		return martTblPnm;
	}

	public String getMartTblLnm() {
		return martTblLnm;
	}

	public String getMartColCnt() {
		return martColCnt;
	}

	public String getMartColPnm() {
		return martColPnm;
	}

	public String getMartColLnm() {
		return martColLnm;
	}

	public String getMartColOrd() {
		return martColOrd;
	}

	public String getMartDataType() {
		return martDataType;
	}

	public String getMartDataLen() {
		return martDataLen;
	}

	public String getMartDataScal() {
		return martDataScal;
	}

	public String getMartPkYn() {
		return martPkYn;
	}

	public String getMartPkOrd() {
		return martPkOrd;
	}

	public String getMartNonulYn() {
		return martNonulYn;
	}

	public String getMartDefltVal() {
		return martDefltVal;
	}

	public String getGapConts() {
		return gapConts;
	}

	public String getDbTsfSchLnm() {
		return dbTsfSchLnm;
	}

	public String getDbTsfConnTrgLnm() {
		return dbTsfConnTrgLnm;
	}

	public String getDbTsfSchId() {
		return dbTsfSchId;
	}

	public String getDbTsfConnTrgId() {
		return dbTsfConnTrgId;
	}

	public String getDdlTsfTblId() {
		return ddlTsfTblId;
	}

	public String getDdlTsfTblLnm() {
		return ddlTsfTblLnm;
	}

	public String getDdlTsfTblPnm() {
		return ddlTsfTblPnm;
	}

	public String getDdlTsfColId() {
		return ddlTsfColId;
	}

	public String getDdlTsfColPnm() {
		return ddlTsfColPnm;
	}

	public String getDdlTsfColLnm() {
		return ddlTsfColLnm;
	}

	public String getDdlTsfColOrd() {
		return ddlTsfColOrd;
	}

	public String getDdlTsfDataType() {
		return ddlTsfDataType;
	}

	public String getDdlTsfDataLen() {
		return ddlTsfDataLen;
	}

	public String getDdlTsfDataScal() {
		return ddlTsfDataScal;
	}

	public String getDdlTsfPkYn() {
		return ddlTsfPkYn;
	}

	public String getDdlTsfPkOrd() {
		return ddlTsfPkOrd;
	}

	public String getDdlTsfNonulYn() {
		return ddlTsfNonulYn;
	}

	public String getDdlTsfDefltVal() {
		return ddlTsfDefltVal;
	}

	public String getDdlTsfColCnt() {
		return ddlTsfColCnt;
	}

	public String getDbRealSchId() {
		return dbRealSchId;
	}

	public String getDbcRealTblNm() {
		return dbcRealTblNm;
	}

	public String getDbcRealColCnt() {
		return dbcRealColCnt;
	}

	public String getDbcRealColOrd() {
		return dbcRealColOrd;
	}

	public String getDbcRealDataType() {
		return dbcRealDataType;
	}

	public String getDbcRealDataLen() {
		return dbcRealDataLen;
	}

	public String getDbcRealDataScal() {
		return dbcRealDataScal;
	}

	public String getDbcRealPkYn() {
		return dbcRealPkYn;
	}

	public String getDbcRealPkOrd() {
		return dbcRealPkOrd;
	}

	public String getDbcRealNonulYn() {
		return dbcRealNonulYn;
	}

	public String getDbcRealDefltVal() {
		return dbcRealDefltVal;
	}

	public String getDbcRealColPnm() {
		return dbcRealColPnm;
	}

	public String getDbcRealColLnm() {
		return dbcRealColLnm;
	}

	public String getDbcDbSchLnm() {
		return dbcDbSchLnm;
	}

	public String getDbcDbConnTrgLnm() {
		return dbcDbConnTrgLnm;
	}

	public String getDbcRealDbSchLnm() {
		return dbcRealDbSchLnm;
	}

	public String getDbcRealDbConnTrgLnm() {
		return dbcRealDbConnTrgLnm;
	}

	public String getDbcTblLnm() {
		return dbcTblLnm;
	}

	public String getDbcRealTblLnm() {
		return dbcRealTblLnm;
	}

	public String getFullPath() {
		return fullPath;
	}

	public String getDbColCnt() {
		return dbColCnt;
	}

	public String getColGapCnt() {
		return colGapCnt;
	}

	public String getSysAreaLnm() {
		return sysAreaLnm;
	}

	public String getSditmDataType() {
		return sditmDataType;
	}

	public String getSditmDataLen() {
		return sditmDataLen;
	}

	public String getSditmDataScal() {
		return sditmDataScal;
	}

	public String getSditmDbConnTrgId() {
		return sditmDbConnTrgId;
	}

	public String getDbmsTypCd() {
		return dbmsTypCd;
	}

	public String getUppSubjLnm() {
		return uppSubjLnm;
	}

	public String getErwinColPnm() {
		return erwinColPnm;
	}

	public String getErwinColLnm() {
		return erwinColLnm;
	}

	public String getErwinColOrd() {
		return erwinColOrd;
	}

	public String getErwinPkYn() {
		return erwinPkYn;
	}

	public String getErwinDataType() {
		return erwinDataType;
	}

	public String getErwinDataLen() {
		return erwinDataLen;
	}

	public String getErwinDataScal() {
		return erwinDataScal;
	}

	public String getErwinNonulYn() {
		return erwinNonulYn;
	}

	public String getErwinDefltVal() {
		return erwinDefltVal;
	}

	public String getSrcDbcColCnt() {
		return srcDbcColCnt;
	}

	public String getSrcDbcTblPnm() {
		return srcDbcTblPnm;
	}

	public String getSrcDbcTblLnm() {
		return srcDbcTblLnm;
	}

	public String getSrcDbcColPnm() {
		return srcDbcColPnm;
	}

	public String getSrcDbcColLnm() {
		return srcDbcColLnm;
	}

	public String getSrcDbcColSpnm() {
		return srcDbcColSpnm;
	}

	public String getSrcDbcColOrd() {
		return srcDbcColOrd;
	}

	public String getSrcDbcPkYn() {
		return srcDbcPkYn;
	}

	public String getSrcDbcDataType() {
		return srcDbcDataType;
	}

	public String getSrcDbcDataLen() {
		return srcDbcDataLen;
	}

	public String getSrcDbcDataScal() {
		return srcDbcDataScal;
	}

	public String getSrcDbcNonulYn() {
		return srcDbcNonulYn;
	}

	public String getSrcDbcDefltVal() {
		return srcDbcDefltVal;
	}

	public String getTgtDbcColCnt() {
		return tgtDbcColCnt;
	}

	public String getTgtDbcColPnm() {
		return tgtDbcColPnm;
	}

	public String getTgtDbcColLnm() {
		return tgtDbcColLnm;
	}

	public String getTgtDbcColOrd() {
		return tgtDbcColOrd;
	}

	public String getTgtDbcPkYn() {
		return tgtDbcPkYn;
	}

	public String getTgtDbcDataType() {
		return tgtDbcDataType;
	}

	public String getTgtDbcDataLen() {
		return tgtDbcDataLen;
	}

	public String getTgtDbcDataScal() {
		return tgtDbcDataScal;
	}

	public String getTgtDbcNonulYn() {
		return tgtDbcNonulYn;
	}

	public String getTgtDbcDefltVal() {
		return tgtDbcDefltVal;
	}

	public String getSrcDdlColCnt() {
		return srcDdlColCnt;
	}

	public String getTgtDdlColCnt() {
		return tgtDdlColCnt;
	}

	public String getSrcDdlTblId() {
		return srcDdlTblId;
	}

	public String getSrcDdlTblPnm() {
		return srcDdlTblPnm;
	}

	public String getSrcDdlTblLnm() {
		return srcDdlTblLnm;
	}

	public String getSrcDdlColPnm() {
		return srcDdlColPnm;
	}

	public String getSrcDdlColLnm() {
		return srcDdlColLnm;
	}

	public String getSrcDdlColSpnm() {
		return srcDdlColSpnm;
	}

	public String getSrcDdlColOrd() {
		return srcDdlColOrd;
	}

	public String getSrcDdlPkYn() {
		return srcDdlPkYn;
	}

	public String getSrcDdlDataType() {
		return srcDdlDataType;
	}

	public String getSrcDdlDataLen() {
		return srcDdlDataLen;
	}

	public String getSrcDdlDataScal() {
		return srcDdlDataScal;
	}

	public String getSrcDdlNonulYn() {
		return srcDdlNonulYn;
	}

	public String getSrcDdlDefltVal() {
		return srcDdlDefltVal;
	}

	public String getTgtDdlColPnm() {
		return tgtDdlColPnm;
	}

	public String getTgtDdlColLnm() {
		return tgtDdlColLnm;
	}

	public String getTgtDdlColOrd() {
		return tgtDdlColOrd;
	}

	public String getTgtDdlPkYn() {
		return tgtDdlPkYn;
	}

	public String getTgtDdlDataType() {
		return tgtDdlDataType;
	}

	public String getTgtDdlDataLen() {
		return tgtDdlDataLen;
	}

	public String getTgtDdlDataScal() {
		return tgtDdlDataScal;
	}

	public String getTgtDdlNonulYn() {
		return tgtDdlNonulYn;
	}

	public String getTgtDdlDefltVal() {
		return tgtDdlDefltVal;
	}

	public String getDdlIdxPnm() {
		return ddlIdxPnm;
	}

	public String getSrcDdlIdxColCnt() {
		return srcDdlIdxColCnt;
	}

	public String getTgtDdlIdxColCnt() {
		return tgtDdlIdxColCnt;
	}

	public String getSrcDdlIdxPnm() {
		return srcDdlIdxPnm;
	}

	public String getSrcUkIdxYn() {
		return srcUkIdxYn;
	}

	public String getSrcDdlIdxColPnm() {
		return srcDdlIdxColPnm;
	}

	public String getSrcDdlIdxColLnm() {
		return srcDdlIdxColLnm;
	}

	public String getSrcDdlIdxColOrd() {
		return srcDdlIdxColOrd;
	}

	public String getSrcSortTyp() {
		return srcSortTyp;
	}

	public String getTgtDdlTblPnm() {
		return tgtDdlTblPnm;
	}

	public String getTgtDdlTblLnm() {
		return tgtDdlTblLnm;
	}

	public String getTgtDdlIdxPnm() {
		return tgtDdlIdxPnm;
	}

	public String getTgtUkIdxYn() {
		return tgtUkIdxYn;
	}

	public String getTgtDdlIdxColPnm() {
		return tgtDdlIdxColPnm;
	}

	public String getTgtDdlIdxColLnm() {
		return tgtDdlIdxColLnm;
	}

	public String getTgtDdlIdxColOrd() {
		return tgtDdlIdxColOrd;
	}

	public String getTgtSortTyp() {
		return tgtSortTyp;
	}

	public String getRegDtm() {
		return regDtm;
	}

	public String getGapDcd() {
		return gapDcd;
	}

	public String getMngUserId() {
		return mngUserId;
	}

	public void setUppDqiLnm(String uppDqiLnm) {
		this.uppDqiLnm = uppDqiLnm;
	}

	public void setDqiLnm(String dqiLnm) {
		this.dqiLnm = dqiLnm;
	}

	public void setCntName(String cntName) {
		this.cntName = cntName;
	}

	public void setTotCnt(BigDecimal totCnt) {
		this.totCnt = totCnt;
	}

	public void setErrCnt(BigDecimal errCnt) {
		this.errCnt = errCnt;
	}

	public void setErrRate(Float errRate) {
		this.errRate = errRate;
	}

	public void setErrSql(String errSql) {
		this.errSql = errSql;
	}

	public void setNoExe(BigDecimal noExe) {
		this.noExe = noExe;
	}

	public void setColCnt(BigDecimal colCnt) {
		this.colCnt = colCnt;
	}

	public void setTblCnt(BigDecimal tblCnt) {
		this.tblCnt = tblCnt;
	}

	public void setSumRate(Float sumRate) {
		this.sumRate = sumRate;
	}

	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}

	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}

	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
	}

	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
	}

	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}

	public void setDbcTblNm(String dbcTblNm) {
		this.dbcTblNm = dbcTblNm;
	}

	public void setDbcTblKorNm(String dbcTblKorNm) {
		this.dbcTblKorNm = dbcTblKorNm;
	}

	public void setDbcColNm(String dbcColNm) {
		this.dbcColNm = dbcColNm;
	}

	public void setDbcColKorNm(String dbcColKorNm) {
		this.dbcColKorNm = dbcColKorNm;
	}

	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}

	public void setAnaStrDtm(String anaStrDtm) {
		this.anaStrDtm = anaStrDtm;
	}

	public void setAnaEndDtm(String anaEndDtm) {
		this.anaEndDtm = anaEndDtm;
	}

	public void setPrfKndCd(String prfKndCd) {
		this.prfKndCd = prfKndCd;
	}

	public void setPrfTyp(String prfTyp) {
		this.prfTyp = prfTyp;
	}

	public void setPrfNm(String prfNm) {
		this.prfNm = prfNm;
	}

	public void setPrfId(String prfId) {
		this.prfId = prfId;
	}

	public void setPrfYn(String prfYn) {
		this.prfYn = prfYn;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setExpYn(String expYn) {
		this.expYn = expYn;
	}

	public void setExpRsnCntn(String expRsnCntn) {
		this.expRsnCntn = expRsnCntn;
	}

	public void setStndRate(String stndRate) {
		this.stndRate = stndRate;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public void setErrLst(String errLst) {
		this.errLst = errLst;
	}

	public void setErrColCnt(BigDecimal errColCnt) {
		this.errColCnt = errColCnt;
	}

	public void setErrList(List<DqrsResultData> errList) {
		this.errList = errList;
	}

	public void setErrData(List<DqrsResultData> errData) {
		this.errData = errData;
	}

	public void setPcolnm(String pcolnm) {
		this.pcolnm = pcolnm;
	}

	public void setBcolnm(String bcolnm) {
		this.bcolnm = bcolnm;
	}

	public void setBcolnm2(String bcolnm2) {
		this.bcolnm2 = bcolnm2;
	}

	public void setPdmTblPnm(String pdmTblPnm) {
		this.pdmTblPnm = pdmTblPnm;
	}

	public void setPdmTblLnm(String pdmTblLnm) {
		this.pdmTblLnm = pdmTblLnm;
	}

	public void setPdmColPnm(String pdmColPnm) {
		this.pdmColPnm = pdmColPnm;
	}

	public void setPdmColLnm(String pdmColLnm) {
		this.pdmColLnm = pdmColLnm;
	}

	public void setGapStatus(String gapStatus) {
		this.gapStatus = gapStatus;
	}

	public void setSditmLnm(String sditmLnm) {
		this.sditmLnm = sditmLnm;
	}

	public void setSditmPnm(String sditmPnm) {
		this.sditmPnm = sditmPnm;
	}

	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}

	public void setInfotpLnm(String infotpLnm) {
		this.infotpLnm = infotpLnm;
	}

	public void setDmnLnm(String dmnLnm) {
		this.dmnLnm = dmnLnm;
	}

	public void setDmnPnm(String dmnPnm) {
		this.dmnPnm = dmnPnm;
	}

	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}

	public void setDataScal(String dataScal) {
		this.dataScal = dataScal;
	}

	public void setNoNullYn(String noNullYn) {
		this.noNullYn = noNullYn;
	}

	public void setPkOrd(String pkOrd) {
		this.pkOrd = pkOrd;
	}

	public void setPkYn(String pkYn) {
		this.pkYn = pkYn;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	public void setGapRate(String gapRate) {
		this.gapRate = gapRate;
	}

	public void setResultDate(String resultDate) {
		this.resultDate = resultDate;
	}

	public void setObjCnt(String objCnt) {
		this.objCnt = objCnt;
	}

	public void setErrorCnt(String errorCnt) {
		this.errorCnt = errorCnt;
	}

	public void setGovYn(String govYn) {
		this.govYn = govYn;
	}

	public void setOrgYn(String orgYn) {
		this.orgYn = orgYn;
	}

	public void setTblNm(String tblNm) {
		this.tblNm = tblNm;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}

	public void setColNm(String colNm) {
		this.colNm = colNm;
	}

	public void setStndType(String stndType) {
		this.stndType = stndType;
	}

	public void setSubjLnm(String subjLnm) {
		this.subjLnm = subjLnm;
	}

	public void setPdmColCnt(String pdmColCnt) {
		this.pdmColCnt = pdmColCnt;
	}

	public void setDdlTblPnm(String ddlTblPnm) {
		this.ddlTblPnm = ddlTblPnm;
	}

	public void setDdlColCnt(String ddlColCnt) {
		this.ddlColCnt = ddlColCnt;
	}

	public void setPdmGapColCntDdl(String pdmGapColCntDdl) {
		this.pdmGapColCntDdl = pdmGapColCntDdl;
	}

	public void setDbcColCnt(String dbcColCnt) {
		this.dbcColCnt = dbcColCnt;
	}

	public void setPdmGapColCntDbc(String pdmGapColCntDbc) {
		this.pdmGapColCntDbc = pdmGapColCntDbc;
	}

	public void setDdlGapColCntDbc(String ddlGapColCntDbc) {
		this.ddlGapColCntDbc = ddlGapColCntDbc;
	}

	public void setDdlTblErrDescn(String ddlTblErrDescn) {
		this.ddlTblErrDescn = ddlTblErrDescn;
	}

	public void setDdlColErrDescn(String ddlColErrDescn) {
		this.ddlColErrDescn = ddlColErrDescn;
	}

	public void setPdmTblErrDescn(String pdmTblErrDescn) {
		this.pdmTblErrDescn = pdmTblErrDescn;
	}

	public void setPdmColErrDescn(String pdmColErrDescn) {
		this.pdmColErrDescn = pdmColErrDescn;
	}

	public void setSubjId(String subjId) {
		this.subjId = subjId;
	}

	public void setSearchBgnDe(String searchBgnDe) {
		this.searchBgnDe = searchBgnDe;
	}

	public void setSearchEndDe(String searchEndDe) {
		this.searchEndDe = searchEndDe;
	}

	public void setSearchTyp(String searchTyp) {
		this.searchTyp = searchTyp;
	}

	public void setObjNm(String objNm) {
		this.objNm = objNm;
	}

	public void setPdmTblId(String pdmTblId) {
		this.pdmTblId = pdmTblId;
	}

	public void setDdlTblId(String ddlTblId) {
		this.ddlTblId = ddlTblId;
	}

	public void setDdlTrgDcd(String ddlTrgDcd) {
		this.ddlTrgDcd = ddlTrgDcd;
	}

	public void setPdmTblCnt(String pdmTblCnt) {
		this.pdmTblCnt = pdmTblCnt;
	}

	public void setGapCnt(String gapCnt) {
		this.gapCnt = gapCnt;
	}

	public void setNmlCnt(String nmlCnt) {
		this.nmlCnt = nmlCnt;
	}

	public void setDdlColOrd(String ddlColOrd) {
		this.ddlColOrd = ddlColOrd;
	}

	public void setDdlDataType(String ddlDataType) {
		this.ddlDataType = ddlDataType;
	}

	public void setDdlDataLen(String ddlDataLen) {
		this.ddlDataLen = ddlDataLen;
	}

	public void setDdlDataScal(String ddlDataScal) {
		this.ddlDataScal = ddlDataScal;
	}

	public void setDdlPkYn(String ddlPkYn) {
		this.ddlPkYn = ddlPkYn;
	}

	public void setDdlPkOrd(String ddlPkOrd) {
		this.ddlPkOrd = ddlPkOrd;
	}

	public void setDdlNonulYn(String ddlNonulYn) {
		this.ddlNonulYn = ddlNonulYn;
	}

	public void setDdlDefltVal(String ddlDefltVal) {
		this.ddlDefltVal = ddlDefltVal;
	}

	public void setPdmColOrd(String pdmColOrd) {
		this.pdmColOrd = pdmColOrd;
	}

	public void setPdmDataType(String pdmDataType) {
		this.pdmDataType = pdmDataType;
	}

	public void setPdmDataLen(String pdmDataLen) {
		this.pdmDataLen = pdmDataLen;
	}

	public void setPdmDataScal(String pdmDataScal) {
		this.pdmDataScal = pdmDataScal;
	}

	public void setPdmPkYn(String pdmPkYn) {
		this.pdmPkYn = pdmPkYn;
	}

	public void setPdmPkOrd(String pdmPkOrd) {
		this.pdmPkOrd = pdmPkOrd;
	}

	public void setPdmNonulYn(String pdmNonulYn) {
		this.pdmNonulYn = pdmNonulYn;
	}

	public void setPdmDefltVal(String pdmDefltVal) {
		this.pdmDefltVal = pdmDefltVal;
	}

	public void setDdlColId(String ddlColId) {
		this.ddlColId = ddlColId;
	}

	public void setDdlColPnm(String ddlColPnm) {
		this.ddlColPnm = ddlColPnm;
	}

	public void setDdlColLnm(String ddlColLnm) {
		this.ddlColLnm = ddlColLnm;
	}

	public void setPdmColId(String pdmColId) {
		this.pdmColId = pdmColId;
	}

	public void setDbcColOrd(String dbcColOrd) {
		this.dbcColOrd = dbcColOrd;
	}

	public void setDbcDataType(String dbcDataType) {
		this.dbcDataType = dbcDataType;
	}

	public void setDbcDataLen(String dbcDataLen) {
		this.dbcDataLen = dbcDataLen;
	}

	public void setDbcDataScal(String dbcDataScal) {
		this.dbcDataScal = dbcDataScal;
	}

	public void setDbcPkYn(String dbcPkYn) {
		this.dbcPkYn = dbcPkYn;
	}

	public void setDbcPkOrd(String dbcPkOrd) {
		this.dbcPkOrd = dbcPkOrd;
	}

	public void setDbcNonulYn(String dbcNonulYn) {
		this.dbcNonulYn = dbcNonulYn;
	}

	public void setDbcDefltVal(String dbcDefltVal) {
		this.dbcDefltVal = dbcDefltVal;
	}

	public void setDbcColPnm(String dbcColPnm) {
		this.dbcColPnm = dbcColPnm;
	}

	public void setDbcColLnm(String dbcColLnm) {
		this.dbcColLnm = dbcColLnm;
	}

	public void setDdlTblLnm(String ddlTblLnm) {
		this.ddlTblLnm = ddlTblLnm;
	}

	public void setMartTblPnm(String martTblPnm) {
		this.martTblPnm = martTblPnm;
	}

	public void setMartTblLnm(String martTblLnm) {
		this.martTblLnm = martTblLnm;
	}

	public void setMartColCnt(String martColCnt) {
		this.martColCnt = martColCnt;
	}

	public void setMartColPnm(String martColPnm) {
		this.martColPnm = martColPnm;
	}

	public void setMartColLnm(String martColLnm) {
		this.martColLnm = martColLnm;
	}

	public void setMartColOrd(String martColOrd) {
		this.martColOrd = martColOrd;
	}

	public void setMartDataType(String martDataType) {
		this.martDataType = martDataType;
	}

	public void setMartDataLen(String martDataLen) {
		this.martDataLen = martDataLen;
	}

	public void setMartDataScal(String martDataScal) {
		this.martDataScal = martDataScal;
	}

	public void setMartPkYn(String martPkYn) {
		this.martPkYn = martPkYn;
	}

	public void setMartPkOrd(String martPkOrd) {
		this.martPkOrd = martPkOrd;
	}

	public void setMartNonulYn(String martNonulYn) {
		this.martNonulYn = martNonulYn;
	}

	public void setMartDefltVal(String martDefltVal) {
		this.martDefltVal = martDefltVal;
	}

	public void setGapConts(String gapConts) {
		this.gapConts = gapConts;
	}

	public void setDbTsfSchLnm(String dbTsfSchLnm) {
		this.dbTsfSchLnm = dbTsfSchLnm;
	}

	public void setDbTsfConnTrgLnm(String dbTsfConnTrgLnm) {
		this.dbTsfConnTrgLnm = dbTsfConnTrgLnm;
	}

	public void setDbTsfSchId(String dbTsfSchId) {
		this.dbTsfSchId = dbTsfSchId;
	}

	public void setDbTsfConnTrgId(String dbTsfConnTrgId) {
		this.dbTsfConnTrgId = dbTsfConnTrgId;
	}

	public void setDdlTsfTblId(String ddlTsfTblId) {
		this.ddlTsfTblId = ddlTsfTblId;
	}

	public void setDdlTsfTblLnm(String ddlTsfTblLnm) {
		this.ddlTsfTblLnm = ddlTsfTblLnm;
	}

	public void setDdlTsfTblPnm(String ddlTsfTblPnm) {
		this.ddlTsfTblPnm = ddlTsfTblPnm;
	}

	public void setDdlTsfColId(String ddlTsfColId) {
		this.ddlTsfColId = ddlTsfColId;
	}

	public void setDdlTsfColPnm(String ddlTsfColPnm) {
		this.ddlTsfColPnm = ddlTsfColPnm;
	}

	public void setDdlTsfColLnm(String ddlTsfColLnm) {
		this.ddlTsfColLnm = ddlTsfColLnm;
	}

	public void setDdlTsfColOrd(String ddlTsfColOrd) {
		this.ddlTsfColOrd = ddlTsfColOrd;
	}

	public void setDdlTsfDataType(String ddlTsfDataType) {
		this.ddlTsfDataType = ddlTsfDataType;
	}

	public void setDdlTsfDataLen(String ddlTsfDataLen) {
		this.ddlTsfDataLen = ddlTsfDataLen;
	}

	public void setDdlTsfDataScal(String ddlTsfDataScal) {
		this.ddlTsfDataScal = ddlTsfDataScal;
	}

	public void setDdlTsfPkYn(String ddlTsfPkYn) {
		this.ddlTsfPkYn = ddlTsfPkYn;
	}

	public void setDdlTsfPkOrd(String ddlTsfPkOrd) {
		this.ddlTsfPkOrd = ddlTsfPkOrd;
	}

	public void setDdlTsfNonulYn(String ddlTsfNonulYn) {
		this.ddlTsfNonulYn = ddlTsfNonulYn;
	}

	public void setDdlTsfDefltVal(String ddlTsfDefltVal) {
		this.ddlTsfDefltVal = ddlTsfDefltVal;
	}

	public void setDdlTsfColCnt(String ddlTsfColCnt) {
		this.ddlTsfColCnt = ddlTsfColCnt;
	}

	public void setDbRealSchId(String dbRealSchId) {
		this.dbRealSchId = dbRealSchId;
	}

	public void setDbcRealTblNm(String dbcRealTblNm) {
		this.dbcRealTblNm = dbcRealTblNm;
	}

	public void setDbcRealColCnt(String dbcRealColCnt) {
		this.dbcRealColCnt = dbcRealColCnt;
	}

	public void setDbcRealColOrd(String dbcRealColOrd) {
		this.dbcRealColOrd = dbcRealColOrd;
	}

	public void setDbcRealDataType(String dbcRealDataType) {
		this.dbcRealDataType = dbcRealDataType;
	}

	public void setDbcRealDataLen(String dbcRealDataLen) {
		this.dbcRealDataLen = dbcRealDataLen;
	}

	public void setDbcRealDataScal(String dbcRealDataScal) {
		this.dbcRealDataScal = dbcRealDataScal;
	}

	public void setDbcRealPkYn(String dbcRealPkYn) {
		this.dbcRealPkYn = dbcRealPkYn;
	}

	public void setDbcRealPkOrd(String dbcRealPkOrd) {
		this.dbcRealPkOrd = dbcRealPkOrd;
	}

	public void setDbcRealNonulYn(String dbcRealNonulYn) {
		this.dbcRealNonulYn = dbcRealNonulYn;
	}

	public void setDbcRealDefltVal(String dbcRealDefltVal) {
		this.dbcRealDefltVal = dbcRealDefltVal;
	}

	public void setDbcRealColPnm(String dbcRealColPnm) {
		this.dbcRealColPnm = dbcRealColPnm;
	}

	public void setDbcRealColLnm(String dbcRealColLnm) {
		this.dbcRealColLnm = dbcRealColLnm;
	}

	public void setDbcDbSchLnm(String dbcDbSchLnm) {
		this.dbcDbSchLnm = dbcDbSchLnm;
	}

	public void setDbcDbConnTrgLnm(String dbcDbConnTrgLnm) {
		this.dbcDbConnTrgLnm = dbcDbConnTrgLnm;
	}

	public void setDbcRealDbSchLnm(String dbcRealDbSchLnm) {
		this.dbcRealDbSchLnm = dbcRealDbSchLnm;
	}

	public void setDbcRealDbConnTrgLnm(String dbcRealDbConnTrgLnm) {
		this.dbcRealDbConnTrgLnm = dbcRealDbConnTrgLnm;
	}

	public void setDbcTblLnm(String dbcTblLnm) {
		this.dbcTblLnm = dbcTblLnm;
	}

	public void setDbcRealTblLnm(String dbcRealTblLnm) {
		this.dbcRealTblLnm = dbcRealTblLnm;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public void setDbColCnt(String dbColCnt) {
		this.dbColCnt = dbColCnt;
	}

	public void setColGapCnt(String colGapCnt) {
		this.colGapCnt = colGapCnt;
	}

	public void setSysAreaLnm(String sysAreaLnm) {
		this.sysAreaLnm = sysAreaLnm;
	}

	public void setSditmDataType(String sditmDataType) {
		this.sditmDataType = sditmDataType;
	}

	public void setSditmDataLen(String sditmDataLen) {
		this.sditmDataLen = sditmDataLen;
	}

	public void setSditmDataScal(String sditmDataScal) {
		this.sditmDataScal = sditmDataScal;
	}

	public void setSditmDbConnTrgId(String sditmDbConnTrgId) {
		this.sditmDbConnTrgId = sditmDbConnTrgId;
	}

	public void setDbmsTypCd(String dbmsTypCd) {
		this.dbmsTypCd = dbmsTypCd;
	}

	public void setUppSubjLnm(String uppSubjLnm) {
		this.uppSubjLnm = uppSubjLnm;
	}

	public void setErwinColPnm(String erwinColPnm) {
		this.erwinColPnm = erwinColPnm;
	}

	public void setErwinColLnm(String erwinColLnm) {
		this.erwinColLnm = erwinColLnm;
	}

	public void setErwinColOrd(String erwinColOrd) {
		this.erwinColOrd = erwinColOrd;
	}

	public void setErwinPkYn(String erwinPkYn) {
		this.erwinPkYn = erwinPkYn;
	}

	public void setErwinDataType(String erwinDataType) {
		this.erwinDataType = erwinDataType;
	}

	public void setErwinDataLen(String erwinDataLen) {
		this.erwinDataLen = erwinDataLen;
	}

	public void setErwinDataScal(String erwinDataScal) {
		this.erwinDataScal = erwinDataScal;
	}

	public void setErwinNonulYn(String erwinNonulYn) {
		this.erwinNonulYn = erwinNonulYn;
	}

	public void setErwinDefltVal(String erwinDefltVal) {
		this.erwinDefltVal = erwinDefltVal;
	}

	public void setSrcDbcColCnt(String srcDbcColCnt) {
		this.srcDbcColCnt = srcDbcColCnt;
	}

	public void setSrcDbcTblPnm(String srcDbcTblPnm) {
		this.srcDbcTblPnm = srcDbcTblPnm;
	}

	public void setSrcDbcTblLnm(String srcDbcTblLnm) {
		this.srcDbcTblLnm = srcDbcTblLnm;
	}

	public void setSrcDbcColPnm(String srcDbcColPnm) {
		this.srcDbcColPnm = srcDbcColPnm;
	}

	public void setSrcDbcColLnm(String srcDbcColLnm) {
		this.srcDbcColLnm = srcDbcColLnm;
	}

	public void setSrcDbcColSpnm(String srcDbcColSpnm) {
		this.srcDbcColSpnm = srcDbcColSpnm;
	}

	public void setSrcDbcColOrd(String srcDbcColOrd) {
		this.srcDbcColOrd = srcDbcColOrd;
	}

	public void setSrcDbcPkYn(String srcDbcPkYn) {
		this.srcDbcPkYn = srcDbcPkYn;
	}

	public void setSrcDbcDataType(String srcDbcDataType) {
		this.srcDbcDataType = srcDbcDataType;
	}

	public void setSrcDbcDataLen(String srcDbcDataLen) {
		this.srcDbcDataLen = srcDbcDataLen;
	}

	public void setSrcDbcDataScal(String srcDbcDataScal) {
		this.srcDbcDataScal = srcDbcDataScal;
	}

	public void setSrcDbcNonulYn(String srcDbcNonulYn) {
		this.srcDbcNonulYn = srcDbcNonulYn;
	}

	public void setSrcDbcDefltVal(String srcDbcDefltVal) {
		this.srcDbcDefltVal = srcDbcDefltVal;
	}

	public void setTgtDbcColCnt(String tgtDbcColCnt) {
		this.tgtDbcColCnt = tgtDbcColCnt;
	}

	public void setTgtDbcColPnm(String tgtDbcColPnm) {
		this.tgtDbcColPnm = tgtDbcColPnm;
	}

	public void setTgtDbcColLnm(String tgtDbcColLnm) {
		this.tgtDbcColLnm = tgtDbcColLnm;
	}

	public void setTgtDbcColOrd(String tgtDbcColOrd) {
		this.tgtDbcColOrd = tgtDbcColOrd;
	}

	public void setTgtDbcPkYn(String tgtDbcPkYn) {
		this.tgtDbcPkYn = tgtDbcPkYn;
	}

	public void setTgtDbcDataType(String tgtDbcDataType) {
		this.tgtDbcDataType = tgtDbcDataType;
	}

	public void setTgtDbcDataLen(String tgtDbcDataLen) {
		this.tgtDbcDataLen = tgtDbcDataLen;
	}

	public void setTgtDbcDataScal(String tgtDbcDataScal) {
		this.tgtDbcDataScal = tgtDbcDataScal;
	}

	public void setTgtDbcNonulYn(String tgtDbcNonulYn) {
		this.tgtDbcNonulYn = tgtDbcNonulYn;
	}

	public void setTgtDbcDefltVal(String tgtDbcDefltVal) {
		this.tgtDbcDefltVal = tgtDbcDefltVal;
	}

	public void setSrcDdlColCnt(String srcDdlColCnt) {
		this.srcDdlColCnt = srcDdlColCnt;
	}

	public void setTgtDdlColCnt(String tgtDdlColCnt) {
		this.tgtDdlColCnt = tgtDdlColCnt;
	}

	public void setSrcDdlTblId(String srcDdlTblId) {
		this.srcDdlTblId = srcDdlTblId;
	}

	public void setSrcDdlTblPnm(String srcDdlTblPnm) {
		this.srcDdlTblPnm = srcDdlTblPnm;
	}

	public void setSrcDdlTblLnm(String srcDdlTblLnm) {
		this.srcDdlTblLnm = srcDdlTblLnm;
	}

	public void setSrcDdlColPnm(String srcDdlColPnm) {
		this.srcDdlColPnm = srcDdlColPnm;
	}

	public void setSrcDdlColLnm(String srcDdlColLnm) {
		this.srcDdlColLnm = srcDdlColLnm;
	}

	public void setSrcDdlColSpnm(String srcDdlColSpnm) {
		this.srcDdlColSpnm = srcDdlColSpnm;
	}

	public void setSrcDdlColOrd(String srcDdlColOrd) {
		this.srcDdlColOrd = srcDdlColOrd;
	}

	public void setSrcDdlPkYn(String srcDdlPkYn) {
		this.srcDdlPkYn = srcDdlPkYn;
	}

	public void setSrcDdlDataType(String srcDdlDataType) {
		this.srcDdlDataType = srcDdlDataType;
	}

	public void setSrcDdlDataLen(String srcDdlDataLen) {
		this.srcDdlDataLen = srcDdlDataLen;
	}

	public void setSrcDdlDataScal(String srcDdlDataScal) {
		this.srcDdlDataScal = srcDdlDataScal;
	}

	public void setSrcDdlNonulYn(String srcDdlNonulYn) {
		this.srcDdlNonulYn = srcDdlNonulYn;
	}

	public void setSrcDdlDefltVal(String srcDdlDefltVal) {
		this.srcDdlDefltVal = srcDdlDefltVal;
	}

	public void setTgtDdlColPnm(String tgtDdlColPnm) {
		this.tgtDdlColPnm = tgtDdlColPnm;
	}

	public void setTgtDdlColLnm(String tgtDdlColLnm) {
		this.tgtDdlColLnm = tgtDdlColLnm;
	}

	public void setTgtDdlColOrd(String tgtDdlColOrd) {
		this.tgtDdlColOrd = tgtDdlColOrd;
	}

	public void setTgtDdlPkYn(String tgtDdlPkYn) {
		this.tgtDdlPkYn = tgtDdlPkYn;
	}

	public void setTgtDdlDataType(String tgtDdlDataType) {
		this.tgtDdlDataType = tgtDdlDataType;
	}

	public void setTgtDdlDataLen(String tgtDdlDataLen) {
		this.tgtDdlDataLen = tgtDdlDataLen;
	}

	public void setTgtDdlDataScal(String tgtDdlDataScal) {
		this.tgtDdlDataScal = tgtDdlDataScal;
	}

	public void setTgtDdlNonulYn(String tgtDdlNonulYn) {
		this.tgtDdlNonulYn = tgtDdlNonulYn;
	}

	public void setTgtDdlDefltVal(String tgtDdlDefltVal) {
		this.tgtDdlDefltVal = tgtDdlDefltVal;
	}

	public void setDdlIdxPnm(String ddlIdxPnm) {
		this.ddlIdxPnm = ddlIdxPnm;
	}

	public void setSrcDdlIdxColCnt(String srcDdlIdxColCnt) {
		this.srcDdlIdxColCnt = srcDdlIdxColCnt;
	}

	public void setTgtDdlIdxColCnt(String tgtDdlIdxColCnt) {
		this.tgtDdlIdxColCnt = tgtDdlIdxColCnt;
	}

	public void setSrcDdlIdxPnm(String srcDdlIdxPnm) {
		this.srcDdlIdxPnm = srcDdlIdxPnm;
	}

	public void setSrcUkIdxYn(String srcUkIdxYn) {
		this.srcUkIdxYn = srcUkIdxYn;
	}

	public void setSrcDdlIdxColPnm(String srcDdlIdxColPnm) {
		this.srcDdlIdxColPnm = srcDdlIdxColPnm;
	}

	public void setSrcDdlIdxColLnm(String srcDdlIdxColLnm) {
		this.srcDdlIdxColLnm = srcDdlIdxColLnm;
	}

	public void setSrcDdlIdxColOrd(String srcDdlIdxColOrd) {
		this.srcDdlIdxColOrd = srcDdlIdxColOrd;
	}

	public void setSrcSortTyp(String srcSortTyp) {
		this.srcSortTyp = srcSortTyp;
	}

	public void setTgtDdlTblPnm(String tgtDdlTblPnm) {
		this.tgtDdlTblPnm = tgtDdlTblPnm;
	}

	public void setTgtDdlTblLnm(String tgtDdlTblLnm) {
		this.tgtDdlTblLnm = tgtDdlTblLnm;
	}

	public void setTgtDdlIdxPnm(String tgtDdlIdxPnm) {
		this.tgtDdlIdxPnm = tgtDdlIdxPnm;
	}

	public void setTgtUkIdxYn(String tgtUkIdxYn) {
		this.tgtUkIdxYn = tgtUkIdxYn;
	}

	public void setTgtDdlIdxColPnm(String tgtDdlIdxColPnm) {
		this.tgtDdlIdxColPnm = tgtDdlIdxColPnm;
	}

	public void setTgtDdlIdxColLnm(String tgtDdlIdxColLnm) {
		this.tgtDdlIdxColLnm = tgtDdlIdxColLnm;
	}

	public void setTgtDdlIdxColOrd(String tgtDdlIdxColOrd) {
		this.tgtDdlIdxColOrd = tgtDdlIdxColOrd;
	}

	public void setTgtSortTyp(String tgtSortTyp) {
		this.tgtSortTyp = tgtSortTyp;
	}

	public void setRegDtm(String regDtm) {
		this.regDtm = regDtm;
	}

	public void setGapDcd(String gapDcd) {
		this.gapDcd = gapDcd;
	}

	public void setMngUserId(String mngUserId) {
		this.mngUserId = mngUserId;
	}
   
    
}
