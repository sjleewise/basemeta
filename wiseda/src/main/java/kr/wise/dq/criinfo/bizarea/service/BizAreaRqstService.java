package kr.wise.dq.criinfo.bizarea.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;

public interface BizAreaRqstService extends CommonRqstService{
	

	public List<WaqBizAreaVO> getVrfBizAreaListIBS(WaqMstr search);
	
	public int delRegBizAreaList(ArrayList<WaqBizAreaVO> reglist);
	
	//결재모듈 개발 완료시 등록요청으로 구현해야 함
	public int submit(WaqMstr mstVo);
	
	//체크로직
	public int check(WaqMstr mstVo);

	/** meta */
	public WaqBizAreaVO getBizAreaRqstDetail(WaqBizAreaVO searchVO);

	/** meta 
	 * @throws Exception */
	public int regWam2Waq(WaqMstr reqmst, ArrayList<WaqBizAreaVO> list) throws Exception;
	
	int regWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo) throws Exception;
}
