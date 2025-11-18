package kr.wise.dq.profile.colefvaana.service;

import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;


public class WaqPrfEfvaAnaVO extends WaqPrfMstrVO {

    private String efvaAnaKndCd;

    private String cdTblDbConnTrgPnm;
    
    private String cdTblDbSchPnm;
    
    private String cdTblDbConnTrgId;
    
    private String cdTblDbSchId;

    private String cdTblDbcTblNm;

    private String cdTblDbcColNm;

    private String cdTblCdIdColNm;

    private String cdTblCdId;

    private String cdTblAdtCndSql;
    
    public String getEfvaAnaKndCd() {
        return efvaAnaKndCd;
    }

    public void setEfvaAnaKndCd(String efvaAnaKndCd) {
        this.efvaAnaKndCd = efvaAnaKndCd;
    }

    public String getCdTblDbConnTrgId() {
        return cdTblDbConnTrgId;
    }

    public void setCdTblDbConnTrgId(String cdTblDbConnTrgId) {
        this.cdTblDbConnTrgId = cdTblDbConnTrgId;
    }

    public String getCdTblDbSchId() {
        return cdTblDbSchId;
    }

    public void setCdTblDbSchId(String cdTblDbSchId) {
        this.cdTblDbSchId = cdTblDbSchId;
    }

    public String getCdTblDbcTblNm() {
        return cdTblDbcTblNm;
    }

    public void setCdTblDbcTblNm(String cdTblDbcTblNm) {
        this.cdTblDbcTblNm = cdTblDbcTblNm;
    }

    public String getCdTblDbcColNm() {
        return cdTblDbcColNm;
    }

    public void setCdTblDbcColNm(String cdTblDbcColNm) {
        this.cdTblDbcColNm = cdTblDbcColNm;
    }

    public String getCdTblCdIdColNm() {
        return cdTblCdIdColNm;
    }

    public void setCdTblCdIdColNm(String cdTblCdIdColNm) {
        this.cdTblCdIdColNm = cdTblCdIdColNm;
    }

    public String getCdTblCdId() {
        return cdTblCdId;
    }

    public void setCdTblCdId(String cdTblCdId) {
        this.cdTblCdId = cdTblCdId;
    }

    public String getCdTblAdtCndSql() {
        return cdTblAdtCndSql;
    }

    public void setCdTblAdtCndSql(String cdTblAdtCndSql) {
        this.cdTblAdtCndSql = cdTblAdtCndSql;
    }
    
	public String getCdTblDbConnTrgPnm() {
		return cdTblDbConnTrgPnm;
	}

	public void setCdTblDbConnTrgPnm(String cdTblDbConnTrgPnm) {
		this.cdTblDbConnTrgPnm = cdTblDbConnTrgPnm;
	}

	public String getCdTblDbSchPnm() {
		return cdTblDbSchPnm;
	}

	public void setCdTblDbSchPnm(String cdTblDbSchPnm) {
		this.cdTblDbSchPnm = cdTblDbSchPnm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqPrfEfvaAnaVO [efvaAnaKndCd=").append(efvaAnaKndCd)
				.append(", cdTblDbConnTrgPnm=").append(cdTblDbConnTrgPnm)
				.append(", cdTblDbSchPnm=").append(cdTblDbSchPnm)
				.append(", cdTblDbConnTrgId=").append(cdTblDbConnTrgId)
				.append(", cdTblDbSchId=").append(cdTblDbSchId)
				.append(", cdTblDbcTblNm=").append(cdTblDbcTblNm)
				.append(", cdTblDbcColNm=").append(cdTblDbcColNm)
				.append(", cdTblCdIdColNm=").append(cdTblCdIdColNm)
				.append(", cdTblCdId=").append(cdTblCdId)
				.append(", cdTblAdtCndSql=").append(cdTblAdtCndSql).append("]");
		return builder.toString();
	}

	
    
}