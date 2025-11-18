/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndItemRqstServiceImpl.java
 * 2. Package : kr.wise.dq.stnd.service.impl
 * 3. Comment : 표준항목 등록요청 서비스 구현체....
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 28. 오전 8:54:48
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 28. :            : 신규 개발.
 */
package kr.wise.dq.stnd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.stnd.service.WdqStndItemRqstService;
import kr.wise.dq.stnd.service.WdqpDvCanAsm;
import kr.wise.dq.stnd.service.WdqpDvCanAsmMapper;
import kr.wise.dq.stnd.service.WdqpDvCanDic;
import kr.wise.dq.stnd.service.WdqpDvCanDicMapper;
import kr.wise.dq.stnd.service.WdqqDmn;
import kr.wise.dq.stnd.service.WdqqDmnMapper;
import kr.wise.dq.stnd.service.WdqqSditm;
import kr.wise.dq.stnd.service.WdqqSditmMapper;
import kr.wise.dq.stnd.service.WdqqStwd;
import kr.wise.dq.stnd.service.WdqqStwdCnfg;
import kr.wise.dq.stnd.service.WdqqStwdCnfgMapper;
import kr.wise.dq.stnd.service.WdqqStwdMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndItemRqstServiceImpl.java
 * 3. Package  : kr.wise.dq.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 28. 오전 8:54:48
 * </PRE>
 */
@Service("wdqStndItemRqstService")
public class WdqStndItemRqstServiceImpl implements WdqStndItemRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WdqqSditmMapper waqmapper;

	@Inject
	private WdqqStwdMapper WdqqStwdMapper;

	@Inject
	private WdqqDmnMapper WdqqDmnMapper;

	@Inject
	private WdqpDvCanDicMapper WdqpDvCanDicMapper;

	@Inject
	private WdqpDvCanAsmMapper WdqpDvCanAsmMapper;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WdqqStwdCnfgMapper WdqqStwdCnfgMapper;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;

    @Inject
	private EgovIdGnrService requestIdGnrService;

	private CommonVo WdqpDvCanAsmVo;

	/** 표준항목 요청 상세내용 조회 insomnia */
	public WdqqSditm getStndItemRqstDetail(WdqqSditm searchVo) {

		return waqmapper.selectStndItemRqstDetail(searchVo);
	}

	/** 개인정보등급 상세내역 조회 */
	public List<Map<String, Object>> getPersCode() {
		
		return waqmapper.selectPersCode();
	}

	/** 표준항목 요청 리스트 조회 insomnia */
	public List<WdqqSditm> getStndItemRqstList(WaqMstr search) {
		return waqmapper.selectItemRqstListbyMst(search);
	}

	/** 표준항목 분리 기능 insomnia */
	public WdqqSditm getItemWordInfo(WdqqSditm data) {

		String sditmLnm = "";
		String sditmPnm = "";
		String lnmCriDs = "";
		String pnmCriDs = "";
		String dmnLnm = "";
		String dmnPnm = "";
		String dmngLnm = "";
		String dmngId = "";
		String infotpId = "";
		String infotpLnm = "";
		String dataType = "";
		String dataLen = "";
		String dataScal = "";
		String uppDmngId = "";
		String uppDmngLnm = "";

		String sepSditmLnm = data.getSditmLnm();
		String [] arrSepSditmLnm = sepSditmLnm.split(";");
		for(int i=0;i < arrSepSditmLnm.length-1;i++) {
			List<WdqqStwd> list = WdqqStwdMapper.selectListByStwdLnm(data.getRqstNo(), arrSepSditmLnm[i],data.getStndAsrt());
			if(list.size() > 1) {
				sditmLnm += arrSepSditmLnm[i];
				sditmPnm += "_[D]";
//				lnmCriDs += "_"+arrSepSditmLnm[i];
				lnmCriDs += ";"+arrSepSditmLnm[i];
				pnmCriDs += ";[D]";
//				pnmCriDs += ";"+list.get(0).getStwdPnm();
			} else if(list.size() > 0) {
				sditmLnm += arrSepSditmLnm[i];
				sditmPnm += "_"+list.get(0).getStwdPnm();
//				lnmCriDs += "_"+arrSepSditmLnm[i];
				lnmCriDs += ";"+arrSepSditmLnm[i];
				pnmCriDs += ";"+list.get(0).getStwdPnm();
			} else {
				sditmLnm += arrSepSditmLnm[i];
				sditmPnm += "_[X]";
//				lnmCriDs += "_"+arrSepSditmLnm[i];
				lnmCriDs += ";"+arrSepSditmLnm[i];
//				pnmCriDs += ";"+list.get(0).getStwdPnm();
				pnmCriDs += ";[X]";
			}
		}
		//단어정보 입력
		if(arrSepSditmLnm.length > 0) {
			WdqqDmn WdqqDmn = new WdqqDmn();
			WdqqDmn.setRqstNo(data.getRqstNo());
			WdqqDmn.setDmnLnm(arrSepSditmLnm[arrSepSditmLnm.length-1]);
			//TODO : 도메인 한글명으로 여러개 나올경우 어떡할건데???
			List<WdqqDmn> tmpDmn = WdqqDmnMapper.selectListByDmnLnm(WdqqDmn);
			
			if ( tmpDmn == null || tmpDmn.isEmpty() ) {
				sditmLnm += WdqqDmn.getDmnLnm();
				sditmPnm += "_[X]";
				dmnLnm 		= WdqqDmn.getDmnLnm();
				dmnPnm 		= "[X]";
				lnmCriDs +=";" +WdqqDmn.getDmnLnm();
				pnmCriDs +=";[X]";
				WdqqDmn = null;
			} else if (tmpDmn.size() > 1 ) {
				WdqqDmn = tmpDmn.get(0);
				sditmLnm += WdqqDmn.getDmnLnm();
				sditmPnm += "_[D]";
				dmnLnm 		= WdqqDmn.getDmnLnm();
				dmnPnm 		= "[D]";
			} else {
				WdqqDmn = tmpDmn.get(0);
				sditmLnm += WdqqDmn.getDmnLnm();
				sditmPnm += "_"+WdqqDmn.getDmnPnm();
				dmnLnm 		= WdqqDmn.getDmnLnm();
				dmnPnm 		= WdqqDmn.getDmnPnm();
				dmngLnm 	= WdqqDmn.getDmngLnm();
				dmngId 		= WdqqDmn.getDmngId();
				//상위도메인 그룹 추가...
				uppDmngId	= WdqqDmn.getUppDmngId();
				uppDmngLnm	= WdqqDmn.getUppDmngLnm();
				infotpId 	= WdqqDmn.getInfotpId();
				infotpLnm 	= WdqqDmn.getInfotpLnm();
				dataType 	= WdqqDmn.getDataType();
				if(WdqqDmn.getDataLen() != null) {
					dataLen = WdqqDmn.getDataLen()+"";
				}
				if(WdqqDmn.getDataScal() != null) {
					dataScal = WdqqDmn.getDataScal()+"";
				}
			}
			

		}
		if(lnmCriDs.equals("")) {	lnmCriDs = ";";	}
		if(pnmCriDs.equals("")) {	pnmCriDs = ";";	}

		WdqqSditm result = new WdqqSditm();

		result.setSditmLnm(sditmLnm);
		result.setSditmPnm(sditmPnm.substring(1));
		result.setLnmCriDs(lnmCriDs.substring(1));
		result.setPnmCriDs(pnmCriDs.substring(1));
		result.setDmnLnm(dmnLnm);
		result.setDmnPnm(dmnPnm);
		result.setDmngId(dmngId);
		result.setDmngLnm(dmngLnm);
		//상위도메인 그룹 추가....
		result.setUppDmngId(uppDmngId);
		result.setUppDmngLnm(uppDmngLnm);
		result.setInfotpId(infotpId);
		result.setInfotpLnm(infotpLnm);
		result.setDataType(dataType);
		if(StringUtils.hasText(dataLen)) {
			result.setDataLen(Integer.parseInt(dataLen));
		}
		if(StringUtils.hasText(dataScal)) {
			result.setDataScal(Integer.parseInt(dataScal));
		}

		return result;
	}
	
	/** 표준항목 자동분할 리스트스 insomnia */
	public List<WdqpDvCanAsm> getItemDivList(WdqpDvCanDic record) {
		//분할정보 조회
		List<WdqpDvCanAsm> list = WdqpDvCanAsmMapper.selectList(record.getDvRqstNo());
		return list;
	}


	/** 표준항목 자동분할 리스트스 insomnia 
	 * @throws Exception */
	public List<WdqpDvCanAsm> getItemDivisionList(WdqpDvCanDic record) throws Exception {
		
		String dvRqstNo = requestIdGnrService.getNextStringId();
		record.setDvRqstNo(dvRqstNo);
		
		int execCnt = 0;
		//포함 단어정보 입력
		WdqpDvCanDicMapper.insertStwd(record);
		//포함 도메인 정보 입력
		WdqpDvCanDicMapper.insertDmn(record);

		//초기데이터 입력
		execCnt = WdqpDvCanAsmMapper.insertFirst(record);

		//도메인정보로 입력
		WdqpDvCanAsmMapper.insertAsmDmn(record.getDvRqstNo());

		//도메인 정보 존재시 초기데이터 삭제
		//WdqpDvCanAsmMapper.deleteExistDmnAsm(record.getDvRqstNo());
						
		while(execCnt > 0) {
			//단어정보로 분할
			WdqpDvCanAsmMapper.insertAsmDic(record.getDvRqstNo());
			//미존재단어정보 반영
			WdqpDvCanAsmMapper.insertAsmNotExistDic(record.getDvRqstNo());
			//이전데이터 삭제
			WdqpDvCanAsmMapper.deleteNotEndPrcAsmDic(record.getDvRqstNo());
			//분할데이터 존재시 분할상태로 코드 업데이트
			execCnt = WdqpDvCanAsmMapper.updateNotEndPrcAsmDic(record.getDvRqstNo());
		}
		
		
		//분할정보 조회
		List<WdqpDvCanAsm> list = WdqpDvCanAsmMapper.selectList(record.getDvRqstNo()); 
		
		//분할정보삭제
		WdqpDvCanDicMapper.deleteDvCanDicByDvRqstNo(record.getDvRqstNo());
		WdqpDvCanAsmMapper.deleteDvCanAsmByDvRqstNo(record.getDvRqstNo());

		return list;
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
			for (WdqqSditm saveVo : (ArrayList<WdqqSditm>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveRqstStndItem(saveVo);
			}

		}
		//인포타입이 없는 경우 도메인이름으로 업데이트한다...
		//waqmapper.updateItemInfoType(rqstNo);
		
		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;

	}

	/** @return insomnia */
	public int saveRqstStndItem(WdqqSditm saveVo) {
		int result = 0;

		String tmpstatus = saveVo.getIbsStatus();

		WdqqDmn WdqqDmn = new WdqqDmn();
		WdqqDmn.setDmnLnm(saveVo.getDmnLnm());
		WdqqDmn.setDmnPnm(saveVo.getDmnPnm());
		String dmnId = WdqqDmnMapper.selectDmnId(WdqqDmn);
		
		saveVo.setDmnId(dmnId);
		
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
		setStwdCnfg(saveVo);

		return result;
	}

	/** 단어구성정보 셋팅(삭제 후 추가) */
	private void setStwdCnfg(WdqqSditm saveVo) {
		//단어구성정보를 먼저 삭제한다.
		WdqqStwdCnfg data = new WdqqStwdCnfg();
		data.setBizDtlCd("SDITM");
		data.setRqstNo(saveVo.getRqstNo());
		data.setRqstSno(saveVo.getRqstSno());
		data.setRqstDcd(saveVo.getRqstDcd());
		
		data.setStndAsrt(saveVo.getStndAsrt());

		//단어구성정보를 먼저 삭제한다(선 삭제 후 입력)
		WdqqStwdCnfgMapper.deleteBySno(data);

		//단어구성정보를 추가한다. (단 삭제요청일 경우에는 추가하지 않음...)
		if(!"D".equals(saveVo.getIbsStatus())) {

			if (StringUtils.hasText(saveVo.getLnmCriDs()) && !"DD".equals(saveVo.getRqstDcd())) {
//				String[] arrStwdLnm = saveVo.getLnmCriDs().split("_");
//				String[] arrStwdPnm = saveVo.getSditmPnm().split("_");
				String[] arrStwdLnm = saveVo.getLnmCriDs().split(";");
				String[] arrStwdPnm = saveVo.getPnmCriDs().split(";");


				for(int i=0;i<arrStwdLnm.length;i++) {
					data.setStwdLnm(arrStwdLnm[i]);
					if(i < arrStwdPnm.length) {
						data.setStwdPnm(arrStwdPnm[i]);
					} else {
						data.setStwdPnm("");
					}
					//단어구성정보 추가
					WdqqStwdCnfgMapper.insertSelective(data);
				}
			}
		}

	}

	/** 표준항목 검증 insomnia */
	public int check(WaqMstr mstVo) {
		int result = 0;

		//요청서번호 가져온다.
		String rqstNo = mstVo.getRqstNo();
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		
		String adminYn = UtilString.null2Blank(user.getIsAdminYn());
		

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setBizDtlCd("SDITM");
//		waqRqstVrfDtls.setBizDtlCd(mstVo.getBizInfo().getBizDtlCd());
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);

		//삭제일경우 모든 컬럼 업데이트
		waqmapper.updateSditmDelInfo(rqstNo);
						
		//인포타입 으로 데이터타입 길이 업데이트
		waqmapper.updateDataTypeByInfotypLnm(rqstNo); 
		
		//검증 시작
		//표준항목 검증
		//도메인 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm", "WDQQ_SDITM");
//		checkmap.put("tblnm", mstVo.getBizInfo().getTblNm());
		checkmap.put("rqstNo", rqstNo);
		checkmap.put("bizDtlCd", "SDITM");
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
//		checkmap.put("vrfDtlCd", "SI003");
//		waqmapper.checkLnmSymn(checkmap);

		//표준단어 존재 체크(SI004)
		checkmap.put("vrfDtlCd", "SI004");
		waqmapper.checkExistStwd(checkmap);

		//인포타입 체크(SI005)
//		checkmap.put("vrfDtlCd", "SI005");
//		waqmapper.checkInfoType(checkmap);

		//인포타입명에 따른 데이터타입 체크(SI013)
//		checkmap.put("vrfDtlCd", "SI013");
//		waqmapper.checkDataType(checkmap);

		//도메인 미존재(SI006)
		checkmap.put("vrfDtlCd", "SI006");
		waqmapper.checkNotExistDmn(checkmap);

		//항목 구성정보 오류(SI007)
//		checkmap.put("vrfDtlCd", "SI007");
//		waqmapper.checkSditmStwdAsm(checkmap);

		//물리명 유니크 검사(SI008)
		checkmap.put("vrfDtlCd", "SI008");
		waqmapper.checkDupSditmPnm(checkmap);

		//항목 논리명 최대길이를 사용 할 경우(SI009)
//		checkmap.put("vrfDtlCd", "SI009");
//		waqmapper.checkSditmLnmMaxLen(checkmap);

		//항목 물리명 최대길이를 사용 할 경우(SI010)
//		checkmap.put("vrfDtlCd", "SI010");
//		waqmapper.checkSditmPnmMaxLen(checkmap);
		
		
		//코드도메인 인포타입변경시 할 경우(SI015)
//		checkmap.put("vrfDtlCd", "SI015");
//		waqmapper.checkSditmCodeInfoTpChg(checkmap);  
		
		//삭제일때 테이블존재 체크(SI016)
		checkmap.put("vrfDtlCd", "SI016");
		waqmapper.checkExistCol(checkmap);
		
		//개인정보여부 예 일때 개인정보등급 체크 검사
		//checkmap.put("vrfDtlCd", "SI018");
		//waqmapper.checkPersInfoGrd(checkmap);
		
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
		for (WdqqSditm savevo : (ArrayList<WdqqSditm>)reglist) {
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
		boolean waq2wam = requestApproveService.setApproveProcessWdq(mstVo);

		
		System.out.println("========================================="+mstVo);
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
			//표준항목 분할정보 남아서 삭제.. 확인 
			WdqpDvCanDicMapper.deleteDvCanDicByDvRqstNo(rqstNo);
			WdqpDvCanAsmMapper.deleteDvCanAsmByDvRqstNo(rqstNo);
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
		List<WdqqSditm> waqclist = waqmapper.selectWaqC(rqstno);
		for (WdqqSditm savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setSditmId(id);

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
		result += waqmapper.updateWaqId(rqstno);

//		result += waqmapper.updateUppDmnId(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

		result += waqmapper.updateWahTransYn(rqstno);	             //테스트변환여부 업데이트
	
		//표준단어 구성은 C,U에 대해 기존꺼 삭제 후 다시 저장으로...
		WdqqStwdCnfgMapper.deleteWAMItem(rqstno);
		WdqqStwdCnfgMapper.insertWAMItem(rqstno);


		return result;
	}

	/** 표준항목 변경대상 조회 및 추가... insomnia * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, List<WdqqSditm> list) throws Exception {
		int result = 0;

		//WAM에서 WAQ에 적재할 내용을 가져온다...
		ArrayList<WdqqSditm> wamlist = waqmapper.selectwamlist(reqmst, list);

		result = register(reqmst, wamlist);
		return result;
	}

	/** insomnia
	 * @throws Exception */
	public int delStndItemRqstList(WaqMstr reqmst, ArrayList<WdqqSditm> list) throws Exception {
		int result = 0;

		//TODO 성능 문제 발생시 한방 SQL로 처리한다.
		//result = waqmapper.deleteitemrqst(reqmst, list);

		for (WdqqSditm savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	/** 항목 자동 분할 후 요청서에 저장한다. insomnia
	 * @throws Exception */
	public int regitemdivision(WaqMstr reqmst, List<WdqqSditm> list) throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		int result = 0;
		String rqstno = reqmst.getRqstNo();
		String dvrqstno = requestIdGnrService.getNextStringId();

		int execCnt = 0;

		WdqpDvCanDic record = new WdqpDvCanDic();
		record.setDvRqstNo(dvrqstno);
		record.setRqstNo(rqstno);

		//포함 단어정보 입력
		WdqpDvCanDicMapper.insertStwdAll(record);
		//포함 도메인 정보 입력
		WdqpDvCanDicMapper.insertDmnAll(record);

		for (WdqqSditm savevo : list) {
			WdqpDvCanDic savedic = new WdqpDvCanDic();
//			savedic.setRqstNo(rqstno);
			savedic.setDvRqstNo(dvrqstno);
			savedic.setTrgLnm(savevo.getSditmLnm());
			savedic.setDvRqstUserId(userid);
			//초기데이터 입력
			execCnt += WdqpDvCanAsmMapper.insertFirst(savedic);
		}

		//도메인정보로 입력
		WdqpDvCanAsmMapper.insertAsmDmn(dvrqstno);

		//도메인 정보 존재시 초기데이터 삭제
		WdqpDvCanAsmMapper.deleteExistDmnAsm(dvrqstno);

		while(execCnt > 0) {
			//단어정보로 분할
			WdqpDvCanAsmMapper.insertAsmDic(dvrqstno);
			//미존재단어정보 반영
			WdqpDvCanAsmMapper.insertAsmNotExistDic(dvrqstno);
			//이전데이터 삭제
			WdqpDvCanAsmMapper.deleteNotEndPrcAsmDic(dvrqstno);
			//분할데이터 존재시 분할상태로 코드 업데이트
			execCnt = WdqpDvCanAsmMapper.updateNotEndPrcAsmDic(dvrqstno);
		}
		//분할정보 조회
		List<WdqpDvCanAsm> divlist = WdqpDvCanAsmMapper.selectItemDivList(dvrqstno);

		//분할정보삭제
//		WdqpDvCanDicMapper.deleteDvCanDicByDvRqstNo(dvrqstno);
//		WdqpDvCanAsmMapper.deleteDvCanAsmByDvRqstNo(dvrqstno);

		Map<String, WdqpDvCanAsm> divmap = new HashMap<String, WdqpDvCanAsm>();
		for (WdqpDvCanAsm divvo : divlist) {
			divmap.put(divvo.getDvTrgLnm(), divvo);
		}
		logger.debug("divmapsize():{}", divmap.size());

		for (WdqqSditm savevo : list) {
			String itemlnm = savevo.getSditmLnm();

			if (divmap.containsKey(itemlnm)) {
				WdqpDvCanAsm findvo = divmap.get(itemlnm);

				savevo.setRqstNo(rqstno);
				savevo.setSditmPnm(findvo.getDicAsmPnm());
				savevo.setDmnLnm(findvo.getDmnLnm());
				savevo.setDmnPnm(findvo.getDmnPnm());
				String dslnm = findvo.getDicAsmDsLnm();
				if (StringUtils.hasText(dslnm)) {
//					savevo.setLnmCriDs(dslnm.replaceAll(";", "_"));
					savevo.setLnmCriDs(dslnm.replaceAll("_", ";"));
				}
			}

		}

		result = register(reqmst, list);

		return result;
	}
	
	
	
	/** 표준항목 자동분할 리스트스 insomnia */
	public List<WdqpDvCanAsm> getItemDvRqstList(WdqpDvCanDic record) {
		//분할정보 조회
		List<WdqpDvCanAsm> list = WdqpDvCanAsmMapper.selectItemDvRqstList(record);
		return list;
	}
	
	/** 항목 자동 분할  저장한다. insomnia
	 * @throws Exception */
	public Map<String, String> regItemAutoDiv(List<WdqpDvCanAsm> list, WdqpDvCanDic record2) throws Exception {
		Map<String, String> resultMap =  new HashMap<String, String>();
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		String dvrqstno = requestIdGnrService.getNextStringId();
		
		int execCnt = 0;
		int rtnCnt = 0;
		
		WdqpDvCanDic record = new WdqpDvCanDic();
		record.setDvRqstNo(dvrqstno);
		record2.setDvRqstNo(dvrqstno);
		//포함 단어정보 입력
		WdqpDvCanDicMapper.insertStwdAll(record);
		//포함 도메인 정보 입력
		WdqpDvCanDicMapper.insertDmnAll(record);
		
		for (WdqpDvCanAsm savevo : list) {
			savevo.setDvRqstNo(dvrqstno);
			savevo.setDvRqstUserId(userid);
			savevo.setDvSeCd("I");
			
			//초기데이터 입력
			execCnt += WdqpDvCanAsmMapper.insertDvListFirst(savevo);
		}
		
		rtnCnt = execCnt;
		
		//도메인정보로 입력
		WdqpDvCanAsmMapper.insertAsmDmn(dvrqstno);
		
		//도메인 정보 존재시 초기데이터 삭제
		//WdqpDvCanAsmMapper.deleteExistDmnAsm(dvrqstno);
		
		while(execCnt > 0) {
			//단어정보로 분할
			WdqpDvCanAsmMapper.insertAsmDic(dvrqstno);
			//미존재단어정보 반영
			WdqpDvCanAsmMapper.insertAsmNotExistDic(dvrqstno);
			//이전데이터 삭제
			WdqpDvCanAsmMapper.deleteNotEndPrcAsmDic(dvrqstno);
			//분할데이터 존재시 분할상태로 코드 업데이트
			execCnt = WdqpDvCanAsmMapper.updateNotEndPrcAsmDic(dvrqstno);
		}
		
		//분할정보삭제
		WdqpDvCanDicMapper.deleteDvCanDicByDvRqstNo(dvrqstno);
//		WdqpDvCanAsmMapper.deleteDvCanAsmByDvRqstNo(dvrqstno);
		
		//분할 결과 검증 UPDATE
		//도메인명 미존재
		WdqpDvCanDicMapper.checkDmnInfo(dvrqstno);
		//단어 미존재
		WdqpDvCanDicMapper.checkSdwd(dvrqstno);
		
		//인포타입 미존재
		WdqpDvCanDicMapper.checkInpotpLnm(dvrqstno); 
						
		//항목기존재
		WdqpDvCanDicMapper.checkDupSditm(dvrqstno);
		//구성정보 오류
		WdqpDvCanDicMapper.checkAsmDs(dvrqstno);
				
		//물리명 길이
		WdqpDvCanDicMapper.checkPnmMaxLen(dvrqstno);
		//물리명 끝자리 숫자체크
		WdqpDvCanDicMapper.checkEndNum(dvrqstno);
		
		//분할정보삭제
		//WdqpDvCanAsmMapper.deleteDvCanAsmByDvOrderBy(record2); //분할된중복에서 select쿼리 제외한 나머지 여기서도 중복나면 동음이의어
		//WdqpDvCanAsmMapper.deleteDvCanAsmByDup(dvrqstno); //중복분할정보 - 도메인이 두개일때(큰거, 작은거) 동음이의어는 삭제안함..
		
		resultMap.put("result", Integer.toString(rtnCnt) );
    	resultMap.put("dvrqstno", dvrqstno);
		
		return resultMap;
		
	}
	
	/** 항목 자동 분할  저장한다. insomnia
	 * @throws Exception */
	public Map<String, String> delItemAutoDiv(List<WdqpDvCanAsm> list) throws Exception {
		Map<String, String> resultMap =  new HashMap<String, String>();
		int rtnCnt = 0;
		String dvrqstno = "";
		for (WdqpDvCanAsm savevo : list) {
			dvrqstno = savevo.getDvRqstNo();
			rtnCnt += WdqpDvCanAsmMapper.delItemAutoDiv(savevo);
		}
		
		
		resultMap.put("result", Integer.toString(rtnCnt) );
		resultMap.put("dvrqstno", dvrqstno);
		
		return resultMap;
		
	}
	
	public int regStndItemByDiv(WaqMstr mstVo, ArrayList<WdqpDvCanAsm> list){
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if( "N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}

		String rqstNo = mstVo.getRqstNo();
		String dvRqstNo = "";

		int result = 0;
		int delResult = 0;

		if(list != null) {
			for (WdqpDvCanAsm WdqpDvCanAsmVo : (ArrayList<WdqpDvCanAsm>)list) {
				//선택된 데이터 만
				if(WdqpDvCanAsmVo.getIbsCheck().equals("1") ){
					//등록가능 데이터만
					if(WdqpDvCanAsmVo.getRegPosYn().equals("N")){
						continue;
					}
					
					WdqqSditm saveVo = new WdqqSditm(); 
					//요청번호 셋팅...
					saveVo.setFrsRqstUserId(userid);
					saveVo.setRqstUserId(userid);
					saveVo.setRqstNo(rqstNo);
					saveVo.setRqstDcd("CU");
					
					saveVo.setSditmLnm(WdqpDvCanAsmVo.getDicAsmLnm());
					saveVo.setSditmPnm(WdqpDvCanAsmVo.getDicAsmPnm());
//					saveVo.setLnmCriDs( WdqpDvCanAsmVo.getDicAsmDsLnm().replaceAll(";","_"));
					saveVo.setLnmCriDs( WdqpDvCanAsmVo.getDicAsmDsLnm());
					saveVo.setPnmCriDs( WdqpDvCanAsmVo.getDicAsmDsPnm());
					
					saveVo.setDmnLnm(WdqpDvCanAsmVo.getDmnLnm());
					saveVo.setDmnPnm(WdqpDvCanAsmVo.getDmnPnm());
					saveVo.setDmngLnm(WdqpDvCanAsmVo.getDmngLnm());
					saveVo.setInfotpId(WdqpDvCanAsmVo.getInfotpId());
					saveVo.setInfotpLnm(WdqpDvCanAsmVo.getInfotpLnm());
					saveVo.setEncYn(WdqpDvCanAsmVo.getDvEncYn());
					saveVo.setObjDescn(WdqpDvCanAsmVo.getDvObjDescn());
					saveVo.setDataType(WdqpDvCanAsmVo.getDataType());
					saveVo.setDataLen(WdqpDvCanAsmVo.getDataLen());
					saveVo.setDataScal(WdqpDvCanAsmVo.getDataScal());

					saveVo.setStndAsrt(WdqpDvCanAsmVo.getStndAsrt());
					saveVo.setPersInfoCnvYn(WdqpDvCanAsmVo.getPersInfoCnvYn());
					saveVo.setPersInfoGrd(WdqpDvCanAsmVo.getPersInfoGrd());
				    
					dvRqstNo = WdqpDvCanAsmVo.getDvRqstNo();
					//단건 저장...
					result += waqmapper.insertSelective(saveVo);
					
					//단어구성정보 셋팅(삭제 후 추가)
					setStwdCnfg(saveVo);
					
					//항목분할 삭제
//					delResult += WdqpDvCanAsmMapper.delItemAutoDiv(WdqpDvCanAsmVo);
				}
			}
		}
		//항목분할 삭제
//		delResult += WdqpDvCanAsmMapper.deleteDvCanAsmByDvRqstNo(dvRqstNo);
		
		logger.debug(" REQ CNT : "+result + "    DEL CNT : " + delResult);
		
		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);
		return result;
	}

	/** insomnia */
	public int regapprove(WaqMstr mstVo, List<WdqqSditm> reglist) {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WdqqSditm savevo : (ArrayList<WdqqSditm>)reglist) {
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
	      List<WdqqSditm> list = waqmapper.selectExistsItemCheck(reqmst);
	      
	      if(list.isEmpty()){
	    	  return 0;
	      }else{
	    	  return 1;
	      }
	
	}
	
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
		public List<WdqqSditm> getUnuseStndItemRqstList(WdqqSditm data) {
			return waqmapper.selectUnuseStndItemList(data);
		}

		

}
