/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : DataSetServiceImpl.java
 * 2. Package : kr.wise.advisor.prepare.dataset.service.impl
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 18. 오후 6:06:32
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 18. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.dataset.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.phrase_extractor.KoreanPhraseExtractor;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer;

import kr.wise.advisor.prepare.dataset.service.DataSetService;
import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.dataset.service.WadAnaVarMapper;
import kr.wise.advisor.prepare.dataset.service.WadDataSet;
import kr.wise.advisor.prepare.dataset.service.WadDataSetMapper;
import kr.wise.advisor.prepare.outlier.service.WadOtlDtc;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;
import scala.collection.Seq;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DataSetServiceImpl.java
 * 3. Package  : kr.wise.advisor.prepare.dataset.service.impl
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 18. 오후 6:06:32
 * </PRE>
 */
@Service("dataSetService")
public class DataSetServiceImpl implements DataSetService {
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WadDataSetMapper mapper;
	
	@Inject
	private WadAnaVarMapper varmapper;
	
	@Inject
	private EgovIdGnrService requestIdGnrService;
	
	@Inject
    private EgovIdGnrService objectIdGnrService;

	/** insomnia 
	 * @throws Exception */
	public List<WadDataSet> regDataSet(List<AnaTrgTblVO> list) throws Exception {
		int result = 0;
		List<WadDataSet> reslist = new ArrayList<WadDataSet>();
		
		//rqst_no 채번
		String reqid = requestIdGnrService.getNextStringId();
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		//기존 데이터셋과 비교해서 없는경우 신규, 있는경우 업데이트로 변경해서 저장한다.
		for (AnaTrgTblVO tblvo : list) {
			WadDataSet savevo = new WadDataSet();
			savevo.setRqstNo(reqid);
			savevo.setWritUserId(userid);
			savevo.setDbSchId(tblvo.getDbSchId());
			savevo.setDbTblNm(tblvo.getDbcTblNm());
			
			logger.debug("\n" + tblvo.getDbSchId());
			logger.debug("\n" + tblvo.getDbcTblNm());
			
			//기존 데이터셋 존재여부 체크한다.
			WadDataSet comds = mapper.selectDataSet(tblvo);
			if (comds != null) {
				savevo.setDaseId(comds.getDaseId());
				savevo.setIbsStatus("U");
				savevo.setDaseLnm(comds.getDaseLnm());
				savevo.setDasePnm(comds.getDasePnm());
			} else {
				
				savevo.setIbsStatus("I");
				savevo.setDaseLnm(tblvo.getDbcTblNm());
				savevo.setDasePnm(tblvo.getDbcTblNm());
			}
			
			result =+ regDataSet(savevo);
			reslist.add(savevo);
		}
		
		
		
		return reslist;
		
	}

	/** 데이터셋 단건 CUD 처리
	 * @throws Exception */
	private int regDataSet(WadDataSet savevo) throws Exception {
		
		int result = 0;
		
		String regtyp = savevo.getIbsStatus();
		
		if ("I".equals(regtyp)) {
			//데이터셋ID채번
			String id =  objectIdGnrService.getNextStringId();
			savevo.setDaseId(id);
			savevo.setRegTypCd("C");
			result = mapper.insertSelective(savevo);
			
		} else if ("U".equals(regtyp)) {
			savevo.setRegTypCd("U");
			result = mapper.updateByPrimaryKeySelective(savevo);
		} else {
			//삭제....
			savevo.setRegTypCd("D");
			result = mapper.deleteByPrimaryKey(savevo.getDaseId());
		}
		
		return result;
	}

	/** @param list
	/** @return insomnia */
	private List<WadDataSet> getDataSet(List<AnaTrgTblVO> list) {
		
		return null;
	}

	/** insomnia 
	 * @throws Exception */
	public int regAnaVar(List<WadAnaVar> varlist) throws Exception {
		logger.debug("데이터셋 변수등록");
		int result = 0;
		
		for (WadAnaVar savevo : varlist) {
			result =+ regAnaVarRow(savevo);
		}
		
		return result;
	}

	/** @param varvo
	/** @return insomnia 
	 * @throws Exception */
	private int regAnaVarRow(WadAnaVar savevo) throws Exception {
		int result = 0;
		
		String ibstmp = savevo.getIbsStatus();
		if ("I".equals(ibstmp)) {
			//데이터셋ID채번
			String id =  objectIdGnrService.getNextStringId();
			savevo.setAnlVarId(id);
			savevo.setRegTypCd("C");
			result = varmapper.insertSelective(savevo);
		} else if ("U".equals(ibstmp)) {
			savevo.setRegTypCd("U");
			result = varmapper.updateByPrimaryKeySelective(savevo);
			
		} else if ("D".equals(ibstmp)) {
			savevo.setRegTypCd("D");
			result = varmapper.deleteByPrimaryKey(savevo.getAnlVarId());
		}
		
		return result;
	}


	/** insomnia */
	public List<WadAnaVar> getAnaVar(List<WadDataSet> dslist) {
		logger.debug("데이터셋 변수목록조회 by 데이터셋 리스트");
		
		return varmapper.selectListbyDslist(dslist);
		
//		return null;
	}

	/** insomnia */
	public List<WadDataSet> getDataSetList(WadDataSet search) {
		logger.debug("데이터셋 목록 조회 :{}", search);
		
		//rqstNo 확인 후 있을 경우 rqstNo조회
		String rqstNo = search.getRqstNo();
//		if(StringUtils.hasText(rqstNo)) {
//			return mapper.selectDataSetListbyrqstNo(rqstNo);
//		} else {
			return mapper.selectDataSetList(search);
//		}
		
	}

	/** insomnia */
	public List<WadAnaVar> getAnaVarbyDs(WadDataSet search) {
		logger.debug("변수목록 조회 (도메인판별결과포함):{}",search);
		
		return varmapper.selectDmnPdtListbyDs(search);
		
	}

	/** insomnia */
	public WadAnaVar getAnlVarDtl(WadAnaVar search) {
		logger.debug("변수 상세 조회:{}", search.getAnlVarId());
		
		return varmapper.selectByPrimaryKey(search.getAnlVarId());
	}

	/** insomnia */
	public List<WaaDbConnTrgVO> getDataSetListbyrqstNo(String rqstNo) {
		logger.debug("도메이판별 grpc 요청할 데이터셋 조회 by rqstNo:{}", rqstNo);
		
		return mapper.selectDataSetListbyrqstNo(rqstNo);
	}

	/** insomnia */
	public WaaDbConnTrgVO getTgtDbmsbyds(WadDataSet search) {
		logger.debug("데이터셋 테이블의 dbms접속정보 조회:{}", search);
		
		return mapper.selectTgtDbmsbyDs(search);
	}


	/** insomnia */
	public List<WadAnaVar> getAnaVarListbyDsId(WadDataSet dataset) {
		logger.debug("변수목록 조회 by ds:{}", dataset);
		
		return varmapper.selectVarListbydsid(dataset);
	}

	@Override
	public List<WadAnaVar> getDaseColList(WadDataSet search) {
		logger.debug("컬럼 목록 조회 >>> {}", search);
		logger.debug("스키마아이디 >>> " + search.getSchDbSchId() + " 테이블명 >>> " + search.getSchDbcTblNm());
		
		return varmapper.selectDaseColList(search); 
	}


}
