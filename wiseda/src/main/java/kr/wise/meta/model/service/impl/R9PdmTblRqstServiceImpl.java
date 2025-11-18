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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.commons.util.ArrayAdapterFactory;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.tblrel.service.WaqPrfRelColVO;
import kr.wise.meta.model.service.R9Mart;
import kr.wise.meta.model.service.R9PdmTblRqstService;
import kr.wise.meta.model.service.WamPdmTbl;
import kr.wise.meta.model.service.WamPdmTblMapper;
import kr.wise.meta.model.service.WaqPdmCol;
import kr.wise.meta.model.service.WaqPdmColMapper;
import kr.wise.meta.model.service.WaqPdmTbl;
import kr.wise.meta.model.service.WaqPdmTblMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.genesis.mmart.action.ERwin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.mail.handlers.message_rfc822;

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
@Service("r9pdmTblRqstService")
public class R9PdmTblRqstServiceImpl implements R9PdmTblRqstService {

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
				
				String r9Dist = message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.R9_DISTRIBUTOR, null, Locale.getDefault());
		    	
		    	logger.debug("\r9Dist:" + r9Dist); 
				
				//단건 저장...
				if(r9Dist.equals("SOFTVERK")){ 
					result += savePdmTblRqstSoft(saveVo);
				}else{
					result += savePdmTblRqst(saveVo);					
				}
					
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
//			String objid = objectIdGnrService.getNextStringId();
//			saveVo.setStwdId(objid);
			saveVo.setSubjId(saveVo.getSubjId());
//			saveVo.setSubjLnm(saveVo.getSubjectarea_name());
			result = waqmapper.insertSelective(saveVo);
			//R9 마트에서 테이블에 해당하는 컬럼 리스트를 가져와 저장한다.
//			waqcolmapper.insertByRqstMartCol(saveVo);
			List rslist = null;
			Map maplist = new HashMap();
			
			maplist.put("category_id", saveVo.getCategory_id());
			maplist.put("model_id", saveVo.getModel_id())  ;
			//erwin 2020 이전 버전
			//maplist.put("subjectarea_id", saveVo.getSubjectarea_id())  ;
			//erwin 2020버전부터
			maplist.put("subjectarea_id", saveVo.getSubjectarea_id())  ;
			maplist.put("entity_id", saveVo.getEntity_id())  ;
			//maplist.put("attribute_id", saveVo.getAttribute_id())  ;
//			maplist.put("category_name", saveVo.getCategory_name())  ;
//			maplist.put("model_name", saveVo.getModel_name())  ;
//			maplist.put("subjectarea_name", saveVo.getSubjectarea_name()) ;
			//maplist.put("entity_owner_name", saveVo.getEntity_owner_name())  ;
			//maplist.put("entity_name", saveVo.getEntity_name())  ; 
			//maplist.put("entity_name_physical", saveVo.getEntity_name_physical())  ;
			//maplist.put("entity_name_logical", saveVo.getEntity_name_logical())  ;
			//maplist.put("entity_logical_yn", saveVo.getEntity_logical_yn())  ;
			
			logger.debug("\n category_name:" + saveVo.getCategory_name());
			logger.debug("\n model_name:" +  saveVo.getModel_name());
			logger.debug("\n subjectarea_name:" + saveVo.getSubjectarea_name()); 
			
			rslist	= ERwin.getComboList2(maplist); 
			
			
			if(rslist != null){
												
	    		for(int i=0; i<rslist.size(); i++){
	    			// if(i > 1000) break;	    			
	    			
	    			List<WaqPdmCol> rsCollist = new ArrayList();
	    			WaqPdmCol waqcol = new WaqPdmCol();
	    			
	    			Map rsmap = (Map)rslist.get(i);
	    				    			
	    			String  pdmTblPnm = UtilString.null2Blank(rsmap.get("entity_name_physical"));
	    			String  pdmColPnm = UtilString.null2Blank(rsmap.get("attribute_name_physical"));
	    			String  pdmColLnm = UtilString.null2Blank(rsmap.get("attribute_name"));
	    			Integer colOrd    = null; 
	    			String  dataType  = UtilString.null2Blank(rsmap.get("attribute_data_type_physical"));
	    			String  pkYn      = UtilString.null2Blank(rsmap.get("attribute_key_YN"));
	    			Integer pkOrd     = null;
	    			
	    			//=========logical only 일경우 스킵============
	    			// ER-WIN API 에서 값을 제대로 주지 못함 
	    			String logicalOnly = UtilString.null2Blank(rsmap.get("attribute_logical_only"));
	    				    				    			
	    			logger.debug("\n logicalOnly:" + logicalOnly);
	    			
	    			if(!logicalOnly.equals("N")) {
	    			    continue;
	    			}	    			
	    			//==============================================
	    			
	    			if(UtilString.null2Blank(rsmap.get("attribute_order_logical")).equals("")){
	    				continue;
	    				
	    			}else{
	    				colOrd = Integer.parseInt(UtilString.null2Blank(rsmap.get("attribute_order_logical")));
	    			}
	    			
	    			
	    			if(UtilString.null2Blank(rsmap.get("attribute_key_YN")).equals("Y")) {
	    				pkOrd = colOrd;
	    			}	    			
	    					     
	    			String  defltVal  = UtilString.null2Blank(rsmap.get("attribute_default_value"));
	    			String  nullYn    = UtilString.null2Blank(rsmap.get("attribute_null_YN"));	    			
	    			String  objDescn  = UtilString.null2Blank(rsmap.get("attribute_comment"));
	    			
	    			waqcol.setRqstDcd("CU");
	    			waqcol.setRqstNo(rqstNo);
	    			waqcol.setRqstSno(saveVo.getRqstSno());
	    			waqcol.setPdmTblPnm(pdmTblPnm);
	    			waqcol.setPdmColPnm(pdmColPnm);
	    			waqcol.setPdmColLnm(pdmColLnm);
	    			waqcol.setDataType(dataType);
	    			
	    			waqcol.setColOrd(colOrd);
	    			
	    			if(nullYn.equals("Y")){ 
	    				waqcol.setNonulYn("N")  ;
	    			}else{
	    				waqcol.setNonulYn("Y")  ;
	    			}
	    			
	    			waqcol.setPkYn(pkYn);	    			
	    			waqcol.setPkOrd(pkOrd);
	    			
	    			waqcol.setDefltVal(defltVal); 
	    			waqcol.setObjDescn(objDescn);
	    			
	    			
	    			rsCollist.add(waqcol);  
	    			
	    			logger.debug("{}",rsCollist);
	    			regPdmColList(rsCollist);
	    		}
	    	}
		
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
	
	/** 물리모델 요청서 단건 저장... @return insomnia */
	private int savePdmTblRqstSoft(WaqPdmTbl saveVo) throws Exception{
		int result  = 0;
		
		String tmpstatus = saveVo.getIbsStatus();
		
		logger.debug("tmpstatus:{} \n WaqPdmTbl:{}", tmpstatus, saveVo);
		String rqstNo = saveVo.getRqstNo();
		int rqstSno = saveVo.getRqstSno();
		
		if ("I".equals(tmpstatus)) {
//			saveVo.setSubjId(saveVo.getSubjId());
			saveVo.setSubjLnm(saveVo.getSubjectarea_name());
			result = waqmapper.insertSelective(saveVo);
			//R9 마트에서 테이블에 해당하는 컬럼 리스트를 가져와 저장한다.
//			waqcolmapper.insertByRqstMartCol(saveVo);
			
			List<WaqPdmCol> rsCollist = new ArrayList();
			WaqPdmCol waqcol = new WaqPdmCol();
			
			String libId = saveVo.getCategory_id();
			String modId = saveVo.getModel_id();
			String subId = saveVo.getSubjectarea_id();
			String entId = saveVo.getEntity_id();
			
			String sMartIP      =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_IP, null, Locale.getDefault());
			String sMartPort    =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_PORT, null, Locale.getDefault());
			String sMartWashome =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_WASHOME, null, Locale.getDefault()); 
			String sMartReport  =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_REPORT, null, Locale.getDefault());
			String sMartId      =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_ID, null, Locale.getDefault());  
			String sMartPw      =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_PW, null, Locale.getDefault());
			
			String sHeaderKey   =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_HEADERKEY, null, Locale.getDefault());
			String sHeaderValue =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_HEADERVALUE, null, Locale.getDefault());
			String sUdpUseYn    =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_UDP_USE_YN, null, Locale.getDefault());
					
			String control = "getAttribute";
			//String url  = "http://"+WiseConfig.MART_IP+":"+WiseConfig.MART_PORT+"/"+ WiseConfig.MART_WASHOME+"/service/report/generateReport/"+WiseConfig.MART_REPORT+"/"+control+"/"+WiseConfig.MART_ID+"/"+WiseConfig.MART_PW;
			
			String url  = "http://"+ sMartIP +":"+ sMartPort +"/"+ sMartWashome +"/service/report/generateReport/" + sMartReport +"/"+ control +"/"+ sMartId +"/"+ sMartPw;

			logger.debug("\n url:" + url);
			
//			String param = "?libId=65543";
//			param += "&modId=65547";
//			param += "&subId=382";
//			param += "&entId=212";
//			param = "";
			
			String param = "?libId="+libId;
			param += "&modId="+modId;
			param += "&subId="+subId;
			param += "&entId="+entId;
			
			int pkOrd = 0;
			
			URL obj = new URL(url+param);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");
		
			//add request header
			con.setRequestProperty(sHeaderKey, sHeaderValue);
		
			int responseCode = con.getResponseCode();
//			System.out.println("\nSending 'GET' request to URL : " + url + param);
//			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			String inputLine;
			StringBuffer responseBuffer = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				responseBuffer.append(inputLine);
			}
			in.close();
			
			String jsonInfo = responseBuffer.toString();
//			String jsonInfo = UtilJson.convertJsonString(responseBuffer.toString());
	    	
			try {
				Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
				Type type = new TypeToken<List<R9Mart>>() {}.getType();
				List<R9Mart> listr9lib = gson.fromJson(jsonInfo, type);
				List<R9Mart> listr9L = gson.fromJson(gson.toJson(listr9lib.get(0).getLibrary()), type);
				
				for(int i=0;i<listr9L.size();i++){				
					List<R9Mart> listr9M = gson.fromJson(gson.toJson(listr9L.get(i).getModel()),type);
					for(int j=0;j<listr9M.size();j++){
						List<R9Mart> listr9S = gson.fromJson(gson.toJson(listr9M.get(j).getSubject_Area()),type);
						for(int k=0;k<listr9S.size();k++){
							List<R9Mart> listr9E = gson.fromJson(gson.toJson(listr9S.get(k).getEntity()),type);
							for(int l=0;l<listr9E.size();l++){
								if(listr9E.get(l).getAttribute() != null){
									pkOrd = 1;
									List<R9Mart> listr9A = gson.fromJson(gson.toJson(listr9E.get(l).getAttribute()),type);
//									System.out.println(listr9A);
									for(int m=0;m<listr9A.size();m++){
										waqcol.setRqstDcd("CU");
										waqcol.setRqstNo(rqstNo);
										waqcol.setRqstSno(saveVo.getRqstSno());
										waqcol.setPdmTblPnm(listr9E.get(l).getPhysical_Name());
										waqcol.setPdmColPnm(listr9A.get(m).getPhysical_Name());
										waqcol.setPdmColLnm(listr9A.get(m).getName());
										waqcol.setDataType(listr9A.get(m).getPhysical_Data_Type());
										waqcol.setColOrd(m+1);
										
										if(listr9A.get(m).getNull_Option_Type() != null && listr9A.get(m).getNull_Option_Type().equals("1")){ 
											waqcol.setNonulYn("Y")  ;
										}else{
											waqcol.setNonulYn("N")  ;
										}
										if(listr9A.get(m).getIs_PK() != null && listr9A.get(m).getIs_PK().equals("true")){ 
											waqcol.setPkYn("Y");	    			
											waqcol.setPkOrd(pkOrd);
											pkOrd++;
										}else{
											waqcol.setPkYn("N");	    														
										}
										
										waqcol.setDefltVal(listr9A.get(m).getDefault_Value()); 
										waqcol.setObjDescn(listr9A.get(m).getDefinition());
	
										rsCollist.add(waqcol);  
										logger.debug("{}",rsCollist);
										waqcol = new WaqPdmCol();
									}
									regPdmColList(rsCollist);
								}
							}
						}				
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			convertDisplay2Datatype(savevo);

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
	public int regMart9Waq(WaqMstr reqmst, ArrayList<WaqPdmTbl> list) throws Exception {
		int result = 0;
		
		result = register(reqmst, list);

		return result;
	}

	/** insomnia */
	@Override
	public List<WaqPdmTbl> getMart9TblList(WaqPdmTbl search) throws Exception { 
		List listSubjectArea	= new ArrayList();
		
		Map param	 = new HashMap();
    	
//    	String category_id				= search.getCategory_id();	
//    	String model_id					= search.getModel_id();
//    	String entity_id				= search.getEntity_id();
//    	String attribute_id				= search.getAttribute_id();
//    	String subjectarea_id			= search.getSubjectArea_id();
//    	String subjectArea_name			= search.getFullPath();
//    	String entity_name				= search.getPdmTblLnm();
//    	String entity_name_physical		= search.getEntity_name_physical();
    	
//    	if(category_id				!= null && !category_id		.equals("0"))  param.put("category_id"		, category_id		);
//    	if(model_id					!= null && !model_id		.equals("0"))  param.put("model_id"			, model_id			);
//    	if(entity_id				!= null && !entity_id		.equals("0"))  param.put("entity_id"		, entity_id			);
//    	if(attribute_id				!= null && !attribute_id	.equals("0"))  param.put("attribute_id"		, attribute_id		);
//    	if(subjectarea_id	!= null && !subjectarea_id	.equals("0"))  param.put("subjectarea_id"	, subjectarea_id	);
//    	if(subjectArea_name	!= null && !subjectArea_name	.equals("0"))  param.put("subjectArea_name"	, subjectArea_name	);
//
//    	if(entity_name			!= null && !entity_name	.equals("0"))  param.put("entity_name"			, entity_name				);
//    	if(entity_name_physical	!= null && !entity_name_physical	.equals("0"))  param.put("entity_name_physical"	, entity_name_physical		);
    	
		String[] arrFullPath = UtilString.null2Blank(search.getSubjLnm()).split("\\>");
		
		String libName  = "";
		String mdlName  = "";
		String subjName = "";
		
		if(arrFullPath.length > 4) {
			libName  = arrFullPath[arrFullPath.length-3];
			mdlName  = arrFullPath[arrFullPath.length-2];
			subjName = arrFullPath[arrFullPath.length-1];
		} else if(arrFullPath.length == 4){
		
			libName  = arrFullPath[1];
			mdlName  = arrFullPath[2];
			subjName = arrFullPath[3];
			
		}else if(arrFullPath.length == 3){
			
			libName  = arrFullPath[0];
			mdlName  = arrFullPath[1];
			subjName = arrFullPath[2];
		}else{
			libName  = arrFullPath[0];
			mdlName  = arrFullPath[1];
			subjName = arrFullPath[2];
		}
				
		String tblName = UtilString.null2Blank(search.getPdmTblLnm());
		    	
    	param.put("category_name", libName);
    	param.put("model_name",mdlName);
//    	param.put("subjectarea_name", subjName);
    	
    	//param.put("entity_name_physical", tblName);
    	    	    	
    	logger.debug("\n libName : "+ libName);
    	logger.debug("\n mdlName : "+ mdlName); 
    	logger.debug("\n subjName : "+ subjName);
    	
    	List list = null;
    	
    	list = ERwin.getComboEntityList(param); 
    	
    	logger.debug("\n list size : "+ list.size());
    	
    	List listmap = new ArrayList();
    	
    	if(list != null){
    		for(int i=0; i<list.size(); i++){
    			if(i > 1000) break;
    			Map maplist = new HashMap();
    			Map map = (Map)list.get(i);
    			maplist.put("category_id", map.get("category_id"));
    			maplist.put("model_id", map.get("model_id"))  ;
    			maplist.put("subjectarea_id", map.get("subjectarea_id"))  ;
    			maplist.put("entity_id", map.get("entity_id"))  ;
    			maplist.put("attribute_id", map.get("attribute_id"))  ;
    			maplist.put("category_name", map.get("category_name"))  ;
    			maplist.put("model_name", map.get("model_name"))  ;
    			maplist.put("subjId", search.getSubjId())  ;
    			maplist.put("fullPath", search.getFullPath())  ;
    			maplist.put("subjectarea_name", map.get("subjectarea_name"))  ;
    			maplist.put("entity_owner_name", map.get("entity_owner_name"))  ;
    			maplist.put("entity_name", map.get("entity_name"))  ;
    			maplist.put("entity_name_physical", map.get("entity_name_physical"))  ;
    			maplist.put("entity_name_logical", map.get("entity_name_logical"))  ;
    			
    			String fullPath = map.get("category_name") + ">" +  map.get("model_name") + ">" +  map.get("subjectarea_name");
    			    			 
    			maplist.put("rqstDcd", "CU");
    			maplist.put("regTypCd", "C");
    			maplist.put("fullPath", search.getSubjLnm());    			
    			maplist.put("pdmTblPnm", map.get("entity_name_physical"))  ;
    			maplist.put("pdmTblLnm", map.get("entity_name_logical"))  ;
    			maplist.put("mdlTblTypCd", map.get("entity_property2"))  ;    			
    			
    			maplist.put("objDescn", map.get("entity_comment"))  ;
    			    			
    			if(map.get("entity_physical_yn").equals("Y")){
    				maplist.put("entity_logical_yn", "N")  ;
    			}else{
    				maplist.put("entity_logical_yn", "Y")  ;
    			}
    			maplist.put("ibsStatus", "I")  ;
    			    			
    			String erwinTblName = UtilString.null2Blank(map.get("entity_name_physical"));
    			
    			if(!tblName.equals("")) {
    			    				    				 
    				// 테이블명 입력시 LIKE 검색 
    				if(erwinTblName.contains(tblName)){
        				
    					listmap.add(maplist);
        			}
    				
    			}else{
    				
    				listmap.add(maplist);
    			}    			    			        		    			
    			
    		}
    	}
    	
    	
    	//===========신규/변경 설정=====================
    	WamPdmTbl pdmTblVo = new WamPdmTbl();
    	
    	pdmTblVo.setFullPath(search.getSubjLnm());
    	
    	List<WamPdmTbl> tblLst = wamPdmTblmapper.selectList(pdmTblVo);
    	
    	for(WamPdmTbl sVo :tblLst){
    		    		    		
    		for(int i = 0; i < listmap.size() ;  i++) {
    			
    			HashMap map = (HashMap)listmap.get(i);
    			
    			String tmplTblNm = UtilString.null2Blank(map.get("entity_name_physical"));
    			
    			map.put("stdAplYn", sVo.getStdAplYn()); 
    			
    			if(tmplTblNm.equals(sVo.getPdmTblPnm())) {
    				 
    				map.put("regTypCd", "U");    				
    				break;
    			}		
    		}
    	}
    	//================================================
    	    	
    	//arraylist sort
    	Collections.sort(listmap, mapComparator);      	
    	
		return listmap;
	}
	
	/** insomnia */
	@Override
	public List<WaqPdmTbl> getMart9TblListSoft(WaqPdmTbl search) throws Exception { 
		List<WaqPdmTbl> list = new ArrayList<WaqPdmTbl>();
		WaqPdmTbl waqtbl = new WaqPdmTbl();
		WaqPdmTbl waqtblE = new WaqPdmTbl();

		String[] arrFullPath = UtilString.null2Blank(search.getSubjLnm()).split("\\>");
		
    	String tblName = UtilString.null2Blank(search.getPdmTblLnm());
    	
    	String sMartIP      =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_IP, null, Locale.getDefault());
		String sMartPort    =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_PORT, null, Locale.getDefault());
		String sMartWashome =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_WASHOME, null, Locale.getDefault()); 
		String sMartReport  =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_REPORT, null, Locale.getDefault());
		String sMartId      =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_ID, null, Locale.getDefault());  
		String sMartPw      =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_PW, null, Locale.getDefault());
		
		String sHeaderKey   =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_HEADERKEY, null, Locale.getDefault());
		String sHeaderValue =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_HEADERVALUE, null, Locale.getDefault());
		String sUdpUseYn    =  message.getMessage(message.getMessage("mode", null, Locale.getDefault())+"."+WiseConfig.MART_UDP_USE_YN, null, Locale.getDefault());
				
		String control = "getEntity"; 
		
		String url  = "http://"+ sMartIP +":"+ sMartPort +"/"+ sMartWashome +"/service/report/generateReport/"+ sMartReport +"/"+control+"/"+ sMartId +"/"+ sMartPw;

		logger.debug("\n url:" + url);
		
		String param = "";
		if(arrFullPath.length >= 1){
			param = "libNm="+arrFullPath[0];
		}
		if(arrFullPath.length >= 2){
			param += "&modNm="+arrFullPath[1];
		}
		if(arrFullPath.length >= 3){
			param += "&subNm="+arrFullPath[2];
		}
		if(tblName != null && !tblName.equals("")){
			param += "&entEnmCnG="+tblName;
		}
//		param += "&entNmG="+tblName;	논리명과 물리명 각각 조회가능 둘중하나 막아야함 문의중임
		
		String encParam = URLEncoder.encode(param, "UTF-8");
		
		logger.debug("\n obj >>> " + url + "?" + encParam);
		logger.debug("\n obj >>> " + url + "?" + param);
		
		URL obj = new URL(url + "?" + encParam);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// optional default is GET
		con.setRequestMethod("GET");
		
		logger.debug("\n con.setRequestMethod('GET')");
	
		//add request header
//		con.setRequestProperty(sHeaderKey, sHeaderValue);
		
//		logger.debug("\n con.setRequestProperty(sHeaderKey, sHeaderValue);");
	
		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'GET' request to URL : " + url + param);
//		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
		String inputLine;
		StringBuffer responseBuffer = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			responseBuffer.append(inputLine);
		}
		in.close();
		
		String jsonInfo = responseBuffer.toString();
//		String jsonInfo = UtilJson.convertJsonString(responseBuffer.toString());
    	
		try {
			Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
			Type type = new TypeToken<List<R9Mart>>() {}.getType();
			List<R9Mart> listr9lib = gson.fromJson(jsonInfo, type);
			List<R9Mart> listr9L = gson.fromJson(gson.toJson(listr9lib.get(0).getLibrary()), type);
			for(int i=0;i<listr9L.size();i++){				
				List<R9Mart> listr9M = gson.fromJson(gson.toJson(listr9L.get(i).getModel()),type);
				if(listr9L.get(i).getName() != null && listr9L.get(i).getName().length() != 0){
					waqtbl.setCategory_id(listr9L.get(i).getId());
					waqtbl.setCategory_name(listr9L.get(i).getName());
				}
				for(int j=0;j<listr9M.size();j++){
					List<R9Mart> listr9S = gson.fromJson(gson.toJson(listr9M.get(j).getSubject_Area()),type);
					waqtbl.setModel_id(listr9M.get(j).getId());
					waqtbl.setModel_name(listr9M.get(j).getName());
					for(int k=0;k<listr9S.size();k++){
						if(listr9S.get(k).getEntity() != null){
							List<R9Mart> listr9E = gson.fromJson(gson.toJson(listr9S.get(k).getEntity()),type);
							waqtbl.setSubjectarea_id(listr9S.get(k).getId());
							waqtbl.setSubjectarea_name(listr9S.get(k).getName());
							for(int l=0;l<listr9E.size();l++){
								if(waqtbl.getCategory_name() != null){
									waqtblE.setFullPath(waqtbl.getCategory_name());
									waqtblE.setCategory_id(waqtbl.getCategory_id());
								}
								if(waqtbl.getModel_name() != null){
									waqtblE.setFullPath(waqtbl.getCategory_name()+">"+waqtbl.getModel_name());
									waqtblE.setModel_id(waqtbl.getModel_id());
								}
								if(waqtbl.getSubjectarea_name() != null){
									waqtblE.setFullPath(waqtbl.getCategory_name()+">"+waqtbl.getModel_name()+">"+waqtbl.getSubjectarea_name());
									waqtblE.setSubjectarea_id(waqtbl.getSubjectarea_id());
								}	
								waqtblE.setEntity_id(listr9E.get(l).getId());
								waqtblE.setPdmTblPnm(listr9E.get(l).getPhysical_Name());
								waqtblE.setPdmTblLnm(listr9E.get(l).getName());
								waqtblE.setObjDescn(listr9E.get(l).getDefinition());
								waqtblE.setIbsStatus("I");
								waqtblE.setRegTypCd("C");
								waqtblE.setRqstDcd("CU");
								
								if(sUdpUseYn.equals("Y")){
									if(listr9E.get(l).getUdp().toString() != null && listr9E.get(l).getUdp().toString().length() != 0){
										for(int m=0;m<listr9E.size();m++){
											List<R9Mart> listr9U = gson.fromJson(gson.toJson(listr9E.get(m).getUdp()),type);
											//UDP 셋팅된 name 값을 WaqPdmTbl에 변수로 만들고 value를 담으면...
										}
									}
								}
								list.add(waqtblE);
								waqtblE = new WaqPdmTbl();
							}
						}
					}				
				}
			}
			
			//===========신규/변경 설정=====================
	    	WamPdmTbl pdmTblVo = new WamPdmTbl();
	    	
	    	pdmTblVo.setFullPath(search.getSubjLnm());
	    	
	    	List<WamPdmTbl> tblLst = wamPdmTblmapper.selectList(pdmTblVo);
	    	
	    	for(WamPdmTbl sVo :tblLst){
	    		    		    		
	    		for(int i = 0; i < list.size() ;  i++) {
	    			
	    			String tmplTblNm = UtilString.null2Blank(list.get(i).getPdmTblPnm());
	    			
	    			list.get(i).setStdAplYn(sVo.getStdAplYn()); 
	    			
	    			if(tmplTblNm.equals(sVo.getPdmTblPnm())) {
	    				list.get(i).setRegTypCd("U");;   				
	    				break;
	    			}		
	    		}
	    	}
	    	
  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Comparator<Map<String, String>> mapComparator = new Comparator<Map<String, String>>() { 
	    public int compare(Map<String, String> m1, Map<String, String> m2) {
	        return m1.get("entity_name_physical").compareTo(m2.get("entity_name_physical"));
	    }
	};


	@Override
	public List<WaqPrfRelColVO> getErMart9RelLst(WaqPrfRelColVO search) {
//		List<WaqPrfRelColVO> list = r9mapper.selectErMart9RelLst(search);
		List<WaqPrfRelColVO> list = null;
		return list;
	}

}
