package kr.wise.meta.ddl.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamDdlPartMapper {
	WaqDdlPart selectDdlPartDtlInfo(@Param("ddlPartId")String ddlPartId, @Param("rqstNo")String rqstNo);
	
	List<WaqDdlPart> selectDdlPartLst(WaqDdlPart searchVO);
	
	List<WaqDdlPart> selectDdlPartHistLst(WaqDdlPart searchVO);
	
	List<WaqDdlPartMain> selectDdlPartMainList(WaqDdlPart searchVO);
	
	List<WaqDdlPartSub> selecDdlPartSubList(WaqDdlPart searchVO);

	int updateDdlPartRqstPrc(WamDdlTbl record);
		
}