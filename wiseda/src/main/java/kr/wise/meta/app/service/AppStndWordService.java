/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AppStndWordService.java
 * 2. Package : kr.wise.meta.app.service
 * 3. Comment :
 * 4. 작성자  : mse
 * 5. 작성일   : 2016. 3. 16. 오전 10:55:20
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    mse : 2016. 3. 16. :            : 신규 개발.
 */
package kr.wise.meta.app.service;

import java.util.ArrayList;
import java.util.List;


/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AppStndWordService.java
 * 3. Package  : kr.wise.meta.app.service
 * 4. Comment  :
 * 5. 작성자   : mse
 * 6. 작성일   : 2016. 3. 16. 오전 10:55:20
 * </PRE>
 */

public interface AppStndWordService {

	/** @param data
	/** @return insomnia */
	List<WamAppStwd> getStndWordList(WamAppStwd data);

	WamAppStwd selectStndWordDetail(String stwdId);
	
	List<WamAppStwd> selectStndWordChangeList(String stwdId);

}
