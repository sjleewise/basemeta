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

import kr.wise.advisor.prepare.dataset.service.DataSetNstndService;
import kr.wise.advisor.prepare.dataset.service.WadAnaVarNstnd;
import kr.wise.advisor.prepare.dataset.service.WadAnaVarNstndMapper;
import kr.wise.advisor.prepare.dataset.service.WadDataSetNstnd;
import kr.wise.advisor.prepare.dataset.service.WadDataSetNstndMapper;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
@Service("dataSetNstndService")
public class DataSetNstndServiceImpl implements DataSetNstndService {
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WadDataSetNstndMapper mapper;
	
	@Inject
	private WadAnaVarNstndMapper varmapper;
	
	@Inject
	private EgovIdGnrService requestIdGnrService;
	
	@Inject
    private EgovIdGnrService objectIdGnrService;

	/** insomnia 
	 * @throws Exception */
	public List<WadDataSetNstnd> regDataSetNstnd(List<AnaTrgTblVO> list) throws Exception {
		int result = 0;
		List<WadDataSetNstnd> reslist = new ArrayList<WadDataSetNstnd>();
		
		//rqst_no 채번
		String reqid = requestIdGnrService.getNextStringId();
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		//기존 데이터셋과 비교해서 없는경우 신규, 있는경우 업데이트로 변경해서 저장한다.
		for (AnaTrgTblVO tblvo : list) {
			WadDataSetNstnd savevo = new WadDataSetNstnd();
			savevo.setRqstNo(reqid);
			savevo.setWritUserId(userid);
			savevo.setDbSchId(tblvo.getDbSchId());
			savevo.setDbTblNm(tblvo.getDbcTblNm());
			
			//기존 데이터셋 존재여부 체크한다.
			WadDataSetNstnd comds = mapper.selectDataSetNstnd(tblvo);
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
			
			result =+ regDataSetNstnd(savevo);
			reslist.add(savevo);
		}
		
		
		
		return reslist;
		
	}

	/** 데이터셋 단건 CUD 처리
	 * @throws Exception */
	private int regDataSetNstnd(WadDataSetNstnd savevo) throws Exception {
		
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
	private List<WadDataSetNstnd> getDataSetNstnd(List<AnaTrgTblVO> list) {
		
		return null;
	}

	/** insomnia 
	 * @throws Exception */
	public int regAnaVarNstnd(List<WadAnaVarNstnd> varlist) throws Exception {
		logger.debug("데이터셋 변수등록");
		int result = 0;
		
		for (WadAnaVarNstnd savevo : varlist) {
			result =+ regAnaVarNstndRow(savevo);
		}
		
		return result;
	}

	/** @param varvo
	/** @return insomnia 
	 * @throws Exception */
	private int regAnaVarNstndRow(WadAnaVarNstnd savevo) throws Exception {
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
	public List<WadAnaVarNstnd> getAnaVarNstnd(List<WadDataSetNstnd> dslist) {
		logger.debug("데이터셋 변수목록조회 by 데이터셋 리스트");
		
		return varmapper.selectListbyDslist(dslist);
		
//		return null;
	}

	/** insomnia */
	public List<WadDataSetNstnd> getDataSetNstndList(WadDataSetNstnd search) {
		logger.debug("데이터셋 목록 조회 :{}", search);
		
		//rqstNo 확인 후 있을 경우 rqstNo조회
		String rqstNo = search.getRqstNo();
//		if(StringUtils.hasText(rqstNo)) {
//			return mapper.selectDataSetListbyrqstNo(rqstNo);
//		} else {
			return mapper.selectDataSetNstndList(search);
//		}
		
	}

	/** insomnia */
	public List<WadAnaVarNstnd> getAnaVarNstndbyDs(WadDataSetNstnd search) {
		logger.debug("변수목록 조회 (도메인판별결과포함):{}",search);
		
		return varmapper.selectDmnPdtListbyDs(search);
		
	}

	/** insomnia */
	public WadAnaVarNstnd getAnlVarNstndDtl(WadAnaVarNstnd search) {
		logger.debug("변수 상세 조회:{}", search.getAnlVarId());
		
		return varmapper.selectByPrimaryKey(search.getAnlVarId());
	}

	/** insomnia */
	public List<WaaDbConnTrgVO> getDataSetNstndListbyrqstNo(String rqstNo) {
		logger.debug("도메이판별 grpc 요청할 데이터셋 조회 by rqstNo:{}", rqstNo);
		
		return mapper.selectDataSetNstndListbyrqstNo(rqstNo);
	}

	/** insomnia */
	public WaaDbConnTrgVO getTgtDbmsbyds(WadDataSetNstnd search) {
		logger.debug("데이터셋 테이블의 dbms접속정보 조회:{}", search);
		
		return mapper.selectTgtDbmsbyDs(search);
	}


	/** insomnia */
	public List<WadAnaVarNstnd> getAnaVarNstndListbyDsId(WadDataSetNstnd dataset) {
		logger.debug("변수목록 조회 by ds:{}", dataset);
		
		return varmapper.selectVarListbydsid(dataset);
	}


}
