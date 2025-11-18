package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.stnd.service.WaqStdErrMsg;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface WaqStdErrMsgMapper extends CommonRqstMapper{
    int insertSelective(WaqStdErrMsg record);
    
	int deleteByPrimaryKey(WaqStdErrMsg saveVo);

    int insert(WaqStdErrMsg record);

	List<WaqStdErrMsg> selectMsgRqstListbyMst(WaqMstr search);
	
	WaqStdErrMsg selectMsgRqstDetail(WaqStdErrMsg searchVo);
	
    WaqStdErrMsg selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);
    
    int updateByPrimaryKeySelective(WaqStdErrMsg record);

    int updateByPrimaryKey(WaqStdErrMsg record);
    
	ArrayList<WaqStdErrMsg> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") List<WaqStdErrMsg> list);

	int updatervwStsCd(WaqStdErrMsg savevo);

	List<WaqStdErrMsg> selectWaqC(@Param("rqstNo") String rqstno);

	int updateidByKey(WaqStdErrMsg savevo);

	int updateCheckInit(String rqstNo);
	
	int updateErrCd(String rqstNo);
	

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
	
	int checkMsgCdNameMache(Map<String,Object> checkmap);
	
	int checkMsgCdNamecheck(Map<String,Object> checkmap);
}