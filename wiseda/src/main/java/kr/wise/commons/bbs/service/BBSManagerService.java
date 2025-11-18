package kr.wise.commons.bbs.service;

import java.util.List;
import java.util.Map;

public interface BBSManagerService {
    /**
     * 조건에 맞는 게시물 목록을 조회 한다.
     * 
     * @param boardVO
     * @return
     * @throws Exception
     */
    public Map<String, Object> selectBoardArticles(BoardVO boardVO, String attrbFlag) throws Exception;

    //public Map<String, Object> selectArticleList(BoardVO boardVO);

    /**
     * 게시물 대하여 상세 내용을 조회 한다.
     * 
     * @param boardVO
     * @return
     * @throws Exception
     */
    public BoardVO selectBoardArticle(BoardVO boardVO) throws Exception;

    /**
     * 게시판에 게시물 또는 답변 게시물을 등록 한다.
     * 
     * @param Board
     * @throws Exception
     */	
    public void insertBoardArticle(Board board) throws Exception;

    /**
     * 게시물 한 건의 내용을 수정 한다.
     * 
     * @param Board
     * @throws Exception
     */
    public void updateBoardArticle(Board board) throws Exception;

    /**
     * 게시물 한 건을 삭제 한다.
     * 
     * @param Board
     * @throws Exception
     */
    public void deleteBoardArticle(Board board) throws Exception;

    /**
     * 공지 게시물을 조회한다.
     * 
     * @param BoardVO
     * @throws Exception
     */
    public List<BoardVO> selectNoticeArticleList(BoardVO boardVO) throws Exception;
	
    /**
     * 방명록에 대한 목록을 조회 한다.
     * 
     * @param boardVO
     * @return
     * @throws Exception
     */
    public Map<String, Object> selectGuestList(BoardVO vo) throws Exception;
	
    /**
     * 방명록 내용을 삭제 한다.
     * 
     * @param boardVO
     * @throws Exception
     */
    public void deleteGuestList(BoardVO boardVO) throws Exception;

    /**
     * 방명록에 대한 패스워드를 조회 한다.
     * 
     * @param Board
     * @return
     * @throws Exception
     */
    public String getPasswordInf(Board Board) throws Exception;
    
    /**
     * 게시물에 대한 등록 사용자 ID를 가져온다
     * 
     * @param BoardVO
     * @return
     * @throws Exception
     */
    public String selectBoardRegID(BoardVO boardVO) throws Exception;
}