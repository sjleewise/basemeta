
package kr.wise.meta.dmngrqst.service.impl;

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
import kr.wise.commons.damgmt.dmnginfo.service.WaaDmngMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.dmngrqst.service.DmngRqstService;
import kr.wise.meta.dmngrqst.service.WaqDmng;
import kr.wise.meta.dmngrqst.service.WaqDmngMapper;
import kr.wise.meta.dmngrqst.service.WaqInfoType;
import kr.wise.meta.dmngrqst.service.WaqInfoTypeMapper;



@Service("dmngRqstService")
public class DmngRqstServiceImpl implements DmngRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaqDmngMapper waqmapper;

	@Inject
	private WaaDmngMapper waaDmngMapper;
	
	@Inject
	private WaqInfoTypeMapper waqinfotypemapper;
	
//	@Inject
//	private WaeInfoTypeMapper waecolmapper; 

	
	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;
    
  
	/**  요청서 저장..  */
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
			for (WaqDmng saveVo : (ArrayList<WaqDmng>)reglist) {
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo);

				//단건 저장...
				result += saveDmngRqst(saveVo);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/**  요청서 단건 저장... @return  */
	private int saveDmngRqst(WaqDmng saveVo) {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

		if ("I".equals(tmpstatus)) {
			result = waqmapper.insertSelective(saveVo);
			//초기 등록시 WAM에 해당하는 인포타입 존재시 추가...
//			waqinfotypemapper.insertByRqstSno(saveVo);

		} else if ("U".equals(tmpstatus)){
			//업데이트
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo);
			//인포타입 요청 리스트 삭제.....
			waqinfotypemapper.deleteByrqstSno(saveVo);
		}

		return result;
	}

	/**  요청서 검증  */
	public int check(WaqMstr mstVo) {
	 
		int result = 0;

		//요청서번호 가져온다.
		String rqstNo   = mstVo.getRqstNo();
		String tblnm    = mstVo.getBizInfo().getTblNm();
		String bizdtlcd = mstVo.getBizInfo().getBizDtlCd();

		//검증 초기화
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setBizDtlCd(bizdtlcd);
		waqRqstVrfDtls.setRqstNo(rqstNo);
		//검증도메인 삭제
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		waqRqstVrfDtls.setBizDtlCd("COL");
		waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);

		//등록유형코드(C/U/D), 검증코드 업데이트
		waqmapper.updateCheckInit(rqstNo);

	
		//도메인이 삭제요청일 경우 해당 인포타입도 삭제요청으로 변경한다.
		waqinfotypemapper.updateRqstDcdbyDmng(rqstNo);

		//등록유형코드(C/U/D), 검증코드 업데이트
		waqinfotypemapper.updateCheckInit(rqstNo);
				
		//도메인그룹명 업데이트...
		waqinfotypemapper.updateDmngNmbyRqstSno(rqstNo);
	

			//검증 시작
		// 도메인그룹 검증
		// 도메인그룹 검증 파라메터 초기화....(도메인명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap = new HashMap<String, Object>();
		checkmap.put("tblnm"	, tblnm);
		checkmap.put("rqstNo"	, rqstNo);
		checkmap.put("bizDtlCd"	, bizdtlcd);

		//요청서내 중복자료 검증(DG001)
		checkmap.put("vrfDtlCd", "DG001");
		waqmapper.checkDupDmng(checkmap);

		//삭제일때 미존재 도메인 체크(DG002)
		checkmap.put("vrfDtlCd", "DG002");
		waqmapper.checkNotExistDmng(checkmap);

		//요청중인 도메인그룹 (DG004)
		checkmap.put("vrfDtlCd", "DG004");
		waqmapper.checkRequestDmng(checkmap);

		//상위도메인그룹 존재하는지 검증 (waq,waa 둘다)  DG005
		checkmap.put("vrfDtlCd", "DG005");
		waqmapper.checkExistsUppDmng(checkmap);
		
		//최하위 도메인그룹만 인포타입 등록가능하도록 DG006
		checkmap.put("vrfDtlCd", "DG006");
		waqmapper.checkDmngLvl(checkmap);
		
		
		// 인포타입 검증
		// 인포타입 검증 파라메터 초기화....(도메인명, 요청번호, 업무상세코드, 검증상세코드)
		Map<String, Object> checkmap2 = new HashMap<String, Object>();
		checkmap2.put("tblnm", "WAQ_INFO_TYPE");
		checkmap2.put("rqstNo", rqstNo);
		checkmap2.put("bizDtlCd", "COL");

		//요청서내 중복(IF001)
		checkmap2.put("vrfDtlCd", "IF001");
		waqinfotypemapper.checkDupInfoType(checkmap2);

		//삭제일때 미존재 인포타입 체크(IF002)
		checkmap2.put("vrfDtlCd", "IF002");
		waqinfotypemapper.checkNotExistInfoType(checkmap2);

		//요청중인 인포타입 (IF003) 요청중인 도메인에 의해 결정됨...
		checkmap2.put("vrfDtlCd", "IF003");
		waqinfotypemapper.checkRequestInfoType(checkmap2);
        
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(IF000)
		checkmap2.put("vrfDtlCd", "IF000");
		waqinfotypemapper.checkNotChgData(checkmap2);

		//인포타입 등록가능 여부 업데이트...
		result += waqRqstVrfDtlsMapper.updateVrfCdsNo(checkmap2);
				
		//REQ_TYP_CD이 변경일 때 변경된 데이터가 없을 경우 등록요청이 되지 않게 처리(DG000)  
		checkmap.put("vrfDtlCd", "DG000");
		waqmapper.checkNotChgData(checkmap);
		
		//인포타입 오류가 있는 경우 체크(DG999)-단 변경사항 없음은 제외한다.
		checkmap.put("vrfDtlCd", "DG999");
		waqmapper.checkInfoTypeErr(checkmap);

		//도메인 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd(checkmap);

		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);
		
		return result;
	}

	/**  요청서 등록요청  */
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**  요청서 승인 처리  */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
        logger.debug("여기여기");
		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 도메인의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqDmng savevo : (ArrayList<WaqDmng>)reglist) {
			savevo.setRqstNo(rqstNo);
			savevo.setAprvUserId(userid);

			result += waqmapper.updatervwStsCd(savevo);
		}

		//업데이트 내용이 없으면 오류 리턴한다.
		if (result <= 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}

		//2. 결재 진행 도메인을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3.최종 승인인지 아닌지 확인한다. (이건 AOP 방식으로 처리할 수 있을까?....)
//		boolean waq2wam = requestApproveService.setApproveProcess(mstVo, "WAQ_DMN");
		boolean waq2wam = requestApproveService.setApproveProcess(mstVo);

		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
			//waq2wam을 처리하자...
			logger.debug(" waq to wam and wah");

			result = 0;
			result += regWaq2Wam(mstVo);
			result += regWaq2WamInfo(mstVo);
			
			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}

		return result;
	}

	

	/** @return 
	 * @throws Exception */
	private int regWaq2WamInfo(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqInfoType> waqclist = waqinfotypemapper.selectWaqC(rqstno);

		for (WaqInfoType savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setInfotpId(id);

			waqinfotypemapper.updateidByKey(savevo);
		}

		result += waqinfotypemapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
//		result += waqinfotypemapper.updateWaqId(rqstno);

		result += waqinfotypemapper.deleteWAM(rqstno);

		result += waqinfotypemapper.insertWAM(rqstno);
		
		//도메인그룹-인포타입 매핑 입	력
		result += waqinfotypemapper.updateWaaDmngInfotpMap(rqstno);
		
		result += waqinfotypemapper.insertWaaDmngInfotpMap(rqstno);
		

//		result += waqinfotypemapper.updateWAH(rqstno);
//
//		result += waqinfotypemapper.insertWAH(rqstno);


		
		return result ;
	}

	/** @return 
	 * @throws Exception */
	private int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqDmng> waqclist = waqmapper.selectWaqC(rqstno);

		for (WaqDmng savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setDmngId(id);

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);


//		result += waqmapper.updateWaqId(rqstno);
		
        //WAA 테이블이므로 UPDATE, WAH_테이블 만들고 이력관리해도됌
		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);
		
		//UPPDMNGID 업데이트
		result += waaDmngMapper.updtUppDmngInfo(rqstno);
		//FULL_PATH 업데이트
		result += waaDmngMapper.updateFullPath(rqstno);
		
		
    
//		result += waqmapper.updateWAH(rqstno);
//
//		result += waqmapper.insertWAH(rqstno);

		return result;
	}

	/**  요청서 상세정보 조회  */
	public WaqDmng getDmngRqstDetail(WaqDmng searchVo) {
		return waqmapper.selectDmngDetail(searchVo);
	}

	/**  */
	public List<WaqDmng> getDmngRqstList(WaqMstr search) {
		return waqmapper.selectDmngListbyMst(search);
	}

	/** 
	 * @throws Exception */
	public int delDmngRqst(WaqMstr reqmst, ArrayList<WaqDmng> list) throws Exception {
		int result = 0;

		//TODO 성능이 문제가 될시에 한방 SQL로 처리한다.
//		result = waqmapper.deletedmnrqst(reqmst, list);

		for (WaqDmng savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	/** 
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDmng> list) throws Exception {
		int result = 0;

		List<WaqDmng> wamlist = waqmapper.selectwamlist(reqmst, list);

		result = register(reqmst, wamlist);

		return result;
	}

	/**  */
	public WaqInfoType getInfoTypeRqstDetail(WaqInfoType search) {

		return waqinfotypemapper.selectInfoTypeDetail(search);
	}

	/**  인포타입 리스트 저장...  */
	public int regInfoTypeList(WaqMstr reqmst, List<WaqInfoType> list) {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		String rqstno = reqmst.getRqstNo();

		for (WaqInfoType savevo : list) {
			savevo.setRqstNo(rqstno);
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);

			result += saveInfoTypeRqst(savevo);
		}

		return result;
	}

	/**  인포타입 단건 저장... @return  */
	private int saveInfoTypeRqst(WaqInfoType savevo) {
		int result = 0;
		
		String tmpStatus = savevo.getIbsStatus();
//		System.out.println("SaveVo : " + savevo.toString());

		if("I".equals(tmpStatus)) {

			result = waqinfotypemapper.insertSelective(savevo);

		} else if ("U".equals(tmpStatus)) {

			result = waqinfotypemapper.updateByPrimaryKeySelective(savevo);

		} else if ("D".equals(tmpStatus)) {

			result = waqinfotypemapper.deleteByPrimaryKey(savevo);

		}

		return result;
	}

	/**  */
	public List<WaqInfoType> getInfoTypeRqstList(WaqMstr search) {

		return waqinfotypemapper.selectInfoTypeRqstList(search);
	}

	/**  */
	public int delInfoTypeRqst(WaqMstr reqmst, ArrayList<WaqInfoType> list) {
		int result = 0;

		for (WaqInfoType delvo : list) {
				delvo.setIbsStatus("D");
		}

		result += regInfoTypeList(reqmst, list);


		return result;
	}

	/**  */
	public HashMap<String, String> regInfoTypexlsList(WaqMstr reqmst, List<WaqInfoType> list)  {
		HashMap<String, String> rtnMap = new HashMap<String, String>();
		int result = 0;

		//요청 도메인그룹 리스트를 가져온다.
		List<WaqDmng> tbllist = waqmapper.selectDmngListbyMst(reqmst);
		Map<String, WaqDmng> tblmap = new HashMap<String, WaqDmng>();


		for (WaqDmng tblvo : tbllist) {
			String fullpath = tblvo.getFullPath();
			String tblpnm = tblvo.getDmngLnm();

			String key = fullpath+"|"+tblpnm;
			tblmap.put(key, tblvo);
		}

		//도메인이 비어있을 경우 ...
		if(tblmap.isEmpty()) {
			rtnMap.put("result", "-999");
			rtnMap.put("colKey", "");
			return rtnMap;
		}

		for (WaqInfoType colvo : list) {
			String comkey = colvo.getFullPath() + "|" + colvo.getDmngLnm();
			if (tblmap.containsKey(comkey)) {
				WaqDmng tmptbl = tblmap.get(comkey);

				colvo.setRqstNo(tmptbl.getRqstNo());
				colvo.setRqstSno(tmptbl.getRqstSno());
			}
			
			else {
//				벌크 작업시 어떤 도메인이 없는지 식별하기 힘듬
//				return "-999";
				rtnMap.put("result", "-999");
				rtnMap.put("colKey", comkey);
				return rtnMap;
			}

		}

		result += regInfoTypeList(reqmst, list);
		rtnMap.put("result",  String.valueOf(result));
		rtnMap.put("colKey", "");

		return rtnMap;
	}
	
	//도메인 인포타입 일괄 저장 
//	@Override
//	public int regPdmXlsTblColList(WaqMstr reqmst, List<WaeInfoType> list) {
//				
//		int result = 0;
//				
//		result += regWaeInfoTypeList(reqmst, list);   
//		
//		reqmst.setRqstStepCd("S"); //임시저장 상태로 변경....
//		requestMstService.updateRqstPrcStep(reqmst);
//		
//		
//		return result;
//	} 

		
		 	/** WAQ에 있는 반려된 건을 재 등록한다. 이상익
	 * @throws Exception */
	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {

		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
				
		//waq의 반려내용을 waq로 다시 입력
		result = waqmapper.insertWaqRejected(reqmst, oldRqstNo);
//        result = waqinfotypemapper.insertWaqColRejected(reqmst, oldRqstNo);
		
		
		//마스터 등록
		register(reqmst, null);
        
		//검증
		check(reqmst);
		
		return result;

	}

	
	/** WAE_PDM_COL   인포타입 리스트 저장...  */
//	public int regWaeInfoTypeList(WaqMstr reqmst, List<WaeInfoType> list) {
//		int result = 0;
//
//		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
//		String userid = user.getUniqId();
//		
//		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
//		if("N".equals(reqmst.getRqstStepCd())) {
//			requestMstService.insertWaqMst(reqmst);
//		}
//
//		String rqstno = reqmst.getRqstNo();
//
//		//인포타입적재하기 전에 기존에 있던 인포타입 내용 삭제
//		result += waecolmapper.deleteByrqstNo(rqstno);
//		
//		
//		for (WaeInfoType savevo : list) {
//			savevo.setRqstNo(rqstno);
//			savevo.setFrsRqstUserId(userid);
//			savevo.setRqstUserId(userid);
//
//			result += saveWaeInfoTypeRqst(savevo);
//		}
//		
//		//===WAQ_PDM_TBL insert ==
//		WaeInfoType colVo = new WaeInfoType();
//		
//		colVo.setRqstNo(rqstno);
//		colVo.setFrsRqstUserId(userid);
//		colVo.setRqstUserId(userid);
//				
//		result += waecolmapper.insertWaqDmngForWae(colVo);
//		
//		//인포타입 insert 
//		result += waecolmapper.insertWaqInfoTypeForWae(colVo); 
//		//=======================
//		
//		return result;
//	}
	
	/**  인포타입 단건 저장... @return  */
//	private int saveWaeInfoTypeRqst(WaeInfoType savevo) {
//		int result = 0;
//		
//		String tmpStatus = savevo.getIbsStatus();
//		System.out.println("SaveVo : " + savevo.toString());
//
//		if("I".equals(tmpStatus)) {
//
//			result = waecolmapper.insertSelective(savevo);
//
//		} else if ("U".equals(tmpStatus)) {
//
//			result = waecolmapper.updateByPrimaryKeySelective(savevo);
//
//		} else if ("D".equals(tmpStatus)) {
//
//			result = waecolmapper.deleteByPrimaryKey(savevo);
//
//		}
//
//		return result;
//	}

	public boolean checkEmptyRqst(WaqMstr reqmst) {
//		return false;
		return waqmapper.checkEmptyRqst(reqmst.getRqstNo());
	}


}
