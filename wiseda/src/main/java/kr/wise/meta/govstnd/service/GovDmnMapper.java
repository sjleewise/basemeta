package kr.wise.meta.govstnd.service;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.user.WaaUserg;


@Mapper
public interface GovDmnMapper {

	 List<GovDmnVo> getDomainList(GovDmnVo data);
	 
	 int insertSelective(GovDmnVo data);
	 
	 int delDiagDmnList(GovDmnVo data);

//	int checkDmnLnm(DiagDmnVo vo);
	
	int updateDupDmn(GovDmnVo vo);
	
//	int updateVrfRmk(DiagDmnVo vo);

	int updateDiagDmnList(GovDmnVo saveVo);

	int checkVrfRmk(GovDmnVo saveVo);
	
	int insertAutoCreGovDmn(Map<String,String> saveTmp);
	
	int deleteAutoCreGovDmn(Map<String,String> saveTmp);
	
}
