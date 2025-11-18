package kr.wise.advisor.prepare.udefoutlier.boxplot.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.advisor.prepare.udefoutlier.boxplot.service.UodcBoxplotService;
import kr.wise.advisor.prepare.udefoutlier.boxplot.service.WadUodcBoxplot;
import kr.wise.advisor.prepare.udefoutlier.boxplot.service.WadUodcBoxplotCol;
import kr.wise.advisor.prepare.udefoutlier.boxplot.service.WadUodcBoxplotColMapper;
import kr.wise.advisor.prepare.udefoutlier.boxplot.service.WadUodcBoxplotMapper;
import kr.wise.advisor.prepare.udefoutlier.datacleansing.service.WadUodcDataClnCol;
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

@Service("UodcBoxplotService")
public class UodcBoxplotServiceImpl implements UodcBoxplotService {
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
	private WadUodcBoxplotMapper wadUodcBoxplotMapper;
	
	@Inject
	private WadUodcBoxplotColMapper wadUodcBoxplotColMapper;
	
	@Inject
	private PythonScriptService pythonScriptService;  
	
	
	@Override
	public WadUodcBoxplot getUodcBoxplotDetail(WadUodcBoxplot search) {
		// TODO Auto-generated method stub
		return wadUodcBoxplotMapper.selectDetailInfo(search);
	}

	@Override
	public List<WadUodcBoxplotCol> getUodcBoxplotColList(WadUodcBoxplot search) {
		// TODO Auto-generated method stub
		return wadUodcBoxplotColMapper.selectBoxplotColList(search);
	}

	@Override
	public int regUodcBoxplot(List<WadUodcBoxplotCol> list, WadUodcBoxplot mstData) throws Exception {
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
		uodvo.setWritUserId(userid);
		
		uodvo.setMdlJsonInf(uodvo.removeComma(mstData.getMdlJsonInf()));
		
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
		creVo.setCreCompNm("Boxplot");
		creVo.setCompId("ODBP");
		
		result = WadUodCreCompMapper.deleteByPrimaryKey(udfOtlDtcId, creCompId);
		
		result = WadUodCreCompMapper.insertSelective(creVo);
		//=======================================
				

		WadUodcBoxplotCol bpVo = new WadUodcBoxplotCol();
		//WadUodcBoxplotCol  delete
		bpVo.setUdfOtlDtcId(udfOtlDtcId);
		bpVo.setCreCompId(creCompId);
		result = wadUodcBoxplotColMapper.deleteBoxplotCol(bpVo);
		
		for(int i=0; i<list.size(); i++) {
			list.get(i).setUdfOtlDtcId(udfOtlDtcId);
			list.get(i).setCreCompId(creCompId);
			list.get(i).setWritUserId(userid);
			list.get(i).setObjVers(1);
			list.get(i).setRegTypCd("C");
			result = wadUodcBoxplotColMapper.insertWadUodcBoxplotCol(list.get(i));
		}
		
		//기존 데이터 UPDATE
		result = wadUodcBoxplotMapper.updateBoxplot(mstData);
		
		if(result == 0){
			//WAD_UODC_DASE_JN data import mst Insert
			result = wadUodcBoxplotMapper.insertBoxplot(mstData); 					
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
		result = wadUodcBoxplotColMapper.updateBoxplotAnaDaseId(mstData);
		result = wadUodcBoxplotColMapper.updateBoxplotAnaDaseColId(mstData);
		
		return result;
	}

	@Override
	public List<WadUodcBoxplotCol> getUodcBoxplotColListForScrt(WadUodcBoxplot search) {
 
		return wadUodcBoxplotColMapper.selectUodcBoxplotColListForScrt(search);
	}

}
