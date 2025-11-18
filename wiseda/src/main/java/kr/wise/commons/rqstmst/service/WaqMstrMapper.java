package kr.wise.commons.rqstmst.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.meta.intf.service.GroupWareSendVO;

@Mapper
public interface WaqMstrMapper {
    int deleteByPrimaryKey(String rqstNo);

    //int insert(WaqMstr record);

    int insertSelective(WaqMstr record);

    WaqMstr selectByPrimaryKey(String rqstNo);

    List<WaqMstr> selectList(WaqMstr record);

    int updateByPrimaryKeySelective(WaqMstr record);

    int updateWaqMstrAprvInfo(WaqMstr record);

	int deleteAll();

	WaqMstr selectrequestMst(WaqMstr reqmst);

	int updateWaqMstrqstNm(@Param("rqstNo") String rqstNo
			, @Param("tblnm") String tblnm , @Param("colnm") String colnm
			, @Param("bizDtlNm") String bizdtlnm);

	int updateWaqMstrqstNm2(@Param("rqstNo") String rqstNo
			, @Param("tblnm") String tblnm , @Param("colnm") String colnm
			, @Param("bizDtlNm") String bizdtlnm);

		
	int updateWaqMstrStepCd(WaqMstr record);

	/** @return insomnia */
	String selectrvwStatus(@Param("rqstNo") String rqstNo, @Param("tblnm") String tblnm);

	/** @param reqmst
	/** @return insomnia */
	int updateWaqMstrRequestInfo(WaqMstr reqmst);

	/** @param record
	/** @return yeonho */
	List<WaqMstr> selectRqstMyList(WaqMstr record);

	/** 내반려목록 조회용*/
	List<WaqMstr> selectRejMyList(WaqMstr record);
	/** @param record
	/** @return yeonho */
	List<WaqMstr> selectRqstMyListForMain(WaqMstr record);

	/** @param record
	/** @return yeonho */
	List<WaqMstr> selectRqstToDoList(WaqMstr record);

	List<WaqMstr> selectRqstResultList(WaqMstr record);

	/** @param record
	/** @return yeonho */
	List<WaqMstr> selectRqstToDoListForMain(WaqMstr record);

	WaqMstr selectRqstMyListCount(WaqMstr record);

	WaqMstr selectRqstToDoListCount(WaqMstr record);
	
	WaqMstr selectRqstCount(WaqMstr record);

	/** @param rqstNo
	/** @param tblNm
	/** @param colNm
	/** @param bizdtlnm insomnia */
	int updateWaqMstrqstNmDic(@Param("rqstNo") String rqstNo
			, @Param("tblnm") String tblnm , @Param("colnm") String colnm
			, @Param("bizDtlNm") String bizdtlnm);
	
	int updateWaqMstrqstNmAdc(@Param("rqstNo") String rqstNo
			, @Param("tblnm") String tblnm , @Param("colnm") String colnm
			, @Param("bizDtlNm") String bizdtlnm);

	/** @param rqstNo
	/** @param string
	/** @return yeonho */
	int deleteRqst(@Param("rqstNo") String rqstNo, @Param("tblNm") String tblNm);

	/** @param rqstNo
	/** @param tblNm
	/** @param colNm
	/** @param bizdtlnm
	/** @return yeonho */
	int updateWaqMstrqstNmDtt(@Param("rqstNo")String rqstNo, @Param("tblnm")String tblNm, @Param("colnm")String colNm,
			@Param("bizDtlNm")String bizdtlnm);
	
	int updateWaqMstrqstNmDtt2(@Param("rqstNo")String rqstNo, @Param("tblnm")String tblNm, @Param("colnm")String colNm,
			@Param("bizDtlNm")String bizdtlnm);

	int updateWaqMstrqstDtlNm(@Param("rqstNo")String rqstNo, @Param("tblnm")String tblNm, @Param("colnm")String colNm,
			@Param("bizDtlNm")String bizdtlnm);

	List<GroupWareSendVO> selectEaiGwSend(String rqstNo);

	int updateWaqMstrqstNmDDP(@Param("rqstNo")String rqstNo, @Param("tblnm")String tblNm, @Param("colnm")String colNm, 
			@Param("bizDtlNm")String bizDtlNm); 

	//엑셀 수동 추가를 위한 조회 쿼리(조회 파라티터 만큼 셀렉트)
	List<CommonVo> selectBlankData(CommonVo vo);
	
}