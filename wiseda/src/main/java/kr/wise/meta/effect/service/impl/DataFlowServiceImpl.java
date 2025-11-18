/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DataFlowServiceImpl.java
 * 2. Package : kr.wise.meta.effect.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 6. 27. 오후 7:35:21
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 6. 27. :            : 신규 개발.
 */
package kr.wise.meta.effect.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.effect.service.DataFlowColMapVO;
import kr.wise.meta.effect.service.DataFlowEdge;
import kr.wise.meta.effect.service.DataFlowMapper;
import kr.wise.meta.effect.service.DataFlowNode;
import kr.wise.meta.effect.service.DataFlowService;
import kr.wise.meta.effect.service.DataFlowTblVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DataFlowServiceImpl.java
 * 3. Package  : kr.wise.meta.effect.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 6. 27. 오후 7:35:21
 * </PRE>
 */
@Service("dataFlowService")
public class DataFlowServiceImpl implements DataFlowService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private DataFlowMapper mapper;
	
	@Inject
    private EgovIdGnrService objectIdGnrService;

	/** insomnia */
	public List<DataFlowTblVO> getDataflowTblList(DataFlowTblVO search) {
//		logger.debug("start mapper");

		if (StringUtils.hasText(search.getDdlTblPnm())) {
			search.setDdlTblPnm(search.getDdlTblPnm().toUpperCase());
		}
		if (StringUtils.hasText(search.getDdlColPnm())) {
			search.setDdlColPnm(search.getDdlColPnm().toUpperCase());
		}

		return mapper.selectDataFlowTblList2(search);
	}

	/** insomnia */
//	@Override
	public List<DataFlowEdge> getDataFlowEdges(DataFlowTblVO search) {
		return mapper.selectDataFlowEdgeList2(search);
	}

	/** insomnia */
//	@Override
	public List<DataFlowNode> getDataFlowNodes(DataFlowTblVO search) {
		return mapper.selectDataFlowNodeList2(search);
	}

	/** insomnia */
	public List<DataFlowColMapVO> getDataflowColMapList(DataFlowTblVO search) {
		return mapper.selectDataFlowComMapList2(search);
	}

	public List<DataFlowColMapVO> getDataflowRqstList(DataFlowColMapVO search) {
		return mapper.selectDataFlowRqstList(search);
	}
	
	@Override
	public int delList(ArrayList<DataFlowColMapVO> list) {

		int result = 0;

		for (DataFlowColMapVO DataFlowColMapVO : list) {
			logger.debug("///DataFlowColMapVO.getIbsStatus()"+DataFlowColMapVO.getIbsStatus());
			logger.debug("///DataFlowColMapVO.getIbsCheck()"+DataFlowColMapVO.getIbsCheck());
			if (DataFlowColMapVO.getIbsCheck().equals("1")) {

				result += mapper.deleteDfwColMap(DataFlowColMapVO);
			}
		}

		return result;

	}
	
	@Override
	public int regList(ArrayList<DataFlowColMapVO> list) throws Exception {

		int result = 0;

		for (DataFlowColMapVO DataFlowColMapVO : list) {
			result += regDataFlow(DataFlowColMapVO);
		}

		return result;

	}
	
	@Override
	public int regDataFlow(DataFlowColMapVO record) throws Exception {
		String tmpStatus = record.getIbsStatus();
		int result = 0;

		logger.debug("tmpStatus : "+tmpStatus);
//		logger.debug("StringUtils.hasText(record.getDfwColId()) : "+StringUtils.hasText(record.getDfwColId()));
		
//		DataFlowColMapVO tmpVO = mapper.selectDataFlowSrcTgtId(record);
//		if (tmpVO != null) {
//			record.setSrcTblId(tmpVO.getSrcTblId());
//			record.setTgtTblId(tmpVO.getTgtTblId());
//			record.setSrcColId(tmpVO.getSrcColId());
//			record.setTgtColId(tmpVO.getTgtColId());
//		}
//		if("I".equals(tmpStatus) && !StringUtils.hasText(record.getDfwColId())) {  // 신규...
//			String id =  objectIdGnrService.getNextStringId();
//			record.setDfwColId(id);
//			
//			result = mapper.insertSelective(record);
//		}else{
//			result = mapper.updateByPrimaryKey(record);
//		}
		
		if("I".equals(tmpStatus) && !StringUtils.hasText(record.getDfwColId())) {  // 신규...
			String id =  objectIdGnrService.getNextStringId();
			record.setDfwColId(id);
			
			result = mapper.insertSelective(record);
		}else{
			result = mapper.updateByPrimaryKey(record);
		}
		
		return result;

		
		
	}
}
