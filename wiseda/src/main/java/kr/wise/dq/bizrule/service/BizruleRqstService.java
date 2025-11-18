/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : BizruleRqstService.java
 * 2. Package : kr.wise.dq.bizrule.service
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 5. 17. 오후 1:56:48
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 5. 17. :            : 신규 개발.
 */
package kr.wise.dq.bizrule.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : BizruleRqstService.java
 * 3. Package  : kr.wise.dq.bizrule.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 5. 17. 오후 1:56:48
 * </PRE>
 */
public interface BizruleRqstService extends CommonRqstService{

	/** @param search
	/** @return meta */
	List<WaqBrMstr> getBizruleRqstist(WaqMstr search);

	/** @param arrayList
	/** @param mstr
	/** @return meta */
	Map<String, String> delRegBizruleList(ArrayList<WaqBrMstr> arrayList,
			WaqMstr mstr);

	/** @param reqmst
	/** @param list
	/** @return meta 
	 * @throws Exception */
	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqBrMstr> list) throws Exception;

	int sqlCheck(WaqMstr reqmst, ArrayList<WaqBrMstr> list) throws Exception;

	int sqlCheckOne(WaqMstr reqmst, ArrayList<WaqBrMstr> list, int sqlFlag) throws Exception;

	int regWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo) throws Exception;

}
