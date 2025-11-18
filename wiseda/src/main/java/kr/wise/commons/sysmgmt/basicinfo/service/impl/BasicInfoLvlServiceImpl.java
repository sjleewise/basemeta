/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : BasicInfoLvlServiceImpl.java
 * 2. Package : kr.wise.commons.sysmgmt.basicinfo.service.impl
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 7. 7. 오후 4:37:05
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 7. 7. :            : 신규 개발.
 */
package kr.wise.commons.sysmgmt.basicinfo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvlMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : BasicInfoLvlServiceImpl.java
 * 3. Package  : kr.wise.commons.sysmgmt.basicinfo.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 7. 8. 오전 10:57:22
 * </PRE>
 */ 
@Service("BasicInfoLvlService")
public class BasicInfoLvlServiceImpl implements BasicInfoLvlService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	WaaBscLvlMapper waaBscLvlMapper;
	


	/** @param waaCriInfoLvl
	/** @return yeonho */
	private int regInfoLvl(WaaBscLvl record) {
		logger.debug("{}", record);
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;



		//기준정보ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		WaaBscLvl tmpVO = waaBscLvlMapper.selectByPrimaryKey(record.getBscId());
		if (tmpVO == null || !tmpVO.getBscId().equals(record.getBscId())) {
			record.setObjVers(1);
			record.setRegTypCd("C");
		} else {
			record.setObjVers(record.getObjVers() + 1);
			record.setRegTypCd("U");
			waaBscLvlMapper.updateExpDtm(record);
		}
		
		record.setWritUserId(user.getUniqId());
		result = waaBscLvlMapper.insertSelective(record);
		return result;
	}

	/** yeonho */
	@Override
	public List<WaaBscLvl> selectBasicInfoLvlList(WaaBscLvl search) {
		return waaBscLvlMapper.selectList(search);
	}

	/** yeonho */
	@Override
	public int regBasicInfoLvlList(ArrayList<WaaBscLvl> list) {
		int result = 0;
		for (WaaBscLvl waaBscLvl : list) {
			result += regInfoLvl(waaBscLvl);
		}

		return result;
	}

	/** yeonho */
	@Override
	public int delBasicInfoLvlList(ArrayList<WaaBscLvl> list) {
		int result = 0;
		for (WaaBscLvl waaBscLvl : list) {
			String id = waaBscLvl.getBscId();
			if (id != null && !"".equals(id)) {
				//WaaInfoType.setIbsStatus("D");
				waaBscLvl.setExpDtm(null);
				result += waaBscLvlMapper.updateExpDtm(waaBscLvl);
			}
		}
		return result;
	}

	/** yeonho */
	@Override
	public WaaBscLvl selectBasicInfoList(WaaBscLvl search) {
		return waaBscLvlMapper.selectByPrimaryKey(search.getBscId());
	}
	

	/** 페이지별 기본정보레벨, SELECT BOX ID 조회 */
	@Override
	public WaaBscLvl selectBasicInfoList(String bscId) throws Exception {
		logger.debug("{}", bscId);
				
		WaaBscLvl data = waaBscLvlMapper.selectByPrimaryKey(bscId);
		
		return data; 
	}

	/** 기본정보레벨 중복저장 검증 */
	@Override
	public int getBscIdCnt(WaaBscLvl waaBscLvl) {
		return waaBscLvlMapper.selectBscId(waaBscLvl);
	}

}
