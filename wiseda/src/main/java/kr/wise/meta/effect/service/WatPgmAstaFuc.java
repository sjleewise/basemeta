package kr.wise.meta.effect.service;

import java.util.ArrayList;

public class WatPgmAstaFuc extends WatPgmAsta {
    private Integer subsystemId;

    private Integer pgmId;

    private Integer fucId;

    private String fucEnm;

    private String requestId;

    private ArrayList<String> tblEnms = new ArrayList<String>();

    private String tblEnm;



    public Integer getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(Integer subsystemId) {
        this.subsystemId = subsystemId;
    }

    public Integer getPgmId() {
        return pgmId;
    }

    public void setPgmId(Integer pgmId) {
        this.pgmId = pgmId;
    }

    public Integer getFucId() {
        return fucId;
    }

    public void setFucId(Integer fucId) {
        this.fucId = fucId;
    }

    public String getFucEnm() {
        return fucEnm;
    }

    public void setFucEnm(String fucEnm) {
        this.fucEnm = fucEnm;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

	/**
	 * @return the tblEnms
	 */
	public ArrayList<String> getTblEnms() {
		return tblEnms;
	}

	/**
	 * @param tblEnms the tblEnms to set
	 */
	public void setTblEnms(ArrayList<String> tblEnms) {
		this.tblEnms = tblEnms;
	}

	/**
	 * @return the tblEnm
	 */
	public String getTblEnm() {
		return tblEnm;
	}

	/**
	 * @param tblEnm the tblEnm to set
	 */
	public void setTblEnm(String tblEnm) {
		this.tblEnm = tblEnm;
	}


}