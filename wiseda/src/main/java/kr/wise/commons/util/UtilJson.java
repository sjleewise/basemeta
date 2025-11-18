/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : UtilJson.java
 * 2. Package : kr.wise.egmd.util
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 18. 오후 4:26:36
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 18. 		: 신규 개발.
 */
package kr.wise.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <PRE>
 * 1. ClassName : UtilJson
 * 2. Package  : kr.wise.egmd.util
 * 3. Comment  : Json 관련 오브젝트
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 18.
 * </PRE>
 */

public class UtilJson {

	Logger logger = LoggerFactory.getLogger(getClass());

	public static String convertJsonString( Object obj) {

		String result = null;

		ObjectMapper om = new ObjectMapper();

		try {
			result = om.writeValueAsString(obj);
		} catch (Exception e) {
			System.out.println("[ERROR] :" + Object.class.getName());
		}

		return result;
	}

}
