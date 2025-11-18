/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : StndItemRqstServiceImpl.java
 * 2. Package : kr.wise.meta.stnd.service.impl
 * 3. Comment : 유사항목 등록요청 서비스 구현체....
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 4. 28. 오전 8:54:48
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 4. 28. :            : 신규 개발.
 */
package kr.wise.meta.symn.service.impl;

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
import kr.wise.commons.util.UtilString;
import kr.wise.meta.app.service.WamAppStwd;
import kr.wise.meta.stnd.service.StndItemRqstService;
import kr.wise.meta.stnd.service.WamStwd;
import kr.wise.meta.stnd.service.WamStwdAbr;
import kr.wise.meta.stnd.service.WapDvCanAsm;
import kr.wise.meta.stnd.service.WapDvCanAsmMapper;
import kr.wise.meta.stnd.service.WapDvCanDic;
import kr.wise.meta.stnd.service.WapDvCanDicMapper;
import kr.wise.meta.stnd.service.WaqDmn;
import kr.wise.meta.stnd.service.WaqDmnMapper;
import kr.wise.meta.stnd.service.WaqStwd;
import kr.wise.meta.stnd.service.WaqStwdCnfg;
import kr.wise.meta.stnd.service.WaqStwdCnfgMapper;
import kr.wise.meta.stnd.service.WaqStwdMapper;
import kr.wise.meta.symn.service.SymnItemRqstService;
import kr.wise.meta.symn.service.SymnItemService;
import kr.wise.meta.symn.service.WamSymnItem;
import kr.wise.meta.symn.service.WamSymnItemMapper;
import kr.wise.meta.symn.service.WaqSymnItem;
import kr.wise.meta.symn.service.WaqSymnItemMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndItemRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 4. 28. 오전 8:54:48
 * </PRE>
 */
@Service("symnItemService")
public class SymnItemServiceImpl implements SymnItemService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamSymnItemMapper wammapper;

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


	
	public List<WamSymnItem> getSymnItemList(WamSymnItem data) {

		logger.debug("searchvo:{}", data);

		return wammapper.selectList(data);
	}
	
	
	public WamSymnItem selectSymnItemInfoDetail(String stwdId) {
		logger.debug("searchId:{}", stwdId);

		return wammapper.selectSymnItemInfoDetail(stwdId);

	}
	
	public List<WamSymnItem> selectSymnItemChangeList(String stwdId) {
		logger.debug("search Id:{}", stwdId);

		return wammapper.selectSymnItemChangeList(stwdId);
	}

}
