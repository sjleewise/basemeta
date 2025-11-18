/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : BaseRqstServiceImpl.java
 * 2. Package : kr.wise.commons.rqstmst.service.impl
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 7. 23. 오후 1:11:01
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 7. 23. :            : 신규 개발.
 */
package kr.wise.commons.rqstmst.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.rqstmst.service.BaseRqstService;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqMstrMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : BaseRqstServiceImpl.java
 * 3. Package  : kr.wise.commons.rqstmst.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 7. 23. 오후 1:11:01
 * </PRE>
 */
@Service("BaseRqstService")
public class BaseRqstServiceImpl implements BaseRqstService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private RequestMstService requestMstService;
	
	@Inject
	private WaqMstrMapper waqMstrMapper;
	
	/** yeonho */
	@Override
	public int deleteRqstList(ArrayList<WaqMstr> list) {
		int result = 0;
		for (WaqMstr waqMstr : list) {
			String rqstNo = waqMstr.getRqstNo();
			if (rqstNo != null && !"".equals(rqstNo)) {
				waqMstr.setIbsStatus("D");

				//getBizInfo() 사용하려 하였으나, 도메인의 경우는 WAQ_DMN 외에 WAQ_CD_VAL의 추가적으로 삭제해야할 테이블이 있음.
				//getBizInfo() 대신 deleteRqst메서드에서 if문 분기 통하여 삭제처리...
//				WaaBizInfo bizInfo = requestMstService.getBizInfo(waqMstr);
//				result += deleteRqst(waqMstr, bizInfo);
				result += deleteRqst(waqMstr);
				
			}
		}

		return result;
		
		
	}

	/** @param waqMstr
	  * @return yeonho */
	private int deleteRqst(WaqMstr waqMstr) {
		int result = 0;
		logger.debug("{}", waqMstr);
		
		//마스터 테이블 삭제...
		result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_MSTR");
		
		if(result > 0) {
			if("DIC".equals(waqMstr.getBizDcd())) {
				//표준단어
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_STWD");
				
				//표준도메인
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_DMN");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_STWD_CNFG");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_CD_VAL");
				
				//표준항목
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_SDITM");
				
			} else if ("PDM".equals(waqMstr.getBizDcd())) {
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_PDM_TBL");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_PDM_COL");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_PDM_REL");
				
			
			} else if ("DDL".equals(waqMstr.getBizDcd())) {
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_DDL_TBL");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_DDL_COL");
			} else if ("DDX".equals(waqMstr.getBizDcd())) {
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_DDL_IDX");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_DDL_IDX_COL");
			
			} else if ("DTT".equals(waqMstr.getBizDcd())) {
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_DDL_TSF_TBL");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_DDL_TSF_COL");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_DDL_TSF_IDX");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_DDL_TSF_IDX_COL");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_DDL_TSF_REL");
				
			} else if ("CCS".equals(waqMstr.getBizDcd())) {
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_CODE_CFC_SYS");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_CODE_CFC_SYS_ITEM");
			
			}else if ("TMP".equals(waqMstr.getBizDcd())) {
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_TBL_MAP");
	
			} else if ("BZA".equals(waqMstr.getBizDcd())) {
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_BIZ_AREA");
			
			} else if ("DQI".equals(waqMstr.getBizDcd())) {
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_DQI");
			
			} else if ("CTQ".equals(waqMstr.getBizDcd())) {
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_CTQ");
			
			} else if ("IMP".equals(waqMstr.getBizDcd())) {
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_CS_ANA_MSTR");
			
			} else if ("IMR".equals(waqMstr.getBizDcd())) {
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_IM_ACT_MSTR");
	
			} else if ("BRA".equals(waqMstr.getBizDcd())) {
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_BR_MSTR");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_BR_DQI_MAP");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_BR_CTQ_MAP");
	
			} else if ("PRF".equals(waqMstr.getBizDcd())) {
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_PRF_MSTR");
				//컬럼분석(PC01)
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_PRF_COL_ANA");
				//유효값분석(PC02)
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_PRF_EFVA_ANA");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_PRF_EFVA_USER_DF");
				//날짜형식분석(PC03)
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_PRF_DTFRM_ANA");
				//범위분석(PC04)
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_PRF_RNG_ANA");
				//사용자정의패턴 분석(PC05)
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_PRF_PTR_USER_DF");
				//관계분석(PT01)
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_PRF_REL_TBL");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_PRF_REL_COL");
				//중복분석(PT02)
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_PRF_UNQ_COL");
				
			} else if ("SOW".equals(waqMstr.getBizDcd())) {
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_SUBJ_OWNER");
			} else if ("DMG".equals(waqMstr.getBizDcd())) {
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_DMNG");
				result += waqMstrMapper.deleteRqst(waqMstr.getRqstNo(), "WAQ_INFO_TYPE");
			} 
		}

		return result;
	}
	
}
