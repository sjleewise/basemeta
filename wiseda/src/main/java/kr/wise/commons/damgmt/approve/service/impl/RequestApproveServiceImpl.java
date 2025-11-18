/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : RequestApproveServieImpl.java
 * 2. Package : kr.wise.commons.damgmt.approve.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 28. 오전 11:17:19
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 28. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.approve.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.damgmt.approve.service.WaaAprPrc;
import kr.wise.commons.damgmt.approve.service.WaaAprPrcMapper;
import kr.wise.commons.damgmt.approve.service.WaaRqstAprp;
import kr.wise.commons.damgmt.approve.service.WaaRqstAprpMapper;
import kr.wise.commons.damgmt.approve.service.WaaRqstBizApr;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : RequestApproveServieImpl.java
 * 3. Package  : kr.wise.commons.damgmt.approve.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 28. 오전 11:17:19
 * </PRE>
 */
@Service("requestApproveService")
public class RequestApproveServiceImpl implements RequestApproveService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaaRqstAprpMapper rqstaprpmapper;

	@Inject
	private WaaAprPrcMapper aprprcmapper;

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private CmcdCodeService cmcdCodeService;

	@Inject
	private ApproveLineServie approveLineServie;

	/** insomnia */
	public List<WaaRqstAprp> getRequestApproveList(WaaRqstAprp search) {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//관리자가 아닐경우 로그인 사용자가 결재자인 경우만 조회한다...
		if (!"Y".equals(user.getIsAdminYn())) {
			search.setUserId(userid);
		}

		return rqstaprpmapper.selectRqstApproveList(search);
	}

	/** insomnia */
	public int regRequestApproveList(ArrayList<WaaRqstAprp> list) {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		String userid = user.getUniqId();

		for (WaaRqstAprp savevo : list) {

			savevo.setWritUserId(userid);

			result += saveRequestApprove(savevo);
		}


		return result;
	}

	/** @param savevo
	/** @return insomnia */
	public int saveRequestApprove(WaaRqstAprp savevo) {
		int result = 0;

		String tmpstatus = savevo.getIbsStatus();

		if("I".equals(tmpstatus)) {

//			result = mapper.insertRqstApprove(savevo);

		} else if ("U".equals(tmpstatus)) {

			result = rqstaprpmapper.updateRqstApprove(savevo);

		} else if ("D".equals(tmpstatus)) {

//			result = mapper.deleteRqstApprove(savevo);
		}

		return result;
	}


	/** 결재라인 변경시 요청서별 결재자도 삭제, 업데이터, 추가 처리한다. insomnia */
	public void updateAllbyLine() {
		rqstaprpmapper.deleteRqstApprove(null);
		rqstaprpmapper.updateRqstApprovebyLine(null);
		rqstaprpmapper.insertRqstApprovebyLine(null);

	}

	/** 등록요청시 요청서별 결재라인 조회... insomnia */
	public List<WaaAprPrc> getapproveline(WaqMstr search) {
		return aprprcmapper.selectapproveline(search);
	}

	/**등록요청시 요청서의 결재라인별 결재자 리스트를 가져온다. 그룹결재일 경우 결재그룹으로 가져온다. insomnia */
	public ArrayList<WaaRqstAprp> getapproveuserbyline(WaqMstr reqmst) {
		return rqstaprpmapper.selectapprovebyline(reqmst);
	}

	/** 등록요청 처리 - 결재진행 테이블에 결재자 등록 및 후처리(메일/메신저 연동 등???) insomnia */
	public int submit(WaqMstr reqmst, ArrayList<WaaAprPrc> list) {

		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		String userid = user.getUniqId();
		String reqid = reqmst.getRqstNo();

		for (WaaAprPrc savevo : list) {
			savevo.setRqstNo(reqid);
			savevo.setWritUserId(userid);
			savevo.setIbsStatus("I");

			result += saveApproveProcess(savevo);
		}

		//결재진행 저장 성공시 요청 마스터 상태와 rqst_nm 업데이트....
		if(result > 0) {
			reqmst.setRqstStepCd("Q"); //등록요청 상태로 업데이트
			requestMstService.updateRqstPrcStep(reqmst);
			requestMstService.updateRequestInfor(reqmst);

			requestMstService.updateRequestMsterNm(reqmst);

			//TODO 요청서명 업데이트 (BIZ_DTL_CD 값 필요)
//			String bizdtlnm = cmcdCodeService.getDetailCodeNm("BIZ_DTL_CD", "STWD");
			//요청명 업데이트 (표준단어 aaaa 외 3건 , BIZ_DTL_CD 값에 따른  waq테이블, 논리명(물리명)-컬럼명을 가져와야함...)
//			requestMstService.updateWaqMstrqstNm(reqid, "WAQ_STWD", "STWD_LNM",  bizdtlnm);

		} else {
			logger.debug("결재진행 내용 저장한 건수가 없습니다.\n요청서:{}\n결재자리스트:{}", reqmst, list);
			throw new WiseBizException("ERR.APPROVE", "결재진행 내용 저장한 건수가 없습니다.");
		}

		return result;
	}

	/** @return insomnia */
	public int saveApproveProcess(WaaAprPrc savevo) {
		int result = 0;
		String tmpstatus = savevo.getIbsStatus();

		if("I".equals(tmpstatus)) {

			result = aprprcmapper.insertapproveprocess(savevo);

		} else if ("U".equals(tmpstatus)) {
			//TODO 업데이트 처리

		} else if ("D".equals(tmpstatus)) {
			//TODO 삭제 처리

		}

		return result;
	}

	/** 결재진행에서 최근 레벨의 결재 업데이트 처리 insomnia */
	public WaaAprPrc updateApproveProcessLine(WaqMstr mstVo) {
		int result = 0;

		WaaAprPrc lastvo =null ;

		//현재 로그인 사용자를 결재자로 업데이트한다.
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		String rvwsts = mstVo.getRvwStsCd();

		mstVo.setAprvUserId(userid);


		//결재진행에 미결재진행 리스트를 가져온다...
		List<WaaAprPrc> aprlist = aprprcmapper.getapproveprocess(mstVo);

		int cnt = 0;
		for (WaaAprPrc aprvo : aprlist) {
			cnt++;
			if (cnt == 1) {
				//첫번째 결재라인은 무조건 업데이트 한다. (결재그룹이든 개별결재든 상관없이 결재한다..)
//				result = aprprcmapper.updateapproveprocessline(mstVo);
				aprvo.setAprvUserId(userid);
				aprvo.setRvwStsCd(rvwsts);

				result += aprprcmapper.updateApproveProcess(aprvo);

				lastvo = aprvo;

			} else{
				//다음 결재자가 나와 동일한 경우 결재한다.
				//TODO 대체 결재자일 경우 일자를 따져 결재가능한지 확인한다....
				String abduser = checkAbdDarp(aprvo);
				if("D".equals(aprvo.getAprFrmlCd()) &&
						( userid.equals(aprvo.getUserId()) || userid.equals(abduser) ) ) {
//					result = aprprcmapper.updateapproveprocessline(mstVo);
					aprvo.setAprvUserId(userid);
					aprvo.setRvwStsCd(rvwsts);

					result += aprprcmapper.updateApproveProcess(aprvo);

					lastvo = aprvo;

				} else {
					//다음 결재자가 본인이 아니거나 그룹 결재인 경우 종료한다....
					break;
				}
			}

			if ("A".equals(aprvo.getAbdDaprDcd())) {
				//결재자가 전결일 경우는 종료한다.
				break;
			}

		}


		return lastvo;
	}

	/** 대체 결재자일 경우 일자를 따져 결재가능한지 확인한다.... @param aprvo insomnia */
	private String checkAbdDarp(WaaAprPrc aprvo) {
		String result = null;
		if ("D".equals(aprvo.getAbdDaprDcd())){
			//대체결재자가 셋팅되어 있을 경우
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String strdate = format.format(now); // 20090529
			//오늘날짜가 시작날짜보다 크거나 같고, 종료날짜보다 작거나 같을 경우만 대체결재자 결재가능....
			logger.debug("대체결재자 확인, 결재라인:{} \n오늘날짜:{}", aprvo, strdate);
			if (strdate.compareTo(aprvo.getDaprStrDt()) >= 0 && strdate.compareTo(aprvo.getDaprEndDt()) <= 0) {
				result = aprvo.getSbsAprpId();
			}
		}
		logger.debug("대체결재자:{}", result);
		return result;
	}

	/** 결재 진행에 결자라인이 남아있는 지 확인한다. insomnia */
	public int getCountApprovePorcess(WaqMstr mstVo) {

		return aprprcmapper.getcountapproveprocess(mstVo);
	}

	/** 결재 진행을 처리하는 전체 공통 서비스 insomnia */
	public boolean setApproveProcess(WaqMstr mstVo, String tblnm) {

		boolean waq2wam = false;
		String rqstNo = mstVo.getRqstNo();

		int result = 0;

		String rvwStatus = null;
		//2. 결재 진행 테이블을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//요청서 내용이 전체가 반려인지 확인해야함 (승이:"1", 반려:"2")
		if("DIC".equals(mstVo.getBizDcd())) {
			rvwStatus = requestMstService.getrvwStatus(rqstNo, "STND");
		}else if("ADC".equals(mstVo.getBizDcd())) {
				rvwStatus = requestMstService.getrvwStatus(rqstNo, "ADC");
		} else {
			rvwStatus = requestMstService.getrvwStatus(rqstNo, tblnm);
		}

		mstVo.setRvwStsCd(rvwStatus); //승인 ("1") ("2"); //반려 ("3")부분반려
		
	     WaaRqstBizApr aprSearch = new WaaRqstBizApr();
	     aprSearch.setBizDcd(mstVo.getBizDcd());
	     
	     List<WaaRqstBizApr> aprlist = approveLineServie.getapprovelinelist(aprSearch); // 결재선검색
	     Boolean check = false;

	     if(aprlist.size() > 0){
	      check = true;
	     }
	     
	     WaaAprPrc lastvo = new WaaAprPrc();
		 if(check){ 
			lastvo = updateApproveProcessLine(mstVo);

			//업데이트 내용이 없으면 오류 리턴한다.
			if (lastvo == null ) {
				logger.debug("결재 승인 실패 : 결재 진행 업데이트 실패함. 요청서마스터:{}", mstVo );
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 결재 진행 업데이트 실패함");
			}
		 }

		//3.최종 승인인지 아닌지 확인한다. (이건 AOP 방식으로 처리할 수 있을까?....)
		//결재가 승인이면...결재라인 남아 있는지 확인한다.
		if ("1".equals(rvwStatus)||"3".equals(rvwStatus)) {
			//결재라인이 남아있으면 남은 갯수를 리턴한다.
			result = 0;
			
			if(check){ 
				result = getCountApprovePorcess(mstVo);
			}

			if(result > 0) {
				//3.1. 마지막 결재가 전결 상태이면 최종결재로 처리한다....
				if("A".equals(lastvo.getAbdDaprDcd()) || !check) {
					waq2wam = true;
				} else {
					waq2wam = false;
				}

			} else {
				waq2wam = true;
			}
		} else {
			//전체 반려이면 waq2wam을 처리할 필요가 없음...
			waq2wam = false;
		}


		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
//		if(waq2wam || "2".equals(rvwStatus)||"3".equals(rvwStatus)) {
		if(waq2wam || "2".equals(rvwStatus)) {					// 부분결재가 윗단계로 안올라가는 경우 발생.
			result = 0;
			//요청 마스터를 업데이트 한다.
			mstVo.setRqstStepCd("A");
			
			//승인한사람이 무조건 RQST_USER_ID가 되는 문제 발생...
//			result = requestMstService.updateRqstPrcStep(mstVo);
			result = requestMstService.updateApproveInfo(mstVo);

			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : 요청마스터 최종승인자 및 상태(결재처리-A) 업데이트 실패. 요청서마스터:{}", mstVo );
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청마스터 최종승인자 및 상태(결재처리-A) 업데이트 실패");
			}

		}

		return waq2wam;
	}
	
	/** 결재 진행을 처리하는 전체 공통 서비스 insomnia */
	public boolean setApproveProcessWdq(WaqMstr mstVo) {
		boolean waq2wam = true;
		int result = 0;
		
		//요청 마스터를 업데이트 한다.
		mstVo.setRqstStepCd("A");
		
		//승인한사람이 무조건 RQST_USER_ID가 되는 문제 발생...
//			result = requestMstService.updateRqstPrcStep(mstVo);
		result = requestMstService.updateApproveInfo(mstVo);

		if (result <= 0 ) {
			logger.debug("저장 실패 : 요청마스터 최종승인자 및 상태(결재처리-A) 업데이트 실패. 요청서마스터:{}", mstVo );
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청마스터 최종승인자 및 상태(결재처리-A) 업데이트 실패");
		}

		return waq2wam;
	}	

	/** 각 업무별 요청서의 ID채번 : 대상은 검증결과 등록가능("1")이고, 등록타입이 신규인 경우("C") insomnia */
	public int setWaqObjectID(WaqMstr reqmst) {

		return 0;
	}

	/** 결재 진행을 처리하는 전체 공통 서비스 insomnia */
	public boolean setApproveProcess(WaqMstr mstVo) {

		logger.debug("mstVo:{}", mstVo);
		
		String tblNm = UtilString.null2Blank(mstVo.getBizInfo().getTblNm());
		
		logger.debug("\n tblNm:", tblNm);

		return setApproveProcess(mstVo, tblNm);

	}

}
