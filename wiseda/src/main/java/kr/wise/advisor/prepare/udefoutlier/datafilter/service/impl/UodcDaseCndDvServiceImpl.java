package kr.wise.advisor.prepare.udefoutlier.datafilter.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.advisor.prepare.udefoutlier.datafilter.service.UodcDaseCndDvService;
import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDv;
import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDvCnd;
import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDvCndMapper;
import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDvMapper;
import kr.wise.advisor.prepare.udefoutlier.script.PythonScriptService;
import kr.wise.advisor.prepare.udefoutlier.service.WadUod;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodCreComp;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodCreCompMapper;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodMapper;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDase;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseColMapper;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseMapper;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("UodcDaseCndDvService")
public class UodcDaseCndDvServiceImpl implements UodcDaseCndDvService  {  
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	
	@Inject 
	private WadUodMapper wadUodMapper;  
	
	@Inject 
	private WadUodCreCompMapper WadUodCreCompMapper;
	
	@Inject  
	private WadUodcDaseCndDvMapper daseCndDvMapper ;
	
	@Inject  
	private WadUodcDaseCndDvCndMapper daseCndDvCndMapper ;   
	
	@Inject  
	private WadUodcAnaDaseMapper wadUodcAnaDaseMapper ;
	
	@Inject  
	private WadUodcAnaDaseColMapper wadUodcAnaDaseColMapper ;    
	
	@Inject
    private EgovIdGnrService uodIdGnrService;
	
	@Inject
	private PythonScriptService pythonScriptService;  
	

	@Override
	public WadUodcDaseCndDv getUodcDaseCndDvDetail(WadUodcDaseCndDv search) {
		
		return daseCndDvMapper.selectDetailInfo(search); 
	}


	
	@Override 
	public int regDaseCndDv( WadUodcDaseCndDv mstData) throws Exception {     
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();   
		String userid = user.getUniqId();
		
		mstData.setWritUserId(userid);
		
		String anaDaseId = uodIdGnrService.getNextStringId();    
		
		WadUod uodvo = new WadUod();
		
		String udfOtlDtcId = mstData.getUdfOtlDtcId(); 
		String udfOtlDtcNm = mstData.getUdfOtlDtcNm();  
		String creCompId   = mstData.getCreCompId();
		String anaDaseNm   = mstData.getAnaDaseNm();
		
		logger.debug("\n getUdfOtlDtcId:" + udfOtlDtcId); 
		
		uodvo.setUdfOtlDtcId(udfOtlDtcId);
		uodvo.setUdfOtlDtcNm(udfOtlDtcNm);
		uodvo.setMdlJsonInf(uodvo.removeComma(mstData.getMdlJsonInf()));
		uodvo.setWritUserId(userid);
		
		result = wadUodMapper.updateMdlScrtJson(uodvo); 
		
		if(result == 0){
		
			//WAD_UOD INSERT		
			result = wadUodMapper.insertSelective(mstData);
		}
				
		//기존 데이터 UPDATE
		result = daseCndDvMapper.updateByPrimaryKeySelective(mstData); 
		
		if(result == 0){ 
		 
			//WAD_UODC_DASE_CND_DV  Insert
			result = daseCndDvMapper.insertSelective(mstData); 					
		}
				
		//===== 분석데이터셋 , 분석데이터셋컬럼 처리=========  
		WadUodcAnaDase anaVo = new WadUodcAnaDase(); 
		
		anaVo.setAnaDaseId(anaDaseId);
		anaVo.setAnaDaseNm(anaDaseNm);
		anaVo.setUdfOtlDtcId(udfOtlDtcId);
		anaVo.setCreCompId(creCompId);
		anaVo.setWritUserId(userid);
		anaVo.setSrcAnaDaseId(mstData.getSrcAnaDaseId()); 
		
		int iRtn;
		
		iRtn = wadUodcAnaDaseMapper.updateFkUodcAnaDase(anaVo); 
		
		if(iRtn == 0) {
			
			//WAD_UODC_ANA_DASE INSERT 
			result += wadUodcAnaDaseMapper.insertSelective(anaVo); 
		}
		
		//WAD_UODC_ANA_DASE_COL DELETE 
		result += wadUodcAnaDaseColMapper.deleteFkUodcAnaDaseCol(anaVo);  
		
		//WAD_UODC_ANA_DASE_COL INSERT 
		result += wadUodcAnaDaseColMapper.insertAnaColDaseCndDv(anaVo); 
		
		
		// 분석데이터셋ID  UPDATE 		
		result = daseCndDvMapper.updateDaseCndDvAnaDaseId(mstData);      
		//====================================
		
		return result; 
	}



	@Override
	public int regDaseCndDvCnd(List<WadUodcDaseCndDvCnd> list, WadUodcDaseCndDv mstData) throws Exception {
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();     
		String userid = user.getUniqId();
		
		mstData.setWritUserId(userid);
		
		String anaDaseId = uodIdGnrService.getNextStringId();    
		
		WadUod uodvo = new WadUod();
		
		String udfOtlDtcId = mstData.getUdfOtlDtcId(); 
		String udfOtlDtcNm = mstData.getUdfOtlDtcNm();  
		String creCompId   = mstData.getCreCompId();
		String anaDaseNm   = mstData.getAnaDaseNm();
		
		logger.debug("\n getUdfOtlDtcId:" + udfOtlDtcId); 
		
		uodvo.setUdfOtlDtcId(udfOtlDtcId);
		uodvo.setUdfOtlDtcNm(udfOtlDtcNm);
		if (mstData.getMdlJsonInf().length() > 0 && mstData.getMdlJsonInf().charAt(mstData.getMdlJsonInf().length()-1)==',') {
			mstData.setMdlJsonInf(mstData.getMdlJsonInf().substring(0, mstData.getMdlJsonInf().length()-1));
		}
		uodvo.setMdlJsonInf(mstData.getMdlJsonInf());
		uodvo.setWritUserId(userid);
		
		result = wadUodMapper.updateMdlScrtJson(uodvo); 
		
		if(result == 0){
		
			//WAD_UOD INSERT		
			result = wadUodMapper.insertSelective(mstData);
		}
		
		//LINK 정보 INSERT 
		pythonScriptService.insertLinkInfo(udfOtlDtcId);
		
		// ====WAD_UOD_CRE_COMP INSERT========
		WadUodCreComp creVo = new WadUodCreComp(); 
		
		creVo.setUdfOtlDtcId(udfOtlDtcId);
		creVo.setCreCompId(creCompId);
		creVo.setCreCompNm("Filter");
		creVo.setCompId("Filter");
		
		result = WadUodCreCompMapper.deleteByPrimaryKey(udfOtlDtcId, creCompId);
		
		result = WadUodCreCompMapper.insertSelective(creVo);
		//=======================================
				
		//기존 데이터 UPDATE
		result = daseCndDvMapper.updateByPrimaryKeySelective(mstData); 
		
		if(result == 0){ 
		 
			//WAD_UODC_DASE_CND_DV  Insert
			result = daseCndDvMapper.insertSelective(mstData); 					
		}
		
		// ====조건분할 조건 insert===========				
		result += daseCndDvCndMapper.deleteCreCompId(udfOtlDtcId, creCompId); 
		
		for(WadUodcDaseCndDvCnd cndVo : list){
			
			cndVo.setUdfOtlDtcId(udfOtlDtcId); 
			cndVo.setCreCompId(creCompId);
			cndVo.setWritUserId(userid);
			
			int iRtn = 0;
			
			//기존 데이터 UPDATE
			iRtn = daseCndDvCndMapper.updateByPrimaryKeySelective(cndVo); 
			
			if(iRtn == 0){ 
			 
				//WAD_UODC_DASE_CND_DV  Insert
				result += daseCndDvCndMapper.insertSelective(cndVo); 					
			}
			
		}
		//=====================================
		//조건 텍스트에 들어가는 CND_CONTS 값 설정
		WadUodcDaseCndDv param = new WadUodcDaseCndDv();
		param.setUdfOtlDtcId(udfOtlDtcId);
		param.setCreCompId(creCompId);
		
		List<WadUodcDaseCndDvCnd> cndContsList = daseCndDvCndMapper.selectUodcDataFilterColList(param);
		
		String cndConts = "";
		for(WadUodcDaseCndDvCnd data : cndContsList) {
			cndConts += data.getCndCd() + " " + data.getColPnm() + " " + data.getOprCd() + " " + data.getCndVal() + " ";
		}
		
		param.setCndConts(cndConts);
		
		daseCndDvMapper.updateByPrimaryKeySelective(param);
		
		//===== 분석데이터셋 , 분석데이터셋컬럼 처리=========  
		WadUodcAnaDase anaVo = new WadUodcAnaDase(); 
		
		anaVo.setAnaDaseId(anaDaseId);
		anaVo.setAnaDaseNm(anaDaseNm);
		anaVo.setUdfOtlDtcId(udfOtlDtcId);
		anaVo.setCreCompId(creCompId);
		anaVo.setWritUserId(userid);
		anaVo.setSrcAnaDaseId(mstData.getSrcAnaDaseId()); 
		
		int iRtn;
		
		iRtn = wadUodcAnaDaseMapper.updateFkUodcAnaDase(anaVo); 
		
		if(iRtn == 0) {
			
			//WAD_UODC_ANA_DASE INSERT 
			result += wadUodcAnaDaseMapper.insertSelective(anaVo); 
		}
		
		//WAD_UODC_ANA_DASE_COL DELETE 
		result += wadUodcAnaDaseColMapper.deleteFkUodcAnaDaseCol(anaVo);  
		
		//WAD_UODC_ANA_DASE_COL INSERT 
		result += wadUodcAnaDaseColMapper.insertAnaColDaseCndDv(anaVo); 
		
		
		// 분석데이터셋ID  UPDATE 		
		result = daseCndDvMapper.updateDaseCndDvAnaDaseId(mstData);      
		//====================================
		
		return result; 
	}



	@Override
	public List<WadUodcDaseCndDvCnd> getUodcDataFilterColList(WadUodcDaseCndDv search) {
		
		return daseCndDvCndMapper.selectUodcDataFilterColList(search);    
	}


	//filter 조건 설정 기능에서 선택 목록 삭제 로직
	@Override
	public int delDaseCndDvCnd(List<WadUodcDaseCndDvCnd> list, WadUodcDaseCndDv mstData) throws Exception {
		int result = 0;
		
		logger.debug("=============================================");
		logger.debug("\n getUdfOtlDtcId:" + mstData.getUdfOtlDtcId());
		logger.debug("\n getCreCompId:" + mstData.getCreCompId());

		WadUod uodvo = new WadUod();
		
		String udfOtlDtcId = mstData.getUdfOtlDtcId();
		String creCompId   = mstData.getCreCompId();
		
		uodvo.setUdfOtlDtcId(udfOtlDtcId);
		
		for(WadUodcDaseCndDvCnd cndVo : list){
			//WAD_UODC_DASE_CND_DV  delete
			result += daseCndDvCndMapper.deleteByPrimaryKey(udfOtlDtcId, creCompId, cndVo.getCndSno());
		}
		
		//조건 텍스트에 들어가는 CND_CONTS 값 설정
		WadUodcDaseCndDv param = new WadUodcDaseCndDv();
		param.setUdfOtlDtcId(udfOtlDtcId);
		param.setCreCompId(creCompId);
		
		List<WadUodcDaseCndDvCnd> cndContsList = daseCndDvCndMapper.selectUodcDataFilterColList(param);
		
		String cndConts = "";
		for(WadUodcDaseCndDvCnd data : cndContsList) {
			cndConts += data.getCndCd() + " " + data.getColPnm() + " " + data.getOprCd() + " " + data.getCndVal() + " ";
		}
		
		param.setCndConts(cndConts);
		
		daseCndDvMapper.updateByPrimaryKeySelective(param);

		return result;
	}



	@Override
	public List<WadUodcDaseCndDvCnd>  getUodcDaseCndDvCndForScrt(WadUodcDaseCndDv search) {

		return daseCndDvCndMapper.selectUodcDaseCndDvCndForScrt(search); 
	}
		
	
}
