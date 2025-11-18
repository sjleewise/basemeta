/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CodeGapServiceImpl.java
 * 2. Package : kr.wise.meta.gap.service.impl
 * 3. Comment : 
 * 4. 작성자  : yeonho
 * 5. 작성일  : 2014. 9. 23. 오후 4:21:07
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    yeonho : 2014. 9. 23. :            : 신규 개발.
 */
package kr.wise.meta.gap.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.gap.service.CodeGapMapper;
import kr.wise.meta.gap.service.CodeGapService;
import kr.wise.meta.gap.service.CodeGapVO;

import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : CodeGapServiceImpl.java
 * 3. Package  : kr.wise.meta.gap.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 9. 23. 오후 4:21:07
 * </PRE>
 */
@Service("CodeGapService")
public class CodeGapServiceImpl implements CodeGapService {

	@Inject
	private CodeGapMapper codeGapMapper;

	/** yeonho */
	@Override
	public List<CodeGapVO> getCodeGapAnalyze(CodeGapVO search) {
		return codeGapMapper.getCodeGapAnalyze(search);
	}

	/** yeonho */
	@Override
	public List<CodeGapVO> getCodeValList(CodeGapVO search) {
		return codeGapMapper.getCodeValList(search);
	}

	/** yeonho */
	@Override
	public List<CodeGapVO> getDmnValList(CodeGapVO search) {
		return codeGapMapper.getDmnValList(search);
	}
	
	@Override
	public List<CodeGapVO> getCodeGapMetaDev(CodeGapVO search) {
		return codeGapMapper.selectCodeGapMetaDev(search);
	}
	
	@Override
	public List<CodeGapVO> getCodeGapMetaReal(CodeGapVO search) {
		return codeGapMapper.selectCodeGapMetaReal(search);
	}
	
	@Override
	public List<CodeGapVO> getCodeGapMetaTsf(CodeGapVO search) {
		return codeGapMapper.selectCodeGapMetaTsf(search);
	}
	@Override
	public List<CodeGapVO> getCodeGapDevReal(CodeGapVO search) {
		return codeGapMapper.selectCodeGapDevReal(search);
	}
	@Override
	public List<CodeGapVO> getCmpCodeGapMetaDev(CodeGapVO search) {
		return codeGapMapper.selectCmpCodeGapMetaDev(search);
	}
	
	@Override
	public List<CodeGapVO> getCmpCodeGapMetaReal(CodeGapVO search) {
		return codeGapMapper.selectCmpCodeGapMetaReal(search);
	}
	
	@Override
	public List<CodeGapVO> getCmpCodeGapMetaTsf(CodeGapVO search) {
		return codeGapMapper.selectCmpCodeGapMetaTsf(search);
	}
	@Override
	public List<CodeGapVO> getCmpCodeGapDevReal(CodeGapVO search) {
		return codeGapMapper.selectCmpCodeGapDevReal(search);
	}
}
