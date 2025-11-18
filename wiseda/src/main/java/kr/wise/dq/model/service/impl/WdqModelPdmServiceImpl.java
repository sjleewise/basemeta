/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : WdqModelPdmService.java
 * 2. Package : kr.wise.meta.model.service
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 26. 오후 5:48:57
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 26. 		: 신규 개발.
 */
package kr.wise.dq.model.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.dq.model.service.WdqModelPdmService;
import kr.wise.dq.model.service.WdqmPdmCol;
import kr.wise.dq.model.service.WdqmPdmColMapper;
import kr.wise.dq.model.service.WdqmPdmTbl;
import kr.wise.dq.model.service.WdqmPdmTblMapper;

/**
 * <PRE>
 * 1. ClassName : WdqModelPdmService
 * 2. Package  : kr.wise.meta.model.service
 * 3. Comment  :
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 26.
 * </PRE>
 */
@Service("wdqModelPdmService")
public class WdqModelPdmServiceImpl implements WdqModelPdmService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WdqmPdmTblMapper mapper;
	
	@Inject
	private WdqmPdmColMapper colmapper;

	@Override
	public List<WdqmPdmTbl> getPdmTblList(WdqmPdmTbl search) {

		List<WdqmPdmTbl> list = mapper.selectList(search);

		return list;

	}
	
	@Override
	public List<WdqmPdmTbl> getPdmTblHist(WdqmPdmTbl search) {
		if(search.getRegTypCd() == null)		{
			search.setRegTypCd("ALL");}
		List<WdqmPdmTbl> list = mapper.selectHisTbl(search);
		return list;

	}

	@Override
	public List<WdqmPdmCol> getPdmColList(WdqmPdmCol search) {
		return colmapper.selectList(search);
	}

	@Override
	public List<WdqmPdmTbl> getPdmTblHistList(WdqmPdmTbl search){
		List<WdqmPdmTbl> list = mapper.selectHisTbl(search);

		return list;
	}

	@Override
	public List<WdqmPdmCol> getPdmColHistList(WdqmPdmTbl search){
		return colmapper.selectcolhisttList(search);
	}
}
