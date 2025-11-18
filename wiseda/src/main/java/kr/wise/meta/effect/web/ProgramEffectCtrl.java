/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ProgramEffectCtrl.java
 * 2. Package : kr.wise.meta.effect.web
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 7. 2. 오후 6:44:30
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 7. 2. :            : 신규 개발.
 */
package kr.wise.meta.effect.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.effect.service.ProgramEffectService;
import kr.wise.meta.effect.service.WatPgmAsta;
import kr.wise.meta.effect.service.WatPgmAstaFuc;
import kr.wise.meta.effect.service.WatPgmAstaTbl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ProgramEffectCtrl.java
 * 3. Package  : kr.wise.meta.effect.web
 * 4. Comment  : 프로그램 영향도 조회....
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 7. 2. 오후 6:44:30
 * </PRE>
 */
@Controller
public class ProgramEffectCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private ProgramEffectService programEffectService;

	@Inject
	private MessageSource message;


	private Map<String, Object> codeMap;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//프로그램 유형 코드 리스트...
		List<CodeListVo> langType   = codeListService.getCodeList(CodeListAction.LangType);
		codeMap.put("langType", langType);
		codeMap.put("langTypeibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(langType)));

		//등록유형코드
//		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
//		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
//		codeMap.put("regTypCdibs", regTypCd);

		return codeMap;
	}

	/** 프로그램 파싱결과 조회 View @return insomnia */
	@RequestMapping("/meta/effect/programeffect.do")
	public String goProgramEffectform() {

		return "/meta/effect/programeffect_lst";
	}

	/** 데이터 흐름도 대상 테이블 리스트 조회 - IBSheet-JSON @return insomnia */
	@RequestMapping("/meta/effect/getProgrameEffectList.do")
	@ResponseBody
	public IBSheetListVO<WatPgmAsta> getProgramEffectList(WatPgmAsta search) {
		logger.debug("{}", search);
		List<WatPgmAsta> list = programEffectService.getProgramEffectList(search);

		return new IBSheetListVO<WatPgmAsta>(list, list.size());
	}

	/** 프로그램 상세정보 @return insomnia */
	@RequestMapping("/meta/effect/getProgrameEffectDetail.do")
	public String getProgramEffectDetail(WatPgmAsta search, ModelMap model) {

		logger.debug("search : {}", search);

		if (search.getSubsystemId() != null && search.getPgmId() != null) {
			WatPgmAsta result = programEffectService.getProgramEffectDetail(search);
			model.addAttribute("result", result);
		}

		return "/meta/effect/programeffect_dtl";
	}

	/** 프로그램 영향도 테이블 CRUD @return insomnia */
	@RequestMapping("/meta/effect/getProgramEffectTblCRUD.do")
	@ResponseBody
	public IBSheetListVO<WatPgmAstaTbl> getProgramEffectTblCRUD(WatPgmAsta search) {

		logger.debug("search : {}", search);
		List<WatPgmAstaTbl> list = programEffectService.getProgramEffectTblCRUD(search);

		return new IBSheetListVO<WatPgmAstaTbl>(list, list.size());

	}

	/** 관련 프로그램 리스트 @return insomnia */
	@RequestMapping("/meta/effect/getRelProgramList.do")
	@ResponseBody
	public IBSheetListVO<WatPgmAsta> getRelProgramList(WatPgmAsta search) {

		logger.debug("search : {}", search);
		List<WatPgmAsta> list = programEffectService.getRelRrogramList(search);

		return new IBSheetListVO<WatPgmAsta>(list, list.size());

	}

	/** 관련 함수 리스트 조회- IBSheet JSON @return insomnia */
	@RequestMapping("/meta/effect/getRelFuncList.do")
	@ResponseBody
	public IBSheetListVO<WatPgmAstaFuc> getRelFuncList(WatPgmAsta search) {

		logger.debug("search : {}", search);
		List<WatPgmAstaFuc> list = programEffectService.getRelFuncList(search);

		return new IBSheetListVO<WatPgmAstaFuc>(list, list.size());

	}

}
