package kr.wise.commons.bbs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface BBSAttributeManageService {

    /**
     * 사용중이지 않은 게시판 속성 정보의 목록을 조회 한다.
     *
     * @param BoradMasterVO
     * @return
     * @throws Exception
     */
     public Map<String, Object> selectNotUsedBdMstrList(BoardMasterVO vo) throws Exception;

    /**
     * 등록된 게시판 속성정보를 삭제한다.
     *
     * @param BoardMaster
     */
     public void deleteBBSMasterInf(BoardMaster boardMaster) throws Exception;

    /**
     * 게시판 속성정보를 수정한다.
     *
     * @param BoardMaster
     */
     public void updateBBSMasterInf(BoardMaster boardMaster) throws Exception;

    /**
     * 게시판 속성정보 한 건을 상세조회한다.
     *
     * @param BoardMasterVO
     */
     public BoardMasterVO selectBBSMasterInf(BoardMaster master) throws Exception;

    /**
     * 게시판 속성 정보의 목록을 조회 한다.
     *
     * @param BoardMasterVO
     */
     public Map<String, Object> selectBBSMasterInfs(BoardMasterVO searchVO) throws Exception;
	
    /**
     * 신규 게시판 속성정보를 생성한다.
     *
     * @param BoardMaster
     */
     public void insertBBSMasterInf(BoardMaster boardMaster) throws Exception;
     
     /**
      * 템플릿의 유효여부를 점검한다.
      *
      * @param BoardMasterVO
      */
     public void validateTemplate(BoardMasterVO searchVO) throws Exception;

     /**
      * 유효한 게시판 마스터 정보를 호출한다.
      *
      * @param searchVO
      * @return
      * @throws Exception
      */
     public List<BoardMasterVO> selectAllBBSMasteInf(BoardMasterVO vo) throws Exception;

     /**
      * 생성된 게시판 리스트 조회.
      *
      * @param searchVO
      * @return
      * @throws Exception
      */
     public List selectAllBoardMstrList(BoardMasterVO vo) throws Exception;

     /**
      * 사용중인 게시판 속성 정보의 목록을 조회 한다.
      *
      * @param BoardMasterVO
      */
     public Map<String, Object> selectBdMstrListByTrget(BoardMasterVO vo) throws Exception;

     /**
      * 커뮤니티, 동호회에서 사용중인 게시판 속성 정보의 목록을 전체조회 한다.
      *
      * @param vo
      * @return
      * @throws Exception
      */
     public List<BoardMasterVO> selectAllBdMstrByTrget(BoardMasterVO vo) throws Exception;

	/**
	 * saction(I|U)에 따른 저장 혹은 업데이트
	 *
	 * @param saveVO
	 * @param saction
	 * @return
	 * @throws Exception
	 */
	public int saveBBS(BoardMasterVO saveVO, String saction) throws Exception;

	/**
	 * 게시판 리스트 저장
	 *
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int regBbsList(ArrayList<BoardMasterVO> list) throws Exception;

	/**
	 * 게시판 삭제
	 *
	/** @param delVO
	/** @return Administrator */
	public int delBBS(BoardMasterVO delVO);
}
