package kr.wise.meta.stnd.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.stnd.service.StndSymnTrmService;
import kr.wise.meta.stnd.service.WamSymnTrm;
import kr.wise.meta.stnd.service.WamSymnTrmMapper;

@Service("StndSymnTrmService")
public class StndSymnTrmServiceImpl implements StndSymnTrmService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	WamSymnTrmMapper mapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	@Override
	public WamSymnTrm selectSymnDetail(String symnId) {
		return mapper.selectByPrimaryKey(symnId);
	}

	/** 유사어 리스트 조회 */
	/** meta */
	@Override
	public List<WamSymnTrm> selectSymnList(WamSymnTrm search) {
		return mapper.selectSymnList(search);
	}

	/** 유사어 단건저장(엑셀업로드 시에도 이용) C,U 기능 */
	/** meta */
	@Override
	public int saveSymnRow(WamSymnTrm record) throws Exception {
		logger.debug("saveSymnRow Method");
		boolean isNew = true;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
		//엑셀업로드시 작성한 SymnId에 대한 검증 -- 없으면 신규처리, 있으면 변경처리
		if(record.getSymnId() != null && !record.getSymnId().equals("")) {
			WamSymnTrm tmpSymn = mapper.selectByPrimaryKey(record.getSymnId());
			
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
	/** meta 
	 * @throws Exception */
	@Override
	public int deleteSymnList(ArrayList<WamSymnTrm> list) throws Exception {
		int result = 0;
		for (WamSymnTrm WamSymnTrm : list) {
			String id = WamSymnTrm.getSymnId();
			if (id != null && !"".equals(id)) {
				WamSymnTrm.setIbsStatus("D");

				result += deleteSymn(WamSymnTrm);
			}
		}

		return result;
	}

	/** 유사어 단건 삭제 - D */
	/** meta */
	@Override
	public int deleteSymn(WamSymnTrm record) {
		int result = 0;
		record.setRegTypCd("D");
		mapper.updateWahSymn(record);  //이력관리용 저장.
		result = mapper.deleteByPrimaryKey(record.getSymnId()); 


		return result;
	}

	/** 엑셀 업로드 저장(다건) */	
	/** meta */
	@Override
	public int saveSymnList(ArrayList<WamSymnTrm> list) throws Exception {
		int result = 0;

		
		for (WamSymnTrm record : list) {
						
			//그리드 상태가 있을 경우만 DB에 처리한다...
			if(!UtilString.isBlank(record.getIbsStatus())) {
				result += saveSymnRow(record);
			}

		}
		//mapper.updateAllDeptNm();

		return result;
	}

	//동음이의어 관리-----------------------------
	
	@Override
	public WamSymnTrm selectHmnmDetail(String symnId) {
		return mapper.selectByPrimaryKey(symnId);
	}

	/** 동음이의어 리스트 조회 */
	/** meta */
	@Override
	public List<WamSymnTrm> selectHmnmList(WamSymnTrm search) {
		return mapper.selectHmnmList(search);
	}

	/** 유사어 단건저장(엑셀업로드 시에도 이용) C,U 기능 */
	/** meta */
	@Override
	public int saveHmnmRow(WamSymnTrm record) throws Exception {
		logger.debug("saveSymnRow Method");
		boolean isNew = true;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
		//엑셀업로드시 작성한 SymnId에 대한 검증 -- 없으면 신규처리, 있으면 변경처리
		if(record.getSymnId() != null && !record.getSymnId().equals("")) {
			WamSymnTrm tmpSymn = mapper.selectByPrimaryKey(record.getSymnId());
			
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
	/** meta 
	 * @throws Exception */
	@Override
	public int deleteHmnmList(ArrayList<WamSymnTrm> list) throws Exception {
		int result = 0;
		for (WamSymnTrm WamSymnTrm : list) {
			String id = WamSymnTrm.getSymnId();
			if (id != null && !"".equals(id)) {
				WamSymnTrm.setIbsStatus("D");

				result += deleteSymn(WamSymnTrm);
			}
		}

		return result;
	}

	/** 유사어 단건 삭제 - D */
	/** meta */
	@Override
	public int deleteHmnm(WamSymnTrm record) {
		int result = 0;
		record.setRegTypCd("D");
		mapper.updateWahSymn(record);  //이력관리용 저장.
		result = mapper.deleteByPrimaryKey(record.getSymnId()); 


		return result;
	}

	/** 엑셀 업로드 저장(다건) */	
	/** meta */
	@Override
	public int saveHmnmList(ArrayList<WamSymnTrm> list) throws Exception {
		int result = 0;

		
		for (WamSymnTrm record : list) {
						
			//그리드 상태가 있을 경우만 DB에 처리한다...
			if(!UtilString.isBlank(record.getIbsStatus())) {
				result += saveSymnRow(record);
			}

		}
		//mapper.updateAllDeptNm();

		return result;
	}

	
	
	
}
