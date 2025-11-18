package kr.wise.meta.stnd.service;

import kr.wise.commons.cmm.CommonVo;

public class WamChnlErrMsg extends CommonVo{
    private String chnlMsgId;

    private String chnlMsgLnm;

    private String chnlMsgPnm;

    private String stdErrMsgId;

    private String errCd;

    private String errTlgLanDscd;

    private String errTpCd;

    private String chnlTpCd;

    private String errCdUsYn;

    private String chnlErrCd;

    private String chnlErrMsgCts;

    private String errCdAplDt;

    private String actnCd;

    private String chnlDtlsClcd;

    private String chnlGrpCd;

    public String getChnlMsgId() {
        return chnlMsgId;
    }

    public void setChnlMsgId(String chnlMsgId) {
        this.chnlMsgId = chnlMsgId;
    }

    public String getChnlMsgLnm() {
        return chnlMsgLnm;
    }

    public void setChnlMsgLnm(String chnlMsgLnm) {
        this.chnlMsgLnm = chnlMsgLnm;
    }

    public String getChnlMsgPnm() {
        return chnlMsgPnm;
    }

    public void setChnlMsgPnm(String chnlMsgPnm) {
        this.chnlMsgPnm = chnlMsgPnm;
    }

    public String getStdErrMsgId() {
        return stdErrMsgId;
    }

    public void setStdErrMsgId(String stdErrMsgId) {
        this.stdErrMsgId = stdErrMsgId;
    }

    public String getErrCd() {
        return errCd;
    }

    public void setErrCd(String errCd) {
        this.errCd = errCd;
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

    public String getChnlTpCd() {
        return chnlTpCd;
    }

    public void setChnlTpCd(String chnlTpCd) {
        this.chnlTpCd = chnlTpCd;
    }

    public String getErrCdUsYn() {
        return errCdUsYn;
    }

    public void setErrCdUsYn(String errCdUsYn) {
        this.errCdUsYn = errCdUsYn;
    }

    public String getChnlErrCd() {
        return chnlErrCd;
    }

    public void setChnlErrCd(String chnlErrCd) {
        this.chnlErrCd = chnlErrCd;
    }

    public String getChnlErrMsgCts() {
        return chnlErrMsgCts;
    }

    public void setChnlErrMsgCts(String chnlErrMsgCts) {
        this.chnlErrMsgCts = chnlErrMsgCts;
    }

    public String getErrCdAplDt() {
        return errCdAplDt;
    }

    public void setErrCdAplDt(String errCdAplDt) {
        this.errCdAplDt = errCdAplDt;
    }

    public String getActnCd() {
        return actnCd;
    }

    public void setActnCd(String actnCd) {
        this.actnCd = actnCd;
    }

    public String getChnlDtlsClcd() {
        return chnlDtlsClcd;
    }

    public void setChnlDtlsClcd(String chnlDtlsClcd) {
        this.chnlDtlsClcd = chnlDtlsClcd;
    }

    public String getChnlGrpCd() {
        return chnlGrpCd;
    }

    public void setChnlGrpCd(String chnlGrpCd) {
        this.chnlGrpCd = chnlGrpCd;
    }

	@Override
	public String toString() {
		return "WamChnlErrMsg [chnlMsgId=" + chnlMsgId + ", chnlMsgLnm="
				+ chnlMsgLnm + ", chnlMsgPnm=" + chnlMsgPnm + ", stdErrMsgId="
				+ stdErrMsgId + ", errCd=" + errCd + ", errTlgLanDscd="
				+ errTlgLanDscd + ", errTpCd=" + errTpCd + ", chnlTpCd="
				+ chnlTpCd + ", errCdUsYn=" + errCdUsYn + ", chnlErrCd="
				+ chnlErrCd + ", chnlErrMsgCts=" + chnlErrMsgCts
				+ ", errCdAplDt=" + errCdAplDt + ", actnCd=" + actnCd
				+ ", chnlDtlsClcd=" + chnlDtlsClcd + ", chnlGrpCd=" + chnlGrpCd
				+ "]";
	}
}