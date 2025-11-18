/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeGapVO.java
 * 2. Package : kr.wise.meta.gap
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 9. 23. 오후 5:09:13
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 9. 23. :            : 신규 개발.
 */
package kr.wise.meta.gap.service;

import kr.wise.commons.cmm.CommonVo;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CodeGapVO.java
 * 3. Package  : kr.wise.meta.gap
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 9. 23. 오후 5:09:13
 * </PRE>
 */
public class CodeGapVO extends CommonVo{
	
	private String gapStatus;
	private String dbConnTrgId;
	private String dbConnTrgLnm;
	private String dbSchId;
	private String dbSchLnm;
	private String codePnm;
	private String codeLnm;
	private String codeId;
	private String codeTypCd;
	private String codeTbl;
	private String codeCol;
	private String codeDescn;
	private String codeEacnt;
	private String codeErrDescn;
	private String codeValErrDescn;
	private String dmnId;
	private String dmnPnm;
	private String dmnLnm;
	private String cdValIvwCd;
	private String lstEntyPnm;
	private String lstAttrPnm;
	private String dmnErrDescn;
	private String dmnValErrDescn;
	
	
	
	//도메인정보
	private String cdVal;
	private String cdValId;
	private String dispOrd;
//	private String objDescn;
	
	//접속대상코드값정보
	private String codeValPnm;
	private String codeValLnm;
	private String codeVal;
	private String codeValId;
	private String codeValOrd;
	private String codeValUseYn;
	private String codeValDescn;
	
	
	
	
	
	//검색조건
	private String cdValNm;
	
	
	//ibkc 신용정보 단순코드, 복잡코드 
	  private String dbConnTrgPnm  	;
      private String dbSchPnm  		  ;
      private String tgtDbConnTrgPnm  	;
      private String tgtDbSchPnm  		  ;
      private String lccd       		;
      private String mccd       		;
      private String sccd       		;
      private String lclsNm     		;
      private String mdcdNm     		;
      private String sclsNm     		;
      private String useYn      		;

      private String etc1       		;
      private String etcNm1     		;
      private String etc2       		;
      private String etcNm2     		;
      private String etc3       		;
      private String etcNm3     		;
      private String etc4       		;
      private String etcNm4     		;
      private String etc5       		;
      private String etcNm5     		;
      private String rmrkCntn   		;
      private String outlCntn1  		;
      private String outlCntn2  		;

      private String tgtLccd       	;
      private String tgtMccd       	;
      private String tgtSccd       	;
      private String tgtLclsNm     	;
      private String tgtMdcdNm     	;
      private String tgtSclsNm     	;
      private String tgtUseYn      	;
      private String tgtDispOrd    	;
      private String tgtEtc1       	;
      private String tgtEtcNm1     	;
      private String tgtEtc2       	;
      private String tgtEtcNm2     	;
      private String tgtEtc3       	;
      private String tgtEtcNm3     	;
      private String tgtEtc4       	;
      private String tgtEtcNm4     	;
      private String tgtEtc5       	;
      private String tgtEtcNm5     	;
      private String tgtRmrkCntn   	;
      private String tgtOutlCntn1  	;
      private String tgtOutlCntn2  	;
      private String tgtCdValId  		;
      
      private String uppCdVal    ;
      private String codeLevel   ;
      private String tgtCdVal    ;
      private String tgtUppCdVal ;
      private String tgtCdValNm  ;
      private String tgtCodeLevel;
	
	public String getUppCdVal() {
		return uppCdVal;
	}
	public void setUppCdVal(String uppCdVal) {
		this.uppCdVal = uppCdVal;
	}
	public String getCodeLevel() {
		return codeLevel;
	}
	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}
	public String getTgtCdVal() {
		return tgtCdVal;
	}
	public void setTgtCdVal(String tgtCdVal) {
		this.tgtCdVal = tgtCdVal;
	}
	public String getTgtUppCdVal() {
		return tgtUppCdVal;
	}
	public void setTgtUppCdVal(String tgtUppCdVal) {
		this.tgtUppCdVal = tgtUppCdVal;
	}
	public String getTgtCdValNm() {
		return tgtCdValNm;
	}
	public void setTgtCdValNm(String tgtCdValNm) {
		this.tgtCdValNm = tgtCdValNm;
	}
	public String getTgtCodeLevel() {
		return tgtCodeLevel;
	}
	public void setTgtCodeLevel(String tgtCodeLevel) {
		this.tgtCodeLevel = tgtCodeLevel;
	}
	public String getTgtDbConnTrgPnm() {
		return tgtDbConnTrgPnm;
	}
	public void setTgtDbConnTrgPnm(String tgtDbConnTrgPnm) {
		this.tgtDbConnTrgPnm = tgtDbConnTrgPnm;
	}
	public String getTgtDbSchPnm() {
		return tgtDbSchPnm;
	}
	public void setTgtDbSchPnm(String tgtDbSchPnm) {
		this.tgtDbSchPnm = tgtDbSchPnm;
	}
	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}
	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}
	public String getDbSchPnm() {
		return dbSchPnm;
	}
	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
	}
	public String getLccd() {
		return lccd;
	}
	public void setLccd(String lccd) {
		this.lccd = lccd;
	}
	public String getMccd() {
		return mccd;
	}
	public void setMccd(String mccd) {
		this.mccd = mccd;
	}
	public String getSccd() {
		return sccd;
	}
	public void setSccd(String sccd) {
		this.sccd = sccd;
	}
	public String getLclsNm() {
		return lclsNm;
	}
	public void setLclsNm(String lclsNm) {
		this.lclsNm = lclsNm;
	}

	public String getSclsNm() {
		return sclsNm;
	}
	public void setSclsNm(String sclsNm) {
		this.sclsNm = sclsNm;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getEtc1() {
		return etc1;
	}
	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}
	public String getEtcNm1() {
		return etcNm1;
	}
	public void setEtcNm1(String etcNm1) {
		this.etcNm1 = etcNm1;
	}
	public String getEtc2() {
		return etc2;
	}
	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}
	public String getEtcNm2() {
		return etcNm2;
	}
	public void setEtcNm2(String etcNm2) {
		this.etcNm2 = etcNm2;
	}
	public String getEtc3() {
		return etc3;
	}
	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}
	public String getEtcNm3() {
		return etcNm3;
	}
	public void setEtcNm3(String etcNm3) {
		this.etcNm3 = etcNm3;
	}
	public String getEtc4() {
		return etc4;
	}
	public void setEtc4(String etc4) {
		this.etc4 = etc4;
	}
	public String getEtcNm4() {
		return etcNm4;
	}
	public void setEtcNm4(String etcNm4) {
		this.etcNm4 = etcNm4;
	}
	public String getEtc5() {
		return etc5;
	}
	public void setEtc5(String etc5) {
		this.etc5 = etc5;
	}
	public String getEtcNm5() {
		return etcNm5;
	}
	public void setEtcNm5(String etcNm5) {
		this.etcNm5 = etcNm5;
	}
	public String getRmrkCntn() {
		return rmrkCntn;
	}
	public void setRmrkCntn(String rmrkCntn) {
		this.rmrkCntn = rmrkCntn;
	}
	public String getOutlCntn1() {
		return outlCntn1;
	}
	public void setOutlCntn1(String outlCntn1) {
		this.outlCntn1 = outlCntn1;
	}
	public String getOutlCntn2() {
		return outlCntn2;
	}
	public void setOutlCntn2(String outlCntn2) {
		this.outlCntn2 = outlCntn2;
	}
	public String getTgtLccd() {
		return tgtLccd;
	}
	public void setTgtLccd(String tgtLccd) {
		this.tgtLccd = tgtLccd;
	}
	public String getTgtMccd() {
		return tgtMccd;
	}
	public void setTgtMccd(String tgtMccd) {
		this.tgtMccd = tgtMccd;
	}
	public String getTgtSccd() {
		return tgtSccd;
	}
	public void setTgtSccd(String tgtSccd) {
		this.tgtSccd = tgtSccd;
	}
	public String getTgtLclsNm() {
		return tgtLclsNm;
	}
	public void setTgtLclsNm(String tgtLclsNm) {
		this.tgtLclsNm = tgtLclsNm;
	}

	public String getMdcdNm() {
		return mdcdNm;
	}
	public void setMdcdNm(String mdcdNm) {
		this.mdcdNm = mdcdNm;
	}
	public String getTgtMdcdNm() {
		return tgtMdcdNm;
	}
	public void setTgtMdcdNm(String tgtMdcdNm) {
		this.tgtMdcdNm = tgtMdcdNm;
	}
	public String getTgtSclsNm() {
		return tgtSclsNm;
	}
	public void setTgtSclsNm(String tgtSclsNm) {
		this.tgtSclsNm = tgtSclsNm;
	}
	public String getTgtUseYn() {
		return tgtUseYn;
	}
	public void setTgtUseYn(String tgtUseYn) {
		this.tgtUseYn = tgtUseYn;
	}
	public String getTgtDispOrd() {
		return tgtDispOrd;
	}
	public void setTgtDispOrd(String tgtDispOrd) {
		this.tgtDispOrd = tgtDispOrd;
	}
	public String getTgtEtc1() {
		return tgtEtc1;
	}
	public void setTgtEtc1(String tgtEtc1) {
		this.tgtEtc1 = tgtEtc1;
	}
	public String getTgtEtcNm1() {
		return tgtEtcNm1;
	}
	public void setTgtEtcNm1(String tgtEtcNm1) {
		this.tgtEtcNm1 = tgtEtcNm1;
	}
	public String getTgtEtc2() {
		return tgtEtc2;
	}
	public void setTgtEtc2(String tgtEtc2) {
		this.tgtEtc2 = tgtEtc2;
	}
	public String getTgtEtcNm2() {
		return tgtEtcNm2;
	}
	public void setTgtEtcNm2(String tgtEtcNm2) {
		this.tgtEtcNm2 = tgtEtcNm2;
	}
	public String getTgtEtc3() {
		return tgtEtc3;
	}
	public void setTgtEtc3(String tgtEtc3) {
		this.tgtEtc3 = tgtEtc3;
	}
	public String getTgtEtcNm3() {
		return tgtEtcNm3;
	}
	public void setTgtEtcNm3(String tgtEtcNm3) {
		this.tgtEtcNm3 = tgtEtcNm3;
	}
	public String getTgtEtc4() {
		return tgtEtc4;
	}
	public void setTgtEtc4(String tgtEtc4) {
		this.tgtEtc4 = tgtEtc4;
	}
	public String getTgtEtcNm4() {
		return tgtEtcNm4;
	}
	public void setTgtEtcNm4(String tgtEtcNm4) {
		this.tgtEtcNm4 = tgtEtcNm4;
	}
	public String getTgtEtc5() {
		return tgtEtc5;
	}
	public void setTgtEtc5(String tgtEtc5) {
		this.tgtEtc5 = tgtEtc5;
	}
	public String getTgtEtcNm5() {
		return tgtEtcNm5;
	}
	public void setTgtEtcNm5(String tgtEtcNm5) {
		this.tgtEtcNm5 = tgtEtcNm5;
	}
	public String getTgtRmrkCntn() {
		return tgtRmrkCntn;
	}
	public void setTgtRmrkCntn(String tgtRmrkCntn) {
		this.tgtRmrkCntn = tgtRmrkCntn;
	}
	public String getTgtOutlCntn1() {
		return tgtOutlCntn1;
	}
	public void setTgtOutlCntn1(String tgtOutlCntn1) {
		this.tgtOutlCntn1 = tgtOutlCntn1;
	}
	public String getTgtOutlCntn2() {
		return tgtOutlCntn2;
	}
	public void setTgtOutlCntn2(String tgtOutlCntn2) {
		this.tgtOutlCntn2 = tgtOutlCntn2;
	}
	public String getTgtCdValId() {
		return tgtCdValId;
	}
	public void setTgtCdValId(String tgtCdValId) {
		this.tgtCdValId = tgtCdValId;
	}
	public String getCdValNm() {
		return cdValNm;
	}
	public void setCdValNm(String cdValNm) {
		this.cdValNm = cdValNm;
	}
	public String getGapStatus() {
		return gapStatus;
	}
	public void setGapStatus(String gapStatus) {
		this.gapStatus = gapStatus;
	}
	public String getDbConnTrgId() {
		return dbConnTrgId;
	}
	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}
	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}
	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
	}
	public String getDbSchId() {
		return dbSchId;
	}
	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}
	public String getDbSchLnm() {
		return dbSchLnm;
	}
	public void setDbSchLnm(String dbSchLnm) {
		this.dbSchLnm = dbSchLnm;
	}
	public String getCodePnm() {
		return codePnm;
	}
	public void setCodePnm(String codePnm) {
		this.codePnm = codePnm;
	}
	public String getCodeLnm() {
		return codeLnm;
	}
	public void setCodeLnm(String codeLnm) {
		this.codeLnm = codeLnm;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getCodeTypCd() {
		return codeTypCd;
	}
	public void setCodeTypCd(String codeTypCd) {
		this.codeTypCd = codeTypCd;
	}
	public String getCodeTbl() {
		return codeTbl;
	}
	public void setCodeTbl(String codeTbl) {
		this.codeTbl = codeTbl;
	}
	public String getCodeCol() {
		return codeCol;
	}
	public void setCodeCol(String codeCol) {
		this.codeCol = codeCol;
	}
	public String getCodeDescn() {
		return codeDescn;
	}
	public void setCodeDescn(String codeDescn) {
		this.codeDescn = codeDescn;
	}
	public String getCodeEacnt() {
		return codeEacnt;
	}
	public void setCodeEacnt(String codeEacnt) {
		this.codeEacnt = codeEacnt;
	}
	public String getCodeErrDescn() {
		return codeErrDescn;
	}
	public void setCodeErrDescn(String codeErrDescn) {
		this.codeErrDescn = codeErrDescn;
	}
	public String getDmnId() {
		return dmnId;
	}
	public void setDmnId(String dmnId) {
		this.dmnId = dmnId;
	}
	public String getDmnPnm() {
		return dmnPnm;
	}
	public void setDmnPnm(String dmnPnm) {
		this.dmnPnm = dmnPnm;
	}
	public String getDmnLnm() {
		return dmnLnm;
	}
	public void setDmnLnm(String dmnLnm) {
		this.dmnLnm = dmnLnm;
	}
	public String getCdValIvwCd() {
		return cdValIvwCd;
	}
	public void setCdValIvwCd(String cdValIvwCd) {
		this.cdValIvwCd = cdValIvwCd;
	}
	public String getLstEntyPnm() {
		return lstEntyPnm;
	}
	public void setLstEntyPnm(String lstEntyPnm) {
		this.lstEntyPnm = lstEntyPnm;
	}
	public String getLstAttrPnm() {
		return lstAttrPnm;
	}
	public void setLstAttrPnm(String lstAttrPnm) {
		this.lstAttrPnm = lstAttrPnm;
	}
	public String getDmnErrDescn() {
		return dmnErrDescn;
	}
	public void setDmnErrDescn(String dmnErrDescn) {
		this.dmnErrDescn = dmnErrDescn;
	}
	public String getCodeValErrDescn() {
		return codeValErrDescn;
	}
	public void setCodeValErrDescn(String codeValErrDescn) {
		this.codeValErrDescn = codeValErrDescn;
	}
	public String getDmnValErrDescn() {
		return dmnValErrDescn;
	}
	public void setDmnValErrDescn(String dmnValErrDescn) {
		this.dmnValErrDescn = dmnValErrDescn;
	}
	public String getCdVal() {
		return cdVal;
	}
	public void setCdVal(String cdVal) {
		this.cdVal = cdVal;
	}
	public String getCdValId() {
		return cdValId;
	}
	public void setCdValId(String cdValId) {
		this.cdValId = cdValId;
	}
	public String getDispOrd() {
		return dispOrd;
	}
	public void setDispOrd(String dispOrd) {
		this.dispOrd = dispOrd;
	}
	public String getCodeValPnm() {
		return codeValPnm;
	}
	public void setCodeValPnm(String codeValPnm) {
		this.codeValPnm = codeValPnm;
	}
	public String getCodeValLnm() {
		return codeValLnm;
	}
	public void setCodeValLnm(String codeValLnm) {
		this.codeValLnm = codeValLnm;
	}
	public String getCodeVal() {
		return codeVal;
	}
	public void setCodeVal(String codeVal) {
		this.codeVal = codeVal;
	}
	public String getCodeValId() {
		return codeValId;
	}
	public void setCodeValId(String codeValId) {
		this.codeValId = codeValId;
	}
	public String getCodeValOrd() {
		return codeValOrd;
	}
	public void setCodeValOrd(String codeValOrd) {
		this.codeValOrd = codeValOrd;
	}
	public String getCodeValUseYn() {
		return codeValUseYn;
	}
	public void setCodeValUseYn(String codeValUseYn) {
		this.codeValUseYn = codeValUseYn;
	}
	public String getCodeValDescn() {
		return codeValDescn;
	}
	public void setCodeValDescn(String codeValDescn) {
		this.codeValDescn = codeValDescn;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CodeGapVO [gapStatus=").append(gapStatus)
				.append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", dbConnTrgLnm=").append(dbConnTrgLnm)
				.append(", dbSchId=").append(dbSchId).append(", dbSchLnm=")
				.append(dbSchLnm).append(", codePnm=").append(codePnm)
				.append(", codeLnm=").append(codeLnm).append(", codeId=")
				.append(codeId).append(", codeTypCd=").append(codeTypCd)
				.append(", codeTbl=").append(codeTbl).append(", codeCol=")
				.append(codeCol).append(", codeDescn=").append(codeDescn)
				.append(", codeEacnt=").append(codeEacnt)
				.append(", codeErrDescn=").append(codeErrDescn)
				.append(", codeValErrDescn=").append(codeValErrDescn)
				.append(", dmnId=").append(dmnId).append(", dmnPnm=")
				.append(dmnPnm).append(", dmnLnm=").append(dmnLnm)
				.append(", cdValIvwCd=").append(cdValIvwCd)
				.append(", lstEntyPnm=").append(lstEntyPnm)
				.append(", lstAttrPnm=").append(lstAttrPnm)
				.append(", dmnErrDescn=").append(dmnErrDescn)
				.append(", dmnValErrDescn=").append(dmnValErrDescn)
				.append(", cdVal=").append(cdVal).append(", cdValId=")
				.append(cdValId).append(", dispOrd=").append(dispOrd)
				.append(", codeValPnm=").append(codeValPnm)
				.append(", codeValLnm=").append(codeValLnm)
				.append(", codeVal=").append(codeVal).append(", codeValId=")
				.append(codeValId).append(", codeValOrd=").append(codeValOrd)
				.append(", codeValUseYn=").append(codeValUseYn)
				.append(", codeValDescn=").append(codeValDescn)
				.append(", cdValNm=").append(cdValNm).append("]");
		return builder.toString();
	}
	
	
	
}
