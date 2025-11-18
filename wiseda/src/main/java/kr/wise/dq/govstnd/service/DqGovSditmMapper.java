package kr.wise.dq.govstnd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface DqGovSditmMapper {
	List<DqGovSditm> selectdiagSditmList(DqGovSditm data);
	
	int insertDiagSditmList(DqGovSditm data);
	
	int updateDiagSditmList(DqGovSditm data);
	
	int deleteDiagSditmList(DqGovSditm data);
	
	List<HashMap> selectInfoSys();
	
	int checkVrfRmk(DqGovSditm data);
	int checkDupGovSditm(DqGovSditm data);
	int checkGovDmn(DqGovSditm data);
	int updateNotDmn(DqGovSditm data);
	int updateVrfRmk(DqGovSditm data);
	
	int insertAutoCreGovSditm(Map<String,String> saveTmp);
	
	int deleteAutoCreGovSditm(Map<String,String> saveTmp);
	
	int updateSditmDmnId(String dbSchId);
}
