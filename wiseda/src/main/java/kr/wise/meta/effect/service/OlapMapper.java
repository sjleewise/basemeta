/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : OlapMapper.java
 * 2. Package : kr.wise.meta.effect.service
 * 3. Comment :
 * 4. 작성자  : 유성열
 * 5. 작성일  : 2014. 7. 8. 15:13:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    유성열 : 2014. 7. 8. :            : 신규 개발.
 */
package kr.wise.meta.effect.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : OlapMapper.java
 * 3. Package  : kr.wise.meta.effect.service
 * 4. Comment  :
 * 5. 작성자   : 유성열
 * 6. 작성일   : 2014. 7. 8. 15:13:00
 * </PRE>
 */
@Mapper
public interface OlapMapper {

	/** @param search
	/** @return 유성열 */
	List<OlapVO> selectOlapReportList(OlapVO search);
	
	List<OlapVO> selectOlapReportDetailList(OlapVO search);



}
