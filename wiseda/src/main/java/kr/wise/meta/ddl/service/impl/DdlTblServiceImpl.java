package kr.wise.meta.ddl.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.ddl.service.DdlTblService;
import kr.wise.meta.ddl.service.WamDdlCol;
import kr.wise.meta.ddl.service.WamDdlColMapper;
import kr.wise.meta.ddl.service.WamDdlGrtMapper;
import kr.wise.meta.ddl.service.WamDdlIdx;
import kr.wise.meta.ddl.service.WamDdlIdxCol;
import kr.wise.meta.ddl.service.WamDdlIdxColMapper;
import kr.wise.meta.ddl.service.WamDdlIdxMapper;
import kr.wise.meta.ddl.service.WamDdlPartMapper;
import kr.wise.meta.ddl.service.WamDdlRel;
import kr.wise.meta.ddl.service.WamDdlRelMapper;
import kr.wise.meta.ddl.service.WamDdlSeqMapper;
import kr.wise.meta.ddl.service.WamDdlTbl;
import kr.wise.meta.ddl.service.WamDdlTblMapper;
import kr.wise.meta.ddl.service.WamKpMngMapper;
import kr.wise.meta.ddl.service.WamTblMst;
import kr.wise.meta.ddl.service.WamTblMstMapper;
import kr.wise.meta.ddletc.service.WamDdlEtcMapper;
import kr.wise.meta.ddltsf.service.WamDdlTsfObj;
import kr.wise.meta.ddltsf.service.WamDdlTsfTblMapper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DdlTblServiceImpl.java
 * 3. Package  : kr.wise.meta.ddl.service.impl
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 4. 24. 오후 6:10:43
 * </PRE>
 */ 
@Service("DdlTblService")
public class DdlTblServiceImpl implements DdlTblService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WamDdlTblMapper ddlTblMapper;
	
	@Inject
	private WamDdlColMapper ddlColMapper;
	
	@Inject
	private WamDdlRelMapper ddlRelMapper;
	
	@Inject
	private WamDdlTsfTblMapper wamDdlTsfTblMapper;
	
	@Inject
	private WamDdlIdxMapper wamDdlIdxMapper;
	
	@Inject
	private WamDdlSeqMapper wamDdlSeqMapper;
	
	@Inject
	private WamDdlEtcMapper wamDdlEtcMapper;
	
	@Inject
	private WamDdlPartMapper wamDdlPartMapper;
	
	@Inject
	private WamDdlGrtMapper wamDdlGrtMapper;
	
	
	
	@Inject
	private WamDdlIdxColMapper idxColMapper;
	
	@Inject 
	private WamTblMstMapper wamTblMstMapper;
	
	@Inject 
	private WamKpMngMapper wamKpMngMapper;
	
	@Inject
    private EgovIdGnrService objectIdGnrService;
	
	@Override
	public List<WamDdlTbl> getList(WamDdlTbl record) {
		return ddlTblMapper.selectList(record);
	}

	@Override
	public WamDdlTbl selectDdlTblInfo(String ddlTblId, String rqstNo) {
		return ddlTblMapper.selectByPrimaryKey(ddlTblId, rqstNo);
	}

	@Override
	public List<WamDdlTbl> selectDdlTblChangeList(String ddlTblId) {
		return ddlTblMapper.selectDdlTblChange(ddlTblId);
	}

	@Override
	public List<WamDdlCol> selectDdlTblColList(WamDdlTbl record) {
		return ddlColMapper.selectColList(record);
	}
	
		@Override
	public List<WamDdlCol> selectDdlTblColGapList(WamDdlTbl record) {
		return ddlColMapper.selectColListGap(record);
	}

	@Override
	public WamDdlCol selectDdlTblColInfo(WamDdlCol record) {
		return ddlColMapper.selectByPrimaryKey(record);
	}

	@Override
	public List<WamDdlCol> selectDdlTblColChangeList(String ddlColId) {
		return ddlColMapper.selectDdlTblColChange(ddlColId);
	}

	@Override
	public List<WamDdlIdxCol> selectDdlTblIdxColList(WamDdlIdxCol search) {
		return idxColMapper.selectDdlTblIdxColList(search);
	}

	/** meta */
	@Override
	public List<WamDdlTbl> getDdlTblRqstList(WamDdlTbl search) {
		return ddlTblMapper.selectDdlTblRqstList(search);
	}

	/** meta */
	@Override
	public int saveDdlTblRqstPrc(ArrayList<WamDdlTbl> list) {
		int result = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String userId = user.getUniqId();
		logger.debug("DdlTblRqstPrcList : {}", list);
		
		for (WamDdlTbl record : list) {
			// 처리유형이 선택된경우(처리완료) 값 셋팅...
			if ("1".equals(record.getPrcTypCd())) {

				record.setPrcTypCd("APV");
				record.setPrcDt("1");
				record.setPrcDbaId(userId);
			}else {
				record.setPrcTypCd("NPC");
				record.setPrcDt(null);
				record.setPrcDbaId(null);
			}
			
			logger.debug("prcDt >>> " + record.getExpDtm());
			
			if("TBL".equals(record.getObjDcd())){
				result += ddlTblMapper.updateDdlTblRqstPrc(record);
			} else if ("IDX".equals(record.getObjDcd())){
				result += wamDdlIdxMapper.updateDdlIdxRqstPrc(record);
			} else if ("SEQ".equals(record.getObjDcd())){
				result += wamDdlSeqMapper.updateDdlSeqRqstPrc(record);
			} else if ("ETC".equals(record.getObjDcd())){
				result += wamDdlEtcMapper.updateDdlEtcRqstPrc(record);
			} else if ("DDP".equals(record.getObjDcd())){
				result += wamDdlPartMapper.updateDdlPartRqstPrc(record);
			} else if ("GRT".equals(record.getObjDcd())){
				result += wamDdlGrtMapper.updateDdlGrtRqstPrc(record);
			}
			
		}
		return result;
	}

	/** meta */
	@Override
	public List<WamDdlTsfObj> getDdlTblTsfRqstList(WamDdlTsfObj search) {
		return wamDdlTsfTblMapper.selectDdlTblTsfRqstList(search);
	}

	/** meta */
	@Override
	public int saveDdlTblTsfRqstPrc(ArrayList<WamDdlTsfObj> list) {
		int result = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String userId = user.getUniqId();
		logger.debug("DdlTblTsfRqstPrcList : {}", list);
		
		for (WamDdlTsfObj record : list) {
			// 처리유형이 선택된경우(처리완료) 값 셋팅...
			if ("1".equals(record.getPrcTypCd())) {

				record.setPrcTypCd("APV");
				record.setPrcDt("1");
				record.setPrcDbaId(userId);
			}else {
				record.setPrcTypCd("NPC");
				record.setPrcDt(null);
				record.setPrcDbaId(null);
			}
			if("TBL".equals(record.getObjDcd())){
				result += wamDdlTsfTblMapper.updateDdlTblTsfRqstPrc(record);
			} else if ("IDX".equals(record.getObjDcd())){
				result += wamDdlTsfTblMapper.updateDdlIdxTsfRqstPrc(record);
			}
			
		}
		return result;
	}

	/** meta */
	@Override
	public List<WamDdlIdx> getIdxList(WamDdlIdx search) {
		return wamDdlIdxMapper.selectList(search);
	}

	/** meta */
	@Override
	public WamDdlIdx selectDdlIdxInfo(String ddlIdxId, String rqstNo) {
		return wamDdlIdxMapper.selectByDdlIdxId(ddlIdxId, rqstNo);
	}

	/** meta */
	@Override
	public List<WamDdlIdxCol> getIdxColList(WamDdlIdxCol search) {
		return idxColMapper.selectDdlIdxColList(search);
	}

	/** meta */
	@Override
	public WamDdlIdxCol selectDdlIdxColInfo(WamDdlIdxCol search) {
		return idxColMapper.selectDdlIdxColInfo(search);
	}

	/** meta */
	@Override
	public List<WamDdlIdx> getIdxChangeList(WamDdlIdx search) {
		return wamDdlIdxMapper.selectChangeList(search);
	}

	/** meta */
	@Override
	public List<WamDdlIdxCol> getIdxColChangeList(WamDdlIdxCol search) {
		return idxColMapper.selectColChangeList(search);
	}

	/** meta */
	@Override
	public List<WamDdlRel> selectDdlTblRelList(WamDdlTbl searchVO) {
		return ddlRelMapper.selectDdlRelList(searchVO);
	}

	/** meta */
	@Override
	public List<WamDdlRel> selectDdlTblRelChangeList(String ddlTblId) {
		return ddlRelMapper.selectDdlRelChangeList(ddlTblId);
	}

	@Override
	public List<WamDdlTbl> getPartitionTblList(WamDdlTbl search){
	    return ddlTblMapper.selectPartitionTblList(search);
	}
	
	// 파티션 여부 저장
	@Override
	public int savePartitionTblPrc(ArrayList<WamDdlTbl> list) {
		int result = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String userId = user.getUniqId();
		logger.debug("DdlPartitionList : {}", list);
		
		for (WamDdlTbl record : list) {
			// 처리유형이 선택된경우(처리완료) 값 셋팅...
			if ("1".equals(record.getPartTblYn())) {

				record.setPartTblYn("Y");
			}else {
				record.setPartTblYn("N");
			}

			result += ddlTblMapper.updatePartTblRqstPrc(record);
			result += ddlTblMapper.updatePartTblRqstPrcWam(record);
			
		}
		return result;
	}
	
	@Override
	public List<WamTblMst> getTblMstList(WamTblMst search){
	    return wamTblMstMapper.selectTblMstList(search);
	}

	@Override
	public WamTblMst selectTblMstDtl(String objId){
	  	return wamTblMstMapper.selectTblMstByKey(objId);
	}

	@Override
	public int saveTblMst(ArrayList<WamTblMst> list) throws Exception{
	
		logger.debug("list : {}",list);
		int result =0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		for(WamTblMst data : list){ 
		   data.setRqstUserId(userid);
		   data.setFrsRqstUserId(userid);
		   logger.debug("오브젝트id"+data.getObjId());
		   if(data.getObjId()==null||data.getObjId().equals("")){
			  logger.debug("인서트");
			  
			  logger.debug("중복체크");
			  WamTblMst temp = wamTblMstMapper.checkDup(data);
			  if(temp !=null){
			     return -1;
			  }
		      String id =  objectIdGnrService.getNextStringId();
		      data.setObjId(id);
		      result = wamTblMstMapper.regTblMst(data);
		   }else {
			  logger.debug("업데이트");
			  result =wamTblMstMapper.updateTblMst(data);
		   }
		}
		
		return result;
		
		
	}
	
	public int delTblMstList(ArrayList<WamTblMst> list){
	     int result  = 0;
	     logger.debug("list : {}",list);
	     	for(WamTblMst data : list){ 
	     	     result +=wamTblMstMapper.delTblMst(data);
	     	}
	     
	     return result;
	
	}

	@Override
	public List<WamDdlCol> selectDdlTblOneColChangeList(String ddlColId) {
		return ddlColMapper.selectDdlTblOneColChange(ddlColId);
	}
	
		@Override
	public List<WamDdlIdxCol> getIdxOneColChangeList(WamDdlIdxCol search) {
		return idxColMapper.selectOneColChangeList(search);
	}
		
//	@Override
//	public List<WamDdlTbl> getPciYnList(WamDdlTbl search){
//	    return ddlTblMapper.selectPciYnList(search);
//	}	
//	
//	// 개인정보분리보관여부 저장
//	@Override
//	public int savePciYnPrc(ArrayList<WamDdlTbl> list) {
//		int result = 0;
//		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
//		String userId = user.getUniqId();
//		logger.debug("DdlPciYnList : {}", list);
//		
//		for (WamDdlTbl record : list) {
//			// 처리유형이 선택된경우(처리완료) 값 셋팅...
//			if ("1".equals(record.getPciYn())) {
//
//				record.setPciYn("Y");
//			}else {
//				record.setPciYn("N");
//			}
//
//			result += ddlTblMapper.updatePciYnPrc(record);
//			result += ddlTblMapper.updatePciYnPrcWam(record);
//			result += wamDdlTsfTblMapper.updatePciYnPrc(record);
//			result += wamDdlTsfTblMapper.updatePciYnPrcWam(record);
//		}
//		return result;
//	}
	
		@Override
	public List<WamTblMst> getKpMngList(WamTblMst search){
	    return wamKpMngMapper.selectKpMngList(search);
	}

	@Override
	public WamTblMst selectKpMngDtl(String objId){
	  	return wamKpMngMapper.selectKpMngByKey(objId);
	}

	@Override
	public int saveKpMng(ArrayList<WamTblMst> list) throws Exception{
	
		logger.debug("list : {}",list);
		int result =0;
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		
		for(WamTblMst data : list){ 
		   data.setRqstUserId(userid);
		   data.setFrsRqstUserId(userid);
		   logger.debug("오브젝트id"+data.getObjId());
		   if(data.getObjId()==null||data.getObjId().equals("")){
			  logger.debug("인서트");
			  
			  logger.debug("중복체크");
			  WamTblMst temp = wamKpMngMapper.checkDup(data);
			  if(temp !=null){
			     return -1;
			  }
		      String id =  objectIdGnrService.getNextStringId();
		      data.setObjId(id);
		      result = wamKpMngMapper.regKpMng(data);
		   }else {
			  logger.debug("업데이트");
			  result =wamKpMngMapper.updateKpMng(data);
		   }
		}
		
		return result;
		
		
	}
	
	public int delKpMngList(ArrayList<WamTblMst> list){
	     int result  = 0;
	     logger.debug("list : {}",list);
	     	for(WamTblMst data : list){ 
	     	     result +=wamKpMngMapper.delKpMng(data);
	     	}
	     
	     return result;
	
	}

	@Override
	public List<WamDdlCol> getDdlIdxForColList(WamDdlTbl search) {
		// TODO Auto-generated method stub
		return ddlColMapper.selectDdlIdxForColList(search);    
	}
	
	//DDL 이관요청 조회	
	@Override
	public List<WamDdlTbl> selectDdlTsfTblListForRqst(WamDdlTbl search) { 
		 
		List<WamDdlTbl> list = null;
		
		String rqstDcd = UtilString.null2Blank(search.getRqstDcd());
		
		if(rqstDcd.equals("DD")){
			
			list = ddlTblMapper.selectDelDdlTsfTblListForRqst(search); 
		}else{
			list = ddlTblMapper.selectDdlTsfTblListForRqst(search);  
		}
		
		return list; 
	}

	@Override
	public List<WamDdlTbl> getDdlTblListForPart(WamDdlTbl record) {
		
		return ddlTblMapper.selectDdlTblListForPart(record);
	}
	
	@Override
	public List<WamDdlTbl> getDdlAllObjForGrtList(WamDdlTbl search) {

		return ddlTblMapper.selectDdlAllObjForGrtList(search);
	}

	@Override
	public List<WamDdlCol> selectDdlTblColListForWah(WamDdlTbl searchVO) {
		return ddlColMapper.selectColListForWah(searchVO);
	}

}
