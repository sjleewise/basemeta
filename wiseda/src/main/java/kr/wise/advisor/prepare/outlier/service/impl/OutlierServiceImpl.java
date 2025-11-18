/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : OutlierServiceImpl.java
 * 2. Package : kr.wise.advisor.prepare.outlier.service.impl
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 26. 오후 4:40:05
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 26. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.outlier.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.admin.ai.algorithm.service.WaaAlg;
import kr.wise.admin.ai.algorithm.service.WaaAlgArg;
import kr.wise.admin.ai.algorithm.service.WaaAlgArgMapper;
import kr.wise.advisor.prepare.dataset.service.DataSetService;
import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.dataset.service.WadAnaVarMapper;
import kr.wise.advisor.prepare.dataset.service.WadDataSet;

import kr.wise.advisor.prepare.outlier.service.OutScatterData;
import kr.wise.advisor.prepare.outlier.service.OutlierService;
import kr.wise.advisor.prepare.outlier.service.WadOtlArg;
import kr.wise.advisor.prepare.outlier.service.WadOtlArgMapper;
import kr.wise.advisor.prepare.outlier.service.WadOtlChartData;
import kr.wise.advisor.prepare.outlier.service.WadOtlData;
import kr.wise.advisor.prepare.outlier.service.WadOtlDataMapper;
import kr.wise.advisor.prepare.outlier.service.WadOtlDtc;
import kr.wise.advisor.prepare.outlier.service.WadOtlDtcMapper;
import kr.wise.advisor.prepare.outlier.service.WadOtlDtcVo;
import kr.wise.advisor.prepare.outlier.service.WadOtlResult;
import kr.wise.advisor.prepare.outlier.service.WadOtlResultMapper;
import kr.wise.advisor.prepare.outlier.service.WadOtlVal;
import kr.wise.advisor.prepare.outlier.service.WadOtlValMapper;

import kr.wise.advisor.prepare.summary.service.WadVarSum;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.criinfo.dqi.service.WamDqiMapper;
import kr.wise.dq.report.service.DataPatternVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : OutlierServiceImpl.java
 * 3. Package  : kr.wise.advisor.prepare.outlier.service.impl
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 26. 오후 4:40:05
 * </PRE>
 */
@Service("outlierService")
public class OutlierServiceImpl implements OutlierService{

	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	@Inject
	private WaaAlgArgMapper argmapper;
	
	@Inject
	private WadOtlDtcMapper mapper;
	
	@Inject
	private WadOtlValMapper valmapper;
	
	@Inject
	private WadOtlArgMapper otlargmapper;
	
	@Inject
	private WadOtlDataMapper datamapper;
	
	@Inject
	private WadOtlResultMapper resmapper;
	
	@Inject
	private WadAnaVarMapper varmapper;
	
	@Inject
    private EgovIdGnrService objectIdGnrService;
	
	@Inject
	private DataSetService dataSetService;

	
	/** insomnia */
	public List<WaaAlgArg> getAlgParam(WaaAlg search) {
		logger.debug("알고리즘변수목록 조회:{}", search.getAlgId());
		
		return argmapper.selectArgListbyId(search);
	}

	/** insomnia 
	 * @throws Exception */
	public int regOutlierDetection(WadOtlDtc saveotl, List<WadAnaVar> list) throws Exception {
		logger.debug("이상값탐지 등록 [데이터셋ID:{}][품질지표ID:{}]", saveotl.getDaseId(), saveotl.getDqiId());
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		saveotl.setWritUserId(userid);
		
		//기존 이상값탐지ID 존재여부 확인 후 저장한다.		
		//WadOtlDtc comvo = mapper.selectbydsnalg(saveotl);
		
		if (StringUtils.hasText(saveotl.getOtlDtcId())) {
		
			saveotl.setOtlDtcId(saveotl.getOtlDtcId());
			saveotl.setIbsStatus("U");
			result = mapper.updateByPrimaryKey(saveotl);
		} else {
			String id = objectIdGnrService.getNextStringId();
			saveotl.setOtlDtcId(id);
			saveotl.setIbsStatus("I");
			result = mapper.insert(saveotl);
		}
		
		//이상값탐지 알고리즘 파라미터 삭제...
		otlargmapper.deleteByotlDtcId(saveotl);
		
		//이상값탐지 알고리즘 파라미터가 존재할 경우 추가...
		String[] algArgIds = saveotl.getAlgArgIds();
		String[] argVals = saveotl.getArgVals();
		
		logger.debug("이상값탐지 알고리즘변수 등록 [파라미터id:{}][파라미터값:{}]", algArgIds, argVals);
		
		if (algArgIds != null &&  algArgIds.length > 0) {
			for(int i = 0; i<algArgIds.length; i++ ) {
				WadOtlArg argvo = new WadOtlArg();
				argvo.setWritUserId(userid);
				argvo.setIbsStatus("I");
				argvo.setOtlDtcId(saveotl.getOtlDtcId());
				argvo.setAlgArgId(algArgIds[i]);
				argvo.setArgVal(argVals[i]);
				
				result =+ regOtlArg(argvo);
				
			}
		}
		
		logger.debug("이상값탐지 변수목록저장 [탐지ID:{}]", saveotl.getOtlDtcId());
		//이상값탐지ID에 해당하는 변수를 삭제 후 등록한다.
		
		for (int i =0 ; i < list.size(); i++) {
			
			WadAnaVar varvo = list.get(i);
			
			WadOtlVal savevo = new WadOtlVal();

			savevo.setIbsStatus("I");
						
			savevo.setWritUserId(userid);
			savevo.setOtlDtcId(saveotl.getOtlDtcId());
			savevo.setAnlVarId(varvo.getAnlVarId());
			savevo.setVarSno(varvo.getVarSno());
			
			savevo.setOtlAddCnd(varvo.getOtlAddCnd());
			savevo.setAnaVarChk(varvo.getIbsCheck());
			
			savevo.setCondCd(varvo.getCondCd());
			savevo.setOprCd(varvo.getOprCd());
			savevo.setOprVal(varvo.getOprVal());
			
			savevo.setWeight(varvo.getWeight());
			
			int iRtn = valmapper.updateByPrimaryKey(savevo);
						
			if(iRtn == 0){
				result = valmapper.insert(savevo);	  
			}
			//result =+ regOtlVal(savevo);
		}
		
		//품질지표 insert
		
		//delete
		result = mapper.deleteWamOtlDqiMap(saveotl.getOtlDtcId());
		//INSERT
		String[] dqiLnms = UtilString.null2Blank(saveotl.getDqiLnm()).split("[,]");
		String[] dqiIds  = UtilString.null2Blank(saveotl.getDqiId()).split("[,]");
		
		for(int i=0; i<dqiLnms.length; i++){
			saveotl.setDqiLnm(dqiLnms[i]);
			saveotl.setDqiId(dqiIds[i]);
			result += mapper.insertWamOtlDqiMap(saveotl);
		}
		
		
		//OTL_NM 업데이트
		result =+ mapper.updateOtlNm(saveotl);
		
		return result;
	}

	/** @param argvo insomnia */
	private int regOtlArg(WadOtlArg argvo) {
		int result = 0;
		String tmpsts = argvo.getIbsStatus();
		
		if ("I".equals(tmpsts)) {
			result = otlargmapper.insert(argvo);
			
		} else if ("U".equals(tmpsts)) {
			result = otlargmapper.updateByPrimaryKey(argvo);
			
		} else if ("D".equals(tmpsts)) {
			result = otlargmapper.deleteByPrimaryKey(argvo.getOtlDtcId(), argvo.getAlgArgId());
		}
		
		return result;
	}

	/** @param savevo
	/** @return insomnia */
	private int regOtlVal(WadOtlVal savevo) {
		int result = 0;
		String tmpsts = savevo.getIbsStatus();
		
		if ("I".equals(tmpsts)) {
			result = valmapper.insert(savevo);
			
		} else if ("U".equals(tmpsts)) {
			result = valmapper.updateByPrimaryKey(savevo);
			
		} else if ("D".equals(tmpsts)) {
			result = valmapper.deleteByvo(savevo);
		}
		return result;
	}


	/** @param resvo
	/** @return insomnia */
	private int regOltResult(WadOtlResult savevo) {
		int result = 0;
		
		String tmpsts = savevo.getIbsStatus();
		
		if ("I".equals(tmpsts)) {
			result = resmapper.insertSelective(savevo);
		} else if ("U".equals(tmpsts)) {
			result = resmapper.updateByPrimaryKey(savevo);
		} else if ("D".equals(tmpsts)) {
			result = resmapper.deleteByresvo(savevo);
		}
		
		return result;
	}

	/** insomnia */
	public List<WadOtlResult> getOutlierResult(WadOtlDtc search) {
		logger.debug("이상값탐지 결과 조회:{}", search.getOtlDtcId());
		
		return resmapper.selectResultbyId(search);
	}

	/** insomnia */
	public List<WadAnaVar> getAlgVarList(WadOtlDtc search) {
		logger.debug("이상값탐지대상 컬럼(변수)목록 조회 :{}", search.getOtlDtcId());
		return varmapper.selectVarListbyOutlier(search);
	}
	/** insomnia */
	public List<WadAnaVar> getOtlVarList(WadOtlDtc search) {
		logger.debug("이상값탐지대상 컬럼(변수)목록 조회 :{}", search.getOtlDtcId());
		return varmapper.selectVarListbyOtl(search);
	}

	/** insomnia */
	public List<WadOtlData> getOutDataList(WadOtlDtc search) {
		logger.debug("이상값탐지 결과 데이터 조회:{}", search.getOtlDtcId());
//		return datamapper.selectDatabydtcId(search);
		
		//단변량일 경우 컬럼리스트 전체를 조회한다.
		//이상값 탐지 알고리즘, 알고리즘파라미터, 탐지 대상 목록을 가져온다...
//		WadOtlDtcVo requestvo = mapper.selectOtlDct(search);
		
		//단변량의 경우 전체를 조회한다.
		return datamapper.selectDatabydtcId(search);
		
	}



	/** insomnia */
	public List<WadOtlData> getOutDataColList(WadOtlDtc search) {
		logger.debug("이상값탐지 결과 데이터 컬럼리스트 조회:{}", search.getOtlDtcId());
		
		return datamapper.selectDataColList(search);
	}

	/** insomnia */
	public List<WadOtlChartData> getOutChartData(WadOtlDtc search) {
		logger.debug("이상값 탐지 차트용 데이터 조회:{}", search.getOtlDtcId());
		
		List<WadOtlChartData> reslist = new ArrayList<WadOtlChartData>();
		
		//이상값 탐지 알고리즘, 알고리즘파라미터, 탐지 대상 목록을 가져온다...
		WadOtlDtcVo requestvo = mapper.selectOtlDct(search);
		
		//단변량의 경우 boxplot grpc를 호출한다.
		if ("단변량".equals(requestvo.getAlg().getAlgLnm())) {
//			result = callBoxGrpc(tgtdbms, requestvo);
			//변수목록을 가져온다...
			List<WadAnaVar> varlist = requestvo.getVarlist();
			for (WadAnaVar varvo : varlist) {
				WadOtlChartData resvo = new WadOtlChartData();
				varvo.setDbSchPnm(search.getDbSchPnm());
				List<Float> flist = datamapper.selectColData(varvo, search.getDbSchPnm());
				resvo.setColNm(varvo.getDbColNm());
				resvo.setColId(varvo.getAnlVarId());
				resvo.setColVal(flist);
				
				reslist.add(resvo);
			}
		} else {
			
			
			
		}
		
		
		return reslist;
	}
	
	
	/** insomnia */
	/*
	public OutScatterData getOutScatterData(WadOtlDtc search) {
		logger.debug("이상값탐지용 scatter chart 데이터조회:{}", search.getOtlDtcId());
		
		List<WadOtlChartData> reslist = new ArrayList<WadOtlChartData>();
		
		//이상값 탐지 알고리즘, 알고리즘파라미터, 탐지 대상 목록을 가져온다...
		WadOtlDtcVo requestvo = mapper.selectOtlDct(search);
		
		//다변량일 경우 outliergrpc호출
		//이상값탐지 클라이언튼 생성...
		WadDataSet ds = new WadDataSet();
		ds.setSchDbSchId(search.getDbSchId());
		
		//histogram를 호출할 dbms정보 및 테이블 정보를 조회한다.....
		WaaDbConnTrgVO tgtdbms = dataSetService.getTgtDbmsbyds(ds);
		
		//grpc 클라이언트 생성 후 outlier 호출
		String host = message.getMessage("wiseda.grpc.host.python", null, Locale.getDefault()).trim();
		String port = message.getMessage("wiseda.grpc.port.python", null, Locale.getDefault()).trim();
		
		OutlierClient client = new OutlierClient(host, Integer.parseInt(port));
		
		OdResponse odres = client.callOutlierGrpc(tgtdbms, requestvo);
		
		//이상값여부 결과리스트
		List<Boolean> outynlist = odres.getResultsList();

		List<ColResponse> colresList = odres.getColresList();
		
		String varId = null;
		int colsize = 0;
		if (!CollectionUtils.isEmpty(colresList) ) {
//			varId = colresList.get(0).getVarId();
			colsize = colresList.size();
		}
		
		
		List<List<Float>> inlier = new ArrayList<List<Float>>();
		List<List<Float>> outlier = new ArrayList<List<Float>>();
		if (colsize == 2) {
			logger.debug("컬럼갯수가 2개인 경우만 처리한다....");
			for (int i=0;i<colsize;i++) {
				inlier.add(new ArrayList<Float>());
				outlier.add(new ArrayList<Float>());
			}
			
		} else {
			return null;
		}
		
		int cnt = 0;
		for (Boolean outyn : outynlist) {
			
			if (outyn) { //정상범위 값인 경우 inlier에 추가
				for (int i=0;i<colsize;i++) {
					inlier.get(i).add(colresList.get(i).getColvalList().get(cnt));
				}
			} else { //이상값인 경우 outlier에 추가
				for (int i=0;i<colsize;i++) {
					outlier.get(i).add(colresList.get(i).getColvalList().get(cnt));
				}
			}
			
			cnt++;
		}
		
		OutScatterData result = new OutScatterData();
		
		result.setInlier(inlier);
		result.setOutlier(outlier);
		
		
		return result;
	}
	*/

	/** insomnia */
	public List<WadOtlData> getOutDataList2(WadOtlDtc search) {
		logger.debug("다변량 이상값 데이터 목록 조회:{}", search.getOtlDtcId());

		//이상값 탐지 알고리즘, 알고리즘파라미터, 탐지 대상 목록을 가져온다...
//		WadOtlDtcVo requestvo = mapper.selectOtlDct(search);
		
		return datamapper.selectDataAll(search.getOtlDtcId());
		
	}

	/** insomnia */
	public int delOutlierDetection(WadOtlDtc search) {
		int result = 0;
		String dtcid = search.getOtlDtcId();
		result = mapper.deleteByPrimaryKey(dtcid);
		
		valmapper.deleteByotlDtcId(dtcid);
		otlargmapper.deleteByotlDtcId(search);
		
		//품질지표 삭제
		result = mapper.deleteWamOtlDqiMap(dtcid);
		
		return result;
	}

	/** insomnia */
	public WadOtlResult getOutlierResultByPatternVO(DataPatternVO search) {
		return resmapper.selectResultbyPatternVO(search);
	}

	/** insomnia */
	public List<WadOtlData> getOutDataColNm(DataPatternVO search) {
		
		return datamapper.selectColNm(search);
	}

	/** insomnia */
	public List<WadOtlData> getOutDataMulti(DataPatternVO pattvo) {
		
		return datamapper.selectDataList(pattvo);
	}

	/** insomnia */
	public List<WadOtlData> getOutDataListOne(WadOtlDtc search, DataPatternVO pattvo) {
		
		return datamapper.selectDataOne(search, pattvo);
	}

	/** insomnia */
	public List<WaaAlgArg> getAlgParamById(WadOtlResult search) {
		return argmapper.selectArgListbyDtcId(search);
	}

	@Override
	public List<WadOtlData> getOutDataAnlVarId(DataPatternVO search) {
		// TODO Auto-generated method stub
		List<WadOtlResult> worList = datamapper.selectAnlVarId(search);
		List<WadOtlData> result = new ArrayList<WadOtlData>();
		
		for(int i=0; i<worList.size(); i++) {
			WadOtlData wod = new WadOtlData();
			WadOtlResult wor = worList.get(i);

			wod.setAnlVarId(wor.getAnlVarId());
			wod.setOtlDtcId(wor.getOtlDtcId());
			wod.setAnaStrDtm(wor.getAnaStrDtm());
			
			result.add(wod);
		}
		
		return result;
	}

	@Override
	public int updateOtlYn(List<WadOtlData> list) {
		// TODO Auto-generated method stub
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		for (WadOtlData savevo : list) {
			//savevo.setWritUserId(userid);
			result =+ datamapper.updateOtlYn(savevo);
		}
		
		return result;
	}

	@Override
	public int updateOtlRpl(WadOtlData data) {
		// TODO Auto-generated method stub
		int res = datamapper.updateOtlRpl(data);
		
		res = datamapper.deleteOtlDataRpl(data);
		
		if(data.getOtlRpl() == null || data.getOtlRpl().equals(""))
			return res;
		
		data.setOtlRplVal1("OTL_DTC_ID, ANA_STR_DTM, OTL_SNO, ANL_VAR_ID, COL_CNT, OTL_YN, OTL_RPL");
		data.setOtlRplVal2("A.OTL_DTC_ID, A.ANA_STR_DTM, A.OTL_SNO, A.ANL_VAR_ID, A.COL_CNT, A.OTL_YN, A.OTL_RPL");
		
		String str1 = "";
		String str2 = "";
		String str3 = "";
		
		if("01".equals(data.getOtlRpl())) {
			for(int i=0; i<data.getColCnt(); i++) {
				str1 += ", COL_NM" + (i+1);
				str2 += ", B.COL_NM" + (i+1);
				
				if(i == data.getColCnt()-1)
					str3 += "MAX(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1);
				else
					str3 += "MAX(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1) + ",";
			}
			
			data.setOtlRplVal1(data.getOtlRplVal1() + str1);
			data.setOtlRplVal2(data.getOtlRplVal2() + str2);
			data.setOtlRplVal3(str3);
		} else if("02".equals(data.getOtlRpl())) {
			for(int i=0; i<data.getColCnt(); i++) {
				str1 += ", COL_NM" + (i+1);
				str2 += ", B.COL_NM" + (i+1);
				
				if(i == data.getColCnt()-1)
					str3 += "MIN(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1);
				else
					str3 += "MIN(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1) + ",";
			}
			
			data.setOtlRplVal1(data.getOtlRplVal1() + str1);
			data.setOtlRplVal2(data.getOtlRplVal2() + str2);
			data.setOtlRplVal3(str3);
		} else if("03".equals(data.getOtlRpl())) {
			for(int i=0; i<data.getColCnt(); i++) {
				str1 += ", COL_NM" + (i+1);
				str2 += ", 'INVALID'";
			}
			
			data.setOtlRplVal1(data.getOtlRplVal1() + str1);
			data.setOtlRplVal2(data.getOtlRplVal2() + str2);
		} else if("04".equals(data.getOtlRpl())) {
			for(int i=0; i<data.getColCnt(); i++) {
				str1 += ", COL_NM" + (i+1);
				str2 += ", 'DEL'";
			}
			
			data.setOtlRplVal1(data.getOtlRplVal1() + str1);
			data.setOtlRplVal2(data.getOtlRplVal2() + str2);
		} else if("05".equals(data.getOtlRpl())) {
			for(int i=0; i<data.getColCnt(); i++) {
				str1 += ", COL_NM" + (i+1);
				str2 += ", B.COL_NM" + (i+1);
				
				if(i == data.getColCnt()-1)
					str3 += "STATS_MODE(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1);
				else
					str3 += "STATS_MODE(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1) + ",";
			}
			
			data.setOtlRplVal1(data.getOtlRplVal1() + str1);
			data.setOtlRplVal2(data.getOtlRplVal2() + str2);
			data.setOtlRplVal3(str3);
		} else if("06".equals(data.getOtlRpl())) {
			for(int i=0; i<data.getColCnt(); i++) {
				str1 += ", COL_NM" + (i+1);
				str2 += ", B.COL_NM" + (i+1);
				
				if(i == data.getColCnt()-1)
					str3 += "AVG(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1);
				else
					str3 += "AVG(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1) + ",";
			}
			
			data.setOtlRplVal1(data.getOtlRplVal1() + str1);
			data.setOtlRplVal2(data.getOtlRplVal2() + str2);
			data.setOtlRplVal3(str3);
		} else if("07".equals(data.getOtlRpl())) {
			for(int i=0; i<data.getColCnt(); i++) {
				str1 += ", COL_NM" + (i+1);
				str2 += ", B.COL_NM" + (i+1);
				
				if(i == data.getColCnt()-1)
					str3 += "MEDIAN(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1);
				else
					str3 += "MEDIAN(TO_NUMBER(COL_NM" + (i+1) + ")) AS COL_NM" + (i+1) + ",";
			}
			
			data.setOtlRplVal1(data.getOtlRplVal1() + str1);
			data.setOtlRplVal2(data.getOtlRplVal2() + str2);
			data.setOtlRplVal3(str3);
		}
		
		res = datamapper.insertOtlDataRpl(data);
		
		return res;
	}

	@Override
	public int requestOutlier(WadOtlDtc search) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OutScatterData getOutScatterData(WadOtlDtc search) {
		// TODO Auto-generated method stub
		return null;
	}
}
