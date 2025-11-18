package kr.wise.portal.myjob.service;

import java.util.Date;
import java.util.List;

    
    public class WaqReqMst {
    	
    private Date xprDttm;
    
    private  String cnt;
    
    private int totCnt;
    
    public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	private int rn;

	private String objNm;

	private String objKnm;

    private String objEnm;

    private String requestId;

    private String orglDttm;

    private String orglUser;

    private Date updtDttm;

    private String updtUser;

    private Date aprvDttm;

    private String aprvUser;

    private String ofBizType;

    private String ofBizTypeId;

	private String requestStatusId;
    
    private String dtlCnt;

    private String bizNm;

    private String requestType;

    private String requestStatus;

    private String ofMstReqType;

    private String requestText;

    private String ofMstRpyType;

    private String reviewTxt;

    private String isDeleteYn;

    private String reqFileNm;

    private String reqRealFileNm;

    private String reqContextType;

    private String reqFileComment;

    private String aprvFileNm;

    private String aprvRealFileNm;

    private String aprvContextType;

    private String aprvFileComment;

    private String csrNo;

    private String prId;

    private String reqRsn;
    
    private String reqBgnde;
    
    private String reqEndde;
    
    private String aprvId;
    
    private String aprvMthd;
  
    
    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setAprvMthd(String aprvMthd) {
		this.aprvMthd = aprvMthd;
	}

	private String userId;
    
    
    
    
    
    public String getAprvId() {
		return aprvId;
	}

	public void setAprvId(String aprvId) {
		this.aprvId = aprvId;
	}

	public String getAprvMthd() {
		return aprvMthd;
	}

	public void setAprvMtfd(String aprvMthd) {
		this.aprvMthd = aprvMthd;
	}

	public Date getXprDttm() {
        return xprDttm;
    }

    public void setXprDttm(Date xprDttm) {
        this.xprDttm = xprDttm;
    }

    public String getObjNm() {
        return objNm;
    }

    public void setObjNm(String objNm) {
        this.objNm = objNm;
    }

    public String getObjKnm() {
        return objKnm;
    }

    public void setObjKnm(String objKnm) {
        this.objKnm = objKnm;
    }

    public String getObjEnm() {
        return objEnm;
    }

    public void setObjEnm(String objEnm) {
        this.objEnm = objEnm;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getOrglDttm() {
        return orglDttm;
    }

    public void setOrglDttm(String orglDttm) {
        this.orglDttm = orglDttm;
    }

    public String getOrglUser() {
        return orglUser;
    }

    public void setOrglUser(String orglUser) {
        this.orglUser = orglUser;
    }

    public Date getUpdtDttm() {
        return updtDttm;
    }

    public void setUpdtDttm(Date updtDttm) {
        this.updtDttm = updtDttm;
    }

    public String getUpdtUser() {
        return updtUser;
    }

    public void setUpdtUser(String updtUser) {
        this.updtUser = updtUser;
    }

    public Date getAprvDttm() {
        return aprvDttm;
    }

    public void setAprvDttm(Date aprvDttm) {
        this.aprvDttm = aprvDttm;
    }

    public String getAprvUser() {
        return aprvUser;
    }

    public void setAprvUser(String aprvUser) {
        this.aprvUser = aprvUser;
    }

    public String getOfBizType() {
        return ofBizType;
    }

    public void setOfBizType(String ofBizType) {
        this.ofBizType = ofBizType;
    }

    public String getDtlCnt() {
        return dtlCnt;
    }

    public void setDtlCnt(String dtlCnt) {
        this.dtlCnt = dtlCnt;
    }

    public String getBizNm() {
        return bizNm;
    }

    public void setBizNm(String bizNm) {
        this.bizNm = bizNm;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getOfMstReqType() {
        return ofMstReqType;
    }

    public void setOfMstReqType(String ofMstReqType) {
        this.ofMstReqType = ofMstReqType;
    }

    public String getRequestText() {
        return requestText;
    }

    public void setRequestText(String requestText) {
        this.requestText = requestText;
    }

    public String getOfMstRpyType() {
        return ofMstRpyType;
    }

    public void setOfMstRpyType(String ofMstRpyType) {
        this.ofMstRpyType = ofMstRpyType;
    }

    public String getReviewTxt() {
        return reviewTxt;
    }

    public void setReviewTxt(String reviewTxt) {
        this.reviewTxt = reviewTxt;
    }

    public String getIsDeleteYn() {
        return isDeleteYn;
    }

    public void setIsDeleteYn(String isDeleteYn) {
        this.isDeleteYn = isDeleteYn;
    }

    public String getReqFileNm() {
        return reqFileNm;
    }

    public void setReqFileNm(String reqFileNm) {
        this.reqFileNm = reqFileNm;
    }

    public String getReqRealFileNm() {
        return reqRealFileNm;
    }

    public void setReqRealFileNm(String reqRealFileNm) {
        this.reqRealFileNm = reqRealFileNm;
    }

    public String getReqContextType() {
        return reqContextType;
    }

    public void setReqContextType(String reqContextType) {
        this.reqContextType = reqContextType;
    }

    public String getReqFileComment() {
        return reqFileComment;
    }

    public void setReqFileComment(String reqFileComment) {
        this.reqFileComment = reqFileComment;
    }

    public String getAprvFileNm() {
        return aprvFileNm;
    }

    public void setAprvFileNm(String aprvFileNm) {
        this.aprvFileNm = aprvFileNm;
    }

    public String getAprvRealFileNm() {
        return aprvRealFileNm;
    }

    public void setAprvRealFileNm(String aprvRealFileNm) {
        this.aprvRealFileNm = aprvRealFileNm;
    }

    public String getAprvContextType() {
        return aprvContextType;
    }

    public void setAprvContextType(String aprvContextType) {
        this.aprvContextType = aprvContextType;
    }

    public String getAprvFileComment() {
        return aprvFileComment;
    }

    public void setAprvFileComment(String aprvFileComment) {
        this.aprvFileComment = aprvFileComment;
    }

    public String getCsrNo() {
        return csrNo;
    }

    public void setCsrNo(String csrNo) {
        this.csrNo = csrNo;
    }

    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId;
    }

    public String getReqRsn() {
        return reqRsn;
    }

    public void setReqRsn(String reqRsn) {
        this.reqRsn = reqRsn;
    }

	public List<WaqReqMst> searchMyRegReq() {
		return null;
	}
	
    public String getOfBizTypeId() {
		return ofBizTypeId;
	}

	public void setOfBizTypeId(String ofBizTypeId) {
		this.ofBizTypeId = ofBizTypeId;
	}

	public String getRequestStatusId() {
		return requestStatusId;
	}

	public void setRequestStatusId(String requestStatusId) {
		this.requestStatusId = requestStatusId;
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
}