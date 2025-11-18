package kr.wise.meta.symn.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.sysmgmt.basicinfo.service.BasicInfoLvlService;
import kr.wise.commons.sysmgmt.basicinfo.service.WaaBscLvl;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.gap.service.ModelGapVO;
import kr.wise.meta.stnd.service.WamDmn;
import kr.wise.meta.stnd.service.WamStwdCnfg;
import kr.wise.meta.symn.service.StructureService;
import kr.wise.meta.symn.service.StructureVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : StructureCtrl.java
 * 3. Package  : kr.wise.meta.symn.web
 * 4. Comment  : 
 * 5. 작성자   : jyson(손준영)
 * 6. 작성일   : 2014. 7. 11. 오후 2:14:29
 * </PRE>
 */ 



@Controller
public class StructureCtrl {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private StructureService structureService;
	
	@Inject
	private BasicInfoLvlService basicInfoLvlService;

	private Map<String, Object> codeMap;
	
	/** 도메인 구조 검사 페이지 */
	/** @return jyson */
	@RequestMapping("/meta/symn/dmnstruct_lst.do")
	public String goDmnStruct(Model model) throws Exception{
		//도메인그룹의 기본정보레벨, SELECT BOX ID값을 불러온다.
		WaaBscLvl data = basicInfoLvlService.selectBasicInfoList("DMNG");
		logger.debug("기본정보레벨 조회 : {}", data);
		if (data != null) {
			model.addAttribute("bscLvl", data.getBscLvl());
			model.addAttribute("selectBoxId", data.getSelectBoxId());
		}
		return "/meta/symn/dmnstruct_lst";
	}
	
	/** 도메인 구조 검사 조회 */
	@RequestMapping("/meta/symn/getDmnStructure.do")
	@ResponseBody
	public IBSheetListVO<StructureVO> getDmnStructure(@ModelAttribute StructureVO search) throws Exception{
		logger.debug("{}", search);
	
		List<StructureVO> list = structureService.getDmnStructure(search);
		
		return new IBSheetListVO<StructureVO>(list, list.size());
	}
	
	/** 도메인 구조 상세정보 조회 */
	@RequestMapping("/meta/symn/ajaxgrid/getDmnStruct_dtl.do")
	@ResponseBody
	public IBSheetListVO<StructureVO> selectDmnInitList(@ModelAttribute StructureVO search) throws Exception {
		logger.debug("{}", search);
		List<StructureVO> list = structureService.selectDmnInitList(search);
		return new IBSheetListVO<StructureVO>(list, list.size());
	}
	
	
	/** 항목 구조 검사 페이지 */
	/** @return jyson */
	@RequestMapping("/meta/symn/itmstruct_lst.do")
	public String goItmStruct(){
		return "/meta/symn/itmstruct_lst";
	}
	
	/** 항목 구조 검사 조회 */
	@RequestMapping("/meta/symn/getItmStructure.do")
	@ResponseBody
	public IBSheetListVO<StructureVO> getItmStructure(@ModelAttribute StructureVO search) {
		logger.debug("{}", search);
		List<StructureVO> list = structureService.getItmStructure(search);
		
		return new IBSheetListVO<StructureVO>(list, list.size());
	}
	
	/** 항목 구조 상세정보 조회 */
	@RequestMapping("/meta/symn/ajaxgrid/getItmStruct_dtl.do")
	@ResponseBody
	public IBSheetListVO<StructureVO> selectItmInitList(@ModelAttribute StructureVO search) throws Exception {
		logger.debug("{}", search);
		List<StructureVO> list = structureService.selectItmInitList(search);
		return new IBSheetListVO<StructureVO>(list, list.size());
	}
	
	
	
	
	/**모델 구조 검사 페이지*/
	/** @return jyson  */
	@RequestMapping("/meta/symn/mdlstruct_lst.do")
	public String goMdlStruct(){
		return "/meta/symn/mdlstruct_lst";
	}
	
	/** 모델 구조 검사 조회 */
	@RequestMapping("/meta/symn/getMdlStructuer.do")
	@ResponseBody
	public IBSheetListVO<StructureVO> getMdlStructuer(@ModelAttribute StructureVO search) {
		logger.debug("{}", search);
		List<StructureVO> list = structureService.getMdlStructure(search);
		
		return new IBSheetListVO<StructureVO>(list, list.size());
	}
	
	/** 모델 구조 상세정보 조회 */
	@RequestMapping("/meta/symn/ajaxgrid/getMdlStruct_dtl.do")
	@ResponseBody
	public IBSheetListVO<StructureVO> selectMdlInitList(@ModelAttribute StructureVO search) throws Exception {
		logger.debug("{}", search);
		List<StructureVO> list = structureService.selectMdlInitList(search);
		return new IBSheetListVO<StructureVO>(list, list.size());
	}
	
	
	/** 코드 유사구성 분석 페이지 */
	/** @return jyson */
	@RequestMapping("/meta/symn/codeStruct_lst.do")
	public String goCodeStruct_lst(){
		return "/meta/symn/codeStruct_lst";
	}
	
	/** 코드 유사구성 분석 조회 */
	@RequestMapping("/meta/symn/getCodeStructuer.do")
	@ResponseBody
	public IBSheetListVO<StructureVO> getCodeStructuer(@ModelAttribute StructureVO search) {
		logger.debug("{}", search);
		List<StructureVO> list = structureService.getCodeStructuer(search);
		
		return new IBSheetListVO<StructureVO>(list, list.size());
	}
	
	/** 코드 유사구성 상세정보 조회 */
	@RequestMapping("/meta/symn/ajaxgrid/getCodeStructuer_dtl.do")
	@ResponseBody
	public IBSheetListVO<StructureVO> selectCodeInitList(@ModelAttribute StructureVO search) throws Exception {
		logger.debug("{}", search);
		List<StructureVO> list = structureService.selectCodeInitList(search);
		return new IBSheetListVO<StructureVO>(list, list.size());
	}
	
	
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();
		
		//목록성 코드
		String dmnginfotp 		= UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp"));
		codeMap.put("dmnginfotp", dmnginfotp);
		
		/*//진단대상
		List<CodeListVo> dmng = codeListService.getCodeList(CodeListAction.dmng);
		codeMap.put("dmngCd",dmng);*/
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);

		//등록유형코드
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);
		
		codeMap.put("stndAsrtibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("STNDASRT")));
		codeMap.put("stndAsrt", cmcdCodeService.getCodeList("STNDASRT"));


		return codeMap;
	}
	
	
}
