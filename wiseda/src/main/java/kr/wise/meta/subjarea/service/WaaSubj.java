package kr.wise.meta.subjarea.service;

import kr.wise.commons.cmm.CommonVo;


public class WaaSubj extends CommonVo {
    private String subjId;		//주제영역 ID

    private String subjLnm;		//주제영역 논리명

    private String subjPnm;		//주제영역 물리명

    private String subjAbrNm;	//주제영역 약어명

    private String uppSubjId;	//상위주제영역 ID

    private Short subjLvl;		//주제영역 레벨

    private String stdAplYn;	//표준적용여부

    private String lecyDcd;		//레거시 코드 여부

    private String sysAreaId;	//시스템영역 ID
    
    private String sysAreaLnm;	//시스템영역명

    private String fullPath; //주제영역경로

    private String uppSubjNm; // 상위주제영역명
    
    private String reaTblCnt; //구현테이블수
    
    private String noStdTblCnt; //비표준테이블수
    
    private String dbmsTypCd ; 

    
    //주제영역 권한 추가
    private String ownerYn ; // 팀장(소유자)(Y)/사용자(N)
    
    private String ownerCheck;	// 권한유무   

	private String subjOwnerId;

	private String userId;
	private String userNm;
	private String userEmailAddr;
	
	private String subjBizDcd;

	private String rqstResn;
	
	
	private String deptNm;
		
	private String stndAsrt;
   
   
    public String getSubjOwnerId() {
		return subjOwnerId;
	}

	public void setSubjOwnerId(String subjOwnerId) {
		this.subjOwnerId = subjOwnerId;
	}

	public String getUserEmailAddr() {
		return userEmailAddr;
	}

	public void setUserEmailAddr(String userEmailAddr) {
		this.userEmailAddr = userEmailAddr;
	}

	public String getSubjBizDcd() {
		return subjBizDcd;
	}

	public void setSubjBizDcd(String subjBizDcd) {
		this.subjBizDcd = subjBizDcd;
	}

	public String getRqstResn() {
		return rqstResn;
	}

	public void setRqstResn(String rqstResn) {
		this.rqstResn = rqstResn;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getOwnerYn() {
		return ownerYn;
	}

	public void setOwnerYn(String ownerYn) {
		this.ownerYn = ownerYn;
	}

	public String getOwnerCheck() {
		return ownerCheck;
	}

	public void setOwnerCheck(String ownerCheck) {
		this.ownerCheck = ownerCheck;
	}

	public String getUppSubjNm() {
		return uppSubjNm;
	}

	public void setUppSubjNm(String uppSubjNm) {
		this.uppSubjNm = uppSubjNm;
	}

	public String getSubjId() {
        return subjId;
    }

    public void setSubjId(String subjId) {
        this.subjId = subjId;
    }

    public String getSubjLnm() {
        return subjLnm;
    }

    public void setSubjLnm(String subjLnm) {
        this.subjLnm = subjLnm;
    }

    public String getSubjPnm() {
        return subjPnm;
    }

    public void setSubjPnm(String subjPnm) {
        this.subjPnm = subjPnm;
    }

    public String getSubjAbrNm() {
        return subjAbrNm;
    }

    public void setSubjAbrNm(String subjAbrNm) {
        this.subjAbrNm = subjAbrNm;
    }

    public String getUppSubjId() {
        return uppSubjId;
    }

    public void setUppSubjId(String uppSubjId) {
        this.uppSubjId = uppSubjId;
    }

    public Short getSubjLvl() {
        return subjLvl;
    }

    public void setSubjLvl(Short subjLvl) {
        this.subjLvl = subjLvl;
    }

    public String getStdAplYn() {
        return stdAplYn;
    }

    public void setStdAplYn(String stdAplYn) {
        this.stdAplYn = stdAplYn;
    }

    public String getLecyDcd() {
        return lecyDcd;
    }

    public void setLecyDcd(String lecyDcd) {
        this.lecyDcd = lecyDcd;
    }

    public String getSysAreaId() {
        return sysAreaId;
    }

    public void setSysAreaId(String sysAreaId) {
        this.sysAreaId = sysAreaId;
    }

	public String getReaTblCnt() {
		return reaTblCnt;
	}

	public void setReaTblCnt(String reaTblCnt) {
		this.reaTblCnt = reaTblCnt;
	}

	public String getNoStdTblCnt() {
		return noStdTblCnt;
	}

	public void setNoStdTblCnt(String noStdTblCnt) {
		this.noStdTblCnt = noStdTblCnt;
	}



	@Override
	public String getFullPath() {
		return fullPath;
	}

	@Override
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	
	public String getSysAreaLnm() {
		return sysAreaLnm;
	}

	public void setSysAreaLnm(String sysAreaLnm) {
		this.sysAreaLnm = sysAreaLnm;
	}
	

	public String getDbmsTypCd() {
		return dbmsTypCd;
	}

	public void setDbmsTypCd(String dbmsTypCd) {
		this.dbmsTypCd = dbmsTypCd;
	}
	
	public String getStndAsrt() {
		return stndAsrt;
	}

	public void setStndAsrt(String stndAsrt) {
		this.stndAsrt = stndAsrt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaSubj [subjId=").append(subjId).append(", subjLnm=")
				.append(subjLnm).append(", subjPnm=").append(subjPnm)
				.append(", subjAbrNm=").append(subjAbrNm)
				.append(", uppSubjId=").append(uppSubjId).append(", subjLvl=")
				.append(subjLvl).append(", stdAplYn=").append(stdAplYn)
				.append(", lecyDcd=").append(lecyDcd).append(", sysAreaId=")
				.append(sysAreaId).append(", fullPath=").append(fullPath)
				.append(", uppSubjNm=").append(uppSubjNm)
				.append(", reaTblCnt=").append(reaTblCnt)
				.append(", noStdTblCnt=").append(noStdTblCnt).append("]");
		return builder.toString();
	}
	
	
}