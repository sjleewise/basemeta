package kr.wise.executor.dm;

import java.math.BigDecimal;

public class SchMstDM {
	private String shdId;
	private String shdLnm;
	private String shdPnm;
	private String rqstNo;
	
	private String shdKndCd; //스케줄종류코드
	private String shdUseYn; //스케줄사용여부
	private String shdBprYn; //일괄처리여부
	private String erDataLdYn; //오류데이터적재여부
	private BigDecimal erDataLdCnt ; //오류데이터적재건수
	
	private String pkDataLdYn; //PK데이터적재여부
	private BigDecimal pkDataLdCnt ; //PK데이터적재건수	
	
	private BigDecimal anaDgr;  //분석차수 
	private String anaDgrAutoIncYn; //분석차수자동증가여부	
	
	private String shdTypCd ; //스케줄유형코드 : 한번만, 매일, 매주 매달 등등
	private String shdStrDtm; //스케줄시작일
	
	private String shdStrHr; //스케줄시작시간
	private String shdStrMnt; //스케줄시작분
	
	private String shdDly ; //스케줄매일
	private int shdDlyVal ; //스케줄매일값
	
	private int shdWkl ; //스케줄매주
	private int shdWklVal ; //스케줄매주값
	
	private long shdMny ; //스케줄매달
	private int shdMnyVal ; //스케줄매달값	
	
	private String rqstUserId; //요청자
	private String aprvUserId; //승인자
	
	private String objDescn; //설명
	
	
	public String getShdId() {
		return shdId;
	}
	public void setShdId(String shdId) {
		this.shdId = shdId;
	}
	public String getShdLnm() {
		return shdLnm;
	}
	public void setShdLnm(String shdLnm) {
		this.shdLnm = shdLnm;
	}
	public String getShdKndCd() {
		return shdKndCd;
	}
	public void setShdKndCd(String shdKndCd) {
		this.shdKndCd = shdKndCd;
	}
	public String getShdUseYn() {
		return shdUseYn;
	}
	public void setShdUseYn(String shdUseYn) {
		this.shdUseYn = shdUseYn;
	}
	public String getShdBprYn() {
		return shdBprYn;
	}
	public void setShdBprYn(String shdBprYn) {
		this.shdBprYn = shdBprYn;
	}
	public String getErDataLdYn() {
		return erDataLdYn;
	}
	public void setErDataLdYn(String erDataLdYn) {
		this.erDataLdYn = erDataLdYn;
	}
	public BigDecimal getErDataLdCnt() {
		return erDataLdCnt;
	}
	public void setErDataLdCnt(BigDecimal erDataLdCnt) {
		this.erDataLdCnt = erDataLdCnt;
	}
	public String getPkDataLdYn() {
		return pkDataLdYn;
	}
	public void setPkDataLdYn(String pkDataLdYn) {
		this.pkDataLdYn = pkDataLdYn;
	}
	public BigDecimal getPkDataLdCnt() {
		return pkDataLdCnt;
	}
	public void setPkDataLdCnt(BigDecimal pkDataLdCnt) {
		this.pkDataLdCnt = pkDataLdCnt;
	}
	public BigDecimal getAnaDgr() {
		return anaDgr;
	}
	public void setAnaDgr(BigDecimal anaDgr) {
		this.anaDgr = anaDgr;
	}
	public String getAnaDgrAutoIncYn() {
		return anaDgrAutoIncYn;
	}
	public void setAnaDgrAutoIncYn(String anaDgrAutoIncYn) {
		this.anaDgrAutoIncYn = anaDgrAutoIncYn;
	}
	public String getShdTypCd() {
		return shdTypCd;
	}
	public void setShdTypCd(String shdTypCd) {
		this.shdTypCd = shdTypCd;
	}
	public String getShdStrDtm() {
		return shdStrDtm;
	}
	public void setShdStrDtm(String shdStrDtm) {
		this.shdStrDtm = shdStrDtm;
	}
	public String getShdStrHr() {
		return shdStrHr;
	}
	public void setShdStrHr(String shdStrHr) {
		this.shdStrHr = shdStrHr;
	}
	public String getShdStrMnt() {
		return shdStrMnt;
	}
	public void setShdStrMnt(String shdStrMnt) {
		this.shdStrMnt = shdStrMnt;
	}
	public String getShdDly() {
		return shdDly;
	}
	public void setShdDly(String shdDly) {
		this.shdDly = shdDly;
	}
	public int getShdWkl() {
		return shdWkl;
	}
	public void setShdWkl(int shdWkl) {
		this.shdWkl = shdWkl;
	}
	public long getShdMny() {
		return shdMny;
	}
	public void setShdMny(long shdMny) {
		this.shdMny = shdMny;
	}
	public int getShdDlyVal() {
		return shdDlyVal;
	}
	public void setShdDlyVal(int shdDlyVal) {
		this.shdDlyVal = shdDlyVal;
	}
	public int getShdWklVal() {
		return shdWklVal;
	}
	public void setShdWklVal(int shdWklVal) {
		this.shdWklVal = shdWklVal;
	}
	public int getShdMnyVal() {
		return shdMnyVal;
	}
	public void setShdMnyVal(int shdMnyVal) {
		this.shdMnyVal = shdMnyVal;
	}
	public String getRqstUserId() {
		return rqstUserId;
	}
	public void setRqstUserId(String rqstUserId) {
		this.rqstUserId = rqstUserId;
	}
	public String getAprvUserId() {
		return aprvUserId;
	}
	public void setAprvUserId(String aprvUserId) {
		this.aprvUserId = aprvUserId;
	}
	public String getObjDescn() {
		return objDescn;
	}
	public void setObjDescn(String objDescn) {
		this.objDescn = objDescn;
	}
	/**
	 * @return the shdPnm
	 */
	public String getShdPnm() {
		return shdPnm;
	}
	/**
	 * @param shdPnm the shdPnm to set
	 */
	public void setShdPnm(String shdPnm) {
		this.shdPnm = shdPnm;
	}
	/**
	 * @return the rqstNo
	 */
	public String getRqstNo() {
		return rqstNo;
	}
	/**
	 * @param rqstNo the rqstNo to set
	 */
	public void setRqstNo(String rqstNo) {
		this.rqstNo = rqstNo;
	}
	
}
