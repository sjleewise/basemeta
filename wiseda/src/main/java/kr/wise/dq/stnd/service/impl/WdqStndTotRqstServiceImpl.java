/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndTotRqstServiceImpl.java
 * 2. Package : kr.wise.dq.stnd.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 29. 오전 9:15:04
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 29. :            : 신규 개발.
 */
package kr.wise.dq.stnd.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.stnd.service.WdqStndDmnRqstService;
import kr.wise.dq.stnd.service.WdqStndItemRqstService;
import kr.wise.dq.stnd.service.WdqStndTotRqstService;
import kr.wise.dq.stnd.service.WdqStndWordRqstService;
import kr.wise.dq.stnd.service.WdqqDmn;
import kr.wise.dq.stnd.service.WdqqDmnMapper;
import kr.wise.dq.stnd.service.WdqqSditm;
import kr.wise.dq.stnd.service.WdqqSditmMapper;
import kr.wise.dq.stnd.service.WdqqStwd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndTotRqstServiceImpl.java
 * 3. Package  : kr.wise.dq.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 29. 오전 9:15:04
 * </PRE>
 */
@Service("wdqStndTotRqstService")
public class WdqStndTotRqstServiceImpl implements WdqStndTotRqstService { 
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private RequestApproveService requestApproveService;

	@Inject
	private WdqStndWordRqstService stndWordRqstService;

	@Inject
	private WdqStndDmnRqstService stndDmnRqstService;

	@Inject
	private WdqStndItemRqstService stndItemRqstService;
	
	@Inject
	private WdqqDmnMapper WdqqDmnMapper;
	
	@Inject
	private WdqqSditmMapper WdqqSditmMapper;
	

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
			logger.debug("표준데이터 전체 waq to wam and wah");

			result = 0;
			result += stndWordRqstService.regWaq2Wam(mstVo); 
			result += stndDmnRqstService.regWaq2Wam(mstVo);
			result += stndDmnRqstService.regWaq2WamCdval(mstVo);
			result += stndItemRqstService.regWaq2Wam(mstVo);

			
			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}

		return result;
	}

	
	
	public int aprvStndTot(WaqMstr mstVo, List<WdqqSditm> lstSditm, List<WdqqDmn> lstDmn, List<WdqqStwd> lstWord) throws Exception { 
		
		int result = 1; 

		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		ObjectMapper mapper = new ObjectMapper(); 
		
		List<WdqqStwd> lstCnvWord   =  mapper.convertValue(lstWord,  new TypeReference<List<WdqqStwd>>(){} ); 
			
		List<WdqqDmn> lstCnvDmn     =  mapper.convertValue(lstDmn,  new TypeReference<List<WdqqDmn>>(){} );
				
		List<WdqqSditm> lstCnvSditm =  mapper.convertValue(lstSditm,  new TypeReference<List<WdqqSditm>>(){} );		
		
		//=============검토처리여부 업데이트================
		//표준단어 승인여부 업데이트
		result = stndWordRqstService.regapprove(mstVo, lstCnvWord);    
		
		//도메인 승인여부 업데이트
		result = stndDmnRqstService.regapprove(mstVo, lstCnvDmn); 
		
		//표준용어 승인여부 업데이트
		result = stndItemRqstService.regapprove(mstVo, lstCnvSditm);
		//==============================================
		
		//==============반려시 처리=====================
		//단어반려시 도메인 반려
		result = WdqqDmnMapper.updateRejDmnByWrd(mstVo);
		
		//단어반려시 용어 반려
		result = WdqqSditmMapper.updateRejSditmByWrd(mstVo);
		
		//도메인반려시 표준용어 반려
		result = WdqqDmnMapper.updateRejItemByDmn(mstVo); 				
		//=============================================	
				
		//2. 결재 진행 테이블을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3.최종 승인인지 아닌지 확인한다. (이건 AOP 방식으로 처리할 수 있을까?....)
//				boolean waq2wam = requestApproveService.setApproveProcess(mstVo, "WAQ_DMN");
		boolean waq2wam = requestApproveService.setApproveProcess(mstVo,"");
		
		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
									
			//waq2wam을 처리하자...
			logger.debug("표준데이터 전체 waq to wam and wah");

			result = 0;
			result += stndWordRqstService.regWaq2Wam(mstVo);  
			result += stndDmnRqstService.regWaq2Wam(mstVo);
			result += stndDmnRqstService.regWaq2WamCdval(mstVo);
			result += stndItemRqstService.regWaq2Wam(mstVo);

			
			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}

		return result;
	}

	
}
