/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : WamMtaTblMapper.java
 * 2. Package : kr.wise.meta.mta.service
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.09.12.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.09.12. :            : 신규 개발.
 */
package kr.wise.meta.mta.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamMtaTblMapper {
	
	WamMtaTbl selectMtaTblInfoDetail(String mtaTblId);
	
	/** 메타데이터테이블 리스트 조회 */
	List<WamMtaTbl> selectList(WamMtaTbl search);
	
	List<HashMap<String, Object>> selectListByRqstNo(@Param("rqstNo") String rqstNo);
	
	/** 요청에서 기 등록된 메타데이터 테이블 리스트 조회 */
	List<WaqMtaTbl> selectMtaTblList(WaqMtaTbl search);
	
	/** 요청에서 기 등록된 메타데이터 테이블 리스트 조회 */
	List<WaqMtaTbl> selectMtaTblListAuto(WaqMtaTbl search);

	List<HashMap<String, Object>> selectWamMtaTblByRqstNo(String mtaRqstNo);
	
	List<HashMap<String, Object>> selectListByTblId(@Param("mtaTblId") String mtaTblId);

//    int deleteByPrimaryKey(String mtaTblId);
//
//    int insert(WamMtaTbl record);
//
//    int insertSelective(WamMtaTbl record);
//
//    WamMtaTbl selectByPrimaryKey(String mtaTblId);
//
//    int updateByPrimaryKeySelective(WamMtaTbl record);
//
//    int updateByPrimaryKey(WamMtaTbl record);
//
//	
//
//	/** @param search	 */
//	List<WamMtaTbl> selectHist(WamMtaTbl search);
//
//	/** @return insomnia */
//	List<TotalCountVO> selectMtaTblCount(@Param("userid")String userid);
//
//	/** @param userid 
//	 * @return insomnia */
//	List<UpdateCntVO> selectUpdateCountStat(@Param("userid")String userid);
//
//	/** @return insomnia */
//	DbcAllErrChartVO selectErrChart();
//
//
//	
//	/** @return hokim */
//	List<StandardChartVO> selectDeptStandardChart();
//
//
//	List<WamMtaTbl> selectStatHisTbl(WamMtaTbl search);
//
//	List<WamMtaTbl> selectHisTbl(WamMtaTbl search);
//
//	List<WamMtaTbl> selectIdxCol(WamMtaTbl search);

	List<BrmInfoVo> selectBrmList();

	List<HashMap<String, Object>> selectEsbTotal(@Param("rqstNo") String rqstNo);

	List<HashMap<String, Object>> selectEsbInfoSys(@Param("infoSysCd")String mtaRqstNo);
}