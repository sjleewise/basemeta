/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndTotRqstService.java
 * 2. Package : kr.wise.meta.stnd.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 29. 오전 9:14:02
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 29. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service;

import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndTotRqstService.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 29. 오전 9:14:02
 * </PRE>
 */
public interface StndTotRqstService extends CommonRqstService {

	//int approveTot(WaqMstr reqmst, List<WaqSditm> lstSditm, List<WaqDmn> lstDmn, List<WaqStwd> lstWord) throws Exception;

	int aprvStndTot(WaqMstr reqmst, List<WaqSditm> lstSditm,
			List<WaqDmn> lstDmn, List<WaqStwd> lstWord) throws Exception;  		 
	
}
