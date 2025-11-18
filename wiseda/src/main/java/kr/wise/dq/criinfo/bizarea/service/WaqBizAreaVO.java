package kr.wise.dq.criinfo.bizarea.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqBizAreaVO extends CommonVo {
//    private String rqstNo;
//
//    private Integer rqstSno;

    private String bizAreaId;

    private String bizAreaLnm;

    private String bizAreaPnm;

//    private String rqstDcd;
//
//    private String rvwStsCd;
//
//    private String rvwConts;
//
//    private String vrfCd;
//
//    private String vrfRmk;

    private Short bizAreaLvl;

    private String uppBizAreaLnm;

    private String uppBizAreaPnm;

    private String uppBizAreaId;
    
    private String deptNm;
    private String deptId;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;

////    private Date frsRqstDtm;
//
//    private String frsRqstUserId;
//
////    private Date rqstDtm;
//
//    private String rqstUserId;
//
//    private Date aprvDtm;
//
//    private String aprvUserId;

//    public String getRqstNo() {
//        return rqstNo;
//    }
//
//    public void setRqstNo(String rqstNo) {
//        this.rqstNo = rqstNo;
//    }
//
//    public Integer getRqstSno() {
//        return rqstSno;
//    }
//
//    public void setRqstSno(Integer rqstSno) {
//        this.rqstSno = rqstSno;
//    }

    public String getBizAreaId() {
        return bizAreaId;
    }

    public void setBizAreaId(String bizAreaId) {
        this.bizAreaId = bizAreaId;
    }

    public String getBizAreaLnm() {
        return bizAreaLnm;
    }

    public void setBizAreaLnm(String bizAreaLnm) {
        this.bizAreaLnm = bizAreaLnm;
    }

    public String getBizAreaPnm() {
        return bizAreaPnm;
    }

    public void setBizAreaPnm(String bizAreaPnm) {
        this.bizAreaPnm = bizAreaPnm;
    }

//    public String getRqstDcd() {
//        return rqstDcd;
//    }
//
//    public void setRqstDcd(String rqstDcd) {
//        this.rqstDcd = rqstDcd;
//    }
//
//    public String getRvwStsCd() {
//        return rvwStsCd;
//    }
//
//    public void setRvwStsCd(String rvwStsCd) {
//        this.rvwStsCd = rvwStsCd;
//    }
//
//    public String getRvwConts() {
//        return rvwConts;
//    }
//
//    public void setRvwConts(String rvwConts) {
//        this.rvwConts = rvwConts;
//    }
//
//    public String getVrfCd() {
//        return vrfCd;
//    }
//
//    public void setVrfCd(String vrfCd) {
//        this.vrfCd = vrfCd;
//    }
//
//    public String getVrfRmk() {
//        return vrfRmk;
//    }
//
//    public void setVrfRmk(String vrfRmk) {
//        this.vrfRmk = vrfRmk;
//    }

    public Short getBizAreaLvl() {
        return bizAreaLvl;
    }

    public void setBizAreaLvl(Short bizAreaLvl) {
        this.bizAreaLvl = bizAreaLvl;
    }

    public String getUppBizAreaLnm() {
        return uppBizAreaLnm;
    }

    public void setUppBizAreaLnm(String uppBizAreaLnm) {
        this.uppBizAreaLnm = uppBizAreaLnm;
    }

    public String getUppBizAreaPnm() {
        return uppBizAreaPnm;
    }

    public void setUppBizAreaPnm(String uppBizAreaPnm) {
        this.uppBizAreaPnm = uppBizAreaPnm;
    }

    public String getUppBizAreaId() {
        return uppBizAreaId;
    }

    public void setUppBizAreaId(String uppBizAreaId) {
        this.uppBizAreaId = uppBizAreaId;
    }

	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqBizAreaVO [bizAreaId=").append(bizAreaId)
				.append(", bizAreaLnm=").append(bizAreaLnm)
				.append(", bizAreaPnm=").append(bizAreaPnm)
				.append(", bizAreaLvl=").append(bizAreaLvl)
				.append(", deptId=").append(deptId)
				.append(", deptNm=").append(deptNm)
				.append(", uppBizAreaLnm=").append(uppBizAreaLnm)
				.append(", uppBizAreaPnm=").append(uppBizAreaPnm)
				.append(", uppBizAreaId=").append(uppBizAreaId).append("]");
		return builder.toString() + super.toString();
	}

//    public String getObjDescn() {
//        return objDescn;
//    }
//
//    public void setObjDescn(String objDescn) {
//        this.objDescn = objDescn;
//    }
//
//    public Integer getObjVers() {
//        return objVers;
//    }
//
//    public void setObjVers(Integer objVers) {
//        this.objVers = objVers;
//    }
//
//    public String getRegTypCd() {
//        return regTypCd;
//    }
//
//    public void setRegTypCd(String regTypCd) {
//        this.regTypCd = regTypCd;
//    }

//    public Date getFrsRqstDtm() {
//        return frsRqstDtm;
//    }
//
//    public void setFrsRqstDtm(Date frsRqstDtm) {
//        this.frsRqstDtm = frsRqstDtm;
//    }
//
//    public String getFrsRqstUserId() {
//        return frsRqstUserId;
//    }
//
//    public void setFrsRqstUserId(String frsRqstUserId) {
//        this.frsRqstUserId = frsRqstUserId;
//    }
//
//    public Date getRqstDtm() {
//        return rqstDtm;
//    }
//
//    public void setRqstDtm(Date rqstDtm) {
//        this.rqstDtm = rqstDtm;
//    }
//
//    public String getRqstUserId() {
//        return rqstUserId;
//    }
//
//    public void setRqstUserId(String rqstUserId) {
//        this.rqstUserId = rqstUserId;
//    }
//
//    public Date getAprvDtm() {
//        return aprvDtm;
//    }
//
//    public void setAprvDtm(Date aprvDtm) {
//        this.aprvDtm = aprvDtm;
//    }
//
//    public String getAprvUserId() {
//        return aprvUserId;
//    }
//
//    public void setAprvUserId(String aprvUserId) {
//        this.aprvUserId = aprvUserId;
//    }

	
    
    
}