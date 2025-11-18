/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : IBSDateJsonSerializer.java
 * 2. Package : kr.wise.egmd.helper
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 10. 오전 12:48:58
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 10. 		: 신규 개발.
 */
package kr.wise.commons.helper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


/**
 * <PRE>
 * 1. ClassName : IBSDateJsonSerializer
 * 2. Package  : kr.wise.egmd.helper
 * 3. Comment  :
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 10.
 * </PRE>
 */

public class IBSDateJsonSerializer extends JsonSerializer<Date> {

	/* (non-Javadoc)
	 * @see org.codehaus.jackson.map.JsonSerializer#serialize(java.lang.Object, org.codehaus.jackson.JsonGenerator, org.codehaus.jackson.map.SerializerProvider)
	 */
	@Override
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateString = dateFormat.format(value);
        jgen.writeString(dateString);

	}


}
