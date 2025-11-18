/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : TextMatchService.java
 * 2. Package : kr.wise.advisor.prepare.textcluster.service
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 10. 8. 오후 5:01:48
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 10. 8. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.udefoutlier.service;

import java.util.List;

import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.dataset.service.WadDataSet;
import kr.wise.advisor.prepare.outlier.service.WadOtlData;
import kr.wise.dq.report.service.DataPatternVO;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : UdefOutlierService.java
 * 3. Package  : kr.wise.advisor.prepare.textcluster.service
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 10. 8. 오후 5:01:48
 * </PRE>
 */
public interface UdefOutlierService {

	
	List<WadUod> getUdefOutlierList(WadUod search);

	int delUdfOtlDtcList(List<WadUod> list);

	WadUod getWadUodSelectDetail(WadUod uodVo);

	List<WadUodcAnaDase> getAnaDaseId(WadUodcAnaDase search);

	WadUodCreComp getUodCompForScrt(WadUodCreComp tmpVo);

	int delComp(WadUodCreComp param);

	int updUod(WadUod data);

	List<WadUodcAnaDase> getAnaDaseIdSaveres(WadUodcAnaDase search);     

	List<WadErrData> getResultViewColNm(WadErrData search);

	List<WadErrData> getResultData(WadErrData pattvo);

	int updateOtlYn(List<WadErrData> list);

	int updateOtlRpl(WadErrData data);
	

}
