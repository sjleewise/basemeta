package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaqCodeTsfMapper extends CommonRqstMapper {
  
	int insertSelective(WaqCdValTsf saveVo);
	
	List<WaqCdValTsf> selectRqstList(WaqMstr reqmst);
	

	
	WaqCdValTsf selectRqstDetail(WaqCdValTsf searchVo);
	
	int updateCheckInit(String rqstNo);
	
	int updateTgtDb(String rqstNo);
	
	int deleteRqstList(WaqCdValTsf saveVo);
	
	int checkNotChgData(Map<String, Object> map);
	
	int checkDupCdTsf(Map<String, Object> map);
	
	int checkDupCdTsfOtherRqst(Map<String, Object> map);
	
    int checkUppCdValExists(Map<String, Object> map);
	
    int checkLowerCdValExists(Map<String, Object> map);
    
    int checkTgtDb(Map<String, Object> map);
    
	int updatervwStsCd(WaqCdValTsf saveVo);

	int insertCodeByDmn(WaqCdValTsf saveVo);
 
	int insertDelCodeByDmn(WaqMstr reqmst);
	
    int insertWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
}