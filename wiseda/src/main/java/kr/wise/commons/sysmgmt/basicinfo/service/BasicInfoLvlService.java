/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : BasicInfoLvlService.java
 * 2. Package : kr.wise.commons.sysmgmt.basicinfo.service
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 7. 7. 오후 4:36:52
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 7. 7. :            : 신규 개발.
 */
package kr.wise.commons.sysmgmt.basicinfo.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : BasicInfoLvlService.java
 * 3. Package  : kr.wise.commons.sysmgmt.basicinfo.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 7. 8. 오전 10:55:53
 * </PRE>
 */ 
public interface BasicInfoLvlService {

	/** @param search
	/** @return yeonho */
	List<WaaBscLvl> selectBasicInfoLvlList(WaaBscLvl search);

	/** @param list
	/** @return yeonho */
	int regBasicInfoLvlList(ArrayList<WaaBscLvl> list);

	/** @param list
	/** @return yeonho */
	int delBasicInfoLvlList(ArrayList<WaaBscLvl> list);

	/** @param search
	/** @return yeonho */
	WaaBscLvl selectBasicInfoList(WaaBscLvl search);

	/** @param search
	/** @param bscId
	/** @return
	/** @throws Exception yeonho */
	WaaBscLvl selectBasicInfoList(String bscId)
			throws Exception;

	int getBscIdCnt(WaaBscLvl waaBscLvl);

}
