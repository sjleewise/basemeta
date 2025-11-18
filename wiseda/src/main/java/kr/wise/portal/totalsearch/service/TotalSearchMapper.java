package kr.wise.portal.totalsearch.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface TotalSearchMapper {
    
	void insertTotalSearchWord(Map<String, String> param);

    List<TotalSearch> selectTotalSearchCnt(Map<String, String> param);

    List<TotalSearch> selectTotalSearchTot(Map<String, String> param);
    
    List<TotalSearch> selectTotalSearchStnd(Map<String, String> param);

    List<TotalSearch> selectTotalSearchTbl(Map<String, String> param);
    
    List<TotalSearch> selectTotalSearchDq(Map<String, String> param);
    
    List<TotalSearch> selectTotalSearchBbs(Map<String, String> param);
    
    List<TotalSearch> selectTotalSearchWord();

	List<TotalSearch> selectTotalSearchSubj(Map<String, String> param);

	int deleteSearchTemp();

	int insertSearchTempWord();

	int insertSearchTempSYMN();

	int insertSearchTempDomain();

	int insertSearchTempItem();

	int insertSearchTempPdmTbl();

	int insertSearchTempDdlTbl();

	int insertSearchTempDbcTbl();

	int insertSearchTempSubj();

	int insertSearchTempProfile();

	int insertSearchTempBizRule();

	int insertSearchTempBbs();

	int deleteSearchIndex();

	int insertSearchIndex();

	int insertSearchTempBizArea();

	int insertSearchTempDqi();

	int insertSearchTempCtq();

	int insertSearchTempImPl();

	int insertSearchTempImRsl();
}