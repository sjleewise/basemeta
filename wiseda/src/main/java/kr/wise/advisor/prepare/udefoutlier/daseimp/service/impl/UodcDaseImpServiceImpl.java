package kr.wise.advisor.prepare.udefoutlier.daseimp.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import kr.wise.advisor.prepare.udefoutlier.daseimp.service.UodcDaseImpService;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImp;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImpCol;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImpColMapper;
import kr.wise.advisor.prepare.udefoutlier.daseimp.service.WadUodcDaseImpMapper;
import kr.wise.advisor.prepare.udefoutlier.script.PythonScriptService;
import kr.wise.advisor.prepare.udefoutlier.service.WadUod;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodCreComp;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodMapper;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDase;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseCol;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseColMapper;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodcAnaDaseMapper;
import kr.wise.advisor.prepare.udefoutlier.service.WadUodCreCompMapper;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;


@Service("UodcDaseImpService")
public class UodcDaseImpServiceImpl implements UodcDaseImpService  {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject 
	private WadUodMapper wadUodMapper; 

	@Inject 
	private WadUodCreCompMapper WadUodCreCompMapper;
	
	@Inject  
	private WadUodcDaseImpMapper daseimpMapper ; 
	
	@Inject  
	private WadUodcDaseImpColMapper daseimpcolMapper ;
	
	@Inject  
	private WadUodcAnaDaseMapper wadUodcAnaDaseMapper ;
	
	@Inject  
	private WadUodcAnaDaseColMapper wadUodcAnaDaseColMapper ;    
	
	@Inject
    private EgovIdGnrService uodIdGnrService;  
	
	@Inject
	private PythonScriptService pythonScriptService; 
	

	

	@Override
	public List<WadUodcDaseImp> getUodcDaseImpList(WadUodcDaseImp search) {
		
		return daseimpMapper.selectDaseImpList(search);  
	}
	
	@Override
	public List<WadUodcDaseImpCol> getUodcDaseImpColList(WadUodcDaseImp search) {
		
		return daseimpMapper.selectDaseImpColList(search);  
	}
	
	@Override
	public List<WadUodcDaseImpCol> getUodcDaseImpHiveColList(WadUodcDaseImp search) {
		
		return daseimpMapper.selectDaseImpHiveColList(search);  
	}


	@Override 
	public int regDataImptlist(List<WadUodcDaseImpCol> list, WadUodcDaseImp mstData) throws Exception {
		
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
		creVo.setCreCompNm("Data Import");
		creVo.setCompId("DataImp");
		
		result = WadUodCreCompMapper.deleteByPrimaryKey(udfOtlDtcId, creCompId);
		
		result = WadUodCreCompMapper.insertSelective(creVo);
		//=======================================
		
		//기존 데이터 UPDATE
		result = daseimpMapper.updateByPrimaryKeySelective(mstData); 
		
		if(result == 0){ 
		
			//WAD_UODC_DASE_IMP data import mst Insert
			result = daseimpMapper.insertSelective(mstData); 					
		}
		
		//WAD_UODC_DASE_IMP_COL 기존 데이터 삭제 
		result = daseimpcolMapper.deleteCreCompId(mstData);
		
		for(WadUodcDaseImpCol saveVo : list) {
			
			saveVo.setUdfOtlDtcId(udfOtlDtcId);
			saveVo.setCreCompId(creCompId);
			saveVo.setWritUserId(userid);
						
			if(UtilString.null2Blank(saveVo.getIbsCheck()).equals("1")) {
			
				//WAD_UODC_DASE_IMP_COL INSERT
				result += daseimpcolMapper.insertSelective(saveVo); 
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
		result += wadUodcAnaDaseColMapper.insertAnaColDataImp(anaVo); 
		
		
		// 분석데이터셋ID  UPDATE 		
		result = daseimpMapper.updateDaseImpAnaDaseId(mstData); 
					
		return result;
	}
	
	@Override
	public WadUodcDaseImp getUodcDaseImpDataDcd(WadUodcDaseImp search) {
		return daseimpMapper.selectImpDataDcd(search);
	}

	@Override
	public WadUodcDaseImp getUodcDaseImpDetail(WadUodcDaseImp search) {
		return daseimpMapper.selectDetailInfo(search);
	}

	@Override
	public List<WadUodcDaseImpCol> gectDaseImpColForScrt(WadUodcDaseImp search) {
		
		return daseimpMapper.selectDaseImpColForScrt(search);
	}

	@Override
	public List<WadUodcDaseImpCol> getResultViewColNm(WadUodcDaseImpCol search) {
		// TODO Auto-generated method stub
		return daseimpcolMapper.selectDaseImpColNm(search);
	}

	
	
	
}
