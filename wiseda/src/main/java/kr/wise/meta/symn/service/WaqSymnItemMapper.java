package kr.wise.meta.symn.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.symn.service.WaqSymnItem;

@Mapper 
public interface WaqSymnItemMapper extends CommonRqstMapper {
    
	int insert(WaqSymnItem record);

    /** 요청서 저장 */
    int insertSelective(WaqSymnItem record);

    /** 요청서 변경 */
	int updateByPrimaryKeySelective(WaqSymnItem saveVo);

	/** 요청서 단건 삭제 */
	int deleteByPrimaryKey(WaqSymnItem saveVo);

	/** 작성 초기화 */
	void updateCheckInit(String rqstNo);

	/** 요청서 조회 */
	List<WaqSymnItem> selectSymnItemRqstListbyMst(WaqMstr search);

	/** 검토결과 */
	int updateRvwStsCd(WaqSymnItem savevo);
	
	/** 승일할 요청서 정보 추출 */
	List<WaqSymnItem> selectWaqC(String rqstno);
	
	/** pk ID채번 값 update */
	void updateidByKey(WaqSymnItem savevo);
	
	/** 요청서내 중복 */
	void checkDupInRqst(Map<String, Object> checkmap);

	/** 다른요청서와 중복 */
	void checkDupOtherRqst(Map<String, Object> checkmap);
	
	/** 변경사항 없음 체크 */
	void checkNotChgData(Map<String, Object> checkmap);
	
	
	WaqSymnItem selectSymnItemRqstDetail(WaqSymnItem searchVo);

	List<WaqSymnItem> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqSymnItem> list);
	

	int insertWaqRejected(WaqMstr reqmst, String oldRqstNo);

	/** 유사항목 논리명 중복체크 */
//	void checkDupSymnItemLnm(Map<String, Object> checkmap);

	/** 유사항목 물리명 중복체크 */
//	void checkDupSymnItemPnm(Map<String, Object> checkmap);

	

    
}