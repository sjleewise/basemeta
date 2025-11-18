package kr.wise.commons.bbs.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface BBSCommentMapper {
	
	List<CommentVO> selectArticleCommentList(CommentVO searchVO);
	
	int selectArticleCommentListCnt(CommentVO commentVO);
	
	void insertArticleComment(Comment comment);
	
	void deleteArticleComment(CommentVO commentVO);
	
	CommentVO selectArticleCommentDetail(CommentVO commentVO);
	
	void updateArticleComment(Comment comment);
	
}