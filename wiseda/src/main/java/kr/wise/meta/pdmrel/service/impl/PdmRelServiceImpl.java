/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : PdmTblRqstServiceImpl.java
 * 2. Package : kr.wise.meta.model.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 2. 오후 4:42:57
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 2. :            : 신규 개발.
 */
package kr.wise.meta.pdmrel.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.meta.ddl.service.DdlIdxRqstService;
import kr.wise.meta.ddl.service.WaqDdlIdx;
import kr.wise.meta.model.service.WaePdmColMapper;
import kr.wise.meta.model.service.WaqPdmColMapper;
import kr.wise.meta.model.service.WaqPdmRel;
import kr.wise.meta.model.service.WaqPdmTbl;
import kr.wise.meta.model.service.WaqPdmTblMapper;
import kr.wise.meta.pdmrel.service.PdmRelMstRqstService;
import kr.wise.meta.pdmrel.service.PdmRelService;
import kr.wise.meta.pdmrel.service.WamPdmRelMst;
import kr.wise.meta.pdmrel.service.WamPdmRelMstMapper;
import kr.wise.meta.pdmrel.service.WaqPdmRelCol;
import kr.wise.meta.pdmrel.service.WaqPdmRelColMapper;
import kr.wise.meta.pdmrel.service.WaqPdmRelMst;
import kr.wise.meta.pdmrel.service.WaqPdmRelMstMapper;


/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : PdmTblRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.model.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 2. 오후 4:42:57
 * </PRE>
 */
@Service("pdmRelService")
public class PdmRelServiceImpl implements PdmRelService { 

	private final Logger logger = LoggerFactory.getLogger(getClass()); 


	@Inject
	private WamPdmRelMstMapper wammapper; 
	
	

	@Override
	public List<WamPdmRelMst> getWamPdmRelList(WamPdmRelMst search) { 

		return wammapper.selectWamPdmRelList(search); 
	}



	@Override
	public List<WamPdmRelMst> getWamPdmRelColList(WamPdmRelMst search) {
		
		return wammapper.selectWamPdmRelColList(search); 
	} 
	

	

}
