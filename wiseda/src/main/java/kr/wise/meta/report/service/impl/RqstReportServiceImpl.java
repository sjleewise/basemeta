package kr.wise.meta.report.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.wise.meta.report.service.RqstReportMapper;
import kr.wise.meta.report.service.RqstReportService;
import kr.wise.meta.report.service.RqstReportVO;

@Service("RqstReportService")
public class RqstReportServiceImpl implements RqstReportService{

	@Inject
	private RqstReportMapper rqstReportMapper;
	
	@Override
	public List<RqstReportVO> getPeriodRqst(RqstReportVO search) {
		return rqstReportMapper.getPeriodRqst(search);
	}

	@Override
	public List<RqstReportVO> getDdlRqstPeriod(RqstReportVO search) {
		return rqstReportMapper.getDdlRqstPeriod(search);
	}

	@Override
	public List<RqstReportVO> getDdlRqstBiz(RqstReportVO search) {
		return rqstReportMapper.getDdlRqstBiz(search);
	}

	@Override
	public List<RqstReportVO> getDdlRqstDept(RqstReportVO search) {
		return rqstReportMapper.getDdlRqstDept(search);
	}

	@Override
	public List<RqstReportVO> getDdlRqstDb(RqstReportVO search) {
		return rqstReportMapper.getDdlRqstDb(search);
	}

	/** yeonho */
	@Override
	public List<RqstReportVO> getRqstStatusList() {
		return rqstReportMapper.getRqstStatusList();
	}

}
