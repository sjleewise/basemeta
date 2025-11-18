package kr.wise.portal.dashboard.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : BizruleProgCtrl.java
 * 3. Package  : kr.wise.dq.report.bizrule.web
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 6. 20. 오후 5:48:53
 * </PRE>
 */ 
@Mapper
public interface DqMainChartMapper {

	List<DqMainChartVO> getApproveChartList();

	List<DqMainChartVO> getAnalyzeChartList(WaaDbConnTrgVO vo);

	List<DqMainChartVO> getQualityChartList();
	
}
