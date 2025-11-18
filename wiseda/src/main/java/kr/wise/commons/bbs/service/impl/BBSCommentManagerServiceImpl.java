package kr.wise.commons.bbs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.bbs.service.BBSAddedOptionMapper;
import kr.wise.commons.bbs.service.BBSCommentMapper;
import kr.wise.commons.bbs.service.BBSCommentManagerService;
import kr.wise.commons.bbs.service.Board;
import kr.wise.commons.bbs.service.BoardMaster;
import kr.wise.commons.bbs.service.BoardMasterVO;
import kr.wise.commons.bbs.service.BoardVO;
import kr.wise.commons.bbs.service.Comment;
import kr.wise.commons.bbs.service.CommentVO;
import kr.wise.commons.bbs.service.ComtnbbsMapper;
import kr.wise.commons.cmm.FileVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.cmm.service.FileManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("bbsCommentMngService")
public class BBSCommentManagerServiceImpl implements BBSCommentManagerService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private BBSAddedOptionMapper bbsAddedOptionMapper;
    
	@Inject
    private BBSCommentMapper bbsCommentMapper;
	
	@Inject
    private EgovIdGnrService egovAnswerNoGnrService;

    /**
     * 댓글 사용 가능 여부를 확인한다.
     */
    public boolean canUseComment(String bbsId) throws Exception {
	//String flag = EgovProperties.getProperty("Globals.addedOptions");
	//if (flag != null && flag.trim().equalsIgnoreCase("true")) {//2011.09.15
	    BoardMaster vo = new BoardMaster();
	    
	    vo.setBbsId(bbsId);
	    
	    BoardMasterVO options = bbsAddedOptionMapper.selectAddedOptionsInf(vo);
	    
	    if (options == null) {
		return false;
	    }
	    
	    if (options.getCommentAt().equals("Y")) {
		return true;
	    }
	//}
	
	return false;
    }    
	
	@Override
	public Map<String, Object> selectArticleCommentList(CommentVO commentVO) {
		log.debug("{}", commentVO);
		
		List<CommentVO> result = bbsCommentMapper.selectArticleCommentList(commentVO);
		int cnt = bbsCommentMapper.selectArticleCommentListCnt(commentVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return map;
	}


	@Override
	public void insertArticleComment(Comment comment) throws Exception {
		comment.setCommentNo(egovAnswerNoGnrService.getNextLongId() + "");//2011.10.18
		bbsCommentMapper.insertArticleComment(comment);
	}


	@Override
	public void deleteArticleComment(CommentVO commentVO) {
		bbsCommentMapper.deleteArticleComment(commentVO);
	}


	@Override
	public CommentVO selectArticleCommentDetail(CommentVO commentVO) {
		return bbsCommentMapper.selectArticleCommentDetail(commentVO);
	}


	@Override
	public void updateArticleComment(Comment comment) {
		bbsCommentMapper.updateArticleComment(comment);
	}

}
