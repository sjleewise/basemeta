package kr.wise.commons.sysmgmt.dept.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaDept extends CommonVo {


	private String deptId;

//    private Date expDtm;
//
//    private Date strDtm;

    private String deptNm;

    private String uppDeptId;

    private Integer deptLvl;
    
//    private String objDescn;
//    
//    private Integer objVers;
//    
//    private String regTypCd;
//    
//    private Date writDtm;
//    
//    private String writUserId;
    
    private String allDeptNm;
    
    private String uppDeptNm;
    
	public String getUppDeptNm() {
		return uppDeptNm;
	}

	public void setUppDeptNm(String uppDeptNm) {
		this.uppDeptNm = uppDeptNm;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
//	@Override
//	public Date getExpDtm() {
//		return expDtm;
//	}
//	@Override
//	public void setExpDtm(Date expDtm) {
//		this.expDtm = expDtm;
//	}
//	@Override
//	public Date getStrDtm() {
//		return strDtm;
//	}
//	@Override
//	public void setStrDtm(Date strDtm) {
//		this.strDtm = strDtm;
//	}

	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public String getUppDeptId() {
		return uppDeptId;
	}

	public void setUppDeptId(String uppDeptId) {
		this.uppDeptId = uppDeptId;
	}

	public Integer getDeptLvl() {
		return deptLvl;
	}

	public void setDeptLvl(Integer deptLvl) {
		this.deptLvl = deptLvl;
	}
//	@Override
//	public String getObjDescn() {
//		return objDescn;
//	}
//	@Override
//	public void setObjDescn(String objDescn) {
//		this.objDescn = objDescn;
//	}
//
//	public Integer getObjVers() {
//		return objVers;
//	}
//
//	public void setObjVers(Integer objVers) {
//		this.objVers = objVers;
//	}
//
//	public String getRegTypCd() {
//		return regTypCd;
//	}
//
//	public void setRegTypCd(String regTypCd) {
//		this.regTypCd = regTypCd;
//	}
//
//	public Date getWritDtm() {
//		return writDtm;
//	}
//
//	public void setWritDtm(Date writDtm) {
//		this.writDtm = writDtm;
//	}
//
//	public String getWritUserId() {
//		return writUserId;
//	}
//
//	public void setWritUserId(String writUserId) {
//		this.writUserId = writUserId;
//	}

	public String getAllDeptNm() {
		return allDeptNm;
	}

	public void setAllDeptNm(String allDeptNm) {
		this.allDeptNm = allDeptNm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaDept [deptId=").append(deptId).append(", deptNm=")
				.append(deptNm).append(", uppDeptId=").append(uppDeptId)
				.append(", deptLvl=").append(deptLvl).append(", allDeptNm=")
				.append(allDeptNm).append("]");
		return builder.toString();
	}
	
	
  


}