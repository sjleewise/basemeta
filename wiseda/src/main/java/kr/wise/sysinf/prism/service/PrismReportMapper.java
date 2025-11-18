package kr.wise.sysinf.prism.service;

import java.util.List;

public interface PrismReportMapper {

	List<PrismReportVo> selectReportbyOrgan(String organName);

	int insertReportbyOrgan(PrismReportVo apivo);

	List<PrismReportVo> selectReport();

	List<PrismReportVo> selectNewReportbyOrgan(String organName);

	int insertReportDetail(PrismReportVo report);
	
	

}
