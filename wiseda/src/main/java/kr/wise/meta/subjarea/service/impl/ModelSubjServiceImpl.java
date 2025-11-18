/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : ModelSubjService.java
 * 2. Package : kr.wise.meta.model.service
 * 3. Comment : 
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 15. 오전 10:57:55
 * 6. 변경이력 : 
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 15. 		: 신규 개발.
 */
package kr.wise.meta.subjarea.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.sysarea.service.WaaSysArea;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.user.service.WaaUser;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.subjarea.service.ModelSubjService;
import kr.wise.meta.subjarea.service.WaaSubj;
import kr.wise.meta.subjarea.service.WaaSubjMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName : ModelSubjService
 * 2. Package  : kr.wise.meta.model.service
 * 3. Comment  : 
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 15.
 * </PRE>
 */
@Service("modelSubjService")

public class ModelSubjServiceImpl implements ModelSubjService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WaaSubjMapper mapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	@Inject 
	private BasicInfoLvlService basicInfoLvlService;
	
	public List<WaaSubj> getSubjList(WaaSubj search) {
		
		List<WaaSubj> list = mapper.selectListOrderSys(search);
		
		return list;
	}
	
	public WaaSubj findSubj(WaaSubj search) {
		WaaSubj vo = mapper.selectByPrimaryKey(search.getSubjId());
		return vo;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : regSubj
	 * 2. Comment    : 단건 등록 (ibsstatus 상태에 따라 쿼리 호출)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 15.
	 * </PRE>
	 *   @return int
	 *   @param record
	 *   @return
	 * @throws Exception 
	 */
	public int regSubj(WaaSubj record) throws Exception {
		String tmpStatus = record.getIbsStatus();
		int result = 0;
		boolean isNew = true;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		
		//상위 주제영역 확인하여 잘못되었을 경우 공백처리.
		if(StringUtils.hasText(record.getUppSubjId())) {
			WaaSubj tmpVO = mapper.selectByPrimaryKey(record.getUppSubjId());
			if(tmpVO == null || !tmpVO.getSubjId().equals(record.getUppSubjId())) {
				record.setUppSubjId(null);
			}
		}
		//주제영역의 기본정보레벨 값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("SUBJ");
		logger.debug("기본정보레벨 조회 : {}", data);
		logger.debug("{}", record);
		if (data != null && data.getBscLvl() < record.getSubjLvl()) { //이하까지 허용
			return -5;
		}
		
		//엑셀업로드시 작성한 SubjId에 대한 검증 -- 없으면 신규처리, 있으면 변경처리
		if(StringUtils.hasText(record.getSubjId())) {
			WaaSubj tmpSubj = mapper.selectByPrimaryKey(record.getSubjId());
			
			if(null == tmpSubj || !tmpSubj.getSubjId().equals(record.getSubjId())) {
				isNew = true;
				record.setSubjId(null);
			} else {
				isNew = false;

			}
		} else {
			isNew = true;
			record.setSubjId(null);
		}
		
		
		logger.debug("isNew : {}", isNew);
		if(isNew) { // 신규...
			
			record.setRegTypCd("C");
			
			record.setObjVers(1);
			
			record.setSubjId(objectIdGnrService.getNextStringId());
			
			
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
		
		record.setWritUserId(user.getUniqId());
		result = mapper.insertSelective(record);
		return result;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : regSubjList
	 * 2. Comment    : IBS 리스트 등록 (ibsStatus)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 15.
	 * </PRE>
	 *   @return void
	 *   @param list
	 * @throws Exception 
	 */
	public int regSubjList(List<WaaSubj> list) throws Exception {
		
		String tmpid = null;
		
		int result = 0;
		for (WaaSubj record : list) {
			
			//레벨이 1이상이며, 상위주제영역이 없을경우, 이전 레코드의 주제영역 ID를 셋팅한 0레벨 -> 1레벨로 변경
			if( UtilString.isBlank(record.getUppSubjId()) && record.getSubjLvl() > 1) {
				//TODO : 상위 주제영역을 찾는다. 중복의 경우 오류가 발생한다. 이경우는 나중에 처리?
				WaaSubj uppSubj = mapper.selectUppSubjBySubjNm(record);
				if(StringUtils.hasText(uppSubj.getSubjId())) {
					record.setUppSubjId(uppSubj.getSubjId());
				} 
			}
			
			//그리드 상태가 있을 경우만 DB에 처리한다...
			if(!UtilString.isBlank(record.getIbsStatus())) {
				
				result += regSubj(record);
			}
			
			//해당 레코드의 주제영역ID를 임시로 저장한다...
			tmpid = record.getSubjId();
			
//			mapper.updateAllSubjNm(record.getSubjId()); //전체경로 업데이트...
			if(result < 0) return result;
		}
		mapper.updateAllSubjNm(null); //전체경로 업데이트...
		
		return result;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : delSubjList
	 * 2. Comment    : 체크상태인 리스트를 받아 삭제 처리한다.(id가 있는 경우만 처리)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 15.
	 * </PRE>
	 *   @return void
	 *   @param list
	 * @throws Exception 
	 */
	public int delSubjList(List<WaaSubj> list) throws Exception {
		
		int result = 0;
		for (WaaSubj record : list) {
			String tmpid = record.getSubjId();
			
			if(tmpid != null && !"".equals(tmpid)) {
				record.setIbsStatus("D");
				result += mapper.updateExpDtm(record);
			}
		}
		
		return result;
	}

	@Override
	public List<WaaSubj> getMetaSubjList(WaaSubj search) {
		return mapper.selectMetaList(search);
	}
	
	

	
	//주제영역권한신청 190425
	

	@Override
	public List<WaaSubj> getsubjOnwerlist(WaaSubj search) {
		List<WaaSubj> list = mapper.selectSubjOnwerlist(search);
		return list;
	}

	@Override
	public List<WaaSubj> getsubjOnwerDetList(WaaSubj search) {

		return mapper.selectSubjOnwerDetList(search);
	}
	
	
	@Override
	public int regSubjOwnerList(List<WaaSubj> list) throws Exception {
		int result = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();

		for (WaaSubj record : list) {
			record.setWritUserId(user.getUniqId());
			WaaSubj searchVO = mapper.selectSubjOnwer(record);
			
			if(!UtilObject.isNull(searchVO)){
				//WAM 삭제
				mapper.deleteWAMbyADMIN(searchVO);
				//WAH 만료
				mapper.updateWAHbyADMIN(searchVO);
				
				record.setRegTypCd("U");
				record.setSubjOwnerId(searchVO.getSubjOwnerId());
				record.setObjVers(searchVO.getObjVers()+1);
				record.setFrsRqstDtm(searchVO.getFrsRqstDtm());
				record.setFrsRqstUserId(searchVO.getFrsRqstUserId());
			}else{
				record.setRegTypCd("C");
				record.setObjVers(1);
				record.setSubjOwnerId(objectIdGnrService.getNextStringId());
			}
			//WAH insert
			result += mapper.insertWAMbyADMIN(record);
			//WAM insert
			result += mapper.insertWAHbyADMIN(record);
		}
		
		return result;
	}
	
	@Override
	public int delSubjOwnerList(List<WaaSubj> list) throws Exception {
		int result = 0;
		for (WaaSubj record : list) {
			//WAM 삭제
			result += mapper.deleteWAMbyADMIN(record);
			//WAH 만료
			result += mapper.updateWAHbyADMIN(record);
		}
		return result;
	}
	
	@Override
	public List<WaaSubj> getsubjFavoritelist(WaaSubj search) {
		List<WaaSubj> list = mapper.selectSubjFavoritelist(search);
		return list;
	}	
	

	@Override
	public int registerSubjFavorite(WaqMstr mstVo, List<?> reglist) throws Exception {
		// TODO Auto-generated method stub
		//logger.debug("mstVo:{}\nbizInfo:{}", mstVo, mstVo.getBizInfo());

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		int result = 0;

		if(reglist != null) {
			for (WaaSubj saveVo : (ArrayList<WaaSubj>)reglist) {
				saveVo.setUserId(userid);
				
				String tmpstatus = saveVo.getIbsStatus();
				if ("I".equals(tmpstatus)) {
					saveVo.setSubjOwnerId(objectIdGnrService.getNextStringId()); // 추가
				}
				
				//단건 저장...
				result += saveSubjFavoriteRqst(saveVo);
			}
		}

		return result;
	}	
	
	@Override
	public int delSubjFavorite(WaqMstr reqmst, ArrayList<WaaSubj> list) throws Exception {
		int result = 0;
		for (WaaSubj savevo : list) {
			savevo.setIbsStatus("D");
		}
		result = registerSubjFavorite(reqmst, list);
		return result;
	}	
	
	
	/** 단건 저장... @return */
	public int saveSubjFavoriteRqst(WaaSubj saveVo) {
		int result  = 0;
		
		String tmpstatus = saveVo.getIbsStatus();

		if ("I".equals(tmpstatus)) {
			result = mapper.insertWAMSF(saveVo);
//		} else if ("U".equals(tmpstatus)){
//			//업데이트
//			result = mapper.updateByPrimaryKeySubjOwner(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = mapper.deleteWAMSF(saveVo);
		}

		return result;
	}
	
	@Override
	public int subjFavoriteCntBySubjId(WaaSubj search) throws Exception {
		return mapper.selectFavoriteCntBySubjId(search);
		// return result;
	}	

	
	public String getDbmsTypCd(String subjLnm) {
		return mapper.selectDbmsTypCd(subjLnm);
	}
}
