package kr.wise.dq.result.vrfcrule.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.util.UtilString;
import kr.wise.dq.bizrule.service.BizruleService;
import kr.wise.dq.bizrule.service.WamBrMstr;
import kr.wise.dq.profile.mstr.service.ProfileMstrService;
import kr.wise.dq.profile.mstr.service.WamPrfMstrMapper;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.mstr.web.ProfileMstrCtrl;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelTblVO;
import kr.wise.dq.report.service.DataPatternService;
import kr.wise.dq.report.service.DataPatternVO;
import kr.wise.dq.result.service.ResultVO;
import kr.wise.dq.result.vrfcrule.service.VrfcruleResultMapper;
import kr.wise.dq.result.vrfcrule.service.VrfcruleResultService;
import kr.wise.dq.result.vrfcrule.service.VrfcruleResultVO;
import kr.wise.dq.vrfcrule.service.VrfcruleErrService;
import kr.wise.dq.vrfcrule.service.VrfcruleVO;
import kr.wise.dq.vrfcrule.sqlgenerator.VrfcSqlGeneratorMng;
import kr.wise.dq.vrfcrule.sqlgenerator.VrfcSqlGeneratorVO;
import kr.wise.dq.vrfcrule.web.VrfcruleErrCtrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

@Service("VrfcruleResultService")
public class VrfcruleResultServiceImpl implements VrfcruleResultService{
	
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private VrfcruleResultMapper vrfcruleResultMapper;
	
	@Inject
	private WamPrfMstrMapper wamPrfMstrMapper;	
	
	@Inject
	private ProfileMstrCtrl profileMstrCtrl;
	
	@Inject
	private VrfcruleErrCtrl vrfcruleErrCtrl;	
	
	//데이터패턴 조회
	@Inject
	private DataPatternService DptrnService;
	
	//프로파일 마스터
	@Inject
	private ProfileMstrService profileMstrService;
	//검증룰 sql조회
	@Inject
	private VrfcruleErrService vrfcruleErrService;
	
	@Inject
	BizruleService bizruleService;
	
	
	@Override
	public List<ResultVO> getVrfcruleResultList(ResultVO search) {
		return vrfcruleResultMapper.selectResultListWithRule(search);
	}
	
	@Override
	public VrfcruleResultVO getTargetTableStatus(VrfcruleResultVO data) {
		return vrfcruleResultMapper.selectAnaTargetTableStatus(data);
	}
	
	@Override
	public VrfcruleResultVO getAnaExecuteStatus(VrfcruleResultVO data) {
		return vrfcruleResultMapper.selectAnaExecuteStatus(data);
	}
	
	@Override
	public List<VrfcruleResultVO> getValAnaResultList(VrfcruleResultVO data) {
		return vrfcruleResultMapper.selectValAnaResultList(data);
	}
	
	/**
	 * 진단대상테이블
	 */
	@Override
	public List<VrfcruleResultVO> getTargetTableList(VrfcruleResultVO data) {
		return  vrfcruleResultMapper.selectAnaTargetTableList(data);
	}
	
	/**
	 * 도메인
	 */
	@Override
	public List<VrfcruleResultVO> getDmnList(VrfcruleResultVO data) {
		
		//수집데이터 5건 불러오기
		List<VrfcruleResultVO> prfIdList = vrfcruleResultMapper.selectDmnList(data);
//		for(VrfcruleResultVO vo : prfIdList) {
//			logger.debug("vo.getObjId() : "+vo.getObjId());
//			if(vo.getObjId() != null){
//				vo.setPrfId(vo.getObjId());
//				VrfcruleResultVO dataPtr = vrfcruleResultMapper.selectResultData(vo);
//				logger.debug("dataPtr : "+dataPtr);
//				if(dataPtr != null){
//					vo.setDataPtr(dataPtr.getDataPtr());
//				}
//				
//			}
//			
//		}
		return  prfIdList;
	}
	
	/**
	 * 참조무결성
	 */
	@Override
	public List<VrfcruleResultVO> getRefIgList(VrfcruleResultVO data) {
		return vrfcruleResultMapper.selectRefItegrityList(data);
	}

//	/**
//	 * 필수값완전성
//	 */
//	@Override
//	public List<VrfcruleResultVO> getReqValList(VrfcruleResultVO data) {
//		return vrfcruleResultMapper.selectReqValCmplList(data);
//	}
//
//	/**
//	 * 데이터중복
//	 */
//	@Override
//	public List<VrfcruleResultVO> getDataRdndList(VrfcruleResultVO data) {
//		return vrfcruleResultMapper.selectDataRdndList(data);
//	}

	/**
	 * 업무규칙
	 */
	@Override
	public List<VrfcruleResultVO> getBrList(VrfcruleResultVO data) {
		return vrfcruleResultMapper.selectBrList(data);
	}
	
	/**
	 * 진단항목실행정보
	 */
	@Override
	public List<VrfcruleResultVO> getExcInfoList(VrfcruleResultVO data) {
		return vrfcruleResultMapper.selectExcInfoList(data);
	}
	
	@Override
	public List<VrfcruleResultVO> getExcErrInfoList(VrfcruleResultVO data) {
		return getExcErrInfoList(data, true);
	}
	
	/**
	 * 진단항목오류정보 vrfcSqlGen
	 */
	@Override
	public List<VrfcruleResultVO> getExcErrInfoList(VrfcruleResultVO data, boolean isDetail) {
		
		// 2021/11/12 SDQ보고서 작성용 프로파일정보 조회
		List<VrfcruleResultVO> prfIdList = vrfcruleResultMapper.selectPrfInfo(data);
		Map<String, Object> sqlGenMap = new HashMap<String, Object>();
		
		for(VrfcruleResultVO vo : prfIdList) {
			logger.debug("vo : {}", vo.getObjId());
			logger.debug("vo.getPrfKndCd() : {}", vo.getPrfKndCd());
			
			// 프로파일  
			if(StringUtils.hasText(vo.getPrfKndCd()) && vo.getPrfKndCd().contains("P")) {
				// sql정보
				logger.debug("vo.getObjId() : {}", vo.getObjId());
				logger.debug("vo.getPrfKndCd() : {}", vo.getPrfKndCd());
				
//				vo.setSqlGeneratorVO(getSqlDtlSearchPop(vo.getObjId()));
				vo.setVrfcSqlGeneratorVO(getVrfcSqlDtlSearchPop(vo.getObjId()));
				
				if(vo.getPrfKndCd().equals("PC01")){
					vo.setErrSql(vo.getVrfcSqlGeneratorVO().getNullCountSql());
					continue;
				} else {
					vo.setErrSql(vo.getVrfcSqlGeneratorVO().getErrorPattern());
				}
				
				// 데이터패턴
				vo.setDataPatternVO(new DataPatternVO());
				vo.getDataPatternVO().setObjId(vo.getObjId());
				vo.getDataPatternVO().setObjDate(vo.getObjDate());
				vo.getDataPatternVO().setObjIdCol("PRF_ID");
				vo.getDataPatternVO().setObjResTbl("WAM_PRF_RESULT");
				vo.getDataPatternVO().setObjErrTbl("WAM_PRF_ERR_DATA");
				vo.getDataPatternVO().setErDataSnoCol("ESN_ER_DATA_SNO");
				vo.getDataPatternVO().setObjGb(vo.getPrfKndCd());
				vo.getDataPatternVO().setErDataSno(0);
				
				
			}
			// 업무규칙
			else {
				
				// 데이터패턴
				vo.setDataPatternVO(new DataPatternVO());
				vo.getDataPatternVO().setObjId(vo.getObjId());
				vo.getDataPatternVO().setObjDate(vo.getObjDate());
				vo.getDataPatternVO().setObjIdCol("BR_ID");
				vo.getDataPatternVO().setObjResTbl("WAM_BR_RESULT");
				vo.getDataPatternVO().setObjErrTbl("WAM_BR_ERR_DATA");
				vo.getDataPatternVO().setErDataSnoCol("ER_DATA_SNO");
				
//				logger.debug("vo.getDataPatternVO() : {}", vo.getDataPatternVO());
//				// 컬럼정보 셋팅
//				WamBrMstr brMst = new WamBrMstr();
//				brMst.setBrId(vo.getObjId());
//				logger.debug(brMst.getBrId());
//				bizDtlPopup(brMst, vo.getDataPatternVO(), null);
//				// 오류데이터 추출 - 데이터패턴
			}
			logger.debug("vo.getDataPatternVO() : {}", vo.getDataPatternVO());
			// 컬럼정보 셋팅
			gotestPop(vo.getDataPatternVO());
			// 오류데이터 추출 - 데이터패턴
			
			// 값진단종합결과인 경우  
			// 구분자 ','로 한 row에 담는다 
			if(isDetail) {
				vo.setErrData(getDataPattern(vo.getDataPatternVO()));
			}
			// 오류상세인경우
			// 리스트로 담는다
//			else {
//				List<DataPatternVO> list = DptrnService.getDataPattern(vo.getDataPatternVO());
//				vo.setDataPatternVoList(list);
//			}
			
			
		}
		
		return prfIdList;
	}
	
	// 2021/11/15 SDQ보고서 작성위해서...
	private SqlGeneratorVO getSqlDtlSearchPop(String prfId) {

		WamPrfMstrVO  prfmstrVO = profileMstrService.getSqlGenDbmsInfoByPrfId(prfId);
		SqlGeneratorVO sqlVO = profileMstrCtrl.getSqlGen(prfmstrVO);
		return sqlVO;
	}
	
	// 2021/11/15 SDQ보고서 작성위해서...
	private VrfcSqlGeneratorVO getSqlDtlSearchPop2(String prfId) {
		Map<String, Object> sqlGenMap = new HashMap<String, Object>();
		WamPrfMstrVO  prfmstrVO = vrfcruleErrService.getSqlGenDbmsInfoByPrfId(prfId);
		//SQL 생성
		VrfcSqlGeneratorMng sqlGenMng = new VrfcSqlGeneratorMng(); 
		VrfcSqlGeneratorVO sqlVO = sqlGenMng.getSql(sqlGenMap);
		return sqlVO;
	}
	
	private VrfcSqlGeneratorVO getVrfcSqlDtlSearchPop(String prfId) {
		//SQL 생성
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
		if(vrfcTyp.toUpperCase().equals("FRM")) {
			sqlVO.setErrorCount("정규식 Pattern : " + vrfcRule);
		}
		
		
		return sqlVO;
	}
	/**
	 * 오류데이터 추출
	 * 
	 * @param search
	 * @return
	 */
	private void gotestPop(DataPatternVO search) {

		logger.debug("{오류데이터추출 search : }", search);
		
		int headerCnt = 0;
		String headerText = new String();
		//IBSHEET 컬럼 건수 조회
		DataPatternVO headerCntVO = DptrnService.DptrnHeaderInit(search);

		if( null !=headerCntVO ){
			headerCnt = headerCntVO.getColCnt();

			//WAM_PRF_ER_DATA 테이블 컬럼명 생성
			String headerColNm = new String();
			StringBuffer colNm = new StringBuffer() ;
			StringBuffer tmpColNm = new StringBuffer() ;

			if(headerCnt > 0){
				for(int i=1; i<=headerCnt; i++){
					colNm.append(", COL_NM" + i);
					tmpColNm.append("COL_NM" + i + "||" + "'|'" + "||" );
				}
				headerColNm = tmpColNm.toString();
				search.setHeaderTextColNm(headerColNm.substring(0, headerColNm.length()-7));

				//IBSHEET 헤더텍스트 조회
				DataPatternVO headerTextVO = DptrnService.DptrnHeaderText(search);
				headerText = headerTextVO.getHeaderText();
			}
			if(DptrnService.getPrfcScript(search) != null){
				search.setPrfcScript(DptrnService.getPrfcScript(search));
			}

			search.setColNm(colNm.toString());
			search.setColCnt(headerCnt);
			search.setHeaderText(headerText);

		}
		// return search;
	}
	
	//업무규칙 상세 팝업
	public void bizDtlPopup(WamBrMstr searchVO, DataPatternVO search, Model model) {

		logger.debug(" {}", searchVO);

		WamBrMstr result = bizruleService.getBizruleDtlList(searchVO);

		model.addAttribute("result", result);

		//데이터패턴 추가...
		int headerCnt = 0;
		String headerText = new String();
		//IBSHEET 컬럼 건수 조회
		DataPatternVO headerCntVO = DptrnService.DptrnHeaderInit(search);
		
		if( null !=headerCntVO ){
			headerCnt = headerCntVO.getColCnt()  ;
			
			//WAM_PRF_ER_DATA 테이블 컬럼명 생성
			String headerColNm = new String() ;
			StringBuffer colNm = new StringBuffer() ;
			StringBuffer tmpColNm = new StringBuffer() ;	
			
			if(headerCnt > 0){
				for(int i=1; i<=headerCnt; i++){
					colNm.append(", COL_NM" + i);
					tmpColNm.append("COL_NM" + i + "||" + "'|'" + "||" );
				}
				headerColNm = tmpColNm.toString();
				search.setHeaderTextColNm(headerColNm.substring(0, headerColNm.length()-7));
				
				//IBSHEET 헤더텍스트 조회
				DataPatternVO headerTextVO = DptrnService.DptrnHeaderText(search);
				headerText = headerTextVO.getHeaderText();
			}
			
			search.setColNm(colNm.toString());
			search.setColCnt(headerCnt);
			search.setHeaderText(headerText);
		}
		
	}
	
	private String getDataPattern(DataPatternVO search) {
		
		StringBuilder sb = new StringBuilder();
		
		List<DataPatternVO> list = DptrnService.getDataPattern(search);
		
		for(int i = 0; i < 10; i++) {
			if(i >= list.size()){
				break;
			}else if(i != 0){
				sb.append(","); 
			}
			
			sb.append(list.get(i).getColNm1());
			logger.debug(sb.toString());
		}
		
		return sb.toString();
	}
	
	
	/**
	 * 오류데이터 패턴을 하나의 문자열로 합친다.
	 * map형태로 반환한다.
	 * 
	 * ex) vo객체에 값이 이하와 같이 저장되어 있다면 
	 * colNm1 | colNm2 | colNm3 | colNm4 
	 * ----------------------------------
	 *  ex1   |  ex2   |  ex3   |  3
	 *  
	 *  errData = "ex1;ex2;ex3"
	 *  cnt = "3"
	 *  으로 반환한다.
	 *  
	 * @param vo 오류데이터 패턴 vo
	 * @return map key - errData, value - 오류데이터 문자열
	 *             key - cnt    , value - 오류건수  
	 */
	public Map<String, String> getErrDataMap (DataPatternVO vo) {
		Map<String, String> map = new HashMap<String, String>();
		
		String getMethodName = "getColNm";
		String errData = "";
		String cnt = "";
		try {
			Class<?> c = vo.getClass();
			// 오류데이터 문자열
			for(int i = 1; i < vo.getColCnt(); i++) { 
				errData += c.getMethod(getMethodName + i).invoke(vo) + ";";
			}
			errData = errData.length() > 0 ? errData.substring(0, errData.length() - 1) : errData;
			// 오류건수
			Method getMethod = c.getMethod(getMethodName + vo.getColCnt());
			cnt = (String) getMethod.invoke(vo);
			
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		map.put("errData", errData);
		map.put("cnt", cnt);
		
		return map;
	}


	
}
