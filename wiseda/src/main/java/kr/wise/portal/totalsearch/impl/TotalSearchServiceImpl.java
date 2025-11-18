package kr.wise.portal.totalsearch.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.portal.totalsearch.service.TotalSearch;
import kr.wise.portal.totalsearch.service.TotalSearchMapper;
import kr.wise.portal.totalsearch.service.TotalSearchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service ("totalSearchService")
public class TotalSearchServiceImpl implements TotalSearchService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private TotalSearchMapper totalSearchMapper;
	
	public void insertTotalSearchWord(Map<String, String> param) throws Exception {
		totalSearchMapper.insertTotalSearchWord(param);
	}
	
	public List<TotalSearch> selectTotalSearchCnt(Map<String, String> param) throws Exception {
		return totalSearchMapper.selectTotalSearchCnt(param);
	}
	
	public List<TotalSearch> selectTotalSearchTot(Map<String, String> param) throws Exception {
		return totalSearchMapper.selectTotalSearchTot(param);
	}
	
	public List<TotalSearch> selectTotalSearchStnd(Map<String, String> param) throws Exception {
		return totalSearchMapper.selectTotalSearchStnd(param);
	}
	
	public List<TotalSearch> selectTotalSearchTbl(Map<String, String> param) throws Exception {
		return totalSearchMapper.selectTotalSearchTbl(param);
	}

	public List<TotalSearch> selectTotalSearchDq(Map<String, String> param) throws Exception {
		return totalSearchMapper.selectTotalSearchDq(param);
	}

	public List<TotalSearch> selectTotalSearchBbs(Map<String, String> param) throws Exception {
		
		return totalSearchMapper.selectTotalSearchBbs(param);
		
	}
	
	public List<TotalSearch> selectTotalSearchWord() throws Exception {
		
		return totalSearchMapper.selectTotalSearchWord();
		
	}

	@Override
	public List<TotalSearch> selectTotalSearchSubj(Map<String, String> param) throws Exception {
		return totalSearchMapper.selectTotalSearchSubj(param);
	}

	@Override
	public void regTotalSearchIndex() {
		//검색 인덱스 템프 삭제
		totalSearchMapper.deleteSearchTemp();
		
		//검색인덱스 추가(단어)
		totalSearchMapper.insertSearchTempWord();

		//검색인덱스 추가(유사어)
		totalSearchMapper.insertSearchTempSYMN();
		
		//검색인덱스 추가(도메인)
		totalSearchMapper.insertSearchTempDomain();
		
		//검색인덱스 추가(항목)
		totalSearchMapper.insertSearchTempItem();

		//검색인덱스 추가(물리모델)
		totalSearchMapper.insertSearchTempPdmTbl();
		
		//검색인덱스 추가(DDL)
		totalSearchMapper.insertSearchTempDdlTbl();
		
		//검색인덱스 추가(DBC)
		totalSearchMapper.insertSearchTempDbcTbl();
		
		//검색인덱스 추가(주제영역)
		totalSearchMapper.insertSearchTempSubj();
		
		//검색인덱스 추가(프로파일)
		totalSearchMapper.insertSearchTempProfile();
		
		//검색인덱스 추가(업무규칙)
		totalSearchMapper.insertSearchTempBizRule();
		
		//검색인덱스 추가(업무영역)
		totalSearchMapper.insertSearchTempBizArea();
		
		//검색인덱스 추가(DQI)
		totalSearchMapper.insertSearchTempDqi();
		
		//검색인덱스 추가(CTQ)
		totalSearchMapper.insertSearchTempCtq();
		
		//검색인덱스 추가(개선계획)
		totalSearchMapper.insertSearchTempImPl();
		
		//검색인덱스 추가(개선결과)
		totalSearchMapper.insertSearchTempImRsl();
		
		//검색인덱스 추가(게시물)
		totalSearchMapper.insertSearchTempBbs();
		
		//검색인덱스 삭제(전체)
		totalSearchMapper.deleteSearchIndex();
		
		//검색인덱스 적재(템프 -> 전체)
		totalSearchMapper.insertSearchIndex();
		
	}
}