package kr.wise.meta.report.service;

import java.util.List;

import kr.wise.meta.symn.service.StructureVO;

public interface RqstReportService {

	public List<RqstReportVO> getPeriodRqst(RqstReportVO search);

	public List<RqstReportVO> getDdlRqstPeriod(RqstReportVO search);

	public List<RqstReportVO> getDdlRqstBiz(RqstReportVO search);

	public List<RqstReportVO> getDdlRqstDept(RqstReportVO search);

	public List<RqstReportVO> getDdlRqstDb(RqstReportVO search);

	/** @return yeonho */
	public List<RqstReportVO> getRqstStatusList();

}
