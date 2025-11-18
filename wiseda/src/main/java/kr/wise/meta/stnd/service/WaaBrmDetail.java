/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : WaqMtaTbl.java
 * 2. Package : kr.wise.meta.mta.service
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.09.12.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.09.12. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service;

import kr.wise.commons.cmm.CommonVo;

public class WaaBrmDetail extends CommonVo {

	private String brmSe;	
	private String brmId;
	private String upperBrmId;
	private String brmNm;
	private String brmFullPath;
	private Integer brmLvl;
	
	
	public String getBrmSe() {
		return brmSe;
	}
	public void setBrmSe(String brmSe) {
		this.brmSe = brmSe;
	}
	public String getBrmId() {
		return brmId;
	}
	public void setBrmId(String brmId) {
		this.brmId = brmId;
	}
	public String getUpperBrmId() {
		return upperBrmId;
	}
	public void setUpperBrmId(String upperBrmId) {
		this.upperBrmId = upperBrmId;
	}
	public String getBrmNm() {
		return brmNm;
	}
	public void setBrmNm(String brmNm) {
		this.brmNm = brmNm;
	}
	public String getBrmFullPath() {
		return brmFullPath;
	}
	public void setBrmFullPath(String brmFullPath) {
		this.brmFullPath = brmFullPath;
	}
	
	public Integer getBrmLvl() {
		return brmLvl;
	}
	public void setBrmLvl(Integer brmLvl) {
		this.brmLvl = brmLvl;
	}
	
	

}
