package kr.wise.meta.ldm.service;

import java.util.List;

import kr.wise.dq.dashboard.service.DbcAllErrChartVO;
import kr.wise.portal.dashboard.service.StandardChartVO;
import kr.wise.portal.dashboard.service.TotalCountVO;
import kr.wise.portal.dashboard.service.UpdateCntVO;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamLdmEntyMapper {
    int deleteByPrimaryKey(String pdmTblId);

    int insert(WamLdmEnty record);

    int insertSelective(WamLdmEnty record);

    WamLdmEnty selectByPrimaryKey(String pdmTblId);

    int updateByPrimaryKeySelective(WamLdmEnty record);

    int updateByPrimaryKey(WamLdmEnty record);

	List<WamLdmEnty> selectList(WamLdmEnty search);

	List<WamLdmEnty> selectTop30(WamLdmEnty search);

	/** @param search	 */
	List<WamLdmEnty> selectHist(WamLdmEnty search);

	/** @return insomnia */
	List<TotalCountVO> selectPdmTblCount(@Param("userid")String userid);

	/** @param userid 
	 * @return insomnia */
	List<UpdateCntVO> selectUpdateCountStat(@Param("userid")String userid);

	/** @return insomnia */
	DbcAllErrChartVO selectErrChart();


	
	/** @return hokim */
	List<StandardChartVO> selectDeptStandardChart();


	List<WamLdmEnty> selectStatHisTbl(WamLdmEnty search);

	List<WamLdmEnty> selectHisTbl(WamLdmEnty search);

	List<WamLdmEnty> selectIdxCol(WamLdmEnty search);

	/** @param search
	/** @return meta */
	List<WamLdmEnty> selectListForDdlCU(WamLdmEnty search);
	
	/** @param search
	/** @return meta */
	List<WamLdmEnty> selectListForDdlD(WamLdmEnty search);

}