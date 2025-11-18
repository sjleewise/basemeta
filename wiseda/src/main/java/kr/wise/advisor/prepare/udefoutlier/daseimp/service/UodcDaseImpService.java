package kr.wise.advisor.prepare.udefoutlier.daseimp.service;

import java.util.List;
import java.util.Map;



public interface UodcDaseImpService {

	List<WadUodcDaseImp> getUodcDaseImpList(WadUodcDaseImp search);

	int regDataImptlist(List<WadUodcDaseImpCol> list, WadUodcDaseImp mstData) throws Exception;

	List<WadUodcDaseImpCol> getUodcDaseImpColList(WadUodcDaseImp search);

	WadUodcDaseImp getUodcDaseImpDetail(WadUodcDaseImp search);

	List<WadUodcDaseImpCol> gectDaseImpColForScrt(WadUodcDaseImp search);

	WadUodcDaseImp getUodcDaseImpDataDcd(WadUodcDaseImp search);

	List<WadUodcDaseImpCol> getResultViewColNm(WadUodcDaseImpCol search);

	List<WadUodcDaseImpCol> getUodcDaseImpHiveColList(WadUodcDaseImp search);   
}
