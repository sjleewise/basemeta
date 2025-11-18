package kr.wise.meta.stnd.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WamCdVal extends CommonVo {
    private String cdValId;

    private String cdVal;

    private String cdValNm;

    private String dmnId;

    private String uppCdValId;

    private String rqstNo;

    private Integer rqstSno;

    private Integer rqstDtlSno;

    private String objDescn;

    private Integer objVers;

    private String regTypCd;

    private Date frsRqstDtm;

    private String frsRqstUserId;

    private Date rqstDtm;

    private String rqstUserId;

    private Date aprvDtm;

    private String aprvUserId;

    private Integer dispOrd;

    private String aplStrDt;

    private String aplEndDt;

    
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
    
    private String uppCdVal;
    
    private String dmnPnm;
    private String dmnLnm;

    private String dmnDscd;
    
    public String getDmnDscd() {
		return dmnDscd;
	}

	public void setDmnDscd(String dmnDscd) {
		this.dmnDscd = dmnDscd;
	}


	private String codeLevel;
    
    public String getCodeLevel() {
		return codeLevel;
	}

	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}



	public String getDmnPnm() {
		return dmnPnm;
	}


	private String lccd;    
     private String mccd   ; 
    private String sccd    ;
    private String lclsNm  ;
    private String mdcfNm  ;
    private String sclsNm  ;
    private String etc1    ;
    private String etcNm1  ;
    private String etc2    ;
    private String etcNm2  ;
    private String etc3    ;
    private String etcNm3  ;
    private String etc4    ;
    private String etcNm4  ;
    private String etc5    ;
    private String etcNm5  ;
    private String rmrkCntn;

    	public void setDmnPnm(String dmnPnm) {
		this.dmnPnm = dmnPnm;
	}

	public String getDmnLnm() {
		return dmnLnm;
	}

	public void setDmnLnm(String dmnLnm) {
		this.dmnLnm = dmnLnm;
	}

	public String getLccd() {
		return lccd;
	}

	public void setLccd(String lccd) {
		this.lccd = lccd;
	}

	public String getMccd() {
		return mccd;
	}

	public void setMccd(String mccd) {
		this.mccd = mccd;
	}

	public String getSccd() {
		return sccd;
	}

	public void setSccd(String sccd) {
		this.sccd = sccd;
	}

	public String getLclsNm() {
		return lclsNm;
	}

	public void setLclsNm(String lclsNm) {
		this.lclsNm = lclsNm;
	}

	public String getMdcfNm() {
		return mdcfNm;
	}

	public void setMdcfNm(String mdcfNm) {
		this.mdcfNm = mdcfNm;
	}

	public String getSclsNm() {
		return sclsNm;
	}

	public void setSclsNm(String sclsNm) {
		this.sclsNm = sclsNm;
	}

	public String getEtc1() {
		return etc1;
	}

	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}

	public String getEtcNm1() {
		return etcNm1;
	}

	public void setEtcNm1(String etcNm1) {
		this.etcNm1 = etcNm1;
	}

	public String getEtc2() {
		return etc2;
	}

	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}

	public String getEtcNm2() {
		return etcNm2;
	}

	public void setEtcNm2(String etcNm2) {
		this.etcNm2 = etcNm2;
	}

	public String getEtc3() {
		return etc3;
	}

	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}

	public String getEtcNm3() {
		return etcNm3;
	}

	public void setEtcNm3(String etcNm3) {
		this.etcNm3 = etcNm3;
	}

	public String getEtc4() {
		return etc4;
	}

	public void setEtc4(String etc4) {
		this.etc4 = etc4;
	}

	public String getEtcNm4() {
		return etcNm4;
	}

	public void setEtcNm4(String etcNm4) {
		this.etcNm4 = etcNm4;
	}

	public String getEtc5() {
		return etc5;
	}

	public void setEtc5(String etc5) {
		this.etc5 = etc5;
	}

	public String getEtcNm5() {
		return etcNm5;
	}

	public void setEtcNm5(String etcNm5) {
		this.etcNm5 = etcNm5;
	}

	public String getRmrkCntn() {
		return rmrkCntn;
	}

	public void setRmrkCntn(String rmrkCntn) {
		this.rmrkCntn = rmrkCntn;
	}

    public String getUppCdVal() {
		return uppCdVal;
	}

	public void setUppCdVal(String uppCdVal) {
		this.uppCdVal = uppCdVal;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
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

    public String getDmnId() {
        return dmnId;
    }

    public void setDmnId(String dmnId) {
        this.dmnId = dmnId;
    }

    public String getUppCdValId() {
        return uppCdValId;
    }

    public void setUppCdValId(String uppCdValId) {
        this.uppCdValId = uppCdValId;
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

    public Integer getRqstDtlSno() {
        return rqstDtlSno;
    }

    public void setRqstDtlSno(Integer rqstDtlSno) {
        this.rqstDtlSno = rqstDtlSno;
    }

    public String getObjDescn() {
        return objDescn;
    }

    public void setObjDescn(String objDescn) {
        this.objDescn = objDescn;
    }

    public Integer getObjVers() {
        return objVers;
    }

    public void setObjVers(Integer objVers) {
        this.objVers = objVers;
    }

    public String getRegTypCd() {
        return regTypCd;
    }

    public void setRegTypCd(String regTypCd) {
        this.regTypCd = regTypCd;
    }

    public Date getFrsRqstDtm() {
        return frsRqstDtm;
    }

    public void setFrsRqstDtm(Date frsRqstDtm) {
        this.frsRqstDtm = frsRqstDtm;
    }

    public String getFrsRqstUserId() {
        return frsRqstUserId;
    }

    public void setFrsRqstUserId(String frsRqstUserId) {
        this.frsRqstUserId = frsRqstUserId;
    }

    public Date getRqstDtm() {
        return rqstDtm;
    }

    public void setRqstDtm(Date rqstDtm) {
        this.rqstDtm = rqstDtm;
    }

    public String getRqstUserId() {
        return rqstUserId;
    }

    public void setRqstUserId(String rqstUserId) {
        this.rqstUserId = rqstUserId;
    }

    public Date getAprvDtm() {
        return aprvDtm;
    }

    public void setAprvDtm(Date aprvDtm) {
        this.aprvDtm = aprvDtm;
    }

    public String getAprvUserId() {
        return aprvUserId;
    }

    public void setAprvUserId(String aprvUserId) {
        this.aprvUserId = aprvUserId;
    }

    public Integer getDispOrd() {
        return dispOrd;
    }

    public void setDispOrd(Integer dispOrd) {
        this.dispOrd = dispOrd;
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
		return "WamCdVal [cdValId=" + cdValId + ", cdVal=" + cdVal
				+ ", cdValNm=" + cdValNm + ", dmnId=" + dmnId + ", uppCdValId="
				+ uppCdValId + ", rqstNo=" + rqstNo + ", rqstSno=" + rqstSno
				+ ", rqstDtlSno=" + rqstDtlSno + ", objDescn=" + objDescn
				+ ", objVers=" + objVers + ", regTypCd=" + regTypCd
				+ ", frsRqstDtm=" + frsRqstDtm + ", frsRqstUserId="
				+ frsRqstUserId + ", rqstDtm=" + rqstDtm + ", rqstUserId="
				+ rqstUserId + ", aprvDtm=" + aprvDtm + ", aprvUserId="
				+ aprvUserId + ", dispOrd=" + dispOrd + ", aplStrDt="
				+ aplStrDt + ", aplEndDt=" + aplEndDt + ", useYn=" + useYn
				+ ", vvNote1=" + vvNote1 + ", vvNote2=" + vvNote2
				+ ", vvNote3=" + vvNote3 + ", vvNote4=" + vvNote4
				+ ", vvNote5=" + vvNote5 + ", vvNoteNm1=" + vvNoteNm1
				+ ", vvNoteNm2=" + vvNoteNm2 + ", vvNoteNm3=" + vvNoteNm3
				+ ", vvNoteNm4=" + vvNoteNm4 + ", vvNoteNm5=" + vvNoteNm5
				+ ", outlCntn1=" + outlCntn1 + ", outlCntn2=" + outlCntn2
				+ ", uppCdVal=" + uppCdVal + ", dmnPnm=" + dmnPnm + ", dmnLnm="
				+ dmnLnm + ", dmnDscd=" + dmnDscd + ", codeLevel=" + codeLevel
				+ ", lccd=" + lccd + ", mccd=" + mccd + ", sccd=" + sccd
				+ ", lclsNm=" + lclsNm + ", mdcfNm=" + mdcfNm + ", sclsNm="
				+ sclsNm + ", etc1=" + etc1 + ", etcNm1=" + etcNm1 + ", etc2="
				+ etc2 + ", etcNm2=" + etcNm2 + ", etc3=" + etc3 + ", etcNm3="
				+ etcNm3 + ", etc4=" + etc4 + ", etcNm4=" + etcNm4 + ", etc5="
				+ etc5 + ", etcNm5=" + etcNm5 + ", rmrkCntn=" + rmrkCntn + "]";
	}
}