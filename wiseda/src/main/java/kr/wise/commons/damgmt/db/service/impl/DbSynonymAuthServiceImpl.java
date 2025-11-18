/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbRoleAuthServiceImpl.java
 * 2. Package : kr.wise.commons.damgmt.db.service.impl
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 8. 13. 오후 1:07:10
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
import kr.wise.commons.damgmt.db.service.DbSynonymAuthService;
import kr.wise.commons.damgmt.db.service.WaaDbSynonym;
import kr.wise.commons.damgmt.db.service.WaaDbSynonymMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;


import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbRoleAuthServiceImpl.java
 * 3. Package  : kr.wise.commons.damgmt.db.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 8. 13. 오후 1:07:10
 * </PRE>
 */
@Service("DbSynonymAuthService")
public class DbSynonymAuthServiceImpl implements DbSynonymAuthService {

	@Inject
	private WaaDbSynonymMapper waaDbSynonymMapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	/** yeonho */
	@Override
	public List<WaaDbSynonym> getDbSynonymAuthList(WaaDbSynonym search) {
		return waaDbSynonymMapper.getDbSynonymList(search);
	}

	/** yeonho 
	 * @throws Exception */
	@Override
	public int regDbSynonymAuthList(ArrayList<WaaDbSynonym> list) throws Exception {
		int result = 0;
		
		for (WaaDbSynonym record : list) {
						
			//그리드 상태가 있을 경우만 DB에 처리한다...
			if(!UtilString.isBlank(record.getIbsStatus())) {
				result += regDbSynonymAuth(record);
			}

		}
		//mapper.updateAllDeptNm();

		return result;
	}

	/** @param record
	/** @return yeonho 
	 * @throws Exception */
	private int regDbSynonymAuth(WaaDbSynonym record) throws Exception {
		
		boolean isNew = true;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;

		//엑셀업로드시 작성한 권한ID에 대한 검증 -- 없으면 신규대상, 있으면 변경처리
		if(record.getSynonymId() != null && !record.getSynonymId().equals("")) {
			WaaDbSynonym tmpPri = waaDbSynonymMapper.selectByPrimaryKey(record.getSynonymId());
			
			if(null == tmpPri || !tmpPri.getSynonymId().equals(record.getSynonymId())) {
				isNew = true;
				record.setSynonymId(null);
			} else {
				isNew = false;
				record.setSynonymId(tmpPri.getSynonymId());
				record.setObjVers(tmpPri.getObjVers());
			}
		} else {
			isNew = true;
			record.setSynonymId(null);
		}
		
		
			
		//권한ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if(isNew) {  // 신규...
			
			
			record.setSynonymId(objectIdGnrService.getNextStringId());
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
			
			
			waaDbSynonymMapper.updateExpDtm(record); //기존 데이터를 삭제...
		} 

		result = waaDbSynonymMapper.insertSelective(record);
		return result;
	}

	/** yeonho */
	@Override
	public int delDbSynonymAuthList(ArrayList<WaaDbSynonym> list) {
		int result = 0;
		for (WaaDbSynonym record : list) {
			String id = record.getSynonymId();
			if (id != null && !"".equals(id)) {
//				record.setIbsStatus("D");

				result += waaDbSynonymMapper.deleteDbSynonymAuth(record);
			}
		}

		return result;
	}

}
