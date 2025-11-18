package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : StndDomainService.java
 * 3. Package  : kr.wise.meta.stnd.service
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 18. 오전 9:34:07
 * </PRE>
 */ 
public interface StndDomainService {

	WamDmn selectDomainDetail(String dmnId);

	List<WamDmn> getDomainList(WamDmn data);
	
	List<WamDmn> selectDmnChangeList(String dmnId);

	List<WamStwdCnfg> selectDmnInitList(String dmnId);
	
	List<WamDmn> selectDmnValueList(String dmnId);
	
	List<WamDmn> selectDmnValueChangeList(String dmnId);

	List<WamWhereUsed> selectDmnWhereUsedList(String dmnId);

	/** @param data
	/** @return yeonho */
	List<WamDmn> getDomainListWithCdVal(WamDmn data);
	
	List<WamCdVal> getSimpleCodeLst(WamCdVal data);

    List<WamCdVal> getComplexCodeLst(WamCdVal data);
    
    int saveDmnTransYnPrc(ArrayList<WamDmn> list);
    
    List<WamDmn> getDmnTransList(WamDmn data);

	List<WamCdVal> selectDmnValueListMsgPop(WamCdVal data);
	
	List<WamDmn> selectDmnComparisonList(String dmnId);

	String selectGovYn(String dmnId);

	WamDmn selectGovDomainDetail(String dmnId);

	List<WamDmn> selectGovDmnValueList(String dmnId);
	
}
