package kr.wise.meta.symn.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.wise.meta.symn.service.ColStructureMapper;
import kr.wise.meta.symn.service.ColStructureService;
import kr.wise.meta.symn.service.ColStructureVO;

@Service("ColStructureService")
public class ColStructureServiceImpl implements ColStructureService {
	
	@Inject
	ColStructureMapper colStructureMapper;
	
	
	@Override
	public List<ColStructureVO> getColStructuer(ColStructureVO search) {
		return colStructureMapper.getColStructuer(search);
	}

	@Override
	public List<ColStructureVO> selectColInitList(ColStructureVO search) {
		return colStructureMapper.selectColInitList(search);
	}
	

}
