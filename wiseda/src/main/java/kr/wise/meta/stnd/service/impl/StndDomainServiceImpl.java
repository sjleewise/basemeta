/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndDoaminServiceImpl.java
 * 2. Package : kr.wise.meta.stnd.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 25. 오전 12:22:23
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 25. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.stnd.service.StndDomainService;
import kr.wise.meta.stnd.service.WamCdVal;
import kr.wise.meta.stnd.service.WamCdValMapper;
import kr.wise.meta.stnd.service.WamDmn;
import kr.wise.meta.stnd.service.WamDmnMapper;
import kr.wise.meta.stnd.service.WamStwdCnfg;
import kr.wise.meta.stnd.service.WamStwdCnfgMapper;
import kr.wise.meta.stnd.service.WamWhereUsed;
import kr.wise.meta.stnd.service.WamWhereUsedMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndDoaminServiceImpl.java
 * 3. Package  : kr.wise.meta.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 25. 오전 12:22:23
 * </PRE>
 */
@Service("stndDoaminService")
public class StndDomainServiceImpl implements StndDomainService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamDmnMapper wamDmnMapper;
	
	@Inject
	private WamStwdCnfgMapper cnfgMapper;
	
	@Inject
	private WamCdValMapper cdValMapper;
	
	@Inject
	private WamWhereUsedMapper whereUsedMapper;

	/** insomnia */
	public List<WamDmn> getDomainList(WamDmn data) {
		
		return wamDmnMapper.selectList(data);
		//SLC의 경우에만 해당
//		return wamDmnMapper.selectListSLC(data);
		
	}
	
	public List<WamDmn> getDmnTransList(WamDmn data){
	
		
		return wamDmnMapper.selectDmnTransList(data);
	}

	/** yeonho */
	@Override
	public WamDmn selectDomainDetail(String dmnId) {
		return wamDmnMapper.selectByPrimaryKey(dmnId);
	}

	@Override
	public List<WamDmn> selectDmnChangeList(String dmnId) {
		logger.debug("{}", dmnId);
		return wamDmnMapper.selectDmnChangeList(dmnId);
	}

	@Override
	public List<WamStwdCnfg> selectDmnInitList(String dmnId) {
		logger.debug("{}", dmnId);
		return cnfgMapper.selectDmnInitList(dmnId);
	}

	@Override
	public List<WamDmn> selectDmnValueList(String dmnId) {
		return cdValMapper.selectDmnValueList(dmnId);
	}
	
	@Override
	public List<WamCdVal> selectDmnValueListMsgPop(WamCdVal data) {
		return cdValMapper.selectDmnValueListMsgPop(data);
	}

	@Override
	public List<WamDmn> selectDmnValueChangeList(String dmnId) {
		return cdValMapper.selectDmnValueChangeList(dmnId);

	}

	@Override
	public List<WamWhereUsed> selectDmnWhereUsedList(String dmnId) {
		return whereUsedMapper.selectDmnWhereUsedList(dmnId);
	}

	/** yeonho */
	@Override
	public List<WamDmn> getDomainListWithCdVal(WamDmn data) {
		return wamDmnMapper.selectDmnListWithCdVal(data);
	}


	@Override
	public List<WamCdVal> getSimpleCodeLst(WamCdVal data){
	   return cdValMapper.selectSimpleCodeLst(data);	
    }
	
		@Override
	public List<WamCdVal> getComplexCodeLst(WamCdVal data){
	   return cdValMapper.selectComplexCodeLst(data);	
    }

		@Override
	public int saveDmnTransYnPrc(ArrayList<WamDmn> list) {
		int result = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String userId = user.getUniqId();
		logger.debug("{}", list);
		
		for (WamDmn record : list) {
			result += wamDmnMapper.saveDmnTransYnPrc(record);
			if(record.getTransYn().equals("1")){ //여부가 체크되었을 때만. 여부가 해제되도 기존의 용어들이 변환여부는 남아있는다.
	            result += wamDmnMapper.updateSditmTransYnPrc(record);  //해당 도메인을 사용하는 표준용어의 테스트변환여부를 업데이트
			}
		}
		return result;
	}

		@Override
		public List<WamDmn> selectDmnComparisonList(String dmnId) {
			return wamDmnMapper.selectDmnComparisonList(dmnId);
		}

		@Override
		public String selectGovYn(String dmnId) {
			// TODO Auto-generated method stub
			return wamDmnMapper.selectGovYn(dmnId);
		}

		@Override
		public WamDmn selectGovDomainDetail(String dmnId) {
			// TODO Auto-generated method stub
			return wamDmnMapper.selectGovByPrimaryKey(dmnId);
		}

		@Override
		public List<WamDmn> selectGovDmnValueList(String dmnId) {
			// TODO Auto-generated method stub
			return cdValMapper.selectGovDmnValueList(dmnId);
		}	
		
}