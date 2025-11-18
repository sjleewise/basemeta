package kr.wise.advisor.prepare.udefoutlier.elev.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.advisor.prepare.udefoutlier.elev.service.UodcElevService;
import kr.wise.advisor.prepare.udefoutlier.elev.service.WadUodcElev;
import kr.wise.advisor.prepare.udefoutlier.elev.service.WadUodcElevMapper;
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

@Service("UodcElevService")
public class UodcElevServiceImpl implements UodcElevService {
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
	private WadUodcElevMapper wadUodcElevMapper;
	
	@Inject
	private PythonScriptService pythonScriptService;  
	

	@Override
	public WadUodcElev getUodcElevDetail(WadUodcElev search) {
		// TODO Auto-generated method stub
		return wadUodcElevMapper.selectDetailInfo(search);
	}

	@Override
	public List<WadUodcElev> getUodcElevColList(WadUodcElev search) {
		// TODO Auto-generated method stub
		return wadUodcElevMapper.selectElevColList(search);
	}

	@Override
	public int regUodcElev(List<WadUodcElev> list, WadUodcElev mstData) throws Exception {
		// TODO Auto-generated method stub
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
		creVo.setCreCompNm("Elliptic Envelope");
		creVo.setCompId("ODELEV");
		
		result = WadUodCreCompMapper.deleteByPrimaryKey(udfOtlDtcId, creCompId);
		
		result = WadUodCreCompMapper.insertSelective(creVo);
		//=======================================
				
		//기존 데이터 UPDATE
		result = wadUodcElevMapper.updateElev(mstData);
		
		if(result == 0){
			//WAD_UODC_DASE_JN data import mst Insert
			result = wadUodcElevMapper.insertElev(mstData); 					
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
		
		//////////////////////////////////////

		WadUodcAnaDaseCol anaColVo = new WadUodcAnaDaseCol();
		anaColVo.setAnaDaseId(anaDaseId);
		anaColVo.setAnaDaseNm(anaDaseNm);
		anaColVo.setUdfOtlDtcId(udfOtlDtcId);
		anaColVo.setCreCompId(creCompId);
		anaColVo.setWritUserId(userid);
		
		for(int i=0; i<list.size(); i++) {
			anaColVo.setColPnm(list.get(i).getColPnm());
			anaColVo.setAnaColSno(String.valueOf(i+1));
			anaColVo.setObjVers(1);
			anaColVo.setRegTypCd("C");
			
			result += wadUodcAnaDaseColMapper.insertAnaColBoxplotCol(anaColVo);
		}
		
		//////////////////////////////////////
		
		// 분석데이터셋ID  UPDATE 		
		result = wadUodcElevMapper.updateElevAnaDaseId(mstData);
		result = wadUodcElevMapper.updateElevAnaDaseColId(mstData);
		
		return result;
	}

	@Override
	public List<WadUodcElev> getUodcElevColLstForScrt(WadUodcElev search) {

		return wadUodcElevMapper.selectUodcElevColLstForScrt(search); 
	}

}
