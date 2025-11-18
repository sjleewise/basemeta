package kr.wise.dq.profile.mstr.service;

import java.util.ArrayList;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.coldtfrmana.service.WamPrfDtfrmAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaVO;
import kr.wise.dq.profile.colptrana.service.WamPrfPtrAnaVO;
import kr.wise.dq.profile.colrngana.service.WamPrfRngAnaVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelTblVO;
import kr.wise.dq.profile.tblunq.service.WamPrfUnqColVO;

public class WamPrfMstrVO extends CommonVo{
    private String prfId;

    private String prfKndCd;
    
    private String prfKndCdNm;

    private String prfNm;

    private String dbConnTrgId;

    private String dbSchId;

    private String dbcTblNm;

    private String objNm;
    
    private String dbcColNm;

    private String adtCndSql;

    private String hntSql;

    private String useYn;
    
    //테이블, 컬럼 프로파일 구분
    private String tblColGb;
    
    //sql 생성
    private String dbmsTypCd;
    
    private String dbConnTrgPnm;
    
    private String dbSchPnm;

    //컬럼분석
    private WamPrfColAnaVO  wamPrfColAnaVO;
    
    //유효값분석
    private WamPrfEfvaAnaVO  wamPrfEfvaAnaVO;
    
    //날짜형식분석
    private WamPrfDtfrmAnaVO   wamPrfDtfrmAnaVO;
    
    //범위분석
    private WamPrfRngAnaVO   wamPrfRngAnaVO;
    
    //패턴분석
    private ArrayList<WamPrfPtrAnaVO> wamPrfPtrAnaVO;
    
    //관계분석
    private WamPrfRelTblVO wamPrfRelTblVO;
    //관계분석 컬럼
    private ArrayList<WamPrfRelColVO> wamPrfRelColVO;
    
    //중복분석
    private ArrayList<WamPrfUnqColVO> wamPrfUnqColVO;
    
    //업무규칙전환
    private String brNm;
    
    //품질지표 id
    private String dqiId;
    //품질지표명
    private String dqiLnm;

	private String errCntSql;
    
    private String prfcScript;
    
    private String stddevVal;
    
    private String varianceVal;
    
    private String avgVal;
    
    private String unqCnt;
    
    private String minCntVal;
    
    private String maxCntVal;
    
    private String dataType;
    
    private String bizAreaLnm;
    
    
    
    
    public String getBizAreaLnm() {
		return bizAreaLnm;
	}

	public void setBizAreaLnm(String bizAreaLnm) {
		this.bizAreaLnm = bizAreaLnm;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getStddevVal() {
		return stddevVal;
	}

	public void setStddevVal(String stddevVal) {
		this.stddevVal = stddevVal;
	}

	public String getVarianceVal() {
		return varianceVal;
	}

	public void setVarianceVal(String varianceVal) {
		this.varianceVal = varianceVal;
	}

	public String getAvgVal() {
		return avgVal;
	}

	public void setAvgVal(String avgVal) {
		this.avgVal = avgVal;
	}

	public String getUnqCnt() {
		return unqCnt;
	}

	public void setUnqCnt(String unqCnt) {
		this.unqCnt = unqCnt;
	}

	public String getMinCntVal() {
		return minCntVal;
	}

	public void setMinCntVal(String minCntVal) {
		this.minCntVal = minCntVal;
	}

	public String getMaxCntVal() {
		return maxCntVal;
	}

	public void setMaxCntVal(String maxCntVal) {
		this.maxCntVal = maxCntVal;
	}

	public String getPrfcScript() {
		return prfcScript;
	}

	public void setPrfcScript(String prfcScript) {
		this.prfcScript = prfcScript;
	}

	public String getPrfId() {
        return prfId;
    }

    public void setPrfId(String prfId) {
        this.prfId = prfId;
    }

    public String getPrfKndCd() {
        return prfKndCd;
    }

    public void setPrfKndCd(String prfKndCd) {
        this.prfKndCd = prfKndCd;
    }

    public String getPrfNm() {
        return prfNm;
    }

    public void setPrfNm(String prfNm) {
        this.prfNm = prfNm;
    }

    public String getDbConnTrgId() {
        return dbConnTrgId;
    }

    public void setDbConnTrgId(String dbConnTrgId) {
        this.dbConnTrgId = dbConnTrgId;
    }

    public String getDbSchId() {
        return dbSchId;
    }

    public void setDbSchId(String dbSchId) {
        this.dbSchId = dbSchId;
    }

    public String getDbcTblNm() {
        return dbcTblNm;
    }

    public void setDbcTblNm(String dbcTblNm) {
        this.dbcTblNm = dbcTblNm;
    }

    public String getObjNm() {
		return objNm;
	}

	public void setObjNm(String objNm) {
		this.objNm = objNm;
	}

	public String getAdtCndSql() {
        return adtCndSql;
    }

    public void setAdtCndSql(String adtCndSql) {
        this.adtCndSql = adtCndSql;
    }

    public String getHntSql() {
        return hntSql;
    }

    public void setHntSql(String hntSql) {
        this.hntSql = hntSql;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
    
	public String getBrNm() {
		return brNm;
	}

	public void setBrNm(String brNm) {
		this.brNm = brNm;
	}

	public String getTblColGb() {
		return tblColGb;
	}

	public void setTblColGb(String tblColGb) {
		this.tblColGb = tblColGb;
	}

	public WamPrfColAnaVO getWamPrfColAnaVO() {
		return wamPrfColAnaVO;
	}

	public void setWamPrfColAnaVO(WamPrfColAnaVO wamPrfColAnaVO) {
		this.wamPrfColAnaVO = wamPrfColAnaVO;
	}

	public WamPrfEfvaAnaVO getWamPrfEfvaAnaVO() {
		return wamPrfEfvaAnaVO;
	}

	public void setWamPrfEfvaAnaVO(WamPrfEfvaAnaVO wamPrfEfvaAnaVO) {
		this.wamPrfEfvaAnaVO = wamPrfEfvaAnaVO;
	}
	
	public WamPrfDtfrmAnaVO getWamPrfDtfrmAnaVO() {
		return wamPrfDtfrmAnaVO;
	}

	public void setWamPrfDtfrmAnaVO(WamPrfDtfrmAnaVO wamPrfDtfrmAnaVO) {
		this.wamPrfDtfrmAnaVO = wamPrfDtfrmAnaVO;
	}

	public WamPrfRngAnaVO getWamPrfRngAnaVO() {
		return wamPrfRngAnaVO;
	}

	public void setWamPrfRngAnaVO(WamPrfRngAnaVO wamPrfRngAnaVO) {
		this.wamPrfRngAnaVO = wamPrfRngAnaVO;
	}
	
	public ArrayList<WamPrfPtrAnaVO> getWamPrfPtrAnaVO() {
		return wamPrfPtrAnaVO;
	}

	public void setWamPrfPtrAnaVO(ArrayList<WamPrfPtrAnaVO> wamPrfPtrAnaVO) {
		this.wamPrfPtrAnaVO = wamPrfPtrAnaVO;
	}

	public ArrayList<WamPrfUnqColVO> getWamPrfUnqColVO() {
		return wamPrfUnqColVO;
	}

	public void setWamPrfUnqColVO(ArrayList<WamPrfUnqColVO> wamPrfUnqColVO) {
		this.wamPrfUnqColVO = wamPrfUnqColVO;
	}

	public String getDbcColNm() {
		return dbcColNm;
	}

	public void setDbcColNm(String dbcColNm) {
		this.dbcColNm = dbcColNm;
	}

	public WamPrfRelTblVO getWamPrfRelTblVO() {
		return wamPrfRelTblVO;
	}

	public void setWamPrfRelTblVO(WamPrfRelTblVO wamPrfRelTblVO) {
		this.wamPrfRelTblVO = wamPrfRelTblVO;
	}

	public ArrayList<WamPrfRelColVO> getWamPrfRelColVO() {
		return wamPrfRelColVO;
	}

	public void setWamPrfRelColVO(ArrayList<WamPrfRelColVO> wamPrfRelColVO) {
		this.wamPrfRelColVO = wamPrfRelColVO;
	}

	public String getDbmsTypCd() {
		return dbmsTypCd;
	}

	public void setDbmsTypCd(String dbmsTypCd) {
		this.dbmsTypCd = dbmsTypCd;
	}

	public String getDbSchPnm() {
		return dbSchPnm;
	}

	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
	}
	
	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}

	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}

	public String getPrfKndCdNm() {
		return prfKndCdNm;
	}

	public void setPrfKndCdNm(String prfKndCdNm) {
		this.prfKndCdNm = prfKndCdNm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamPrfMstrVO [prfId=")
				.append(prfId)
				.append(", prfKndCd=")
				.append(prfKndCd)
				.append(", prfKndCdNm=")
				.append(prfKndCdNm)
				.append(", prfNm=")
				.append(prfNm)
				.append(", dbConnTrgId=")
				.append(dbConnTrgId)
				.append(", dbSchId=")
				.append(dbSchId)
				.append(", dbcTblNm=")
				.append(dbcTblNm)
				.append(", objNm=")
				.append(objNm)
				.append(", dbcColNm=")
				.append(dbcColNm)
				.append(", adtCndSql=")
				.append(adtCndSql)
				.append(", hntSql=")
				.append(hntSql)
				.append(", useYn=")
				.append(useYn)
				.append(", tblColGb=")
				.append(tblColGb)
				.append(", dbmsTypCd=")
				.append(dbmsTypCd)
				.append(", dbConnTrgPnm=")
				.append(dbConnTrgPnm)
				.append(", dbSchPnm=")
				.append(dbSchPnm)
				.append("]");
		return builder.toString();
	}

	public String getDqiId() {
		return dqiId;
	}

	public void setDqiId(String dqiId) {
		this.dqiId = dqiId;
	}

    public String getDqiLnm() {
		return dqiLnm;
	}

	public void setDqiLnm(String dqiLnm) {
		this.dqiLnm = dqiLnm;
	}
	
	public String getErrCntSql() {
		return errCntSql;
	}

	public void setErrCntSql(String errCntSql) {
		this.errCntSql = errCntSql;
	}
	
}