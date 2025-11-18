/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : Mapper.java
 * 2. Package : kr.wise.commons.cmn.service
 * 3. Comment : mybais mapper 인터페이스를 스캔하기 위한 어노테이션....
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 24. 오전 11:23:33
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 24. :            : 신규 개발.
 */
package kr.wise.commons.cmm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : Mapper.java
 * 3. Package  : kr.wise.commons.cmn.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 24. 오전 11:23:33
 * </PRE>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mapper {

	String value() default "";

}
