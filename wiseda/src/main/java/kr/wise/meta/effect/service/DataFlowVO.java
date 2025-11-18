/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DataFlowVO.java
 * 2. Package : kr.wise.commons.helper.grid
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 6. 27. 오후 11:46:40
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 6. 27. :            : 신규 개발.
 */
package kr.wise.meta.effect.service;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DataFlowVO.java
 * 3. Package  : kr.wise.commons.helper.grid
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 27. 오후 11:46:40
 * </PRE>
 */
public class DataFlowVO {
	public List<DataFlowNode> nodes;

	public List<DataFlowEdge> edges;

	/** insomnia */
	public DataFlowVO() {
		// TODO Auto-generated constructor stub
	}

	public DataFlowVO(List<DataFlowNode> nodes, List<DataFlowEdge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}
}
