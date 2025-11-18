/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlTblRqstServiceImpl.java
 * 2. Package : kr.wise.meta.ddl.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 14. 오후 3:10:37
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 14. :            : 신규 개발.
 */
package kr.wise.meta.ddletc.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
import kr.wise.meta.ddl.script.service.DdlScriptMapper;
import kr.wise.meta.ddl.script.service.DdlScriptService;
import kr.wise.meta.ddl.service.DdlIdxRqstService;
import kr.wise.meta.ddl.service.DdlTblRqstService;
import kr.wise.meta.ddl.service.WaqDdlCol;
import kr.wise.meta.ddl.service.WaqDdlColMapper;
import kr.wise.meta.ddl.service.WaqDdlIdxColMapper;
import kr.wise.meta.ddl.service.WaqDdlIdxMapper;
import kr.wise.meta.ddl.service.WaqDdlRel;
import kr.wise.meta.ddl.service.WaqDdlRelMapper;
import kr.wise.meta.ddl.service.WaqDdlTbl;
import kr.wise.meta.ddl.service.WaqDdlTblMapper;
import kr.wise.meta.ddletc.service.DdlEtcRqstService;
import kr.wise.meta.ddletc.service.WaqDdlEtc;
import kr.wise.meta.ddletc.service.WaqDdlEtcMapper;
import kr.wise.meta.model.service.WaqPdmTbl;
import kr.wise.meta.stnd.service.WaqDmn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlEtcRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.ddl.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 14. 오후 3:10:37
 * </PRE>
 */
@Service("ddlEtcRqstService")
public class DdlEtcRqstServiceImpl implements DdlEtcRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	
	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;
    
    @Inject
    private DdlScriptService ddlScriptService;
    
    
    @Inject
	private WaqDdlEtcMapper waqmapper;


    

	/** DDL 테이블 요청서 저장... insomnia */
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
			for (WaqDdlEtc saveVo : (ArrayList<WaqDdlEtc>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveDdlEtcRqst(saveVo);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/** DDL 테이블 단건 저장 insomnia */ 
	private int saveDdlEtcRqst(WaqDdlEtc saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

		if ("I".equals(tmpstatus)) {
			//요청서 추가...
			result = waqmapper.insertSelective(saveVo);
			

		} else if ("U".equals(tmpstatus)){
			//요청서 내용 변경시...
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
			
			
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo); 
			
		}

		return result;
	}
	
	@Override
	public List<WaqDdlEtc> getDdlEtcRqstList(WaqMstr search) {
		
		return waqmapper.selectDdlEtcRqstList(search); 
	}

	@Override
	public int check(WaqMstr mstVo) throws Exception {
		
		int result = 0;
		//요청서번호 가져온다.
		String rqstNo   = mstVo.getRqstNo();
		String tblnm    = mstVo.getBizInfo().getTblNm();
		String bizdtlcd = mstVo.getBizInfo().getBizDtlCd();

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
//				waqRqstVrfDtls.setBizDtlCd("TBL");
		waqRqstVrfDtls.setBizDtlCd(bizdtlcd);
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		
		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);
		
		//dbSchId 업데이트
		waqmapper.updateDbSchId(rqstNo);
		
		//검증 시작
		//DDL 기타오브젝트 
		//DDL 테이블 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);

		//요청서내 중복자료 검증(DT001)
		checkmap.put("vrfDtlCd", "ETC01");
		waqmapper.checkDupTbl(checkmap); 

		//삭제일때 미존재 테이블 체크(DT002)
		checkmap.put("vrfDtlCd", "ETC02");
		waqmapper.checkNotExistTbl(checkmap);

		//요청중인 테이블 (DT004)
		checkmap.put("vrfDtlCd", "ETC04");
		waqmapper.checkRequestTbl(checkmap); 
		
		//변경된 내용 없음(DT000)
		checkmap.put("vrfDtlCd", "ETC00");
		waqmapper.checkNotChgData(checkmap);
		
		//테이블 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);
		
		updateDdlEtcScriptWaq(mstVo);
		
		return result;
	}
	
	public void updateDdlEtcScriptWaq(WaqMstr mstVo) throws Exception {
		
		List<WaqDdlEtc> waqlist = ddlScriptService.updateDdlEtcScriptWaq(mstVo);
		
		for(WaqDdlEtc savevo : waqlist){
			waqmapper.updateDdlScriptWaq(savevo);
		}
	}

	@Override
	public int submit(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
	
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqDdlEtc savevo : (ArrayList<WaqDdlEtc>)reglist) {
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
			logger.debug("물리모델 waq to wam and wah");

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

	 
	private int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqDdlEtc> waqclist = waqmapper.selectWaqC(rqstno);

		for (WaqDdlEtc savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setDdlEtcId(id);
						

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
//		result += waqmapper.updateWaqId(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno); 

		result += waqmapper.updateWAH(rqstno); 

		result += waqmapper.insertWAH(rqstno);  

		return result;
	}

	
	@Override
	public WaqDdlEtc getDdlEtcRqstDetail(WaqDdlEtc searchVo) {

		return waqmapper.selectDdlEtcRqstDetail(searchVo);
	}

	@Override
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlEtc> list) throws Exception { 
		
		int result = 0;

		//WAM에서 WAQ에 적재할 내용을 가져온다.
		ArrayList<WaqDdlEtc> wamlist = waqmapper.selectwamlist(reqmst, list); 

		result = register(reqmst, wamlist);  

		return result;
	}

	@Override
	public int delDdlEtcRqst(WaqMstr reqmst, ArrayList<WaqDdlEtc> list) throws Exception {
		
		int result = 0;

		//TODO 성능이 문제가 될시에 한방 SQL로 처리한다.
//		result = waqmapper.deletedmnrqst(reqmst, list);

		for (WaqDdlEtc savevo : list) { 
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
		reqmst.getBizInfo().setBizDtlCd("ETC");
		
		//waq의 반려내용을 waq로 다시 입력
		result = waqmapper.insertWaqRejected(reqmst, oldRqstNo);
		//마스터 등록
		register(reqmst, null);
		//검증
		check(reqmst);
		
		return result;
	}
}
