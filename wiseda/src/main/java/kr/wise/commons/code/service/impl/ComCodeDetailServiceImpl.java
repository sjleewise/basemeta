package kr.wise.commons.code.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.ComCodeDetailMapper;
import kr.wise.commons.code.service.ComCodeDetailService;
import kr.wise.commons.code.service.ComCodeDetailVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("comCodeDetailService")
public class ComCodeDetailServiceImpl implements ComCodeDetailService{
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private ComCodeDetailMapper comDetailCodemapper;

	public List<ComCodeDetailVO> selectComCodeDetailList(ComCodeDetailVO searchVO) {
		return comDetailCodemapper.selectComDetailCodeList(searchVO);
	}

	public ComCodeDetailVO selectComCodeDetailInfo(ComCodeDetailVO searchVO) {
		
		return comDetailCodemapper.selectByPrimaryKey(searchVO.getCodeId(), searchVO.getCode());
	}

	public int saveComCodeDetail(ComCodeDetailVO saveVO, String saction) {
		if("I".equals(saction)) {
			return comDetailCodemapper.insertSelective(saveVO);
		} else if ("U".equals(saction)) {
			return comDetailCodemapper.updateByPrimaryKeySelective(saveVO);
		} else {
			return 0;
		}
	}

	public int deleteComCodeDetail(ComCodeDetailVO deleteVO) {
		String[] ids = deleteVO.getCodeId().split(",");
		String[] codes = deleteVO.getCode().split(",");
		
		Map<String, String[]> param = new HashMap<String, String[]>();
		param.put("ids", ids);
		param.put("codes", codes);
		
		return comDetailCodemapper.deleteComDetailCOde(param);
	}

}
