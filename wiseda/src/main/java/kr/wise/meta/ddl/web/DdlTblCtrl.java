package kr.wise.meta.ddl.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.grid.IBSResultVO;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.ddl.script.model.Sequence;
import kr.wise.meta.ddl.script.service.DdlScriptService;
import kr.wise.meta.ddl.service.DdlTblService;
import kr.wise.meta.ddl.service.WamDdlCol;
import kr.wise.meta.ddl.service.WamDdlIdx;
import kr.wise.meta.ddl.service.WamDdlIdxCol;
import kr.wise.meta.ddl.service.WamDdlRel;
import kr.wise.meta.ddl.service.WamDdlTbl;
import kr.wise.meta.ddl.service.WaqDdlIdx;
import kr.wise.meta.ddl.service.WaqDdlPart;
import kr.wise.meta.ddl.service.WaqDdlSeq;
import kr.wise.meta.ddl.service.WaqDdlTbl;
import kr.wise.meta.ddltsf.service.WamDdlTsfObj;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlTblCtrl.java
 * 3. Package  : kr.wise.meta.ddl.web
 * 4. Comment  :
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 4. 24. 오후 6:06:49
 * </PRE>
 */
@Controller("DdlTblCtrl")
//@RequestMapping("/meta/ddl/*")
public class DdlTblCtrl {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private CodeListService codeListService;

	@Inject
	private DdlTblService ddlTblService;

	@Inject
	private DdlScriptService ddlScriptService;

	@Inject
	private MessageSource message;

//	private IBSResult ibsRes = new IBSResult();
//
//	private IBSJsonSearch ibsJson = new IBSJsonSearch();

	private Map<String, Object> codeMap;

	static class WamDdlTbls extends HashMap<String, ArrayList<WamDdlTbl>> {}

	static class WamDdlTsfObjs extends HashMap<String, ArrayList<WamDdlTsfObj>> {}
	
	static class WaqDdlParts extends HashMap<String, ArrayList<WaqDdlPart>> {}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//목록성 코드(시스템영역 코드리스트)
		List<CodeListVo> sysarea = codeListService.getCodeList("sysarea");
		codeMap.put("sysarea", UtilJson.convertJsonString(sysarea));
		codeMap.put("sysareaibs", UtilJson.convertJsonString(codeListService.getCodeListIBS(sysarea))); 		
		
		//진단대상/스키마 정보(double_select_upcode 용 목록성코드 : 개발)
		String connTrgSchJsonBySubjSchMap   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchJsonBySubjSchMap"));
		codeMap.put("connTrgSchJsonBySubjSchMap", connTrgSchJsonBySubjSchMap);
		
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);

		//개발 진단대상/스키마 정보(double_select용 목록성코드)
		String devConnTrgSch   = UtilJson.convertJsonString(codeListService.getCodeList("devConnTrgSchId"));
		codeMap.put("devConnTrgSch", devConnTrgSch);
		
		//주제영역 정보(목록성코드)
		String subjLnm   = UtilJson.convertJsonString(codeListService.getCodeList("subj"));
		codeMap.put("subjLnmibs", subjLnm);
		codeMap.put("subjLnm", codeListService.getCodeList("subj"));
		
		//공통 코드(요청구분 코드리스트)

		//등록유형코드(REG_TYP_CD)
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("regTypCdibs", regTypCd);
		
		//테이블종류코드(TBL_TYP_CD)
		String tblTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TBL_TYP_CD"));
		codeMap.put("tblTypCd", cmcdCodeService.getCodeList("TBL_TYP_CD"));
		codeMap.put("tblTypCdibs", tblTypCd);
		
		//오브젝트구분코드(OBJ_DCD)
		String objDcd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("OBJ_DCD"));
		codeMap.put("objDcd", cmcdCodeService.getCodeList("OBJ_DCD"));
		codeMap.put("objDcdibs", objDcd);
		
		//오브젝트구분코드(OBJ_DCD)
		String prcTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRC_TYP_CD"));
		codeMap.put("prcTypCd", cmcdCodeService.getCodeList("PRC_TYP_CD"));
		codeMap.put("prcTypCdibs", prcTypCd);
		
		codeMap.put("rvwStsCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RVW_STS_CD")));
		codeMap.put("rvwStsCd", cmcdCodeService.getCodeList("RVW_STS_CD"));
		
		//요청구분코드
		codeMap.put("rqstDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_DCD")));
		codeMap.put("rqstDcd", cmcdCodeService.getCodeList("RQST_DCD"));
		
		//검증코드
		codeMap.put("vrfCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRF_CD")));
		codeMap.put("vrfCd", cmcdCodeService.getCodeList("VRF_CD"));

		//테이블변경유형코드
		codeMap.put("tblChgTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TBL_CHG_TYP_CD")));
		codeMap.put("tblChgTypCd", cmcdCodeService.getCodeList("TBL_CHG_TYP_CD"));
		
		//인덱스유형코드
		codeMap.put("idxTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("IDX_TYP_CD")));
		codeMap.put("idxTypCd", cmcdCodeService.getCodeList("IDX_TYP_CD"));
		
		//인덱스컬럼정렬순서
		codeMap.put("idxColSrtOrdCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("IDX_COL_SRT_ORD_CD")));
		codeMap.put("idxColSrtOrdCd", cmcdCodeService.getCodeList("IDX_COL_SRT_ORD_CD"));
		
		//관계유형코드
		codeMap.put("relTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REL_TYP_CD")));
		codeMap.put("relTypCd", cmcdCodeService.getCodeList("REL_TYP_CD"));
		//카디널리티유형코드
		codeMap.put("crdTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CRD_TYP_CD")));
		codeMap.put("crdTypCd", cmcdCodeService.getCodeList("CRD_TYP_CD"));
		//Parent Optionality 유형코드
		codeMap.put("paOptTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PA_OPT_TYP_CD")));
		codeMap.put("paOptTypCd", cmcdCodeService.getCodeList("PA_OPT_TYP_CD"));		
		
		//DDL대상구분 (D : 개발, T : 테스트, R : 운영)
		codeMap.put("ddlTrgDcdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DDL_TRG_DCD")));
		codeMap.put("ddlTrgDcd", cmcdCodeService.getCodeList("DDL_TRG_DCD"));		
		
		return codeMap;
	}
	
	/** DDL 테이블 추가용 팝업 for 파티션 (DDL테이블 조회 팝업)  */
	@RequestMapping("/meta/ddl/popup/ddltblforpart_pop.do")
	public String goDdlTblPopforPart(@ModelAttribute("search") WamDdlTbl search, Model model, Locale locale) {
		//logger.debug("DDL테이블 조회 팝업 for 파티션:{}", search);
		
		return "/meta/ddl/popup/ddltblforpart_pop";
	}
		
	/** DDL 테이블 추가용 팝업 (DDL테이블 조회 팝업) @return meta */
    @RequestMapping("/meta/ddl/popup/ddltblforidx_pop.do")
    public String goDdlTblPop(@ModelAttribute("search") WamDdlTbl search, Model model, Locale locale) {
		logger.debug("search:{}", search);

		return "/meta/ddl/popup/ddltblforidx_pop";
	}
    /** DDL 컬럼 팝업 () @return meta */
    @RequestMapping("/meta/ddl/popup/ddlcol_pop.do")
    public String goDdlColPop(@ModelAttribute("search") WamDdlCol search, Model model, Locale locale) {
    	logger.debug("search:{}", search);
    	
    	return "/meta/ddl/popup/ddlcol_pop";
    }
    
    /** DDL 인덱스 팝업 @return meta */
    @RequestMapping("/meta/ddl/popup/ddlidx_pop.do")
    public String goDdlIdxPop(@ModelAttribute("search") WamDdlIdx search, Model model, Locale locale) {
    	logger.debug("search:{}", search);
    	
    	return "/meta/ddl/popup/ddlidx_pop";
    }

	@RequestMapping("/meta/ddl/ddltbl_lst.do")
	public String gosubjFrom(@ModelAttribute("search")WamDdlTbl search,String linkFlag,Model model) {
		logger.debug("linkFlag : {}",linkFlag);
		logger.debug("search : {}",search);
		model.addAttribute("linkFlag",linkFlag);
		
		return "/meta/ddl/ddltbl_lst";
	}
	
	/** DDL인덱스 조회페이지 호출 */
	@RequestMapping("/meta/ddl/ddlidx_lst.do")
	public String goDdlIdxFrom(@ModelAttribute("search")WamDdlIdx search, String linkFlag, Model model) {
		logger.debug("linkFlag : {}",linkFlag);
		logger.debug("search : {}",search);
		model.addAttribute("linkFlag",linkFlag);
		
		return "/meta/ddl/ddlidx_lst";
	}

	@RequestMapping("/meta/ddl/ddltblrqst_lst.do")
	public String goDdlTblRqstList(@ModelAttribute("search")WamDdlTbl search, Model model) {
		
		logger.debug("search : {}",search);
		
		
		return "/meta/ddl/ddltblrqst_lst";
	}
	@RequestMapping("/meta/ddl/ddltbltsfrqst_lst.do")
	public String goDdlTblTsfRqstList(@ModelAttribute("search")WamDdlTbl search, Model model) {
		
		logger.debug("search : {}",search);
		
		
		return "/meta/ddl/ddltbltsfrqst_lst";
	}
	
	@RequestMapping("/meta/ddl/popup/ddlscript_pop.do")
	public String goDdlScriptPop(@ModelAttribute("search")WamDdlTbl search, Model model) {
		
		logger.debug("search : {}",search);
		
		
		return "/meta/ddl/popup/ddlscript_pop";
	}
	
	/** DDL 테이블 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdllist.do")
	@ResponseBody
	public IBSheetListVO<WamDdlTbl> selectList(@ModelAttribute WamDdlTbl search) {
		logger.debug("{}", search);
		List<WamDdlTbl> list = ddlTblService.getList(search);

		return new IBSheetListVO<WamDdlTbl>(list, list.size());
	}

	/** DDL 테이블 요청리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlRqstlist.do")
	@ResponseBody
	public IBSheetListVO<WamDdlTbl> selectDdlRqstList(@ModelAttribute WamDdlTbl search) {
		logger.debug("{}", search);
		List<WamDdlTbl> list = ddlTblService.getDdlTblRqstList(search);
		
		return new IBSheetListVO<WamDdlTbl>(list, list.size());
	}

	/** DDL이관 테이블 요청리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlTsfRqstlist.do")
	@ResponseBody
	public IBSheetListVO<WamDdlTsfObj> selectDdlTsfRqstList(@ModelAttribute WamDdlTsfObj search) {
		logger.debug("{}", search);
		List<WamDdlTsfObj> list = ddlTblService.getDdlTblTsfRqstList(search);
		
		return new IBSheetListVO<WamDdlTsfObj>(list, list.size());
	}
	
	/** DDL 테이블 상세정보 조회 */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddltblinfo_dtl.do")
	public String selectDdlTblInfoDetail(String ddlTblId, String rqstNo, ModelMap model) {
		logger.debug(" {}", ddlTblId);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(ddlTblId) && !UtilObject.isNull(rqstNo)) {

			WamDdlTbl result = ddlTblService.selectDdlTblInfo(ddlTblId, rqstNo);
			model.addAttribute("result", result);
		}
		return "/meta/ddl/ddltblinfo_dtl";
	}

	/** DDL 스크립트 상세정보 조회
	 * @throws Exception */
	@RequestMapping("/meta/ddl/ajaxgrid/ddlscript_dtl.do")
	public String getDdlScriptTable(String ddlTblId, String ddlIdxId, String ddlSeqId, String ddlEtcId, String ddlPartId, String ddlGrtId, String regTypCd, String rqstNo, String scrtInfo, ModelMap model) throws Exception {
		logger.debug("\n @ddlTblId: {}", ddlTblId);
		logger.debug("\n @ddlIdxId: {}", ddlIdxId);
		logger.debug("\n @ddlSeqId: {}", ddlSeqId);
		logger.debug("\n @ddlEtcId: {}", ddlEtcId);
		logger.debug("\n @ddlPartId: {}", ddlPartId);
		logger.debug("\n @ddlGrtId: {}", ddlGrtId);
		logger.debug("\n @regTypCd: {}", regTypCd);
		logger.debug("\n @scrtInfo: {}", scrtInfo);
		logger.debug("\n @rqstNo: {}", rqstNo);

//		if(StringUtils.hasText(ddlTblId)) {
//			String ddlscirpt = ddlScriptService.getDdlScriptTable(ddlTblId);
//			model.addAttribute("ddlscript", ddlscirpt);
//		}
		
		String ddlscript = "";
		
		if(!UtilString.null2Blank(ddlIdxId).equals("")) {
			
			//테이블 조회
			//Table table = ddlScriptService.getTableIndexOne(ddlIdxId);
			
			//ddlscript = ddlScriptService.getDDlScriptIndex(table);  
			WaqDdlIdx idx = new WaqDdlIdx();
			idx.setDdlIdxId(ddlIdxId);
			idx.setRegTypCd(regTypCd);
//			ddlscript += ddlScriptService.getDdlIdxScirpt(idx);  
			ddlscript += ddlScriptService.getWahDdlIdxScirpt(ddlIdxId, rqstNo);
						
		} else if(!UtilString.null2Blank(ddlTblId).equals("")){
			if(!UtilString.null2Blank(regTypCd).equals("")) {
				String tmp = "";
				if("C".equals(regTypCd)) {
					tmp = "CRE";
				} else {
					tmp = "DRP";
				}
				ddlscript = ddlScriptService.getDdlScriptTable(ddlTblId, tmp);  
				
			} else {
				ddlscript = ddlScriptService.getWahDdlTblScript(ddlTblId, rqstNo, null);
			}										
		} else if(!UtilString.null2Blank(ddlSeqId).equals("")) {
			Sequence seq = new Sequence();
			seq.setDdlSeqId(ddlSeqId);
			seq.setRegTypCd(regTypCd);
//			ddlscript = ddlScriptService.getDdlSeqScirpt(seq); 
			ddlscript = ddlScriptService.getWahDdlSeqScirpt(ddlSeqId, rqstNo);
		} else if(!UtilString.null2Blank(ddlEtcId).equals("")){
			ddlscript = ddlScriptService.getWahDdlEtcScript(ddlEtcId, rqstNo);
			
		} else if(!UtilString.null2Blank(ddlPartId).equals("")){
			ddlscript = ddlScriptService.getDDlScriptPartition(ddlPartId, null);
			
		} else if(!UtilString.null2Blank(ddlGrtId).equals("")){
			ddlscript = ddlScriptService.getDdlGrtScript(ddlGrtId);
			
		}
		
		logger.debug("ddlscript >>>> " + ddlscript);
		model.addAttribute("ddlscript", ddlscript);
		
		return "/meta/ddl/ddlscript_dtl";
	}

	/** DDL 스크립트 조회(다건 팝업)
	 * @throws IOException */
	@RequestMapping("/meta/ddl/ajaxgrid/ddlscript_pop_dtl.do")
	public String getDdlScriptPop(String objId, String objDcd, String scrtInfo, String creDrpDcd, String rqstNo, String scrnDcd, String regTypCd, Model model) throws Exception {
//		logger.debug("{}, {}", objId, scrtInfo);
		logger.debug("\n objId: {}", objId);
		logger.debug("\n objDcd: {}", objDcd);
		logger.debug("\n scrtInfo: {}", scrtInfo);
		logger.debug("\n creDrpDcd: {}", creDrpDcd);
		logger.debug("\n rqstNo: {}", rqstNo);
		logger.debug("\n scrnDcd: {}", scrnDcd);
		logger.debug("\n regTypCd: {}", regTypCd);
		
		String ddlScript = "";
		
		if(objId != null) {
			String[] tmpDdlObjId = objId.split("\\|");
			String[] tmpDdlRqstNo = rqstNo.split("\\|");
			if(objDcd != null) {
				String[] tmpDdlObjDcd = objDcd.split("\\|");
				for(int i = 0; i < tmpDdlObjId.length; i++) {
					//ddlScript += ddlScriptService.getScrtInfo(ddlTblId);
					String tmp = tmpDdlObjDcd[i];
					if("TBL".equals(tmp)) {
						if(scrnDcd.equals("DDLPRC")){
							ddlScript += ddlScriptService.getWahDdlTblScript(tmpDdlObjId[i], tmpDdlRqstNo[i], creDrpDcd);
						} else{
							ddlScript += ddlScriptService.getDdlScriptTable(tmpDdlObjId[i], creDrpDcd);
						}
						
					} else if ("IDX".equals(tmp)) {
						WaqDdlIdx idx = new WaqDdlIdx();
						idx.setDdlIdxId(tmpDdlObjId[i]);
						
						if(creDrpDcd != null){
							if("CRE".equals(creDrpDcd)) {
								idx.setRegTypCd("C");
							} else {
								idx.setRegTypCd("U");
							}
						} else {
							idx.setRegTypCd(regTypCd);
						}
						if(scrnDcd.equals("DDLPRC")){
							ddlScript += ddlScriptService.getWahDdlIdxScirpt(tmpDdlObjId[i], tmpDdlRqstNo[i]);
						} else{
							ddlScript += ddlScriptService.getDdlIdxScirpt(idx); 
						} 
						
						
					} else if ("SEQ".equals(tmp)) {
						Sequence seq = new Sequence();
						seq.setDdlSeqId(tmpDdlObjId[i]);
						if("CRE".equals(creDrpDcd)) {
							seq.setRegTypCd("C");
						} else {
							seq.setRegTypCd("U");
						}
//						ddlScript += ddlScriptService.getDdlSeqScirpt(seq);  
						
						ddlScript += ddlScriptService.getWahDdlSeqScirpt(tmpDdlObjId[i], tmpDdlRqstNo[i]);
						
					} else if ("ETC".equals(tmp)) {
						ddlScript += ddlScriptService.getWahDdlEtcScript(tmpDdlObjId[i], tmpDdlRqstNo[i]);
						
						
					} else if ("DDP".equals(tmp)){
						ddlScript += ddlScriptService.getWahDdlPartScript(tmpDdlObjId[i], tmpDdlRqstNo[i]);
						
					}  else if ("GRT".equals(tmp)){
						ddlScript += ddlScriptService.getWahDdlGrtScript(tmpDdlObjId[i], tmpDdlRqstNo[i]);
					}
					
					ddlScript += "\n\r\n";
					
					ddlScript += "\n\r\n\r\n\r\n\r\n\r\n\r";
				}
			} else {
				for(String ddlTblId : tmpDdlObjId) {
					ddlScript += ddlScriptService.getDdlScriptTable(ddlTblId);  
					
					ddlScript += "\n\r\n";
					
					ddlScript += "\n\r\n\r\n\r\n\r\n\r\n\r";
				}
			}
		}
		
		model.addAttribute("ddlscript", ddlScript);
		return "/meta/ddl/popup/ddlscript_pop_dtl";
	}
	
	/** DDL 스크립트 조회(다건 팝업)
	 * @throws IOException */
	@RequestMapping("/meta/ddl/ajaxgrid/ddlwaqscript_pop_dtl.do")
	public String getDdlWaqScriptPop(String scrnDcd, String rqstNo, String rqstSno, String rqstDtlSno, String creDrpDcd, Model model) throws Exception {
		logger.debug("{}, {}", rqstNo, rqstSno); 
		String ddlScript = "";
		
//		logger.debug("\n scrnDcd:" + scrnDcd);
//		logger.debug("\n creDrpDcd:" + creDrpDcd);
		
		String tblChgTypCd = UtilString.null2Blank(creDrpDcd);
		
		
		if(rqstSno != null) {
			String[] arrRqstSno    = rqstSno.split("\\|");
			String[] arrRqstDtlSno = rqstDtlSno.split("\\|");
			String[] arrCreDrpDcd = tblChgTypCd.split("\\|");
			
			logger.debug(rqstSno + " ---- " + rqstDtlSno + " ---- " + tblChgTypCd);
			
			for(int i = 0; i < arrRqstSno.length; i++) {
								
				if (scrnDcd.equals("WAQTBL")){
				
					WaqDdlTbl ddlVo = new WaqDdlTbl();

					if(arrCreDrpDcd !=null && arrCreDrpDcd.length != 0 && i < arrCreDrpDcd.length) {
					   if(arrCreDrpDcd.length == 1)
					   	ddlVo.setTblChgTypCd(arrCreDrpDcd[0]);
					   else
					   	ddlVo.setTblChgTypCd(arrCreDrpDcd[i]); 
					} else {
						if(arrCreDrpDcd.length == 1)
							ddlVo.setTblChgTypCd(arrCreDrpDcd[0]);
					    else
					    	ddlVo.setTblChgTypCd("");
					}
					
					ddlVo.setRqstNo(rqstNo);
					ddlVo.setRqstSno(Integer.parseInt(arrRqstSno[i]));
										
					ddlScript += ddlScriptService.getDdlScirptWaqRqstSno(ddlVo);
				} else if(scrnDcd.equals("WAQIDX")){
					
					WaqDdlIdx idxVo = new WaqDdlIdx();
					
					idxVo.setRqstNo(rqstNo);
					idxVo.setRqstSno(Integer.parseInt(arrRqstSno[i]));
					idxVo.setRqstDtlSno(Integer.parseInt(arrRqstDtlSno[i]));
					
					
					ddlScript += ddlScriptService.getDdlIdxScirptWaqRqstSno(idxVo);
				} else if(scrnDcd.equals("WAQSEQ")){
					WaqDdlSeq seqVo = new WaqDdlSeq();
					seqVo.setRqstNo(rqstNo);
					seqVo.setRqstSno(Integer.parseInt(arrRqstSno[i]));
					
					ddlScript += ddlScriptService.getDdlSeqScriptWaqRqstSno(seqVo);
					
				} else{ // WAQPART
					WaqDdlPart partVo = new WaqDdlPart();

					partVo.setRqstNo(rqstNo);
					partVo.setRqstSno(Integer.parseInt(arrRqstSno[i]));
					
					ddlScript += ddlScriptService.getDDlScriptPartByWaq(partVo, null);
				}
									   					
				ddlScript += "\n\n\n"; 
			}
		}
		
		model.addAttribute("ddlscript", ddlScript);
		return "/meta/ddl/popup/ddlscript_pop_dtl";
	}
	
	/** DDL 테이블 변경이력 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddltblchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDdlTbl> selectDdlTblChangeList(@ModelAttribute("searchVO") WamDdlTbl searchVO, String ddlTblId) throws Exception {

		logger.debug("{}", ddlTblId);
		if(!UtilObject.isNull(ddlTblId)) {
			List<WamDdlTbl> list = ddlTblService.selectDdlTblChangeList(ddlTblId);
			return new IBSheetListVO<WamDdlTbl>(list, list.size());
		} else {
			return null;
		}


	}

	/** DDL 테이블 컬럼목록 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddltblcollist_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDdlCol> selectDdlTblColList(@ModelAttribute("searchVO") WamDdlTbl searchVO) throws Exception {

		logger.debug("{}", searchVO);
//		if(!UtilObject.isNull(ddlTblId)) {
			List<WamDdlCol> list = ddlTblService.selectDdlTblColList(searchVO);
			return new IBSheetListVO<WamDdlCol>(list, list.size());
//		} else {
//			return null;
//		}


	}
	
	/** DDL 테이블 관계목록 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddltblrellist_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDdlRel> selectDdlTblRelList(@ModelAttribute("searchVO") WamDdlTbl searchVO) throws Exception {

		logger.debug("{}", searchVO);
//		if(!UtilObject.isNull(ddlTblId)) {
			List<WamDdlRel> list = ddlTblService.selectDdlTblRelList(searchVO);
			return new IBSheetListVO<WamDdlRel>(list, list.size());
//		} else {
//			return null;
//		}


	}

	/** DDL 테이블 컬럼 상세정보 조회 */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddltblcolinfo_dtl.do")
	public String selectDdlTblColInfoDetail(String ddlColId, String rqstNo, String regTypCd, ModelMap model) {
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(ddlColId) && !UtilObject.isNull(rqstNo)) {
			WamDdlCol tmpCol = new WamDdlCol();
			tmpCol.setDdlColId(ddlColId);
			tmpCol.setRqstNo(rqstNo);
			tmpCol.setRegTypCd(regTypCd);

			WamDdlCol result = ddlTblService.selectDdlTblColInfo(tmpCol);
			model.addAttribute("result", result);
		}
		return "/meta/ddl/ddltblcolinfo_dtl";
	}


	/** DDL 테이블 컬럼 변경이력 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddltblcolchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDdlCol> selectDdlTblColChangeList(@ModelAttribute("searchVO") WamDdlCol searchVO) throws Exception {

		if(!UtilObject.isNull(searchVO.getDdlTblId())) {
			List<WamDdlCol> list = ddlTblService.selectDdlTblColChangeList(searchVO.getDdlTblId());
			return new IBSheetListVO<WamDdlCol>(list, list.size());
		} else {
			return null;
		}


	}
	
	/** DDL 테이블 관계 변경이력 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddltblrelchange_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDdlRel> selectDdlTblRelChangeList(@ModelAttribute("searchVO") WamDdlTbl searchVO) throws Exception {

		if(!UtilObject.isNull(searchVO.getDdlTblId())) {
			List<WamDdlRel> list = ddlTblService.selectDdlTblRelChangeList(searchVO.getDdlTblId());
			return new IBSheetListVO<WamDdlRel>(list, list.size());
		} else {
			return null;
		}


	}

	/** DDL 테이블 인덱스컬럼 리스트 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddltblidxcol_dtl.do")
	@ResponseBody
	public IBSheetListVO<WamDdlIdxCol> selectDdlTblIdxColList(@ModelAttribute("searchVO") WamDdlIdxCol searchVO) throws Exception {
		logger.debug("{}", searchVO);
		//DDL테이블 조회에서는 tblId를, DDL요청에서는 Pnm을 들고 검색
		if(!UtilObject.isNull(searchVO.getDdlTblId()) || !UtilObject.isNull(searchVO.getDdlTblPnm())) {
			List<WamDdlIdxCol> list = ddlTblService.selectDdlTblIdxColList(searchVO);

			return new IBSheetListVO<WamDdlIdxCol>(list, list.size());
		} else {
			return null;
		}


	}


	/** DDL 요청처리 저장 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("/meta/ddl/saveDdlTblRqstPrc.do")
	@ResponseBody
	public IBSResultVO<WamDdlTbl> regList(@RequestBody WamDdlTbls data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<WamDdlTbl> list = data.get("data");
		int result = ddlTblService.saveDdlTblRqstPrc(list);

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WamDdlTbl>(result, resmsg, action);
	}
	
	/** DDL이관테이블 요청처리 저장 - IBSheet JSON 
	 * @throws Exception */
	@RequestMapping("/meta/ddl/saveDdlTblTsfRqstPrc.do")
	@ResponseBody
	public IBSResultVO<WamDdlTsfObj> regList(@RequestBody WamDdlTsfObjs data, Locale locale) throws Exception {
		logger.debug("{}", data);
		ArrayList<WamDdlTsfObj> list = data.get("data");
		int result = ddlTblService.saveDdlTblTsfRqstPrc(list);

		String resmsg;

		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SAVE", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SAVE", null, locale);
		}

		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		return new IBSResultVO<WamDdlTsfObj>(result, resmsg, action);
	}
	
	
	/** DDL 인덱스 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlIdxlist.do")
	@ResponseBody
	public IBSheetListVO<WamDdlIdx> selectDdlIdxList(@ModelAttribute WamDdlIdx search) {
		logger.debug("{}", search);
		List<WamDdlIdx> list = ddlTblService.getIdxList(search);

		return new IBSheetListVO<WamDdlIdx>(list, list.size());
	}
	
	/** DDL 컬럼 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlIdxForColList.do")
	@ResponseBody
	public IBSheetListVO<WamDdlCol> getDdlIdxForColList(@ModelAttribute WamDdlTbl search) {
		logger.debug("{}", search);
		List<WamDdlCol> list = ddlTblService.getDdlIdxForColList(search); 

		return new IBSheetListVO<WamDdlCol>(list, list.size());
	}
	
	/** DDL 인덱스 상세정보 조회 */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddlidxinfo_dtl.do")
	public String selectDdlIdxInfoDetail(String ddlIdxId, String rqstNo, ModelMap model) {
		logger.debug(" {}", ddlIdxId);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(ddlIdxId) && !UtilObject.isNull(rqstNo)) {

			WamDdlIdx result = ddlTblService.selectDdlIdxInfo(ddlIdxId, rqstNo);
			model.addAttribute("result", result);
		}
		return "/meta/ddl/ddlidxinfo_dtl";
	}
	
	/** DDL 인덱스컬럼 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlIdxCollist.do")
	@ResponseBody
	public IBSheetListVO<WamDdlIdxCol> selectDdlIdxColList(@ModelAttribute WamDdlIdxCol search) {
		logger.debug("{}", search);
		List<WamDdlIdxCol> list = ddlTblService.getIdxColList(search);

		return new IBSheetListVO<WamDdlIdxCol>(list, list.size());
	}
	/** DDL 인덱스컬럼 상세정보 조회 */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddlidxcolinfo_dtl.do")
	public String selectDdlIdxColInfoDetail(String ddlIdxColId, String rqstNo, ModelMap model) {
		logger.debug("{}", ddlIdxColId);
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(ddlIdxColId) && !UtilObject.isNull(rqstNo)) {
			WamDdlIdxCol tmpcol = new WamDdlIdxCol();
			tmpcol.setDdlIdxColId(ddlIdxColId);
			tmpcol.setRqstNo(rqstNo);

			WamDdlIdxCol result = ddlTblService.selectDdlIdxColInfo(tmpcol);
			model.addAttribute("result", result);
		}
		return "/meta/ddl/ddlidxcolinfo_dtl";
	}
	
	/** DDL 인덱스 변경이력 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlIdxChange.do")
	@ResponseBody
	public IBSheetListVO<WamDdlIdx> selectDdlIdxChange(@ModelAttribute WamDdlIdx search) {
		logger.debug("{}", search);
		List<WamDdlIdx> list = ddlTblService.getIdxChangeList(search);

		return new IBSheetListVO<WamDdlIdx>(list, list.size());
	}
	
	/** DDL 인덱스 변경이력 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlIdxColChange.do")
	@ResponseBody
	public IBSheetListVO<WamDdlIdxCol> selectDdlIdxColChange(@ModelAttribute WamDdlIdxCol search) {
		logger.debug("{}", search);
		List<WamDdlIdxCol> list = ddlTblService.getIdxColChangeList(search);

		return new IBSheetListVO<WamDdlIdxCol>(list, list.size());
	}
	
	
	/** 이관요청대상 DDL 목록 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/ddl/getDdlTsfTblListForRqst.do")
	@ResponseBody
	public IBSheetListVO<WamDdlTbl> selectDdlTsfTblListForRqst(WamDdlTbl search) throws Exception {

		logger.debug("{}", search);
		
		List<WamDdlTbl> list = ddlTblService.selectDdlTsfTblListForRqst(search);
		return new IBSheetListVO<WamDdlTbl>(list, list.size());    
		
	}
	
	@RequestMapping("/meta/ddl/partitiontbl_lst.do") 
    public String goDdlPartionTblList(@ModelAttribute("search")WamDdlTbl search, Model model) {
        
        logger.debug("search : {}",search);
        
        
        return "/meta/ddl/partitiontbl_lst";
    }
    
    /** 파티션테이블 관리리스트 조회 - IBSheet JSON */
    @RequestMapping("/meta/ddl/getPartitionTblList.do")
    @ResponseBody
    public IBSheetListVO<WamDdlTbl> selectPartitionTblList(@ModelAttribute WamDdlTbl search) {
        logger.debug("{}", search);
        List<WamDdlTbl> list = ddlTblService.getPartitionTblList(search);
        
        return new IBSheetListVO<WamDdlTbl>(list, list.size());
    }
    
    /** DDL 파티션 테이블 요청처리 저장 - IBSheet JSON 
     * @throws Exception */
    @RequestMapping("/meta/ddl/savePartitionTblPrc.do")
    @ResponseBody
    public IBSResultVO<WamDdlTbl> regPartionTblYn(@RequestBody WamDdlTbls data, Locale locale) throws Exception {
        logger.debug("{}", data);
        ArrayList<WamDdlTbl> list = data.get("data");
        //int result = ddlTblService.saveDdlTblRqstPrc(list);
        int result = ddlTblService.savePartitionTblPrc(list);

        String resmsg;

        if(result > 0) {
            result = 0;
            resmsg = message.getMessage("MSG.SAVE", null, locale);
        } else {
            result = -1;
            resmsg = message.getMessage("ERR.SAVE", null, locale);
        }

        String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
        return new IBSResultVO<WamDdlTbl>(result, resmsg, action);
    }
    
	/** DDL 오브젝트 스크립트 컬럼(scrtInfo) 정보 조회
	 * @throws IOException */
	@RequestMapping("/meta/ddl/ajaxgrid/ddlScriptObj_dtl.do")
	public String getDdlScriptObj(String ddlObjId,String ddlObjType, ModelMap model) throws IOException {
		//logger.debug("DDL 오브젝트 스크립트 컬럼:{}/ddlObjId:{}",ddlObjType, ddlObjId);

		if(StringUtils.hasText(ddlObjType)) {
			String ddlscirpt = "";
			if(ddlObjType.equals("IDX")){
//				ddlscirpt = ddlScriptService.getScrtInfoIdx(ddlObjId);
				ddlscirpt = ddlScriptService.getDDlScriptIndexByChgTypCd(ddlObjId, null);
			}else if(ddlObjType.equals("SEQ")){
				ddlscirpt = ddlScriptService.getScrtInfoSeq(ddlObjId);
			}else if(ddlObjType.equals("TBL")){
//				ddlscirpt = ddlScriptService.getScrtInfo(ddlObjId);
				ddlscirpt = ddlScriptService.getDDlScriptTable(ddlObjId, null);
			}else if(ddlObjType.equals("DDP")){
				ddlscirpt = ddlScriptService.getDDlScriptPartition(ddlObjId, null);
//				ddlscirpt = ddlScriptService.getScrtInfoPart(ddlObjId);
			}
			model.addAttribute("ddlscript", ddlscirpt);
		}		
		return "/meta/ddl/ddlobjscript_dtl";
	}
	
	
	/** 파티션신청용 DDL 테이블 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlTblListForPart.do")
	@ResponseBody
	public IBSheetListVO<WamDdlTbl> getDdlTblListForPart(@ModelAttribute WamDdlTbl search) {
		//logger.debug("파티션신청용 DDL 테이블 리스트 조회:{}", search);
		List<WamDdlTbl> list = ddlTblService.getDdlTblListForPart(search);
		
		return new IBSheetListVO<WamDdlTbl>(list, list.size());
	}
	
	@RequestMapping("/meta/ddl/popup/ddlwaqscript_pop.do")
	public String goDdlWaqScriptPop(Model model) {
		return "/meta/ddl/popup/ddlwaqscript_pop";
	}
	
	/** DDL 스크립트 조회(다건 팝업)
	 * @throws IOException */
	@RequestMapping("/meta/ddl/ajaxgrid/getddlpartwaqscriptlist.do")
	@ResponseBody
	public IBSResultVO<WaqDdlPart>  getDdlPartWaqScriptList(@RequestBody WaqDdlParts datas, String chgTypCd, Locale locale) throws Exception {
		ArrayList<WaqDdlPart> ddllist = datas.get("data");
		WaqDdlPart resultvo = new WaqDdlPart();
		int result = 0;
		String ddlScript = "";
		String resmsg = "";
		
		for(WaqDdlPart ddlvo : ddllist) {
			ddlScript += ddlScriptService.getDDlScriptPartByWaq(ddlvo, chgTypCd); 
			ddlScript += "\n\r\n\r\n\r\n\r\n\r\n\r";
		}
		resultvo.setScrtInfo(ddlScript);
		
		/*
		if(result > 0) {
			result = 0;
			resmsg = message.getMessage("MSG.SEARCH", null, locale);
		} else {
			result = -1;
			resmsg = message.getMessage("ERR.SEARCH", null, locale);
		}
		*/
		
		String action = WiseMetaConfig.IBSAction.REG_LIST.getAction();
		
		return new IBSResultVO<WaqDdlPart>(resultvo, result, resmsg, action);
	}
	
	/** DDL 전체오브젝트 조회용 팝업 (DDL전체오브젝트 조회 팝업) @return yeonho */
    @RequestMapping("/meta/ddl/popup/ddlallobjforgrt_pop.do")
    public String goDdlAllObjForGrtPop(@ModelAttribute("search") WamDdlTbl search, Model model, Locale locale) {
		logger.debug("search:{}", search);

		return "/meta/ddl/popup/ddlallobjforgrt_pop";
	}
	
    /** DDL 전체오브젝트 리스트 조회 - IBSheet JSON */
	@RequestMapping("/meta/ddl/getDdlallobjforgrtlist.do")
	@ResponseBody
	public IBSheetListVO<WamDdlTbl> selectDdlAllObjForGrtList(@ModelAttribute WamDdlTbl search) {
		logger.debug("{}", search);
		List<WamDdlTbl> list = ddlTblService.getDdlAllObjForGrtList(search);

		return new IBSheetListVO<WamDdlTbl>(list, list.size());
	}
	
	/** DDL요청관리용 테이블 컬럼목록 조회 -IBSheet json */
	/** meta */
	@RequestMapping("/meta/ddl/ajaxgrid/ddltblcollist_dtl_forwah.do")
	@ResponseBody
	public IBSheetListVO<WamDdlCol> selectDdlTblColListForWah(@ModelAttribute("searchVO") WamDdlTbl searchVO) throws Exception {

		logger.debug("{}", searchVO);
//		if(!UtilObject.isNull(ddlTblId)) {
			List<WamDdlCol> list = ddlTblService.selectDdlTblColListForWah(searchVO);
			return new IBSheetListVO<WamDdlCol>(list, list.size());
//		} else {
//			return null;
//		}


	}

}
