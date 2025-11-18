/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : UsergGroupService.java
 * 2. Package : kr.wise.cmvw.user.service
 * 3. Comment :
 * 4. 작성자  : 유연철
 * 5. 작성일  : 2013. 4. 21.
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    유연철 	: 2013. 4. 21. 		: 신규 개발.
 */
package kr.wise.commons.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.user.WaaUserg;
import kr.wise.commons.user.service.UserGroupService;
import kr.wise.commons.user.service.WaaUsergMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : UserGroupService
 * 2. Package  : kr.wise.cmvw.user.service
 * 3. Comment  :
 * 4. 작성자   : 유연철
 * 5. 작성일   : 2013. 4. 21.
 * </PRE>
 */
@Service("userGroupService")
public class UserGroupServiceImpl implements UserGroupService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaaUsergMapper mapper;

	@Inject
	private EgovIdGnrService objectIdGnrService;

	@Override
	public List<WaaUserg> getUsergList(WaaUserg search) {
		List<WaaUserg> list = mapper.selectList(search);
		return list;
	}

	@Override
	public WaaUserg findUserg(WaaUserg search) {

		return mapper.selectByPrimaryKey(search.getUsergId());
	}

	@Override
	public int regUserg(WaaUserg record) throws Exception {
		String tmpStatus = record.getIbsStatus();
		int result = -1;

		if("I".equals(tmpStatus)) {
			record.setRegTypCd("C");
			//OBJ_ID 셋팅
			String objid = objectIdGnrService.getNextStringId();
			record.setUsergId(objid);

			result = mapper.insertSelective(record);
		} else if("U".equals(tmpStatus)) {
			record.setRegTypCd("U");
			result = mapper.updateByPrimaryKeySelective(record);
		} else if("D".equals(tmpStatus)) {
			record.setRegTypCd("D");
//			result = mapper.deleteByPrimaryKey(record.getSysAreaId());
			result = mapper.deleteRegTypCd(record);
		}
		return result;
	}

	@Override
	public int regUsergList(ArrayList<WaaUserg> list) throws Exception {

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		int result = 0;

		for (WaaUserg waaUserg : list) {
			waaUserg.setWritUserId(userid);
			result += regUserg(waaUserg);
		}

		return result;

	}


	@Override
	public int delUsergList(ArrayList<WaaUserg> list) throws Exception {

		int result = 0;

		for (WaaUserg WaaUserg : list) {
			String id = WaaUserg.getUsergId();
			if (id != null && !"".equals(id)) {
				WaaUserg.setIbsStatus("D");
				result += regUserg(WaaUserg);
			}
		}

		return result;

	}

	@Override
	public int getUsergCnt(WaaUserg waaUserg) {
		// TODO Auto-generated method stub
		return mapper.selectUsergCnt(waaUserg);
	}

}
