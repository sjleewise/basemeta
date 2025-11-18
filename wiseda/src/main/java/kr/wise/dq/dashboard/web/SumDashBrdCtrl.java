package kr.wise.dq.dashboard.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.bbs.service.BBSManagerService;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.util.UtilJson;
import kr.wise.portal.dashboard.service.DqMainChartVO;
import kr.wise.portal.dashboard.service.TotalCountVO;
import kr.wise.portal.dashboard.service.TotalDashService;

@Controller
public class SumDashBrdCtrl {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private BBSManagerService bbsMngService;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private TotalDashService totalDashService;
	
	@Inject
	private CodeListService codelistService;
	
	private Map<String, Object> codeMap;
	
	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		//진단대상
		List<CodeListVo> connTrgDbms = codelistService.getCodeList(CodeListAction.connTrgDbms);

		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(connTrgDbms)));
		codeMap.put("connTrgDbmsCd", connTrgDbms); 
	
		return codeMap;
	}
	
	@RequestMapping("/dq/dashboard/summary_dashboard.do")
	public ModelAndView goDqMain() throws Exception {
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		log.debug("user = {}",user);

		ModelAndView mv = new ModelAndView();


/*		BoardVO vo = new BoardVO();
		vo.setBbsId("BBSMSTR_000000000007");

		vo.setFirstIndex(0);
		vo.setRecordCountPerPage(6);

		//Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, vo.getBbsAttrbCode());
		Map<String, Object> map = bbsMngService.selectBoardArticles(vo, "");//2011.09.07

		Map<String,Object> param = new HashMap<String,Object>();

		param.put("firstIndex", 0);
		param.put("recordCountPerPage", 6);
		param.put("userId", user.getId());


		//내 요청정보 조회
		WaqMstr record = new WaqMstr() ;
		record.setRqstStepCd("Q");
		List<WaqMstr> rqstMyList = requestMstService.getRqstMyListForMain(record);
		for(int i=rqstMyList.size();i<5;i++) {
			WaqMstr waqMstr = new WaqMstr();
			waqMstr.setRqstNm("&nbsp");
			rqstMyList.add(waqMstr);
		}

		//내 결재정보 조회
		List<WaqMstr> rqstToDoList = requestMstService.getRqstToDoListForMain(record);
		for(int i=rqstToDoList.size();i<5;i++) {
			WaqMstr waqMstr = new WaqMstr();
			waqMstr.setRqstNm("&nbsp");
			rqstToDoList.add(waqMstr);
		}
    	mv.addObject("bbsList", map.get("resultList"));
    	mv.addObject("reqList", rqstMyList);
    	mv.addObject("aprvList", rqstToDoList);



    	//기준정보 현황 조회
		List<TotalCountVO> criresult =  dqdashService.getTotCntDqCri();

		 업무영역별 데이터품질 개선활동 진행현황  
		List<DqdashSystemVO> bizareaImpvResult = dqdashService.getBizAareaImpvList();


		 데이터모델 조회 
		//List<UpdateCntVO> modelresult = totalDashService.selectUpdateCntStat(userid);
		//mv.addObject("modelresult", modelresult);

		mv.addObject("criresult", criresult);
		mv.addObject("bizareaImpvResult", bizareaImpvResult);*/

		mv.setViewName("/dq/dashboard/summary_dashboard");
		
		//보유현황
    	List<TotalCountVO> mtaTotalCnt = totalDashService.selectDbmsCnt();
    	mv.addObject("mtaTotalCntList", mtaTotalCnt);
    	
    	//프로파일
//    	List<TotalCountVO> prfTotalCnt = totalDashService.selectPrfCnt();
    	WaaDbConnTrgVO vo = new WaaDbConnTrgVO();
    	List<DqMainChartVO> prfTotalCnt = totalDashService.selectPrfCntByDbms(vo);
    	mv.addObject("prfTotalCnt", prfTotalCnt);
    	
//    	log.debug("prfTotalCnt:{}", prfTotalCnt.toString());
    	
    	String json = new ObjectMapper().writeValueAsString(prfTotalCnt);//prfTotalCnt를 json으로 바꿈
    	mv.addObject("result", json );
    	
//    	log.debug("json:{}", json.toString());
		
    	List<TotalCountVO> chartData = totalDashService.selectChartCnt();
		mv.addObject("chartCnt",chartData);
		
//		log.debug("chartData:{}", chartData.toString());

		return mv;

	}
}
