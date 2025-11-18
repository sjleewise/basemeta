package kr.wise.meta.mapping.service;


public class MapListVO extends WaqTblMap {

	  private String tgtDatamodelNm;
	  
	  private String tgtPrimarySubjNm;
	  
	  private String tgtSubjNm;
	  
	  
	  // 테이블 id
	  private String pdmTblId;
	  
	  private String fullPath;
	  
	  //컬럼매핑 정의서 조회
	  //컬럼매핑구분
	  private String colMapType;
	  
	  private String colOrd;
	  
	  private String tgtColPnm;
	  
	  private String tgtColLnm;
	  
	  private String tgtDataType;
	  
	  private String tgtPkYn;
	  
	  private String tgtNonulYn;
	  
	  private String tgtDmnLnm;
	  
	  private String srcTblPnm;
	  
	  private String srcColLnm;
	  
	  private String srcColPnm;
	  
	  private String srcDescn;
	  
	  private String srcDataType;
	  
	  private String defltVal;
	  
	  private String srcCdDmnCd;
	  
	  //매핑조건
	  private String mapCndNm;
	  
	  private String colRefDbPnm;
	  
	  private String colRefTblPnm;
	  
	  private String tgtColId;
	  
	  
	  //코드매핑
	  //코드전환타입
	  private String cdCnvsType;
	  //코드매핑타입
	  private String cdMapType;
	  //타겟코드값
	  private String tgtCdVal;
	  //타겟코드값명
	  private String tgtCdValNm;
	  //소스코드값
	  private String srcCdVal;
	  //소스코드값명
	  private String srcCdValNm;
	  //타겟도메인id
	  private String tgtDmnId;
	  //타겟도메인명
	  private String tgtDmnPnm;
	  //코드매핑ID
	  private String colMapId;
	  private String srcUppCdVal;
	  private String srcUppCdValNm;
	  
	  //컬럼매핑 GAP
	  private String gapStaus;
	  private String regC;
	  private String regU;
	  private String regD;
	  private String pdmTblPnm;
	  private String pdmTblLnm;
	  
	  private String pdmTblPnm1;
	  private String fullPath1;
	  
	  private String pdmDataType;
	  private String pdmColOrd;
	  private String pdmDmnLnm;
	  
	  
	public String getColMapId() {
		return colMapId;
	}

	public void setColMapId(String colMapId) {
		this.colMapId = colMapId;
	}

	public String getCdCnvsType() {
		return cdCnvsType;
	}

	public void setCdCnvsType(String cdCnvsType) {
		this.cdCnvsType = cdCnvsType;
	}

	public String getCdMapType() {
		return cdMapType;
	}

	public void setCdMapType(String cdMapType) {
		this.cdMapType = cdMapType;
	}

	public String getTgtCdVal() {
		return tgtCdVal;
	}

	public void setTgtCdVal(String tgtCdVal) {
		this.tgtCdVal = tgtCdVal;
	}

	public String getTgtCdValNm() {
		return tgtCdValNm;
	}

	public void setTgtCdValNm(String tgtCdValNm) {
		this.tgtCdValNm = tgtCdValNm;
	}

	public String getSrcCdVal() {
		return srcCdVal;
	}

	public void setSrcCdVal(String srcCdVal) {
		this.srcCdVal = srcCdVal;
	}

	public String getSrcCdValNm() {
		return srcCdValNm;
	}

	public void setSrcCdValNm(String srcCdValNm) {
		this.srcCdValNm = srcCdValNm;
	}

	public String getTgtDmnId() {
		return tgtDmnId;
	}

	public void setTgtDmnId(String tgtDmnId) {
		this.tgtDmnId = tgtDmnId;
	}

	public String getTgtDmnPnm() {
		return tgtDmnPnm;
	}

	public void setTgtDmnPnm(String tgtDmnPnm) {
		this.tgtDmnPnm = tgtDmnPnm;
	}

	public String getTgtColId() {
		return tgtColId;
	}

	public void setTgtColId(String tgtColId) {
		this.tgtColId = tgtColId;
	}

	public String getColMapType() {
		return colMapType;
	}

	public void setColMapType(String colMapType) {
		this.colMapType = colMapType;
	}

	public String getColOrd() {
		return colOrd;
	}

	public void setColOrd(String colOrd) {
		this.colOrd = colOrd;
	}

	public String getTgtColPnm() {
		return tgtColPnm;
	}

	public void setTgtColPnm(String tgtColPnm) {
		this.tgtColPnm = tgtColPnm;
	}

	public String getTgtColLnm() {
		return tgtColLnm;
	}

	public void setTgtColLnm(String tgtColLnm) {
		this.tgtColLnm = tgtColLnm;
	}

	public String getTgtDataType() {
		return tgtDataType;
	}

	public void setTgtDataType(String tgtDataType) {
		this.tgtDataType = tgtDataType;
	}

	public String getTgtPkYn() {
		return tgtPkYn;
	}

	public void setTgtPkYn(String tgtPkYn) {
		this.tgtPkYn = tgtPkYn;
	}

	public String getTgtNonulYn() {
		return tgtNonulYn;
	}

	public void setTgtNonulYn(String tgtNonulYn) {
		this.tgtNonulYn = tgtNonulYn;
	}

	public String getTgtDmnLnm() {
		return tgtDmnLnm;
	}

	public void setTgtDmnLnm(String tgtDmnLnm) {
		this.tgtDmnLnm = tgtDmnLnm;
	}

	public String getSrcColLnm() {
		return srcColLnm;
	}

	public void setSrcColLnm(String srcColLnm) {
		this.srcColLnm = srcColLnm;
	}

	public String getSrcDescn() {
		return srcDescn;
	}

	public void setSrcDescn(String srcDescn) {
		this.srcDescn = srcDescn;
	}

	public String getSrcDataType() {
		return srcDataType;
	}

	public void setSrcDataType(String srcDataType) {
		this.srcDataType = srcDataType;
	}

	public String getDefltVal() {
		return defltVal;
	}

	public void setDefltVal(String defltVal) {
		this.defltVal = defltVal;
	}

	public String getMapCndNm() {
		return mapCndNm;
	}

	public void setMapCndNm(String mapCndNm) {
		this.mapCndNm = mapCndNm;
	}

	public String getColRefDbPnm() {
		return colRefDbPnm;
	}

	public void setColRefDbPnm(String colRefDbPnm) {
		this.colRefDbPnm = colRefDbPnm;
	}

	public String getColRefTblPnm() {
		return colRefTblPnm;
	}

	public void setColRefTblPnm(String colRefTblPnm) {
		this.colRefTblPnm = colRefTblPnm;
	}

	public String getFullPath() {
	return fullPath;
	}
	
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	
		public String getPdmTblId() {
		return pdmTblId;
	}
	
	public void setPdmTblId(String pdmTblId) {
		this.pdmTblId = pdmTblId;
	}

	public String getTgtDatamodelNm() {
		return tgtDatamodelNm;
	}
	
	public void setTgtDatamodelNm(String tgtDatamodelNm) {
		this.tgtDatamodelNm = tgtDatamodelNm;
	}
	
	public String getTgtPrimarySubjNm() {
		return tgtPrimarySubjNm;
	}
	
	public void setTgtPrimarySubjNm(String tgtPrimarySubjNm) {
		this.tgtPrimarySubjNm = tgtPrimarySubjNm;
	}
	
	public String getTgtSubjNm() {
		return tgtSubjNm;
	}
	
	public void setTgtSubjNm(String tgtSubjNm) {
		this.tgtSubjNm = tgtSubjNm;
	}
	
	public String getSrcTblPnm() {
		return srcTblPnm;
	}

	public void setSrcTblPnm(String srcTblPnm) {
		this.srcTblPnm = srcTblPnm;
	}

	public String getSrcColPnm() {
		return srcColPnm;
	}

	public void setSrcColPnm(String srcColPnm) {
		this.srcColPnm = srcColPnm;
	}

	public String getSrcUppCdVal() {
		return srcUppCdVal;
	}

	public void setSrcUppCdVal(String srcUppCdVal) {
		this.srcUppCdVal = srcUppCdVal;
	}

	public String getSrcUppCdValNm() {
		return srcUppCdValNm;
	}

	public void setSrcUppCdValNm(String srcUppCdValNm) {
		this.srcUppCdValNm = srcUppCdValNm;
	}

	public String getSrcCdDmnCd() {
		return srcCdDmnCd;
	}

	public void setSrcCdDmnCd(String srcCdDmnCd) {
		this.srcCdDmnCd = srcCdDmnCd;
	}

	public String getGapStaus() {
		return gapStaus;
	}

	public void setGapStaus(String gapStaus) {
		this.gapStaus = gapStaus;
	}

	public String getRegC() {
		return regC;
	}

	public void setRegC(String regC) {
		this.regC = regC;
	}

	public String getRegU() {
		return regU;
	}

	public void setRegU(String regU) {
		this.regU = regU;
	}

	public String getRegD() {
		return regD;
	}

	public void setRegD(String regD) {
		this.regD = regD;
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

	public String getPdmTblPnm1() {
		return pdmTblPnm1;
	}

	public void setPdmTblPnm1(String pdmTblPnm1) {
		this.pdmTblPnm1 = pdmTblPnm1;
	}

	public String getFullPath1() {
		return fullPath1;
	}

	public void setFullPath1(String fullPath1) {
		this.fullPath1 = fullPath1;
	}

	public String getPdmDataType() {
		return pdmDataType;
	}

	public void setPdmDataType(String pdmDataType) {
		this.pdmDataType = pdmDataType;
	}

	public String getPdmColOrd() {
		return pdmColOrd;
	}

	public void setPdmColOrd(String pdmColOrd) {
		this.pdmColOrd = pdmColOrd;
	}

	public String getPdmDmnLnm() {
		return pdmDmnLnm;
	}

	public void setPdmDmnLnm(String pdmDmnLnm) {
		this.pdmDmnLnm = pdmDmnLnm;
	}

	@Override
	public String toString() {
		return "MapListVO [tgtDatamodelNm=" + tgtDatamodelNm
				+ ", tgtPrimarySubjNm=" + tgtPrimarySubjNm + ", tgtSubjNm="
				+ tgtSubjNm + ", pdmTblId=" + pdmTblId + ", fullPath="
				+ fullPath + ", colMapType=" + colMapType + ", colOrd="
				+ colOrd + ", tgtColPnm=" + tgtColPnm + ", tgtColLnm="
				+ tgtColLnm + ", tgtDataType=" + tgtDataType + ", tgtPkYn="
				+ tgtPkYn + ", tgtNonulYn=" + tgtNonulYn + ", tgtDmnLnm="
				+ tgtDmnLnm + ", srcTblPnm=" + srcTblPnm + ", srcColLnm="
				+ srcColLnm + ", srcColPnm=" + srcColPnm + ", srcDescn="
				+ srcDescn + ", srcDataType=" + srcDataType + ", defltVal="
				+ defltVal + ", srcCdDmnCd=" + srcCdDmnCd + ", mapCndNm="
				+ mapCndNm + ", colRefDbPnm=" + colRefDbPnm + ", colRefTblPnm="
				+ colRefTblPnm + ", tgtColId=" + tgtColId + ", cdCnvsType="
				+ cdCnvsType + ", cdMapType=" + cdMapType + ", tgtCdVal="
				+ tgtCdVal + ", tgtCdValNm=" + tgtCdValNm + ", srcCdVal="
				+ srcCdVal + ", srcCdValNm=" + srcCdValNm + ", tgtDmnId="
				+ tgtDmnId + ", tgtDmnPnm=" + tgtDmnPnm + ", colMapId="
				+ colMapId + ", srcUppCdVal=" + srcUppCdVal
				+ ", srcUppCdValNm=" + srcUppCdValNm + ", gapStaus=" + gapStaus
				+ ", regC=" + regC + ", regU=" + regU + ", regD=" + regD
				+ ", pdmTblPnm=" + pdmTblPnm + ", pdmTblLnm=" + pdmTblLnm
				+ ", pdmTblPnm1=" + pdmTblPnm1 + ", fullPath1=" + fullPath1
				+ ", pdmDataType=" + pdmDataType + ", pdmColOrd=" + pdmColOrd
				+ ", pdmDmnLnm=" + pdmDmnLnm + "]";
	}
}
