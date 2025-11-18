package kr.wise.dq.stnd.service;

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
public interface WdqStndDomainService {

	WdqmDmn selectDomainDetail(String dmnId);

	List<WdqmDmn> getDomainList(WdqmDmn data);
	
	List<WdqmDmn> selectDmnChangeList(String dmnId);

	List<WdqmStwdCnfg> selectDmnInitList(String dmnId);
	
	List<WdqmDmn> selectDmnValueList(String dmnId);
	
	List<WdqmDmn> selectDmnValueChangeList(String dmnId);

	List<WdqmWhereUsed> selectDmnWhereUsedList(String dmnId);

	/** @param data
	/** @return yeonho */
	List<WdqmDmn> getDomainListWithCdVal(WdqmDmn data);
	
	List<WdqmCdVal> getSimpleCodeLst(WdqmCdVal data);

    List<WdqmCdVal> getComplexCodeLst(WdqmCdVal data);
    
    int saveDmnTransYnPrc(ArrayList<WdqmDmn> list);
    
    List<WdqmDmn> getDmnTransList(WdqmDmn data);

	List<WdqmCdVal> selectDmnValueListMsgPop(WdqmCdVal data);
	
	List<WdqmDmn> selectDmnComparisonList(String dmnId);
	
}
