/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbcSeqCtrl.java
 * 2. Package : kr.wise.meta.dbc.web
 * 3. Comment : 
 * 4. 작성자  : hjan93
 * 5. 작성일  : 2020. 7. 29. 
 * 6. 변경이력 : 
 *                    이름          : 일자          		: 근거자료   	 : 변경내용
 *                   ------------------------------------------------------
 *                    hjan93 : 2020. 7. 29. :            : 신규 개발.
 */
package kr.wise.meta.dbc.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.dbc.service.DbcSeqService;
import kr.wise.meta.dbc.service.WatDbcCol;
import kr.wise.meta.dbc.service.WatDbcIdxCol;
import kr.wise.meta.dbc.service.WatDbcSeq;
import kr.wise.meta.dbc.service.WatDbcTbl;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbcSeqCtrl.java
 * 3. Package  : kr.wise.meta.dbc.web
 * 4. Comment  : 
 * 5. 작성자   : hjan93
 * 6. 작성일   : 2020. 7. 29. 
 * </PRE>
 */
@Controller
@RequestMapping
public class DbcSeqCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	@Inject
	private DbcSeqService dbcSeqService;
	
	@Inject
	private MessageSource message;
	
	private Map<String, Object> codeMap;

	static class WatDbcSeqs extends HashMap<String, ArrayList<WatDbcSeq>> {}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	@RequestMapping("/meta/dbc/dbcseq_lst.do")
	public String goFormpage(@ModelAttribute("search") WatDbcSeq search ,String linkFlag ,Model model) {
		logger.debug("linkFlag : {}",linkFlag);
		logger.debug("search : {}",search);
		model.addAttribute("linkFlag",linkFlag);
		return "/meta/dbc/dbcseq_lst";
	}
	
	/** DBC 시퀀스 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/dbc/getDbcSeqlist.do")
	@ResponseBody
	public IBSheetListVO<WatDbcSeq> selectList(@ModelAttribute WatDbcSeq search) {
		logger.debug("{}", search);
		List<WatDbcSeq> list = dbcSeqService.getList(search);

		return new IBSheetListVO<WatDbcSeq>(list, list.size());
	}

	/** DBC 시퀀스 상세정보 조회 */
	/** meta */
	@RequestMapping("/meta/dbc/ajaxgrid/dbcseqinfo_dtl.do")
	public String selectDbcSeqInfoDetail(String dbSchId, String dbcSeqNm, ModelMap model) {
		logger.debug("param : {}, {}", dbSchId, dbcSeqNm);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(dbSchId) && !UtilObject.isNull(dbcSeqNm)) {

			WatDbcSeq result = dbcSeqService.selectDbcSeqInfo(dbSchId, dbcSeqNm);
			model.addAttribute("result", result);
		}
		return "/meta/dbc/dbcseqinfo_dtl";
	}
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		//주제영역 정보(double_select용 목록성코드)
		String subjLnm   = UtilJson.convertJsonString(codeListService.getCodeList("subj"));
		codeMap.put("subjLnmibs", subjLnm);
		codeMap.put("subjLnm", codeListService.getCodeList("subj"));
		
		//공통 코드(요청구분 코드리스트)

		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);

		String dbmsTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD"));
		codeMap.put("dbmsTypCd", cmcdCodeService.getCodeList("DBMS_TYP_CD"));
		codeMap.put("dbmsTypCdibs", dbmsTypCd);
		
		return codeMap;
	}
}
