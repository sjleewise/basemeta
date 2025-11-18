package kr.wise.meta.ddl.service.impl;

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
import kr.wise.meta.ddl.script.service.DdlScriptMapper;
import kr.wise.meta.ddl.script.service.DdlScriptService;
import kr.wise.meta.ddl.service.DdlGrtService;
import kr.wise.meta.ddl.service.WamDdlGrt;
import kr.wise.meta.ddl.service.WamDdlGrtMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : DdlGrtServiceImpl
 * 2. FileName  : DdlGrtServiceImpl.java
 * 3. Package  : kr.wise.meta.ddl.service.impl
 * 4. Comment  : DDL시퀀스관리
 * 5. 작성자   : syyoo
 * 6. 작성일   : 2016. 11. 08.
 * </PRE>
 */
@Service("DdlGrtService")
public class DdlGrtServiceImpl implements DdlGrtService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamDdlGrtMapper wammapper;

    
    @Override
	public List<WamDdlGrt> getWamGrtList(WamDdlGrt search) {
		return wammapper.selectList(search);
	}
    
    @Override
	public WamDdlGrt getWamGrtDetail(String ddlGrtId, String rqstNo) {
		return wammapper.selectByddlGrtId(ddlGrtId, rqstNo);
	}
    
    @Override
	public List<WamDdlGrt> getGrtChangeList(WamDdlGrt search) {
		return wammapper.selectChangeList(search);
	}

	@Override
	public WamDdlGrt selectDdlGrtInfo(String ddlGrtId, String rqstNo) {
		// TODO Auto-generated method stub
		return wammapper.selectByPrimaryKeyRqstList(ddlGrtId, rqstNo);
	}

	@Override
	public WamDdlGrt getWahGrtDetail(WamDdlGrt grt) {
		// TODO Auto-generated method stub
		return wammapper.selectByRqstNo(grt);
	}
	
}
