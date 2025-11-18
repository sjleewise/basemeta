package kr.wise.advisor.prepare.udefoutlier.coldasedv.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.UodcColDaseDvService;
import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.WadUodcColDaseDv;
import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.WadUodcColDaseDvCol;
import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.WadUodcColDaseDvColMapper;
import kr.wise.advisor.prepare.udefoutlier.coldasedv.service.WadUodcColDaseDvMapper;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.UodcDaseImpService;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImp;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImpCol;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImpColMapper;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImpMapper;
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


@Service("UodcColDaseDvService")
public class UodcColDaseDvServiceImpl implements UodcColDaseDvService  {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	
	@Inject 
	private WadUodMapper wadUodMapper;  
	
	@Inject 
	private WadUodCreCompMapper WadUodCreCompMapper;
	
	@Inject  
	private WadUodcColDaseDvMapper colDaseDvMapper ;  
	
	@Inject  
	private WadUodcColDaseDvColMapper colDaseDvColMapper ;   
	
	@Inject  
	private WadUodcAnaDaseMapper wadUodcAnaDaseMapper ;
	
	@Inject  
	private WadUodcAnaDaseColMapper wadUodcAnaDaseColMapper ;    
	
	@Inject
    private EgovIdGnrService uodIdGnrService;  
	
	@Inject
	private PythonScriptService pythonScriptService; 
	

	
	@Override
	public WadUodcColDaseDv getUodcColDaseDvDetail(WadUodcColDaseDv search) {
		
		return colDaseDvMapper.selectDetailInfo(search); 
	}

	@Override
	public List<WadUodcColDaseDv> getUodcColDaseDvColList(WadUodcColDaseDv search) {
		
		return colDaseDvColMapper.selectUodcColDaseDvColList(search);
	}

	@Override
	public int regColDaseDvlist(List<WadUodcColDaseDvCol> list, WadUodcColDaseDv mstData) throws Exception {
		
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
		creVo.setCreCompNm("Subset(Col)");
		creVo.setCompId("SubsetCol");
		
		result = WadUodCreCompMapper.deleteByPrimaryKey(udfOtlDtcId, creCompId);
		
		result = WadUodCreCompMapper.insertSelective(creVo);
		//=======================================
				
		//기존 데이터 UPDATE
		result = colDaseDvMapper.updateByPrimaryKeySelective(mstData); 
		
		if(result == 0){ 
		
			//WAD_UODC_DASE_IMP data import mst Insert
			result = colDaseDvMapper.insertSelective(mstData); 					
		}
		
		//WAD_UODC_COL_DASE_DV_COL 기존 데이터 삭제 
		result = colDaseDvColMapper.deleteCreCompId(mstData);  
		
		for(WadUodcColDaseDvCol saveVo : list) {
			
			saveVo.setUdfOtlDtcId(udfOtlDtcId);
			saveVo.setCreCompId(creCompId);
			saveVo.setWritUserId(userid);
						
			if(UtilString.null2Blank(saveVo.getIbsCheck()).equals("1")) {
			
				//WAD_UODC_COL_DASE_DV_COL INSERT
				result += colDaseDvColMapper.insertSelective(saveVo); 
			}								
		}
		
		// 분석데이터셋 , 분석데이터셋컬럼 처리  
		WadUodcAnaDase anaVo = new WadUodcAnaDase(); 
		
		anaVo.setAnaDaseId(anaDaseId);
		anaVo.setAnaDaseNm(anaDaseNm);
		anaVo.setUdfOtlDtcId(udfOtlDtcId);
		anaVo.setCreCompId(creCompId);
		anaVo.setWritUserId(userid);
		
		
		int iRtn;
		
		iRtn = wadUodcAnaDaseMapper.updateFkUodcAnaDase(anaVo);
		
		if(iRtn == 0) {
			
			//WAD_UODC_ANA_DASE INSERT 
			result += wadUodcAnaDaseMapper.insertSelective(anaVo); 
		}
		
		//WAD_UODC_ANA_DASE_COL DELETE 
		result += wadUodcAnaDaseColMapper.deleteFkUodcAnaDaseCol(anaVo);  
		
		//WAD_UODC_ANA_DASE_COL INSERT 
		result += wadUodcAnaDaseColMapper.insertAnaColColDaseDv(anaVo); 
		
		
		// 분석데이터셋ID  UPDATE 		
		result = colDaseDvMapper.updateColDaseDvAnaDaseId(mstData);   
					
		return result;
	}

	@Override
	public List<WadUodcColDaseDvCol> getUodcColDaseDvColLstForScrt(WadUodcColDaseDv search) { 

		return colDaseDvColMapper.selectUodcColDaseDvColLstForScrt(search); 
	}
	
	
}
