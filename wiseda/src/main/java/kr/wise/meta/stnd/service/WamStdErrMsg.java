package kr.wise.meta.stnd.service;

import kr.wise.commons.cmm.CommonVo;

public class WamStdErrMsg extends CommonVo{
    private String stdErrMsgId;

    private String stdErrMsgLnm;

    private String stdErrMsgPnm;

    private String errTlgLanDscd;

    private String errTpCd;

    private String msgDit;

    private String stdErrCdYn;

    private String actnCd;

    private String errCd;

    private String isdErrCasCts;

    private String isdErrActnCts;

    private String osdErrCasCts;

    private String osdErrActnCts;

    private String errCdAplDt;

    private String stsyErrCd;

    private String tskDmn;

    private String mngUser;
    
    private String msgTyp;
    
    private String bizDit;

    public String getStdErrMsgId() {
        return stdErrMsgId;
    }

    public void setStdErrMsgId(String stdErrMsgId) {
        this.stdErrMsgId = stdErrMsgId;
    }

    public String getStdErrMsgLnm() {
        return stdErrMsgLnm;
    }

    public void setStdErrMsgLnm(String stdErrMsgLnm) {
        this.stdErrMsgLnm = stdErrMsgLnm;
    }

    public String getStdErrMsgPnm() {
        return stdErrMsgPnm;
    }

    public void setStdErrMsgPnm(String stdErrMsgPnm) {
        this.stdErrMsgPnm = stdErrMsgPnm;
    }

    public String getErrTlgLanDscd() {
        return errTlgLanDscd;
    }

    public void setErrTlgLanDscd(String errTlgLanDscd) {
        this.errTlgLanDscd = errTlgLanDscd;
    }

    public String getErrTpCd() {
        return errTpCd;
    }

    public void setErrTpCd(String errTpCd) {
        this.errTpCd = errTpCd;
    }

    public String getMsgDit() {
        return msgDit;
    }

    public void setMsgDit(String msgDit) {
        this.msgDit = msgDit;
    }

    public String getStdErrCdYn() {
        return stdErrCdYn;
    }

    public void setStdErrCdYn(String stdErrCdYn) {
        this.stdErrCdYn = stdErrCdYn;
    }

    public String getActnCd() {
        return actnCd;
    }

    public void setActnCd(String actnCd) {
        this.actnCd = actnCd;
    }

    public String getErrCd() {
        return errCd;
    }

    public void setErrCd(String errCd) {
        this.errCd = errCd;
    }

    public String getIsdErrCasCts() {
        return isdErrCasCts;
    }

    public void setIsdErrCasCts(String isdErrCasCts) {
        this.isdErrCasCts = isdErrCasCts;
    }

    public String getIsdErrActnCts() {
        return isdErrActnCts;
    }

    public void setIsdErrActnCts(String isdErrActnCts) {
        this.isdErrActnCts = isdErrActnCts;
    }

    public String getOsdErrCasCts() {
        return osdErrCasCts;
    }

    public void setOsdErrCasCts(String osdErrCasCts) {
        this.osdErrCasCts = osdErrCasCts;
    }

    public String getOsdErrActnCts() {
        return osdErrActnCts;
    }

    public void setOsdErrActnCts(String osdErrActnCts) {
        this.osdErrActnCts = osdErrActnCts;
    }

    public String getErrCdAplDt() {
        return errCdAplDt;
    }

    public void setErrCdAplDt(String errCdAplDt) {
        this.errCdAplDt = errCdAplDt;
    }

    public String getStsyErrCd() {
        return stsyErrCd;
    }

    public void setStsyErrCd(String stsyErrCd) {
        this.stsyErrCd = stsyErrCd;
    }

    public String getTskDmn() {
        return tskDmn;
    }

    public void setTskDmn(String tskDmn) {
        this.tskDmn = tskDmn;
    }

    public String getMngUser() {
        return mngUser;
    }

    public void setMngUser(String mngUser) {
        this.mngUser = mngUser;
    }

	public String getMsgTyp() {
		return msgTyp;
	}

	public void setMsgTyp(String msgTyp) {
		this.msgTyp = msgTyp;
	}

	public String getBizDit() {
		return bizDit;
	}

	public void setBizDit(String bizDit) {
		this.bizDit = bizDit;
	}

	@Override
	public String toString() {
		return "WamStdErrMsg [stdErrMsgId=" + stdErrMsgId + ", stdErrMsgLnm="
				+ stdErrMsgLnm + ", stdErrMsgPnm=" + stdErrMsgPnm
				+ ", errTlgLanDscd=" + errTlgLanDscd + ", errTpCd=" + errTpCd
				+ ", msgDit=" + msgDit + ", stdErrCdYn=" + stdErrCdYn
				+ ", actnCd=" + actnCd + ", errCd=" + errCd + ", isdErrCasCts="
				+ isdErrCasCts + ", isdErrActnCts=" + isdErrActnCts
				+ ", osdErrCasCts=" + osdErrCasCts + ", osdErrActnCts="
				+ osdErrActnCts + ", errCdAplDt=" + errCdAplDt + ", stsyErrCd="
				+ stsyErrCd + ", tskDmn=" + tskDmn + ", mngUser=" + mngUser
				+ ", msgTyp=" + msgTyp + ", bizDit=" + bizDit + "]";
	}
}