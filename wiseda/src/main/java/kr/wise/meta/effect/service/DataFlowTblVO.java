/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DataFlowTblVO.java
 * 2. Package : kr.wise.meta.effect.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 6. 27. 오후 7:31:35
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 6. 27. :            : 신규 개발.
 */
package kr.wise.meta.effect.service;

import kr.wise.commons.cmm.CommonVo;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DataFlowTblVO.java
 * 3. Package  : kr.wise.meta.effect.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 27. 오후 7:31:35
 * </PRE>
 */
public class DataFlowTblVO extends CommonVo {

    private String ddlTblId;

    private String ddlTblPnm;

    private String ddlTblLnm;

    private String dbSchId;

    private String dbSchPnm;

    private String dbConnTrgId;

    private String dbConnTrgPnm;

    private String ddlColPnm;
    
    private String dataFlowFlag;

	/**
	 * @return the ddlTblId
	 */
	public String getDdlTblId() {
		return ddlTblId;
	}

	/**
	 * @param ddlTblId the ddlTblId to set
	 */
	public void setDdlTblId(String ddlTblId) {
		this.ddlTblId = ddlTblId;
	}

	/**
	 * @return the ddlTblPnm
	 */
	public String getDdlTblPnm() {
		return ddlTblPnm;
	}

	/**
	 * @param ddlTblPnm the ddlTblPnm to set
	 */
	public void setDdlTblPnm(String ddlTblPnm) {
		this.ddlTblPnm = ddlTblPnm;
	}

	/**
	 * @return the ddlTblLnm
	 */
	public String getDdlTblLnm() {
		return ddlTblLnm;
	}

	/**
	 * @param ddlTblLnm the ddlTblLnm to set
	 */
	public void setDdlTblLnm(String ddlTblLnm) {
		this.ddlTblLnm = ddlTblLnm;
	}

	/**
	 * @return the dbSchId
	 */
	public String getDbSchId() {
		return dbSchId;
	}

	/**
	 * @param dbSchId the dbSchId to set
	 */
	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}

	/**
	 * @return the dbSchPnm
	 */
	public String getDbSchPnm() {
		return dbSchPnm;
	}

	/**
	 * @param dbSchPnm the dbSchPnm to set
	 */
	public void setDbSchPnm(String dbSchPnm) {
		this.dbSchPnm = dbSchPnm;
	}

	/**
	 * @return the dbConnTrgId
	 */
	public String getDbConnTrgId() {
		return dbConnTrgId;
	}

	/**
	 * @param dbConnTrgId the dbConnTrgId to set
	 */
	public void setDbConnTrgId(String dbConnTrgId) {
		this.dbConnTrgId = dbConnTrgId;
	}

	/**
	 * @return the dbConnTrgPnm
	 */
	public String getDbConnTrgPnm() {
		return dbConnTrgPnm;
	}

	/**
	 * @param dbConnTrgPnm the dbConnTrgPnm to set
	 */
	public void setDbConnTrgPnm(String dbConnTrgPnm) {
		this.dbConnTrgPnm = dbConnTrgPnm;
	}

	/** insomnia */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataFlowTblVO [ddlTblId=").append(ddlTblId)
				.append(", ddlTblPnm=").append(ddlTblPnm)
				.append(", ddlTblLnm=").append(ddlTblLnm).append(", dbSchId=")
				.append(dbSchId).append(", dbSchPnm=").append(dbSchPnm)
				.append(", dbConnTrgId=").append(dbConnTrgId)
				.append(", dbConnTrgPnm=").append(dbConnTrgPnm).append("]");
		return builder.toString();
	}

	/**
	 * @return the ddlColPnm
	 */
	public String getDdlColPnm() {
		return ddlColPnm;
	}

	/**
	 * @param ddlColPnm the ddlColPnm to set
	 */
	public void setDdlColPnm(String ddlColPnm) {
		this.ddlColPnm = ddlColPnm;
	}

	public String getDataFlowFlag() {
		return dataFlowFlag;
	}

	public void setDataFlowFlag(String dataFlowFlag) {
		this.dataFlowFlag = dataFlowFlag;
	}


}
