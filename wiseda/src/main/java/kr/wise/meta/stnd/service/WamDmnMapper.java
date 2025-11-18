package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamDmnMapper {
    int deleteByPrimaryKey(String dmnId);

    int insert(WamDmn record);

    int insertSelective(WamDmn record);

    WamDmn selectByPrimaryKey(String dmnId);

    List<WamDmn> selectList(WamDmn record);
    List<WamDmn> selectListSLC(WamDmn record);

    int updateByPrimaryKeySelective(WamDmn record);

    int updateByPrimaryKey(WamDmn record);

    //register mapper
    int insertWamDmn(String rqstNo);

    int deleteWamDmn(String rqstNo);

	List<WamDmn> selectTop30(WamDmn record);
	
	List<WamDmn> selectDmnChangeList(@Param("dmnId") String dmnId);

	/** @param data
	/** @return yeonho */
	List<WamDmn> selectDmnListWithCdVal(WamDmn data);
	
	int saveDmnTransYnPrc(WamDmn record);
	
	List<WamDmn> selectDmnTransList(WamDmn record);
	
    int updateSditmTransYnPrc(WamDmn record);	
    
    List<WamDmn> selectDmnComparisonList(String dmnId);

	String selectGovYn(String dmnId);

	WamDmn selectGovByPrimaryKey(String dmnId);
}