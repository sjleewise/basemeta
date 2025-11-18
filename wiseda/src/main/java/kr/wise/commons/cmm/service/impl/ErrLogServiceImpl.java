package kr.wise.commons.cmm.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.service.ErrLogService;
import kr.wise.commons.cmm.service.WaaErrLog;
import kr.wise.commons.cmm.service.WaaErrLogMapper;

import org.springframework.stereotype.Service;

@Service("errLogService")
public class ErrLogServiceImpl implements ErrLogService {

	@Inject
	WaaErrLogMapper waaErrLogMapper;
	
	
	@Override
	public int insertErrLog(WaaErrLog vo) {
		return waaErrLogMapper.insertErrLog(vo);
	
	}

	@Override
	public List<WaaErrLog> selectErrLogList(WaaErrLog search) {
		return waaErrLogMapper.selectErrLog(search);
	}

	

}
