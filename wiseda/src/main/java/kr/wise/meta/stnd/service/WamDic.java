package kr.wise.meta.stnd.service;

import kr.wise.commons.cmm.CommonVo;

public class WamDic extends CommonVo {
    
	/** 단어명ID */
    private String wdName;

    /** 사전종류 */
    private String dicOrgn;

    /** 영문명 */
    private String ennm;
    
    /**  설명 */
    private String dicDesc;
    
    private String dicRqstUserId;
    
     


	public String getWdName() {
		return wdName;
	}

	public void setWdName(String wdName) {
		this.wdName = wdName;
	}

	public String getDicOrgn() {
		return dicOrgn;
	}

	public void setDicOrgn(String dicOrgn) {
		this.dicOrgn = dicOrgn;
	}

	public String getEnnm() {
		return ennm;
	}

	public void setEnnm(String ennm) {
		this.ennm = ennm;
	}

	public String getDicDesc() {
		return dicDesc;
	}

	public void setDicDesc(String dicDesc) {
		this.dicDesc = dicDesc;
	}
	
	public String getDicRqstUserId() {
		return dicRqstUserId;
	}

	public void setDicRqstUserId(String dicRqstUserId) {
		this.dicRqstUserId = dicRqstUserId;
	}
	@Override
	public String toString() {
		return "WaqDic [wdName=" + wdName + ", dicOrgn=" + dicOrgn + ", ennm=" + ennm + ", dicDesc=" + dicDesc + ", Level="
				+ Level + ", FontColor=" + FontColor + ", BackColor=" + BackColor + ", pageNum=" + pageNum
				+ ", onePageRow=" + onePageRow + "]";
	}

	
	


}