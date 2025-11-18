package kr.wise.dq.stnd.service;

import kr.wise.commons.cmm.CommonVo;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class WdqmStwd extends CommonVo  {
	/** 표준단어ID */
	private String stwdId;

	/** 표준단어논리명 */
    private String stwdLnm;

    /** 표준단어물리명 */
    private String stwdPnm;

    /** 영문의미 */
    private String engMean;

    /** 한자명 */
    private String cchNm;

    /** 출처구분 */
    private String orgDs;

    /** 요청번호 */
//    private String rqstNo;
//
//    /** 요청일련번호 */
//    private Integer rqstSno;

    /** 객체설명 */
//    private String objDescn;
//
//    /** 객체버전 */
//    private Integer objVers;
//
//    /** 등록구분코드 */
//    private String regTypCd;
//
//    /** 최초요청일시 */
//    private Date frsRqstDtm;
//
//    /** 최초요청사용자ID */
//    private String frsRqstUserId;
//
//    /** 요청일시 */
//    private Date rqstDtm;
//
//    /** 요청사용자ID */
//    private String rqstUserId;
//
//    /** 승인일시 */
//    private Date aprvDtm;
//
//    /** 승인사용자ID */
//    private String aprvUserId;

    /** 표준명 */
    private String stndNm;

    private String bizDtlCd;

    /** 검색시작일 */
    private String searchBgnDe = "";

    /** 검색종료일 */
    private String searchEndDe = "";

//    private String frsRqstUserNm;
//
//    private String rqstUserNm;
//
//    private String aprvUserNm;
    
    private String wdDcd;
    
    
    private String dmnYn;
    



    public String getDmnYn() {
		return dmnYn;
	}

	public void setDmnYn(String dmnYn) {
		this.dmnYn = dmnYn;
	}

	public String getWdDcd() {
		return wdDcd;
	}

	public void setWdDcd(String wdDcd) {
		this.wdDcd = wdDcd;
	}

//	public String getFrsRqstUserNm() {
//		return frsRqstUserNm;
//	}
//
//	public void setFrsRqstUserNm(String frsRqstUserNm) {
//		this.frsRqstUserNm = frsRqstUserNm;
//	}
//
//	public String getRqstUserNm() {
//		return rqstUserNm;
//	}
//
//	public void setRqstUserNm(String rqstUserNm) {
//		this.rqstUserNm = rqstUserNm;
//	}
//
//	public String getAprvUserNm() {
//		return aprvUserNm;
//	}
//
//	public void setAprvUserNm(String aprvUserNm) {
//		this.aprvUserNm = aprvUserNm;
//	}

	public String getStwdId() {
        return stwdId;
    }

    public void setStwdId(String stwdId) {
        this.stwdId = stwdId;
    }

    public String getStwdLnm() {
        return stwdLnm;
    }

    public void setStwdLnm(String stwdLnm) {
        this.stwdLnm = stwdLnm;
    }

    public String getStwdPnm() {
        return stwdPnm;
    }

    public void setStwdPnm(String stwdPnm) {
        this.stwdPnm = stwdPnm;
    }

    public String getEngMean() {
        return engMean;
    }

    public void setEngMean(String engMean) {
        this.engMean = engMean;
    }

    public String getCchNm() {
        return cchNm;
    }

    public void setCchNm(String cchNm) {
        this.cchNm = cchNm;
    }

    public String getOrgDs() {
        return orgDs;
    }

    public void setOrgDs(String orgDs) {
        this.orgDs = orgDs;
    }

//    @Override
//	public String getRqstNo() {
//        return rqstNo;
//    }
//
//    @Override
//	public void setRqstNo(String rqstNo) {
//        this.rqstNo = rqstNo;
//    }
//
//    @Override
//	public Integer getRqstSno() {
//        return rqstSno;
//    }
//
//    @Override
//	public void setRqstSno(Integer rqstSno) {
//        this.rqstSno = rqstSno;
//    }
//
//    @Override
//	public String getObjDescn() {
//        return objDescn;
//    }
//
//    @Override
//	public void setObjDescn(String objDescn) {
//        this.objDescn = objDescn;
//    }
//
//    @Override
//	public Integer getObjVers() {
//        return objVers;
//    }
//
//    @Override
//	public void setObjVers(Integer objVers) {
//        this.objVers = objVers;
//    }
//
//    @Override
//	public String getRegTypCd() {
//        return regTypCd;
//    }
//
//    @Override
//	public void setRegTypCd(String regTypCd) {
//        this.regTypCd = regTypCd;
//    }
//
//    @Override
//	public Date getFrsRqstDtm() {
//        return frsRqstDtm;
//    }
//
//    @Override
//	public void setFrsRqstDtm(Date frsRqstDtm) {
//        this.frsRqstDtm = frsRqstDtm;
//    }
//
//    @Override
//	public String getFrsRqstUserId() {
//        return frsRqstUserId;
//    }
//
//    @Override
//	public void setFrsRqstUserId(String frsRqstUserId) {
//        this.frsRqstUserId = frsRqstUserId;
//    }
//
//    @Override
//	public Date getRqstDtm() {
//        return rqstDtm;
//    }
//
//    @Override
//	public void setRqstDtm(Date rqstDtm) {
//        this.rqstDtm = rqstDtm;
//    }
//
//    @Override
//	public String getRqstUserId() {
//        return rqstUserId;
//    }
//
//    @Override
//	public void setRqstUserId(String rqstUserId) {
//        this.rqstUserId = rqstUserId;
//    }
//
//    @Override
//	public Date getAprvDtm() {
//        return aprvDtm;
//    }
//
//    @Override
//	public void setAprvDtm(Date aprvDtm) {
//        this.aprvDtm = aprvDtm;
//    }
//
//    @Override
//	public String getAprvUserId() {
//        return aprvUserId;
//    }
//
//    @Override
//	public void setAprvUserId(String aprvUserId) {
//        this.aprvUserId = aprvUserId;
//    }

	/**
	 * @return the stndNm
	 */
	public String getStndNm() {
		return stndNm;
	}

	/**
	 * @param stndNm the stndNm to set
	 */
	public void setStndNm(String stndNm) {
		this.stndNm = stndNm;
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

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamStwd [stwdId=").append(stwdId).append(", stwdLnm=")
				.append(stwdLnm).append(", stwdPnm=").append(stwdPnm)
				.append(", engMean=").append(engMean).append(", cchNm=")
				.append(cchNm).append(", orgDs=").append(orgDs)
				.append(", stndNm=").append(stndNm).append(", bizDtlCd=")
				.append(bizDtlCd).append(", searchBgnDe=").append(searchBgnDe)
				.append(", searchEndDe=").append(searchEndDe)
				.append(", wdDcd=").append(wdDcd).append("]");
		return builder.toString() + super.toString();
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
}