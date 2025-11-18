package kr.wise.commons.sysmgmt.dept.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.sysmgmt.dept.service.DeptService;
import kr.wise.commons.sysmgmt.dept.service.WaaDept;
import kr.wise.commons.sysmgmt.dept.service.WaaDeptMapper;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service("deptService")
public class DeptServiceImpl implements DeptService {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaaDeptMapper mapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;
	
	public List<WaaDept> getList(WaaDept search) {

		List<WaaDept> list = mapper.selectList(search);

		return list;

	}

	public WaaDept findDept(WaaDept search) {
		// TODO Auto-generated method stub
		return null;
	}

	public int regDept(WaaDept record) throws Exception {
		logger.debug("DeptServiceImpl.Java regDept Method");
		String tmpStatus = record.getIbsStatus();
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
						
		//부서의 기본정보레벨 값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DEPT");
		logger.debug("기본정보레벨 조회 : {}", data);
 		logger.debug("{}", record);
		if (data != null && data.getBscLvl() <= record.getDeptLvl()) {
			return -5;
		}
		
		//상위메뉴가 없을경우 최상위 메뉴레벨로 설정(0레벨)
		if(!StringUtils.hasText(record.getUppDeptId())) {
			record.setDeptLvl(0);
		} else {
		//상위메뉴명을 적었을경우, 상위메뉴의 레벨+1 처리
			WaaDept tmpVO = mapper.selectByPrimaryKey(record.getUppDeptId());
			if(tmpVO != null && tmpVO.getDeptId().equals(record.getUppDeptId())) {
				record.setDeptLvl(tmpVO.getDeptLvl() + 1);
			} else {
			//메뉴명이 서로 불일치할 경우 최상위메뉴레벨 설정
				record.setUppDeptId(null);
				record.setDeptLvl(0);
			}
		}
			
		
		
		//메뉴ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if("I".equals(tmpStatus) && !StringUtils.hasText(record.getDeptId())) {  // 신규...
			
			
			record.setDeptId(objectIdGnrService.getNextStringId());
 			record.setRegTypCd("C");
 			record.setObjVers(1);
			
		} else if("U".equals(tmpStatus) || "D".equals(tmpStatus) || StringUtils.hasText(record.getDeptId())) {
			if ("I".equals(tmpStatus) || "R".equals(tmpStatus)) {
				record.setRegTypCd("U");
			} else {
				record.setRegTypCd(tmpStatus);
			}
			
			if (UtilObject.isNull(record.getObjVers())) {
				record.setObjVers(1);
			}
			else { 
				record.setObjVers(record.getObjVers()+1);
			}
			//result = menuMapper.updateByPrimaryKeySelective(record);
		} 
		
		mapper.updateExpDtm(record);
  		record.setWritUserId(user.getUniqId());
		result = mapper.insertSelective(record);
		return result;
	}

	public int regList(ArrayList<WaaDept> list) throws Exception {
		int result = 0;
		
		for (WaaDept record : list) {
			//레벨이 0이상이며, 상위ID가 없고, 상위부서명은 있는 경우
			if( UtilString.isBlank(record.getUppDeptId()) && StringUtils.hasText(record.getUppDeptNm()) && record.getDeptLvl() > 0) {
				WaaDept wd = mapper.selectByUppDeptNm(record);
				
				if(wd != null){
					record.setUppDeptId(wd.getDeptId());
				}
			}
			
			//그리드 상태가 있을 경우만 DB에 처리한다...
			if(!UtilString.isBlank(record.getIbsStatus())) {
                  				result += regDept(record);
			}
			
			mapper.updateAllDeptNm(record.getDeptId());
		}
		

		return result;
	}

	public int delList(ArrayList<WaaDept> list) throws Exception {
		int result = 0;
		for (WaaDept WaaDept : list) {
			String id = WaaDept.getDeptId();
			if (id != null && !"".equals(id)) {
				WaaDept.setIbsStatus("D");

				result += regDept(WaaDept);
			}
		}

		return result;
	}

	@Override
	public int getDeptCnt(WaaDept waaDept) {
		// TODO Auto-generated method stub
		return mapper.selectDeptCnt(waaDept);
	}

}
