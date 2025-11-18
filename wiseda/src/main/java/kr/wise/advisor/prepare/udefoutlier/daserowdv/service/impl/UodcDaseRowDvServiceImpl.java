package kr.wise.advisor.prepare.udefoutlier.daserowdv.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.UodcColDaseDvService;

import kr.wise.advisor.prepare.udefoutlier.daseimp.service.UodcDaseImpService;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImp;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImpCol;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImpColMapper;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImpMapper;
import kr.wise.advisor.prepare.udefoutlier.daserowdv.service.UodcDaseRowDvService;
import kr.wise.advisor.prepare.udefoutlier.daserowdv.service.WadUodcDaseRowDv;
import kr.wise.advisor.prepare.udefoutlier.daserowdv.service.WadUodcDaseRowDvMapper;
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
import kr.wise.commons.util.UtilString;


@Service("UodcDaseRowDvService")
public class UodcDaseRowDvServiceImpl implements UodcDaseRowDvService  { 
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	
	@Inject 
	private WadUodMapper wadUodMapper;  
	
	@Inject 
	private WadUodCreCompMapper WadUodCreCompMapper;
	
	@Inject  
	private WadUodcDaseRowDvMapper daseRowDvMapper ;  
	 
	@Inject  
	private WadUodcAnaDaseMapper wadUodcAnaDaseMapper ;
	
	@Inject  
	private WadUodcAnaDaseColMapper wadUodcAnaDaseColMapper ;    
	
	@Inject
    private EgovIdGnrService uodIdGnrService;
	
	@Inject
	private PythonScriptService pythonScriptService;  

	@Override
	public WadUodcDaseRowDv getUodcDaseRowDvDetail(WadUodcDaseRowDv search) {
		
		return daseRowDvMapper.selectDetailInfo(search); 
	}

	@Override
	public int regRowDaseDv(WadUodcDaseRowDv mstData) throws Exception {
		
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
		creVo.setCreCompNm("Subset(Row)");
		creVo.setCompId("SubsetRow");
		
		result = WadUodCreCompMapper.deleteByPrimaryKey(udfOtlDtcId, creCompId);
		
		result = WadUodCreCompMapper.insertSelective(creVo);
		//=======================================
							
		//기존 데이터 UPDATE
		result = daseRowDvMapper.updateByPrimaryKeySelective(mstData); 
		
		if(result == 0){ 
		
			mstData.setAnaDaseId(anaDaseId);
			
			//WAD_UODC_DASE_ROW_DV data import mst Insert
			result = daseRowDvMapper.insertSelective(mstData); 					
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
		result += wadUodcAnaDaseColMapper.insertAnaColDaseRowDv(anaVo); 
		
		
		// 분석데이터셋ID  UPDATE 		
		result = daseRowDvMapper.updateDaseRowDvAnaDaseId(mstData);      
		//====================================
		
		return result; 
	}  
	

	
	
	
}
