package kr.wise.meta.ddl.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.ddl.service.DdlPartService;
import kr.wise.meta.ddl.service.WamDdlPartMapper; 
import kr.wise.meta.ddl.service.WaqDdlPart;
import kr.wise.meta.ddl.service.WaqDdlPartMain;
import kr.wise.meta.ddl.service.WaqDdlPartSub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("DdlPartService")
public class DdlPartServiceImpl implements DdlPartService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WamDdlPartMapper ddlPartMapper;

	@Override
	public WaqDdlPart getDdlPartDtlInfo(String ddlPartId, String rqstNo) {
		return ddlPartMapper.selectDdlPartDtlInfo(ddlPartId, rqstNo);
	}

	@Override
	public List<WaqDdlPart> getDdlPartLst(WaqDdlPart searchVO) {
		return ddlPartMapper.selectDdlPartLst(searchVO);
	}
	
	@Override
	public List<WaqDdlPart> getDdlPartHistLst(WaqDdlPart searchVO) {
		return ddlPartMapper.selectDdlPartHistLst(searchVO);
	}

	@Override
	public List<WaqDdlPartMain> getDdlPartMainList(WaqDdlPart search) {
		return ddlPartMapper.selectDdlPartMainList(search);
	}

	@Override
	public List<WaqDdlPartSub> getDdlPartSubList(WaqDdlPart search) {
		return ddlPartMapper.selecDdlPartSubList(search);
	}
	
	

}
