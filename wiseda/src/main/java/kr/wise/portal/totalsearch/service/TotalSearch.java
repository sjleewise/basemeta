package kr.wise.portal.totalsearch.service;

import java.util.Date;

public class TotalSearch {
    private String categ1Nm;

    private String categ1Cd;

    private String categ2Nm;

    private String categ2Cd;

    private String objId;

    private String objKnm;

    private String objEnm;

    private String shotDesc;

    private String info1;

    private String regMan;

    private String regId;

    private Date regDate;

    private Date batchDate;

    private String objDesc;

    private int totCnt;
    
    private String userId;
    
    private String searchWord;

    public String getCateg1Nm() {
        return categ1Nm;
    }

    public void setCateg1Nm(String categ1Nm) {
        this.categ1Nm = categ1Nm;
    }

    public String getCateg1Cd() {
        return categ1Cd;
    }

    public void setCateg1Cd(String categ1Cd) {
        this.categ1Cd = categ1Cd;
    }

    public String getCateg2Nm() {
        return categ2Nm;
    }

    public void setCateg2Nm(String categ2Nm) {
        this.categ2Nm = categ2Nm;
    }

    public String getCateg2Cd() {
        return categ2Cd;
    }

    public void setCateg2Cd(String categ2Cd) {
        this.categ2Cd = categ2Cd;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getObjKnm() {
        return objKnm;
    }

    public void setObjKnm(String objKnm) {
        this.objKnm = objKnm;
    }

    public String getObjEnm() {
        return objEnm;
    }

    public void setObjEnm(String objEnm) {
        this.objEnm = objEnm;
    }

    public String getShotDesc() {
        return shotDesc;
    }

    public void setShotDesc(String shotDesc) {
        this.shotDesc = shotDesc;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getRegMan() {
        return regMan;
    }

    public void setRegMan(String regMan) {
        this.regMan = regMan;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
    }

    public String getObjDesc() {
        return objDesc;
    }

    public void setObjDesc(String objDesc) {
        this.objDesc = objDesc;
    }

	public int getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
}