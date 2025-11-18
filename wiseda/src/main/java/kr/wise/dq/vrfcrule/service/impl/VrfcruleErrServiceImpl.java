package kr.wise.dq.vrfcrule.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelTblVO;
import kr.wise.dq.vrfcrule.service.VrfcruleErrMapper;
import kr.wise.dq.vrfcrule.service.VrfcruleErrService;
import kr.wise.dq.vrfcrule.service.VrfcruleErrVO;
import kr.wise.dq.vrfcrule.service.VrfcruleVO;
import kr.wise.dq.vrfcrule.sqlgenerator.VrfcSqlGeneratorVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("vrfcruleErrService")
public class VrfcruleErrServiceImpl implements VrfcruleErrService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private VrfcruleErrMapper vrfcMapper;

	public List<VrfcruleErrVO> selectVrfcList(VrfcruleErrVO searchVO) {
		return vrfcMapper.selectVrfcList(searchVO);
	}

	public VrfcruleErrVO selectVrfcDetail(String prfId) {
		return vrfcMapper.selectVrfcDetail(prfId);
	}
	
	public VrfcSqlGeneratorVO getCntSql(String prfId) {
		return vrfcMapper.selectCntSql(prfId);
	}

	public VrfcruleVO getSqlGenDbmsInfoByRuleRelId(String prfId) {
		return vrfcMapper.selectSqlGenDbmsInfoByRuleRelId(prfId);
	}

	public VrfcruleErrVO selectMstrByPrfId(VrfcruleErrVO searchVO) {
		return vrfcMapper.selectMstrByPrfId(searchVO);
	}

	@Override
	public WamPrfRelTblVO getPrfPT01Dtl(String prfId) {
		return vrfcMapper.selectPrfPT01Dtl(prfId);
	}

	@Override
	public List<WamPrfRelColVO> getPrfPT01RelColLst(String prfId) {
		return vrfcMapper.selectPrfPT01RelColLst(prfId);
	}

	@Override
	public WamPrfMstrVO getSqlGenDbmsInfoByPrfId(String prfId) {
		return vrfcMapper.selectSqlGenDbmsInfoByPrfId(prfId);
	}

}