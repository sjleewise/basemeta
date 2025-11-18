/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AppStndItemRqstServiceImpl.java
 * 2. Package : kr.wise.meta.app.service.impl
 * 3. Comment : 표준항목 등록요청 서비스 구현체....
 * 4. 작성자  : mse
 * 5. 작성일   : 2016. 3. 16. 오전 10:55:20
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    mse : 2016. 3. 16. :            : 신규 개발.
 */
package kr.wise.meta.app.service.impl;

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
import kr.wise.meta.app.service.AppStndItemRqstService;
import kr.wise.meta.app.service.WaqAppSditm;
import kr.wise.meta.app.service.WaqAppSditmMapper;
import kr.wise.meta.app.service.WaqAppStwd;
import kr.wise.meta.app.service.WaqAppStwdMapper;
import kr.wise.meta.stnd.service.WapDvCanAsm;
import kr.wise.meta.stnd.service.WapDvCanAsmMapper;
import kr.wise.meta.stnd.service.WapDvCanDic;
import kr.wise.meta.stnd.service.WapDvCanDicMapper;
import kr.wise.meta.stnd.service.WaqSditm;
import kr.wise.meta.stnd.service.WaqStwdCnfg;
import kr.wise.meta.stnd.service.WaqStwdCnfgMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AppStndItemRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.app.service.impl
 * 4. Comment  :
 * 5. 작성자   : mse
 * 6. 작성일   : 2016. 3. 16. 오전 10:55:20
 * </PRE>
 */
@Service("appStndItemRqstService")
public class AppStndItemRqstServiceImpl implements AppStndItemRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqAppSditmMapper waqmapper;

	@Inject
	private WaqAppStwdMapper waqStwdMapper;

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
    
    @Inject
    private WapDvCanDicMapper wapDvCanDicMapper;
    
    @Inject
    private WapDvCanAsmMapper wapDvCanAsmMapper;
    
	@Inject
	private WaqStwdCnfgMapper waqStwdCnfgMapper;

	/** 표준항목 요청 상세내용 조회 insomnia */
	public WaqAppSditm getStndItemRqstDetail(WaqAppSditm searchVo) {

		return waqmapper.selectStndItemRqstDetail(searchVo);
	}

	/** 표준항목 요청 리스트 조회 insomnia */
	public List<WaqAppSditm> getStndItemRqstList(WaqMstr search) {
		return waqmapper.selectItemRqstListbyMst(search);
	}

	/** 표준항목 분리 기능 insomnia */
	public WaqAppSditm getItemWordInfo(WaqAppSditm data) {

		String appSditmLnm = "";
		String appSditmPnm = "";
		String lnmCriDs = "";
		String pnmCriDs = "";

		String sepSditmLnm = data.getAppSditmLnm();
		String [] arrSepSditmLnm = sepSditmLnm.split(";");
		for(int i=0;i < arrSepSditmLnm.length-1;i++) {
			List<WaqAppStwd> list = waqStwdMapper.selectListByStwdLnm(data.getRqstNo(), arrSepSditmLnm[i]);
			if(list.size() > 1) {
				appSditmLnm += arrSepSditmLnm[i];
				appSditmPnm += "_[D]";
//				lnmCriDs += "_"+arrSepSditmLnm[i];
				lnmCriDs += ";"+arrSepSditmLnm[i];
				pnmCriDs += ";"+list.get(0).getAppStwdPnm();
			} else if(list.size() > 0) {
				appSditmLnm += arrSepSditmLnm[i];
				appSditmPnm += "_"+list.get(0).getAppStwdPnm();
//				lnmCriDs += "_"+arrSepSditmLnm[i];
				lnmCriDs += ";"+arrSepSditmLnm[i];
				pnmCriDs += ";"+list.get(0).getAppStwdPnm();
			} else {
				appSditmLnm += arrSepSditmLnm[i];
				appSditmPnm += "_[X]";
//				lnmCriDs += "_"+arrSepSditmLnm[i];
				lnmCriDs += ";"+arrSepSditmLnm[i];
				pnmCriDs += ";"+list.get(0).getAppStwdPnm();
			}
		}
		//단어정보 입력
//		if(arrSepSditmLnm.length > 0) {
//			WaqDmn waqDmn = new WaqDmn();
//			waqDmn.setRqstNo(data.getRqstNo());
//			waqDmn.setDmnLnm(arrSepSditmLnm[arrSepSditmLnm.length-1]);
//			//TODO : 도메인 한글명으로 여러개 나올경우 어떡할건데???
//			List<WaqDmn> tmpDmn = waqDmnMapper.selectListByDmnLnm(waqDmn);
//			
//			if ( tmpDmn == null || tmpDmn.isEmpty() ) {
//				waqDmn = null;
//			} else if (tmpDmn.size() > 1 ) {
//				waqDmn = tmpDmn.get(0);
//				appSditmLnm += waqDmn.getDmnLnm();
//				appSditmPnm += "_[D]";
//				dmnLnm 		= waqDmn.getDmnLnm();
//				dmnPnm 		= "[D]";
//			} else {
//				waqDmn = tmpDmn.get(0);
//				appSditmLnm += waqDmn.getDmnLnm();
//				appSditmPnm += "_"+waqDmn.getDmnPnm();
//				dmnLnm 		= waqDmn.getDmnLnm();
//				dmnPnm 		= waqDmn.getDmnPnm();
//				dmngLnm 	= waqDmn.getDmngLnm();
//				dmngId 		= waqDmn.getDmngId();
//				//상위도메인 그룹 추가...
//				uppDmngId	= waqDmn.getUppDmngId();
//				uppDmngLnm	= waqDmn.getUppDmngLnm();
//				infotpId 	= waqDmn.getInfotpId();
//				infotpLnm 	= waqDmn.getInfotpLnm();
//				dataType 	= waqDmn.getDataType();
//				if(waqDmn.getDataLen() != null) {
//					dataLen = waqDmn.getDataLen()+"";
//				}
//				if(waqDmn.getDataScal() != null) {
//					dataScal = waqDmn.getDataScal()+"";
//				}
//			}
//			
//
//		}
//		if(lnmCriDs.equals("")) {	lnmCriDs = ";";	}
//		if(pnmCriDs.equals("")) {	pnmCriDs = ";";	}

		WaqAppSditm result = new WaqAppSditm();

		
		result.setLnmCriDs(lnmCriDs.substring(1));
		result.setPnmCriDs(pnmCriDs.substring(1));
		
		return result;
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
			for (WaqAppSditm saveVo : (ArrayList<WaqAppSditm>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveRqstStndItem(saveVo);
			}

		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;

	}

	/** @return insomnia */
	public int saveRqstStndItem(WaqAppSditm saveVo) {
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
		setStwdCnfg(saveVo);

		return result;
	}
	
	/** 단어구성정보 셋팅(삭제 후 추가) */
	private void setStwdCnfg(WaqAppSditm saveVo) {
		//단어구성정보를 먼저 삭제한다.
		WaqStwdCnfg data = new WaqStwdCnfg();
		data.setBizDtlCd("API");
		data.setRqstNo(saveVo.getRqstNo());
		data.setRqstSno(saveVo.getRqstSno());
		data.setRqstDcd(saveVo.getRqstDcd());

		//단어구성정보를 먼저 삭제한다(선 삭제 후 입력)
		waqStwdCnfgMapper.deleteBySno(data);

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
					waqStwdCnfgMapper.insertSelective(data);
				}
			}
		}

	}

	/** 표준항목 검증 insomnia */
	public int check(WaqMstr mstVo) {
		int result = 0;

		//요청서번호 가져온다.
		String rqstNo = mstVo.getRqstNo();

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setBizDtlCd("API");
//		waqRqstVrfDtls.setBizDtlCd(mstVo.getBizInfo().getBizDtlCd());
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);

		//APP물리명 변경(데이터타입기준으로 구버전과 같게 ex.varchar=S, number=N)
		waqmapper.updateLnmPnm(rqstNo);
		
		//검증 시작
		//표준항목 검증
		//도메인 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm", "WAQ_APP_SDITM");
//		checkmap.put("tblnm", mstVo.getBizInfo().getTblNm());
		checkmap.put("rqstNo", rqstNo);
		checkmap.put("bizDtlCd", "API");
//		checkmap.put("bizDtlCd", mstVo.getBizInfo().getBizDtlCd());

		//등록요청중인 항목 검증 (AI012)
		checkmap.put("vrfDtlCd", "AI012");
		waqmapper.checkRequestDmn(checkmap);

		//요청서내 중복자료 검증(AI001)
		checkmap.put("vrfDtlCd", "AI001");
		waqmapper.checkDupSditm(checkmap);

		//삭제일때 미존재항목 체크(AI002)
		checkmap.put("vrfDtlCd", "AI002");
		waqmapper.checkNotExistSditm(checkmap);

		//유사어 존재(AI003)
//		checkmap.put("vrfDtlCd", "AI003");
//		waqmapper.checkLnmSymn(checkmap);

		//표준단어 존재 체크(AI004)
		checkmap.put("vrfDtlCd", "AI004");
		waqmapper.checkExistStwd(checkmap);

		//데이터 길이 체크(AI005)
		checkmap.put("vrfDtlCd", "AI005");
		waqmapper.checkDataLen(checkmap);
		
		//데이터타입  체크(AI006)
		checkmap.put("vrfDtlCd", "AI006");
		waqmapper.checkDataType(checkmap);

		//물리명 유니크 검사(AI008)
//		checkmap.put("vrfDtlCd", "AI008");
//		waqmapper.checkDupSditmPnm(checkmap);

		//항목 물리명 최대값 검증을 사용 할 경우(AI009)
//		checkmap.put("vrfDtlCd", "AI009");
//		waqmapper.checkSditmPnmMaxLen(checkmap);

		//항목 논리명 최대값 검증을 사용 할 경우(AI010)
//		checkmap.put("vrfDtlCd", "AI010");
//		waqmapper.checkSditmLnmMaxLen(checkmap);

		//항목 물리명 첫 글자 숫자 여부 검사(AI011)
//		checkmap.put("vrfDtlCd", "AI011");
//		waqmapper.checkSditmPnmStrNum(checkmap);

		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(AI000)
		checkmap.put("vrfDtlCd", "AI000");
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
		for (WaqAppSditm savevo : (ArrayList<WaqAppSditm>)reglist) {
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
		List<WaqAppSditm> waqclist = waqmapper.selectWaqC(rqstno);
		for (WaqAppSditm savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setAppSditmId(id);

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
//		result += waqmapper.updateWaqId(rqstno);

//		result += waqmapper.updateUppDmnId(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);


		return result;
	}

	/** 표준항목 변경대상 조회 및 추가... insomnia * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, List<WaqAppSditm> list) throws Exception {
		int result = 0;

		//WAM에서 WAQ에 적재할 내용을 가져온다...
		ArrayList<WaqAppSditm> wamlist = waqmapper.selectwamlist(reqmst, list);

		result = register(reqmst, wamlist);
		return result;
	}

	/** insomnia
	 * @throws Exception */
	public int delStndItemRqstList(WaqMstr reqmst, ArrayList<WaqAppSditm> list) throws Exception {
		int result = 0;

		//TODO 성능 문제 발생시 한방 SQL로 처리한다.
		//result = waqmapper.deleteitemrqst(reqmst, list);

		for (WaqAppSditm savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	/** 항목 자동 분할 후 요청서에 저장한다. insomnia
	 * @throws Exception */
	public int regitemdivision(WaqMstr reqmst, List<WaqAppSditm> list) throws Exception {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		int result = 0;
		String rqstno = reqmst.getRqstNo();
		String dvrqstno = requestIdGnrService.getNextStringId();

		int execCnt = 0;

		result = register(reqmst, list);

		return result;
	}
	
	
	
	/** insomnia */
	public int regapprove(WaqMstr mstVo, List<WaqAppSditm> reglist) {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqAppSditm savevo : (ArrayList<WaqAppSditm>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqmapper.updatervwStsCd(savevo);
		}

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result < 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}
		return result;
	}
	
	/** APP용어 자동분할 리스트스  
	 * @throws Exception */
//	public List<WapDvCanAsm> getItemDivisionList(WapDvCanDic record) throws Exception {
//		
//		String dvRqstNo = requestIdGnrService.getNextStringId();
//		record.setDvRqstNo(dvRqstNo);
//		
//		int execCnt = 0;
//		//포함 단어정보 입력
//		wapDvCanDicMapper.insertAppStwd(record);
//
//		//초기데이터 입력
//		execCnt = wapDvCanAsmMapper.insertFirstApp(record);
//
//		while(execCnt > 0) {
//			//단어정보로 분할
//			wapDvCanAsmMapper.insertAsmAppDic(record.getDvRqstNo());
//			//미존재단어정보 반영
//			wapDvCanAsmMapper.insertAsmNotExistAppDic(record.getDvRqstNo());
//			//이전데이터 삭제
//			wapDvCanAsmMapper.deleteNotEndPrcAsmAppDic(record.getDvRqstNo());
//			//분할데이터 존재시 분할상태로 코드 업데이트
//			execCnt = wapDvCanAsmMapper.updateNotEndPrcAsmAppDic(record.getDvRqstNo());
//		}
//		//분할정보 조회
//		List<WapDvCanAsm> list = wapDvCanAsmMapper.selectList(record.getDvRqstNo());
//		//분할정보삭제
//		wapDvCanDicMapper.deleteDvCanDicByDvRqstNo(record.getDvRqstNo());
//		wapDvCanAsmMapper.deleteDvCanAsmByDvRqstNo(record.getDvRqstNo());
//
//		return list;
//	}
	
	/** 표준항목 자동분할 리스트스  
	 * @throws Exception */
	public List<WapDvCanAsm> getItemDivisionList(WapDvCanDic record) throws Exception {
		
		String dvRqstNo = requestIdGnrService.getNextStringId();
		record.setDvRqstNo(dvRqstNo);
		
		int execCnt = 0;
		//포함 단어정보 입력
		wapDvCanDicMapper.insertAppStwd(record);

		//초기데이터 입력
		execCnt = wapDvCanAsmMapper.insertFirst(record);

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
		//언더바 치환
		wapDvCanAsmMapper.updateUnderbar(record.getDvRqstNo());
		//분할정보 조회
		List<WapDvCanAsm> list = wapDvCanAsmMapper.selectList(record.getDvRqstNo()); 
		
		//분할정보삭제
		wapDvCanDicMapper.deleteDvCanDicByDvRqstNo(record.getDvRqstNo());
		wapDvCanAsmMapper.deleteDvCanAsmByDvRqstNo(record.getDvRqstNo());

		return list;
	}
	
	/** 표준항목 자동분할 리스트스 insomnia */
	public List<WapDvCanAsm> getItemDivList(WapDvCanDic record) {
		//분할정보 조회
		List<WapDvCanAsm> list = wapDvCanAsmMapper.selectList(record.getDvRqstNo());
		return list;
	}
}
