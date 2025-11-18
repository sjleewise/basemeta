package kr.wise.sysinf.eai.service;

import kr.wise.commons.cmm.annotation.Mapper;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface EaiMapper {

	int insertWAEDMNCDVAL(@Param("rqstNo") String rqstNo);

	int insertWAEDMN(@Param("rqstNo") String rqstNo);

	int insertWAECDVAL(@Param("rqstNo") String rqstNo);
  
	int insertWAESTDERRMSG(@Param("rqstNo") String rqstNo);
}
