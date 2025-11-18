package kr.wise.meta.ddl.service;

import java.util.List;


/**
 * <PRE>
 * 1. ClassName : DdlSeqService
 * 2. FileName  : DdlSeqService.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  : DDL시퀀스관리
 * 5. 작성자   : syyoo
 * 6. 작성일   : 2016. 11. 08.
 * </PRE>
 */
public interface DdlSeqService {

	List<WamDdlSeq> getWamSeqList(WamDdlSeq search);
	
	WamDdlSeq getWamSeqDetail(String ddlSeqId, String rqstNo);
	
	List<WamDdlSeq> getSeqChangeList(WamDdlSeq search);
	
	
}
