package kr.wise.sysinf.prism.service;

import java.util.List;

import kr.wise.rest.client.ResearchList;

public interface PrismService {
	
	List<PrismOrgan> getOrganList();

	int regResearchList(PrismOrgan orgvo, ResearchList reslist);

	List<PrismOrgan> getOrganResList();

	List<PrismResearchMst> getResearchListbyOrganName(String OrganName);

	int regResearchDetail(List<ResearchDetail> resdtllist);

	List<PrismOrgan> getOrganListbyNew();

	int regNewResearchDetail(ResearchDetail resdetail);

	int regReportList(PrismOrgan orgvo, PrismReportList reslist);

	List<PrismOrgan> getOrganListbyNewReport();

	List<PrismReportVo> getReportListbyOrgan(String organName);

	int regReportDetail(ReportDetail repdetail);

}
