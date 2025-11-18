package kr.wise.meta.symn.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.wise.meta.symn.service.StructureMapper;
import kr.wise.meta.symn.service.StructureService;
import kr.wise.meta.symn.service.StructureVO;

@Service("StructureService")
public class StructureServiceImpl implements StructureService {

	@Inject
	StructureMapper structureMapper;
	
	@Override
	public List<StructureVO> getDmnStructure(StructureVO search) {
		return structureMapper.getDmnStructure(search);
	}
	
	@Override
	public List<StructureVO> getItmStructure(StructureVO search) {
		return structureMapper.getItmStructure(search);
	}
	
	@Override
	public List<StructureVO> getMdlStructure(StructureVO search) {
		return structureMapper.getMdlStructure(search);
	}

	@Override
	public List<StructureVO> selectDmnInitList(StructureVO search) {
		return structureMapper.selectDmnInitList(search);
	}

	@Override
	public List<StructureVO> selectItmInitList(StructureVO search) {
		return structureMapper.selectItmInitList(search);
	}

	@Override
	public List<StructureVO> selectMdlInitList(StructureVO search) {
		return structureMapper.selectMdlInitList(search);
	}

	@Override
	public List<StructureVO> getCodeStructuer(StructureVO search) {
		return structureMapper.getCodeStructuer(search);
	}

	@Override
	public List<StructureVO> selectCodeInitList(StructureVO search) {
		return structureMapper.selectCodeInitList(search);
	}

}
