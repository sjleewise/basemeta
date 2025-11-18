package kr.wise.dq.gap.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;


import kr.wise.dq.gap.service.StndGapMapper;
import kr.wise.dq.gap.service.StndGapService;
import kr.wise.dq.gap.service.StndGapVO;


@Service("StndGapService")
public class StndGapServiceImpl implements StndGapService {

	@Inject
	StndGapMapper stndGapMapper;

	@Override
	public List<StndGapVO> getDBStndItemGap(StndGapVO search) {
		// TODO Auto-generated method stub
		return stndGapMapper.getDBStndItemGapList(search);
	}

	@Override
	public List<StndGapVO> getDBDmnGap(StndGapVO search) {
		// TODO Auto-generated method stub
		return stndGapMapper.getDBDmnGapList(search);
	}

	@Override
	public List<StndGapVO> getStndItemGap(StndGapVO search) {
		// TODO Auto-generated method stub
		return stndGapMapper.getStndItemGapList(search);
	}

	@Override
	public List<StndGapVO> getDmnGap(StndGapVO search) {
		// TODO Auto-generated method stub
		return stndGapMapper.getDmnGapList(search);
	}

	@Override
	public int saveGapResult(StndGapVO saveVo) throws Exception {
		// TODO Auto-generated method stub
		return stndGapMapper.insertGapResult(saveVo);
	}


	@Override
	public Map<String, String> getDBStndItemGapResult() {
		// TODO Auto-generated method stub
		return stndGapMapper.selectDBStndItemGapResult();
	}
	
	
	@Override
	public Map<String, String> getDBStndItemGapUneqResult() {
		// TODO Auto-generated method stub
		return stndGapMapper.selectDBStndItemGapUneqResult();
	}

	@Override
	public Map<String, String> getDBDmnGapResult() {
		// TODO Auto-generated method stub
		return stndGapMapper.selectDBDmnGapResult();
	}
	
	@Override
	public Map<String, String> getDBDmnGapUneqResult() {
		// TODO Auto-generated method stub
		return stndGapMapper.selectDBDmnGapUneqResult();
	}

	@Override
	public Map<String, String> getStndItemGapResult() {
		// TODO Auto-generated method stub
		return stndGapMapper.selectStndItemGapResult();
	}
	
	@Override
	public Map<String, String> getStndItemGapUneqResult() {
		// TODO Auto-generated method stub
		return stndGapMapper.selectStndItemGapUneqResult();
	}

	@Override
	public Map<String, String> getDmnGapResult() {
		// TODO Auto-generated method stub
		return stndGapMapper.selectDmnGapResult();
	}
	
	@Override
	public Map<String, String> getDmnGapUneqResult() {
		// TODO Auto-generated method stub
		return stndGapMapper.selectDmnGapUneqResult();
	}

	@Override
	public List<StndGapVO> getColDefGapList(StndGapVO search) {
		
		return stndGapMapper.getColDefGapList(search);
	}

	@Override
	public int regColDefGapList(StndGapVO saveVO) {
		return stndGapMapper.regColDefGapList(saveVO);
	}

	@Override
	public List<StndGapVO> getGapResultList(StndGapVO search) {
		// TODO Auto-generated method stub
		return stndGapMapper.selectGapResultList(search);
	}

	@Override
	public List<StndGapVO> getMtaColGapList(StndGapVO search) {
		// TODO Auto-generated method stub
		return stndGapMapper.getMtaColGapList(search);
	}

	@Override
	public int regMtaColGapList(StndGapVO saveVO) {
		// TODO Auto-generated method stub
		return stndGapMapper.regMtaColGapList(saveVO);
	}
	
	
	
}
