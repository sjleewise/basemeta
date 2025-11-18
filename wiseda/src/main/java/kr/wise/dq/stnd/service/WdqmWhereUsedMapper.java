package kr.wise.dq.stnd.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WdqmWhereUsedMapper {
	List<WdqmWhereUsed> selectDmnWhereUsedList(@Param("dmnId") String dmnId);
	
	//List<WdqmWhereUsed> selectStwdWhereUsedList(@Param("stwdId") String stwdId);
	
	List<WdqmWhereUsed> selectStwdWhereUsedList(WdqmStwd vo);
	
	List<WdqmWhereUsed> selectSditmWhereUsedList(@Param("sditmId") String sditmId);
}
