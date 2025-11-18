/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : AdminInfoSysServiceImpl.java
 * 2. Package : kr.wise.meta.admin.service.impl
 * 3. Comment : 기관메타 > 관리자 - 정보시스템관리
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.12.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.12. :            : 신규 개발.
 */
package kr.wise.meta.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;


import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.net.CommNetApiUtil;
import kr.wise.commons.user.service.UserService;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.user.service.WaaUserMapper;
import kr.wise.commons.user.service.WaaUserSys;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.admin.service.AdminInfoSysService;
import kr.wise.meta.admin.service.WaaInfoSys;
import kr.wise.meta.admin.service.WaaInfoSysMapper;

@Service("adminInfoSysService")
public class AdminInfoSysServiceImpl implements AdminInfoSysService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WaaInfoSysMapper mapper;
	
	@Inject
	private WaaUserMapper usermapper;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private UserService userService;
	
	@Override
	public List<WaaInfoSys> getInfoSysList(WaaInfoSys search) {
		return mapper.selectList(search);
	}

	@Override
	public int regInfoSysList(ArrayList<WaaInfoSys> list) throws Exception {
		
		int result = 0;
		int checkResultCnt = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		for(WaaInfoSys record : list) {
			
			//2019.03.19 수정 : 정보시스템명 중복여부 확인 후 처리 중지 result += regInfoSys(record);
			checkResultCnt = regInfoSys(record);
			
			if(checkResultCnt < 0) {
				return checkResultCnt;
			}else {
				result += checkResultCnt;
			}
			//2019.03.19 수정 끝
			
			//20181221 정보시스템 신규 등록 시 중앙메타 전송전에 등록자를 담당자로 설정해준다.
			if(record.getIbsStatus().equals("I")){
			   //record.setOperDeptNm(user.getDeptNm());
			   //record.setCrgTelNo(user.getUserHtelno());
			   record.setCrgEmailAddr(user.getEmail());
			   record.setCrgUserNm(user.getName());
			}
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("data", list);
		
		/*//중앙메타 url
		String ctrMetaUrl = message.getMessage("ctrMetaUrl", null, null);
		//중앙메타 등록
		IBSResultVO<Object> reqResult = CommNetApiUtil.callJsonStringApiListRequestIbsResult(ctrMetaUrl+"/response/regInfoSysList.do", HttpMethod.POST, paramMap);
		
		if(reqResult.RESULT.CODE != 0){
			logger.info("중앙메타 연계 실패///" +reqResult.RESULT.CODE);
			//result = 555;
//			throw new WiseLinkException(Integer.toString(reqResult.RESULT.CODE) , reqResult.RESULT.MESSAGE); //트랜잭션 롤백
			throw new WiseRunTimeException(Integer.toString(reqResult.RESULT.CODE) , "네트워크 에러입니다."); //트랜잭션 롤백
		}
//		else{
//			   ArrayList<WaaUserSys> list2 = new ArrayList<>();
//			   result+=  userService.updateInfoSysCrgUserApi(list2);
//	    }
*/		
		return result ;
	}
	

	@Override
	public int regInfoSys(WaaInfoSys record) {
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		String tmpStatus = "";
		int result = 0;
		
		record.setWritUserId(user.getUniqId());
		tmpStatus = record.getIbsStatus();
		
		
		if("I".equals(tmpStatus)) {
			
			WaaInfoSys checkResult = mapper.selectInfoSysDupCheck(record);
			
			if(checkResult != null) {
				
				if(checkResult.getInfoSysCnt() > 0) {

					if(checkResult.getUserInfoSysCnt() == 0) {
						//해당 유저의 정보시스템이 아님
						return -998;
					}
					//정보시스템명 중복
					return -999;
				}
			}
			
			
			result = mapper.insertSelective(record);
			
			/*
			//181220 정보시스템유저 매핑정보 추가(등록자 자신)
			WaaUserSys userSysVo = new WaaUserSys();
			userSysVo.setUserId(user.getUniqId());
			userSysVo.setOrgCd(user.getOrgCd());
			userSysVo.setInfoSysCd(record.getInfoSysCd());
			userSysVo.setRegTypCd("C");
			userSysVo.setWritUserId(user.getUniqId());
			result = usermapper.insertUserSys(userSysVo);
			
			//입력자를 제외한 관리자 존재 유무 확인
			int chkCnt = usermapper.selectAdminUserCnt(userSysVo);
			
			if(chkCnt > 0){
				//해당기관메타 관리자에게도 권한을 넣어줌
				HashMap<String, Object> recordMap = new HashMap<>();
				userSysVo.setOrgCd(record.getOrgCd());		//입력받은 기관
				userSysVo.setTopOrgCd(user.getTopOrgCd());	//상위기관
				recordMap.put("userSys", userSysVo);
				result += usermapper.insertAdminUserSys(recordMap);
			}
			*/ 
					
		}else if("U".equals(tmpStatus)) {
			
			if (UtilObject.isNull(record.getObjVers())) {
				record.setObjVers(1);
			} else { 
				record.setObjVers(record.getObjVers()+1);
			}
			
			result = mapper.updateWaaInfoSys(record);
		}
		
		return result;
	}

	
	@Override
	public int delInfoSysList(ArrayList<WaaInfoSys> list) throws Exception {
		
		int result = 0;
		
		for(WaaInfoSys record : list) {
			
			if("U".equals(record.getIbsStatus())) {
				
				result += mapper.updateExpDtm(record);
				
				//181220 정보시스템 매핑 삭제플래그 업데이트
				LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
				WaaUserSys userSysVo = new WaaUserSys();
				userSysVo.setWritUserId(user.getUniqId());
				userSysVo.setInfoSysCd(record.getInfoSysCd());
				
				//result += usermapper.deleteUserSys(userSysVo);
				
			}
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("data", list);
		
		/*//중앙메타 url
		String ctrMetaUrl = message.getMessage("ctrMetaUrl", null, null);
		//중앙메타 삭제
		IBSResultVO<Object> reqResult = CommNetApiUtil.callJsonStringApiListRequestIbsResult(ctrMetaUrl+"/response/delInfoSysList.do", HttpMethod.POST, paramMap);
		
		if(reqResult.RESULT.CODE != 0){
			logger.info("중앙메타 연계 실패###" +reqResult.RESULT.CODE);
			//result = -999;
			throw new RuntimeException(); //트랜잭션 롤백
		}*/
		
		
		return result;
	}

	@Override
	public List<WaaInfoSys> getInfoSysListApi(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectListApi(map);
	}

	@Override
	public List<WaaInfoSys> getDicInfoSysList(WaaInfoSys record) {
		return mapper.getDicInfoSysList(record);
	}
	
	/** 정보시스템 상세 조회 */
	@Override
	public WaaInfoSys getInfoSysDetail(String orgCd, String infoSysCd) {
		
		logger.debug("infoSysCd:{}", infoSysCd);
		
		return mapper.selectInfoSysInfoDetail(orgCd, infoSysCd);
	}
}
