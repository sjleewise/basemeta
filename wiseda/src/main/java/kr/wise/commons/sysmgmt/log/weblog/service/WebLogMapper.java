package kr.wise.commons.sysmgmt.log.weblog.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WebLogMapper {

	void logInsertWebLog(WebLog webLog);

	List<WebLog> selectweblogbyuser(WebLog searchVO);

}
