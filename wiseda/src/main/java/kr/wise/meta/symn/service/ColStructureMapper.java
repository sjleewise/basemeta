package kr.wise.meta.symn.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface ColStructureMapper {

	List<ColStructureVO> getColStructuer(ColStructureVO search);
	
	List<ColStructureVO> selectColInitList(ColStructureVO search);

}
