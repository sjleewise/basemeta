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
package kr.wise.meta.ldm.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.ldm.service.ModelLdmService;
import kr.wise.meta.ldm.service.WamLdmAttr;
import kr.wise.meta.ldm.service.WamLdmAttrMapper;
import kr.wise.meta.ldm.service.WamLdmEnty;
import kr.wise.meta.ldm.service.WamLdmEntyMapper;


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
@Service("modelLdmService")
public class ModelLdmServiceImpl implements ModelLdmService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamLdmEntyMapper mapper;

	@Inject
	private WamLdmAttrMapper attrmapper; 
	
	
	@Override
	public List<WamLdmEnty> getLdmEntyList(WamLdmEnty search) {

		List<WamLdmEnty> list = mapper.selectList(search);

		return list;

	}

	@Override
	public List<WamLdmEnty> getPdmTblHist(WamLdmEnty search) {
		if(search.getRegTypCd() == null)		{
			search.setRegTypCd("ALL");}
		List<WamLdmEnty> list = mapper.selectHist(search);
		return list;

	}

	@Override
	public List<WamLdmEnty> getPdmTblTop30(WamLdmEnty search) {

		List<WamLdmEnty> list = mapper.selectTop30(search);

		return list;

	}

	/**
	 * <PRE>
	 * 1. MethodName : getPdmColList
	 * 2. Comment    :
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 27.
	 * </PRE>
	 *   @return List<WamLdmEnty>
	 *   @param search
	 *   @return
	 */
	@Override
	public List<WamLdmAttr> getLdmAttrList(WamLdmAttr search) {
		return attrmapper.selectList(search);
	}
	
		@Override
	public List<WamLdmAttr> getGapPdmColList(WamLdmAttr search) {
		return attrmapper.selectListGap(search);
	}


	@Override
	public List<WamLdmEnty> getPdmStatHisTblList(WamLdmEnty search){
		List<WamLdmEnty> list = mapper.selectStatHisTbl(search);

		return list;
	}

	@Override
	public List<WamLdmAttr> getPdmColDtlList(WamLdmAttr search){
		return attrmapper.seleccoldtltList(search);
	}

	@Override
	public List<WamLdmEnty> getLdmEntyHistList(WamLdmEnty search){
		
		List<WamLdmEnty> list = mapper.selectHisTbl(search);

		return list;
	}

	@Override
	public List<WamLdmAttr> getLdmAttrHistList(WamLdmEnty search){
		
		return attrmapper.seleAttrHisttList(search);
	}

	@Override
	public List<WamLdmEnty> getPdmIdxColList(WamLdmEnty search){
		List<WamLdmEnty> list = mapper.selectIdxCol(search);

		return list;
	}

	/** meta */
	@Override
	public List<WamLdmAttr> selectPdmColList(WamLdmAttr search) {
		return attrmapper.selectPdmColList(search);
	}

	/** meta */
	@Override
	public List<WamLdmEnty> getPdmTblListForDdl(WamLdmEnty search) {
		List<WamLdmEnty> list = null;
		if(search.getRqstDcd().equals("CU")){
			list = mapper.selectListForDdlCU(search);
		}else if(search.getRqstDcd().equals("DD")){
			list = mapper.selectListForDdlD(search);
		}

		return list;
	}
	
	/** lsi */
	@Override
	public List<WamLdmAttr> getPdmColHistListDtl(WamLdmAttr search){
		return attrmapper.seleccolhisttListDtl(search);
	}
	
	@Override
	public List<WamLdmAttr> getPdmColChgList(WamLdmAttr search) {
		return attrmapper.selectPdmColChgList(search);
	}
	
	@Override
	public List<WamLdmAttr> getColNonStndlist(WamLdmAttr search) {
		return attrmapper.selectColNonStndList(search);
	}
	
}
