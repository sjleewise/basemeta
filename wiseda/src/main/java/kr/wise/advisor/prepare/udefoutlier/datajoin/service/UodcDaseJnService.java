package kr.wise.advisor.prepare.udefoutlier.datajoin.service;

import java.util.List;


import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseCol;

public interface UodcDaseJnService {

	WadUodcDaseJn getUodcDaseJnDetail(WadUodcDaseJn search);

	List<WadUodcDaseJnCol> getUodcJoinColList(WadUodcDaseJn search);

	List<WadUodcAnaDaseCol> getSubSelBox(WadUodcDaseJnCol search);

	int delDaseJnCol(List<WadUodcDaseJnCol> list, WadUodcDaseJn mstData);

	int regUodcJoin(List<WadUodcDaseJnCol> list, WadUodcDaseJn mstData) throws Exception;

	List<WadUodcDaseJnCol> getUodcDaseJnColLstForScrt(WadUodcDaseJn search); 

}
