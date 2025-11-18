/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeTsfServiceImpl.java
 * 2. Package : kr.wise.meta.stnd.service.impl
 * 3. Comment : 코드이관 조회 서비스 구현체....
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 11. 24. 
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    이상익 : 2015. 11. 24. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.meta.stnd.service.CodeTsfRqstService;
import kr.wise.meta.stnd.service.CodeTsfService;
import kr.wise.meta.stnd.service.WamCdVal;
import kr.wise.meta.stnd.service.WamCdValTsf;
import kr.wise.meta.stnd.service.WamCodeTsfMapper;
import kr.wise.meta.stnd.service.WaqCdValTsf;
import kr.wise.meta.stnd.service.WaqCodeTsfMapper;







import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : MsgRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : 이상익
 * 6. 작성일   : 2015. 9. 25. 
 * </PRE>
 */
@Service("CodeTsfService")

public class CodeTsfServiceImpl implements CodeTsfService {
	private final Logger logger = LoggerFactory.getLogger(getClass());



	@Inject
	private RequestMstService requestMstService;

	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;

	@Inject
	private RequestApproveService requestApproveService;

    @Inject
    private EgovIdGnrService objectIdGnrService;

    @Inject
	private EgovIdGnrService requestIdGnrService;

    @Inject
    private WamCodeTsfMapper wamCodeTsfMapper;
    
    @Inject
    private WaqCodeTsfMapper waqCodeTsfMapper;
    
	
	
	
	
	

	
	/** 코드 리스트 조회 lsi */	
    public List<WamCdValTsf> selectCodeTsfWamList(WamCdValTsf saerchVo){
    	logger.debug("코드이관 리스트조회");
    	return wamCodeTsfMapper.selectCdTsfWamList(saerchVo);
    }
    
    
    public WamCdValTsf selectCodeTsfWamDetail(WamCdValTsf searchVo){
        logger.debug("{}", searchVo);
        logger.debug("코드이관 상세조회");
    	return wamCodeTsfMapper.selectCdTsfWamDetail(searchVo);
    }


	@Override
	public List<WamCdValTsf> selectCodeTsfWamChg(WamCdValTsf searchVo) {
			return wamCodeTsfMapper.selectCdTsfWamChg(searchVo);
	}
		
}
