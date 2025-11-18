package kr.wise.meta.stnd.service;

import java.util.Date;

import kr.wise.commons.cmm.CommonVo;

public class WamStwdAbr extends CommonVo  {
    private String abrId;

    private Integer dtlSeq;

    private Date strDtm;

    private String orglUser;

    private String resultCd;

    private String resultTxt;

    private String termKnm;

    private String engFullNm;

    private String description;

    private String definition;

    private String chinChar;

    private String srcType;

    private String dictionType;

    private String initEnm01;

    private String initEnm02;

    private String initEnm03;

    private String initEnm04;

    private String initEnm05;

    private String initEnm06;

    private String errTxt01;

    private String errTxt02;

    private String errTxt03;

    private String errTxt04;

    private String errTxt05;

    private String errTxt06;

    private String genRule;

    private String korFullNm;

    private String korDispNm;
    
    private String abrLength;

    private Integer overlapCount;
    
    private String wdDcd;
    
    private String dmnYn;
    
    
    public String getDmnYn() {
		return dmnYn;
	}

	public void setDmnYn(String dmnYn) {
		this.dmnYn = dmnYn;
	}

	public Integer getOverlapCount() {
		return overlapCount;
	}

	public void setOverlapCount(Integer overlapCount) {
		this.overlapCount = overlapCount;
	}

	public String getAbrLength() {
		return abrLength;
	}

	public void setAbrLength(String abrLength) {
		this.abrLength = abrLength;
	}

	public String getAbrId() {
        return abrId;
    }

    public void setAbrId(String abrId) {
        this.abrId = abrId;
    }

    public Integer getDtlSeq() {
        return dtlSeq;
    }

    public void setDtlSeq(Integer dtlSeq) {
        this.dtlSeq = dtlSeq;
    }

    public Date getStrDtm() {
        return strDtm;
    }

    public void setStrDtm(Date strDtm) {
        this.strDtm = strDtm;
    }

    public String getOrglUser() {
        return orglUser;
    }

    public void setOrglUser(String orglUser) {
        this.orglUser = orglUser;
    }

    public String getResultCd() {
        return resultCd;
    }

    public void setResultCd(String resultCd) {
        this.resultCd = resultCd;
    }

    public String getResultTxt() {
        return resultTxt;
    }

    public void setResultTxt(String resultTxt) {
        this.resultTxt = resultTxt;
    }

    public String getTermKnm() {
        return termKnm;
    }

    public void setTermKnm(String termKnm) {
        this.termKnm = termKnm;
    }

    public String getEngFullNm() {
        return engFullNm;
    }

    public void setEngFullNm(String engFullNm) {
        this.engFullNm = engFullNm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getChinChar() {
        return chinChar;
    }

    public void setChinChar(String chinChar) {
        this.chinChar = chinChar;
    }

    public String getSrcType() {
        return srcType;
    }

    public void setSrcType(String srcType) {
        this.srcType = srcType;
    }

    public String getDictionType() {
        return dictionType;
    }

    public void setDictionType(String dictionType) {
        this.dictionType = dictionType;
    }

    public String getInitEnm01() {
        return initEnm01;
    }

    public void setInitEnm01(String initEnm01) {
        this.initEnm01 = initEnm01;
    }

    public String getInitEnm02() {
        return initEnm02;
    }

    public void setInitEnm02(String initEnm02) {
        this.initEnm02 = initEnm02;
    }

    public String getInitEnm03() {
        return initEnm03;
    }

    public void setInitEnm03(String initEnm03) {
        this.initEnm03 = initEnm03;
    }

    public String getInitEnm04() {
        return initEnm04;
    }

    public void setInitEnm04(String initEnm04) {
        this.initEnm04 = initEnm04;
    }

    public String getInitEnm05() {
        return initEnm05;
    }

    public void setInitEnm05(String initEnm05) {
        this.initEnm05 = initEnm05;
    }

    public String getInitEnm06() {
        return initEnm06;
    }

    public void setInitEnm06(String initEnm06) {
        this.initEnm06 = initEnm06;
    }

    public String getErrTxt01() {
        return errTxt01;
    }

    public void setErrTxt01(String errTxt01) {
        this.errTxt01 = errTxt01;
    }

    public String getErrTxt02() {
        return errTxt02;
    }

    public void setErrTxt02(String errTxt02) {
        this.errTxt02 = errTxt02;
    }

    public String getErrTxt03() {
        return errTxt03;
    }

    public void setErrTxt03(String errTxt03) {
        this.errTxt03 = errTxt03;
    }

    public String getErrTxt04() {
        return errTxt04;
    }

    public void setErrTxt04(String errTxt04) {
        this.errTxt04 = errTxt04;
    }

    public String getErrTxt05() {
        return errTxt05;
    }

    public void setErrTxt05(String errTxt05) {
        this.errTxt05 = errTxt05;
    }

    public String getErrTxt06() {
        return errTxt06;
    }

    public void setErrTxt06(String errTxt06) {
        this.errTxt06 = errTxt06;
    }

    public String getGenRule() {
        return genRule;
    }

    public void setGenRule(String genRule) {
        this.genRule = genRule;
    }

    public String getKorFullNm() {
        return korFullNm;
    }

    public void setKorFullNm(String korFullNm) {
        this.korFullNm = korFullNm;
    }

    public String getKorDispNm() {
        return korDispNm;
    }

    public void setKorDispNm(String korDispNm) {
        this.korDispNm = korDispNm;
    }

	public String getWdDcd() {
		return wdDcd;
	}

	public void setWdDcd(String wdDcd) {
		this.wdDcd = wdDcd;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamStwdAbr [abrId=").append(abrId).append(", dtlSeq=")
				.append(dtlSeq).append(", strDtm=").append(strDtm)
				.append(", orglUser=").append(orglUser).append(", resultCd=")
				.append(resultCd).append(", resultTxt=").append(resultTxt)
				.append(", termKnm=").append(termKnm).append(", engFullNm=")
				.append(engFullNm).append(", description=").append(description)
				.append(", definition=").append(definition)
				.append(", chinChar=").append(chinChar).append(", srcType=")
				.append(srcType).append(", dictionType=").append(dictionType)
				.append(", initEnm01=").append(initEnm01)
				.append(", initEnm02=").append(initEnm02)
				.append(", initEnm03=").append(initEnm03)
				.append(", initEnm04=").append(initEnm04)
				.append(", initEnm05=").append(initEnm05)
				.append(", initEnm06=").append(initEnm06).append(", errTxt01=")
				.append(errTxt01).append(", errTxt02=").append(errTxt02)
				.append(", errTxt03=").append(errTxt03).append(", errTxt04=")
				.append(errTxt04).append(", errTxt05=").append(errTxt05)
				.append(", errTxt06=").append(errTxt06).append(", genRule=")
				.append(genRule).append(", korFullNm=").append(korFullNm)
				.append(", korDispNm=").append(korDispNm)
				.append(", abrLength=").append(abrLength)
				.append(", overlapCount=").append(overlapCount).append("]");
		return builder.toString();
	}
    
    
}