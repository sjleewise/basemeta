package kr.wise.dq.criinfo.anatrg.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaExpTblMapper { 

	List<WaaExpTbl> selectList(WaaExpTbl search);
	
	List<WaaExpTbl> selectconntrglist(WaaExpTbl search); 

	List<WaaExpTbl> selectListByFK(String dbConnTrgId);

	WaaExpTbl selectByPrimaryKey(String dbSchId);

	int updateExpDtm(String dbSchId);

	int insertSelective(WaaExpTbl record); 

	int updateByPrimaryKeySelective(WaaExpTbl saveVo);

	List<WaaExpTbl> selectTrgTbl(WaaExpTbl vo);

	List<WaaExpTbl> selectExpRuleTbl(WaaExpTbl vo);

	int updateByPrimaryKeySelective2(WaaExpTbl saveVo);

	int insertSelective2(WaaExpTbl saveVo);

	int deleteExpRule(WaaExpTbl delVo);

	
}

