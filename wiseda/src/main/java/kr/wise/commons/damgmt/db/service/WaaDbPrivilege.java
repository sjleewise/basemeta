package kr.wise.commons.damgmt.db.service;


public class WaaDbPrivilege extends WaaDbRole {
    private String privilegeId;

    private String roleId;

	private String dbConnTrgId;

    private String dbSchId;

    private String selectYn;

    private String insertYn;

    private String updateYn;

    private String deleteYn;

    private String applyRuleTgt;

    private String applyRuleMthd;

    private String applyRuleCont;

    private String grantedDbConnTrgId;
    
    

	//DDL 생성 필요
    private String srcDbSchPnm;
    
    private String tgtRolePnm;
    
    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

//    public String getRoleId() {
//        return roleId;
//    }

//    public void setRoleId(String roleId) {
//        this.roleId = roleId;
//    }

//    public String getDbConnTrgId() {
//        return dbConnTrgId;
//    }

//    public void setDbConnTrgId(String dbConnTrgId) {
//        this.dbConnTrgId = dbConnTrgId;
//    }

    public String getDbSchId() {
        return dbSchId;
    }

    public void setDbSchId(String dbSchId) {
        this.dbSchId = dbSchId;
    }

    public String getSelectYn() {
        return selectYn;
    }

    public void setSelectYn(String selectYn) {
        this.selectYn = selectYn;
    }

    public String getInsertYn() {
        return insertYn;
    }

    public void setInsertYn(String insertYn) {
        this.insertYn = insertYn;
    }

    public String getUpdateYn() {
        return updateYn;
    }

    public void setUpdateYn(String updateYn) {
        this.updateYn = updateYn;
    }

    public String getDeleteYn() {
        return deleteYn;
    }

    public void setDeleteYn(String deleteYn) {
        this.deleteYn = deleteYn;
    }

    public String getApplyRuleTgt() {
        return applyRuleTgt;
    }

    public void setApplyRuleTgt(String applyRuleTgt) {
        this.applyRuleTgt = applyRuleTgt;
    }

    public String getApplyRuleMthd() {
        return applyRuleMthd;
    }

    public void setApplyRuleMthd(String applyRuleMthd) {
        this.applyRuleMthd = applyRuleMthd;
    }

    public String getApplyRuleCont() {
        return applyRuleCont;
    }

    public void setApplyRuleCont(String applyRuleCont) {
        this.applyRuleCont = applyRuleCont;
    }

	public String getSrcDbSchPnm() {
		return srcDbSchPnm;
	}

	public void setSrcDbSchPnm(String srcDbSchPnm) {
		this.srcDbSchPnm = srcDbSchPnm;
	}

	public String getTgtRolePnm() {
		return tgtRolePnm;
	}

	public void setTgtRolePnm(String tgtRolePnm) {
		this.tgtRolePnm = tgtRolePnm;
	}
	
	public String getGrantedDbConnTrgId() {
		return grantedDbConnTrgId;
	}

	public void setGrantedDbConnTrgId(String grantedDbConnTrgId) {
		this.grantedDbConnTrgId = grantedDbConnTrgId;
	}

    public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaDbPrivilege [privilegeId=").append(privilegeId)
				.append(", dbSchId=").append(dbSchId).append(", selectYn=")
				.append(selectYn).append(", insertYn=").append(insertYn)
				.append(", updateYn=").append(updateYn).append(", deleteYn=")
				.append(deleteYn).append(", applyRuleTgt=")
				.append(applyRuleTgt).append(", applyRuleMthd=")
				.append(applyRuleMthd).append(", applyRuleCont=")
				.append(applyRuleCont).append(", grantedDbConnTrgId=")
				.append(grantedDbConnTrgId).append(", srcDbSchPnm=")
				.append(srcDbSchPnm).append(", tgtRolePnm=").append(tgtRolePnm)
				.append("]");
		return builder.toString()+super.toString();
	}
	
	
   
}