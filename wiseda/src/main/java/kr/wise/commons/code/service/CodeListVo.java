/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CodeListVo.java
 * 2. Package : kr.wise.egmd.cmcd.model
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 15. 오후 3:30:01
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 15. 		: 신규 개발.
 */
package kr.wise.commons.code.service;


/**
 * <PRE>
 * 1. ClassName : CodeListVo
 * 2. Package  : kr.wise.egmd.cmcd.model
 * 3. Comment  : 목록성 코드리스트용 VO
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 15.
 * </PRE>
 */

public class CodeListVo {

	private String codeCd;		//코드 CD(ID)
	private String codeLnm;		//코드명(논리명)

	private String codePnm;		//코드명(물리명)
	private String upcodeCd;	//상위코드 CD(ID)
	
	
	private String stndAsrt; //표준분류 (멀티사전)
	

	
	public String getStndAsrt() {
		return stndAsrt;
	}
	public void setStndAsrt(String stndAsrt) {
		this.stndAsrt = stndAsrt;
	}
	public String getCodeCd() {
		return codeCd;
	}
	public void setCodeCd(String codeCd) {
		this.codeCd = codeCd;
	}
	public String getCodeLnm() {
		return codeLnm;
	}
	public void setCodeLnm(String codeLnm) {
		this.codeLnm = codeLnm;
	}
	public String getCodePnm() {
		return codePnm;
	}
	public void setCodePnm(String codePnm) {
		this.codePnm = codePnm;
	}
	public String getUpcodeCd() {
		return upcodeCd;
	}
	public void setUpcodeCd(String upcodeCd) {
		this.upcodeCd = upcodeCd;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CodeListVo [codeCd=");
		builder.append(codeCd);
		builder.append(", codeLnm=");
		builder.append(codeLnm);
		builder.append(", codePnm=");
		builder.append(codePnm);
		builder.append(", upcodeCd=");
		builder.append(upcodeCd);
		builder.append("]");
		return builder.toString();
	}
}
