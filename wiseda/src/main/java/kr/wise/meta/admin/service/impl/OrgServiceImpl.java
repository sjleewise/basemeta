/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : OrgService.java
 * 2. Package : kr.wise.meta.model.service
 * 3. Comment : 
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 15. 오전 10:57:55
 * 6. 변경이력 : 
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 15. 		: 신규 개발.
 */
package kr.wise.meta.admin.service.impl;

import java.util.List;
import javax.inject.Inject;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.admin.service.OrgService;
import kr.wise.meta.admin.service.WaaOrg;
import kr.wise.meta.admin.service.WaaOrgMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName : OrgService
 * 2. Package  : kr.wise.meta.model.service
 * 3. Comment  : 
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 15.
 * </PRE>
 */
@Service("orgService")

public class OrgServiceImpl implements OrgService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WaaOrgMapper mapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	public List<WaaOrg> getOrgList(WaaOrg search) {
		
		List<WaaOrg> list = mapper.selectListOrderSys(search);
		
		return list;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : regOrg
	 * 2. Comment    : 단건 등록 (ibsstatus 상태에 따라 쿼리 호출)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 15.
	 * </PRE>
	 *   @return int
	 *   @param record
	 *   @return
	 * @throws Exception 
	 */
	public int regOrg(WaaOrg record) throws Exception {
		String tmpStatus = record.getIbsStatus();
		int result = 0;
		boolean isNew = true;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		//상위기관코드가 없을경우 최상위 메뉴레벨로 설정(0레벨)
		if(!StringUtils.hasText(record.getUppOrgCd())) {
			record.setOrgLvl((short)1);
		} else {
		//상위기관코드명을 적었을경우, 상위기관코드의 레벨+1 처리
			WaaOrg tmpVO = mapper.selectByPrimaryKey(record.getUppOrgCd());
			if(tmpVO != null && tmpVO.getOrgCd().equals(record.getUppOrgCd())) {
				record.setOrgLvl((short) (tmpVO.getOrgLvl() + 1));
			} else {
			//기관명이 서로 불일치할 경우 최상위기관레벨 설정
				record.setUppOrgCd(null);
				record.setOrgLvl((short)0);
			}
		}
		
		
		//엑셀업로드시 작성한 OrgCd에 대한 검증 -- 없으면 신규처리, 있으면 변경처리
		if(StringUtils.hasText(record.getOrgCd())) {
			WaaOrg tmpOrg = mapper.selectByPrimaryKey(record.getOrgCd());
			
			if(tmpOrg == null) { //신규
				isNew = true;

				record.setRegTypCd("C");
				record.setObjVers(1);
				
			} else {//변경
				if(!record.getOrgNm().equals(tmpOrg.getOrgNm())){
					logger.debug("\n저장을 실패했습니다.");
					logger.debug("\n오류: 이미 존재하는 기관코드입니다.");
					return -5;
					
				}else{
					isNew = false;

					//기존 레코드 만료처리...	
					mapper.updateExpDtm(tmpOrg);
					
					if (UtilObject.isNull(record.getObjVers()))
						record.setObjVers(1);
					else 
						record.setObjVers(record.getObjVers()+1);
					
					record.setRegTypCd("U");	
				}
				
			}
		}

		logger.debug("isNew : {}", isNew);
		
		
		record.setWritUserId(user.getUniqId());
		result = mapper.insertSelective(record);
		return result;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : regOrgList
	 * 2. Comment    : IBS 리스트 등록 (ibsStatus)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 15.
	 * </PRE>
	 *   @return void
	 *   @param list
	 * @throws Exception 
	 */
	public int regOrgList(List<WaaOrg> list) throws Exception {
		
//		String tmpid = null;
		
		int result = 0;
		for (WaaOrg record : list) {
			
			// 엑셀업로드시 상위기관 null이면 기관레벨을 1로 세팅해주기 
			if(UtilString.isBlank(record.getUppOrgCd()) && record.getOrgLvl() == null){
				record.setOrgLvl((short)1);
			}
			
			//레벨이 1이상이며, 상위주제영역이 없을경우, 이전 레코드의 주제영역 ID를 셋팅한 0레벨 -> 1레벨로 변경
			if( UtilString.isBlank(record.getUppOrgCd()) && record.getOrgLvl() > 1) {
				//TODO : 상위 주제영역을 찾는다. 중복의 경우 오류가 발생한다. 이경우는 나중에 처리?
				WaaOrg uppOrg = mapper.selectUppOrgByOrgNm(record);
				if(StringUtils.hasText(uppOrg.getOrgCd())) {
					record.setUppOrgCd(uppOrg.getOrgCd());
				} 
			}
			
			//그리드 상태가 있을 경우만 DB에 처리한다...
			if(!UtilString.isBlank(record.getIbsStatus())) {
				
				result += regOrg(record);
			}
			
			//해당 레코드의 주제영역ID를 임시로 저장한다...
//			tmpid = record.getOrgCd();
			
//			mapper.updateAllOrgNm(record.getOrgCd()); //전체경로 업데이트...
			if(result < 0) return result;
		}
		mapper.updateAllOrgNm(null); //전체경로 업데이트...
		
		return result;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : delOrgList
	 * 2. Comment    : 체크상태인 리스트를 받아 삭제 처리한다.(id가 있는 경우만 처리)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 15.
	 * </PRE>
	 *   @return void
	 *   @param list
	 * @throws Exception 
	 */
	public int delOrgList(List<WaaOrg> list) throws Exception {
		
		int result = 0;
		for (WaaOrg record : list) {
			String tmpid = record.getOrgCd();
			if(tmpid != null && !"".equals(tmpid)) {
				record.setIbsStatus("D");
				result += mapper.updateExpDtm(record);
			}
		}
		
		return result;
	}

}
