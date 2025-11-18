/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndItemRqstService.java
 * 2. Package : kr.wise.meta.stnd.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 28. 오전 8:48:38
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 28. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndItemRqstService.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 28. 오전 8:48:38
 * </PRE>
 */
public interface StndItemEngRqstService extends CommonRqstService {

	List<WaqSditm> getItemEngRqstList(WaqMstr search);   

	

}
