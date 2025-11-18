/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CmvwSysareaService.java
 * 2. Package : kr.wise.cmvw.sysarea.service
 * 3. Comment : 
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 12. 오전 9:04:30
 * 6. 변경이력 : 
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 12. 		: 신규 개발.
 */
package kr.wise.commons.damgmt.sysarea.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.sysarea.service.DaSysareaService;
import kr.wise.commons.damgmt.sysarea.service.WaaSysArea;
import kr.wise.commons.damgmt.sysarea.service.WaaSysAreaMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.user.service.WaaUserMapper;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.stnd.service.WamSymn;
import kr.wise.meta.subjarea.service.WaaSubj;
import kr.wise.meta.subjarea.service.WaaSubjMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName : CmvwSysareaService
 * 2. Package  : kr.wise.cmvw.sysarea.service
 * 3. Comment  : 
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 12.
 * </PRE>
 */
@Service("sysareaService")
public class DaSysareaServiceImpl implements DaSysareaService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WaaSysAreaMapper mapper;
	
	@Inject
	private WaaSubjMapper subjMapper;
	
	@Inject
	private WaaUserMapper userMapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	
	public List<WaaSysArea> getSysareaList(WaaSysArea search) {
		List<WaaSysArea> list = mapper.selectList(search);
		return list;
	}
	
	
	public WaaSysArea findSysarea(WaaSysArea search) {
		
		return mapper.selectByPrimaryKey(search.getSysAreaId());
	}
	
	
	
	/** yeonho */
	public int regSysarea(WaaSysArea record, String uniqId) throws Exception {
		String tmpStatus = record.getIbsStatus();
		int result = 0;
		boolean isNew = true;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		//엑셀업로드시 작성한 SysAreaId에 대한 검증 -- 없으면 신규처리, 있으면 변경처리
		if(record.getSysAreaId() != null && !record.getSysAreaId().equals("")) {
			WaaSysArea tmpSysArea = mapper.selectByPrimaryKey(record.getSysAreaId());
			
			if(null == tmpSysArea || !tmpSysArea.getSysAreaId().equals(record.getSysAreaId())) {
				isNew = true;
				record.setSysAreaId(null);
			} else {
				isNew = false;

			}
		} else {
			isNew = true;
			record.setSysAreaId(null);
		}
		
		//담당자ID 확인하여 잘못되었을 경우 공백처리.
		if(StringUtils.hasText(record.getCrgUserId())) {
			WaaUser tmpVO = userMapper.selectByPrimaryKey(record.getCrgUserId());
			if(tmpVO == null || !tmpVO.getUserId().equals(record.getCrgUserId())) {
				record.setCrgUserId(null);
			}
		}
		
		
		if(isNew) { // 신규...
			
			
			record.setRegTypCd("C");
			
			record.setObjVers(1);
			
			record.setSysAreaId(objectIdGnrService.getNextStringId());
			
			
		} else { // 변경...
			//기존 레코드 만료처리...
			mapper.updateExpDtm(record);		
			
			if (UtilObject.isNull(record.getObjVers()))
				record.setObjVers(1);
			else 
				record.setObjVers(record.getObjVers()+1);
			
			record.setRegTypCd("U");			
//			result = mapper.updateByPrimaryKeySelective(record);
		} 
		
		
		record.setWritUserId(uniqId);
		result = mapper.insert(record);
		return result;
	}

	/**
	 * <PRE>
	 * 1. MethodName : regSysareaList
	 * 2. Comment    : 
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 13.
	 * </PRE>
	 *   @return void
	 *   @param list
	 * @throws Exception 
	 */
	public int regSysareaList(ArrayList<WaaSysArea> list) throws Exception {
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		int rescount = 0;
		
		for (WaaSysArea waaSysArea : list) {
			
			rescount += regSysarea(waaSysArea, user.getUniqId());
		}
		
		return rescount;
		
	}

/*	private int regSysarea(WaaSysArea waaSysArea, String uniqId) throws Exception {
		
		waaSysArea.setCrgUserId(uniqId);
		waaSysArea.setWritUserId(uniqId);
		
		return regSysarea(waaSysArea);
	}*/


	/**
	 * <PRE>
	 * 1. MethodName : delSysareaList
	 * 2. Comment    : 체크 상태인 리스트를 삭제상태로 변경 후 저장
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 14.
	 * </PRE>
	 *   @return void
	 *   @param list
	 * @throws Exception 
	 */
	public int delSysareaList(ArrayList<WaaSysArea> list) throws Exception {
		
				
		int result = 0;
		
		for (WaaSysArea waaSysArea : list) {
			String id = waaSysArea.getSysAreaId();
			
			WaaSubj subjVo = new WaaSubj(); 
			
			subjVo.setSysAreaId(id);
			
			List<WaaSubj> subjLst = subjMapper.selectChkSubjExists(subjVo);
			
			if(subjLst.size() > 0){
				
				return -2; 
			}
			 			
			if (id != null && !"".equals(id)) {
				waaSysArea.setIbsStatus("D");
				result += mapper.updateExpDtm(waaSysArea);;
			}
		}
		
		return result;
		
	}


	/** 시스템영역 일괄 삭제... */
	public int delsysarealist(WaaSysArea saveVO) {
		String id = saveVO.getSysAreaId();
		String[] ids = id.split("[|]");
		
		return mapper.delsysarealist(ids);
	}
	
}
