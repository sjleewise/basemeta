package kr.wise.meta.stnd.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqStwd extends CommonVo {
//    private String rqstNo;

//    private Integer rqstSno;

	/** 표준단어ID */
    private String stwdId;

    /** 표준단어논리명 */
    private String stwdLnm;

    /** 표준단어물리명 */
    private String stwdPnm;

//    private String rqstDcd;

//    private String rvwStsCd;

//    private String rvwConts;

//    private String vrfCd;

//    private String vrfRmk;

    /** 영문의미 */
    private String engMean;

    /** 한자명 */
    private String cchNm;

    /** 출처구분 */
    private String orgDs;

//    private String objDescn;

//    private Integer objVers;

//    private String regTypCd;

//    private Date frsRqstDtm;

//    private String frsRqstUserId;

//    private Date rqstDtm;

//    private String rqstUserId;

//    private Date aprvDtm;

//    private String aprvUserId;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqStwd [stwdId=").append(stwdId).append(", stwdLnm=")
				.append(stwdLnm).append(", stwdPnm=").append(stwdPnm)
				.append(", engMean=").append(engMean).append(", cchNm=")
				.append(cchNm).append(", orgDs=").append(orgDs)
				.append(", wdDcd=").append(wdDcd).append("]");
		return builder.toString() + super.toString();
	}

}