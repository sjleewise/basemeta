/**
 * 0. Project  : WISE Advisor 프로젝트
 *
 * 1. FileName : OutlierService.java
 * 2. Package : kr.wise.advisor.prepare.outlier.service
 * 3. Comment : 
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2017. 9. 26. 오후 4:37:52
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2017. 9. 26. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.outlier.service;

import java.util.List;

import kr.wise.admin.ai.algorithm.service.WaaAlg;
import kr.wise.admin.ai.algorithm.service.WaaAlgArg;
import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.dq.report.service.DataPatternVO;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : OutlierService.java
 * 3. Package  : kr.wise.advisor.prepare.outlier.service
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 9. 26. 오후 4:37:52
 * </PRE>
 */
public interface OutlierService {

	/** @param search
	/** @return insomnia */
	List<WaaAlgArg> getAlgParam(WaaAlg search);

	/** @param search
	/** @param list
	/** @return insomnia 
	 * @throws Exception */
	int regOutlierDetection(WadOtlDtc search, List<WadAnaVar> list) throws Exception;

	/** @param search insomnia */
	int requestOutlier(WadOtlDtc search);

	/** @param search
	/** @return insomnia */
	List<WadOtlResult> getOutlierResult(WadOtlDtc search);

	/** @param search
	/** @return insomnia */
	List<WadAnaVar> getAlgVarList(WadOtlDtc search);

	/** @param search
	/** @return insomnia */
	List<WadOtlData> getOutDataList(WadOtlDtc search);

	/** @param search
	/** @return insomnia */
	List<WadOtlData> getOutDataColList(WadOtlDtc search);

	/** @param search
	/** @return insomnia */
	List<WadOtlChartData> getOutChartData(WadOtlDtc search);

	/** @param search
	/** @return insomnia */
	OutScatterData getOutScatterData(WadOtlDtc search);

	/** @param search
	/** @return insomnia */
	List<WadOtlData> getOutDataList2(WadOtlDtc search);

	/** @param search
	/** @return insomnia */
	List<WadAnaVar> getOtlVarList(WadOtlDtc search);

	/** @param search
	/** @return insomnia */
	int delOutlierDetection(WadOtlDtc search);

	/** @param search
	/** @return insomnia */
	WadOtlResult getOutlierResultByPatternVO(DataPatternVO search);

	/** @param search
	/** @return insomnia */
	List<WadOtlData> getOutDataColNm(DataPatternVO search);

	/** @param pattvo
	/** @return insomnia */
	List<WadOtlData> getOutDataMulti(DataPatternVO pattvo);

	/** @param search
	/** @param pattvo
	/** @return insomnia */
	List<WadOtlData> getOutDataListOne(WadOtlDtc search, DataPatternVO pattvo);

	/** @param search
	/** @return insomnia */
	List<WaaAlgArg> getAlgParamById(WadOtlResult search);

	List<WadOtlData> getOutDataAnlVarId(DataPatternVO search);

	int updateOtlYn(List<WadOtlData> list);

	int updateOtlRpl(WadOtlData data);


}
