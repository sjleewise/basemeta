package kr.wise.meta.mapping.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.UtilJson;
import kr.wise.meta.mapping.service.MapListService;
import kr.wise.meta.mapping.service.MapListVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : MapListCtrl.java
 * 3. Package  : kr.wise.meta.mapping.web
 * 4. Comment  : 
 * 5. 작성자   : jyson(손준영)
 * 6. 작성일   : 2014. 7. 30. 오후 1:27:41
 * </PRE>
 */

@Controller
public class MapListCtrl {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private CodeListService codeListService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	private Map<String, Object> codeMap;
	
	@Inject
	private MapListService mapListCtrl;
	
	
	/**테이블 매핑정의서 페이지*/
	/**  @return jyson */
	@RequestMapping("/meta/mapping/tblmap_lst.do")
	public String goTblMap_lst(){
		return "/meta/mapping/tblmap_lst";
	}
	
	/** 테이블 매핑정의서 조회 */
	@RequestMapping("/meta/mapping/getTblMap.do")
	@ResponseBody
	public IBSheetListVO<MapListVO> getTblMap(@ModelAttribute MapListVO search) throws Exception{
		logger.debug("{}", search);
	
		List<MapListVO> list = mapListCtrl.getTblMap(search);
		
		return new IBSheetListVO<MapListVO>(list, list.size());
	}
	
	/** 컬럼 매핑정의서 GAP 조회 */
	@RequestMapping("/meta/mapping/getColMapGap.do")
	@ResponseBody
	public IBSheetListVO<MapListVO> getColMapGap(@ModelAttribute MapListVO search) throws Exception{
		logger.debug("{}", search);
		
		List<MapListVO> list = mapListCtrl.getColMapGap(search);
		
		return new IBSheetListVO<MapListVO>(list, list.size());
	}
	
	/** 컬럼 매핑정의서 GAP 조회 */
	@RequestMapping("/meta/mapping/getColMapGapDtl.do")
	@ResponseBody
	public IBSheetListVO<MapListVO> getColMapGapDtl(@ModelAttribute MapListVO search) throws Exception{
		logger.debug("{}", search);
		
		List<MapListVO> list = mapListCtrl.getColMapGapDtl(search);
		
		return new IBSheetListVO<MapListVO>(list, list.size());
	}
	
	/**컬럼 매핑정의서 GAP 페이지*/
	/**  @return  */
	@RequestMapping("/meta/mapping/mapgap_lst.do")
	public String goMapGap_lst(){
		return "/meta/mapping/mapgap_lst";
	}
	
	/**컬럼 매핑정의서 GAP 상세 페이지*/
	/**  @return  */
	@RequestMapping("/meta/mapping/mapgapdtl_lst.do")
	public String goMapGapDtl_lst(){
		return "/meta/mapping/mapgapdtl_lst";
	}
	
	/**컬럼 매핑정의서 페이지*/
	/**  @return jyson */
	@RequestMapping("/meta/mapping/colmap_lst.do")
	public String goColMap_lst(){
		return "/meta/mapping/colmap_lst";
	}
	
	/** 컬럼 매핑정의서 조회 */
	@RequestMapping("/meta/mapping/getColMap.do")
	@ResponseBody
	public IBSheetListVO<MapListVO> getColMap(@ModelAttribute MapListVO search) throws Exception{
		logger.debug("{}", search);
	
		List<MapListVO> list = mapListCtrl.getColMap(search);
		
		return new IBSheetListVO<MapListVO>(list, list.size());
	}
	
	/** 코드 매핑정의서 페이지*/
	/**  @return yeonho */
	@RequestMapping("/meta/mapping/codemap_lst.do")
	public String codemappage(){
		return "/meta/mapping/codmap_lst";
	}
	
	/** 코드 매핑정의서 조회 */
	@RequestMapping("/meta/mapping/getCodeMap.do")
	@ResponseBody
	public IBSheetListVO<MapListVO> getCodeMap(@ModelAttribute MapListVO search) throws Exception{
		logger.debug("{}", search);
	
		List<MapListVO> list = mapListCtrl.getCodeMap(search);
		
		return new IBSheetListVO<MapListVO>(list, list.size());
	}
	
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {
	
		codeMap = new HashMap<String, Object>();
		//업무구분코드
	//	codeMap.put("bizDcdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DCD")));
	//	codeMap.put("rqstDcd", cmcdCodeService.getCodeList("RQST_DCD"));
		
		//매핑정의서유형
		codeMap.put("mapDfTypCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("MAP_DF_TYP_CD")));
		codeMap.put("mapDfTypCd", cmcdCodeService.getCodeList("MAP_DF_TYP_CD"));
		//타겟이행유형
		codeMap.put("tgtFlfTypCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TGT_FLF_TYP_CD")));
		codeMap.put("tgtFlfTypCd", cmcdCodeService.getCodeList("TGT_FLF_TYP_CD"));
		//테이블매핑유형
		codeMap.put("tblMapTypCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("TBL_MAP_TYP_CD")));
		codeMap.put("tblMapTypCd", cmcdCodeService.getCodeList("TBL_MAP_TYP_CD"));
		
		//컬럼매핑유형
		codeMap.put("colMapTypCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("COL_MAP_TYP_CD")));
		codeMap.put("colMapTypCd", cmcdCodeService.getCodeList("COL_MAP_TYP_CD"));
		
		//코드전환구분코드
		codeMap.put("cdCnvsTypeibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_CNVS_TYPE")));
		codeMap.put("cdCnvsType", cmcdCodeService.getCodeList("CD_CNVS_TYPE"));
		
		//코드매핑유형코드
		codeMap.put("cdMapTypeibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_MAP_TYPE")));
		codeMap.put("cdMapType", cmcdCodeService.getCodeList("CD_MAP_TYPE"));
		
		
		//코드전환유형
		codeMap.put("cdCnvsTypCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_CNVS_TYPE")));
		codeMap.put("cdCnvsTypCd", cmcdCodeService.getCodeList("CD_CNVS_TYPE"));
		
		//코드매핑유형
		codeMap.put("codMapTypCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_MAP_TYPE")));
		codeMap.put("codMapTypCd", cmcdCodeService.getCodeList("CD_MAP_TYPE"));

		//코드값유형
		codeMap.put("cdValTypCd", cmcdCodeService.getCodeList("CD_VAL_TYP_CD"));
		codeMap.put("cdValTypCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_TYP_CD")));
		
		//코드값부여방식
		codeMap.put("cdValIvwCd", cmcdCodeService.getCodeList("CD_VAL_IVW_CD"));
		codeMap.put("cdValIvwCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_IVW_CD")));
		
		//컬럼매핑정의서 GAP 상태
		codeMap.put("mapColGap", cmcdCodeService.getCodeList("MAP_COL_GAP"));
		codeMap.put("mapColGapibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("MAP_COL_GAP")));
		
		//목록성 코드(시스템영역 코드리스트)
		String dmngibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("dmng"));
		String infotpibs 	= UtilJson.convertJsonString(codeListService.getCodeListIBS("infotp"));
		String dmnginfotp 		= UtilJson.convertJsonString(codeListService.getCodeList("dmnginfotp"));

		codeMap.put("dmngibs", dmngibs);
		codeMap.put("infotpibs", infotpibs);
		codeMap.put("dmnginfotp", dmnginfotp);
		codeMap.put("infotpId", codeListService.getCodeList("infotp"));

		//공통 코드(요청구분 코드리스트)
		String bizdtlcd = UtilJson.convertJsonString(cmcdCodeService.getCodeList("BIZ_DTL_CD"));
		String bizdtlcdibs = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("BIZ_DTL_CD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String cdValIvwCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_IVW_CD"));
		String cdValTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("CD_VAL_TYP_CD"));

		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("cdValIvwCd", cmcdCodeService.getCodeList("CD_VAL_IVW_CD"));
		codeMap.put("cdValTypCd", cmcdCodeService.getCodeList("CD_VAL_TYP_CD"));
		codeMap.put("bizdtlcd", bizdtlcd);

		codeMap.put("bizdtlcdibs", bizdtlcdibs);
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("cdValIvwCdibs", cdValIvwCd);
		codeMap.put("cdValTypCdibs", cdValTypCd);
		
		return codeMap;
	}

}
