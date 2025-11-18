package kr.wise.sysinf.prism.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wise.rest.client.ResearchList;
import kr.wise.rest.client.ResearchVo;
import kr.wise.sysinf.prism.service.PrismOrgan;
import kr.wise.sysinf.prism.service.PrismOrganMapper;
import kr.wise.sysinf.prism.service.PrismReportList;
import kr.wise.sysinf.prism.service.PrismReportMapper;
import kr.wise.sysinf.prism.service.PrismReportVo;
import kr.wise.sysinf.prism.service.PrismResearchDtlMapper;
import kr.wise.sysinf.prism.service.PrismResearchMst;
import kr.wise.sysinf.prism.service.PrismResearchMstMapper;
import kr.wise.sysinf.prism.service.PrismService;
import kr.wise.sysinf.prism.service.ReportDetail;
import kr.wise.sysinf.prism.service.ResearchDetail;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Service("prismService")
public class PrismServiceImpl implements PrismService {

	private final Logger log = LoggerFactory.getLogger(getClass());


//	@Inject
	private PrismOrganMapper organmapper;


//	@Inject
	private PrismResearchMstMapper prismresearchmapper;

//	@Inject
	private PrismResearchDtlMapper prismresdetailmapper;

//	@Inject
	private PrismReportMapper prismreportmapper;

//	@Inject
	private SqlSessionFactory nkissqlSessionFactory;

	@Override
	public List<PrismOrgan> getOrganList() {
		return organmapper.selectList();
	}


	@Override
	public int regResearchList(PrismOrgan orgvo, ResearchList reslist) {
		int result = 0;
//		SqlSession sqlsession =  nkissqlSessionFactory.openSession(ExecutorType.BATCH, false);
//
//		PrismResearchMstMapper prismresearchmapper1 = sqlsession.getMapper(PrismResearchMstMapper.class);

		//기조에 등록된 해당기관별 연구보고서 목록를 가져온다.
		List<PrismResearchMst> reslistold = prismresearchmapper.selectResearchbyOrgan(orgvo.getOrganName());
		Map<String, String> researchmap = new HashMap<String, String>();

		//기존 연구보고서 목록의 "과제ID|순번"으로 맵형태의 키를 만든다.
		for (PrismResearchMst vo : reslistold) {
			String key = vo.getResearchId();
			String value = vo.getResearchName();
			researchmap.put(key, value);
		}

		//기관별 과제목록을 가져온다.
		List<ResearchVo> volist = reslist.getResearch();
		for (ResearchVo researchVo : volist) {
			String key = researchVo.getResearchID();
			if(!researchmap.containsKey(key)) {
				//과제를 인서트 한다.
				result = prismresearchmapper.insertResearch(researchVo);
			}
		}

//		sqlsession.commit();

		return result;
	}


	@Override
	public List<PrismOrgan> getOrganResList() {
		return organmapper.selectOrganResList();
	}


	@Override
	public List<PrismResearchMst> getResearchListbyOrganName(String OrganName) {
		return prismresearchmapper.selectByOrgan(OrganName);
	}


	@Override
	public int regResearchDetail(List<ResearchDetail> resdetail) {
		int result = 0;

		for (ResearchDetail vo : resdetail) {
			result = prismresdetailmapper.insertResearchDetail(vo);
		}

//		resdetail.getResearch()

		return 0;
	}


	@Override
	public List<PrismOrgan> getOrganListbyNew() {
		return organmapper.selectOrganListbyNew();
	}


	@Override
	public int regNewResearchDetail(ResearchDetail resdetail) {
		return prismresdetailmapper.insertResearchDetail(resdetail);
	}


	@Override
	public int regReportList(PrismOrgan orgvo, PrismReportList reslist) {

		int result = 0;

		//기조에 등록된 해당기관별 연구보고서 목록를 가져온다. (하지만 프리즘 과제목록에는 없지만 리포트가 있는 경우 발생... ==> 리포트 목록 전체 리스트 조회로 변경함...)
//		List<PrismReportVo> reportlist = prismreportmapper.selectReportbyOrgan(orgvo.getOrganName());
		List<PrismReportVo> reportlist = prismreportmapper.selectReport();
		Map<String, String> reportmap = new HashMap<String, String>();

		//기존 연구보고서 목록의 "과제ID|순번"으로 맵형태의 키를 만든다.
		for (PrismReportVo vo : reportlist) {
			String key = vo.getResearchID() + "|" + vo.getSeqNo();
			String value = vo.getReportTitle();
			reportmap.put(key, value);
		}


		//기존 연구보고서 목록에 없는 경우에만 추가한다.
		for (PrismReportVo apivo : reslist.getReport()) {
			if (apivo.getSeqNo() == null || "".equals(apivo.getSeqNo()) ) {
				apivo.setSeqNo("NULL");
			}
			String key = apivo.getResearchID() + "|" + apivo.getSeqNo();
			log.debug("compare key:{}", key);
			if(!reportmap.containsKey(key)) {
				//연구보고서를 DB에 등록한다.
				result += prismreportmapper.insertReportbyOrgan(apivo);
			}
		}

		return result;
	}


	@Override
	public List<PrismOrgan> getOrganListbyNewReport() {
		return organmapper.selectOrganListbyNewReport();
	}


	@Override
	public List<PrismReportVo> getReportListbyOrgan(String organName) {
		return prismreportmapper.selectNewReportbyOrgan(organName);
	}


	@Override
	public int regReportDetail(ReportDetail repdetail) {
		PrismReportVo regvo = repdetail.getReport();
		if (regvo.getSeqNo() == null || "".equals(regvo.getSeqNo()) ) {
			regvo.setSeqNo("NULL");
		}

		return prismreportmapper.insertReportDetail(regvo);
	}

}
