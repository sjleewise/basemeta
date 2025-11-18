package kr.wise.meta.ddl.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * <PRE>
 * 1. ClassName : DdlSeqRqstService
 * 2. FileName  : DdlSeqRqstService.java
 * 3. Package  : kr.wise.meta.ddl.service
 * 4. Comment  : DDL시퀀스관리
 * 5. 작성자   : syyoo
 * 6. 작성일   : 2016. 11. 02.
 * </PRE>
 */
public interface DdlSeqRqstService extends CommonRqstService {

	
	
	List<WaqDdlSeq> getDdlSeqRqstList(WaqMstr search);
	
	WaqDdlSeq getDdlSeqRqstDetail(WaqDdlSeq searchVo);

	int regWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlSeq> list) throws Exception;

	int delDdlSeqRqst(WaqMstr reqmst, ArrayList<WaqDdlSeq> list) throws Exception;
	
	void updateDdlSeqScriptWaq(WaqMstr mstVo) throws Exception;
	
	int regWaq2Wam(WaqMstr mstVo) throws Exception;
	
	int regWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo) throws Exception;
	
	//운영이관시 WAM영역 개발, 테스트 SR번호/서브프로젝트 번호 UPDATE
	int updateSrMngNoPrjMngNo(WaqMstr mstVo) throws Exception;

	List<WamDdlSeq> selectDdlTsfSeqListForRqst(WamDdlSeq search);

	int regTsfWam2Waq(WaqMstr reqmst, ArrayList<WaqDdlSeq> list) throws Exception;

	int registerTsf(WaqMstr mstVo, List<?> wamlist) throws Exception;
	
}
