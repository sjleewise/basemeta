package kr.wise.meta.aocd.service;

import kr.wise.meta.stnd.service.WamStwd;

public class PusAocdVO extends WamStwd{

	//객체설명
	private String objDescn;
	//도메인 사용건수
	private String domnNumCnt;
	//항목 사용건수
	private String itemNumCnt;
	//테이블 사용건수
	private String entyNumCnt;
	//사용건수 총합계
	private String totlNumCnt;
	//표준단어명 논리명,물리명
	private String stndNm;
	//정렬순서
	private String orderby;
	//정렬방식
	private String asc;
	
	//도메인 한글명
	private String dmnLnm;
	//도메인 영문명
	private String dmnPnm;
	//도메인 id
	private String dmnId;
	//도메인 설명
	private String domnDesc;
	//표준단어 사용건수
	private String stwdNumCnt;
	//인포타입 사용건수
	private String infotypeNumCnt;
	
	//항목id
	private String sditmId;
	//항목 한글명
	private String sditmLnm;
	//항목 영문명
	private String sditmPnm;
	//항목 설명
	private String itemDesc;
	
	//시스템명
	private String sysNm;
	//모델명
	private String datamodelNm;
	//상위주제영역명
	private String primarySubjNm;
	//주제영역명
	private String subjNm;
	//주제영역id
	private String subjId;
	//물리테이블 한글명
	private String pdmTblLnm;
	//물리테이블 영문명
	private String pdmTblPnm;
	//물리테이블 id
	private String pdmTblId;
	//컬럼개수
	private String colTotCnt;
	//ddl 개수
	private String ddlCnt;
	//dbc 개수
	private String dbcCnt;
	//표준분류
	private String stndAsrt;
	
	
	public String getStndAsrt() {
		return stndAsrt;
	}
	public void setStndAsrt(String stndAsrt) {
		this.stndAsrt = stndAsrt;
	}
	public String getSysNm() {
		return sysNm;
	}
	public void setSysNm(String sysNm) {
		this.sysNm = sysNm;
	}
	public String getDatamodelNm() {
		return datamodelNm;
	}
	public void setDatamodelNm(String datamodelNm) {
		this.datamodelNm = datamodelNm;
	}
	public String getPrimarySubjNm() {
		return primarySubjNm;
	}
	public void setPrimarySubjNm(String primarySubjNm) {
		this.primarySubjNm = primarySubjNm;
	}
	public String getSubjNm() {
		return subjNm;
	}
	public void setSubjNm(String subjNm) {
		this.subjNm = subjNm;
	}
	public String getSubjId() {
		return subjId;
	}
	public void setSubjId(String subjId) {
		this.subjId = subjId;
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
	public String getPdmTblId() {
		return pdmTblId;
	}
	public void setPdmTblId(String pdmTblId) {
		this.pdmTblId = pdmTblId;
	}
	public String getColTotCnt() {
		return colTotCnt;
	}
	public void setColTotCnt(String colTotCnt) {
		this.colTotCnt = colTotCnt;
	}
	public String getDdlCnt() {
		return ddlCnt;
	}
	public void setDdlCnt(String ddlCnt) {
		this.ddlCnt = ddlCnt;
	}
	public String getDbcCnt() {
		return dbcCnt;
	}
	public void setDbcCnt(String dbcCnt) {
		this.dbcCnt = dbcCnt;
	}
	public String getSditmId() {
		return sditmId;
	}
	public void setSditmId(String sditmId) {
		this.sditmId = sditmId;
	}
	public String getSditmLnm() {
		return sditmLnm;
	}
	public void setSditmLnm(String sditmLnm) {
		this.sditmLnm = sditmLnm;
	}
	public String getSditmPnm() {
		return sditmPnm;
	}
	public void setSditmPnm(String sditmPnm) {
		this.sditmPnm = sditmPnm;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getDmnLnm() {
		return dmnLnm;
	}
	public void setDmnLnm(String dmnLnm) {
		this.dmnLnm = dmnLnm;
	}
	public String getDmnPnm() {
		return dmnPnm;
	}
	public void setDmnPnm(String dmnPnm) {
		this.dmnPnm = dmnPnm;
	}
	public String getDmnId() {
		return dmnId;
	}
	public void setDmnId(String dmnId) {
		this.dmnId = dmnId;
	}
	public String getDomnDesc() {
		return domnDesc;
	}
	public void setDomnDesc(String domnDesc) {
		this.domnDesc = domnDesc;
	}
	public String getStwdNumCnt() {
		return stwdNumCnt;
	}
	public void setStwdNumCnt(String stwdNumCnt) {
		this.stwdNumCnt = stwdNumCnt;
	}
	public String getInfotypeNumCnt() {
		return infotypeNumCnt;
	}
	public void setInfotypeNumCnt(String infotypeNumCnt) {
		this.infotypeNumCnt = infotypeNumCnt;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public String getAsc() {
		return asc;
	}
	public void setAsc(String asc) {
		this.asc = asc;
	}
	public String getStndNm() {
		return stndNm;
	}
	public void setStndNm(String stndNm) {
		this.stndNm = stndNm;
	}
	public String getObjDescn() {
		return objDescn;
	}
	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}
	public String getDomnNumCnt() {
		return domnNumCnt;
	}
	public void setDomnNumCnt(String domnNumCnt) {
		this.domnNumCnt = domnNumCnt;
	}
	public String getItemNumCnt() {
		return itemNumCnt;
	}
	public void setItemNumCnt(String itemNumCnt) {
		this.itemNumCnt = itemNumCnt;
	}
	public String getEntyNumCnt() {
		return entyNumCnt;
	}
	public void setEntyNumCnt(String entyNumCnt) {
		this.entyNumCnt = entyNumCnt;
	}
	public String getTotlNumCnt() {
		return totlNumCnt;
	}
	public void setTotlNumCnt(String totlNumCnt) {
		this.totlNumCnt = totlNumCnt;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PusAocdVO [objDescn=").append(objDescn)
				.append(", domnNumCnt=").append(domnNumCnt)
				.append(", itemNumCnt=").append(itemNumCnt)
				.append(", entyNumCnt=").append(entyNumCnt)
				.append(", totlNumCnt=").append(totlNumCnt).append(", stndNm=")
				.append(stndNm).append(", orderby=").append(orderby)
				.append(", asc=").append(asc).append(", dmnLnm=")
				.append(dmnLnm).append(", dmnPnm=").append(dmnPnm)
				.append(", dmnId=").append(dmnId).append(", domnDesc=")
				.append(domnDesc).append(", stwdNumCnt=").append(stwdNumCnt)
				.append(", infotypeNumCnt=").append(infotypeNumCnt)
				.append(", sditmId=").append(sditmId).append(", sditmLnm=")
				.append(sditmLnm).append(", sditmPnm=").append(sditmPnm)
				.append(", itemDesc=").append(itemDesc)
				.append(", datamodelNm=").append(datamodelNm)
				.append(", primarySubjNm=").append(primarySubjNm)
				.append(", subjNm=").append(subjNm).append(", subjId=")
				.append(subjId).append(", pdmTblLnm=").append(pdmTblLnm)
				.append(", pdmTblPnm=").append(pdmTblPnm).append(", pdmTblId=")
				.append(pdmTblId).append(", colTotCnt=").append(colTotCnt)
				.append(", ddlCnt=").append(ddlCnt).append(", dbcCnt=")
				.append(dbcCnt).append("]");
		return builder.toString();
	}
	
	

	
}
