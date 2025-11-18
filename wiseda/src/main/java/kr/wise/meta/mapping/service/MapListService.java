package kr.wise.meta.mapping.service;

import java.util.List;

public interface MapListService {

	List<MapListVO> getTblMap(MapListVO search);

	List<MapListVO> getColMap(MapListVO search);

	List<MapListVO> getColMapGap(MapListVO search);

	List<MapListVO> getColMapGapDtl(MapListVO search);
	
	List<MapListVO> getCodeMap(MapListVO search);

}
