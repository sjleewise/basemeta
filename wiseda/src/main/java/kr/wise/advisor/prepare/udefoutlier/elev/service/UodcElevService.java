package kr.wise.advisor.prepare.udefoutlier.elev.service;

import java.util.List;

import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseCol;

public interface UodcElevService {

	WadUodcElev getUodcElevDetail(WadUodcElev search);

	List<WadUodcElev> getUodcElevColList(WadUodcElev search);

	int regUodcElev(List<WadUodcElev> list, WadUodcElev mstData) throws Exception;

	List<WadUodcElev> getUodcElevColLstForScrt(WadUodcElev search); 

}
