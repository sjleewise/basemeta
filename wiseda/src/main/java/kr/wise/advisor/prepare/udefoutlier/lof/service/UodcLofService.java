package kr.wise.advisor.prepare.udefoutlier.lof.service;

import java.util.List;

import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseCol;

public interface UodcLofService {

	WadUodcLof getUodcLofDetail(WadUodcLof search);

	List<WadUodcLof> getUodcLofColList(WadUodcLof search);

	int regUodcLof(List<WadUodcLof> list, WadUodcLof mstData) throws Exception;

	List<WadUodcLof> getUodcLofColLstForScrt(WadUodcLof search); 

}
