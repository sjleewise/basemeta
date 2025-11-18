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

public class IBSheetListVO<T> {
	
	public List<T> DATA;
	public int TOTAL;
	public Map<String, String> ETC;
	public String MESSAGE;
	
	public IBSRes RESULT = new IBSRes();
	
	public class IBSRes {
		public int CODE;
		public String MESSAGE;
	}
	
	public IBSheetListVO() {
		// TODO Auto-generated constructor stub
	}
	
	public IBSheetListVO (List<T> data, int code, String message) {
		this.DATA = data;
		this.MESSAGE = message;
		this.RESULT.CODE = code;
	}

	public IBSheetListVO (List<T> data, int total, Map<String, String> etcmap) {
		this.DATA = data;
		this.TOTAL = total;
		this.ETC = etcmap;
	}
	
	public IBSheetListVO (List<T> data, int total) {
		this.DATA = data;
		this.TOTAL = total;
	}

}
