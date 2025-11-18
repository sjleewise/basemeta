package kr.wise.dq.dqrs.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface DqrsExpMapper {

	////////////테이블
	List<DqrsExpTbl> selectExpTbl(DqrsExpTbl vo);

	int updateExpTbl(DqrsExpTbl saveVo);

	int insertExpTbl(DqrsExpTbl saveVo);

	///////////컬럼
	List<DqrsExpCol> selectExpCol(DqrsExpCol search);

	int updateExpCol(DqrsExpCol saveVo);

	int deleteExpCol(DqrsExpCol saveVo);

	int insertExpCol(DqrsExpCol saveVo);
	
}
