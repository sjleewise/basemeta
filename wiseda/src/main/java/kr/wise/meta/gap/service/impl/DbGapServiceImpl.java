/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbGapServiceImpl.java
 * 2. Package : kr.wise.meta.gap.service.impl
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 8. 24. 오후 5:08:16
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 8. 24. :            : 신규 개발.
 */
package kr.wise.meta.gap.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.gap.service.DbGapService;
import kr.wise.meta.gap.service.WatDbcGapCol;
import kr.wise.meta.gap.service.WatDbcGapColMapper;
import kr.wise.meta.gap.service.WatDbcGapIdx;
import kr.wise.meta.gap.service.WatDbcGapIdxCol;
import kr.wise.meta.gap.service.WatDbcGapIdxColMapper;
import kr.wise.meta.gap.service.WatDbcGapIdxMapper;
import kr.wise.meta.gap.service.WatDbcGapTbl;
import kr.wise.meta.gap.service.WatDbcGapTblMapper;

import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbGapServiceImpl.java
 * 3. Package  : kr.wise.meta.gap.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 8. 24. 오후 5:08:16
 * </PRE>
 */
@Service("DbGapService") 
public class DbGapServiceImpl implements DbGapService {

	@Inject
	private WatDbcGapTblMapper watDbcGapTblMapper;
	
	@Inject
	private WatDbcGapColMapper watDbcGapColMapper;
	
	@Inject
	private WatDbcGapIdxMapper watDbcGapIdxMapper;
	
	@Inject
	private WatDbcGapIdxColMapper watDbcGapIdxColMapper;
	
	/** yeonho */
	@Override
	public List<WatDbcGapTbl> getDbGapList(WatDbcGapTbl search) {
		return watDbcGapTblMapper.getDbGapList(search);
	}

	/** yeonho */
	@Override
	public List<WatDbcGapCol> getGapColList(WatDbcGapCol search) {
		return watDbcGapColMapper.getGapColList(search);
	}

	/** yeonho */
	@Override
	public List<WatDbcGapCol> getGapSrcList(WatDbcGapCol search) {
		return watDbcGapColMapper.getGapSrcList(search);
	}

	/** yeonho */
	@Override
	public List<WatDbcGapCol> getGapTgtList(WatDbcGapCol search) {
		return watDbcGapColMapper.getGapTgtList(search);
	}
	
	/** yeonho */
	@Override
	public List<WatDbcGapIdx> getGapIdxList(WatDbcGapIdx search) {
		return watDbcGapIdxMapper.getGapIdxList(search);
	}
	
	/** yeonho */
	@Override
	public List<WatDbcGapIdxCol> getGapIdxColSrcList(WatDbcGapIdx search) {
		return watDbcGapIdxColMapper.getGapIdxColSrcList(search);
	}
	/** yeonho */
	@Override
	public List<WatDbcGapIdxCol> getGapIdxColTgtList(WatDbcGapIdx search) {
		return watDbcGapIdxColMapper.getGapIdxColTgtList(search);
	}

}
