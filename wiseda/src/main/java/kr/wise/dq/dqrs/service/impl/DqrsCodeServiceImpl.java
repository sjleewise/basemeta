package kr.wise.dq.dqrs.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.code.service.CodeListVo;
import kr.wise.dq.dqrs.service.DqrsCodeMapper;
import kr.wise.dq.dqrs.service.DqrsCodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("dqrsCodeService")
public class DqrsCodeServiceImpl implements DqrsCodeService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private DqrsCodeMapper dqrsCodeMapper;
	
	//코드 리스트 조회 쿼리(기존 CodeList에 추가 하지 않고 신규 모듈에 추가(기존 소스 영향 없게 하기 위함.))
	@Override
	public List<CodeListVo> getPubSditmConnTrgId() {
		// TODO Auto-generated method stub
		return dqrsCodeMapper.selectPubSditmConnTrgId();
	}

	@Override
	public List<CodeListVo> getPubDmnConnTrgId() {
		// TODO Auto-generated method stub
		return dqrsCodeMapper.selectPubDmnConnTrgId();
	}

	@Override
	public List<CodeListVo> getAllDbms() {
		// TODO Auto-generated method stub
		return dqrsCodeMapper.selectAllDbms();
	}

	@Override
	public List<CodeListVo> getAllDbmsSchId() {
		// TODO Auto-generated method stub
		return dqrsCodeMapper.selectAllDbmsSchId();
	}

	@Override
	public List<CodeListVo> getGovTblDbms() {
		// TODO Auto-generated method stub
		return dqrsCodeMapper.selectGovTblDbms();
	}

	@Override
	public List<CodeListVo> getGovTblSchId() {
		// TODO Auto-generated method stub
		return dqrsCodeMapper.selectGovTblSchId();
	}
	

}
