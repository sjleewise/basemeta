/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ScheduleLogServiceImpl.java
 * 2. Package : kr.wise.commons.damgmt.schedule.service.impl
 * 3. Comment :
 * 4. 작성자  : kchoi
 * 5. 작성일  : 2014. 4. 24. 오후 1:20:23
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    kchoi : 2014. 4. 24. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.schedule.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.damgmt.dmnginfo.service.WaaDmng;
import kr.wise.commons.damgmt.schedule.service.ScheduleLogService;
import kr.wise.commons.damgmt.schedule.service.WamShdLogMapper;
import kr.wise.commons.damgmt.schedule.service.WamShdLogVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ScheduleLogServiceImpl.java
 * 3. Package  : kr.wise.commons.damgmt.schedule.service.impl
 * 4. Comment  :
 * 5. 작성자   : kchoi
 * 6. 작성일   : 2014. 4. 24. 오후 1:20:23
 * </PRE>
 */
@Service("ScheduleLogService")
public class ScheduleLogServiceImpl implements  ScheduleLogService{
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamShdLogMapper mapper;

	@Override
	public List<WamShdLogVO> getScheduleLogList(WamShdLogVO search) {
		return mapper.selecLogtList(search);
	}
	@Override
	public List<WamShdLogVO> getScheduleJobList(WamShdLogVO search) {
		return mapper.selecJobtList(search);
	}
	@Override
	public WamShdLogVO selectShdDetail(String shdId) {
		return mapper.selectByPrimaryKey(shdId);
	}
	@Override
	public List<WamShdLogVO> getLogLst(WamShdLogVO search) {
		return mapper.selectLogLst(search);
	}
	@Override
	public WamShdLogVO selectAnaKndCd(WamShdLogVO search) {
		return mapper.selectAnaKndCd(search);
	}
	/** yeonho */
	@Override
	public int deleteLogList(ArrayList<WamShdLogVO> list) {
		int result = 0;
		for (WamShdLogVO record : list) {
			String id = record.getShdId();
			if (id != null && !"".equals(id)) {
				//WaaDmng.setIbsStatus("D");
				record.setExpDtm(null);
				result +=mapper.deleteShdLog(record);
			}
		}	
		return result;
	}
}