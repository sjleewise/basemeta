package kr.wise.commons.bbs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.bbs.service.BBSManagerService;
import kr.wise.commons.bbs.service.Board;
import kr.wise.commons.bbs.service.BoardVO;
import kr.wise.commons.bbs.service.ComtnbbsMapper;
import kr.wise.commons.cmm.FileVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.cmm.service.FileManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



/**
 * 게시물 관리를 위한 서비스 구현 클래스
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
 *   2009.3.19  이삼섭          최초 생성
 *	 2011.09.22 서준식          nttId IdGen 서비스로 변경
 * </pre>
 */
@Service ("bbsMngService")
public class BBSManagerServiceImpl implements BBSManagerService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private ComtnbbsMapper bbsMapper;
	
//    @Resource(name = "egovNttIdGnrService")
	@Inject
    private EgovIdGnrService egovNttIdGnrService;
	
	@Inject
	private FileManagerService  fileMngService;

	/**
     * 조건에 맞는 게시물 목록을 조회 한다.
     * 
     * @see egovframework.com.cop.bbs.brd.service.EgovBBSManageService#selectBoardArticles(egovframework.com.cop.bbs.brd.service.BoardVO)
     */
    public Map<String, Object> selectBoardArticles(BoardVO boardVO, String attrbFlag) throws Exception {
    	log.debug("{}", boardVO);
    	
		List<BoardVO> list = bbsMapper.selectBoardArticleList(boardVO);
		List<BoardVO> result = new ArrayList<BoardVO>();
	
/*		if ("BBSA01".equals(attrbFlag)) {
		    // 유효게시판 임
		    String today = EgovDateUtil.getToday();
	
		    BoardVO vo;
		    Iterator<BoardVO> iter = list.iterator();
		    while (iter.hasNext()) {
			vo = (BoardVO)iter.next();
			
			if (!"".equals(vo.getNtceBgnde()) || !"".equals(vo.getNtceEndde())) {
			    if (EgovDateUtil.getDaysDiff(today, vo.getNtceBgnde()) > 0 || EgovDateUtil.getDaysDiff(today, vo.getNtceEndde()) < 0) {
				// 시작일이 오늘날짜보다 크거나, 종료일이 오늘 날짜보다 작은 경우
				vo.setIsExpired("Y");
			    }
			}
			result.add(vo);
		    }
		} else {*/
		    result = list;
//		}
	
		int cnt = bbsMapper.selectBoardArticleListCnt(boardVO);
	
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));
	
		return map;
    }

    /**
     * 게시물 한 건을 삭제 한다.
     * 
     * @see egovframework.com.cop.bbs.brd.service.EgovBBSManageService#deleteBoardArticle(egovframework.com.cop.bbs.brd.service.Board)
     */
    public void deleteBoardArticle(Board board) throws Exception {
		FileVO fvo = new FileVO();
		
		fvo.setAtchFileId(board.getAtchFileId());
	
		board.setNttSj("이 글은 작성자에 의해서 삭제되었습니다.");
	
		bbsMapper.deleteBoardArticle(board);
	
		if (!"".equals(fvo.getAtchFileId()) || fvo.getAtchFileId() != null) {
			fileMngService.deleteAllFileInf(fvo);
		}
    }

    /**
     * 게시판에 게시물 또는 답변 게시물을 등록 한다.
     * 
     * @see egovframework.com.cop.bbs.brd.service.EgovBBSManageService#insertBoardArticle(egovframework.com.cop.bbs.brd.service.Board)
     */
    public void insertBoardArticle(Board board) throws Exception {
		// SORT_ORDR는 부모글의 소트 오더와 같게, NTT_NO는 순서대로 부여
	
		if ("Y".equals(board.getReplyAt())) {
		    // 답글인 경우 1. Parnts를 세팅, 2.Parnts의 sortOrdr을 현재글의 sortOrdr로 가져오도록, 3.nttNo는 현재 게시판의 순서대로
		    // replyLc는 부모글의 ReplyLc + 1
		    
		    //String tmpParnts = board.getParnts();
				
		    @SuppressWarnings("unused")
		    long tmpNttId = 0L; // 답글 게시물 ID
		    
		    board.setNttId(egovNttIdGnrService.getNextIntegerId());
				
		    bbsMapper.replyBoardArticle(board);
		    
		    long tmpNttNo = bbsMapper.getParentNttNo(board);
		    
		    board.setNttNo(tmpNttNo);
			bbsMapper.updateOtherNttNo(board);

			board.setNttNo(tmpNttNo + 1);
			bbsMapper.updateNttNo(board);
	
		} else {
		    // 답글이 아닌경우 Parnts = 0, replyLc는 = 0, sortOrdr = nttNo(Query에서 처리)
		    board.setParnts("0");
		    board.setReplyLc("0");
		    board.setReplyAt("N");
		    board.setNttId(egovNttIdGnrService.getNextIntegerId());//2011.09.22
		    
		    bbsMapper.insertBoardArticle(board);
		}
    }

	/**
     * 게시물 대하여 상세 내용을 조회 한다.
     * 
     * @see egovframework.com.cop.bbs.brd.service.EgovBBSManageService#selectBoardArticle(egovframework.com.cop.bbs.brd.service.BoardVO)
     */
    public BoardVO selectBoardArticle(BoardVO boardVO) throws Exception {
		if (boardVO.isPlusCount()) {
		    int iniqireCo = bbsMapper.selectMaxInqireCo(boardVO);
		    
		    boardVO.setInqireCo(iniqireCo);
		    bbsMapper.updateInqireCo(boardVO);
		}
	
		return bbsMapper.selectBoardArticle(boardVO);
    }

    /**
     * 게시물 한 건의 내용을 수정 한다.
     * 
     * @param board
     * @throws Exception
     */
    public void updateBoardArticle(Board board) throws Exception {
		bbsMapper.updateBoardArticle(board);
    }

	public Map<String, Object> selectGuestList(BoardVO boardVO)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteGuestList(BoardVO boardVO) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public String getPasswordInf(Board Board) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String selectBoardRegID(BoardVO boardVO) throws Exception {
		return bbsMapper.selectBoardRegID(boardVO);
	}

	@Override
	public List<BoardVO> selectNoticeArticleList(BoardVO boardVO)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
