package kr.wise.meta.aocd.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.aocd.service.PusAocdMapper;
import kr.wise.meta.aocd.service.PusAocdService;
import kr.wise.meta.aocd.service.PusAocdVO;

import org.springframework.stereotype.Service;


@Service("PusAocdService")
public class PusAocdServiceImpl implements PusAocdService {

	@Inject
	private PusAocdMapper pusAocdMapper;
	
	@Override
	public List<PusAocdVO> getStndWordPusAocd(PusAocdVO search) {
		return pusAocdMapper.getStndWordPusAocd(search);
	}

	@Override
	public List<PusAocdVO> getDmnPusAocd(PusAocdVO search) {
		return pusAocdMapper.getDmnPusAocd(search);
	}

	@Override
	public List<PusAocdVO> getItmPusAocd(PusAocdVO search) {
		return pusAocdMapper.getItmPusAocd(search);
	}

	@Override
	public List<PusAocdVO> getTblPusAocd(PusAocdVO search) {
		return pusAocdMapper.getTblPusAocd(search);
	}

	@Override
	public List<PusAocdVO> getTblPusAocdWhereused(PusAocdVO search) {
		// TODO Auto-generated method stub
		return pusAocdMapper.getTblPusAocdWhereused(search);
	}


}
