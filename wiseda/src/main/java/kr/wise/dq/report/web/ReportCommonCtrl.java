/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ColProfileCtrl.java
 * 2. Package : kr.wise.dq.criinfo.profile.web
 * 3. Comment :
 * 4. 작성자  : hwang
 * 5. 작성일  : 2014. 3. 24. 오후 1:29:21
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hwang : 2014. 3. 24. :            : 신규 개발.
 */
package kr.wise.dq.report.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.damgmt.schedule.service.ScheduleLogService;
import kr.wise.commons.damgmt.schedule.service.WamShdLogVO;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.dq.profile.anares.service.ProfileResultService;
import kr.wise.dq.profile.anares.service.WamPrfResultVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.report.service.DataPatternService;
import kr.wise.dq.report.service.DataPatternVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReportCommonCtrl {

	private final  Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> codeMap;

	@Inject
	private CodeListService codelistService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	//로그 조회
	@Inject
	private ScheduleLogService LogService;

	//데이터패턴 조회
	@Inject
	private DataPatternService DptrnService;

	//프로파일 결과
	@Inject
	private ProfileResultService profileResultService;
	
	@Inject
	private MessageSource message;

	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//코드리스트 JSON IBSheet용
		String anaStsCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("JOBSTSCD"));

		//공통코드 - IBSheet Combo Code용
		codeMap.put("anaStsCdibs", anaStsCd);

		return codeMap;
	}
	
	//데이터 패턴 팝업이동
	@RequestMapping("/dq/report/popup/prfcScriptSave.do")
	public IBSResultVO<DataPatternVO> prfcScriptSave(@ModelAttribute("search") DataPatternVO search, Model model, Locale locale) {
		int result =  DptrnService.prfcScriptSave(search);
		String resultMsg = null;

		//result "0" 이 아닌 경우 실패 메세지 전송...
		if(result > 0) {
			result = 0;
			resultMsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resultMsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG.getAction();
		return new IBSResultVO(result, resultMsg, action);
	}

	//데이터 패턴 팝업이동
	@RequestMapping("/dq/report/popup/datapattern_pop.do")
	public String gotestPop(@ModelAttribute("search") DataPatternVO search, Model model, Locale locale) {
		logger.debug(" search {}", search);

		int headerCnt = 0;
		String headerText = new String();
		//IBSHEET 컬럼 건수 조회
		DataPatternVO headerCntVO = DptrnService.DptrnHeaderInit(search);

		if( null !=headerCntVO ){
			headerCnt = headerCntVO.getColCnt()  ;

			//WAM_PRF_ER_DATA 테이블 컬럼명 생성
			String headerColNm = new String() ;
			//MARIA DB의 경우 || 로 문자열 결합 불가하여 CONCAT 새로 추가함.
			String headerColNmMaria = new String() ;
			StringBuffer colNm = new StringBuffer() ;
			StringBuffer tmpColNm = new StringBuffer() ;
			StringBuffer tmpColNmMaria = new StringBuffer() ;
			
			if(headerCnt > 0){
				for(int i=1; i<=headerCnt; i++){
					colNm.append(", COL_NM" + i);
					tmpColNm.append("COL_NM" + i + "||" + "'|'" + "||" );
					tmpColNmMaria.append(", COL_NM" + i + ", '|'");
				}
				headerColNm = tmpColNm.toString();
				headerColNmMaria = tmpColNmMaria.toString();
				search.setHeaderTextColNm(headerColNm.substring(0, headerColNm.length()-7));
				search.setHeaderTextColNmMaria("CONCAT(".concat(tmpColNmMaria.substring(1, headerColNmMaria.length()-5).concat(")")));

				//IBSHEET 헤더텍스트 조회
				DataPatternVO headerTextVO = DptrnService.DptrnHeaderText(search);
				headerText = headerTextVO.getHeaderText();
			}
			
			search.setPrfcScript(DptrnService.getPrfcScript(search));

			search.setColNm(colNm.toString());
			search.setColCnt(headerCnt);
			search.setHeaderText(headerText);

			model.addAttribute("headerVO", search);
		}
		return "/dq/report/popup/datapattern_pop";
	}

	//PK_DATA 팝업이동
	@RequestMapping("/dq/report/popup/pkdata_pop.do")
	public String goPkPop(@ModelAttribute("search") DataPatternVO search, Model model, Locale locale) {
		logger.debug(" 여기search {}", search);

		int headerCnt = 0;
		String headerText = new String();
		//IBSHEET 컬럼 건수 조회
		DataPatternVO headerCntVO = DptrnService.DptrnHeaderInit(search);

		if( null !=headerCntVO ){
			headerCnt = headerCntVO.getColCnt()  ;

			//WAM_PRF_ER_DATA 테이블 컬럼명 생성
			String headerColNm = new String() ;
			//MARIA DB의 경우 || 로 문자열 결합 불가하여 CONCAT 새로 추가함.
			String headerColNmMaria = new String() ;
			StringBuffer colNm = new StringBuffer() ;
			StringBuffer tmpColNm = new StringBuffer() ;
			StringBuffer tmpColNmMaria = new StringBuffer() ;

			if(headerCnt > 0){
				for(int i=1; i<=headerCnt; i++){
					colNm.append(", COL_NM" + i);
					tmpColNm.append("COL_NM" + i + "||" + "'|'" + "||" );
					tmpColNmMaria.append(", COL_NM" + i + ", '|'");
				}
				headerColNm = tmpColNm.toString();
				headerColNmMaria = tmpColNmMaria.toString();
				search.setHeaderTextColNm(headerColNm.substring(0, headerColNm.length()-7));
				search.setHeaderTextColNmMaria("CONCAT(".concat(tmpColNmMaria.substring(1, headerColNmMaria.length()-5).concat(")")));

				//IBSHEET 헤더텍스트 조회
				DataPatternVO headerTextVO = DptrnService.PkDptrnHeaderText(search);
				headerText = headerTextVO.getHeaderText();
			}

			search.setColNm(colNm.toString());
			search.setColCnt(headerCnt);
			search.setHeaderText(headerText);

			model.addAttribute("headerVO", search);
		}
		return "/dq/report/popup/pkdata_pop";
	}


	 //데이터패턴 리스트 조회
	@RequestMapping("/dq/report/popup/dataptr_lst.do")
	@ResponseBody
	public IBSheetListVO<DataPatternVO> getDataPattern( DataPatternVO search, Locale locale) {
		logger.debug("search++ : {}", search);
		List<DataPatternVO> list = DptrnService.getDataPattern(search);

		return new IBSheetListVO<DataPatternVO>(list, list.size());
	}

	//PK데이터패턴 리스트 조회
	@RequestMapping("/dq/report/popup/pk_dataptr_lst.do")
	@ResponseBody
	public IBSheetListVO<DataPatternVO> getPkDataPattern( DataPatternVO search, Locale locale) {
		logger.debug("{}", search);

			StringBuffer colNm = new StringBuffer() ;
			StringBuffer tmpColNm = new StringBuffer() ;

				for(int i=1; i<=search.getColCnt(); i++){
					colNm.append(", P.COL_NM" + i);
					tmpColNm.append("P.COL_NM" + i + "||" + "'|'" + "||" );
				}

			search.setColNm(colNm.toString());

		List<DataPatternVO> list = DptrnService.getPkDataPattern(search);

		return new IBSheetListVO<DataPatternVO>(list, list.size());
	}

	/*분석결과 조회*/
	@RequestMapping("/dq/report/ajaxgrid/getAnaResDtl.do")
	public String getAnaResDtl( DataPatternVO search, Model model, Locale locale) {
		logger.debug(" search {}", search);

		//프로파일
		if(search.getObjGb().indexOf("P") > -1){
			search = DptrnService.getPrfAnaResDtl(search);
		}
		//업무규칙
		else{
			search = DptrnService.getBrAnaResDtl(search);
		}

		model.addAttribute("anaResVO", search);

		return "/dq/report/popup/anares_dtl";
	}

	/*컬럼프로파일 분석결과 조회*/
	@RequestMapping("/dq/report/ajaxgrid/getColAnaResDtl.do")
	public String searchColAnaResDtl(DataPatternVO search, Model model, Locale locale) {
		logger.debug(" search {}", search);
		WamPrfResultVO result = profileResultService.getColAnaResDtl(search.getObjId());
		model.addAttribute("colAnaResVO", result);
		return "/dq/report/popup/colanares_dtl";
	}
	
	/*컬럼프로파일 분석결과 조회*/
	@RequestMapping("/dq/report/ajaxgrid/getColAnaTrsLst.do")
	@ResponseBody
	public IBSheetListVO<WamPrfResultVO> searchColAnaTrsLst(DataPatternVO search, Model model, Locale locale) {
		logger.debug(" search {}", search);
		
		List<WamPrfResultVO> list = profileResultService.getColAnaTrsLst(search.getObjId());
		
		logger.debug("list size >>>>>> " + list.size());

		return new IBSheetListVO<WamPrfResultVO>(list, list.size());
	}


	/* 진단대상 테이블 상세 조회
	@RequestMapping("/dq/profile/getAnaTrgTblDetail.do")
	@ResponseBody
	public AnaTrgTblVO getAnaTrgTblDetail(@ModelAttribute("search") AnaTrgTblVO search, Locale locale) {
		String sTblColGb = search.getTblColGb();
		search =  anaTrgService.getAnaTrgTblDtl(search);
		search.setTblColGb(sTblColGb);

		logger.debug(" {} search "+search);
		return search;
	}
*/
	/* 로그팝업 이동*/
	@RequestMapping("/dq/report/popup/analog_pop.do")
	public String goLogPop(@ModelAttribute("search") WamShdLogVO search, Model model, Locale locale) {
		logger.debug(" search {}", search);

		model.addAttribute("search", search);
		return "/dq/report/popup/analog_pop";
	}

	/* 로그 조회 */
	@RequestMapping("/dq/report/popup/analog_lst.do")
	@ResponseBody
	public IBSheetListVO<WamShdLogVO> getLogLst(@ModelAttribute WamShdLogVO search, Locale locale) {
		logger.debug("{}", search);
		List<WamShdLogVO> list = LogService.getLogLst(search);
//		logger.debug("{}", list);
		return new IBSheetListVO<WamShdLogVO>(list, list.size());
	}




}
