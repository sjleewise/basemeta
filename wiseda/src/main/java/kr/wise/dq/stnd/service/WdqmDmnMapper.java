package kr.wise.dq.stnd.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WdqmDmnMapper {
    int deleteByPrimaryKey(String dmnId);

    int insert(WdqmDmn record);

    int insertSelective(WdqmDmn record);

    WdqmDmn selectByPrimaryKey(String dmnId);

    List<WdqmDmn> selectList(WdqmDmn record);
    List<WdqmDmn> selectListSLC(WdqmDmn record);

    int updateByPrimaryKeySelective(WdqmDmn record);

    int updateByPrimaryKey(WdqmDmn record);

    //register mapper
    int insertWdqmDmn(String rqstNo);

    int deleteWdqmDmn(String rqstNo);

	List<WdqmDmn> selectTop30(WdqmDmn record);
	
	List<WdqmDmn> selectDmnChangeList(@Param("dmnId") String dmnId);

	/** @param data
	/** @return yeonho */
	List<WdqmDmn> selectDmnListWithCdVal(WdqmDmn data);
	
	int saveDmnTransYnPrc(WdqmDmn record);
	
	List<WdqmDmn> selectDmnTransList(WdqmDmn record);
	
    int updateSditmTransYnPrc(WdqmDmn record);	
    
    List<WdqmDmn> selectDmnComparisonList(String dmnId);
}