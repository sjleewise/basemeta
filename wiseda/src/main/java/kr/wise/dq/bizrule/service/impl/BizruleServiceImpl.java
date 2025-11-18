/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : BizruleServiceImple.java
 * 2. Package : kr.wise.dq.bizrule.service.impl
 * 3. Comment :
 * 4. 작성자  : hwang
 * 5. 작성일  : 2014. 4. 29. 오전 9:55:25
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hwang : 2014. 4. 29. :            : 신규 개발.
 */
package kr.wise.dq.bizrule.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.dq.bizrule.service.BizruleService;
import kr.wise.dq.bizrule.service.WamBrMstr;
import kr.wise.dq.bizrule.service.WamBrMstrMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : BizruleServiceImple.java
 * 3. Package  : kr.wise.dq.bizrule.service.impl
 * 4. Comment  :
 * 5. 작성자   : hwang
 * 6. 작성일   : 2014. 4. 29. 오전 9:55:25
 * </PRE>
 */
@Service("bizruleService")
public class BizruleServiceImpl implements BizruleService{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamBrMstrMapper mapper;

	@Inject
	private EgovIdGnrService objectIdGnrService;

	@Override
	public List<WamBrMstr> getBizruleList(WamBrMstr search) {
		return mapper.selectBizrule(search);
	}

	@Override
	public WamBrMstr getBizruleDtlList(WamBrMstr search) {
		return mapper.selectBizruleDtl(search);
	}

	/** meta */
	@Override
	public List<WamBrMstr> getBizruleListPop(WamBrMstr search) {
		return mapper.selectBizrulePop(search);
	}

	/** kchoi */
	@Override
	public List<WamBrMstr> getvrtDbList(WamBrMstr search) {
		return mapper.selectVrtDbList(search);
	}

	/** kchoi	 */
	@Override
	public List<WamBrMstr> getvrtTblList(WamBrMstr search) {
		return mapper.selectVrtTblList(search);
	}

	/** kchoi	 */
	@Override
	public List<WamBrMstr> getvrtColList(WamBrMstr search) {
		return mapper.selectVrtColList(search);
	}

	/** kchoi */
	@Override
	public List<WamBrMstr> getBizRuleHstLst(String brId) {
		return mapper.selectBizRuleHstLst(brId);
	}

	@Override
	public List<WamBrMstr> getBizruleXlsxList(WamBrMstr search) {  
		
		return mapper.selectBizruleXlsxList(search);    
	}






}
