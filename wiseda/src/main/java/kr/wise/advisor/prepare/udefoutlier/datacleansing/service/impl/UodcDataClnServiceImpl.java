package kr.wise.advisor.prepare.udefoutlier.datacleansing.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.advisor.prepare.udefoutlier.datacleansing.service.UodcDataClnService;
import kr.wise.advisor.prepare.udefoutlier.datacleansing.service.WadUodcDataCln;
import kr.wise.advisor.prepare.udefoutlier.datacleansing.service.WadUodcDataClnCol;
import kr.wise.advisor.prepare.udefoutlier.datacleansing.service.WadUodcDataClnColMapper;
import kr.wise.advisor.prepare.udefoutlier.datacleansing.service.WadUodcDataClnMapper;
import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDv;
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

@Service("UodcDataClnService")
public class UodcDataClnServiceImpl implements UodcDataClnService {
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
	private WadUodcDataClnMapper wadUodcDataClnMapper;
	
	@Inject
	private WadUodcDataClnColMapper wadUodcDataClnColMapper;
	
	@Inject
	private PythonScriptService pythonScriptService;  
	
	@Override
	public WadUodcDataCln getDataClnDetail(WadUodcDataCln search) {
		// TODO Auto-generated method stub
		return wadUodcDataClnMapper.selectDataClnDetail(search);
	}

	@Override
	public List<WadUodcAnaDaseCol> getUodcDataClnColList(WadUodcDataClnCol search) {
		// TODO Auto-generated method stub
		WadUodcDaseCndDv param = new WadUodcDaseCndDv();
		param.setAnaDaseId(search.getSrcAnaDaseId());
		param.setCreCompId(search.getCreCompId());
		return wadUodcDataClnColMapper.selectAnaDaseColList(param);
	}

	@Override
	public int regUodcDataCln(List<WadUodcDataClnCol> list, WadUodcDataCln mstData) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();  
		String userid = user.getUniqId();
		
		mstData.setWritUserId(userid);
		
		String anaDaseId = uodIdGnrService.getNextStringId();
		
		WadUod uodvo = new WadUod();
		
		String udfOtlDtcId  = mstData.getUdfOtlDtcId();
		String udfOtlDtcNm  = mstData.getUdfOtlDtcNm();  
		String creCompId    = mstData.getCreCompId();
		String anaDaseNm    = mstData.getAnaDaseNm();	
		String srcAnaDaseId = mstData.getSrcAnaDaseId(); 
		
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
		creVo.setCreCompNm("Cleansing");
		creVo.setCompId("Cleansing");
		
		result = WadUodCreCompMapper.deleteByPrimaryKey(udfOtlDtcId, creCompId);
		
		result = WadUodCreCompMapper.insertSelective(creVo);
		//=======================================
		
		WadUodcDataClnCol dccVo = new WadUodcDataClnCol();
		//WadUodcDataClnCol  delete
		dccVo.setUdfOtlDtcId(udfOtlDtcId);
		dccVo.setCreCompId(creCompId);
		result = wadUodcDataClnColMapper.deleteDataClnCol(dccVo);
		
		for(int i=0; i<list.size(); i++) {
			list.get(i).setUdfOtlDtcId(udfOtlDtcId);
			list.get(i).setCreCompId(creCompId);
			list.get(i).setWritUserId(userid); 
			list.get(i).setObjVers(1);
			list.get(i).setRegTypCd("C");
			//list.get(i).setColSno(wadUodcDataClnColMapper.getDataClnColSno(list.get(i)));
			result = wadUodcDataClnColMapper.insertWadUodcDataClnCol(list.get(i));
		}
		
		//기존 데이터 UPDATE
		result = wadUodcDataClnMapper.updateDataCln(mstData);
		
		if(result == 0){
			//WAD_UODC_DASE_JN data import mst Insert
			result = wadUodcDataClnMapper.insertDataCln(mstData); 					
		}

		// 분석데이터셋 , 분석데이터셋컬럼 처리  
		WadUodcAnaDase anaVo = new WadUodcAnaDase(); 
		
		anaVo.setAnaDaseId(anaDaseId);
		anaVo.setAnaDaseNm(anaDaseNm);
		anaVo.setUdfOtlDtcId(udfOtlDtcId);
		anaVo.setCreCompId(creCompId);
		anaVo.setWritUserId(userid);
		anaVo.setSrcAnaDaseId(srcAnaDaseId);
		
		
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
		result += wadUodcDataClnColMapper.updateDataClnAnaDaseId(mstData);
		result += wadUodcDataClnColMapper.updateDaseClnAnaDaseColId(mstData);
		
		return result;
	}

	@Override
	public List<WadUodcDataClnCol> getUodcDataClnColList2(WadUodcDataClnCol search) {
		// TODO Auto-generated method stub
		return wadUodcDataClnColMapper.selectClnColList(search);
	}

}
