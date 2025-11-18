package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqMsgTsfMapper extends CommonRqstMapper {
  
	//이관요청할 리스트 조회(팝업)
    List<WaqMsgTsf> selectMsgListTsf(WaqMsgTsf searchVo);
	
    //메시지이관 등록요청 저장
	int insertSelective(WaqMsgTsf saveVo);
	
	//메시지이관 등록리스트 조회
	List<WaqMsgTsf> selectRqstList(WaqMstr reqmst);

	List<WaqMsgTsf> selectMsgWamList(WaqMsgTsf searchVo);
	
	int updateCheckInit(String rqstNo);
	
	int deleteRqstList(WaqMsgTsf saveVo);
	
	int checkNotChgData(Map<String, Object> map);
	
	int checkDupMsgTsf(Map<String, Object> map);
	
	int checkDupMsgTsfOtherRqst(Map<String, Object> map);
	
	int updatervwStsCd(WaqMsgTsf saveVo);
	
	int updateTgtDb(String rqstNo);
	
	WaqMsgTsf selectRqstDetail(WaqMsgTsf searchVo);
	
	int checkTgtDb(Map<String, Object> map);

	int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}