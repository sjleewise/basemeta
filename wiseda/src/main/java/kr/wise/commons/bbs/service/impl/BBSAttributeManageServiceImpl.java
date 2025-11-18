package kr.wise.commons.bbs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.bbs.service.BBSAttributeManageService;
import kr.wise.commons.bbs.service.BoardMaster;
import kr.wise.commons.bbs.service.BoardMasterVO;
import kr.wise.commons.bbs.service.ComtnbbsmasterMapper;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * 게시판 속성관리를 위한 서비스 구현 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.3.24  이삼섭          최초 생성
 *   2009.06.26	한성곤		2단계 기능 추가 (댓글관리, 만족도조사)
 *   2011.09.15 서준식       댓글, 만족도 조사 적용 방법 변경
 * </pre>
 */
@Service ("BBSAttributeManageService")
public class BBSAttributeManageServiceImpl implements BBSAttributeManageService {


	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private ComtnbbsmasterMapper bbsmasterMapper;

	@Inject
	private EgovIdGnrService egovBBSMstrIdGnrService;



	@Override
	public void deleteBBSMasterInf(BoardMaster boardMaster) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertBBSMasterInf(BoardMaster boardMaster) throws Exception {
		// TODO Auto-generated method stub
		
	}



    /**
     * 게시판 속성정보 한 건을 상세조회한다.
     *
     * @see egovframework.com.cop.bbs.brd.service.EgovBBSAttributeManageService#selectBBSMasterInf(egovframework.com.cop.bbs.brd.service.BoardMasterVO)
     */
    @Override
	public BoardMasterVO selectBBSMasterInf(BoardMaster master) throws Exception {
	//---------------------------------
	// 2009.06.26 : 2단계 기능 추가
	//---------------------------------
	//return attrbMngDAO.selectBBSMasterInf(searchVO);
    	BoardMasterVO result =  bbsmasterMapper.selectBBSMasterInf(master);

	//String flag = EgovProperties.getProperty("Globals.addedOptions");
	//if (flag != null && flag.trim().equalsIgnoreCase("true")) {
/*	if(EgovComponentChecker.hasComponent("EgovBBSCommentService") || EgovComponentChecker.hasComponent("EgovBBSSatisfactionService")){//2011.09.15
	    BoardMasterVO options = addedOptionsDAO.selectAddedOptionsInf(searchVO);

	    if (options != null) {
		if (options.getCommentAt().equals("Y")) {
		    result.setOption("comment");
		}

		if (options.getStsfdgAt().equals("Y")) {
		    result.setOption("stsfdg");
		}
	    } else {
		result.setOption("na");	// 미지정 상태로 수정 가능 (이미 지정된 경우는 수정 불가로 처리)

	    }
	}*/

    	result.setOption("na");
    	return result;
	////-------------------------------

    }

	@Override
	public Map<String, Object> selectBBSMasterInfs(BoardMasterVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateBBSMasterInf(BoardMaster boardMaster) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateTemplate(BoardMasterVO searchVO) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public List<BoardMasterVO> selectAllBBSMasteInf(BoardMasterVO vo)
			throws Exception {
		return null;
	}

	// 게시판 조회
	@Override
	public List selectAllBoardMstrList(BoardMasterVO vo)
			throws Exception {
		return bbsmasterMapper.selectAllBoardMstrList(vo);
	}

	@Override
	public Map<String, Object> selectBdMstrListByTrget(BoardMasterVO vo)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BoardMasterVO> selectAllBdMstrByTrget(BoardMasterVO vo)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> selectNotUsedBdMstrList(BoardMasterVO vo)
			throws Exception {
		// TODO Auto-generated method stubg
		return null;
	}

	
	/** 게시판 폼 내용을 저장 - saction (I|U)에 따라 저장 또는 업데이트...
	 * @throws Exception */
	
	@Override
	public int saveBBS(BoardMasterVO saveVO, String saction) throws Exception{

		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
    	log.debug("USER : {}", user);

		log.debug("param : [{}]  ", saveVO.getFrstRegisterId());

		if("I".equals(saction)) {
			String objid = egovBBSMstrIdGnrService.getNextStringId();
			saveVO.setBbsId(objid);
			saveVO.setFrstRegisterId(user.getId());
			return bbsmasterMapper.insertBBSMastetInf(saveVO);
		} else if ("U".equals(saction)) {
			saveVO.setLastUpdusrId(user.getId());
			return bbsmasterMapper.updateBBSMastetInf(saveVO);
		} else {
			return 0;
		}
	}
	

	
	/** 게시판 폼 내용을 삭제*/
	
	@Override
	public int delBBS(BoardMasterVO delVO){
		log.debug("delBBS-delVO:{}", delVO);

		String id = delVO.getBbsId();
		String[] ids = id.split("[|]");

		log.debug("ibs:{}", ids);

		return bbsmasterMapper.deleteBbs(ids);

	}

	/** 게시판 리스트 일괄 저장
	 * @throws Exception */
	/**
	@Override
	public int regBbsList(ArrayList<BoardMasterVO> list) throws Exception {

		int res = 0;

		for (BoardMasterVO vo : list) {
			res += regBbs(vo);
		}

		return res;
	}
	*/

	/** 게시판 ID에 따라 신규/변경 처리
	 *  이력 테이블이 아니며 삭제시 실제 삭제 처리함....
	 * @throws Exception
	 *  */
	/**
	private int regBbs(BoardMasterVO vo) throws Exception {

		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();

		String tmpStatus = vo.getIbsStatus();
		String tmpId = vo.getBbsId();

//		log.debug("BbsId  : " + tmpId);
//		log.debug("tmpStatus before : " + tmpStatus);
		if("I".equals(tmpStatus) && !StringUtils.hasText(tmpId)){tmpStatus = "C";}
		else {tmpStatus = "U";}

//		log.debug("tmpStatus after : " + tmpStatus);

		int result = 0;

		if("C".equals(tmpStatus)) {
			String objid = egovBBSMstrIdGnrService.getNextStringId();
			vo.setBbsId(objid);
			vo.setFrstRegisterId(user.getId());
			result = bbsmasterMapper.insertBBSMastetInf(vo);

		} else if ("U".equals(tmpStatus)) {
			vo.setLastUpdusrId(user.getId());
			result = bbsmasterMapper.updateBBSMastetInf(vo);

		}
//		else if ("D".equals(tmpId)) {
//			//체크된 행 중에 입력상태인 경우 시트에서 제거...
//			result = bbsmasterMapper.deleteBbsVO(vo);
//		}

		return result;
	}*/

	

	@Override
	public int regBbsList(ArrayList<BoardMasterVO> list) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
