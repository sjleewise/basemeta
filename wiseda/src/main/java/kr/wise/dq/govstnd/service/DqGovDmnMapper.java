package kr.wise.dq.govstnd.service;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.user.WaaUserg;


@Mapper
public interface DqGovDmnMapper {

	 List<DqGovDmnVo> getDomainList(DqGovDmnVo data);
	 
	 int insertSelective(DqGovDmnVo data);
	 
	 int delDiagDmnList(DqGovDmnVo data);

//	int checkDmnLnm(DiagDmnVo vo);
	
	int updateDupDmn(DqGovDmnVo vo);
	
//	int updateVrfRmk(DiagDmnVo vo);

	int updateDiagDmnList(DqGovDmnVo saveVo);

	int checkVrfRmk(DqGovDmnVo saveVo);
	
	int insertAutoCreGovDmn(Map<String,String> saveTmp);
	
	int deleteAutoCreGovDmn(Map<String,String> saveTmp);
	
}
