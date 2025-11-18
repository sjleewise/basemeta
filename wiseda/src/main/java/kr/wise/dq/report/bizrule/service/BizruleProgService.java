package kr.wise.dq.report.bizrule.service;

import java.util.List;

import kr.wise.dq.bizrule.service.WamBrMstr;
import kr.wise.dq.report.profile.service.ProgChartVO;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : BizruleProgService.java
 * 3. Package  : kr.wise.dq.report.bizrule.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 16. 오후 1:38:21
 * </PRE>
 */ 
public interface BizruleProgService {

	List<WamBrMstr> getBizruleProgQuality(WamBrMstr search);

	List<WamBrMstr> getBizareaProgQuality(WamBrMstr search);

	List<WamBrMstr> getDqiProgQuality(WamBrMstr search);

	List<WamBrMstr> getCtqProgQuality(WamBrMstr search);

	List<ProgChartVO> getBizruleQuality(ProgChartVO search);

	List<WamBrMstr> getDqiQuality(WamBrMstr search);

}
