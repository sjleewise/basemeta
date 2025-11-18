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

import java.util.List;
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

public class IBSheetListApiVO<T> {
	
	public IBSRes RESULT = new IBSRes();
	public List<T> CONTENT;
	public Map<String, String> ETC;
	public String MESSAGE;
	
	public class IBSRes {
		public int CODE;
		public String MESSAGE;
		public int TOTAL;
	}
	
	public IBSheetListApiVO() {
		// TODO Auto-generated constructor stub
	}
	
	public IBSheetListApiVO (List<T> CONTENT, int code, String message) {
		this.CONTENT = CONTENT;
		this.RESULT.MESSAGE = message;
		this.RESULT.CODE = code;
		this.RESULT.TOTAL = CONTENT.size();
	}

	public IBSheetListApiVO (List<T> CONTENT, int code, String message, int count) {
		this.CONTENT = CONTENT;
		this.RESULT.MESSAGE = message;
		this.RESULT.CODE = code;
		this.RESULT.TOTAL = count;
	}
	
	public IBSheetListApiVO (List<T> CONTENT, int total, Map<String, String> etcmap) {
		this.CONTENT = CONTENT;
		this.RESULT.TOTAL = total;
		this.ETC = etcmap;
	}
	
	public IBSheetListApiVO (List<T> CONTENT, int total) {
		this.CONTENT = CONTENT;
		this.RESULT.TOTAL = total;
	}

}
