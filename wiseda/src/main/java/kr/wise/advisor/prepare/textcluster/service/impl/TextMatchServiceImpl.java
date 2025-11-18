/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : TextMatchServiceImpl.java
 * 2. Package : kr.wise.advisor.prepare.textcluster.service
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 10. 8. 오후 5:02:04
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 10. 8. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.textcluster.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import kr.wise.advisor.prepare.dataset.service.DataSetService;
import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.dataset.service.WadDataSet;

import kr.wise.advisor.prepare.textcluster.service.TextMatchService;
import kr.wise.advisor.prepare.textcluster.service.WadClstData;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcCol;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcColMapper;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcTbl;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcTblMapper;
import kr.wise.advisor.prepare.textcluster.service.WadMtcData;
import kr.wise.advisor.prepare.textcluster.service.WadMtcDataMapper;
import kr.wise.advisor.prepare.textcluster.service.WadMtcTgtData;
import kr.wise.advisor.prepare.textcluster.service.WadMtcTgtDataMapper;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.helper.UserDetailHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : TextMatchServiceImpl.java
 * 3. Package  : kr.wise.advisor.prepare.textcluster.service
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 10. 8. 오후 5:02:04
 * </PRE>
 */
@Service("textMatchService")
public class TextMatchServiceImpl implements TextMatchService {

	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private MessageSource message;
	
	@Inject
	private EgovIdGnrService requestIdGnrService; 
	
	@Inject
	private WadDataMtcTblMapper tblmapper;

	@Inject
	private WadDataMtcColMapper colmapper;
	
	@Inject
	private WadMtcDataMapper srcmapper;
	
	@Inject
	private WadMtcTgtDataMapper tgtmapper;
	
	@Inject
	private DataSetService dataSetService;
	
	/** insomnia */
	public List<WadDataMtcTbl> getMatchTbl(WadDataMtcTbl search) {
		logger.debug("텍스트매칭 테이블 리스트 조회 : {}", search);
		
		return  tblmapper.selectList(search);
		
	}

	/** insomnia */
	public List<WadDataMtcCol> getMatchCol(WadDataMtcTbl search) {
		logger.debug("데이터매칭 컬럼 목록 by 매칭ID:{}", search.getMtcId());
		
		
		return colmapper.selectColList(search.getMtcId());
	}

	/** insomnia */
	public List<WadMtcData> getMatchData(WadDataMtcTbl search) {
		logger.debug("데이터매칭 결과 데이터 조회 by 매칭ID:{}", search.getMtcId());
		logger.debug("데이터매칭 결과 데이터 조회 by getiPageSize:{}", search.getiPageSize());
		
		//데이터매칭 컬럼목록을 가져온다.
		List<WadDataMtcCol> collist = colmapper.selectColList(search.getMtcId());
		int colsize = collist.size();
		
		List<String> srccolnms = new ArrayList<String>();
		List<String> tgtcolnms = new ArrayList<String>();
		
		for(int i=0;i<colsize;i++) {
			srccolnms.add("SRC_COL_NM"+(i+1));
			tgtcolnms.add("TGT_COL_NM"+(i+1));
		}
		
		return srcmapper.selectListbyId(search.getMtcId(), srccolnms, tgtcolnms, search.getiPageNo(), search.getiPageSize());
	}

	/** insomnia */
	public List<WadMtcData> getMatchtgtData(WadMtcData search) {
		
		logger.debug("데이터매칭 순위결과 조회 :{}", search);
		
		//데이터매칭 컬럼목록을 가져온다.
		List<WadDataMtcCol> collist = colmapper.selectColList(search.getMtcId());
		int colsize = collist.size();
		
		List<String> srccolnms = new ArrayList<String>();
		List<String> tgtcolnms = new ArrayList<String>();
		
		for(int i=0;i<colsize;i++) {
			srccolnms.add("SRC_COL_NM"+(i+1));
			tgtcolnms.add("TGT_COL_NM"+(i+1));
		}
		
		return srcmapper.selectListbySno(search, srccolnms, tgtcolnms);
	}


	/** insomnia */
	public List<WadMtcData> getClusterData(WadAnaVar search) {
		logger.debug("텍스트 클러스터링 결과 조회 by 컬럼ID:{}", search.getAnlVarId());
		
		return srcmapper.selectClustList(search.getAnlVarId());
	}
	
	/** insomnia */
	public List<WadClstData> getClstData(WadAnaVar search) {
		logger.debug("텍스트 클러스터링 결과 조회 by 컬럼ID:{}", search.getAnlVarId());
		
		return srcmapper.selectClstList(search.getAnlVarId());
	}

	@Override
	public int regTextMatchInf(WadDataMtcTbl tblVo, List<WadDataMtcCol> reglist) throws Exception { 
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		int result = 0;

		if(reglist != null) { 
			
			tblVo.setRqstUserId(userid);
			
			//MATCH 테이블 정보 저장
			result += saveWadMtcTbl(tblVo);  
			
			int iCnt = 1;
						
			for (WadDataMtcCol saveVo : (ArrayList<WadDataMtcCol>)reglist) {
				
				//mtcId 세팅 
				saveVo.setMtcId(tblVo.getMtcId()); 				
				saveVo.setMtcColSno(iCnt);
				
				saveVo.setRqstNo(tblVo.getRqstNo());
				saveVo.setRqstSno(iCnt);
				
				saveVo.setRqstUserId(userid);
				
				//MATCH 테이블 정보 저장
				result += saveWadMtcCol(saveVo);   
				
				iCnt++;
			}
		}
		
		return result; 
	}

	

	private int saveWadMtcTbl(WadDataMtcTbl saveVo) throws Exception {
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		saveVo.setRqstUserId(userid);
		
				
		if ("I".equals(tmpstatus)) {
			
			String rqstNo= requestIdGnrService.getNextStringId(); 
			
			String mtcId = rqstNo.replace("REQ", "MTC");
			
			logger.debug("\n mtcId:" + mtcId);
			
			saveVo.setRqstNo(rqstNo); 
			saveVo.setMtcId(mtcId); 
			
			result = tblmapper.insertSelective(saveVo);
	
		} else if ("U".equals(tmpstatus)){
			//업데이트
			
			result = tblmapper.updateByPrimaryKeySelective(saveVo);
			
		} else if ("D".equals(tmpstatus)) {
			
			String mtcId = saveVo.getMtcId();
			
			result = tblmapper.deleteByPrimaryKey(mtcId);

		}
		
		return result;
	}
	
	private int saveWadMtcCol(WadDataMtcCol saveVo) {
		
		int result  = 0;

		String tmpstatus = saveVo.getIbsStatus();
				
		if ("I".equals(tmpstatus)) {
	
			result = colmapper.insertSelective(saveVo);
	
		} else if ("U".equals(tmpstatus)){
			//업데이트
			LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
			String userid = user.getUniqId();
			saveVo.setRqstUserId(userid);
			
			result = colmapper.updateByPrimaryKeySelective(saveVo);
			
		} else if ("D".equals(tmpstatus)) {
			
			String mtcId = saveVo.getMtcId();
			
			result = colmapper.deleteByMtcId(mtcId);

		}
		
		return result;
	}

	@Override
	public List<WadDataMtcTbl> getTbl(WadDataMtcTbl search) {
		// TODO Auto-generated method stub
		return tblmapper.tblList(search);
	}
	
	@Override
	public List<WadDataMtcTbl> getHiveTbl(WadDataMtcTbl search) {
		// TODO Auto-generated method stub
		return tblmapper.hiveTblList(search);
	}

	@Override
	public int delMatchData(WadDataSet dataset, List<WadDataMtcCol> list) {
		// TODO Auto-generated method stub
		logger.debug("\n list.size() >>>> " + list.size());
		
		int result = 0;

		for(int i=0; i<list.size(); i++) {
			logger.debug("\n list.get(i).getMtcId() >>>> " + list.get(i).getMtcId());
			result += srcmapper.deleteByiD(list.get(i).getMtcId());
			result += tgtmapper.deleteByiD(list.get(i).getMtcId());
			result += colmapper.deleteByMtcId(list.get(i).getMtcId());
			result += tblmapper.deleteByPrimaryKey(list.get(i).getMtcId());
		}
		
		return result;
	}

	@Override
	public int regTextClust(List<WadClstData> list) {
		// TODO Auto-generated method stub
		int result = 0;
		
		for (WadClstData clstvo : list) {
			if(clstvo.getIbsCheck().equals("1"))
				clstvo.setClstYn("Y");
			else
				clstvo.setClstYn("N");
			
			result =+ tgtmapper.updateClstYn(clstvo);
			
		}

		return result;
	}

	@Override
	public int requestTextMatch(WadDataMtcTbl search) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int requestTextCluster(WadDataSet dataset, List<WadAnaVar> list) {
		// TODO Auto-generated method stub
		return 0;
	}

}
