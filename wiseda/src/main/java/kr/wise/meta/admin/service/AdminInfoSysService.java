/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : AdminInfoSysService.java
 * 2. Package : kr.wise.meta.admin.service
 * 3. Comment : 기관메타 > 관리자 - 정보시스템관리
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.12.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.12. :            : 신규 개발.
 */
package kr.wise.meta.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AdminInfoSysService {

	List<WaaInfoSys> getInfoSysList(WaaInfoSys search);
	
	int regInfoSysList(ArrayList<WaaInfoSys> list) throws Exception;
	
	int regInfoSys(WaaInfoSys record);
	
	int delInfoSysList(ArrayList<WaaInfoSys> list) throws Exception;
	
	List<WaaInfoSys> getInfoSysListApi(HashMap<String, Object> paramMap);
	
	//중복제거 시스템코드, 시스템명 조회
	List<WaaInfoSys> getDicInfoSysList(WaaInfoSys search);

	WaaInfoSys getInfoSysDetail(String orgCd, String infoSysCd);
	
}
