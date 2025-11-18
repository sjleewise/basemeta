/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbRoleServiceImpl.java
 * 2. Package : kr.wise.commons.damgmt.db.service.impl
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 8. 13. 오전 10:20:09
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 8. 13. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.db.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.db.service.DbRoleService;
import kr.wise.commons.damgmt.db.service.WaaDbRole;
import kr.wise.commons.damgmt.db.service.WaaDbRoleMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbRoleServiceImpl.java
 * 3. Package  : kr.wise.commons.damgmt.db.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 8. 13. 오전 10:20:09
 * </PRE>
 */
@Service("DbRoleService")
public class DbRoleServiceImpl implements DbRoleService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WaaDbRoleMapper waaDbRoleMapper; 
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	/** yeonho */
	@Override
	public List<WaaDbRole> getDbRoleList(WaaDbRole search) {
		return waaDbRoleMapper.getDbRoleList(search);
	}

	/** yeonho 
	 * @throws Exception */
	@Override
	public int regDbRoleList(ArrayList<WaaDbRole> list) throws Exception {
		int result = 0;
		
		for (WaaDbRole record : list) {
						
			//그리드 상태가 있을 경우만 DB에 처리한다...
			if(!UtilString.isBlank(record.getIbsStatus())) {
				result += regDbRole(record);
			}

		}
		//mapper.updateAllDeptNm();

		return result;
	}

	/** @param record
	/** @return yeonho 
	 * @throws Exception */
	private int regDbRole(WaaDbRole record) throws Exception {
		//PNM기준으로 신규/변경이 결정되나, PNM만 기준으로 잡으면 변경건이 신규가 될수 있음.
		//ID로 우선검색후 동일ID 존재시 변경건 처리, 미존재시 PNM으로 비교하여 PNM존재시 변경, 없을시 신규처리.
		
		boolean isNew = true;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;

		//엑셀업로드시 작성한 Role ID에 대한 검증 -- 없으면 신규대상, 있으면 변경처리
		if(record.getRoleId() != null && !record.getRoleId().equals("")) {
			WaaDbRole tmpRole = waaDbRoleMapper.selectByPrimaryKey(record.getRoleId());
			
			if(null == tmpRole || !tmpRole.getRoleId().equals(record.getRoleId())) {
				isNew = true;
				record.setRoleId(null);
			} else {
				isNew = false;
				record.setRoleId(tmpRole.getRoleId());
				record.setObjVers(tmpRole.getObjVers());
			}
		} else {
			isNew = true;
			record.setRoleId(null);
		}
		
		//신규 대상일경우 PNM, db로 비교한다.
		if(isNew) {
			WaaDbRole tmpRole2 = waaDbRoleMapper.selectByPnm(record.getRolePnm(),record.getDbConnTrgId());
			if(record.getRolePnm() != null && !record.getRolePnm().equals("")) {
				
				if(null == tmpRole2 || !tmpRole2.getRolePnm().equals(record.getRolePnm())) {
					isNew = true;
					record.setRoleId(null);
				} else {
					isNew = false;
					record.setRoleId(tmpRole2.getRoleId());
					record.setObjVers(tmpRole2.getObjVers());
				}
			} else {
				isNew = true;
				record.setRoleId(null);
			}
		}
			
		//Role ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if(isNew) {  // 신규...
			
			
			record.setRoleId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);
			record.setWritUserId(user.getUniqId());
			
			
		} else { // 변경...
		
			record.setRegTypCd("U");
			record.setWritUserId(user.getUniqId());
			if (UtilObject.isNull(record.getObjVers())) {
				record.setObjVers(1);
			}
			else { 
				record.setObjVers(record.getObjVers()+1);
			}
			waaDbRoleMapper.updateExpDtm(record); //기존 데이터를 삭제...
	   }
	

		result = waaDbRoleMapper.insertSelective(record);
		return result;
	}
	

	/** yeonho */
	@Override
	public int delDbRoleList(ArrayList<WaaDbRole> list) {
		int result = 0;
		for (WaaDbRole record : list) {
			String id = record.getRoleId();
			if (id != null && !"".equals(id)) {
//				record.setIbsStatus("D");

				result += waaDbRoleMapper.deleteDbRole(record);
			}
		}

		return result;
	}

}
