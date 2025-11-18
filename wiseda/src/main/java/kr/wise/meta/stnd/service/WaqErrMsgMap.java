package kr.wise.meta.stnd.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WaqErrMsgMap extends CommonVo{
    private String msgMapId;

    private String msgMapLnm;

    private String msgMapPnm;

    private String stdErrMsgId;

    private String errCd;

    private String isdErrCasCts;

    private String osdErrCasCts;

    private String dmnId;

    private String dmnLnm;

    private String dmnPnm;

    private String cdValId;

    private String cdVal;

    private String cdValNm;

    private String isttUsImpoYn;

    private String rpstErcdYn;

    public String getMsgMapId() {
        return msgMapId;
    }

    public void setMsgMapId(String msgMapId) {
        this.msgMapId = msgMapId;
    }

    public String getMsgMapLnm() {
        return msgMapLnm;
    }

    public void setMsgMapLnm(String msgMapLnm) {
        this.msgMapLnm = msgMapLnm;
    }

    public String getMsgMapPnm() {
        return msgMapPnm;
    }

    public void setMsgMapPnm(String msgMapPnm) {
        this.msgMapPnm = msgMapPnm;
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

    public String getIsdErrCasCts() {
        return isdErrCasCts;
    }

    public void setIsdErrCasCts(String isdErrCasCts) {
        this.isdErrCasCts = isdErrCasCts;
    }

    public String getOsdErrCasCts() {
        return osdErrCasCts;
    }

    public void setOsdErrCasCts(String osdErrCasCts) {
        this.osdErrCasCts = osdErrCasCts;
    }

    public String getDmnId() {
        return dmnId;
    }

    public void setDmnId(String dmnId) {
        this.dmnId = dmnId;
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

    public String getCdValId() {
        return cdValId;
    }

    public void setCdValId(String cdValId) {
        this.cdValId = cdValId;
    }

    public String getCdVal() {
        return cdVal;
    }

    public void setCdVal(String cdVal) {
        this.cdVal = cdVal;
    }

    public String getCdValNm() {
        return cdValNm;
    }

    public void setCdValNm(String cdValNm) {
        this.cdValNm = cdValNm;
    }

    public String getIsttUsImpoYn() {
        return isttUsImpoYn;
    }

    public void setIsttUsImpoYn(String isttUsImpoYn) {
        this.isttUsImpoYn = isttUsImpoYn;
    }

    public String getRpstErcdYn() {
        return rpstErcdYn;
    }

    public void setRpstErcdYn(String rpstErcdYn) {
        this.rpstErcdYn = rpstErcdYn;
    }

	@Override
	public String toString() {
		return "WaqErrMsgMap [msgMapId=" + msgMapId + ", msgMapLnm="
				+ msgMapLnm + ", msgMapPnm=" + msgMapPnm + ", stdErrMsgId="
				+ stdErrMsgId + ", errCd=" + errCd + ", isdErrCasCts="
				+ isdErrCasCts + ", osdErrCasCts=" + osdErrCasCts + ", dmnId="
				+ dmnId + ", dmnLnm=" + dmnLnm + ", dmnPnm=" + dmnPnm
				+ ", cdValId=" + cdValId + ", cdVal=" + cdVal + ", cdValNm="
				+ cdValNm + ", isttUsImpoYn=" + isttUsImpoYn + ", rpstErcdYn="
				+ rpstErcdYn + "]";
	}
}