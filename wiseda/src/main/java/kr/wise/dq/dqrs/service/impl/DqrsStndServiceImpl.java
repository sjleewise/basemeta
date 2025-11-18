package kr.wise.dq.dqrs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.dq.dqrs.service.DqrsDmn;
import kr.wise.dq.dqrs.service.DqrsResult;
import kr.wise.dq.dqrs.service.DqrsSditm;
import kr.wise.dq.dqrs.service.DqrsStndMapper;
import kr.wise.dq.dqrs.service.DqrsStndService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("dqrsStndService")
public class DqrsStndServiceImpl implements DqrsStndService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private DqrsStndMapper dqrsStndMapper;
	
    @Inject
    private EgovIdGnrService objectIdGnrService;
	
    
    ////////////////////용어
    @Override
	public List<DqrsSditm> getDqrsSditmLst(DqrsSditm data){
		return dqrsStndMapper.selectDqrsSditmLst(data);
	}
    
    @Override
	public List<DqrsSditm> getDqrsPubSditmLst(DqrsSditm data){
		return dqrsStndMapper.selectDqrsPubSditmLst(data);
	}

	/** 표준항목 요청서 리스트 저장 insomnia 
	 * @throws Exception 
	 */
	public int regDqrsSditm(List<DqrsSditm> reglist) throws Exception {
		
		logger.debug("reglist:{}", reglist.toString());

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		int result = 0;

		if(reglist != null) {
			for (DqrsSditm saveVo : (ArrayList<DqrsSditm>)reglist) {
				
				saveVo.setRegUserId(userid);
				saveVo.setRqstUserId(userid);
				//단건 저장...
				result += saveDqrsSditm(saveVo);
			}

		}

		return result;

	}

	/** @return insomnia 
	 * @throws Exception */
	public int saveDqrsSditm(DqrsSditm saveVo) throws Exception {
		int result = 0;

		String tmpstatus = saveVo.getIbsStatus();

		if("I".equals(tmpstatus)) {
			
			String id =  objectIdGnrService.getNextStringId();
			saveVo.setSditmId(id);
			saveVo.setRegTypCd("C");
			result = dqrsStndMapper.insertDqrsSditm(saveVo);

		} else if ("U".equals(tmpstatus)) {
			
			result = dqrsStndMapper.deleteDqrsSditm(saveVo);
			saveVo.setRegTypCd("U");
			result = dqrsStndMapper.insertDqrsSditm(saveVo);

		} else if ("D".equals(tmpstatus)) {
			saveVo.setRegTypCd("D");
			result = dqrsStndMapper.deleteDqrsSditm(saveVo);

		}
		//대용량 업데이트를 위한 update 구문 정리 (select에서 검증해주고있음)
		if(!"D".equals(tmpstatus)) {
			checkDqrsSditm(saveVo);
		}

//		//단어구성정보 셋팅(삭제 후 추가)
//		setStwdCnfg(saveVo);

		return result;
	}
	
	@Override
	public int saveDqrsSditmLst(List<DqrsSditm> reglist) throws Exception {
		int result = 0;
		
		result = regDqrsSditm(reglist);
		
		return result;
	}
	
	@Override
	public int delDqrsSditmLst(ArrayList<DqrsSditm> list) throws Exception {
		
		int result = 0;
		
		for(DqrsSditm saveVo : list) {
			saveVo.setIbsStatus("D");
		}
		
		result = regDqrsSditm(list);
		
		return result;
	}
	
//	@Override
//	public List<HashMap> getInfoSys() {   
//
//		return dqrsStndMapper.selectInfoSys();
//	}
	
	public int checkDqrsSditm(DqrsSditm saveVo) {
		
		int result = 0;
		
		// Check
		result =+ dqrsStndMapper.checkSditmVrfRmk(saveVo);
//		result =+ dqrsStndMapper.checkDbms(saveVo);
		result =+ dqrsStndMapper.checkDupDqrsSditm(saveVo);
		result =+ dqrsStndMapper.checkDqrsDmn(saveVo);
		result =+ dqrsStndMapper.updateNotDmn(saveVo);
		result =+ dqrsStndMapper.updateSditmVrfRmk(saveVo);

		return result;
	}
	
	//////////////도메인
	@Override
	public List<DqrsDmn> getDqrsDmnLst(DqrsDmn data) {
		// TODO Auto-generated method stub
		return dqrsStndMapper.selectDqrsDmnLst(data);
	}
	
	@Override
	public List<DqrsDmn> getDqrsPubDmnLst(DqrsDmn data) {
		// TODO Auto-generated method stub
		return dqrsStndMapper.selectDqrsPubDmnLst(data);
	}

	public int regDqrsDmnLst(ArrayList<DqrsDmn> list) throws Exception {
		int result=0;
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String regUserId = user.getUniqId();
		
		if(list != null){
			for(DqrsDmn vo : list){
				vo.setRegUserId(regUserId);
				result += saveDqrsDmn(vo);
			}
		}		
		
		return result;
	}
	
	private int saveDqrsDmn(DqrsDmn saveVo) throws Exception{
		int result =0;
		String tmpstatus = saveVo.getIbsStatus();
		
		if("I".equals(tmpstatus)) {
			String id =  objectIdGnrService.getNextStringId();
			saveVo.setDmnId(id);
			saveVo.setRegTypCd("C");
			result = dqrsStndMapper.insertDqrsDmn(saveVo);

		} else if ("U".equals(tmpstatus)) {
			result += dqrsStndMapper.updateExpDqrsDmn(saveVo);
			saveVo.setRegTypCd("U");
			result += dqrsStndMapper.insertDqrsDmn(saveVo);
		} else if ("D".equals(tmpstatus)) {
			saveVo.setRegTypCd("D");
			result = dqrsStndMapper.updateExpDqrsDmn(saveVo);
		}
		
		if(!"D".equals(tmpstatus)) {
			checkDqrsDmn(saveVo);
		}
		
		return result;
	}
	
	public int checkDqrsDmn(DqrsDmn saveVo){
		int result =0;
		
			result += dqrsStndMapper.checkDmnVrfRmk(saveVo);
			result += dqrsStndMapper.updateDupDqrsDmn(saveVo);
		
		return result;
	}
	
	@Override
	public int saveDqrsDmnLst(ArrayList<DqrsDmn> list) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		
		result = regDqrsDmnLst(list);
		
		return result;
	}

	@Override
	public int delDqrsDmnLst(ArrayList<DqrsDmn> list) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		
		for(DqrsDmn saveVo : list) {
			saveVo.setIbsStatus("D");
		}
		
		result = regDqrsDmnLst(list);
		
		return result;
	}

	
	
	//////////////////
	@Override
	public int batchDeleteDqrsSditmLst(String allDbms,String allDbmsSchId,String delType) throws Exception {
		
		int result = 0;
		
		Map<String,String> saveTmp = new HashMap();
		
		saveTmp.put("allDbms", allDbms);
		saveTmp.put("allDbmsSchId", allDbmsSchId);
		
		if(delType.equals("dmnSditm")) {//검증표준관리 > 일괄삭제 할 경우
			result += dqrsStndMapper.batchDelDqrsDmn(saveTmp);
			result += dqrsStndMapper.batchDelDqrsSditm(saveTmp);
		}else if(delType.equals("tbl")) {//검증모델관리 > 일괄삭제 할 경우
			result += dqrsStndMapper.batchDelDqrsTbl(saveTmp);
		}
		
		
		
		return result;
	}
	
	
	/////////////표준준수 분석
	@Override
	public List<DqrsResult> getDqrsStndColGapLst(DqrsResult search) {

		return dqrsStndMapper.selectDqrsStndColGapLst(search);
	}
}
