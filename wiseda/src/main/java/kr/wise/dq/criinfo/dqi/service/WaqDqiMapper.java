package kr.wise.dq.criinfo.dqi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqDqiMapper extends CommonRqstMapper{
    int deleteByPrimaryKey(WaqDqiVO delVO);

    int insert(WaqDqiVO record);

    int insertSelective(WaqDqiVO record);

    WaqDqiVO selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqDqiVO record);

    int updateByPrimaryKey(WaqDqiVO record);

    //조회
    List<WaqDqiVO> selectDqiRqstList(WaqMstr search);

    //검증상세 테이블  WAQ_RQST_VRF_DTLS 삭제
    int deleteRqstVrfDtls(WaqMstr mstVo);

    //검증시작
    //초기화
    int updateCheckInit(String rqstNo);

    //등록유형코드, OBJ_ID, OBJ_VERS UPDATE
    int updateObjInfo(String rqstNo);

    //상위중요정보항목 ID UPDATE
  	int updateVrfUppBizAareId(WaqMstr mstVo);
  	
  	//상위데이터품질지표ID UPDATE(WAM테이블에 없을때)
  	int updateVrfUppDqiId(String rqstNo);

  	//레벨 update
  	int updateVrfDqiLvl(WaqMstr mstVo);
  	
  	//변경 데이터 없음 (CTQ00)
  	int checkNoChg(Map<String, Object> checkmap);

    //요청서내 중복자료 검증(CTQ01)
	int checkDupDqi(Map<String, Object> checkmap);

	//상위중요정보항목명 미존재(CTQ03)
	int checkNotExistUppDqi(Map<String, Object> checkmap);

	//미존재 중요정보항목(삭제시)(CTQ02)
	int checkNotExistDqi(Map<String, Object> checkmap);

	//사용중 중요정보항목(삭제시)(CTQ04)
	int checkUsedDqi(Map<String, Object> checkmap);

	//삭제시 하위 중요정보항목 존재여부 체크(CTQ05)
	int checkLowRankBizAareId(Map<String, Object> checkmap);

	//요청서내 상위중요정보항목 삭제(CTQ06)
	int checkDelUppBizAareId(Map<String, Object> checkmap);

	//결재로직 완료 후 요청중 데이터품질지표(DQI07)
	int checkDupDqiRqst(Map<String, Object> checkmap);

	//등록가능 VRF_CD UPDATE
	int updateVrfCd(WaqMstr mstVo);

	//wam 등록완료  waq_영역 검증상태코드 RVW_STS_CD update
	int updateRvwStsCd(WaqMstr mstVo);

	/** @param searchVO
	/** @return meta */
	WaqDqiVO selectRqstDqi(WaqDqiVO searchVO);

	/** @param savevo
	/** @return meta */
	int updatervwStsCd(WaqDqiVO savevo);

	/** @param rqstno
	/** @return meta */
	List<WaqDqiVO> selectWaqC(String rqstno);

	/** @param savevo
	/** @return meta */
	int updateidByKey(WaqDqiVO savevo);



	/** @param reqmst
	/** @param list
	/** @return meta */
	int insertwam2waq(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqDqiVO> list);

	/**
	 * @param rqstno
	 * @return
	 */
	int updateWaqFullPath(String rqstno);



	/**
	 * @param rqstno
	 * @return
	 */
	int updateWamFullPath(String rqstno);

	/**
	 * @param rqstno
	 * @return
	 */
	int updateWahFullPath(String rqstno);

	/** @param checkmap
	/** @return meta */
	int checkDqiBscLvl(Map<String, Object> checkmap);

	/** @param mstVo
	/** @return meta */
	int updateVrfUppDqiIdClear(WaqMstr mstVo);

	int insertExlDqi(String prfId, String dqiLnm);

	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}