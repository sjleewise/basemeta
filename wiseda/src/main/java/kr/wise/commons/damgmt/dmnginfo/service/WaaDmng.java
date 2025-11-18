package kr.wise.commons.damgmt.dmnginfo.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.cmm.CommonVo;

public class WaaDmng extends CommonVo {
    private String dmngId;

    //private Date expDtm;

    //private Date strDtm;

    private String dmngLnm;

    private String dmngPnm;

    private String uppDmngId;
    
    private Integer dmngLvl;

    private String cdDmnYn;

    private String infotpChgCanYn;
    
    private String uppDmngLnm;

     //private String objDescn;

    //private Integer objVers;

   // private Date writDtm;

    //private String writUserId;

//    private List<Integer> lvlList = new ArrayList<Integer>();
    
    
    
    public String getDmngId() {
        return dmngId;
    }

    public String getUppDmngLnm() {
		return uppDmngLnm;
	}

	public void setUppDmngLnm(String uppDmngLnm) {
		this.uppDmngLnm = uppDmngLnm;
	}



	public void setDmngId(String dmngId) {
        this.dmngId = dmngId;
    }

/*    public Date getExpDtm() {
        return expDtm;
    }

    public void setExpDtm(Date expDtm) {
        this.expDtm = expDtm;
    }

    public Date getStrDtm() {
        return strDtm;
    }

    public void setStrDtm(Date strDtm) {
        this.strDtm = strDtm;
    }*/

    public String getDmngLnm() {
        return dmngLnm;
    }

    public void setDmngLnm(String dmngLnm) {
        this.dmngLnm = dmngLnm;
    }

    public String getDmngPnm() {
        return dmngPnm;
    }

    public void setDmngPnm(String dmngPnm) {
        this.dmngPnm = dmngPnm;
    }

    public String getUppDmngId() {
        return uppDmngId;
    }

    public void setUppDmngId(String uppDmngId) {
        this.uppDmngId = uppDmngId;
    }

    public Integer getDmngLvl() {
        return dmngLvl;
    }

    public void setDmngLvl(int i) {
        this.dmngLvl = i;
    }

    public String getCdDmnYn() {
        return cdDmnYn;
    }

    public void setCdDmnYn(String cdDmnYn) {
        this.cdDmnYn = cdDmnYn;
    }

    public String getInfotpChgCanYn() {
        return infotpChgCanYn;
    }

    public void setInfotpChgCanYn(String infotpChgCanYn) {
        this.infotpChgCanYn = infotpChgCanYn;
    }

//	public List<Integer> getLvlList() {
//		return lvlList;
//	}
//
//	public void setLvlList(List<Integer> lvlList) {
//		this.lvlList = lvlList;
//	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaDmng [dmngId=").append(dmngId).append(", dmngLnm=")
				.append(dmngLnm).append(", dmngPnm=").append(dmngPnm)
				.append(", uppDmngId=").append(uppDmngId).append(", dmngLvl=")
				.append(dmngLvl).append(", cdDmnYn=").append(cdDmnYn)
				.append(", infotpChgCanYn=").append(infotpChgCanYn)
				.append(", uppDmngLnm=").append(uppDmngLnm)
//				.append(", lvlList=").append(lvlList)
				.append("]");
		return builder.toString() + super.toString();
	}

  /*  public String getObjDescn() {
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

    public Date getWritDtm() {
        return writDtm;
    }

    public void setWritDtm(Date writDtm) {
        this.writDtm = writDtm;
    }

    public String getWritUserId() {
        return writUserId;
    }

    public void setWritUserId(String writUserId) {
        this.writUserId = writUserId;
    }*/
	
	
}