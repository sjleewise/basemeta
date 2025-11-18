/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AutoComServiceImpl.java
 * 2. Package : kr.wise.commons.autocom.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 30. 오후 1:10:43
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 30. :            : 신규 개발.
 */
package kr.wise.commons.autocom.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.autocom.service.AutoComMapper;
import kr.wise.commons.autocom.service.AutoComService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AutoComServiceImpl.java
 * 3. Package  : kr.wise.commons.autocom.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 30. 오후 1:10:43
 * </PRE>
 */
@Service("autoComService")
public class AutoComServiceImpl implements AutoComService{

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private AutoComMapper mapper;

	/** insomnia */
	@Override
	public List<String> getsearchTermList(Map<String, Object> searchmap) {
		//조회할 단어를 uppser처리한다....
		searchmap.put("searchTerm",  ((String)searchmap.get("searchTerm")).toUpperCase());

		String searchType = (String)searchmap.get("searchType");

		List<String> resultList = null;
		
		if("STWD".equals(searchType)) {
			resultList =  mapper.selectStndWordList(searchmap);
		} else if ("DMN".equals(searchType)) {
			resultList = mapper.selectStndDmnList(searchmap);
		} else if ("SDITM".equals(searchType)) {
			resultList =  mapper.selectStndSditmList(searchmap);
		} else if ("SYMN".equals(searchType)) {
			resultList =  mapper.selectSymnList(searchmap);
		} else if ("SBSWD".equals(searchType)) {
			resultList =  mapper.selectSymnSbswdList(searchmap);
		} else if ("SUBJ".equals(searchType)) {
			resultList =  mapper.selectSubjList(searchmap);
		} else if ("PDMTBL".equals(searchType)) {
			resultList =  mapper.selectPdmTblList(searchmap);
		} else if ("PDMCOL".equals(searchType)) {
			resultList =  mapper.selectPdmColList(searchmap);
		} else if ("ASISPDMTBL".equals(searchType)) {
			resultList =  mapper.selectAsisPdmTblList(searchmap);
		} else if ("ASISPDMCOL".equals(searchType)) {
			resultList =  mapper.selectAsisPdmColList(searchmap);
		} else if ("DDLTBL".equals(searchType)) {
			resultList =  mapper.selectDdlTblList(searchmap);
		} else if ("DBCTBL".equals(searchType)) {
			resultList =  mapper.selectDbcTblList(searchmap);
		} else if("BIZLNM".equals(searchType)){
			resultList =  mapper.selectBizLnmList(searchmap);
		} else if("DQILNM".equals(searchType)){
			resultList =  mapper.selectDqiLnmList(searchmap);
		} else if("CTQLNM".equals(searchType)){
			resultList =  mapper.selectCtqLnmList(searchmap);
		} else if("DBSCH".equals(searchType)){
			resultList =  mapper.selectDbSchList(searchmap);
		} else if("DBCCOL".equals(searchType)){
			resultList =  mapper.selectDbcColList(searchmap);
		} else if("BRNM".equals(searchType)){
			resultList =  mapper.selectBrNmList(searchmap);
		} else if("SHDLNM".equals(searchType)){
			resultList =  mapper.selectShdLnmList(searchmap);
		} else if("USRNM".equals(searchType)){
			resultList =  mapper.selectUserNmList(searchmap);
		} else if("OBJNM".equals(searchType)){
			resultList =  mapper.selectObjNmList(searchmap);
		} else if("DBMS".equals(searchType)){
			resultList =  mapper.selectDbmsList(searchmap);
		} else if("DEPT".equals(searchType)){
			resultList =  mapper.selectDeptList(searchmap);
		} else if("DMNG".equals(searchType)){
			resultList =  mapper.selectDmngList(searchmap);
		} else if("MENU".equals(searchType)){
			resultList =  mapper.selectMenuList(searchmap);
		}else if("DDLTSFTBL".equals(searchType)){
			resultList =  mapper.selectDdlTsfTblList(searchmap);
		}else if("SLCITEM".equals(searchType)) {
			resultList =  mapper.selectSlcItemList(searchmap);
		}else if("APD".equals(searchType)) {
			resultList =  mapper.selectAppStwdList(searchmap);
		}else if("API".equals(searchType)) {
			resultList =  mapper.selectAppSditmList(searchmap);
		}else if("TBLMAP".equals(searchType)) {
			resultList =  mapper.selectTblMapList(searchmap);
		}else if("COLMAP".equals(searchType)) {
			resultList =  mapper.selectColMapList(searchmap);
		}else if("CODMAP".equals(searchType)) {
			resultList =  mapper.selectCodMapList(searchmap);
		}else if ("DDLSEQ".equals(searchType)) {
			resultList =  mapper.selectDDLSEQList(searchmap);
		}else if ("DBCSEQ".equals(searchType)) {
			resultList =  mapper.selectDBCSEQList(searchmap);
		}
		
		
		if(resultList==null) {
			logger.error("------ AutoComplete Service error ------ ");
		}
		return resultList;
	}

}
