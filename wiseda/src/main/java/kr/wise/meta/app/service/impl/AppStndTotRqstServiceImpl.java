/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AppStndTotRqstServiceImpl.java
 * 2. Package : kr.wise.meta.app.service.impl
 * 3. Comment :
 * 4. 작성자  : mse
 * 5. 작성일   : 2016. 3. 16. 오전 10:55:20
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    mse : 2016. 3. 16. :            : 신규 개발.
 */
package kr.wise.meta.app.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.app.service.AppStndItemRqstService;
import kr.wise.meta.app.service.AppStndTotRqstService;
import kr.wise.meta.app.service.AppStndWordRqstServie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AppStndTotRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.app.service.impl
 * 4. Comment  :
 * 5. 작성자   : mse
 * 6. 작성일   : 2016. 3. 16. 오전 10:55:20
 * </PRE>
 */
@Service("appstndTotRqstService")
public class AppStndTotRqstServiceImpl implements AppStndTotRqstService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private RequestApproveService requestApproveService;

	@Inject
	private AppStndWordRqstServie appStndWordRqstServie;

	@Inject
	private AppStndItemRqstService appStndItemRqstService;


	/** insomnia */
	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/** insomnia */
	public int check(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/** insomnia */
	public int submit(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/** insomnia */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 1;

		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//2. 결재 진행 테이블을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3.최종 승인인지 아닌지 확인한다. (이건 AOP 방식으로 처리할 수 있을까?....)
//				boolean waq2wam = requestApproveService.setApproveProcess(mstVo, "WAQ_DMN");
		boolean waq2wam = requestApproveService.setApproveProcess(mstVo);

		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
			//waq2wam을 처리하자...
			logger.debug("APP데이터 전체 waq to wam and wah");

			result = 0;
			result += appStndWordRqstServie.regWaq2Wam(mstVo);
			result += appStndItemRqstService.regWaq2Wam(mstVo);

			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}

		return result;
	}

}
