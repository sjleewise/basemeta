/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CommonRqstMapper.java
 * 2. Package : kr.wise.commons.rqstmst.service
 * 3. Comment : WAQ ==> WAM, WAH 적재를 처리하는 공통 매퍼 인터페이스
 *              waq 매퍼에서 상속받아서 사용하도록 하자.....
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 11. 오후 4:57:38
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 11. :            : 신규 개발.
 */
package kr.wise.commons.rqstmst.service;

import org.apache.ibatis.annotations.Param;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CommonRqstMapper.java
 * 3. Package  : kr.wise.commons.rqstmst.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 11. 오후 4:57:38
 * </PRE>
 */
public interface CommonRqstMapper {

	//적재 쿼리
	int updateWaqCUD(@Param("rqstNo") String rqstNo);

	int updateWaqC(@Param("rqstNo") String rqstNo);

	int deleteWAM(@Param("rqstNo") String rqstNo);

	int insertWAM(@Param("rqstNo") String rqstNo);

	int updateWAH(@Param("rqstNo") String rqstNo);

	int insertWAH(@Param("rqstNo") String rqstNo);

}
