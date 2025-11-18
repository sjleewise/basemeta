/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : AlgorithmService.java
 * 2. Package : kr.wise.admin.ai.algorithm.service
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 10. 23. 오후 3:38:05
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 10. 23. :            : 신규 개발.
 */
package kr.wise.admin.ai.algorithm.service;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : AlgorithmService.java
 * 3. Package  : kr.wise.admin.ai.algorithm.service
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 10. 23. 오후 3:38:05
 * </PRE>
 */
public interface AlgorithmService {

	/** @param search
	/** @return insomnia */
	List<WaaAlg> getAlgorithmList(WaaAlg search);

	/** @param list
	/** @return insomnia 
	 * @throws Exception */
	int regAlgorithm(List<WaaAlg> list) throws Exception;

	/** @param list
	/** @return insomnia */
	int delAlgorithm(List<WaaAlg> list);

	/** @param search
	/** @return insomnia */
	List<WaaAlgArg> getAlgorithmParam(WaaAlg search);

	/** @param algvo
	/** @param list
	/** @return insomnia 
	 * @throws Exception */
	int regAlgorithmParam(WaaAlg algvo, List<WaaAlgArg> list) throws Exception;

	/** @param algvo
	/** @param list
	/** @return insomnia */
	int delAlgorithmParam(WaaAlg algvo, List<WaaAlgArg> list);

}
