package kr.wise.admin.sysmgmt.help.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.admin.sysmgmt.help.service.WaaHelp;
import kr.wise.admin.sysmgmt.help.service.WaaHelpMapper;
import kr.wise.admin.sysmgmt.help.service.WaaHelpService;
import kr.wise.admin.sysmgmt.help.service.WaaHelpVO;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service ("WaaHelpService")
public class WaaHelpServiceImpl implements WaaHelpService {

	private static final Logger log = LoggerFactory.getLogger(WaaHelpServiceImpl.class);
	
	@Inject
	private WaaHelpMapper waaHelpMapper;
	
	@Inject
	private EgovIdGnrService egovHelpMstrIdGnrService;
	
	public List<WaaHelpVO> selectAllHelpMstrList(WaaHelpVO vo) throws Exception{
		return waaHelpMapper.selectAllHelpMstrList(vo);
	}
	
	public WaaHelpVO selectHelpMasterInf(WaaHelp help) throws Exception {
		WaaHelpVO result =  waaHelpMapper.selectHelpMasterInf(help);

		result.setOption("na");	// 미지정 상태로 수정 가능 (이미 지정된 경우는 수정 불가로 처리)

	return result;

    }
	
	/** 게시판 폼 내용을 저장 - saction (I|U)에 따라 저장 또는 업데이트...
	 * @throws Exception */
	public int saveHelp(WaaHelpVO saveVO, String saction) throws Exception{
		int result = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
    	log.debug("USER : {}", user);

		log.debug("param : [{}]  ", saveVO.getHelpId());
		String use = saveVO.getUseYn();
		
		if("I".equals(saction)) {
			
			String objid = egovHelpMstrIdGnrService.getNextStringId();
			saveVO.setHelpId(objid);
			saveVO.setWritUserId(user.getId());
			result = waaHelpMapper.insertHelpMastetInf(saveVO);
			
			if ("Y".equals(use)){
				waaHelpMapper.updateHelpId(saveVO);
			}
			else if ("N".equals(use)){
				waaHelpMapper.updateHelpIdNull(saveVO);
			}
		} else if ("U".equals(saction)) {
			saveVO.setWritUserId(user.getId());
			result =  waaHelpMapper.updateHelpMastetInf(saveVO);
			
			if ("Y".equals(use)){ 
				waaHelpMapper.updateHelpId(saveVO);
			}
			else if ("N".equals(use)){
				waaHelpMapper.updateHelpIdNull(saveVO);
			}
			
		} 
		
		return result;
	}

	
	public int delHelp(WaaHelpVO delVO) throws Exception{
		log.debug("delHelp-delVO:{}", delVO);

		int result = 0;
		String id = delVO.getHelpId();
		String[] ids = id.split("[|]");
				
		log.debug("ibs:{}", ids);
		result = waaHelpMapper.deleteHelp(ids);
		waaHelpMapper.updateHelpIdReset(ids);
		return result;
	}

	public WaaHelpVO selectHelpCtnt(String helpId) {
		return waaHelpMapper.selectHelpCtnt(helpId);
	}

}
