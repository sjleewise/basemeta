package kr.wise.meta.ddltsf.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.ddl.service.WamDdlIdx;
import kr.wise.meta.ddl.service.WamDdlIdxCol;
import kr.wise.meta.ddl.service.WamDdlIdxColMapper;
import kr.wise.meta.ddltsf.service.DdlTsfTblService;
import kr.wise.meta.ddltsf.service.WamDdlTsfColMapper;
import kr.wise.meta.ddltsf.service.WamDdlTsfColObj;
import kr.wise.meta.ddltsf.service.WamDdlTsfObj;
import kr.wise.meta.ddltsf.service.WamDdlTsfTblMapper;
import kr.wise.meta.ddltsf.service.WaqDdlTsfIdx;
import kr.wise.meta.ddltsf.service.WaqDdlTsfIdxCol;
import kr.wise.meta.ddltsf.service.WaqDdlTsfIdxColMapper;
import kr.wise.meta.ddltsf.service.WaqDdlTsfIdxMapper;
import kr.wise.meta.ddltsf.service.WaqDdlTsfTbl;
import kr.wise.meta.ddltsf.service.WaqDdlTsfTblMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DdlTsfTblServiceImpl.java
 * 3. Package  : kr.wise.meta.ddltsf.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 24. 오후 6:10:43
 * </PRE>
 */ 
@Service("DdlTsfTblService")
public class DdlTsfTblServiceImpl implements DdlTsfTblService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	WamDdlTsfTblMapper ddlTsfTblMapper;
	
	@Inject
	WamDdlTsfColMapper ddlTsfColMapper;
	
	@Inject
	WamDdlIdxColMapper idxColMapper;
	
	@Inject
	WaqDdlTsfTblMapper waqDdlTsfTblMapper;
	
	@Inject
	WaqDdlTsfIdxMapper waqDdlTsfIdxMapper;
	
	@Inject
	WaqDdlTsfIdxColMapper waqDdlTsfIdxColMapper;
	
	@Override
	public List<WamDdlTsfObj> getList(WamDdlTsfObj record) {
		return ddlTsfTblMapper.selectList(record);
	}

	@Override
	public WamDdlTsfObj selectDdlTsfTblInfo(String ddlTsfTblId) {
		return ddlTsfTblMapper.selectByPrimaryKey(ddlTsfTblId);
	}
	
	
	@Override
	public List<WamDdlTsfColObj> selectDdlTsfTblColList(String ddlTsfTblId) {
		return ddlTsfColMapper.selectColList(ddlTsfTblId);
	}

	@Override
	public List<WamDdlTsfObj> selectDdlTsfTblChangeList(String ddlTsfTblId) {
		return ddlTsfTblMapper.selectDdlTsfTblChangeList(ddlTsfTblId);
	}


	@Override
	public WamDdlTsfColObj selectDdlTsfTblColInfo(String ddlColId) {
		return ddlTsfColMapper.selectByPrimaryKey(ddlColId);
	}

	@Override
	public List<WamDdlTsfColObj> selectDdlTsfTblColChange(String ddlColId) {
		return ddlTsfColMapper.selectDdlTsfTblColChange(ddlColId);
	}

	/** yeonho */
	@Override
	public List<WaqDdlTsfTbl> selectDdlTsfTblListForRqst(WaqDdlTsfTbl search) {
		return waqDdlTsfTblMapper.selectDdlTsfTblList(search);
	}

	/** yeonho */
	@Override
	public WaqDdlTsfIdx selectDdlTsfIdxInfo(String ddlIdxId) {
		return waqDdlTsfIdxMapper.selectDdlTsfIdxInfo(ddlIdxId);
	}

	/** yeonho */
	@Override
	public List<WaqDdlTsfIdxCol> getTsfIdxColList(WamDdlIdxCol search) {
		return waqDdlTsfIdxColMapper.selectTsfIdxColList(search);
	}

	/** yeonho */
	@Override
	public WaqDdlTsfIdxCol selectDdlTsfIdxColInfo(String ddlIdxColId) {
		return waqDdlTsfIdxColMapper.selectDdlTsfIdxColInfo(ddlIdxColId);
	}

	/** yeonho */
	@Override
	public List<WaqDdlTsfIdx> getTsfIdxChangeList(WamDdlIdx search) {
		return waqDdlTsfIdxMapper.selectTsfIdxChangeList(search);
	}

	/** yeonho */
	@Override
	public List<WaqDdlTsfIdxCol> getTsfIdxColChangeList(WamDdlIdxCol search) {
		return waqDdlTsfIdxColMapper.selectTsfIdxColChangeList(search);
	}
	
//	@Override
//	public List<WamDdlIdxCol> selectddlTsfTblIdxColList(String ddlTsfTblId) {
//		return idxColMapper.selectddlTsfTblIdxColList(ddlTsfTblId);
//	}
//
//	/** yeonho */
//	@Override
//	public List<WamDdlTsfObj> getDdlTblRqstList(WamDdlTsfObj search) {
//		return ddlTsfTblMapper.selectDdlTblRqstList(search);
//	}
//
//	/** yeonho */
//	@Override
//	public int saveDdlTblRqstPrc(ArrayList<WamDdlTsfObj> list) {
//		int result = 0;
//		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
//		String userId = user.getUniqId();
//		logger.debug("DdlTblRqstPrcList : {}", list);
//		
//		for (WamDdlTsfObj record : list) {
//			// 처리유형이 선택된경우(처리완료) 값 셋팅...
//			if ("1".equals(record.getPrcTypCd())) {
//
//				record.setPrcTypCd("APV");
//				record.setPrcDt("1");
//				record.setPrcDbaId(userId);
//			}else {
//				record.setPrcTypCd("NPC");
//				record.setPrcDt(null);
//				record.setPrcDbaId(null);
//			}
//			if("TBL".equals(record.getObjDcd())){
//				result += ddlTsfTblMapper.updateDdlTblRqstPrc(record);
//			} else if ("IDX".equals(record.getObjDcd())){
//				
//			}
//			
//		}
//		return result;
//	}

}
