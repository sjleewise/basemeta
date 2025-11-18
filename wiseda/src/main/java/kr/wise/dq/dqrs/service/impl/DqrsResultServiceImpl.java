package kr.wise.dq.dqrs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.dq.dqrs.service.DqrsDbConnTrgVO;
import kr.wise.dq.dqrs.service.DqrsPoiGapResult;
import kr.wise.dq.dqrs.service.DqrsPoiResult;
import kr.wise.dq.dqrs.service.DqrsResult;
import kr.wise.dq.dqrs.service.DqrsResultMapper;
import kr.wise.dq.dqrs.service.DqrsResultService;
import kr.wise.dq.profile.mstr.service.ProfileMstrService;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.mstr.web.ProfileMstrCtrl;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.report.service.DataPatternService;
import kr.wise.dq.report.service.DataPatternVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("dqrsResultServiceImpl")
public class DqrsResultServiceImpl implements DqrsResultService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private DqrsResultMapper dqrsResultMapper;
	
	//데이터패턴 조회
	@Inject
	private DataPatternService DptrnService;
	
	//프로파일 마스터
	@Inject
	private ProfileMstrCtrl profileMstrCtrl;
	@Inject
	private ProfileMstrService profileMstrService;
	
	@Override
	public List<DqrsResult> getDqrsResult(DqrsResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectDqrsResult(search);
	}

	@Override
	public DqrsPoiResult getAnaExecuteStatus(DqrsPoiResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectAnaExecuteStatus(search);
	}

	@Override
	public DqrsDbConnTrgVO getByDbms(DqrsDbConnTrgVO search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectByDbms(search);
	}

	@Override
	public DqrsPoiResult getTargetTableStatus(DqrsPoiResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectAnaTargetTableStatus(search);
	}

	@Override
	public List<DqrsPoiResult> getValAnaResultList(DqrsPoiResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectValAnaResultList(search);
	}

	@Override
	public List<DqrsPoiResult> getTargetTableList(DqrsPoiResult search) {
		// TODO Auto-generated method stub
		return  dqrsResultMapper.selectAnaTargetTableList(search);
	}

	@Override
	public List<DqrsPoiResult> getDmnList(DqrsPoiResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectDmnList(search);
	}

	@Override
	public List<DqrsPoiResult> getRefIgList(DqrsPoiResult data) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectRefItegrityList(data);
	}

	@Override
	public List<DqrsPoiResult> getBrList(DqrsPoiResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectBrList(search);
	}

	@Override
	public List<DqrsPoiResult> getExcInfoList(DqrsPoiResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectExcInfoList(search);
	}

	@Override
	public List<DqrsPoiResult> getExcErrInfoList(DqrsPoiResult search) {
		// TODO Auto-generated method stub
		return getExcErrInfoList(search, true);
	}

	@Override
	public String getDbConnTrgURL(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectDbConnTrgURL(search);
	}

	@Override
	public DqrsPoiGapResult getGovCnt(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectGovCnt(search);
	}

	@Override
	public DqrsPoiGapResult getStndTerm(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectStndDtm(search);
	}

	@Override
	public List<DqrsPoiGapResult> getGovSditmTblList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectGovSditmTblList(search);
	}

	@Override
	public List<DqrsPoiGapResult> getGovSditmList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectGovSditmList(search);
	}

	@Override
	public List<DqrsPoiGapResult> getGovDmnList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectGovDmnList(search);
	}

	@Override
	public List<DqrsPoiGapResult> getStructResultList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectStructResultList(search);
	}

	@Override
	public List<DqrsPoiGapResult> getDbcTblList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectDbcTblList(search);
	}

	@Override
	public DqrsPoiGapResult getModelTerm(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectModelDtm(search);
	}

	@Override
	public List<DqrsPoiGapResult> getDbcColList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectDbcColList(search);
	}

	@Override
	public List<DqrsPoiGapResult> getPdmTblList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectPdmTblList(search);
	}

	@Override
	public List<DqrsPoiGapResult> getPdmColList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectPdmColList(search);
	}

	@Override
	public List<DqrsPoiGapResult> getTblDbList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectTblDbList(search);
	}

	@Override
	public List<DqrsPoiGapResult> getColDbList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectColDbList(search);
	}

	
	@Override
	public List<DqrsPoiResult> getExcErrInfoList(DqrsPoiResult data, boolean isDetail) {
		
		// 2021/11/12 SDQ보고서 작성용 프로파일정보 조회
		List<DqrsPoiResult> prfIdList = dqrsResultMapper.selectPrfInfo(data);
		Map<String, Object> sqlGenMap = new HashMap<String, Object>();
		
		for(DqrsPoiResult vo : prfIdList) {
			
			// 프로파일  
			if(StringUtils.hasText(vo.getPrfKndCd()) && vo.getPrfKndCd().contains("P")) {
				// sql정보
				logger.debug("vo.getObjId() : {}", vo.getObjId());
				
				vo.setSqlGeneratorVO(getSqlDtlSearchPop(vo.getObjId()));
				
				if(vo.getPrfKndCd().equals("PC01")){
					vo.setErrSql(vo.getSqlGeneratorVO().getNullCountSql());
					continue;
				} else {
					vo.setErrSql(vo.getSqlGeneratorVO().getErrorPattern());
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
			
			
		}
		
		return prfIdList;
	}
	
	private SqlGeneratorVO getSqlDtlSearchPop(String prfId) {

		WamPrfMstrVO  prfmstrVO = profileMstrService.getSqlGenDbmsInfoByPrfId(prfId);
		SqlGeneratorVO sqlVO = profileMstrCtrl.getSqlGen(prfmstrVO);
		
		return sqlVO;
	}
	
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

	@Override
	public List<DqrsPoiGapResult> getGovStructResultList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectGovStructResultList(search);
	}

	@Override
	public List<DqrsPoiGapResult> getGovDbcTblList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectGovDbcTblList(search);
	}

	@Override
	public List<DqrsPoiGapResult> getGovDbcColList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectGovDbcColList(search);
	}

	@Override
	public List<DqrsPoiGapResult> getGovTblList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectGovTblList(search);
	}

	@Override
	public List<DqrsPoiGapResult> getGovColList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectGovColList(search);
	}

	@Override
	public List<DqrsPoiGapResult> getGovTblDbList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectGovTblDbList(search);
	}

	@Override
	public List<DqrsPoiGapResult> getGovColDbList(DqrsPoiGapResult search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectGovColDbList(search);
	}
	
	@Override
	public List<DqrsDbConnTrgVO> getInfoSys(DqrsDbConnTrgVO search) {
		// TODO Auto-generated method stub
		return dqrsResultMapper.selectInfoSys(search);
	}

	@Override
	public int updateInfoSys(ArrayList<DqrsDbConnTrgVO> list) {
		// TODO Auto-generated method stub
		int result = 0;
		
		for(DqrsDbConnTrgVO saveVo : list){
			result += dqrsResultMapper.updateInfoSys(saveVo);
		}
		
		return result;
	}

}