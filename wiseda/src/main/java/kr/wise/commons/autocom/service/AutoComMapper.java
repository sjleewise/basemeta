/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AutoComMapper.java
 * 2. Package : kr.wise.commons.autocom.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 30. 오후 1:38:22
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 30. :            : 신규 개발.
 */
package kr.wise.commons.autocom.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AutoComMapper.java
 * 3. Package  : kr.wise.commons.autocom.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 30. 오후 1:38:22
 * </PRE>
 */
@Mapper
public interface AutoComMapper {

	/** @param searchmap
	/** @return insomnia */
	List<String> selectStndWordList(Map<String, Object> searchmap);

	/** @param searchmap
	/** @return insomnia */
	List<String> selectStndDmnList(Map<String, Object> searchmap);

	/** @param searchmap
	/** @return yeonho */
	List<String> selectStndSditmList(Map<String, Object> searchmap);

	/** @param searchmap
	/** @return yeonho */
	List<String> selectSymnList(Map<String, Object> searchmap);

	/** @param searchmap
	/** @return yeonho */
	List<String> selectSymnSbswdList(Map<String, Object> searchmap);

	/** @param searchmap
	/** @return yeonho */
	List<String> selectSubjList(Map<String, Object> searchmap);

	/** @param searchmap
	/** @return yeonho */
	List<String> selectPdmTblList(Map<String, Object> searchmap);
	
	/** @param searchmap */
	List<String> selectAsisPdmTblList(Map<String, Object> searchmap);

	/** @param searchmap
	/** @return yeonho */
	List<String> selectPdmColList(Map<String, Object> searchmap);

	/** @param searchmap */
	List<String> selectAsisPdmColList(Map<String, Object> searchmap);

	/** @param searchmap
	/** @return yeonho */
	List<String> selectDdlTblList(Map<String, Object> searchmap);

	/** @param searchmap
	/** @return yeonho */
	List<String> selectDbcTblList(Map<String, Object> searchmap);

	/**
	 * @param searchmap
	 * @return
	 */
	List<String> selectBizLnmList(Map<String, Object> searchmap);

	/**
	 * @param searchmap
	 * @return
	 */
	List<String> selectDqiLnmList(Map<String, Object> searchmap);

	/**
	 * @param searchmap
	 * @return
	 */
	List<String> selectCtqLnmList(Map<String, Object> searchmap);

	/**
	 * @param searchmap
	 * @return
	 */
	List<String> selectDbSchList(Map<String, Object> searchmap);

	/**
	 * @param searchmap
	 * @return
	 */
	List<String> selectDbcColList(Map<String, Object> searchmap);

	/**
	 * @param searchmap
	 * @return
	 */
	List<String> selectBrNmList(Map<String, Object> searchmap);

	/**
	 * @param searchmap
	 * @return
	 */
	List<String> selectShdLnmList(Map<String, Object> searchmap);

	/**
	 * @param searchmap
	 * @return
	 */
	List<String> selectUserNmList(Map<String, Object> searchmap);

	/**
	 * @param searchmap
	 * @return
	 */
	List<String> selectObjNmList(Map<String, Object> searchmap);

	/** @param searchmap
	/** @return yeonho */
	List<String> selectDbmsList(Map<String, Object> searchmap);

	/** @param searchmap
	/** @return yeonho */
	List<String> selectDeptList(Map<String, Object> searchmap);

	/** @param searchmap
	/** @return yeonho */
	List<String> selectDmngList(Map<String, Object> searchmap);

	/** @param searchmap
	/** @return yeonho */
	List<String> selectMenuList(Map<String, Object> searchmap);
	
	/** @param searchmap
	/** @return yeonho */
	List<String> selectDdlTsfTblList(Map<String, Object> searchmap);

	List<String> selectSlcItemList(Map<String, Object> searchmap);
	
	List<String> selectAppStwdList(Map<String, Object> searchmap);
	
	List<String> selectAppSditmList(Map<String, Object> searchmap);
	
	List<String> selectTblMapList(Map<String, Object> searchmap);

	List<String> selectColMapList(Map<String, Object> searchmap);
	
	List<String> selectCodMapList(Map<String, Object> searchmap);
	
	/** 시퀀스 */
	List<String> selectDDLSEQList(Map<String, Object> searchmap);

	List<String> selectDBCSEQList(Map<String, Object> searchmap);

}
