package kr.wise.dq.vrfcrule.web;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.SearchVO;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.util.ConnectionHelper;
import kr.wise.commons.util.UtilJson;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.metadmn.service.MetaDmnCdValItfVO;
import kr.wise.dq.metadmn.service.MetaDmnItfVO;
import kr.wise.dq.metadmn.service.MetaInterfaceService;
import kr.wise.dq.profile.colefvaana.service.ProfilePC02Service;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfVO;
import kr.wise.dq.profile.mstr.service.ProfileMstrService;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.reac.service.ProfileReacService;
import kr.wise.dq.profile.reac.service.WamPrfReacColVO;
import kr.wise.dq.profile.reac.service.WamPrfReacTblVO;
import kr.wise.dq.profile.tblrel.service.ProfilePT01Service;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelTblVO;
import kr.wise.dq.profile.tblunq.service.ProfilePT02Service;
import kr.wise.dq.profile.tblunq.service.WamPrfUnqColVO;
import kr.wise.dq.report.service.DataPatternService;
import kr.wise.dq.report.service.DataPatternVO;
import kr.wise.dq.vrfcrule.service.VrfcruleErrService;
import kr.wise.dq.vrfcrule.service.VrfcruleErrVO;
import kr.wise.dq.vrfcrule.service.VrfcruleVO;
import kr.wise.dq.vrfcrule.sqlgenerator.VrfcSqlGeneratorMng;
import kr.wise.dq.vrfcrule.sqlgenerator.VrfcSqlGeneratorVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : MenuManageCtrl.java
 * 3. Package  : kr.wise.commons.sysmgmt.menu.web
 * 4. Comment  : 메뉴 관리 컨트롤러
 * 5. 작성자   : insomnia(장명수)
 * 6. 작성일   : 2013. 12. 24. 오후 2:05:16
 * </PRE>
 */ 
@Controller
@RequestMapping("/dq/vrfcrule")

public class VrfcruleErrCtrl {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Map<String, Object> codeMap;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private CodeListService codelistService;
	
	//데이터패턴 조회
	@Inject
	private DataPatternService DptrnService;
	
	@Inject
	private VrfcruleErrService vrfcruleErrService;
	
	//메타도메인정보조회
	@Inject
	private MetaInterfaceService metaItfService;
		
	
	@Inject
	private ProfileReacService reacService;
	
	@Inject
	private MessageSource message;
	
	//관계분석
	@Inject
	private ProfilePT01Service pt01Service;

	//코드분석
	@Inject
	private ProfilePC02Service pc02Service;
	
	//프로파일 마스터
	@Inject
	private ProfileMstrService profileMstrService;

	
	/** 검증룰리스트 조회 폼 */
	@RequestMapping("/vrfcrule_err_lst.do")
	public String selectVrfc(@ModelAttribute("searchVO") SearchVO searchVO, ModelMap model) throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = UserDetailHelper.isAuthenticated();
    	if(!isAuthenticated) {
//    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	//return "egovframework/com/uat/uia/EgovLoginUsr";
    	}
		return "/dq/vrfcrule/vrfcrule_err_lst";
	}
	
	/** 검증룰리스트 조회 -IBSheet json */
	@RequestMapping("/selectVrfcErrList.do")
	@ResponseBody
	public IBSheetListVO<VrfcruleErrVO> selectVrfcList(@ModelAttribute("searchVO") VrfcruleErrVO search) throws Exception {
		logger.debug("selectVrfcList");
		logger.debug("serach >>> " + search.toString());
		
		List<VrfcruleErrVO> list = vrfcruleErrService.selectVrfcList(search);
		
		return new IBSheetListVO<VrfcruleErrVO>(list, list.size()); 
	}
	
	/** 메뉴 상세 정보 조회 */
	@RequestMapping("/selectVrfcErrDetail.do")
	public String selectVrfcErrDetail(String vrfcId, String saction, ModelMap model) {
		logger.debug(" {}", vrfcId);

		//신규 입력으로 초기화
		model.addAttribute("saction", "I");
		//메뉴 ID가 있을 경우 메뉴정보를 조회 하고 업데이트로 변경
		if(!UtilObject.isNull(vrfcId)) {

			VrfcruleErrVO result = vrfcruleErrService.selectVrfcDetail(vrfcId);
			model.addAttribute("result", result);
		}
		
		return "/dq/vrfcrule/vrfcrule_err_dtl";
	}
	
	//검증룰  상세 팝업
	@RequestMapping("/popup/vrfcdtl_pop.do")
	public String vrfcDtlPop(@ModelAttribute("search") VrfcruleErrVO search,@ModelAttribute("dpVo") DataPatternVO dpVo, ModelMap model) {
		logger.debug("search++ : {}", search);
		VrfcruleErrVO result = vrfcruleErrService.selectMstrByPrfId(search);
		model.addAttribute("search", result);
		
		int headerCnt = 0;
		String headerText = new String();
		//IBSHEET 컬럼 건수 조회
		DataPatternVO headerCntVO = DptrnService.DptrnHeaderInit(dpVo);

		if( null !=headerCntVO ){
			headerCnt = headerCntVO.getColCnt()  ;

			//WAM_PRF_ER_DATA 테이블 컬럼명 생성
			String headerColNm = new String() ;
			// String headerColNmMaria = new String() ;			//MARIA용
			// StringBuffer tmpColNmMaria = new StringBuffer() ;	//MARIA용
			StringBuffer colNm = new StringBuffer() ;
			StringBuffer tmpColNm = new StringBuffer("( ") ;  //ORACLE 버전으로 수정 

			if(headerCnt > 0){
				for(int i=1; i<=headerCnt; i++){
					colNm.append(", COL_NM" + i);
					//tmpColNmMaria.append(", COL_NM" + i + ", '|'"); //MARIA용
					if(i==headerCnt){
						tmpColNm.append("COL_NM" + i + ")"); // ORACLE 버전으로 수정
					}
					else{
						tmpColNm.append("COL_NM" + i + "||" + "'|'" + "||" ); // ORACLE 버전으로 수정
					}
					
				}
				
				headerColNm = tmpColNm.toString();
				//search.setHeaderTextColNm(headerColNm.substring(0, headerColNm.length()-7));
				dpVo.setHeaderTextColNm(headerColNm); // ORACLE 버전으로 수정
				// headerColNmMaria = tmpColNmMaria.toString();		//MARIA용
				// search.setHeaderTextColNmMaria("CONCAT(".concat(tmpColNmMaria.substring(1, headerColNmMaria.length()-5).concat(")")));	//MARIA용

				//IBSHEET 헤더텍스트 조회
				DataPatternVO headerTextVO = DptrnService.DptrnHeaderText(dpVo);
				headerText = headerTextVO.getHeaderText();
			}

			dpVo.setColNm(colNm.toString());
			dpVo.setColCnt(headerCnt);
			dpVo.setHeaderText(headerText);

			model.addAttribute("headerVO", dpVo);
		}
		
		return "/dq/vrfcrule/popup/vrfcdtl_pop";
	}
	
	@RequestMapping("/ajaxgrid/sql_dtl.do")
	@ResponseBody
	public VrfcSqlGeneratorVO getSqlDtlSearchPop(String prfId,  ModelMap model  ) {
		
		Map<String, Object> sqlGenMap = new HashMap<String, Object>();

		//진단대상 DBMS_TYP_CD 조회
		VrfcruleVO  vrfcVO = vrfcruleErrService.getSqlGenDbmsInfoByRuleRelId(prfId); 
		
		String prfKndCd    = vrfcVO.getPrfKndCd();
		
		String vrfcId      = vrfcVO.getVrfcId();
		String vrfcNm      = UtilString.null2Blank(vrfcVO.getVrfcNm());
		String vrfcRule    = UtilString.null2Blank(vrfcVO.getVrfcRule());
		String vrfcDescn   = UtilString.null2Blank(vrfcVO.getVrfcDescn());
		String vrfcTyp     = UtilString.null2Blank(vrfcVO.getVrfcTyp());
		String cdSql       = UtilString.null2Blank(vrfcVO.getCdSql());
		
		String dbConnTrgId = vrfcVO.getDbConnTrgId();
		String dbmsTypCd   = vrfcVO.getDbmsTypCd();

		logger.debug(" ===== dbms type  : "+ dbmsTypCd);
		logger.debug(" ===== prf knd cd : "+ prfKndCd);
		logger.debug(" ===== vrfcNm     : "+ vrfcNm);
		logger.debug(" ===== vrfcRule   : "+ vrfcRule);
		logger.debug(" ===== vrfcDescn  : "+ vrfcDescn);
		logger.debug(" ===== vrfcTyp    : "+ vrfcTyp);
		logger.debug(" ===== cdSql      : "+ cdSql);
		
		sqlGenMap.put("vrfcVO", vrfcVO);
		
		String cntSql="";    
		String anaSql="";    
		String errCntSql=""; 
		
		if(vrfcNm.equals("PT01")){
			//관계분석 상세
			WamPrfRelTblVO result = vrfcruleErrService.getPrfPT01Dtl(prfId);
			//관계컬럼
			if(null != result){
				List<WamPrfRelColVO> list = vrfcruleErrService.getPrfPT01RelColLst(prfId);
				result.setWamPrfRelColVO((ArrayList<WamPrfRelColVO>) list);
			}
			sqlGenMap.put("prfDtlVO", result);
			
			//진단대상 DBMS_TYP_CD 조회
			WamPrfMstrVO  prfmstrVO = vrfcruleErrService.getSqlGenDbmsInfoByPrfId(prfId);
							
			sqlGenMap.put("prfMstrVO", prfmstrVO);
		} else if(vrfcNm.equals("BR")){//업무규칙
			VrfcSqlGeneratorVO brInfo = vrfcruleErrService.getCntSql(prfId);
			
			cntSql    = brInfo.getCntRtSql();
			anaSql    = brInfo.getErrorPattern();
			errCntSql = "SELECT COUNT(1) FROM (\n"+anaSql +"\n) ERR_CNT";
		}
				
		//SQL 생성
		VrfcSqlGeneratorMng sqlGenMng = new VrfcSqlGeneratorMng(); 
		VrfcSqlGeneratorVO sqlVO = sqlGenMng.getSql(sqlGenMap); 
		
		if(vrfcNm.equals("BR")){
			sqlVO.setTotalCount(cntSql);
			sqlVO.setErrorPattern(anaSql);
			sqlVO.setErrorCount(errCntSql);
		}
		
		sqlVO.setRuleRelId(prfId); 
		sqlVO.setPrfKndCd(prfKndCd);
		sqlVO.setDbConnTrgId(dbConnTrgId);
		sqlVO.setVrfcNm(vrfcNm);
		sqlVO.setVrfcRule(vrfcRule);
		sqlVO.setVrfcId(vrfcId);
		sqlVO.setVrfcTyp(vrfcTyp);
//		if(vrfcTyp.toUpperCase().equals("FRM")) {
//			sqlVO.setErrorCount("정규식 Pattern : " + vrfcRule);
//		}
		
		
		return sqlVO;

	}

	/** 공통코드 및 목록성 코드리스트를 가져온다. */
	@ModelAttribute("codeMap")
	public Map<String, Object> getcodeMap() {

		codeMap = new HashMap<String, Object>();

		//시스템영역 코드리스트 JSON
		codeMap.put("vrfcTyp", cmcdCodeService.getCodeList("VRFC_TYP"));
		codeMap.put("vrfcTypibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRFC_TYP")));

		//시스템영역 코드리스트 JSON
		String rqstDcd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("RQST_DCD"));
		String regTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("REG_TYP_CD"));
		String dbmsTypCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_TYP_CD"));
		String dbmsVersCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("DBMS_VERS_CD"));
		//프로파일종류코드
		String prfKndCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("PRF_KND_CD"));

		String anaStsCd = UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("JOBSTSCD"));

		//진단대상
		List<CodeListVo> connTrgDbms = codelistService.getCodeList(CodeListAction.connTrgDbms);

		//실행차수
		List<CodeListVo> anaDgr = codelistService.getCodeList(CodeListAction.anaDgr);

		//form 태그 사용
		codeMap.put("regTypCd", cmcdCodeService.getCodeList("REG_TYP_CD"));
		codeMap.put("dbmsTypCd",cmcdCodeService.getCodeList("DBMS_TYP_CD"));
		codeMap.put("dbmsVersCd",cmcdCodeService.getCodeList("DBMS_VERS_CD"));
		codeMap.put("prfKndCd",cmcdCodeService.getCodeList("PRF_KND_CD"));
		codeMap.put("rngCnc",cmcdCodeService.getCodeList("RNG_CNC_CD"));
		//유효값유형
		codeMap.put("efvaAnaKndCd",cmcdCodeService.getCodeList("EFVA_ANA_KND_CD"));
		//유효값유형
		codeMap.put("efvaAnaKndCdibs", UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("EFVA_ANA_KND_CD")));
		//날짜형식코드
		codeMap.put("dateFrmCd",cmcdCodeService.getCodeList("DATE_FRM_CD"));
		//범위분석코드
		codeMap.put("rngOprCd",cmcdCodeService.getCodeList("RNG_OPR_CD"));
		codeMap.put("connTrgDbmsCd", connTrgDbms);
		//실행차수
		codeMap.put("anaDgrCd", anaDgr);
		
		//결재방식코드
		codeMap.put("vrfCdibs",  UtilJson.convertJsonString(cmcdCodeService.getCodeListIBS("VRF_CD")));
		codeMap.put("vrfCd", cmcdCodeService.getCodeList("VRF_CD"));

		//공통코드 - IBSheet Combo Code용
		codeMap.put("anaStsCdibs", anaStsCd);
		codeMap.put("rqstDcdibs", rqstDcd);
		codeMap.put("regTypCdibs", regTypCd);
		codeMap.put("dbmsTypCdibs", dbmsTypCd);
		codeMap.put("dbmsVersCdibs", dbmsVersCd);
		//프로파일종류 IB시트 콤보
		codeMap.put("prfKndCdibs", prfKndCd);
		codeMap.put("connTrgDbmsibs", UtilJson.convertJsonString(codelistService.getCodeListIBS(connTrgDbms)));

		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSch   = UtilJson.convertJsonString(codelistService.getCodeList("connTrgSchId"));
		codeMap.put("connTrgSch", connTrgSch);
		//진단대상/스키마 정보(double_select용 목록성코드)
		String connTrgSchIdByOnwer   = UtilJson.convertJsonString(codelistService.getCodeList("connTrgSchIdByOwner"));
		codeMap.put("connTrgSchIdByOnwer", connTrgSchIdByOnwer);
		
		return codeMap;
	}
}
