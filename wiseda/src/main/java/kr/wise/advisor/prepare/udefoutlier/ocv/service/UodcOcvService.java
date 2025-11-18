package kr.wise.advisor.prepare.udefoutlier.ocv.service;

import java.util.List;

import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseCol;

public interface UodcOcvService {

	WadUodcOcv getUodcOcvDetail(WadUodcOcv search);

	List<WadUodcOcv> getUodcOcvColList(WadUodcOcv search);

	int regUodcOcv(List<WadUodcOcv> list, WadUodcOcv mstData) throws Exception;

	List<WadUodcOcv> getUodcOcvColLstForScrt(WadUodcOcv search); 

}
