package kr.wise.meta.govstnd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface GovSditmMapper {
	List<GovSditm> selectdiagSditmList(GovSditm data);
	
	int insertDiagSditmList(GovSditm data);
	
	int updateDiagSditmList(GovSditm data);
	
	int deleteDiagSditmList(GovSditm data);
	
	List<HashMap> selectInfoSys();
	
	int checkVrfRmk(GovSditm data);
	int checkDupGovSditm(GovSditm data);
	int checkGovDmn(GovSditm data);
	int updateNotDmn(GovSditm data);
	int updateVrfRmk(GovSditm data);
	
	int insertAutoCreGovSditm(Map<String,String> saveTmp);
	
	int deleteAutoCreGovSditm(Map<String,String> saveTmp);
	
	int updateSditmDmnId(String dbSchId);
}
