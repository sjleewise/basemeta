package kr.wise.portal.myjob.service;


public class VWaaAprvPrcs {
    private String requestId;

    private String requestType;

    private String requestTypeNm;

    private String bizNm;

    private String aprvUser;

    private String rqstUser;

    private String requestStatus;

    private String orglDttm;

    private String aprvDttm;

    private String csrNo;

    private Short aprvStep;

    private String reqXpnDdlDate;

    private String isDdlUncYn;
    
    private String requestStatusNm;
    
    private String reqBgnde;
    
    private String reqEndde;
    
    public String getUserid() {
		return userid;
	}

	public void setUserid(String userId) {
		this.userid = userId;
	}

	private int totCnt;
    
    private String userid;
    
    public int getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}

	public int getRn() {
		return rn;
	}

	public void setRn(int rn) {
		this.rn = rn;
	}

	private int rn;
    

	public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestTypeNm() {
        return requestTypeNm;
    }

    public void setRequestTypeNm(String requestTypeNm) {
        this.requestTypeNm = requestTypeNm;
    }

    public String getBizNm() {
        return bizNm;
    }

    public void setBizNm(String bizNm) {
        this.bizNm = bizNm;
    }

    public String getAprvUser() {
        return aprvUser;
    }

    public void setAprvUser(String aprvUser) {
        this.aprvUser = aprvUser;
    }

    public String getRqstUser() {
        return rqstUser;
    }

    public void setRqstUser(String rqstUser) {
        this.rqstUser = rqstUser;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getOrglDttm() {
        return orglDttm;
    }

    public void setOrglDttm(String orglDttm) {
        this.orglDttm = orglDttm;
    }

    public String getAprvDttm() {
        return aprvDttm;
    }

    public void setAprvDttm(String aprvDttm) {
        this.aprvDttm = aprvDttm;
    }

    public String getCsrNo() {
        return csrNo;
    }

    public void setCsrNo(String csrNo) {
        this.csrNo = csrNo;
    }

    public Short getAprvStep() {
        return aprvStep;
    }

    public String getRequestStatusNm() {
		return requestStatusNm;
	}

	public void setRequestStatusNm(String requestStatusNm) {
		this.requestStatusNm = requestStatusNm;
	}

	public void setAprvStep(Short aprvStep) {
        this.aprvStep = aprvStep;
    }

    public String getReqXpnDdlDate() {
        return reqXpnDdlDate;
    }

    public void setReqXpnDdlDate(String reqXpnDdlDate) {
        this.reqXpnDdlDate = reqXpnDdlDate;
    }

    public String getIsDdlUncYn() {
        return isDdlUncYn;
    }

    public void setIsDdlUncYn(String isDdlUncYn) {
        this.isDdlUncYn = isDdlUncYn;
    }
    
    public String getReqBgnde() {
		return reqBgnde;
	}

	public void setReqBgnde(String reqBgnde) {
		this.reqBgnde = reqBgnde;
	}

	public String getReqEndde() {
		return reqEndde;
	}

	public void setReqEndde(String reqEndde) {
		this.reqEndde = reqEndde;
	}
}