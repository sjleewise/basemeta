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
package kr.wise.meta.mta.service;

import kr.wise.commons.cmm.CommonVo;

public class BrmInfoVo extends CommonVo {

	private String subj_id;
	private String subj_nm;
	private String subj_path;
	
	private String subj_lvl;
	private String subj_se;
	
	
	public String getSubj_lvl() {
		return subj_lvl;
	}
	public void setSubj_lvl(String subj_lvl) {
		this.subj_lvl = subj_lvl;
	}
	public String getSubj_se() {
		return subj_se;
	}
	public void setSubj_se(String subj_se) {
		this.subj_se = subj_se;
	}
	public String getSubj_id() {
		return subj_id;
	}
	public void setSubj_id(String subj_id) {
		this.subj_id = subj_id;
	}
	public String getSubj_nm() {
		return subj_nm;
	}
	public void setSubj_nm(String subj_nm) {
		this.subj_nm = subj_nm;
	}
	public String getSubj_path() {
		return subj_path;
	}
	public void setSubj_path(String subj_path) {
		this.subj_path = subj_path;
	}

	
	

}

