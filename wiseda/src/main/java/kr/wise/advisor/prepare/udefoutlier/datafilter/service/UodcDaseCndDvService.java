package kr.wise.advisor.prepare.udefoutlier.datafilter.service;

import java.util.List;
import java.util.Map;



public interface UodcDaseCndDvService {

	WadUodcDaseCndDv getUodcDaseCndDvDetail(WadUodcDaseCndDv search); 

	int regDaseCndDv(WadUodcDaseCndDv mstData) throws Exception;

	int regDaseCndDvCnd(List<WadUodcDaseCndDvCnd> list, WadUodcDaseCndDv mstData) throws Exception;

	List<WadUodcDaseCndDvCnd> getUodcDataFilterColList(WadUodcDaseCndDv search);        

	int delDaseCndDvCnd(List<WadUodcDaseCndDvCnd> list, WadUodcDaseCndDv mstData) throws Exception;

	List<WadUodcDaseCndDvCnd>  getUodcDaseCndDvCndForScrt(WadUodcDaseCndDv search); 
}
