package kr.wise.meta.subjarea.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

public interface SubjOwnerRqstService extends CommonRqstService {

	//요청서 조회
	List<WaaSubj> getSubjOwnerRqstList(WaqMstr search); 
	
	int delSubjOwnerRqstList(WaqMstr search ,ArrayList<WaaSubj> list) throws Exception; 
	
	int regWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo) throws Exception;
	
	int insertSubjOwner(Map<String, Object> map)  throws Exception ;


}
