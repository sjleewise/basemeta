/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : PdMartMapper.java
 * 2. Package : kr.wise.sysinf.pwdesigner.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 8. 19. 오후 2:45:34
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 8. 19. :            : 신규 개발.
 */
package kr.wise.sysinf.daSharp.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.tblrel.service.WaqPrfRelColVO;
import kr.wise.meta.model.service.WaqPdmCol;
import kr.wise.meta.model.service.WaqPdmRel;
import kr.wise.meta.model.service.WaqPdmTbl;
import kr.wise.portal.myjob.service.WaqReqMst;

import org.apache.ibatis.annotations.Param;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : R7MartMapper.java
 * 3. Package  : kr.wise.sysinf.erwin.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 8. 19. 오후 2:45:34
 * </PRE>
 */
public interface DasMartMapper {

	/** @param search
	/** @return insomnia */
	List<WaqPdmTbl> selectMartList(WaqPdmTbl search);

	/** @param reqmst
	/** @param list
	/** @return insomnia */
	List<WaqPdmTbl> selectmartlistadd(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqPdmTbl> list);

	/** @param saveVo
	/** @return insomnia */
	List<WaqPdmCol> selectDasColListbyTblId(WaqPdmTbl saveVo);
	
	List<WaqPdmCol> selectDasColListbyTblIds(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqPdmTbl> list);
	
	//da#
	List<WaqPdmRel> selectDasMartRelLst(WaqPdmTbl search);
	
	int insertDasColListbyTblIdsDirect(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqPdmTbl> list);

}
