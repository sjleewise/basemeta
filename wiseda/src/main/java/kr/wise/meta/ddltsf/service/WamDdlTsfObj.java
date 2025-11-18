/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : WamDdlTsfObj.java
 * 2. Package : kr.wise.meta.ddltsf.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 8. 23. 오후 4:58:23
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 8. 23. :            : 신규 개발.
 */
package kr.wise.meta.ddltsf.service;

import kr.wise.meta.ddl.service.WamDdlTbl;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : WamDdlTsfObj.java
 * 3. Package  : kr.wise.meta.ddltsf.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 8. 23. 오후 4:58:23
 * </PRE>
 */
public class WamDdlTsfObj extends WamDdlTbl {
	
	private String tsfStep;
	private String tgtDbConnTrgPnm;
	private String tgtDbSchPnm;
	private String srcDbConnTrgPnm;
	private String srcDbSchPnm;
	private String tgtDbConnTrgId;
	private String tgtDbSchId;
	private String srcDbConnTrgId;
	private String srcDbSchId;
	
	public String getTsfStep() {
		return tsfStep;
	}
	public void setTsfStep(String tsfStep) {
		this.tsfStep = tsfStep;
	}
	public String getTgtDbConnTrgPnm() {
		return tgtDbConnTrgPnm;
	}
	public void setTgtDbConnTrgPnm(String tgtDbConnTrgPnm) {
		this.tgtDbConnTrgPnm = tgtDbConnTrgPnm;
	}
	public String getTgtDbSchPnm() {
		return tgtDbSchPnm;
	}
	public void setTgtDbSchPnm(String tgtDbSchPnm) {
		this.tgtDbSchPnm = tgtDbSchPnm;
	}
	public String getSrcDbConnTrgPnm() {
		return srcDbConnTrgPnm;
	}
	public void setSrcDbConnTrgPnm(String srcDbConnTrgPnm) {
		this.srcDbConnTrgPnm = srcDbConnTrgPnm;
	}
	public String getSrcDbSchPnm() {
		return srcDbSchPnm;
	}
	public void setSrcDbSchPnm(String srcDbSchPnm) {
		this.srcDbSchPnm = srcDbSchPnm;
	}
	public String getTgtDbConnTrgId() {
		return tgtDbConnTrgId;
	}
	public void setTgtDbConnTrgId(String tgtDbConnTrgId) {
		this.tgtDbConnTrgId = tgtDbConnTrgId;
	}
	public String getTgtDbSchId() {
		return tgtDbSchId;
	}
	public void setTgtDbSchId(String tgtDbSchId) {
		this.tgtDbSchId = tgtDbSchId;
	}
	public String getSrcDbConnTrgId() {
		return srcDbConnTrgId;
	}
	public void setSrcDbConnTrgId(String srcDbConnTrgId) {
		this.srcDbConnTrgId = srcDbConnTrgId;
	}
	public String getSrcDbSchId() {
		return srcDbSchId;
	}
	public void setSrcDbSchId(String srcDbSchId) {
		this.srcDbSchId = srcDbSchId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WamDdlTsfObj [tsfStep=").append(tsfStep)
				.append(", tgtDbConnTrgPnm=").append(tgtDbConnTrgPnm)
				.append(", tgtDbSchPnm=").append(tgtDbSchPnm)
				.append(", srcDbConnTrgPnm=").append(srcDbConnTrgPnm)
				.append(", srcDbSchPnm=").append(srcDbSchPnm)
				.append(", tgtDbConnTrgId=").append(tgtDbConnTrgId)
				.append(", tgtDbSchId=").append(tgtDbSchId)
				.append(", srcDbConnTrgId=").append(srcDbConnTrgId)
				.append(", srcDbSchId=").append(srcDbSchId).append("]");
		return builder.toString()+super.toString();
	}
	
	
	
}
