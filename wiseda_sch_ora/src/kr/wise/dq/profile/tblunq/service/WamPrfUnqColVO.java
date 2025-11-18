package kr.wise.dq.profile.tblunq.service;

import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

public class WamPrfUnqColVO extends WamPrfMstrVO {
    private String dbcColNm;
    
    private String dbcColKorNm;

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
		builder.append("WamPrfUnqColVO [dbcColNm=").append(dbcColNm)
				.append(", dbcColKorNm=").append(dbcColKorNm).append("]");
		return builder.toString();
	}
    
	
}