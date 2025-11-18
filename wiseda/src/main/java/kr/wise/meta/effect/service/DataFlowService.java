/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DataFlowService.java
 * 2. Package : kr.wise.meta.effect.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 6. 27. 오후 7:34:47
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 6. 27. :            : 신규 개발.
 */
package kr.wise.meta.effect.service;

import java.util.List;
import java.util.ArrayList;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DataFlowService.java
 * 3. Package  : kr.wise.meta.effect.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 27. 오후 7:34:47
 * </PRE>
 */
public interface DataFlowService {

	/** @param search
	/** @return insomnia */
	List<DataFlowTblVO> getDataflowTblList(DataFlowTblVO search);

	/** @param search
	/** @return insomnia */
	List<DataFlowEdge> getDataFlowEdges(DataFlowTblVO search);

	/** @param search
	/** @return insomnia */
	List<DataFlowNode> getDataFlowNodes(DataFlowTblVO search);

	/** @param search
	/** @return insomnia */
	List<DataFlowColMapVO> getDataflowColMapList(DataFlowTblVO search);

	/** @param search
	/** @return insomnia */
	List<DataFlowColMapVO> getDataflowRqstList(DataFlowColMapVO search);

	public int delList(ArrayList<DataFlowColMapVO> list) ;
	
	public int regList(ArrayList<DataFlowColMapVO> list) throws Exception ;
	
	public int regDataFlow(DataFlowColMapVO record) throws Exception;
}
