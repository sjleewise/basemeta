package kr.wise.meta.stnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamWhereUsedMapper {
	List<WamWhereUsed> selectDmnWhereUsedList(@Param("dmnId") String dmnId);
	
	//List<WamWhereUsed> selectStwdWhereUsedList(@Param("stwdId") String stwdId);
	
	List<WamWhereUsed> selectStwdWhereUsedList(WamStwd vo);
	
	List<WamWhereUsed> selectSditmWhereUsedList(@Param("sditmId") String sditmId);
}
