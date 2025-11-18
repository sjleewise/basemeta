package kr.wise.meta.ddl.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.ddl.service.DdlSeqService;
import kr.wise.meta.ddl.service.WamDdlSeq;
import kr.wise.meta.ddl.service.WamDdlSeqMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : DdlSeqServiceImpl
 * 2. FileName  : DdlSeqServiceImpl.java
 * 3. Package  : kr.wise.meta.ddl.service.impl
 * 4. Comment  : DDL시퀀스관리
 * 5. 작성자   : syyoo
 * 6. 작성일   : 2016. 11. 08.
 * </PRE>
 */
@Service("DdlSeqService")
public class DdlSeqServiceImpl implements DdlSeqService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamDdlSeqMapper wammapper;

    
    @Override
	public List<WamDdlSeq> getWamSeqList(WamDdlSeq search) {
		return wammapper.selectWamSeqList(search);
	}
    
    @Override
	public WamDdlSeq getWamSeqDetail(String ddlSeqId, String rqstNo) {
		return wammapper.selectWamSeqListById(ddlSeqId, rqstNo);
	}
    
    @Override
	public List<WamDdlSeq> getSeqChangeList(WamDdlSeq search) {
		return wammapper.selectSeqChangeList(search);
	}
	
}
