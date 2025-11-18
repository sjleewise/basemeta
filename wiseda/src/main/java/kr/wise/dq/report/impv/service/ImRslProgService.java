package kr.wise.dq.report.impv.service;

import java.util.List;

import kr.wise.dq.report.profile.service.ProgChartVO;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ImRslProgService.java
 * 3. Package  : kr.wise.dq.report.impv.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 17. 오후 5:01:12
 * </PRE>
 */ 
public interface ImRslProgService {

	List<ProgChartVO> getImRslProgQuality(ProgChartVO search);
	

}
