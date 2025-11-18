package kr.wise.commons.cmm.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WaaErrLogMapper {
	
	int insertErrLog(WaaErrLog vo);
	
	List<WaaErrLog> selectErrLog(WaaErrLog vo);

}
