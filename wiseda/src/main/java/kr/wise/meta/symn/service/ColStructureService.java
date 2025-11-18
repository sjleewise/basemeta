package kr.wise.meta.symn.service;

import java.util.List;

import javax.inject.Inject;

public interface ColStructureService {
	
	List<ColStructureVO> getColStructuer(ColStructureVO search);

	List<ColStructureVO> selectColInitList(ColStructureVO search);
}
