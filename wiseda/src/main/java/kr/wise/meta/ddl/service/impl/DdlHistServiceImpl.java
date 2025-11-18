/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlHistServiceImpl.java
 * 2. Package : kr.wise.meta.ddl.service.impl
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 9. 15. 오전 11:15:10
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 9. 15. :            : 신규 개발.
 */
package kr.wise.meta.ddl.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.ddl.service.DdlHistService;
import kr.wise.meta.ddl.service.WamDdlCol;
import kr.wise.meta.ddl.service.WamDdlColMapper;
import kr.wise.meta.ddl.service.WamDdlIdx;
import kr.wise.meta.ddl.service.WamDdlIdxCol;
import kr.wise.meta.ddl.service.WamDdlIdxColMapper;
import kr.wise.meta.ddl.service.WamDdlIdxMapper;
import kr.wise.meta.ddl.service.WamDdlRel;
import kr.wise.meta.ddl.service.WamDdlRelMapper;
import kr.wise.meta.ddl.service.WamDdlTbl;
import kr.wise.meta.ddl.service.WamDdlTblMapper;

import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DdlHistServiceImpl.java
 * 3. Package  : kr.wise.meta.ddl.service.impl
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 9. 15. 오전 11:15:10
 * </PRE>
 */
@Service("DdlHistService")
public class DdlHistServiceImpl implements DdlHistService {

	@Inject
	private WamDdlIdxMapper wamDdlIdxMapper;
	
	@Inject
	private WamDdlTblMapper wamDdlTblMapper;
	
	@Inject
	private WamDdlIdxColMapper wamDdlIdxColMapper;
	
	@Inject
	private WamDdlColMapper wamDdlColMapper;
	
	@Inject
	private WamDdlRelMapper wamDdlRelMapper;
	
	/** meta */
	@Override
	public List<WamDdlIdx> getIdxHistList(WamDdlIdx search) {
		return wamDdlIdxMapper.selectDdlIdxHistList(search);
	}

	/** meta */
	@Override
	public WamDdlIdx selectDdlIdxHistInfo(WamDdlIdx search) {
		return wamDdlIdxMapper.selectDdlIdxHistInfo(search);
	}

	/** meta */
	@Override
	public List<WamDdlIdxCol> getIdxColHistList(WamDdlIdxCol search) {
		return wamDdlIdxColMapper.selectDdlIdxColHistList(search);
	}

	/** meta */
	@Override
	public WamDdlIdxCol selectDdlIdxColInfo(WamDdlIdxCol search) {
		return wamDdlIdxColMapper.selectDdlIdxColHistInfo(search);
	}

	/** meta */
	@Override
	public List<WamDdlIdx> getIdxChangeHistList(WamDdlIdx search) {
		return wamDdlIdxMapper.selectIdxChangeHistList(search);
	}

	/** meta */
	@Override
	public List<WamDdlIdxCol> getIdxColChangeHistList(WamDdlIdxCol search) {
		return wamDdlIdxColMapper.selectIdxColChangeHistList(search);
	}

	/** meta */
	@Override
	public List<WamDdlTbl> getDdlHistList(WamDdlTbl search) {
		return wamDdlTblMapper.selectDdlTblHistList(search);
	}

	/** meta */
	@Override
	public WamDdlTbl selectDdlTblInfoHist(WamDdlTbl search) {
		return wamDdlTblMapper.selectDdlTblHistDetail(search);
	}

	/** meta */
	@Override
	public List<WamDdlCol> selectDdlTblColHistList(WamDdlTbl searchVO) {
		return wamDdlColMapper.selectDdlTblColHistList(searchVO);
	}

	/** meta */
	@Override
	public List<WamDdlRel> selectDdlTblRelHistList(WamDdlTbl searchVO) {
		return wamDdlRelMapper.selectDdlRelHistList(searchVO);
	}

	/** meta */
	@Override
	public List<WamDdlIdxCol> selectDdlTblIdxColHistList(WamDdlIdxCol searchVO) {
		return wamDdlIdxColMapper.selectDdlTblIdxColHistList(searchVO);
	}

	/** meta */
	@Override
	public WamDdlCol selectDdlTblColHistInfo(WamDdlCol search) {
		return wamDdlColMapper.selectDdlTblColHistInfo(search);
	}

}
