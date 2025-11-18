/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DbcSeqServiceImpl.java
 * 2. Package : kr.wise.meta.dbc.service.impl
 * 3. Comment : 
 * 4. 작성자  : hjan93
 * 5. 작성일  : 2020. 7. 30.
 * 6. 변경이력 : 
 *                    이름     	  : 일자          	  	: 근거자료      : 변경내용
 *                   ------------------------------------------------------
 *                    hjan93  : 2020. 7. 30. :        : 신규 개발.
 */
package kr.wise.meta.dbc.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.wise.meta.dbc.service.DbcSeqService;
import kr.wise.meta.dbc.service.WatDbcSeq;
import kr.wise.meta.dbc.service.WatDbcSeqMapper;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : DbcSeqServiceImpl.java
 * 3. Package  : kr.wise.meta.dbc.service.impl
 * 4. Comment  : 
 * 5. 작성자   : hjan93
 * 6. 작성일   : 2020. 7. 30.
 * </PRE>
 */
@Service("DbcSeqService")
public class DbcSeqServiceImpl implements DbcSeqService {

	@Inject
	private WatDbcSeqMapper watDbcSeqMapper;

	/** meta */
	@Override
	public List<WatDbcSeq> getList(WatDbcSeq search) {
		return watDbcSeqMapper.selectList(search);
	}

	/** meta */
	@Override
	public WatDbcSeq selectDbcSeqInfo(String dbSchId, String dbcSeqNm) {
		return watDbcSeqMapper.selectByPrimaryKey(dbSchId, dbcSeqNm);
	}
}
