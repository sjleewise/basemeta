package kr.wise.advisor.prepare.udefoutlier.datacleansing.service;

import java.util.List;

import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseCol;

public interface UodcDataClnService {

	WadUodcDataCln getDataClnDetail(WadUodcDataCln search);

	List<WadUodcAnaDaseCol> getUodcDataClnColList(WadUodcDataClnCol search);

	int regUodcDataCln(List<WadUodcDataClnCol> list, WadUodcDataCln mstData) throws Exception;
	
	List<WadUodcDataClnCol> getUodcDataClnColList2(WadUodcDataClnCol search);

}
