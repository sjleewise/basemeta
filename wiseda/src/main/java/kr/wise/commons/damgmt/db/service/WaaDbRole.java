package kr.wise.commons.damgmt.db.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaDbRole extends CommonVo{
    private String roleId;

    private String dbConnTrgId;

    private String rolePnm;

    private String roleLnm;


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

    public String getRolePnm() {
        return rolePnm;
    }

    public void setRolePnm(String rolePnm) {
        this.rolePnm = rolePnm;
    }

    public String getRoleLnm() {
        return roleLnm;
    }

    public void setRoleLnm(String roleLnm) {
        this.roleLnm = roleLnm;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaDbRole [roleId=").append(roleId)
				.append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", rolePnm=").append(rolePnm).append(", roleLnm=")
				.append(roleLnm).append("]");
		return builder.toString()+super.toString();
	}
    
    
}