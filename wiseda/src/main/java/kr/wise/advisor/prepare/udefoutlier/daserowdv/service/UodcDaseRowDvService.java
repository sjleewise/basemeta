package kr.wise.advisor.prepare.udefoutlier.daserowdv.service;

import java.util.List;
import java.util.Map;



public interface UodcDaseRowDvService {

	WadUodcDaseRowDv getUodcDaseRowDvDetail(WadUodcDaseRowDv search); 

	int regRowDaseDv(WadUodcDaseRowDv mstData) throws Exception;

	
}
