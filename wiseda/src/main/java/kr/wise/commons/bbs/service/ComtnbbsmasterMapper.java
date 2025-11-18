package kr.wise.commons.bbs.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface ComtnbbsmasterMapper {

	List selectAllBoardMstrList(BoardMasterVO vo);



//    int deleteByPrimaryKey(String bbsId);

//    int insert(Comtnbbsmaster record);

//    int insertSelective(Comtnbbsmaster record);

//    Comtnbbsmaster selectByPrimaryKey(String bbsId);

    BoardMasterVO  selectBBSMasterInf(BoardMaster master);



	/**
	/* 게시판 저장
	/*
	/*
	 * @param saveVO
	 * @return
	 */
	int insertBBSMastetInf(BoardMasterVO saveVO);



	/**
	/* 게시판 정보 업데이트
	/*
	 * @param saveVO
	 * @return
	 */
	int updateBBSMastetInf(BoardMasterVO saveVO);


	/**
	/* 게시판 삭제
	/*
	/** @param ids
	/** @return Administrator */
	int deleteBbs(String[] delVO);



	/** @param vo
	/** @return Administrator */
	int deleteBbsVO(BoardMasterVO vo);



	void deleteBBSMaster(BoardMaster boardMaster);



	void updateBBSMaster(BoardMaster boardMaster);



	List<BoardMasterVO> selectBBSMasterInfs(BoardMasterVO searchVO);



	int selectBBSMasterInfsCnt(BoardMasterVO searchVO);



	void insertBBSMasterInf(BoardMaster boardMaster);

//    int updateByPrimaryKeySelective(Comtnbbsmaster record);

//    int updateByPrimaryKey(Comtnbbsmaster record);
}

//List selectProgrmList(SearchVO searchVO);
//
//int selectProgrmListTotCnt(SearchVO searchVO);
//
//ProgrmManageVO selectProgrmDetail(ProgrmManageVO searchVO);
//
//int insertProgram(ProgrmManageVO saveVO);
//
//int updateProgram(ProgrmManageVO saveVO);
//
//int deleteProgram(String[] saveVO);
//
//int deleteProgramVo(ProgrmManageVO vo);
