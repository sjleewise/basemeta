package kr.wise.meta.stnd.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.stnd.service.StndSymnService;
import kr.wise.meta.stnd.service.WamStwd;
import kr.wise.meta.stnd.service.WamStwdMapper;
import kr.wise.meta.stnd.service.WamSymn;
import kr.wise.meta.stnd.service.WamSymnMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : StndSymnServiceImpl.java
 * 3. Package  : kr.wise.meta.stnd.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 9. 오후 1:54:05
 * </PRE>
 */ 
@Service("stndSymnService")
public class StndSymnServiceImpl implements StndSymnService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	@Inject
	WamSymnMapper mapper;
	
	@Inject
	WamStwdMapper stwdMapper;
	
	/** yeonho */
	@Override
	public WamSymn selectSymnDetail(String symnId) {
		return mapper.selectByPrimaryKey(symnId);
	}

	/** 유사어 리스트 조회 */
	/** yeonho */
	@Override
	public List<WamSymn> selectSymnList(WamSymn search) {
		return mapper.selectSymnList(search);
	}

	/** 유사어 단건저장(엑셀업로드 시에도 이용) C,U 기능 */
	/** yeonho */
	@Override
	public int saveSymnRow(WamSymn record) throws Exception {
		logger.debug("saveSymnRow Method");
		boolean isNew = true;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
		//엑셀업로드시 작성한 SymnId에 대한 검증 -- 없으면 신규처리, 있으면 변경처리
		if(record.getSymnId() != null && !record.getSymnId().equals("")) {
			WamSymn tmpSymn = mapper.selectByPrimaryKey(record.getSymnId());
			
			if(null == tmpSymn || !tmpSymn.getSymnId().equals(record.getSymnId())) {
				isNew = true;
				record.setSymnId(null);
			} else {
				isNew = false;
				record.setFrsRqstDtm(tmpSymn.getFrsRqstDtm());
				record.setFrsRqstUserId(tmpSymn.getFrsRqstUserId());
			}
		} else {
			isNew = true;
			record.setSymnId(null);
		}
		
		//엑셀업로드시 작성한 대체어에 대한 검증 -- 일치하지 않으면 공백 처리 
//		if((record.getSbswdLnm() != null && !record.getSbswdLnm().equals("")) || (record.getSbswdPnm() != null && !record.getSbswdPnm().equals(""))) {
//			List<WamStwd> tmpSbswd = stwdMapper.selectByLnmPnm(record.getSbswdLnm(), record.getSbswdPnm());
//			if(tmpSbswd == null || tmpSbswd.size() == 0) {
//				record.setSbswdLnm(null);
//				record.setSbswdPnm(null);
//			}
//		}
		//논리명, 물리명 중복여부 체크하여, 중복일경우 리턴...
//		int tmpCnt = 0;
//		List<WamSymn> tmpSymn = mapper.selectByLnmPnm(record.getSymnLnm(), record.getSymnPnm());
//		if(tmpSymn != null && tmpSymn.size() > 0 && isNew) { // 신규일시...
//			logger.debug("유사어 논리명/물리명 중복발생");
//			return 0;
//		} else if(tmpSymn != null && tmpSymn.size() > 0 && !isNew) { // 변경일시...
//			for (int i=0; i<tmpSymn.size(); i++) {
//				if(tmpSymn.get(i).getSymnLnm().equals(record.getSymnLnm()) && tmpSymn.get(i).getSymnPnm().equals(record.getSymnPnm())) {
//					tmpCnt += 1;
//				}
//			}
//			
//			if(tmpCnt != 1) {
//				logger.debug("유사어 논리명/물리명 중복발생(수정)");
//				return 0;
//			}
//		}
		
		
		
		//메뉴ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if(isNew) {  // 신규...
			
			
			record.setSymnId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);
			record.setFrsRqstUserId(user.getUniqId());
			
			
		} else { // 변경...
			
			record.setRegTypCd("U");
			mapper.updateWahSymn(record); // 이력관리를 위해 기존데이터를 WahSymn테이블에 저장...
			
			record.setRqstUserId(user.getUniqId());
			if (UtilObject.isNull(record.getObjVers())) {
				record.setObjVers(1);
			}
			else { 
				record.setObjVers(record.getObjVers()+1);
			}
			
			
			mapper.deleteByPrimaryKey(record.getSymnId()); //기존 데이터를 삭제...
		} 

		result = mapper.insertSelective(record);
		return result;
	}

	/** 유사어 리스트 삭제 */
	/** yeonho 
	 * @throws Exception */
	@Override
	public int deleteSymnList(ArrayList<WamSymn> list) throws Exception {
		int result = 0;
		for (WamSymn WamSymn : list) {
			String id = WamSymn.getSymnId();
			if (id != null && !"".equals(id)) {
				WamSymn.setIbsStatus("D");

				result += deleteSymn(WamSymn);
			}
		}

		return result;
	}

	/** 유사어 단건 삭제 - D */
	/** yeonho */
	@Override
	public int deleteSymn(WamSymn record) {
		int result = 0;
		record.setRegTypCd("D");
		mapper.updateWahSymn(record);  //이력관리용 저장.
		result = mapper.deleteByPrimaryKey(record.getSymnId()); 


		return result;
	}

	/** 엑셀 업로드 저장(다건) */	
	/** yeonho */
	@Override
	public int saveSymnList(ArrayList<WamSymn> list) throws Exception {
		int result = 0;

		
		for (WamSymn record : list) {
						
			//그리드 상태가 있을 경우만 DB에 처리한다...
			if(!UtilString.isBlank(record.getIbsStatus())) {
				result += saveSymnRow(record);
			}

		}
		//mapper.updateAllDeptNm();

		return result;
	}

	@Override
	public List<WamSymn> selectSymnChangeList(String symnId) {
		// TODO Auto-generated method stub
		return mapper.selectSymnChangeList(symnId);
	}

	@Override
	public int getSymnCnt(WamSymn saveVO) {
		// TODO Auto-generated method stub
		return mapper.selectSymnCnt(saveVO);
	}

	
	

}
