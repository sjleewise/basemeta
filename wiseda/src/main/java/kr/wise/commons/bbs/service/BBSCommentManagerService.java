package kr.wise.commons.bbs.service;

import java.util.Map;

public interface BBSCommentManagerService {

    public boolean canUseComment(String bbsId) throws Exception;

    public Map<String, Object> selectArticleCommentList(CommentVO commentVO) throws Exception;

	public void insertArticleComment(Comment comment) throws Exception;

	public void deleteArticleComment(CommentVO commentVO) throws Exception;

	public CommentVO selectArticleCommentDetail(CommentVO commentVO) throws Exception;

	public void updateArticleComment(Comment comment) throws Exception;

}