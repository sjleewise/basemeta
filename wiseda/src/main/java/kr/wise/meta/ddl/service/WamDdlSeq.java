package kr.wise.meta.ddl.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WamDdlSeq extends CommonVo {
    private String ddlSeqId;

    private String ddlSeqPnm;

    private String ddlSeqLnm;

    private String dbSchId;

    private String incby;

    private String strtwt;

    private String maxval;

    private String minval;

    private String cycYn;

    private String cacheSz;

    private String ordYn;

    private String nmRlTypCd;

    private String usTypCd;

    private String pdmTblPnm;

    private String pdmColPnm;

    private String l1cdPnm;

    private String l3cdPnm;

    private String grtLst;

    private String prcTypCd;

    private String prcDt;

    private String prcDbaId;
    
    private String dbConnTrgPnm;

    private String dbSchPnm;
    
    private String dbConnTrgId;

    private String dbSchPnmId;
    
    private String accDbmsYn;   //계정계여부


//    private String rqstNo;
//
//    private Integer rqstSno;
//
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
    
    private String prjMngNo;
    private String srMngNo;
    private String objDcd;
    private String objId;
    private String aplReqTypCd;
    private String aplReqdt;
    
    
    private String pckgNm;
    private String seqClasCd;
    private String seqInitCd;
    private String bizIfnc;
    
    
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

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getObjDcd() {
		return objDcd;
	}

	public void setObjDcd(String objDcd) {
		this.objDcd = objDcd;
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

    public String getDbSchId() {
        return dbSchId;
    }

    public void setDbSchId(String dbSchId) {
        this.dbSchId = dbSchId;
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

    public String getPrcTypCd() {
        return prcTypCd;
    }

    public void setPrcTypCd(String prcTypCd) {
        this.prcTypCd = prcTypCd;
    }

    public String getPrcDt() {
        return prcDt;
    }

    public void setPrcDt(String prcDt) {
        this.prcDt = prcDt;
    }

    public String getPrcDbaId() {
        return prcDbaId;
    }

    public void setPrcDbaId(String prcDbaId) {
        this.prcDbaId = prcDbaId;
    }
    
    public String getScrtInfo() {
		return scrtInfo;
	}

	public void setScrtInfo(String scrtInfo) {
		this.scrtInfo = scrtInfo;
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
	

	public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}

	public String getDbSchPnmId() {
		return dbSchPnmId;
	}

	public void setDbSchPnmId(String dbSchPnmId) {
		this.dbSchPnmId = dbSchPnmId;
	}

	public String getAccDbmsYn() {
		return accDbmsYn;
	}

	public void setAccDbmsYn(String accDbmsYn) {
		this.accDbmsYn = accDbmsYn;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamDdlSeq [ddlSeqId=").append(ddlSeqId)
				.append(", ddlSeqPnm=").append(ddlSeqPnm)
				.append(", ddlSeqLnm=").append(ddlSeqLnm).append(", dbSchId=")
				.append(dbSchId).append(", incby=").append(incby)
				.append(", strtwt=").append(strtwt).append(", maxval=")
				.append(maxval).append(", minval=").append(minval)
				.append(", cycYn=").append(cycYn).append(", cacheSz=")
				.append(cacheSz).append(", ordYn=").append(ordYn)
				.append(", nmRlTypCd=").append(nmRlTypCd).append(", usTypCd=")
				.append(usTypCd).append(", pdmTblPnm=").append(pdmTblPnm)
				.append(", pdmColPnm=").append(pdmColPnm).append(", l1cdPnm=")
				.append(l1cdPnm).append(", l3cdPnm=").append(l3cdPnm)
				.append(", grtLst=").append(grtLst).append(", prcTypCd=")
				.append(prcTypCd).append(", prcDt=").append(prcDt)
				.append(", prcDbaId=").append(prcDbaId)
				.append(", dbConnTrgPnm=").append(dbConnTrgPnm)
				.append(", dbSchPnm=").append(dbSchPnm)
				.append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", dbSchPnmId=").append(dbSchPnmId)
				.append(", accDbmsYn=").append(accDbmsYn).append(", scrtInfo=")
				.append(scrtInfo).append("]");
		return builder.toString();
	}

	public String getDdlTrgDcd() {
		return ddlTrgDcd;
	}

	public void setDdlTrgDcd(String ddlTrgDcd) {
		this.ddlTrgDcd = ddlTrgDcd;
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
	
	    
}