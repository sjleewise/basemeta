package kr.wise.commons.code.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.SearchVO;
import kr.wise.commons.code.service.ComCodeService;
import kr.wise.commons.code.service.Comtccmmncode;
import kr.wise.commons.code.service.ComtccmmncodeMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("comCodeService")
public class ComCodeServiceImpl implements ComCodeService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private ComtccmmncodeMapper comCodeMapper;

	public List<Comtccmmncode> selectComCodeList(SearchVO searchVO) {

		return comCodeMapper.selectComCodeList(searchVO);
	}

	public Comtccmmncode selectComCodeDetail(Comtccmmncode searchVO) {
		
		return comCodeMapper.selectByPrimaryKey(searchVO.getCodeId());
	}

	public int saveComCode(Comtccmmncode saveVO, String saction) {
		if("I".equals(saction)) {
			return comCodeMapper.insertSelective(saveVO);
		} else if ("U".equals(saction)) {
			return comCodeMapper.updateByPrimaryKeySelective(saveVO);
		} else {
			return 0;
		}
	}

	public int deleteComCode(Comtccmmncode deleteVO) {
		String[] ids = deleteVO.getCodeId().split(",");
		return comCodeMapper.deleteComCode(ids);
	}

}
