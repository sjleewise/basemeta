/**
 * 0. Project  : WISE DA �봽濡쒖젥�듃
 *
 * 1. FileName : DataPatternServiceImpl.java
 * 2. Package : kr.wise.commons.damgmt.schedule.service.impl
 * 3. Comment :
 * 4. �옉�꽦�옄  : kchoi
 * 5. �옉�꽦�씪  : 2014. 5. 2. �삤�썑 1:20:23
 * 6. 蹂�寃쎌씠�젰 :
 *                    �씠由�     : �씪�옄          : 洹쇨굅�옄猷�   : 蹂�寃쎈궡�슜
 *                   ------------------------------------------------------
 *                    kchoi : 2014. 5. 2. :            : �떊洹� 媛쒕컻.
 */
package kr.wise.dq.report.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.report.service.DataPatternMapper;
import kr.wise.dq.report.service.DataPatternService;
import kr.wise.dq.report.service.DataPatternVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DataPatternServiceImpl.java
 * 3. Package  : kr.wise.commons.damgmt.schedule.service.impl
 * 4. Comment  :
 * 5. �옉�꽦�옄   : kchoi
 * 6. �옉�꽦�씪   : 2014. 5. 2. �삤�썑 1:20:23
 * </PRE>
 */
@Service("DataPatternService")
public class DataPatternServiceImpl implements  DataPatternService{
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private DataPatternMapper mapper;

	@Override
	public List<DataPatternVO> getDataPattern(DataPatternVO search) {
		String objDate = search.getObjDate();
		if (StringUtils.hasText(objDate))		
			return mapper.dptrnList(search);
		else return mapper.dptrnListNoDate(search);
	}

	@Override
	public DataPatternVO DptrnHeaderInit(DataPatternVO search) {
		return mapper.dptrnHeader(search);
	}

	@Override
	public DataPatternVO DptrnHeaderText(DataPatternVO search) {
		return mapper.DptrnHeaderText(search);
	}

	@Override
	public DataPatternVO getPrfAnaResDtl(DataPatternVO search) {
		return mapper.selectPrfAnaResDtl(search);
	}

	@Override
	public DataPatternVO getBrAnaResDtl(DataPatternVO search) {
		return mapper.selectBrAnaResDtl(search);
	}

	@Override
	public List<DataPatternVO> getPkDataPattern(DataPatternVO search) {
		return mapper.selectPkDptrnList(search);
	}

	@Override
	public DataPatternVO PkDptrnHeaderText(DataPatternVO search) {
		return mapper.PkDptrnHeaderText(search);
	}

	@Override
	public String getPrfcScript(DataPatternVO search) {
		// TODO Auto-generated method stub
		return mapper.selectPrfcScript(search);
	}

	@Override
	public int prfcScriptSave(DataPatternVO search) {
		// TODO Auto-generated method stub
		return  mapper.updatePrfcScript(search);
	}

}