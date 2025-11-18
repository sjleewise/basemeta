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
package kr.wise.meta.model.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.tblrel.service.WaqPrfRelColVO;
import kr.wise.meta.model.service.DasPdmTblRqstService;
import kr.wise.meta.model.service.WamPdmTbl;
import kr.wise.meta.model.service.WamPdmTblMapper;
import kr.wise.meta.model.service.WaqPdmCol;
import kr.wise.meta.model.service.WaqPdmColMapper;
import kr.wise.meta.model.service.WaqPdmTbl;
import kr.wise.meta.model.service.WaqPdmTblMapper;
import kr.wise.sysinf.daSharp.service.DasMartMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.genesis.mmart.action.ERwin;

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
@Service("daspdmTblRqstService")
public class DasPdmTblRqstServiceImpl implements DasPdmTblRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;

	@Inject
	private WaqPdmTblMapper waqmapper;

	@Inject
	private WaqPdmColMapper waqcolmapper;
	
	@Inject
	private WamPdmTblMapper wamPdmTblmapper; 

	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;
    
    @Inject
    private DasMartMapper dasMartMapper;

	/** 물리모델 요청서 저장.. insomnia */
	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {
		logger.debug("mstVo:{}\nbizInfo:{}", mstVo, mstVo.getBizInfo());

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if("N".equals(mstVo.getRqstStepCd())) { 
			requestMstService.insertWaqMst(mstVo);
		}

		String rqstNo = mstVo.getRqstNo();
		int rqstSno = 1;
		int result = 0;
		if(reglist != null) {
			for (WaqPdmTbl saveVo : (ArrayList<WaqPdmTbl>)reglist) { //<== 여기서 스탑. 따로넣어줘야하나..
				
				//요청번호 셋팅
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setRqstNo(rqstNo); 				
				
//				saveVo.setPdmTblId(saveVo.getEntity_id());
//				saveVo.setPdmTblPnm(saveVo.getEntity_name_physical());
//				saveVo.setPdmTblLnm(saveVo.getEntity_name_logical());
//				saveVo.setSubjId(saveVo.getSubjectArea_id());
//				saveVo.setSubjLnm(saveVo.getSubjectarea_name());
				
				saveVo.setRqstSno(rqstSno);
				rqstSno++;
				
//				String dasDist = message.getMessage("das_distributor", null, Locale.getDefault());
//		    	
//		    	logger.debug("\n dasDist:" + dasDist); 
				
				//단건 저장...
				result += savePdmTblRqst(saveVo);	
					
				//savePdmColRqst(saveVo);
				
				logger.debug("tmpstatus:" +rqstSno);
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);

		return result;
	}

	/** 물리모델 요청서 단건 저장... @return insomnia */
	private int savePdmTblRqst(WaqPdmTbl saveVo) throws Exception{
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();

		logger.debug("tmpstatus:{} \n WaqPdmTbl:{}", tmpstatus, saveVo);
		String rqstNo = saveVo.getRqstNo();
		int rqstSno = saveVo.getRqstSno();

		if ("I".equals(tmpstatus)) {
			//신규 등록 : 나중에 적재를 위해 미리 오브젝트 ID를 셋팅한다...
			saveVo.setSubjId(saveVo.getSubjId());
			result = waqmapper.insertSelective(saveVo);
			
			List<WaqPdmCol> rsCollist = dasMartMapper.selectDasColListbyTblId(saveVo);
			
			regPdmColList(rsCollist);
		} else if ("U".equals(tmpstatus)){
			//업데이트
			result = waqmapper.updateByPrimaryKeySelective(saveVo);
		} else if ("D".equals(tmpstatus)) {
			//요청내용 삭제...
			result = waqmapper.deleteByrqstSno(saveVo);
			//컬럼 요청 리스트 삭제.....
			waqcolmapper.deleteByrqstSno(saveVo);
		}

		return result;
	}
	

	/** 물리모델 요청서 검증 insomnia */
	public int check(WaqMstr mstVo) {
		int result = 0;

		

		return result;
	}

	/** 물리모델 요청서 등록요청 insomnia */
	public int submit(WaqMstr mstVo) { 
		// TODO Auto-generated method stub
		return 0;
	}

	/** 물리모델 요청서 승인 처리 insomnia */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}", userid, rqstNo);

		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		for (WaqPdmTbl savevo : (ArrayList<WaqPdmTbl>)reglist) {
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
			logger.debug("물리모델 waq to wam and wah");

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

	/** @return insomnia
	 * @throws Exception */
	private int regWaq2WamCol(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqPdmCol> waqclist = waqcolmapper.selectWaqC(rqstno);

		for (WaqPdmCol savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setPdmColId(id);

			waqcolmapper.updateidByKey(savevo);
		}

		result += waqcolmapper.updateWaqCUD(rqstno);
		//각종 ID 업데이트 하도록 한다.
		result += waqcolmapper.updateWaqId(rqstno);

		result += waqcolmapper.deleteWAM(rqstno);

		result += waqcolmapper.insertWAM(rqstno);

		result += waqcolmapper.updateWAH(rqstno);

		result += waqcolmapper.insertWAH(rqstno);


		return result ;
	}

	/** @return insomnia
	 * @throws Exception */
	private int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();

		List<WaqPdmTbl> waqclist = waqmapper.selectWaqC(rqstno);

		for (WaqPdmTbl savevo : waqclist) {
			String id =  objectIdGnrService.getNextStringId();
			savevo.setPdmTblId(id);

			waqmapper.updateidByKey(savevo);
		}

		result += waqmapper.updateWaqCUD(rqstno);

		//각종 ID 업데이트 하도록 한다.
		result += waqmapper.updateWaqId(rqstno);

		result += waqmapper.deleteWAM(rqstno);

		result += waqmapper.insertWAM(rqstno);

		result += waqmapper.updateWAH(rqstno);

		result += waqmapper.insertWAH(rqstno);

		return result;
	}

	/** 물리모델 요청서 상세정보 조회 insomnia */
	public WaqPdmTbl getPdmTblRqstDetail(WaqPdmTbl searchVo) {
		return waqmapper.selectPdmTblDetail(searchVo);
	}

	/** insomnia */
	public List<WaqPdmTbl> getPdmTblRqstList(WaqMstr search) {
		return waqmapper.selectPdmTblListbyMst(search);
	}

	/** insomnia
	 * @throws Exception */
	public int delPdmTblRqst(WaqMstr reqmst, ArrayList<WaqPdmTbl> list) throws Exception {
		int result = 0;

		//TODO 성능이 문제가 될시에 한방 SQL로 처리한다.
//		result = waqmapper.deletedmnrqst(reqmst, list);

		for (WaqPdmTbl savevo : list) {
			savevo.setIbsStatus("D");
		}

		result = register(reqmst, list);

		return result;
	}

	/** insomnia
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqPdmTbl> list) throws Exception {
		int result = 0;

		List<WaqPdmTbl> wamlist = waqmapper.selectwamlist(reqmst, list);

		result = register(reqmst, wamlist);

		return result;
	}

	/** insomnia */
	public WaqPdmCol getPdmColRqstDetail(WaqPdmCol search) {

		return waqcolmapper.selectPdmColDetail(search);
	}

	/** 물리모델 컬럼 리스트 저장... insomnia */
	public int regPdmColList(WaqMstr reqmst, List<WaqPdmCol> list) {
		int result = 0;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		String rqstno = reqmst.getRqstNo();

		for (WaqPdmCol savevo : list) {
			savevo.setRqstNo(rqstno);
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);
//			convertDisplay2Datatype(savevo);
			result += savePdmColRqst(savevo);
		}

		return result;
	}
	/** 물리모델 컬럼 리스트 저장... insomnia */
	public int regPdmColList(List<WaqPdmCol> list) {
		int result = 0;
//		int i = 1;
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

//		String rqstno = reqmst.getRqstNo();

		for (WaqPdmCol savevo : list) {
//			savevo.setRqstNo(rqstno);
			savevo.setIbsStatus("I");
			savevo.setFrsRqstUserId(userid);
			savevo.setRqstUserId(userid);
//			savevo.setRqstSno(i++);
			//데이터 타입 Convert : NUMBER(10,2) ==> NUMBER, 10, 2
//			convertDisplay2Datatype(savevo);

			result += savePdmColRqst(savevo);
		}

		return result;
	}

	/** 데이터 타입 Convert : NUMBER(10,2) ==> NUMBER, 10, 2 @param savevo insomnia */
	private void convertDisplay2Datatype(WaqPdmCol savevo) {
		String datatype = savevo.getDataType();
	    String dataType = null;
	    Integer dataLen = null;
	    Integer dataScal= null;
		if(StringUtils.hasText(datatype)) {

			if (datatype.indexOf(",") > 0) {
				dataType = datatype.substring(0, datatype.indexOf("("));
				dataLen = Integer.valueOf(datatype.substring(datatype.indexOf("(")+1, datatype.indexOf(",")).trim());
				dataScal = Integer.valueOf(datatype.substring(datatype.indexOf(",")+1, datatype.indexOf(")")).trim());

			} else if (datatype.indexOf("(") > 0) {
				dataType = datatype.substring(0, datatype.indexOf("("));
				if(datatype.substring(datatype.indexOf("(")+1, datatype.indexOf(")")).equals("")){
					dataLen = null;
				}else{
					dataLen = Integer.valueOf(datatype.substring(datatype.indexOf("(")+1, datatype.indexOf(")")).trim());
				}
			} else {
				dataType = datatype;
			}

			savevo.setDataType(dataType);
			savevo.setDataLen(dataLen);
			savevo.setDataScal(dataScal);
		}


	}

	/** 물리모델 컬럼 단건 저장... @return insomnia */
	private int savePdmColRqst(WaqPdmCol savevo) {
		int result = 0;

		String tmpStatus = savevo.getIbsStatus();

		if("I".equals(tmpStatus)) {

			result = waqcolmapper.insertSelective(savevo);

		} else if ("U".equals(tmpStatus)) {

			result = waqcolmapper.updateByPrimaryKeySelective(savevo);

		} else if ("D".equals(tmpStatus)) {

			result = waqcolmapper.deleteByPrimaryKey(savevo);

		}

		return result;
	}

	/** insomnia */
	public List<WaqPdmCol> getPdmColRqstList(WaqMstr search) {

		return waqcolmapper.selectPdmColRqstList(search);
	}

	/** insomnia */
	public int delPdmColRqst(WaqMstr reqmst, ArrayList<WaqPdmCol> list) {
		int result = 0;

		for (WaqPdmCol delvo : list) {
//			if("C".equals(delvo.getRegTypCd())) {
				delvo.setIbsStatus("D");
//			} else {
//				delvo.setIbsStatus("U");
//				delvo.setRqstDcd("DD");
//			}
		}

		result += regPdmColList(reqmst, list);


		return result;
	}

	/** insomnia */
	public int regPdmxlsColList(WaqMstr reqmst, List<WaqPdmCol> list) {
		int result = 0;

		//요청 테이블 리스트를 가져온다.
		List<WaqPdmTbl> tbllist = waqmapper.selectPdmTblListbyMst(reqmst);
		Map<String, WaqPdmTbl> tblmap = new HashMap<String, WaqPdmTbl>();


		for (WaqPdmTbl tblvo : tbllist) {
			String fullpath = tblvo.getFullPath();
			String tblpnm = tblvo.getPdmTblPnm();
//			String rqstNo = tblvo.getRqstNo();
//			Integer rqstSno = tblvo.getRqstSno();

			String key = fullpath+"|"+tblpnm;
			tblmap.put(key, tblvo);
		}

		//테이블이 비어있을 경우 ...
		if(tblmap.isEmpty()) {
			return -999;
		}

		for (WaqPdmCol colvo : list) {
			String comkey = colvo.getFullPath() + "|" + colvo.getPdmTblPnm();
			if (tblmap.containsKey(comkey)) {
				WaqPdmTbl tmptbl = tblmap.get(comkey);

				colvo.setRqstNo(tmptbl.getRqstNo());
				colvo.setRqstSno(tmptbl.getRqstSno());
			} else {
				return -999;
			}

		}

		result += regPdmColList(reqmst, list);


		return result;
	}

	/** insomnia
	 * @throws Exception */
	public int regMartDasWaq(WaqMstr reqmst, ArrayList<WaqPdmTbl> list) throws Exception {
		int result = 0;
		
		result = register(reqmst, list);

		return result;
	}

	/** insomnia */
	@Override
	public List<WaqPdmTbl> getMartDasTblList(WaqPdmTbl search) throws Exception { 
//		List listSubjectArea	= new ArrayList();
//		
//		Map param	 = new HashMap();
//		
		String[] arrFullPath = UtilString.null2Blank(search.getSubjLnm()).split("\\>");
//		
//		String libName  = "";
//		String mdlName  = "";
		String subjName = "";
		
		subjName = arrFullPath[arrFullPath.length-1];
//		
//		if(arrFullPath.length == 4){
//		
//			libName  = arrFullPath[1];
//			mdlName  = arrFullPath[2];
//			subjName = arrFullPath[3];
//			
//		}else if(arrFullPath.length == 3){
//			
//			libName  = arrFullPath[0];
//			mdlName  = arrFullPath[1];
//			subjName = arrFullPath[2];
//		}else{
//			libName  = arrFullPath[0];
//			mdlName  = arrFullPath[1];
//			subjName = arrFullPath[2];
//		}
//				
//		String tblName = UtilString.null2Blank(search.getPdmTblLnm());
//		    	
//    	param.put("category_name", libName);
//    	param.put("model_name",mdlName);
//    	param.put("subjectarea_name", subjName);
//    	
//    	//param.put("entity_name_physical", tblName);
//    	    	    	
//    	logger.debug("\n libName : "+ libName);
//    	logger.debug("\n mdlName : "+ mdlName); 
//    	logger.debug("\n subjName : "+ subjName);
		
		logger.debug("\n getMartDasTblList >>>>");
    	
		search.setSubjectarea_name(subjName);
		
    	List<WaqPdmTbl> reslist = dasMartMapper.selectMartList(search);
    	
//    	for(int i=0; i<reslist.size(); i++) {
//    		reslist.get(i).setFullPath(UtilString.null2Blank(search.getSubjLnm()));
//    	}
    	
		return reslist;
	}
	
	public Comparator<Map<String, String>> mapComparator = new Comparator<Map<String, String>>() { 
	    public int compare(Map<String, String> m1, Map<String, String> m2) {
	        return m1.get("entity_name_physical").compareTo(m2.get("entity_name_physical"));
	    }
	};


	@Override
	public List<WaqPrfRelColVO> getErMartDasRelLst(WaqPrfRelColVO search) {
//		List<WaqPrfRelColVO> list = r9mapper.selectErMart9RelLst(search);
		List<WaqPrfRelColVO> list = null;
		return list;
	}

}
