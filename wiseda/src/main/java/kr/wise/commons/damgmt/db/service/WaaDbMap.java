package kr.wise.commons.damgmt.db.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaDbMap extends CommonVo {
	private String dbMapId;
	
    private String srcDbSchId;

    private String tgtDbSchId;
    
    /** 소스 스키마물리명 insomnia */
    private String srcDbSchPnm;
    /** 타겟 스키마물리명 insomnia */
    private String tgtDbSchPnm;

    /** 소스 DBMS물리명 insomnia */
    private String srcDbConnTrgPnm;
    /** 타겟 DBMS물리명 insomnia */
    private String tgtDbConnTrgPnm;

    /** 소스 DDL대상구분 insomnia */
    private String srcDdlTrgDcd;
    /** 타겟 DDL대상구분 insomnia */
    private String tgtDdlTrgDcd;
    /** 소스 DDL대상구분 yeonho */
    private String srcDdlTrgDcdNm;
    /** 타겟 DDL대상구분 yeonho */
    private String tgtDdlTrgDcdNm;

    private String srcDbConnTrgId;
    private String tgtDbConnTrgId;
    
    
  

//	private Date expDtm;


    /** DDL이관 대상여부 **/
    private String ddlTsfYn;

//    private String objDescn;
//
//    private Integer objVers;
//
//    private String regTypCd;
//    
//    private String writUserId;
//    private String writUserNm;


	public String getDbMapId() {
		return dbMapId;
	}

	public void setDbMapId(String dbMapId) {
		this.dbMapId = dbMapId;
	}
    
    
	/**
	 * @return the srcDbSchId
	 */
	public String getSrcDbSchId() {
		return srcDbSchId;
	}


	/**
	 * @param srcDbSchId the srcDbSchId to set
	 */
	public void setSrcDbSchId(String srcDbSchId) {
		this.srcDbSchId = srcDbSchId;
	}

	/**
	 * @return the tgtDbSchId
	 */
	public String getTgtDbSchId() {
		return tgtDbSchId;
	}

	/**
	 * @param tgtDbSchId the tgtDbSchId to set
	 */
	public void setTgtDbSchId(String tgtDbSchId) {
		this.tgtDbSchId = tgtDbSchId;
	}

    public String getSrcDbConnTrgId() {
		return srcDbConnTrgId;
	}

	public void setSrcDbConnTrgId(String srcDbConnTrgId) {
		this.srcDbConnTrgId = srcDbConnTrgId;
	}

	public String getTgtDbConnTrgId() {
		return tgtDbConnTrgId;
	}

	public void setTgtDbConnTrgId(String tgtDbConnTrgId) {
		this.tgtDbConnTrgId = tgtDbConnTrgId;
	}
	
	/**
	 * @return the srcDbSchPnm
	 */
	public String getSrcDbSchPnm() {
		return srcDbSchPnm;
	}

	/**
	 * @param srcDbSchPnm the srcDbSchPnm to set
	 */
	public void setSrcDbSchPnm(String srcDbSchPnm) {
		this.srcDbSchPnm = srcDbSchPnm;
	}

	/**
	 * @return the tgtDbSchPnm
	 */
	public String getTgtDbSchPnm() {
		return tgtDbSchPnm;
	}

	/**
	 * @param tgtDbSchPnm the tgtDbSchPnm to set
	 */
	public void setTgtDbSchPnm(String tgtDbSchPnm) {
		this.tgtDbSchPnm = tgtDbSchPnm;
	}

	/**
	 * @return the srcDbConnTrgPnm
	 */
	public String getSrcDbConnTrgPnm() {
		return srcDbConnTrgPnm;
	}

	/**
	 * @param srcDbConnTrgPnm the srcDbConnTrgPnm to set
	 */
	public void setSrcDbConnTrgPnm(String srcDbConnTrgPnm) {
		this.srcDbConnTrgPnm = srcDbConnTrgPnm;
	}

	/**
	 * @return the tgtDbConnTrgPnm
	 */
	public String getTgtDbConnTrgPnm() {
		return tgtDbConnTrgPnm;
	}

	/**
	 * @param tgtDbConnTrgPnm the tgtDbConnTrgPnm to set
	 */
	public void setTgtDbConnTrgPnm(String tgtDbConnTrgPnm) {
		this.tgtDbConnTrgPnm = tgtDbConnTrgPnm;
	}

	/**
	 * @return the srcDdlTrgDcd
	 */
	public String getSrcDdlTrgDcd() {
		return srcDdlTrgDcd;
	}

	/**
	 * @param srcDdlTrgDcd the srcDdlTrgDcd to set
	 */
	public void setSrcDdlTrgDcd(String srcDdlTrgDcd) {
		this.srcDdlTrgDcd = srcDdlTrgDcd;
	}

	/**
	 * @return the tgtDdlTrgDcd
	 */
	public String getTgtDdlTrgDcd() {
		return tgtDdlTrgDcd;
	}

	/**
	 * @param tgtDdlTrgDcd the tgtDdlTrgDcd to set
	 */
	public void setTgtDdlTrgDcd(String tgtDdlTrgDcd) {
		this.tgtDdlTrgDcd = tgtDdlTrgDcd;
	}

	/**
	 * @return the expDtm
	 */
//	public Date getExpDtm() {
//		return expDtm;
//	}
//
//	/**
//	 * @param expDtm the expDtm to set
//	 */
//	public void setExpDtm(Date expDtm) {
//		this.expDtm = expDtm;
//	}


	/**
	 * @return the ddlTsfYn
	 */
	public String getDdlTsfYn() {
		return ddlTsfYn;
	}

	/**
	 * @param ddlTsfYn the ddlTsfYn to set
	 */
	public void setDdlTsfYn(String ddlTsfYn) {
		this.ddlTsfYn = ddlTsfYn;
	}

//	/**
//	 * @return the objDescn
//	 */
//	public String getObjDescn() {
//		return objDescn;
//	}
//
//	/**
//	 * @param objDescn the objDescn to set
//	 */
//	public void setObjDescn(String objDescn) {
//		this.objDescn = objDescn;
//	}
//
//	/**
//	 * @return the objVers
//	 */
//	public Integer getObjVers() {
//		return objVers;
//	}
//
//	/**
//	 * @param objVers the objVers to set
//	 */
//	public void setObjVers(Integer objVers) {
//		this.objVers = objVers;
//	}
//
//	/**
//	 * @return the regTypCd
//	 */
//	public String getRegTypCd() {
//		return regTypCd;
//	}
//
//	/**
//	 * @param regTypCd the regTypCd to set
//	 */
//	public void setRegTypCd(String regTypCd) {
//		this.regTypCd = regTypCd;
//	}
//
//	
//
//	/**
//	 * @return the writUserId
//	 */
//	public String getWritUserId() {
//		return writUserId;
//	}
//
//	/**
//	 * @param writUserId the writUserId to set
//	 */
//	public void setWritUserId(String writUserId) {
//		this.writUserId = writUserId;
//	}
//	
//	
//
//	/**
//	 * @return the writUserNm
//	 */
//	public String getWritUserNm() {
//		return writUserNm;
//	}
//
//	/**
//	 * @param writUserNm the writUserNm to set
//	 */
//	public void setWritUserNm(String writUserNm) {
//		this.writUserNm = writUserNm;
//	}

	public String getSrcDdlTrgDcdNm() {
		return srcDdlTrgDcdNm;
	}

	public void setSrcDdlTrgDcdNm(String srcDdlTrgDcdNm) {
		this.srcDdlTrgDcdNm = srcDdlTrgDcdNm;
	}

	public String getTgtDdlTrgDcdNm() {
		return tgtDdlTrgDcdNm;
	}

	public void setTgtDdlTrgDcdNm(String tgtDdlTrgDcdNm) {
		this.tgtDdlTrgDcdNm = tgtDdlTrgDcdNm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaaDbMap [srcDbSchId=").append(srcDbSchId)
				.append(", tgtDbSchId=").append(tgtDbSchId)
				.append(", srcDbSchPnm=").append(srcDbSchPnm)
				.append(", tgtDbSchPnm=").append(tgtDbSchPnm)
				.append(", srcDbConnTrgPnm=").append(srcDbConnTrgPnm)
				.append(", tgtDbConnTrgPnm=").append(tgtDbConnTrgPnm)
				.append(", srcDdlTrgDcd=").append(srcDdlTrgDcd)
				.append(", tgtDdlTrgDcd=").append(tgtDdlTrgDcd)
				.append(", srcDdlTrgDcdNm=").append(srcDdlTrgDcdNm)
				.append(", tgtDdlTrgDcdNm=").append(tgtDdlTrgDcdNm)
				.append(", srcDbConnTrgId=").append(srcDbConnTrgId)
				.append(", tgtDbConnTrgId=").append(tgtDbConnTrgId)
				.append(", ddlTsfYn=").append(ddlTsfYn).append("]");
		return builder.toString()+super.toString();
	}
	

    
}