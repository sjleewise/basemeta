/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : PdmTblRqstService.java
 * 2. Package : kr.wise.meta.model.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 2. 오후 4:41:08
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 2. :            : 신규 개발.
 */
package kr.wise.dq.model.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.rqstmst.service.CommonRqstService;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : PdmTblRqstService.java
 * 3. Package  : kr.wise.meta.model.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 2. 오후 4:41:08
 * </PRE>
 */
public interface WdqPdmTblRqstService extends CommonRqstService {

	List<WdqmPdmTbl> getPdmTblList(WdqmPdmTbl search);

	int regList(ArrayList<WdqmPdmTbl> list) throws Exception;

	int delList(ArrayList<WdqmPdmTbl> list);

	int regPdmTbl(WdqmPdmTbl pdmTbl) throws Exception;

	List<WdqmPdmCol> getPdmColList(WdqmPdmCol search) throws Exception;

	int regColList(ArrayList<WdqmPdmCol> list) throws Exception;

	int delColList(ArrayList<WdqmPdmCol> list) throws Exception;
 
}
