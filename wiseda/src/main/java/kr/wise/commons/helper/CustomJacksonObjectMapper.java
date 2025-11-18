/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CustomJacksonObjectMapper.java
 * 2. Package : kr.wise.egmd.helper
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 10. 오전 12:54:41
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 10. 		: 신규 개발.
 */
package kr.wise.commons.helper;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * <PRE>
 * 1. ClassName : CustomJacksonObjectMapper
 * 2. Package  : kr.wise.egmd.helper
 * 3. Comment  :
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 10.
 * </PRE>
 */

public class CustomJacksonObjectMapper  extends ObjectMapper {

	static final Logger logger = LoggerFactory.getLogger(CustomJacksonObjectMapper.class);
	/**
	 * <PRE>
	 * 1. MethodName : CustomJacksonObjectMapper
	 * 2. ClassName  : CustomJacksonObjectMapper
	 * 3. Comment  :
	 * 4. 작성자   : insomnia(장명수)
	 * 5. 작성일   : 2013. 4. 10.
	 * </PRE>
	 */
	public CustomJacksonObjectMapper() {

		super();
		//VO ==> JSON date convert 등록...
//		CustomSerializerFactory factory = new CustomSerializerFactory();
//		factory.addSpecificMapping(Date.class, new IBSDateJsonSerializer());
//		this.setSerializerFactory(factory);


//		this.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
//		this.configure(Feature.WRITE_NULL_MAP_VALUES, false);
//		this.configure(Feature.WRITE_NULL_PROPERTIES, false);
		this.setDateFormat(new SimpleDateFormat("yyyyMMdd HHmmss"));

		logger.debug("json set");
		this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// to prevent exception when encountering unknown property:
		this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

//		this.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);

//		SimpleModule testModule = new SimpleModule("IBSDateModule", new Version(1, 0, 0, null)).addDeserializer(Date.class, new IBSDateJsonDeserializer<Date>());
//		super.registerModule(testModule);
//		SimpleModule testModule2 = new SimpleModule("IBSDateserial", new Version(1,0,0,null)).addSerializer(Date.class, new IBSDateJsonSerializer());
//		super.registerModule(testModule2);
	}

}
