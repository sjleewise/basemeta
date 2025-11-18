package kr.wise.commons.cmm.service;

import java.util.List;

public interface ErrLogService {

	int insertErrLog(WaaErrLog vo);
	
	public List<WaaErrLog> selectErrLogList(WaaErrLog search);
}



