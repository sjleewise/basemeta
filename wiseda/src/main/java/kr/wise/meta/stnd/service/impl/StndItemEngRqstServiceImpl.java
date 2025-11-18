/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndItemRqstServiceImpl.java
 * 2. Package : kr.wise.meta.stnd.service.impl
 * 3. Comment : 표준항목 등록요청 서비스 구현체....
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 28. 오전 8:54:48
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 28. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service.impl;

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
import kr.wise.meta.stnd.service.StndItemEngRqstService;
import kr.wise.meta.stnd.service.StndItemRqstService;
import kr.wise.meta.stnd.service.WamStwdAbr;
import kr.wise.meta.stnd.service.WapDvCanAsm;
import kr.wise.meta.stnd.service.WapDvCanAsmMapper;
import kr.wise.meta.stnd.service.WapDvCanDic;
import kr.wise.meta.stnd.service.WapDvCanDicMapper;
import kr.wise.meta.stnd.service.WaqDmn;
import kr.wise.meta.stnd.service.WaqDmnMapper;
import kr.wise.meta.stnd.service.WaqSditm;
import kr.wise.meta.stnd.service.WaqSditmEngMapper;
import kr.wise.meta.stnd.service.WaqSditmMapper;
import kr.wise.meta.stnd.service.WaqStwd;
import kr.wise.meta.stnd.service.WaqStwdCnfg;
import kr.wise.meta.stnd.service.WaqStwdCnfgMapper;
import kr.wise.meta.stnd.service.WaqStwdMapper;

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
@Service("stndItemEngRqstService") 
public class StndItemEngRqstServiceImpl implements StndItemEngRqstService { 

	private final Logger logger = LoggerFactory.getLogger(getClass()); 

	@Inject
	private WaqSditmMapper waqmapper;
		
	@Inject 
	private WaqSditmEngMapper waqengmapper; 
	
	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqStwdCnfgMapper waqStwdCnfgMapper;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;

    @Inject
	private EgovIdGnrService requestIdGnrService;

	private CommonVo wapDvCanAsmVo;

	/** 표준항목 요청 상세내용 조회 insomnia */
	public WaqSditm getStndItemRqstDetail(WaqSditm searchVo) {

		return waqmapper.selectStndItemRqstDetail(searchVo);
	}

	

	/** 표준항목 요청서 리스트 저장 insomnia */
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
			for (WaqSditm saveVo : (ArrayList<WaqSditm>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveRqstStndItemEng(saveVo);
			}

		}
		
		//인포타입이 없는 경우 도메인이름으로 업데이트한다...
		waqmapper.updateItemInfoType(rqstNo);


		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;

	}

	/** @return insomnia */
	public int saveRqstStndItemEng(WaqSditm saveVo) {
		int result = 0;

		String tmpstatus = saveVo.getIbsStatus();

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
		//setStwdCnfg(saveVo);

		return result;
	}


	/** 표준항목 검증 insomnia */
	public int check(WaqMstr mstVo) {
		int result = 0;

		//요청서번호 가져온다.
		String rqstNo = mstVo.getRqstNo();

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setBizDtlCd("SDENG");
//		waqRqstVrfDtls.setBizDtlCd(mstVo.getBizInfo().getBizDtlCd());
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);

		//검증 시작
		//표준항목 검증
		//도메인 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm", "WAQ_SDITM");
//		checkmap.put("tblnm", mstVo.getBizInfo().getTblNm());
		checkmap.put("rqstNo", rqstNo);
		checkmap.put("bizDtlCd", "SDENG");
//		checkmap.put("bizDtlCd", mstVo.getBizInfo().getBizDtlCd());

		//등록요청중인 항목 검증 (SI012)
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
		//waqmapper.checkLnmSymn(checkmap);

		//표준단어 존재 체크(SI004)
		checkmap.put("vrfDtlCd", "SI004");
		//waqmapper.checkExistStwd(checkmap);

		//인포타입 체크(SI005)
		checkmap.put("vrfDtlCd", "SI005");
		waqengmapper.checkEngInfoType(checkmap);

		//인포타입명에 따른 데이터타입 체크(SI013)
		checkmap.put("vrfDtlCd", "SI013");
		waqmapper.checkDataType(checkmap);
		

		//항목 물리명 최대값 검증을 사용 할 경우(SI009)
		checkmap.put("vrfDtlCd", "SI009");
		waqmapper.checkSditmPnmMaxLen(checkmap);

		//항목 논리명 최대값 검증을 사용 할 경우(SI010)
		checkmap.put("vrfDtlCd", "SI010");
		waqmapper.checkSditmLnmMaxLen(checkmap);
		
//		checkmap.put("vrfDtlCd", "SI014");
//		waqmapper.checkObjDesn(checkmap);

		//항목 물리명 첫 글자 숫자 여부 검사(SI011)
//		checkmap.put("vrfDtlCd", "SI011");
//		waqmapper.checkSditmPnmStrNum(checkmap);

		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(SI000)
		checkmap.put("vrfDtlCd", "SI000");
		waqmapper.checkNotChgData(checkmap);

		//도메인 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);


		return result;
	}

	/** 표준항목 등록요청 insomnia */
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	/** 표준항목 승인 insomnia */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqSditm savevo : (ArrayList<WaqSditm>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqmapper.updatervwStsCd(savevo);
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
			logger.debug("표준항목 waq to wam and wah");

			result = 0;
			result += regWaq2Wam(mstVo);

			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

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
		List<WaqSditm> waqclist = waqmapper.selectWaqC(rqstno);
		for (WaqSditm savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setSditmId(id);

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
		//result += waqmapper.updateWaqId(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

	
		return result;
	}

	/** 표준항목 변경대상 조회 및 추가... insomnia * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, List<WaqSditm> list) throws Exception {
		int result = 0;

		//WAM에서 WAQ에 적재할 내용을 가져온다...
		ArrayList<WaqSditm> wamlist = waqmapper.selectwamlist(reqmst, list);

		result = register(reqmst, wamlist);
		return result;
	}

	/** insomnia
	 * @throws Exception */
	public int delStndItemRqstList(WaqMstr reqmst, ArrayList<WaqSditm> list) throws Exception {
		int result = 0;

		//TODO 성능 문제 발생시 한방 SQL로 처리한다.
		//result = waqmapper.deleteitemrqst(reqmst, list);

		for (WaqSditm savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	
	/** insomnia */
	public int regapprove(WaqMstr mstVo, List<WaqSditm> reglist) {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqSditm savevo : (ArrayList<WaqSditm>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqmapper.updatervwStsCd(savevo);
		}
		
		waqmapper.updatervwStsCdRejectSwtd(rqstNo);
		waqmapper.updatervwStsCdRejectDmn(rqstNo);

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result < 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}
		return result;
	}
	
	//등록요청 탭 클릭 확인용 
	public int checkExistsWaqItem(WaqMstr reqmst){
	      List<WaqSditm> list = waqmapper.selectExistsItemCheck(reqmst);
	      
	      if(list.isEmpty()){
	    	  return 0;
	      }else{
	    	  return 1;
	      }
	
	}

	@Override
	public List<WaqSditm> getItemEngRqstList(WaqMstr search) { 
		// TODO Auto-generated method stub
		return waqengmapper.selectItemEngRqstListbyMst(search); 
	}
	
	

}
