package kr.wise.meta.stnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WamStwdMapper.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 27. 오후 2:08:12
 * </PRE>
 */ 
@Mapper
public interface WamStwdMapper {
    int deleteByPrimaryKey(String stwdId);

    int insert(WamStwd record);

    int insertSelective(WamStwd record);

    WamStwd selectByPrimaryKey(String stwdId);

    List<WamStwd> selectList(WamStwd record);

    List<WamStwd> selectStndList(WamStwd record);

    int updateByPrimaryKeySelective(WamStwd record);

    int updateByPrimaryKey(WamStwd record);

    //register mapper
    int insertWamStwd(String rqstNo);

    int deleteWamStwd(String rqstNo);

	List<WamStwd> selectListTop30(WamStwd record);
	
	List<WamStwd> selectWordChangeList(String stwdId);


	WamStwd selectWordDetail(String stwdId);

	//사전 변경 이력
	List<WamStwd> selectAltHistoryList(WamStwd data);

	
	/** yeonho */
	List<WamStwd> selectByLnmPnm(@Param("sbswdLnm") String sbswdLnm, @Param("sbswdPnm") String sbswdPnm);
	
	//사전비교 리스트
	List<WamStwd> selectStndWordComparisonList(String stwdId);

	String selectGovYn(String stwdId);

	WamStwd selectGovWordDetail(String stwdId);

}