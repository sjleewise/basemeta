/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbcSeqService.java
 * 2. Package : kr.wise.meta.dbc.service
 * 3. Comment : 
 * 4. 작성자  : hjan93
 * 5. 작성일  : 2020. 7. 30. 
 * 6. 변경이력 : 
 *                    이름     	 : 일자                     : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hjan93 : 2020. 7. 30. :        : 신규 개발.
 */
package kr.wise.meta.dbc.service;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbcSeqService.java
 * 3. Package  : kr.wise.meta.dbc.service
 * 4. Comment  : 
 * 5. 작성자   : hjan93
 * 6. 작성일   : 2020. 7. 30. 
 * </PRE>
 */
public interface DbcSeqService {

	/** @param search
	/** @return meta */
	List<WatDbcSeq> getList(WatDbcSeq search);

	/** @param dbSchId
	/** @param dbcSeqNm
	/** @return meta */
	WatDbcSeq selectDbcSeqInfo(String dbSchId, String dbcSeqNm);

}
