package kr.wise.commons.damgmt.schedule.service;

import java.math.BigDecimal;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;

public class WamShd extends CommonVo{

    private String shdId;

    private String shdLnm; //�뒪耳�以꾨챸

    private String shdPnm;

    private String shdKndCd;

    private String shdKndNm;

    private String shdUseYn;

    private String shdBprYn;

    private String erDataLdYn;

	private BigDecimal erDataLdCnt;

    private String pkDataLdYn;

    private BigDecimal pkDataLdCnt;

    private Integer anaDgr;

    private String anaDgrNm;

	private String anaDgrAutoIncYn;

    private String shdTypCd;

    private String shdTypNm;

    private String shdStrDtm;

    private String shdStrHr;

    private String shdStrMnt;

    private String shdDly;

    private String shdDlyVal;

    private String shdWkl;

    private String shdWklVal;

    private String shdMny;

    private String shdMnyVal;
    
    private String orgShdId;
    
    private String dbcTblNm;

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

	public String getShdPnm() {
		return shdPnm;
	}

	public void setShdPnm(String shdPnm) {
		this.shdPnm = shdPnm;
	}

	public String getShdKndCd() {
		return shdKndCd;
	}

	public void setShdKndCd(String shdKndCd) {
		this.shdKndCd = shdKndCd;
	}

	public String getShdKndNm() {
		return shdKndNm;
	}

	public void setShdKndNm(String shdKndNm) {
		this.shdKndNm = shdKndNm;
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

	public Integer getAnaDgr() {
		return anaDgr;
	}

	public void setAnaDgr(Integer anaDgr) {
		this.anaDgr = anaDgr;
	}

	public String getAnaDgrNm() {
		return anaDgrNm;
	}

	public void setAnaDgrNm(String anaDgrNm) {
		this.anaDgrNm = anaDgrNm;
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

	public String getShdTypNm() {
		return shdTypNm;
	}

	public void setShdTypNm(String shdTypNm) {
		this.shdTypNm = shdTypNm;
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

	public String getShdDlyVal() {
		return shdDlyVal;
	}

	public void setShdDlyVal(String shdDlyVal) {
		this.shdDlyVal = shdDlyVal;
	}

	public String getShdWkl() {
		return shdWkl;
	}

	public void setShdWkl(String shdWkl) {
		this.shdWkl = shdWkl;
	}

	public String getShdWklVal() {
		return shdWklVal;
	}

	public void setShdWklVal(String shdWklVal) {
		this.shdWklVal = shdWklVal;
	}

	public String getShdMny() {
		return shdMny;
	}

	public void setShdMny(String shdMny) {
		this.shdMny = shdMny;
	}

	public String getShdMnyVal() {
		return shdMnyVal;
	}

	public void setShdMnyVal(String shdMnyVal) {
		this.shdMnyVal = shdMnyVal;
	}
	
	public String getOrgShdId() {
        return orgShdId;
    }

    public void setOrgShdId(String orgShdId) {
        this.orgShdId = orgShdId;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamShd [shdId=").append(shdId).append(", shdLnm=")
				.append(shdLnm).append(", shdPnm=").append(shdPnm)
				.append(", shdKndCd=").append(shdKndCd).append(", shdKndNm=")
				.append(shdKndNm).append(", shdUseYn=").append(shdUseYn)
				.append(", shdBprYn=").append(shdBprYn).append(", erDataLdYn=")
				.append(erDataLdYn).append(", erDataLdCnt=")
				.append(erDataLdCnt).append(", pkDataLdYn=").append(pkDataLdYn)
				.append(", pkDataLdCnt=").append(pkDataLdCnt)
				.append(", anaDgr=").append(anaDgr).append(", anaDgrNm=")
				.append(anaDgrNm).append(", anaDgrAutoIncYn=")
				.append(anaDgrAutoIncYn).append(", shdTypCd=").append(shdTypCd)
				.append(", shdTypNm=").append(shdTypNm).append(", shdStrDtm=")
				.append(shdStrDtm).append(", shdStrHr=").append(shdStrHr)
				.append(", shdStrMnt=").append(shdStrMnt).append(", shdDly=")
				.append(shdDly).append(", shdDlyVal=").append(shdDlyVal)
				.append(", shdWkl=").append(shdWkl).append(", shdWklVal=")
				.append(shdWklVal).append(", shdMny=").append(shdMny)
				.append(", shdMnyVal=").append(shdMnyVal)
				.append("]");
		return builder.toString();
	}

	public String getDbcTblNm() {
		return dbcTblNm;
	}

	public void setDbcTblNm(String dbcTblNm) {
		this.dbcTblNm = dbcTblNm;
	}

}