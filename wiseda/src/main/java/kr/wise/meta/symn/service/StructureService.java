package kr.wise.meta.symn.service;

import java.util.List;
import kr.wise.meta.symn.service.StructureVO;

public interface StructureService {
	
	List<StructureVO> getDmnStructure(StructureVO search);

	List<StructureVO> getItmStructure(StructureVO search);

	List<StructureVO> getMdlStructure(StructureVO search);

	List<StructureVO> selectDmnInitList(StructureVO search);

	List<StructureVO> selectItmInitList(StructureVO search);

	List<StructureVO> selectMdlInitList(StructureVO search);

	List<StructureVO> getCodeStructuer(StructureVO search);

	List<StructureVO> selectCodeInitList(StructureVO search);
}
