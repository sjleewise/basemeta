/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ApproveLineServieImpl.java
 * 2. Package : kr.wise.commons.damgmt.approve.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 3. 27. 오후 2:35:50
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 3. 27. :            : 신규 개발.
 */
package kr.wise.commons.damgmt.approve.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.approve.service.ApproveLineServie;
import kr.wise.commons.damgmt.approve.service.MstrAprPrcVO;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.damgmt.approve.service.WaaAprPrc;
import kr.wise.commons.damgmt.approve.service.WaaAprPrcMapper;
import kr.wise.commons.damgmt.approve.service.WaaRqstAprpMapper;
import kr.wise.commons.damgmt.approve.service.WaaRqstBizApr;
import kr.wise.commons.damgmt.approve.service.WaaRqstBizAprMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : ApproveLineServieImpl.java
 * 3. Package  : kr.wise.commons.damgmt.approve.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 27. 오후 2:35:50
 * </PRE>
 */
@Service("approveLineServie")
public class ApproveLineServieImpl implements ApproveLineServie {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaaRqstBizAprMapper mapper;

	@Inject
	private WaaRqstAprpMapper rqstmapper;

	@Inject
	private WaaAprPrcMapper aprprcmapper;

	@Inject
	private RequestApproveService requestApproveService;

	@Inject
	private EgovIdGnrService objectIdGnrService;


	/** 요청서별 결재라인 리스트 조회 insomnia */
	public List<WaaRqstBizApr> getapprovelinelist(WaaRqstBizApr search) {

		return mapper.selectAprvLineList(search);
	}



	/** 요청서별 결재라인 리스트 저장 - IBSheet JSON insomnia
	 * @throws Exception */
	public int regaprvlinelise(ArrayList<WaaRqstBizApr> list) throws Exception {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();

		String userid = user.getUniqId();

		for (WaaRqstBizApr savevo : list) {

			savevo.setWritUserId(userid);

			result += saveApproveLine(savevo);

		}

		//결재라인 추가 및 수정시 요청서별 결재자 일괄 삭제, 업데이트, 추가 처리...
		requestApproveService.updateAllbyLine();

		return result;
	}



	/** 요청서별 결재라인 저장 - @return insomnia
	 * @throws Exception */
	public int saveApproveLine(WaaRqstBizApr savevo) throws Exception {
		int result = 0;

		String tmpstatus = savevo.getIbsStatus();

		if("I".equals(tmpstatus)) { //신규저장
			//String objid = objectIdGnrService.getNextStringId();
			savevo.setRegTypCd("C");
			savevo.setObjVers(1);
			result += mapper.insertApproveLine(savevo);

		} else if ("U".equals(tmpstatus)) { //변경 업데이트
			savevo.setRegTypCd("U");
			savevo.setObjVers(savevo.getObjVers()+1);

			result += mapper.updateApproveLine(savevo);
			result += mapper.updateApproveLineAs(savevo); // 레벨변경시 연관된 요청서결재자 레벨도 같이 변경함.
			
			
		} else if ("D".equals(tmpstatus)) { //삭제 업데이트
			savevo.setRegTypCd("D");
			savevo.setObjVers(savevo.getObjVers()+1);

			result += mapper.deleteApproveLine(savevo);

		}

		return result;
	}



	/** 요청서별 결재자 리스트 삭제 - IBSheet JSON insomnia
	 * @throws Exception */
	public int delaprvlinelist(ArrayList<WaaRqstBizApr> list) throws Exception {
		int result = 0;

		for (WaaRqstBizApr delvo : list) {
			delvo.setIbsStatus("D");

			result += saveApproveLine(delvo);
		}

		//결재라인 삭제시 해당 요청서별 결재자 테이블에서 일괄 삭제처리...
		rqstmapper.deleteRqstApprove(null);

		return result;
	}



	/** insomnia */
	public List<WaaAprPrc> getapproveprocess(WaqMstr search) {
		return aprprcmapper.selectapproveprocess(search);
	}



	/** insomnia */
	public Boolean checkapproveuser(WaqMstr reqmst) {
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		boolean result = false;

		//결재진행에 미결재진행 리스트를 가져온다...
		List<WaaAprPrc> aprlist = aprprcmapper.selectcheckapproveuser(reqmst.getRqstNo(), userid);

		for (WaaAprPrc aprvo : aprlist) {

			//그룹결재일 경우 현재 로그인 ID와 결재그룹에 포함된 USER_ID와 비교한다...
			if("G".equals(aprvo.getAprFrmlCd()) && userid.equals(aprvo.getGroupUserId()) ) {
				result = true;
				break;
			}

			//개별결재일 경우 결재자 또는 대체결재자와 비교한다...
			String abduser = checkAbdDarp(aprvo);
			if("D".equals(aprvo.getAprFrmlCd()) &&
					( userid.equals(aprvo.getUserId()) || userid.equals(abduser) ) ) {
				result = true;
				break;
			}

			//관리자일 경우는 무조건 결재가능하도록 변경????
//			if ("Y".equals(user.getIsAdminYn())) {
//				result = true;
//			}
			break;
		}

		return result;
	}

	/** 대체 결재자일 경우 일자를 따져 결재가능한지 확인한다.... @param aprvo insomnia */
	private String checkAbdDarp(WaaAprPrc aprvo) {
		String result = null;
		if ("D".equals(aprvo.getAbdDaprDcd()) && StringUtils.hasText(aprvo.getDaprStrDt()) && StringUtils.hasText(aprvo.getDaprEndDt())){
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



	@Override
	public int checkRequst(MstrAprPrcVO mapvo) {
		int mapcount = rqstmapper.checkRequst(mapvo);
		return mapcount;
	}



	@Override
	public int requestCancel(String rqst_no) {
		
		int result = -1;
		
		result = rqstmapper.updateRequestCancel(rqst_no);
		
		rqstmapper.deleteRqstmapper(rqst_no);
		
		return result;
		
	}

}
