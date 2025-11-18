package kr.wise.advisor.prepare.udefoutlier.isolationforest.service;

import java.util.List;

import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseCol;

public interface UodcIsfrService {

	WadUodcIsfr getUodcIsfrDetail(WadUodcIsfr search);

	List<WadUodcIsfr> getUodcIsfrColList(WadUodcIsfr search);

	int regUodcIsfr(List<WadUodcIsfr> list, WadUodcIsfr mstData) throws Exception;

	List<WadUodcIsfr> getUodcIsfrColLstForScrt(WadUodcIsfr search); 

}
