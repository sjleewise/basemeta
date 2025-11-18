package kr.wise.commons.rqstmst.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqMstr extends CommonVo{
    private String rqstNo;		//요청번호

    private String rqstNm;		//요청명

    private String bizDcd;		//업무구분코드

    private String bizDtlCd;	//업무상세구분코드

    private String rqstStepCd;  //요청단계코드
    
    private String rqstStepCdNm;  //요청단계코드명

    private String rqstResn;	//요청사유

    private WaaBizInfo bizInfo;  //요청업무별 정보(테이블명, 컬럼명, url)

    /** yeonho */
    private String aprvStepLvl; //결재단계명(내 요청목록조회)

    /** yeonho */
    private String searchBgnDe; //검색 시작일자

    /** yeonho */
    private String searchEndDe; //검색 종료일자

    private String bizDcdNm; //업무구분코드 한글명

    private Integer rqstTmpCount; //내 임시저장 카운트
    
    private Integer rqstMyCount; //내 결재요청건수 카운트

    private Integer rqstToDoCount; //내 결재목록건수 카운트

    private Integer aprLvl; //결재레벨
    
    private String aprvStatus; //결재상태


    private String searchObj; //검색어
    
    private String dbSchId;
    private String dbSchPnm; 
    
    private String aprvUserNm; //결재자명
    private String rqstUserNm; //요청자명 
    
    private String dtSrchDcd; //일자검색구분
    
    private String orgCd; //기관코드
    
    private String subjClDcd; //중앙부처,지차체구분
    
    private String gapStsCd; //범정부GAP상태코드 

    private String sendStsCd; //범정부전송상태코드 
    
    private int saveFlag;
    private int selRow;
    
    private float agrNv;
    private float glNv;
    
    private String govYn; // 범정부연계 여부
    

    
	public String getSendStsCd() {
		return sendStsCd;
	}

	public void setSendStsCd(String sendStsCd) {
		this.sendStsCd = sendStsCd;
	}

	public String getAprvStatus() {
		return aprvStatus;
	}

	public void setAprvStatus(String aprvStatus) {
		this.aprvStatus = aprvStatus;
	}

	public Integer getRqstTmpCount() {
		return rqstTmpCount;
	}

	public void setRqstTmpCount(Integer rqstTmpCount) {
		this.rqstTmpCount = rqstTmpCount;
	}

	public Integer getRqstMyCount() {
		return rqstMyCount;
	}

	public void setRqstMyCount(Integer rqstMyCount) {
		this.rqstMyCount = rqstMyCount;
	}

	public Integer getRqstToDoCount() {
		return rqstToDoCount;
	}

	public void setRqstToDoCount(Integer rqstToDoCount) {
		this.rqstToDoCount = rqstToDoCount;
	}

	public String getBizDcdNm() {
		return bizDcdNm;
	}

	public void setBizDcdNm(String bizDcdNm) {
		this.bizDcdNm = bizDcdNm;
	}

	public String getSearchBgnDe() {
		return searchBgnDe;
	}

	public void setSearchBgnDe(String searchBgnDe) {
		this.searchBgnDe = searchBgnDe;
	}

	public String getSearchEndDe() {
		return searchEndDe;
	}

	public void setSearchEndDe(String searchEndDe) {
		this.searchEndDe = searchEndDe;
	}

	public String getAprvStepLvl() {
		return aprvStepLvl;
	}

	public void setAprvStepLvl(String aprvStepLvl) {
		this.aprvStepLvl = aprvStepLvl;
	}

	@Override
	public String getRqstNo() {
        return rqstNo;
    }

    @Override
	public void setRqstNo(String rqstNo) {
        this.rqstNo = rqstNo;
    }

    public String getRqstNm() {
        return rqstNm;
    }

    public void setRqstNm(String rqstNm) {
        this.rqstNm = rqstNm;
    }

    public String getBizDcd() {
        return bizDcd;
    }

    public void setBizDcd(String bizDcd) {
        this.bizDcd = bizDcd;
    }

    public String getRqstStepCd() {
        return rqstStepCd;
    }

    public void setRqstStepCd(String rqstStepCd) {
        this.rqstStepCd = rqstStepCd;
    }

    public String getRqstStepCdNm() {
		return rqstStepCdNm;
	}

	public void setRqstStepCdNm(String rqstStepCdNm) {
		this.rqstStepCdNm = rqstStepCdNm;
	}

	public String getRqstResn() {
        return rqstResn;
    }

    public void setRqstResn(String rqstResn) {
        this.rqstResn = rqstResn;
    }
	public Integer getAprLvl() {
		return aprLvl;
	}

	public void setAprLvl(Integer aprLvl) {
		this.aprLvl = aprLvl;
	}

	/**
	 * @return the bizDtlCd
	 */
	public String getBizDtlCd() {
		return bizDtlCd;
	}

	/**
	 * @param bizDtlCd the bizDtlCd to set
	 */
	public void setBizDtlCd(String bizDtlCd) {
		this.bizDtlCd = bizDtlCd;
	}

	

	/**
	 * @return the bizInfo
	 */
	public WaaBizInfo getBizInfo() {
		return bizInfo;
	}

	/**
	 * @param bizInfo the bizInfo to set
	 */
	public void setBizInfo(WaaBizInfo bizInfo) {
		this.bizInfo = bizInfo;
	}

	public String getSearchObj() {
		return searchObj;
	}

	public void setSearchObj(String searchObj) {
		this.searchObj = searchObj;
	}
    
	public String getDbSchId() {
		return dbSchId;
	}

	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}

	public String getDbSchPnm() {
		return dbSchPnm;
	}

	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
	}

	public String getAprvUserNm() {
		return aprvUserNm;
	}

	public void setAprvUserNm(String aprvUserNm) {
		this.aprvUserNm = aprvUserNm;
	}

	public String getRqstUserNm() {
		return rqstUserNm;
	}

	public void setRqstUserNm(String rqstUserNm) {
		this.rqstUserNm = rqstUserNm;
	}

	public String getDtSrchDcd() {
		return dtSrchDcd; 
	}

	public void setDtSrchDcd(String dtSrchDcd) {
		this.dtSrchDcd = dtSrchDcd;
	}

	public String getOrgCd() {
		return orgCd;
	}

	public void setOrgCd(String orgCd) { 
		this.orgCd = orgCd;
	}
    
	public String getSubjClDcd() {
		return subjClDcd;
	}

	public void setSubjClDcd(String subjClDcd) {
		this.subjClDcd = subjClDcd;
	}
    
	public String getGapStsCd() {
		return gapStsCd;
	}

	public void setGapStsCd(String gapStsCd) {
		this.gapStsCd = gapStsCd;
	}

	public int getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(int saveFlag) {
		this.saveFlag = saveFlag;
	}

	public int getSelRow() {
		return selRow;
	}

	public void setSelRow(int selRow) {
		this.selRow = selRow;
	}

	public float getAgrNv() {
		return agrNv;
	}

	public void setAgrNv(float agrNv) {
		this.agrNv = agrNv;
	}

	public float getGlNv() {
		return glNv;
	}

	public void setGlNv(float glNv) {
		this.glNv = glNv;
	}
	

	public String getGovYn() {
		return govYn;
	}

	public void setGovYn(String govYn) {
		this.govYn = govYn;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqMstr [rqstNo=").append(rqstNo).append(", rqstNm=")
				.append(rqstNm).append(", bizDcd=").append(bizDcd)
				.append(", bizDtlCd=").append(bizDtlCd).append(", rqstStepCd=")
				.append(rqstStepCd).append(", rqstResn=").append(rqstResn)
				.append(", bizInfo=").append(bizInfo)
				.append(", searchBgnDe=").append(searchBgnDe)
				.append(", searchEndDe=").append(searchEndDe)
				.append(", searchObj=").append(searchObj)
				.append(", bizDcdNm=").append(bizDcdNm)
				.append(", rqstMyCount=").append(rqstMyCount)
				.append(", rqstToDoCount=").append(rqstToDoCount)
				.append(", aprLvl=").append(aprLvl).append(", aprvStatus=")
				.append(", aprvUserNm=").append(aprvUserNm).append(", rqstUserNm=").append(rqstUserNm)
				.append(", agrNv=").append(agrNv).append(", glNv=").append(glNv)
				.append(aprvStatus).append("]");
		return builder.toString()+super.toString();
	}
	

}