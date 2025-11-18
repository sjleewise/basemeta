/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : RequestChgDtlsServiceImpl.java
 * 2. Package : kr.wise.commons.rqstmst.service.impl
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 7. 25. 오후 3:36:24
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 7. 25. :            : 신규 개발.
 */
package kr.wise.commons.rqstmst.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.rqstmst.service.RequestChgDtlsService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqRqstChgDtls;
import kr.wise.commons.rqstmst.service.WaqRqstChgDtlsMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : RequestChgDtlsServiceImpl.java
 * 3. Package  : kr.wise.commons.rqstmst.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 7. 25. 오후 3:36:24
 * </PRE>
 */
@Service("RequestChgDtlsService")
public class RequestChgDtlsServiceImpl implements RequestChgDtlsService{
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WaqRqstChgDtlsMapper mapper;
	
	//======= 변경항목 상세조회 =====
	@Override
	public List<WaqRqstChgDtls> getRqstChangeLst(WaqMstr search, String subInfo ){
		
		List<WaqRqstChgDtls> list = null;
//	    logger.debug("변경항목조회 : 업무코드 : " + search.getBizDcd());
//		logger.debug("변경항목조회 : 상세업무코드 : " + search.getBizDtlCd());
//		logger.debug("변경항목조회 : subInfo : " + subInfo);
	
		//표준단어
		if(search.getBizDtlCd().equals("STWD")){
			list = mapper.selectStwdChgList(search);
		} else if(search.getBizDtlCd().equals("DMN")){
			list = mapper.selectDmnChgList(search);
		} else if(search.getBizDtlCd().equals("SDITM")){
			list = mapper.selectItemChgList(search);
		}else if(search.getBizDtlCd().equals("CDVAL")){ 
		    list = mapper.selectCdValChgList(search);
		}else if(search.getBizDcd().equals("LDM") ){ 
			//컬럼 검색일경우와 컬럼검색이 아닐경우...
			if(subInfo != null && "COL".equals(subInfo)) {

				list = mapper.selectLdmAttrChgList(search);		 	
			} else {
			
				list = mapper.selectLdmEntyChgList(search);
			}
		}else if(search.getBizDcd().equals("PDM") || search.getBizDcd().equals("R7P") || search.getBizDcd().equals("R9P") || search.getBizDcd().equals("ASP") || search.getBizDcd().equals("DAS") || search.getBizDcd().equals("EXP")){ 
			//컬럼 검색일경우와 컬럼검색이 아닐경우...
			if(subInfo != null && "COL".equals(subInfo)) {
//				System.out.println("컬럼변경항목조회");
				list = mapper.selectPdmColChgList(search);
			}else if(subInfo != null && "REL".equals(subInfo)) {
				list = mapper.selectPdmRelChgList(search);
			} else {
			
				list = mapper.selectPdmTblChgList(search);
			}
		} else if(search.getBizDcd().equals("DDL") || search.getBizDcd().equals("DTT")){
			//컬럼 검색일경우와 컬럼검색이 아닐경우...
			if(subInfo != null && "COL".equals(subInfo)) {
				list = mapper.selectDdlColChgList(search);
			}else if(subInfo != null && "REL".equals(subInfo)) {
				list = mapper.selectDdlRelChgList(search);
			} else {
				list = mapper.selectDdlTblChgList(search);
			}
		} else if(search.getBizDcd().equals("DDX")){
			//컬럼 검색일경우와 컬럼검색이 아닐경우...
			if(subInfo != null && "COL".equals(subInfo)) {
				list = mapper.selectDdlIdxColChgList(search);
			} else {
				list = mapper.selectDdlIdxChgList(search);
			}
		/*	
		} else if(search.getBizDcd().equals("DTT")){
			//인덱스 검색일경우와 컬럼검색이 아닐경우...
			if(subInfo != null && "IDX".equals(subInfo)) {  //인덱스검색
				list = mapper.selectDdlTsfIdxChgList(search);
			}else if(search.getBizDtlCd().equals("TSFCOL")){
			    logger.debug("컬럼이관변경");
			    list = mapper.selectDdlColTsfChgList(search);
		    }else if(search.getBizDtlCd().equals("TSFIDXCOL")){
		        logger.debug("인덱스컬럼이관변경");
			    list = mapper.selectDdlIdxColTsfChgList(search);
		    }
			else {
				list = mapper.selectDdlTsfTblChgList(search);
			}
		*/	
		}else if( search.getBizDcd().equals("DDS") ){ //DDL SEQUENCE
			list = mapper.selectDdlSeqChgList(search);
		}else if( search.getBizDcd().equals("TSFSEQ") ){
	        //logger.debug("기타오브젝트이관변경");
		    list = mapper.selectDdlSeqChgList(search);
	    } else if(search.getBizDcd().equals("TMP")){
			list = mapper.selectTblMapChgList(search);
		} else if(search.getBizDcd().equals("DMP")){
			list = mapper.selectCodMapChgList(search);
		} else if(search.getBizDcd().equals("CCS")){
			list = mapper.selectCodeCfcSysChgList(search);
		} else if(search.getBizDcd().equals("BZA")){
			list = mapper.selectBizAreaChgList(search);
		} else if(search.getBizDcd().equals("DQI")){
			list = mapper.selectDqiChgList(search);
		} else if(search.getBizDcd().equals("CTQ")){
			list = mapper.selectCtqChgList(search);
		} else if(search.getBizDcd().equals("BRA")){
			list = mapper.selectBizruleChgList(search);
		} else if(search.getBizDcd().equals("IMP")){
			list = mapper.selectImPlChgList(search);
		} else if(search.getBizDcd().equals("IMR")){
			list = mapper.selectImRslChgList(search);
		}else if(search.getBizDcd().equals("PDP")){
			//컬럼 검색일경우와 컬럼검색이 아닐경우...
			if(subInfo != null && "COL".equals(subInfo)) {
				list = mapper.selectPdmColChgList(search);
			}else if(subInfo != null && "REL".equals(subInfo)) {
				list = mapper.selectPdmRelChgList(search);
			} else {
				list = mapper.selectPdmTblChgList(search);
			}
		}else if (search.getBizDcd().equals("MSG")){
			list =mapper.selectMsgChgList(search);
		}else if (search.getBizDcd().equals("CDT")){
			list =mapper.selectCodeTsfChgList(search);
		}else if (search.getBizDcd().equals("MST")){
			list =mapper.selectMsgTsfChgList(search);
		}else if (search.getBizDcd().equals("SEM")){
			list =mapper.selectMsgStdChgList(search);
		}else if (search.getBizDcd().equals("CEM")){
			list =mapper.selectMsgChnlChgList(search);
		}else if (search.getBizDcd().equals("OEM")){
			list =mapper.selectMsgMapChgList(search);
		}else if (search.getBizDcd().equals("MAC")){
			list =mapper.selectMsgCodeChgList(search);
		}else if (search.getBizDcd().equals("DCV")){
			list =mapper.selectAsisDmnCdValChgList(search);
		}else if (search.getBizDcd().equals("APD")){
			list =mapper.selectAppStwdChgList(search);
		}else if (search.getBizDcd().equals("API")){
			list =mapper.selectAppSditmChgList(search);
		}else if (search.getBizDcd().equals("SYM")){
			list =mapper.selectSymnItemChgList(search);
		}else if (search.getBizDcd().equals("DMG")){
			if(subInfo != null && "COL".equals(subInfo)) {
				list =mapper.selectInfoTypeChgList(search);
			}else {
				list =mapper.selectDmngChgList(search);
			}
		}else if(search.getBizDcd().equals("DDG")){ //DDL 권한
			list = mapper.selectDdlGrtChgList(search);
		}else if(search.getBizDcd().equals("DDP")){
			//DDL 파티션 변경사항 조회
			list = mapper.selectDdlPartChgList(search);
		}
		
		
		return list;
	}
}
