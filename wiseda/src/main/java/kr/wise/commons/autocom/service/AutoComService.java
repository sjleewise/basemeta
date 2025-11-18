/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AutoComService.java
 * 2. Package : kr.wise.commons.autocom.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 30. 오후 1:10:13
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 30. :            : 신규 개발.
 */
package kr.wise.commons.autocom.service;

import java.util.List;
import java.util.Map;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AutoComService.java
 * 3. Package  : kr.wise.commons.autocom.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 30. 오후 1:10:13
 * </PRE>
 */
public interface AutoComService {

	/** @param searchmap
	/** @return insomnia */
	List<String> getsearchTermList(Map<String, Object> searchmap);

}
