package kr.wise.dq.vrfcrule.service; 

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

public class VrfcRuleVO extends CommonVo{
    private String ruleRelId;

    private String prfKndCd;
    
    private String prfKndCdNm;

    private String vrfcId;
    
    private String vrfcNm;
    
    private String vrfcRule;
    
    private String vrfcDescn;
    
    private String vrfcTyp;

    private String dbConnTrgId;

    private String dbSchId;

    private String dbcTblNm;
    
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
    
    private String cdSql;
                                                    
	private String cdClsColNm;                                                                             
    private String cdClsNmColNm;                                   
    private String cdIdColNm;                                  
    private String cdNmColNm;          
    
    private String cdTypCd;    
    
    
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

	public String getRuleRelId() {
		return ruleRelId;
	}

	public void setRuleRelId(String ruleRelId) {
		this.ruleRelId = ruleRelId;
	}

	public String getPrfKndCd() {
		return prfKndCd;
	}

	public void setPrfKndCd(String prfKndCd) {
		this.prfKndCd = prfKndCd;
	}

	public String getPrfKndCdNm() {
		return prfKndCdNm;
	}

	public void setPrfKndCdNm(String prfKndCdNm) {
		this.prfKndCdNm = prfKndCdNm;
	}

	public String getVrfcId() {
		return vrfcId;
	}

	public void setVrfcId(String vrfcId) {
		this.vrfcId = vrfcId;
	}

	public String getVrfcNm() {
		return vrfcNm;
	}

	public void setVrfcNm(String vrfcNm) {
		this.vrfcNm = vrfcNm;
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

	public String getDbcColNm() {
		return dbcColNm;
	}

	public void setDbcColNm(String dbcColNm) {
		this.dbcColNm = dbcColNm;
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

	public String getTblColGb() {
		return tblColGb;
	}

	public void setTblColGb(String tblColGb) {
		this.tblColGb = tblColGb;
	}

	public String getDbmsTypCd() {
		return dbmsTypCd;
	}

	public void setDbmsTypCd(String dbmsTypCd) {
		this.dbmsTypCd = dbmsTypCd;
	}

	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}

	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}

	public String getDbSchPnm() {
		return dbSchPnm;
	}

	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
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

	public ArrayList<WamPrfUnqColVO> getWamPrfUnqColVO() {
		return wamPrfUnqColVO;
	}

	public void setWamPrfUnqColVO(ArrayList<WamPrfUnqColVO> wamPrfUnqColVO) {
		this.wamPrfUnqColVO = wamPrfUnqColVO;
	}

	public String getVrfcRule() {
		return vrfcRule;
	}

	public void setVrfcRule(String vrfcRule) {
		this.vrfcRule = vrfcRule;
	}

	public String getVrfcDescn() {
		return vrfcDescn;
	}

	public void setVrfcDescn(String vrfcDescn) {
		this.vrfcDescn = vrfcDescn;
	}

	public String getVrfcTyp() {
		return vrfcTyp;
	}

	public void setVrfcTyp(String vrfcTyp) {
		this.vrfcTyp = vrfcTyp;
	}

	public String getCdSql() {
		return cdSql;
	}

	public void setCdSql(String cdSql) {
		this.cdSql = cdSql;
	}

	public String getCdClsColNm() {
		return cdClsColNm;
	}

	public void setCdClsColNm(String cdClsColNm) {
		this.cdClsColNm = cdClsColNm;
	}

	public String getCdClsNmColNm() {
		return cdClsNmColNm;
	}

	public void setCdClsNmColNm(String cdClsNmColNm) {
		this.cdClsNmColNm = cdClsNmColNm;
	}

	public String getCdIdColNm() {
		return cdIdColNm;
	}

	public void setCdIdColNm(String cdIdColNm) {
		this.cdIdColNm = cdIdColNm;
	}

	public String getCdNmColNm() {
		return cdNmColNm;
	}

	public void setCdNmColNm(String cdNmColNm) {
		this.cdNmColNm = cdNmColNm;
	}

	public String getCdTypCd() {
		return cdTypCd;
	}

	public void setCdTypCd(String cdTypCd) {
		this.cdTypCd = cdTypCd;
	}
    
    
	
}