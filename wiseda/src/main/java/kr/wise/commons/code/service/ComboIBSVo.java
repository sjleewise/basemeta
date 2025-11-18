/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : ComboIBSVo.java
 * 2. Package : kr.wise.egmd.cmcd.model
 * 3. Comment : 
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 18. 오후 3:45:37
 * 6. 변경이력 : 
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 18. 		: 신규 개발.
 */
package kr.wise.commons.code.service;

/**
 * <PRE>
 * 1. ClassName : ComboIBSVo
 * 2. Package  : kr.wise.egmd.cmcd.model
 * 3. Comment  : IBSheet 용 콤보코드 셋팅용 VO
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 18.
 * </PRE>
 */

public class ComboIBSVo {
	
	public String ComboText ; 	//콤보코드 텍스트
	public String ComboCode ;	//콤보코드 
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ComboIBSVo [ComboText=");
		builder.append(ComboText);
		builder.append(", ComboCode=");
		builder.append(ComboCode);
		builder.append("]");
		return builder.toString();
	}

}
