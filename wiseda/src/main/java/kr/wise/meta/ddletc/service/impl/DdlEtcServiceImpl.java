/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlTblRqstServiceImpl.java
 * 2. Package : kr.wise.meta.ddl.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 14. 오후 3:10:37
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 14. :            : 신규 개발.
 */
package kr.wise.meta.ddletc.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
import kr.wise.meta.ddl.script.service.DdlScriptMapper;
import kr.wise.meta.ddl.script.service.DdlScriptService;
import kr.wise.meta.ddl.service.DdlIdxRqstService;
import kr.wise.meta.ddl.service.DdlTblRqstService;
import kr.wise.meta.ddl.service.WamDdlTbl;
import kr.wise.meta.ddl.service.WaqDdlCol;
import kr.wise.meta.ddl.service.WaqDdlColMapper;
import kr.wise.meta.ddl.service.WaqDdlIdxColMapper;
import kr.wise.meta.ddl.service.WaqDdlIdxMapper;
import kr.wise.meta.ddl.service.WaqDdlRel;
import kr.wise.meta.ddl.service.WaqDdlRelMapper;
import kr.wise.meta.ddl.service.WaqDdlTbl;
import kr.wise.meta.ddl.service.WaqDdlTblMapper;
import kr.wise.meta.ddletc.service.DdlEtcRqstService;
import kr.wise.meta.ddletc.service.DdlEtcService;
import kr.wise.meta.ddletc.service.WamDdlEtc;
import kr.wise.meta.ddletc.service.WamDdlEtcMapper;
import kr.wise.meta.ddletc.service.WaqDdlEtc;
import kr.wise.meta.ddletc.service.WaqDdlEtcMapper;
import kr.wise.meta.model.service.WaqPdmTbl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlEtcRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.ddl.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 14. 오후 3:10:37
 * </PRE>
 */
@Service("ddlEtcService")
public class DdlEtcServiceImpl implements DdlEtcService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

    
    @Inject
	private WamDdlEtcMapper mapper;


	@Override
	public List<WamDdlEtc> getDdlEtcList(WamDdlEtc search) {
		// TODO Auto-generated method stub
		return mapper.selectDdlEtcList(search) ; 
	}


	@Override
	public WamDdlEtc gettDdlEtcDetail(String ddlEtcId, String rqstNo) {
		// TODO Auto-generated method stub
		return  mapper.selectDdlEtcDetail(ddlEtcId, rqstNo) ; 
	}


	

    
}
