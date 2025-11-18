package kr.wise.meta.model.service;

import java.util.List;

import kr.wise.dq.dashboard.service.DbcAllErrChartVO;
import kr.wise.portal.dashboard.service.StandardChartVO;
import kr.wise.portal.dashboard.service.TotalCountVO;
import kr.wise.portal.dashboard.service.UpdateCntVO;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamAsisPdmTblMapper {
    int deleteByPrimaryKey(String pdmTblId);

    int insert(WamPdmTbl record);

    int insertSelective(WamPdmTbl record);

    WamPdmTbl selectByPrimaryKey(String pdmTblId);

    int updateByPrimaryKeySelective(WamPdmTbl record);

    int updateByPrimaryKey(WamPdmTbl record);

	List<WamPdmTbl> selectList(WamPdmTbl search);

	List<WamPdmTbl> selectTop30(WamPdmTbl search);

	/** @param search	 */
	List<WamPdmTbl> selectHist(WamPdmTbl search);

	/** @return insomnia */
	List<TotalCountVO> selectPdmTblCount(@Param("userid")String userid);

	/** @param userid 
	 * @return insomnia */
	List<UpdateCntVO> selectUpdateCountStat(@Param("userid")String userid);

	/** @return insomnia */
	DbcAllErrChartVO selectErrChart();


	
	/** @return hokim */
	List<StandardChartVO> selectDeptStandardChart();


	List<WamPdmTbl> selectStatHisTbl(WamPdmTbl search);

	List<WamPdmTbl> selectHisTbl(WamPdmTbl search);

	List<WamPdmTbl> selectIdxCol(WamPdmTbl search);

	/** @param search
	/** @return yeonho */
	List<WamPdmTbl> selectListForDdlCU(WamPdmTbl search);
	
	/** @param search
	/** @return yeonho */
	List<WamPdmTbl> selectListForDdlD(WamPdmTbl search);

}