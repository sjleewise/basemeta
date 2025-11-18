package kr.wise.meta.ddl.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqDdlSeq extends CommonVo {
    private String rqstNo;

    private Integer rqstSno;

    private String ddlSeqId;

    private String ddlSeqPnm;

    private String ddlSeqLnm;

//    private String rqstDcd;
//
//    private String rvwStsCd;
//
//    private String rvwConts;
//
//    private String vrfCd;
//
//    private String vrfRmk;

    private String dbConnTrgPnm;

    private String dbSchId;

    private String dbSchPnm;

    private String incby;

    private String strtwt;

    private String maxval;

    private String minval;

    private String cycYn;

    private String cacheSz;

    private String ordYn;

    private String nmRlTypCd;

    private String usTypCd;
    
    private String nmRlTypNm;

    private String usTypNm;

    private String pdmTblPnm;

    private String pdmColPnm;

    private String l1cdPnm;

    private String l3cdPnm;

    private String grtLst;
    
    private String accDbmsYn;   //계정계여부

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//
//    private Date frsRqstDtm;
//
//    private String frsRqstUserId;
//
//    private Date rqstDtm;
//
//    private String rqstUserId;
//
//    private Date aprvDtm;
//
//    private String aprvUserId;

    private String scrtInfo;
    
    private String ddlTrgDcd;
    private String srcDbSchId;
    private String srcDdlSeqId;

    //DDL 적용요청코드, 일자
    private String aplReqTypCd;
    private String aplReqdt;
    
    private String prjMngNo;
    private String srMngNo;
    private String cudYn;
    
    private String pckgNm;
    private String seqClasCd;
    private String seqInitCd;
    private String bizIfnc;
    
    /** 요청사유 */
    private String rqstResn;
    
    
    
    
    public String getRqstResn() {
		return rqstResn;
	}

	public void setRqstResn(String rqstResn) {
		this.rqstResn = rqstResn;
	}

	public String getPckgNm() {
		return pckgNm;
	}

	public void setPckgNm(String pckgNm) {
		this.pckgNm = pckgNm;
	}

	public String getSeqClasCd() {
		return seqClasCd;
	}

	public void setSeqClasCd(String seqClasCd) {
		this.seqClasCd = seqClasCd;
	}

	public String getSeqInitCd() {
		return seqInitCd;
	}

	public void setSeqInitCd(String seqInitCd) {
		this.seqInitCd = seqInitCd;
	}

	public String getBizIfnc() {
		return bizIfnc;
	}

	public void setBizIfnc(String bizIfnc) {
		this.bizIfnc = bizIfnc;
	}

	public String getCudYn() {
		return cudYn;
	}

	public void setCudYn(String cudYn) {
		this.cudYn = cudYn;
	}

	public String getRqstNo() {
        return rqstNo;
    }

    public void setRqstNo(String rqstNo) {
        this.rqstNo = rqstNo;
    }

    public Integer getRqstSno() {
        return rqstSno;
    }

    public void setRqstSno(Integer rqstSno) {
        this.rqstSno = rqstSno;
    }

    public String getDdlSeqId() {
        return ddlSeqId;
    }

    public void setDdlSeqId(String ddlSeqId) {
        this.ddlSeqId = ddlSeqId;
    }

    public String getDdlSeqPnm() {
        return ddlSeqPnm;
    }

    public void setDdlSeqPnm(String ddlSeqPnm) {
        this.ddlSeqPnm = ddlSeqPnm;
    }

    public String getDdlSeqLnm() {
        return ddlSeqLnm;
    }

    public void setDdlSeqLnm(String ddlSeqLnm) {
        this.ddlSeqLnm = ddlSeqLnm;
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

    public String getDbConnTrgPnm() {
        return dbConnTrgPnm;
    }

    public void setDbConnTrgPnm(String dbConnTrgPnm) {
        this.dbConnTrgPnm = dbConnTrgPnm;
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

    public String getIncby() {
        return incby;
    }

    public void setIncby(String incby) {
        this.incby = incby;
    }

    public String getStrtwt() {
        return strtwt;
    }

    public void setStrtwt(String strtwt) {
        this.strtwt = strtwt;
    }

    public String getMaxval() {
        return maxval;
    }

    public void setMaxval(String maxval) {
        this.maxval = maxval;
    }

    public String getMinval() {
        return minval;
    }

    public void setMinval(String minval) {
        this.minval = minval;
    }

    public String getCycYn() {
        return cycYn;
    }

    public void setCycYn(String cycYn) {
        this.cycYn = cycYn;
    }

    public String getCacheSz() {
        return cacheSz;
    }

    public void setCacheSz(String cacheSz) {
        this.cacheSz = cacheSz;
    }

    public String getOrdYn() {
        return ordYn;
    }

    public void setOrdYn(String ordYn) {
        this.ordYn = ordYn;
    }

    public String getNmRlTypCd() {
        return nmRlTypCd;
    }

    public void setNmRlTypCd(String nmRlTypCd) {
        this.nmRlTypCd = nmRlTypCd;
    }

    public String getUsTypCd() {
        return usTypCd;
    }

    public void setUsTypCd(String usTypCd) {
        this.usTypCd = usTypCd;
    }

    public String getPdmTblPnm() {
        return pdmTblPnm;
    }

    public void setPdmTblPnm(String pdmTblPnm) {
        this.pdmTblPnm = pdmTblPnm;
    }

    public String getPdmColPnm() {
        return pdmColPnm;
    }

    public void setPdmColPnm(String pdmColPnm) {
        this.pdmColPnm = pdmColPnm;
    }

    public String getL1cdPnm() {
        return l1cdPnm;
    }

    public void setL1cdPnm(String l1cdPnm) {
        this.l1cdPnm = l1cdPnm;
    }

    public String getL3cdPnm() {
        return l3cdPnm;
    }

    public void setL3cdPnm(String l3cdPnm) {
        this.l3cdPnm = l3cdPnm;
    }

    public String getGrtLst() {
        return grtLst;
    }

    public void setGrtLst(String grtLst) {
        this.grtLst = grtLst;
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
//
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

    public String getScrtInfo() {
        return scrtInfo;
    }

    public void setScrtInfo(String scrtInfo) {
        this.scrtInfo = scrtInfo;
    }
    
    
    
    public String getNmRlTypNm() {
		return nmRlTypNm;
	}

	public void setNmRlTypNm(String nmRlTypNm) {
		this.nmRlTypNm = nmRlTypNm;
	}

	public String getUsTypNm() {
		return usTypNm;
	}

	public void setUsTypNm(String usTypNm) {
		this.usTypNm = usTypNm;
	}

	public String getAccDbmsYn() {
		return accDbmsYn;
	}

	public void setAccDbmsYn(String accDbmsYn) {
		this.accDbmsYn = accDbmsYn;
	}

	public String getDdlTrgDcd() {
		return ddlTrgDcd;
	}

	public void setDdlTrgDcd(String ddlTrgDcd) {
		this.ddlTrgDcd = ddlTrgDcd;
	}

	public String getSrcDbSchId() {
		return srcDbSchId;
	}

	public void setSrcDbSchId(String srcDbSchId) {
		this.srcDbSchId = srcDbSchId;
	}

	public String getSrcDdlSeqId() {
		return srcDdlSeqId;
	}

	public void setSrcDdlSeqId(String srcDdlSeqId) {
		this.srcDdlSeqId = srcDdlSeqId;
	}


	public String getAplReqTypCd() {
		return aplReqTypCd;
	}

	public void setAplReqTypCd(String aplReqTypCd) {
		this.aplReqTypCd = aplReqTypCd;
	}

	public String getAplReqdt() {
		return aplReqdt;
	}

	public void setAplReqdt(String aplReqdt) {
		this.aplReqdt = aplReqdt;
	}

	public String getPrjMngNo() {
		return prjMngNo;
	}

	public void setPrjMngNo(String prjMngNo) {
		this.prjMngNo = prjMngNo;
	}

	public String getSrMngNo() {
		return srMngNo;
	}

	public void setSrMngNo(String srMngNo) {
		this.srMngNo = srMngNo;
	}

	@Override
	public String toString() {
		return "WaqDdlSeq [rqstNo=" + rqstNo + ", rqstSno=" + rqstSno + ", ddlSeqId=" + ddlSeqId + ", ddlSeqPnm="
				+ ddlSeqPnm + ", ddlSeqLnm=" + ddlSeqLnm + ", dbConnTrgPnm=" + dbConnTrgPnm + ", dbSchId=" + dbSchId
				+ ", dbSchPnm=" + dbSchPnm + ", incby=" + incby + ", strtwt=" + strtwt + ", maxval=" + maxval
				+ ", minval=" + minval + ", cycYn=" + cycYn + ", cacheSz=" + cacheSz + ", ordYn=" + ordYn
				+ ", nmRlTypCd=" + nmRlTypCd + ", usTypCd=" + usTypCd + ", nmRlTypNm=" + nmRlTypNm + ", usTypNm="
				+ usTypNm + ", pdmTblPnm=" + pdmTblPnm + ", pdmColPnm=" + pdmColPnm + ", l1cdPnm=" + l1cdPnm
				+ ", l3cdPnm=" + l3cdPnm + ", grtLst=" + grtLst + ", accDbmsYn=" + accDbmsYn + ", scrtInfo=" + scrtInfo
				+ ", ddlTrgDcd=" + ddlTrgDcd + ", srcDbSchId=" + srcDbSchId + ", srcDdlSeqId=" + srcDdlSeqId
				+ ", aplReqTypCd=" + aplReqTypCd + ", aplReqdt=" + aplReqdt + ", prjMngNo=" + prjMngNo + ", srMngNo="
				+ srMngNo + ", cudYn=" + cudYn + ", pckgNm=" + pckgNm + ", seqClasCd=" + seqClasCd + ", seqInitCd="
				+ seqInitCd + ", bizIfnc=" + bizIfnc + ", rqstResn=" + rqstResn + "]";
	}

	
	  
}