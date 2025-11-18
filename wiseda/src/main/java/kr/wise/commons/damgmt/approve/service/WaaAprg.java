package kr.wise.commons.damgmt.approve.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaAprg extends CommonVo{
	/** 결재그룹 ID */
	private String aprgId;
	
	private String aprgRnk;
//	/** 만료시각 */
//	private Date expDtm;
//	
//	/** 시작시각 */
//	private Date strDtm;




	/** 결재그룹명 */
	private String aprgNm;
	
	/** 객체설명 */
//	private String objDescn;
//	
//	/** 객체버전 */
//	private Integer objVers;
//	
//	/** 등록구분코드 */
//	private String regTypCd;
	
//	/** 작성시각 */
//	private Date writDtm;
//	
//	/** 작성자ID */
//	private String writUserId;

	public String getAprgId() {
		return aprgId;
	}

	public void setAprgId(String aprgId) {
		this.aprgId = aprgId;
	}

//	public Date getExpDtm() {
//		return expDtm;
//	}
//
//	public void setExpDtm(Date expDtm) {
//		this.expDtm = expDtm;
//	}
//
//	public Date getStrDtm() {
//		return strDtm;
//	}
//
//	public void setStrDtm(Date strDtm) {
//		this.strDtm = strDtm;
//	}

	public String getAprgNm() {
		return aprgNm;
	}

	public void setAprgNm(String aprgNm) {
		this.aprgNm = aprgNm;
	}
	
	public String getAprgRnk() {
		return aprgRnk;
	}

	public void setAprgRnk(String aprgRnk) {
		this.aprgRnk = aprgRnk;
	}
	

//	public String getObjDescn() {
//		return objDescn;
//	}
//
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaAprg [aprgId=").append(aprgId).append(", aprgNm=")
				.append(aprgNm).append("]");
		return builder.toString();
	}


	
	
}
