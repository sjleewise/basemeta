/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : ModelPdmService.java
 * 2. Package : kr.wise.meta.model.service
 * 3. Comment :
 * 4. 작성자  : 
 * 5. 작성일  : 
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *     	:  		: 신규 개발.
 */
package kr.wise.meta.model.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.model.service.AsisModelPdmService;
import kr.wise.meta.model.service.WamAsisPdmColMapper;
import kr.wise.meta.model.service.WamAsisPdmTblMapper;
import kr.wise.meta.model.service.WamPdmCol;
import kr.wise.meta.model.service.WamPdmRel;
import kr.wise.meta.model.service.WamPdmRelMapper;
import kr.wise.meta.model.service.WamPdmTbl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : ModelPdmService
 * 2. Package  : kr.wise.meta.model.service
 * 3. Comment  :
 * 4. 작성자   : 
 * 5. 작성일   : 
 * </PRE>
 */
@Service("asisModelPdmService")
public class AsisModelPdmServiceImpl implements AsisModelPdmService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamAsisPdmTblMapper asismapper;

	@Inject
	private WamAsisPdmColMapper asiscolmapper;
	
	@Inject
	private WamPdmRelMapper wamPdmRelMapper;

	@Override
	public List<WamPdmTbl> getPdmTblList(WamPdmTbl search) {

		List<WamPdmTbl> list = asismapper.selectList(search);

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
		List<WamPdmTbl> list = asismapper.selectHist(search);
		return list;

	}

	@Override
	public List<WamPdmTbl> getPdmTblTop30(WamPdmTbl search) {

		List<WamPdmTbl> list = asismapper.selectTop30(search);

		return list;

	}

	/**
	 * <PRE>
	 * 1. MethodName : getPdmColList
	 * 2. Comment    :
	 * 3. 작성자       : 
	 * 4. 작성일       : 
	 * </PRE>
	 *   @return List<WamPdmCol>
	 *   @param search
	 *   @return
	 */
	@Override
	public List<WamPdmCol> getPdmColList(WamPdmCol search) {
		return asiscolmapper.selectList(search);
	}
	
		@Override
	public List<WamPdmCol> getGapPdmColList(WamPdmCol search) {
		return asiscolmapper.selectListGap(search);
	}


	@Override
	public List<WamPdmTbl> getPdmStatHisTblList(WamPdmTbl search){
		List<WamPdmTbl> list = asismapper.selectStatHisTbl(search);

		return list;
	}

	@Override
	public List<WamPdmCol> getPdmColDtlList(WamPdmCol search){
		return asiscolmapper.seleccoldtltList(search);
	}

	@Override
	public List<WamPdmTbl> getPdmTblHistList(WamPdmTbl search){
		List<WamPdmTbl> list = asismapper.selectHisTbl(search);

		return list;
	}

	@Override
	public List<WamPdmCol> getPdmColHistList(WamPdmTbl search){
		return asiscolmapper.seleccolhisttList(search);
	}

	@Override
	public List<WamPdmTbl> getPdmIdxColList(WamPdmTbl search){
		List<WamPdmTbl> list = asismapper.selectIdxCol(search);

		return list;
	}

	/** yeonho */
	@Override
	public List<WamPdmCol> selectPdmColList(WamPdmCol search) {
		return asiscolmapper.selectPdmColList(search);
	}

	/** yeonho */
	@Override
	public List<WamPdmTbl> getPdmTblListForDdl(WamPdmTbl search) {
		List<WamPdmTbl> list = null;
		if(search.getRqstDcd().equals("CU")){
			list = asismapper.selectListForDdlCU(search);
		}else if(search.getRqstDcd().equals("DD")){
			list = asismapper.selectListForDdlD(search);
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
		return asiscolmapper.seleccolhisttListDtl(search);
	}
	
	@Override
	public List<WamPdmCol> getPdmColChgList(WamPdmCol search) {
		return asiscolmapper.selectPdmColChgList(search);
	}
	
	@Override
	public List<WamPdmCol> getColNonStndlist(WamPdmCol search) {
		return asiscolmapper.selectColNonStndList(search);
	}
}
