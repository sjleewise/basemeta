/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DataFlowMapper.java
 * 2. Package : kr.wise.meta.effect.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 6. 27. 오후 7:44:27
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 6. 27. :            : 신규 개발.
 */
package kr.wise.meta.effect.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DataFlowMapper.java
 * 3. Package  : kr.wise.meta.effect.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 27. 오후 7:44:27
 * </PRE>
 */
@Mapper
public interface DataFlowMapper {

	/** @param search
	/** @return insomnia */
	List<DataFlowTblVO> selectDataFlowTblList(DataFlowTblVO search);

	/** @param search
	/** @return insomnia */
	List<DataFlowEdge> selectDataFlowEdgeList(DataFlowTblVO search);

	/** @param search
	/** @return insomnia */
	List<DataFlowNode> selectDataFlowNodeList(DataFlowTblVO search);

	/** @param search
	/** @return insomnia */
	List<DataFlowColMapVO> selectDataFlowComMapList(DataFlowTblVO search);

	/** @param search
	/** @return insomnia */
	List<DataFlowColMapVO> selectDataFlowRqstList(DataFlowColMapVO search);
	
	int deleteDfwColMap(DataFlowColMapVO record);
	
	int insertSelective(DataFlowColMapVO record);
	
	DataFlowColMapVO selectDataFlowSrcTgtId(DataFlowColMapVO search);
	
	int updateByPrimaryKey(DataFlowColMapVO record);

	List<DataFlowTblVO> selectDataFlowTblList2(DataFlowTblVO search);
	List<DataFlowEdge> selectDataFlowEdgeList2(DataFlowTblVO search);
	List<DataFlowNode> selectDataFlowNodeList2(DataFlowTblVO search);
	List<DataFlowColMapVO> selectDataFlowComMapList2(DataFlowTblVO search);
}
