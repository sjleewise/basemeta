/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DataFlowCtrl.java
 * 2. Package : kr.wise.meta.effect.web
 * 3. Comment : 데이터 흐름도 조회...
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 6. 27. 오후 3:01:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 6. 27. :            : 신규 개발.
 */
package kr.wise.meta.effect.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.effect.service.DataFlowColMapVO;
import kr.wise.meta.effect.service.DataFlowEdge;
import kr.wise.meta.effect.service.DataFlowNode;
import kr.wise.meta.effect.service.DataFlowService;
import kr.wise.meta.effect.service.DataFlowTblVO;
import kr.wise.meta.effect.service.DataFlowVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DataFlowCtrl.java
 * 3. Package  : kr.wise.meta.effect.web
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 27. 오후 3:01:00
 * </PRE>
 */
@Controller
public class DataFlowCtrl {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	static class DataFlowColMapVOs extends HashMap<String, ArrayList<DataFlowColMapVO>> { }

	@Inject
	private CodeListService codeListService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private MessageSource message;

	@Inject
	private DataFlowService dataFlowService;

	private Map<String, Object> codeMap;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);

		//등록유형코드
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);


		return codeMap;
	}

	/** 데이터 흐름도 조회 View @return insomnia */
	@RequestMapping("/meta/effect/dataflow.do")
	public String godataflowform() {

		return "/meta/effect/dataflow_lst";
	}

	/** 데이터 흐름도 대상 테이블 리스트 조회 - IBSheet-JSON @return insomnia */
	@RequestMapping("/meta/effect/getDataflowTblList.do")
	@ResponseBody
	public IBSheetListVO<DataFlowTblVO> getDataflowTblList(DataFlowTblVO search) {
		logger.debug("{}", search);
		List<DataFlowTblVO> list = dataFlowService.getDataflowTblList(search);

		return new IBSheetListVO<DataFlowTblVO>(list, list.size());
	}

	@RequestMapping("/meta/effect/getdataflowlist.do")
	@ResponseBody
	public DataFlowVO getDataflowList(DataFlowTblVO search) {
		logger.debug("getDataflowList >>>> " + search.getDdlTblPnm());
		logger.debug("{}", search);

		DataFlowVO flow = new DataFlowVO();

		List<DataFlowNode> nodes = dataFlowService.getDataFlowNodes(search);

		List<DataFlowEdge> edges = dataFlowService.getDataFlowEdges(search);


//		String result   = UtilJson.convertJsonString(flow);
//
//		logger.debug("{}", result);

		flow.nodes = nodes;
		flow.edges = edges;

		return flow;
	}

	@RequestMapping("/meta/effect/getdataflowcolmaplist.do")
	@ResponseBody
	public IBSheetListVO<DataFlowColMapVO> getDataflowColMapList(DataFlowTblVO search) {
		logger.debug("{}", search);
		List<DataFlowColMapVO> list = dataFlowService.getDataflowColMapList(search);

		return new IBSheetListVO<DataFlowColMapVO>(list, list.size());
	}

	private void testdataflow(DataFlowVO flow) {

		List<DataFlowNode> nodes = new ArrayList<DataFlowNode>();
		DataFlowNode data = new DataFlowNode();
//		 { data: { id: 'j', name: '한글1' } },
//	      { data: { id: 'e', name: '한글2' } },
//	      { data: { id: 'k', name: '한글3' } },
//	      { data: { id: 'g', name: '한글4' } }
		data.data.id = "j";
		data.data.name = "한글1";
		nodes.add(data);

		data = new DataFlowNode();
		data.data.id = "e";
		data.data.name = "한글2";
		nodes.add(data);

		data = new DataFlowNode();
		data.data.id = "k";
		data.data.name = "한글3";
		nodes.add(data);

		data = new DataFlowNode();
		data.data.id = "g";
		data.data.name = "한글4";
		nodes.add(data);

		List<DataFlowEdge> edges = new ArrayList<DataFlowEdge>();
		DataFlowEdge edge = new DataFlowEdge();
//		  { data: { source: 'j', target: 'e' } },
//	      { data: { source: 'j', target: 'k' } },
//	      { data: { source: 'j', target: 'g' } },
//	      { data: { source: 'e', target: 'j' } },
		edge.data.source = "j";
		edge.data.target = "e";
		edges.add(edge);

		edge = new DataFlowEdge();
		edge.data.source = "j";
		edge.data.target = "k";
		edges.add(edge);

		edge = new DataFlowEdge();
		edge.data.source = "j";
		edge.data.target = "g";
		edges.add(edge);

		edge = new DataFlowEdge();
		edge.data.source = "e";
		edge.data.target = "j";
		edges.add(edge);

		flow.nodes = nodes;
		flow.edges = edges;

	}

	/** 데이터흐름 관리 화면 */
	@RequestMapping("/meta/effect/dataflow_rqst.do")
	public String goDataFlowRqstPage() {
		return "/meta/effect/dataflow_rqst";
	}
	
	/** 데이터흐름 관리 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/effect/dataflowSelectlist.do")
	@ResponseBody
	public IBSheetListVO<DataFlowColMapVO> selectList(@ModelAttribute DataFlowColMapVO search) {
		logger.debug("{}", search);
		List<DataFlowColMapVO> list = dataFlowService.getDataflowRqstList(search);
		return new IBSheetListVO<DataFlowColMapVO>(list, list.size());
	}
	
	/** 데이터흐름 관리 리스트 등록 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("/meta/effect/dataflowReglist.do")
	@ResponseBody
	public IBSResultVO<DataFlowColMapVO> regList(@RequestBody DataFlowColMapVOs data, Locale locale) throws Exception {

		logger.debug("{}", data);
		ArrayList<DataFlowColMapVO> list = data.get("data");

		int result = dataFlowService.regList(list);
		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();

		return new IBSResultVO<DataFlowColMapVO>(result, resmsg, action);
	}
	
	/** 데이터흐름 관리 리스트 삭제 - IBSheet JSON */
	@RequestMapping("/meta/effect/dataflowDellist.do")
	@ResponseBody
	public IBSResultVO<DataFlowColMapVO> delList(@RequestBody DataFlowColMapVOs data, Locale locale) {
		logger.debug("{}", data);
		ArrayList<DataFlowColMapVO> list = data.get("data");

		int result = dataFlowService.delList(list);

		String resmsg ;
		if (result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.DEL", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.DEL", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.DEL.getAction();


		return new IBSResultVO<DataFlowColMapVO>(result, resmsg, action);
	}
}
