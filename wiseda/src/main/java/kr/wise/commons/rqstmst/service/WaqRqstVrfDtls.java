package kr.wise.commons.rqstmst.service;

public class WaqRqstVrfDtls {
    private String bizDtlCd;

    private String rqstNo;

    private Integer rqstSno;

    private Integer vrfSno;

    private Integer rqstDtlSno;

    private String vrfDtlCd;

    private String vrfDtls;
    
    private String vrfDescn;

    public String getBizDtlCd() {
        return bizDtlCd;
    }

    public void setBizDtlCd(String bizDtlCd) {
        this.bizDtlCd = bizDtlCd;
    }

    public String getRqstNo() {
        return rqstNo;
    }

    public void setRqstNo(String rqstNo) {
        this.rqstNo = rqstNo;
    }

    public Integer getRqstSno() {
        return rqstSno;
    }

    public void setRqstSno(Integer rqstSno) {
        this.rqstSno = rqstSno;
    }

    public Integer getVrfSno() {
        return vrfSno;
    }

    public void setVrfSno(Integer vrfSno) {
        this.vrfSno = vrfSno;
    }

    public Integer getRqstDtlSno() {
		return rqstDtlSno;
	}

	public void setRqstDtlSno(Integer rqstDtlSno) {
		this.rqstDtlSno = rqstDtlSno;
	}

	public String getVrfDtlCd() {
        return vrfDtlCd;
    }

    public void setVrfDtlCd(String vrfDtlCd) {
        this.vrfDtlCd = vrfDtlCd;
    }

    public String getVrfDtls() {
        return vrfDtls;
    }

    public void setVrfDtls(String vrfDtls) {
        this.vrfDtls = vrfDtls;
    }

	public String getVrfDescn() {
		return vrfDescn;
	}

	public void setVrfDescn(String vrfDescn) {
		this.vrfDescn = vrfDescn;
	}
    
}