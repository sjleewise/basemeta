/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndItemRqstServiceImpl.java
 * 2. Package : kr.wise.meta.stnd.service.impl
 * 3. Comment : 유사항목 등록요청 서비스 구현체....
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 28. 오전 8:54:48
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 28. :            : 신규 개발.
 */
package kr.wise.meta.symn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.stnd.service.WapDvCanAsm;
import kr.wise.meta.stnd.service.WapDvCanAsmMapper;
import kr.wise.meta.stnd.service.WapDvCanDic;
import kr.wise.meta.stnd.service.WapDvCanDicMapper;
import kr.wise.meta.symn.service.SymnItemRqstService;
import kr.wise.meta.symn.service.WamSymnItem;
import kr.wise.meta.symn.service.WamSymnItemMapper;
import kr.wise.meta.symn.service.WaqSymnItem;
import kr.wise.meta.symn.service.WaqSymnItemMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndItemRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 28. 오전 8:54:48
 * </PRE>
 */
@Service("symnItemRqstService")
public class SymnItemRqstServiceImpl implements SymnItemRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqSymnItemMapper waqmapper;
	
	@Inject
	private WapDvCanDicMapper wapDvCanDicMapper;

	@Inject
	private WapDvCanAsmMapper wapDvCanAsmMapper;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;

    @Inject
	private EgovIdGnrService requestIdGnrService;


	/** 유사항목 요청 상세내용 조회 insomnia */
	public WaqSymnItem getStndItemRqstDetail(WaqSymnItem searchVo) {
		return waqmapper.selectSymnItemRqstDetail(searchVo);
	}

	/** 유사항목 요청 리스트 조회 insomnia */
	public List<WaqSymnItem> getStndItemRqstList(WaqMstr search) {
		return waqmapper.selectSymnItemRqstListbyMst(search);
	}

	
	/** 유사항목 자동분할 리스트스 insomnia */
	public List<WapDvCanAsm> getItemDivList(WapDvCanDic record) {
		//분할정보 조회
		List<WapDvCanAsm> list = wapDvCanAsmMapper.selectList(record.getDvRqstNo());
		return list;
	}


	/** 유사항목 자동분할 리스트스 insomnia 
	 * @throws Exception */
	public List<WapDvCanAsm> getItemDivisionList(WapDvCanDic record) throws Exception {
		
		String dvRqstNo = requestIdGnrService.getNextStringId();
		record.setDvRqstNo(dvRqstNo);
		
		int execCnt = 0;
		//포함 단어정보 입력
		wapDvCanDicMapper.insertStwd(record);
		//포함 도메인 정보 입력
		wapDvCanDicMapper.insertDmn(record);

		//초기데이터 입력
		execCnt = wapDvCanAsmMapper.insertFirst(record);

		//도메인정보로 입력
		wapDvCanAsmMapper.insertAsmDmn(record.getDvRqstNo());

		//도메인 정보 존재시 초기데이터 삭제
		//wapDvCanAsmMapper.deleteExistDmnAsm(record.getDvRqstNo());
						
		while(execCnt > 0) {
			//단어정보로 분할
			wapDvCanAsmMapper.insertAsmDic(record.getDvRqstNo());
			//미존재단어정보 반영
			wapDvCanAsmMapper.insertAsmNotExistDic(record.getDvRqstNo());
			//이전데이터 삭제
			wapDvCanAsmMapper.deleteNotEndPrcAsmDic(record.getDvRqstNo());
			//분할데이터 존재시 분할상태로 코드 업데이트
			execCnt = wapDvCanAsmMapper.updateNotEndPrcAsmDic(record.getDvRqstNo());
		}
		
		
		//분할정보 조회
		List<WapDvCanAsm> list = wapDvCanAsmMapper.selectList(record.getDvRqstNo()); 
		
		//분할정보삭제
		wapDvCanDicMapper.deleteDvCanDicByDvRqstNo(record.getDvRqstNo());
		wapDvCanAsmMapper.deleteDvCanAsmByDvRqstNo(record.getDvRqstNo());

		return list;
	}
	

	/** #1. 유사항목 요청서 리스트 저장 insomnia */
	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {
		logger.debug("mstVo:{}\nbizInfo:{}", mstVo, mstVo.getBizInfo());

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}

		String rqstNo = mstVo.getRqstNo();

		int result = 0;

		if(reglist != null) {
			for (WaqSymnItem saveVo : (ArrayList<WaqSymnItem>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveRqstSymnItem(saveVo);
			}

		}
		//인포타입이 없는 경우 도메인이름으로 업데이트한다...
	//	waqmapper.updateItemInfoType(rqstNo);
		
		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;

	}

	/** #1-1. 단건저장 */
	public int saveRqstSymnItem(WaqSymnItem saveVo) {
		int result = 0;

		String tmpstatus = saveVo.getIbsStatus();
		
		logger.debug("tmpstatus : " + tmpstatus);

		if("I".equals(tmpstatus)) {
			result = waqmapper.insertSelective(saveVo);

		} else if ("U".equals(tmpstatus)) {
			LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
			String userid = user.getUniqId();
			saveVo.setRqstUserId(userid);
			result = waqmapper.updateByPrimaryKeySelective(saveVo);

		} else if ("D".equals(tmpstatus)) {

			result = waqmapper.deleteByPrimaryKey(saveVo);

		}

		//단어구성정보 셋팅(삭제 후 추가)
		//TODO : 유사항목 단어 구성필요할까 나중에  추가
		//setStwdCnfg(saveVo);

		return result;
	}

	/** 유사항목 검증 insomnia */
	public int check(WaqMstr mstVo) {
		int result = 0;

		//요청서번호 가져온다.
		String rqstNo = mstVo.getRqstNo();
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		String adminYn = UtilString.null2Blank(user.getIsAdminYn());
		

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setBizDtlCd(mstVo.getBizInfo().getBizDtlCd());	//SYM
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);

	/*	//삭제일경우 모든 컬럼 업데이트
		waqmapper.updateSditmDelInfo(rqstNo);
						
		//인포타입 으로 데이터타입 길이 업데이트
		waqmapper.updateDataTypeByInfotypLnm(rqstNo); */
		
		
		logger.debug("tblnm " + mstVo.getBizInfo().getTblNm() );
		logger.debug("bizDtlCd" +  mstVo.getBizInfo().getBizDtlCd() );
		//검증 시작
		//유사항목 검증
		//도메인 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm", mstVo.getBizInfo().getTblNm());	//WAQ_SYMN_ITEM
		checkmap.put("rqstNo", rqstNo);
		checkmap.put("bizDtlCd", mstVo.getBizInfo().getBizDtlCd());	//SYM

		checkmap.put("vrfDtlCd", "CM001");
		waqmapper.checkDupInRqst(checkmap);

		
		checkmap.put("vrfDtlCd", "MS012");
		waqmapper.checkDupOtherRqst(checkmap);
		
		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(SI000)
		checkmap.put("vrfDtlCd", "SI000");
		waqmapper.checkNotChgData(checkmap);
		
		
		/*등록요청중인 항목 검증 (SI012)
		checkmap.put("vrfDtlCd", "SI012");
		waqmapper.checkRequestDmn(checkmap);

		//요청서내 중복자료 검증(SI001)
		checkmap.put("vrfDtlCd", "SI001");
		waqmapper.checkDupSditm(checkmap);

		//삭제일때 미존재항목 체크(SI002)
		checkmap.put("vrfDtlCd", "SI002");
		waqmapper.checkNotExistSditm(checkmap);

		//유사어 존재(SI003)
		checkmap.put("vrfDtlCd", "SI003");
		waqmapper.checkLnmSymn(checkmap);

		//표준단어 존재 체크(SI004)
		checkmap.put("vrfDtlCd", "SI004");
		waqmapper.checkExistStwd(checkmap);

		//인포타입 체크(SI005)
		checkmap.put("vrfDtlCd", "SI005");
		waqmapper.checkInfoType(checkmap);

		//인포타입명에 따른 데이터타입 체크(SI013)
		checkmap.put("vrfDtlCd", "SI013");
		waqmapper.checkDataType(checkmap);

		//도메인 미존재(SI006)
		checkmap.put("vrfDtlCd", "SI006");
		waqmapper.checkNotExistDmn(checkmap);

		//항목 구성정보 오류(SI007)
		checkmap.put("vrfDtlCd", "SI007");
		waqmapper.checkSditmStwdAsm(checkmap);

		//물리명 유니크 검사(SI008)
		checkmap.put("vrfDtlCd", "SI008");
		waqmapper.checkDupSditmPnm(checkmap);

		//항목 물리명 최대값 검증을 사용 할 경우(SI009)
		checkmap.put("vrfDtlCd", "SI009");
		waqmapper.checkSditmLnmMaxLen(checkmap);

		//항목 논리명 최대값 검증을 사용 할 경우(SI010)
		checkmap.put("vrfDtlCd", "SI010");
		waqmapper.checkSditmPnmMaxLen(checkmap);
		
		//코드도메인 인포타입변경시 할 경우(SI015)
		checkmap.put("vrfDtlCd", "SI015");
		waqmapper.checkSditmCodeInfoTpChg(checkmap);  
		
		//삭제일때 테이블존재 체크(SI016)
		checkmap.put("vrfDtlCd", "SI016");
		waqmapper.checkExistCol(checkmap);*/
		
//		checkmap.put("vrfDtlCd", "SI014");
//		waqmapper.checkObjDesn(checkmap);

		//항목 물리명 첫 글자 숫자 여부 검사(SI011)
//		checkmap.put("vrfDtlCd", "SI011");
//		waqmapper.checkSditmPnmStrNum(checkmap);

		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(SI000)
		/*checkmap.put("vrfDtlCd", "SI000");
		waqmapper.checkNotChgData(checkmap);*/

		//등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);


		return result;
	}

	/** 유사항목 등록요청 insomnia */
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** 유사항목 승인 insomnia */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqSymnItem savevo : (ArrayList<WaqSymnItem>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqmapper.updateRvwStsCd(savevo);
		}

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result <= 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}

		//2. 결재 진행 테이블을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3.최종 승인인지 아닌지 확인한다. (이건 AOP 방식으로 처리할 수 있을까?....)
//		boolean waq2wam = requestApproveService.setApproveProcess(mstVo, "WAQ_DMN");
		boolean waq2wam = requestApproveService.setApproveProcess(mstVo);

		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
			//waq2wam을 처리하자...
			logger.debug("유사항목 waq to wam and wah");

			result = 0;
			result += regWaq2Wam(mstVo);

			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}
			//유사항목 분할정보 남아서 삭제.. 확인 
//			wapDvCanDicMapper.deleteDvCanDicByDvRqstNo(rqstNo);
//			wapDvCanAsmMapper.deleteDvCanAsmByDvRqstNo(rqstNo);
		}

		return result;
	}

	/** @param mstVo
	/** @return insomnia
	 * @throws Exception */
	public int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		//신규 대상인 경우 ID를 채번한다.
		List<WaqSymnItem> waqclist = waqmapper.selectWaqC(rqstno);
		for (WaqSymnItem savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setSymnItemId(id);	//pk 필드

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		//TODO : 유사항목 각종 ID 업데이트 하도록 한다. 
		//result += waqmapper.updateWaqId(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

		return result;
	}

	/** 유사항목 변경대상 조회 및 추가... insomnia * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqSymnItem> list) throws Exception {
		int result = 0;

		//WAM에서 WAQ에 적재할 내용을 가져온다...
		List<WaqSymnItem> wamlist = waqmapper.selectwamlist(reqmst, list);

		result = register(reqmst, wamlist);
		return result;
	}

	/** insomnia
	 * @throws Exception */
	public int delSymnItemRqstList(WaqMstr reqmst, ArrayList<WaqSymnItem> list) throws Exception {
		int result = 0;

		//TODO 성능 문제 발생시 한방 SQL로 처리한다.
		//result = waqmapper.deleteitemrqst(reqmst, list);

		for (WaqSymnItem savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}
	
	
	/** 유사항목 자동분할 리스트스 insomnia */
	public List<WapDvCanAsm> getItemDvRqstList(WapDvCanDic record) {
		//분할정보 조회
		List<WapDvCanAsm> list = wapDvCanAsmMapper.selectItemDvRqstList(record);
		return list;
	}
	
	/** 항목 자동 분할  저장한다. insomnia
	 * @throws Exception */
	public Map<String, String> regItemAutoDiv(List<WapDvCanAsm> list, WapDvCanDic record2) throws Exception {
		Map<String, String> resultMap =  new HashMap<String, String>();
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		String dvrqstno = requestIdGnrService.getNextStringId();
		
		int execCnt = 0;
		int rtnCnt = 0;
		
		WapDvCanDic record = new WapDvCanDic();
		record.setDvRqstNo(dvrqstno);
		record2.setDvRqstNo(dvrqstno);
		//포함 단어정보 입력
		wapDvCanDicMapper.insertStwdAll(record);
		//포함 도메인 정보 입력
		wapDvCanDicMapper.insertDmnAll(record);
		
		for (WapDvCanAsm savevo : list) {
			savevo.setDvRqstNo(dvrqstno);
			savevo.setDvRqstUserId(userid);
			savevo.setDvSeCd("I");
			
			//초기데이터 입력
			execCnt += wapDvCanAsmMapper.insertDvListFirst(savevo);
		}
		
		rtnCnt = execCnt;
		
		//도메인정보로 입력
		wapDvCanAsmMapper.insertAsmDmn(dvrqstno);
		
		//도메인 정보 존재시 초기데이터 삭제
		//wapDvCanAsmMapper.deleteExistDmnAsm(dvrqstno);
		
		while(execCnt > 0) {
			//단어정보로 분할
			wapDvCanAsmMapper.insertAsmDic(dvrqstno);
			//미존재단어정보 반영
			wapDvCanAsmMapper.insertAsmNotExistDic(dvrqstno);
			//이전데이터 삭제
			wapDvCanAsmMapper.deleteNotEndPrcAsmDic(dvrqstno);
			//분할데이터 존재시 분할상태로 코드 업데이트
			execCnt = wapDvCanAsmMapper.updateNotEndPrcAsmDic(dvrqstno);
		}
		
		//분할정보삭제
		wapDvCanDicMapper.deleteDvCanDicByDvRqstNo(dvrqstno);
//		wapDvCanAsmMapper.deleteDvCanAsmByDvRqstNo(dvrqstno);
		
		//분할 결과 검증 UPDATE
		//도메인명 미존재
		wapDvCanDicMapper.checkDmnInfo(dvrqstno);
		//단어 미존재
		wapDvCanDicMapper.checkSdwd(dvrqstno);
		
		//인포타입 미존재
		wapDvCanDicMapper.checkInpotpLnm(dvrqstno); 
						
		//항목기존재
		wapDvCanDicMapper.checkDupSditm(dvrqstno);
		//구성정보 오류
		wapDvCanDicMapper.checkAsmDs(dvrqstno);
				
		//물리명 길이
		wapDvCanDicMapper.checkPnmMaxLen(dvrqstno);
		//물리명 끝자리 숫자체크
		wapDvCanDicMapper.checkEndNum(dvrqstno);
		
		//분할정보삭제
		//wapDvCanAsmMapper.deleteDvCanAsmByDvOrderBy(record2); //분할된중복에서 select쿼리 제외한 나머지 여기서도 중복나면 동음이의어
		//wapDvCanAsmMapper.deleteDvCanAsmByDup(dvrqstno); //중복분할정보 - 도메인이 두개일때(큰거, 작은거) 동음이의어는 삭제안함..
		
		resultMap.put("result", Integer.toString(rtnCnt) );
    	resultMap.put("dvrqstno", dvrqstno);
		
		return resultMap;
		
	}
	
	/** 항목 자동 분할  저장한다. insomnia
	 * @throws Exception */
	public Map<String, String> delItemAutoDiv(List<WapDvCanAsm> list) throws Exception {
		Map<String, String> resultMap =  new HashMap<String, String>();
		int rtnCnt = 0;
		String dvrqstno = "";
		for (WapDvCanAsm savevo : list) {
			dvrqstno = savevo.getDvRqstNo();
			rtnCnt += wapDvCanAsmMapper.delItemAutoDiv(savevo);
		}
		
		
		resultMap.put("result", Integer.toString(rtnCnt) );
		resultMap.put("dvrqstno", dvrqstno);
		
		return resultMap;
		
	}
	
	/** insomnia */
	public int regApprove(WaqMstr mstVo, List<WaqSymnItem> reglist) {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqSymnItem savevo : (ArrayList<WaqSymnItem>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqmapper.updateRvwStsCd(savevo);
		}
		
		//업데이트 내용이 없으면 오류 리턴한다.
		if (result < 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}
		return result;
	}
	
/*	//등록요청 탭 클릭 확인용 
	public int checkExistsWaqItem(WaqMstr reqmst){
	      List<WaqSymnItem> list = waqmapper.selectExistsItemCheck(reqmst);
	      
	      if(list.isEmpty()){
	    	  return 0;
	      }else{
	    	  return 1;
	      }
	
	}*/
	
	  	/** WAQ에 있는 반려된 건을 재 등록한다. 이상익
	 * @throws Exception */
	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {

		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
				
		//waq의 반려내용을 waq로 다시 입력
		result = waqmapper.insertWaqRejected(reqmst, oldRqstNo);

		//마스터 등록
		register(reqmst, null);
        
		//검증
		check(reqmst);
		

		return result;

	}

		@Override
		public WaqSymnItem getItemWordInfo(WaqSymnItem search) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int delStndItemRqstList(WaqMstr reqmst, ArrayList<WaqSymnItem> list) throws Exception {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int regitemdivision(WaqMstr reqmst, List<WaqSymnItem> list) throws Exception {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int checkExistsWaqItem(WaqMstr reqmst) {
			// TODO Auto-generated method stub
			return 0;
		}

}
