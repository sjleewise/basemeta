package kr.wise.meta.report.service;

import kr.wise.commons.rqstmst.service.WaqMstr;

public class RqstReportVO extends WaqMstr {
	
	//기간별 요청서 처리현황
	//결재 요청 총합계
	private String rqstTot;
	//승인건수 카운트
	private String aprvCnt;
	//반려건수 카운트
	private String rjctCnt;
	
	//검색날짜조건
	private String searchBgnDe;
	private String searchEndDe;
	
	//분기구분 
	private String dtmDs;
	
	
	//DBA DDL요청서 처리현황
	//년도 월
	private String staDtm;
	//ddl 요청건수
	private String ddlCnt;
	private String ddlCntDisp;
	
	private String ddlIdxCnt;
	private String ddlIdxCntDisp;
	//기타오브젝트 요청건수
	private String dfcCnt;
	//총처리건수(요청서) 
	private String tot;
	private String totDisp;
	//ddl 처리현황건수
	private String tDdlCnt;
	
	private String tDdlIdxCnt;
	//기타오브젝트 처리현황건수
	private String tDfcCnt;
	//총 처리현황 건수
	private String tTot;
	
	
	//모델명
	private String datamodelNm;
	//상위주제영역명
	private String primarySubjNm;
	//생성건수
	private String insCnt;
	//변경건수
	private String updCnt;
	//삭제건수
	private String delCnt;
	
	//부서id
	private String deptId;
	//부서명
	private String deptNm;
	//파트id
	private String partId;
	//파트명
	private String partNm;
	//기타요청건수
	private String etcCnt;
	
	//DB물리명
	private String dbConnTrgLnm;
	//테이블생성건수
	private String tblInsCnt;
	//테이블변경건수
	private String tblUpdCnt;
	//테이블삭제건수
	private String tblDelCnt;
	//인덱스생성건수
	private String idxInsCnt;
	//인덱스변경건수
	private String idxUpdCnt;
	//인덱스삭제건수
	private String idxDelCnt;
	//트리거건수
	private String triCnt;
	//시노님건수
	private String symCnt;
	//프로시저건수
	private String procCnt;
	//함수건수
	private String funcCnt;
	//파티션건수
	private String partCnt;

	private String sysAreaLnm;
	private String fullPath;
	
	private String rqstCnt;
	private String rqstAprvCnt;
	private String rqstRjctCnt;
	
	
	
	
	public String getRqstCnt() {
		return rqstCnt;
	}
	public void setRqstCnt(String rqstCnt) {
		this.rqstCnt = rqstCnt;
	}
	public String getRqstAprvCnt() {
		return rqstAprvCnt;
	}
	public void setRqstAprvCnt(String rqstAprvCnt) {
		this.rqstAprvCnt = rqstAprvCnt;
	}
	public String getRqstRjctCnt() {
		return rqstRjctCnt;
	}
	public void setRqstRjctCnt(String rqstRjctCnt) {
		this.rqstRjctCnt = rqstRjctCnt;
	}
	public String getSysAreaLnm() {
		return sysAreaLnm;
	}
	public void setSysAreaLnm(String sysAreaLnm) {
		this.sysAreaLnm = sysAreaLnm;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}
	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
	}
	public String getTblInsCnt() {
		return tblInsCnt;
	}
	public void setTblInsCnt(String tblInsCnt) {
		this.tblInsCnt = tblInsCnt;
	}
	public String getTblUpdCnt() {
		return tblUpdCnt;
	}
	public void setTblUpdCnt(String tblUpdCnt) {
		this.tblUpdCnt = tblUpdCnt;
	}
	public String getTblDelCnt() {
		return tblDelCnt;
	}
	public void setTblDelCnt(String tblDelCnt) {
		this.tblDelCnt = tblDelCnt;
	}
	public String getIdxInsCnt() {
		return idxInsCnt;
	}
	public void setIdxInsCnt(String idxInsCnt) {
		this.idxInsCnt = idxInsCnt;
	}
	public String getIdxUpdCnt() {
		return idxUpdCnt;
	}
	public void setIdxUpdCnt(String idxUpdCnt) {
		this.idxUpdCnt = idxUpdCnt;
	}
	public String getIdxDelCnt() {
		return idxDelCnt;
	}
	public void setIdxDelCnt(String idxDelCnt) {
		this.idxDelCnt = idxDelCnt;
	}
	public String getTriCnt() {
		return triCnt;
	}
	public void setTriCnt(String triCnt) {
		this.triCnt = triCnt;
	}
	public String getSymCnt() {
		return symCnt;
	}
	public void setSymCnt(String symCnt) {
		this.symCnt = symCnt;
	}
	public String getProcCnt() {
		return procCnt;
	}
	public void setProcCnt(String procCnt) {
		this.procCnt = procCnt;
	}
	public String getFuncCnt() {
		return funcCnt;
	}
	public void setFuncCnt(String funcCnt) {
		this.funcCnt = funcCnt;
	}
	public String getPartCnt() {
		return partCnt;
	}
	public void setPartCnt(String partCnt) {
		this.partCnt = partCnt;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}
	public String getPartNm() {
		return partNm;
	}
	public void setPartNm(String partNm) {
		this.partNm = partNm;
	}
	public String getEtcCnt() {
		return etcCnt;
	}
	public void setEtcCnt(String etcCnt) {
		this.etcCnt = etcCnt;
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
	public String getInsCnt() {
		return insCnt;
	}
	public void setInsCnt(String insCnt) {
		this.insCnt = insCnt;
	}
	public String getUpdCnt() {
		return updCnt;
	}
	public void setUpdCnt(String updCnt) {
		this.updCnt = updCnt;
	}
	public String getDelCnt() {
		return delCnt;
	}
	public void setDelCnt(String delCnt) {
		this.delCnt = delCnt;
	}
	public String getStaDtm() {
		return staDtm;
	}
	public void setStaDtm(String staDtm) {
		this.staDtm = staDtm;
	}
	public String getDdlCnt() {
		return ddlCnt;
	}
	public void setDdlCnt(String ddlCnt) {
		this.ddlCnt = ddlCnt;
	}
	public String getDfcCnt() {
		return dfcCnt;
	}
	public void setDfcCnt(String dfcCnt) {
		this.dfcCnt = dfcCnt;
	}
	public String getTot() {
		return tot;
	}
	public void setTot(String tot) {
		this.tot = tot;
	}
	public String gettDdlCnt() {
		return tDdlCnt;
	}
	public void settDdlCnt(String tDdlCnt) {
		this.tDdlCnt = tDdlCnt;
	}
	public String gettDfcCnt() {
		return tDfcCnt;
	}
	public void settDfcCnt(String tDfcCnt) {
		this.tDfcCnt = tDfcCnt;
	}
	public String gettTot() {
		return tTot;
	}
	public void settTot(String tTot) {
		this.tTot = tTot;
	}
	public String getDtmDs() {
		return dtmDs;
	}
	public void setDtmDs(String dtmDs) {
		this.dtmDs = dtmDs;
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
	public String getRqstTot() {
		return rqstTot;
	}
	public void setRqstTot(String rqstTot) {
		this.rqstTot = rqstTot;
	}
	public String getAprvCnt() {
		return aprvCnt;
	}
	public void setAprvCnt(String aprvCnt) {
		this.aprvCnt = aprvCnt;
	}
	public String getRjctCnt() {
		return rjctCnt;
	}
	public void setRjctCnt(String rjctCnt) {
		this.rjctCnt = rjctCnt;
	}
	
	
	public String getDdlCntDisp() {
		return ddlCntDisp;
	}
	public void setDdlCntDisp(String ddlCntDisp) {
		this.ddlCntDisp = ddlCntDisp;
	}
	public String getDdlIdxCnt() {
		return ddlIdxCnt;
	}
	public void setDdlIdxCnt(String ddlIdxCnt) {
		this.ddlIdxCnt = ddlIdxCnt;
	}
	public String getDdlIdxCntDisp() {
		return ddlIdxCntDisp;
	}
	public void setDdlIdxCntDisp(String ddlIdxCntDisp) {
		this.ddlIdxCntDisp = ddlIdxCntDisp;
	}
	public String getTotDisp() {
		return totDisp;
	}
	public void setTotDisp(String totDisp) {
		this.totDisp = totDisp;
	}
	public String gettDdlIdxCnt() {
		return tDdlIdxCnt;
	}
	public void settDdlIdxCnt(String tDdlIdxCnt) {
		this.tDdlIdxCnt = tDdlIdxCnt;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RqstReportVO [rqstTot=").append(rqstTot)
				.append(", aprvCnt=").append(aprvCnt).append(", rjctCnt=")
				.append(rjctCnt).append(", searchBgnDe=").append(searchBgnDe)
				.append(", searchEndDe=").append(searchEndDe)
				.append(", dtmDs=").append(dtmDs).append(", staDtm=")
				.append(staDtm).append(", ddlCnt=").append(ddlCnt)
				.append(", ddlCntDisp=").append(ddlCntDisp)
				.append(", ddlIdxCnt=").append(ddlIdxCnt)
				.append(", ddlIdxCntDisp=").append(ddlIdxCntDisp)
				.append(", dfcCnt=").append(dfcCnt).append(", tot=")
				.append(tot).append(", totDisp=").append(totDisp)
				.append(", tDdlCnt=").append(tDdlCnt).append(", tDdlIdxCnt=")
				.append(tDdlIdxCnt).append(", tDfcCnt=").append(tDfcCnt)
				.append(", tTot=").append(tTot).append(", datamodelNm=")
				.append(datamodelNm).append(", primarySubjNm=")
				.append(primarySubjNm).append(", insCnt=").append(insCnt)
				.append(", updCnt=").append(updCnt).append(", delCnt=")
				.append(delCnt).append(", deptId=").append(deptId)
				.append(", deptNm=").append(deptNm).append(", partId=")
				.append(partId).append(", partNm=").append(partNm)
				.append(", etcCnt=").append(etcCnt).append(", dbConnTrgLnm=")
				.append(dbConnTrgLnm).append(", tblInsCnt=").append(tblInsCnt)
				.append(", tblUpdCnt=").append(tblUpdCnt)
				.append(", tblDelCnt=").append(tblDelCnt)
				.append(", idxInsCnt=").append(idxInsCnt)
				.append(", idxUpdCnt=").append(idxUpdCnt)
				.append(", idxDelCnt=").append(idxDelCnt).append(", triCnt=")
				.append(triCnt).append(", symCnt=").append(symCnt)
				.append(", procCnt=").append(procCnt).append(", funcCnt=")
				.append(funcCnt).append(", partCnt=").append(partCnt)
				.append(", sysAreaLnm=").append(sysAreaLnm)
				.append(", fullPath=").append(fullPath).append(", rqstCnt=")
				.append(rqstCnt).append(", rqstAprvCnt=").append(rqstAprvCnt)
				.append(", rqstRjctCnt=").append(rqstRjctCnt).append("]");
		return builder.toString();
	}
	
	
	
}
