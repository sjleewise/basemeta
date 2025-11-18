/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : OlapVO.java
 * 2. Package : kr.wise.meta.effect.service
 * 3. Comment :
 * 4. 작성자  : 유성열
 * 5. 작성일  : 2014. 7. 8. 15:04:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    유성열 : 2014. 7. 8. :            : 신규 개발.
 */
package kr.wise.meta.effect.service;

import kr.wise.commons.cmm.CommonVo;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : OlapVO.java
 * 3. Package  : kr.wise.meta.effect.service
 * 4. Comment  :
 * 5. 작성자   : 유성열
 * 6. 작성일   : 2014. 7. 8. 15:04:00
 * </PRE>
 */
public class OlapVO extends CommonVo {
   
	private String prjId;
	private String prjNm;
	private String orglDttm;
	private String updtDttm;
	private String description;
	private String reportNm;
	
	public String getPrjId() {
		return prjId;
	}
	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}
	public String getPrjNm() {
		return prjNm;
	}
	public void setPrjNm(String prjNm) {
		this.prjNm = prjNm;
	}
	public String getOrglDttm() {
		return orglDttm;
	}
	public void setOrglDttm(String orglDttm) {
		this.orglDttm = orglDttm;
	}
	public String getUpdtDttm() {
		return updtDttm;
	}
	public void setUpdtDttm(String updtDttm) {
		this.updtDttm = updtDttm;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReportNm() {
		return reportNm;
	}
	public void setReportNm(String reportNm) {
		this.reportNm = reportNm;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OlapVO [prjId=").append(prjId).append(", prjNm=")
				.append(prjNm).append(", orglDttm=").append(orglDttm)
				.append(", updtDttm=").append(updtDttm)
				.append(", description=").append(description)
				.append(", reportNm=").append(reportNm).append("]");
		return builder.toString();
	}

	
	
	

}
