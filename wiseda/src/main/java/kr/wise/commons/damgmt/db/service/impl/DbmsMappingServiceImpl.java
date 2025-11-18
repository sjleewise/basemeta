/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbmsMappingServiceImpl.java
 * 2. Package : kr.wise.commons.damgmt.db.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 7. 8. 오후 2:44:02
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 7. 8. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.db.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.db.service.DbmsMappingService;
import kr.wise.commons.damgmt.db.service.WaaDbMap;
import kr.wise.commons.damgmt.db.service.WaaDbMapMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSchMapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DbmsMappingServiceImpl.java
 * 3. Package  : kr.wise.commons.damgmt.db.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 7. 8. 오후 2:44:02
 * </PRE>
 */
@Service("dbmsMappingService")
public class DbmsMappingServiceImpl implements DbmsMappingService {

	private final  Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private EgovIdGnrService objectIdGnrService;
	
	@Inject
	private WaaDbMapMapper mapper;
	
	@Inject
	WaaDbSchMapper waaDbSchMapper;
	
	@Inject
	WaaDbMapMapper waaDbMapMapper;


	/** insomnia */
	public List<WaaDbMap> getDbMapList(WaaDbMap search) {
		return mapper.selectDbMapList(search);
	}
	
	
	/* 유성열 */
	public int regDbMapList(ArrayList<WaaDbMap> list) throws Exception {
		int result = 0;
		for (WaaDbMap waaDbMap : list) {
			result += regDbMap(waaDbMap);
		}
		return result;
		
	}
	

	/* 유성열 */
	private int regDbMap(WaaDbMap record) throws Exception {
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		record.setWritUserId(user.getUniqId());
		int result = 0;
	
		if (record.getIbsStatus() .equals("I")) { 
			//동일 소스 타겟 스키마명 확인
			 List<WaaDbMap> dupChkMap = mapper.selectByPrimaryKey(record);
			if( dupChkMap.size() > 0){
				return -9;
			}
			//OBJ_ID 셋팅
			String objid = objectIdGnrService.getNextStringId();
			record.setDbMapId(objid);
			record.setObjVers(1);
			
		} else if (record.getIbsStatus() .equals("U")) {
			record.setObjVers(record.getObjVers()+1);
			record.setRegTypCd("U");
			result = waaDbMapMapper.updateExpDtm(record);	
			
		}	

		result = waaDbMapMapper.insertSelective(record);
		
		return result;
	}
	
	 
	 
	/* 유성열 */
	public int delDbMapList(ArrayList<WaaDbMap> list) {
		int result = 0;
		for (WaaDbMap waaDbMap : list) {
			waaDbMap.setRegTypCd("D");
			result += waaDbMapMapper.deleteByPrimaryKey(waaDbMap);	
		}	
		return result;
	}
	
		
	public WaaDbMap getDbMapDefaultOne(WaaDbMap search) {
		return mapper.selectDbMapDefaultOne(search);
	}

}
