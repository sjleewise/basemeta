package kr.wise.dq.stnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WdqmStwdMapper.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 27. 오후 2:08:12
 * </PRE>
 */ 
@Mapper
public interface WdqmStwdMapper {
    int deleteByPrimaryKey(String stwdId);

    int insert(WdqmStwd record);

    int insertSelective(WdqmStwd record);

    WdqmStwd selectByPrimaryKey(String stwdId);

    List<WdqmStwd> selectList(WdqmStwd record);

    List<WdqmStwd> selectStndList(WdqmStwd record);

    int updateByPrimaryKeySelective(WdqmStwd record);

    int updateByPrimaryKey(WdqmStwd record);

    //register mapper
    int insertWdqmStwd(String rqstNo);

    int deleteWdqmStwd(String rqstNo);

	List<WdqmStwd> selectListTop30(WdqmStwd record);
	
	List<WdqmStwd> selectWordChangeList(String stwdId);


	WdqmStwd selectWordDetail(String stwdId);

	//사전 변경 이력
	List<WdqmStwd> selectAltHistoryList(WdqmStwd data);

	
	/** yeonho */
	List<WdqmStwd> selectByLnmPnm(@Param("sbswdLnm") String sbswdLnm, @Param("sbswdPnm") String sbswdPnm);
	
	//사전비교 리스트
	List<WdqmStwd> selectStndWordComparisonList(String stwdId);

}