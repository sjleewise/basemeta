package kr.wise.meta.stnd.service;

import kr.wise.commons.cmm.CommonVo;

public class WaqCdVal extends CommonVo {
    private Integer rqstDtlSno;

    private String cdValId;

    private String cdVal;

    private String cdValNm;

    private String dmnId;

    private String dmnLnm;

    private String uppCdValId;

    private String uppCdVal;
    
    private String useYn;
     //기타코드 및 적요
    private String vvNote1;
    private String vvNote2;
    private String vvNote3;
    private String vvNote4;
    private String vvNote5;
    
    private String vvNoteNm1;
    private String vvNoteNm2;
    private String vvNoteNm3;
    private String vvNoteNm4;
    private String vvNoteNm5;
    
    private String outlCntn1;
    private String outlCntn2;
    
    private String dispOrd;
    
    private String dmnPnm;
    
    private String aplStrDt;
    private String aplEndDt;
    
    public String getDmnPnm() {
		return dmnPnm;
	}

	public void setDmnPnm(String dmnPnm) {
		this.dmnPnm = dmnPnm;
	}

	public String getDispOrd() {
		return dispOrd;
	}

	public void setDispOrd(String dispOrd) {
		this.dispOrd = dispOrd;
	}

	public String getVvNote1() {
		return vvNote1;
	}

	public void setVvNote1(String vvNote1) {
		this.vvNote1 = vvNote1;
	}

	public String getVvNote2() {
		return vvNote2;
	}

	public void setVvNote2(String vvNote2) {
		this.vvNote2 = vvNote2;
	}

	public String getVvNote3() {
		return vvNote3;
	}

	public void setVvNote3(String vvNote3) {
		this.vvNote3 = vvNote3;
	}

	public String getVvNote4() {
		return vvNote4;
	}

	public void setVvNote4(String vvNote4) {
		this.vvNote4 = vvNote4;
	}

	public String getVvNote5() {
		return vvNote5;
	}

	public void setVvNote5(String vvNote5) {
		this.vvNote5 = vvNote5;
	}

	public String getVvNoteNm1() {
		return vvNoteNm1;
	}

	public void setVvNoteNm1(String vvNoteNm1) {
		this.vvNoteNm1 = vvNoteNm1;
	}

	public String getVvNoteNm2() {
		return vvNoteNm2;
	}

	public void setVvNoteNm2(String vvNoteNm2) {
		this.vvNoteNm2 = vvNoteNm2;
	}

	public String getVvNoteNm3() {
		return vvNoteNm3;
	}

	public void setVvNoteNm3(String vvNoteNm3) {
		this.vvNoteNm3 = vvNoteNm3;
	}

	public String getVvNoteNm4() {
		return vvNoteNm4;
	}

	public void setVvNoteNm4(String vvNoteNm4) {
		this.vvNoteNm4 = vvNoteNm4;
	}

	public String getVvNoteNm5() {
		return vvNoteNm5;
	}

	public void setVvNoteNm5(String vvNoteNm5) {
		this.vvNoteNm5 = vvNoteNm5;
	}

	public String getOutlCntn1() {
		return outlCntn1;
	}

	public void setOutlCntn1(String outlCntn1) {
		this.outlCntn1 = outlCntn1;
	}

	public String getOutlCntn2() {
		return outlCntn2;
	}

	public void setOutlCntn2(String outlCntn2) {
		this.outlCntn2 = outlCntn2;
	}


    

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

    public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public Integer getRqstDtlSno() {
        return rqstDtlSno;
    }

    public void setRqstDtlSno(Integer rqstDtlSno) {
        this.rqstDtlSno = rqstDtlSno;
    }

    public String getCdValId() {
        return cdValId;
    }

    public void setCdValId(String cdValId) {
        this.cdValId = cdValId;
    }

    public String getCdVal() {
        return cdVal;
    }

    public void setCdVal(String cdVal) {
        this.cdVal = cdVal;
    }

    public String getCdValNm() {
        return cdValNm;
    }

    public void setCdValNm(String cdValNm) {
        this.cdValNm = cdValNm;
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

    public String getDmnId() {
        return dmnId;
    }

    public void setDmnId(String dmnId) {
        this.dmnId = dmnId;
    }

    public String getDmnLnm() {
        return dmnLnm;
    }

    public void setDmnLnm(String dmnLnm) {
        this.dmnLnm = dmnLnm;
    }

    public String getUppCdValId() {
        return uppCdValId;
    }

    public void setUppCdValId(String uppCdValId) {
        this.uppCdValId = uppCdValId;
    }

    public String getUppCdVal() {
        return uppCdVal;
    }

    public void setUppCdVal(String uppCdVal) {
        this.uppCdVal = uppCdVal;
    }

	public String getAplStrDt() {
		return aplStrDt;
	}

	public void setAplStrDt(String aplStrDt) {
		this.aplStrDt = aplStrDt;
	}

	public String getAplEndDt() {
		return aplEndDt;
	}

	public void setAplEndDt(String aplEndDt) {
		this.aplEndDt = aplEndDt;
	}

	@Override
	public String toString() {
		return "WaqCdVal [rqstDtlSno=" + rqstDtlSno + ", cdValId=" + cdValId
				+ ", cdVal=" + cdVal + ", cdValNm=" + cdValNm + ", dmnId="
				+ dmnId + ", dmnLnm=" + dmnLnm + ", uppCdValId=" + uppCdValId
				+ ", uppCdVal=" + uppCdVal + ", useYn=" + useYn + ", vvNote1="
				+ vvNote1 + ", vvNote2=" + vvNote2 + ", vvNote3=" + vvNote3
				+ ", vvNote4=" + vvNote4 + ", vvNote5=" + vvNote5
				+ ", vvNoteNm1=" + vvNoteNm1 + ", vvNoteNm2=" + vvNoteNm2
				+ ", vvNoteNm3=" + vvNoteNm3 + ", vvNoteNm4=" + vvNoteNm4
				+ ", vvNoteNm5=" + vvNoteNm5 + ", outlCntn1=" + outlCntn1
				+ ", outlCntn2=" + outlCntn2 + ", dispOrd=" + dispOrd
				+ ", dmnPnm=" + dmnPnm + ", aplStrDt=" + aplStrDt
				+ ", aplEndDt=" + aplEndDt + "]";
	}
}