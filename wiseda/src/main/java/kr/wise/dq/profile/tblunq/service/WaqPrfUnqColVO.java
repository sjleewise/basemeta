package kr.wise.dq.profile.tblunq.service;


import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;

public class WaqPrfUnqColVO extends WaqPrfMstrVO {
	private Integer rqstDtlSno; 	// 요청상세일련번호
	
    private String dbcColNm;
    
    private String dbcColKorNm;

    
    public Integer getRqstDtlSno() {
		return rqstDtlSno;
	}

	public void setRqstDtlSno(Integer rqstDtlSno) {
		this.rqstDtlSno = rqstDtlSno;
	}

	public String getDbcColNm() {
        return dbcColNm;
    }

    public void setDbcColNm(String dbcColNm) {
        this.dbcColNm = dbcColNm;
    }

	public String getDbcColKorNm() {
		return dbcColKorNm;
	}

	public void setDbcColKorNm(String dbcColKorNm) {
		this.dbcColKorNm = dbcColKorNm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqPrfUnqColVO [rqstDtlSno=").append(rqstDtlSno)
				.append(", dbcColNm=").append(dbcColNm)
				.append(", dbcColKorNm=").append(dbcColKorNm).append("]");
		return builder.toString();
	}
    
}