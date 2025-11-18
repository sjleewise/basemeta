package kr.wise.dq.criinfo.bizarea.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqBizAreaMapper extends CommonRqstMapper{
    int deleteByPrimaryKey(WaqBizAreaVO delVO);

    int insert(WaqBizAreaVO record);

    int insertSelective(WaqBizAreaVO record);

    WaqBizAreaVO selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);

    int updateByPrimaryKeySelective(WaqBizAreaVO record);

    int updateByPrimaryKey(WaqBizAreaVO record);

    //조회
    List<WaqBizAreaVO> selectBizAreaRqstList(WaqMstr search);

    //검증상세 테이블  WAQ_RQST_VRF_DTLS 삭제
//    int deleteRqstVrfDtls(WaqMstr mstVo);

    //검증시작
    //초기화
    int updateCheckInit(String rqstNo);

    //등록유형코드, OBJ_ID, OBJ_VERS UPDATE
    int updateObjInfo(String rqstNo);

    //상위업무영역 ID UPDATE
  	int updateVrfUppBizAareId(WaqMstr mstVo);
  	
  	//상위업무영역ID UPDATE(WAM테이블에 없을때)
  	int updateVrfUppBizAreaId(String rqstNo);

  	//레벨 update
  	int updateVrfBizAreaLvl(WaqMstr mstVo);

  	//변경 데이터 없음 (BA000)
  	int checkNoChg(Map<String, Object> checkmap);

    //요청서내 중복자료 검증(BA001)
	int checkDupBizArea(Map<String, Object> checkmap);

	//상위업무영역명 미존재(BA003)
	int checkNotExistUppBizArea(Map<String, Object> checkmap);

	//미존재 업무영역(삭제시)(BA002)
	int checkNotExistBizArea(Map<String, Object> checkmap);

	//사용중 업무영역(삭제시)(BA004)
	//업무규칙 테이블 생성 후 구현
	int checkUsedBizArea(Map<String, Object> checkmap);

	//삭제시 하위 업무영역 존재여부 체크(BA005)
	int checkLowRankBizAareId(Map<String, Object> checkmap);

	//요청서내 상위업무영역 삭제(BA006)
	int checkDelUppBizAareId(Map<String, Object> checkmap);

	//결재로직 완료 후 요청중 업무영역(BA007)
	int checkDupBizAreaRqst(Map<String, Object> checkmap);

	//등록가능 VRF_CD UPDATE
	int updateVrfCd(WaqMstr mstVo);

	//wam 등록완료  waq_영역 검증상태코드 RVW_STS_CD update
	int updateRvwStsCd(WaqMstr mstVo);

	/** meta */
	WaqBizAreaVO selectRqstBizArea(WaqBizAreaVO searchVO);

	/** meta */
	int updatervwStsCd(WaqBizAreaVO savevo);

	/** meta */
	List<WaqBizAreaVO> selectWaqC(String rqstno);

	/** meta */
	int updateidByKey(WaqBizAreaVO savevo);



	/** @param reqmst
	/** @param list
	/** @return meta */
	int insertwam2waq(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqBizAreaVO> list);

	/**
	 * @param mstVo
	 * @return
	 */
	int updateVrtFullPath(WaqMstr mstVo);

	/**
	 * @param mstVo
	 * @return
	 */
	int updateWamFullPath(WaqMstr mstVo);

	/**
	 * @param mstVo
	 * @return
	 */
	int updateWahFullPath(WaqMstr mstVo);

	/** @param checkmap
	/** @return meta */
	int checkBizAreaBscLvl(Map<String, Object> checkmap);

	/** @param mstVo
	/** @return meta */
	int updateVrfUppBizAreaIdClear(WaqMstr mstVo);

	int updateVrfDeptId(WaqMstr mstVo);

	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}