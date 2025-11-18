/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeCfcSysService.java
 * 2. Package : kr.wise.meta.codecfcsys.service
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 8. 7. 오후 4:33:19
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 8. 7. :            : 신규 개발.
 */
package kr.wise.meta.codecfcsys.service;

import java.util.List;

import kr.wise.commons.sysmgmt.dept.service.WaaDept;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CodeCfcSysService.java
 * 3. Package  : kr.wise.meta.codecfcsys.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 8. 7. 오후 4:33:19
 * </PRE>
 */
public interface CodeCfcSysService {

	/** @param search
	/** @return meta */
	List<WamCodeCfcSys> getCodeCfcSysList(WamCodeCfcSys search);

	/** @param search
	/** @return meta */
	List<WaqCodeCfcSysItem> getCodeCfcSysItemList(WaqCodeCfcSysItem search);

	/** @param searchVo
	/** @return meta */
	WamCodeCfcSys getCodeCfcSysDetail(String codeCfcSysId);

	/** @param search
	/** @return meta */
	List<WaqCodeCfcSys> getCodeCfcSysHistList(WaqCodeCfcSys search);

	/** @param search
	/** @return meta */
	List<WaqCodeCfcSysItem> getCodeCfcSysItemHistList(WaqCodeCfcSysItem search);

}
