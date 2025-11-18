package kr.wise.commons.damgmt.gov.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface GovStndMapMapper {
	//공통표준 단어 매핑
	WaaGovStwdMap selectStwdMapDtlById(@Param("govStwdId") String govStwdId);
	List<WaaGovStwdMap> selectStwdMapList(WaaGovStwdMap record);
	int delGovStwdMap(@Param("govStwdId") String govStwdId);
	List<WaaGovStwdMap> selectStwdMapChgList(@Param("govStwdId") String govStwdId);
	String selectGovStwdMapId(WaaGovStwdMap record);
	int updateGovStwdMapById(WaaGovStwdMap record);
	int insertGovStwdMap(WaaGovStwdMap record);
	void updateGovStwdMapExpDtm(@Param("govStwdId") String govStwdId);
	WaaGovStwdMap selectStwdByLnm(WaaGovStwdMap record);

	//공통표준 도메인 매핑
	WaaGovDmnMap selectDmnMapDtlById(@Param("govDmnId") String govDmnId);
	List<WaaGovDmnMap> selectDmnMapList(WaaGovDmnMap record);
	int delGovDmnMap(@Param("govDmnId") String govDmnId);
	List<WaaGovDmnMap> selectDmnMapChgList(@Param("govDmnId") String govDmnId);
	String selectGovDmnMapId(WaaGovDmnMap record);
	int updateGovDmnMapById(WaaGovDmnMap record);
	int insertGovDmnMap(WaaGovDmnMap record);
	void updateGovDmnMapExpDtm(@Param("govDmnId") String govDmnId);
	WaaGovDmnMap selectDmnByLnm(WaaGovDmnMap record);
	
	//공통표준 용어 매핑
	WaaGovSditmMap selectSditmMapDtlById(@Param("govSditmId") String govSditmId);
	List<WaaGovSditmMap> selectSditmMapList(WaaGovSditmMap record);
	int delGovSditmMap(@Param("govSditmId") String govSditmId);
	List<WaaGovSditmMap> selectSditmMapChgList(@Param("govSditmId") String govSditmId);
	String selectGovSditmMapId(WaaGovSditmMap record);
	int updateGovSditmMapById(WaaGovSditmMap record);
	int insertGovSditmMap(WaaGovSditmMap record);
	void updateGovSditmMapExpDtm(@Param("govSditmId") String govSditmId);
	WaaGovSditmMap selectSditmByLnm(WaaGovSditmMap record);
	String selectGovDmnMapIdInSditm(WaaGovSditmMap record);
}
