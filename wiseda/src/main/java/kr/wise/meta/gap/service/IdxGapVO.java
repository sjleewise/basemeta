/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : IdxGapVO.java
 * 2. Package : kr.wise.meta.gap.service
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 9. 24. 오후 4:33:24
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 9. 24. :            : 신규 개발.
 */
package kr.wise.meta.gap.service;

import kr.wise.commons.cmm.CommonVo;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : IdxGapVO.java
 * 3. Package  : kr.wise.meta.gap.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 9. 24. 오후 4:33:24
 * </PRE>
 */
public class IdxGapVO extends CommonVo{
	
	private String gapStatus;
	private String gapConts;
	private String dbConnTrgId;
	private String dbConnTrgLnm;
	private String dbSchId;
	private String dbSchLnm;
	private String dbcTblNm;
	private String dbcIdxNm;
	private String dbcTblSpacNm;
	private String pkYn;
	private String uqYn;
	private String colEacnt;
	private String descn;
	private String ddlIdxErrDescn;
	private String ddlIdxColErrDescn;
	private String ddlIdxId;
	private String ddlIdxPnm;
	private String ddlIdxLnm;
	private String idxTypCd;
	private String idxSpacId;
	private String idxSpacPnm;
	
	private String ddlIdxColId;
	private String ddlIdxColPnm;
	private String ddlIdxColOrd;
	private String sortTyp;
	private String dataType;
	private String dataLen;
	private String dataScal;
	private String objDescn;
	private String dbcIdxColNm;
	private String ord;
	private String sortType;
	private String dataPnt;
	
	private String ddlPkYn;
	private String ddlUqYn;
	
	private String ddlTblPnm;
	
	private String ddlIdxColCnt;
//
	private String ddlTsfIdxId;
	private String ddlTsfIdxPnm;
	private String ddlTsfIdxLnm;
	private String ddlTsfIdxColId;
	private String ddlTsfIdxColPnm;
	private String ddlTsfIdxColOrd;
	private String ddlTsfPkYn;
	private String ddlTsfUqYn;
	private String ddlTsfTblPnm;
	private String ddlTsfIdxColCnt;
	private String tsfSortTyp;
	
	private String dbTsfConnTrgId;
	private String dbTsfConnTrgLnm;
	private String dbTsfSchId;
	private String dbTsfSchLnm;
	
	private String dbcRealTblNm;
	private String dbcRealIdxNm;
	private String colRealEacnt;
	
	private String gapDcd;
	
	public String getGapDcd() {
		return gapDcd;
	}
	public void setGapDcd(String gapDcd) {
		this.gapDcd = gapDcd;
	}
	public String getDbcRealTblNm() {
		return dbcRealTblNm;
	}
	public void setDbcRealTblNm(String dbcRealTblNm) {
		this.dbcRealTblNm = dbcRealTblNm;
	}
	public String getDbcRealIdxNm() {
		return dbcRealIdxNm;
	}
	public void setDbcRealIdxNm(String dbcRealIdxNm) {
		this.dbcRealIdxNm = dbcRealIdxNm;
	}
	public String getColRealEacnt() {
		return colRealEacnt;
	}
	public void setColRealEacnt(String colRealEacnt) {
		this.colRealEacnt = colRealEacnt;
	}
	public String getDbTsfConnTrgId() {
		return dbTsfConnTrgId;
	}
	public void setDbTsfConnTrgId(String dbTsfConnTrgId) {
		this.dbTsfConnTrgId = dbTsfConnTrgId;
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
	public String getDbTsfSchLnm() {
		return dbTsfSchLnm;
	}
	public void setDbTsfSchLnm(String dbTsfSchLnm) {
		this.dbTsfSchLnm = dbTsfSchLnm;
	}
	public String getTsfSortTyp() {
		return tsfSortTyp;
	}
	public void setTsfSortTyp(String tsfSortTyp) {
		this.tsfSortTyp = tsfSortTyp;
	}
	public String getDdlTsfIdxId() {
		return ddlTsfIdxId;
	}
	public void setDdlTsfIdxId(String ddlTsfIdxId) {
		this.ddlTsfIdxId = ddlTsfIdxId;
	}
	public String getDdlTsfIdxPnm() {
		return ddlTsfIdxPnm;
	}
	public void setDdlTsfIdxPnm(String ddlTsfIdxPnm) {
		this.ddlTsfIdxPnm = ddlTsfIdxPnm;
	}
	public String getDdlTsfIdxLnm() {
		return ddlTsfIdxLnm;
	}
	public void setDdlTsfIdxLnm(String ddlTsfIdxLnm) {
		this.ddlTsfIdxLnm = ddlTsfIdxLnm;
	}
	public String getDdlTsfIdxColId() {
		return ddlTsfIdxColId;
	}
	public void setDdlTsfIdxColId(String ddlTsfIdxColId) {
		this.ddlTsfIdxColId = ddlTsfIdxColId;
	}
	public String getDdlTsfIdxColPnm() {
		return ddlTsfIdxColPnm;
	}
	public void setDdlTsfIdxColPnm(String ddlTsfIdxColPnm) {
		this.ddlTsfIdxColPnm = ddlTsfIdxColPnm;
	}
	public String getDdlTsfIdxColOrd() {
		return ddlTsfIdxColOrd;
	}
	public void setDdlTsfIdxColOrd(String ddlTsfIdxColOrd) {
		this.ddlTsfIdxColOrd = ddlTsfIdxColOrd;
	}
	public String getDdlTsfPkYn() {
		return ddlTsfPkYn;
	}
	public void setDdlTsfPkYn(String ddlTsfPkYn) {
		this.ddlTsfPkYn = ddlTsfPkYn;
	}
	public String getDdlTsfUqYn() {
		return ddlTsfUqYn;
	}
	public void setDdlTsfUqYn(String ddlTsfUqYn) {
		this.ddlTsfUqYn = ddlTsfUqYn;
	}
	public String getDdlTsfTblPnm() {
		return ddlTsfTblPnm;
	}
	public void setDdlTsfTblPnm(String ddlTsfTblPnm) {
		this.ddlTsfTblPnm = ddlTsfTblPnm;
	}
	public String getDdlTsfIdxColCnt() {
		return ddlTsfIdxColCnt;
	}
	public void setDdlTsfIdxColCnt(String ddlTsfIdxColCnt) {
		this.ddlTsfIdxColCnt = ddlTsfIdxColCnt;
	}
	public String getDdlIdxColCnt() {
		return ddlIdxColCnt;
	}
	public void setDdlIdxColCnt(String ddlIdxColCnt) {
		this.ddlIdxColCnt = ddlIdxColCnt;
	}
	public String getDdlTblPnm() {
		return ddlTblPnm;
	}
	public void setDdlTblPnm(String ddlTblPnm) {
		this.ddlTblPnm = ddlTblPnm;
	}
	public String getDdlPkYn() {
		return ddlPkYn;
	}
	public void setDdlPkYn(String ddlPkYn) {
		this.ddlPkYn = ddlPkYn;
	}
	public String getDdlUqYn() {
		return ddlUqYn;
	}
	public void setDdlUqYn(String ddlUqYn) {
		this.ddlUqYn = ddlUqYn;
	}
	public String getGapConts() {
		return gapConts;
	}
	public void setGapConts(String gapConts) {
		this.gapConts = gapConts;
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
	public String getDbcTblNm() {
		return dbcTblNm;
	}
	public void setDbcTblNm(String dbcTblNm) {
		this.dbcTblNm = dbcTblNm;
	}
	public String getDbcIdxNm() {
		return dbcIdxNm;
	}
	public void setDbcIdxNm(String dbcIdxNm) {
		this.dbcIdxNm = dbcIdxNm;
	}
	public String getDbcTblSpacNm() {
		return dbcTblSpacNm;
	}
	public void setDbcTblSpacNm(String dbcTblSpacNm) {
		this.dbcTblSpacNm = dbcTblSpacNm;
	}
	public String getPkYn() {
		return pkYn;
	}
	public void setPkYn(String pkYn) {
		this.pkYn = pkYn;
	}
	public String getUqYn() {
		return uqYn;
	}
	public void setUqYn(String uqYn) {
		this.uqYn = uqYn;
	}
	public String getColEacnt() {
		return colEacnt;
	}
	public void setColEacnt(String colEacnt) {
		this.colEacnt = colEacnt;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public String getDdlIdxErrDescn() {
		return ddlIdxErrDescn;
	}
	public void setDdlIdxErrDescn(String ddlIdxErrDescn) {
		this.ddlIdxErrDescn = ddlIdxErrDescn;
	}
	public String getDdlIdxColErrDescn() {
		return ddlIdxColErrDescn;
	}
	public void setDdlIdxColErrDescn(String ddlIdxColErrDescn) {
		this.ddlIdxColErrDescn = ddlIdxColErrDescn;
	}
	public String getDdlIdxId() {
		return ddlIdxId;
	}
	public void setDdlIdxId(String ddlIdxId) {
		this.ddlIdxId = ddlIdxId;
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
	public String getIdxTypCd() {
		return idxTypCd;
	}
	public void setIdxTypCd(String idxTypCd) {
		this.idxTypCd = idxTypCd;
	}
	public String getIdxSpacId() {
		return idxSpacId;
	}
	public void setIdxSpacId(String idxSpacId) {
		this.idxSpacId = idxSpacId;
	}
	public String getIdxSpacPnm() {
		return idxSpacPnm;
	}
	public void setIdxSpacPnm(String idxSpacPnm) {
		this.idxSpacPnm = idxSpacPnm;
	}
	public String getDdlIdxColId() {
		return ddlIdxColId;
	}
	public void setDdlIdxColId(String ddlIdxColId) {
		this.ddlIdxColId = ddlIdxColId;
	}
	public String getDdlIdxColPnm() {
		return ddlIdxColPnm;
	}
	public void setDdlIdxColPnm(String ddlIdxColPnm) {
		this.ddlIdxColPnm = ddlIdxColPnm;
	}
	public String getDdlIdxColOrd() {
		return ddlIdxColOrd;
	}
	public void setDdlIdxColOrd(String ddlIdxColOrd) {
		this.ddlIdxColOrd = ddlIdxColOrd;
	}
	public String getSortTyp() {
		return sortTyp;
	}
	public void setSortTyp(String sortTyp) {
		this.sortTyp = sortTyp;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataLen() {
		return dataLen;
	}
	public void setDataLen(String dataLen) {
		this.dataLen = dataLen;
	}
	public String getDataScal() {
		return dataScal;
	}
	public void setDataScal(String dataScal) {
		this.dataScal = dataScal;
	}
	public String getObjDescn() {
		return objDescn;
	}
	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}
	public String getDbcIdxColNm() {
		return dbcIdxColNm;
	}
	public void setDbcIdxColNm(String dbcIdxColNm) {
		this.dbcIdxColNm = dbcIdxColNm;
	}
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getDataPnt() {
		return dataPnt;
	}
	public void setDataPnt(String dataPnt) {
		this.dataPnt = dataPnt;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IdxGapVO [gapStatus=").append(gapStatus)
				.append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", dbConnTrgLnm=").append(dbConnTrgLnm)
				.append(", dbSchId=").append(dbSchId).append(", dbSchLnm=")
				.append(dbSchLnm).append(", dbcTblNm=").append(dbcTblNm)
				.append(", dbcIdxNm=").append(dbcIdxNm)
				.append(", dbcTblSpacNm=").append(dbcTblSpacNm)
				.append(", pkYn=").append(pkYn).append(", uqYn=").append(uqYn)
				.append(", colEacnt=").append(colEacnt).append(", descn=")
				.append(descn).append(", ddlIdxErrDescn=")
				.append(ddlIdxErrDescn).append(", ddlIdxColErrDescn=")
				.append(ddlIdxColErrDescn).append(", ddlIdxId=")
				.append(ddlIdxId).append(", ddlIdxPnm=").append(ddlIdxPnm)
				.append(", ddlIdxLnm=").append(ddlIdxLnm).append(", idxTypCd=")
				.append(idxTypCd).append(", idxSpacId=").append(idxSpacId)
				.append(", idxSpacPnm=").append(idxSpacPnm)
				.append(", ddlIdxColId=").append(ddlIdxColId)
				.append(", ddlIdxColPnm=").append(ddlIdxColPnm)
				.append(", ddlIdxColOrd=").append(ddlIdxColOrd)
				.append(", sortTyp=").append(sortTyp).append(", dataType=")
				.append(dataType).append(", dataLen=").append(dataLen)
				.append(", dataScal=").append(dataScal).append(", objDescn=")
				.append(objDescn).append(", dbcIdxColNm=").append(dbcIdxColNm)
				.append(", ord=").append(ord).append(", sortType=")
				.append(sortType).append(", dataPnt=").append(dataPnt)
				.append("]");
		return builder.toString();
	}
	
	
	
}
