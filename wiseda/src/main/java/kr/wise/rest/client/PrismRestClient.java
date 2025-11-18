package kr.wise.rest.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import kr.wise.sysinf.prism.service.PrismOrgan;
import kr.wise.sysinf.prism.service.PrismReportList;
import kr.wise.sysinf.prism.service.PrismReportVo;
import kr.wise.sysinf.prism.service.PrismResearchMst;
import kr.wise.sysinf.prism.service.PrismService;
import kr.wise.sysinf.prism.service.ReportDetail;
import kr.wise.sysinf.prism.service.ResearchDetail;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;


//@Controller
public class PrismRestClient {

	private final Logger log  = LoggerFactory.getLogger(getClass());

//	@Inject
	private PrismService prismService;

	private final String authKey = "417824cd3a9847d882683da2dbc1aa9f";

	private final String baseUrl = "http://www.prism.go.kr/homepage/openapi/{serviceName}/{authKey}";

	private final RestTemplate client = new RestTemplate();

	private JAXBContext jaxbContext;


	@RequestMapping("/rest/prism/researchList")
	public String getPrismResearchList() {

		int cnt = 0;

		int result = 0;

		//프리즘 기관 리스트 정보를 가져온다.
		List<PrismOrgan> orgids = prismService.getOrganList();

		log.debug("PrismOrgan total count : {}", orgids.size());

		for (PrismOrgan orgvo : orgids) {
			String orgid = orgvo.getOrganId();

			//요청 url을 만든다.
			String url = baseUrl + "/{orgid}";

			//요청 url에 매핑되는 파라미터 생성 (서비스명, 인증키, 기관id)
			Map<String, String> params = new HashMap<String, String>();
			params.put("serviceName", "researchList");
			params.put("authKey", authKey);
			params.put("orgid", orgid);

			log.debug("url:{}\norginfo:{}", url, orgvo);

			//OPEN API 호출
			ResearchList reslist = getResearchList(url, params);
			if(reslist != null) {
				//과제 리스트를 DB에 담는다.
				result += prismService.regResearchList(orgvo, reslist);
			}

			cnt++;
//			if (cnt == 2) break;
		}

		log.debug("research insert totcnt:{}", result);

//		String urlParam = "";
//		String url = buildApiRequestUrl("researchList", urlParam);


		return "/login";
	}

	@RequestMapping("/rest/prism/reportList")
	public String getPrismReportList() {

		int cnt = 0;

		int result = 0;

		//프리즘 기관 리스트 정보를 가져온다. (
		List<PrismOrgan> orgids = prismService.getOrganList();

		log.debug("PrismOrgan total count : {}", orgids.size());

		for (PrismOrgan orgvo : orgids) {
			String orgid = orgvo.getOrganId();

			//요청 url을 만든다.
			String url = baseUrl + "/{orgid}";

			//요청 url에 매핑되는 파라미터 생성 (서비스명, 인증키, 기관id)
			Map<String, String> params = new HashMap<String, String>();
			params.put("serviceName", "reportList");
			params.put("authKey", authKey);
			params.put("orgid", orgid);

			log.debug("url:{}\norginfo:{}", url, orgvo);

			//OPEN API 호출 (기관별 리포트 목록을 가져온다)
			PrismReportList reslist = getReportList(url, params);

			if(reslist != null) {
				//과제 리스트를 DB에 담는다.
				result += prismService.regReportList(orgvo, reslist);
			}

			cnt++;
//			if (cnt == 2) break;
		}

		log.debug("research insert totcnt:{}", result);

//		String urlParam = "";
//		String url = buildApiRequestUrl("researchList", urlParam);


		return "/login";
	}

	@RequestMapping("/rest/prism/researchDetail")
	public String getResDetailbyOrgan() {

		int result = 0;

		//프리즘 기관 리스트 정보를 가져온다. (과제목록에 있지만 과제상가 없는 경우만)
//		List<PrismOrgan> orglist = prismService.getOrganResList();
		List<PrismOrgan> orglist = prismService.getOrganListbyNew();


		log.debug("org cnt:{}", orglist.size());

		for (PrismOrgan orgvo : orglist) {

//			if ("Y".equals(orgvo.getAggrYn())) continue;

			List<PrismResearchMst> reslist = prismService.getResearchListbyOrganName(orgvo.getOrganName());

			log.debug("org name:{}, res cnt:{}", orgvo.getOrganName(), reslist.size());

			//기관별 과제상세 리스트를 가져온다.
//			List<ResearchDetail> resdtllist = getResearchDetailListbyOrgname(reslist);

			//기관별 신규 과제상제 정보를 가져와 DB에 저장한다.
			result += regNewResearchDetailbyOrganame(reslist);

			//과제 상세 리스트를 DB에 담는다.
//			result += prismService.regResearchDetail(resdtllist);

//				log.debug("url:{}\norginfo:{}", url, orgvo);
//			break;
		}

		log.debug("research totcnt:{}", result);

		return "/login";
	}

	@RequestMapping("/rest/prism/reportDetail")
	public String getReportDetailbyOrgan() {
		int result = 0;

		//프리즘 기관 리스트 정보를 가져온다. (리포트목록에 있지만 리포트상세가 없는 경우만)
//		List<PrismOrgan> orglist = prismService.getOrganResList();
		List<PrismOrgan> orglist = prismService.getOrganListbyNewReport();

		log.debug("org cnt:{}", orglist.size());

		for (PrismOrgan orgvo : orglist) {
			List<PrismReportVo> replist = prismService.getReportListbyOrgan(orgvo.getOrganName());

			result += regNewReportDetailbyOrgan(replist);
//			break;
		}
		log.debug("reportdetail insert totcnt:{}", result);

		return "/login";
	}


	private int regNewReportDetailbyOrgan(List<PrismReportVo> replist) {
		int result = 0;

		String url = baseUrl + "/{resid}";

		int cnt = 0;


		for (PrismReportVo repvo : replist) {
			String resid = repvo.getResearchID();

			//요청 url을 만든다.
			//요청 url에 매핑되는 파라미터 생성 (서비스명, 인증키, 기관id)
			Map<String, String> params = new HashMap<String, String>();
			params.put("serviceName", "reportDetail");
			params.put("authKey", authKey);
			params.put("resid", resid);

			//OPEN API 호출
			ReportDetail repdetail = getReportDetail(url, params);
			log.debug("reportdetail:{}", repdetail.getReport());

			result += prismService.regReportDetail(repdetail);

//			break;
		}

		log.debug("reportdetail insert cnt:{}", result);

		return result;
	}

	private ReportDetail getReportDetail(String url, Map<String, String> params) {
		ReportDetail response = null;
		try {

			response = client.getForObject(url, ReportDetail.class, params);
//			response = client.getForObject("http://www.prism.go.kr/homepage/openapi/reportDetail/417824cd3a9847d882683da2dbc1aa9f/1051000-200900117", ReportDetail.class);
			log.debug("resid:{}", params.get("resid"));

		} catch (Exception e) {
			//호출시 오류 발생하는 경우 존재 ==> 호출 내용 및 오류 찍고 리턴한다.
			log.error("researchlist openapi call error:{}, param:{}", url, params);
			e.printStackTrace();
		}
		return response;
	}

	private int regNewResearchDetailbyOrganame(List<PrismResearchMst> reslist) {
		int result = 0;
		String url = baseUrl + "/{resid}";

		int cnt = 0;
		for (PrismResearchMst resvo : reslist) {
			String resid = resvo.getResearchId();
			//요청 url을 만든다.

			//요청 url에 매핑되는 파라미터 생성 (서비스명, 인증키, 기관id)
			Map<String, String> params = new HashMap<String, String>();
			params.put("serviceName", "researchDetail");
			params.put("authKey", authKey);
			params.put("resid", resid);

			//OPEN API 호출
			ResearchDetail resdetail = getResearchDetail(url, params);

			//신규 과제 상세정보를 DB에 insert 처리.
			if(resdetail != null) {
				result += prismService.regNewResearchDetail(resdetail);
			}

			cnt++;
//			if (cnt == 1) break;
		}

		return result;

	}

	private List<ResearchDetail> getResearchDetailListbyOrgname(
			List<PrismResearchMst> reslist) {

		List<ResearchDetail> resdtllist = new ArrayList<ResearchDetail>();
		String url = baseUrl + "/{resid}";

		int cnt = 0;
		for (PrismResearchMst resvo : reslist) {

			String resid = resvo.getResearchId();
			//요청 url을 만든다.

			//요청 url에 매핑되는 파라미터 생성 (서비스명, 인증키, 기관id)
			Map<String, String> params = new HashMap<String, String>();
			params.put("serviceName", "researchDetail");
			params.put("authKey", authKey);
			params.put("resid", resid);

			//OPEN API 호출
			ResearchDetail resdetail = getResearchDetail(url, params);
			resdtllist.add(resdetail);
			cnt++;
//			if (cnt == 1) break;
		}
		return resdtllist;
	}

	private ResearchDetail getResearchDetail(String url, Map<String, String> params) {
		ResearchDetail response = null;
		try {
			response = client.getForObject(url, ResearchDetail.class, params);
			log.debug("ResearchDetail:{}", response);

		} catch (Exception e) {
			log.error("researchdetail openapi call error:{}, param:{}", url, params);
			e.printStackTrace();
		}
//		ResearchDetail response = client.getForObject("http://www.prism.go.kr/homepage/openapi/researchDetail/417824cd3a9847d882683da2dbc1aa9f/1312000-201400005", ResearchDetail.class);

		return response;

	}

	private ResearchList getResearchList(String url, Map<String, String> params) {
		ResearchList response = null;
		try {
			response = client.getForObject(url, ResearchList.class, params);
			log.debug("orgid:{}, totcnt:{}", params.get("orgid"), response.getTotalCnt());

		} catch (Exception e) {
			log.error("researchlist openapi call error:{}, param:{}", url, params);
			e.printStackTrace();
		}
		return response;

	}

	private PrismReportList getReportList(String url, Map<String, String> params) {
		PrismReportList response = null;
		try {
			response = client.getForObject(url, PrismReportList.class, params);
			log.debug("orgid:{}, reportcnt:{}", params.get("orgid"), response.getTotalCnt());

		} catch (Exception e) {
			//호출시 오류 발생하는 경우 존재 ==> 호출 내용 및 오류 찍고 리턴한다.
			log.error("researchlist openapi call error:{}, param:{}", url, params);
			e.printStackTrace();
		}
		return response;

	}


	@RequestMapping("/rest/prismtest")
	public String getPrismTest() {

		String url = "http://www.prism.go.kr/homepage/openapi/researchList/417824cd3a9847d882683da2dbc1aa9f/4390000";

//		String result = client.getForObject(url, String.class);
//
//		log.debug(result);

		ResearchList response = client.getForObject(url, ResearchList.class);
//
		log.debug("{}\n{}", response.getTotalCnt(), response.getResearch().get(0));

		return "/login";
	}

	@RequestMapping("/rest/jaxbtest")
	public String getJaxbtest() throws ClientProtocolException, IOException, JAXBException{

		String url = "http://www.prism.go.kr/homepage/openapi/researchList/417824cd3a9847d882683da2dbc1aa9f/4390000";

		//restful service call
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httprequest = new HttpGet(url);
		HttpResponse httpresponse = httpclient.execute(httprequest);
		HttpEntity entity = httpresponse.getEntity();

		InputStream input = entity.getContent();

//		String result = client.getForObject(url, String.class);
//		InputStream input = new ByteArrayInputStream(result.getBytes(Charset.forName("UTF-8")));

		//xml parse
		this.jaxbContext = JAXBContext.newInstance(ResearchList.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		StreamSource source = new StreamSource(input);

		ResearchList response = (ResearchList) unmarshaller.unmarshal(source);


		//result
		log.debug("{}\n{}", response.getTotalCnt(), response.getResearch().get(0));

		return "/login";
	}



	public static void main(String[] args) throws ClientProtocolException, IOException, JAXBException {

		PrismRestClient prismclient = new PrismRestClient();

		prismclient.getPrismResearchList();

	}


	private String buildApiRequestUrl(String serviceName, String urlParam){
		StringBuilder reqUrl = new StringBuilder();
		reqUrl.append(this.baseUrl).append(serviceName).append("/").append(this.authKey).append("/").append(urlParam);
//		reqUrl.append(this.authKey);
//		reqUrl.append("&url=" + EncodeUtils.encodeUtf8(urlParam));

		log.debug("url:{}", reqUrl);

		return reqUrl.toString();
	}



}
