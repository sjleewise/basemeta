package kr.wise.commons.user.service;

import kr.wise.commons.user.WaaUserg;

public class WaaUser extends WaaUserg {
    private String userId;


//    private String usergLnm;


    private String loginAcId;

    private String loginAcPwd;

    private String userNm;

//    private String usergId;

    private String deptId;

    private String deptNm;

    private String deptPath;

    private String jgdNm;

    private String userTelno;

    private String userHtelno;

    private String emailAddr;

    private String exclDwldAuthYn;

    private String idUseExpDt;

    private String pwdExpDt;

    private String aprvDcd;
    
    private String loginAcPwdEnc;
    
    private String saltKey;
    
    private int loginFailCount;
    
    private String isLock;
    
    private int lockCount;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}


    public String getLoginAcId() {
        return loginAcId;
    }

    public void setLoginAcId(String loginAcId) {
        this.loginAcId = loginAcId;
    }

    public String getLoginAcPwd() {
        return loginAcPwd;
    }

    public void setLoginAcPwd(String loginAcPwd) {
        this.loginAcPwd = loginAcPwd;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

//    public String getUsergId() {
//        return usergId;
//    }
//
//    public void setUsergId(String usergId) {
//        this.usergId = usergId;
//    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getJgdNm() {
        return jgdNm;
    }

    public void setJgdNm(String jgdNm) {
        this.jgdNm = jgdNm;
    }

    public String getUserTelno() {
        return userTelno;
    }

    public void setUserTelno(String userTelno) {
        this.userTelno = userTelno;
    }

    public String getUserHtelno() {
        return userHtelno;
    }

    public void setUserHtelno(String userHtelno) {
        this.userHtelno = userHtelno;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getExclDwldAuthYn() {
        return exclDwldAuthYn;
    }

    public void setExclDwldAuthYn(String exclDwldAuthYn) {
        this.exclDwldAuthYn = exclDwldAuthYn;
    }

    public String getIdUseExpDt() {
        return idUseExpDt;
    }

    public void setIdUseExpDt(String idUseExpDt) {
        this.idUseExpDt = idUseExpDt;
    }

    public String getPwdExpDt() {
        return pwdExpDt;
    }

    public void setPwdExpDt(String pwdExpDt) {
        this.pwdExpDt = pwdExpDt;
    }


    public String getAprvDcd() {
        return aprvDcd;
    }

    public void setAprvDcd(String aprvDcd) {
        this.aprvDcd = aprvDcd;
    }


//    public String getUsergLnm() {
//        return usergLnm;
//    }
//
//    public void setUsergLnm(String usergLnm) {
//        this.usergLnm = usergLnm;
//    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaUser [userId=").append(userId)
				.append(", loginAcId=").append(loginAcId)
				.append(", loginAcPwd=").append(loginAcPwd).append(", userNm=")
				.append(userNm).append(", deptId=").append(deptId)
				.append(", deptNm=").append(deptNm).append(", deptPath=")
				.append(deptPath).append(", jgdNm=").append(jgdNm)
				.append(", userTelno=").append(userTelno)
				.append(", userHtelno=").append(userHtelno)
				.append(", emailAddr=").append(emailAddr)
				.append(", exclDwldAuthYn=").append(exclDwldAuthYn)
				.append(", idUseExpDt=").append(idUseExpDt)
				.append(", pwdExpDt=").append(pwdExpDt).append(", aprvDcd=")
				.append(aprvDcd).append("]");
		return builder.toString()+super.toString();
	}

	/**
	 * @return the deptPath
	 */
	public String getDeptPath() {
		return deptPath;
	}

	/**
	 * @param deptPath the deptPath to set
	 */
	public void setDeptPath(String deptPath) {
		this.deptPath = deptPath;
	}

	public String getLoginAcPwdEnc() {
		return loginAcPwdEnc;
	}

	public void setLoginAcPwdEnc(String loginAcPwdEnc) {
		this.loginAcPwdEnc = loginAcPwdEnc;
	}

	public String getSaltKey() {
		return saltKey;
	}

	public void setSaltKey(String saltKey) {
		this.saltKey = saltKey;
	}

	public int getLoginFailCount() {
		return loginFailCount;
	}

	public void setLoginFailCount(int loginFailCount) {
		this.loginFailCount = loginFailCount;
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public int getLockCount() {
		return lockCount;
	}

	public void setLockCount(int lockCount) {
		this.lockCount = lockCount;
	}


}