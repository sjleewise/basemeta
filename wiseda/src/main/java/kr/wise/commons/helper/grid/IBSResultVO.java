/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : IBSJsonSearch.java
 * 2. Package : kr.wise.egmd.helper
 * 3. Comment : 
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 7. 오후 12:19:44
 * 6. 변경이력 : 
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 7. 		: 신규 개발.
 */
package kr.wise.commons.helper.grid;

import java.util.Map;

/**
 * <PRE>
 * 1. ClassName : IBSJsonSearch
 * 2. Package  : kr.wise.egmd.helper
 * 3. Comment  : 
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 7.
 * </PRE>
 */

public class IBSResultVO<T> {
	
	public T resultVO;
	
	public Map<String, String> ETC;
	
	public IBSRes RESULT = new IBSRes();
	
	public String action;
	
	public class IBSRes {
		public int CODE;
		public String MESSAGE;
	}
	
	public IBSResultVO() {
		// TODO Auto-generated constructor stub
	}
	
	public IBSResultVO(T resultvo , int code, String message, String action) {
		this.resultVO 		= resultvo;
		this.RESULT.CODE 	= code;
		this.RESULT.MESSAGE = message;
		this.action			= action;
	}

	public IBSResultVO(int code, String message, String action) {
		this.RESULT.CODE 	= code;
		this.RESULT.MESSAGE = message;
		this.action			= action;
	}

	public IBSResultVO(Map<String, String> etc, int code, String message, String action) {
		this.ETC			= etc;
		this.RESULT.CODE 	= code;
		this.RESULT.MESSAGE = message;
		this.action			= action;
	}
}
