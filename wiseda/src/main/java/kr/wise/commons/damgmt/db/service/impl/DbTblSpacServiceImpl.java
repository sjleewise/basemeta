/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbTblSpacServiceImpl.java
 * 2. Package : kr.wise.commons.damgmt.db.service.impl
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 8. 12. 오후 3:46:01
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 8. 12. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.db.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.db.service.DbTblSpacService;
import kr.wise.commons.damgmt.db.service.WaaTblSpac;
import kr.wise.commons.damgmt.db.service.WaaTblSpacMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbTblSpacServiceImpl.java
 * 3. Package  : kr.wise.commons.damgmt.db.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 8. 12. 오후 3:46:01
 * </PRE>
 */
@Service("DbTblSpacService")
public class DbTblSpacServiceImpl implements DbTblSpacService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WaaTblSpacMapper waaTblSpacMapper; 
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	/** yeonho */
	@Override
	public WaaTblSpac selectDbTblSpacDetail(String dbTblSpacId) {
		return waaTblSpacMapper.selectByPrimaryKey(dbTblSpacId);
	}

	/** 테이블스페이스 단건저장 C,U 기능 */
	/** yeonho */
	@Override
	public int regDbTblSpac(WaaTblSpac record) throws Exception {

		boolean isNew = true;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
		
		//엑셀업로드시 작성한 테이블스페이스 물리명에 대한 검증 -- 없으면 신규처리, 있으면 변경처리
		if(record.getDbTblSpacId() != null && !record.getDbTblSpacId().equals("")) {
			WaaTblSpac tmpSpac = waaTblSpacMapper.selectByPrimaryKey(record.getDbTblSpacId());
			
			if(null == tmpSpac || !tmpSpac.getDbTblSpacId().equals(record.getDbTblSpacId())) {
				isNew = true;
				record.setDbTblSpacId(null);
			} else {
				isNew = false;
				record.setDbTblSpacId(tmpSpac.getDbTblSpacId());
				record.setObjVers(tmpSpac.getObjVers());
			}
		} else {
			isNew = true;
			record.setDbTblSpacId(null);
		}
			
		//메뉴ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if(isNew) {  // 신규...
			
			
			record.setDbTblSpacId(objectIdGnrService.getNextStringId());
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
			
			
			waaTblSpacMapper.updateExpDtm(record); //기존 데이터를 삭제...
		} 

		result = waaTblSpacMapper.insertSelective(record);
		return result;
	}

	/** 테이블스페이스 조회 */
	/** yeonho */
	@Override
	public List<WaaTblSpac> getDbTblSpacList(WaaTblSpac search) {
		return waaTblSpacMapper.getDbTblSpacList(search);
	}

	
	/** 테이블스페이스 삭제 */
	/** yeonho */
	@Override
	public int delDbTblSpacList(ArrayList<WaaTblSpac> list) {
		int result = 0;
		for (WaaTblSpac record : list) {
			String id = record.getDbTblSpacId();
			if (id != null && !"".equals(id)) {
//				record.setIbsStatus("D");

				result += waaTblSpacMapper.deleteDbTblSpac(record);
			}
		}

		return result;
	}

	/** 테이블스페이스 리스트 저장 */
	/** yeonho 
	 * @throws Exception */
	@Override
	public int regDbTblSpacList(ArrayList<WaaTblSpac> list) throws Exception {
		int result = 0;
		
		for (WaaTblSpac record : list) {
						
			//그리드 상태가 있을 경우만 DB에 처리한다...
			if(!UtilString.isBlank(record.getIbsStatus())) {
				result += regDbTblSpacExcel(record);
			}

		}
		//mapper.updateAllDeptNm();

		return result;
	}

	/** 테이블스페이스 단건저장(엑셀업로드시) */
	/** @param record
	/** @return yeonho 
	 * @throws Exception */
	private int regDbTblSpacExcel(WaaTblSpac record) throws Exception {
		boolean isNew = true;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;

		//테이블스페이스 물리명 및 테이블스페이스 유형코드 검증
		WaaTblSpac tmpSpac = waaTblSpacMapper.selectByPnm(record.getDbTblSpacPnm(), record.getDbConnTrgId());
		
		//엑셀업로드시 작성한 테이블스페이스 물리명에 대한 검증 -- 없으면 신규처리, 있으면 변경처리
		if(record.getDbTblSpacPnm() != null && !record.getDbTblSpacPnm().equals("")) {
			
			if(null == tmpSpac || !tmpSpac.getDbTblSpacPnm().equals(record.getDbTblSpacPnm())) {
				isNew = true;
				record.setDbTblSpacId(null);
			} else {
				isNew = false;
				record.setDbTblSpacId(tmpSpac.getDbTblSpacId());
			}
		} else {
			isNew = true;
			record.setDbTblSpacId(null);
		}
			
		//테이블스페이스 ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if(isNew) {  // 신규...
			
			
			record.setDbTblSpacId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);
			record.setWritUserId(user.getUniqId());
			
			
		} else { // 변경...
			
			record.setRegTypCd("U");
			
			record.setWritUserId(user.getUniqId());
			if (UtilObject.isNull(tmpSpac.getObjVers())) {
				record.setObjVers(1);
			}
			else { 
				record.setObjVers(tmpSpac.getObjVers()+1);
			}
			
			waaTblSpacMapper.updateExpDtm(record); //기존 데이터를 삭제...
		} 

		// DDL표시여부 항목이 빈값일 경우 default "예" 셋팅
		if(record.getDdlDisplay() == null || record.getDdlDisplay().equals("")){
			record.setDdlDisplay("Y");
		}
		// 디폴트테이블스페이스여부 항목이 빈값일 경우 default "예" 셋팅
		if(record.getDefaultUsage() == null || record.getDefaultUsage().equals("")){
			record.setDefaultUsage("Y");
		}

		result = waaTblSpacMapper.insertSelective(record);
		return result;
	}

}
