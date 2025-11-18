package kr.wise.dq.profile.colefvaana.service;

import java.util.ArrayList;

import kr.wise.dq.metadmn.service.MetaDmnCdValItfVO;
import kr.wise.dq.metadmn.service.MetaDmnItfVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;


public class WamPrfEfvaAnaVO extends WamPrfMstrVO {
    private String efvaAnaKndCd;

    private String cdTblDbConnTrgId;

    private String cdTblDbSchId;

    private String cdTblDbcTblNm;

    private String cdTblDbcColNm;

    private String cdTblCdIdColNm;

    private String cdTblCdId;

    private String cdTblAdtCndSql;
    
    private String cdTblDbConnTrgLdm;
    
    private String cdTblDbConnTrgPdm;
    
    private String cdTblDbSchLdm;
    
    private String cdTblDbSchPdm;
    
    private String cdTblDbcTblKorNm;
    
    private String cdTblDbcColKorNm;

    private String cdTblCdIdColKorNm;
    
    //사용자정의 유효값
    private ArrayList<WamPrfEfvaUserDfVO> wamPrfEfvaUserDfVO;
    
    //메타도메인정보
    private MetaDmnItfVO metaDmnItfVO;
    
    //메타 코드유효값
    private ArrayList<MetaDmnCdValItfVO> metaDmnCdValItfVO;

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


	public String getCdTblDbConnTrgLdm() {
		return cdTblDbConnTrgLdm;
	}

	public void setCdTblDbConnTrgLdm(String cdTblDbConnTrgLdm) {
		this.cdTblDbConnTrgLdm = cdTblDbConnTrgLdm;
	}

	public String getCdTblDbConnTrgPdm() {
		return cdTblDbConnTrgPdm;
	}

	public void setCdTblDbConnTrgPdm(String cdTblDbConnTrgPdm) {
		this.cdTblDbConnTrgPdm = cdTblDbConnTrgPdm;
	}

	public String getCdTblDbSchLdm() {
		return cdTblDbSchLdm;
	}

	public void setCdTblDbSchLdm(String cdTblDbSchLdm) {
		this.cdTblDbSchLdm = cdTblDbSchLdm;
	}

	public String getCdTblDbSchPdm() {
		return cdTblDbSchPdm;
	}

	public void setCdTblDbSchPdm(String cdTblDbSchPdm) {
		this.cdTblDbSchPdm = cdTblDbSchPdm;
	}

	public String getCdTblDbcTblKorNm() {
		return cdTblDbcTblKorNm;
	}

	public void setCdTblDbcTblKorNm(String cdTblDbcTblKorNm) {
		this.cdTblDbcTblKorNm = cdTblDbcTblKorNm;
	}

	public String getCdTblDbcColKorNm() {
		return cdTblDbcColKorNm;
	}

	public void setCdTblDbcColKorNm(String cdTblDbcColKorNm) {
		this.cdTblDbcColKorNm = cdTblDbcColKorNm;
	}

	public String getCdTblCdIdColKorNm() {
		return cdTblCdIdColKorNm;
	}

	public void setCdTblCdIdColKorNm(String cdTblCdIdColKorNm) {
		this.cdTblCdIdColKorNm = cdTblCdIdColKorNm;
	}

	public ArrayList<WamPrfEfvaUserDfVO> getWamPrfEfvaUserDfVO() {
		return wamPrfEfvaUserDfVO;
	}

	public void setWamPrfEfvaUserDfVO(
			ArrayList<WamPrfEfvaUserDfVO> wamPrfEfvaUserDfVO) {
		this.wamPrfEfvaUserDfVO = wamPrfEfvaUserDfVO;
	}

	public MetaDmnItfVO getMetaDmnItfVO() {
		return metaDmnItfVO;
	}

	public void setMetaDmnItfVO(MetaDmnItfVO metaDmnItfVO) {
		this.metaDmnItfVO = metaDmnItfVO;
	}

	public ArrayList<MetaDmnCdValItfVO> getMetaDmnCdValItfVO() {
		return metaDmnCdValItfVO;
	}

	public void setMetaDmnCdValItfVO(ArrayList<MetaDmnCdValItfVO> metaDmnCdValItfVO) {
		this.metaDmnCdValItfVO = metaDmnCdValItfVO;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamPrfEfvaAnaVO [efvaAnaKndCd=").append(efvaAnaKndCd)
				.append(", cdTblDbConnTrgId=").append(cdTblDbConnTrgId)
				.append(", cdTblDbSchId=").append(cdTblDbSchId)
				.append(", cdTblDbcTblNm=").append(cdTblDbcTblNm)
				.append(", cdTblDbcColNm=").append(cdTblDbcColNm)
				.append(", cdTblCdIdColNm=").append(cdTblCdIdColNm)
				.append(", cdTblCdId=").append(cdTblCdId)
				.append(", cdTblAdtCndSql=").append(cdTblAdtCndSql)
				.append(", cdTblDbConnTrgLdm=").append(cdTblDbConnTrgLdm)
				.append(", cdTblDbSchLdm=").append(cdTblDbSchLdm)
				.append(", cdTblDbcTblKnm=").append(cdTblDbcTblKorNm)
				.append(", cdTblDbcColKnm=").append(cdTblDbcColKorNm)
				.append(", cdTblCdIdColKnm=").append(cdTblCdIdColKorNm)
				.append("]");
		return builder.toString();
	}

	
    
}