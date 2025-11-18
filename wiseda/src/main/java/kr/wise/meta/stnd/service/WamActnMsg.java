package kr.wise.meta.stnd.service;

import kr.wise.commons.cmm.CommonVo;

public class WamActnMsg extends CommonVo{
    private String actnMsgId;

    private String actnMsgLnm;

    private String actnMsgPnm;

    private String actnCd;

    private String errTlgLanDscd;

    private String actnMsgCtnt;

    private String actnUseYn;

    private String actnDelYn;

    private String actnCdAplDt;

    private String chnlMsgCd;

    private String chnlTypCd;

    private String mciLnkYn;

    private String mciLnkDt;

	public String getActnMsgId() {
		return actnMsgId;
	}

	public void setActnMsgId(String actnMsgId) {
		this.actnMsgId = actnMsgId;
	}

	public String getActnMsgLnm() {
		return actnMsgLnm;
	}

	public void setActnMsgLnm(String actnMsgLnm) {
		this.actnMsgLnm = actnMsgLnm;
	}

	public String getActnMsgPnm() {
		return actnMsgPnm;
	}

	public void setActnMsgPnm(String actnMsgPnm) {
		this.actnMsgPnm = actnMsgPnm;
	}

	public String getActnCd() {
		return actnCd;
	}

	public void setActnCd(String actnCd) {
		this.actnCd = actnCd;
	}

	public String getErrTlgLanDscd() {
		return errTlgLanDscd;
	}

	public void setErrTlgLanDscd(String errTlgLanDscd) {
		this.errTlgLanDscd = errTlgLanDscd;
	}

	public String getActnMsgCtnt() {
		return actnMsgCtnt;
	}

	public void setActnMsgCtnt(String actnMsgCtnt) {
		this.actnMsgCtnt = actnMsgCtnt;
	}

	public String getActnUseYn() {
		return actnUseYn;
	}

	public void setActnUseYn(String actnUseYn) {
		this.actnUseYn = actnUseYn;
	}

	public String getActnDelYn() {
		return actnDelYn;
	}

	public void setActnDelYn(String actnDelYn) {
		this.actnDelYn = actnDelYn;
	}

	public String getActnCdAplDt() {
		return actnCdAplDt;
	}

	public void setActnCdAplDt(String actnCdAplDt) {
		this.actnCdAplDt = actnCdAplDt;
	}

	public String getChnlMsgCd() {
		return chnlMsgCd;
	}

	public void setChnlMsgCd(String chnlMsgCd) {
		this.chnlMsgCd = chnlMsgCd;
	}

	public String getChnlTypCd() {
		return chnlTypCd;
	}

	public void setChnlTypCd(String chnlTypCd) {
		this.chnlTypCd = chnlTypCd;
	}

	public String getMciLnkYn() {
		return mciLnkYn;
	}

	public void setMciLnkYn(String mciLnkYn) {
		this.mciLnkYn = mciLnkYn;
	}

	public String getMciLnkDt() {
		return mciLnkDt;
	}

	public void setMciLnkDt(String mciLnkDt) {
		this.mciLnkDt = mciLnkDt;
	}

	@Override
	public String toString() {
		return "WamActnMsg [actnMsgId=" + actnMsgId + ", actnMsgLnm="
				+ actnMsgLnm + ", actnMsgPnm=" + actnMsgPnm + ", actnCd="
				+ actnCd + ", errTlgLanDscd=" + errTlgLanDscd
				+ ", actnMsgCtnt=" + actnMsgCtnt + ", actnUseYn=" + actnUseYn
				+ ", actnDelYn=" + actnDelYn + ", actnCdAplDt=" + actnCdAplDt
				+ ", chnlMsgCd=" + chnlMsgCd + ", chnlTypCd=" + chnlTypCd
				+ ", mciLnkYn=" + mciLnkYn + ", mciLnkDt=" + mciLnkDt + "]";
	}
}