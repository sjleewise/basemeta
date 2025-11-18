package kr.wise.advisor.prepare.stat.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.advisor.prepare.stat.service.StatMapper;
import kr.wise.advisor.prepare.stat.service.StatService;
import kr.wise.advisor.prepare.stat.service.StatVo;
import kr.wise.commons.cmm.CommonVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service("statService")
public class StatServiceImpl implements StatService {
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	@Inject
	private StatMapper statMapper;

	@Override
	public List<StatVo> getProfStatList(StatVo search) {
		// TODO Auto-generated method stub
		return statMapper.selectProfStatList(search);
	}

	@Override
	public List<StatVo> getBrStatList(StatVo search) {
		// TODO Auto-generated method stub
		return statMapper.selectBrStatList(search);
	}

	@Override
	public List<StatVo> getOtlStatList(StatVo search) {
		// TODO Auto-generated method stub
		return statMapper.selectOtlStatList(search);
	}

	@Override
	public List<StatVo> getProfStatDtl(StatVo search) {
		// TODO Auto-generated method stub
		return statMapper.selectProfStatDtl(search);
	}

	@Override
	public List<StatVo> getBrStatDtl(StatVo search) {
		// TODO Auto-generated method stub
		return statMapper.selectBrStatDtl(search);
	}

	@Override
	public List<StatVo> getOtlStatDtl(StatVo search) {
		// TODO Auto-generated method stub
		return statMapper.selectOtlStatDtl(search);
	}

	@Override
	public List<StatVo> getClstStatList(StatVo search) {
		// TODO Auto-generated method stub
		return statMapper.selectClstStatList(search);
	}

	@Override
	public List<StatVo> getMtchStatList(StatVo search) {
		// TODO Auto-generated method stub
		return statMapper.selectMtchStatList(search);
	}

	@Override
	public List<StatVo> getClstStatDtl(StatVo search) {
		// TODO Auto-generated method stub
		return statMapper.selectClstStatDtl(search);
	}

	@Override
	public List<StatVo> getMtchStatDtl(StatVo search) {
		// TODO Auto-generated method stub
		return statMapper.selectMtchStatDtl(search);
	}
}
