/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : IdxGapServiceImpl.java
 * 2. Package : kr.wise.meta.gap.service.impl
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 9. 24. 오후 4:35:30
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 9. 24. :            : 신규 개발.
 */
package kr.wise.meta.gap.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.gap.service.IdxGapMapper;
import kr.wise.meta.gap.service.IdxGapService;
import kr.wise.meta.gap.service.IdxGapVO;

import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : IdxGapServiceImpl.java
 * 3. Package  : kr.wise.meta.gap.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 9. 24. 오후 4:35:30
 * </PRE>
 */
@Service("IdxGapService")
public class IdxGapServiceImpl implements IdxGapService {
	
	@Inject
	private IdxGapMapper idxGapMapper;
	
	/** yeonho */
	@Override
	public List<IdxGapVO> getIdxGapAnalyze(IdxGapVO search) {
		return idxGapMapper.selectIdxGapAnalyze(search);
	}

	/** yeonho */
	@Override
	public List<IdxGapVO> getDbcIdxColList(IdxGapVO search) {
		return idxGapMapper.selectDbcIdxColList(search);
	}

	/** yeonho */
	@Override
	public List<IdxGapVO> getDdlIdxColList(IdxGapVO search) {
		return idxGapMapper.selectDdlIdxColList(search);
	}


    @Override
	public List<IdxGapVO> getDdlDbcIdxGapList(IdxGapVO search){
		return idxGapMapper.selectDdlDbcIdxGapList(search);
	}
    
    @Override
	public List<IdxGapVO> getDdlDbcIdxColGapList(IdxGapVO search){
		return idxGapMapper.selectDdlDbcIdxColGapList(search);
	}
    
    @Override
	public List<IdxGapVO> getDdlTsfIdxGapList(IdxGapVO search){
		return idxGapMapper.selectDdlTsfIdxGapList(search);
	}
    
    @Override
	public List<IdxGapVO> getDdlTsfIdxColGapList(IdxGapVO search){
		return idxGapMapper.selectDdlTsfIdxColGapList(search);
	}
    
    @Override
	public List<IdxGapVO> getDdlTsfDbcIdxGapList(IdxGapVO search){
		return idxGapMapper.selectDdlTsfDbcIdxGapList(search);
	}
    
    @Override
	public List<IdxGapVO> getDdlTsfDbcIdxColGapList(IdxGapVO search){
		return idxGapMapper.selectDdlTsfDbcIdxColGapList(search);
	}
    
    @Override
	public List<IdxGapVO> getDbcIdxGapList(IdxGapVO search){
		return idxGapMapper.selectDbcIdxGapList(search);
	}
    
    @Override
	public List<IdxGapVO> getDbcIdxColGapList(IdxGapVO search){
		return idxGapMapper.selectDbcIdxColGapList(search);
	}
}
