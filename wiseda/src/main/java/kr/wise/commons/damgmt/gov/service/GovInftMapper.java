package kr.wise.commons.damgmt.gov.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface GovInftMapper {

	List<WaaGovInft> selectTotList(WaaGovInft search);

	List<WaaGovInft> selectDbList(WaaGovInft search);

	List<WaaGovInft> selectTblList(WaaGovInft search);

	List<WaaGovInft> selectColList(WaaGovInft search);

	List<WaaGovInft> selectMapColList(WaaGovInft search);

	List<WaaGovInft> selectSubjBisMapList(WaaGovInft search);

	int deleteSubjBisMap(WaaGovInft govInft);

	int insertSubjBisMap(WaaGovInft govInft);


}