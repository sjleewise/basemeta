package kr.wise.advisor.prepare.udefoutlier.usrdef.service;

public interface UodcUsrdefService {

	WadUodcUsrdef getDataUsrDef(WadUodcUsrdef search);

	int regUodcUsrDef(WadUodcUsrdef mstData) throws Exception;

}
