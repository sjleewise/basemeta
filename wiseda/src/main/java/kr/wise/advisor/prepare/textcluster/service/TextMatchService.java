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
package kr.wise.advisor.prepare.textcluster.service;

import java.util.List;

import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.dataset.service.WadDataSet;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : TextMatchService.java
 * 3. Package  : kr.wise.advisor.prepare.textcluster.service
 * 4. Comment  : 
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2017. 10. 8. 오후 5:01:48
 * </PRE>
 */
public interface TextMatchService {

	/** @param search
	/** @return insomnia */
	List<WadDataMtcTbl> getMatchTbl(WadDataMtcTbl search);

	/** @param search
	/** @return insomnia */
	List<WadDataMtcCol> getMatchCol(WadDataMtcTbl search);

	/** @param search
	/** @return insomnia */
	int requestTextMatch(WadDataMtcTbl search);

	/** @param search
	/** @return insomnia */
	List<WadMtcData> getMatchData(WadDataMtcTbl search);

	/** @param search
	/** @return insomnia */
	List<WadMtcData> getMatchtgtData(WadMtcData search);

	/** @param dataset 
	 * @param list
	/** @return insomnia */
	int requestTextCluster(WadDataSet dataset, List<WadAnaVar> list);

	/** @param search
	/** @return insomnia */
	List<WadMtcData> getClusterData(WadAnaVar search);

	int regTextMatchInf(WadDataMtcTbl dataset, List<WadDataMtcCol> list) throws Exception;

	List<WadDataMtcTbl> getTbl(WadDataMtcTbl search);

	int delMatchData(WadDataSet dataset, List<WadDataMtcCol> list);

	List<WadClstData> getClstData(WadAnaVar search);

	int regTextClust(List<WadClstData> list);

	List<WadDataMtcTbl> getHiveTbl(WadDataMtcTbl search);  

}
