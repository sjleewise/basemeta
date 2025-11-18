package kr.wise.dq.dqrs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.code.service.CodeListVo;

@Mapper
public interface DqrsStndMapper {
	//////////용어
	List<DqrsSditm> selectDqrsSditmLst(DqrsSditm data);

	int insertDqrsSditm(DqrsSditm saveVo);

	int deleteDqrsSditm(DqrsSditm saveVo);

	int checkSditmVrfRmk(DqrsSditm saveVo);

	int checkDupDqrsSditm(DqrsSditm saveVo);
	
	int checkDqrsDmn(DqrsSditm saveVo);

	int updateNotDmn(DqrsSditm saveVo);

	int updateSditmVrfRmk(DqrsSditm saveVo);

	/////////////도메인
	List<DqrsDmn> selectDqrsDmnLst(DqrsDmn data);

	int insertDqrsDmn(DqrsDmn saveVo);

	int updateExpDqrsDmn(DqrsDmn saveVo);

	int checkDmnVrfRmk(DqrsDmn saveVo);

	int updateDupDqrsDmn(DqrsDmn saveVo);
	
	
	//////////////공통표준
	List<DqrsSditm> selectDqrsPubSditmLst(DqrsSditm data);

	List<DqrsDmn> selectDqrsPubDmnLst(DqrsDmn data);
	

	
	
	///////////////////
	int batchDelDqrsDmn(Map<String, String> saveTmp);

	int batchDelDqrsSditm(Map<String, String> saveTmp);

	int batchDelDqrsTbl(Map<String, String> saveTmp);

	//////////////표준준수 갭 분석
	List<DqrsResult> selectDqrsStndColGapLst(DqrsResult search);
	
}
