/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : IBSDateJsonDeserializer.java
 * 2. Package : kr.wise.egmd.helper
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 10. 오전 1:10:03
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 10. 		: 신규 개발.
 */
package kr.wise.commons.helper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.util.UtilDate;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


/**
 * <PRE>
 * 1. ClassName : IBSDateJsonDeserializer
 * 2. Package  : kr.wise.egmd.helper
 * 3. Comment  :
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 10.
 * </PRE>
 */

public class IBSDateJsonDeserializer extends JsonDeserializer<Date> {
	static final Logger logger = LoggerFactory.getLogger(IBSDateJsonDeserializer.class);
	/**
	 * <PRE>
	 * 1. MethodName : IBSDateJsonDeserializer
	 * 2. ClassName  : IBSDateJsonDeserializer
	 * 3. Comment  :
	 * 4. 작성자   : insomnia(장명수)
	 * 5. 작성일   : 2013. 4. 10.
	 * </PRE>
	 *   @param vc
	 */
	protected IBSDateJsonDeserializer() {
		//super(Date.class);
	}

	/* (non-Javadoc)
	 * @see org.codehaus.jackson.map.JsonDeserializer#deserialize(org.codehaus.jackson.JsonParser, org.codehaus.jackson.map.DeserializationContext)
	 */
	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		
		if (!StringUtils.hasText(jp.getText())) {
			logger.debug("json 날짜 값이 없슴");
			return null;
		}
		
		int lendate =  jp.getTextLength();
				
		String strdate = "";
		
		if (lendate == 4) strdate = "yyyy";
		else if (lendate == 6) strdate = "yyyyMM";
		else if (lendate == 8) strdate = "yyyyMMdd";
		else if (lendate == 12) strdate = "yyyyMMddHHmm";
		else if (lendate == 14) strdate = "yyyyMMddHHmmss";
		else if (lendate == 15) strdate = "yyyyMMdd HHmmss";
		else {
			logger.error("json 날짜 형식이 틀림:{}", jp.getText());
			return null;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(strdate);
		
		Date result = null;
		try {
//			if(jp.getTextLength() > 8) {
				result = dateFormat.parse(jp.getText());
//			} else if (jp.getTextLength() == 8 ) {
//				dateFormat = new SimpleDateFormat("yyyyMMdd");
//				result = dateFormat.parse(jp.getText());
//			}

		} catch (ParseException e) {
			logger.error("json 날짜 형식이 틀림:{}", jp.getText());
			throw new WiseBizException("json 날짜 형식이 틀림");
		}
		return result;
	}

}
