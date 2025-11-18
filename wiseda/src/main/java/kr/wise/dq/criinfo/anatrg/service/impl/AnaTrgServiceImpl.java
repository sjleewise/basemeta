/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ColProfileServiceImpl.java
 * 2. Package : kr.wise.dq.criinfo.profile.service.impl
 * 3. Comment : 
 * 4. 작성자  : hwang
 * 5. 작성일  : 2014. 3. 24. 오후 1:35:18
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hwang : 2014. 3. 24. :            : 신규 개발.
 */
package kr.wise.dq.criinfo.anatrg.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgMapper;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.dq.codemng.service.WaaCdRule;
import kr.wise.dq.codemng.service.WaaCdRuleMapper;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgColVO;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgMapper;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgService;
import kr.wise.dq.criinfo.anatrg.service.AnaTrgTblVO;
import kr.wise.dq.criinfo.anatrg.service.WaaColRuleRel;
import kr.wise.dq.criinfo.anatrg.service.WaaColRuleRelMapper;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSchMapper;
import kr.wise.dq.criinfo.anatrg.service.WaaExpTbl;
import kr.wise.dq.criinfo.anatrg.service.WaaExpTblMapper;
import kr.wise.dq.vrfcrule.service.VrfcruleErrMapper;
import kr.wise.dq.vrfcrule.service.VrfcruleMapper;
import kr.wise.dq.vrfcrule.service.VrfcruleVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ColProfileServiceImpl.java
 * 3. Package  : kr.wise.dq.criinfo.profile.service.impl
 * 4. Comment  : 
 * 5. 작성자   : hwang
 * 6. 작성일   : 2014. 3. 24. 오후 1:35:18
 * </PRE>
 */
@Service("anaTrgService")
public class AnaTrgServiceImpl implements AnaTrgService{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private AnaTrgMapper mapper;
	
	@Inject
	private WaaDbConnTrgMapper wMapper;
	
	@Inject
	private WaaDbSchMapper sMapper;
	
	@Inject
	private WaaExpTblMapper expMapper;
	
	@Inject
	private WaaColRuleRelMapper relMapper;  
	
	@Inject
	private VrfcruleMapper vrfcMapper;
	
	@Inject
	private WaaCdRuleMapper waaCdRuleMapper;
	
	@Inject
	private VrfcruleErrMapper vrfcruleErrMapper; 
	
	@Inject
    private EgovIdGnrService objectIdGnrService;	
	
	//진단대상 조회 팝업
	public List<AnaTrgTblVO> getAnaTrgTblLst(AnaTrgTblVO search) {
		return mapper.selectAnaTrgTblLst(search);
	}
	
	public List<AnaTrgColVO> getAnaTrgColLst(AnaTrgTblVO search) {
		return mapper.selectAnaTrgColLst(search);
	}
	
	//프로파일 진단대상 조회
	public List<AnaTrgTblVO> getPrfTblLst(AnaTrgTblVO search) {
		return mapper.selectPrfTblLst(search);
	}
	
	public List<AnaTrgColVO> getPrfColLst(AnaTrgTblVO search) {
		return mapper.selectPrfColLst(search);
	}
	
	/* 진단대상 테이블 상세 조회*/
	public AnaTrgTblVO getAnaTrgTblDtl(AnaTrgTblVO search) {
		return mapper.selectAnaTrgTblDtl(search);
	}
	
	/* 진단대상 컬럼 상세 조회*/
	public AnaTrgTblVO getAnaTrgColDtl(AnaTrgTblVO search) {
		return mapper.selectAnaTrgColDtl(search);
	}

	@Override
	public List<WaaDbConnTrgVO> selectList(WaaDbConnTrgVO search) {
		return wMapper.selectListDqDbms(search);
		
	}

	@Override
	public WaaDbConnTrgVO selectAnaTrgDbmsDetail(String dbConnTrgId) {
		logger.debug("Impl에서 DBMS ID : {}", dbConnTrgId);
		
		return wMapper.selectByPrimaryKeyDqDbms(dbConnTrgId);
		
	}

	@Override
	public List<WaaDbConnTrgVO> selectHstList(String dbConnTrgId) {
		return wMapper.selectHstListDqDbms(dbConnTrgId);

	}
	
	//DBMS 스키마정보 곽효신
	@Override
	public List<WaaDbConnTrgVO> getDbSchList(String dbConnTrgId) {
		return wMapper.getDbSchList(dbConnTrgId);
		
	}

	@Override
	public List<WaaDbSch> selectDbSchList(WaaDbSch search) {
		return sMapper.selectList(search);
		
	}

	@Override
	public WaaDbSch selectDbSchDetail(String dbSchId) {
		return sMapper.selectByPrimaryKey(dbSchId);
		
	}

	/** meta */
	@Override
	public List<AnaTrgTblVO> getPrfTblLstNotRedline(AnaTrgTblVO search) {
		return mapper.selectPrfTblLstNotRedline(search);
	}

	/** meta */
	@Override
	public List<AnaTrgColVO> getPrfColLstNotRedline(AnaTrgTblVO search) {
		return mapper.selectPrfColLstNotRedline(search);
	}
	
	@Override
	public List<WaaExpTbl> selectTrgTbl(WaaExpTbl vo) { 
		
		return expMapper.selectTrgTbl(vo);   
	}

	@Override
	public int regTrgTblList(ArrayList<WaaExpTbl> list) throws Exception {
		
		int result = 0;
		
		for(WaaExpTbl saveVo : list){
		
			result = expMapper.updateByPrimaryKeySelective(saveVo); 
			
			if(result == 0){
								
				result += expMapper.insertSelective(saveVo);
			}
		}
		
		return result;
	}

	@Override
	public List<WaaColRuleRel> getCheckRuleTbl(WaaExpTbl vo) {
		
		return relMapper.selectCheckRuleTbl(vo);   
	}

	@Override
	public int regChkRuleAply(ArrayList<WaaColRuleRel> list) throws Exception {
		
		int result = 0;
		
		for(WaaColRuleRel saveVo : list){
		
			result = relMapper.updateByPrimaryKeySelective(saveVo); 
			
			if(result == 0){
				
				String ruleRelId = objectIdGnrService.getNextStringId();  
				
				saveVo.setRuleRelId(ruleRelId); 
				
				
				result += relMapper.insertSelective(saveVo);
			}
		}
		
		//검증룰 매칭 되지 않으 데이터 삭제 
		relMapper.deleteVrfcNull();
		
		return result;
	}

	@Override
	public int delRuleAply(ArrayList<WaaColRuleRel> list) throws Exception {
		
		int result = 0;
		
		for(WaaColRuleRel delVo : list){
			result += relMapper.deleteByPrimaryKeySelective(delVo);
			relMapper.deleteShdJobByPrimaryKeySelective(delVo);
		}
		
		return result;
	}
	
	@Override
	public List<VrfcruleVO> getVrfcRuleLst(VrfcruleVO vo) {
		// TODO Auto-generated method stub
		return vrfcMapper.selectVrfcList(vo);
	}

	@Override
	public WaaDbConnTrgVO getDbmsInfo(WaaCdRule search) { 

		return wMapper.selectDbmsInfo(search);  
	}

	@Override
	public List<WaaCdRule> getWaaCdRule(WaaCdRule search) {
	
		return waaCdRuleMapper.selectCodeMngList(search);
	}

	@Override
	public List<WaaColRuleRel> getItmAnaExec(WaaColRuleRel vo) {
		
		return vrfcruleErrMapper.selectItmAnaExecList(vo);
	}

	/* 진단대상 테이블 상세 조회*/
	public AnaTrgTblVO getAnaTrgTblReacDtl(AnaTrgTblVO search) {
		return mapper.selectAnaTrgTblReacDtl(search);
	}

	@Override
	public List<AnaTrgColVO> getPrfColLstCheck(AnaTrgTblVO search) {
		return mapper.selectPrfColLstCheck(search);
	}

	//프로파일 진단대상 조회
	public List<AnaTrgTblVO> getPrfTblLstAna(AnaTrgTblVO search) {
		return mapper.selectPrfTblLstAna(search);
	}
	
	@Override
	public List<WaaExpTbl> selectExpRuleTbl(WaaExpTbl vo) {
		// TODO Auto-generated method stub
		return expMapper.selectExpRuleTbl(vo);   
	}
	
	@Override
	public int regExpRuleTbl(ArrayList<WaaExpTbl> list) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		
		for(WaaExpTbl saveVo : list){
			//String checkId = saveVo.getExpRuleId();
			result = expMapper.updateByPrimaryKeySelective2(saveVo); 
			
			if(result == 0){
				
				String expRuleId = objectIdGnrService.getNextStringId();  
				//String dbConnTrgId = saveVo.getD
				saveVo.setExpRuleId(expRuleId); 
				result += expMapper.insertSelective2(saveVo);
			}		
		
		}
		
		
		return result;
	}
	
	@Override
	public int delExpRule(ArrayList<WaaExpTbl> list) throws Exception {
	int result = 0;
		
		for(WaaExpTbl delVo : list){
			result+=expMapper.deleteExpRule(delVo);
		}
		
		return result;
	}

	@Override
	public WaaDbConnTrgVO getByDbms(WaaDbConnTrgVO search) {
		return wMapper.selectByDbms(search);
	}
	
	
}
