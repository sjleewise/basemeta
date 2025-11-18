/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlTblRqstService.java
 * 2. Package : kr.wise.meta.ddl.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 14. 오후 3:09:29
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 14. :            : 신규 개발.
 */
package kr.wise.meta.ddletc.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.ddl.service.WamDdlTbl;
import kr.wise.meta.ddl.service.WaqDdlTbl;
import kr.wise.meta.model.service.WaqPdmTbl;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlTblRqstService.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 14. 오후 3:09:29
 * </PRE>
 */
public interface DdlEtcService  {

	List<WamDdlEtc> getDdlEtcList(WamDdlEtc search);

	WamDdlEtc gettDdlEtcDetail(String ddlEtcId, String rqstNo);  

	

}
