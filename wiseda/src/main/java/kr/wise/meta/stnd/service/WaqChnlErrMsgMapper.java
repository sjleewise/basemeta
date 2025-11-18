package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
@Mapper
public interface WaqChnlErrMsgMapper extends CommonRqstMapper{
    int insertSelective(WaqChnlErrMsg record);
    
	int deleteByPrimaryKey(WaqChnlErrMsg saveVo);

    int insert(WaqChnlErrMsg record);

	List<WaqChnlErrMsg> selectMsgRqstListbyMst(WaqMstr search);
	
	WaqChnlErrMsg selectMsgRqstDetail(WaqChnlErrMsg searchVo);
	
    WaqChnlErrMsg selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);
    
    int updateByPrimaryKeySelective(WaqChnlErrMsg record);

    int updateByPrimaryKey(WaqChnlErrMsg record);
    
	ArrayList<WaqChnlErrMsg> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") List<WaqChnlErrMsg> list);

	int updatervwStsCd(WaqChnlErrMsg savevo);

	List<WaqChnlErrMsg> selectWaqC(@Param("rqstNo") String rqstno);

	int updateidByKey(WaqChnlErrMsg savevo);

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