/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : EtclService.java
 * 2. Package : kr.wise.meta.effect.service
 * 3. Comment :
 * 4. 작성자  : 유성열
 * 5. 작성일  : 2014. 7. 4. 16:56:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    유성열  : 2014. 7. 4. :            : 신규 개발.
 */
package kr.wise.meta.effect.service;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : EtclService.java
 * 3. Package  : kr.wise.meta.effect.service
 * 4. Comment  :
 * 5. 작성자   : 유성열
 * 6. 작성일   : 2014. 7. 4. 16:56:00
 * </PRE>
 */
public interface EtclService {

	/** @param search
	/** @return 유성열 */
	List<EtclVO> selectEtclTaskList(EtclVO search);
	
	List<EtclVO> selectEtclTaskSource(EtclVO search);
	
	List<EtclVO> selectEtclTaskTarget(EtclVO search);


}
