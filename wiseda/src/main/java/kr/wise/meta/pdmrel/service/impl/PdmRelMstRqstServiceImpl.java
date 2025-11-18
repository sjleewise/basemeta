/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : PdmTblRqstServiceImpl.java
 * 2. Package : kr.wise.meta.model.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 2. 오후 4:42:57
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 2. :            : 신규 개발.
 */
package kr.wise.meta.pdmrel.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.meta.ddl.service.DdlIdxRqstService;
import kr.wise.meta.ddl.service.WaqDdlIdx;
import kr.wise.meta.model.service.WaePdmCol;
import kr.wise.meta.model.service.WaePdmColMapper;
import kr.wise.meta.model.service.WaqPdmColMapper;
import kr.wise.meta.model.service.WaqPdmRel;
import kr.wise.meta.model.service.WaqPdmTbl;
import kr.wise.meta.model.service.WaqPdmTblMapper;
import kr.wise.meta.pdmrel.service.PdmRelMstRqstService;
import kr.wise.meta.pdmrel.service.WaePdmRelCol;
import kr.wise.meta.pdmrel.service.WaePdmRelColMapper;
import kr.wise.meta.pdmrel.service.WaqPdmRelCol;
import kr.wise.meta.pdmrel.service.WaqPdmRelColMapper;
import kr.wise.meta.pdmrel.service.WaqPdmRelMst;
import kr.wise.meta.pdmrel.service.WaqPdmRelMstMapper;


/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : PdmTblRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.model.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 2. 오후 4:42:57
 * </PRE>
 */
@Service("pdmRelMstRqstService")
public class PdmRelMstRqstServiceImpl implements PdmRelMstRqstService { 

	private final Logger logger = LoggerFactory.getLogger(getClass());


	@Inject
	private WaePdmRelColMapper waecolmapper;  

	@Inject
	private WaqPdmRelMstMapper waqPdmRelMstMapper; 
	
	@Inject
	private WaqPdmRelColMapper waqPdmRelColMapper; 
	
	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;
    
    
    @Inject 
    DdlIdxRqstService ddlIdxRqstService;
       
    
 
	/** @param savevo
	/** @return meta */
	private int savePdmRelMstRqst(WaqPdmRelMst savevo) {
		int result = 0;

		String tmpStatus = savevo.getIbsStatus();

		
		if("I".equals(tmpStatus)) {
			
			//========관계명,부모테이블명 rqstSno 구하기===============
			WaqPdmRelMst tmpVo = waqPdmRelMstMapper.selectExistsRqstSno(savevo);   
			
			if(tmpVo == null){
				
				tmpVo = waqPdmRelMstMapper.selectMaxRqstSno(savevo);   
			}
			//===============================================
			
			logger.debug("\n rqstSno:" + tmpVo.getRqstSno());
			
			savevo.setRqstSno(tmpVo.getRqstSno());
			
			result = waqPdmRelMstMapper.insertSelective(savevo);
			
			//초기 등록시 WAM에 해당하는 컬럼 존재시 추가...
			waqPdmRelMstMapper.insertByRqstSno(savevo);
						

		} else if ("U".equals(tmpStatus)) {

			result = waqPdmRelMstMapper.updateByPrimaryKeySelective(savevo);

		} else if ("D".equals(tmpStatus)) {

			result = waqPdmRelMstMapper.deleteByPrimaryKey(savevo);

		}

		return result; 
	}
	
	@Override
	public int register(WaqMstr reqmst, List<?> list) throws Exception {
		
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리 
		if("N".equals(reqmst.getRqstStepCd())) {
			
			reqmst.setRqstUserId(userid);
			
			requestMstService.insertWaqMst(reqmst);
		}
		
		String rqstno = reqmst.getRqstNo();
		
		
		for (WaqPdmRelMst savevo : (ArrayList<WaqPdmRelMst>)list) { 
			savevo.setRqstNo(rqstno);
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);
			
			
			result += savePdmRelMstRqst(savevo);
		}
		
		reqmst.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(reqmst); 

		return result;
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
//		waqRqstVrfDtls.setBizDtlCd("TBL");
		waqRqstVrfDtls.setBizDtlCd(bizdtlcd);
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증테이블 삭제
		//waqRqstVrfDtls.setBizDtlCd("PRELM");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		
		waqRqstVrfDtls.setBizDtlCd("PRELC");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		//등록유형코드(C/U/D), 검증코드 업데이트
		waqPdmRelMstMapper.updateCheckInit(rqstNo); 
			
		//관계 등록유형코드, 검증코드 업데이트
		waqPdmRelColMapper.updateCheckInit(rqstNo);

		//관계 컬럼에 없는것 삭제로 이서트 
		waqPdmRelColMapper.insertDelInit(rqstNo); 
					
		//관계가 삭제요청일 경우 해당 관게컬럼도 삭제요청으로 변경한다.
		waqPdmRelColMapper.updateRqstDcdbyRelMst(rqstNo);
								
		//검증 시작
		//물리모델 관계 검증
		//물리모델 관계 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);
		
		//요청서내 중복자료 검증(PR011)
		checkmap.put("vrfDtlCd", "PR011");
		waqPdmRelMstMapper.checkDupRel(checkmap); 
		
		//요청중인 관계 (PR010)
		checkmap.put("vrfDtlCd", "PR010");
		waqPdmRelMstMapper.checkRequestRel(checkmap);

		//삭제일때 미존재 인덱스 체크(PR009)
		checkmap.put("vrfDtlCd", "PR009");
		waqPdmRelMstMapper.checkNotExistRel(checkmap);

		//부모엔터티명 미존재 (PR002)
		checkmap.put("vrfDtlCd", "PR002");
		waqPdmRelMstMapper.checkPaEnty(checkmap);
		
		//자식엔터티명 미존재 (PR005)
		checkmap.put("vrfDtlCd", "PR005");
		waqPdmRelMstMapper.checkChEnty(checkmap);
		
		//물리모델 관계컬럼 검증
		//물리모델 컬럼 검증 파라메터 초기화....(테이블명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap2 = new HashMap<String, Object>();
		checkmap2.put("tblnm", "WAQ_PDM_REL_COL");
		checkmap2.put("rqstNo", rqstNo);
		checkmap2.put("bizDtlCd", "PRELC");

		//부모속성명 미존재 (PR003)
		checkmap2.put("vrfDtlCd", "PR003");
		waqPdmRelColMapper.checkPaAttr(checkmap2);
        
		//자식속성명 미존재 (PR006)
		checkmap2.put("vrfDtlCd", "PR006");
		waqPdmRelColMapper.checkChAttr(checkmap2);
		 
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(PR000)
		checkmap2.put("vrfDtlCd", "PR000");
		waqPdmRelColMapper.checkNotChgData(checkmap2);		
		
		
		//관계컬럼 등록가능 여부 업데이트...
		result += waqRqstVrfDtlsMapper.updateVrfCdDtlsNo(checkmap2);
		
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(PR000)  
		checkmap.put("vrfDtlCd", "PR000");
		waqPdmRelMstMapper.checkNotChgData(checkmap);

				
		//컬럼, 관계에 오류가 있는 경우 체크(PR999)-단 변경사항 없음은 제외한다.
		checkmap.put("vrfDtlCd", "PR999");
		waqPdmRelMstMapper.checkColErr(checkmap);

		//관계 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCdsNo(checkmap);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);
		
		return result;
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
		for (WaqPdmRelMst savevo : (ArrayList<WaqPdmRelMst>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqPdmRelMstMapper.updatervwStsCd(savevo);	 				
		}
		
		//관계 승인시 관계 컬럼 승인 
		result += waqPdmRelColMapper.updatervwStsCdForCol(rqstNo); 

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
			logger.debug("물리모델관계 waq to wam and wah");

			result = 0;
			result += regWaq2Wam(mstVo);
			result += regWaq2WamCol(mstVo);



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

		List<WaqPdmRelMst> waqclist = waqPdmRelMstMapper.selectWaqC(rqstno);

		for (WaqPdmRelMst savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId(); 
			savevo.setPdmRelId(id);

			waqPdmRelMstMapper.updateidByKey(savevo);
		}

		result += waqPdmRelMstMapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
		result += waqPdmRelMstMapper.updateWaqId(rqstno);

		result += waqPdmRelMstMapper.deleteWAM(rqstno);

		result += waqPdmRelMstMapper.insertWAM(rqstno);
  
		//result += waqPdmRelMstMapper.updateWAH(rqstno);

		//result += waqPdmRelMstMapper.insertWAH(rqstno);


		return result ;
	}
	
	private int regWaq2WamCol(WaqMstr mstVo) throws Exception {
		
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqPdmRelCol> waqclist = waqPdmRelColMapper.selectWaqC(rqstno);

		for (WaqPdmRelCol savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId(); 
			savevo.setPdmRelColId(id);

			waqPdmRelColMapper.updateidByKey(savevo);
		}

		result += waqPdmRelColMapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
		result += waqPdmRelColMapper.updateWaqId(rqstno);

		result += waqPdmRelColMapper.deleteWAM(rqstno);

		result += waqPdmRelColMapper.insertWAM(rqstno);
  
		//result += waqPdmRelColMapper.updateWAH(rqstno);

		//result += waqPdmRelColMapper.insertWAH(rqstno);


		return result ;
	}


	@Override
	public List<WaqPdmRelMst> getPdmRelRqstList(WaqMstr search) {
		
		return waqPdmRelMstMapper.selectPdmRelRqstList(search);
	}

	@Override
	public WaqPdmRelMst getPdmRelRqstDetail(WaqPdmRelMst search) {
		
		return waqPdmRelMstMapper.selectPdmRelDetail(search);
	}

	@Override
	public int delPdmRelRqst(WaqMstr reqmst, ArrayList<WaqPdmRelMst> list) {
		
		int result = 0; 
		
		for (WaqPdmRelMst savevo : list) {
			savevo.setIbsStatus("D");
			
			result += savePdmRelMstRqst(savevo);
		}

		return result;
	}

	@Override
	public int regPdmxlsRelList(WaqMstr reqmst, List<WaePdmRelCol> list) {
		int result = 0;
		
		result += regWaePdmRelList(reqmst, list);    
		
		reqmst.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(reqmst);
		
		
		return result;
	}

	private int regWaePdmRelList(WaqMstr reqmst, List<WaePdmRelCol> list) {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(reqmst.getRqstStepCd())) {
			requestMstService.insertWaqMst(reqmst);
		}

		String rqstno = reqmst.getRqstNo();
		
		//해당요청 번호 삭제 
		waecolmapper.deleteByRqstNo(reqmst);

		//시규 insert 
		for (WaePdmRelCol savevo : list) { 
			savevo.setRqstNo(rqstno);
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);

			result += saveWaePdmRelColRqst(savevo);
		}
		
		//===WAQ_PDM_REL_MST insert ==
		WaePdmRelCol colVo = new WaePdmRelCol();
		
		colVo.setRqstNo(rqstno);
		colVo.setFrsRqstUserId(userid);
		colVo.setRqstUserId(userid);
				
		result += waecolmapper.insertWaqPdmRelForWae(colVo); 
		
		//컬럼 insert 
		result += waecolmapper.insertWaqPdmRelColForWae(colVo); 
		//=======================
		
		return result;
	}

	private int saveWaePdmRelColRqst(WaePdmRelCol savevo) {
		int result = 0;
		
		String tmpStatus = savevo.getIbsStatus();
		System.out.println("SaveVo : " + savevo.toString());

		if("I".equals(tmpStatus)) {

			result = waecolmapper.insertSelective(savevo);

		} else if ("U".equals(tmpStatus)) {

			result = waecolmapper.updateByPrimaryKeySelective(savevo);

		} else if ("D".equals(tmpStatus)) {

			result = waecolmapper.deleteByPrimaryKey(savevo);

		}

		return result;
	}

	@Override
	public int regPdmRelColList(WaqMstr reqmst, List<WaqPdmRelCol> list) {
		
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		String rqstno = reqmst.getRqstNo();

		for (WaqPdmRelCol savevo : list) {
			savevo.setRqstNo(rqstno);
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);
			
			
			result += savePdmRelColRqst(savevo);
		}
		
		reqmst.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(reqmst);  

		return result;
	}
	
	/** @param savevo
	/** @return meta */
	private int savePdmRelColRqst(WaqPdmRelCol savevo) {
		int result = 0;

		String tmpStatus = savevo.getIbsStatus();

		
		if("I".equals(tmpStatus)) {
						
			result = waqPdmRelColMapper.insertSelective(savevo);

		} else if ("U".equals(tmpStatus)) {

			result = waqPdmRelColMapper.updateByPrimaryKeySelective(savevo); 

		} else if ("D".equals(tmpStatus)) {

			result = waqPdmRelColMapper.deleteByPrimaryKey(savevo);

		}

		return result; 
	}

	@Override
	public List<WaqPdmRelCol> getPdmRelColRqstList(WaqMstr search) {
		
		return waqPdmRelColMapper.selectPdmRelColRqstList(search);
	}

	@Override
	public int delPdmRelColRqst(WaqMstr reqmst, ArrayList<WaqPdmRelCol> list) {  
		
		int result = 0; 
		
		for (WaqPdmRelCol savevo : list) {
			savevo.setIbsStatus("D");
			
			result += savePdmRelColRqst(savevo);
		}

		return result;
	}

	@Override 
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqPdmRelMst> list) throws Exception {
		
		int result = 0;

		List<WaqPdmRelMst> wamlist = waqPdmRelMstMapper.selectwamlist(reqmst, list);

		result = register(reqmst, wamlist); 

		return result;
	}

}
