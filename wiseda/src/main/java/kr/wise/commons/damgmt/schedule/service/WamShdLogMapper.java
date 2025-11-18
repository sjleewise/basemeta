/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : WamShdLogMapper.java
 * 2. Package : kr.wise.commons.damgmt.schedule.service
 * 3. Comment :
 * 4. 작성자  : kchoi
 * 5. 작성일  : 2014. 4. 24. 오후 1:24:18
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    kchoi : 2014. 4. 24. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.schedule.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : WamShdLogMapper.java
 * 3. Package  : kr.wise.commons.damgmt.schedule.service
 * 4. Comment  :
 * 5. 작성자   : kchoi
 * 6. 작성일   : 2014. 4. 24. 오후 1:24:18
 * </PRE>
 */
@Mapper
public interface WamShdLogMapper {

	// 스케줄로그 조회
	List<WamShdLogVO> selecLogtList(WamShdLogVO search);

	// 스케줄로그 작업목록 조회
	List<WamShdLogVO> selecJobtList(WamShdLogVO search);

	// 스케줄 상세 조회
	WamShdLogVO selectByPrimaryKey(String shdId);

	//로그 조회
	List<WamShdLogVO> selectLogLst(WamShdLogVO search);

	//프로파일 PRF_KND_CD 조회
	WamShdLogVO selectAnaKndCd(WamShdLogVO search);

	/** @param record
	/** @return yeonho */
	int deleteShdLog(WamShdLogVO record);

}
