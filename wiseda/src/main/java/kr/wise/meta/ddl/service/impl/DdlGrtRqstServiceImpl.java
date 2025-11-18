/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlGrtRqstServiceImpl.java
 * 2. Package : kr.wise.meta.ddl.service.impl
 * 3. Comment :
 * 4. 작성자  : 유성열
 * 5. 작성일  : 2014. 8. 6.  16:17:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    유성열   : 2014. 8. 6. :            : 신규 개발.
 *                    김연호   : 2014. 8.27. : 			  : 추가
 */
package kr.wise.meta.ddl.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import kr.wise.meta.ddl.service.DdlGrtRqstService;
import kr.wise.meta.ddl.service.WaqDdlGrt;
import kr.wise.meta.ddl.service.WaqDdlGrtMapper;
import kr.wise.meta.intf.service.GroupWareSendService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlGrtRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.ddl.service.impl
 * 4. Comment  :
 * 5. 작성자   : 유성열
 * 6. 작성일   : 2014. 8. 6.  16:17:00
 * </PRE>
 */
@Service("DdlGrtRqstService")
public class DdlGrtRqstServiceImpl implements DdlGrtRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqDdlGrtMapper waqmapper;

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
    private DdlScriptMapper ddlmapper;

    @Inject
	private GroupWareSendService groupWareSendService;
	
	@Inject
    private MessageSource message;

	@Override
	public int submit(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/** DDL 기타오브젝트 요청서 조회... 유성열 */
	public List<WaqDdlGrt> getDdlGrtRqstList(WaqMstr search) {
		return waqmapper.selectDdlGrtListbyMst(search) ;
	}
	
	/** DDL 기타오브젝트 요청서 상세 조회... 유성열 */
	public WaqDdlGrt getDdlGrtRqstDetail(WaqDdlGrt searchVo) {
		return waqmapper.selectDdlGrtDetail(searchVo);
	}
	

	/** DDL 기타오브젝트 요청서 저장... yeonho */
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
			for (WaqDdlGrt saveVo : (ArrayList<WaqDdlGrt>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);
				
				if(UtilString.null2Blank(saveVo.getSelectYn()).equals("")||UtilString.null2Blank(saveVo.getSelectYn()).equals("N")){
					saveVo.setSelectYn("N");
				}else{
					saveVo.setSelectYn("Y");
				}
				
				if(UtilString.null2Blank(saveVo.getInsertYn()).equals("")||UtilString.null2Blank(saveVo.getInsertYn()).equals("N")){
					saveVo.setInsertYn("N");
				}else{
					saveVo.setInsertYn("Y");
				}
				
				if(UtilString.null2Blank(saveVo.getUpdateYn()).equals("")||UtilString.null2Blank(saveVo.getUpdateYn()).equals("N")){
					saveVo.setUpdateYn("N");
				}else{
					saveVo.setUpdateYn("Y");
				}
				
				if(UtilString.null2Blank(saveVo.getDeleteYn()).equals("")||UtilString.null2Blank(saveVo.getDeleteYn()).equals("N")){
					saveVo.setDeleteYn("N");
				}else{
					saveVo.setDeleteYn("Y");
				}
				
				if(UtilString.null2Blank(saveVo.getExecuteYn()).equals("")||UtilString.null2Blank(saveVo.getExecuteYn()).equals("N")){
					saveVo.setExecuteYn("N");
				}else{
					saveVo.setExecuteYn("Y");
				}

				//단건 저장...
				result += saveDdlGrtRqst(saveVo);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/** DDL 기타오브젝트 단건 저장 insomnia */
	private int saveDdlGrtRqst(WaqDdlGrt saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

		if ("I".equals(tmpstatus)) {
			
			WaqDdlGrt objvo = waqmapper.selectMaxRqstSno(saveVo);   
			
			saveVo.setRqstSno(objvo.getRqstSno());
			
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
	

	/** DDL 기타오브젝트 요청서 체크... insomnia
	 * @throws Exception */
	public int check(WaqMstr mstVo) throws Exception {
		int result = 0;
		//요청서번호 가져온다.
		String rqstNo   = mstVo.getRqstNo();
		String tblnm    = "WAQ_DDL_GRT";
		String bizdtlcd = mstVo.getBizInfo().getBizDtlCd();

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
//				waqRqstVrfDtls.setBizDtlCd("TBL");
		waqRqstVrfDtls.setBizDtlCd(bizdtlcd);
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtls.setBizDtlCd("DDLGRT");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		//순선바꾸면 안됨(엑셀업로드)
		// 접속대상ID, 스키마ID
        waqmapper.updateGrtorKeyId(rqstNo);
        // 접속대상ID, ROLE ID, 권한YN	//권한YN은 제외. 사용자가 직접 선택하기로 함.
        waqmapper.updateGrtedKeyId(rqstNo);
        
        // 오브젝트ID 업데이트
        waqmapper.updateObjectKeyId(rqstNo);
        
        //오브젝트 별 권한 'N'처리
        waqmapper.updateObjectGrtYN(rqstNo);
        
		//===============================
        
		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);


		//검증 시작
		//DDL 테이블 검증
		//DDL 테이블 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);

		//요청서내 중복자료 검증(DDG01)
		checkmap.put("vrfDtlCd", "DDG01");
		waqmapper.checkDupGrt(checkmap);

		//삭제일때 미존재 기타오브젝트 체크(DDG02)
		checkmap.put("vrfDtlCd", "DDG02");
		waqmapper.checkNotExistGrt(checkmap);

		//요청중인 기타오브젝트 (DDG03)
		checkmap.put("vrfDtlCd", "DDG03");
		waqmapper.checkRequestGrt(checkmap);
		
		//DB접속정보 미존재 (DDG04)
		checkmap.put("vrfDtlCd", "DDG04");
		waqmapper.checkNonDbConnTrg(checkmap);

		//DB스키마정보 미존재 (DDG05)
		checkmap.put("vrfDtlCd", "DDG05");
		waqmapper.checkNonDbSch(checkmap);
		
		//DBMS가 다른경우(DDG06)
		checkmap.put("vrfDtlCd", "DDG06");
		waqmapper.checkNotMatchDbms(checkmap);
		
		//오브젝트정보 미존재 (DDG07)
		checkmap.put("vrfDtlCd", "DDG07");
		waqmapper.checkNonObject(checkmap);
		
		//권한없음
		checkmap.put("vrfDtlCd", "DDG08");
		waqmapper.checkNotGrt(checkmap);
		
		//한요청서에 여러 스키마 권한을 요청할 수 없음(권한을 주는 스키마에 대한 결재자를 선택해야 하기 때문에)
		checkmap.put("vrfDtlCd", "DDG09");
		waqmapper.checkRqstMultiSchema(checkmap);
		
		//GRANTOR 에게 기본적으로 부여된 권한에 권한을 신청할 경우
		checkmap.put("vrfDtlCd", "DDG10");
		waqmapper.checkRqstGrantSame(checkmap);
		
		//권한 요청용 ROLE 말고 스키마매핑용 ROLE로 권한을 요청하는 경우
		//checkmap.put("vrfDtlCd", "DDG11");
		//waqmapper.checkRqstGrantMapRole(checkmap);
		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DDG00)
		checkmap.put("vrfDtlCd", "DDG00");
		waqmapper.checkNotChgData(checkmap);

		//기타오브젝트 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);
		
		//result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);
		
		//요청서명 업데이트		
		requestMstService.updateRequestMsterNm(mstVo);  

//		//DDL 스크립트 업데이트...
		updateDdlGrtScriptWaq(mstVo);

		return result;
	}

	/** @param mstVo insomnia
	 * @throws IOException */

	public void updateDdlGrtScriptWaq(WaqMstr mstVo) throws Exception {

		List<WaqDdlGrt> waqlist = ddlScriptService.updateDdlGrtScirptWaq(mstVo);

		for (WaqDdlGrt savevo : waqlist) {
			waqmapper.updateDdlGrtScriptWaq(savevo);
		}

	}

//	/** DDL 테이블 요청서 등록요청... insomnia */
//	public int submit(WaqMstr mstVo) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
	/** DDL 기타오브젝트 요청서 승인... insomnia */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqDdlGrt savevo : (ArrayList<WaqDdlGrt>)reglist) {
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
			logger.debug("DDL기타오브젝트 waq to wam and wah");

			result = 0;
			result += regWaq2Wam(mstVo);

			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}
		
		//결재알림 연동
//		if("Y".equals(UtilString.null2Blank(message.getMessage("wiseda.req.sendnotice", null, Locale.getDefault())))){
//				if(!waq2wam){
//			//		logger.debug("wiseda.req.sendnotice Y!!! : " + mstVo.getRqstUserId());
//					//if(mstVo.getRqstUserId().equals("meta"))
//					groupWareSendService.sendGroupWare(mstVo.getRqstNo());
//				}
//		}			

		return result;
	}
//

	/** @return yeonho
	 * @throws Exception */
	public int regWaq2Wam(WaqMstr mstVo) throws Exception {   
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqDdlGrt> waqclist = waqmapper.selectWaqC(rqstno);

		for (WaqDdlGrt savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setDdlGrtId(id);

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

		return result;
	}



	/** yeonho
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlGrt> list) throws Exception {
		int result = 0 ;

		List<WaqDdlGrt> wamlist = waqmapper.selectddlGrtlist(reqmst, list);

		result += register(reqmst, wamlist);

		return result;
	}

	/** insomnia
	 * @throws Exception */
	public int delDdlGrtRqst(WaqMstr reqmst, ArrayList<WaqDdlGrt> list) throws Exception {
		int result = 0;

		//TODO 성능이 문제가 될시에 한방 SQL로 처리한다.
//		result = waqmapper.deletedmnrqst(reqmst, list);

		for (WaqDdlGrt savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}
	
	public int insertWaqDdlGrtGrtorObjDD(String rqstNo) throws Exception{
		int result = 0;
		
		result = waqmapper.insertWaqDdlGrtGrtorObjDD(rqstNo);
		
		return result;
	}

	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
		reqmst.getBizInfo().setBizDtlCd("DDLGRT");
		
		//waq의 반려내용을 waq로 다시 입력
		result = waqmapper.insertWaqRejected(reqmst, oldRqstNo);
		//마스터 등록
		register(reqmst, null);
		//검증
		check(reqmst);
		
		return result;
	}
}
