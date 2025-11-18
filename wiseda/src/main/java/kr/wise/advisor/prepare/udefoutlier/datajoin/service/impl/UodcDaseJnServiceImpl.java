package kr.wise.advisor.prepare.udefoutlier.datajoin.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import kr.wise.advisor.prepare.udefoutlier.datafilter.service.WadUodcDaseCndDv;
import kr.wise.advisor.prepare.udefoutlier.datajoin.service.UodcDaseJnService;
import kr.wise.advisor.prepare.udefoutlier.datajoin.service.WadUodcDaseJn;
import kr.wise.advisor.prepare.udefoutlier.datajoin.service.WadUodcDaseJnCol;
import kr.wise.advisor.prepare.udefoutlier.datajoin.service.WadUodcDaseJnColMapper;
import kr.wise.advisor.prepare.udefoutlier.datajoin.service.WadUodcDaseJnMapper;
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

@Service("UodcDaseJnService")
public class UodcDaseJnServiceImpl implements UodcDaseJnService {
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
	private WadUodcDaseJnMapper wadUodcDaseJnMapper;
	
	@Inject
	private WadUodcDaseJnColMapper wadUodcDaseJnColMapper;
	
	@Inject
	private PythonScriptService pythonScriptService;  
	
	
	@Override
	public WadUodcDaseJn getUodcDaseJnDetail(WadUodcDaseJn search) {
		// TODO Auto-generated method stub
		return wadUodcDaseJnMapper.selectDetailInfo(search);
	}

	@Override
	public List<WadUodcDaseJnCol> getUodcJoinColList(WadUodcDaseJn search) {
		// TODO Auto-generated method stub
		return wadUodcDaseJnColMapper.selectJnColList(search);
	}

	@Override
	public List<WadUodcAnaDaseCol> getSubSelBox(WadUodcDaseJnCol search) {
		// TODO Auto-generated method stub
		WadUodcDaseCndDv param = new WadUodcDaseCndDv();
		param.setAnaDaseId(search.getAnaDaseId());
		return wadUodcAnaDaseColMapper.selectAnaDaseColList(param);
	}

	@Override
	public int delDaseJnCol(List<WadUodcDaseJnCol> list, WadUodcDaseJn mstData) {
		// TODO Auto-generated method stub
		int result = 0;
		
		logger.debug("=============================================");
		logger.debug("\n getUdfOtlDtcId:" + mstData.getUdfOtlDtcId());
		logger.debug("\n getCreCompId:" + mstData.getCreCompId());

		WadUod uodvo = new WadUod();
		
		String udfOtlDtcId = mstData.getUdfOtlDtcId();
		String creCompId   = mstData.getCreCompId();
		
		uodvo.setUdfOtlDtcId(udfOtlDtcId);
		
		for(WadUodcDaseJnCol cndVo : list){
			//WAD_UODC_DASE_JN_COL  delete
			cndVo.setUdfOtlDtcId(udfOtlDtcId);
			cndVo.setCreCompId(creCompId);
			logger.debug("\n getJnColSno >>>> " + cndVo.getJnColSno());
			result += wadUodcDaseJnColMapper.deleteJnCol(cndVo);
			logger.debug("\n result >>>> " + result);
		}

		return result;
	}

	@Override
	public int regUodcJoin(List<WadUodcDaseJnCol> list, WadUodcDaseJn mstData) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();  
		String userid = user.getUniqId();
		
		mstData.setWritUserId(userid);
		
		String anaDaseId = uodIdGnrService.getNextStringId();
		logger.debug("\n anaDaseId >>>>> " + anaDaseId);
		
		WadUod uodvo = new WadUod();
		
		String udfOtlDtcId = mstData.getUdfOtlDtcId();
		String udfOtlDtcNm = mstData.getUdfOtlDtcNm();  
		String creCompId   = mstData.getCreCompId();
		String anaDaseNm   = mstData.getAnaDaseNm();
		
		logger.debug("\n anaDaseNm >>>> " + anaDaseNm);
		
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
		creVo.setCreCompNm("Join");
		creVo.setCompId("Join");
		
		result = WadUodCreCompMapper.deleteByPrimaryKey(udfOtlDtcId, creCompId);
		
		result = WadUodCreCompMapper.insertSelective(creVo);
		//=======================================
						

		/*for(WadUodcDaseJnCol cndVo : list){
			//WAD_UODC_DASE_JN_COL  delete
			cndVo.setUdfOtlDtcId(udfOtlDtcId);
			cndVo.setCreCompId(creCompId);
			result += wadUodcDaseJnColMapper.deleteJnCol(cndVo);
		}*/
		result = wadUodcDaseJnColMapper.deleteByPrimaryKey(udfOtlDtcId, creCompId);
		
		for(int i=0; i<list.size(); i++) {
			list.get(i).setUdfOtlDtcId(udfOtlDtcId);
			list.get(i).setCreCompId(creCompId);
			list.get(i).setJnColSno(wadUodcDaseJnColMapper.getJnColSno(list.get(i)));
			list.get(i).setWritUserId(userid);
			result = wadUodcDaseJnColMapper.insertWadUodcDaseJnCol(list.get(i));
		}
		
		//기존 데이터 UPDATE
		result = wadUodcDaseJnMapper.updateDaseJn(mstData);
		
		if(result == 0){
			//WAD_UODC_DASE_JN data import mst Insert
			result = wadUodcDaseJnMapper.insertDaseJn(mstData); 					
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
		
		logger.debug("\n irtn >>>> " + iRtn);
		logger.debug("\n result >>>>>> " + result);
		
		//WAD_UODC_ANA_DASE_COL DELETE
		result += wadUodcAnaDaseColMapper.deleteFkUodcAnaDaseCol(anaVo);  
		
		//////////////////////////////////////
		//ANA_DASE_COL에 들어갈 컬럼 조회
		List<WadUodcDaseJnCol> data = new ArrayList<WadUodcDaseJnCol>();
		data.addAll(wadUodcDaseJnColMapper.getJnColPnm(list.get(0)));

		WadUodcAnaDaseCol anaColVo = new WadUodcAnaDaseCol();
		anaColVo.setAnaDaseId(anaDaseId);
		anaColVo.setAnaDaseNm(anaDaseNm);
		anaColVo.setUdfOtlDtcId(udfOtlDtcId);
		anaColVo.setCreCompId(creCompId);
		anaColVo.setWritUserId(userid);
		
		for(int i=0; i<data.size(); i++) {
			anaColVo.setColPnm(data.get(i).getLeftColPnm());
			anaColVo.setColLnm(data.get(i).getColLnm());
			anaColVo.setAnaVarId(data.get(i).getAnaVarId());
			anaColVo.setAnaColSno(String.valueOf(i+1));
			anaColVo.setObjVers(1);
			anaColVo.setRegTypCd("C");
			
			result += wadUodcAnaDaseColMapper.insertAnaColJnCol(anaColVo);
		}
		
		//////////////////////////////////////
		
		// 분석데이터셋ID  UPDATE 		
		result = wadUodcDaseJnColMapper.updateDaseJnAnaDaseId(mstData);
		result += wadUodcDaseJnColMapper.updateDaseJnAnaDaseColId(mstData);
		
		logger.debug("\n result >>>>>>> ", result);
					
		return result;
	}

	@Override 
	public List<WadUodcDaseJnCol> getUodcDaseJnColLstForScrt(WadUodcDaseJn search) {
		
		return wadUodcDaseJnColMapper.selectUodcDaseJnColLstForScrt(search);
	}

}
