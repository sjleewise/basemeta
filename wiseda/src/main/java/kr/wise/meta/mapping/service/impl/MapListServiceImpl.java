package kr.wise.meta.mapping.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.mapping.service.MapListMapper;
import kr.wise.meta.mapping.service.MapListService;
import kr.wise.meta.mapping.service.MapListVO;

import org.springframework.stereotype.Service;

@Service("MapListService")
public class MapListServiceImpl implements MapListService {

	@Inject
	MapListMapper mapListMapper;

	@Override
	public List<MapListVO> getTblMap(MapListVO search) {
		return mapListMapper.getTblMap(search);
	}

	@Override
	public List<MapListVO> getColMap(MapListVO search) {
		return mapListMapper.getColMap(search);
	}
	
	@Override
	public List<MapListVO> getColMapGap(MapListVO search) {
		return mapListMapper.getColMapGap(search);
	}
	
	@Override
	public List<MapListVO> getColMapGapDtl(MapListVO search) {
		return mapListMapper.getColMapGapDtl(search);
	}

	@Override
	public List<MapListVO> getCodeMap(MapListVO search) {
		return mapListMapper.getCodeMap(search);
	}
}