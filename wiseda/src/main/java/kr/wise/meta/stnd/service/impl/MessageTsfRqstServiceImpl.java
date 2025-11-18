/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : MessageTsfRqstServiceImpl.java
 * 2. Package : kr.wise.meta.stnd.service.impl
 * 3. Comment : 메시지이관 등록요청 서비스 구현체....
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 11. 30. 
 * 6. 변경이력 :

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
import kr.wise.meta.stnd.service.MessageTsfRqstService;
import kr.wise.meta.stnd.service.WamMsgTsf;
import kr.wise.meta.stnd.service.WaqMsgTsf;
import kr.wise.meta.stnd.service.WaqMsgTsfMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : MsgRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : 이상익
 * 6. 작성일   : 2015. 9. 25. 
 * </PRE>
 */
@Service("MessageTsfRqstService")

public class MessageTsfRqstServiceImpl implements MessageTsfRqstService {
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
	private EgovIdGnrService requestIdGnrService;

    
    @Inject
    private WaqMsgTsfMapper waqMsgTsfMapper;
    
	
	//메시지 이관요청용 리스트 조회(팝업)
    @Override
    public List<WaqMsgTsf> selectMsgListTsf(WaqMsgTsf searchVo) {

	    return waqMsgTsfMapper.selectMsgListTsf(searchVo);
    }
	


    //이관등록
	public int regMessageTsf(WaqMstr mstVo, ArrayList<WaqMsgTsf> reglist) throws Exception {

		logger.debug("mstVo:{}\nbizInfo:{}", mstVo, mstVo.getBizInfo());

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}
		String rqstNo = mstVo.getRqstNo();
        String rqstDcd = mstVo.getRqstDcd();
        String tgtDbConnTrgId = mstVo.getTgtDbConnTrgId();
        String tgtDbConnTrgPnm = mstVo.getTgtDbConnTrgPnm();
        String tgtDbSchId = mstVo.getTgtDbSchId();
        String tgtDbSchPnm = mstVo.getTgtDbSchPnm();
		int result = 0;
		
		      for(WaqMsgTsf saveVo : reglist){
		      	   //요청번호 셋팅
		      		saveVo.setFrsRqstUserId(userid);
		      		saveVo.setRqstUserId(userid);
		      		saveVo.setRqstNo(rqstNo);
                    saveVo.setRqstDcd(rqstDcd);
                      //타겟db정보 일괄세팅
                    if(!rqstDcd.equals("DD")){
                       saveVo.setTgtDbConnTrgPnm(tgtDbConnTrgPnm);
                       saveVo.setTgtDbConnTrgId(tgtDbConnTrgId);
                       saveVo.setTgtDbSchId(tgtDbSchId);
                       saveVo.setTgtDbSchPnm(tgtDbSchPnm);
                    }
		      		//단건 저장...
		      	    result += saveRqstMsgTsf(saveVo);
		      }
		      
		      
		
		   
		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);
		
		return result;
	}
	
	int saveRqstMsgTsf(WaqMsgTsf saveVo){
	
		int result =0;

		result += waqMsgTsfMapper.insertSelective(saveVo);
		
		
		return result;
	}
	
	@Override
	public List<WaqMsgTsf> selectMsgTsfRqstList(WaqMstr reqmst) {
		// TODO Auto-generated method stub
		return waqMsgTsfMapper.selectRqstList(reqmst);
		
	}
	
	@Override
	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}




	@Override
	public int check(WaqMstr mstVo) throws Exception {
		int result = 0;

		//요청서번호 가져온다.
		String rqstNo = mstVo.getRqstNo();

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setBizDtlCd("MST");
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

	    //등록유형코드(C/U/D), 검증코드 업데이트
		waqMsgTsfMapper.updateCheckInit(rqstNo);
		//타겟DB정보가 NULL일 경우 업데이트
		waqMsgTsfMapper.updateTgtDb(rqstNo);
		
		
		//검증 시작
		//코드이관 검증
		//검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm", "WAQ_MSG_TSF");
		checkmap.put("rqstNo", rqstNo);
		checkmap.put("bizDtlCd", "MST");


		//요청서내 중복자료 검증(MT001)
		checkmap.put("vrfDtlCd", "MT001");
		waqMsgTsfMapper.checkDupMsgTsf(checkmap);

		//등록요청된 타요청서에 중복된자료 있는지 검사
		checkmap.put("vrfDtlCd", "MT002");
		waqMsgTsfMapper.checkDupMsgTsfOtherRqst(checkmap);

		//타겟DB정보 존재유무
		checkmap.put("vrfDtlCd", "MT003");
		waqMsgTsfMapper.checkTgtDb(checkmap);
		
		        
		//코드이관 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(CT000)
		checkmap.put("vrfDtlCd", "MT000");
		waqMsgTsfMapper.checkNotChgData(checkmap);
		
		
		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		result += requestMstService.updateRequestMsterNm(mstVo);
		//requestMstService.updateRequestMsterNm(mstVo); 임시처리
		
		return result;
	}


   @Override
   public int delCodeTsfRqstList(WaqMstr mstVo, ArrayList<WaqMsgTsf> list) throws Exception{
	    int result =0;
	    String rqstNo = mstVo.getRqstNo();
	    
	    for(WaqMsgTsf saveVo : list){
	    	
	    	saveVo.setRqstNo(rqstNo);
	        result += waqMsgTsfMapper.deleteRqstList(saveVo);
	        
	    }
	    return result;
   
   
   }

	@Override
	public int submit(WaqMstr mstVo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}



//승인
	@Override
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {

		int result = 0;

		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqMsgTsf savevo : (ArrayList<WaqMsgTsf>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqMsgTsfMapper.updatervwStsCd(savevo);
		}

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result <= 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}

		//2. 결재 진행 테이블을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3.최종 승인인지 아닌지 확인한다. 
//		boolean waq2wam = requestApproveService.setApproveProcess(mstVo, "WAQ_DMN");
		boolean waq2wam = requestApproveService.setApproveProcess(mstVo);

		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
			//waq2wam을 처리하자...
			logger.debug("메시지 등록요청 waq to wam and wah");

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
	/** @return lsi
	 * @throws Exception */
	public int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		//신규 대상인 경우 ID를 채번한다.
		//List<WaqCdValTsf> waqclist = waqMsgMapper.selectWaqC(rqstno);
		//for (WaqCdValTsf savevo : waqclist) {
		//	String id =  objectIdGnrService.getNextStringId();
		//	savevo.setMId(id);

		//	waqMsgMapper.updateidByKey(savevo);
		//}

		result += waqMsgTsfMapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
		//result += waqMsgMapper.updateWaqId(rqstno);



      result += waqMsgTsfMapper.deleteWAM(rqstno);

	  result += waqMsgTsfMapper.insertWAM(rqstno);

	  result += waqMsgTsfMapper.updateWAH(rqstno);

	  result += waqMsgTsfMapper.insertWAH(rqstno);

     

		return result;
	
	}



//상세정보조회
@Override
public WaqMsgTsf getMsgTsfRqstDetail(WaqMsgTsf searchVo) {
	// TODO Auto-generated method stub
	return waqMsgTsfMapper.selectRqstDetail(searchVo);
}



@Override
public List<WaqMsgTsf> selectMsgWamList(WaqMsgTsf searchVo) {
        
	return waqMsgTsfMapper.selectMsgWamList(searchVo);
}

	   	/** WAQ에 있는 반려된 건을 재 등록한다. 이상익
	 * @throws Exception */
	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {

		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
				
		//waq의 반려내용을 waq로 다시 입력
		
		result = waqMsgTsfMapper.insertWaqRejected(reqmst, oldRqstNo);
		
		//마스터 등록
        
		register(reqmst, null);
        
		//검증
        
		check(reqmst);
                
		

		return result;

	}
		
}
