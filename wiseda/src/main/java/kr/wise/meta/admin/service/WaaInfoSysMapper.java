/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : WaaInfoSysMapper.java
 * 2. Package : kr.wise.meta.admin.service
 * 3. Comment : 기관메타 > 관리자 - 정보시스템관리
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.12.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.12. :            : 신규 개발.
 */
package kr.wise.meta.admin.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.user.service.WaaUserSys;

@Mapper
public interface WaaInfoSysMapper {

	/** 목록 조회 */
	List<WaaInfoSys> selectList(WaaInfoSys search);
	
	/** 정보시스템 등록(단건) */
	int insertSelective(WaaInfoSys record);
	
	/** 정보시스템 수정(단건) */
	int updateWaaInfoSys(WaaInfoSys record);
	
	/** 삭제 */
	int updateExpDtm(WaaInfoSys record);
	
	List<WaaInfoSys> selectListApi(Map<String, Object> map);
	
	List<WaaInfoSys> getDicInfoSysList(WaaInfoSys record);
	
	WaaInfoSys selectInfoSysInfoDetail(@Param("orgCd") String orgCd, @Param("infoSysCd") String infoSysCd);
	
	int insertInfoSysUserMap(WaaUserSys record);
	
	WaaInfoSys selectInfoSysDupCheck(WaaInfoSys search);
}
