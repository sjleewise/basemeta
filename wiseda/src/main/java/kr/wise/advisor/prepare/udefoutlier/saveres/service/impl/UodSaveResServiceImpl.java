package kr.wise.advisor.prepare.udefoutlier.saveres.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.advisor.prepare.udefoutlier.isolationforest.service.UodcIsfrService;

import kr.wise.advisor.prepare.udefoutlier.saveres.service.UodcSaveResService;
import kr.wise.advisor.prepare.udefoutlier.saveres.service.WadUodcSvRes;
import kr.wise.advisor.prepare.udefoutlier.saveres.service.WadUodcSvResMapper;
import kr.wise.advisor.prepare.udefoutlier.script.PythonScriptService;
import kr.wise.advisor.prepare.udefoutlier.service.WadUod;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodCreComp;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodCreCompMapper;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodMapper;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDase;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseCol;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseColMapper;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseMapper;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("UodcSaveResService")
public class UodSaveResServiceImpl implements UodcSaveResService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WadUodMapper wadUodMapper;
	
	@Inject 
	private WadUodCreCompMapper WadUodCreCompMapper;
	
	@Inject
	private WadUodcAnaDaseMapper wadUodcAnaDaseMapper;
	
	@Inject
	private WadUodcAnaDaseColMapper wadUodcAnaDaseColMapper;
	
	@Inject
	private EgovIdGnrService uodIdGnrService;
	
	@Inject
	private WadUodcSvResMapper wadUodcSvResMapper;
	
	@Inject
	private PythonScriptService pythonScriptService;  
	
	

	@Override
	public WadUodcSvRes getUodcSvResDetail(WadUodcSvRes search) {
		
		return wadUodcSvResMapper.selectUodcSvResDetail(search);
	}


	@Override
	public int regUodcSvRes( WadUodcSvRes mstData) throws Exception {
		
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
		
		//LINK 정보 INSERT  
		pythonScriptService.insertLinkInfo(udfOtlDtcId);
		
		
		// ====WAD_UOD_CRE_COMP INSERT========
		WadUodCreComp creVo = new WadUodCreComp(); 
		
		creVo.setUdfOtlDtcId(udfOtlDtcId);
		creVo.setCreCompId(creCompId);
		creVo.setCreCompNm("Save Result");
		creVo.setCompId("SaveRes");
		
		result = WadUodCreCompMapper.deleteByPrimaryKey(udfOtlDtcId, creCompId);
		
		result = WadUodCreCompMapper.insertSelective(creVo);
		//=======================================
		
				
		//기존 데이터 UPDATE
		result = wadUodcSvResMapper.updateByPrimaryKeySelective(mstData); 
		
		if(result == 0){ 
			
			mstData.setAnaDaseId(anaDaseId); 
		 
			//WAD_UODC_DASE_CND_DV  Insert
			result = wadUodcSvResMapper.insertSelective(mstData); 					
		}
				
		
		return result; 
	}


	@Override
	public WadUodcSvRes getUodcSaveResForScrt(WadUodcSvRes search) { 

		return wadUodcSvResMapper.selectUodcSaveResForScrt(search); 	
	} 

	

}
