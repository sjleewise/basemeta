package kr.wise.dq.profile.anares.service;

import java.math.BigDecimal;
import java.util.Date;

import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

public class WamPrfResultVO extends WamPrfMstrVO {

    private Date anaStrDtm;

    private Date anaEndDtm;

    private Integer anaDgr;

    private BigDecimal anaCnt;

    private BigDecimal esnErCnt;

    private String esnErRate;

    private String anaLogId;

    private String anaUserId;

    private String anaUserNm;
    
    //품질추이
    private String dbConnTrgLnm;
    private String anaDgrDisp;
    private String dpmo;
    private String sigma;
    private String erRate;
    
    //컬럼분석 정보
    private BigDecimal nullCnt;
    private BigDecimal spaceCnt;
    private BigDecimal minLen;
    private BigDecimal maxLen;
    private String minVal1;
    private String minVal2;
    private String minVal3;
    private String maxVal1;
    private String maxVal2;
    private String maxVal3;
    
    private String stddevVal;
    private String varianceVal;
    private String avgVal;
    private String unqCnt;
    private String minCntVal;
    private String maxCntVal;
    
    //검증룰
    private String vrfcTyp;
    
    public String getVrfcTyp() {
		return vrfcTyp;
	}

	public void setVrfcTyp(String vrfcTyp) {
		this.vrfcTyp = vrfcTyp;
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

	//조회조건
    private String schAnaStrDtm;
    

	public Date getAnaStrDtm() {
        return anaStrDtm;
    }

//	@JsonDeserialize(using = IBSDateJsonDeserializer.class)
    public void setAnaStrDtm(Date anaStrDtm) {
        this.anaStrDtm = anaStrDtm;
    }

    public Date getAnaEndDtm() {
        return anaEndDtm;
    }

//    @JsonDeserialize(using = IBSDateJsonDeserializer.class)
    public void setAnaEndDtm(Date anaEndDtm) {
        this.anaEndDtm = anaEndDtm;
    }

    public Integer getAnaDgr() {
        return anaDgr;
    }

    public void setAnaDgr(Integer anaDgr) {
        this.anaDgr = anaDgr;
    }

    public BigDecimal getAnaCnt() {
        return anaCnt;
    }

    public void setAnaCnt(BigDecimal anaCnt) {
        this.anaCnt = anaCnt;
    }

    public BigDecimal getEsnErCnt() {
        return esnErCnt;
    }

    public void setEsnErCnt(BigDecimal esnErCnt) {
        this.esnErCnt = esnErCnt;
    }

    public String getAnaLogId() {
        return anaLogId;
    }

    public void setAnaLogId(String anaLogId) {
        this.anaLogId = anaLogId;
    }

    public String getAnaUserId() {
        return anaUserId;
    }

    public void setAnaUserId(String anaUserId) {
        this.anaUserId = anaUserId;
    }

	public String getAnaUserNm() {
		return anaUserNm;
	}

	public void setAnaUserNm(String anaUserNm) {
		this.anaUserNm = anaUserNm;
	}

	public String getEsnErRate() {
		return esnErRate;
	}

	public void setEsnErRate(String esnErRate) {
		this.esnErRate = esnErRate;
	}

	
	public String getSchAnaStrDtm() {
		return schAnaStrDtm;
	}

	public void setSchAnaStrDtm(String schAnaStrDtm) {
		this.schAnaStrDtm = schAnaStrDtm;
	}
	
	public BigDecimal getNullCnt() {
		return nullCnt;
	}

	public void setNullCnt(BigDecimal nullCnt) {
		this.nullCnt = nullCnt;
	}

	public BigDecimal getSpaceCnt() {
		return spaceCnt;
	}

	public void setSpaceCnt(BigDecimal spaceCnt) {
		this.spaceCnt = spaceCnt;
	}

	public BigDecimal getMinLen() {
		return minLen;
	}

	public void setMinLen(BigDecimal minLen) {
		this.minLen = minLen;
	}

	public BigDecimal getMaxLen() {
		return maxLen;
	}

	public void setMaxLen(BigDecimal maxLen) {
		this.maxLen = maxLen;
	}

	public String getMinVal1() {
		return minVal1;
	}

	public void setMinVal1(String minVal1) {
		this.minVal1 = minVal1;
	}

	public String getMinVal2() {
		return minVal2;
	}

	public void setMinVal2(String minVal2) {
		this.minVal2 = minVal2;
	}

	public String getMinVal3() {
		return minVal3;
	}

	public void setMinVal3(String minVal3) {
		this.minVal3 = minVal3;
	}

	public String getMaxVal1() {
		return maxVal1;
	}

	public void setMaxVal1(String maxVal1) {
		this.maxVal1 = maxVal1;
	}

	public String getMaxVal2() {
		return maxVal2;
	}

	public void setMaxVal2(String maxVal2) {
		this.maxVal2 = maxVal2;
	}

	public String getMaxVal3() {
		return maxVal3;
	}

	public void setMaxVal3(String maxVal3) {
		this.maxVal3 = maxVal3;
	}
	
	

	public String getDbConnTrgLnm() {
		return dbConnTrgLnm;
	}

	public void setDbConnTrgLnm(String dbConnTrgLnm) {
		this.dbConnTrgLnm = dbConnTrgLnm;
	}

	public String getAnaDgrDisp() {
		return anaDgrDisp;
	}

	public void setAnaDgrDisp(String anaDgrDisp) {
		this.anaDgrDisp = anaDgrDisp;
	}

	public String getDpmo() {
		return dpmo;
	}

	public void setDpmo(String dpmo) {
		this.dpmo = dpmo;
	}

	public String getSigma() {
		return sigma;
	}

	public void setSigma(String sigma) {
		this.sigma = sigma;
	}

	public String getErRate() {
		return erRate;
	}

	public void setErRate(String erRate) {
		this.erRate = erRate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamPrfResultVO [anaStrDtm=").append(anaStrDtm)
				.append(", anaEndDtm=").append(anaEndDtm).append(", anaDgr=")
				.append(anaDgr).append(", anaCnt=").append(anaCnt)
				.append(", esnErCnt=").append(esnErCnt).append(", esnErRate=")
				.append(esnErRate).append(", anaLogId=").append(anaLogId)
				.append(", anaUserId=").append(anaUserId)
				.append(", anaUserNm=").append(anaUserNm)
				.append(", dbConnTrgLnm=").append(dbConnTrgLnm)
				.append(", anaDgrDisp=").append(anaDgrDisp).append(", dpmo=")
				.append(dpmo).append(", sigma=").append(sigma)
				.append(", erRate=").append(erRate).append(", nullCnt=")
				.append(nullCnt).append(", minLen=").append(minLen)
				.append(", maxLen=").append(maxLen).append(", minVal1=")
				.append(minVal1).append(", minVal2=").append(minVal2)
				.append(", minVal3=").append(minVal3).append(", maxVal1=")
				.append(maxVal1).append(", maxVal2=").append(maxVal2)
				.append(", maxVal3=").append(maxVal3).append(", schAnaStrDtm=")
				.append(schAnaStrDtm).append("]");
		return builder.toString() + super.toString();
	}
	
	

}