/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : WaaDdlMapVo.java
 * 2. Package : kr.wise.meta.ddltsf.search
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 8. 24. 오후 5:53:41
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 8. 24. :            : 신규 개발.
 */
package kr.wise.meta.ddltsf.service;

import kr.wise.commons.damgmt.db.service.WaaDbMap;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : WaaDdlMapVo.java
 * 3. Package  : kr.wise.meta.ddltsf.search
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 8. 24. 오후 5:53:41
 * </PRE>
 */
public class WaaDbMapVo extends WaaDbMap {

	private String ddlTblLnm;

	private String ddlTblPnm;
	
	public String getDdlTblPnm() {
		return ddlTblPnm;
	}

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

}
