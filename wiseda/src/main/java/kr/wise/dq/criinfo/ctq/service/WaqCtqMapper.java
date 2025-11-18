package kr.wise.dq.criinfo.ctq.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqCtqMapper extends CommonRqstMapper{
    int deleteByPrimaryKey(WaqCtqVO delVO);

    int insert(WaqCtqVO record);

    int insertSelective(WaqCtqVO record);

    WaqCtqVO selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqCtqVO record);

    int updateByPrimaryKey(WaqCtqVO record);

    //조회
    List<WaqCtqVO> selectCtqRqstList(String rqstNo);

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
  	int updateVrfUppCtqId(String rqstNo);

  	//레벨 update
  	int updateVrfCtqLvl(WaqMstr mstVo);

  	//변경 데이터 없음 (CTQ00)
  	int checkNoChg(Map<String, Object> checkmap);

    //요청서내 중복자료 검증(CTQ01)
	int checkDupCtq(Map<String, Object> checkmap);

	//상위중요정보항목명 미존재(CTQ03)
	int checkNotExistUppCtq(Map<String, Object> checkmap);

	//미존재 중요정보항목(삭제시)(CTQ02)
	int checkNotExistCtq(Map<String, Object> checkmap);

	//사용중 중요정보항목(삭제시)(CTQ04)
	int checkUsedCtq(Map<String, Object> checkmap);

	//삭제시 하위 중요정보항목 존재여부 체크(CTQ05)
	int checkLowRankBizAareId(Map<String, Object> checkmap);

	//요청서내 상위중요정보항목 삭제(CTQ06)
	int checkDelUppBizAareId(Map<String, Object> checkmap);

	//결재로직 완료 후 요청중 중요정보항목(CTQ07)
	int checkDupCtqRqst(Map<String, Object> checkmap);

	//등록가능 VRF_CD UPDATE
	int updateVrfCd(WaqMstr mstVo);

	//wam 등록완료  waq_영역 검증상태코드 RVW_STS_CD update
	int updateRvwStsCd(WaqMstr mstVo);


	/** @param search
	/** @return meta */
	List<WaqCtqVO> selectCtqRqstList(WaqMstr search);

	/** @param searchVO
	/** @return meta */
	WaqCtqVO selectRqstCtq(WaqCtqVO searchVO);

	/** @param savevo
	/** @return meta */
	int updatervwStsCd(WaqCtqVO savevo);

	/** @param rqstno
	/** @return meta */
	List<WaqCtqVO> selectWaqC(String rqstno);

	/** @param savevo
	/** @return meta */
	int updateidByKey(WaqCtqVO savevo);

	/** @param reqmst
	/** @param list
	/** @return meta */
	int insertwam2waq(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqCtqVO> list);

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
	int checkCtqBscLvl(Map<String, Object> checkmap);

	/** @param mstVo
	/** @return meta */
	int updateVrfUppCtqIdClear(WaqMstr mstVo);

	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}