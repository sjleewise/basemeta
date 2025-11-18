/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : ModelPdmService.java
 * 2. Package : kr.wise.meta.model.service
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 26. 오후 5:48:57
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 26. 		: 신규 개발.
 */
package kr.wise.meta.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;
import kr.wise.meta.admin.service.WaaInfoSys;
import kr.wise.meta.model.service.GovVo;
import kr.wise.meta.model.service.ModelPdmService;
import kr.wise.meta.model.service.PdmTblColDownVo;
import kr.wise.meta.model.service.WaaGovServerInfo;
import kr.wise.meta.model.service.WamAsisPdmTblMapper;
import kr.wise.meta.model.service.WamPdmCol;
import kr.wise.meta.model.service.WamPdmColMapper;
import kr.wise.meta.model.service.WamPdmRel;
import kr.wise.meta.model.service.WamPdmRelMapper;
import kr.wise.meta.model.service.WamPdmTbl;
import kr.wise.meta.model.service.WamPdmTblMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : ModelPdmService
 * 2. Package  : kr.wise.meta.model.service
 * 3. Comment  :
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 26.
 * </PRE>
 */
@Service("modelPdmService")
public class ModelPdmServiceImpl implements ModelPdmService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamPdmTblMapper mapper;
	
	@Inject
	private WamAsisPdmTblMapper asismapper;

	@Inject
	private WamPdmColMapper colmapper;
	
	@Inject
	private WamPdmRelMapper wamPdmRelMapper;
	
	@Override
	public List<WamPdmTbl> getPdmTblList(WamPdmTbl search) {

		List<WamPdmTbl> list = mapper.selectList(search);

		return list;

	}
	
	@Override
	public List<WamPdmTbl> getAsisPdmTblList(WamPdmTbl search) {
		
		List<WamPdmTbl> list = asismapper.selectList(search);
		
		return list;
		
	}

	@Override
	public List<WamPdmTbl> getPdmTblHist(WamPdmTbl search) {
		if(search.getRegTypCd() == null)		{
			search.setRegTypCd("ALL");}
		List<WamPdmTbl> list = mapper.selectHist(search);
		return list;

	}

	@Override
	public List<WamPdmTbl> getPdmTblTop30(WamPdmTbl search) {

		List<WamPdmTbl> list = mapper.selectTop30(search);

		return list;

	}

	/**
	 * <PRE>
	 * 1. MethodName : getPdmColList
	 * 2. Comment    :
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 27.
	 * </PRE>
	 *   @return List<WamPdmCol>
	 *   @param search
	 *   @return
	 */
	@Override
	public List<WamPdmCol> getPdmColList(WamPdmCol search) {
		return colmapper.selectList(search);
	}
	
		@Override
	public List<WamPdmCol> getGapPdmColList(WamPdmCol search) {
		return colmapper.selectListGap(search);
	}


	@Override
	public List<WamPdmTbl> getPdmStatHisTblList(WamPdmTbl search){
		List<WamPdmTbl> list = mapper.selectStatHisTbl(search);

		return list;
	}

	@Override
	public List<WamPdmCol> getPdmColDtlList(WamPdmCol search){
		return colmapper.seleccoldtltList(search);
	}

	@Override
	public List<WamPdmTbl> getPdmTblHistList(WamPdmTbl search){
		List<WamPdmTbl> list = mapper.selectHisTbl(search);

		return list;
	}

	@Override
	public List<WamPdmCol> getPdmColHistList(WamPdmTbl search){
		return colmapper.seleccolhisttList(search);
	}

	@Override
	public List<WamPdmTbl> getPdmIdxColList(WamPdmTbl search){
		List<WamPdmTbl> list = mapper.selectIdxCol(search);

		return list;
	}

	/** yeonho */
	@Override
	public List<WamPdmCol> selectPdmColList(WamPdmCol search) {
		return colmapper.selectPdmColList(search);
	}

	/** yeonho */
	@Override
	public List<WamPdmTbl> getPdmTblListForDdl(WamPdmTbl search) {
		List<WamPdmTbl> list = null;
		if(search.getRqstDcd().equals("CU")){
			list = mapper.selectListForDdlCU(search);
		}else if(search.getRqstDcd().equals("DD")){
			list = mapper.selectListForDdlD(search);
		}

		return list;
	}

	/** yeonho */
	@Override
	public List<WamPdmRel> getPdmRelList(WamPdmRel search) {
		return wamPdmRelMapper.getPdmRelList(search);
	}
	
	/** yeonho */
	@Override
	public List<WamPdmRel> getPdmRelHistList(WamPdmRel search) {
		return wamPdmRelMapper.getPdmRelHistList(search);
	}

	/** yeonho */
	@Override
	public List<WamPdmRel> getPdmRelListHistPage(WamPdmRel search) {
		return wamPdmRelMapper.getPdmRelListHistPage(search);
	}

	/** yeonho */
	@Override
	public List<WamPdmRel> getPdmRelHistListHistPage(WamPdmRel search) {
		return wamPdmRelMapper.getPdmRelHistListHistPage(search);
	}
	
	
	/** lsi */
	@Override
	public List<WamPdmCol> getPdmColHistListDtl(WamPdmCol search){
		return colmapper.seleccolhisttListDtl(search);
	}
	
	@Override
	public List<WamPdmCol> getPdmColChgList(WamPdmCol search) {
		return colmapper.selectPdmColChgList(search);
	}
	
	@Override
	public List<WamPdmCol> getColNonStndlist(WamPdmCol search) {
		return colmapper.selectColNonStndList(search);
	}

	@Override
	public List<GovVo> getGovList(GovVo search) {
		// TODO Auto-generated method stub
		return mapper.selectGovList(search);
	}

	@Override
	public GovVo getOrgNm() {
		return mapper.selectOrgNm();
	}

	@Override
	public int regGovServerInfo(ArrayList<WaaGovServerInfo> list) throws Exception {
		
		int result = 0;
		int checkResultCnt = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		for(WaaGovServerInfo record : list) {
			checkResultCnt = regServerInfo(record);
			
			if(checkResultCnt < 0) {
				return checkResultCnt;
			}else {
				result += checkResultCnt;
			}
		}
		return result;
	}

	private int regServerInfo(WaaGovServerInfo record) {
		
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		String tmpStatus = "";
		int result = 0;
		
		record.setWritUserId(user.getUniqId());
		tmpStatus = record.getIbsStatus();
		
		if("I".equals(tmpStatus)) {
			
			result = mapper.updateServerInfo(record); 
			 
			result += mapper.insertServerInfo(record);
					
		}else if("U".equals(tmpStatus)) {
			
//			result = mapper.updateWaaInfoSys(record);
		}
		
		return result;
	}

	@Override
	public WaaGovServerInfo getGovServerInfo() {
		return mapper.getGovServerInfo();
	}
	
	@Override
	public List<GovVo> getGovListForSendFile(GovVo search) {
		// TODO Auto-generated method stub
		return mapper.selectGovListForSendFile(search);
	}

	@Override
	public int connTestGovServer(String resMsg, String resErrMsg) {
		// TODO Auto-generated method stub
		return mapper.connTestGovServer(resMsg, resErrMsg);
	}

	@Override
	public List<PdmTblColDownVo> getPdmTblForDownBaiExl(PdmTblColDownVo search) {
		// TODO Auto-generated method stub
		return mapper.selectPdmTblForDownBaiExl(search);
	}

	@Override
	public List<PdmTblColDownVo> getPdmColForDownBaiExl(PdmTblColDownVo search) {
		// TODO Auto-generated method stub
		return mapper.selectPdmColForDownBaiExl(search);
	}
}
