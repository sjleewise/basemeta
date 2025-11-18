package kr.wise.advisor.prepare.udefoutlier.saveres.service;

import java.util.List;

import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseCol;

public interface UodcSaveResService {

	WadUodcSvRes getUodcSvResDetail(WadUodcSvRes search); 

	
	int regUodcSvRes( WadUodcSvRes mstData) throws Exception;


	WadUodcSvRes getUodcSaveResForScrt(WadUodcSvRes search); 

}
