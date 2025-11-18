/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbcTblServiceImpl.java
 * 2. Package : kr.wise.meta.dbc.service.impl
 * 3. Comment : 
 * 4. 작성자  : meta
 * 5. 작성일  : 2014. 5. 22. 오후 4:20:54
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    meta : 2014. 5. 22. :            : 신규 개발.
 */
package kr.wise.meta.dbc.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.dbc.service.DbcTblService;
import kr.wise.meta.dbc.service.WatDbcCol;
import kr.wise.meta.dbc.service.WatDbcColMapper;
import kr.wise.meta.dbc.service.WatDbcIdxCol;
import kr.wise.meta.dbc.service.WatDbcIdxColMapper;
import kr.wise.meta.dbc.service.WatDbcTbl;
import kr.wise.meta.dbc.service.WatDbcTblMapper;
import kr.wise.meta.ddl.service.WamDdlIdxCol;

import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbcTblServiceImpl.java
 * 3. Package  : kr.wise.meta.dbc.service.impl
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 5. 22. 오후 4:20:54
 * </PRE>
 */
@Service("DbcTblService")
public class DbcTblServiceImpl implements DbcTblService {

	@Inject
	private WatDbcTblMapper watDbcTblMapper;
	
	@Inject
	private WatDbcColMapper watDbcColMapper;
	
	@Inject 
	private WatDbcIdxColMapper watDbcIdxColMapper;
	
	/** meta */
	@Override
	public List<WatDbcTbl> getList(WatDbcTbl search) {
		return watDbcTblMapper.selectList(search);
	}

	/** meta */
	@Override
	public WatDbcTbl selectDbcTblInfo(String dbSchId, String dbcTblNm) {
		return watDbcTblMapper.selectByPrimaryKey(dbSchId, dbcTblNm);
	}

	/** meta */
	@Override
	public List<WatDbcCol> selectDbcTblColList(String dbSchId, String dbcTblNm) {
		return watDbcColMapper.selectList(dbSchId, dbcTblNm);
	}

	/** meta */
	@Override
	public List<WatDbcIdxCol> selectDbcTblIdxColList(String dbSchId, String dbcTblNm) {
		return watDbcIdxColMapper.selectList(dbSchId, dbcTblNm);
	}

	/** meta */
	@Override
	public WatDbcCol selectDbcTblColInfo(String dbSchId, String dbcTblNm,
			String dbcColNm) {
		return watDbcColMapper.selectByPrimaryKey(dbSchId, dbcTblNm, dbcColNm);
	}

	/** meta */
	@Override
	public List<WatDbcCol> selectDbcTblColListNonColor(String dbSchId,
			String dbcTblNm) {
		return watDbcColMapper.selectListNonColor(dbSchId, dbcTblNm);
	}

	/** meta */
	@Override
	public List<WatDbcTbl> getHistList(WatDbcTbl search) {
		return watDbcTblMapper.selectHistList(search);
	}

	/** meta */
	@Override
	public List<WatDbcTbl> selectDbcTblChangeList(String dbSchId,
			String dbcTblNm) {
		return watDbcTblMapper.selectDbcTblChangeList(dbSchId, dbcTblNm);
	}

	/** meta */
	@Override
	public List<WatDbcCol> selectDbcColChangeList(String dbSchId,
			String dbcTblNm) {
		return watDbcColMapper.selectDbcColChangeList(dbSchId, dbcTblNm);
	}

	/** meta */
	@Override
	public List<WatDbcIdxCol> selectDbcIdxColChangeList(String dbSchId,
			String dbcTblNm) {
		return watDbcIdxColMapper.selectDbcIdxColChangeList(dbSchId, dbcTblNm);
	}
	
	@Override
	public List<WatDbcTbl> getDbcDpndList(String dbSchId, String dbcTblNm, String dbConnTrgLnm, String dbSchLnm) {
		return watDbcTblMapper.selectDbcDpndList(dbSchId, dbcTblNm, dbConnTrgLnm, dbSchLnm);
	}


}
