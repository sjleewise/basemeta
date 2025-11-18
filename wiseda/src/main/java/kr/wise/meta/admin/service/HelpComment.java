/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : HelpComment.java
 * 2. Package : kr.wise.meta.admin.service
 * 3. Comment : 도움말
 * 4. 작성자  : jjlim
 * 5. 작성일  : 2019.1.21.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    jjlim : 2019.01.21. :            : 신규 개발.
 */
package kr.wise.meta.admin.service;

import kr.wise.commons.cmm.CommonVo;

public class HelpComment extends CommonVo {
	
	
	private Integer sno;           
	private String typCd;		// 구분코드
	private String typNm;		// 구분
	private Integer itemNo;		// 항목번호
	private String itemNm;		// 항목명
	private String colctMthd;	// 수집방식
	private String regMth;		// 등록방법
	public Integer getSno() {
		return sno;
	}
	public void setSno(Integer sno) {
		this.sno = sno;
	}
	public String getTypCd() {
		return typCd;
	}
	public void setTypCd(String typCd) {
		this.typCd = typCd;
	}
	public String getTypNm() {
		return typNm;
	}
	public void setTypNm(String typNm) {
		this.typNm = typNm;
	}
	public Integer getItemNo() {
		return itemNo;
	}
	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}
	public String getItemNm() {
		return itemNm;
	}
	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}
	public String getColctMthd() {
		return colctMthd;
	}
	public void setColctMthd(String colctMthd) {
		this.colctMthd = colctMthd;
	}
	public String getRegMth() {
		return regMth;
	}
	public void setRegMth(String regMth) {
		this.regMth = regMth;
	}
	@Override
	public String toString() {
		return "HelpComment [sno=" + sno + ", typCd=" + typCd + ", typNm=" + typNm + ", itemNo=" + itemNo + ", itemNm="
				+ itemNm + ", colctMthd=" + colctMthd + ", regMth=" + regMth + "]";
	}
	
	
	
	
}
