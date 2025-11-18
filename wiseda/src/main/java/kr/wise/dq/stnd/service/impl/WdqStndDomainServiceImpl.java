/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndDoaminServiceImpl.java
 * 2. Package : kr.wise.dq.stnd.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 25. 오전 12:22:23
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 25. :            : 신규 개발.
 */
package kr.wise.dq.stnd.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.stnd.service.WdqStndDomainService;
import kr.wise.dq.stnd.service.WdqmCdVal;
import kr.wise.dq.stnd.service.WdqmCdValMapper;
import kr.wise.dq.stnd.service.WdqmDmn;
import kr.wise.dq.stnd.service.WdqmDmnMapper;
import kr.wise.dq.stnd.service.WdqmStwdCnfg;
import kr.wise.dq.stnd.service.WdqmStwdCnfgMapper;
import kr.wise.dq.stnd.service.WdqmWhereUsed;
import kr.wise.dq.stnd.service.WdqmWhereUsedMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndDoaminServiceImpl.java
 * 3. Package  : kr.wise.dq.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 25. 오전 12:22:23
 * </PRE>
 */
@Service("wdqStndDomainServiceImpl")
public class WdqStndDomainServiceImpl implements WdqStndDomainService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WdqmDmnMapper WdqmDmnMapper;
	
	@Inject
	private WdqmStwdCnfgMapper cnfgMapper;
	
	@Inject
	private WdqmCdValMapper cdValMapper;
	
	@Inject
	private WdqmWhereUsedMapper whereUsedMapper;

	/** insomnia */
	public List<WdqmDmn> getDomainList(WdqmDmn data) {
		
		return WdqmDmnMapper.selectList(data);
		//SLC의 경우에만 해당
//		return WdqmDmnMapper.selectListSLC(data);
		
	}
	
	public List<WdqmDmn> getDmnTransList(WdqmDmn data){
	
		
		return WdqmDmnMapper.selectDmnTransList(data);
	}

	/** yeonho */
	@Override
	public WdqmDmn selectDomainDetail(String dmnId) {
		return WdqmDmnMapper.selectByPrimaryKey(dmnId);
	}

	@Override
	public List<WdqmDmn> selectDmnChangeList(String dmnId) {
		logger.debug("{}", dmnId);
		return WdqmDmnMapper.selectDmnChangeList(dmnId);
	}

	@Override
	public List<WdqmStwdCnfg> selectDmnInitList(String dmnId) {
		logger.debug("{}", dmnId);
		return cnfgMapper.selectDmnInitList(dmnId);
	}

	@Override
	public List<WdqmDmn> selectDmnValueList(String dmnId) {
		return cdValMapper.selectDmnValueList(dmnId);
	}
	
	@Override
	public List<WdqmCdVal> selectDmnValueListMsgPop(WdqmCdVal data) {
		return cdValMapper.selectDmnValueListMsgPop(data);
	}

	@Override
	public List<WdqmDmn> selectDmnValueChangeList(String dmnId) {
		return cdValMapper.selectDmnValueChangeList(dmnId);

	}

	@Override
	public List<WdqmWhereUsed> selectDmnWhereUsedList(String dmnId) {
		return whereUsedMapper.selectDmnWhereUsedList(dmnId);
	}

	/** yeonho */
	@Override
	public List<WdqmDmn> getDomainListWithCdVal(WdqmDmn data) {
		return WdqmDmnMapper.selectDmnListWithCdVal(data);
	}


	@Override
	public List<WdqmCdVal> getSimpleCodeLst(WdqmCdVal data){
	   return cdValMapper.selectSimpleCodeLst(data);	
    }
	
		@Override
	public List<WdqmCdVal> getComplexCodeLst(WdqmCdVal data){
	   return cdValMapper.selectComplexCodeLst(data);	
    }

		@Override
	public int saveDmnTransYnPrc(ArrayList<WdqmDmn> list) {
		int result = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String userId = user.getUniqId();
		logger.debug("{}", list);
		
		for (WdqmDmn record : list) {
			result += WdqmDmnMapper.saveDmnTransYnPrc(record);
			if(record.getTransYn().equals("1")){ //여부가 체크되었을 때만. 여부가 해제되도 기존의 용어들이 변환여부는 남아있는다.
	            result += WdqmDmnMapper.updateSditmTransYnPrc(record);  //해당 도메인을 사용하는 표준용어의 테스트변환여부를 업데이트
			}
		}
		return result;
	}

		@Override
		public List<WdqmDmn> selectDmnComparisonList(String dmnId) {
			return WdqmDmnMapper.selectDmnComparisonList(dmnId);
		}	
		
}