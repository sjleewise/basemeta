package kr.wise.meta.stnd.service;

public class WapDvCanDic {

	/** 분할요청번호 */
    private String dvRqstNo;

    /** 사전논리명 */
    private String dicLnm;

    /** 사전물리명 */
    private String dicPnm;

    /** 사전구분 */
    private String dicDs;

    /** 요청번호 */
    private String rqstNo;

    /** 요청도메인명 */
    private String trgLnm;
    
    /** 요청도메인명 */
    private String trgPnm;
    
    
	/** 분할요청사용자ID */
    private String dvRqstUserId;
    
    private String dvEncYn;
    
    private String dvObjDescn;
    
    private String dvSeCd;
    
    //분할구분
    private String dvRqstDiv;
    
    private String dmngLnm; //도메인그룹논리명
    
    private String infotpLnm; //인포타입논리명
    
    private String dvRqstDivDmn; //분할구분(도메인)
    
    private String stndAsrt; //표준분류

	public String getStndAsrt() {
		return stndAsrt;
	}

	public void setStndAsrt(String stndAsrt) {
		this.stndAsrt = stndAsrt;
	}

	public String getDvRqstNo() {
        return dvRqstNo;
    }

    public void setDvRqstNo(String dvRqstNo) {
        this.dvRqstNo = dvRqstNo;
    }

    public String getDicLnm() {
        return dicLnm;
    }

    public void setDicLnm(String dicLnm) {
        this.dicLnm = dicLnm;
    }

    public String getDicPnm() {
        return dicPnm;
    }

    public void setDicPnm(String dicPnm) {
        this.dicPnm = dicPnm;
    }

    public String getDicDs() {
        return dicDs;
    }

    public void setDicDs(String dicDs) {
        this.dicDs = dicDs;
    }

	public String getDvRqstUserId() {
		return dvRqstUserId;
	}

	public void setDvRqstUserId(String dvRqstUserId) {
		this.dvRqstUserId = dvRqstUserId;
	}

	/**
	 * @return the rqstNo
	 */
	public String getRqstNo() {
		return rqstNo;
	}

	/**
	 * @param rqstNo the rqstNo to set
	 */
	public void setRqstNo(String rqstNo) {
		this.rqstNo = rqstNo;
	}

	/**
	 * @return the trgLnm
	 */
	public String getTrgLnm() {
		return trgLnm;
	}

	/**
	 * @param trgLnm the trgLnm to set
	 */
	public void setTrgLnm(String trgLnm) {
		this.trgLnm = trgLnm;
	}

	
	
	public String getTrgPnm() {
		return trgPnm;
	}

	public void setTrgPnm(String trgPnm) {
		this.trgPnm = trgPnm;
	}

	public String getDvEncYn() {
		return dvEncYn;
	}

	public void setDvEncYn(String dvEncYn) {
		this.dvEncYn = dvEncYn;
	}

	public String getDvObjDescn() {
		return dvObjDescn;
	}

	public void setDvObjDescn(String dvObjDescn) {
		this.dvObjDescn = dvObjDescn;
	}

	public String getDvSeCd() {
		return dvSeCd;
	}

	public void setDvSeCd(String dvSeCd) {
		this.dvSeCd = dvSeCd;
	}

	public String getDvRqstDiv() {
		return dvRqstDiv;
	}

	public void setDvRqstDiv(String dvRqstDiv) {
		this.dvRqstDiv = dvRqstDiv;
	}
		
	public String getInfotpLnm() {
		return infotpLnm;
	}

	public void setInfotpLnm(String infotpLnm) {
		this.infotpLnm = infotpLnm;
	}
		
	public String getDmngLnm() {
		return dmngLnm;
	}

	public void setDmngLnm(String dmngLnm) {
		this.dmngLnm = dmngLnm;
	}
		
	public String getDvRqstDivDmn() {
		return dvRqstDivDmn;
	}

	public void setDvRqstDivDmn(String dvRqstDivDmn) {
		this.dvRqstDivDmn = dvRqstDivDmn;
	}

	
	/** insomnia */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WapDvCanDic [dvRqstNo=").append(dvRqstNo)
				.append(", dicLnm=").append(dicLnm).append(", dicPnm=")
				.append(dicPnm).append(", dicDs=").append(dicDs)
				.append(", rqstNo=").append(rqstNo)
				.append(", trgLnm=").append(trgLnm).append(", trgPnm=").append(trgPnm)
				.append(", dvRqstDiv=").append(dvRqstDiv)
				.append("]");
		return builder.toString();
	}
}