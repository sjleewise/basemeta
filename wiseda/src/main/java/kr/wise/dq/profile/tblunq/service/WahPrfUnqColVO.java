package kr.wise.dq.profile.tblunq.service;

import kr.wise.dq.profile.mstr.service.WahPrfMstrVO;

public class WahPrfUnqColVO extends WahPrfMstrVO {
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
    
}