package kr.wise.dq.bizrule.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : WamBrMstrMapper.java
 * 3. Package  : kr.wise.dq.bizrule.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 5. 오후 3:25:53
 * </PRE>
 */ 
@Mapper
public interface WamBrMstrMapper {

	List<WamBrMstr> selectBizrule(WamBrMstr record);

	/** @param search meta
	 * @return */
	List<WamBrMstr> selectErrBizrule(WamBrMstr search);

	WamBrMstr selectBizruleDtl(WamBrMstr search);

	/** @param search
	/** @return meta */
	List<WamBrMstr> selectBizrulePop(WamBrMstr search);

	/**
	 * @param search
	 * @return
	 */
	List<WamBrMstr> selectVrtDbList(WamBrMstr search);

	/**
	 * @param search
	 * @return
	 */
	List<WamBrMstr> selectVrtTblList(WamBrMstr search);

	/**
	 * @param search
	 * @return
	 */
	List<WamBrMstr> selectVrtColList(WamBrMstr search);

	/**
	 * @param brId
	 * @return
	 */
	List<WamBrMstr> selectBizRuleHstLst(String brId);



	
	/** @param search
	/** @return meta */
	/** 업무규칙 품질추이 조회 */
	List<WamBrMstr> selectBizruleProgQuality(WamBrMstr search);

	/** @param search
	/** @return meta */
	/** 업무영역별 품질추이 조회 */
	List<WamBrMstr> selectBizareaProgQuality(WamBrMstr search);

	/** @param search
	/** @return meta */
	/** DQI별 품질추이 조회 */
	List<WamBrMstr> selectDqiProgQuality(WamBrMstr search);

	/** @param search
	/** @return meta */
	/** CTQ별 품질추이 조회 */
	List<WamBrMstr> selectCtqProgQuality(WamBrMstr search);
	

	List<WamBrMstr> selectBizruleXlsxList(WamBrMstr search);  

	/** @param search
	/** @return meta */
	/** DQI별 품질추이 조회 */
	List<WamBrMstr> selectDqiQuality(WamBrMstr search);

}