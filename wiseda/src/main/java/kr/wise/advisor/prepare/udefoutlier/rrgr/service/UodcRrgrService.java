package kr.wise.advisor.prepare.udefoutlier.rrgr.service;

import java.util.List;

public interface UodcRrgrService {

	WadUodcRrgr getUodcRrgrDetail(WadUodcRrgr search);

	List<WadUodcRrgr> getUodcRrgrColList(WadUodcRrgr search);

	int regUodcRrgr(List<WadUodcRrgr> list, WadUodcRrgr mstData) throws Exception;

	List<WadUodcRrgr> getUodcRrgrColLstForScrt(WadUodcRrgr search); 

}
