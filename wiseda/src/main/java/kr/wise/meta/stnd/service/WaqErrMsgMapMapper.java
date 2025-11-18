package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.stnd.service.WaqErrMsgMap;

import org.apache.ibatis.annotations.Param;
@Mapper
public interface WaqErrMsgMapMapper extends CommonRqstMapper{
    int insertSelective(WaqErrMsgMap saveVo);
    
	int deleteByPrimaryKey(WaqErrMsgMap saveVo);

    int insert(WaqErrMsgMap record);

	List<WaqErrMsgMap> selectMsgRqstListbyMst(WaqMstr search);
	
	WaqErrMsgMap selectMsgRqstDetail(WaqErrMsgMap searchVo);
	
    WaqErrMsgMap selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);
    
    int updateByPrimaryKeySelective(WaqErrMsgMap record);

    int updateByPrimaryKey(WaqErrMsgMap record);
    
	ArrayList<WaqErrMsgMap> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") List<WaqErrMsgMap> list);

	int updatervwStsCd(WaqErrMsgMap savevo);

	List<WaqErrMsgMap> selectWaqC(@Param("rqstNo") String rqstno);

	int updateidByKey(WaqErrMsgMap savevo);

	int updateCheckInit(String rqstNo);
	
	

	int checkDupMsg(Map<String, Object> checkmap);
	
	int checkNotExistMsg(Map<String, Object> checkmap);

	int checkNotChgData(Map<String, Object> checkmap);

	int checkRequestMsg(Map<String, Object> checkmap);

	int checkDataType(Map<String, Object> checkmap);
	
	int checkUseYn(Map<String, Object> checkmap);
	
	//메시지코드 길이 체크
	int checkLenMsg(Map<String, Object> checkmap);
	
	//메시지코드 명명규칙 체크
	int checkMsgCdName(Map<String,Object> checkmap);
}