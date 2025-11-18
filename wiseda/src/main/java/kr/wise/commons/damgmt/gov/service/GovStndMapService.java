package kr.wise.commons.damgmt.gov.service;

import java.util.ArrayList;
import java.util.List;

public interface GovStndMapService {
	//공통표준단어 매핑
	WaaGovStwdMap selectStwdMapDetail(String govStwdId);
	List<WaaGovStwdMap> selectGovStwdMapList(WaaGovStwdMap record);
	int saveGovStwdMapRow(WaaGovStwdMap saveVO) throws Exception;
	int deleteGovStwdMapList(ArrayList<WaaGovStwdMap> list);
	int saveGovStwdMapList(ArrayList<WaaGovStwdMap> list) throws Exception;
	List<WaaGovStwdMap> selectGovStwdMapChangeList(String govStwdId);

	//공통표준도메인 매핑
	WaaGovDmnMap selectDmnMapDetail(String govDmnId);
	List<WaaGovDmnMap> selectGovDmnMapList(WaaGovDmnMap record);
	int saveGovDmnMapRow(WaaGovDmnMap saveVO) throws Exception;
	int deleteGovDmnMapList(ArrayList<WaaGovDmnMap> list);
	int saveGovDmnMapList(ArrayList<WaaGovDmnMap> list) throws Exception;
	List<WaaGovDmnMap> selectGovDmnMapChangeList(String govDmnId);
	
	//공통표준용어 매핑
	WaaGovSditmMap selectSditmMapDetail(String govSditmId);
	List<WaaGovSditmMap> selectGovSditmMapList(WaaGovSditmMap record);
	int saveGovSditmMapRow(WaaGovSditmMap saveVO) throws Exception;
	int deleteGovSditmMapList(ArrayList<WaaGovSditmMap> list);
	int saveGovSditmMapList(ArrayList<WaaGovSditmMap> list) throws Exception;
	List<WaaGovSditmMap> selectGovSditmMapChangeList(String govSditmId);
}
