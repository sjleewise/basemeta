package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.stnd.service.WamCdValTsf;
import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamCodeTsfMapper{
  
	List<WamCdValTsf> selectWamDmnValueList(String dmnLnm);
    List<WamCdValTsf> selectWamDmnListTsf(WamCdValTsf searchVo);
	
	List<WamCdValTsf> selectCdTsfWamList(WamCdValTsf searchVo);
	
	WamCdValTsf selectCdTsfWamDetail(WamCdValTsf searchVo);
	
	List<WamCdValTsf> selectCdTsfWamChg(WamCdValTsf searchVo);
}