package kr.wise.advisor.prepare.dataset.service;

import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;

public class WadDataSet extends AnaTrgTblVO {
    private String daseId;

    private String rqstNo;

    private String daseLnm;

    private String dasePnm;

    private String dbSchId;

    private String dbTblNm;

    private String summaryYn;


    public String getDaseId() {
        return daseId;
    }

    public void setDaseId(String daseId) {
        this.daseId = daseId;
    }

    public String getRqstNo() {
        return rqstNo;
    }

    public void setRqstNo(String rqstNo) {
        this.rqstNo = rqstNo;
    }

    public String getDaseLnm() {
        return daseLnm;
    }

    public void setDaseLnm(String daseLnm) {
        this.daseLnm = daseLnm;
    }

    public String getDasePnm() {
        return dasePnm;
    }

    public void setDasePnm(String dasePnm) {
        this.dasePnm = dasePnm;
    }

    public String getDbSchId() {
        return dbSchId;
    }

    public void setDbSchId(String dbSchId) {
        this.dbSchId = dbSchId;
    }

    public String getDbTblNm() {
        return dbTblNm;
    }

    public void setDbTblNm(String dbTblNm) {
        this.dbTblNm = dbTblNm;
    }

	/** insomnia */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WadDataSet [daseId=").append(daseId).append(", rqstNo=").append(rqstNo).append(", daseLnm=")
				.append(daseLnm).append(", dasePnm=").append(dasePnm).append(", dbSchId=").append(dbSchId)
				.append(", dbTblNm=").append(dbTblNm).append("]");
		return builder.toString();
	}

	/**
	 * @return the summaryYn
	 */
	public String getSummaryYn() {
		return summaryYn;
	}

	/**
	 * @param summaryYn the summaryYn to set
	 */
	public void setSummaryYn(String summaryYn) {
		this.summaryYn = summaryYn;
	}

}