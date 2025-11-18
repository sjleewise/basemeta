package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqMsgMapper extends CommonRqstMapper {
    int insertSelective(WaqMsg record);
    
	int deleteByPrimaryKey(WaqMsg saveVo);

    int insert(WaqMsg record);

	/** @param search
	/** @return  */
	List<WaqMsg> selectMsgRqstListbyMst(WaqMstr search);
	
		/** @param searchVo
	/** @return  */
	WaqMsg selectMsgRqstDetail(WaqMsg searchVo);
	

    WaqMsg selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno);
    

    int updateByPrimaryKeySelective(WaqMsg record);

    int updateByPrimaryKey(WaqMsg record);




	/** @param rqstNo insomnia */
	int updateCheckInit(String rqstNo);

	/** @param checkmap lsi */
	int checkDupMsg(Map<String, Object> checkmap);
	
	/** @param checkmap lsi */
	int checkNotExistMsg(Map<String, Object> checkmap);


	/** @param checkmap lsi */
	int checkNotChgData(Map<String, Object> checkmap);

	/** @param reqmst
	/** @param list
	/** @return lsi */
	ArrayList<WaqMsg> selectwamlist(@Param("reqmst") WaqMstr reqmst, @Param("list") List<WaqMsg> list);

	/** @param savevo
	/** @return insomnia */
	int updatervwStsCd(WaqMsg savevo);

	/** @param rqstno
	/** @return insomnia */
	List<WaqMsg> selectWaqC(@Param("rqstNo") String rqstno);

	/** @param savevo insomnia */
	int updateidByKey(WaqMsg savevo);


	/** @param checkmap lsi */
	int checkRequestMsg(Map<String, Object> checkmap);

	/** @param checkmap insomnia */
	int checkDataType(Map<String, Object> checkmap);
	
		/** @param checkmap insomnia */
	int checkUseYn(Map<String, Object> checkmap);
	
	//메시지코드 길이 체크
	int checkLenMsg(Map<String, Object> checkmap);
	
	//메시지코드 명명규칙 체크
	int checkMsgCdName(Map<String,Object> checkmap);
}