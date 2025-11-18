/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : SummaryServiceImpl.java
 * 2. Package : kr.wise.advisor.prepare.summary.service.impl
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 21. 오후 7:29:47
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 21. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.summary.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import kr.wise.advisor.prepare.dataset.service.DataSetService;
import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.dataset.service.WadDataSet;
import kr.wise.advisor.prepare.domain.grpc.DmnPredictClient;
import kr.wise.advisor.prepare.domain.grpc.DmnPredictResult;
import kr.wise.advisor.prepare.summary.grpc.Summary;
import kr.wise.advisor.prepare.summary.grpc.SummaryClient;
import kr.wise.advisor.prepare.summary.service.SummaryService;
import kr.wise.advisor.prepare.summary.service.WadVarHst;
import kr.wise.advisor.prepare.summary.service.WadVarHstMapper;
import kr.wise.advisor.prepare.summary.service.WadVarSum;
import kr.wise.advisor.prepare.summary.service.WadVarSumMapper;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : SummaryServiceImpl.java
 * 3. Package  : kr.wise.advisor.prepare.summary.service.impl
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 21. 오후 7:29:47
 * </PRE>
 */
@Service("summaryService")
public class SummaryServiceImpl implements SummaryService {

	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	@Inject
	private WadVarSumMapper mapper;
	
	@Inject
	private WadVarHstMapper hstmapper;
	
	@Inject
	private DataSetService dataSetService;

	/** insomnia */
	public WadVarSum getSummaryDtl(WadAnaVar search) {
		logger.debug("컬럼 summary 조회  by anaid:{}", search.getAnlVarId());
		
		return mapper.selectByPrimaryKey(search.getAnlVarId());
	}

	/** insomnia */
	public int regSummarybyDs(WadDataSet search) {
		logger.debug("테이블 summary 조회 후 저장:{}", search.getDaseId());
		int result = 0 ;
		
		//summary를 호출할 dbms정보 및 테이블 정보를 조회한다.....
		WaaDbConnTrgVO tgtdbms = dataSetService.getTgtDbmsbyds(search);
		String tblnm = search.getSchDbcTblNm();
		
		//summary grpc를 호출한다.
		result = callSummaryGrpc(tgtdbms, search);
		
		
		return result;
	}
	
	/** insomnia 
	 * @param tgtdbms */
	public int callSummaryGrpc(WaaDbConnTrgVO tgtdbms, WadDataSet dataset) {
		logger.debug("Summary grpc 호출  by Tablerqst:{},{}", tgtdbms, dataset.getSchDbcTblNm());
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		//조회할 테이블 명 셋팅
		String tblnm = dataset.getSchDbcTblNm().toLowerCase();
		
		//summary 저장을 위해 데이터셋의 변수목록 정보 조회 후 맵으로 저장..
		List<WadAnaVar> varlist =  dataSetService.getAnaVarListbyDsId(dataset);
		HashMap<String, WadAnaVar> varMap = new HashMap<String, WadAnaVar>();
		for (WadAnaVar varvo : varlist) {
			varMap.put(varvo.getDbcColNm().toUpperCase(), varvo);
		}

		//grpc 클라이언트 생성 후 getSummary 호출
		String host = message.getMessage("wiseda.grpc.host.python", null, Locale.getDefault()).trim();
		String port = message.getMessage("wiseda.grpc.port.python", null, Locale.getDefault()).trim();
		
		SummaryClient client = new SummaryClient(host, Integer.parseInt(port));
		
		//결과 리스트를 가져온다.
		List<Summary> reslist = client.CallSummarygrpc(tgtdbms, tblnm);
		for (Summary resvo : reslist) {
			//변수목록에 있는지 확인 후 저장한다. (컬럼명을 비교하여 있는 경우 summary 정보 셋팅 후 저장)
			if(varMap.containsKey(resvo.getName().toUpperCase())){
				WadAnaVar tmpvarvo = varMap.get(resvo.getName().toUpperCase());
				
				WadVarSum savevo = new WadVarSum();
				savevo.setIbsStatus(tmpvarvo.getIbsStatus());
				savevo.setAnlVarId(tmpvarvo.getAnlVarId());
				savevo.setVarType(resvo.getType());
				savevo.setTotCnt(UtilString.getString(resvo.getCount()) );
				savevo.setMdnVal(UtilString.getString(resvo.getQ2()) ); 
				savevo.setMaxVal(UtilString.getString(resvo.getMax()) );
				savevo.setTopVal(resvo.getTop());
				savevo.setFrqVal(UtilString.getString(resvo.getFreq()) );
				savevo.setMinVal(UtilString.getString(resvo.getMin()) );
				savevo.setUnqVal(UtilString.getString(resvo.getUnique()) );
				savevo.setMnVal(UtilString.getString(resvo.getMean()) );
				savevo.setStdDvt(UtilString.getString(resvo.getStd()) );
				savevo.setQrtCnt1(UtilString.getString(resvo.getQ1()) );
				savevo.setQrtCnt3(UtilString.getString(resvo.getQ3()) );
				savevo.setWritUserId(userid);
				
				result =+ regSummary(savevo);
			}
			
		}	
			
		return result;
	}

	/** @param savevo
	/** @return insomnia */
	private int regSummary(WadVarSum savevo) {
		int result = 0;
		
		String tmpsts = savevo.getIbsStatus();
		//신규일 경우....
		if ("I".equals(tmpsts)) {
			result = mapper.insertSelective(savevo);
		} else if ("U".equals(tmpsts)) {
			result = mapper.updateByPrimaryKey(savevo);
		} else if ("D".equals(tmpsts)) {
			result = mapper.deleteByPrimaryKey(savevo.getAnlVarId());
		}
	
		return result;
	}

	/** insomnia */
	public List<WadVarSum> getHistoDtl(WadAnaVar search) {
		logger.debug("변수 히스토그램 상세정보 조회 by id:{}", search.getAnlVarId());
		
		return hstmapper.selectHistoListbyId(search);
	}

	/** insomnia */
	public int regHistobyVar(WadAnaVar search) {
		logger.debug("히스토그램 저장 by 변수ID:{}", search.getAnlVarId());
		int result = 0;
		
		WadDataSet ds = new WadDataSet();
		ds.setSchDbSchId(search.getDbSchId());
		
		//histogram를 호출할 dbms정보 및 테이블 정보를 조회한다.....
		WaaDbConnTrgVO tgtdbms = dataSetService.getTgtDbmsbyds(ds);
				
		//histogram grpc를 호출한다.
		result = callHistogramGrpc(tgtdbms, search);
		
		return result;
	}

	/** @param tgtdbms
	/** @param tblnm
	/** @param colnm
	/** @return insomnia */
	private int callHistogramGrpc(WaaDbConnTrgVO tgtdbms, WadAnaVar searchvo) {
		logger.debug("히스토그램 client grpc 호출:{},{}", tgtdbms.getDbSchId(), searchvo.getAnlVarId());
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		//조회할 테이블 명 셋팅
		String tblnm = searchvo.getDbTblNm().toLowerCase();
		String colnm = searchvo.getDbColNm().toLowerCase();
		
		//grpc 클라이언트 생성 후 callHisto 호출
		String host = message.getMessage("wiseda.grpc.host.python", null, Locale.getDefault()).trim();
		String port = message.getMessage("wiseda.grpc.port.python", null, Locale.getDefault()).trim();
		
		SummaryClient client = new SummaryClient(host, Integer.parseInt(port));
		
		//결과 리스트를 가져온다.
		Map<String, Object> resmap = client.CallHistoGrpc(tgtdbms, tblnm, colnm, searchvo.getAnlVarId());
		
		String varId = (String) resmap.get("varId");
		List<Float> sctVal = (List<Float>) resmap.get("sctVal");
		List<Float> strVal = (List<Float>) resmap.get("strVal");
		
		hstmapper.deleteByvarId(varId);
		
		for (int i = 0; i < sctVal.size(); i++) {
			WadVarHst savevo = new WadVarHst();
			savevo.setAnlVarId(varId);
			savevo.setSctStrVal(strVal.get(i));
			savevo.setSctEndVal(strVal.get(i+1));
			savevo.setSctVal(sctVal.get(i));
			savevo.setHstSno(i);
			savevo.setWritUserId(userid);
			savevo.setIbsStatus("I");
			
			result =+ regHisto(savevo);
		}
		
		return result;
	}

	/** @param savevo
	/** @return insomnia */
	private int regHisto(WadVarHst savevo) {
		int result = 0;
		
		String tmpsts = savevo.getIbsStatus();
		
		if ("I".equals(tmpsts)) {
			result = hstmapper.insertSelective(savevo);
		} else if ("U".equals(tmpsts)) {
			result = hstmapper.updateByPrimaryKey(savevo);
		} else if ("D".equals(tmpsts)) {
			result = hstmapper.deleteByPrimaryKey(savevo.getAnlVarId(), savevo.getHstSno());
		}
		
		
		return result;
	}
	
}
