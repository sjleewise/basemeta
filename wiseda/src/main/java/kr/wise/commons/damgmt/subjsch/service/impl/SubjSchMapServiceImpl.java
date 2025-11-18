/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : SubjSchMapServiceImpl.java
 * 2. Package : kr.wise.commons.damgmt.subjsch.service.impl
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 5. 23. 오후 5:04:00
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 5. 23. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.subjsch.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.damgmt.subjsch.service.SubjSchMapService;
import kr.wise.commons.damgmt.subjsch.service.WaaSubjDbSchMap;
import kr.wise.commons.damgmt.subjsch.service.WaaSubjDbSchMapMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSchMapper;
import kr.wise.meta.subjarea.service.WaaSubj;
import kr.wise.meta.subjarea.service.WaaSubjMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : SubjSchMapServiceImpl.java
 * 3. Package  : kr.wise.commons.damgmt.subjsch.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 5. 23. 오후 5:04:00
 * </PRE>
 */
@Service("SubjSchMapService")
public class SubjSchMapServiceImpl implements SubjSchMapService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	WaaSubjMapper waaSubjMapper;
	
	@Inject
	WaaDbSchMapper waaDbSchMapper;
	
	@Inject
	WaaSubjDbSchMapMapper waaSubjDbSchMapMapper;
	
	@Override
	public int regSubjSchMapList(ArrayList<WaaSubjDbSchMap> list) {
		int result = 0;
		for (WaaSubjDbSchMap waaSubjDbSchMap : list) {
			result += regSubjSchMap(waaSubjDbSchMap);
		}
		return result;
		
	}

	private int regSubjSchMap(WaaSubjDbSchMap record) {
		logger.debug("{}", record);
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
						
		WaaSubj subjVO = waaSubjMapper.selectByPrimaryKey(record.getSubjId());
		WaaDbSch dbSchVO = waaDbSchMapper.selectByPrimaryKey(record.getDbSchId());
		
		//주제영역ID, 스키마ID가 있는지 확인하여 없을경우 리턴처리..(데이터 미입력)
		if(subjVO == null || !subjVO.getSubjId().equals(record.getSubjId())) {
			return 0;
		}
		if(dbSchVO == null || !dbSchVO.getDbSchId().equals(record.getDbSchId())) {
			return 0;
		}
		
			//주제영역ID, 스키마ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..(데이터 미입력)
			WaaSubjDbSchMap tmpVO = waaSubjDbSchMapMapper.selectByPrimaryKey(record.getDbSchId(), record.getSubjId());
			if (tmpVO == null || !tmpVO.getSubjId().equals(record.getSubjId()) || !tmpVO.getDbSchId().equals(record.getDbSchId())) {
				record.setObjVers(1);
				record.setRegTypCd("C");
				
			} else {
				if (UtilObject.isNull(record.getObjVers())) {
					record.setObjVers(1);
				}
				else { 
					record.setObjVers(record.getObjVers()+1);
					record.setRegTypCd("U");
				}
				waaSubjDbSchMapMapper.updateExpDtm(record);
				
				
			}
			
		
		
		
		record.setWritUserId(user.getUniqId());
		result = waaSubjDbSchMapMapper.insertSelective(record);
		return result;
	}

	@Override
	public List<WaaSubjDbSchMap> getList(WaaSubjDbSchMap search) {
		return waaSubjDbSchMapMapper.selectList(search);
	}

	@Override
	public int delSubjSchMapList(ArrayList<WaaSubjDbSchMap> list) {
		int result = 0;
		for (WaaSubjDbSchMap waaSubjDbSchMap : list) {
			String subjId = waaSubjDbSchMap.getSubjId();
			String dbSchId = waaSubjDbSchMap.getDbSchId();
			if (subjId != null && dbSchId != null && !"".equals(subjId) && !"".equals(dbSchId)) {
				//WaaDmng.setIbsStatus("D");
				waaSubjDbSchMap.setExpDtm(null);
				result += waaSubjDbSchMapMapper.updateExpDtm(waaSubjDbSchMap);
			}
		}	
		return result;
	}

}
