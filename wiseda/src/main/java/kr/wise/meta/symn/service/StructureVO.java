package kr.wise.meta.symn.service;

import kr.wise.meta.stnd.service.WamDmn;

public class StructureVO extends WamDmn{
	
	//타겟 도메인id
	private String tgtDmnId;
	
	//타겟 도메인명
	private String tgtDmnLnm;
	//타겟 도메인영문명
	private String tgtDmnPnm;
	//분류코드
	private String tgtDmnDscd;
	
	//단어개수(소스)
	private String srcTrmCnt;
	//단어 개수(타겟)
	private String tgtTrmCnt;
	//동일단어수
	private String samTrmCnt;
	//동일비율(소스)
	private String srcSameRt;
	//동일비율(타겟)
	private String tgtSameRt;
	
	private String srcSameOrder;
	
	private String tgtSameOrder;
	
	
	
	//기준비율
	private String crlRt;
	//단어한글명(소스)
	private String srcTrmKnm;
	//단어영문명(소스)
	private String srcTrmEnm;
	//단어한글명(타겟)
	private String tgtTrmKnm;
	//단어영문명(타겟)
	private String tgtTrmEnm;
	
	//소스항목id
	private String srcItemId;
	//항목 한글명
	private String srcItemKnm;
	//항목 영문명
	private String srcItemEnm;
	//도메인명
	private String domainKnm;
	//도메인그룹명
	private String dmngrpNm;
	//인포타입
	private String infotypeNm;
	//타겟항목id
	private String tgtItemId;
	//타겟항목 한글명
	private String tgtItemKnm;
	//타겟항목 영문명
	private String tgtItemEnm;
	
	
	//모델 구조 검사
	//모델 소스id(물리모델테이블id)
	private String srcObjId;
	//모델명(소스)
	private String srcDatemodelNm;
	//상위주제영역명(소스)
	private String srcPrimarySubjNm;
	//주제영역(소스)
	private String srcSubjNm;
	//테이블한글명(소스)
	private String srcTblKnm;
	//테이블영문명(소스)
	private String srcTblEnm;
	//컬럼개수(소스)
	private String srcColCnt;
	//소스id(타겟)
	private String tgtObjId;
	//모델명(타겟)
	private String tgtDatemodelNm;
	//상위주제영역명(타겟)
	private String tgtPrimarySubjNm;
	//주제영역(타겟)
	private String tgtSubjNm;
	//테이블한글명(타겟)
	private String tgtTblKnm;
	//테이블영문명(타겟)
	private String tgtTblEnm ;
	//컬럼개수(타겟)
	private String tgtColCnt;
	//동일컬럼수
	private String sameColCnt;
	
	//컬럼명
	private String colNm;
	
	//컬럼 한글명(소스)
	private String srcColKnm;
	//컬럼 영문명(소스)
	private String srcColEnm;
	//컬럼 순서(소스)
	private String srcPosition;
	//pk여부(소스)
	private String srcPkYn;
	//null여부(소스)
	private String srcNullYn;
	//데이터타입(소스)
	private String srcDataType;
	//컬럼 한글명(타겟)
	private String tgtColKnm;
	//컬럼 영문명(타겟)
	private String tgtColEnm;
	//컬럼 순서(타겟)
	private String tgtPosition;
	//pk여부(타겟)
	private String tgtPkYn;
	//null여부(타겟)
	private String tgtNullYn;
	//데이터타입(타겟)
	private String tgtDataType;
	//테이블id(소스)
	private String srcTblId;
	//컬럼id(소스)
	private String srcColId;
	//테이블id(타겟)
	private String tgtTblId;
	//컬럼id(타겟)
	private String tgtColId;
	//fullPath(소스) 검색조건 
	private String fullPath;
	
	//소스 코드개수
	private String srcCdCnt;
	//타겟 코드개수
	private String tgtCdCnt;
	// 동일 코드개수
	private String samCdCnt;
	
	
	//소스 코드명
	private String srcCdValNm;
	//소스 코드값
	private String srcCdVal;
	//소스 코드id
	private String srcCdValId;
	//타겟 코드명
	private String tgtCdValNm;
	//타겟 코드값
	private String tgtCdVal;
	//타겟 코드id
	private String tgtCdValId;
	
	private String stndAsrt;
	
	private String srcDmnId;
	
	
	public String getStndAsrt() {
		return stndAsrt;
	}

	public void setStndAsrt(String stndAsrt) {
		this.stndAsrt = stndAsrt;
	}

	public String getSrcCdValNm() {
		return srcCdValNm;
	}

	public void setSrcCdValNm(String srcCdValNm) {
		this.srcCdValNm = srcCdValNm;
	}

	public String getSrcCdVal() {
		return srcCdVal;
	}

	public void setSrcCdVal(String srcCdVal) {
		this.srcCdVal = srcCdVal;
	}

	public String getSrcCdValId() {
		return srcCdValId;
	}

	public void setSrcCdValId(String srcCdValId) {
		this.srcCdValId = srcCdValId;
	}

	public String getTgtCdValNm() {
		return tgtCdValNm;
	}

	public void setTgtCdValNm(String tgtCdValNm) {
		this.tgtCdValNm = tgtCdValNm;
	}

	public String getTgtCdVal() {
		return tgtCdVal;
	}

	public void setTgtCdVal(String tgtCdVal) {
		this.tgtCdVal = tgtCdVal;
	}

	public String getTgtCdValId() {
		return tgtCdValId;
	}

	public void setTgtCdValId(String tgtCdValId) {
		this.tgtCdValId = tgtCdValId;
	}

	public String getSrcCdCnt() {
		return srcCdCnt;
	}

	public void setSrcCdCnt(String srcCdCnt) {
		this.srcCdCnt = srcCdCnt;
	}

	public String getTgtCdCnt() {
		return tgtCdCnt;
	}

	public void setTgtCdCnt(String tgtCdCnt) {
		this.tgtCdCnt = tgtCdCnt;
	}

	public String getSamCdCnt() {
		return samCdCnt;
	}

	public void setSamCdCnt(String samCdCnt) {
		this.samCdCnt = samCdCnt;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getSrcColKnm() {
		return srcColKnm;
	}

	public void setSrcColKnm(String srcColKnm) {
		this.srcColKnm = srcColKnm;
	}

	public String getSrcColEnm() {
		return srcColEnm;
	}

	public void setSrcColEnm(String srcColEnm) {
		this.srcColEnm = srcColEnm;
	}

	public String getSrcPosition() {
		return srcPosition;
	}

	public void setSrcPosition(String srcPosition) {
		this.srcPosition = srcPosition;
	}

	public String getSrcPkYn() {
		return srcPkYn;
	}

	public void setSrcPkYn(String srcPkYn) {
		this.srcPkYn = srcPkYn;
	}

	public String getSrcNullYn() {
		return srcNullYn;
	}

	public void setSrcNullYn(String srcNullYn) {
		this.srcNullYn = srcNullYn;
	}

	public String getSrcDataType() {
		return srcDataType;
	}

	public void setSrcDataType(String srcDataType) {
		this.srcDataType = srcDataType;
	}

	public String getTgtColKnm() {
		return tgtColKnm;
	}

	public void setTgtColKnm(String tgtColKnm) {
		this.tgtColKnm = tgtColKnm;
	}

	public String getTgtColEnm() {
		return tgtColEnm;
	}

	public void setTgtColEnm(String tgtColEnm) {
		this.tgtColEnm = tgtColEnm;
	}

	public String getTgtPosition() {
		return tgtPosition;
	}

	public void setTgtPosition(String tgtPosition) {
		this.tgtPosition = tgtPosition;
	}

	public String getTgtPkYn() {
		return tgtPkYn;
	}

	public void setTgtPkYn(String tgtPkYn) {
		this.tgtPkYn = tgtPkYn;
	}

	public String getTgtNullYn() {
		return tgtNullYn;
	}

	public void setTgtNullYn(String tgtNullYn) {
		this.tgtNullYn = tgtNullYn;
	}

	public String getTgtDataType() {
		return tgtDataType;
	}

	public void setTgtDataType(String tgtDataType) {
		this.tgtDataType = tgtDataType;
	}

	public String getSrcTblId() {
		return srcTblId;
	}

	public void setSrcTblId(String srcTblId) {
		this.srcTblId = srcTblId;
	}

	public String getSrcColId() {
		return srcColId;
	}

	public void setSrcColId(String srcColId) {
		this.srcColId = srcColId;
	}

	public String getTgtTblId() {
		return tgtTblId;
	}

	public void setTgtTblId(String tgtTblId) {
		this.tgtTblId = tgtTblId;
	}

	public String getTgtColId() {
		return tgtColId;
	}

	public void setTgtColId(String tgtColId) {
		this.tgtColId = tgtColId;
	}

	public String getColNm() {
		return colNm;
	}

	public void setColNm(String colNm) {
		this.colNm = colNm;
	}

	public String getSrcObjId() {
		return srcObjId;
	}

	public void setSrcObjId(String srcObjId) {
		this.srcObjId = srcObjId;
	}

	public String getSrcDatemodelNm() {
		return srcDatemodelNm;
	}

	public void setSrcDatemodelNm(String srcDatemodelNm) {
		this.srcDatemodelNm = srcDatemodelNm;
	}

	public String getSrcPrimarySubjNm() {
		return srcPrimarySubjNm;
	}

	public void setSrcPrimarySubjNm(String srcPrimarySubjNm) {
		this.srcPrimarySubjNm = srcPrimarySubjNm;
	}

	public String getSrcSubjNm() {
		return srcSubjNm;
	}

	public void setSrcSubjNm(String srcSubjNm) {
		this.srcSubjNm = srcSubjNm;
	}

	public String getSrcTblKnm() {
		return srcTblKnm;
	}

	public void setSrcTblKnm(String srcTblKnm) {
		this.srcTblKnm = srcTblKnm;
	}

	public String getSrcTblEnm() {
		return srcTblEnm;
	}

	public void setSrcTblEnm(String srcTblEnm) {
		this.srcTblEnm = srcTblEnm;
	}

	public String getSrcColCnt() {
		return srcColCnt;
	}

	public void setSrcColCnt(String srcColCnt) {
		this.srcColCnt = srcColCnt;
	}

	public String getTgtObjId() {
		return tgtObjId;
	}

	public void setTgtObjId(String tgtObjId) {
		this.tgtObjId = tgtObjId;
	}

	public String getTgtDatemodelNm() {
		return tgtDatemodelNm;
	}

	public void setTgtDatemodelNm(String tgtDatemodelNm) {
		this.tgtDatemodelNm = tgtDatemodelNm;
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

	public String getTgtTblKnm() {
		return tgtTblKnm;
	}

	public void setTgtTblKnm(String tgtTblKnm) {
		this.tgtTblKnm = tgtTblKnm;
	}

	public String getTgtTblEnm() {
		return tgtTblEnm;
	}

	public void setTgtTblEnm(String tgtTblEnm) {
		this.tgtTblEnm = tgtTblEnm;
	}

	public String getTgtColCnt() {
		return tgtColCnt;
	}

	public void setTgtColCnt(String tgtColCnt) {
		this.tgtColCnt = tgtColCnt;
	}

	public String getSameColCnt() {
		return sameColCnt;
	}

	public void setSameColCnt(String sameColCnt) {
		this.sameColCnt = sameColCnt;
	}

	public String getSrcItemId() {
		return srcItemId;
	}

	public void setSrcItemId(String srcItemId) {
		this.srcItemId = srcItemId;
	}

	public String getSrcItemKnm() {
		return srcItemKnm;
	}

	public void setSrcItemKnm(String srcItemKnm) {
		this.srcItemKnm = srcItemKnm;
	}

	public String getSrcItemEnm() {
		return srcItemEnm;
	}

	public void setSrcItemEnm(String srcItemEnm) {
		this.srcItemEnm = srcItemEnm;
	}

	public String getDomainKnm() {
		return domainKnm;
	}

	public void setDomainKnm(String domainKnm) {
		this.domainKnm = domainKnm;
	}

	public String getDmngrpNm() {
		return dmngrpNm;
	}

	public void setDmngrpNm(String dmngrpNm) {
		this.dmngrpNm = dmngrpNm;
	}

	public String getInfotypeNm() {
		return infotypeNm;
	}

	public void setInfotypeNm(String infotypeNm) {
		this.infotypeNm = infotypeNm;
	}

	public String getTgtItemId() {
		return tgtItemId;
	}

	public void setTgtItemId(String tgtItemId) {
		this.tgtItemId = tgtItemId;
	}

	public String getTgtItemKnm() {
		return tgtItemKnm;
	}

	public void setTgtItemKnm(String tgtItemKnm) {
		this.tgtItemKnm = tgtItemKnm;
	}

	public String getTgtItemEnm() {
		return tgtItemEnm;
	}

	public void setTgtItemEnm(String tgtItemEnm) {
		this.tgtItemEnm = tgtItemEnm;
	}

	public String getSrcTrmKnm() {
		return srcTrmKnm;
	}

	public void setSrcTrmKnm(String srcTrmKnm) {
		this.srcTrmKnm = srcTrmKnm;
	}

	public String getSrcTrmEnm() {
		return srcTrmEnm;
	}

	public void setSrcTrmEnm(String srcTrmEnm) {
		this.srcTrmEnm = srcTrmEnm;
	}

	public String getTgtTrmKnm() {
		return tgtTrmKnm;
	}

	public void setTgtTrmKnm(String tgtTrmKnm) {
		this.tgtTrmKnm = tgtTrmKnm;
	}

	public String getTgtTrmEnm() {
		return tgtTrmEnm;
	}

	public void setTgtTrmEnm(String tgtTrmEnm) {
		this.tgtTrmEnm = tgtTrmEnm;
	}

	public String getCrlRt() {
		return crlRt;
	}

	public void setCrlRt(String crlRt) {
		this.crlRt = crlRt;
	}

	public String getTgtDmnId() {
		return tgtDmnId;
	}

	public void setTgtDmnId(String tgtDmnId) {
		this.tgtDmnId = tgtDmnId;
	}

	public String getTgtDmnLnm() {
		return tgtDmnLnm;
	}

	public void setTgtDmnLnm(String tgtDmnLnm) {
		this.tgtDmnLnm = tgtDmnLnm;
	}

	public String getTgtDmnPnm() {
		return tgtDmnPnm;
	}

	public void setTgtDmnPnm(String tgtDmnPnm) {
		this.tgtDmnPnm = tgtDmnPnm;
	}

	public String getTgtDmnDscd() {
		return tgtDmnDscd;
	}

	public void setTgtDmnDscd(String tgtDmnDscd) {
		this.tgtDmnDscd = tgtDmnDscd;
	}

	public String getSrcTrmCnt() {
		return srcTrmCnt;
	}

	public void setSrcTrmCnt(String srcTrmCnt) {
		this.srcTrmCnt = srcTrmCnt;
	}

	public String getTgtTrmCnt() {
		return tgtTrmCnt;
	}

	public void setTgtTrmCnt(String tgtTrmCnt) {
		this.tgtTrmCnt = tgtTrmCnt;
	}

	public String getSamTrmCnt() {
		return samTrmCnt;
	}

	public void setSamTrmCnt(String samTrmCnt) {
		this.samTrmCnt = samTrmCnt;
	}

	public String getSrcSameRt() {
		return srcSameRt;
	}

	public void setSrcSameRt(String srcSameRt) {
		this.srcSameRt = srcSameRt;
	}

	public String getTgtSameRt() {
		return tgtSameRt;
	}

	public void setTgtSameRt(String tgtSameRt) {
		this.tgtSameRt = tgtSameRt;
	}

	public String getSrcSameOrder() {
		return srcSameOrder;
	}

	public void setSrcSameOrder(String srcSameOrder) {
		this.srcSameOrder = srcSameOrder;
	}

	public String getTgtSameOrder() {
		return tgtSameOrder;
	}

	public void setTgtSameOrder(String tgtSameOrder) {
		this.tgtSameOrder = tgtSameOrder;
	}
	
	public String getSrcDmnId() {
		return srcDmnId;
	}

	public void setSrcDmnId(String srcDmnId) {
		this.srcDmnId = srcDmnId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StructureVO [tgtDmnId=").append(tgtDmnId)
				.append(", tgtDmnLnm=").append(tgtDmnLnm)
				.append(", tgtDmnPnm=").append(tgtDmnPnm)
				.append(", tgtDmnDscd=").append(tgtDmnDscd)
				.append(", srcTrmCnt=").append(srcTrmCnt)
				.append(", tgtTrmCnt=").append(tgtTrmCnt)
				.append(", samTrmCnt=").append(samTrmCnt)
				.append(", srcSameRt=").append(srcSameRt)
				.append(", tgtSameRt=").append(tgtSameRt)
				.append(", srcSameOrder=").append(srcSameOrder)
				.append(", tgtSameOrder=").append(tgtSameOrder)
				.append(", crlRt=").append(crlRt).append(", srcTrmKnm=")
				.append(srcTrmKnm).append(", srcTrmEnm=").append(srcTrmEnm)
				.append(", tgtTrmKnm=").append(tgtTrmKnm)
				.append(", tgtTrmEnm=").append(tgtTrmEnm)
				.append(", srcItemId=").append(srcItemId)
				.append(", srcItemKnm=").append(srcItemKnm)
				.append(", srcItemEnm=").append(srcItemEnm)
				.append(", domainKnm=").append(domainKnm).append(", dmngrpNm=")
				.append(dmngrpNm).append(", infotypeNm=").append(infotypeNm)
				.append(", tgtItemId=").append(tgtItemId)
				.append(", tgtItemKnm=").append(tgtItemKnm)
				.append(", tgtItemEnm=").append(tgtItemEnm)
				.append(", srcObjId=").append(srcObjId)
				.append(", srcDatemodelNm=").append(srcDatemodelNm)
				.append(", srcPrimarySubjNm=").append(srcPrimarySubjNm)
				.append(", srcSubjNm=").append(srcSubjNm)
				.append(", srcTblKnm=").append(srcTblKnm)
				.append(", srcTblEnm=").append(srcTblEnm)
				.append(", srcColCnt=").append(srcColCnt).append(", tgtObjId=")
				.append(tgtObjId).append(", tgtDatemodelNm=")
				.append(tgtDatemodelNm).append(", tgtPrimarySubjNm=")
				.append(tgtPrimarySubjNm).append(", tgtSubjNm=")
				.append(tgtSubjNm).append(", tgtTblKnm=").append(tgtTblKnm)
				.append(", tgtTblEnm=").append(tgtTblEnm)
				.append(", tgtColCnt=").append(tgtColCnt)
				.append(", sameColCnt=").append(sameColCnt).append(", colNm=")
				.append(colNm).append(", srcColKnm=").append(srcColKnm)
				.append(", srcColEnm=").append(srcColEnm)
				.append(", srcPosition=").append(srcPosition)
				.append(", srcPkYn=").append(srcPkYn).append(", srcNullYn=")
				.append(srcNullYn).append(", srcDataType=").append(srcDataType)
				.append(", tgtColKnm=").append(tgtColKnm)
				.append(", tgtColEnm=").append(tgtColEnm)
				.append(", tgtPosition=").append(tgtPosition)
				.append(", tgtPkYn=").append(tgtPkYn).append(", tgtNullYn=")
				.append(tgtNullYn).append(", tgtDataType=").append(tgtDataType)
				.append(", srcTblId=").append(srcTblId).append(", srcColId=")
				.append(srcColId).append(", tgtTblId=").append(tgtTblId)
				.append(", tgtColId=").append(tgtColId).append(", fullPath=")
				.append(fullPath).append(", srcCdCnt=").append(srcCdCnt)
				.append(", tgtCdCnt=").append(tgtCdCnt).append(", samCdCnt=")
				.append(samCdCnt).append(", srcCdValNm=").append(srcCdValNm)
				.append(", srcCdVal=").append(srcCdVal).append(", srcCdValId=")
				.append(srcCdValId).append(", tgtCdValNm=").append(tgtCdValNm)
				.append(", tgtCdVal=").append(tgtCdVal).append(", tgtCdValId=")
				.append(tgtCdValId).append(", srcDmnId=").append(srcDmnId).append("]");
		return builder.toString();
	}
	
}
