package kr.wise.meta.effect.service;

import java.util.Date;

public class WatPgmAstaRel {
    private String requestId;

    private String objNm;

    private Date staDttm;

    private Short subsystemId;

    private Short pgmId;

    private Short callSubsystemId;

    private Short callPgmId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getObjNm() {
        return objNm;
    }

    public void setObjNm(String objNm) {
        this.objNm = objNm;
    }

    public Date getStaDttm() {
        return staDttm;
    }

    public void setStaDttm(Date staDttm) {
        this.staDttm = staDttm;
    }

    public Short getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(Short subsystemId) {
        this.subsystemId = subsystemId;
    }

    public Short getPgmId() {
        return pgmId;
    }

    public void setPgmId(Short pgmId) {
        this.pgmId = pgmId;
    }

    public Short getCallSubsystemId() {
        return callSubsystemId;
    }

    public void setCallSubsystemId(Short callSubsystemId) {
        this.callSubsystemId = callSubsystemId;
    }

    public Short getCallPgmId() {
        return callPgmId;
    }

    public void setCallPgmId(Short callPgmId) {
        this.callPgmId = callPgmId;
    }
}