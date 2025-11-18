package kr.wise.commons.damgmt.gov.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DmngInfotpMapService.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 18. 오전 9:46:06
 * </PRE>
 */ 
public interface GovLstMapService {
	
	// 공통 표준 용어 관리
	
	public List<WaaGovStwd> getList(WaaGovStwd search) ;
	
	public int delgovStwdLstList(ArrayList<WaaGovStwd> list) ;

	public int regGovStwdLstList(ArrayList<WaaGovStwd> list) throws Exception;
	
	
    // 공통 표준 단어 관리
	
	public List<WaaGovSditm> sditmgetList(WaaGovSditm search) ;
	
	public int delgovSditmLstList(ArrayList<WaaGovSditm> list) ;

	public int regGovSditmLstList(ArrayList<WaaGovSditm> list) throws Exception;

	
	// 공통 표준 도메인 관리
	
	public List<WaaGovDmn> dmngetList(WaaGovDmn search) ;
	
	public int delgovdmnLstList(ArrayList<WaaGovDmn> list) ;

	public int regGovdmnLstList(ArrayList<WaaGovDmn> list) throws Exception;
	
	// 공통 표준 유효값 관리
	
	public List<WaaGovCdVal> cdValgetList(WaaGovCdVal search) ;
	
	public int delgovCdValLstList(ArrayList<WaaGovCdVal> list) ;
//
	public int regGovCdValLstList(ArrayList<WaaGovCdVal> list) throws Exception;
	
	// 기타

//	public WaaDmngInfotpMap findDmngInfotpMap(WaaDmngInfotpMap search) ;
//	
//	public int regdmngInfotpMap(WaaDmngInfotpMap record);
}
