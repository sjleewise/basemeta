package kr.wise.advisor.prepare.otlquality.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.advisor.prepare.otlquality.service.OtlAlgQltyVo;
import kr.wise.advisor.prepare.otlquality.service.OtlQltyMapper;
import kr.wise.advisor.prepare.otlquality.service.OtlQltyService;
import kr.wise.advisor.prepare.otlquality.service.OtlQltyVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("otlQltyService")
public class OtlQltyServiceImpl implements OtlQltyService {
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private OtlQltyMapper otlQltyMapper;
	
	
	public List<OtlQltyVo> getDbmsQltyList(OtlQltyVo search) {
		// TODO Auto-generated method stub
		logger.debug("진단대상별 품질현황 OtlQltyVo >>> \n" + search);
		
		return otlQltyMapper.selectDbmsQltyList(search);
	}


	@Override
	public List<OtlQltyVo> getDbmsQltyPie(OtlQltyVo search) {
		// TODO Auto-generated method stub
		logger.debug("진단대상별 품질현황 OtlQltyVo >>> \n" + search);
		
		return otlQltyMapper.selectDbmsQltyPie(search);
	}


	@Override
	public List<OtlQltyVo> getTblQltyList(OtlQltyVo search) {
		// TODO Auto-generated method stub
		return otlQltyMapper.selectTblQltyList(search);
	}


	@Override
	public List<OtlQltyVo> getTblQltyPie(OtlQltyVo search) {
		// TODO Auto-generated method stub
		return otlQltyMapper.selectTblQltyPie(search);
	}


	@Override
	public List<OtlQltyVo> getColQltyList(OtlQltyVo search) {
		// TODO Auto-generated method stub
		return otlQltyMapper.selectColQltyList(search);
	}


	@Override
	public List<OtlQltyVo> getColQltyPie(OtlQltyVo search) {
		// TODO Auto-generated method stub
		return otlQltyMapper.selectColQltyPie(search);
	}


	@Override
	public List<OtlAlgQltyVo> getOtlQltyList(OtlAlgQltyVo search) {
		// TODO Auto-generated method stub
		return otlQltyMapper.selectOtlQltyList(search);
	}


	@Override
	public List<OtlAlgQltyVo> getOtlQltyPie(OtlAlgQltyVo search) {
		// TODO Auto-generated method stub
		return otlQltyMapper.selectOtlQltyPie(search);
	}


	@Override
	public List<OtlAlgQltyVo> getOtlQltyBar(OtlAlgQltyVo search) {
		// TODO Auto-generated method stub
		return otlQltyMapper.selectOtlQltyBar(search);
	}

}
