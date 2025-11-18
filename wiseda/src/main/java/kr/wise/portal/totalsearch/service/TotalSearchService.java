package kr.wise.portal.totalsearch.service;

import java.util.List;
import java.util.Map;

public interface TotalSearchService {

	public void insertTotalSearchWord(Map<String, String> param) throws Exception;
	
	public List<TotalSearch> selectTotalSearchCnt(Map<String, String> param) throws Exception;

	public List<TotalSearch> selectTotalSearchTot(Map<String, String> param) throws Exception;
	
	public List<TotalSearch> selectTotalSearchTbl(Map<String, String> param) throws Exception;

	public List<TotalSearch> selectTotalSearchDq(Map<String, String> param) throws Exception;

	public List<TotalSearch> selectTotalSearchStnd(Map<String, String> param) throws Exception;
	
	public List<TotalSearch> selectTotalSearchBbs(Map<String, String> param) throws Exception;
	
	public List<TotalSearch> selectTotalSearchWord() throws Exception;

	public List<TotalSearch> selectTotalSearchSubj(Map<String, String> param) throws Exception;

	public void regTotalSearchIndex();

}
