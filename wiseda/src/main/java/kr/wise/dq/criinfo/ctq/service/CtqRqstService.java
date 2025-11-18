package kr.wise.dq.criinfo.ctq.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

public interface CtqRqstService extends CommonRqstService {
	
	
	public Map<String, String> delRegCtqList(ArrayList<WaqCtqVO> reglist, WaqMstr mstr);
	
	//결재모듈 개발 완료시 등록요청으로 구현해야 함
	public int submit(WaqMstr mstVo);
	
	//체크로직
	public int check(WaqMstr mstVo);

	/** @param search
	/** @return meta */
	public List<WaqCtqVO> getVrfCtqListIBS(WaqMstr search);

	/** @param searchVO
	/** @return meta */
	public WaqCtqVO getCtqRqstDetail(WaqCtqVO searchVO);

	/** @param reqmst
	/** @param list
	/** @return meta 
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqCtqVO> list) throws Exception;
	
	int regWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo) throws Exception;
}
