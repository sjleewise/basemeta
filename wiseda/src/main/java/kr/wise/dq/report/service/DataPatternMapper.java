/**
 * 0. Project  : WISE DA �봽濡쒖젥�듃
 *
 * 1. FileName : ShdDptrnMapper.java
 * 2. Package : kr.wise.commons.damgmt.schedule.service
 * 3. Comment :
 * 4. �옉�꽦�옄  : kchoi
 * 5. �옉�꽦�씪  : 2014. 5. 2. �삤�썑 1:24:18
 * 6. 蹂�寃쎌씠�젰 :
 *                    �씠由�     : �씪�옄          : 洹쇨굅�옄猷�   : 蹂�寃쎈궡�슜
 *                   ------------------------------------------------------
 *                    kchoi : 2014. 5. 2. :            : �떊洹� 媛쒕컻.
 */
package kr.wise.dq.report.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : WamShdDptrnMapper.java
 * 3. Package  : kr.wise.commons.damgmt.schedule.service
 * 4. Comment  :
 * 5. �옉�꽦�옄   : kchoi
 * 6. �옉�꽦�씪   : 2014. 5. 2. �삤�썑 1:24:18
 * </PRE>
 */
@Mapper
public interface DataPatternMapper {

	List<DataPatternVO> dptrnList(DataPatternVO search);

	DataPatternVO dptrnHeader(DataPatternVO search);

	DataPatternVO DptrnHeaderText(DataPatternVO search);

	DataPatternVO selectPrfAnaResDtl(DataPatternVO search);

	DataPatternVO selectBrAnaResDtl(DataPatternVO search);

	List<DataPatternVO> selectPkDptrnList(DataPatternVO search);

	DataPatternVO PkDptrnHeaderText(DataPatternVO search);

	List<DataPatternVO> dptrnListNoDate(DataPatternVO search);

	String selectPrfcScript(DataPatternVO search);

	int updatePrfcScript(DataPatternVO search);

}
