/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DataFlowColMapVO.java
 * 2. Package : kr.wise.meta.effect.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 6. 28. 오후 2:54:11
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 6. 28. :            : 신규 개발.
 */
package kr.wise.meta.effect.service;

import kr.wise.commons.cmm.CommonVo;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DataFlowColMapVO.java
 * 3. Package  : kr.wise.meta.effect.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 28. 오후 2:54:11
 * </PRE>
 */
public class DataFlowColMapVO extends CommonVo {

	private String srcDbPnm 	    ;
	private String srcDbSch 	    ;
	private String srcSchPnm 	    ;
	private String srcTblPnm 		;
	private String srcColPnm 		;
	private String srcDataYype 	;
	private String dbPnm 		    ;
	private String dbSch 		    ;
	private String tblPnm 			;
	private String colPnm 			;
	private String dataType 		;
	private String tgtDbPnm 	    ;
	private String tgtSchPnm 	    ;
	private String tgtDbSch 	    ;
	private String tgtTblPnm 		;
	private String tgtColPnm 		;
	private String tgtDataType 	;
	
	private String tgtTblId			;
	private String tgtColId			;
	private String srcTblId			;
	private String srcColId			;
	private String dfwColId			;
	/**
	 * @return the srcDbPnm
	 */
	public String getSrcDbPnm() {
		return srcDbPnm;
	}
	/**
	 * @param srcDbPnm the srcDbPnm to set
	 */
	public void setSrcDbPnm(String srcDbPnm) {
		this.srcDbPnm = srcDbPnm;
	}
	/**
	 * @return the srcDbSch
	 */
	public String getSrcDbSch() {
		return srcDbSch;
	}
	/**
	 * @param srcDbSch the srcDbSch to set
	 */
	public void setSrcDbSch(String srcDbSch) {
		this.srcDbSch = srcDbSch;
	}
	/**
	 * @return the srcTblPnm
	 */
	public String getSrcTblPnm() {
		return srcTblPnm;
	}
	/**
	 * @param srcTblPnm the srcTblPnm to set
	 */
	public void setSrcTblPnm(String srcTblPnm) {
		this.srcTblPnm = srcTblPnm;
	}
	/**
	 * @return the srcColPnm
	 */
	public String getSrcColPnm() {
		return srcColPnm;
	}
	/**
	 * @param srcColPnm the srcColPnm to set
	 */
	public void setSrcColPnm(String srcColPnm) {
		this.srcColPnm = srcColPnm;
	}
	/**
	 * @return the srcDataYype
	 */
	public String getSrcDataYype() {
		return srcDataYype;
	}
	/**
	 * @param srcDataYype the srcDataYype to set
	 */
	public void setSrcDataYype(String srcDataYype) {
		this.srcDataYype = srcDataYype;
	}
	/**
	 * @return the dbPnm
	 */
	public String getDbPnm() {
		return dbPnm;
	}
	/**
	 * @param dbPnm the dbPnm to set
	 */
	public void setDbPnm(String dbPnm) {
		this.dbPnm = dbPnm;
	}
	/**
	 * @return the dbSch
	 */
	public String getDbSch() {
		return dbSch;
	}
	/**
	 * @param dbSch the dbSch to set
	 */
	public void setDbSch(String dbSch) {
		this.dbSch = dbSch;
	}
	/**
	 * @return the tblPnm
	 */
	public String getTblPnm() {
		return tblPnm;
	}
	/**
	 * @param tblPnm the tblPnm to set
	 */
	public void setTblPnm(String tblPnm) {
		this.tblPnm = tblPnm;
	}
	/**
	 * @return the colPnm
	 */
	public String getColPnm() {
		return colPnm;
	}
	/**
	 * @param colPnm the colPnm to set
	 */
	public void setColPnm(String colPnm) {
		this.colPnm = colPnm;
	}
	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	/**
	 * @return the tgtDbPnm
	 */
	public String getTgtDbPnm() {
		return tgtDbPnm;
	}
	/**
	 * @param tgtDbPnm the tgtDbPnm to set
	 */
	public void setTgtDbPnm(String tgtDbPnm) {
		this.tgtDbPnm = tgtDbPnm;
	}
	/**
	 * @return the tgtDbSch
	 */
	public String getTgtDbSch() {
		return tgtDbSch;
	}
	/**
	 * @param tgtDbSch the tgtDbSch to set
	 */
	public void setTgtDbSch(String tgtDbSch) {
		this.tgtDbSch = tgtDbSch;
	}
	/**
	 * @return the tgtTblPnm
	 */
	public String getTgtTblPnm() {
		return tgtTblPnm;
	}
	/**
	 * @param tgtTblPnm the tgtTblPnm to set
	 */
	public void setTgtTblPnm(String tgtTblPnm) {
		this.tgtTblPnm = tgtTblPnm;
	}
	/**
	 * @return the tgtColPnm
	 */
	public String getTgtColPnm() {
		return tgtColPnm;
	}
	/**
	 * @param tgtColPnm the tgtColPnm to set
	 */
	public void setTgtColPnm(String tgtColPnm) {
		this.tgtColPnm = tgtColPnm;
	}
	/**
	 * @return the tgtDataType
	 */
	public String getTgtDataType() {
		return tgtDataType;
	}
	/**
	 * @param tgtDataType the tgtDataType to set
	 */
	public void setTgtDataType(String tgtDataType) {
		this.tgtDataType = tgtDataType;
	}
	public String getTgtTblId() {
		return tgtTblId;
	}
	public void setTgtTblId(String tgtTblId) {
		this.tgtTblId = tgtTblId;
	}
	public String getTgtColId() {
		return tgtColId;
	}
	public void setTgtColId(String tgtColId) {
		this.tgtColId = tgtColId;
	}
	public String getSrcTblId() {
		return srcTblId;
	}
	public void setSrcTblId(String srcTblId) {
		this.srcTblId = srcTblId;
	}
	public String getSrcColId() {
		return srcColId;
	}
	public void setSrcColId(String srcColId) {
		this.srcColId = srcColId;
	}
	public String getDfwColId() {
		return dfwColId;
	}
	public void setDfwColId(String dfwColId) {
		this.dfwColId = dfwColId;
	}
	public String getSrcSchPnm() {
		return srcSchPnm;
	}
	public void setSrcSchPnm(String srcSchPnm) {
		this.srcSchPnm = srcSchPnm;
	}
	public String getTgtSchPnm() {
		return tgtSchPnm;
	}
	public void setTgtSchPnm(String tgtSchPnm) {
		this.tgtSchPnm = tgtSchPnm;
	}

}
