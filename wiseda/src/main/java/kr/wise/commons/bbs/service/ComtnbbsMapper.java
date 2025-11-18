package kr.wise.commons.bbs.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface ComtnbbsMapper {
/*    int deleteByPrimaryKey(@Param("nttId") BigDecimal nttId, @Param("bbsId") String bbsId);

    int insert(Comtnbbs record);

    int insertSelective(Comtnbbs record);

    Comtnbbs selectByPrimaryKey(@Param("nttId") BigDecimal nttId, @Param("bbsId") String bbsId);

    int updateByPrimaryKeySelective(Comtnbbs record);

    int updateByPrimaryKeyWithBLOBs(Comtnbbs record);

    int updateByPrimaryKey(Comtnbbs record);*/
	
	List<BoardVO> selectBoardArticleList(BoardVO searchVO);

	int selectBoardArticleListCnt(BoardVO boardVO);

	int replyBoardArticle(Board board);

	void insertBoardArticle(Board board);

	int selectMaxInqireCo(BoardVO boardVO);

	void updateInqireCo(BoardVO boardVO);

	BoardVO selectBoardArticle(BoardVO boardVO);

	void deleteBoardArticle(Board board);

	void updateBoardArticle(Board board);

	long getParentNttNo(Board board);

	void updateOtherNttNo(Board board);

	void updateNttNo(Board board);
	
	String selectBoardRegID (BoardVO boardVO);

	List<BoardVO> selectNoticeArticleList(BoardVO boardVO);
}